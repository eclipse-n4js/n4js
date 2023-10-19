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
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.types.TClass;
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
public class ClassWithSuperClassAndRolesTypesBuilderTest extends AbstractTypesBuilderTest {

	@Inject
	private ASTStructureAssertionExtension astStructX;

	@Inject
	private TypesStructureAssertionExtension typesStructX;

	@Override
	protected boolean enableUserDataCompare() {
		// to check the complete AST just change false to true
		// false
		return true;
	}

	@Test
	public void test() {
		String textFileName = "ClassWithSuperClassAndRoles.n4js";
		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypesNamePairs = List.of(
				Pair.of(TClass.class, "MyClass"),
				Pair.of(TInterface.class, "Persistable"),
				Pair.of(TInterface.class, "Comparable"),
				Pair.of(TClass.class, "MySubClass"));

		List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairsOnIndex = List
				.of(
						Pair.of(TModule.class, getQualifiedNamePrefix() + "ClassWithSuperClassAndRoles"),
						Pair.of(TClass.class, "MyClass"),
						Pair.of(TInterface.class, "Persistable"),
						Pair.of(TInterface.class, "Comparable"),
						Pair.of(TClass.class, "MySubClass"));
		int expectedTypesCount = expectedTypesNamePairs.size();
		int expectedExportedElementsCount = expectedExportedTypeToNamePairsOnIndex.size();
		executeTest(textFileName, expectedExportedTypeToNamePairsOnIndex, expectedTypesCount,
				expectedExportedElementsCount);
	}

	@Override
	public String getExpectedTypesSerialization() {
		return """
				TModule {
				    ref EObject astElement ref: Script@(unresolved proxy src/org/eclipse/n4js/tests/typesbuilder/ClassWithSuperClassAndRoles.n4js#/0)
				    attr EString astMD5 '22b4445f617bd45c1bcdfd6c6f2f23b1'
				    cref ExportDefinition exportDefinitions [
				        0: ElementExportDefinition {
				            ref TExportableElement exportedElement ref: TClass@(resource null)
				            attr EString exportedName 'MySubClass'
				        }
				    ]
				    attr EString moduleSpecifier 'org/eclipse/n4js/tests/typesbuilder/ClassWithSuperClassAndRoles'
				    attr EString packageName 'org.eclipse.n4js.lang.tests'
				    attr EString projectID 'org.eclipse.n4js.lang.tests'
				    attr EString qualifiedName 'org/eclipse/n4js/tests/typesbuilder/ClassWithSuperClassAndRoles'
				    attr EString simpleName 'ClassWithSuperClassAndRoles'
				    cref Type types [
				        0: TClass {
				            ref EObject astElement ref: N4ClassDeclaration@(unresolved proxy src/org/eclipse/n4js/tests/typesbuilder/ClassWithSuperClassAndRoles.n4js#/0/@scriptElements.0)
				            attr EString name 'MyClass'
				        }
				        1: TInterface {
				            ref EObject astElement ref: N4InterfaceDeclaration@(unresolved proxy src/org/eclipse/n4js/tests/typesbuilder/ClassWithSuperClassAndRoles.n4js#/0/@scriptElements.1)
				            attr EString name 'Persistable'
				        }
				        2: TInterface {
				            ref EObject astElement ref: N4InterfaceDeclaration@(unresolved proxy src/org/eclipse/n4js/tests/typesbuilder/ClassWithSuperClassAndRoles.n4js#/0/@scriptElements.2)
				            attr EString name 'Comparable'
				        }
				        3: TClass {
				            ref EObject astElement ref: N4ClassDeclaration@(unresolved proxy src/org/eclipse/n4js/tests/typesbuilder/ClassWithSuperClassAndRoles.n4js#/0/@scriptElements.3/@exportedElement)
				            attr TypeAccessModifier declaredTypeAccessModifier 'publicInternal'
				            attr EBoolean directlyExported 'true'
				            ref ElementExportDefinition exportingExportDefinitions [
				                0: ElementExportDefinition@(resource null)
				            ]
				            cref ParameterizedTypeRef implementedInterfaceRefs [
				                0: ParameterizedTypeRef {
				                    ref Type declaredType ref: TInterface@(resource null)
				                }
				                1: ParameterizedTypeRef {
				                    ref Type declaredType ref: TInterface@(resource null)
				                }
				            ]
				            attr EString name 'MySubClass'
				            cref ParameterizedTypeRef superClassRef ParameterizedTypeRef {
				                ref Type declaredType ref: TClass@(resource null)
				            }
				        }
				    ]
				    attr EString vendorID 'org.eclipse.n4js'
				}""";
	}

	@Override
	public void assertExampleTypeStructure(String phase, Resource newN4jsResource) {
		assertEquals("AST and TModule as root", 2, newN4jsResource.getContents().size());

		TClass tClass = typesStructX.assertTClass(phase, newN4jsResource, "MySubClass", 0);

		// TODO to be supported in the next Sprint
		// assertAnnotations(phase, tClass, newN4jsResource, )

		typesStructX.assertAccessModifier(phase, tClass, newN4jsResource, TypeAccessModifier.PUBLIC_INTERNAL);

		typesStructX.assertSuperClass(phase, tClass, newN4jsResource, "MyClass");

		typesStructX.assertConsumedRoles(phase, tClass, newN4jsResource, "Persistable", "Comparable");
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.getContents().size());

		Script script = astStructX.assertScript(phase, resource, 4);

		ExportableElement exportedElement = astStructX.assertExportDeclaration(phase, script, 3);

		N4ClassDeclaration n4Class = astStructX.assertN4ClassDeclaration(phase, exportedElement, "MySubClass", 0);

		astStructX.assertAnnotations(phase, n4Class, resource, "Internal");

		astStructX.assertDeclaredAccessModifier(phase, n4Class, resource, N4Modifier.PUBLIC);

		astStructX.assertSuperClass(phase, n4Class, resource, "MyClass");

		astStructX.assertConsumedRoles(phase, n4Class, resource, "Persistable", "Comparable");
	}
}
