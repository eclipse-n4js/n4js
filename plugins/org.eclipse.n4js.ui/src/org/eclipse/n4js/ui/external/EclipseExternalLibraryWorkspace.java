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
package org.eclipse.n4js.ui.external;

import static com.google.common.collect.Iterators.unmodifiableIterator;
import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.core.runtime.SubMonitor.convert;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.RebuildWorkspaceProjectsScheduler;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.StoreUpdatedListener;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.xtext.util.Pair;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Eclipse based implementation of the external library workspace. This class assumes a running {@link Platform
 * platform}.
 */
@Singleton
public class EclipseExternalLibraryWorkspace extends ExternalLibraryWorkspace implements StoreUpdatedListener {
	private static Logger logger = Logger.getLogger(EclipseExternalLibraryWorkspace.class);

	@Inject
	private IN4JSCore core;

	@Inject
	private ExternalLibraryBuilder builder;

	@Inject
	private ExternalProjectsCollector collector;

	@Inject
	private RebuildWorkspaceProjectsScheduler scheduler;

	@Inject
	private ExternalProjectProvider projectProvider;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	/**
	 */
	@Inject
	void init() {
		try {
			projectProvider.updateCache();
		} catch (Throwable t) {
			logger.error("Failed to initialize external library workspace.", t);
			UIUtils.showError(t);
		}
		externalLibraryPreferenceStore.addListener(this);
	}

	@Override
	public void storeUpdated(ExternalLibraryPreferenceStore store, IProgressMonitor monitor) {
		projectProvider.updateCache();
	}

	@Override
	public ProjectDescription getProjectDescription(URI location) {
		Pair<N4JSExternalProject, ProjectDescription> pair = projectProvider.getProjectWithDescription(location);
		return null == pair ? null : pair.getSecond();
	}

	@Override
	public URI getLocation(URI projectURI, ProjectReference reference) {
		String name = reference.getProjectName();
		ExternalProject project = projectProvider.getProject(name);

		if (null == project) {
			return null;
		}

		File referencedProject = new File(project.getLocationURI());
		URI refLocation = URI.createFileURI(referencedProject.getAbsolutePath());
		Pair<N4JSExternalProject, ProjectDescription> pair = projectProvider.getProjectWithDescription(refLocation);
		if (null != pair) {
			return refLocation;
		}

		return null;
	}

	@Override
	public Iterator<URI> getFolderIterator(URI folderLocation) {
		URI findProjectWith = findProjectWith(folderLocation);
		if (null != findProjectWith) {
			String projectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(findProjectWith);
			ExternalProject project = projectProvider.getProject(projectName);
			if (null != project) {
				String projectPath = new File(project.getLocationURI()).getAbsolutePath();
				String folderPath = folderLocation.toFileString();
				IContainer container = projectPath.equals(folderPath) ? project
						: project.getFolder(folderPath.substring(projectPath.length() + 1));
				Collection<URI> result = Lists.newLinkedList();
				try {
					container.accept(resource -> {
						if (resource instanceof IFile) {
							String path = new File(resource.getLocationURI()).getAbsolutePath();
							result.add(URI.createFileURI(path));
						}
						// do not iterate over contents of nested node_modules folders
						if (resource.getType() == IResource.FOLDER &&
								resource.getName().equals(N4JSGlobals.NODE_MODULES)) {
							return false;
						}
						return true;
					});
					return unmodifiableIterator(result.iterator());
				} catch (CoreException e) {
					return unmodifiableIterator(result.iterator());
				}
			}
		}

		return Collections.emptyIterator();
	}

	@Override
	public URI findArtifactInFolder(URI folderLocation, String folderRelativePath) {
		IResource folder = getResource(folderLocation);
		if (folder instanceof IFolder) {
			IFile file = ((IFolder) folder).getFile(folderRelativePath);
			if (file instanceof IExternalResource) {
				File externalResource = ((IExternalResource) file).getExternalResource();
				return URI.createFileURI(externalResource.getAbsolutePath());
			}
		}

		return null;
	}

	@Override
	public URI findProjectWith(URI nestedLocation) {
		java.net.URI rootLoc = getRootLocationForResource(nestedLocation);

		if (rootLoc != null) {
			URI uriCandidate = doOliversStuff(nestedLocation, rootLoc);
			if (projectProvider.getProject(uriCandidate) != null) {
				return uriCandidate;
			}
		}

		return null;
	}

	// FIXME: @mor: please document and rename
	private URI doOliversStuff(URI nestedLocation, java.net.URI rootLoc) {
		String rootLocStr = rootLoc.toString();
		URI loc = URI.createURI(rootLocStr);
		URI prefix = !loc.hasTrailingPathSeparator() ? loc.appendSegment("") : loc;
		int oldSegmentCount = nestedLocation.segmentCount();
		int newSegmentCount = prefix.segmentCount()
				- 1 // -1 because of the trailing empty segment
				+ 1; // +1 to include the project folder
		if (newSegmentCount - 1 >= oldSegmentCount) {
			return null; // can happen if the URI of an external library location is passed in
		}
		String projectNameCandidate = nestedLocation.segment(newSegmentCount - 1);
		if (projectNameCandidate.startsWith("@")) {
			// last segment is a folder representing an npm scope, not a project folder
			// --> add 1 to include the actual project folder
			++newSegmentCount;
		}
		URI uriCandidate = nestedLocation.trimSegments(oldSegmentCount - newSegmentCount).trimFragment();
		return uriCandidate;
	}

