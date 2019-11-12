/**
 * Copyright (c) 2015, 2017 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.clustering.DisabledClusteringPolicy;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.service.OperationCanceledManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
@SuppressWarnings("restriction")
public class XIncrementalBuilder {

	/** All languages */
	@Inject
	protected IResourceServiceProvider.Registry languagesRegistry;

	@Inject
	private Provider<XStatefulIncrementalBuilder> provider;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	/** Run the build without clustering. */
	public XBuildResult build(XBuildRequest request) {
		return build(request, new DisabledClusteringPolicy());
	}

	/** Run the build. */
	public XBuildResult build(XBuildRequest request,
			IResourceClusteringPolicy clusteringPolicy) {

		ResourceDescriptionsData resDescrsCopy = request.getState().getResourceDescriptions().copy();
		XSource2GeneratedMapping fileMappingsCopy = request.getState().getFileMappings().copy();
		XIndexState oldState = new XIndexState(resDescrsCopy, fileMappingsCopy);

		XtextResourceSet resourceSet = request.getResourceSet();
		XBuildContext context = new XBuildContext(languagesRegistry::getResourceServiceProvider,
				resourceSet, oldState, clusteringPolicy, request.getCancelIndicator());

		XStatefulIncrementalBuilder builder = provider.get();
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
