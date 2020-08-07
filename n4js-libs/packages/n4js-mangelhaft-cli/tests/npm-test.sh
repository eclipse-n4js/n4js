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

echo "Create test catalogs"
../../../n4js-cli/bin/n4jsc.js compile ../../../..

find ../../.. -name "testcatalog.json" -type f | while read -r FILE; do
	PROJECT_PATH=$(cd $(dirname ${FILE}); pwd)
	echo ""
    echo "Run mangelhaft for ${PROJECT_PATH}:"
	PROJECT_NAME=$(basename ${PROJECT_PATH})
	REPORT_NAME="./build/report_${PROJECT_NAME}.xml"
	
	# run mangelhaft
	../../bin/n4js-mangelhaft-cli.js \
	    --testCatalog ${FILE} \
	    --xunitReportFile ${REPORT_NAME} \
	    --xunitReportName test-report \
	    --xunitReportPackage ${PROJECT_NAME} \
	    $@

    echo "Saved test report at: ${REPORT_NAME}"
done
