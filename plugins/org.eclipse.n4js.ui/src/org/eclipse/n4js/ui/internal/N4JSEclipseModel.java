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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.internal.N4JSSourceContainerType;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseArchive;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseSourceContainer;
import org.eclipse.n4js.utils.resources.ExternalProject;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The eclipse specific implementation aspects of the N4JSModel.
 */
@SuppressWarnings("javadoc")
@Singleton
public class N4JSEclipseModel extends N4JSModel {

	private static final Logger LOGGER = Logger.getLogger(N4JSEclipseModel.class);

	private final IWorkspaceRoot workspace;

	@Inject
	public N4JSEclipseModel(EclipseBasedN4JSWorkspace workspace) {
		super(workspace);
		this.workspace = workspace.getWorkspace();
	}

	@Override
	public N4JSEclipseProject getN4JSProject(URI location) {
		checkArgument(
				location.isPlatformResource() || location.isFile(),
				"Expected either platform:/resource or file:/ URI. Was: " + location);

		if (location.isPlatformResource() && location.segmentCount() != DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) {
			checkArgument(
					DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT == location.segmentCount(),
					"Expected 2 segment counts for platform resource URI. Was " + location.segmentCount());
		}

		final String projectName = location.lastSegment();
		final IProject project;
		if (location.isFile()) {
			project = externalLibraryWorkspace.getProject(projectName);
			checkNotNull(project, "Project does not exist in external workspace. URI: " + location);
		} else {
			project = workspace.getProject(projectName);
		}

		return doGetN4JSProject(project, location);
	}

	@Override
	protected IN4JSProject newAbsentProject(String projectId) {
		final URI absent = URI.createPlatformResourceURI(projectId, false);
		return new N4JSEclipseProject(workspace.getProject(projectId), absent, this);
	}

	@Override
	public N4JSEclipseProject findProjectWith(URI nestedLocation) {
		return (N4JSEclipseProject) super.findProjectWith(nestedLocation);
	}

	@Override
	public Optional<? extends IN4JSSourceContainer> findN4JSSourceContainer(URI location) {

		Optional<? extends IN4JSSourceContainer> n4jsContainer = Optional.absent();
		if (!location.isArchive()) {

			if (N4Scheme.isN4Scheme(location)) {
				return n4jsContainer;
			}

			if (!location.isPlatformResource()) {

				// in case of compare views (git, resource compare,...) we get requests with schemes like
				// "revision:/Variadic1.js"
				// just do not throw exception then, but continue with null:
				if ("revision".equals(location.scheme())) {
					if (LOGGER.isDebugEnabled()) {
						final String message = "Got revision-scheme request, but refuse to find source-container for that:"
								+ location;
						LOGGER.debug(message);
					}
					return n4jsContainer;
				}

				if (location.isFile()) {
					final IN4JSEclipseProject eclipseProject = findProjectWith(location);
					if (null != eclipseProject && eclipseProject.exists()) {
						if (eclipseProject.getProject() instanceof ExternalProject) {
							final IResource resource = externalLibraryWorkspace.getResource(location);
							if (null != resource) {
								n4jsContainer = getN4JSSourceContainer(resource);
							}
						}
					}
				}

				return n4jsContainer;
			}

			final IN4JSEclipseProject project = findProjectWith(location);
			if (null != project && project.exists()) {
				final Path path = new Path(location.toPlatformString(true));
				final IResource resource;
				if (1 == path.segmentCount()) {
					resource = workspace.getProject(path.segment(0));
				} else {
					resource = workspace.getFile(path);
				}
				n4jsContainer = getN4JSSourceContainer(resource);
			}

		} else {
			String archiveFilePath = location.authority();
			URI archiveURI = URI.createURI(archiveFilePath.substring(0, archiveFilePath.length() - 1));
			N4JSEclipseProject containingProject = findProjectWith(archiveURI);
			N4JSEclipseArchive archive = getN4JSArchive(containingProject,
					workspace.getFile(new Path(archiveURI.toPlatformString(true))));
			n4jsContainer = findN4JSSourceContainerInArchive(location, archive);
		}
		return n4jsContainer;
	}

	public N4JSEclipseProject getN4JSProject(IProject project) {
		if (project instanceof ExternalProject) {
			return doGetN4JSProject(project, URI.createFileURI(new File(project.getLocationURI()).getAbsolutePath()));
		} else {
			return doGetN4JSProject(project, URI.createPlatformResourceURI(project.getName(), true));
		}
	}

	private N4JSEclipseProject doGetN4JSProject(IProject project, URI location) {
		return new N4JSEclipseProject(project, location, this);
	}

	public N4JSEclipseArchive getN4JSArchive(N4JSEclipseProject project, URI archiveLocation) {
		IFile archive = workspace.getFile(new Path(archiveLocation.toPlatformString(true)));
		return getN4JSArchive(project, archive);
	}

	public N4JSEclipseArchive getN4JSArchive(N4JSEclipseProject project, IFile archiveFile) {
		return new N4JSEclipseArchive(project, archiveFile);
	}

	public ImmutableList<? extends IN4JSEclipseArchive> getLibraries(N4JSEclipseProject project) {
		URI location = project.getLocation();
		return doGetLibraries(project, location);
	}

