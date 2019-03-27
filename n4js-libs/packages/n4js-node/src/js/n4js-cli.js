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
(function() {
    "use strict";

    const execData = global.$executionData;
    if (execData) {
        delete global.$executionData;
        require("./run.js").runWith({
            "ideExecData": execData,
            "exec": (typeof execData === "string" ? execData : execData.userSelection)
        }, true /* exitOnError */);
    } else if (process.argv.length > 2) { // first argument is starter module
        const exec = process.argv.splice(2, 1)[0];
        const options = {};
        // When using a defined export symbol `module:export`, it is used as the main entry:
        options[exec.indexOf(":") >= 0 ? "main" : "exec"] = exec;
        require("./run.js").runWith(options, true /* exitOnError */);
    } else {
        process.stdout.write("\nUsage: n4js <module>|<module>:<export>\n\n");
    }
})();
