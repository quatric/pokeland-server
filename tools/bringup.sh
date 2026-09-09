#!/usr/bin/env bash
# Bring the whole test rig up from nothing: emulator -> server -> game.
# tools/build_apk.sh disables the retired client's end-of-service flag, so the
# emulator and server both stay on the normal current clock.
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

# Undo the old test rig's rollback settings on existing AVDs. Android obtains
# the actual time from its configured network source; no root access is needed.
adb shell settings put global auto_time 1 >/dev/null 2>&1 || true
adb shell settings put global auto_time_zone 1 >/dev/null 2>&1 || true
echo "== device time: $(adb shell date -u | tr -d '\r')"

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
