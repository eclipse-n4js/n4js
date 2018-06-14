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

HOST_NAME=`hostname`

# Turn http://localhost:4873 -> localhost:4873
NPM_REGISTRY_WITHOUT_PROTOCOL=$(echo ${NPM_REGISTRY} | awk -F"//" '{print $2}')

# Create .npmrc with auth token in all npm projects
DIRS=$(find ./packages/ -type d -mindepth 1 -maxdepth 1)
for dir in $DIRS
do
	touch "$dir/.npmrc"
	echo -e "\n//${NPM_REGISTRY_WITHOUT_PROTOCOL}/:_authToken=\${NPM_TOKEN}" > "$dir/.npmrc"
done

echo "Publishing using .npmrc configuration to ${NPM_REGISTRY}";

lerna publish --loglevel silly --skip-git --registry="${NPM_REGISTRY}" --exact --canary --yes --sort --npm-tag="${NPM_TAG}"

# Remove .npmrc for now
for dir in $DIRS
do
	rm "$dir/.npmrc"
done
