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
package org.eclipse.n4js.transpiler.es.tests

import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 * Tests the code in the transpiler that creates the initial transpiler state, mainly the intermediate model.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class PreparationStepTest extends AbstractTranspilerTest {


	@Test
	def void testClassDecl()  throws AssertionError {

		val state = '''

			class C {}
			C;

		'''.createTranspilerState;

		state.validateState;
		state.assertNumOfSymbolTableEntries(1);

		state.assertSymbolTableEntry("C")
		.originalTarget(state.findFirstInModule(TClass))
		.elementsOfThisName(state.findFirstInIM(N4ClassDeclaration))
		.referencingElements(state.findFirstInIM(IdentifierRef_IM))
	}

	@Test
	def void testVariable() throws AssertionError {

		val state = '''

			var v;
			v;

		'''.createTranspilerState;

		state.validateState;
		state.assertNumOfSymbolTableEntries(1);

		state.assertSymbolTableEntry("v")
		.originalTarget(state.findFirstInAST(VariableDeclaration))
		.elementsOfThisName(state.findFirstInIM(VariableDeclaration))
		.referencingElements(state.findFirstInIM(IdentifierRef_IM))
	}

	@Test
	def void testVariableExported() throws AssertionError {

		val state = '''

			export public var ve;
			ve;

		'''.createTranspilerState;

		state.validateState;
		state.assertNumOfSymbolTableEntries(1);

		state.assertSymbolTableEntry("ve")
		.originalTarget(state.findFirstInModule(TVariable))
		.elementsOfThisName(state.findFirstInIM(VariableDeclaration))
		.referencingElements(state.findFirstInIM(IdentifierRef_IM))
	}

	@Test
	def void testImportNamed() throws AssertionError {

		val resSet = createResourceSet().addFileN4js("B1", '''
			export public class B1 {}
		''');

		val state = '''

			import { B1 } from "B1"
			var b1 : B1;

		'''.createTranspilerState(resSet);

		state.validateState;
		state.assertNumOfSymbolTableEntries(2);

		state.assertSymbolTableEntry("B1")
		.originalTarget(state.findFirstInRemoteModule("B1",TClass))
		.elementsOfThisName() // should be empty, because class B1 is in a remote resource
		.importSpecifier(state.findFirstInIM(NamedImportSpecifier))
		.referencingElements(state.findFirstInIM(ParameterizedTypeRef_IM))

		state.assertSymbolTableEntry("b1");
	}

	@Test
	def void testImportNamed_withAlias() throws AssertionError {

		val resSet = createResourceSet().addFileN4js("B1", '''
			export public class B1 {}
		''');

		val state = '''

			import { B1 as BX } from "B1"
			var b1 : BX;

		'''.createTranspilerState(resSet);

		state.validateState;
		state.assertNumOfSymbolTableEntries(2);

		state.assertSymbolTableEntry("BX")
		.originalTarget(state.findFirstInRemoteModule("B1",TClass))
		.elementsOfThisName() // should be empty, because class B1 is in a remote resource
		.importSpecifier(state.findFirstInIM(NamedImportSpecifier))
		.referencingElements(state.findFirstInIM(ParameterizedTypeRef_IM))

		state.assertSymbolTableEntry("b1");
	}

	@Test
	def void testImportNamespace_typeRef() throws AssertionError {

		val resSet = createResourceSet().addFileN4js("B1", '''
			export public class B1 {}
		''');

		val state = '''

			import * as NS from "B1"
			var b1 : NS.B1;                       // note: here we have a ParameterizedTypeRef

		'''.createTranspilerState(resSet);

		state.validateState;
		state.assertNumOfSymbolTableEntries(3);

		state.assertSymbolTableEntry("NS")
		.originalTarget(state.findFirstInModule(ModuleNamespaceVirtualType))
		.elementsOfThisName() // should be empty for namespace symbol table entries
		.importSpecifier(state.findFirstInIM(NamespaceImportSpecifier))
		.referencingElements() // no direct reference to namespace in this case (compare with next test case!)

		state.assertSymbolTableEntry("B1")
		.originalTarget(state.findFirstInRemoteModule("B1",TClass))
		.elementsOfThisName() // should be empty, because class B1 is in a remote resource
		.importSpecifier(state.findFirstInIM(NamespaceImportSpecifier))
		.referencingElements(state.findFirstInIM(ParameterizedTypeRef_IM))

		state.assertSymbolTableEntry("b1");
	}

	@Test
	def void testImportNamespace_identifierRef() throws AssertionError {

		val resSet = createResourceSet().addFileN4js("B1", '''
			export public class B1 {}
		''');

		val state = '''

			import * as NS from "B1"
			NS.B1;                              // note: here we have an IdentifierRef

		'''.createTranspilerState(resSet);

		state.validateState;
		state.assertNumOfSymbolTableEntries(2);

		state.assertSymbolTableEntry("B1")
		.originalTarget(state.findFirstInRemoteModule("B1",TClass))
		.elementsOfThisName() // should be empty, because class B1 is in a remote resource
		.importSpecifier(state.findFirstInIM(NamespaceImportSpecifier))
		.referencingElements(state.findFirstInIM(ParameterizedPropertyAccessExpression_IM))

		state.assertSymbolTableEntry("NS")
		.originalTarget(state.findFirstInModule(ModuleNamespaceVirtualType))
		.elementsOfThisName() // should be empty for namespace symbol table entries
		.importSpecifier(state.findFirstInIM(NamespaceImportSpecifier))
		.referencingElements(state.findFirstInIM(IdentifierRef_IM)) // compare with previous test
	}

	@Test
	def void testBuiltIn_referenceToPrimitiveType() throws AssertionError {

		val state = '''

			var x : string;

		'''.createTranspilerState;

		state.validateState;
		state.assertNumOfSymbolTableEntries(2);

		state.assertSymbolTableEntry("string")
		.originalTarget(state.G.stringType)
		.elementsOfThisName()
		.importSpecifier(null)
		.referencingElements(state.findFirstInIM(ParameterizedTypeRef_IM))

		state.assertSymbolTableEntry("x")
		.originalTarget(state.findFirstInAST(VariableDeclaration))
		.elementsOfThisName(state.findFirstInIM(VariableDeclaration))
		.importSpecifier(null)
		.referencingElements()
	}

	@Test
	def void testBuiltIn_referenceToBuiltInType() throws AssertionError {

		val state = '''

			var x : N4Object;

		'''.createTranspilerState;

		state.validateState;
		state.assertNumOfSymbolTableEntries(2);

		state.assertSymbolTableEntry("N4Object")
		.originalTarget(state.G.n4ObjectType)
		.elementsOfThisName()
		.importSpecifier(null)
		.referencingElements(state.findFirstInIM(ParameterizedTypeRef_IM))

		state.assertSymbolTableEntry("x")
		.originalTarget(state.findFirstInAST(VariableDeclaration))
		.elementsOfThisName(state.findFirstInIM(VariableDeclaration))
		.importSpecifier(null)
		.referencingElements()
	}

	@Test
	def void testBuiltIn_referenceToMemberOfGlobalObject() throws AssertionError {

		val state = '''

			var x = undefined;

		'''.createTranspilerState;

		state.validateState;
		state.assertNumOfSymbolTableEntries(2);

		state.assertSymbolTableEntry("undefined")
		.originalTarget(state.G.globalObjectType.ownedMembers.findFirst[name=="undefined"])
		.elementsOfThisName()
		.importSpecifier(null)
		.referencingElements(state.findFirstInIM(IdentifierRef_IM))

		state.assertSymbolTableEntry("x")
		.originalTarget(state.findFirstInAST(VariableDeclaration))
		.elementsOfThisName(state.findFirstInIM(VariableDeclaration))
		.importSpecifier(null)
		.referencingElements()
	}
}
