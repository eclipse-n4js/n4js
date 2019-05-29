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
package org.eclipse.n4js.resource;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.SegmentSequence;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * Specialized, {@link SyntaxRelatedTElement syntax element} aware unloader for {@link EObject EObjects}.
 *
 * Proxification of EObject is by default completely on based on the fact that the objects will be enhanced by its
 * unique identifier: the {@link InternalEObject#eSetProxyURI(URI) proxy URI} will be set. That implies, all other
 * features of the objects stay the same. It then consumes more memory than before. Even worse, if the proxy is removed
 * from the resource and set into another structure, e.g. into an EObjectDescription, all its properties will be
 * strongly referenced. Therefore we have to make sure that the index entries do not hold references to other EObjects.
 *
 * This implementation takes care of {@link SyntaxRelatedTElement}, which will be put into the index. They refer to ast
 * elements which in turn are still referencing the node model, the resource, the cache etc. Therefore we have to proxy
 * {@link SyntaxRelatedTElement#setAstElement(EObject) that particular reference} in the {@link SyntaxRelatedTElement}.
 */
public class N4JSUnloader implements IReferableElementsUnloader {

	/**
	 * Set to true if you want to enable a debugging mode where each handcrafted URI is compared to the one returned
	 * from {@link Resource#getURIFragment(EObject)}.
	 */
	private static final boolean DEBUG = false;

	/**
	 * Set to true if you want to measure the cost of {@link #unloadRoot(EObject)} by poor mans mechanism of
	 * {@link System#currentTimeMillis()}.
	 */
	private static final boolean TIME = false;

	@Override
	public void unloadRoot(EObject root) {
		// Content adapters should be removed the same way as they are added: top-down.
		// Fragments are recursive, so we need them to be calculated before proxifying the container.
		// OTOH, some model elements resolve their children when being proxified (e.g. EPackageImpl).
		// => first calculate all fragments, then proxify elements starting form root.

		if (TIME) {
			long time = System.currentTimeMillis();
			List<ObjectToFragment> unloadUs = collectElementsToUnload(root);
			doUnload(unloadUs, getResourceURI(root));
			long duration = System.currentTimeMillis() - time;
			if (duration > 0) {
				System.out.println("Took " + duration + " ms for " + unloadUs.size() + " elements");
			}
		} else {
			List<ObjectToFragment> unloadUs = collectElementsToUnload(root);
			doUnload(unloadUs, getResourceURI(root));
		}
	}

	private URI getResourceURI(EObject root) {
		Resource resource = root.eResource();
		if (resource != null)
			return resource.getURI();
		return EcoreUtil.getURI(root).trimFragment();
	}

	/**
	 * Traverse the list of objects and uri fragments to install proxy URIs.
	 */
	private void doUnload(List<ObjectToFragment> result, URI resourceURI) {
		for (ObjectToFragment elementToUnload : result) {
			unload(elementToUnload, resourceURI);
		}
	}

	/**
	 * Traverse the contents of {@code root} and (including root itself) and collect all the URI fragments in one shot.
	 *
	 * This method inlines the logic of {@link Resource#getURIFragment(EObject)} to traverse the tree of objects in one
	 * shot. Rather than walking up the containment hierarchy for each contained element and looking up list indexes,
	 * these will be carried around and accumulated instead. Simplified complexity consideration: This effectively turns
	 * this into an operation of O(n) where n is the number of elements in the tree. Otherwise we'd see something along
	 * O(n^2).
	 */
	private List<ObjectToFragment> collectElementsToUnload(EObject root) {
		// the starting point
		String initialFragment = getInitialFragment(root);
		// the resulting list
		List<ObjectToFragment> result = Lists.newArrayList();
		ObjectToFragment start = new ObjectToFragment(root, initialFragment);
		addAndClearAdapters(start, result);
		for (Iterator<ObjectToFragment> iter = getAllProperContents(start, new StringBuilder(initialFragment)); iter
				.hasNext();) {
			addAndClearAdapters(iter.next(), result);
		}
		return result;
	}

	private String getInitialFragment(EObject root) {
		Resource resource = root.eResource();
		if (resource != null)
			return resource.getURIFragment(root);
		return EcoreUtil.getURI(root).fragment();
	}

	private void addAndClearAdapters(ObjectToFragment object, List<ObjectToFragment> result) {
		result.add(object);
		object.object.eAdapters().clear();
	}

	private void unload(ObjectToFragment element, URI resourceURI) {
		InternalEObject eObject = (InternalEObject) element.object;
		if (eObject instanceof SyntaxRelatedTElement) {
			SyntaxRelatedTElement casted = (SyntaxRelatedTElement) eObject;
			EObject astElementOrProxy = (EObject) casted.eGet(
					TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT, false);
			if (astElementOrProxy != null && !astElementOrProxy.eIsProxy()) {
				// release the reference to the AST
				casted.eSetDeliver(false);
				casted.setAstElement(null);
			}
		}
		eObject.eSetProxyURI(resourceURI.appendFragment(element.fragment));
	}

	/**
	 * Small utility: Pair of an EObject and an URI fragment.
	 */
	private static class ObjectToFragment {
		final EObject object;
		final String fragment;

		private ObjectToFragment(EObject object, String fragment) {
			this.object = object;
			this.fragment = fragment;
			if (DEBUG) {
				String originalFragment = EcoreUtil.getURI(object).fragment();
				if (!fragment.equals(originalFragment)) {
					throw new AssertionError("orig: " + originalFragment + "\ncomp: " + fragment);
				}
			}
		}
	}

	/**
	 * Traverse the contents of the object that is contained in the {@link ObjectToFragment}. The string builder is used
	 * as an accumulating parameter. That is, during the traversal it will be used and trimmed again. This allows to
	 * reuse the underlying char array which is already copied when the fragment is turned into a string.
	 *
	 * The fragments use the very same notation as described in
	 * {@link InternalEObject#eURIFragmentSegment(EStructuralFeature, EObject)}.
	 *
	 * The implementation from {@link ResourceImpl} uses a {@link SegmentSequence} rather than a {@link StringBuilder}.
	 * Since we have to concatenate the string in the end anyway.
	 */
	private static Iterator<ObjectToFragment> getAllProperContents(ObjectToFragment eObject,
			final StringBuilder result) {
		// we inherit from the AbstractTreeIterator which will help us getting a proper pre-order traversal
		return new AbstractTreeIterator<>(eObject, false /* don't resolve containment proxies */) {

			@Override
			public Iterator<ObjectToFragment> getChildren(Object parent) {
				final EObject current = ((ObjectToFragment) parent).object;
				final EStructuralFeature[] containments = containmentFeatures(current);
				if (containments == null || containments.length == 0) {
					// no containment features found, exit
					return Collections.emptyIterator();
				}
				// we have at least one containment feature - append the fragment delimiter '/'
				result.append('/');
				// and concatenate all the iterators for the children
				return Iterators.concat(getFeatureIterators(current, containments));
			}

			/**
			 * Returns an iterator of iterators. Each nested iterator covers a single containment feature. If the
			 * feature is multi valued, the nested iterator has as many values as are set on the feature.
			 */
			private Iterator<Iterator<ObjectToFragment>> getFeatureIterators(EObject current,
					EStructuralFeature[] containments) {
				return new AbstractIterator<>() {
					/**
					 * The length of the string builder before something was added by this traversing iterator.
					 */
					private final int prevLength = result.length();

					private int featureIdx = 0;

					@Override
					protected Iterator<ObjectToFragment> computeNext() {
						while (featureIdx < containments.length) {
							EStructuralFeature containment = containments[featureIdx];
							featureIdx++;
							// only consider features that have values
							if (current.eIsSet(containment)) {
								// reset the string builder
								result.setLength(prevLength);
								// append the @ sign and the feature name
								// '@<feature-name>'
								result.append('@').append(containment.getName());
								// compute the contained values
								return newValueIterator(containment);
							}
						}
						return endOfData();
					}

					/**
					 * Returns an iterator for the values that are assigned to the given containment feature.
					 */
					private Iterator<ObjectToFragment> newValueIterator(EStructuralFeature feature) {
						if (feature.isMany()) {
							// add the dot as the delimiter between feature name and index in the list
							result.append('.');
							// compute the value indexes
							return newManyValueIterator((List<?>) current.eGet(feature));
						} else {
							// no dot is added for single valued features
							return newSingleValueIterator((EObject) current.eGet(feature));
						}
					}

					/**
					 * Finalize the fragment for the given instance and return a singleton iterator for it.
					 */
					private Iterator<ObjectToFragment> newSingleValueIterator(EObject value) {
						ObjectToFragment objectToFragment = new ObjectToFragment(value, result.toString());
						return Iterators.singletonIterator(objectToFragment);
					}

					/**
					 * Traverse the list of values and return the feature indexes along with the values.
					 */
					private AbstractIterator<ObjectToFragment> newManyValueIterator(final List<?> values) {
						return new AbstractIterator<>() {

							/**
							 * The length of the string builder before something was added by this traversing iterator.
							 */
							private final int prevLengthBeforeIdx = result.length();

							/**
							 * The index in the value list.
							 */
							private int valueIdx = 0;

							@Override
							protected ObjectToFragment computeNext() {
								if (valueIdx < values.size()) {
									EObject value = (EObject) values.get(valueIdx);
									result.setLength(prevLengthBeforeIdx);
									result.append(valueIdx);
									valueIdx++;
									return new ObjectToFragment(value, result.toString());
								}
								return endOfData();
							}
						};
					}
				};
			}

			private EStructuralFeature[] containmentFeatures(final EObject current) {
				return ((EClassImpl.FeatureSubsetSupplier) current.eClass().getEAllStructuralFeatures()).containments();
			}
		};
	}

}
