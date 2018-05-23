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
package org.eclipse.n4js.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;

/**
 * A scope provider with special support for content assist, cf. {@link #getScopeForContentAssist(EObject, EReference)}.
 */
public interface IContentAssistScopeProvider extends IScopeProvider {

	/**
	 * Like {@link IScopeProvider#getScope(EObject, EReference)}, but with some special handling for content assist.
	 * This method will produce reasonable scopes for contexts / references that would be invalid in normal scoping. By
	 * supporting these cases in a separate method, the ordinary scoping need not be tampered with just for supporting
	 * content assist.
	 */
	public IScope getScopeForContentAssist(EObject context, EReference reference);
}
