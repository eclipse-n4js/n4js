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

var lib_path = require("path"),
    lib_fs = require("fs");
var n4mfParser = require("./src-gen/n4mf-parser");

/**
 * Parses the given manifest content.
 */
function parseManifest(content, path) {
    try {
        return n4mfParser.parse(content);
    } catch (exc) {
        if (exc.location) {
            var loc = exc.location.start;
            console.error("File:", path, "line:", loc.line, "column:", loc.column, "\n\n", exc.message);
        }
        throw exc;
    }
}

/**
 * Reads the given manifest path and parses its content.
 */
function readManifest(path) {
    return new Promise(function(resolve, reject) {
        path = lib_path.resolve(process.cwd(), path);
        lib_fs.readFile(path, "UTF-8", function(err, content) {
            if (err) {
                return reject(err);
            }
            try {
                resolve(parseManifest(content));
            } catch (exc) {
                reject(exc);
            }
        });
    });
}

/**
 * Synchronously reads the given manifest path and parses its content.
 */
function readManifestSync(path) {
    path = lib_path.resolve(process.cwd(), path);
    return parseManifest(lib_fs.readFileSync(path, "UTF-8"));
}

module.exports = {
    parseManifest: parseManifest,
    readManifest: readManifest,
    readManifestSync: readManifestSync
};
