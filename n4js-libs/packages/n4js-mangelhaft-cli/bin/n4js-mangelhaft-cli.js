#!/usr/bin/env node

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
/*eslint-disable no-empty */

require("n4js-node/src-gen/run.js").runWith({
    "test-mode": true,
    "main": "n4js-mangelhaft-cli/src-gen/org/eclipse/n4js/mangelhaft/runner/node/NodeTestCLI"
}, true /* exitOnError */);
