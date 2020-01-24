/*
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

"use strict";


if (process.env.N4JSCLI_SKIP_POSTINSTALL && process.env.N4JSCLI_SKIP_POSTINSTALL == "true") {
    const log = require("npmlog");
    const NPM_NAME = `${process.env.npm_package_name}@${process.env.npm_package_version}`;
    log.info(NPM_NAME, "skipping npm-postinstall for n4js-cli");
} else {
    require("./src-gen/npm-postinstall.js");
}
