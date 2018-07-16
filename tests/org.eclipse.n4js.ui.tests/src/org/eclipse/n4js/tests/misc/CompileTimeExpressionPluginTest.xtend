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

import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests some corner-cases of compile-time expressions which require a PluginUI test. Most tests for compile-time
 * expressions do not require this and are thus located elsewhere.
 */
class CompileTimeExpressionPluginTest extends AbstractBuilderParticipantTest {

	@Test
	def void testReferenceToConstFieldInOtherFile() throws Exception {
		val projectMain = createN4JSProject("Main", ProjectType.LIBRARY);
		val folderSrc = projectMain.project.getFolder("src");
		assertTrue(folderSrc.exists);

		createTestFile(folderSrc, "Other", '''
			function foo(): int { return 42; }

			export public class B {
				const ['field1'] = 42;
				const field2 = foo();
			}
		''');
		waitForAutoBuild();

		val fileMain = createTestFile(folderSrc, "Main", '''
			import { B } from "Other";

			class A {
				// XPECT errors --> "Not a compile-time expression: reference must point to a directly owned field (i.e. not inherited, consumed, or polyfilled) and the field must not have a computed name." at "field1"
				[B.field1] = 1;
				// XPECT errors --> "Not a compile-time expression: field field2 is const but does not have a compile-time expression as initializer: a call expression is never a compile-time expression at "foo()"." at "field2"
				[B.field2] = 2;
			}
		''');
		waitForAutoBuild();

		assertIssues(fileMain,
			'''line 5: Not a compile-time expression: reference must point to a directly owned field (i.e. not inherited, consumed, or polyfilled) and the field must not have a computed name.''',
			'''line 7: Not a compile-time expression: field field2 is const but does not have a compile-time expression as initializer.''');
	}
}
