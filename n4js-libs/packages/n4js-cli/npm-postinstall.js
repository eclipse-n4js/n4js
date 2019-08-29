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
/*eslint-disable no-console */
"use strict";

const lib_path = require("path");
const lib_fs = require("fs");
const lib_child_process = require("child_process");
const log = require("npmlog");
const env = process.env;
const jarPath = env.N4JSC_JAR;

if (jarPath) {
    const outPath = lib_path.resolve(__dirname, "bin", "n4jsc.jar");
    log.info(`${env.npm_package_name}@${env.npm_package_version}`, `Replacing ${outPath} with ${jarPath}`);

    if (lib_fs.existsSync(outPath)) {
        lib_fs.unlinkSync(outPath); // remove to notice any problems
    }

    // minimal dep just against wget
    if (/^https?:\/\//.test(jarPath)) {
        lib_child_process
            .spawn("wget", ["-O", outPath, jarPath], {
                stdio: "inherit",
                env: process.env
            })
            .on("exit", code => process.exit(code));
    } else {
        lib_fs.copyFileSync(jarPath, outPath);
    }
}
