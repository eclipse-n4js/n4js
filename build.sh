#!/bin/bash

#
# Small helper script to build the project inside a container.
# Usage:
#   ./build.sh mvn package ...

# build docker image
docker build -t n4js-build docker-build/

# run the docker container
docker run -ti --rm \
    -v m2_repo:/root/.m2/repository \
    -v $(pwd):/workspace \
    n4js-build $@
