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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest;
import org.junit.Test;

/**
 * Low-level tests for classes {@code TypeRefProcessor} (handling resolution in AST) and {@code TypeAliasProcessor}
 * (handling resolution in TModule).
 */
public class TypeAliasResolutionTest extends AbstractTypesystemTest {

	@Test
	public void testVarDecl() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type S = string;
				let x: S;
				""");

		VariableDeclaration varDecl = head(
				filter(script.eAllContents(), VariableDeclaration.class));
		assertResolutionBothSides("S <=> string", varDecl.getDeclaredTypeRefNode(),
				varDecl.getDefinedVariable().getTypeRef());
	}

	@Test
	public void testExportedVarDecl() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type S = string;
				export let x: S;
				""");

		VariableDeclaration varDecl = head(
				filter(script.eAllContents(), VariableDeclaration.class));
		assertResolutionBothSides("S <=> string", varDecl.getDeclaredTypeRefNode(),
				varDecl.getDefinedVariable().getTypeRef());
	}

	@Test
	public void testClassMembers() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type S = string;
				class Cls {
					field: S;
					get g(): S { return null; }
					set s(p: S) {}
					m(p: S): S { return null; }
				}
				""");

		N4ClassDeclaration n4Class = head(
				filter(script.getScriptElements(), N4ClassDeclaration.class));
		N4FieldDeclaration fieldDecl = head(n4Class.getOwnedFields());
		N4GetterDeclaration getterDecl = head(n4Class.getOwnedGetters());
		N4SetterDeclaration setterDecl = head(n4Class.getOwnedSetters());
		N4MethodDeclaration methodDecl = head(n4Class.getOwnedMethods());

		assertResolutionBothSides("S <=> string", fieldDecl.getDeclaredTypeRefNode(),
				fieldDecl.getDefinedField().getTypeRef());
		assertResolutionBothSides("S <=> string", getterDecl.getDeclaredTypeRefNode(),
				getterDecl.getDefinedGetter().getTypeRef());
		assertResolutionBothSides("S <=> string", setterDecl.getFpar().getDeclaredTypeRefNode(),
				setterDecl.getDefinedSetter().getTypeRef());
		assertResolutionBothSides("S <=> string", head(methodDecl.getFpars()).getDeclaredTypeRefNode(),
				head(methodDecl.getDefinedFunction().getFpars()).getTypeRef());
		assertResolutionBothSides("S <=> string", methodDecl.getDeclaredReturnTypeRefNode(),
				methodDecl.getDefinedFunction().getReturnTypeRef());
	}

	@Test
	public void testInterfaceMembers() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type S = string;
				interface Ifc {
					field: S;
					get g(): S
					set s(p: S)
					m(p: S): S
				}
				""");

		N4InterfaceDeclaration n4Ifc = head(
				filter(script.getScriptElements(), N4InterfaceDeclaration.class));
		N4FieldDeclaration fieldDecl = head(n4Ifc.getOwnedFields());
		N4GetterDeclaration getterDecl = head(n4Ifc.getOwnedGetters());
		N4SetterDeclaration setterDecl = head(n4Ifc.getOwnedSetters());
		N4MethodDeclaration methodDecl = head(n4Ifc.getOwnedMethods());

		assertResolutionBothSides("S <=> string", fieldDecl.getDeclaredTypeRefNode(),
				fieldDecl.getDefinedField().getTypeRef());
		assertResolutionBothSides("S <=> string", getterDecl.getDeclaredTypeRefNode(),
				getterDecl.getDefinedGetter().getTypeRef());
		assertResolutionBothSides("S <=> string", setterDecl.getFpar().getDeclaredTypeRefNode(),
				setterDecl.getDefinedSetter().getTypeRef());
		assertResolutionBothSides("S <=> string", head(methodDecl.getFpars()).getDeclaredTypeRefNode(),
				head(methodDecl.getDefinedFunction().getFpars()).getTypeRef());
		assertResolutionBothSides("S <=> string", methodDecl.getDeclaredReturnTypeRefNode(),
				methodDecl.getDefinedFunction().getReturnTypeRef());
	}

	@Test
	public void testTypeAlias() {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				type S = string;
				type A = S;
				""");

		N4TypeAliasDeclaration aliasDecl = head(filter(filter(
				script.eAllContents(), N4TypeAliasDeclaration.class), ad -> "A".equals(ad.getName())));
		assertResolutionInAST("S <=> string", aliasDecl.getDeclaredTypeRefNode());

		TypeRef actualTypeRefInModule = aliasDecl.getDefinedTypeAsTypeAlias().getTypeRef();
		assertNotNull(actualTypeRefInModule);
		assertTrue(actualTypeRefInModule.isAliasUnresolved()); // NOTE: the actual type of a type alias is not resolved
																// in the TModule!
		assertFalse(actualTypeRefInModule.isAliasResolved());
	}

	private void assertResolutionBothSides(String expectedResolutionAsString, TypeReferenceNode<?> astNode,
			TypeRef typeRefInModule) {
		// in AST:
		assertResolutionInAST(expectedResolutionAsString, astNode);

		// in TModule:
		assertNotNull(typeRefInModule);
		assertFalse(typeRefInModule.isAliasUnresolved());
		assertTrue(typeRefInModule.isAliasResolved());
		assertEquals(expectedResolutionAsString, typeRefInModule.getTypeRefAsStringWithAliasExpansion());
	}

	private void assertResolutionInAST(String expectedResolutionAsString, TypeReferenceNode<?> astNode) {
		assertNotNull(astNode);
		assertTrue(astNode.getTypeRefInAST().isAliasUnresolved());
		assertFalse(astNode.getTypeRefInAST().isAliasResolved());
		assertFalse(astNode.getTypeRef().isAliasUnresolved());
		assertTrue(astNode.getTypeRef().isAliasResolved());
		assertEquals(expectedResolutionAsString, astNode.getTypeRef().getTypeRefAsStringWithAliasExpansion());
	}
}
