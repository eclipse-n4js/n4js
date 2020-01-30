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

// The following two modules cannot be imported using ES6 imports.
// Hence this wrapper loads them using require and passes them to the extensionProvider.

const vscode = require("vscode");
const vscodeLC = require("vscode-languageclient");

require = require("esm")(module);

const extensionProvider = require("./src-gen/extensionProvider.js");

module.exports = {
    activate: extensionProvider.get(vscode, vscodeLC)
}
