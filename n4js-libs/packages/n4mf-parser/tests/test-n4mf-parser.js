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

describe("N4MF manifest parser", function() {
    var lib_path = require("path"),
        assert = require("assert"),
        cli = require("../index.js");

    describe("#readManifest()", function() {
        var parsed;
        it("should parse the test manifest", function() {
            return cli.readManifest(lib_path.join(__dirname, "data", "test-manifest.n4mf")).then(function(res) {
                parsed = res;
            });
        });
        it("should match the test fixture", function() {
            assert.deepStrictEqual(parsed, require("./data/test-fixture.js"), "should match fixture");
        });
    });

    describe("#readManifestSync()", function() {
        var parsed;
        it("should parse the test manifest", function() {
            parsed = cli.readManifestSync(lib_path.join(__dirname, "data", "test-manifest.n4mf"));
        });
        it("should match the test fixture", function() {
            assert.deepStrictEqual(parsed, require("./data/test-fixture.js"), "should match fixture");
        });
    });
});
