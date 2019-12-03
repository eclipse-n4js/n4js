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
    if (/^https?:\/\//i.test(jarPath)) {
        const stream = lib_fs.createWriteStream(outPath);
        const download = (url) => {
            require(/^http:\/\//i.test(url) ? "http" : "https").get(url, resp => {
                if (Math.trunc(resp.statusCode /100) === 3 && resp.headers.location) { // redirect
                    download(resp.headers.location);
                } else {
                    if (resp.statusCode !== 200) {
                        throw new Error(`Request on ${url} failed: ${resp.statusCode}`);
                    }
                    resp.pipe(stream);
                }
            });
        };
        download(jarPath);
    } else {
        lib_fs.copyFileSync(jarPath, outPath);
    }
}
