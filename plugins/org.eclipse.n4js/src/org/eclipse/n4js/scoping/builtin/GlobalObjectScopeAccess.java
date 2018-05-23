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

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Encapsulates the logic how the global object scope is stored on the resource set as an adapter.
 */
public class GlobalObjectScopeAccess extends AdapterImpl {

	private final GlobalObjectScope scope;

	/**
	 * Registers an instance of the {@link GlobalObjectScope} for the given context {@link ResourceSet}.
	 */
	public static void registerGlobalObjectScope(GlobalObjectScope scope, ResourceSet context) {
		if (EcoreUtil.getAdapter(context.eAdapters(), GlobalObjectScope.class) != null) {
			throw new IllegalStateException("Attempt to install adapter for GlobalObjectScope twice");
		}
		GlobalObjectScopeAccess adapter = new GlobalObjectScopeAccess(scope);
		context.eAdapters().add(adapter);
	}

	/**
	 * Non-public constructor for the adapter.
	 */
	GlobalObjectScopeAccess(GlobalObjectScope scope) {
		this.scope = scope;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return GlobalObjectScope.class.equals(type);
	}

	GlobalObjectScope getScope() {
		return scope;
	}

}
