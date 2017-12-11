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
package org.eclipse.n4js.n4idl.scoping;

import org.eclipse.xtext.scoping.IScope;

import org.eclipse.n4js.ts.types.TClassifier;

/**
 * Provides scopes that can be used to find different versions of versionable objects.
 */
public interface VersionScopeProvider {
	/**
	 * Returns a scope that can be used to find all versions of the given classifier.
	 *
	 * @param classifier
	 *            the classifier
	 * @return the scope
	 */
	public IScope getVersionScope(TClassifier classifier);
}
