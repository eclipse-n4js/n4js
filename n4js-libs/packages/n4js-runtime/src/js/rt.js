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
import "./rt/N4BuiltInClasses.js";
import "./rt/N4RuntimeBootstrap.js";

(function(global) {
    "use strict";

    // TODO: will be removed once the parser could properly support global `import`.
    // just for immediate execution without compile step
    global._n4jsImport = function(path) {
        return import(path);
    };

}(typeof global === "object" ? global : self));
