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

function echo_exec {
    echo "$@"
    $@
}

echo "We are in $PWD"

if [ "${N4_N4JSC_JAR}" == "" ]; then
	echo "N4_N4JSC_JAR environment variable is not set. Can't know where to obtain n4jsc.jar"	
	exit 1;
fi

echo_exec mkdir -p bin

if [[ ! ${N4_N4JSC_JAR} =~ ^https?:// ]]; then
    echo_exec cp ${N4_N4JSC_JAR} ./bin/n4jsc.jar
elif [ "`which wget`" != "" ]; then
    echo_exec wget --no-check-certificate ${N4_N4JSC_JAR} -O ./bin/n4jsc.jar
else
    echo_exec curl --insecure --location ${N4_N4JSC_JAR} -o ./bin/n4jsc.jar
fi
echo "End running npm-build"