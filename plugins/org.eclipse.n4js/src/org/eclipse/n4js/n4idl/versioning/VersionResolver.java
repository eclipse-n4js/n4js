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
package org.eclipse.n4js.n4idl.versioning;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;

/**
 * Strategy for resolving the actual version of a type referenced by a given type reference.
 */
public interface VersionResolver {
	/**
	 * Returns a type reference referencing the requested version of the type referenced by the given type reference.
	 *
	 * @param typeRef
	 *            the type reference to resolve
	 * @param versionedReference
	 *            the versioned reference to obtain the context version from
	 * @return a reference to the resolved version of the type
	 */
	public <T extends TypeArgument, S> T resolveVersion(T typeRef, S versionedReference);

	/**
	 * Returns a type reference referencing the requested version of the type referenced by the given type reference.
	 * May return the given type argument or a different instance that is assignable to <code>T</code>. Type arguments
	 * are also allowed to allow easier use of this method.
	 *
	 * @param typeRef
	 *            the type reference to resolve
	 * @param contextVersion
	 *            the context version to use when determining the actual version of the type referenced by the actual
	 *            parameter
	 * @return a reference to the resolved version of the type
	 */
	public <T extends TypeArgument> T resolveVersion(T typeRef, int contextVersion);
}
