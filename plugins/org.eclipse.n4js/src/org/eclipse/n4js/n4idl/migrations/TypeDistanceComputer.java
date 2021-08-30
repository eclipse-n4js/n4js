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
import java.util.Optional;

import org.eclipse.n4js.n4idl.migrations.SuperClassifierIterator.SuperClassifierEntry;
import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.BuiltInType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.utils.TypeCompareUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.collections.Iterables2;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * A compile-time type-distance computer based on the minimum distance of two types in the class hierarchy.
 *
 * This type distance computer supports {@link TClassifier}s, {@link TypeTypeRef}s, {@link BuiltInType}s and
 * {@link PrimitiveType}s.
 */
public class TypeDistanceComputer {

	/**
	 * The maximum type distance of two types.
	 *
	 * This value indicates that two types are unrelated.
	 */
	public static final double MAX_DISTANCE = Double.POSITIVE_INFINITY;

	/**
	 * Distinguished exception which may be raised by a type distance computation, in case the given type references do
	 * not represent types which are a valid pair of operands for a type distance computation.
	 */
	public static final class UnsupportedTypeDistanceOperandsException extends Exception {
		/** Instantiates a new {@link UnsupportedTypeDistanceOperandsException} with the given message. */
		public UnsupportedTypeDistanceOperandsException(String message) {
			super(message);
		}
	}

	/**
	 * Computes the overall type distance from {@code fromTypeRefs} to {@code toTypeRefs}.
	 *
	 * Returns {@link #MAX_DISTANCE} if one or all of the types in {@code fromTypes} are infinitely distant (unrelated)
	 * from the types in {@code toTypes}.
	 *
	 * @param fromTypeRefs
	 *            The types references to compute the distance from.
	 * @param toTypeRefs
	 *            The type references to compute the distance to.
	 * @throws UnsupportedTypeDistanceOperandsException
	 *             If one of the given pairs of type references do not represent a pair of types which are valid
	 *             operands for a type distance computation (e.g. null, unsupported TypeRef subclasses, etc.).
	 */
	public double computeDistance(List<TypeRef> fromTypeRefs, List<TypeRef> toTypeRefs)
			throws UnsupportedTypeDistanceOperandsException {
		if (fromTypeRefs.size() != toTypeRefs.size()) {
			return MAX_DISTANCE;
		}
		// Compute the distance for each pair of types in fromTypeResf and toTypeRefs.
		// The use of Double.POSITIVE_INIFINTY as MAX_DISTANCE prevents overflows in case of a pair being of infinite
		// distance.
		double sum = 0;

		Iterable<Pair<TypeRef, TypeRef>> argumentParemeterPairs = Iterables2.align(fromTypeRefs, toTypeRefs);

		for (Pair<TypeRef, TypeRef> typePair : argumentParemeterPairs) {
			final double distance = computeDistance(typePair.getKey(), typePair.getValue());
			// early-exit if one pair has MAX_DISTANCE
			if (distance == MAX_DISTANCE) {
				return MAX_DISTANCE;
			}
			sum += distance;
		}

		return sum;
	}

