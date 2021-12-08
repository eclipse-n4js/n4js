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
	exit 1
else
	export DESTINATION=$1
	if [ "$DESTINATION" = "public" ]; then
		export NPM_REGISTRY="https://registry.npmjs.org"
	elif [ "$DESTINATION" = "staging" ]; then
		export NPM_REGISTRY="http://n4ide1-nexus.service.cd-dev.consul/repository/npm-internal"
	elif [ "$DESTINATION" = "local" ]; then
		export NPM_REGISTRY="http://localhost:4873"
	else
		echo "Invalid destination: $DESTINATION (must be 'local', 'staging', or 'public')"
		exit 1
	fi
fi

# The second parameter is the version.
if [ -z "$2" ]; then
	echo "The version must be specified as the second parameter."
	exit 1
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
# (we assume this script is located in folder /releng/utils/scripts)
cd `dirname $0`
cd ../../..
REPO_ROOT_DIR=`pwd -P`

# Navigate to n4js-libs folder
cd n4js-libs

echo "Repository root directory: ${REPO_ROOT_DIR}"
echo "Current working directory: $PWD"


echo "==== STEP 1/5: check preconditions"
# check that everything was prepared (n4jsc.jar available; 'yarn install' in n4js-libs; building of n4js-libs)
if [ ! -f "../target/n4jsc.jar" ]; then
	echo "ERROR: n4jsc.jar must have been built and copied to folder 'target' before running this script!"
	exit 1
fi
if [ ! -f "./node_modules/.bin/n4jsc" ]; then
	echo "ERROR: 'yarn install' must have been executed before running this script!"
	exit 1
fi
if [ ! -f "./packages/n4js-runtime/.n4js.projectstate" ]; then
	echo "ERROR: n4js-libs must have been compiled before running this script!"
	exit 1
fi
if [ ! -f "./packages/n4js-cli/bin/n4jsc.jar" ]; then
	echo "ERROR: n4jsc.jar missing from n4js-cli/bin!"
	exit 1
fi
if ! cmp -s "../target/n4jsc.jar" "./packages/n4js-cli/bin/n4jsc.jar"; then
	echo "ERROR: 'n4js-cli/bin/n4jsc.jar' is different from 'target/n4jsc.jar'!"
	exit 1
fi
# check NPM_TOKEN
if [ "$DESTINATION" != "local" ]; then
	if [ -z "$NPM_TOKEN" ]; then
		echo "ERROR: publishing to 'public' or 'staging' requires the environment variable NPM_TOKEN to be set but it has not been set!"
		exit 1
	fi
	echo "Environment variable NPM_TOKEN is set."
fi
# check consistency of dist-tag and pre-release segment (does not apply when publishing to 'local')
if [ "$DESTINATION" != "local" ]; then
	if [ "$DIST_TAG" = "latest" ]; then
		if [[ "$PUBLISH_VERSION" == *"-"* ]]; then
			echo "ERROR: when publishing to dist-tag 'latest', the version must not include a pre-release segment!"
			exit 1
		fi
	else
		if [[ "$PUBLISH_VERSION" != *"-$DIST_TAG"* ]]; then
			echo "ERROR: when publishing to dist-tag other than 'latest', the version must include a pre-release segment starting with the dist-tag!"
			exit 1
		fi
	fi
	echo "Dist-tag and pre-release segment are consistent."
fi


echo "==== STEP 2/5: prepare .npmrc and environment variables"
# prepare .npmrc for credentials
if [ "$DESTINATION" != "local" ]; then
	# we made sure above that NPM_TOKEN is set
	git checkout HEAD -- './.npmrc'
	echo '//registry.npmjs.org/:_authToken=${NPM_TOKEN}' >> .npmrc
	echo '//localhost:4873/:_authToken=${NPM_TOKEN}' >> .npmrc
	echo '//localhost:4874/:_authToken=${NPM_TOKEN}' >> .npmrc
fi
export NPM_CONFIG_GLOBALCONFIG="$REPO_ROOT_DIR/n4js-libs"
echo "Publishing using .npmrc configuration to ${NPM_REGISTRY}";

export PATH=`pwd`/node_modules/.bin:${PATH}
export N4_N4JSC_JAR="${REPO_ROOT_DIR}/target/n4jsc.jar"


# update repository meta-info in package.json of all n4js-libs to point to the commit ID of n4js-libs folder
# and to enforce consistent repository meta-info in package.json
# NOTE: we use our own property 'gitHeadN4jsLibs' instead of the official 'gitHead' property because:
# 1) yarn isn't updating 'gitHead' at all at the moment (see https://github.com/yarnpkg/yarn/issues/2978 ) and
# 2) behavior of lerna w.r.t. property 'gitHead' has recently changed and we want to avoid surprises in the future.
echo "==== STEP 3/5: Updating property 'gitHeadN4jsLibs' in package.json of all n4js-libs to new local commit ID ..."
# obtain commit ID of 'n4js' repository (used only for informational purposes):
N4JS_COMMIT_ID_LOCAL=`git log -1 --format="%H"`
# obtain commit ID of folder 'n4js-libs' in the local git working copy:
N4JS_LIBS_COMMIT_ID_LOCAL=`git log -1 --format="%H" -- .`

SCRIPT="\"
	this.gitHeadN4jsLibs = \\\"${N4JS_LIBS_COMMIT_ID_LOCAL}\\\";
	this.repository = {type: \\\"git\\\", url: \\\"https://github.com/eclipse/n4js/tree/master/n4js-libs/packages/\${LERNA_PACKAGE_NAME}\\\"};
\""
lerna exec -- json -I -f package.json -e $SCRIPT


echo "==== STEP 4/5: Appending version information to README.md files ..."
# reset README.md files to avoid appending version info multiple times in case script is run more than once
find ./packages -name "README.md" -exec git checkout HEAD -- {} \;
export VERSION_INFO="\n\n## Version\n\nVersion ${PUBLISH_VERSION} of \${LERNA_PACKAGE_NAME} was built from commit [${N4JS_LIBS_COMMIT_ID_LOCAL}](https://github.com/eclipse/n4js/tree/${N4JS_LIBS_COMMIT_ID_LOCAL}/n4js-libs/packages/\${LERNA_PACKAGE_NAME}).\n\nCompiled with an N4JS compiler built from commit [${N4JS_COMMIT_ID_LOCAL}](https://github.com/eclipse/n4js/tree/${N4JS_COMMIT_ID_LOCAL}).\n\n"
lerna exec -- 'printf "'${VERSION_INFO}'" >> README.md'


echo "==== STEP 5/5: Now publishing with version '${PUBLISH_VERSION}' and dist-tag '${DIST_TAG}' to registry ${NPM_REGISTRY}"
echo "lerna publish --loglevel warn --no-git-tag-version --no-push --registry=${NPM_REGISTRY} --exact --yes --dist-tag=${DIST_TAG} ${PUBLISH_VERSION}"
if [ "$DESTINATION" = "local" ]; then
	lerna publish --loglevel warn --no-git-tag-version --no-push --registry="${NPM_REGISTRY}" --exact --yes --dist-tag="${DIST_TAG}" "${PUBLISH_VERSION}"
else
	echo DISABLED
fi

echo "==== PUBLISH N4JS-LIBS - DONE"
