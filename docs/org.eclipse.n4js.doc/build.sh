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



# Parameters:
# $1 = first path
# $2 = second path
#
# Relpath function: returns the relative path between 1st and 2nd paths
relpath() {
    local pos="${1%%/}" ref="${2%%/}" down=''

    while :; do
        test "$pos" = '/' && break
        case "$ref" in $pos/*) break;; esac
        down="../$down"
        pos=${pos%/*}
    done

    echo "$down${ref##$pos/}"
}

GEN_FOLDER=generated-docs

echo copying resources to ./$GEN_FOLDER/

rm -rf ./$GEN_FOLDER/; mkdir ./$GEN_FOLDER/ 
cp -r ./res/scripts ./res/styles ./src/articles ./src/faq ./src/features ./src/images ./src/releases  ./src/userguides ./$GEN_FOLDER/
cp src/index.html src/downloads.html src/community.html ./$GEN_FOLDER

pushd src
	FILES=`find .  -name "*.adoc" ! -name 'config.adoc' -print`
popd

# Rundoc function to convert source files to HTML. Attributes are passed with the '-a' flag.
for f in $FILES; 
do 

	ASPEC=asciidoctor
	ADOC_FILE=src/$f
	REL_PATH="${f//\.\//}"
	REL_PATH="${REL_PATH//[^\/]/}"
	REL_PATH="${REL_PATH//[\/]/../}"
	HEADER_DIR=$(basename $(dirname $f))
	OUT_FOLDER=./$GEN_FOLDER/$(dirname $f)
	ATTRS="-a doctype=book -a experimental=true -a sectlinks -a docinfo1=true -a linkcss=true -a !source-highlighter -a reproducible -a icons=font"

	echo running $ASPEC on $ADOC_FILE to $OUT_FOLDER

	mkdir -p $OUT_FOLDER

	$ASPEC $ATTRS -a stylesdir=${REL_PATH}../res/styles  -a highlightjsdir=${REL_PATH}/scripts \
	-a docinfodir=${REL_PATH}../res/headers/$HEADER_DIR -D $OUT_FOLDER $ADOC_FILE
done

# Delete unwanted source files.
pushd ./$GEN_FOLDER/
	find . -name "*.adoc" -delete && find . -name "*.graffle" -delete
popd

# If Jenkins is building, move generated docs
if [ "${1}" == "--jenkins" ]; then
	cp -r generated-docs ../
	exit 0
# Add -p flag to launch pages after build
elif [ "${1}" == "--preview" ] || [ "${1}" == "-p" ]; then
	open ./$GEN_FOLDER/index.html
	exit 0
fi
