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
package org.eclipse.n4js.ide.xtext.server.build;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.n4js.ide.xtext.server.build.XClusteringStorageAwareResourceLoader.LoadResult;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.IContextualOutputConfigurationProvider2;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.generator.URIBasedFileSystemAccess;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.IResourceStorageFacade;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.resource.persistence.StorageAwareResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

/** Builder instance that is bound to a single running build. */
@SuppressWarnings("restriction")
public class XStatefulIncrementalBuilder {

	private XBuildContext context;

	private XBuildRequest request;

	@Inject
	private XIndexer indexer;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	/** Unload a resource with the given URI. */
	protected void unloadResource(URI uri) {
		XtextResourceSet resourceSet = this.request.getResourceSet();
		Resource resource = resourceSet.getResource(uri, false);
		if (resource != null) {
			resourceSet.getResources().remove(resource);
			// proxify
			resource.unload();
		}
	}

	/**
	 * Run the build.
	 * <p>
	 * Cancellation behavior: does not throw exception but returns with a partial result.
	 */
	public XBuildResult launch() {
		List<IResourceDescription.Delta> allProcessedDeltas = new ArrayList<>();

		try {
			XSource2GeneratedMapping newSource2GeneratedMapping = request.getFileMappings();

			Set<URI> unloaded = new HashSet<>();
			for (URI deleted : request.getDeletedFiles()) {
				if (unloaded.add(deleted)) {
					unloadResource(deleted);
				}
			}
			for (URI dirty : request.getDirtyFiles()) {
				if (unloaded.add(dirty)) {
					unloadResource(dirty);
				}
			}

			List<Delta> allProcessedAndExternalDeltas = new ArrayList<>(request.getExternalDeltas());

			ResourceDescriptionsData oldIndex = context.getOldIndex();
			Set<URI> remainingURIs = new LinkedHashSet<>(oldIndex.getAllURIs()); // note: creating a copy!

			XIndexer.XIndexResult result = indexer.computeAndIndexDeletedAndChanged(request, context);
			ResourceDescriptionsData newIndex = result.getNewIndex();
			List<Delta> deltasToBeProcessed = result.getResourceDeltas();
			List<Delta> affectedByExternal = indexer.computeAndIndexAffected(newIndex, remainingURIs,
					request.getExternalDeltas(), allProcessedAndExternalDeltas, context);
			affectedByExternal.forEach(delta -> request.afterDetectedAsAffected(delta.getUri()));
			// avoid duplicates in case resources reported as changed/deleted are also affected by external deltas:
			addIfNotYetPresent(deltasToBeProcessed, affectedByExternal);

			operationCanceledManager.checkCanceled(request.getCancelIndicator());

			// continue as long as there are more deltas to be processed (either the deltas representing the initial
			// deletions/changes or in later iterations the deltas representing affected resources)
			while (!deltasToBeProcessed.isEmpty()) {

				// process the deltas
				List<Delta> newDeltas = new ArrayList<>();
				List<URI> urisToBeBuilt = new ArrayList<>();
				for (IResourceDescription.Delta delta : deltasToBeProcessed) {
					URI uri = delta.getUri();
					if (delta.getOld() != null && unloaded.add(uri)) {
						unloadResource(uri);
					}
					if (delta.getNew() == null) {
						// deleted resources are not being built, thus add immediately to 'newDeltas'
						request.afterValidate(uri, Collections.emptyList());
						removeGeneratedFiles(uri, newSource2GeneratedMapping);
						newDeltas.add(delta);
					} else {
						urisToBeBuilt.add(uri);
					}
					remainingURIs.remove(uri);
				}

				List<IResourceDescription.Delta> deltasBuilt = context.executeClustered(urisToBeBuilt,
						(loadResult) -> buildClustered(loadResult, newSource2GeneratedMapping, result));
				newDeltas.addAll(deltasBuilt);

				allProcessedDeltas.addAll(newDeltas);
				allProcessedAndExternalDeltas.addAll(newDeltas);

				// find more deltas to be processed (for affected resources)
				deltasToBeProcessed = indexer.computeAndIndexAffected(newIndex, remainingURIs, newDeltas,
						allProcessedAndExternalDeltas, context);
				deltasToBeProcessed.forEach(delta -> request.afterDetectedAsAffected(delta.getUri()));
			}

		} catch (CancellationException e) {
			// catch CancellationException here and proceed normally to save already resolved deltas
			// (note: do not handle OperationCanceledException this way; it would break the builder, see GH-1775)
		}

		return new XBuildResult(request.getIndex(), request.getFileMappings(), allProcessedDeltas);
	}

