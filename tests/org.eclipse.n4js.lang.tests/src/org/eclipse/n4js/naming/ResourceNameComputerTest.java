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
package org.eclipse.n4js.naming;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSInjectorProviderWithMockProject;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 */
@InjectWith(N4JSInjectorProviderWithMockProject.class)
@RunWith(XtextRunner.class)
public class ResourceNameComputerTest {

	@Inject
	ParseHelper<Script> parserHelper;
	private Script script;
	private TModule module;
	private Type type;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@SuppressWarnings("javadoc")
	@Before
	public void prepare() throws Exception {
		URI fileUri = URIUtils.toFileUri("p/C.n4js");
		this.script = parserHelper.parse("class C{}", fileUri, resourceSetProvider.get());
		this.module = script.getModule();
		this.type = module.getTopLevelTypes().get(0);
	}

	/**
	 * Test method for {@link ResourceNameComputer#getSimpleTypeName(org.eclipse.n4js.ts.types.Type)}.
	 */
	@Test
	public void testGetSimpleTypeName() {
		assertEquals("C", resourceNameComputer.getSimpleTypeName(type));
	}

	/**
	 * Test method for {@link ResourceNameComputer#getFullyQualifiedTypeName(org.eclipse.n4js.ts.types.Type)} .
	 */
	@Test
	public void testGetQualifiedTypeName() {
		assertEquals("p/C/C", resourceNameComputer.getFullyQualifiedTypeName(type));
	}

	/**
	 * Test method for {@link ResourceNameComputer#getCompleteModuleSpecifier(org.eclipse.n4js.ts.types.TModule)} .
	 */
	@Test
	public void testGetCompleteModuleSpecifier() {
		assertEquals("p/C", resourceNameComputer.getCompleteModuleSpecifier(module));
	}
}
