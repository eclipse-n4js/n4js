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
                var i = v.indexOf("=");
                options[i < 0 ? v : v.substring(0, i)] = i < 0 ? true : v.substring(i + 1);
            });
        }

        var timerHandle;

        return new Promise(function(resolve, reject) {
            options = require("./rt/node-bootstrap.js").installN4JSRuntime(options);

            if (options["keep-eventloop"]) {
                // dummy interval to avoid termination on open main/exec promise:
                timerHandle = setInterval(function() {}, Number.MAX_SAFE_INTEGER);
            }

            n4.handleMainModule().then(function(res) {
                if (options.debug) {
                    console.log("## node.js exec done.");
                }
                resolve(res);
            }, reject);
        }).then(function(res) {
            clearInterval(timerHandle);
            return res;
        }, function(err) {
            console.error((err && typeof err === "object") && err.stack || err);
            clearInterval(timerHandle);
            if (exitOnError) {
                process.stdout.write("", process.exit.bind(process, 1));
            }
            throw err;
        });
    };
})();
