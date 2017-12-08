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
(function() {
    "use strict";

    var lib_path = require("path");
    var N4JS_RT_PREFIX = "N4JS_RT_";

    function installN4JSRuntime(options) {
        if (typeof $makeClass !== "undefined") {
            throw new Error("N4JS runtime is already set up.");
        }

        options = options || {};

        var env = process.env;
        var envOptions = Object.keys(env).reduce(function(memo, k) {
            if (k.startsWith(N4JS_RT_PREFIX)) {
                memo[k.substring(N4JS_RT_PREFIX.length).toLowerCase().replace(/_/g, "-")] = env[k];
            }
            return memo;
        }, {});

        options = Object.assign(envOptions, global.n4 && global.n4.runtimeOptions, options);
        if (options.debug) {
            console.log("## node.js exec.");
            console.log(process.title, "\n", process.versions, "\nargs:", process.argv);
            console.log("NODE_PATH=");
            if (process.env.NODE_PATH) {
                console.log(process.env.NODE_PATH.split(lib_path.delimiter).join("\n" + lib_path.delimiter));
            }
            console.log("## Runtime Options:", options);

            Error.stackTraceLimit = 10000;//Infinity;
        }

        process.on("unhandledRejection", function(reason, promise) {
            console.warn("unhandledRejection", (reason && typeof reason === "object") && reason.stack || reason, promise);
        });

        global.n4 = {
            runtimeOptions: options,
            runtimeInfo: {
                platformId: "nodejs",
                platformVariant: process.version,
                isTouch: false,
                deviceId: "pc"
            }
        };

        var fetch = global.fetch = require("node-fetch");
        ["Request", "Response", "Headers"].forEach(function(p) {
            global[p] = fetch[p];
        });
        require("./node-url-polyfill.js");

        if (!global.System || !global.System.import) {
            if (options.cjs) { // enforce commonJS
                var CJSLoader = require("./node-cjs-loader-polyfill.js").Loader;
                global.System = new CJSLoader(require, { exports: {} });
            } else { // install SystemJS
                require("systemjs");
            }
        }
        require("n4js-es5/rt");

        return options;
    }

    exports.installN4JSRuntime = installN4JSRuntime;
}());
