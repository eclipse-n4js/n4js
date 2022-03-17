/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.postprocessing.N4JSPostProcessor;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.xtext.ide.server.XLanguageServerImpl;
import org.eclipse.n4js.xtext.ide.server.build.ProjectBuilder;
import org.eclipse.n4js.xtext.ide.server.build.WorkspaceAwareResourceSet;
import org.eclipse.n4js.xtext.ide.server.build.XBuildContext;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest;
import org.eclipse.n4js.xtext.ide.server.build.XStatefulIncrementalBuilder;
import org.eclipse.n4js.xtext.ide.server.build.XWorkspaceManager;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.IFileSystemScanner;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * N4JS-specific adjustments to {@link XLanguageServerImpl}.
 */
public class N4JSStatefulIncrementalBuilder extends XStatefulIncrementalBuilder {

	static class AdjustedBuildRequest extends XBuildRequest {
		final XBuildRequest delegate;
		final ImmutableCollection<URI> dirtyFiles;
		final ImmutableCollection<URI> deletedFiles;

		AdjustedBuildRequest(XBuildRequest delegate) {
			this(delegate, null, null);
		}

		AdjustedBuildRequest(XBuildRequest delegate, Collection<URI> dirtyFiles, Collection<URI> deletedFiles) {
			super(delegate.getProjectName());
			this.delegate = delegate;
			this.dirtyFiles = dirtyFiles == null ? null : ImmutableList.copyOf(dirtyFiles);
			this.deletedFiles = deletedFiles == null ? null : ImmutableList.copyOf(deletedFiles);
		}

		@Override
		public Collection<URI> getDirtyFiles() {
			if (dirtyFiles == null) {
				return super.getDirtyFiles();
			}
			return this.dirtyFiles;
		}

		@Override
		public void setDirtyFiles(Collection<URI> dirtyFiles) {
			throw new IllegalStateException("no!");
		}

		@Override
		public Collection<URI> getDeletedFiles() {
			if (deletedFiles == null) {
				return super.getDeletedFiles();
			}
			return this.deletedFiles;
		}

		@Override
		public void setDeletedFiles(Collection<URI> deletedFiles) {
			throw new IllegalStateException("no!");
		}
	}

	@Inject
	XWorkspaceManager workspaceManager;

	@Inject
	IFileSystemScanner fileSystemScanner;

	/**
	 * Never unload built-in resources for performance considerations.
	 */
	@Override
	protected void unloadResource(URI uri) {
		if (!N4Scheme.isN4Scheme(uri)) {
			super.unloadResource(uri);
		}
	}

	/**
	 * This override introduces two changes:
	 * <p>
	 * (1) on initial/full builds, all uris are sorted.
	 * <p>
	 * (2) on closure builds, the build request is adjusted so that dirty/deleted files always respect the URI closure.
	 */
	@Override
	protected XBuildRequest initializeBuildRequest(XBuildRequest initialRequest, XBuildContext context) {
		ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder(initialRequest.getProjectName());
		N4JSProjectConfigSnapshot projectConfig = (N4JSProjectConfigSnapshot) projectBuilder.getProjectConfig();
		ResourceDescriptionsData oldIndex = context.getOldIndex();
		boolean isInitialBuild = oldIndex.isEmpty() && initialRequest.getDeletedFiles().isEmpty();

		if (projectConfig.hasTsConfigBuildSemantic()) {
			// this is a closure build
			// adjust the build request: compute file closure and adjust the set of changed/deleted uris

			Multimap<URI, URI> dependsOn = LinkedHashMultimap.create();
			initDependencyMaps(initialRequest.getResourceSet(), projectConfig, projectConfig.getAllContents(),
					new LinkedHashSet<>(), dependsOn, LinkedHashMultimap.create());
			Collection<URI> startUris = projectConfig.computeStartUris(fileSystemScanner);
			startUris.addAll(initialRequest.getDirtyFiles());
			List<URI> sortedUriClosure = computeSortedUriClosure(startUris, dependsOn);

			if (isInitialBuild) {
				return new AdjustedBuildRequest(initialRequest, sortedUriClosure, null);
			} else {
				Set<URI> removedFromClosure = Sets.newHashSet(oldIndex.getAllURIs());
				removedFromClosure.removeAll(sortedUriClosure);
				Set<URI> addedToClosure = Sets.newHashSet(sortedUriClosure);
				addedToClosure.removeAll(oldIndex.getAllURIs());
				Set<URI> adjDeletedUris = new HashSet<>();
				adjDeletedUris.addAll(initialRequest.getDeletedFiles());
				adjDeletedUris.addAll(removedFromClosure);
				Set<URI> adjDirtyUris = new HashSet<>();
				adjDirtyUris.addAll(initialRequest.getDirtyFiles());
				adjDirtyUris.addAll(addedToClosure);
				return new AdjustedBuildRequest(initialRequest, adjDirtyUris, adjDeletedUris);
			}

		} else if (isInitialBuild) {
			// this is a normal initial build

			WorkspaceAwareResourceSet resourceSet = initialRequest.getResourceSet();
			List<URI> allUris = Lists.newArrayList(projectConfig.getAllContents());
			Set<URI> noDeps = new LinkedHashSet<>();
			Multimap<URI, URI> dependsOn = LinkedHashMultimap.create();
			Multimap<URI, URI> dependsOnInverse = LinkedHashMultimap.create();
			initDependencyMaps(resourceSet, projectConfig, allUris, noDeps, dependsOn, dependsOnInverse);
			List<URI> sortedUris = sortAllUris(noDeps, allUris, dependsOn, dependsOnInverse);
			return new AdjustedBuildRequest(initialRequest, sortedUris, null);

		} else {
			// this is a normal incremental build. Sorting is unnecessary (usually only one file changed).
			return initialRequest;
		}
	}

