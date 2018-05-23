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

/**
 */
public class ScopeRegistrar extends BuiltInSchemeRegistrar {

	@Override
	protected void register(ResourceSet resourceSet, ExecutionEnvironmentDescriptor descriptor) {
		super.register(resourceSet, descriptor);

		GlobalObjectScope globalObjectScope = new GlobalObjectScope(descriptor);
		GlobalObjectScopeAccess.registerGlobalObjectScope(globalObjectScope, resourceSet);

		VirtualBaseTypeScope virtualBaseTypeScope = new VirtualBaseTypeScope(descriptor);
		VirtualBaseTypeScopeAccess.registerVirtualBaseTypeScope(virtualBaseTypeScope, resourceSet);
	}

}
