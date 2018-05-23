#!/bin/bash
#
# Copyright (c) 2017 NumberFour AG.
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

function echo_exec {
    echo "$@"
    $@
}

SRC_BASE=./test/n4js
OUT_BASE=./test/js-gen/babel
rm -rf ${OUT_BASE} ; mkdir -p ${OUT_BASE}

echo_exec ./node_modules/.bin/babel \
    --source-root ${SRC_BASE} \
    --quiet \
    --no-babelrc \
    --plugins transform-es2015-modules-commonjs \
    --out-dir ${OUT_BASE} \
    --extensions .n4js \
    --only **/run.n4js \
    ${SRC_BASE}
