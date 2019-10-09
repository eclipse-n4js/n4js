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


echo "==== PUBLISH N4JS-LIBS (including n4js-cli)"

# Set working directory to root folder of repository
# (we assume this scripts is located in folder /releng/utils/scripts)
cd `dirname $0`
cd ../../..
REPO_ROOT_DIR=`pwd -P`

# Navigate to n4js-libs folder
cd n4js-libs
N4JS_LIBS_ROOT=`pwd -P`

echo "Repository root directory: ${REPO_ROOT_DIR}"
echo "Current working directory: $PWD"

# The first parameter can be 'local' or 'public'
if [ -z "$1" ]; then
    echo "The destination (local or public) must be specified as the first parameter."
    exit -1
else
    export DESTINATION=$1
fi

# The second parameter is the version.
if [ -z "$1" ]; then
    echo "The version must be specified as the second parameter."
    exit -1
else
    export PUBLISH_VERSION=$2
fi

# The optional third parameter is the URL to the npm registry (e.g. http://localhost:4873)
if [ -z "$3" ]; then
    if [ "$DESTINATION" = "public" ]; then
        export NPM_REGISTRY="http://registry.npmjs.org"
    else
        export NPM_REGISTRY="http://localhost:4873"
    fi
else
    export NPM_REGISTRY=$3
fi

if [ "$DESTINATION" = "public" ]; then
    if [ -z "$NPM_TOKEN" ]; then
        echo "Publishing to public requires the environment variable NPM_TOKEN to be set but it has not been set!"
        exit -1
    fi
else
    # If publishing to local, use the dist-tag 'test' otherwise 'latest'.
    export NPM_TOKEN=dummy
fi

echo "==== STEP 1/8: clean up (clean yarn cache, etc.)"
yarn cache clean
rm -rf $(find . -type d -name "node_modules")
# Since we include the commit ID in the published artifacts, we should
# make sure to not publish any dirty state in the working copy.
# Thus, we reset the working copy here:
git checkout HEAD -- .
# obtain commit ID of folder 'n4js-libs' in the local git working copy:
N4JS_LIBS_COMMIT_ID_LOCAL=`git log -1 --format="%H" -- .`

echo "==== STEP 2/8: install dependencies and prepare npm task scripts"
yarn install

echo "==== STEP 3/8: run 'lerna run build/test' on n4js-libs"
export PATH=`pwd`/node_modules/.bin:${PATH}
export N4_N4JSC_JAR="${REPO_ROOT_DIR}/tools/org.eclipse.n4js.hlc/target/n4jsc.jar"
lerna run build
lerna run test

export NPM_CONFIG_GLOBALCONFIG="$N4JS_LIBS_ROOT"
echo "Publishing using .npmrc configuration to ${NPM_REGISTRY}";

if [ "$DESTINATION" = "public" ]; then
    DIST_TAG=latest

    echo "==== STEP 4/8: Choosing a dist-tag and pre-release version ..."
    VERSION_DIST_TAG_REQUESTED=`jq -r '.tag' version.json`
    echo "Requested dist tag              : ${VERSION_DIST_TAG_REQUESTED} (from file n4js-libs/version.json)"
    if [ \( "$VERSION_DIST_TAG_REQUESTED" != "null" \) -a \( "$VERSION_DIST_TAG_REQUESTED" != "" \) ]; then
        if [ "$VERSION_DIST_TAG_REQUESTED" != "${DIST_TAG}" ]; then
            echo "Npm dist-tag requested -> appending a generated pre-release segment to version"
            DIST_TAG="${VERSION_DIST_TAG_REQUESTED}"
            VERSION_DIST_TAG_TIMESTAMP=`date "+%Y%m%d.%H%M"`
            PUBLISH_VERSION="${PUBLISH_VERSION}-${VERSION_DIST_TAG_REQUESTED}.${VERSION_DIST_TAG_TIMESTAMP}"
        fi
    fi
    echo "Adjusted version for publishing : ${PUBLISH_VERSION}"

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
else
    echo "==== Skipping steps 4-6 (because publishing only for testing purposes)"
    DIST_TAG=test
fi

# enforce consistent repository meta-info in package.json of all n4js-libs
echo "==== STEP 7/8: Setting property 'repository' in package.json of all n4js-libs (for consistency) ..."
lerna exec -- cp package.json package.json_TEMP
lerna exec -- 'jq -r ".repository |= {type: \"git\", url: \"https://github.com/eclipse/n4js/tree/master/n4js-libs/packages/$LERNA_PACKAGE_NAME\"}" package.json_TEMP > package.json'
lerna exec -- rm package.json_TEMP

echo "==== STEP 8/8: Now publishing with version ${PUBLISH_VERSION} and dist-tag ${DIST_TAG}"
if [ "${DIST_TAG}" = "test" ]; then
    lerna publish --loglevel warn --skip-git --registry="${NPM_REGISTRY}" --repo-version="${PUBLISH_VERSION}" --exact --yes --npm-tag="${DIST_TAG}"
else
    echo "NOT PUBLISHING FROM BRANCH GH-1503"
    echo lerna publish --loglevel warn --skip-git --registry="${NPM_REGISTRY}" --repo-version="${PUBLISH_VERSION}" --exact --yes --npm-tag="${DIST_TAG}"
fi

echo "==== PUBLISH N4JS-LIBS - DONE"
