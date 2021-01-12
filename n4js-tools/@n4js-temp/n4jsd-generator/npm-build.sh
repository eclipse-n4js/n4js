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


cd `dirname $0`

echo "Compiling TypeScript code ..."
./node_modules/.bin/tsc

echo "Compiling N4JS code ..."
./node_modules/.bin/n4jsc compile .
