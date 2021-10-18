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


# The first parameter is the location of the source .d.ts files
if [ -z "$1" ]; then
	echo "The location of the source .d.ts files (usually folder /src/lib of the TypeScript repository) must be specified as the first parameter."
	exit 1
else
	SOURCE_DIR="$1"
fi

if [[ ! -d "${SOURCE_DIR}" ]]; then
	echo "Given location of the source .d.ts files does not exist:"
	echo "${SOURCE_DIR}"
	exit 1
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


rm -f "${REPO_ROOT_DIR}"/plugins/org.eclipse.n4js/src-env/env/builtin_js.n4jsd
rm -f "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-es2015/src/n4js/*.n4jsd
rm -f "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-esnext/src/n4js/*.n4jsd
#rm -f "${REPO_ROOT_DIR}"/n4js-libs/packages/n4js-runtime-html5/src/n4js/*.n4jsd

rm -rf "src-dts"
mkdir "src-dts"
cp "${SOURCE_DIR}/es5.d.ts" "src-dts"
cp "${SOURCE_DIR}"/es20*.d.ts "src-dts"
#cp "${SOURCE_DIR}"/dom*.d.ts "src-dts"
# delete some files we do not need/want to convert
# (they only contain triple slash directives, for the most part):
rm src-dts/*.full.d.ts
rm src-dts/es20??.d.ts
rm src-dts/es2021*.d.ts

rm -rf "out"
mkdir "out"
pushd "${GENERATOR_DIR}" > /dev/null
node -r esm "${GENERATOR_DIR}/bin/dts2n4jsd.js" --runtime-libs --copy-type-refs --no-doc --force --output "${BUILD_DIR}/out" "${BUILD_DIR}/src-dts"
popd > /dev/null

cd "out/@n4jsd/src-dts"
cp es5.n4jsd "${REPO_ROOT_DIR}/plugins/org.eclipse.n4js/src-env/env/builtin_js.n4jsd"
cp es2015*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-es2015/src/n4js"
cp es2016*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp es2017*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp es2018*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp es2019*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
cp es2020*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-esnext/src/n4js"
#cp dom*.n4jsd "${REPO_ROOT_DIR}/n4js-libs/packages/n4js-runtime-html5/src/n4js"
