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


# Set working directory to root folder of repository
# (we assume this script is located in folder /releng/utils/scripts)
cd `dirname $0`
cd ../../..
REPO_ROOT_DIR=`pwd -P`
VERDACCIO_CONFIG_FILE="${REPO_ROOT_DIR}/releng/org.eclipse.n4js.libs.build/verdaccioConfig/config.yaml"

# Navigate to n4js-libs folder
cd n4js-libs


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
    echo "ERROR: 'n4js-libs/packages/n4js-cli/bin/n4jsc.jar' is different from 'target/n4jsc.jar'!"
    exit 1
fi


# start verdaccio
docker rm -f n4js-test-verdaccio || true
docker run -d -it --rm \
	--name n4js-test-verdaccio \
	-p 4873:4873 \
	-v "${VERDACCIO_CONFIG_FILE}:/verdaccio/conf/config.yaml" \
	"verdaccio/verdaccio@sha256:818b76e40631cf888c288d1cfe9579a6e9e816f2e1e5f87fe1471eb3f36dc168" # verdaccio/verdaccio:5.1.2


# populate verdaccio: publish n4js-libs into verdaccio after starting it
# (use dist-tag "latest" to make sure tests will install this version by default)
./releng/utils/scripts/publish-n4js-libs.sh local 0.0.1 latest
