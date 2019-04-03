/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester.nodejs

import java.nio.file.Path
import org.eclipse.n4js.N4JSGlobals

/**
 * The template of the temporary, auto-generated Javascript file used to launch tests from the
 * N4JS IDE via the node tester.
 */
class TestLaunchScriptTemplate {

	def public static String getTestLaunchScript(Path workingDirectory, String testTreeAsJSON) {
		val nodeModulesFolder = workingDirectory.resolve(N4JSGlobals.NODE_MODULES).toAbsolutePath;
		return '''
			(function() {
				"use strict";

				const lib_run = require("«nodeModulesFolder»/n4js-node/src-gen/run.js");
				lib_run.runWith({
					"test-catalog": «testTreeAsJSON»,
					"keep-eventloop": true,
					"main": "«nodeModulesFolder»/org.eclipse.n4js.mangelhaft.runner.ide/src-gen/org/eclipse/n4js/mangelhaft/runner/ide/IDENodeTestRunner"
				}, true /* exitOnError */);
			})();
		''';
	}
}
