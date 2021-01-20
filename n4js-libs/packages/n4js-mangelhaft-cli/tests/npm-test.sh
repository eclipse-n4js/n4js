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
cd `dirname $0`/TestPrj

echo "which env"
echo $(which env)

echo "which node"
echo $(which node)

echo "Create test catalogs"
../../../n4js-cli/src-gen/n4jsc.js compile ../../../..


REPORT_NAME="./build/report.xml"
echo "Run Mangelhaft"
../../bin/n4js-mangelhaft-cli.js ../../../.. \
		--xunitReportFile $REPORT_NAME \
		--xunitReportName test-report \
		--xunitReportPackage n4js-libs-report \
		$@

echo "Saved test report at: ${REPORT_NAME}"

