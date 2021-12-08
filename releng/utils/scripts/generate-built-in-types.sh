#!/bin/bash
#
# Copyright (c) 2021 NumberFour AG.
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


# The first parameter is the commit ID of the .d.ts files to use as input (from the TypeScript repository)
if [ -z "$1" ]; then
	echo "Commit ID from TypeScript repository must be specified as the first parameter."
	exit 1
else
	TS_COMMIT_ID="$1"
fi


# Set working directory to root folder of repository
# (we assume this script is located in folder /releng/utils/scripts)
cd `dirname $0`
cd ../../..
REPO_ROOT_DIR=`pwd -P`


GENERATOR_DIR="${REPO_ROOT_DIR}/n4js-tools/n4jsd-generator"
if [[ ! -d "${GENERATOR_DIR}/node_modules" || ! -f "${GENERATOR_DIR}/src-gen/generator.js" ]]; then
	echo "ERROR: the n4jsd-generator must be built before running this script!"
	exit 1
fi


BUILD_DIR="${REPO_ROOT_DIR}/target/built-in-types"
mkdir -p "${BUILD_DIR}"
cd "${BUILD_DIR}"


# STEP #1: CLEAN UP

# temporary work-around: remove this when deleting deprecated file Iterator.n4jsd in 'n4js-runtime-es2015'
mv "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-es2015/src/n4js/Iterator.n4jsd "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-es2015/src/n4js/Iterator.n4jsd_

rm -f "${REPO_ROOT_DIR}"/plugins/org.eclipse.n4js/src-env/env/builtin_js.n4jsd
rm -f "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-es2015/src/n4js/*.n4jsd
rm -f "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-esnext/src/n4js/*.n4jsd
rm -f "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-html5/src/n4js/*.n4jsd

# temporary work-around: remove this when deleting deprecated file Iterator.n4jsd in 'n4js-runtime-es2015'
mv "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-es2015/src/n4js/Iterator.n4jsd_ "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-es2015/src/n4js/Iterator.n4jsd


# STEP #2: DOWNLOAD .d.ts FILES FROM TYPESCRIPT REPO (if not available already)

TS_REPO_FOLDER="typescript-repo-${TS_COMMIT_ID}"
if [[ ! -d "${TS_REPO_FOLDER}" ]]; then
	echo "Downloading TypeScript repository contents for commit ${TS_COMMIT_ID} ..."
	mkdir "${TS_REPO_FOLDER}"
	pushd "${TS_REPO_FOLDER}"
	# unfortunately this downloads the entire repository (without history) even though we only need a few files
	curl -L "https://api.github.com/repos/microsoft/TypeScript/zipball/${TS_COMMIT_ID}" -o "typescript-repo.zip"
	unzip "typescript-repo.zip" "*/src/lib/*"
	rm "typescript-repo.zip"
	mv microsoft-TypeScript-*/src .
	rmdir microsoft-TypeScript-*
	popd
else
	echo "Using existing TypeScript repository contents at: ${BUILD_DIR}/${TS_REPO_FOLDER}"
fi

SOURCE_DIR="${BUILD_DIR}/${TS_REPO_FOLDER}/src/lib"
if [[ ! -d "${SOURCE_DIR}" ]]; then
	echo "ERROR: TypeScript repository download folder does not contain src/lib subfolder: ${SOURCE_DIR}"
	exit 1
fi


# STEP #3: GENERATE .n4jsd FILES FROM .d.ts FILES

rm -rf "src-dts"
mkdir "src-dts"
cp "${SOURCE_DIR}/es5.d.ts" "src-dts"
cp "${SOURCE_DIR}"/es20*.d.ts "src-dts"
cp "${SOURCE_DIR}"/dom*.d.ts "src-dts"

# delete some files we do not need/want to convert
# (they only contain triple slash directives, for the most part):
rm src-dts/*.full.d.ts
rm src-dts/es20??.d.ts
# delete some files we do not want to support yet (they are "too new")
rm src-dts/es2021*.d.ts

rm -rf "out"
mkdir "out"
pushd "${GENERATOR_DIR}" > /dev/null
echo "Generating .n4jsd files ..."
node -r esm "${GENERATOR_DIR}/bin/dts2n4jsd.js" --runtime-libs --copy-type-refs --no-doc --force --output "${BUILD_DIR}/out" "${BUILD_DIR}/src-dts"
popd > /dev/null

sed -i bak "s/<<COMMIT_ID>>/${TS_COMMIT_ID}/g" "${BUILD_DIR}"/out/\@n4jsd/src-dts/*.n4jsd


# STEP #4: DEPLOY .n4jsd FILES

cd "out/@n4jsd/src-dts"
cp es5.n4jsd "${REPO_ROOT_DIR}/plugins/org.eclipse.n4js/src-env/env/builtin_js.n4jsd"
cp es2015*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-es2015/src/n4js"
cp es2016*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp es2017*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp es2018*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp es2019*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp es2020*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp dom*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-html5/src/n4js"
