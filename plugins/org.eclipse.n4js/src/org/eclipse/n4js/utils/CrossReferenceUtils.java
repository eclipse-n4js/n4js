/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Utilities for handling cross-references in the AST, e.g. {@link IdentifierRef}s.
 * <p>
 * For utilities related to "find references" functionality see {@link FindReferenceHelper}.
 */
public class CrossReferenceUtils {

	public static TModule getTargetModule(IdentifierRef idRef, JavaScriptVariantHelper javaScriptVariantHelper) {
		IdentifiableElement target = idRef.getId();
		if (target == null || target.eIsProxy()) {
			return null;
		}

		boolean isNonN4JSInterfaceInN4JSD = target instanceof TInterface
				&& javaScriptVariantHelper.isExternalMode(target)
				&& !AnnotationDefinition.N4JS.hasAnnotation((TInterface) target);
		boolean isStringBasedEnum = target instanceof TEnum
				&& AnnotationDefinition.STRING_BASED.hasAnnotation((TEnum) target);
		if (isNonN4JSInterfaceInN4JSD || isStringBasedEnum) {
			return null;
		}

		TModule targetModule = EcoreUtil2.getContainerOfType(target, TModule.class);
		if (targetModule != null) {
			return targetModule;
		}

		// references to variables within the same module don't point to the TModule but to the variable declaration in
		// the AST, so we need the following additional check:
		Script targetScript = EcoreUtil2.getContainerOfType(target, Script.class);
		if (targetScript != null) {
			return targetScript.getModule();
		}

		return null;
	}

	public static Set<TModule> getAllCyclicRunTimeDependentModules(TModule module) {
		Set<TModule> result = new HashSet<>();
		collectCyclicRunTimeDependentModules(module, module.getDependenciesRunTime(), new HashSet<>(), new HashSet<>(),
				result);
		return result;
	}

	public static void collectCyclicRunTimeDependentModules(TModule start, Collection<TModule> next,
			Set<TModule> visited, Set<TModule> currPath, Set<TModule> addHere) {
		for (TModule curr : next) {
			if (curr == start) {
				addHere.addAll(currPath);
			} else {
				if (visited.add(curr)) {
					currPath.add(curr);
					try {
						collectCyclicRunTimeDependentModules(start, curr.getDependenciesRunTime(), visited, currPath,
								addHere);
					} finally {
						currPath.remove(curr);
					}
				} else {
					if (addHere.contains(curr)) {
						addHere.addAll(currPath);
					}
				}
			}
		}
	}
}
