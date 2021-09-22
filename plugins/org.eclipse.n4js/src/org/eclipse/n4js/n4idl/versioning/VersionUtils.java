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

import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.VersionedElement;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Static utility class with regard to versioned and versionable elements.
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
	 * Returns {@code true} if the given context is version-aware (allows for explicit type version requests).
	 */
	public static boolean isVersionAwareContext(EObject context) {
		// first check for early-exit opportunities
		if ((context instanceof AnnotableElement)) {
			// early exit, if the context element is annotated as version-aware already
			if (hasVersionAwarenessAnnotation((AnnotableElement) context)) {
				return true;
			}
		}
		if (context instanceof TAnnotableElement) {
			// early exit, if the context element is annotated as version-aware already
			if (hasVersionAwarenessAnnotation((TAnnotableElement) context)) {
				return true;
			}
		}
		// exception for import declarations
		if (context instanceof ImportDeclaration) {
			return true;
		}

		// check whether the context is the built-in MigrationContext (which is considered version-aware)
		if (context == BuiltInTypeScope.get(context.eResource().getResourceSet()).getMigrationContextType()) {
			return true;
		}

		// Otherwise traverse the containment tree of context:
		// Find the first container which has any of the version-awareness annotations
		return StreamSupport
				.stream(EcoreUtil2.getAllContainers(context).spliterator(), false)
				// filter and cast to AnnotableElement
				.filter(AnnotableElement.class::isInstance).map(e -> (AnnotableElement) e)
				// check whether the container has one of the version-awareness annotations
				.findAny().map(VersionUtils::isVersionAwareContext).orElse(false);
	}

	/**
	 * Returns {@code true} if the given element is annotated with one of the
	 * {@link N4IDLGlobals#VERSION_AWARENESS_ANNOTATIONS}.
	 *
	 * Equivalent of {@link VersionUtils#hasVersionAwarenessAnnotation(AnnotableElement)} for AST elements.
	 */
	public static boolean hasVersionAwarenessAnnotation(AnnotableElement element) {
		return N4IDLGlobals.VERSION_AWARENESS_ANNOTATIONS.stream()
				.anyMatch(anno -> anno.hasAnnotation(element));
	}

	/**
	 * Returns {@code true} if the given (type model) element is annotated with one of the
	 * {@link N4IDLGlobals#VERSION_AWARENESS_ANNOTATIONS}.
	 *
	 * Equivalent of {@link VersionUtils#hasVersionAwarenessAnnotation(AnnotableElement)} for type model elements.
	 */
	public static boolean hasVersionAwarenessAnnotation(TAnnotableElement element) {
		return N4IDLGlobals.VERSION_AWARENESS_ANNOTATIONS.stream()
				.anyMatch(anno -> anno.hasAnnotation(element));

	}

	private VersionUtils() {
		// non-instantiable utility class
	}
}
