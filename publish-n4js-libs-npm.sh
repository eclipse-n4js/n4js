#!/bin/bash

set -e

echo "Start publishing n4js-libs and n4js-cli"

# The first parameter is the npm registry url
NPM_REGISTRY=$1

# Navigate to n4js-libs folder
cd `dirname $0`/n4js-libs

rm -rf node_modules

# Install dependencies and prepare npm task scripts
echo "=== Install dependencies and prepare npm task scripts"
npm install

# Build n4js-cli lib
echo "=== Run lerna run build to build n4js-cli"
lerna run build

# Run npm task script 'publish-canary' to publish n4js-libs and n4js-cli to local npm registry
yarn run publish-canary $NPM_REGISTRY

echo "End publishing n4js-libs and n4js-cli"
