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
	echo ""
    echo "Run mangelhaft for ${FILE}:"
	../../bin/n4js-mangelhaft-cli.js \
	    --testCatalog ${FILE} \
	    --xunitReportFile ./build/report.xml \
	    --xunitReportName test-report \
	    --xunitReportPackage TestPrj \
	    $@
done
