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
package org.eclipse.n4js.typesystem

import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.types.TypeAlias
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest
import org.junit.Test

import static org.junit.Assert.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Low-level tests for class {@code TypeAliasComputer}.
 */
class TypeAliasComputerTest extends AbstractTypesystemTest {

	@Test
	def void testBasic() {
		val script = '''
			type A = string;
		'''.parseAndValidateSuccessfully;

		val G = script.newRuleEnvironment;
		val aliasA = script.findTypeAliasA;
		val typeRef = aliasA.ref;

		val resolvedTypeRef = tsh.resolveTypeAliases(G, typeRef);

		assertTrue(resolvedTypeRef.isAliasResolved);
		assertTrue(resolvedTypeRef instanceof ParameterizedTypeRef);
		assertEquals("A <=> string", resolvedTypeRef.typeRefAsStringWithAliasResolution);
	}

	@Test
	def void testResolutionChangesMetaType() {
		val script = '''
			type A = string | number;
		'''.parseAndValidateSuccessfully;

		val G = script.newRuleEnvironment;
		val aliasA = script.findTypeAliasA;
		val typeRef = aliasA.ref;

		val resolvedTypeRef = tsh.resolveTypeAliases(G, typeRef);

		assertTrue(resolvedTypeRef.isAliasResolved);
		assertTrue(resolvedTypeRef instanceof UnionTypeExpression);
		assertEquals("A <=> union{string,number}", resolvedTypeRef.typeRefAsStringWithAliasResolution);
	}

	@Test
	def void testNestedInComposedTypeRef() {
		val script = '''
			type S = string;
			type U = number | S;
			type A = U;
		'''.parseAndValidateSuccessfully;

		val G = script.newRuleEnvironment;
		val aliasA = script.findTypeAliasA;
		val typeRef = aliasA.ref;

		val resolvedTypeRef = tsh.resolveTypeAliases(G, typeRef);

		assertTrue(resolvedTypeRef.isAliasResolved);
		assertTrue(resolvedTypeRef instanceof UnionTypeExpression);

		val nestedTypeRef1 = (resolvedTypeRef as UnionTypeExpression).typeRefs.get(0);
		assertFalse(nestedTypeRef1.isAliasUnresolved);
		assertFalse(nestedTypeRef1.isAliasResolved);
		val nestedTypeRef2 = (resolvedTypeRef as UnionTypeExpression).typeRefs.get(1);
		assertTrue(nestedTypeRef2.isAliasResolved);
		assertEquals("S <=> string", nestedTypeRef2.typeRefAsStringWithAliasResolution);
	}

	@Test
	def void testNestedInFunctionTypeRef() {
		val script = '''
			type S = string;
			type F = (S)=>S;
			type A = F;
		'''.parseAndValidateSuccessfully;

		val G = script.newRuleEnvironment;
		val aliasA = script.findTypeAliasA;
		val typeRef = aliasA.ref;

		val resolvedTypeRef = tsh.resolveTypeAliases(G, typeRef);

		assertTrue(resolvedTypeRef.isAliasResolved);
		assertTrue(resolvedTypeRef instanceof FunctionTypeExpression);

		val nestedTypeRef1 = (resolvedTypeRef as FunctionTypeExpression).fpars.get(0).typeRef;
		assertTrue(nestedTypeRef1.isAliasResolved);
		assertEquals("S <=> string", nestedTypeRef1.typeRefAsStringWithAliasResolution);
		val nestedTypeRef2 = (resolvedTypeRef as FunctionTypeExpression).returnTypeRef;
		assertTrue(nestedTypeRef2.isAliasResolved);
		assertEquals("S <=> string", nestedTypeRef2.typeRefAsStringWithAliasResolution);
	}

	def private TypeAlias findTypeAliasA(Script script) {
		val result = script.module.topLevelTypes.filter(TypeAlias).filter[name=="A"].head;
		assertNotNull(result);
		return result;
	}
}
