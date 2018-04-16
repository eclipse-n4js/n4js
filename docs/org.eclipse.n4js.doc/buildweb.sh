#!/bin/bash

# Copyright (c) 2017 NumberFour AG.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#   NumberFour AG - Initial API and implementation
#

# Location of source asciidoctor files
ADOC_DIR="src"

# Where to generate the website HTML
GEN_FOLDER="web-html"

if [ ! -d "$GEN_FOLDER" ]; then
	mkdir -p $GEN_FOLDER
fi

cp -r ../org.eclipse.n4js.spec/scripts ../org.eclipse.n4js.spec/styles ../org.eclipse.n4js.spec/images $ADOC_DIR/. ./web-html

pushd $GEN_FOLDER
	FILES=`find .  -name "*.adoc" -print`
popd

for f in $FILES;
do
	echo processing $f
	ADOC_FILE=$GEN_FOLDER/$f
	HEADER_DIR=../../assets/headers/$(basename $(dirname $f))
	ATTRS="-a doctype=book -a experimental=true -a sectlinks -a docinfo1=true -a linkcss=true -a !source-highlighter -a reproducible -a icons=font -a !webfonts -a !stylesheet"

	if [ "${1}" == "--docker" ]; then
		echo "Asciidoctor Docker container converting $f in-place"
		docker run --rm -v $(pwd):/documents asciidoctor/docker-asciidoctor asciidoctor $ATTRS -a docinfodir=$HEADER_DIR $ADOC_FILE
	else 
		echo "Asciidoctor gem converting $f in-place"
		asciidoctor $ATTRS -a docinfodir=$HEADER_DIR $ADOC_FILE
	fi
done

# Delete unwanted copied .adoc and .graffle files.
pushd ./$GEN_FOLDER/
	find . -name "*.adoc" -delete && find . -name "*.graffle" -delete
popd
