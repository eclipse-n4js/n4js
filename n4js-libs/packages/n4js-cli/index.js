/*
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
/*eslint-disable no-console */
"use strict";

const lib_path = require("path");
const lib_child_process = require("child_process");

/**
 * Calls n4jsc with the given set of arguments, e.g.
 *
 * "buildType": "allprojects"
 * "debug": true
 *
 * Any stdout/stderr will be logged as is.
 *
 * Call `n4jsc -h` for more details.
 *
 * Returns a promise.
 */
function n4jsc(options, files) {
    return new Promise(function(resolve, reject) {
        var args = ["-jar", lib_path.resolve(__dirname, "bin", "n4jsc.jar")];

        if (Array.isArray(options)) { // take as is
            args = args.concat(options);
        } else {
            Object.keys(options).forEach(function(k) {
                var o = options[k];
                if (o != null && o !== false) {
                    args.push((k.length > 1 ? "--" : "-") + k);
                    if (typeof o !== "boolean") {
                        args.push(o);
                    }
                }
            });
            if (files) {
                args = args.concat(files);
            }
        }

        if (options.debug) {
            console.log("spawn:", "java " + args.join(" "));
        }

        lib_child_process.spawn("java", args, {
            stdio: "inherit",
            env: Object.assign({ NODEJS_PATH: process.argv[0] }, process.env)
        }).on("close", function(code) {
            if (code === 0) {
                resolve(0);
            } else {
                reject(code);
            }
        });
    });
}

module.exports = {
    n4jsc: n4jsc
};
