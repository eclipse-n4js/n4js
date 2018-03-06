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
package org.eclipse.n4js.n4idl

import org.eclipse.n4js.ts.types.BuiltInType
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.Type

/**
 * A {@link TypeDistanceComputer} allows to compute the height and difference of types.
 */
class TypeDistanceComputer {
	/**
	 * Computes the height of the given {@link Type}.
	 */
	public def int computeHeight(Type t) {
		switch (t) {
			BuiltInType:	return 1 // > any
			TEnum:			return 2 // > TEnum > any 
			PrimitiveType: 	return 1 // > any
			TClassifier: 	return computeHeight(t)
			default:
				throw new UnsupportedOperationException("There is currently no rule" + 
					"to determine the height of type " + t)
		}
	}
	
	/**
	 * Computes the height of the given {@link TClassifier}.
	 */
	public def int computeHeight(TClassifier classifier) {
		if (classifier.superClassifierRefs.empty) {
			if (classifier instanceof TClass || classifier instanceof TInterface) {
				// for TClass and TInterface instances, there is three more levels (> N4Object > Object > any)
				return 3;
			} else { // other classifiers inherit directly from Object, thus their height is 2
				return 2; // > Object > any
			}
		} else if (classifier instanceof TClass) {
			// for TClasses the type height is that of the super class +1
			return computeHeight(classifier.superClass) + 1;
		} else {
			// follow all super-classifier references and return the  minimum 
			// distance to the top as height
			return classifier.superClassifierRefs.map[ superRef |
				computeHeight(superRef.declaredType)
			].min + 1
		}
	}
	
	/** Computes the height vector for the given list of types. */
	public def Iterable<Integer> computeHeight(Iterable<Type> types) {
		return types.map[computeHeight(it)].toList;
	}
	
	/** Computes the norm of the given type height vector. */
	public def double computeNorm(Iterable<Integer> heightVector) {
		return Math.sqrt(heightVector.map[Math.pow(it, 2)].reduce[p1, p2| p1 + p2]);
	}
	
	/**
	 * Returns the distance between t1 and t2 in terms of type height.
	 */
	public def int computeDifference(Type t1, Type t2) {
		return computeHeight(t1) - computeHeight(t2);
	} 
}