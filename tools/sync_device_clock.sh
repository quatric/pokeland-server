#!/bin/bash
# Roll the device clock back past the client's End-of-Service cutoff, and print
# the matching RealAnchor line for server/Pokeland.Server/PokelandClock.cs.
#
# The two have to be set together: PokelandClock derives its offset as
# DeviceEpoch + (now - RealAnchor), so RealAnchor must be the real UTC instant
# at which the device was set to DeviceEpoch. Anchoring to a constant rather
# than to "whenever the process started" is what keeps the offset stable across
# server restarts.
#
# Usage: sync_device_clock.sh [DeviceEpoch as YYYY-MM-DDTHH:MM:SSZ]
set -euo pipefail

EPOCH="${1:-2020-06-20T20:00:00Z}"
Y=${EPOCH:0:4} MO=${EPOCH:5:2} D=${EPOCH:8:2}
H=${EPOCH:11:2} MI=${EPOCH:14:2} S=${EPOCH:17:2}

# Android's own time sync would drag the clock straight back to real time.
adb shell settings put global auto_time 0
adb shell settings put global auto_time_zone 0

# `date` on the device runs in the device's timezone; force it to UTC so the
# value we set is the value we mean.
adb shell su 0 setprop persist.sys.timezone UTC >/dev/null 2>&1 || true

REAL_BEFORE=$(date -u +%Y-%m-%dT%H:%M:%SZ)
adb shell "date -u ${MO}${D}${H}${MI}${Y}.${S}" >/dev/null
REAL_AFTER=$(date -u +%Y-%m-%dT%H:%M:%SZ)

echo "device now: $(adb shell date -u | tr -d '\r')"
echo "real clock at the moment of the set: $REAL_BEFORE .. $REAL_AFTER"
echo
echo "paste into server/Pokeland.Server/PokelandClock.cs:"
printf '    private static readonly DateTime RealAnchor = new(%d, %d, %d, %d, %d, %d, DateTimeKind.Utc);\n' \
    "${REAL_AFTER:0:4}" "$((10#${REAL_AFTER:5:2}))" "$((10#${REAL_AFTER:8:2}))" \
    "$((10#${REAL_AFTER:11:2}))" "$((10#${REAL_AFTER:14:2}))" "$((10#${REAL_AFTER:17:2}))"
printf '    private static readonly DateTime DeviceEpoch = new(%d, %d, %d, %d, %d, %d, DateTimeKind.Utc);\n' \
    "$((10#$Y))" "$((10#$MO))" "$((10#$D))" "$((10#$H))" "$((10#$MI))" "$((10#$S))"
