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
set -eo pipefail
# use the following for excessive logging:
#set -x


# regular expression for extracting the commit ID from a GitHub URL of the form:
# https://github.com/eclipse/n4js/tree/89a8d09e9dcd924ae1ffeb03138fb9140e6e370d/n4js-libs/packages/n4js-node
EXTRACT_COMMIT_ID_REGEX=".*\/n4js\/tree\/([^\/]+)\/n4js-libs\/packages\/.*"

# this is the n4js-libs package that will be used to obtain the version number and commit ID
# of the latest published version of all n4js-libs packages
N4JS_LIBS_REPRESENTATIVE="n4js-runtime"

# the public npm registry (used to obtain the version number / commit ID of the latest published
# version of the n4js-libs packages)
NPM_REGISTRY="http://registry.npmjs.org"


echo "==== COMPUTE VERSION (compute-version.sh)"

# Set working directory to root folder of repository
# (we assume this scripts is located in folder /releng/utils/scripts)
cd `dirname $0`
cd ../../..
REPO_ROOT_DIR=`pwd -P`

# Navigate to n4js-libs folder
cd n4js-libs
N4JS_LIBS_ROOT=`pwd -P`

echo "Repository root directory: ${REPO_ROOT_DIR}"
echo "Current working directory: $PWD"

export NPM_TOKEN=dummy

echo "==== STEP 1/0: clean up (clean yarn cache, etc.)"
yarn cache clean
rm -rf $(find . -type d -name "node_modules")
# Since we include the commit ID in the published artifacts, we should
# make sure to not publish any dirty state in the working copy.
# Thus, we reset the working copy here:
git checkout HEAD -- .
# obtain commit ID of folder 'n4js-libs' in the local git working copy:
N4JS_LIBS_COMMIT_ID_LOCAL=`git log -1 --format="%H" -- .`

echo "==== STEP 2/0: Checking whether publication of n4js-libs is required (using ${N4JS_LIBS_REPRESENTATIVE} as representative) ..."

# Obtain the latest commit on npm registry, using the representative
N4JS_LIBS_VERSION_PUBLIC=`curl -s ${NPM_REGISTRY}/${N4JS_LIBS_REPRESENTATIVE} | jq -r '.["dist-tags"].latest'`
echo "  - version of latest published n4js-libs       : $N4JS_LIBS_VERSION_PUBLIC"

N4JS_LIBS_COMMIT_ID_PUBLIC=`curl -s ${NPM_REGISTRY}/${N4JS_LIBS_REPRESENTATIVE} | jq -r '.versions."'${N4JS_LIBS_VERSION_PUBLIC}'".gitHeadN4jsLibs'`
if [ "${N4JS_LIBS_COMMIT_ID_PUBLIC}" = "null" ]; then
    N4JS_LIBS_COMMIT_ID_PUBLIC="unknown"
fi
echo "  - commit ID of latest published n4js-libs     : $N4JS_LIBS_COMMIT_ID_PUBLIC"
echo "  - commit ID of local n4js-libs                : $N4JS_LIBS_COMMIT_ID_LOCAL"

if [ "${N4JS_LIBS_COMMIT_ID_LOCAL}" = "${N4JS_LIBS_COMMIT_ID_PUBLIC}" ]; then
    echo '-> will NOT publish a new version of n4js-libs (because commit IDs are identical, i.e. no changes since last publication)'
    echo "-> N4JS version is the version of the latest n4js-libs on npmjs.org: ${N4JS_LIBS_VERSION_PUBLIC}"
    N4JS_LIBS_VERSION="${N4JS_LIBS_VERSION_PUBLIC}"
    N4JS_LIBS_PUBLISHING_REQUIRED="false"
