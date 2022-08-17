/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4JS;

import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ABSTRACT_VARIABLE__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ANNOTATION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FUNCTION_DECLARATION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FUNCTION_EXPRESSION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.LABELLED_STATEMENT__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_CLASS_EXPRESSION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_ENUM_LITERAL__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_NAMESPACE_DECLARATION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_TYPE_VARIABLE__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME;
import static org.eclipse.n4js.ts.types.TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;

/**
 * Utilities for dealing with the N4JS-related EMF meta-models for AST, types, and type references, i.e.
 * {@link N4JSPackage}, {@link TypesPackage}, and {@link TypeRefsPackage} and their contents.
 */
public class N4JSMetaModelUtils {

	/**
	 * The {@link EPackage}s used by the core N4JS language implementation, not including those of the more exotic
	 * meta-models such as <code>IM.xcore</code>, <code>Semver.xcore</code>, <code>JSON.xcore</code>, or
	 * <code>Validation.xcore</code>.
	 */
	public static final List<EPackage> N4JS_EPACKAGES = ImmutableList.of(
			N4JSPackage.eINSTANCE,
			TypesPackage.eINSTANCE,
			TypeRefsPackage.eINSTANCE);

	/**
	 * Maps {@link EClass}es of {@link N4JSPackage} to their containment {@link EReference}s of type
	 * {@link TypeReferenceNode}, including both owned and inherited references. The references may be single- or
	 * many-valued.
	 * <p>
	 * Most clients will want to use utility method {@link N4JSASTUtils#getContainedTypeReferenceNodes(EObject)}
	 * instead.
	 */
	public static final ListMultimap<EClass, EReference> containersOfTypeReferenceNodes;

	private static final Collection<EClass> ECLASSES_CONTRIBUTING_CONTENT_ASSIST_PROPOSALS = ImmutableList.of(
			TypesPackage.Literals.IDENTIFIABLE_ELEMENT);

	private static final Set<EClass> cachedAllEClassesContributingContentAssistProposals;

	static {
		ListMultimap<EClass, EReference> eClassToOwnedRefs = ArrayListMultimap.create();
		for (EClass eClass : IterableExtensions.filter(N4JSPackage.eINSTANCE.getEClassifiers(), EClass.class)) {
			eClassToOwnedRefs.putAll(eClass, IterableExtensions.filter(eClass.getEReferences(),
					eRef -> eRef.isContainment()
							&& N4JSPackage.Literals.TYPE_REFERENCE_NODE.isSuperTypeOf(eRef.getEReferenceType())));
		}
		ListMultimap<EClass, EReference> map2 = ArrayListMultimap.create();
		for (EClass eClass : IterableExtensions.filter(N4JSPackage.eINSTANCE.getEClassifiers(), EClass.class)) {
			map2.putAll(eClass, Iterables.concat(
					eClassToOwnedRefs.get(eClass),
					IterableExtensions.flatMap(eClass.getEAllSuperTypes(), eClassToOwnedRefs::get)));
		}
		containersOfTypeReferenceNodes = ImmutableListMultimap.copyOf(map2);

		ImmutableSet.Builder<EClass> resultBuilder = ImmutableSet.builder();
		for (EPackage ePkg : N4JS_EPACKAGES) {
			for (EClassifier eClassifier : ePkg.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;
					for (EClass eClassSuper : ECLASSES_CONTRIBUTING_CONTENT_ASSIST_PROPOSALS) {
						if (eClassSuper.isSuperTypeOf(eClass)) {
							resultBuilder.add(eClass);
							break;
						}
					}
				}
			}
		}
		resultBuilder.add(TypesPackage.eINSTANCE.getTModule());
		cachedAllEClassesContributingContentAssistProposals = resultBuilder.build();
	}

	/** Caching information for each EClass of the {@link N4JSMetaModelUtils#N4JS_EPACKAGES N4JS-related EPackages}. */
	public static class N4JSMetaModelCache<T> {

		private final ImmutableMap<EClass, T> infoPerEClass;

		/** Creates a new {@link N4JSMetaModelCache}. */
		public N4JSMetaModelCache(Function<EClass, T> infoProvider) {
			ImmutableMap.Builder<EClass, T> declMergingKindIndexBuilder = ImmutableMap.builder();
			for (EPackage ePkg : N4JS_EPACKAGES) {
				for (EClassifier eClassifier : ePkg.getEClassifiers()) {
					if (eClassifier instanceof EClass) {
						EClass eClass = (EClass) eClassifier;
						T value = infoProvider.apply(eClass);
						if (value != null) {
							declMergingKindIndexBuilder.put(eClass, value);
						}
					}
				}
			}
			infoPerEClass = declMergingKindIndexBuilder.build();
		}

		/** Obtain the information for the given {@link EClass}. May be called from any thread. */
		public T get(EClass eClass) {
			return infoPerEClass.get(eClass); // can share the map across threads because it is immutable
		}
	}

	/**
	 * Tells whether objects of the given type may appear as an entry in list of content assist proposals.
	 */
	public static boolean isContributingContentAssistProposals(EClassifier eClassifier) {
		return cachedAllEClassesContributingContentAssistProposals.contains(eClassifier);
	}

	/**
	 * Returns the EMF feature actually holding the name of the given element or <code>null</code>, if no such feature
	 * exists.
	 * <p>
	 * Note that this does not always return {@link EAttribute}s of type {@link String} but may also return an
	 * {@link EReference} of type {@link LiteralOrComputedPropertyName}.
	 *
	 * @see N4JSASTUtils#getElementName(EObject)
	 */
	public static EStructuralFeature getElementNameFeature(EObject elementWithName) {
		if (elementWithName instanceof Annotation)
			return ANNOTATION__NAME;
		if (elementWithName instanceof AbstractVariable)
			return ABSTRACT_VARIABLE__NAME;
		if (elementWithName instanceof N4TypeVariable)
			return N4_TYPE_VARIABLE__NAME;
		if (elementWithName instanceof FunctionDeclaration)
			return FUNCTION_DECLARATION__NAME;
		if (elementWithName instanceof FunctionExpression)
			return FUNCTION_EXPRESSION__NAME;
		if (elementWithName instanceof LabelledStatement)
			return LABELLED_STATEMENT__NAME;
		if (elementWithName instanceof N4TypeDeclaration)
			return N4_TYPE_DECLARATION__NAME;
		if (elementWithName instanceof N4NamespaceDeclaration)
			return N4_NAMESPACE_DECLARATION__NAME;
		if (elementWithName instanceof N4ClassExpression)
			return N4_CLASS_EXPRESSION__NAME;
		if (elementWithName instanceof N4EnumLiteral)
			return N4_ENUM_LITERAL__NAME;
		if (elementWithName instanceof PropertyNameOwner)
			return PROPERTY_NAME_OWNER__DECLARED_NAME;
		if (elementWithName instanceof IdentifiableElement)
			return IDENTIFIABLE_ELEMENT__NAME;
		return null;
	}
}
