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
package org.eclipse.n4js.tests.parser

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test

/**
 * Parser tests for N4 specific type expressions. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_02_1_1_IdentifierNamesAndIdentifiersTest extends AbstractParserTest {

	@Inject extension ParseHelper<Script>
	@Inject Provider<XtextResourceSet> resourceSetProvider;
	@Inject N4JSQualifiedNameProvider qualifiedNameProvider;
	@Inject IQualifiedNameConverter qualifiedNameConverter;

	private def withVendorAndProject(Script script, String vendorID, String projectID) {
		script.eResource.contents.get(1) as TModule => [
			it.projectID = projectID
			it.vendorID = vendorID
		]
		return script
	}

	@Test
	def void test_FQN_ModuleDotName() {
		val rs = resourceSetProvider.get();
		val s1= '''
			export public class X {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('V', 'B')

		var classX = s1.module.types.head as TClass;
		assertEquals("X", classX.name)
		val fqn = qualifiedNameProvider.getFullyQualifiedName(classX)
		assertEquals("A/!/X", qualifiedNameConverter.toString(fqn));
	}

	@Test
	def void test_FQN_WhenModuleNameIsEqualToClassName() {
		val rs = resourceSetProvider.get();
		val s1= '''
			export public class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('V', 'B')

		var classX = s1.module.types.head as TClass;
		assertEquals("A", classX.name)
		val fqn = qualifiedNameProvider.getFullyQualifiedName(classX)
		assertEquals("A/!/A", qualifiedNameConverter.toString(fqn));

	}
}
