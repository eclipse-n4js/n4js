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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ClassWithMethodsTypesBuilderTest extends AbstractTypesBuilderTest {

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
		String textFileName = "ClassWithMethods.n4js";
		List<?> expectedTypesNamePairs = List.of(
				Pair.of(TClass.class, "MySuperClass"),
				Pair.of(TClass.class, "MyInterface"),
				Pair.of(TEnum.class, "Storage"),
				Pair.of(TClass.class, "MyClass"));

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairsOnIndex = List
				.of(
						Pair.of(TModule.class, getQualifiedNamePrefix() + "ClassWithMethods"),
						Pair.of(TClass.class, "MySuperClass"),
						Pair.of(TInterface.class, "MyInterface"),
						Pair.of(TEnum.class, "Storage"),
						Pair.of(TClass.class, "MyClass"));
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

		TClass tClass = typesStructX.assertTClass(phase, newN4jsResource, "MyClass", 4);

		assertFirstTField(phase, tClass, newN4jsResource);

		assertFirstTMethod(phase, tClass, newN4jsResource);

		assertSecondTMethod(phase, tClass, newN4jsResource);

		assertThirdTMethod(phase, tClass, newN4jsResource);
	}

	private void assertFirstTField(String phase, TClass tClass, Resource newN4jsResource) {
		TField tField = typesStructX.assertTField(phase, tClass, "instanceCounter");

		Type type = typesStructX.assertTypeRefOfTField(phase, tField, newN4jsResource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, "/1/@types.12");
	}

	private void assertFirstTMethod(String phase, TClass tClass, Resource newN4jsResource) {
		TMethod tMethod = typesStructX.assertTMethod(phase, tClass, "myMethod1", 0);

		Type type = typesStructX.assertReturnTypeRef(phase, tMethod, newN4jsResource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, "/1/@types.7");
	}

	private void assertSecondTMethod(String phase, TClass tClass, Resource newN4jsResource) {
		TMethod tMethod = typesStructX.assertTMethod(phase, tClass, "myMethod2", 1);

		Type returnType = typesStructX.assertReturnTypeRef(phase, tMethod, newN4jsResource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, returnType, "/1/@types.6");

		Type parameterType = typesStructX.assertParameter(phase, tMethod, newN4jsResource, "input", false);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, parameterType, "/1/@types.6");
	}

	private void assertThirdTMethod(String phase, TClass tClass, Resource newN4jsResource) {
		TMethod tMethod = typesStructX.assertTMethod(phase, tClass, "secretMethod", 2);

		Type type = typesStructX.assertReturnTypeRef(phase, tMethod, newN4jsResource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, "/1/@types.6");

		Type firstParameterType = typesStructX.assertParameter(phase, tMethod, newN4jsResource, "element", false);

		typesStructX.assertTInterface(phase, newN4jsResource, firstParameterType, "MyInterface", 0);

		Type secondParameterType = typesStructX.assertParameter(phase, tMethod, newN4jsResource, "storages", true);

		typesStructX.assertTEnum(phase, secondParameterType, "Storage", "LITERAL");
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.getContents().size());

		Script script = astStructX.assertScript(phase, resource, 4);

		ExportableElement exportableElement = astStructX.assertExportDeclaration(phase, script, 3);

		N4ClassDeclaration n4Class = astStructX.assertN4ClassDeclaration(phase, exportableElement, "MyClass", 4);

		Type type = assertFirstN4Field(phase, n4Class, resource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, type, "/1/@types.12");

		assertFirstN4Method(phase, n4Class, resource);

		assertSecondN4Method(phase, n4Class, resource);

		assertThirdN4Method(phase, n4Class, resource);
	}

	private Type assertFirstN4Field(String phase, N4ClassDeclaration n4Class, Resource resource) {
		N4FieldDeclaration field = astStructX.assertN4FieldDeclaration(phase, n4Class, "instanceCounter");

		return typesStructX.assertTypeRef(phase, field, resource);
	}

	private void assertFirstN4Method(String phase, N4ClassDeclaration n4Class, Resource resource) {
		N4MethodDeclaration n4Method = astStructX.assertN4Method(phase, n4Class, "myMethod1", 0);

		Type type = astStructX.assertReturnTypeRef(phase, n4Method, resource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, type, "/1/@types.7");
	}

	private void assertSecondN4Method(String phase, N4ClassDeclaration n4Class, Resource resource) {
		N4MethodDeclaration n4Method = astStructX.assertN4Method(phase, n4Class, "myMethod2", 1);

		Type returnType = astStructX.assertReturnTypeRef(phase, n4Method, resource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, returnType, "/1/@types.6");

		Type parameterType = astStructX.assertParameter(phase, n4Method, resource, "input", false);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, parameterType, "/1/@types.6");
	}

	private void assertThirdN4Method(String phase, N4ClassDeclaration n4Class, Resource resource) {
		N4MethodDeclaration n4Method = astStructX.assertN4Method(phase, n4Class, "secretMethod", 2);

		Type type = astStructX.assertReturnTypeRef(phase, n4Method, resource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, type, "/1/@types.6");

		Type firstParameterType = astStructX.assertParameter(phase, n4Method, resource, "element", false);

		typesStructX.assertTInterface(phase, resource, firstParameterType, "MyInterface", 0);

		Type secondParameterType = astStructX.assertParameter(phase, n4Method, resource, "storages", true);

		typesStructX.assertTEnum(phase, secondParameterType, "Storage", "LITERAL");
	}
}