	protected ImmutableList<? extends IN4JSEclipseArchive> doGetLibraries(N4JSEclipseProject project, URI location) {
		ImmutableList.Builder<IN4JSEclipseArchive> result = ImmutableList.builder();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			List<ProjectDependency> dependencies = description.getProjectDependencies();
			for (ProjectDependency dependency : dependencies) {
				URI dependencyLocation = getInternalWorkspace().getLocation(location, dependency,
						N4JSSourceContainerType.ARCHIVE);
				if (dependencyLocation != null) {
					IFile archive = workspace.getFile(new Path(dependencyLocation.toPlatformString(true)));
					result.add(getN4JSArchive(project, archive));
				}
			}
		}
		return result.build();
	}

	public ImmutableList<? extends IN4JSEclipseArchive> getLibraries(N4JSEclipseArchive archive) {
		URI location = archive.getLocation();
		return doGetLibraries(archive.getProject(), location);
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseSourceContainer> getN4JSSourceContainers(N4JSProject project) {
		ImmutableList.Builder<IN4JSEclipseSourceContainer> result = ImmutableList.builder();
		URI location = project.getLocation();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			List<SourceContainerDescription> sourceFragments = newArrayList(from(description.getSourceContainers()));
			sourceFragments.sort((f1, f2) -> f1.compareByFragmentType(f2));
			for (SourceContainerDescription sourceFragment : sourceFragments) {
				List<String> paths = sourceFragment.getPaths();
				for (String p : paths) {
					IN4JSEclipseSourceContainer sourceContainer = createProjectN4JSSourceContainer(project,
							sourceFragment.getSourceContainerType(), p);
					result.add(sourceContainer);
				}
			}
		}
		return result.build();
	}

	@Override
	protected String getLocationPath(URI location) {
		return CommonPlugin.asLocalURI(location).toFileString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ImmutableList<? extends IN4JSEclipseProject> getDependencies(N4JSProject project,
			boolean includeAbsentProjects) {
		return (ImmutableList<? extends IN4JSEclipseProject>) super.getDependencies(project, includeAbsentProjects);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ImmutableList<? extends IN4JSEclipseProject> getDependenciesAndImplementedApis(N4JSProject project,
			boolean includeAbsentProjects) {
		return (ImmutableList<? extends IN4JSEclipseProject>) super.getDependenciesAndImplementedApis(project,
				includeAbsentProjects);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ImmutableList<? extends IN4JSEclipseProject> getImplementedProjects(N4JSProject project) {
		return (ImmutableList<? extends IN4JSEclipseProject>) super.getImplementedProjects(project);
	}

	@Override
	protected IN4JSEclipseSourceContainer createProjectN4JSSourceContainer(N4JSProject project,
			SourceContainerType type, String relativeLocation) {

		N4JSEclipseProject casted = (N4JSEclipseProject) project;
		IProject eclipseProject = casted.getProject();
		final IContainer container;

		String relPath = new Path(relativeLocation).toString();
		if (relPath.isEmpty() || ".".equals(relPath)) {
			container = eclipseProject;
		} else {
			container = eclipseProject.getFolder(relativeLocation);
		}
		return new EclipseSourceContainer(casted, type, container);
	}

	public Optional<? extends IN4JSSourceContainer> getN4JSSourceContainer(IResource resource) {
		N4JSEclipseProject project = getN4JSProject(resource.getProject());
		ImmutableList<? extends IN4JSEclipseSourceContainer> containers = project.getSourceContainers();
		IPath fullPath = resource.getFullPath();
		IN4JSEclipseSourceContainer matchingContainer = null;
		int matchingSegmentCount = -1;
		for (IN4JSEclipseSourceContainer container : containers) {
			if (matchPaths(fullPath, container)) {
				int segmentCount = container.getContainer().getFullPath().segmentCount();
				if (segmentCount > matchingSegmentCount) {
					matchingContainer = container;
					matchingSegmentCount = segmentCount;
				}
			}
		}
		return Optional.fromNullable(matchingContainer);
	}

	private boolean matchPaths(IPath fullPath, IN4JSEclipseSourceContainer sourceContainer) {
		IContainer container = sourceContainer.getContainer();
		return pathStartsWithFolder(fullPath, container);
	}

	private boolean pathStartsWithFolder(IPath fullPath, IContainer container) {
		IPath containerPath = container.getFullPath();
		int maxSegments = containerPath.segmentCount();
		if (fullPath.segmentCount() >= maxSegments) {
			for (int j = 0; j < maxSegments; j++) {
				if (!fullPath.segment(j).equals(containerPath.segment(j))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public Iterable<IN4JSProject> findAllProjects() {
		return findAllProjectMappings().values();
	}

	public Map<String, IN4JSProject> findAllProjectMappings() {
		final Map<String, IN4JSProject> workspaceProjectMapping = newLinkedHashMap();
		for (IProject project : workspace.getProjects()) {
			if (project.isOpen()) {
				N4JSEclipseProject n4jsProject = getN4JSProject(project);
				workspaceProjectMapping.put(n4jsProject.getProjectId(), n4jsProject);
			}
		}

		for (IProject project : externalLibraryWorkspace.getProjects()) {
			if (!workspaceProjectMapping.containsKey(project.getName())) {
				N4JSEclipseProject n4jsProject = getN4JSProject(project);
				workspaceProjectMapping.put(n4jsProject.getProjectId(), n4jsProject);
			}
		}

		return workspaceProjectMapping;
	}
}