	/**
	 * Computes the type distance from {@code typeRef1} to {@code typeRef2}.
	 *
	 * Returns {@link #MAX_DISTANCE} if {@code typeRef1} is not a nominal subtype of {@code typeRef2}.
	 *
	 * Returns {@code 0} for primitive, literal, and built-in types iff they are equal (==).
	 *
	 * Only handles {@link TypeTypeRef}s and {@link TypeRef}s with non-null {@link TypeRef#getDeclaredType()} for now.
	 *
	 * @throws UnsupportedTypeDistanceOperandsException
	 *             If the given pair of type references do not represent a pair of types which are valid operands for a
	 *             type distance computation (e.g. null, unsupported TypeRef subclasses, etc.).
	 */
	public double computeDistance(TypeRef typeRef1, TypeRef typeRef2) throws UnsupportedTypeDistanceOperandsException {
		// handle special case of literal types up front
		if (typeRef1 instanceof LiteralTypeRef) {
			if (typeRef2 instanceof LiteralTypeRef) {
				if (TypeCompareUtils.isEqual(typeRef1, typeRef2)) {
					return 0;
				}
			} else {
				if (N4JSLanguageUtils.isLiteralTypeBase((LiteralTypeRef) typeRef1, typeRef2)) {
					return 0;
				}
			}
			return MAX_DISTANCE;
		} else if (typeRef2 instanceof LiteralTypeRef) {
			return MAX_DISTANCE;
		}

		// obtain a valid pair of operands from the given type references
		final Optional<Pair<Type, Type>> operands = extractTypeDistanceOperands(typeRef1, typeRef2);
		// if that fails already, we assume a maximum type distance
		if (!operands.isPresent()) {
			return MAX_DISTANCE;
		}

		final Type type1 = operands.get().getKey();
		final Type type2 = operands.get().getValue();

		// shortcut for equal types (includes primitive and built-in types)
		if (type1 == type2) {
			return 0;
		}

		if (type1 instanceof PrimitiveType || type2 instanceof PrimitiveType) {
			// if one of the types is primitive but not equal (see above), they must be unrelated
			return MAX_DISTANCE;
		}

		if (type1 instanceof BuiltInType || type2 instanceof BuiltInType) {
			// if one of the types is built-in but not equal (see above), they must be unrelated
			return MAX_DISTANCE;
		}

		// In case the initial type references do not match in their version,
		// they cannot be equal (even in case of virtual types)
		if (typeRef1.getVersion() != typeRef2.getVersion()) {
			return MAX_DISTANCE;
		}

		// handle classifiers via actual type distance computation
		if (type1 instanceof TClassifier && type2 instanceof TClassifier) {
			return classifierDistance((TClassifier) type1, (TClassifier) type2);
		}

		throw new UnsupportedOperationException(
				"Types (" + type1 + ",\n " + type2 + ") cannot be handled by the compile-time type distance computer.");
	}

	/**
	 * Extracts a valid pair of type-distance {@link Type} operands from the two given type references.
	 *
	 * Returns {@link Optional#empty()} if the given type references are not a valid pair of type distance computation
	 * operands (e.g. incompatible type types such as TypeTypeRef vs. TClassifier ref).
	 *
	 * @throws UnsupportedTypeDistanceOperandsException
	 *             If the type references do not represent a valid operands pair for type distance computation.
	 */
	private Optional<Pair<Type, Type>> extractTypeDistanceOperands(TypeRef typeRef1, TypeRef typeRef2)
			throws UnsupportedTypeDistanceOperandsException {
		// handle TypeTypeRefs
		if (typeRef1 instanceof TypeTypeRef && typeRef2 instanceof TypeTypeRef &&
				(((TypeTypeRef) typeRef1).getTypeArg() != null && ((TypeTypeRef) typeRef2).getTypeArg() != null)) {
			final TypeArgument typeTypeArg1 = ((TypeTypeRef) typeRef1).getTypeArg();
			final TypeArgument typeTypeArg2 = ((TypeTypeRef) typeRef2).getTypeArg();

			// only handle TypeTypeRefs which refer to concrete types (TypeArgument is TypeRef)
			if (typeTypeArg1 instanceof TypeRef && typeTypeArg2 instanceof TypeRef) {
				final Type type1 = ((TypeRef) typeTypeArg1).getDeclaredType();
				final Type type2 = ((TypeRef) typeTypeArg2).getDeclaredType();

				if (type1 != null && type2 != null) {
					return Optional.of(Pair.of(type1, type2));
				}
			}
		}

		if (typeRef1 instanceof TypeTypeRef || typeRef2 instanceof TypeTypeRef) {
			// If only one of the references is a TypeTypeRef, the result is
			// an infinite type distance (since unrelated).
			return Optional.empty();
		}

		final Type type1 = typeRef1.getDeclaredType();
		final Type type2 = typeRef2.getDeclaredType();

		// from this point on, we do not handle any types which return null for {@link getDeclaredType()}
		if (null == type1 || null == type2) {
			throw new UnsupportedTypeDistanceOperandsException(
					"The TypeDistanceComputer does not support null types as input: type1="
							+ type1 + ", type2=" + type2);
		}

		// all combinations of handled classes are supported
		if (isOfHandledClass(type1) && isOfHandledClass(type2)) {
			return Optional.of(Pair.of(type1, type2));
		}

		// otherwise we ran into a corner-case which is not-yet handled by this computer
		throw new UnsupportedTypeDistanceOperandsException(
				"No support for type-distance computation from " + type1.getClass() + " to "
						+ type2.getClass() + " type model instances.");
	}

	/** Returns {@code true} ff the given type is an instance of a class which is handled by this computer. */
	private boolean isOfHandledClass(Type type) {
		return type instanceof PrimitiveType || type instanceof BuiltInType || type instanceof TClassifier;
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
