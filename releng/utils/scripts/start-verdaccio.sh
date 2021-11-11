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
