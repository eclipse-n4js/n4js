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
package org.eclipse.n4js.ui.scoping.builtin;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.scoping.builtin.GlobalObjectScope;
import org.eclipse.n4js.scoping.builtin.GlobalObjectScopeAccess;
import org.eclipse.n4js.scoping.builtin.VirtualBaseTypeScope;
import org.eclipse.n4js.scoping.builtin.VirtualBaseTypeScopeAccess;
import org.eclipse.n4js.ts.scoping.builtin.ExecutionEnvironmentDescriptor;
import org.eclipse.xtext.ui.resource.IResourceSetInitializer;

import com.google.inject.Singleton;

/**
 */
@Singleton
public class ScopeInitializer implements IResourceSetInitializer {

	@Override
	public void initialize(ResourceSet resourceSet, IProject project) {
		ExecutionEnvironmentDescriptor descriptor = new ExecutionEnvironmentDescriptor(resourceSet);
		GlobalObjectScope globalObjectScope = new GlobalObjectScope(descriptor);
		GlobalObjectScopeAccess.registerGlobalObjectScope(globalObjectScope, resourceSet);

		VirtualBaseTypeScope virtualBaseTypeScope = new VirtualBaseTypeScope(descriptor);
		VirtualBaseTypeScopeAccess.registerVirtualBaseTypeScope(virtualBaseTypeScope, resourceSet);
	}
}
