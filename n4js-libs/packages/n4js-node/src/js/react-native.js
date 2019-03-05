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

    if (!global.$makeClass) {
        global.n4 = {
            runtimeOptions: global.n4 && global.n4.runtimeOptions || {},
            runtimeInfo: {
                deviceId: undefined,
                isTouch: true,
                platformId: "react-native",
                platformVariant: undefined
            }
        };

        require("./rt/node-url-polyfill.js");
        require("n4js-es5/src-gen/rt.js");
    }

}(typeof global === "object" ? global : self));
