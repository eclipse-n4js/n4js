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


rm -f index-bundled.js
rm -f index-bundled_TEMP.js

ln -s src-gen/index.js index-bundled.js

./node_modules/.bin/esbuild src-gen/index.js --bundle --outfile=index-bundled_TEMP.js --format=cjs --platform=node

rm index-bundled.js
mv index-bundled_TEMP.js index-bundled.js

echo "Successfully bundled code in folder 'src-gen' into file 'index-bundled.js'."


rm -f N4Injector-bundled_TEMP.js
./node_modules/.bin/esbuild src-gen/n4js/lang/N4Injector.js --bundle --outfile=N4Injector-bundled_TEMP.js --external:n4js-runtime --format=cjs --platform=node
rm src-gen/n4js/lang/N4Injector.js
rm -f src-gen/n4js/lang/N4Injector.map
mv N4Injector-bundled_TEMP.js src-gen/n4js/lang/N4Injector.js

echo "Successfully bundled 'src-gen/n4js/lang/N4Injector.js'."