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
package org.eclipse.n4js.typesystem;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.Arrays;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.StructuralMembersTripleIterator;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class StructuralTypingComputerTest_toSortedListTest extends AbstractStructuralTypingComputerTest {

	@Inject
	ContainerTypesHelper containerTypesHelper;

	@Test
	public void testSortMembers() {
		assertSortedMembers(
				"a, b, c",
				"""
						class C { a; b; c; }
						var c: C;
						""");
	}

	@Test
	public void testSortMembers2() {
		assertSortedMembers(
				"a, b, c",
				"""
						class C { c; b; a; }
						var c: C;
						""");
	}

	@Test
	public void testSortMembers3() {
		assertSortedMembers(
				"a(), b(), c",
				"""
						class C { c; b(){}; a(){}; }
						var c: C;
						""");
	}

	@Test
	public void testSortMembers4() {
		assertSortedMembers(
				"c, get b, set b",
				"""
						class C { c; get b(){ return null; }; set b(x){}; }
						var c: C;
						""");
	}

	public void assertSortedMembers(String expectedMembers, CharSequence scriptSrc) {
		Script script = assembler.setupScript(scriptSrc, JavaScriptVariant.n4js, 0);
		TypeRef typeRef = ((VariableStatement) last(script.getScriptElements())).getVarDecl().get(0)
				.getDeclaredTypeRef();
		Iterable<TMember> members = filter(
				containerTypesHelper.fromContext(script).members(((TClass) ((ParameterizedTypeRef) typeRef)
						.getDeclaredType())),
				m -> m.getContainingType() == typeRef.getDeclaredType());

		assertMembers(expectedMembers, Arrays.asList(StructuralMembersTripleIterator.toSortedArray(members)));
	}

}
