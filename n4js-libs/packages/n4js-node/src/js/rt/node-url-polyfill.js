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
(function(global) {
    "use strict";

    //
    // For the time being we use this poor man's URL shim:
    //
    const lib_url = require("url");
    global.URL = function(urlStr) {
        const ret = lib_url.parse(urlStr);
        ret.hostname_ = ret.hostname;

        Object.defineProperties(ret, {
            hostname: {
                get: function() { return this.hostname_; },
                set: function(v) { this.hostname_ = v; this.host = null; }
            },
            username: {
                get: function() { return this.auth.split(":")[0]; },
                set: function(v) {
                    var auth = this.auth || "";
                    auth = v + ":" + (auth.split(":")[1] || "");
                    if (auth === ":") {
                        auth = "";
                    }
                    this.auth = auth;
                }
            },
            password: {
                get: function() { return this.auth.split(":")[1]; },
                set: function(v) {
                    var auth = this.auth || "";
                    auth = (auth.split(":")[0] || "") + ":" + v;
                    if (auth === ":") {
                        auth = "";
                    }
                    this.auth = auth;
                }
            },
            toString: {
                value: function() { return lib_url.format(this); }
            }
        });
        return ret;
    };

}(typeof global === "object" ? global : self));
