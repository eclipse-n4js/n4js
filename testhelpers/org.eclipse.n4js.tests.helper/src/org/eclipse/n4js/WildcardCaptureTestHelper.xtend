/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js

import com.google.common.collect.Lists
import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.constraints.TypeConstraint
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.utils.UtilN4.DigitIterator

/**
 * Helper methods of dealing with capturing wildcards in tests.
 */
class WildcardCaptureTestHelper {

	/**
	 * Creates variations of the given constraint system by capturing and/or reopening any wildcards.
	 * See {@link #createCaptureVariations(RuleEnvironment, Collection, boolean)} for details.
	 */
	def public List<List<TypeConstraint>> createCaptureVariationsForConstraints(RuleEnvironment G,
		Collection<TypeConstraint> constraints, boolean avoidClosedCaptures) {

		val constraintsAsSingleListOfObjects = constraints.flatMap[#[left, right, variance]].toList;
		val variations = createCaptureVariations(G, constraintsAsSingleListOfObjects, avoidClosedCaptures);
		val listOfListOfConstraints = variations.map[
			val result = newArrayList;
			val iter = it.iterator;
			while (iter.hasNext) {
				result += new TypeConstraint(iter.next as TypeArgument, iter.next as TypeArgument, iter.next as Variance);
			}
			return result as List<TypeConstraint>;
		].toList;
		return listOfListOfConstraints;
	}

	/**
	 * Same as {@link #createCaptureVariations(RuleEnvironment, Collection, boolean)} with
	 * avoidClosedCaptures === false.
	 */
	def public <T> List<List<T>> createCaptureVariations(RuleEnvironment G, Collection<T> objects) {
		return createCaptureVariations(G, objects, false);
	}

	/**
	 * Creates variations of the given collection by capturing and/or reopening any contained wildcards.
	 * All combinations of wildcard modifications will be returned and elements of the given collection
	 * that are not of type Wildcard will simply be passed through to the result.
	 * <p>
	 * For example, given a list <code>[w1, w2, "other"]</code> with w1, w2 being wildcards, this method
	 * will return the following list of lists:
	 * <pre>
	 * [
	 *     [w1,                     w2,                     "other"],
	 *     [capture of w1,          w2,                     "other"],
	 *     [reopened capture of w1, w2,                     "other"],
	 *     [w1,                     capture of w2,          "other"],
	 *     [capture of w1,          capture of w2,          "other"],
	 *     [reopened capture of w1, capture of w2,          "other"],
	 *     [w1,                     reopened capture of w2, "other"],
	 *     [capture of w1,          reopened capture of w2, "other"],
	 *     [reopened capture of w1, reopened capture of w2, "other"]
	 * ]
	 * </pre>
	 * and if avoidClosedCaptures is set it will return:
	 * <pre>
	 * [
	 *     [w1,                     w2,                     "other"],
	 *     [reopened capture of w1, w2,                     "other"],
	 *     [w1,                     reopened capture of w2, "other"],
	 *     [reopened capture of w1, reopened capture of w2, "other"]
	 * ]
	 * </pre>
	 */
	def public <T> List<List<T>> createCaptureVariations(RuleEnvironment G, Collection<T> objects, boolean avoidClosedCaptures) {
		val numOfVariationsPerWildcard = if (avoidClosedCaptures) 2 else 3;
		val numOfWildcards = objects.filter(Wildcard).length;
		val numOfTotalVariations = Math.round(Math.pow(numOfVariationsPerWildcard, numOfWildcards));

		val result = <List<T>>newArrayList;
		val iter = new DigitIterator(0, numOfVariationsPerWildcard);
		while (iter.value < numOfTotalVariations) {
			val variation = Lists.newArrayList(createCaptureVariation(G, objects, iter, avoidClosedCaptures));
			result += variation;
			iter.inc;
		}
		return result;
	}

	def private <T> Iterable<T> createCaptureVariation(RuleEnvironment G, Iterable<T> objects, DigitIterator iter, boolean avoidClosedCaptures) {
		return objects.map[obj |
			if (obj instanceof Wildcard) {
				val digit = iter.nextDigit;
				if (digit === 0) {
					obj
				} else if (digit === 1 && !avoidClosedCaptures) {
					capture(obj) as T
				} else {
					captureAndReopen(G, obj) as T
				}
			} else {
				obj
			}
		];
	}

	def public TypeRef capture(Wildcard wildcard) {
		val captured = TypeUtils.captureWildcard(null, wildcard);
		return captured;
	}

	def public TypeRef captureAndReopen(RuleEnvironment G, Wildcard wildcard) {
		val captured = capture(wildcard);
		val reopened = reopenExistentialTypes(G, captured);
		return reopened;
	}

	def public TypeRef reopenExistentialTypes(RuleEnvironment G, TypeRef typeRef) {
		return reopenExistentialTypes(G, typeRef as TypeArgument) as TypeRef;
	}

	def public TypeArgument reopenExistentialTypes(RuleEnvironment G, TypeArgument typeArg) {
		val isOrContainsClosedExistential = typeArg.isClosedExistentialTypeRef
				|| typeArg.eAllContents().exists[isClosedExistentialTypeRef];
		if (isOrContainsClosedExistential) {
			val cpy = TypeUtils.copy(typeArg);
			if (cpy instanceof ExistentialTypeRef) {
				cpy.setReopened(true);
			}
			cpy.eAllContents().filter(ExistentialTypeRef).forEach[reopened = true];
			return cpy;
		}
		return typeArg;
	}

	def private static boolean isClosedExistentialTypeRef(EObject eObj) {
		return if (eObj instanceof ExistentialTypeRef) !eObj.isReopened() else false;
	}
}
