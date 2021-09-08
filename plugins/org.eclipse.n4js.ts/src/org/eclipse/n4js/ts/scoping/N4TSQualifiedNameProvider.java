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
package org.eclipse.n4js.ts.scoping;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.inject.Inject;

/**
 */
public class N4TSQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

	/**
	 * Segment used for the global module.
	 */
	public static String GLOBAL_NAMESPACE_SEGMENT = "#";

	/** Segment used to mark polyfills. The "!" ensures that no real name can conflict with this pseudo-segment. */
	public static String POLYFILL_SEGMENT = "!POLY";

	/**
	 * Segment used to mark polyfilling modules. Differs from POLYFILL_SEGMENT which is used to mark specific filling
	 * types.
	 */
	public static String MODULE_POLYFILL_SEGMENT = "!MPOLY";

	/**
	 * The injected qualified name converter.
	 */
	@Inject
	protected IQualifiedNameConverter converter;

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		String name = SimpleAttributeResolver.NAME_RESOLVER.apply(obj);
		if (name == null || name.isEmpty())
			return null;
		return converter.toQualifiedName(name);
	}

	/**
	 * Returns the matching (internal) name of the polyfill matching the given classifier. There are no polyfills of
	 * polyfills, so the returned name is independent from the polyfill flag of the given classifier.
	 */
	public static QualifiedName getPolyfillFQN(TClassifier tClassifier, IQualifiedNameProvider qualifiedNameProvider) {
		QualifiedName prefix = qualifiedNameProvider.getFullyQualifiedName(EcoreUtil.getRootContainer(tClassifier));
		if ("n4ts".equals(tClassifier.eResource().getURI().fileExtension())) { // } instanceof TObjectPrototype) {
			prefix = append(prefix, GLOBAL_NAMESPACE_SEGMENT);
			// TODO this has to be removed, instead, also n4ts files should use "#" as global namespace segment
		}
		prefix = append(prefix, POLYFILL_SEGMENT);
		return append(prefix, tClassifier.getName());
	}

	/**
	 * Static case: Returns the matching (internal) name of the polyfill matching the given classifier. There are no
	 * polyfills of polyfills, so the returned name is independent from the polyfill flag of the given classifier.
	 */
	public static QualifiedName getStaticPolyfillFQN(TClassifier tClassifier,
			IQualifiedNameProvider qualifiedNameProvider) {
		QualifiedName prefix = qualifiedNameProvider.getFullyQualifiedName(EcoreUtil.getRootContainer(tClassifier));
		// if ("n4ts".equals(tClassifier.eResource().getURI().fileExtension())) { // } instanceof TObjectPrototype) {
		// prefix = append(prefix, GLOBAL_NAMESPACE_SEGMENT);
		// // TODO this has to be removed, instead, also n4ts files should use "#" as global namespace segment
		// }
		prefix = prepend(MODULE_POLYFILL_SEGMENT, prefix);
		prefix = append(prefix, POLYFILL_SEGMENT);
		return append(prefix, tClassifier.getName());
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
	 * Null-safe appending of segments. If segment is null, null is returned. If prefix is null, a new qualified name
	 * (with non-null segment) is created.
	 */
	protected static QualifiedName append(QualifiedName prefix, String segment) {
		if (segment == null) {
			return null;
		}
		if (prefix == null) {
			return QualifiedName.create(segment);
		}
		return prefix.append(segment);
	}

	/**
	 * Prefixing a qualified name. If the prefix segment is null, the suffix is returned, else if the suffix is null a
	 * QN with only one segment is returned. Otherwise segment gets prefixed to suffix.
	 */
	protected static QualifiedName prepend(String segment, QualifiedName suffix) {
		if (segment == null) {
			return suffix;
		}
		QualifiedName qn = QualifiedName.create(segment);
		if (suffix != null) {
			qn = qn.append(suffix);
		}
		return qn;
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
			return Optional.of(prepend(MODULE_POLYFILL_SEGMENT, name));
		} else
			return Optional.empty();
	}
}
