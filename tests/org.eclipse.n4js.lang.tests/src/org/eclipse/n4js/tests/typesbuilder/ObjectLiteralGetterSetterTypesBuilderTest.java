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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ObjectLiteralGetterSetterTypesBuilderTest extends AbstractTypesBuilderTest {

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
		String textFileName = "ObjectLiteralGetterSetter.n4js";
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypesNamePairs = List.of(
				Pair.of(TVariable.class, "callee"));

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairsOnIndex = List
				.of(
						Pair.of(TModule.class, getQualifiedNamePrefix() + "ObjectLiteralGetterSetter"),
						Pair.of(TVariable.class, "callee"));
		int expectedTypesCount = expectedTypesNamePairs.size();
		int expectedExportedElementsCount = 1; // because of 1 variable statements
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

		TVariable firstVariable = typesStructX.assertTVariable(phase, newN4jsResource, 0, "callee", false);
		Type firstVariableType = typesStructX.assertTypeRef(phase, firstVariable, newN4jsResource);
		typesStructX.assertBuiltinTypeFragmentURI(phase, newN4jsResource, firstVariableType, "/1/@types.3"); // any
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		assertEquals(phase + ": AST and TModule as root", 2, resource.getContents().size());

		TModule tModule = (TModule) resource.getContents().get(1);
		assertEquals(phase + ": one internal type", 1, tModule.getExposedInternalTypes().size());

		Script script = astStructX.assertScript(phase, resource, 1);

		ExportableElement firstExportableElement = astStructX.assertExportDeclaration(phase, script, 0);
		VariableStatement firstVariableStatement = astStructX.assertVariableStatement(phase, firstExportableElement, 1);

		VariableDeclaration firstVariable = astStructX.assertVariable(phase, firstVariableStatement, "callee", false);
		astStructX.assertDefinedVariable(phase, firstVariable, resource, ObjectLiteral.class);

		ObjectLiteral objectLiteral = (ObjectLiteral) firstVariable.getExpression();

		assertEquals(phase + ": expected ObjectLiteral property assignment count", 7,
				objectLiteral.getPropertyAssignments().size());

		astStructX.assertPropertyNameValuePair(phase, objectLiteral, "a", PropertyNameKind.IDENTIFIER, "a",
				astStructX.stringLiteralValueCalculation(phase));
		astStructX.assertPropertyNameValuePair(phase, objectLiteral, "$data_property_b", PropertyNameKind.IDENTIFIER,
				"undefined", astStructX.identifierRefValueCalculation(phase));
		astStructX.assertPropertyNameValuePair(phase, objectLiteral, "c", PropertyNameKind.IDENTIFIER, "c",
				astStructX.stringLiteralValueCalculation(phase));
		astStructX.assertPropertyNameValuePair(phase, objectLiteral, "d", PropertyNameKind.IDENTIFIER, "d",
				astStructX.stringLiteralValueCalculation(phase));
		astStructX.assertPropertyNameValuePair(phase, objectLiteral, "$data_property_e", PropertyNameKind.IDENTIFIER,
				"undefined", astStructX.identifierRefValueCalculation(phase));

		astStructX.assertPropertyGetter(phase, objectLiteral, "b");
		astStructX.assertPropertySetter(phase, objectLiteral, "e", "newE");

		assertEquals(phase + ": still one internal types", 1, tModule.getExposedInternalTypes().size());

		assertTrue(phase + ": object literal should reference a TStructuralType",
				objectLiteral.getDefinedType() instanceof TStructuralType);
		TStructuralType tStructType = (TStructuralType) objectLiteral.getDefinedType();

		assertEquals(phase + ": TStructuralType is the one internal types", tStructType,
				head(tModule.getExposedInternalTypes()));

		assertEquals(phase + ": expected TStructuralType structural members count", 5,
				size(filter(tStructType.getOwnedMembers(), TStructField.class)));
		assertEquals(phase + ": expected TStructuralType getter count", 1,
				size(filter(tStructType.getOwnedMembers(), TStructGetter.class)));
		assertEquals(phase + ": expected TStructuralType setter count", 1,
				size(filter(tStructType.getOwnedMembers(), TStructSetter.class)));

		typesStructX.assertTStructField(phase, tStructType, "a", "/1/@types.4");
		typesStructX.assertTStructField(phase, tStructType, "$data_property_b", "/1/@types.6");
		typesStructX.assertTStructField(phase, tStructType, "c", "/1/@types.4");
		typesStructX.assertTStructField(phase, tStructType, "d", "/1/@types.4");
		typesStructX.assertTStructField(phase, tStructType, "$data_property_e", "/1/@types.6");

		typesStructX.assertTGetter(phase, tStructType, "b");
		typesStructX.assertTSetter(phase, tStructType, "e", "newE", "/1/@types.4");
	}
}
