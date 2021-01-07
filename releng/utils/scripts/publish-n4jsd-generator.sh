#!/bin/bash
#
# Copyright (c) 2020 NumberFour AG.
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


NPM_REGISTRY="https://nexus3.internal.numberfour.eu/repository/npm-internal/"
BASE_VERSION="0.0.1"

# Choose a version for publishing
TIMESTAMP=`date "+%Y%m%d-%H%M"`
PUBLISH_VERSION="$BASE_VERSION-v$TIMESTAMP"


echo "==== PUBLISH N4JSD-GENERATOR"
echo "Registry : $NPM_REGISTRY"
echo "Version  : $PUBLISH_VERSION"

# Set working directory to folder of n4jsd-generator project
# (we assume this script is located in folder .../n4js-extended/releng/utils/scripts)
cd `dirname $0`
cd ../../../../n4js/n4js-tools/@n4js-temp/n4jsd-generator
echo "Current working directory: $PWD"

echo "==== STEP 1/6: check preconditions"
# check NPM_TOKEN
if [ -z "$NPM_TOKEN" ]; then
    echo "Publishing requires the environment variable NPM_TOKEN to be set but it has not been set!"
    exit -1
fi
echo "Environment variable NPM_TOKEN is set."

echo "==== STEP 2/6: clean up"
npm cache clean --force
rm -rf node_modules
rm -rf src-gen
mkdir src-gen
# prepare .npmrc for credentials
# (we made sure above that NPM_TOKEN is set)
echo '//nexus3.internal.numberfour.eu/repository/npm-internal/:_authToken=${NPM_TOKEN}' >> .npmrc

echo "==== STEP 3/6: install dependencies and prepare npm task scripts"
npm install
export PATH=${PATH}:`pwd`/node_modules/.bin

echo "==== STEP 4/6: build n4jsd-generator"
npm run build
ls -al
ls -al src-gen

echo "==== STEP 5/6: test n4jsd-generator"
npm test

echo "==== STEP 6/6: Now publishing with version '${PUBLISH_VERSION}' to registry ${NPM_REGISTRY}"
echo "Using .npmrc configuration at ${NPM_CONFIG_GLOBALCONFIG}"
export NPM_CONFIG_GLOBALCONFIG=`pwd -P`/.npmrc
npm version "${PUBLISH_VERSION}"
npm publish --registry $NPM_REGISTRY

echo "==== PUBLISH N4JSD-GENERATOR - DONE"
