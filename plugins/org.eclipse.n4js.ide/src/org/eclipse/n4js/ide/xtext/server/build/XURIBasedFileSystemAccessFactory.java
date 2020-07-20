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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.xtext.generator.IContextualOutputConfigurationProvider;
import org.eclipse.xtext.generator.IFilePostProcessor;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.URIBasedFileSystemAccess;
import org.eclipse.xtext.generator.trace.TraceFileNameProvider;
import org.eclipse.xtext.generator.trace.TraceRegionSerializer;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IProjectConfigProvider;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/** Creates an {@link IFileSystemAccess file system access} that is backed by a {@link URIConverter}. */
@SuppressWarnings("restriction")
@Singleton
class XURIBasedFileSystemAccessFactory {
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

	/** Create a new URIBasedFileSystemAccess. */
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
			IProjectConfig projectConfig = this.projectConfigProvider.getProjectConfig(resource.getResourceSet());
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