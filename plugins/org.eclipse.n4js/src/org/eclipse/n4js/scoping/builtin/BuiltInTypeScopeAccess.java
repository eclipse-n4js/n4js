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
 * Facade to retrieve BuiltInTypeScope for a given resource set.
 */
public class BuiltInTypeScopeAccess extends AdapterImpl {

	private final BuiltInTypeScope scope;

	/**
	 * Assign the given scope to the given resource set by means of an Adapter.
	 */
	public static void registerBuiltInTypeScope(BuiltInTypeScope scope, ResourceSet context) {
		if (EcoreUtil.getAdapter(context.eAdapters(), BuiltInTypeScope.class) != null) {
			throw new IllegalStateException("Attempt to install adapter for BuiltInTypeScope twice");
		}
		BuiltInTypeScopeAccess adapter = new BuiltInTypeScopeAccess(scope);
		context.eAdapters().add(adapter);
	}

	BuiltInTypeScopeAccess(BuiltInTypeScope scope) {
		this.scope = scope;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return BuiltInTypeScope.class.equals(type);
	}

	BuiltInTypeScope getScope() {
		return scope;
	}

}