	@Override
	public RegisterResult registerProjects(IProgressMonitor monitor, Set<URI> toBeUpdated) {
		ISchedulingRule rule = builder.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);
			return registerProjectsInternal(monitor, toBeUpdated);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	@Override
	public RegisterResult deregisterProjects(IProgressMonitor monitor, Set<URI> toBeDeleted) {
		ISchedulingRule rule = builder.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);
			return deregisterProjectsInternal(monitor, toBeDeleted, new HashSet<>());
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	@Override
	public RegisterResult deregisterAllProjects(IProgressMonitor monitor) {
		ISchedulingRule rule = builder.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);
			Set<URI> toBeDeleted = new HashSet<>();
			for (ExternalProject extProject : getProjects()) {
				URI location = URIUtils.convert(extProject);
				toBeDeleted.add(location);
			}
			Set<URI> toBeWiped = new HashSet<>();
			for (java.net.URI rootLocation : projectProvider.getRootLocationsInReversedShadowingOrder()) {
				toBeWiped.add(URIUtils.toFileUri(rootLocation));
			}
			return deregisterProjectsInternal(monitor, toBeDeleted, toBeWiped);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	private RegisterResult deregisterProjectsInternal(IProgressMonitor monitor, Set<URI> toBeDeleted,
			Set<URI> toBeWiped) {
		if (!ExternalLibrariesActivator.requiresInfrastructureForLibraryManager()) {
			logger.warn("Built-in libraries and NPM support are disabled.");
		}

		SubMonitor subMonitor = convert(monitor, 1);

		// Clean projects.
		Set<N4JSExternalProject> allProjectsToClean = getAllToBeCleaned(toBeDeleted);

		List<IProject> extPrjCleaned = new LinkedList<>();
		if (!allProjectsToClean.isEmpty()) {
			extPrjCleaned.addAll(builder.clean(allProjectsToClean, subMonitor.split(1)));
		}

		Set<IProject> wsPrjAffected = newHashSet();
		wsPrjAffected.addAll(collector.getWSProjectsDependendingOn(allProjectsToClean));

		toBeWiped.addAll(toBeDeleted);
		wipeIndex(monitor, toBeWiped, allProjectsToClean);

		return new RegisterResult(
				extPrjCleaned.toArray(new IProject[0]),
				wsPrjAffected.toArray(new IProject[0]),
				toBeWiped);
	}

	private Set<N4JSExternalProject> getAllToBeCleaned(Set<URI> toBeDeleted) {
		Set<N4JSExternalProject> allProjectsToClean = new HashSet<>();
		Set<N4JSExternalProject> projectsToClean = new HashSet<>();
		for (URI toBeDeletedLocation : toBeDeleted) {
			N4JSExternalProject project = projectProvider.getProject(toBeDeletedLocation);
			if (project != null) {
				projectsToClean.add(project);
			}
		}
		allProjectsToClean.addAll(projectsToClean);
		allProjectsToClean.addAll(newHashSet(collector.getExtProjectsDependendingOn(projectsToClean)));
		return allProjectsToClean;
	}

	private void wipeIndex(IProgressMonitor monitor, Set<URI> toBeDeleted,
			Set<N4JSExternalProject> allProjectsToClean) {

		HashSet<URI> toBeWiped = new HashSet<>(toBeDeleted);
		for (N4JSExternalProject project : allProjectsToClean) {
			toBeWiped.add(URIUtils.toFileUri(project.getLocationURI()));
		}
		builder.wipeURIsFromIndex(monitor, toBeWiped);
	}

	private RegisterResult registerProjectsInternal(IProgressMonitor monitor, Set<URI> toBeUpdated) {
		Collection<IProject> extPrjBuilt = new LinkedList<>();

		if (!ExternalLibrariesActivator.requiresInfrastructureForLibraryManager()) {
			logger.warn("Built-in libraries and NPM support are disabled.");
		}

		SubMonitor subMonitor = convert(monitor, 1);

		// Rebuild whole external workspace. Filter out projects that are present in the Eclipse workspace.
		Collection<N4JSExternalProject> projectsToBuild = getExternalProjects(toBeUpdated);
		Set<N4JSExternalProject> allProjectsToBuild = new HashSet<>();
		allProjectsToBuild.addAll(projectsToBuild);
		allProjectsToBuild.addAll(collector.getExtProjectsDependendingOn(projectsToBuild));

		// Build recently added projects that do not exist in workspace.
		// Also includes projects that exist already in the index, but are shadowed.
		if (!Iterables.isEmpty(allProjectsToBuild)) {
			extPrjBuilt.addAll(builder.build(allProjectsToBuild, subMonitor.split(1)));
		}

		Set<IProject> wsPrjAffected = newHashSet();
		Set<N4JSExternalProject> affectedProjects = new HashSet<>();
		affectedProjects.addAll(allProjectsToBuild);
		wsPrjAffected.addAll(collector.getWSProjectsDependendingOn(affectedProjects));

		return new RegisterResult(extPrjBuilt.toArray(new IProject[0]), wsPrjAffected.toArray(new IProject[0]));
	}

