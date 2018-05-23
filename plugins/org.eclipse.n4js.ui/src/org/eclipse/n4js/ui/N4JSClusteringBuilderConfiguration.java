/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ui.building.BuilderStateLogger;
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.building.N4JSBuildTypeTrackingBuilder;
import org.eclipse.n4js.ui.building.N4JSGenerateImmediatelyBuilderState;
import org.eclipse.n4js.ui.containers.N4JSStorage2UriMapper;
import org.eclipse.n4js.ui.editor.PrevStateAwareDirtyStateManager;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.xtext.builder.builderState.PersistedStateProvider;
import org.eclipse.xtext.builder.clustering.ClusteringBuilderState;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.XtextBuilder;
import org.eclipse.xtext.resource.clustering.DynamicResourceClusteringPolicy;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.ui.editor.DirtyStateManager;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;

import com.google.inject.AbstractModule;

/**
 * Enables the dynamic clustering in the Xtext builder. As soon as the amount of available HEAP is smaller than a
 * certain threshold, the resource set if cleared to release memory.
 * <p>
 * N4JSClusteringBuilderConfiguration is registered in plugin.xml of the N4JS UI module to override the existing Google
 * Guice shared configuration.
 */
@SuppressWarnings("restriction")
public class N4JSClusteringBuilderConfiguration extends AbstractModule {

	private static final Logger LOGGER = Logger.getLogger(N4JSClusteringBuilderConfiguration.class);

	@Override
	protected void configure() {
		bind(IResourceClusteringPolicy.class).to(N4JSVerboseClusteringPolicy.class);
		bind(XtextBuilder.class).to(N4JSBuildTypeTrackingBuilder.class);
		bind(ClusteringBuilderState.class).to(N4JSGenerateImmediatelyBuilderState.class);
		bind(IStorage2UriMapper.class).to(N4JSStorage2UriMapper.class);
		bind(PersistedStateProvider.class).to(ContributingResourceDescriptionPersister.class);
		bind(IBuildLogger.class).annotatedWith(BuilderState.class).to(BuilderStateLogger.class);
		bind(DirtyStateManager.class).to(PrevStateAwareDirtyStateManager.class);
	}

	static class N4JSVerboseClusteringPolicy extends DynamicResourceClusteringPolicy {

		@Override
		public boolean continueProcessing(ResourceSet resourceSet, URI next, int alreadyProcessed) {
			if (alreadyProcessed < 100) {
				return true;
			}
			return super.continueProcessing(resourceSet, next, alreadyProcessed);
		}

		@Override
		protected void logClusterCapped(ResourceSet resourceSet, int alreadyProcessed, long freeMemory,
				long totalMemory) {

			int loadedResources = resourceSet.getResources().size();
			String msg = "";
			msg += "Cluster capped at " + alreadyProcessed + '/' + loadedResources + " processed/loaded resources; ";
			msg += (freeMemory >> 20) + "/" + (totalMemory >> 20) + " free/total MB memory";
			LOGGER.info(msg);
		}
	}

}
