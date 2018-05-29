#!/bin/bash

set -e

echo "Start publishing n4js-libs and n4js-cli"

# Navigate to n4js-libs folder
cd `dirname $0`/n4js-libs

rm -rf node_modules

# Install dependencies and prepare npm task scripts
echo "=== Install dependencies and prepare npm task scripts"
npm install

# Build n4js-cli lib
echo "=== Run lerna run build to build n4js-cli"
lerna run build

# Run npm task script 'publish-canary'
yarn run publish-canary

echo "End publishing n4js-libs and n4js-cli"
