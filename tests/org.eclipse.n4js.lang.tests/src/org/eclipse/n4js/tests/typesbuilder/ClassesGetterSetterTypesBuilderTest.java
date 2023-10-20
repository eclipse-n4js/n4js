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
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ClassesGetterSetterTypesBuilderTest extends AbstractTypesBuilderTest {

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
		String textFileName = "ClassesGetterSetter.n4js";
		List<Pair<Class<?>, String>> expectedTypesNamePairs = List.of(
				Pair.of(TClass.class, "Callee"));

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairsOnIndex = List
				.of(
						Pair.of(TModule.class, getQualifiedNamePrefix() + "ClassesGetterSetter"),
						Pair.of(TClass.class, "Callee"));
		int expectedTypesCount = expectedTypesNamePairs.size();
		int expectedExportedElementsCount = 3; // because of 3 variable statements
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

		TClass tClass = typesStructX.assertTClass(phase, newN4jsResource, "Callee", 8);

		assertTField(phase, tClass, newN4jsResource, "a", "/1/@types.4");
		assertTField(phase, tClass, newN4jsResource, "data_property_b", "/1/@types.4");
		assertTField(phase, tClass, newN4jsResource, "data_property_c", "/1/@types.4");
		assertTField(phase, tClass, newN4jsResource, "data_property_d", "/1/@types.4");

		assertTGetter(phase, tClass, newN4jsResource, "b", "/1/@types.4");
		assertTGetter(phase, tClass, newN4jsResource, "d", "/1/@types.4");

		assertTSetter(phase, tClass, newN4jsResource, "c", "newC", "/1/@types.4");
		assertTSetter(phase, tClass, newN4jsResource, "d", "newD", "/1/@types.8");
	}

	private void assertTField(String phase, TClass tClass, Resource newN4jsResource, String expectedName,
			String expectedTypeFragmentURI) {
		TField tField = typesStructX.assertTField(phase, tClass, expectedName);

		Type type = typesStructX.assertTypeRefOfTField(phase, tField, newN4jsResource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, expectedTypeFragmentURI);
	}

	private void assertTGetter(String phase, TClass tClass, Resource newN4jsResource, String expectedName,
			String expectedReturnTypeFragmentURI) {
		TGetter tGetter = typesStructX.assertTGetter(phase, tClass, expectedName);

		Type type = astStructX.assertReturnTypeRef(phase, tGetter, newN4jsResource);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, expectedReturnTypeFragmentURI);
	}

	private void assertTSetter(String phase, TClass tClass, Resource newN4jsResource, String expectedName,
			String expectedParameterName, String expectedReturnTypeFragmentURI) {
		TSetter tSetter = typesStructX.assertTSetter(phase, tClass, expectedName);

		Type parameterType = typesStructX.assertParameter(phase, tSetter, newN4jsResource, expectedParameterName,
				false);

		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, parameterType, expectedReturnTypeFragmentURI);
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.getContents().size());

		Script script = astStructX.assertScript(phase, resource, 1);

		ExportableElement exportableElement = astStructX.assertExportDeclaration(phase, script, 0);
		N4ClassDeclaration n4Class = astStructX.assertN4ClassDeclaration(phase, exportableElement, "Callee", 8);

		assertN4Field(phase, n4Class, resource, "a", "/1/@types.4");
		assertN4Field(phase, n4Class, resource, "data_property_b", "/1/@types.4");
		assertN4Field(phase, n4Class, resource, "data_property_c", "/1/@types.4");
		assertN4Field(phase, n4Class, resource, "data_property_d", "/1/@types.4");

		assertN4Getter(phase, n4Class, resource, "b", "/1/@types.4");
		assertN4Getter(phase, n4Class, resource, "d", "/1/@types.4");

		assertN4Setter(phase, n4Class, resource, "c", "newC", "/1/@types.4");
		assertN4Setter(phase, n4Class, resource, "d", "newD", "/1/@types.8");
	}

	private void assertN4Field(String phase, N4ClassDeclaration n4Class, Resource resource, String expectedName,
			String expectedTypeFragmentURI) {
		N4FieldDeclaration field = astStructX.assertN4FieldDeclaration(phase, n4Class, expectedName);
		Type type = typesStructX.assertTypeRef(phase, field, resource);
		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, type, expectedTypeFragmentURI);
	}

	private void assertN4Getter(String phase, N4ClassDeclaration n4Class, Resource resource, String expectedName,
			String expectedReturnTypeFragmentURI) {
		N4GetterDeclaration n4Getter = astStructX.assertN4Getter(phase, n4Class, expectedName);

		assertTrue("Should have parameterized type ref",
				n4Getter.getDeclaredTypeRefInAST() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) n4Getter.getDeclaredTypeRefInAST();
		assertEquals(phase + ": expected resource content size", 2, resource.getContents().size());
		// test whether reference can be resolved
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, type, expectedReturnTypeFragmentURI);
	}

	private void assertN4Setter(String phase, N4ClassDeclaration n4Class, Resource resource, String expectedName,
			String expectedParameterName, String expectedReturnTypeFragmentURI) {
		N4SetterDeclaration n4Setter = astStructX.assertN4Setter(phase, n4Class, expectedName);
		FormalParameter parameter = n4Setter.getFpar();
		assertEquals(expectedParameterName, parameter.getName());
		assertEquals(phase + ": Should have the expected variadic setup", false, parameter.isVariadic());
		assertTrue("Should have parameterized type ref",
				parameter.getDeclaredTypeRefInAST() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) parameter.getDeclaredTypeRefInAST();
		assertEquals(phase + ": expected resource content size", 2, resource.getContents().size());
		Type type = parameterizedTypeRef.getDeclaredType();
		assertNotNull(phase + ": declaredType should not be null", type);

		typesStructX.assertBuiltinTypeFragmentURI(phase, resource, type, expectedReturnTypeFragmentURI);
	}
}
