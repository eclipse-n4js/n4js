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
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class RoleWithRoleAndInterfaceTypesBuilderTest extends AbstractTypesBuilderTest {

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
		String textFileName = "RoleWithRoleAndInterface.n4js";
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypesNamePairs = List.of(
				Pair.of(TInterface.class, "Loadable"),
				Pair.of(TInterface.class, "MyInterface"),
				Pair.of(TEnum.class, "StorageType"),
				Pair.of(TClass.class, "Storage"),
				Pair.of(TInterface.class, "Persistable"));

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairsOnIndex = List
				.of(
						Pair.of(TModule.class, getQualifiedNamePrefix() + "RoleWithRoleAndInterface"),
						Pair.of(TInterface.class, "Loadable"),
						Pair.of(TInterface.class, "MyInterface"),
						Pair.of(TEnum.class, "StorageType"),
						Pair.of(TClass.class, "Storage"),
						Pair.of(TInterface.class, "Persistable"));
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
		TInterface role = typesStructX.assertTRole(phase, newN4jsResource, "Persistable", 2);
		typesStructX.assertAccessModifier(phase, role, newN4jsResource, TypeAccessModifier.PUBLIC);
		typesStructX.assertImplementedInterfaces(phase, role, newN4jsResource, "Loadable", "MyInterface");
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.getContents().size());
		Script script = astStructX.assertScript(phase, resource, 5);
		ExportableElement exportableElement = astStructX.assertExportDeclaration(phase, script, 4);
		N4InterfaceDeclaration role = astStructX.assertN4RoleDeclaration(phase, exportableElement, "Persistable", 2);
		astStructX.assertDeclaredAccessModifier(phase, role, resource, N4Modifier.PUBLIC);
		astStructX.assertImplementedInterfaces(phase, role, resource, "Loadable", "MyInterface");
	}

}
