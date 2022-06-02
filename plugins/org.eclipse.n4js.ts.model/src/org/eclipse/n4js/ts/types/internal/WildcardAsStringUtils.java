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
package org.eclipse.n4js.ts.types.internal;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;

/**
 * In the {@link TypeArgument#internalGetTypeRefAsString(boolean) #internalGetTypeRefAsString()} method of
 * {@link Wildcard}s, we want to show implicit upper bounds (e.g. helpful in error messages). However, this means we
 * will run into an infinite recursion issue in cases like:
 *
 * <pre>
 * class C&lt;T extends C&lt;?>> {
 * }
 * </pre>
 *
 * To properly guard for infinite recursion in such cases, this class contains some special handling and method
 * {@link Wildcard#internalGetTypeRefAsString(boolean)} delegates here.
 */
public final class WildcardAsStringUtils {

	private static final Set<Thread> threadsCurrentlyConvertingWildcardsWithImplicitUpperBounds = ConcurrentHashMap
			.newKeySet();

	/** workaround for Xtend/Xcore bug. Delegates to {@link #internalGetTypeRefAsString(Wildcard, boolean)}. */
	public static final String internalGetTypeRefAsString_workaround(Object wildcard, boolean resolveProxies) {
		return internalGetTypeRefAsString((Wildcard) wildcard, resolveProxies);
	}

	/**
	 * Method {@link Wildcard#getTypeRefAsString()} delegates here. Should not be called by any other code!
	 */
	public static final String internalGetTypeRefAsString(Wildcard w, boolean resolveProxies) {
		if (w.isImplicitUpperBoundInEffect()) {
			final Thread myThread = Thread.currentThread();
			final boolean wIsFirst = threadsCurrentlyConvertingWildcardsWithImplicitUpperBounds.add(myThread);
			try {
				return primGetTypeRefAsString(w, wIsFirst, // first shows the implicit bounds, all later ones don't
						resolveProxies);
			} finally {
				if (wIsFirst)
					threadsCurrentlyConvertingWildcardsWithImplicitUpperBounds.remove(myThread);
			}
		} else {
			return primGetTypeRefAsString(w, false, resolveProxies);
		}
	}

	private static final String primGetTypeRefAsString(Wildcard w, boolean showImplicitUpperBounds,
			boolean resolveProxies) {
		final StringBuilder sb = new StringBuilder();
		sb.append("?");
		final TypeRef declUpperBound = w.getDeclaredUpperBound();
		final TypeRef upperBound = showImplicitUpperBounds ? w.getDeclaredOrImplicitUpperBound() : declUpperBound;
		final TypeRef lowerBound = w.getDeclaredLowerBound();
		if (upperBound != null) {
			sb.append(" extends ");
			sb.append(upperBound.getTypeRefAsString(resolveProxies));
		} else if (lowerBound != null) {
			sb.append(" super ");
			sb.append(lowerBound.getTypeRefAsString(resolveProxies));
		}
		return sb.toString();
	}

	private WildcardAsStringUtils() {
	}
}
