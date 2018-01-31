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

    if (!global.$makeClass) {
        require("./rt/node-bootstrap.js").installN4JSRuntime();
    }
    var CJSLoader = require("./rt/node-cjs-loader-polyfill.js").Loader,
        staticSystem = new CJSLoader();

    exports.staticSystem = staticSystem;

    exports.System = function(req, mod) {
        // detect whether the module is being loaded via require(),
        // otherwise fall back to using SystemJS:
        return mod.loaded ? global.System : new CJSLoader(req, mod);
    };
})();
