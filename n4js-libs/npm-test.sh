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
set -e
cd `dirname $0`


echo "========= Running tests defined in the individual packages of yarn workspace 'n4js-libs' ..."
./node_modules/.bin/lerna run test


echo "========= Running mangelhaft tests for all packages in yarn workspace 'n4js-libs' ..."
REPORT_NAME="./build/report.xml"
mkdir -p "./build"
echo "Run mangelhaft ..."
packages/n4js-mangelhaft-cli/src-gen/org/eclipse/n4js/mangelhaft/runner/node/NodeTestMain.js \
        ./packages/org.eclipse.n4js.mangelhaft.test/test-catalog.json \
        --xunitReportFile $REPORT_NAME \
        --sonarTestExecutionReportFile ./build/sqreport.xml \
        --xunitReportName test-report \
        --xunitReportPackage n4js-libs-report \
        --nycCoveragePath ./build/coverage.json \
        --nodeOptions ./node_modules/esm
echo "Done running mangehalft."
echo "Saved test report at: ${REPORT_NAME}"
