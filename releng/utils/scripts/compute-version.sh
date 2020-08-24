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

# option -i of sed is incompatible between GNU and BSD unix, hence we provide
# our own function for in-place modification of files:
function sed_in_place() {
    echo "Replacing '$1' by '$2' in $3"
    sed -e "s/$1/$2/g" "$3" > "$3.new"
    mv -f "$3.new" "$3"
}


echo "==== COMPUTE VERSION (compute-version.sh)"

export NPM_TOKEN=dummy
TIMESTAMP_DATE=`date "+%Y%m%d"`
TIMESTAMP_TIME=`date "+%H%M"`

# Set working directory to root folder of repository
# (we assume this scripts is located in folder /releng/utils/scripts)
cd `dirname $0`
cd ../../..
REPO_ROOT_DIR=`pwd -P`

# Navigate to n4js-libs folder
cd n4js-libs

echo "Repository root directory: ${REPO_ROOT_DIR}"
echo "Current working directory: $PWD"

echo "==== STEP 1/7: clean up (clean yarn cache, etc.)"
yarn cache clean
rm -rf $(find . -type d -name "node_modules")
# Since we include the commit ID in the published artifacts, we should
# make sure to not publish any dirty state in the working copy.
# Thus, we reset the working copy here:
git checkout HEAD -- .
# obtain commit IDs of root folder and folder 'n4js-libs' in the local git working copy:
N4JS_COMMIT_ID_LOCAL=`git log -1 --format="%H"`
N4JS_LIBS_COMMIT_ID_LOCAL=`git log -1 --format="%H" -- .`

echo "==== STEP 2/7: install dependencies (to have tool 'semver' available)"
yarn install
export PATH="${REPO_ROOT_DIR}/n4js-libs/node_modules/.bin:${PATH}"

echo "==== STEP 3/7: Checking whether publication of n4js-libs is required (using ${N4JS_LIBS_REPRESENTATIVE} as representative) ..."

# Obtain the latest commit on npm registry, using the representative
N4JS_LIBS_VERSION_PUBLIC=`curl -s ${NPM_REGISTRY}/${N4JS_LIBS_REPRESENTATIVE} | jq -r '.["dist-tags"].latest'`
if [ \( "$N4JS_LIBS_VERSION_PUBLIC" = "null" \) -o \( "$N4JS_LIBS_VERSION_PUBLIC" = "" \) ]; then
    echo "Unable to retrieve latest published version of ${N4JS_LIBS_REPRESENTATIVE} on npm registry ${NPM_REGISTRY}"
    exit -1
fi
echo "  - version of latest published n4js-libs       : $N4JS_LIBS_VERSION_PUBLIC"

N4JS_LIBS_COMMIT_ID_PUBLIC=`curl -s ${NPM_REGISTRY}/${N4JS_LIBS_REPRESENTATIVE} | jq -r '.versions."'${N4JS_LIBS_VERSION_PUBLIC}'".gitHeadN4jsLibs'`
if [ \( "$N4JS_LIBS_COMMIT_ID_PUBLIC" = "null" \) -o \( "$N4JS_LIBS_COMMIT_ID_PUBLIC" = "" \) ]; then
    N4JS_LIBS_COMMIT_ID_PUBLIC="unknown"
fi
echo "  - commit ID of latest published n4js-libs     : $N4JS_LIBS_COMMIT_ID_PUBLIC"
echo "  - commit ID of local n4js-libs                : $N4JS_LIBS_COMMIT_ID_LOCAL"
if [ "${N4JS_LIBS_COMMIT_ID_LOCAL}" = "${N4JS_LIBS_COMMIT_ID_PUBLIC}" ]; then
    echo "-> commit IDs equal"
    if [ "${FORCE_N4JS_LIBS_PUBLISHING}" = "true" ]; then
        echo "-> publishing of a new version of n4js-libs not required but *FORCED*, because environment variable FORCE_N4JS_LIBS_PUBLISHING was set to 'true'"
        N4JS_LIBS_PUBLISHING_REQUIRED="true"
    else
        echo "-> publishing of a new version of n4js-libs *NOT* required"
        N4JS_LIBS_PUBLISHING_REQUIRED="false"
    fi
else
    echo "-> commit IDs differ (i.e. changes since last publication)"
    echo "-> publishing of a new version of n4js-libs is required"
    N4JS_LIBS_PUBLISHING_REQUIRED="true"
fi

