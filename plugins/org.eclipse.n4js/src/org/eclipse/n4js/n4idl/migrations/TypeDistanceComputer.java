/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.migrations;

import java.util.List;
import java.util.stream.StreamSupport;

import org.eclipse.n4js.n4idl.migrations.SuperClassifierIterator.SuperClassifierEntry;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.BuiltInType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.collections.Iterables2;

/**
 * A compile-time type-distance computer based on the minimum distance of two types in the class hierarchy.
 *
 * This type distance computer support {@link TClassifier}s, {@link TypeTypeRef}s, {@link BuiltInType} and
 * {@link PrimitiveType}s.
 */
public class TypeDistanceComputer {

	/**
	 * The maximum type distance of two types.
	 *
	 * This value indicates that the two types are unrelated.
	 */
	public static final double MAX_DISTANCE = Double.POSITIVE_INFINITY;

	/**
	 * Computes the overall type distance from {@code fromTypes} to {@code toTypes}.
	 *
	 * Returns {@link #MAX_DISTANCE} if one or all of the types in {@code fromTypes} are infinitely distant from the
	 * types in {@code toTypes}.
	 *
	 * @param fromTypes
	 *            The types to compute the distance from.
	 * @param toTypes
	 *            The types to compute the distance to.
	 */
	public double computeDistance(List<Type> fromTypes, List<Type> toTypes) {
		if (fromTypes.size() != toTypes.size()) {
			return MAX_DISTANCE;
		}
		// Compute the distance for each pair of types in types1 and types2.
		// The use of Double.POSITIVE_INIFINTY as MAX_DISTANCE prevents overflows in case of a pair being of infinite
		// distance
		double distanceSum = StreamSupport.stream(Iterables2.align(fromTypes, toTypes).spliterator(), false)
				.mapToDouble(typePair -> computeDistance(typePair.getKey(), typePair.getValue()))
				// sum them up
				.sum();

		return distanceSum;
	}

	/**
	 * Computes the type distance from {@code type1} to {@code type2}.
	 *
	 * Returns {@link Integer#MAX_VALUE} if no {@code type1} is not a nominal subtype of {@code type2}.
	 *
	 * Returns {@code 0} for primitive and built-in types iff they are equal (==).
	 */
	public double computeDistance(Type type1, Type type2) {
		// shortcut for equal types (includes primitive types)
		if (type1 == type2) {
			return 0;
		}

		if (type1 instanceof PrimitiveType || type2 instanceof PrimitiveType) {
			// if both types are primitive but not equal (see above), they are unrelated
			// TODO handle number vs int and auto-boxing
			return MAX_DISTANCE;
		}

		if (type1 instanceof BuiltInType || type2 instanceof BuiltInType) {
			// if both types are primitive but not equal (see above), they are unrelated
			return MAX_DISTANCE;
		}

		if (type1 instanceof TClassifier && type2 instanceof TClassifier) {
			return classifierDistance((TClassifier) type1, (TClassifier) type2);
		}

		throw new UnsupportedOperationException(
				"Types (" + type1 + ", " + type2 + ") cannot be handled by the compile-time type distance computer.");
	}

	/**
	 * Computes the classifier distance from {@code classifier1} to {@code classifier2}.
	 *
	 * Returns {@link Integer#MAX_VALUE} if no nominal subtype relation exists between {@code classifier1} and
	 * {@code classifier2}.
	 */
	private double classifierDistance(TClassifier classifier1, TClassifier classifier2) {
		final SuperClassifierIterator superClassifierIterator = new SuperClassifierIterator(classifier1);
		while (superClassifierIterator.hasNext()) {
			final SuperClassifierEntry entry = superClassifierIterator.next();
			if (entry.classifier == classifier2) {
				return entry.level;
			}
		}
		return MAX_DISTANCE;
	}
}
