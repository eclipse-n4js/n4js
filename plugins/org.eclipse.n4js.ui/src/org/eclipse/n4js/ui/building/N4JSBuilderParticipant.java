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
package org.eclipse.n4js.ui.building;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.uniqueIndex;
import static java.util.Collections.emptyMap;
import static org.eclipse.xtext.ui.util.ResourceUtil.getContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.generator.ICompositeGenerator;
import org.eclipse.n4js.ui.building.instructions.BuildInstruction;
import org.eclipse.n4js.ui.building.instructions.CleanInstruction;
import org.eclipse.n4js.ui.building.instructions.IBuildParticipantInstruction;
import org.eclipse.xtext.builder.BuilderParticipant;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * A custom builder participant that can be used by the {@link N4JSGenerateImmediatelyBuilderState} to process the
 * resource as soon as it was validated.
 * <p>
 * N4JSBuilderParticipant have to be bound in N4JSUiModule as XtextBuilder. It is responsible to generate the Javascript
 * files in the output directory. It mainly delegates to N4JSStatefulBuilderParticipant that it also configured
 * depending the build type (CLEAN, BUILD).
 * <p>
 * Adopted from {@link BuilderParticipant}.
 */
@SuppressWarnings("restriction")
@Singleton
public class N4JSBuilderParticipant extends BuilderParticipant {

	@Inject
	private Provider<EclipseResourceFileSystemAccess2> fileSystemAccessProvider;

	@Inject
	private IStorage2UriMapper storage2UriMapper;

	@Inject
	private Injector injector;

	@Inject
	private ICompositeGenerator compositeGenerator;

	/**
	 * Intentionally package visible producer for the {@link IBuildParticipantInstruction}.
	 *
	 * @param project
	 *            the currently build project
	 * @param buildType
	 *            the current build type
	 * @return a StatefulBuilderParticipant. Never <code>null</code>.
	 */
	IBuildParticipantInstruction prepareBuild(IProject project, IXtextBuilderParticipant.BuildType buildType)
			throws CoreException {

		if (!isEnabled(project)) {
			return IBuildParticipantInstruction.NOOP;
		}
		EclipseResourceFileSystemAccess2 access = fileSystemAccessProvider.get();
		access.setProject(project);
		final Map<String, OutputConfiguration> outputConfigurations = getOutputConfigurations(project);
		refreshOutputFolders(project, outputConfigurations, null);
		access.setOutputConfigurations(outputConfigurations);
		if (buildType == BuildType.CLEAN || buildType == BuildType.RECOVERY) {
			IBuildParticipantInstruction clean = new CleanInstruction(project, outputConfigurations,
					getDerivedResourceMarkers());
			if (buildType == BuildType.RECOVERY) {
				clean.finish(Collections.<Delta> emptyList(), null);
			} else {
				return clean;
			}
		}
		Map<OutputConfiguration, Iterable<IMarker>> generatorMarkers = getGeneratorMarkers(project,
				outputConfigurations.values());
		BuildInstruction buildInstruction = new BuildInstruction(project, outputConfigurations,
				getDerivedResourceMarkers(), access,
				generatorMarkers, storage2UriMapper, compositeGenerator, injector);
		return buildInstruction;
	}

	/**
	 * @see #isEnabled(org.eclipse.xtext.builder.IXtextBuilderParticipant.IBuildContext)
	 */
	protected boolean isEnabled(final IProject project) {
		return getBuilderPreferenceAccess().isAutoBuildEnabled(project);
	}

	/**
	 * @see #refreshOutputFolders(org.eclipse.xtext.builder.IXtextBuilderParticipant.IBuildContext, Map,
	 *      IProgressMonitor)
	 */
	protected void refreshOutputFolders(IProject project, Map<String, OutputConfiguration> outputConfigurations,
			IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, outputConfigurations.size());
		for (OutputConfiguration config : outputConfigurations.values()) {
			SubMonitor child = subMonitor.newChild(1);
			IContainer container = getContainer(project, config.getOutputDirectory());
			if (null != container) {
				container.refreshLocal(IResource.DEPTH_INFINITE, child);
			}
		}
	}

	/**
	 * @see #getOutputConfigurations(org.eclipse.xtext.builder.IXtextBuilderParticipant.IBuildContext)
	 */
	protected Map<String, OutputConfiguration> getOutputConfigurations(IProject project) {
		Set<OutputConfiguration> configurations = getOutputConfigurationProvider().getOutputConfigurations(project);
		return uniqueIndex(getNonNullOutputConfigurations(configurations), new Function<OutputConfiguration, String>() {
			@Override
			public String apply(OutputConfiguration from) {
				return from.getName();
			}
		});
	}

	private/* @Nonnull */
	Iterable<OutputConfiguration> getNonNullOutputConfigurations(Set<OutputConfiguration> configurations) {
		if (configurations == null) {
			return new HashSet<>();
		} else {
			return configurations;
		}
	}

	@Override
	public void build(IBuildContext context, IProgressMonitor monitor) throws CoreException {
		IBuildParticipantInstruction delegate = (IBuildParticipantInstruction) EcoreUtil.getAdapter(context
				.getResourceSet().eAdapters(), IBuildParticipantInstruction.class);
		if (delegate == null) {
			if (context.getBuildType() == BuildType.CLEAN) {
				super.build(context, monitor);
			}
		} else {
			delegate.finish(context.getDeltas(), monitor);
			if (delegate.isRebuild()) {
				context.needRebuild();
			}
		}
	}

	@Override
	protected void clearResourceSet(final ResourceSet resourceSet) {
		N4JSResourceSetCleanerUtils.clearResourceSet(resourceSet);
	}

	@Override
	protected Map<OutputConfiguration, Iterable<IMarker>> getGeneratorMarkers(IProject builtProject,
			Collection<OutputConfiguration> outputConfigurations) throws CoreException {

		if (builtProject instanceof ExternalProject) {
			return emptyMap();
		}

		Map<OutputConfiguration, Iterable<IMarker>> generatorMarkers = newHashMap();
		for (OutputConfiguration config : outputConfigurations) {
			if (config.isCleanUpDerivedResources()) {
				List<IMarker> markers = Lists.newArrayList();
				for (IContainer container : getOutputs(builtProject, config)) {
					Iterables.addAll(
							markers,
							getDerivedResourceMarkers().findDerivedResourceMarkers(container,
									getGeneratorIdProvider().getGeneratorIdentifier()));
				}
				generatorMarkers.put(config, markers);
			}
		}
		return generatorMarkers;
	}

}
