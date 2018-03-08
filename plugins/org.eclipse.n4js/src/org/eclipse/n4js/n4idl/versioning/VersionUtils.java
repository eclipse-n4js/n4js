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

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.VersionedElement;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TVersionable;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.Iterators;

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

	/**
	 * Returns the declared version for the given {@link TypeRef}.
	 *
	 * This method only works with explicitly declared type requests. Therefore, it is safe to use it in a pre-linking
	 * phase of AST processing/ type model building.
	 *
	 * Returns {@code 0} if {@code typeRef} is {@code null}.
	 */
	public static int getVersion(TypeRef typeRef) {
		return streamVersionedSubReferences(typeRef).findFirst()
				.map(s -> s.getVersion()).orElse(0);
	}

	/**
	 * Returns the declared version of the given list of {@link TypeRef}.
	 *
	 * Returns the first version that can be inferred from the given list of {@link TypeRef}
	 *
	 * @see #getVersion(TypeRef)
	 */
	public static int getVersion(List<TypeRef> typeRefs) {
		return typeRefs.stream()
				.map(ref -> VersionUtils.getVersion(ref))
				.filter(v -> v != 0)
				.findFirst().orElse(0);
	}

	/**
	 * Returns a stream of all versioned sub-references of the given {@link TypeRef}.
	 *
	 * If {@code typeRef} happens to be versioned itself, the stream starts with it.
	 */
	public static Stream<VersionedParameterizedTypeRef> streamVersionedSubReferences(TypeRef typeRef) {
		return streamSubReferences(typeRef)
				.filter(t -> t instanceof VersionedParameterizedTypeRef)
				.map(t -> (VersionedParameterizedTypeRef) t);
	}

	/**
	 * Returns a stream of all sub-references of the given {@link TypeRef}.
	 *
	 * The (ordered) stream starts with {@code typeRef} itself.
	 */
	private static Stream<TypeRef> streamSubReferences(TypeRef typeRef) {
		// create iterator over reference itself, followed by all its children according to #eAllContents()
		final Iterator<EObject> refAndChildren = Iterators.concat(Iterators.singletonIterator(typeRef),
				typeRef.eAllContents());

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(refAndChildren, Spliterator.ORDERED), false)
				.filter(TypeRef.class::isInstance)
				.map(TypeRef.class::cast);

	}

	private VersionUtils() {
		// non-instantiable utility class
	}
}
