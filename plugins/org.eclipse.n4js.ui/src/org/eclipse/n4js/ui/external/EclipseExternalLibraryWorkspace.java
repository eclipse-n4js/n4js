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

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterators.emptyIterator;
import static com.google.common.collect.Iterators.unmodifiableIterator;
import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.core.runtime.SubMonitor.convert;
import static org.eclipse.n4js.internal.N4JSSourceContainerType.PROJECT;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.RebuildWorkspaceProjectsScheduler;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.internal.N4JSSourceContainerType;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
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
@SuppressWarnings("restriction")
public class EclipseExternalLibraryWorkspace extends ExternalLibraryWorkspace {
	private static Logger logger = Logger.getLogger(EclipseExternalLibraryWorkspace.class);

	@Inject
	private ExternalIndexUpdater indexUpdater;

	@Inject
	private ExternalLibraryBuilder builder;

	@Inject
	private ExternalProjectsCollector collector;

	@Inject
	private RebuildWorkspaceProjectsScheduler scheduler;

	/** This provider creates {@link ExternalProject}s */
	@Inject
	private ExternalProjectProvider projectProvider;

	/**
	 */
	@Inject
	void init() {
		projectProvider.ensureInitialized();
		projectProvider.addExternalLocationsUpdatedListener(indexUpdater);
	}

	@Override
	public ProjectDescription getProjectDescription(URI location) {
		Pair<N4JSExternalProject, ProjectDescription> pair = projectProvider.getProjectWithDescription(location);
		return null == pair ? null : pair.getSecond();
	}

	@Override
	public URI getLocation(URI projectURI, ProjectReference reference,
			N4JSSourceContainerType expectedN4JSSourceContainerType) {

		if (PROJECT.equals(expectedN4JSSourceContainerType)) {

			String name = reference.getProject().getProjectId();
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

		}

		return null;
	}

	@Override
	public Iterator<URI> getArchiveIterator(URI archiveLocation, String archiveRelativeLocation) {
		return emptyIterator();
	}

	@Override
	public Iterator<URI> getFolderIterator(URI folderLocation) {
		URI findProjectWith = findProjectWith(folderLocation);
		if (null != findProjectWith) {
			String projectName = findProjectWith.lastSegment();
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
						return true;
					});
					return unmodifiableIterator(result.iterator());
				} catch (CoreException e) {
					return unmodifiableIterator(result.iterator());
				}
			}
		}

		return emptyIterator();
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
		String path = nestedLocation.toFileString();
		if (null == path) {
			return null;
		}

		File nestedResource = new File(path);
		if (!nestedResource.exists()) {
			return null;
		}

		Path nestedResourcePath = nestedResource.toPath();

		Iterable<URI> registeredProjectUris = projectProvider.getProjectURIs();
		for (URI projectUri : registeredProjectUris) {
			if (projectUri.isFile()) {
				File projectRoot = new File(projectUri.toFileString());
				Path projectRootPath = projectRoot.toPath();
				if (nestedResourcePath.startsWith(projectRootPath)) {
					return projectUri;
				}
			}
		}

		return null;
	}

	@Override
	public RegisterResult registerProjects(ToBeBuilt result, IProgressMonitor monitor, boolean triggerCleanbuild) {
		ISchedulingRule rule = builder.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);
			return registerProjectsInternal(result, monitor, triggerCleanbuild);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	private RegisterResult registerProjectsInternal(ToBeBuilt result, IProgressMonitor monitor,
			boolean triggerCleanbuild) {

		Collection<IProject> extPrjCleaned = null;
		Collection<IProject> extPrjBuilt = null;

		if (!ExternalLibrariesActivator.requiresInfrastructureForLibraryManager()) {
			logger.warn("Built-in libraries and NPM support are disabled.");
		}

		SubMonitor subMonitor = convert(monitor, 3);

		// Clean projects.
		Set<N4JSExternalProject> allProjectsToClean = new HashSet<>();
		Set<N4JSExternalProject> projectsToClean = new HashSet<>();
		for (URI toBeDeletedLocation : result.getToBeDeleted()) {
			N4JSExternalProject project = projectProvider.getProject(toBeDeletedLocation);
			if (project != null) {
				projectsToClean.add(project);
			}
		}
		allProjectsToClean.addAll(projectsToClean);
		allProjectsToClean.addAll(newHashSet(collector.getExtProjectsDependendingOn(projectsToClean)));

		if (!allProjectsToClean.isEmpty()) {
			extPrjCleaned = builder.clean(allProjectsToClean, subMonitor.newChild(1));
		}
		subMonitor.worked(1);

		// Rebuild whole external workspace. Filter out projects that are present in the Eclipse workspace (if any).
		Collection<N4JSExternalProject> projectsToBuild = getExternalProjects(result);
		Set<N4JSExternalProject> allProjectsToBuild = new HashSet<>();
		allProjectsToBuild.addAll(projectsToBuild);
		allProjectsToBuild.addAll(newHashSet(collector.getExtProjectsDependendingOn(projectsToBuild)));
		allProjectsToBuild.addAll(newHashSet(collector.getExtProjectsDependendingOn(allProjectsToClean)));

		// Build recently added projects that do not exist in workspace.
		// Also includes projects that exist already in the index, but are shadowed.
		if (!Iterables.isEmpty(allProjectsToBuild)) {
			extPrjBuilt = builder.build(allProjectsToBuild, subMonitor.newChild(1));
		}
		subMonitor.worked(1);

		Set<IProject> wsPrjToRebuild = newHashSet();
		if (triggerCleanbuild) {
			Set<N4JSExternalProject> affectedProjects = new HashSet<>();
			affectedProjects.addAll(newHashSet(projectsToClean));
			affectedProjects.addAll(newHashSet(allProjectsToBuild));
			wsPrjToRebuild.addAll(newHashSet(collector.getWSProjectsDependendingOn(affectedProjects)));

			scheduler.scheduleBuildIfNecessary(wsPrjToRebuild);
		}

		return new RegisterResult(extPrjCleaned, extPrjBuilt, wsPrjToRebuild);
	}

	private Collection<N4JSExternalProject> getExternalProjects(ToBeBuilt result) {
		Set<N4JSExternalProject> projectsToBeUpdated = from(result.getToBeUpdated())
				.transform(uri -> projectProvider.getProject(uri)).toSet();

		Collection<N4JSExternalProject> nonWSProjects = collector.filterNonWSProjects(projectsToBeUpdated);

		return nonWSProjects;
	}

	@Override
	public Collection<N4JSExternalProject> getProjects() {
		return projectProvider.getProjects();
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(java.net.URI rootLocation) {
		return projectProvider.getProjectsIn(rootLocation);
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(final Collection<java.net.URI> rootLocations) {
		return projectProvider.getProjectsIn(rootLocations);
	}

	@Override
	public Collection<ProjectDescription> getProjectsDescriptions(java.net.URI rootLocation) {
		return projectProvider.getProjectsDescriptions(rootLocation);
	}

	@Override
	public ExternalProject getProject(String projectName) {
		return projectProvider.getProject(projectName);
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
				String projectName = projectLocation.lastSegment();
				IProject project = getProject(projectName);
				if (project instanceof ExternalProject) {
					File projectResource = new File(project.getLocationURI());
					if (projectResource.exists() && projectResource.isDirectory()) {

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
	 * This cannot be done in construction time, because it might happen that N4MF is not initialized yet, hence not
	 * available when injecting this instance.
	 */
	@Override
	public void updateState() {
		projectProvider.updateCache();
	}

}
