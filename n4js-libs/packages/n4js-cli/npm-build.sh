#!/bin/bash
#
# Copyright (c) 2016 NumberFour AG.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#   NumberFour AG - Initial API and implementation
#
echo "Start running npm-build"
set -e
cd `dirname $0`

CUR_DIR=`pwd`

function echo_exec {
    echo "$@"
    $@
}

echo "We are in $PWD"

echo "Copy freshly built tools/org.eclipse.n4js.hlc/target/n4jsc.jar to ./bin folder"
echo_exec cp ../../../tools/org.eclipse.n4js.hlc/target/n4jsc.jar ./bin/n4jsc.jar

echo "End running npm-build"
