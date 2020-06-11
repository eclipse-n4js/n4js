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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.EcoreUtil2;
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
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;

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
			XSource2GeneratedMapping newSource2GeneratedMapping = this.request.getState().getFileMappings();

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

			for (URI source : request.getDeletedFiles()) {
				request.setResultIssues(source, Collections.emptyList());
				removeGeneratedFiles(source, newSource2GeneratedMapping);
			}

			List<Delta> allProcessedAndExternalDeltas = new ArrayList<>(request.getExternalDeltas());

			ResourceDescriptionsData oldIndex = context.getOldState().getResourceDescriptions();
			Set<URI> remainingURIs = new LinkedHashSet<>(oldIndex.getAllURIs()); // note: creating a copy!

			XIndexer.XIndexResult result = indexer.computeAndIndexDeletedAndChanged(request, context);
			ResourceDescriptionsData newIndex = result.getNewIndex();
			List<Delta> deltasToBeProcessed = result.getResourceDeltas();
			List<Delta> deltasFromExternal = indexer.computeAndIndexAffected(newIndex, remainingURIs,
					request.getExternalDeltas(), allProcessedAndExternalDeltas, context);
			deltasToBeProcessed.addAll(deltasFromExternal);

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
						newDeltas.add(delta);
					} else {
						urisToBeBuilt.add(uri);
					}
					remainingURIs.remove(uri);
				}

				List<IResourceDescription.Delta> deltasBuilt = context.executeClustered(urisToBeBuilt,
						(resource) -> buildClustured(resource, newSource2GeneratedMapping, result));
				newDeltas.addAll(deltasBuilt);

				allProcessedDeltas.addAll(newDeltas);
				allProcessedAndExternalDeltas.addAll(newDeltas);

				// find more deltas to be processed (for affected resources)
				deltasToBeProcessed = indexer.computeAndIndexAffected(newIndex, remainingURIs, newDeltas,
						allProcessedAndExternalDeltas, context);
			}

		} catch (CancellationException e) {
			// catch CancellationException here and proceed normally to save already resolved deltas
			// (note: do not handle OperationCanceledException this way; it would break the builder, see GH-1775)
		}

		return new XBuildResult(this.request.getState(), allProcessedDeltas);
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
					request.setResultDeleteFile(generated);
				} catch (IOException e) {
					Exceptions.sneakyThrow(e);
				}
			}
		}
	}

	private Delta buildClustured(Resource resource,
			XSource2GeneratedMapping newSource2GeneratedMapping,
			XIndexer.XIndexResult result) {

		CancelIndicator cancelIndicator = request.getCancelIndicator();
		operationCanceledManager.checkCanceled(cancelIndicator);

		// trigger init
		resource.getContents();
		EcoreUtil2.resolveLazyCrossReferences(resource, CancelIndicator.NullImpl);
		operationCanceledManager.checkCanceled(cancelIndicator);

		URI source = resource.getURI();
		IResourceServiceProvider serviceProvider = getResourceServiceProvider(resource);
		IResourceDescription.Manager manager = serviceProvider.getResourceDescriptionManager();
		IResourceValidator resourceValidator = serviceProvider.getResourceValidator();

		IResourceDescription description = manager.getResourceDescription(resource);
		SerializableResourceDescription copiedDescription = SerializableResourceDescription.createCopy(description);
		result.getNewIndex().addDescription(source, copiedDescription);
		operationCanceledManager.checkCanceled(cancelIndicator);

		if (request.canValidate()) {
			List<Issue> issues = resourceValidator.validate(resource, CheckMode.ALL, request.getCancelIndicator());
			operationCanceledManager.checkCanceled(request.getCancelIndicator());
			request.setResultIssues(source, issues);
			boolean proceedGenerate = !request.containsValidationErrors(source);

			if (proceedGenerate) {
				operationCanceledManager.checkCanceled(cancelIndicator);
				generate(resource, newSource2GeneratedMapping, serviceProvider);
			} else {
				removeGeneratedFiles(resource.getURI(), newSource2GeneratedMapping);
			}
		}

		IResourceDescription old = context.getOldState().getResourceDescriptions().getResourceDescription(source);
		return manager.createDelta(old, copiedDescription);
	}

	private IResourceServiceProvider getResourceServiceProvider(Resource resource) {
		if ((resource instanceof XtextResource)) {
			return ((XtextResource) resource).getResourceServiceProvider();
		}
		return context.getResourceServiceProvider(resource.getURI());
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
			request.setResultGeneratedFile(source, uri);
			return contents;
		});
		fileSystemAccess.setBeforeDelete((uri) -> {
			newMappings.deleteGenerated(uri);
			request.setResultDeleteFile(uri);
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
		if (request.canGenerate()) {
			GeneratorContext generatorContext = new GeneratorContext();
			generatorContext.setCancelIndicator(request.getCancelIndicator());
			generator.generate(resource, fileSystemAccess, generatorContext);
			XtextResourceSet resourceSet = request.getResourceSet();
			for (URI noLongerCreated : previous) {
				try {
					resourceSet.getURIConverter().delete(noLongerCreated, CollectionLiterals.emptyMap());
					request.setResultDeleteFile(noLongerCreated);
				} catch (IOException e) {
					Exceptions.sneakyThrow(e);
				}
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
		IProjectConfig projectConfig = workspaceManager.getProjectConfig(resourceUri);
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