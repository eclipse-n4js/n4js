/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.bugs;

import java.io.File
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.junit.Test

/**
 * Test class for checking type system does not cause build failure when processing and traversing broken AST.
 */
public class GHOLD_129_BrokenAstMustNotCauseBuildFailure_IdeTest extends ConvertedIdeTest {

	private static final String PROJECT_NAME = "GHOLD-129";

	/**
	 * We expect validation errors which means the build was not broken and interrupted due to broken AST when inferring
	 * the types.
	 */
	@Test
	def void checkBrokenAstDoesNotCauseBuildFailure_ExpectValidationErrors() {
		importProband(new File("probands", PROJECT_NAME));
		assertIssues(
			"BrokenAst_GHOLD_129" -> #[
				"(Error, [11:0 - 13:1], Name missing in type declaration.)",
				"(Error, [14:4 - 14:5], Couldn't resolve reference to IdentifiableElement 'A'.)"
			]
		);
	}
}
