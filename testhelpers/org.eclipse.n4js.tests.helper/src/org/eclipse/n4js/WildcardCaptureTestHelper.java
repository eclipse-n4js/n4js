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
package org.eclipse.n4js;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.UtilN4.DigitIterator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

/**
 * Helper methods of dealing with capturing wildcards in tests.
 */
public class WildcardCaptureTestHelper {

	/**
	 * Creates variations of the given constraint system by capturing and/or reopening any wildcards. See
	 * {@link #createCaptureVariations(RuleEnvironment, Collection, boolean)} for details.
	 */
	public List<List<TypeConstraint>> createCaptureVariationsForConstraints(RuleEnvironment G,
			Collection<TypeConstraint> constraints, boolean avoidClosedCaptures) {

		List<Object> constraintsAsSingleListOfObjects = IterableExtensions
				.toList(IterableExtensions.flatMap(constraints, c -> List.of(c.left, c.right, c.variance)));
		List<List<Object>> variations = createCaptureVariations(G, constraintsAsSingleListOfObjects,
				avoidClosedCaptures);
		List<List<TypeConstraint>> listOfListOfConstraints = new ArrayList<>();
		for (List<Object> variation : variations) {
			List<TypeConstraint> result = new ArrayList<>();
			Iterator<Object> iter = variation.iterator();
			while (iter.hasNext()) {
				result.add(new TypeConstraint((TypeArgument) iter.next(), (TypeArgument) iter.next(),
						(Variance) iter.next()));
			}
			listOfListOfConstraints.add(result);
		}

		return listOfListOfConstraints;
	}

	/**
	 * Same as {@link #createCaptureVariations(RuleEnvironment, Collection, boolean)} with avoidClosedCaptures ===
	 * false.
	 */
	public <T> List<List<T>> createCaptureVariations(RuleEnvironment G, Collection<T> objects) {
		return createCaptureVariations(G, objects, false);
	}

	/**
	 * Creates variations of the given collection by capturing and/or reopening any contained wildcards. All
	 * combinations of wildcard modifications will be returned and elements of the given collection that are not of type
	 * Wildcard will simply be passed through to the result.
	 * <p>
	 * For example, given a list <code>[w1, w2, "other"]</code> with w1, w2 being wildcards, this method will return the
	 * following list of lists:
	 *
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
	 *
	 * and if avoidClosedCaptures is set it will return:
	 *
	 * <pre>
	 * [
	 *     [w1,                     w2,                     "other"],
	 *     [reopened capture of w1, w2,                     "other"],
	 *     [w1,                     reopened capture of w2, "other"],
	 *     [reopened capture of w1, reopened capture of w2, "other"]
	 * ]
	 * </pre>
	 */
	public <T> List<List<T>> createCaptureVariations(RuleEnvironment G, Collection<T> objects,
			boolean avoidClosedCaptures) {

		int numOfVariationsPerWildcard = avoidClosedCaptures ? 2 : 3;
		int numOfWildcards = IterableExtensions.size(IterableExtensions.filter(objects, Wildcard.class));
		long numOfTotalVariations = Math.round(Math.pow(numOfVariationsPerWildcard, numOfWildcards));

		List<List<T>> result = new ArrayList<>();
		DigitIterator iter = new DigitIterator(0, numOfVariationsPerWildcard);
		while (iter.getValue() < numOfTotalVariations) {
			Iterable<T> captureVariation = createCaptureVariation(G, objects, iter, avoidClosedCaptures);
			List<T> variation = IterableExtensions.toList(captureVariation);
			result.add(variation);
			iter.inc();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private <T> Iterable<T> createCaptureVariation(RuleEnvironment G, Iterable<T> objects, DigitIterator iter,
			boolean avoidClosedCaptures) {
		return IterableExtensions.map(objects, obj -> {
			if (obj instanceof Wildcard) {
				Wildcard wildcard = (Wildcard) obj;
				int digit = iter.nextDigit();
				if (digit == 0) {
					return obj;
				} else if (digit == 1 && !avoidClosedCaptures) {
					return (T) capture(wildcard);
				} else {
					return (T) captureAndReopen(G, wildcard);
				}
			} else {
				return obj;
			}
		});
	}

	/***/
	public TypeRef capture(Wildcard wildcard) {
		ExistentialTypeRef captured = TypeUtils.captureWildcard(wildcard);
		return captured;
	}

	/***/
	public TypeRef captureAndReopen(RuleEnvironment G, Wildcard wildcard) {
		TypeRef captured = capture(wildcard);
		TypeRef reopened = reopenExistentialTypes(G, captured);
		return reopened;
	}

	/***/
	public TypeRef reopenExistentialTypes(RuleEnvironment G, TypeRef typeRef) {
		return (TypeRef) reopenExistentialTypes(G, (TypeArgument) typeRef);
	}

	/***/
	public TypeArgument reopenExistentialTypes(RuleEnvironment G, TypeArgument typeArg) {
		boolean isOrContainsClosedExistential = isClosedExistentialTypeRef(typeArg)
				|| IteratorExtensions.exists(typeArg.eAllContents(), elem -> isClosedExistentialTypeRef(elem));

		if (isOrContainsClosedExistential) {
			TypeArgument cpy = TypeUtils.copy(typeArg);
			if (cpy instanceof ExistentialTypeRef) {
				((ExistentialTypeRef) cpy).setReopened(true);
			}
			for (ExistentialTypeRef ref : IterableExtensions.filter(IteratorExtensions.toIterable(cpy.eAllContents()),
					ExistentialTypeRef.class)) {
				ref.setReopened(true);
			}
			return cpy;
		}
		return typeArg;
	}

	private static boolean isClosedExistentialTypeRef(EObject eObj) {
		return (eObj instanceof ExistentialTypeRef) ? !((ExistentialTypeRef) eObj).isReopened() : false;
	}
}
