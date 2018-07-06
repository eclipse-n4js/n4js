/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.util;

import java.io.IOException;
import java.util.function.Consumer;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Injector;

/**
 * Test utility methods for creating and modifying N4JS package.json files in terms of its {@link JSONPackage} model
 * representation.
 */
public class PackageJSONTestHelper {
	/**
	 * Loads the given project description file (using {@link Resource}) and applies
	 * {@code projectDescriptionAdjustments} to the {@link JSONObject} to be found in the root of the given
	 * {@code package.json} project description file.
	 *
	 * After the adjustments have been applied, the resource is saved.
	 */
	public void updateProjectDescription(IFile projectDescriptionFile,
			Consumer<JSONObject> projectDescriptionAdjustments) throws IOException {
		final URI uri = URI.createPlatformResourceURI(projectDescriptionFile.getFullPath().toString(), true);
		final ResourceSet resourceSet = getInjector().getInstance(IResourceSetProvider.class)
				.get(projectDescriptionFile.getProject());
		final Resource resource = resourceSet.getResource(uri, true);

		final JSONObject root = PackageJSONTestUtils.getPackageJSONRoot(resource);
		projectDescriptionAdjustments.accept(root);
		resource.save(null);
	}

	/** Returns the UI injector of the N4JS language */
	private Injector getInjector() {
		final Injector injector = N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
		return injector;
	}

}