else
    echo "==== STEP 3/0: Compute new version number for publishing ..."
    VERSION_MAJOR_REQUESTED=`jq -r '.major' version.json`
    VERSION_MINOR_REQUESTED=`jq -r '.minor' version.json`
    VERSION_PRE_RELEASE_REQUESTED=`jq -r '."pre-release"' version.json`
    VERSION_DIST_TAG_REQUESTED=`jq -r '.tag' version.json`
    echo "Requested major segment       : ${VERSION_MAJOR_REQUESTED} (from file n4js-libs/version.json)"
    echo "Requested minor segment       : ${VERSION_MINOR_REQUESTED} (from file n4js-libs/version.json)"
    echo "Requested dist tag            : ${VERSION_DIST_TAG_REQUESTED} (from file n4js-libs/version.json)"
    echo "Latest published version      : ${N4JS_LIBS_VERSION_PUBLIC}"
    MAJOR_MINOR_PATTERN="([0-9]+)\\.([0-9]+)\\..*"
    if [[ "${N4JS_LIBS_VERSION_PUBLIC}" =~ ${MAJOR_MINOR_PATTERN} ]]; then
        VERSION_MAJOR_PUBLIC="${BASH_REMATCH[1]}"
        VERSION_MINOR_PUBLIC="${BASH_REMATCH[2]}"
    else
        echo "ERROR: cannot extract major/minor segments from latest published version!"
        exit -1
    fi
    if [ "$VERSION_MAJOR_REQUESTED" -gt "$VERSION_MAJOR_PUBLIC" ]; then
        echo "New major version segment requested in file n4js-libs/version.json -> will bump major segment"
        PUBLISH_VERSION="$VERSION_MAJOR_REQUESTED.$VERSION_MINOR_REQUESTED.0"
    elif [ "$VERSION_MINOR_REQUESTED" -gt "$VERSION_MINOR_PUBLIC" ]; then
        echo "New minor version segment requested in file n4js-libs/version.json -> will bump minor segment"
        # for major segment we use the latest public version as template to make sure
        # we do not end up with a lower version than the latest public version
        PUBLISH_VERSION="$VERSION_MAJOR_PUBLIC.$VERSION_MINOR_REQUESTED.0"
    elif [ "$VERSION_MAJOR_REQUESTED" -eq "$VERSION_MAJOR_PUBLIC" ] && [ "$VERSION_MINOR_REQUESTED" -eq "$VERSION_MINOR_PUBLIC" ]; then
        echo "No new major/minor version segment requested in file n4js-libs/version.json -> will bump patch segment"
        PUBLISH_VERSION=`semver -i patch ${N4JS_LIBS_VERSION_PUBLIC}`
    else
        echo "ERROR: requested major/minor segment must not be lower than latest published major/minor segment!"
        exit -1
    fi
    if [ \( "$VERSION_DIST_TAG_REQUESTED" != "null" \) -a \( "$VERSION_DIST_TAG_REQUESTED" != "" \) ]; then
        if [ "$VERSION_DIST_TAG_REQUESTED" != "${NPM_TAG}" ]; then
            echo "Npm dist-tag requested -> appending a generated pre-release segment to version"
            VERSION_DIST_TAG_TIMESTAMP=`date "+%Y%m%d.%H%M"`
            PUBLISH_VERSION="${PUBLISH_VERSION}-${VERSION_DIST_TAG_REQUESTED}.${VERSION_DIST_TAG_TIMESTAMP}"
            NPM_TAG="${VERSION_DIST_TAG_REQUESTED}"
        fi
    fi
    echo "Bumped version of n4js-libs for publishing : ${PUBLISH_VERSION}"
    N4JS_LIBS_VERSION="${PUBLISH_VERSION}"
    N4JS_LIBS_PUBLISHING_REQUIRED="true"
fi

echo "==== STEP 4/0: Computing N4JS version ..."
TIME_STAMP=`date "+%Y%m%d_%H%M"`
N4JS_VERSION="${N4JS_LIBS_VERSION}.${TIME_STAMP}"
echo "N4JS version: ${N4JS_VERSION}"

echo "==== STEP 5/0: Writing version to output files ..."

N4JS_VERSION_PROPERTIES_FILE="${REPO_ROOT_DIR}/plugins/org.eclipse.n4js.utils/n4js-version.properties"
echo "Writing N4JS version ${N4JS_VERSION} to file ${N4JS_VERSION_PROPERTIES_FILE} ..."
rm -f ${N4JS_VERSION_PROPERTIES_FILE}
echo "n4js.version = ${N4JS_VERSION}" > ${N4JS_VERSION_PROPERTIES_FILE}

N4JS_LIBS_PUBLISHING_REQUEST_FILE="${REPO_ROOT_DIR}/n4js-libs-publishing-request.json"
rm -f ${N4JS_LIBS_PUBLISHING_REQUEST_FILE}
if [ "${N4JS_LIBS_PUBLISHING_REQUIRED}" = "true" ]; then
    echo "Publishing of n4js-libs required -> writing n4js-libs version ${N4JS_LIBS_VERSION} to file ${N4JS_LIBS_PUBLISHING_REQUEST_FILE}"
    echo "${N4JS_LIBS_VERSION}" > ${N4JS_LIBS_PUBLISHING_REQUEST_FILE}
else
    echo "Publishing of n4js-libs not required -> NOT creating file ${N4JS_LIBS_PUBLISHING_REQUEST_FILE}"
fi
