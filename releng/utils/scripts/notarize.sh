#!/bin/bash

# Performs Apple notarization of a signed file on Eclipse build infrastructure,
# using the corresponding web service.
#
# Adjusted from the script at:
# https://ci.eclipse.org/oomph/job/mac-sign/configure
#
# For more information see:
# https://bugs.eclipse.org/bugs/show_bug.cgi?id=550135#c38
# https://developer.apple.com/documentation/xcode/notarizing_macos_software_before_distribution

if [ -z "$2" ]; then
    echo 'Usage: notarize.sh <INPUT_FILE> <OUTPUT_FILE> [<STAPLE>]'
    echo ''
    echo 'Argument STAPLE tells whether "stapling" should be activated, i.e. whether the notarization ticket'
    echo 'should be attached to the output file (not supported for zip archives). Must be either "true" or'
    echo '"false"; default is "true".'
    exit -1
fi

INPUT_FILE=$1
OUTPUT_FILE=$2
if [ -z "$3" ]; then
    STAPLE='true'
else
    STAPLE=$3
fi

if [[ $STAPLE != 'true' && $STAPLE != 'false' ]]; then
    echo "Value for argument STAPLE should be 'true' or 'false', but was: $STAPLE"
    exit -1
fi


RESPONSE=\
$(curl -X POST \
  -F "file=@$INPUT_FILE" \
  -F 'options={"primaryBundleId": "app-bundle", "staple": '"$STAPLE"'};type=application/json' \
  http://172.30.206.146:8383/macos-notarization-service/notarize)

UUID=$(echo $RESPONSE | grep -Po '"uuid"\s*:\s*"\K[^"]+')

STATUS=$(echo $RESPONSE | grep -Po '"status"\s*:\s*"\K[^"]+')

while [[ $STATUS == 'IN_PROGRESS' ]]; do
    sleep 1m
    RESPONSE=$(curl -s http://172.30.206.146:8383/macos-notarization-service/$UUID/status)
    STATUS=$(echo $RESPONSE | grep -Po '"status"\s*:\s*"\K[^"]+')
done

if [[ $STATUS != 'COMPLETE' ]]; then
    echo "Upload failed: $RESPONSE"
    exit 1
fi

curl -J -o "$OUTPUT_FILE" http://172.30.206.146:8383/macos-notarization-service/$UUID/download
