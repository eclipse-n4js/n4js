#!/bin/bash

echo "Start publishing n4js-libs"

# Navigate to n4js-libs folder
cd `dirname $0`/n4js-libs

rm -rf node_modules

# Install dependencies in order to prepare npm-task script
yarn install

# Build n4js-cli
yarn run build

# Run npm task script 'publish-canary'
yarn run publish-canary

echo "End publishing n4js-libs"
