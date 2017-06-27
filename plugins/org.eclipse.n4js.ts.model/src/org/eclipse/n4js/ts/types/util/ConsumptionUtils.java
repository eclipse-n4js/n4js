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
package org.eclipse.n4js.ts.types.util;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.RecursionGuard;

/**
 * Utility methods for super interface, e.g., retrieving the path to an indirectly implemented interface. For other
 * member related issues such as which member is actually mixed in, see ContainerTypesHelper in n4js bundle.
 */
public class ConsumptionUtils {

	/**
	 * Returns the directly consumed (or otherwise inherited) classifier which indirectly provides the consumed member
	 * (or null, if interface is found). Usually the returned classifier is a TInterface.
	 *
	 * @param consumingClassifier
	 *            the consuming classifier
	 * @param consumedMember
	 *            the member consumed by the classifier
	 */
	public static TClassifier findInterfaceDefiningConsumedMember(TClassifier consumingClassifier,
			TMember consumedMember) {
		if (consumedMember == null || !(consumedMember.eContainer() instanceof TInterface)) {
			return null;
		}
		TInterface tinterface = (TInterface) consumedMember.eContainer();
		List<TClassifier> path = findPathToInterface(consumingClassifier, tinterface);
		if (path.isEmpty()) {
			return null;
		}
		return path.get(0);
	}

	/**
	 * Returns a path of all implemented interfaces from the given classifier to the interface, including the searched
	 * interface itself if found. E.g., for
	 *
	 * <pre>
	 * class A with R1{}
	 * interface R1 with R2{}
	 * interface R2 with R3{}
	 * </pre>
	 *
	 * the result for {@code findPathToInterface(A, R3)} would be {@code [R1,R2,R3]}.
	 */
	public static List<TClassifier> findPathToInterface(TClassifier from, TInterface toInterface) {
		List<ParameterizedTypeRef> superInterfaces = (from instanceof TClass) ? ((TClass) from)
				.getImplementedInterfaceRefs()
				: ((TInterface) from).getSuperInterfaceRefs();
				ConsumptionUtils finder = new ConsumptionUtils(toInterface);
				finder.doFindPathToInterface(superInterfaces);
				return finder.result;
	}

	RecursionGuard<TClassifier> guard = new RecursionGuard<>();
	List<TClassifier> result = new LinkedList<>();
	TInterface searchedInterface;

	private ConsumptionUtils(TInterface searchedInterface) {
		this.searchedInterface = searchedInterface;
	}

	private boolean doFindPathToInterface(List<ParameterizedTypeRef> superInterfaces) {
		if (superInterfaces.stream().anyMatch(ref -> ref.getDeclaredType() == searchedInterface)) {
			result.add(0, searchedInterface);
			return true;
		}
		for (ParameterizedTypeRef ptr : superInterfaces) {
			TInterface tInterface = (TInterface) ptr.getDeclaredType();
			if (guard.tryNext(tInterface)) {
				if (doFindPathToInterface(tInterface.getSuperInterfaceRefs())) {
					result.add(0, tInterface);
					return true;
				}
			}
		}
		return false;
	}

}
