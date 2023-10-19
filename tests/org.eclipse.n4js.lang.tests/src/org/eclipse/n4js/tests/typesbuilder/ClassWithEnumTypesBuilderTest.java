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
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TField;
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
public class ClassWithEnumTypesBuilderTest extends AbstractTypesBuilderTest {
	@Inject
	private TypesStructureAssertionExtension typesStructX;

	@Inject
	private ASTStructureAssertionExtension astStructX;

	@Override
	protected boolean enableUserDataCompare() {
		// to check the complete AST just change false to true
		return false;
		// true
	}

	@Test
	public void test() {
		String textFileName = "ClassWithEnum.n4js";
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs = List.of(
				Pair.of(TModule.class, getQualifiedNamePrefix() + "ClassWithEnum"),
				Pair.of(TEnum.class, "StorageType"),
				Pair.of(TClass.class, "Storage"));
		int expectedTypesCount = 2;
		int expectedExportedElementsCount = expectedExportedTypeToNamePairs.size();// one function is not exported
		executeTest(textFileName, expectedExportedTypeToNamePairs, expectedTypesCount, expectedExportedElementsCount);
	}

	@Override
	public String getExpectedTypesSerialization() {
		return """
				TModule {
				}
				""";
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.getContents().size());

		Script script = astStructX.assertScript(phase, resource, 2);

		ExportableElement exportedElement = astStructX.assertExportDeclaration(phase, script, 1);

		N4ClassDeclaration n4Class = astStructX.assertN4ClassDeclaration(phase, exportedElement, "Storage", 1);

		N4FieldDeclaration n4ClassField = astStructX.assertN4FieldDeclaration(phase, n4Class, "type");

		Type enumType = typesStructX.assertTypeRef(phase, n4ClassField, resource);

		typesStructX.assertTypeFragmentURI(phase, resource, enumType, "/1/@types.0");

		typesStructX.assertTEnum(phase, enumType, "StorageType", "FILESYSTEM", "DATABASE");
	}

	@Override
	public void assertExampleTypeStructure(String phase, Resource newN4jsResource) {
		assertEquals("AST and TModule as root", 2, newN4jsResource.getContents().size());

		TClass storageTClass = typesStructX.assertTClass(phase, newN4jsResource, "Storage", 1);

		TField tField = typesStructX.assertTField(phase, storageTClass, "type");

		Type enumType = typesStructX.assertTypeRefOfTField(phase, tField, newN4jsResource);

		typesStructX.assertTypeFragmentURI(phase, newN4jsResource, enumType, "/1/@types.0");

		typesStructX.assertTEnum(phase, enumType, "StorageType", "FILESYSTEM", "DATABASE");
	}
}
