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
package org.eclipse.n4js.scoping.utils;

import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Utilities for polyfills.
 */
public class PolyfillUtils {

	/** Segment used to mark polyfills. The "!" ensures that no real name can conflict with this pseudo-segment. */
	public static String POLYFILL_SEGMENT = "!POLY";

	/**
	 * Segment used to mark polyfilling modules. Differs from POLYFILL_SEGMENT which is used to mark specific filling
	 * types.
	 */
	public static String MODULE_POLYFILL_SEGMENT = "!MPOLY";

	/**
	 * Returns the matching (internal) name of the polyfill matching the given classifier. There are no polyfills of
	 * polyfills, so the returned name is independent from the polyfill flag of the given classifier.
	 */
	public static QualifiedName getPolyfillFQN(TClassifier tClassifier, IQualifiedNameProvider qualifiedNameProvider) {
		QualifiedName prefix = qualifiedNameProvider.getFullyQualifiedName(EcoreUtil.getRootContainer(tClassifier));
		prefix = QualifiedNameUtils.append(prefix, POLYFILL_SEGMENT);
		return QualifiedNameUtils.append(prefix, tClassifier.getName());
	}

	/**
	 * Static case: Returns the matching (internal) name of the polyfill matching the given classifier. There are no
	 * polyfills of polyfills, so the returned name is independent from the polyfill flag of the given classifier.
	 */
	public static QualifiedName getStaticPolyfillFQN(TClassifier tClassifier,
			IQualifiedNameProvider qualifiedNameProvider) {
		QualifiedName prefix = qualifiedNameProvider.getFullyQualifiedName(EcoreUtil.getRootContainer(tClassifier));
		prefix = QualifiedNameUtils.prepend(MODULE_POLYFILL_SEGMENT, prefix);
		prefix = QualifiedNameUtils.append(prefix, POLYFILL_SEGMENT);
		return QualifiedNameUtils.append(prefix, tClassifier.getName());
	}

	/**
	 * Compute QN of the static-polyfill
	 *
	 * @param tModule
	 *            Module to be filled.
	 * @param qualifiedNameProvider
	 *            provider configured for the correct language
	 * @return null
	 */
	public static QualifiedName getStaticPolyfillFQN(TModule tModule,
			IQualifiedNameProvider qualifiedNameProvider) {
		QualifiedName prefix = qualifiedNameProvider.getFullyQualifiedName(EcoreUtil.getRootContainer(tModule));
		return toStaticPolyfillFQN(prefix).orElse(null);
	}

	/**
	 * Returns true if the given name describes a polyfill, that is if it's second last segment matches
	 * {@link #POLYFILL_SEGMENT}.
	 */
	public static boolean isPolyfill(QualifiedName name) {
		if (name == null) {
			return false;
		}
		// as long as Module-polyfill is not enforced, we check for presence and then adapt:
		int polyModuleOffest = isModulePolyfill(name) ? 1 : 0;
		int polyFillSegmentIdx = name.getSegmentCount() - (2 + polyModuleOffest);
		return polyFillSegmentIdx >= 0 && POLYFILL_SEGMENT.equals(name.getSegment(polyFillSegmentIdx));
	}

	/**
	 * Checks if this qualified name denotes an element inside of a polyfill module.
	 *
	 * @param name
	 *            to investigate.
	 * @return <code>true</code> if the first segment of name is {@link #MODULE_POLYFILL_SEGMENT}
	 */
	public static boolean isModulePolyfill(QualifiedName name) {
		if (name == null || name.isEmpty())
			return false;
		return MODULE_POLYFILL_SEGMENT.equals(name.getFirstSegment());
	}

	/**
	 * Return corresponding FQN for staticPolyfill (prefixed with !MPOLY
	 *
	 * @param name
	 *            non-filled name
	 * @return fqn of filling, not present if name itself denotes a static polyfill or is null.
	 */
	public static Optional<QualifiedName> toStaticPolyfillFQN(QualifiedName name) {
		if (name != null
				&& !name.isEmpty()
				&& !MODULE_POLYFILL_SEGMENT.equals(name.getFirstSegment())) {
			return Optional.of(QualifiedNameUtils.prepend(MODULE_POLYFILL_SEGMENT, name));
		} else
			return Optional.empty();
	}
}
