/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.ts.typeRefs.Versionable
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TVersionable
import org.eclipse.xtext.resource.IEObjectDescription

/**
 * An {@link N4IDLVersionableFilter} can be used to filter versionable elements
 * according to the N4IDL versioning rules.
 *
 * The filter ignores any non-versionable elements and will never filter them out.
 */
class N4IDLVersionableFilter {
	private final int contextVersion;

	new(int contextVersion) {
		this.contextVersion = contextVersion;
	}

	/**
	 * Returns a filtered view of the given iterable that only contains the descriptions of elements such that either of the following conditions hold:
	 * <ul>
	 * <li>The element is not an instance of {@link TClassifier}, i.e., it is not versionable.</li>
	 * <li>The element is an instance of {@link TClassifier} and its version is contained in the requested version range and its version is maximal among
	 * all such elements.</li>
	 * </ul>
	 */
	public def Iterable<IEObjectDescription> filterElements(Iterable<IEObjectDescription> descriptions) {
		// If no matching versioned element was found, then maxVersion will be -1. In that case, all versioned elements
		// are filtered out in the lambda since no element has a version of -1.
		val int maxVersion = getMaxVersion(descriptions);

		val result = descriptions.filter [ d |
			val EObject o = d.EObjectOrProxy;
			return !(o instanceof TVersionable) || ((o as Versionable).version === maxVersion)
		]
		return result;
	}

	/**
	 * Returns the maximum version no of any versioned element described by the given descriptions that is contained
	 * in the requested version range. If the given iterable does not contain a description of a versioned element
	 * whose version is in the requested version range, then <code>-1</code> is returned.
	 */
	public def int getMaxVersion(Iterable<IEObjectDescription> descriptions) {
		val max = descriptions
			.filter[EObjectOrProxy instanceof TVersionable] // consider only versionable elements
			.map[EObjectOrProxy as TVersionable] // map to TVersionable
			.filter[return version <= contextVersion] // only consider elements up to the context version
			.fold(-1)[u, c| Integer.max(u, c.version)] // select the maximum version

		return max;
	}

	public def int getContextVersion() {
		return this.contextVersion;
	}

}
