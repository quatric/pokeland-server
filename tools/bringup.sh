#!/usr/bin/env bash
# Bring the whole test rig up from nothing: emulator -> clock -> server.
#
# The AVD crashes fairly often ("Failed to find ColorBuffer") and comes back on
# real time, which silently re-arms the 2020-07-22 End-of-Service gate - so the
# clock sync and the matching server clock environment have to be captured
# together, every single time, before the server starts. That coupling is the
# whole reason this script exists.
set -euo pipefail
export ANDROID_HOME=${ANDROID_HOME:-/opt/homebrew/share/android-commandlinetools}
export PATH="$PATH:$ANDROID_HOME/platform-tools"
REPO="$(cd "$(dirname "$0")/.." && pwd)"
AVD=${AVD:-pokeland30}
# swiftshader_indirect spews "Failed to find ColorBuffer" and takes the emulator
# down every so often, but it is still the best mode for this game. Keep it and
# just re-run this script when the emulator dies.
#
# The converted CDN bundles have GLES2 programs, while the retail player has
# GLES3/Vulkan programs. tools/build_apk.sh resolves that mismatch statically:
# it supplies a complete GLES2 player shader set and selects OpenGLES2. Emulator
# flags or a runtime instrumentation hook are not required.
GPU=${GPU:-swiftshader_indirect}

if ! adb shell true >/dev/null 2>&1; then
    echo "== starting emulator $AVD"
    nohup "$ANDROID_HOME/emulator/emulator" -avd "$AVD" -no-window \
        -gpu "$GPU" > /private/tmp/emu.log 2>&1 &
    adb wait-for-device
    adb shell 'while [ "$(getprop sys.boot_completed)" != 1 ]; do sleep 3; done'
fi
adb root >/dev/null 2>&1 || true
sleep 3; adb wait-for-device

echo "== syncing device clock"
clock_output=$(bash "$REPO/tools/sync_device_clock.sh")
printf '%s\n' "$clock_output"
POKELAND_DEVICE_EPOCH=$(printf '%s\n' "$clock_output" | sed -n 's/^POKELAND_DEVICE_EPOCH=//p')
POKELAND_REAL_ANCHOR=$(printf '%s\n' "$clock_output" | sed -n 's/^POKELAND_REAL_ANCHOR=//p')
[ -n "$POKELAND_DEVICE_EPOCH" ] || { echo "device epoch was not reported" >&2; exit 1; }
[ -n "$POKELAND_REAL_ANCHOR" ] || { echo "real anchor was not reported" >&2; exit 1; }
export POKELAND_DEVICE_EPOCH POKELAND_REAL_ANCHOR

echo "== restarting server"
lsof -ti tcp:5199 | while read -r p; do kill -9 "$p"; done || true
cd "$REPO/server/Pokeland.Server"
if [ -z "${POKELAND_WIRE_DIR:-}" ]; then
    POKELAND_WIRE_DIR=$(mktemp -d /private/tmp/pokeland-wire.XXXXXX)
else
    mkdir -p "$POKELAND_WIRE_DIR"
fi
export POKELAND_WIRE_DIR
nohup dotnet run --urls http://0.0.0.0:5199 > /private/tmp/pokeland-server.log 2>&1 &
for _ in $(seq 30); do
    grep -q "Now listening" /private/tmp/pokeland-server.log && break
    sleep 2
done
grep -m1 "Now listening" /private/tmp/pokeland-server.log

echo "== launching game"
adb shell am force-stop jp.pokemon.pokemonscrambleSP
adb logcat -c
adb shell am start -n jp.pokemon.pokemonscrambleSP/com.google.firebase.MessagingUnityPlayerActivity >/dev/null
echo "ready"
