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
package org.eclipse.n4js.environments;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.ui.resource.IResourceSetInitializer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;

/**
 * Bound via {@link ContributingModule}.
 */
@Singleton
public class BuiltInSchemeResourceSetInitializer implements IResourceSetInitializer, N4Scheme {

	@Inject
	private BuiltInSchemeRegistrar builtInSchemeRegistrar;

	@Override
	public void initialize(ResourceSet resourceSet, IProject project) {
		builtInSchemeRegistrar.registerScheme(resourceSet);
	}

}