echo "==== STEP 4/7: Compute n4js-libs version number for this build ..."
VERSION_MAJOR_REQUESTED=`jq -r '.major' ${REPO_ROOT_DIR}/version.json`
VERSION_MINOR_REQUESTED=`jq -r '.minor' ${REPO_ROOT_DIR}/version.json`
VERSION_DIST_TAG_REQUESTED=`jq -r '.tag' ${REPO_ROOT_DIR}/version.json`
echo "Requested major segment       : ${VERSION_MAJOR_REQUESTED} (from file version.json)"
echo "Requested minor segment       : ${VERSION_MINOR_REQUESTED} (from file version.json)"
echo "Requested dist tag            : ${VERSION_DIST_TAG_REQUESTED} (from file version.json)"
echo "Latest published version      : ${N4JS_LIBS_VERSION_PUBLIC}"
if [ "${N4JS_LIBS_PUBLISHING_REQUIRED}" = "true" ]; then
    echo "-> this build will publish a new version of n4js-libs"
    echo "-> this build's n4js-libs version is a new, incremented version ..."
    MAJOR_MINOR_PATTERN="([0-9]+)\\.([0-9]+)\\..*"
    if [[ "${N4JS_LIBS_VERSION_PUBLIC}" =~ ${MAJOR_MINOR_PATTERN} ]]; then
        VERSION_MAJOR_PUBLIC="${BASH_REMATCH[1]}"
        VERSION_MINOR_PUBLIC="${BASH_REMATCH[2]}"
    else
        echo "ERROR: cannot extract major/minor segments from latest published version!"
        exit -1
    fi
    if [ "$VERSION_MAJOR_REQUESTED" -gt "$VERSION_MAJOR_PUBLIC" ]; then
        echo "New major version segment requested in file version.json -> will bump major segment"
        INCREMENTED_VERSION="$VERSION_MAJOR_REQUESTED.$VERSION_MINOR_REQUESTED.0"
    elif [ "$VERSION_MINOR_REQUESTED" -gt "$VERSION_MINOR_PUBLIC" ]; then
        echo "New minor version segment requested in file version.json -> will bump minor segment"
        # for major segment we use the latest public version as template to make sure
        # we do not end up with a lower version than the latest public version
        INCREMENTED_VERSION="$VERSION_MAJOR_PUBLIC.$VERSION_MINOR_REQUESTED.0"
    elif [ "$VERSION_MAJOR_REQUESTED" -eq "$VERSION_MAJOR_PUBLIC" ] && [ "$VERSION_MINOR_REQUESTED" -eq "$VERSION_MINOR_PUBLIC" ]; then
        echo "No new major/minor version segment requested in file version.json -> will bump patch segment"
        INCREMENTED_VERSION=`semver -i patch ${N4JS_LIBS_VERSION_PUBLIC}`
    else
        echo "ERROR: requested major/minor segment must not be lower than latest published major/minor segment!"
        exit -1
    fi
    N4JS_LIBS_BASE_VERSION="${INCREMENTED_VERSION}"
else
    echo "-> this build will NOT publish a new version of n4js-libs"
    echo "-> this build's n4js-libs version is the version of the latest n4js-libs on npmjs.org: ${N4JS_LIBS_VERSION_PUBLIC}"
    N4JS_LIBS_BASE_VERSION="${N4JS_LIBS_VERSION_PUBLIC}"
fi
N4JS_LIBS_VERSION="${N4JS_LIBS_BASE_VERSION}"
N4JS_LIBS_DIST_TAG="latest"
if [ \( "$VERSION_DIST_TAG_REQUESTED" != "null" \) -a \( "$VERSION_DIST_TAG_REQUESTED" != "" \) ]; then
    echo "Npm dist-tag requested -> appending a generated pre-release segment to n4js-libs version"
    N4JS_LIBS_VERSION="${N4JS_LIBS_BASE_VERSION}-${VERSION_DIST_TAG_REQUESTED}.${TIMESTAMP_DATE}.${TIMESTAMP_TIME}"
    N4JS_LIBS_DIST_TAG="${VERSION_DIST_TAG_REQUESTED}"
fi
echo "This build's n4js-libs version: ${N4JS_LIBS_VERSION}"

echo "==== STEP 5/7: Computing language version (derived from n4js-libs version) ..."
if [ "$N4JS_LIBS_DIST_TAG" != "latest" ]; then
    LANGUAGE_VERSION="${N4JS_LIBS_BASE_VERSION}.${N4JS_LIBS_DIST_TAG}_v${TIMESTAMP_DATE}-${TIMESTAMP_TIME}"
else
    LANGUAGE_VERSION="${N4JS_LIBS_BASE_VERSION}.v${TIMESTAMP_DATE}-${TIMESTAMP_TIME}"
fi
echo "This build's language version: ${LANGUAGE_VERSION}"

echo "==== STEP 6/7: Check validity ..."
HISTORY_OF_MASTER=`git log -n 50 --pretty=format:\"%H\" origin/master | grep $N4JS_COMMIT_ID_LOCAL || true`
if [ "$HISTORY_OF_MASTER" == "" ]; then
    # we are not on master (i.e. the HEAD commit N4JS_COMMIT_ID_LOCAL is not in the history of origin/master)
    if [ "$N4JS_LIBS_DIST_TAG" == "latest" ]; then
        echo "ERROR: not allowed to publish with dist-tag 'latest' when not on master (i.e. when HEAD is not in the history of origin/master)"
        exit -1
    fi
fi
echo "Ok."

echo "==== STEP 7/7: Writing version information to output files ..."

VERSION_INFO_FILE="${REPO_ROOT_DIR}/version-info.json"
echo "Writing entire version information to file ${VERSION_INFO_FILE}"
cat > ${VERSION_INFO_FILE} <<EOF
{
    "baseVersion": "${N4JS_LIBS_BASE_VERSION}",
    "languageVersion": "${LANGUAGE_VERSION}",
    "n4jsLibsVersion": "${N4JS_LIBS_VERSION}",
    "n4jsLibsDistTag": "${N4JS_LIBS_DIST_TAG}",
    "n4jsLibsPublishingRequired": ${N4JS_LIBS_PUBLISHING_REQUIRED}
}
EOF
cat ${VERSION_INFO_FILE}

LANGUAGE_VERSION_PROPERTIES_FILE="${REPO_ROOT_DIR}/plugins/org.eclipse.n4js/res/language-version.properties"
echo "Writing language version ${LANGUAGE_VERSION} to file ${LANGUAGE_VERSION_PROPERTIES_FILE}"
rm -f ${LANGUAGE_VERSION_PROPERTIES_FILE}
echo "language.version = ${LANGUAGE_VERSION}" > ${LANGUAGE_VERSION_PROPERTIES_FILE}

echo "==== COMPUTE VERSION - DONE"
