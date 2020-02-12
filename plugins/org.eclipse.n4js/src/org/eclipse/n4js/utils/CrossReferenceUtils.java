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

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.collect.Sets;

/**
 * Utilities for handling cross-references in the AST, e.g. {@link IdentifierRef}s.
 * <p>
 * For utilities related to "find references" functionality see {@link FindReferenceHelper}.
 */
public class CrossReferenceUtils {

	public static TModule getTargetModule(IdentifierRef idRef) {
		IdentifiableElement target = idRef.getId();
		if (target == null || target.eIsProxy()) {
			return null;
		}

		boolean isDefSiteStructInterface = target instanceof TInterface
				&& TypeUtils.isStructural(((TInterface) target).getTypingStrategy());
		boolean isStringBasedEnum = target instanceof TEnum
				&& AnnotationDefinition.STRING_BASED.hasAnnotation((TEnum) target);
		if (isDefSiteStructInterface || isStringBasedEnum) {
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

	public static Set<TModule> getLTDXsOf(TModule module) {
		Set<TModule> ltdxs = new HashSet<>();
		Set<TModule> cyclicModules = getAllCyclicRunTimeDependentModules(module);
		for (TModule cyclicModule : cyclicModules) {
			if (cyclicModule.getDependenciesLoadTimeForInheritance().contains(module)) {
				ltdxs.add(cyclicModule);
			}
		}
		// if (!ltdxs.isEmpty()) {
		// System.out.println(module.getQualifiedName() + ": "
		// + ltdxs.stream().map(m -> m.getQualifiedName()).collect(Collectors.joining(", ")));
		// }
		return ltdxs;
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
				}
			}
		}
	}

	public static boolean dependsOn(TModule thisModule, TModule candidate) {
		return dependsOnAny(
				thisModule,
				Collections.singleton(candidate),
				false,
				null,
				null);
	}

	public static boolean dependsOnAny(TModule thisModule, Set<TModule> candidates, boolean bePessimistic,
			Set<URI> onlyConsiderTheseURIs, IResourceDescriptions index) {
		if (onlyConsiderTheseURIs != null) {
			// early check whether the candidates stem from the same project as the requested thisURI
			// (note: this is based on the assumption that there cannot be a cyclic dependency between modules of
			// different projects, because cyclic dependencies between projects are disallowed)
			candidates.retainAll(onlyConsiderTheseURIs);
		}
		// are there any relevant candidates at all?
		if (candidates.isEmpty()) {
			return false;
		}
		// Keep track of all visited URIs
		final Set<TModule> visited = Sets.newHashSet();
		// breadth first search since it is more likely to find resources from the same project
		// in our own dependencies rather than in the transitive dependencies
		final Queue<TModule> queue = new ArrayDeque<>();
		// the starting point. It is deliberately not added to the visited resources
		// to allow to detect cycles.
		queue.add(thisModule);
		while (!queue.isEmpty()) {
			// try to find the direct dependencies for the next URI in the queue
			Optional<List<TModule>> dependencies = readDirectRunTimeDependencies(index, queue.poll());
			if (!dependencies.isPresent()) {
				if (bePessimistic) {
					// none found - be pessimistic and announce a dependency
					return true;
				}
				continue;
			}
			// traverse the direct dependencies
			for (TModule dependency : dependencies.get()) {
				// mark the dependency as visited and if its the first occurrence
				if (visited.add(dependency)) {
					// are we only interested in the project local dependency graph?
					// or does the initial URI and the current candidate stem from the same project?
					if (onlyConsiderTheseURIs == null || onlyConsiderTheseURIs.contains(dependency)) {
						// it is part of the interesting resources, return true
						if (candidates.contains(dependency)) {
							return true;
						}
						// enque the dependency
						queue.add(dependency);
					}
				}
			}
		}
		// the entire relevant graph was successfully traversed. There is no transitive dependency
		// to one of the candidates
		return false;
	}

	private static Optional<List<TModule>> readDirectRunTimeDependencies(IResourceDescriptions index, TModule next) {
		return Optional.of(next.getDependenciesRunTime());
	}
}