	private void addIfNotYetPresent(List<Delta> addHere, List<Delta> toBeAdded) {
		Set<URI> presentURIs = addHere.stream().map(Delta::getUri).collect(Collectors.toSet());
		for (Delta delta : toBeAdded) {
			if (!presentURIs.contains(delta.getUri())) {
				addHere.add(delta);
			}
		}
	}

	private void removeGeneratedFiles(URI source, XSource2GeneratedMapping source2GeneratedMapping) {
		Map<URI, String> outputConfigMap = source2GeneratedMapping.deleteSourceAndGetOutputConfigs(source);
		IResourceServiceProvider serviceProvider = context.getResourceServiceProvider(source);
		IContextualOutputConfigurationProvider2 outputConfigurationProvider = serviceProvider
				.get(IContextualOutputConfigurationProvider2.class);
		XtextResourceSet resourceSet = request.getResourceSet();
		Set<OutputConfiguration> outputConfigs = outputConfigurationProvider.getOutputConfigurations(resourceSet);
		Map<String, OutputConfiguration> outputConfigsMap = Maps.uniqueIndex(outputConfigs,
				OutputConfiguration::getName);
		URIConverter uriConverter = resourceSet.getURIConverter();
		for (URI generated : outputConfigMap.keySet()) {
			OutputConfiguration config = outputConfigsMap.get(outputConfigMap.get(generated));
			if (config != null && config.isCleanUpDerivedResources()) {
				try {
					uriConverter.delete(generated, CollectionLiterals.emptyMap());
					request.afterDelete(generated);
				} catch (IOException e) {
					Exceptions.sneakyThrow(e);
				}
			}
		}
	}

	/** Build the given resource. */
	protected Delta buildClustered(LoadResult loadResult,
			XSource2GeneratedMapping newSource2GeneratedMapping,
			XIndexer.XIndexResult result) {

		CancelIndicator cancelIndicator = request.getCancelIndicator();
		operationCanceledManager.checkCanceled(cancelIndicator);

		URI source = loadResult.uri;
		IResourceServiceProvider serviceProvider = getResourceServiceProvider(loadResult);
		IResourceDescription.Manager manager = serviceProvider.getResourceDescriptionManager();
		IResourceValidator resourceValidator = serviceProvider.getResourceValidator();

		Resource resource = loadResult.resource;

		if (resource == null) {
			// loading of resource failed
			if (loadResult.isFileNotFound()) {
				// a source file was renamed/deleted and we did not get a 'didChangeWatchedFiles' notification
				// OR the rename/delete happened while the build was in progress
				result.getNewIndex().removeDescription(source);
				request.afterValidate(source, Collections.emptyList());
				removeGeneratedFiles(source, newSource2GeneratedMapping);
				IResourceDescription old = context.getOldIndex().getResourceDescription(loadResult.uri);
				return manager.createDelta(old, null);
			}
			Throwables.throwIfUnchecked(loadResult.throwable);
			throw new WrappedException((Exception) loadResult.throwable);
		}

		// trigger init
		resource.getContents();
		EcoreUtil2.resolveLazyCrossReferences(resource, CancelIndicator.NullImpl);
		operationCanceledManager.checkCanceled(cancelIndicator);

		IResourceDescription description = manager.getResourceDescription(resource);
		SerializableResourceDescription copiedDescription = SerializableResourceDescription.createCopy(description);
		result.getNewIndex().addDescription(source, copiedDescription);
		operationCanceledManager.checkCanceled(cancelIndicator);

		if (request.canValidate()) {
			List<Issue> issues = resourceValidator.validate(resource, CheckMode.ALL, cancelIndicator);
			// next line required, because #validate() sometimes returns null when canceled:
			operationCanceledManager.checkCanceled(cancelIndicator);
			request.afterValidate(source, issues);

			boolean proceedGenerate = request.canGenerate() && !containsValidationErrors(issues);
			if (proceedGenerate) {
				operationCanceledManager.checkCanceled(cancelIndicator);
				generate(resource, newSource2GeneratedMapping, serviceProvider);
			} else {
				removeGeneratedFiles(resource.getURI(), newSource2GeneratedMapping);
			}
		}

		IResourceDescription old = context.getOldIndex().getResourceDescription(source);
		return manager.createDelta(old, copiedDescription);
	}

