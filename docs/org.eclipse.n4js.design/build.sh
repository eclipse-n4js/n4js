#!/bin/bash

# -e  == exit immediately
# -x  == enable debug. (+x for disable)
set -e +x

MAIN_FILE="N4JSDesign"

docker run --rm --tty \
  --user $(id -u):$(id -g) \
  --volume $(pwd):/workspace:rw \
  --env DOC_NAME=$MAIN_FILE \
  docker-corp.numberfour.eu/numberfour/asciispec:0.0.12 "$@"

# running "./build.sh -p" (preview) will skip PDF and launch index.html
if [ "${1}" == "-p" ]; then
  open ./generated-docs/$MAIN_FILE.html
  exit 0
fi
