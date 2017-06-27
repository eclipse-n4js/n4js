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


# -e  == exit immediately
# -x  == enable debug. (+x for disable)
# -v  == Print shell input lines as they are read.
set -e +x -v

echo "Terminate running but possibly outdated asciispec server..."
asciispec term


# Which document to convert 
SPEC="N4JSSpec.adoc"

# Document attributes
ATTRS="-a stylesheet=foundation.min.css -a docinfodir=headers "

############## Build HTML #############

# if Jenkins is building, use the following params
if [ "${1}" == "--jenkins" ]; then

	# Specify & Clean output directory
	GEN_FOLDER="../generated-docs/spec"
	rm -rf $GEN_FOLDER; mkdir -p $GEN_FOLDER

	# Copy resources to ./$GEN_FOLDER/
	cp -r ./styles ./images ./scripts ./chapters $GEN_FOLDER/

	####### Build HTML Spec #######
	asciispec $ATTRS -D $GEN_FOLDER $SPEC
	
	####### Build PDF via docbook toolchain #######
	asciispec -b docbook -D $GEN_FOLDER $SPEC && fopub $GEN_FOLDER/N4JSSpec.xml 

	##### EXIT ##### 
	exit 0

# otherwise, use these params
else
	# Specify & Clean output directory
	GEN_FOLDER=generated-docs
	rm -rf ./$GEN_FOLDER/; mkdir -p ./$GEN_FOLDER/

	# Copy resources to ./$GEN_FOLDER/
	cp -r styles images scripts chapters ./$GEN_FOLDER/

	####### Build HTML Spec #######
	asciispec $ATTRS -D $GEN_FOLDER N4JSSpec.adoc 

	# running "./build.sh -p" (preview) will skip PDF build and launch N4JSSpec.html
	if [ "${1}" == "--preview" ] || [ "${1}" == "-p" ]; then
		open ./$GEN_FOLDER/N4JSSpec.html
		exit 0
	fi
fi

####### Build PDF via docbook toolchain #######
asciispec -b docbook -D $GEN_FOLDER N4JSSpec.adoc && fopub $GEN_FOLDER/N4JSSpec.xml 

# Clean unwanted adoc/graffle files and delete empty subdirectories
pushd ./$GEN_FOLDER/chapters
	rm -rf **/*.adoc && rm -rf **/**/*.graffle &&	find . -type d -empty -print
popd
