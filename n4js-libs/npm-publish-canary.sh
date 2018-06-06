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
set -e

cd `dirname $0`
cd `pwd -P`

echo "We are currently in $PWD"

# Login as a test user
# npm-cli-login -u testuser -p testpass -e test@pass.com -r $NPM_REGISTRY

# Publish using canary version
if [ -z $NPM_REGISTRY ]; then
	echo "Publishing using .npmrc configuration";
	lerna publish --loglevel silly --skip-git --exact --canary --yes --sort
else
	echo "Publishing to $NPM_REGISTRY";
	# We publish the npms to the test channel 
	lerna publish --force --loglevel silly --skip-git --registry="${NPM_REGISTRY}" --exact --canary --yes --sort --npm-tag=test
fi



