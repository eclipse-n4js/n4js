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
package org.eclipse.n4js.utils.emf;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Basic {@link EStructuralFeature structural feature} merger for {@link EObject}s.
 */
public class EObjectFeatureMerger {

	/**
	 * Merges the structural features from the {@code from} argument to the {@code to} argument.
	 *
	 * @param from
	 *            the object to copy and merge the features from.
	 * @param to
	 *            the target of the merge operation.
	 * @return the merged object.
	 */
	public EObject merge(final EObject from, final EObject to) {
		final EObject copy = EcoreUtil.copy(from);
		EcoreUtil.resolveAll(copy);
		return merge(copy, to, newHashSet());
	}

	private EObject merge(final EObject from, final EObject to, final Collection<Object> visitedObjects) {

		if (null == from || null == to) {
			return null;
		}

		// This is against cycles through EReferences.
		if (visitedObjects.contains(from) || visitedObjects.contains(to)) {
			return to;
		}

		visitedObjects.add(to);
		visitedObjects.add(from);

		final Collection<EStructuralFeature> fromFeatures = from.eClass().getEAllStructuralFeatures();

		for (final EStructuralFeature feature : fromFeatures) {
			if (-1 != to.eClass().getFeatureID(feature) && feature.isChangeable()) {
				if (from.eIsSet(feature)) {
					final Object fromValue = from.eGet(feature, true);
					final Object toValue = to.eGet(feature, true);
					if (null == toValue) {
						to.eSet(feature, fromValue);
					} else {
						if (feature.isMany()) {
							@SuppressWarnings("unchecked")
							final Collection<Object> toManyValue = (Collection<Object>) toValue;
							@SuppressWarnings("unchecked")
							final Collection<Object> fromManyValue = (Collection<Object>) fromValue;
							for (final Iterator<Object> itr = fromManyValue.iterator(); itr.hasNext(); /**/) {
								final Object fromElement = itr.next();
								if (!contains(toManyValue, fromElement)) {
									itr.remove();
									toManyValue.add(fromElement);
								}
							}
						} else {
							if (feature instanceof EAttribute) {
								to.eSet(feature, fromValue);
							} else if (feature instanceof EReference) {
								to.eSet(feature, merge((EObject) fromValue, (EObject) toValue, visitedObjects));
							}
						}
					}
				}
			}
		}
		return to;
	}

	/**
	 * When copying the {@code fromElement} from a many valued feature into the {@code toManyValue} feature, the this
	 * method is called to check for existence. By default the default {@link #equals(Object)} and {@link #hashCode()}
	 * will be used to checking existence. One could override this method to implement uniqueness based on any other
	 * conditions.
	 *
	 * @param toManyValue
	 *            the possible target container for the {@code fromElement}.
	 * @param fromElement
	 *            the from element that will be possible copied to the {@toManyValue} many valued target feature.
	 * @return {@code true} if the from element contained in the many valued feature in any circumstances. Otherwise
	 *         returns with {@code false}.
	 */
	protected boolean contains(final Collection<? extends Object> toManyValue, final Object fromElement) {
		return toManyValue.contains(fromElement);
	}

}
