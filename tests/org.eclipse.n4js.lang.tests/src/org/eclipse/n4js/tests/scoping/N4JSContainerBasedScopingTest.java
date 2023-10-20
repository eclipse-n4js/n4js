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
package org.eclipse.n4js.tests.scoping;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Test to double-check that container-based scoping shows the correct behavior in the context of synthesized JUnit
 * tests.
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class N4JSContainerBasedScopingTest {
	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testNoPrimitiveTypesInIdentifiableScope() throws Exception {
		// The fact that we don't allow primitive types in Identifiable Scopes
		// is tested more thoroughly in the corresponding Xpect test. This is just
		// to double-check the behavior is the same in all testing contexts.
		valTestHelper.assertIssue(parseHelper.parse("""
				var a = int
				"""), N4JSPackage.Literals.IDENTIFIER_REF,
				Diagnostic.LINKING_DIAGNOSTIC,
				Severity.ERROR,
				"Couldn't resolve reference to IdentifiableElement 'int'.");
	}
}
