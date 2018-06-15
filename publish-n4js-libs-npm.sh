#!/bin/bash

set -e

echo "Start publishing n4js-libs and n4js-cli"

function echo_exec {
    echo "$@"
    $@
}

# The first parameter is can be local, staging or public
if [ -z "$1" ]; then
	echo "The destination (local, staging or public) must be specified as the first parameter"
	exit 1
else
	export DESTINATION=$1
fi

# The second parameter is the url to npm registry (http://localhost:4873), if not exists default to npmjs
if [ -z "$2" ]; then
	echo "The url to npm registry must be specified"
	exit
else
	export NPM_REGISTRY=$2
fi

# Navigate to n4js-libs folder
cd `dirname $0`/n4js-libs

rm -rf node_modules

# If publishing to local or staging, use the dist-tag 'test' otherwise 'latest'
export NPM_TAG=test

if [ "$DESTINATION" = "public" ]; then
	if [ -z $NPM_TOKEN]; then
		echo "Publishing to public requires the environment variable NPM_TOKEN to be set but it has not been set!"
		exit 0;
	fi
	export NPM_TAG=latest
else
	# Dummy token
	export NPM_TOKEN=dummy	
fi;

# Install dependencies and prepare npm task scripts
echo "=== Install dependencies and prepare npm task scripts"
yarn install

# Build n4js-cli lib
echo "=== Run lerna run build/test on n4js libs"
lerna run build
lerna run test

# Run npm task script 'publish-canary' to publish n4js-libs and n4js-cli to NPM_REGISTRY
yarn run publish-canary $DESTINATION $NPM_REGISTRY


echo "End publishing n4js-libs and n4js-cli"
