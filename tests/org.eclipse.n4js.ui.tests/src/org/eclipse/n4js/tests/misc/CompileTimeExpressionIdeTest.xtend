/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.misc

import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.junit.Test

/**
 * Tests some corner-cases of compile-time expressions which require an IDE test. Most tests for compile-time
 * expressions do not require this and are thus located elsewhere.
 */
// converted from CompileTimeExpressionPluginTest
class CompileTimeExpressionIdeTest extends ConvertedIdeTest {

	@Test
	def void testReferenceToConstFieldInOtherFile() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"Other" -> '''
				function foo(): int { return 42; }

				export public class B {
					const ['field1'] = 42;
					const field2 = foo();
				}
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		createFile("Main", '''
			import { B } from "Other";

			class A {
				[B.field1] = 1;
				[B.field2] = 2;
			}
		''');
		joinServerRequests();

		assertIssues(
			"Main" -> #[
				"(Error, [3:4 - 3:10], Not a compile-time expression: reference must point to a directly owned field (i.e. not inherited, consumed, or polyfilled) and the field must not have a computed name.)",
				"(Error, [4:4 - 4:10], Not a compile-time expression: field field2 is const but does not have a compile-time expression as initializer.)"
			]
		);
	}
}
