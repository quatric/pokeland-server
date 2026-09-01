#!/usr/bin/env bash
# Drive the game RPCs straight over curl, no emulator involved.
#
# The device loop (boot -> onboarding taps -> tutorial) takes minutes and the
# AVD dies partway through often enough that inspecting one response shape used
# to cost a full bring-up. Login here, keep the SessionID, and POST whatever
# endpoint you want to look at:
#
#     tools/rpc.sh StartStage '{"Mode":0,"StageCode":[1,0,1,1],"TZOffsetMin":0}'
#
# It prints the response JSON (pretty-printed) for that endpoint.
set -euo pipefail
BASE=${BASE:-http://127.0.0.1:5199}
EP=${1:?usage: rpc.sh <Endpoint> [extra-json-fields]}
EXTRA=${2:-'{}'}

login=$(curl -sS -X POST "$BASE/1.600/game" -H 'Content-Type: application/json' \
    -d '{"Endpoint":"Login","Rev":0,"Market":"US","AppVer":"1.6.0","AssetVer":0,"TZOffsetMin":0}')
sid=$(printf '%s' "$login" | python3 -c 'import json,sys; print(json.load(sys.stdin)["SessionID"])')

if [ "$EP" = "Login" ]; then printf '%s' "$login" | python3 -m json.tool; exit 0; fi

body=$(EP="$EP" SID="$sid" EXTRA="$EXTRA" python3 -c '
import json, os
b = json.loads(os.environ["EXTRA"])
b.update(Endpoint=os.environ["EP"], Rev=0, SessionID=os.environ["SID"])
print(json.dumps(b))')

curl -sS -X POST "$BASE/1.600/game" -H 'Content-Type: application/json' -d "$body" \
    | python3 -m json.tool