	private void initDependencyMaps(WorkspaceAwareResourceSet resourceSet, N4JSProjectConfigSnapshot pcs,
			Iterable<URI> allUris, Set<URI> noDeps, Multimap<URI, URI> dependsOn, Multimap<URI, URI> dependsOnInverse) {

		Map<URI, Resource> resourcesMap = new HashMap<>();
		Multimap<String, URI> moduleName2Result = HashMultimap.create();
		for (URI uri : allUris) {
			Resource resource = null;
			try {
				resource = resourceSet.getResource(uri, true);
			} catch (Exception e) {
				// ignore error during load
			}
			if (resource != null) {
				resourcesMap.put(uri, resource);

				SourceFolderSnapshot srcFolder = pcs.findSourceFolderContaining(uri);
				URI srcFolderUri = srcFolder.getPath();
				URI relUri = uri.deresolve(srcFolderUri);

				String moduleName = URIUtils.trimFileExtension(relUri).toFileString();
				moduleName2Result.put(moduleName, uri);
				noDeps.add(uri);
			}
		}

		String prjName = pcs.getName();

		for (URI uri : resourcesMap.keySet()) {
			Resource resource = resourcesMap.get(uri);
			for (EObject eobj : resource.getContents()) {
				if (eobj instanceof Script) {
					Script script = (Script) eobj;

					for (EObject topLevelStmt : script.getScriptElements()) {
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
								for (URI dependsOnResult : moduleName2Result.get(moduleSpecifier)) {
									dependsOn.put(uri, dependsOnResult);
									dependsOnInverse.put(dependsOnResult, uri);
								}
								noDeps.remove(uri);
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

	/**
	 * The given list of URIs is sorted by this method to improve the post processing of N4JS started by the
	 * {@link N4JSPostProcessor}. During post processing, other dependency resources will be processed too in a
	 * recursive fashion. However, by sorting URIs beforehand here, that will not be necessary since dependencies
	 * already have been processed. Note however that in case of dependency cycles, sorting cannot avoid the recursive
	 * post processing of dependencies.
	 */
	private List<URI> sortAllUris(Set<URI> noDeps, Collection<URI> allUris,
			Multimap<URI, URI> dependsOn, Multimap<URI, URI> dependsOnInverse) {

		List<URI> sortedResults = new ArrayList<>(allUris.size());
		while (sortedResults.size() < allUris.size()) {
			if (noDeps.isEmpty()) {
				// there exist dependency cycles
				URI randomCyclicResult = dependsOn.entries().iterator().next().getKey();
				noDeps.add(randomCyclicResult);
				Collection<URI> dependencies = dependsOn.removeAll(randomCyclicResult);
				for (URI dependency : dependencies) {
					dependsOnInverse.remove(dependency, randomCyclicResult);
				}
			}

			Iterator<URI> iter = noDeps.iterator();
			URI result = iter.next();
			iter.remove();
			sortedResults.add(result);

			Collection<URI> dependees = dependsOnInverse.removeAll(result);
			for (URI dependee : dependees) {
				dependsOn.remove(dependee, result);
				if (!dependsOn.containsKey(dependee)) {
					noDeps.add(dependee);
				}
			}
		}
		return sortedResults;
	}

	private List<URI> computeSortedUriClosure(Collection<URI> startUris, Multimap<URI, URI> dependsOn) {
		List<URI> sortedResults = new ArrayList<>(startUris.size());
		Set<URI> worklist = new HashSet<>();
		worklist.addAll(startUris);
		while (!worklist.isEmpty()) {
			Iterator<URI> iter = worklist.iterator();
			URI result = iter.next();
			iter.remove();
			sortedResults.add(result);

			worklist.addAll(dependsOn.get(result));
		}

		return Lists.reverse(sortedResults);
	}

}
