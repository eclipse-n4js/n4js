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

# DIRS contains all direct folders of packages
DIRS=$(find ./packages/ -type d -mindepth 1 -maxdepth 1)

function echo_exec {
    echo "$@"
    $@
}

cleanup() {
	set +e
	# Remove node_modules before and after publishing
	for dir in $DIRS
	do
		rm -rf "$dir/node_modules"
	done
	rm -rf "${DIR_ROOT}/node_modules"
	set -e
}

cleanup
export NPM_CONFIG_GLOBALCONFIG="DIR_ROOT"
echo "Publishing using .npmrc configuration to ${NPM_REGISTRY}";

if [ "${NPM_TAG}" = "latest" ]; then
	lerna publish --loglevel silly --skip-git --registry="${NPM_REGISTRY}" --exact --canary --yes --sort --npm-tag="${NPM_TAG}"
else
	# Use a version that we are sure can not exist on public npm registry for 
	lerna publish --loglevel silly --skip-git --registry="${NPM_REGISTRY}" --repo-version="9999.0.0" --exact --canary --yes --sort --npm-tag="${NPM_TAG}"
fi

cleanup
