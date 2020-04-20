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



SCRIPT_DIR_REL=$(dirname "$0")
pushd ${SCRIPT_DIR_REL}
SCRIPT_DIR=$(pwd)
N4JS_DIR=$(cd ../../.. && pwd)
popd
TARGET_DIR=${N4JS_DIR}/target
EXTENSION_DIR=${N4JS_DIR}/lsp-clients/n4js-vscode-extension
VERDACCIO_CONFIG_DIR=${N4JS_DIR}/releng/org.eclipse.n4js.libs.build/verdaccioConfig
VERDACCIO_IMAGE=verdaccio/verdaccio@sha256:e64056b6aa104197dbac07e77a1fbcf8f5c67d9b443e0ed2a191d41a1da7a944



# PARAMETER 1: package name.
if [ -z "$1" ]; then
    echo "The package name must be specified as the first parameter."
    exit -1
else
    PACKAGE_NAME=$1
fi

# PARAMETER 2: version.
if [ -z "$2" ]; then
    echo "The version must be specified as the first parameter."
    exit -1
else
    VERSION=$2
fi




# PRECONDITION: version not published already (GH-1723)
set +e
RESULT=$(npm view n4js-runtime@${VERSION} | grep n4js-runtime)
set -e

if [[ $RESULT == *"n4js-runtime"* ]]; then
    echo "Version ${VERSION} already published. Skipping build of N4JS VSCode extension."
    exit 0

else
    echo "Version ${VERSION} not yet in use. Continue build of N4JS VSCode extension."
fi


# PRECONDITION: port is free
echo "Check port 4873"
set +e
lsof -n -i :4873
RESULT=$?
set -e
if [ $RESULT -eq 0 ]; then
    echo "ERROR: Port 4873 is occupied"

    echo "Kill running docker with: 'docker rm -f extension-publish-verdaccio || true'"
    #docker rm -f extension-publish-verdaccio || true
    exit -1
else
    echo "Port 4873 is free"
fi



echo "==== STEP 1/8: Start Verdaccio"
docker run -d -it --rm --name extension-publish-verdaccio -p 4873:4873 -v ${VERDACCIO_CONFIG_DIR}/config.yaml:/verdaccio/conf/config.yaml ${VERDACCIO_IMAGE}

echo "Wait 1s"
sleep 1s
echo "Wait 2s"
sleep 1s
echo "Wait 3s"
sleep 1s



echo "==== STEP 2/8: Check Verdaccio"
RESULT=$(curl -f http://localhost:4873 | grep title)

#echo $RESULT
if [[ $RESULT == *"Verdaccio"* ]]; then
    echo "Verdaccio successfully started"
else
    echo "ERROR: Verdaccio could not be started"
    exit -1
fi



echo "==== STEP 3/8: Publish libs to local verdaccio registry: calling script 'publish-n4js-libs.sh'"
echo "==="
echo "=="
echo "="
echo "=="
echo "==="
${SCRIPT_DIR}/publish-n4js-libs.sh local ${VERSION}
echo "==="
echo "=="
echo "="
echo "=="
echo "==="



echo "==== STEP 4/8: Update versions inside package.json of extension"
pushd ${EXTENSION_DIR}
    npx json -I -f package.json -e "
        this.version=\"$VERSION\"
        this.dependencies['n4js-cli']=\"$VERSION\"
        this.dependencies['n4js-runtime']=\"$VERSION\"
        this.dependencies['n4js-runtime-node']=\"$VERSION\"
        this.dependencies['n4js-runtime-es2015']=\"$VERSION\"
    "

    echo "==== STEP 5/8: Call npm install (use local verdaccio registry)"
    npm install --registry http://localhost:4873


    echo "==== STEP 6/8: Pack extension"
    npx vsce package --out ${PACKAGE_NAME}


    echo "==== STEP 7/8: Copy extension"
    cp ${PACKAGE_NAME} ${TARGET_DIR}/
popd


echo "==== STEP 8/8: Killing Verdaccio"
docker rm -f extension-publish-verdaccio || true


echo "==== PUBLISH N4JS-LIBS - DONE"
