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

if (parentDir == "node_nodules") {
    require("./src-gen/npm-postinstall.js");
} else {
    const log = require("npmlog");
    const NPM_NAME = `${process.env.npm_package_name}@${process.env.npm_package_version}`;
    log.info(NPM_NAME, "skipping npm-postinstall for n4js-cli since it is not located inside a node_modules folder and src-gen files might missing");
}
