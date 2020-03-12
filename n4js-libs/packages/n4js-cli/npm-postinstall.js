/*
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

"use strict";

let path = require('path');
let parentDir = path.dirname(process.cwd()).split(path.sep).pop();

if (parentDir == "node_modules") {
	// npm-postinstall actions require populated src-gen folders. Note that:
	// - If this package was actually installed into a node_modules folder, we know that src-gen folder is populated.
	// - If this package was checked out as part of the yarn workspace and 'yarn install' was run, the src-gen folder is still empty.
    require("./src-gen/npm-postinstall.js");
} else {
    const log = require("npmlog");
    const NPM_NAME = `${process.env.npm_package_name}@${process.env.npm_package_version}`;
    log.info(NPM_NAME, "skipping npm-postinstall for n4js-cli since it is not located inside a node_modules folder and src-gen files might be missing");
}
