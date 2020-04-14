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
set -eo pipefail
# use the following for excessive logging:
#set -x



BASE_DIR=$(pwd $(dirname "$0"))
pushd ${BASE_DIR}
N4JS_DIR=$(cd ../../.. && pwd)
popd
TARGET_DIR=${N4JS_DIR}/target
EXTENSION_DIR=${N4JS_DIR}/lsp-clients/n4js-vscode-extension
VERDACCIO_CONFIG_DIR=${N4JS_DIR}/releng/org.eclipse.n4js.libs.build/verdaccioConfig
VERDACCIO_IMAGE=verdaccio/verdaccio@sha256:e64056b6aa104197dbac07e77a1fbcf8f5c67d9b443e0ed2a191d41a1da7a944



# PARAMETER 1: version.
if [ -z "$1" ]; then
    echo "The version must be specified as the first parameter."
    exit -1
else
    VERSION=$1
fi


# PARAMETER 2: (Optional) npm dist-tag
if [ -z "$2" ]; then
    DIST_TAG="latest"
else
    DIST_TAG=$2
fi



# PRECONDITION: port is free
echo "Check port 4873"
set +e
lsof -n -i :4873
RESULT=$?
set -e
if [ $RESULT -eq 0 ]; then
  echo "ERROR: Port 4873 is occupied"
  
  echo "Kill running docker"
  docker rm -f n4js-test-verdaccio || true
  #exit -1
else
  echo "Port 4873 is free"
fi



echo "==== STEP ?/?: Start Verdaccio"
echo ${VERDACCIO_CONFIG_DIR}
docker run -d -it --rm --name n4js-test-verdaccio -p 4873:4873 -v ${VERDACCIO_CONFIG_DIR}/config.yaml:/verdaccio/conf/config.yaml ${VERDACCIO_IMAGE}

echo "Wait 1s"
sleep 1s
echo "Wait 2s"
sleep 1s
echo "Wait 3s"
sleep 1s



echo "==== STEP ?/?: Check Verdaccio"
RESULT=$(curl -f http://localhost:4873 | grep title)

echo $RESULT
if [[ $RESULT == *"Verdaccio"* ]]; then
  echo "Verdaccio successfully started"
else
  echo "ERROR: Verdaccio could not be started"
  exit -1
fi



echo "==== STEP ?/?: Publish libs to local verdaccio registry"
./publish-n4js-libs.sh local ${VERSION}



pushd ${EXTENSION_DIR}
	echo "==== STEP ?/?: Update versions inside package.json of extension"
	npx json -I -f package.json -e 'this.version="${VERSION}"'
	npx json -I -f package.json -e 'this.dependencies["n4js-cli"]="${VERSION}"'
	npx json -I -f package.json -e 'this.dependencies["n4js-runtime"]="${VERSION}"'
	npx json -I -f package.json -e 'this.dependencies["n4js-runtime-node"]="${VERSION}"'

	echo "==== STEP ?/?: Call npm install (use local verdaccio registry)"
	npm install --registry http://localhost:4873
	
	
	echo "==== STEP ?/?: Pack extension"
	vsce package
	
	
	echo "==== STEP ?/?: Copy extension"
	cp n4js-vscode-extension-${VERSION}.vsix ${TARGET_DIR}/
popd


echo "==== STEP ?/?: Killing Verdaccio"
docker rm -f n4js-test-verdaccio || true


echo "==== PUBLISH N4JS-LIBS - DONE"
