#!/bin/bash

#
# Small helper script to build the project inside a container.
# Usage:
#   ./build.sh mvn package ...

# build docker image
docker build --build-arg MAVEN_CENTRAL_URL=$MAVEN_CENTRAL_URL -t n4js-build docker-build/

# run the docker container
docker run -ti --rm      \
    -u `id -u`:`id -g`   \
    -v ~/.m2:/root/.m2/  \
    -v $(pwd):/workspace \
    n4js-build $@
