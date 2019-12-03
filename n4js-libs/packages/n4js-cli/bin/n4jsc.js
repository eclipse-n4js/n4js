#!/usr/bin/env node

/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
"use strict";

const {resolve} = require("path");
const {spawn} = require("child_process");
const args = ["-jar", resolve(__dirname, "n4jsc.jar")].concat(process.argv.slice(2));

spawn("java", args, {
    stdio: "inherit",
    env: Object.assign({ NODEJS_PATH: process.argv[0] }, process.env)
}).on("close", function(code) {
    if (code !== 0) {
        process.exit(code);
    }
});
