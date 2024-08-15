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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.postprocessing.N4JSPostProcessor;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.xtext.ide.server.XLanguageServerImpl;
import org.eclipse.n4js.xtext.ide.server.build.ProjectBuilder;
import org.eclipse.n4js.xtext.ide.server.build.WorkspaceAwareResourceSet;
import org.eclipse.n4js.xtext.ide.server.build.XBuildContext;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest;
import org.eclipse.n4js.xtext.ide.server.build.XBuildResult;
import org.eclipse.n4js.xtext.ide.server.build.XStatefulIncrementalBuilder;
import org.eclipse.n4js.xtext.ide.server.build.XWorkspaceManager;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Strings;
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

		AdjustedBuildRequest(XBuildRequest delegate, Collection<URI> dirtyFiles, Collection<URI> deletedFiles) {

			super(delegate.getProjectName(), delegate.getBaseDir(), dirtyFiles, deletedFiles,
					delegate.getExternalDeltas(), delegate.getIndex(),
					delegate.getResourceSet(), delegate.getFileMappings(),
					delegate.isGeneratorEnabled(), delegate.isValidatorEnabled(), delegate.isIndexOnly(),
					delegate.isWriteStorageResources());

			this.delegate = delegate;
		}

		@Override
		public void addAfterValidateListener(AfterValidateListener listener) {
			delegate.addAfterValidateListener(listener);
		}

		@Override
		public void afterValidate(URI source, List<? extends Issue> issues) {
			delegate.afterValidate(source, issues);
		}

		@Override
		public void addAfterGenerateListener(AfterGenerateListener listener) {
			delegate.addAfterGenerateListener(listener);
		}

		@Override
		public void afterGenerate(URI source, URI generated) {
			delegate.afterGenerate(source, generated);
		}

		@Override
		public void addAfterDeleteListener(AfterDeleteListener listener) {
			delegate.addAfterDeleteListener(listener);
		}

		@Override
		public void afterDelete(URI file) {
			delegate.afterDelete(file);
		}

		@Override
		public void addAffectedListener(AffectedListener listener) {
			delegate.addAffectedListener(listener);
		}

		@Override
		public void afterDetectedAsAffected(URI uri) {
			delegate.afterDetectedAsAffected(uri);
		}

		@Override
		public void addBeforeBuildFileListener(BeforeBuildFileListener listener) {
			delegate.addBeforeBuildFileListener(listener);
		}

		@Override
		public void beforeBuildFile(URI uri) {
			delegate.beforeBuildFile(uri);
		}

		@Override
		public void addAfterBuildFileListener(AfterBuildFileListener listener) {
			delegate.addAfterBuildFileListener(listener);
		}

		@Override
		public void afterBuildFile(URI uri) {
			delegate.afterBuildFile(uri);
		}

		@Override
		public void addAfterBuildRequestListener(AfterBuildRequestListener listener) {
			delegate.addAfterBuildRequestListener(listener);
		}

		@Override
		public void afterBuildRequest(XBuildResult buildResult) {
			delegate.afterBuildRequest(buildResult);
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
		if (N4Scheme.isN4Scheme(uri)) {
			// ignore
		} else {
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

			Collection<URI> allUris = projectConfig.getAllContents(fileSystemScanner);
			Collection<URI> startUris = projectConfig.computeStartUris(fileSystemScanner);
			startUris.addAll(initialRequest.getDirtyFiles());

			Multimap<String, URI> moduleName2Uri = getModuleName2UrisMap(projectConfig, allUris);
			List<URI> sortedUriClosure = computeSortedUriClosure(initialRequest.getResourceSet(),
					projectConfig, moduleName2Uri, startUris);

			if (isInitialBuild) {
				return new AdjustedBuildRequest(initialRequest, sortedUriClosure, null);
			} else {
				Set<URI> removedFromClosure = Sets.newLinkedHashSet(oldIndex.getAllURIs());
				removedFromClosure.removeIf(URIUtils::isVirtualResourceURI);
				removedFromClosure.removeAll(sortedUriClosure);
				Set<URI> addedToClosure = Sets.newLinkedHashSet(sortedUriClosure);
				addedToClosure.removeAll(oldIndex.getAllURIs());

				Set<URI> adjDeletedUris = new LinkedHashSet<>();
				adjDeletedUris.addAll(initialRequest.getDeletedFiles());
				adjDeletedUris.addAll(removedFromClosure);
				Set<URI> adjDirtyUris = new LinkedHashSet<>();
				adjDirtyUris.addAll(initialRequest.getDirtyFiles());
				adjDirtyUris.addAll(addedToClosure);
				return new AdjustedBuildRequest(initialRequest, adjDirtyUris, adjDeletedUris);
			}

		} else if (isInitialBuild) {
			// this is a normal initial build
			List<URI> allUris = initialRequest.getDirtyFiles();
			Multimap<String, URI> moduleName2Uri = getModuleName2UrisMap(projectConfig, allUris);
			List<URI> sortedUris = computeSortedUriClosure(initialRequest.getResourceSet(),
					projectConfig, moduleName2Uri, allUris);

			return new AdjustedBuildRequest(initialRequest, sortedUris, null);

		} else {
			// this is a normal incremental build. Sorting is unnecessary (usually only one file changed).
			return initialRequest;
		}
	}

	private Multimap<String, URI> getModuleName2UrisMap(N4JSProjectConfigSnapshot pcs, Iterable<URI> allUris) {
		Multimap<String, URI> moduleName2Uri = LinkedHashMultimap.create();
		for (URI uri : allUris) {
			SourceFolderSnapshot srcFolder = pcs.findSourceFolderContaining(uri);
			if (srcFolder != null) {
				URI srcFolderUri = srcFolder.getPath();
				URI relUri = uri.deresolve(srcFolderUri);

				String moduleName = URIUtils.trimFileExtension(relUri).toFileString();
				moduleName2Uri.put(moduleName, uri);
			}
		}
		return moduleName2Uri;
	}

	private Collection<URI> getImportedUris(WorkspaceAwareResourceSet resourceSet,
			N4JSProjectConfigSnapshot projectConfig,
			Multimap<String, URI> moduleName2Uri, URI uri) {

		List<URI> result = new ArrayList<>();
		List<ModuleRef> moduleRefs = getModuleRefsToOtherModules(resourceSet, uri);
		for (ModuleRef moduleRef : moduleRefs) {
			String moduleSpecifier = moduleRef.getModuleSpecifierAsText();
			String adjModuleSpecifier = getAdjustedModuleSpecifierOrNull(moduleSpecifier, projectConfig,
					moduleName2Uri);
			if (adjModuleSpecifier != null) {
				result.addAll(moduleName2Uri.get(adjModuleSpecifier));
			}
		}
		return result;
	}

	private List<ModuleRef> getModuleRefsToOtherModules(WorkspaceAwareResourceSet resourceSet, URI uri) {
		try (Measurement m = N4JSDataCollectors.dcModuleRefsToOtherModules.getMeasurement()) {
			List<ModuleRef> result = new ArrayList<>();
			Resource resource = null;
			try {
				resource = resourceSet.getResource(uri, true);
			} catch (Exception e) {
				// ignore error during load
			}
			if (resource == null) {
				return result;
			}

			if (resource instanceof N4JSResource) {
				N4JSResource n4res = (N4JSResource) resource;
				Script script = n4res.getScript();
				if (script != null && !script.eIsProxy()) {
					for (EObject topLevelStmt : script.getScriptElements()) {
						if (topLevelStmt instanceof ModuleRef) {
							ModuleRef impExpDecl = (ModuleRef) topLevelStmt;
							if (!impExpDecl.isReferringToOtherModule()) {
								continue;
							}
							if (impExpDecl.getModuleSpecifierAsText() == null) {
								continue;
							}
							result.add(impExpDecl);
						} else {

							// we know that all import statements are at the beginning of a file
							break;
						}
					}
				}
			}
			return result;
		}
	}

	private String getAdjustedModuleSpecifierOrNull(String moduleSpecifier, N4JSProjectConfigSnapshot projectConfig,
			Multimap<String, URI> moduleName2Uri) {

		ProjectDescription pd = projectConfig.getProjectDescription();
		String prjName = Strings.nullToEmpty(projectConfig.getPackageName());
		if (moduleName2Uri.containsKey(moduleSpecifier)) {
			return moduleSpecifier;
		} else if (moduleSpecifier.startsWith("./")) {
			moduleSpecifier = moduleSpecifier.substring(2); // remove './'
		} else if (moduleSpecifier.equals(prjName)) {
			moduleSpecifier = pd.getMainModule();
		} else if (moduleSpecifier.startsWith(prjName)) {
			moduleSpecifier = moduleSpecifier.substring(prjName.length() + 1);
		}

		if (moduleName2Uri.containsKey(moduleSpecifier)) {
			return moduleSpecifier;
		}

		URI msUri = URI.createFileURI(moduleSpecifier);
		String msWithoutExt = URIUtils.trimFileExtension(msUri).toString();
		if (moduleName2Uri.containsKey(msWithoutExt)) {
			return msWithoutExt;
		}
		return null;
	}

	/**
	 * The given list of URIs is sorted by this method to improve the post processing of N4JS started by the
	 * {@link N4JSPostProcessor}. During post processing, other dependency resources will be processed too in a
	 * recursive fashion. However, by sorting URIs beforehand here, that will not be necessary since dependencies
	 * already have been processed. Note however that in case of dependency cycles, sorting cannot avoid the recursive
	 * post processing of dependencies.
	 */
	private List<URI> computeSortedUriClosure(WorkspaceAwareResourceSet resourceSet,
			N4JSProjectConfigSnapshot projectConfig,
			Multimap<String, URI> moduleName2Uri, Collection<URI> startUris) {

		List<URI> sortedResults = new ArrayList<>(startUris.size());
		Set<URI> worklist = new LinkedHashSet<>();
		worklist.addAll(startUris);
		while (!worklist.isEmpty()) {
			Iterator<URI> iter = worklist.iterator();
			URI uri = iter.next();
			iter.remove();
			sortedResults.add(uri);

			Collection<URI> importedUris = getImportedUris(resourceSet, projectConfig, moduleName2Uri, uri);
			importedUris.removeAll(sortedResults);
			worklist.addAll(importedUris);
		}

		return Lists.reverse(sortedResults);
	}

}
