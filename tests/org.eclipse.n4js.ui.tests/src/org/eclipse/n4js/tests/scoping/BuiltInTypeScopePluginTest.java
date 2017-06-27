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
package org.eclipse.n4js.tests.scoping;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;

/**
 */
@InjectWith(N4JSUiInjectorProvider.class)
@RunWith(XtextRunner.class)
public class BuiltInTypeScopePluginTest {
	@Inject
	IResourceSetProvider resourceSetProvider;

	@SuppressWarnings("javadoc")
	@Test
	public void testLoadingBuiltInTypes() {
		XtextResourceSet resourceSet = (XtextResourceSet) resourceSetProvider.get(null);
		resourceSet.setClasspathURIContext(N4JSResource.class.getClassLoader());
		BuiltInTypeScope scope = BuiltInTypeScope.get(resourceSet);
		IEObjectDescription anyType = scope.getSingleElement(QualifiedName.create("any"));
		Assert.assertNotNull(anyType);
	}
}
