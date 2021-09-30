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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Predicate;

/**
 */
public class BuiltInTypesGlobalScopeProvider extends DefaultN4GlobalScopeProvider {

	@Override
	protected IScope getScope(IScope parent, final Resource context, boolean ignoreCase, EClass type,
			Predicate<IEObjectDescription> filter) {
		// we are only interested in the builtin types
		// and don't care about other types from the index / available on the classpath
		return parent;
	}

}
