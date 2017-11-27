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

docker build                                            \
       --build-arg MAVEN_CENTRAL_URL=$MAVEN_CENTRAL_URL \
       -t n4js-build                                    \
       docker-build/

docker run -ti --rm         \
    -v ~/.ssh2:/root/.ssh2/ \
    -v ~/.m2:/root/.m2/     \
    -v $(pwd):/workspace    \
    n4js-build              \
    $@
