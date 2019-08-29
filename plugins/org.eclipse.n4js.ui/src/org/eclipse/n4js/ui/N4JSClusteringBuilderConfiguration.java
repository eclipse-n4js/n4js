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

import org.eclipse.n4js.ui.building.BuildScopeAwareParallelLoaderProvider;
import org.eclipse.n4js.ui.building.BuilderStateLogger;
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.building.DefaultSharedContributionOverridingRegistry;
import org.eclipse.n4js.ui.building.N4JSBuildTypeTrackingBuilder;
import org.eclipse.n4js.ui.building.N4JSGenerateImmediatelyBuilderState;
import org.eclipse.n4js.ui.building.N4JSMarkerUpdater;
import org.eclipse.n4js.ui.building.VerboseClusteringPolicy;
import org.eclipse.n4js.ui.containers.N4JSStorage2UriMapper;
import org.eclipse.n4js.ui.editor.PrevStateAwareDirtyStateManager;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.builderState.IMarkerUpdater;
import org.eclipse.xtext.builder.builderState.PersistedStateProvider;
import org.eclipse.xtext.builder.clustering.ClusteringBuilderState;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuilderStateDiscarder;
import org.eclipse.xtext.builder.impl.XtextBuilder;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.ui.editor.DirtyStateManager;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.ui.shared.internal.SharedStateContributionRegistryImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

/**
 * Enables the dynamic clustering in the Xtext builder. As soon as the amount of available HEAP is smaller than a
 * certain threshold, the resource set if cleared to release memory.
 * <p>
 * N4JSClusteringBuilderConfiguration is registered in plugin.xml of the N4JS UI module to override the existing Google
 * Guice shared configuration.
 */
@SuppressWarnings("restriction")
public class N4JSClusteringBuilderConfiguration extends AbstractModule {

	@Override
	protected void configure() {
		bind(IResourceClusteringPolicy.class).to(VerboseClusteringPolicy.class);
		bind(XtextBuilder.class).to(N4JSBuildTypeTrackingBuilder.class);
		bind(IBuilderState.class).to(N4JSGenerateImmediatelyBuilderState.class).in(Scopes.SINGLETON);
		bind(IMarkerUpdater.class).to(N4JSMarkerUpdater.class);
		bind(IStorage2UriMapper.class).to(N4JSStorage2UriMapper.class);
		bind(PersistedStateProvider.class).to(ContributingResourceDescriptionPersister.class);
		bind(IBuildLogger.class).annotatedWith(BuilderState.class).to(BuilderStateLogger.class);
		bind(DirtyStateManager.class).to(PrevStateAwareDirtyStateManager.class);
		bind(IResourceLoader.class).annotatedWith(
				Names.named(ClusteringBuilderState.RESOURCELOADER_GLOBAL_INDEX)).toProvider(
						new BuildScopeAwareParallelLoaderProvider());
		bind(BuilderStateDiscarder.class);
		bind(SharedStateContributionRegistryImpl.class).to(DefaultSharedContributionOverridingRegistry.class);
		binder().install(new MyReferenceSearchResultContentProviderCustomModule());
	}
}
