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

# "http://localhost:4873"
NPM_REGISTRY=$1

echo "We are currently in $PWD"

# Login as a test user
# npm-cli-login -u testuser -p testpass -e test@pass.com -r $NPM_REGISTRY

# Publish using canary version
echo "Publishing to $NPM_REGISTRY"
lerna publish --loglevel silly --skip-git --registry="${NPM_REGISTRY}" --force-publish --exact --canary --yes --sort
