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

INPUT_FILE=$1

mv $INPUT_FILE unnotarized_$INPUT_FILE

RESPONSE=\
$(curl -X POST \
  -F "file=@unnotarized_$INPUT_FILE" \
  -F 'options={"primaryBundleId": "app-bundle", "staple": true};type=application/json' \
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

curl -J -o "$INPUT_FILE" http://172.30.206.146:8383/macos-notarization-service/$UUID/download
