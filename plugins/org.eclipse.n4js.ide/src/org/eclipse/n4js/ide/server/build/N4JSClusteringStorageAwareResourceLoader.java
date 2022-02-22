/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.build;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.ide.server.build.XBuildContext;
import org.eclipse.n4js.xtext.ide.server.build.XClusteringStorageAwareResourceLoader;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigAdapter;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 *
 */
public class N4JSClusteringStorageAwareResourceLoader extends XClusteringStorageAwareResourceLoader {

	@Override
	protected List<LoadResult> sort(XBuildContext context, List<LoadResult> results) {
		if (results.isEmpty()) {
			return results;
		}
		WorkspaceConfigSnapshot wcs = WorkspaceConfigAdapter.getWorkspaceConfig(context.getResourceSet());
		ProjectConfigSnapshot pcs = wcs.findProjectContaining(results.get(0).uri);
		if (pcs == null) {
			return results;
		}

		Set<LoadResult> noDeps = new LinkedHashSet<>();
		Multimap<LoadResult, LoadResult> dependsOn = HashMultimap.create();
		Multimap<LoadResult, LoadResult> dependsOnInverse = HashMultimap.create();
		initDependencyMaps(results, pcs, noDeps, dependsOn, dependsOnInverse);

		List<LoadResult> sortedResults = sortLoadResults(results, noDeps, dependsOn, dependsOnInverse);
		return sortedResults;
	}

	private void initDependencyMaps(List<LoadResult> results, ProjectConfigSnapshot pcs, Set<LoadResult> noDeps,
			Multimap<LoadResult, LoadResult> dependsOn, Multimap<LoadResult, LoadResult> dependsOnInverse) {

		Map<String, LoadResult> moduleName2Result = new HashMap<>();
		for (LoadResult result : results) {
			URI uri = result.resource.getURI();
			SourceFolderSnapshot srcFolder = pcs.findSourceFolderContaining(uri);
			URI srcFolderUri = srcFolder.getPath();
			URI relUri = uri.deresolve(srcFolderUri);

			String moduleName = URIUtils.trimFileExtension(relUri).toFileString();
			moduleName2Result.put(moduleName, result);
			noDeps.add(result);
		}

		String prjName = pcs.getName();

		for (LoadResult result : results) {
			for (EObject eobj : result.resource.getContents()) {
				if (eobj instanceof Script) {
					for (EObject topLevelStmt : eobj.eContents()) {
						if (topLevelStmt instanceof ImportDeclaration) {
							ImportDeclaration impDecl = (ImportDeclaration) topLevelStmt;
							String moduleSpecifier = impDecl.getModuleSpecifierAsText();

							boolean hasInnerProjectDependency = false;
							if (moduleName2Result.containsKey(moduleSpecifier)) {
								hasInnerProjectDependency = true;
							} else if (moduleSpecifier.startsWith(prjName)) {
								moduleSpecifier = moduleSpecifier.substring(prjName.length() + 1);

								if (moduleName2Result.containsKey(moduleSpecifier)) {
									hasInnerProjectDependency = true;
								}
							}
							if (hasInnerProjectDependency) {
								LoadResult dependsOnResult = moduleName2Result.get(moduleSpecifier);
								dependsOn.put(result, dependsOnResult);
								dependsOnInverse.put(dependsOnResult, result);
								noDeps.remove(result);
							}
						} else {

							// we know that all import statements are at the beginning of a file
							break;
						}
					}
				}
			}
		}
	}

	private List<LoadResult> sortLoadResults(List<LoadResult> results, Set<LoadResult> noDeps,
			Multimap<LoadResult, LoadResult> dependsOn, Multimap<LoadResult, LoadResult> dependsOnInverse) {

		List<LoadResult> sortedResults = new ArrayList<>(results.size());
		while (sortedResults.size() < results.size()) {
			if (noDeps.isEmpty()) {
				// there exist dependency cycles
				LoadResult randomCyclicResult = dependsOn.entries().iterator().next().getKey();
				noDeps.add(randomCyclicResult);
				Collection<LoadResult> dependencies = dependsOn.removeAll(randomCyclicResult);
				for (LoadResult dependency : dependencies) {
					dependsOnInverse.remove(dependency, randomCyclicResult);
				}
			}

			Iterator<LoadResult> iter = noDeps.iterator();
			LoadResult result = iter.next();
			iter.remove();
			sortedResults.add(result);

			Collection<LoadResult> dependees = dependsOnInverse.removeAll(result);
			for (LoadResult dependee : dependees) {
				dependsOn.remove(dependee, result);
				if (!dependsOn.containsKey(dependee)) {
					noDeps.add(dependee);
				}
			}
		}
		return sortedResults;
	}

}
