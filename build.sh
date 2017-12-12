#!/bin/bash

#
# Small helper script to build the project inside a container.
# Builds a Docker image from n4js-build/Dockerfile and executes it to
# actually do the heavy build-lifting.
#
# Usage:
#   ./build.sh mvn package ...
#
# You may want to set the environment variable
#    MAVEN_CENTRAL_URL
# to a local/company Maven proxy (nexus, artifactory, archiva) so speed up builds.

docker build                                            \
       --build-arg MAVEN_CENTRAL_URL=$MAVEN_CENTRAL_URL \
       -t n4js-build                                    \
       docker-build/

docker run -ti --rm      \
    -u `id -u`:`id -g`   \
    -v ~/.m2:/root/.m2/  \
    -v $(pwd):/workspace \
    n4js-build $@
