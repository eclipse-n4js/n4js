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
echo "Terminate running but possibly outdated asciispec server..."
asciispec term

SRC_DIR="../org.eclipse.n4js.spec"
SPEC_DIR=web-html/spec

function copy_scripts () {
	echo "[INFO] Copying shared assets to web-html dir (scripts, images)"
	cp -r $SRC_DIR/$GEN_FOLDER/. $SPEC_DIR
	cp -r assets/scripts assets/styles assets/images web-html	
}

pushd $SRC_DIR
	# Specify & Clean output directory
	GEN_FOLDER=generated-docs
	rm -rf ./$GEN_FOLDER/; mkdir -p ./$GEN_FOLDER/
	cp -r images chapters ./$GEN_FOLDER/

	echo "[INFO] Building HTML Language Specification"
	asciispec -D $GEN_FOLDER N4JSSpec.adoc

	# running "./buildspec.sh -p" (preview) will skip PDF build and launch N4JSSpec.html
	if [ "${1}" == "--preview" ] || [ "${1}" == "-p" ]; then
		popd
		copy_scripts
		open $SRC_DIR/$GEN_FOLDER/N4JSSpec.html
		exit 0
	fi

	echo "[INFO] Building PDF via docbook toolchain"
	asciispec -b docbook -D $GEN_FOLDER N4JSSpec.adoc && fopub $GEN_FOLDER/N4JSSpec.xml && rm $GEN_FOLDER/N4JSSpec.xml
popd

if [ ! -d "$SPEC_DIR" ]; then
   mkdir -p ./$SPEC_DIR/
fi

copy_scripts

# Clean unwanted source files
pushd $SPEC_DIR/chapters
	rm -rf **/*.adoc **/*.graffle && find . -type d -empty -delete
popd

echo "[INFO] N4JS Language Specification built"
