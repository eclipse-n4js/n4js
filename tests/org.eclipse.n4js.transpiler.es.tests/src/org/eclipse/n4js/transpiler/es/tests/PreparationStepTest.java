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
package org.eclipse.n4js.transpiler.es.tests;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.Objects;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests the code in the transpiler that creates the initial transpiler state, mainly the intermediate model.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class PreparationStepTest extends AbstractTranspilerTest {

	@Test
	public void testClassDecl() throws AssertionError {

		TranspilerState state = createTranspilerState("""

				class C {}
				C;

				""");

		validateState(state);
		assertNumOfSymbolTableEntries(state, 1);

		assertSymbolTableEntry(state, "C")
				.originalTarget(findFirstInModule(state, TClass.class))
				.elementsOfThisName(findFirstInIM(state, N4ClassDeclaration.class))
				.referencingElements(findFirstInIM(state, IdentifierRef_IM.class));
	}

	@Test
	public void testVariable() throws AssertionError {

		TranspilerState state = createTranspilerState("""

				var v;
				v;

				""");

		validateState(state);
		assertNumOfSymbolTableEntries(state, 1);

		assertSymbolTableEntry(state, "v")
				.originalTarget(findFirstInModule(state, TVariable.class))
				.elementsOfThisName(findFirstInIM(state, VariableDeclaration.class))
				.referencingElements(findFirstInIM(state, IdentifierRef_IM.class));
	}

	@Test
	public void testVariableExported() throws AssertionError {

		TranspilerState state = createTranspilerState("""

				export public var ve;
				ve;

				""");

		validateState(state);
		assertNumOfSymbolTableEntries(state, 1);

		assertSymbolTableEntry(state, "ve")
				.originalTarget(findFirstInModule(state, TVariable.class))
				.elementsOfThisName(findFirstInIM(state, VariableDeclaration.class))
				.referencingElements(findFirstInIM(state, IdentifierRef_IM.class));
	}

	@Test
	public void testImportNamed() throws AssertionError {

		ResourceSet resSet = addFileN4js(createResourceSet(), "B1", """
				export public class B1 {}
				""");

		TranspilerState state = createTranspilerState("""

				import { B1 } from "B1"
				var b1 : B1;
				B1;

				""", resSet);

		validateState(state);
		assertNumOfSymbolTableEntries(state, 2);

		assertSymbolTableEntry(state, "B1")
				.originalTarget(findFirstInRemoteModule(state, "B1", TClass.class))
				.elementsOfThisName() // should be empty, because class B1 is in a remote resource
				.importSpecifier(findFirstInIM(state, NamedImportSpecifier.class))
				.referencingElements(findFirstInIM(state, IdentifierRef_IM.class)); // note: the type reference pointing
																					// to B1 does not appear here

		assertSymbolTableEntry(state, "b1");
	}

	@Test
	public void testImportNamed_withAlias() throws AssertionError {

		ResourceSet resSet = addFileN4js(createResourceSet(), "B1", """
				export public class B1 {}
				""");

		TranspilerState state = createTranspilerState("""

				import { B1 as BX } from "B1"
				var b1 : BX;
				BX;

				""", resSet);

		validateState(state);
		assertNumOfSymbolTableEntries(state, 2);

		assertSymbolTableEntry(state, "BX")
				.originalTarget(findFirstInRemoteModule(state, "B1", TClass.class))
				.elementsOfThisName() // should be empty, because class B1 is in a remote resource
				.importSpecifier(findFirstInIM(state, NamedImportSpecifier.class))
				.referencingElements(findFirstInIM(state, IdentifierRef_IM.class)); // note: the type reference pointing
																					// to BX does not appear here

		assertSymbolTableEntry(state, "b1");
	}

	@Test
	public void testImportNamespace_typeRef() throws AssertionError {

		ResourceSet resSet = addFileN4js(createResourceSet(), "B1", """
				export public class B1 {}
				""");

		TranspilerState state = createTranspilerState("""

				import * as NS from "B1"
				var b1 : NS.B1;                       // note: here we have a ParameterizedTypeRef

				""", resSet);

		validateState(state);
		assertNumOfSymbolTableEntries(state, 2);

		// no STE for class B1, because only a type reference is referring to B1

		assertSymbolTableEntry(state, "NS")
				.originalTarget(findFirstInModule(state, ModuleNamespaceVirtualType.class))
				.elementsOfThisName() // should be empty for namespace symbol table entries
				.importSpecifier(findFirstInIM(state, NamespaceImportSpecifier.class))
				.referencingElements(); // no direct reference to namespace in this case (compare with next test case!)

		assertSymbolTableEntry(state, "b1");
	}

	@Test
	public void testImportNamespace_identifierRef() throws AssertionError {

		ResourceSet resSet = addFileN4js(createResourceSet(), "B1", """
					export public class B1 {}
				""");

		TranspilerState state = createTranspilerState("""

				import * as NS from "B1"
				NS.B1;                              // note: here we have an IdentifierRef

				""", resSet);

		validateState(state);
		assertNumOfSymbolTableEntries(state, 2);

		assertSymbolTableEntry(state, "B1")
				.originalTarget(findFirstInRemoteModule(state, "B1", TClass.class))
				.elementsOfThisName() // should be empty, because class B1 is in a remote resource
				.importSpecifier(findFirstInIM(state, NamespaceImportSpecifier.class))
				.referencingElements(findFirstInIM(state, ParameterizedPropertyAccessExpression_IM.class));

		assertSymbolTableEntry(state, "NS")
				.originalTarget(findFirstInModule(state, ModuleNamespaceVirtualType.class))
				.elementsOfThisName() // should be empty for namespace symbol table entries
				.importSpecifier(findFirstInIM(state, NamespaceImportSpecifier.class))
				.referencingElements(findFirstInIM(state, IdentifierRef_IM.class));// compare with previous test
	}

	@Test
	public void testBuiltIn_referenceToPrimitiveType() throws AssertionError {

		TranspilerState state = createTranspilerState("""

				var x : string;

				""");

		validateState(state);
		assertNumOfSymbolTableEntries(state, 1);

		// no STE for 'string', because only a type reference is referring to 'string'

		assertSymbolTableEntry(state, "x")
				.originalTarget(findFirstInModule(state, TVariable.class))
				.elementsOfThisName(findFirstInIM(state, VariableDeclaration.class))
				.importSpecifier(null)
				.referencingElements();
	}

	@Test
	public void testBuiltIn_referenceToBuiltInType_typeRef() throws AssertionError {

		TranspilerState state = createTranspilerState("""

				var x : N4Object;

				""");

		validateState(state);
		assertNumOfSymbolTableEntries(state, 1);

		assertSymbolTableEntry(state, "x")
				.originalTarget(findFirstInModule(state, TVariable.class))
				.elementsOfThisName(findFirstInIM(state, VariableDeclaration.class))
				.importSpecifier(null)
				.referencingElements();
	}

	@Test
	public void testBuiltIn_referenceToBuiltInType_identifierRef() throws AssertionError {

		TranspilerState state = createTranspilerState("""

				N4Object;

				""");

		validateState(state);
		assertNumOfSymbolTableEntries(state, 1);

		assertSymbolTableEntry(state, "N4Object")
				.originalTarget(RuleEnvironmentExtensions.n4ObjectType(state.G))
				.elementsOfThisName()
				.importSpecifier(null)
				.referencingElements(findFirstInIM(state, IdentifierRef_IM.class));
	}

	@Test
	public void testBuiltIn_referenceToMemberOfGlobalObject() throws AssertionError {

		TranspilerState state = createTranspilerState("""

				var x = undefined;

				""");

		validateState(state);
		assertNumOfSymbolTableEntries(state, 2);

		assertSymbolTableEntry(state, "undefined")
				.originalTarget(findFirst(
						RuleEnvironmentExtensions.globalObjectType(state.G).getOwnedMembers(),
						m -> Objects.equals(m.getName(), "undefined")))
				.elementsOfThisName()
				.importSpecifier(null)
				.referencingElements(findFirstInIM(state, IdentifierRef_IM.class));

		assertSymbolTableEntry(state, "x")
				.originalTarget(findFirstInModule(state, TVariable.class))
				.elementsOfThisName(findFirstInIM(state, VariableDeclaration.class))
				.importSpecifier(null)
				.referencingElements();
	}
}