	@Override
	public void scheduleWorkspaceProjects(IProgressMonitor monitor, Set<URI> toBeScheduled) {
		Set<IProject> scheduledProjects = newHashSet();
		for (URI scheduledURI : toBeScheduled) {
			IN4JSProject wsProject = core.findProject(scheduledURI).orNull();
			if (wsProject instanceof N4JSEclipseProject) {
				N4JSEclipseProject n4EclProject = (N4JSEclipseProject) wsProject;
				scheduledProjects.add(n4EclProject.getProject());
			}
		}
		scheduler.scheduleBuildIfNecessary(scheduledProjects);
	}

	private Collection<N4JSExternalProject> getExternalProjects(Set<URI> toBeUpdated) {
		Set<N4JSExternalProject> projectsToBeUpdated = new HashSet<>();
		for (URI tbu : toBeUpdated) {
			N4JSExternalProject n4Prj = projectProvider.getProject(tbu);
			projectsToBeUpdated.add(n4Prj);
		}

		Collection<N4JSExternalProject> nonWSProjects = collector.filterNonWSProjects(projectsToBeUpdated);

		return nonWSProjects;
	}

	@Override
	public Collection<N4JSExternalProject> getProjects() {
		return projectProvider.getProjects();
	}

	@Override
	public boolean isNecessary(URI location) {
		return projectProvider.getAllProjectLocations().contains(location);
	}

	@Override
	public List<Pair<URI, ProjectDescription>> computeProjectsIncludingUnnecessary() {
		return projectProvider.computeProjectsIncludingUnnecessary();
	}

	@Override
	public Collection<URI> getAllProjectLocations() {
		return projectProvider.getAllProjectLocations();
	}

	@Override
	public Map<String, VersionNumber> getProjectNameVersionMap() {
		Map<String, VersionNumber> externalLibs = new HashMap<>();
		for (N4JSExternalProject pd : getProjects()) {
			String name = pd.getIProject().getProjectName();
			VersionNumber version = pd.getIProject().getVersion();
			externalLibs.put(name, version);
		}

		return externalLibs;
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(java.net.URI rootLocation) {
		return projectProvider.getProjectsIn(rootLocation);
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(final Collection<java.net.URI> rootLocations) {
		Collection<N4JSExternalProject> projects = new LinkedList<>();
		for (java.net.URI rootLoc : rootLocations) {
			projects.addAll(getProjectsIn(rootLoc));
		}
		return projects;
	}

	@Override
	public N4JSExternalProject getProject(String projectName) {
		return projectProvider.getProject(projectName);
	}

	@Override
	public N4JSExternalProject getProject(URI projectLocation) {
		return projectProvider.getProject(projectLocation);
	}

	@Override
	public IResource getResource(URI location) {
		String path = location.toFileString();
		if (null == path) {
			return null;
		}
		File nestedResource = new File(path);
		if (nestedResource.exists()) {
			URI projectLocation = findProjectWith(location);
			if (null != projectLocation) {
				String projectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(projectLocation);
				IProject project = getProject(projectName);
				if (project instanceof ExternalProject) {
					File projectResource = new File(project.getLocationURI());
					if (projectResource.isDirectory()) {

						Path projectPath = projectResource.toPath();
						Path nestedPath = nestedResource.toPath();

						if (projectPath.equals(nestedPath)) {
							return project;
						}

						// TODO: project.getFile and project.getFolder don't check whether then given path is a file or
						// a folder, and they should not?
						Path relativePath = projectPath.relativize(nestedPath);
						IFile file = project.getFile(relativePath.toString());
						if (file.exists())
							return file;

						IFolder folder = project.getFolder(relativePath.toString());
						if (folder.exists())
							return folder;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Updates the internal state based on the available external project root locations.
	 * <p>
	 * This cannot be done in construction time, because it might happen that some bundles/classes are not initialized
	 * yet, hence not available when injecting this instance.
	 */
	@Override
	public void updateState() {
		projectProvider.updateCache();
	}

	@Override
	public List<Pair<URI, ProjectDescription>> getProjectsIncludingUnnecessary() {
		return projectProvider.getProjectsIncludingUnnecessary();
	}

	@Override
	public List<N4JSExternalProject> getProjectsForName(String projectName) {
		return projectProvider.getProjectsForName(projectName);
	}

	@Override
	public java.net.URI getRootLocationForResource(org.eclipse.emf.common.util.URI nestedLocation) {
		return getRootLocationForResource(projectProvider.getAllRootLocations(), nestedLocation);
	}

}
