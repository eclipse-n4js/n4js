/*
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

describe("n4jsc", function() {
    const cli = require("../index.js");

    describe("#call", function() {
        it("calling it should work", function() {
            this.timeout(30000);
            return cli.n4jsc({
                help: true,
                debug: true
            });
        });
    });
});
