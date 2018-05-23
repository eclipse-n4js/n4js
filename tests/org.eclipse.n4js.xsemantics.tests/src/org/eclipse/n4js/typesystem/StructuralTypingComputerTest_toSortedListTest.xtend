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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.StructuralMembersTripleIterator
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class StructuralTypingComputerTest_toSortedListTest extends AbstractStructuralTypingComputerTest {

	@Inject ContainerTypesHelper containerTypesHelper

	@Test
	def testSortMembers() {
		assertSortedMembers(
			"a, b, c",
			'''
				class C { a; b; c; }
				var c: C;
			''');
	}

	@Test
	def testSortMembers2() {
		assertSortedMembers(
			"a, b, c",
			'''
				class C { c; b; a; }
				var c: C;
			''');
	}

	@Test
	def testSortMembers3() {
		assertSortedMembers(
			"a(), b(), c",
			'''
				class C { c; b(){}; a(){}; }
				var c: C;
			''');
	}
	@Test
	def testSortMembers4() {
		assertSortedMembers(
			"c, get b, set b",
			'''
				class C { c; get b(){ return null; }; set b(x){}; }
				var c: C;
			''');
	}

	def void assertSortedMembers(String expectedMembers, CharSequence scriptSrc) {
		val script = assembler.setupScript(scriptSrc, JavaScriptVariant.n4js, 0);
		val typeRef = (script.scriptElements.last as VariableStatement).varDecl.head.declaredTypeRef;
		val members = containerTypesHelper.fromContext(script).members(((typeRef as ParameterizedTypeRef).declaredType as TClass))
			.filter[it.containingType==typeRef.declaredType]

		assertMembers(expectedMembers, StructuralMembersTripleIterator.toSortedArray(members));
	}

}
