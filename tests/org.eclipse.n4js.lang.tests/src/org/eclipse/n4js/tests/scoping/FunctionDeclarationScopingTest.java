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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests function declaration specific functionality, such as type variables.
 *
 * see IDE-346
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class FunctionDeclarationScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testTypeVariableBindingLocalVars() throws Exception {
		Script script = parseHelper.parse("""
				function <T> f1() {
				    var t: T;
				}
				""");
		valTestHelper.assertNoErrors(script);
		TFunction tfunc = (TFunction) head(filter(script.eAllContents(), FunctionDeclaration.class)).getDefinedType();
		VariableDeclaration t = head(filter(script.eAllContents(), VariableDeclaration.class));
		assertEquals(t.getDeclaredTypeRefInAST().getDeclaredType(), tfunc.getTypeVars().get(0));
	}

	@Test
	public void testTypeVariableBindingParameters() throws Exception {
		Script script = parseHelper.parse("""
				function <T> f1(t: T, u: U): T {
				    return null;
				}
				""");
		// valTestHelper.assertNoErrors(script); // we expect errors, U cannot be bound
		FunctionDeclaration funcDecl = head(filter(script.eAllContents(), FunctionDeclaration.class));
		TFunction tfunc = (TFunction) funcDecl.getDefinedType();
		TypeVariable typeVar = tfunc.getTypeVars().get(0);
		assertEquals(funcDecl.getDeclaredReturnTypeRefInAST().getDeclaredType(), typeVar);
		assertEquals(funcDecl.getFpars().get(0).getDeclaredTypeRefInAST().getDeclaredType(), typeVar);
		// ensure that we do not compare nonsense:
		assertNotEquals(funcDecl.getFpars().get(1).getDeclaredTypeRefInAST().getDeclaredType(), typeVar);
	}

}
