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
package org.eclipse.n4js.ui.internal;

import static com.google.inject.Scopes.SINGLETON;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.BinariesProvider;
import org.eclipse.n4js.binaries.BinariesValidator;
import org.eclipse.n4js.binaries.BinaryCommandFactory;
import org.eclipse.n4js.binaries.OsgiBinariesPreferenceStore;
import org.eclipse.n4js.binaries.nodejs.NodeBinaryLocatorHelper;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.binaries.nodejs.NodeProcessBuilder;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.binaries.nodejs.NpmrcBinary;
import org.eclipse.n4js.external.EclipseTargetPlatformInstallLocationProvider;
import org.eclipse.n4js.external.ExternalLibraryUriHelper;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProjectCacheLoader;
import org.eclipse.n4js.external.ExternalProjectProvider;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.external.GitCloneSupplier;
import org.eclipse.n4js.external.RebuildWorkspaceProjectsScheduler;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.external.TypeDefinitionGitLocationProvider;
import org.eclipse.n4js.external.TypeDefinitionGitLocationProvider.TypeDefinitionGitLocationProviderImpl;
import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.OsgiExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.ui.containers.CompositeStorage2UriMapperContribution;
import org.eclipse.n4js.ui.containers.N4JSExternalLibraryStorage2UriMapperContribution;
import org.eclipse.n4js.ui.containers.N4JSToBeBuiltComputer;
import org.eclipse.n4js.ui.containers.NfarStorageMapper;
import org.eclipse.n4js.ui.external.EclipseExternalLibraryWorkspace;
import org.eclipse.n4js.ui.external.ExternalLibraryBuildJobProvider;
import org.eclipse.n4js.ui.external.ExternalLibraryBuilderHelper;
import org.eclipse.n4js.ui.external.ProjectStateChangeListener;
import org.eclipse.n4js.ui.navigator.N4JSProjectExplorerLabelProvider;
import org.eclipse.n4js.ui.navigator.internal.N4JSProjectExplorerHelper;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.scoping.builtin.ScopeInitializer;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBrokerImpl;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerModificationStrategyProvider;
import org.eclipse.n4js.ui.workingsets.WorkingSetManualAssociationWizard;
import org.eclipse.n4js.ui.workingsets.WorkingSetProjectNameFilterWizard;
import org.eclipse.n4js.ui.workingsets.WorkspaceRepositoriesProvider;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.process.OutputStreamPrinterThreadProvider;
import org.eclipse.n4js.utils.process.OutputStreamProvider;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.clustering.CurrentDescriptions;
import org.eclipse.xtext.builder.impl.IToBeBuiltComputerContribution;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.LiveShadowedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;
import org.eclipse.xtext.ui.resource.IResourceSetInitializer;
import org.eclipse.xtext.ui.resource.IStorage2UriMapperContribution;
import org.eclipse.xtext.ui.shared.contribution.IEagerContribution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 */
@SuppressWarnings("restriction")
public class ContributingModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(IToBeBuiltComputerContribution.class).to(N4JSToBeBuiltComputer.class);
		binder.bind(IStorage2UriMapperContribution.class).to(CompositeStorage2UriMapperContribution.class);
		binder.bind(IN4JSCore.class).to(IN4JSEclipseCore.class);
		binder.bind(IN4JSEclipseCore.class).to(N4JSEclipseCore.class);
		binder.bind(NfarStorageMapper.class);
		binder.bind(N4JSModel.class);
		binder.bind(InternalN4JSWorkspace.class).to(EclipseBasedN4JSWorkspace.class);
		binder.bind(EclipseBasedN4JSWorkspace.class);
		binder.bind(IWorkspaceRoot.class).toProvider(new Provider<IWorkspaceRoot>() {
			@Inject
			IWorkspace workspace;

			@Override
			public IWorkspaceRoot get() {
				return workspace.getRoot();
			}
		});
		binder.bind(StatusHelper.class);
		binder.bind(TargetPlatformInstallLocationProvider.class).to(EclipseTargetPlatformInstallLocationProvider.class)
				.in(SINGLETON);
		binder.bind(GitCloneSupplier.class).in(SINGLETON);
		binder.bind(TypeDefinitionGitLocationProvider.class).to(TypeDefinitionGitLocationProviderImpl.class)
				.in(SINGLETON);
		binder.bind(ExternalProjectCacheLoader.class);
		binder.bind(ExternalLibraryWorkspace.class).to(EclipseExternalLibraryWorkspace.class).in(SINGLETON);
		binder.bind(ProjectStateChangeListener.class);
		binder.bind(ExternalLibraryBuildJobProvider.class);
		binder.bind(ExternalLibraryBuilderHelper.class);
		binder.bind(N4JSExternalLibraryStorage2UriMapperContribution.class);
		binder.bind(ExternalProjectsCollector.class);
		binder.bind(ExternalProjectProvider.class);
		binder.bind(RebuildWorkspaceProjectsScheduler.class);
		binder.bind(N4JSEclipseModel.class);
		binder.bind(ExternalLibraryUriHelper.class);
		binder.bind(FileBasedExternalPackageManager.class);
		binder.bind(ExternalLibraryPreferenceStore.class).to(OsgiExternalLibraryPreferenceStore.class).in(SINGLETON);
		binder.bind(OsgiExternalLibraryPreferenceStore.class);
		binder.bind(XtextResourceSet.class);
		binder.bind(IEagerContribution.class).to(ProjectDescriptionLoadListener.class);
		binder.bind(ProjectDescriptionLoadListener.Strategy.class).to(N4MFProjectDependencyStrategy.class);
		binder.bind(IResourceSetInitializer.class).to(ScopeInitializer.class);
		binder.bind(ClassLoader.class).toInstance(getClass().getClassLoader());

		binder.bind(WorkingSetManagerBrokerImpl.class).in(SINGLETON);
		binder.bind(WorkingSetManagerBroker.class).to(WorkingSetManagerBrokerImpl.class).in(SINGLETON);
		binder.bind(WorkingSetManualAssociationWizard.class);
		binder.bind(WorkingSetManagerModificationStrategyProvider.class);
		binder.bind(WorkingSetProjectNameFilterWizard.class);
		binder.bind(N4JSProjectExplorerLabelProvider.class);
		binder.bind(N4JSProjectExplorerHelper.class);
		binder.bind(ObjectMapper.class);

		binder.bind(WorkspaceRepositoriesProvider.class).in(SINGLETON);

		binder.bind(ResourceDescriptionsProvider.class);
		binder.bind(ResourceSetBasedResourceDescriptions.class);
		binder.bind(IResourceDescriptions.class)
				.annotatedWith(Names.named(ResourceDescriptionsProvider.LIVE_SCOPE))
				.to(LiveShadowedResourceDescriptions.class);
		binder.bind(IResourceDescriptions.class)
				.annotatedWith(Names.named(ResourceDescriptionsProvider.NAMED_BUILDER_SCOPE))
				.to(CurrentDescriptions.ResourceSetAware.class);
		binder.bind(IResourceDescriptions.class)
				.annotatedWith(Names.named(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS))
				.to(IBuilderState.class);

		binder.bind(ProcessExecutor.class).in(SINGLETON);
		binder.bind(BinaryCommandFactory.class).in(SINGLETON);
		binder.bind(NodeProcessBuilder.class).in(SINGLETON);
		binder.bind(OutputStreamPrinterThreadProvider.class).in(SINGLETON);
		binder.bind(OutputStreamProvider.class);
		binder.bind(BinariesPreferenceStore.class).to(OsgiBinariesPreferenceStore.class).in(SINGLETON);
		binder.bind(BinariesValidator.class).in(SINGLETON);
		binder.bind(BinariesProvider.class).in(SINGLETON);

		binder.bind(NodeBinaryLocatorHelper.class);
		binder.bind(NodeJsBinary.class).in(SINGLETON);
		binder.bind(NpmBinary.class).in(SINGLETON);
		binder.bind(NpmrcBinary.class).in(SINGLETON);

	}

}
