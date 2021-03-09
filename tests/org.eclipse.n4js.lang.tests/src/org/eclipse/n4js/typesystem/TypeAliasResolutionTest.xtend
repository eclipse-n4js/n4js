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

import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest
import org.junit.Test

import static org.junit.Assert.*

/**
 * Low-level tests for classes {@code TypeRefProcessor} (handling resolution in AST) and
 * {@code TypeAliasProcessor} (handling resolution in TModule).
 */
class TypeAliasResolutionTest extends AbstractTypesystemTest {

	@Test
	def void testVarDecl() {
		val script = '''
			type S = string;
			let x: S;
		'''.parseAndValidateSuccessfully;

		val varDecl = script.eAllContents.filter(VariableDeclaration).head;
		assertResolutionInAST("S <=> string", varDecl.declaredTypeRefNode);
	}	

	@Test
	def void testExportedVarDecl() {
		val script = '''
			type S = string;
			export let x: S;
		'''.parseAndValidateSuccessfully;

		val varDecl = script.eAllContents.filter(ExportedVariableDeclaration).head;
		assertResolutionBothSides("S <=> string", varDecl.declaredTypeRefNode, varDecl.definedVariable.typeRef);
	}	

	@Test
	def void testClassMembers() {
		val script = '''
			type S = string;
			class Cls {
				field: S;
				get g(): S { return null; }
				set s(p: S) {}
				m(p: S): S { return null; }
			}
		'''.parseAndValidateSuccessfully;

		val n4Class = script.scriptElements.filter(N4ClassDeclaration).head;
		val fieldDecl = n4Class.ownedFields.head;
		val getterDecl = n4Class.ownedGetters.head;
		val setterDecl = n4Class.ownedSetters.head;
		val methodDecl = n4Class.ownedMethods.head;

		assertResolutionBothSides("S <=> string", fieldDecl.declaredTypeRefNode, fieldDecl.definedField.typeRef);
		assertResolutionBothSides("S <=> string", getterDecl.declaredTypeRefNode, getterDecl.definedGetter.typeRef);
		assertResolutionBothSides("S <=> string", setterDecl.fpar.declaredTypeRefNode, setterDecl.definedSetter.typeRef);
		assertResolutionBothSides("S <=> string", methodDecl.fpars.head.declaredTypeRefNode, methodDecl.definedFunction.fpars.head.typeRef);
		assertResolutionBothSides("S <=> string", methodDecl.declaredReturnTypeRefNode, methodDecl.definedFunction.returnTypeRef);
	}	

	@Test
	def void testInterfaceMembers() {
		val script = '''
			type S = string;
			interface Ifc {
				field: S;
				get g(): S
				set s(p: S)
				m(p: S): S
			}
		'''.parseAndValidateSuccessfully;

		val n4Ifc = script.scriptElements.filter(N4InterfaceDeclaration).head;
		val fieldDecl = n4Ifc.ownedFields.head;
		val getterDecl = n4Ifc.ownedGetters.head;
		val setterDecl = n4Ifc.ownedSetters.head;
		val methodDecl = n4Ifc.ownedMethods.head;

		assertResolutionBothSides("S <=> string", fieldDecl.declaredTypeRefNode, fieldDecl.definedField.typeRef);
		assertResolutionBothSides("S <=> string", getterDecl.declaredTypeRefNode, getterDecl.definedGetter.typeRef);
		assertResolutionBothSides("S <=> string", setterDecl.fpar.declaredTypeRefNode, setterDecl.definedSetter.typeRef);
		assertResolutionBothSides("S <=> string", methodDecl.fpars.head.declaredTypeRefNode, methodDecl.definedFunction.fpars.head.typeRef);
		assertResolutionBothSides("S <=> string", methodDecl.declaredReturnTypeRefNode, methodDecl.definedFunction.returnTypeRef);
	}	

	@Test
	def void testTypeAlias() {
		val script = '''
			type S = string;
			type A = S;
		'''.parseAndValidateSuccessfully;

		val aliasDecl = script.eAllContents.filter(N4TypeAliasDeclaration).filter[name=="A"].head;
		assertResolutionInAST("S <=> string", aliasDecl.declaredTypeRefNode);

		val actualTypeRefInModule = aliasDecl.definedTypeAsTypeAlias.typeRef;
		assertNotNull(actualTypeRefInModule);
		assertTrue(actualTypeRefInModule.isAliasUnresolved); // NOTE: the actual type of a type alias is not resolved in the TModule!
		assertFalse(actualTypeRefInModule.isAliasResolved);
	}	

	def private void assertResolutionBothSides(String expectedResolutionAsString, TypeReferenceNode<?> astNode, TypeRef typeRefInModule) {
		// in AST:
		assertResolutionInAST(expectedResolutionAsString, astNode);

		// in TModule:
		assertNotNull(typeRefInModule);
		assertFalse(typeRefInModule.isAliasUnresolved);
		assertTrue (typeRefInModule.isAliasResolved);
		assertEquals(expectedResolutionAsString, typeRefInModule.typeRefAsStringWithAliasResolution);
	}

	def private void assertResolutionInAST(String expectedResolutionAsString, TypeReferenceNode<?> astNode) {
		assertNotNull(astNode);
		assertTrue (astNode.typeRefInAST.isAliasUnresolved);
		assertFalse(astNode.typeRefInAST.isAliasResolved);
		assertFalse(astNode.typeRef.isAliasUnresolved);
		assertTrue (astNode.typeRef.isAliasResolved);
		assertEquals(expectedResolutionAsString, astNode.typeRef.typeRefAsStringWithAliasResolution);
	}
}
