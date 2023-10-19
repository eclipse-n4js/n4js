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
package org.eclipse.n4js.tests.typesbuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class VariablesAndConstantsTypesBuilderTest extends AbstractTypesBuilderTest {

	@Inject
	private ASTStructureAssertionExtension astStructX;

	@Inject
	private TypesStructureAssertionExtension typesStructX;

	@Override
	protected boolean enableUserDataCompare() {
		// to check the complete AST just change false to true
		return false;
		// true
	}

	@Test
	public void test() {
		String textFileName = "VariablesAndConstants.n4js";
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypesNamePairs = List.of(
				Pair.of(TVariable.class, "a"),
				Pair.of(TVariable.class, "b"),
				Pair.of(TVariable.class, "CONST1"),
				Pair.of(TVariable.class, "CONST2"));

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairsOnIndex = List
				.of(
						Pair.of(TModule.class, getQualifiedNamePrefix() + "VariablesAndConstants"),
						Pair.of(TVariable.class, "a"),
						Pair.of(TVariable.class, "b"),
						Pair.of(TVariable.class, "CONST1"),
						Pair.of(TVariable.class, "CONST2"));
		int expectedTypesCount = expectedTypesNamePairs.size();
		int expectedExportedElementsCount = 3;// because of 3 variable statements
		executeTest(textFileName, expectedExportedTypeToNamePairsOnIndex, expectedTypesCount,
				expectedExportedElementsCount);
	}

	@Override
	public String getExpectedTypesSerialization() {
		return """
				TModule {

				}
				""";
	}

	@Override
	public void assertExampleTypeStructure(String phase, Resource newN4jsResource) {
		assertEquals("AST and TModule as root", 2, newN4jsResource.getContents().size());

		TVariable firstVariable = typesStructX.assertTVariable(phase, newN4jsResource, 0, "a", false);
		Type firstVariableType = typesStructX.assertTypeRef(phase, firstVariable, newN4jsResource);
		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, firstVariableType, "/1/@types.8");// int

		TVariable secondVariable = typesStructX.assertTVariable(phase, newN4jsResource, 1, "b", false);
		Type secondVariableType = typesStructX.assertTypeRef(phase, secondVariable, newN4jsResource);
		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, secondVariableType, "/1/@types.4");

		TVariable thirdVariable = typesStructX.assertTVariable(phase, newN4jsResource, 2, "CONST1", true);
		assertTrue(thirdVariable.getTypeRef() instanceof NumericLiteralTypeRef);
		assertEquals("6", thirdVariable.getTypeRef().getTypeRefAsString());

		TVariable fourthVariable = typesStructX.assertTVariable(phase, newN4jsResource, 3, "CONST2", true);
		Type fourthVariableType = typesStructX.assertTypeRef(phase, fourthVariable, newN4jsResource);
		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, fourthVariableType, "/1/@types.3");

		Assert.assertEquals("Should have only 4 exported variables as the fifth one is not marked as exported",
				4, ((TModule) newN4jsResource.getContents().get(1)).getExportedVariables().size());
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.getContents().size());

		Script script = astStructX.assertScript(phase, resource, 3);

		ExportableElement firstExportableElement = astStructX.assertExportDeclaration(phase, script, 0);
		VariableStatement firstVariableStatement = astStructX.assertVariableStatement(phase, firstExportableElement, 2);

		VariableDeclaration firstVariable = astStructX.assertVariable(phase, firstVariableStatement, "a", false);
		astStructX.assertDefinedVariable(phase, firstVariable, resource, Literal.class);

		VariableDeclaration secondVariable = astStructX.assertVariable(phase, firstVariableStatement, "b", false);
		astStructX.assertDefinedVariable(phase, secondVariable, resource, null);

		ExportableElement secondExportableElement = astStructX.assertExportDeclaration(phase, script, 1);
		VariableStatement secondVariableStatement = astStructX.assertVariableStatement(phase, secondExportableElement,
				2);

		VariableDeclaration thirdVariable = astStructX.assertVariable(phase, secondVariableStatement, "CONST1", true);
		astStructX.assertDefinedVariable(phase, thirdVariable, resource, Literal.class);

		VariableDeclaration fourthVariable = astStructX.assertVariable(phase, secondVariableStatement, "CONST2", true);
		astStructX.assertDefinedVariable(phase, fourthVariable, resource, Literal.class);

		VariableStatement thirdExportableElement = (VariableStatement) script.getScriptElements().get(2);
		VariableStatement thirdVariableStatement = astStructX.assertVariableStatement(phase, thirdExportableElement, 1);

		VariableDeclaration fifthVariable = astStructX.assertVariable(phase, thirdVariableStatement, "c", false);
		astStructX.assertDefinedVariable(phase, fifthVariable, resource, FunctionExpression.class);
	}
}
