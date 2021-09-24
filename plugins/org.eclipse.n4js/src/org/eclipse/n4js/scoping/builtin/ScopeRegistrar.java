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
package org.eclipse.n4js.scoping.builtin;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.n4js.ts.scoping.builtin.ExecutionEnvironmentDescriptor;

import com.google.inject.Singleton;

/**
 */
@Singleton
public class ScopeRegistrar extends BuiltInSchemeRegistrar {

	/**
	 * Cache the scopes on the target resource set based on the shared scopes of the builtInSchemeResourceSet.
	 */
	@Override
	public void registerScopes(ResourceSet targetResourceSet, ResourceSet builtInSchemeResourceSet) {
		super.registerScopes(targetResourceSet, builtInSchemeResourceSet);

		GlobalObjectScopeAccess.registerGlobalObjectScope(
				() -> GlobalObjectScope.get(builtInSchemeResourceSet), targetResourceSet);

		VirtualBaseTypeScopeAccess.registerVirtualBaseTypeScope(
				() -> VirtualBaseTypeScope.get(builtInSchemeResourceSet), targetResourceSet);
	}

	@Override
	protected void register(ResourceSet resourceSet, ExecutionEnvironmentDescriptor descriptor) {
		super.register(resourceSet, descriptor);

		GlobalObjectScopeAccess.registerGlobalObjectScope(
				() -> new GlobalObjectScope(descriptor), resourceSet);

		VirtualBaseTypeScopeAccess.registerVirtualBaseTypeScope(
				() -> new VirtualBaseTypeScope(descriptor), resourceSet);
	}

}
