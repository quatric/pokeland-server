#!/usr/bin/env bash
# Bring the whole test rig up from nothing: emulator -> clock -> server.
#
# The AVD crashes fairly often ("Failed to find ColorBuffer") and comes back on
# real time, which silently re-arms the 2020-07-22 End-of-Service gate - so the
# clock sync and the matching PokelandClock.RealAnchor edit have to happen
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
# "Desired shader compiler platform 9 is not available in shader blob" is NOT
# specific to -gpu host - it fires under swiftshader too, and it is survivable.
# Platform 9 is GLES3Plus, and every shader in the retail CDN bundles carries
# only platforms 5 (GLES2, verified by decompressing a blob to "#version 100")
# and 14 (Metal). The APK meanwhile declares m_GraphicsAPIs = [21, 11] =
# Vulkan, GLES3, and its built-in shaders are {9, 18}. So a GLES3 context takes
# the GLES2 bundle blobs for most shaders and only a handful fall through to
# magenta - which is why the globe island renders magenta.
#
# Do NOT "fix" this by forcing GLES2 with `-feature -GLESDynamicVersion`. That
# does work (ro.opengles.version drops to 131072 and Unity switches to asking
# for platform 5), but the APK's own built-ins have no GLES2 variant, so the
# entire screen turns magenta instead of just the island. GLES3 is the strictly
# better of the two.
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
anchor=$(bash "$REPO/tools/sync_device_clock.sh" | grep 'RealAnchor = new' | sed 's/^ *//')
sed -i '' "s|.*RealAnchor = new(.*|    private static readonly DateTime $(echo "$anchor" | sed 's|.*DateTime ||')|" \
    "$REPO/server/Pokeland.Server/PokelandClock.cs"
grep -n 'RealAnchor = new' "$REPO/server/Pokeland.Server/PokelandClock.cs"

echo "== restarting server"
lsof -ti tcp:5199 | while read -r p; do kill -9 "$p"; done || true
cd "$REPO/server/Pokeland.Server"
rm -rf "${POKELAND_WIRE_DIR:=/private/tmp/pokeland-wire}"
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
