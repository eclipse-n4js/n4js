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
package org.eclipse.n4js.internal;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.eclipse.n4js.projectModel.IExternalPackageManager;
import org.eclipse.n4js.n4mf.ProjectDescription;

/**
 */
@Singleton
public class FileBasedExternalPackageManager implements IExternalPackageManager {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Override
	public ProjectDescription loadManifest(URI manifest) {
		ResourceSet resourceSet = resourceSetProvider.get();
		Resource resource = resourceSet.getResource(manifest, true);
		List<EObject> contents = resource.getContents();
		if (contents.isEmpty() || !(contents.get(0) instanceof ProjectDescription)) {
			return null;
		}
		// do some error handling:
		if (!resource.getErrors().isEmpty()) {
			throw new N4JSBrokenProjectException("Reading project description from "
					+ manifest
					+ " raised the following errors: "
					+ Joiner.on('\n').join(
							resource.getErrors().stream().map(
									error -> error.getMessage() + " at line " + error.getLine())
									.iterator()));
		}
		ProjectDescription result = (ProjectDescription) contents.get(0);
		contents.clear();
		return result;
	}

}
