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

../bin/n4js-mangelhaft-cli.js \
    --compile \
    --xunitReportFile ./build/report.xml \
    --xunitReportName test-report \
    --xunitReportPackage TestPrj \
    --scan . --scan ../..
