#!/bin/bash
#
# Copyright (c) 2018 NumberFour AG.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#   NumberFour AG - Initial API and implementation
#
set -e

cd `dirname $0`
cd `pwd -P`

DIR_ROOT=`pwd`

echo "We are currently in $PWD"

function echo_exec {
    echo "$@"
    $@
}

if [ -f .npmrc ]; then
	rm .npmrc
fi

HOST_NAME=`hostname`
NPM_TAG=test
NPM_REGISTRY="http://localhost:${NPM_REGISTRY_PORT}"

# Create an .npmrc only if we are publishing to the public
if [ "$DESTINATION" = "public" ]; then
	if [ -z $NPM_TOKEN]; then
		echo "Publishing to public requires the NPM_TOKEN to be set but it has not been set!"
		exit 0;
	fi
	NPM_TAG=latest
else
	# Dummy token
	NPM_TOKEN=dummy	
fi;
NPM_REGISTRY_WITHOUT_PROTOCOL=$(echo ${NPM_REGISTRY} | awk -F"//" '{print $2}')


DIRS=$(find ./packages/ -type d -mindepth 1 -maxdepth 1)

for dir in $DIRS
do
	touch "$dir/.npmrc"
	echo "//${NPM_REGISTRY_WITHOUT_PROTOCOL}/:_authToken=${NPM_TOKEN}" > "$dir/.npmrc"
done

echo "Publishing using .npmrc configuration";
echo "${NPM_REGISTRY}"
#export NPM_CONFIG_USERCONFIG="$DIR_ROOT/packages/n4js-node/.npmrc"
#ls -la ./packages/n4js-node
#echo "${NPM_CONFIG_USERCONFIG}"
lerna publish --loglevel silly --skip-git --registry="${NPM_REGISTRY}" --exact --canary --yes --sort --npm-tag="${NPM_TAG}" --force-publish
