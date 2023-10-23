/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest;
import org.junit.Test;

/**
 * Low-level tests for class {@code TypeAliasComputer}.
 */
public class TypeAliasComputerTest extends AbstractTypesystemTest {

	@Test
	public void testBasic() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type A = string;
				""");

		RuleEnvironment G = newRuleEnvironment(script);
		TypeAlias aliasA = findTypeAliasA(script);
		TypeRef typeRef = ref(aliasA);

		TypeRef resolvedTypeRef = tsh.resolveTypeAliases(G, typeRef);

		assertTrue(resolvedTypeRef.isAliasResolved());
		assertTrue(resolvedTypeRef instanceof ParameterizedTypeRef);
		assertEquals("A <=> string", resolvedTypeRef.getTypeRefAsStringWithAliasExpansion());
	}

	@Test
	public void testResolutionChangesMetaType() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type A = string | number;
				""");

		RuleEnvironment G = newRuleEnvironment(script);
		TypeAlias aliasA = findTypeAliasA(script);
		TypeRef typeRef = ref(aliasA);

		TypeRef resolvedTypeRef = tsh.resolveTypeAliases(G, typeRef);

		assertTrue(resolvedTypeRef.isAliasResolved());
		assertTrue(resolvedTypeRef instanceof UnionTypeExpression);
		assertEquals("A <=> union{string,number}", resolvedTypeRef.getTypeRefAsStringWithAliasExpansion());
	}

	@Test
	public void testNestedInComposedTypeRef() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type S = string;
				type U = number | S;
				type A = U;
				""");

		RuleEnvironment G = newRuleEnvironment(script);
		TypeAlias aliasA = findTypeAliasA(script);
		TypeRef typeRef = ref(aliasA);

		TypeRef resolvedTypeRef = tsh.resolveTypeAliases(G, typeRef);

		assertTrue(resolvedTypeRef.isAliasResolved());
		assertTrue(resolvedTypeRef instanceof UnionTypeExpression);

		TypeRef nestedTypeRef1 = ((UnionTypeExpression) resolvedTypeRef).getTypeRefs().get(0);
		assertFalse(nestedTypeRef1.isAliasUnresolved());
		assertFalse(nestedTypeRef1.isAliasResolved());
		TypeRef nestedTypeRef2 = ((UnionTypeExpression) resolvedTypeRef).getTypeRefs().get(1);
		assertTrue(nestedTypeRef2.isAliasResolved());
		assertEquals("S <=> string", nestedTypeRef2.getTypeRefAsStringWithAliasExpansion());
	}

	@Test
	public void testNestedInFunctionTypeRef() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type S = string;
				type F = (S)=>S;
				type A = F;
				""");

		RuleEnvironment G = newRuleEnvironment(script);
		TypeAlias aliasA = findTypeAliasA(script);
		TypeRef typeRef = ref(aliasA);

		TypeRef resolvedTypeRef = tsh.resolveTypeAliases(G, typeRef);

		assertTrue(resolvedTypeRef.isAliasResolved());
		assertTrue(resolvedTypeRef instanceof FunctionTypeExpression);

		TypeRef nestedTypeRef1 = ((FunctionTypeExpression) resolvedTypeRef).getFpars().get(0).getTypeRef();
		assertTrue(nestedTypeRef1.isAliasResolved());
		assertEquals("S <=> string", nestedTypeRef1.getTypeRefAsStringWithAliasExpansion());
		TypeRef nestedTypeRef2 = ((FunctionTypeExpression) resolvedTypeRef).getReturnTypeRef();
		assertTrue(nestedTypeRef2.isAliasResolved());
		assertEquals("S <=> string", nestedTypeRef2.getTypeRefAsStringWithAliasExpansion());
	}

	private TypeAlias findTypeAliasA(Script script) {
		TypeAlias result = head(filter(filter(
				script.getModule().getTypes(), TypeAlias.class), ta -> "A".equals(ta.getName())));
		assertNotNull(result);
		return result;
	}
}
