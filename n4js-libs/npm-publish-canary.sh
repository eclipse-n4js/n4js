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
set -eo pipefail

# use the following for excessive logging:
#set -x


# regular expression for extracting the commit ID from a GitHub URL of the form:
# https://github.com/eclipse/n4js/tree/89a8d09e9dcd924ae1ffeb03138fb9140e6e370d/n4js-libs/packages/n4js-node
EXTRACT_COMMIT_ID_REGEX=".*\/n4js\/tree\/([^\/]+)\/n4js-libs\/packages\/.*"

# this is the n4js-libs package that will be used to obtain the version number and commit ID
# of the latest published version of all n4js-libs packages
N4JS_LIBS_REPRESENTATIVE="n4js-node"


cd `dirname $0`
cd `pwd -P`

DIR_ROOT=`pwd`

echo "We are currently in $PWD"

# DIRS contains all direct folders of packages
DIRS=$(find ./packages/ -type d -mindepth 1 -maxdepth 1)

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

if [ "${NPM_TAG}" = "latest" ]; then
    # GH-1113: Since lerna.json may have been changed by local publishing, reverse all changes
    # We only publish if there are changes in n4js-libs since the last commit
    #PKG_VERSION=`cat lerna.json  | jq -r '.versionfixed' | xargs -t semver -i minor {}`

    echo "Checking whether publication of n4js-libs is required (using ${N4JS_LIBS_REPRESENTATIVE} as representative) ..."

    # Check the latest commit on npm registry, using the representative
    N4JS_LIBS_VERSION_PUBLIC=`curl -s ${NPM_REGISTRY}/${N4JS_LIBS_REPRESENTATIVE} | jq -r '.["dist-tags"].latest'`
    echo "  - version of latest published n4js-libs       : $N4JS_LIBS_VERSION_PUBLIC"

    N4JS_LIBS_COMMIT_ID_PUBLIC=`curl -s ${NPM_REGISTRY}/${N4JS_LIBS_REPRESENTATIVE} | jq -r '.versions."'${N4JS_LIBS_VERSION_PUBLIC}'".gitHead'`
    if [ "${N4JS_LIBS_COMMIT_ID_PUBLIC}" = "null" ]; then
        N4JS_LIBS_COMMIT_ID_PUBLIC=unknown
    fi
    echo "  - commit ID of latest published n4js-libs     : $N4JS_LIBS_COMMIT_ID_PUBLIC"

    N4JS_LIBS_COMMIT_ID_LOCAL=`git log -1 --format="%H" ${DIR_ROOT}`
    echo "  - commit ID of local n4js-libs                : $N4JS_LIBS_COMMIT_ID_LOCAL"

    # Stop if the local ID equals public ID
    if [ "${N4JS_LIBS_COMMIT_ID_LOCAL}" = "${N4JS_LIBS_COMMIT_ID_PUBLIC}" ]; then
        echo '==> will NOT publish (because commit IDs are identical, i.e. no changes since last publication)'
        exit 0
    else
        echo '==> will publish (because commit IDs are different, i.e. there are changes since last publication)'
    fi

    # update repository meta-info in package.json of all n4js-libs to point to the GitHub and the correct commit
    # (yarn isn't doing this at the moment: https://github.com/yarnpkg/yarn/issues/2978 )
    echo "Updating property 'gitHead' in package.json of all n4js-libs to new local commit ID ..."
    lerna exec -- cp package.json package.json_TEMP
    lerna exec -- 'jq -r ".gitHead |= \"'$N4JS_LIBS_COMMIT_ID_LOCAL'\"" package.json_TEMP > package.json'
    lerna exec -- rm package.json_TEMP

    # enforce consistent repository meta-info in package.json of all n4js-libs
    echo "Setting property 'repository' in package.json of all n4js-libs (for consistency) ..."
    lerna exec -- cp package.json package.json_TEMP
    lerna exec -- 'jq -r ".repository |= {type: \"git\", url: \"https://github.com/eclipse/n4js/tree/master/n4js-libs/packages/$LERNA_PACKAGE_NAME\"}" package.json_TEMP > package.json'
    lerna exec -- rm package.json_TEMP


    #PUBLISH_VERSION=`semver -i patch ${N4JS_LIBS_VERSION_PUBLIC}`
    PUBLISH_VERSION="0.12.100"
    echo "Now publishing with version: ${PUBLISH_VERSION}"

    lerna publish --loglevel info --skip-git --registry="${NPM_REGISTRY}" --repo-version="${PUBLISH_VERSION}" --exact --yes --bail --npm-tag="${NPM_TAG}"
else
    # Use a version that we are sure can not exist on public npm registry for 
    lerna publish --loglevel info --skip-git --registry="${NPM_REGISTRY}" --repo-version="9999.0.0" --exact --yes --bail --npm-tag="${NPM_TAG}"
fi

# Remove node_modules after publishing
cleanup
