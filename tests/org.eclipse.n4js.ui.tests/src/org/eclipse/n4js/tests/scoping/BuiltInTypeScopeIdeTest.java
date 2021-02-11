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

import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 */
// converted from BuiltInTypeScopePluginTest
public class BuiltInTypeScopeIdeTest extends ConvertedIdeTest {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@SuppressWarnings("javadoc")
	@Test
	public void testLoadingBuiltInTypes() {
		// we only create an empty project and start the server to ensure that we test with a "realistic" setup
		testWorkspaceManager.createTestProjectOnDisk();
		startAndWaitForLspServer();
		assertNoIssues();

		XtextResourceSet resourceSet = resourceSetProvider.get();
		BuiltInTypeScope scope = BuiltInTypeScope.get(resourceSet);
		IEObjectDescription anyType = scope.getSingleElement(QualifiedName.create("any"));
		Assert.assertNotNull(anyType);
	}
}
