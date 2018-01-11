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
package org.eclipse.n4js.n4idl.versioning;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.VersionedElement;
import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.types.TVersionable;

/**
 * Static utility class with regard to versioned elements.
 */
public class VersionUtils {
	/**
	 * Returns {@code true} if the given {@link VersionedElement} is considered to be versioned.
	 *
	 * A return value of {@code true} indicates a non-null value for the field
	 * {@link VersionedElement#getDeclaredVersion()}.
	 */
	public static boolean isVersioned(EObject element) {
		return (element instanceof VersionedElement) &&
				((VersionedElement) element).getDeclaredVersion() != null &&
				((VersionedElement) element).getDeclaredVersion().intValue() != 0;
	}

	/**
	 * Returns {@code true} if the given {@link TVersionable} is considered to be versioned.
	 *
	 * An element is considered to be versioned, if it has a non-zero version.
	 *
	 * A return value of {@code true} indicates a non-zero value for {@link TVersionable#getVersion()}.
	 */
	public static boolean isTVersionable(EObject element) {
		return (element instanceof TVersionable) && ((TVersionable) element).getVersion() != 0;
	}

	/**
	 * Returns {@code true} if the given element implements {@link Versionable} and is considered to be versioned.
	 *
	 * An element is considered to be versioned, if it has a non-zero version.
	 *
	 * A return value of {@code true} indicates a non-zero value for {@link TVersionable#getVersion()}.
	 */
	public static boolean isVersionable(EObject element) {
		return (element instanceof Versionable) && ((Versionable) element).getVersion() != 0;
	}

	private VersionUtils() {
		// non-instantiable utility class
	}
}
