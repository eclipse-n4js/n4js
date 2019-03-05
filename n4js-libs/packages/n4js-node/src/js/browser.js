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
        var isBrowser = !!(typeof window !== 'undefined' && typeof navigator !== 'undefined' && window.document),
            isWebWorker = !isBrowser && typeof importScripts !== 'undefined';

        global.n4 = {
            runtimeOptions: global.n4 && global.n4.runtimeOptions || {},
            runtimeInfo: {
                deviceId: undefined,
                isTouch: undefined,
                platformId: (isBrowser || isWebWorker) ? "web" : undefined,
                platformVariant: isWebWorker ? "webworker" : undefined
            }
        };
    }

    require("n4js-es5/src-gen/rt.js");

}(typeof global === "object" ? global : self));
