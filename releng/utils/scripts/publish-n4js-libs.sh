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


echo "==== PUBLISH n4js-libs (including n4js-cli)"

# Set working directory to root folder of repository
# (we assume this scripts is located in folder /releng/utils/scripts)
cd `dirname $0`
cd ../../..
REPO_ROOT_DIR=`pwd -P`

# Navigate to n4js-libs folder
cd n4js-libs
N4JS_LIBS_ROOT=`pwd -P`

echo "We are currently in $PWD"

# The first parameter can be local, staging or public
if [ -z "$1" ]; then
	echo "The destination (local, staging or public) must be specified as the first parameter"
	exit 1
else
	export DESTINATION=$1
fi

# The second parameter is the url to npm registry (http://localhost:4873), if not exists default to npmjs
if [ -z "$2" ]; then
	echo "The url to npm registry must be specified as the second parameter"
	exit
else
	export NPM_REGISTRY=$2
fi

# If publishing to local or staging, use the dist-tag 'test' otherwise 'latest'
export NPM_TAG=test

if [ "$DESTINATION" = "public" ]; then
	if [ -z $NPM_TOKEN ]; then
		echo "Publishing to public requires the environment variable NPM_TOKEN to be set but it has not been set!"
		exit 0;
	fi
	export NPM_TAG=latest
else
	# Dummy token
	export NPM_TOKEN=dummy	
fi;

echo "==== STEP 1/9: clean up (clean yarn cache, etc.)"
yarn cache clean
rm -rf $(find . -type d -name "node_modules")

echo "==== STEP 2/9: install dependencies and prepare npm task scripts"
yarn install

echo "==== STEP 3/9: run lerna run build/test on n4js-libs"
export PATH=`pwd`/node_modules/.bin:${PATH}
export N4_N4JSC_JAR="${REPO_ROOT_DIR}/tools/org.eclipse.n4js.hlc/target/n4jsc.jar"
lerna run build
lerna run test

export NPM_CONFIG_GLOBALCONFIG="$N4JS_LIBS_ROOT"
echo "Publishing using .npmrc configuration to ${NPM_REGISTRY}";

if [ "$DESTINATION" = "public" ]; then
    echo "==== STEP 4/9: Checking whether publication of n4js-libs is required (using ${N4JS_LIBS_REPRESENTATIVE} as representative) ..."
    echo "!!! SKIPPED !!! (will always publish)"

    N4JS_LIBS_COMMIT_ID_LOCAL=`git log -1 --format="%H" ${N4JS_LIBS_ROOT}`
    echo "  - commit ID of local n4js-libs                : $N4JS_LIBS_COMMIT_ID_LOCAL"

    # update repository meta-info in package.json of all n4js-libs to point to the GitHub and the correct commit
    # (yarn isn't doing this at the moment: https://github.com/yarnpkg/yarn/issues/2978 )
    echo "==== STEP 5/9: Updating property 'gitHead' in package.json of all n4js-libs to new local commit ID ..."
    lerna exec -- cp package.json package.json_TEMP
    lerna exec -- 'jq -r ".gitHead |= \"'$N4JS_LIBS_COMMIT_ID_LOCAL'\"" package.json_TEMP > package.json'
    lerna exec -- rm package.json_TEMP

    echo "==== STEP 6/9: Compute new version number for publishing ..."
    echo "!!! SKIPPED !!! (using version hard-coded in shell script)"
    export PUBLISH_VERSION="0.14.0"
    echo "Version for publishing: ${PUBLISH_VERSION}"

    echo "==== STEP 7/9: Appending version information to README.md files ..."
    export VERSION_INFO="\n\n## Version\n\nVersion ${PUBLISH_VERSION} of \${LERNA_PACKAGE_NAME} was built from commit [${N4JS_LIBS_COMMIT_ID_LOCAL}](https://github.com/eclipse/n4js/tree/${N4JS_LIBS_COMMIT_ID_LOCAL}/n4js-libs/packages/\${LERNA_PACKAGE_NAME}).\n\n"
    lerna exec -- 'printf "'${VERSION_INFO}'" >> README.md'
else
    # Use a version that we are sure can not exist on public npm registry for testing
    echo "==== Skipping steps 4-7 (because publishing only for testing purposes)"
    PUBLISH_VERSION="9999.0.0"
fi

# enforce consistent repository meta-info in package.json of all n4js-libs
echo "==== STEP 8/9: Setting property 'repository' in package.json of all n4js-libs (for consistency) ..."
lerna exec -- cp package.json package.json_TEMP
lerna exec -- 'jq -r ".repository |= {type: \"git\", url: \"https://github.com/eclipse/n4js/tree/master/n4js-libs/packages/$LERNA_PACKAGE_NAME\"}" package.json_TEMP > package.json'
lerna exec -- rm package.json_TEMP

echo "==== STEP 9/9: Now publishing with version: ${PUBLISH_VERSION} and dist-tag ${NPM_TAG}"
lerna publish --loglevel info --skip-git --registry="${NPM_REGISTRY}" --repo-version="${PUBLISH_VERSION}" --exact --yes --bail --npm-tag="${NPM_TAG}"

echo "==== DONE publishing n4js-libs (including n4js-cli)"
