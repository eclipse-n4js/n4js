/*
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
/*eslint-disable no-process-exit */

(function() {
    "use strict";

    exports.runWith = function(options, exitOnError) {
        if (options.ideExecData) { // additionally mixin all command line args as options
            process.argv.slice(2).forEach(function(v) {
                const i = v.indexOf("=");
                options[i < 0 ? v : v.substring(0, i)] = i < 0 ? true : v.substring(i + 1);
            });
        }

        let timerHandle;

        new Promise(function(resolve, reject) {
            options = require("./rt/node-bootstrap.js").installN4JSRuntime(options);

            require = require("esm")(module);
            global._nodeRequire = require;

            // TODO: fragile, will be removed once we have proper api/impl binding
            if (options["strip-api-prj-suffix"]) {
                const Module = require("module");
                const _resolveFilename = Module._resolveFilename;
                const stripApiSuffix_re = /^([^/]+)\.api\//;
                Module._resolveFilename = function(request, parent, isMain) {
                    request = request.replace(stripApiSuffix_re, "$1/");
                    return _resolveFilename(request, parent, isMain);
                };
            }

            const { handleMainModule } = require("./rt/handle-main.js");
            
            if (options["keep-eventloop"]) {
                // dummy interval to avoid termination on open main/exec promise:
                timerHandle = setInterval(function() {}, 0x7fffffff /* max 32bit signed int */);
            }

            handleMainModule().then(function() {
                if (options.debug) {
                    console.log("## node.js exec done.");
                }
                resolve();
            }, reject);
        }).then(function() {
            clearInterval(timerHandle);
        }, function(err) {
            if (err && (typeof err === "string" || typeof err === "object")) {
                // just log errors in case rejection with a string or an Error: 
                console.error(err.stack || err);
            }
            clearInterval(timerHandle);
            if (exitOnError) {
                const exitCode = typeof err === "number" ? err|0 : null;
                // Flush stdout and exit:
                process.stdout.write("", process.exit.bind(process, exitCode || 1));
            }
        });
    };
})();