	/** @return true iff the given source has issues of severity ERROR */
	private boolean containsValidationErrors(List<Issue> issues) {
		for (Issue issue : issues) {
			Severity severity = issue.getSeverity();
			if (severity == Severity.ERROR) {
				return true;
			}
		}
		return false;
	}

	private IResourceServiceProvider getResourceServiceProvider(LoadResult loadResult) {
		if ((loadResult.resource instanceof XtextResource)) {
			return ((XtextResource) loadResult.resource).getResourceServiceProvider();
		}
		return context.getResourceServiceProvider(loadResult.uri);
	}

	/** Generate code for the given resource */
	protected void generate(Resource resource, XSource2GeneratedMapping newMappings,
			IResourceServiceProvider serviceProvider) {
		GeneratorDelegate generator = serviceProvider.get(GeneratorDelegate.class);
		if (generator == null) {
			return;
		}

		if (isResourceInOutputDirectory(resource, serviceProvider)) {
			return;
		}

		URI source = resource.getURI();
		Set<URI> previous = newMappings.deleteSource(source);
		URIBasedFileSystemAccess fileSystemAccess = this.createFileSystemAccess(serviceProvider, resource);
		fileSystemAccess.setBeforeWrite((uri, outputCfgName, contents) -> {
			newMappings.addSource2Generated(source, uri, outputCfgName);
			previous.remove(uri);
			request.afterGenerate(source, uri);
			return contents;
		});
		fileSystemAccess.setBeforeDelete((uri) -> {
			newMappings.deleteGenerated(uri);
			request.afterDelete(uri);
			return true;
		});
		fileSystemAccess.setContext(resource);
		if (request.isWriteStorageResources() && resource instanceof StorageAwareResource) {
			IResourceStorageFacade resourceStorageFacade = ((StorageAwareResource) resource)
					.getResourceStorageFacade();
			if (resourceStorageFacade != null) {
				resourceStorageFacade.saveResource((StorageAwareResource) resource, fileSystemAccess);
			}
		}

		GeneratorContext generatorContext = new GeneratorContext();
		generatorContext.setCancelIndicator(request.getCancelIndicator());
		generator.generate(resource, fileSystemAccess, generatorContext);
		XtextResourceSet resourceSet = request.getResourceSet();
		for (URI noLongerCreated : previous) {
			try {
				resourceSet.getURIConverter().delete(noLongerCreated, CollectionLiterals.emptyMap());
				request.afterDelete(noLongerCreated);
			} catch (IOException e) {
				Exceptions.sneakyThrow(e);
			}
		}
	}

	private boolean isResourceInOutputDirectory(Resource resource, IResourceServiceProvider serviceProvider) {
		XWorkspaceManager workspaceManager = serviceProvider.get(XWorkspaceManager.class);
		if (workspaceManager == null) {
			return false;
		}
		OutputConfigurationProvider outputConfProvider = serviceProvider.get(OutputConfigurationProvider.class);
		URI resourceUri = resource.getURI();
		ProjectConfigSnapshot projectConfig = workspaceManager.getProjectConfig(resourceUri);
		Set<OutputConfiguration> outputConfigurations = outputConfProvider.getOutputConfigurations(resource);
		URI projectBaseUri = projectConfig.getPath();
		Path resourcePath = URIUtils.toPath(resourceUri);

		for (OutputConfiguration outputConf : outputConfigurations) {
			for (String outputDir : outputConf.getOutputDirectories()) {
				URI outputUri = projectBaseUri.appendSegment(outputDir);
				Path outputPath = URIUtils.toPath(outputUri);
				if (resourcePath.startsWith(outputPath)) {
					return true;
				}
			}
		}
		return false;
	}

	/** Create a new file system access. */
	protected URIBasedFileSystemAccess createFileSystemAccess(IResourceServiceProvider serviceProvider,
			Resource resource) {

		XURIBasedFileSystemAccessFactory fsaFactory = serviceProvider.get(XURIBasedFileSystemAccessFactory.class);
		return fsaFactory.newFileSystemAccess(resource, this.request);
	}

	/** Getter */
	protected XBuildContext getContext() {
		return this.context;
	}

	/** Setter */
	protected void setContext(XBuildContext context) {
		this.context = context;
	}

	/** Getter */
	protected XBuildRequest getRequest() {
		return this.request;
	}

	/** Setter */
	protected void setRequest(XBuildRequest request) {
		this.request = request;
	}
}