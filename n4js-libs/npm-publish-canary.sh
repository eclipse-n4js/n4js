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
	for dir in $DIRS
	do
		rm -rf "$dir/node_modules"
	done
	rm -rf "${DIR_ROOT}/node_modules"
	set -e
}

export NPM_CONFIG_GLOBALCONFIG="DIR_ROOT"
echo "Publishing using .npmrc configuration to ${NPM_REGISTRY}";

# Check if there are changes in n4js-libs

PKG_VERSION=`cat lerna.json  | jq -r '.version' | xargs -t semver -i minor {}`

N4JS_LIBS_COMMIT_ID_LOCAL=`git log -1 --format="%H" $DIRS | cut -c1-8`

#  Check the latest commit on public npm registry, use n4js-node as a representative
N4JS_LIBS_VERSION_PUBLIC=`curl -s ${NPM_REGISTRY}/n4js-node | jq -r '.["dist-tags"].latest'`
echo "N4JS_LIBS_VERSION_PUBLIC=${N4JS_LIBS_VERSION_PUBLIC}"

# The commit ID is the last 8 letters of public version
N4JS_LIBS_COMMIT_ID_PUBLIC=${N4JS_LIBS_VERSION_PUBLIC: -8}

# Stop if the local ID equals public ID
if [${N4JS_LIBS_COMMIT_ID_LOCAL} -eq ${N4JS_LIBS_COMMIT_ID_PUBLIC}]
then
    echo "n4js-libs has NOT been changed since the last publish. Local commit ID = Public commit ID = ${N4JS_LIBS_COMMIT_ID_LOCAL}. n4js-libs will not be published."
    exit 0
else
    echo "n4js-libs has not been changed since the last publish. Local commit ID = ${N4JS_LIBS_COMMIT_ID_LOCAL} while public commit ID = ${N4JS_LIBS_COMMIT_ID_PUBLIC}"
fi

CURRENT_DATE=`date +%Y%m%d`

PUBLISH_VERSION="${PKG_VERSION}-alpha.${CURRENT_DATE}+${N4JS_LIBS_COMMIT_ID_LOCAL}" 
echo "PUBLISH_VERSION=${PUBLISH_VERSION}"


if [ "${NPM_TAG}" = "latest" ]; then
	echo_exec lerna publish --loglevel silly --skip-git --registry="${NPM_REGISTRY}" --repo-version="${PUBLISH_VERSION}" --exact --canary --yes --sort --npm-tag="${NPM_TAG}"
	echo "BLAH latest"
else
	# Use a version that we are sure can not exist on public npm registry for 
	echo_exec lerna publish --loglevel silly --skip-git --registry="${NPM_REGISTRY}" --repo-version="9999.0.0" --exact --canary --yes --sort --npm-tag="${NPM_TAG}"
	echo "BLAH not latest"
fi

# Remove node_modules after publishing
cleanup
