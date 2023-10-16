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
package org.eclipse.n4js.tests.parser;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Parser tests for N4 specific type expressions. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class N4_02_1_1_IdentifierNamesAndIdentifiersTest extends AbstractParserTest {

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	N4JSQualifiedNameProvider qualifiedNameProvider;
	@Inject
	IQualifiedNameConverter qualifiedNameConverter;

	private Script withVendorAndProject(Script script, String projectID) {
		TModule module = (TModule) script.eResource().getContents().get(1);
		module.setProjectID(projectID);
		module.setVendorID(projectID);
		return script;
	}

	@Test
	public void test_FQN_ModuleDotName() throws Exception {
		ResourceSet rs = resourceSetProvider.get();
		Script s1 = withVendorAndProject(parseHelper.parse("""
					export public class X {}
				""", URI.createURI("A.n4js"), rs), "B");

		TClass classX = (TClass) s1.getModule().getTypes().get(0);
		assertEquals("X", classX.getName());
		QualifiedName fqn = qualifiedNameProvider.getFullyQualifiedName(classX);
		assertEquals("A/!/X", qualifiedNameConverter.toString(fqn));
	}

	@Test
	public void test_FQN_WhenModuleNameIsEqualToClassName() throws Exception {
		ResourceSet rs = resourceSetProvider.get();
		Script s1 = withVendorAndProject(parseHelper.parse("""
					export public class A {}
				""", URI.createURI("A.n4js"), rs), "B");

		TClass classX = (TClass) s1.getModule().getTypes().get(0);
		assertEquals("A", classX.getName());
		QualifiedName fqn = qualifiedNameProvider.getFullyQualifiedName(classX);
		assertEquals("A/!/A", qualifiedNameConverter.toString(fqn));

	}
}
