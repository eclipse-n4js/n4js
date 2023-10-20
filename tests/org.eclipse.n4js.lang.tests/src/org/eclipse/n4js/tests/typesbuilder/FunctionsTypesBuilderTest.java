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
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TModule;
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
public class FunctionsTypesBuilderTest extends AbstractTypesBuilderTest {

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
		String textFileName = "Functions.n4js";
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypesNamePairs = List.of(
				Pair.of(TFunction.class, "filterFunction"),
				Pair.of(TFunction.class, "transformFunction"));

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairsOnIndex = List
				.of(
						Pair.of(TModule.class, getQualifiedNamePrefix() + "Functions"),
						Pair.of(TFunction.class, "filterFunction"),
						Pair.of(TFunction.class, "transformFunction"));
		int expectedTypesCount = expectedTypesNamePairs.size();
		int expectedExportedElementsCount = expectedExportedTypeToNamePairsOnIndex.size();
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

		assertFirstTFunction(phase, newN4jsResource);

		assertSecondTFunction(phase, newN4jsResource);
	}

	public void assertFirstTFunction(String phase, Resource newN4jsResource) {
		TFunction tFunction = typesStructX.assertTFunction(phase, newN4jsResource, "filterFunction", 2);

		typesStructX.assertTypeVariables(phase, tFunction, newN4jsResource, "T", "U");

		Type type = typesStructX.assertReturnTypeRef(phase, tFunction, newN4jsResource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, "/1/@types.8");

		Type firstParameterType = typesStructX.assertParameter(phase, tFunction, newN4jsResource, "input", false);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, firstParameterType, "/1/@types.8");

		Type secondParameterType = typesStructX.assertParameter(phase, tFunction, newN4jsResource, "hint", false);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, secondParameterType, "/1/@types.8");
	}

	public void assertSecondTFunction(String phase, Resource newN4jsResource) {
		TFunction tFunction = typesStructX.assertTFunction(phase, newN4jsResource, "transformFunction", 0);

		typesStructX.assertTypeVariables(phase, tFunction, newN4jsResource);
		assertNotNull("inferred return type expected", tFunction.getReturnTypeRef());
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.getContents().size());

		Script script = astStructX.assertScript(phase, resource, 2);

		ExportableElement exportableElement = astStructX.assertExportDeclaration(phase, script, 0);

		assertFirstFunction(phase, resource, exportableElement);
	}

	public void assertFirstFunction(String phase, Resource resource, ExportableElement exportableElement) {
		FunctionDeclaration n4Function = astStructX.assertN4Function(phase, resource, exportableElement,
				"filterFunction", 2);

		astStructX.assertTypeVariables(phase, n4Function, resource, "T", "U");

		Type type = astStructX.assertReturnTypeRef(phase, n4Function, resource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, type, "/1/@types.8");

		Type firstParameterType = astStructX.assertParameter(phase, n4Function, resource, "input", false);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, firstParameterType, "/1/@types.8");

		Type secondParameterType = astStructX.assertParameter(phase, n4Function, resource, "hint", false);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, secondParameterType, "/1/@types.8");
	}

	public void assertSecondFunction(String phase, Resource resource, ExportableElement exportableElement) {
		FunctionDeclaration n4Function = astStructX.assertN4Function(phase, resource, exportableElement,
				"transformFunction", 0);

		astStructX.assertTypeVariables(phase, n4Function, resource);

		Assert.assertNull("no return type expected", n4Function.getDeclaredReturnTypeRefInAST());
	}
}
