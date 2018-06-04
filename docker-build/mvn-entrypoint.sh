#! /bin/bash -eu

set -o pipefail

# Start docker daemon
/usr/bin/dockerd &

exec "$@"
