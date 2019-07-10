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

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseSourceContainer;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The eclipse specific implementation aspects of the N4JSModel.
 */
@SuppressWarnings("javadoc")
@Singleton
public class N4JSEclipseModel extends N4JSModel<PlatformResourceURI> {

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

		if (location.isPlatformResource() && !URIUtils.isPlatformResourceUriPointingToProject(location)) {
			checkArgument(
					URIUtils.isPlatformResourceUriPointingToProject(location),
					"Expected 2 segment counts for platform resource URI pointing to a project. Was "
							+ location.segmentCount());
		}

		final String n4jsProjectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(location);
		IProject project;
		if (location.isFile()) {
			project = externalLibraryWorkspace.getProject(n4jsProjectName);
			if (project == null) { // via source map
				String eclipseProjectName = ProjectDescriptionUtils
						.convertN4JSProjectNameToEclipseProjectName(n4jsProjectName);
				project = workspace.getProject(eclipseProjectName);
				if (project != null) { // get location newly from project to make it a platform URI
					return getN4JSProject(project);
				}
			}
			checkNotNull(project, "Project does not exist in external workspace. URI: " + location);

		} else {
			final String eclipseProjectName = ProjectDescriptionUtils
					.convertN4JSProjectNameToEclipseProjectName(n4jsProjectName);
			project = workspace.getProject(eclipseProjectName);
		}
		return doGetN4JSProject(project, toProjectLocation(location));
	}

	@Override
	protected IN4JSProject newAbsentProject(String projectName) {
		final URI uri = URI.createPlatformResourceURI(projectName, false);
		final String eclipseProjectName = ProjectDescriptionUtils
				.convertN4JSProjectNameToEclipseProjectName(projectName);
		return new N4JSEclipseProject(workspace.getProject(eclipseProjectName), new PlatformResourceURI(uri), this);
	}

	@Override
	protected N4JSEclipseProject getN4JSProject(SafeURI<?> location, boolean external) {
		N4JSEclipseProject result = getN4JSProject(location.toURI());
		if (result.isExternal() != external) {
			return null;
		}
		return result;
	}

	@Override
	public N4JSEclipseProject findProjectWith(URI nestedLocation) {
		return (N4JSEclipseProject) super.findProjectWith(nestedLocation);
	}

	@Override
	public Optional<? extends IN4JSSourceContainer> findN4JSSourceContainer(URI location) {

		Optional<? extends IN4JSSourceContainer> n4jsContainer = Optional.absent();

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
					n4jsContainer = Optional.fromNullable(eclipseProject.findSourceFolderContaining(location));
					// if (eclipseProject.getProject() instanceof N4JSExternalProject) {
					// final IResource resource = externalLibraryWorkspace.getResource(location);
					// if (null != resource) {
					// n4jsContainer = getN4JSSourceContainer(resource);
					// }
					// } else { // in case of locating a project from source maps:
					// String locString = location.toFileString();
					//
					// String projPathString = eclipseProject.getLocationPath().toString();
					// if (locString.startsWith(projPathString)) {
					// locString = eclipseProject.getLocation().toString()
					// + locString.substring(projPathString.length());
					// }
					//
					// for (IN4JSEclipseSourceContainer sc : eclipseProject.getSourceContainers()) {
					// String scLoc = sc.getLocation().toString();
					// if (locString.startsWith(scLoc)) {
					// return Optional.of(sc);
					//
					// }
					// }
					// }
				}
			}

			return n4jsContainer;
		}

		final IN4JSEclipseProject project = findProjectWith(location);
		if (null != project && project.exists()) {
			n4jsContainer = Optional.fromNullable(project.findSourceFolderContaining(location));
			// final Path path = new Path(location.toPlatformString(true));
			// final IResource resource;
			// if (1 == path.segmentCount()) {
			// resource = workspace.getProject(path.segment(0));
			// } else {
			// resource = workspace.getFile(path);
			// }
			// n4jsContainer = getN4JSSourceContainer(resource);
		}

		return n4jsContainer;
	}

	public N4JSEclipseProject getN4JSProject(IProject project) {
		if (project instanceof IExternalResource) {
			File file = ((IExternalResource) project).getExternalResource();
			return doGetN4JSProject(project, new FileURI(file));
		}
		return doGetN4JSProject(project, new PlatformResourceURI(project));
	}

	private N4JSEclipseProject doGetN4JSProject(IProject project, SafeURI<?> location) {
		return new N4JSEclipseProject(project, location, this);
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseSourceContainer> getN4JSSourceContainers(N4JSProject project) {
		ImmutableList.Builder<IN4JSEclipseSourceContainer> result = ImmutableList.builder();
		SafeURI<?> location = project.getSafeLocation();
		ProjectDescription description = getProjectDescription(location.toURI());
		if (description != null) {
			List<SourceContainerDescription> sourceFragments = newArrayList(from(description.getSourceContainers()));
			sourceFragments.sort((f1, f2) -> ProjectDescriptionUtils.compareBySourceContainerType(f1, f2));
			for (SourceContainerDescription sourceFragment : sourceFragments) {
				List<String> paths = ProjectDescriptionUtils.getPathsNormalized(sourceFragment);
				for (String p : paths) {
					IN4JSEclipseSourceContainer sourceContainer = createProjectN4JSSourceContainer(project,
							sourceFragment.getSourceContainerType(), p);
					result.add(sourceContainer);
				}
			}
		}
		return result.build();
	}

	// @Override
	// protected String getLocationPath(URI location) {
	// return CommonPlugin.asLocalURI(location).toFileString();
	// }

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
		final Map<String, IN4JSProject> workspaceProjectMapping = new LinkedHashMap<>();
		for (IProject project : workspace.getProjects()) {
			if (isRelevantToN4JS(project)) {
				N4JSProject n4jsProject = getN4JSProject(project);
				workspaceProjectMapping.put(n4jsProject.getProjectName(), n4jsProject);
			}
		}

		for (IProject project : externalLibraryWorkspace.getProjects()) {
			final N4JSProject n4jsProject = getN4JSProject(project);
			if (!workspaceProjectMapping.containsKey(n4jsProject.getProjectName())) {
				workspaceProjectMapping.put(n4jsProject.getProjectName(), n4jsProject);
			}
		}

		return workspaceProjectMapping;
	}

	/**
	 * Low-level method for deciding whether a given project residing in the Eclipse workspace is to be treated as an
	 * "N4JS project", i.e. whether N4JS-related implementations should take not of this project or simply ignore it
	 * entirely. For example, this affects whether methods such as {@link IN4JSCore#findAllProjects()} will return this
	 * project or not.
	 * <p>
	 * In particular, this method requires that the given project is properly configured as an Xtext project.
	 * <p>
	 * This method is not intended to be used with projects residing in the {@link ExternalLibraryWorkspace}, which are
	 * by definition always relevant to N4JS.
	 */
	private boolean isRelevantToN4JS(IProject project) {
		return project != null
				&& project.isAccessible() // includes #isOpen() as a requirement
				&& XtextProjectHelper.hasNature(project)
				&& XtextProjectHelper.hasBuilder(project);
	}
}
