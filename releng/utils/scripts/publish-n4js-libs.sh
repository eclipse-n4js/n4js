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


# The first parameter is the destination and must be 'local', 'staging', or 'public'
if [ -z "$1" ]; then
    echo "The destination ('local', 'staging', or 'public') must be specified as the first parameter."
    exit -1
else
    export DESTINATION=$1
    if [ "$DESTINATION" = "public" ]; then
        export NPM_REGISTRY="http://registry.npmjs.org"
    elif [ "$DESTINATION" = "staging" ]; then
        export NPM_REGISTRY="http://n4ide1-nexus.service.cd-dev.consul/repository/npm-internal"
    elif [ "$DESTINATION" = "local" ]; then
        export NPM_REGISTRY="http://localhost:4873"
    else
        echo "Invalid destination: $DESTINATION (must be 'local', 'staging', or 'public')"
        exit -1
    fi
fi

# The second parameter is the version.
if [ -z "$2" ]; then
    echo "The version must be specified as the second parameter."
    exit -1
else
    export PUBLISH_VERSION=$2
fi

# The optional third parameter is the npm dist-tag
if [ -z "$3" ]; then
    DIST_TAG="latest"
else
    DIST_TAG=$3
fi


echo "==== PUBLISH N4JS-LIBS (including n4js-cli)"

echo "Destination: $DESTINATION"
echo "Registry   : $NPM_REGISTRY"
echo "Version    : $PUBLISH_VERSION"
echo "Dist-tag   : $DIST_TAG"

# Set working directory to root folder of repository
# (we assume this scripts is located in folder /releng/utils/scripts)
cd `dirname $0`
cd ../../..
REPO_ROOT_DIR=`pwd -P`

# Navigate to n4js-libs folder
cd n4js-libs

echo "Repository root directory: ${REPO_ROOT_DIR}"
echo "Current working directory: $PWD"

echo "==== STEP 0/8: add .npmrc publish token"
echo '//registry.npmjs.org/:_authToken=${NPM_TOKEN}' >> .npmrc
echo '//localhost:4873/:_authToken=${NPM_TOKEN}' >> .npmrc
echo '//localhost:4874/:_authToken=${NPM_TOKEN}' >> .npmrc

echo "==== STEP 1/8: check preconditions"
# check NPM_TOKEN
if [ "$DESTINATION" = "local" ]; then
    echo "Publishing to local -> setting environment variable NPM_TOKEN to a dummy value"
    export NPM_TOKEN=dummy
else
    if [ -z "$NPM_TOKEN" ]; then
        echo "Publishing to 'public' or 'staging' requires the environment variable NPM_TOKEN to be set but it has not been set!"
        exit -1
    fi
    echo "Environment variable NPM_TOKEN is set to: $NPM_TOKEN"
fi
# check consistency of dist-tag and pre-release segment (does not apply when publishing to 'local')
if [ "$DESTINATION" != "local" ]; then
    if [ "$DIST_TAG" = "latest" ]; then
        if [[ "$PUBLISH_VERSION" == *"-"* ]]; then
            echo "When publishing to dist-tag 'latest', the version must not include a pre-release segment!"
            exit -1
        fi
    else
        if [[ "$PUBLISH_VERSION" != *"-$DIST_TAG"* ]]; then
            echo "When publishing to dist-tag other than 'latest', the version must include a pre-release segment starting with the dist-tag!"
            exit -1
        fi
    fi
    echo "Dist-tag and pre-release segment are consistent."
fi

echo "==== STEP 2/8: clean up (clean yarn cache, etc.)"
yarn cache clean
rm -rf $(find . -type d -name "node_modules")
# Since we include the commit ID in the published artifacts, we should
# make sure to not publish any dirty state in the working copy.
# Thus, we reset the working copy here:
git checkout HEAD -- .
# obtain commit ID of folder 'n4js-libs' in the local git working copy:
N4JS_LIBS_COMMIT_ID_LOCAL=`git log -1 --format="%H" -- .`

echo "==== STEP 3/8: install dependencies and prepare npm task scripts"
yarn install
export PATH=`pwd`/node_modules/.bin:${PATH}

echo "==== STEP 4/8: run 'lerna run build/test' on n4js-libs"
export N4_N4JSC_JAR="${REPO_ROOT_DIR}/target/n4jsc.jar"
lerna run build
lerna run test

export NPM_CONFIG_GLOBALCONFIG="$REPO_ROOT_DIR/n4js-libs"
echo "Publishing using .npmrc configuration to ${NPM_REGISTRY}";

# update repository meta-info in package.json of all n4js-libs to point to the commit ID of n4js-libs folder
# NOTE: we use our own property 'gitHeadN4jsLibs' instead of the official 'gitHead' property because:
# 1) yarn isn't updating 'gitHead' at all at the moment (see https://github.com/yarnpkg/yarn/issues/2978 ) and
# 2) behavior of lerna w.r.t. property 'gitHead' has recently changed and we want to avoid surprises in the future.
echo "==== STEP 5/8: Updating property 'gitHeadN4jsLibs' in package.json of all n4js-libs to new local commit ID ..."
lerna exec -- cp package.json package.json_TEMP
lerna exec -- 'jq -r ".gitHeadN4jsLibs |= \"'$N4JS_LIBS_COMMIT_ID_LOCAL'\"" package.json_TEMP > package.json'
lerna exec -- rm package.json_TEMP

echo "==== STEP 6/8: Appending version information to README.md files ..."
export VERSION_INFO="\n\n## Version\n\nVersion ${PUBLISH_VERSION} of \${LERNA_PACKAGE_NAME} was built from commit [${N4JS_LIBS_COMMIT_ID_LOCAL}](https://github.com/eclipse/n4js/tree/${N4JS_LIBS_COMMIT_ID_LOCAL}/n4js-libs/packages/\${LERNA_PACKAGE_NAME}).\n\n"
lerna exec -- 'printf "'${VERSION_INFO}'" >> README.md'

# enforce consistent repository meta-info in package.json of all n4js-libs
echo "==== STEP 7/8: Setting property 'repository' in package.json of all n4js-libs (for consistency) ..."
lerna exec -- cp package.json package.json_TEMP
lerna exec -- 'jq -r ".repository |= {type: \"git\", url: \"https://github.com/eclipse/n4js/tree/master/n4js-libs/packages/$LERNA_PACKAGE_NAME\"}" package.json_TEMP > package.json'
lerna exec -- rm package.json_TEMP

echo "==== STEP 8/8: Now publishing with version ${PUBLISH_VERSION} and dist-tag ${DIST_TAG}"
lerna publish --loglevel warn --skip-git --registry="${NPM_REGISTRY}" --repo-version="${PUBLISH_VERSION}" --exact --yes --npm-tag="${DIST_TAG}"

echo "==== PUBLISH N4JS-LIBS - DONE"
