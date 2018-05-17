#!/bin/bash

set -e

echo "Stop verdaccio"

kill $(pgrep verdaccio)
