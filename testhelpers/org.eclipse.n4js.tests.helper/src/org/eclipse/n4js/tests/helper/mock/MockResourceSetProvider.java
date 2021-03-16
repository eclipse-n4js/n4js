/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.helper.mock;

import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.n4js.ts.scoping.builtin.ConfiguredResourceSetProvider;
import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInSchemeProvider;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigAccess;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Makes the {@link MockWorkspaceSupplier mock workspace configuration} available in resource sets created via an
 * ordinary <code>Provider&lt;XtextResourceSet></code>
 */
@Singleton
public class MockResourceSetProvider extends ConfiguredResourceSetProvider {

	@Inject
	private MockWorkspaceSupplier mockWorkspaceSupplier;

	/** Creates a new {@link MockResourceSetProvider}. */
	@Inject
	public MockResourceSetProvider(BuiltInSchemeRegistrar builtInSchemeRegistrar, ClassLoader classLoader,
			ResourceSetWithBuiltInSchemeProvider builtInProvider, UriExtensions uriExtensions) {
		super(builtInSchemeRegistrar, classLoader, builtInProvider, uriExtensions);
	}

	@Override
	public SynchronizedXtextResourceSet get() {
		SynchronizedXtextResourceSet result = super.get();
		WorkspaceConfigAccess.setWorkspaceConfig(result, mockWorkspaceSupplier.getMockWorkspaceConfig());
		return result;
	}

}
