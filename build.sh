#!/bin/bash

#
# Small helper script to build the project inside a container.
#
# Builds a Docker image from n4js-build/Dockerfile
# and executes it
# to actually do the heavy build-lifting.
#
# Usage:
#   ./build.sh mvn package ...

docker build -t n4js-build docker-build/

docker run -ti --rm      \
    -v ~/.m2:/root/.m2/:cached  \
    -v $(pwd):/workspace:cached \
    n4js-build $@
