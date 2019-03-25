#!/usr/bin/env node

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
/*eslint-disable no-empty */

(function() {
    "use strict";

    function tryJSON(json) {
        if (typeof json === "string") {
            try {
                json = JSON.parse(json);
            } catch (exc) {} // TODO IDE should not set JSON.stringified data
        }
        return json;
    }

    var lib_run = require("n4js-node/src-gen/run.js");

    var execData = global.$executionData;
    if (execData) {
        delete global.$executionData;
        lib_run.runWith({
            "ideExecData": execData,
            "test-catalog": tryJSON(execData.testTree),
            "loader-support-api-not-implemented": true,
            "keep-eventloop": true,
            "main": "org.eclipse.n4js.mangelhaft.runner.ide/src-gen/org/eclipse/n4js/mangelhaft/runner/ide/IDENodeTestRunner"
        }, true /* exitOnError */);
    } else {
        console.warn("No $executionData given.");
    }
})();
