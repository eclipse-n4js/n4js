/**
 * Copyright (c) 2015, 2017 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.IContextualOutputConfigurationProvider;
import org.eclipse.xtext.generator.IContextualOutputConfigurationProvider2;
import org.eclipse.xtext.generator.IFilePostProcessor;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IShouldGenerate;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.URIBasedFileSystemAccess;
import org.eclipse.xtext.generator.trace.TraceFileNameProvider;
import org.eclipse.xtext.generator.trace.TraceRegionSerializer;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.clustering.DisabledClusteringPolicy;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.resource.persistence.IResourceStorageFacade;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.resource.persistence.StorageAwareResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IProjectConfigProvider;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
@SuppressWarnings("restriction")
public class XIncrementalBuilder {
	/**
	 * The result of the build. Encapsulates the new index state and the list of changes.
	 */
	public static class XResult {
		private final XIndexState indexState;

		private final List<IResourceDescription.Delta> affectedResources;

		/**
		 * Constructor.
		 */
		public XResult(XIndexState indexState, List<IResourceDescription.Delta> affectedResources) {
			super();
			this.indexState = indexState;
			this.affectedResources = affectedResources;
		}

		/**
		 * Getter.
		 */
		public XIndexState getIndexState() {
			return this.indexState;
		}

		/**
		 * Getter.
		 */
		public List<IResourceDescription.Delta> getAffectedResources() {
			return this.affectedResources;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((this.indexState == null) ? 0 : this.indexState.hashCode());
			return prime * result + ((this.affectedResources == null) ? 0 : this.affectedResources.hashCode());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			XIncrementalBuilder.XResult other = (XIncrementalBuilder.XResult) obj;
			if (this.indexState == null) {
				if (other.indexState != null)
					return false;
			} else if (!this.indexState.equals(other.indexState))
				return false;
			if (this.affectedResources == null) {
				if (other.affectedResources != null)
					return false;
			} else if (!this.affectedResources.equals(other.affectedResources))
				return false;
			return true;
		}

		@Override
		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("indexState", this.indexState);
			b.add("affectedResources", this.affectedResources);
			return b.toString();
		}

	}

	/**
	 * Builder instance that is bound to a single running build.
	 */
	public static class XInternalStatefulIncrementalBuilder {

		/**
		 * Creates an {@link IFileSystemAccess file system access} that is backed by a {@link URIConverter}.
		 */
		@Singleton
		public static class XURIBasedFileSystemAccessFactory {
			@Inject
			private IContextualOutputConfigurationProvider outputConfigurationProvider;

			@Inject
			private IFilePostProcessor postProcessor;

			@Inject(optional = true)
			private IEncodingProvider encodingProvider;

			@Inject
			private TraceFileNameProvider traceFileNameProvider;

			@Inject
			private TraceRegionSerializer traceRegionSerializer;

			@Inject(optional = true)
			private IProjectConfigProvider projectConfigProvider;

			/**
			 * Create a new URIBasedFileSystemAccess.
			 */
			public URIBasedFileSystemAccess newFileSystemAccess(Resource resource, XBuildRequest request) {
				URIBasedFileSystemAccess uriBasedFileSystemAccess = new URIBasedFileSystemAccess();
				uriBasedFileSystemAccess.setOutputConfigurations(IterableExtensions.toMap(
						this.outputConfigurationProvider.getOutputConfigurations(resource),
						OutputConfiguration::getName));
				uriBasedFileSystemAccess.setPostProcessor(this.postProcessor);
				if (this.encodingProvider != null) {
					uriBasedFileSystemAccess.setEncodingProvider(this.encodingProvider);
				}
				uriBasedFileSystemAccess.setTraceFileNameProvider(this.traceFileNameProvider);
				uriBasedFileSystemAccess.setTraceRegionSerializer(this.traceRegionSerializer);
				uriBasedFileSystemAccess.setGenerateTraces(true);
				uriBasedFileSystemAccess.setBaseDir(request.getBaseDir());
				if (this.projectConfigProvider != null) {
					IProjectConfig projectConfig = this.projectConfigProvider
							.getProjectConfig(resource.getResourceSet());
					if (projectConfig != null) {
						ISourceFolder sourceFolder = projectConfig.findSourceFolderContaining(resource.getURI());
						if (sourceFolder != null) {
							uriBasedFileSystemAccess.setCurrentSource(sourceFolder.getName());
						}
					}
				}
				uriBasedFileSystemAccess.setConverter(resource.getResourceSet().getURIConverter());
				return uriBasedFileSystemAccess;
			}
		}

		private XBuildContext context;

		private XBuildRequest request;

		@Inject
		private XIndexer indexer;

		@Inject
		private OperationCanceledManager operationCanceledManager;

		/**
		 * Unload a resource with the given URI.
		 */
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
		 */
		public XIncrementalBuilder.XResult launch() {
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
				request.getAfterValidate().afterValidate(source, Collections.emptyList());
				Map<URI, String> outputConfigs = newSource2GeneratedMapping.deleteSourceAndGetOutputConfigs(source);
				for (URI generated : outputConfigs.keySet()) {
					IResourceServiceProvider serviceProvider = context.getResourceServiceProvider(source);
					XtextResourceSet resourceSet = request.getResourceSet();
					Set<OutputConfiguration> configs = serviceProvider
							.get(IContextualOutputConfigurationProvider2.class).getOutputConfigurations(resourceSet);
					String configName = outputConfigs.get(generated);
					OutputConfiguration config = FluentIterable.from(configs)
							.firstMatch(it -> it.getName().equals(configName)).orNull();
					if (config != null && config.isCleanUpDerivedResources()) {
						try {
							resourceSet.getURIConverter().delete(generated,
									CollectionLiterals.emptyMap());
							request.getAfterDeleteFile().apply(generated);
						} catch (IOException e) {
							Exceptions.sneakyThrow(e);
						}
					}
				}
			}
			XIndexer.XIndexResult result = indexer.computeAndIndexAffected(request, context);
			operationCanceledManager.checkCanceled(request.getCancelIndicator());

			List<IResourceDescription.Delta> resolvedDeltas = new ArrayList<>();
			for (IResourceDescription.Delta delta : result.getResourceDeltas()) {
				URI uri = delta.getUri();
				if (delta.getOld() != null && unloaded.add(uri)) {
					unloadResource(uri);
				}
				if (delta.getNew() == null) {
					resolvedDeltas.add(delta);
				}
			}

			Iterable<IResourceDescription.Delta> deltas = context.executeClustered(
					FluentIterable.from(result.getResourceDeltas()).filter((it) -> it.getNew() != null)
							.transform(Delta::getUri),
					(resource) -> {
						CancelIndicator cancelIndicator = request.getCancelIndicator();
						operationCanceledManager.checkCanceled(cancelIndicator);
						// trigger init
						resource.getContents();
						EcoreUtil2.resolveLazyCrossReferences(resource, CancelIndicator.NullImpl);
						operationCanceledManager.checkCanceled(cancelIndicator);
						IResourceServiceProvider serviceProvider = getResourceServiceProvider(resource);
						IResourceDescription.Manager manager = serviceProvider.getResourceDescriptionManager();
						IResourceDescription description = manager.getResourceDescription(resource);
						SerializableResourceDescription copiedDescription = SerializableResourceDescription
								.createCopy(description);
						result.getNewIndex().addDescription(resource.getURI(), copiedDescription);
						operationCanceledManager.checkCanceled(cancelIndicator);
						if ((!request.isIndexOnly() && validate(resource)
								&& serviceProvider.get(IShouldGenerate.class).shouldGenerate(resource,
										CancelIndicator.NullImpl))) {
							operationCanceledManager.checkCanceled(cancelIndicator);
							generate(resource, request, newSource2GeneratedMapping);
						}
						IResourceDescription old = context.getOldState().getResourceDescriptions()
								.getResourceDescription(resource.getURI());
						return manager.createDelta(old, copiedDescription);
					});

			Iterables.addAll(resolvedDeltas, deltas);
			return new XIncrementalBuilder.XResult(this.request.getState(), resolvedDeltas);
		}

		private IResourceServiceProvider getResourceServiceProvider(Resource resource) {
			if ((resource instanceof XtextResource)) {
				return ((XtextResource) resource).getResourceServiceProvider();
			}
			return context.getResourceServiceProvider(resource.getURI());
		}

		/**
		 * Validate the resource and return true, if the build should proceed for the current state.
		 */
		protected boolean validate(Resource resource) {
			IResourceValidator resourceValidator = getResourceServiceProvider(resource)
					.getResourceValidator();
			if (resourceValidator == null) {
				return true;
			}
			List<Issue> validationResult = resourceValidator.validate(resource, CheckMode.ALL,
					request.getCancelIndicator());
			return request.getAfterValidate().afterValidate(resource.getURI(), validationResult);
		}

		/**
		 * Generate code for the given resource
		 */
		@SuppressWarnings("hiding")
		protected void generate(Resource resource, XBuildRequest request,
				XSource2GeneratedMapping newMappings) {
			IResourceServiceProvider serviceProvider = getResourceServiceProvider(resource);
			GeneratorDelegate generator = serviceProvider.get(GeneratorDelegate.class);
			if (generator == null) {
				return;
			}
			Set<URI> previous = newMappings.deleteSource(resource.getURI());
			URIBasedFileSystemAccess fileSystemAccess = this.createFileSystemAccess(serviceProvider, resource);
			fileSystemAccess.setBeforeWrite((uri, outputCfgName, contents) -> {
				newMappings.addSource2Generated(resource.getURI(), uri, outputCfgName);
				previous.remove(uri);
				request.getAfterGenerateFile().apply(resource.getURI(), uri);
				return contents;
			});
			fileSystemAccess.setBeforeDelete((uri) -> {
				newMappings.deleteGenerated(uri);
				request.getAfterDeleteFile().apply(uri);
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
					resourceSet.getURIConverter().delete(noLongerCreated,
							CollectionLiterals.emptyMap());
					request.getAfterDeleteFile().apply(noLongerCreated);
				} catch (IOException e) {
					Exceptions.sneakyThrow(e);
				}
			}
		}

		/**
		 * Create a new file system access.
		 */
		protected URIBasedFileSystemAccess createFileSystemAccess(IResourceServiceProvider serviceProvider,
				Resource resource) {
			return serviceProvider.get(XURIBasedFileSystemAccessFactory.class).newFileSystemAccess(resource,
					this.request);
		}

		/**
		 * Getter
		 */
		protected XBuildContext getContext() {
			return this.context;
		}

		/**
		 * Setter
		 */
		protected void setContext(XBuildContext context) {
			this.context = context;
		}

		/**
		 * Getter
		 */
		protected XBuildRequest getRequest() {
			return this.request;
		}

		/**
		 * Setter
		 */
		protected void setRequest(XBuildRequest request) {
			this.request = request;
		}
	}

	@Inject
	private Provider<XIncrementalBuilder.XInternalStatefulIncrementalBuilder> provider;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Run the build without clustering.
	 */
	public XIncrementalBuilder.XResult build(XBuildRequest request,
			Function1<? super URI, ? extends IResourceServiceProvider> languages) {
		return build(request, languages, new DisabledClusteringPolicy());
	}

	/**
	 * Run the build.
	 */
	public XIncrementalBuilder.XResult build(XBuildRequest request,
			Function1<? super URI, ? extends IResourceServiceProvider> languages,
			IResourceClusteringPolicy clusteringPolicy) {
		XtextResourceSet resourceSet = request.getResourceSet();
		XIndexState oldState = new XIndexState(request.getState().getResourceDescriptions().copy(),
				request.getState().getFileMappings().copy());
		XBuildContext context = new XBuildContext(languages, resourceSet, oldState, clusteringPolicy,
				request.getCancelIndicator());
		XIncrementalBuilder.XInternalStatefulIncrementalBuilder builder = provider.get();
		builder.setContext(context);
		builder.setRequest(request);
		try {
			return builder.launch();
		} catch (Throwable t) {
			this.operationCanceledManager.propagateIfCancelException(t);
			throw t;
		}
	}
}
