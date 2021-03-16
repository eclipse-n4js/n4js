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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.testing.util.ResourceHelper;

import com.google.inject.Inject;

/**
 * Ensures that {@link ResourceHelper#computeUnusedUri(ResourceSet) auto-generated URIs} are properly contained in the
 * {@link MockWorkspaceSupplier#getMockProjectConfig() mocked test project}.
 */
public class MockResourceHelper extends ResourceHelper {

	@Inject
	private MockWorkspaceSupplier mockWorkspaceSupplier;

	@Override
	protected URI computeUnusedUri(ResourceSet resourceSet) {
		URI result = super.computeUnusedUri(resourceSet);
		if (result.isRelative()) {
			result = mockWorkspaceSupplier.toTestProjectURI(result.path());
		}
		return result;
	}
}
