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

import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.core.runtime.SubMonitor.convert;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.StoreUpdatedListener;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.xtext.builder.impl.BuilderStateDiscarder;
import org.eclipse.xtext.builder.impl.IBuildFlag;
import org.eclipse.xtext.util.Pair;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Eclipse based implementation of the external library workspace. This class assumes a running {@link Platform
 * platform}.
 */
@SuppressWarnings("restriction")
@Singleton
public class EclipseExternalLibraryWorkspace extends ExternalLibraryWorkspace
		implements StoreUpdatedListener {
	private static Logger logger = Logger.getLogger(EclipseExternalLibraryWorkspace.class);

	@Inject
	private IN4JSCore core;

	@Inject
	private ExternalLibraryBuilder builder;

	@Inject
	private ExternalProjectsCollector collector;

	@Inject
	private BuilderStateDiscarder builderStateDiscarder;

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
	public ProjectDescription getProjectDescription(FileURI location) {
		Pair<N4JSExternalProject, ProjectDescription> pair = projectProvider.getProjectWithDescription(location);
		return null == pair ? null : pair.getSecond();
	}

	@Override
	public FileURI getLocation(ProjectReference reference) {
		N4JSProjectName name = new N4JSProjectName(reference.getProjectName());
		N4JSExternalProject project = projectProvider.getProject(name);

		if (null == project) {
			return null;
		}

		FileURI refLocation = (FileURI) project.getIProject().getLocation();
		Pair<N4JSExternalProject, ProjectDescription> pair = projectProvider.getProjectWithDescription(refLocation);
		if (null != pair) {
			return refLocation;
		}

		return null;
	}

	@Override
	public Iterator<? extends FileURI> getFolderIterator(FileURI folderLocation) {
		return Iterators.filter(folderLocation.getAllChildren(), loc -> !loc.isDirectory());
	}

	@Override
	public FileURI findArtifactInFolder(FileURI folderLocation, String folderRelativePath) {
		FileURI result = folderLocation.appendPath(folderRelativePath);
		if (result != null && result.exists()) {
			return result;
		}
		return null;
	}

	@Override
	public FileURI findProjectWith(FileURI nestedLocation) {
		FileURI rootLoc = getRootLocationForResource(nestedLocation);
		if (rootLoc != null) {
			FileURI uriCandidate = computeProjectURI(nestedLocation, rootLoc);
			if (projectProvider.getProject(uriCandidate) != null) {
				return uriCandidate;
			}
		}
		return null;
	}

	private FileURI computeProjectURI(FileURI nestedLocation, FileURI rootLoc) {
		List<String> path = nestedLocation.deresolve(rootLoc);
		Iterator<String> iterator = path.iterator();
		FileURI result = rootLoc;
		while (iterator.hasNext()) {
			String segment = iterator.next();
			result = result.appendSegment(segment);
			if (!segment.startsWith("@")) {
				return result;
			}
		}
		return result;
	}

	@Override
	public RegisterResult registerProjects(IProgressMonitor monitor, Set<FileURI> toBeUpdated) {
		ISchedulingRule rule = builder.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);
			return registerProjectsInternal(monitor, toBeUpdated);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	@Override
	public RegisterResult deregisterProjects(IProgressMonitor monitor, Set<FileURI> toBeDeleted) {
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
			Set<FileURI> toBeDeleted = new HashSet<>();
			for (N4JSExternalProject extProject : getProjects()) {
				toBeDeleted.add(extProject.getSafeLocation());
			}
			Set<FileURI> toBeWiped = new HashSet<>(projectProvider.getRootLocationsInReversedShadowingOrder());
			return deregisterProjectsInternal(monitor, toBeDeleted, toBeWiped);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	private RegisterResult deregisterProjectsInternal(IProgressMonitor monitor, Set<FileURI> toBeDeleted,
			Set<FileURI> toBeWiped) {
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

		Set<FileURI> cleaned = Sets.newHashSet();
		for (IProject cleanedExternal : extPrjCleaned) {
			cleaned.add(getProject(new EclipseProjectName(cleanedExternal.getName()).toN4JSProjectName())
					.getSafeLocation());
		}
		return new RegisterResult(
				cleaned,
				getURIs(wsPrjAffected.toArray(new IProject[0])),
				toBeWiped);
	}

	static private Collection<PlatformResourceURI> getURIs(IProject[] projects) {
		HashSet<PlatformResourceURI> uris = new HashSet<>();
		for (IProject project : projects) {
			uris.add(new PlatformResourceURI(project));
		}
		return uris;
	}

	private Set<N4JSExternalProject> getAllToBeCleaned(Set<FileURI> toBeDeleted) {
		Set<N4JSExternalProject> allProjectsToClean = new HashSet<>();
		Set<N4JSExternalProject> projectsToClean = new HashSet<>();
		for (FileURI toBeDeletedLocation : toBeDeleted) {
			N4JSExternalProject project = projectProvider.getProject(toBeDeletedLocation);
			if (project != null) {
				projectsToClean.add(project);
			}
		}
		allProjectsToClean.addAll(projectsToClean);
		allProjectsToClean.addAll(newHashSet(collector.getExtProjectsDependendingOn(projectsToClean)));
		return allProjectsToClean;
	}

	private void wipeIndex(IProgressMonitor monitor, Set<FileURI> toBeDeleted,
			Set<N4JSExternalProject> allProjectsToClean) {

		HashSet<FileURI> toBeWiped = new HashSet<>(toBeDeleted);
		for (N4JSExternalProject project : allProjectsToClean) {
			toBeWiped.add(project.getSafeLocation());
		}
		builder.wipeURIsFromIndex(monitor, toBeWiped);
	}

	private RegisterResult registerProjectsInternal(IProgressMonitor monitor, Set<FileURI> toBeUpdated) {
		Collection<IProject> extPrjBuilt = new LinkedList<>();
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

		Set<FileURI> built = Sets.newHashSet();
		for (IProject cleanedExternal : extPrjBuilt) {
			built.add(getProject(new EclipseProjectName(cleanedExternal.getName()).toN4JSProjectName())
					.getSafeLocation());
		}
		return new RegisterResult(built, getURIs(wsPrjAffected.toArray(new IProject[0])));
	}

	@Override
	public void scheduleWorkspaceProjects(IProgressMonitor monitor, Set<SafeURI<?>> toBeScheduled) {
		Set<IProject> scheduledProjects = newHashSet();
		for (SafeURI<?> scheduledURI : toBeScheduled) {
			IN4JSProject wsProject = core.findProject(scheduledURI.toURI()).orNull();
			if (wsProject instanceof IN4JSEclipseProject) {
				IN4JSEclipseProject n4EclProject = (IN4JSEclipseProject) wsProject;
				scheduledProjects.add(n4EclProject.getProject());
			}
		}
		Map<String, String> args = new HashMap<>();
		IBuildFlag.FORGET_BUILD_STATE_ONLY.addToMap(args);
		builderStateDiscarder.forgetLastBuildState(scheduledProjects, args);
	}

	private Collection<N4JSExternalProject> getExternalProjects(Set<FileURI> toBeUpdated) {
		Set<N4JSExternalProject> projectsToBeUpdated = new HashSet<>();
		for (SafeURI<?> tbu : toBeUpdated) {
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
	public boolean isNecessary(SafeURI<?> location) {
		return projectProvider.getAllProjectLocations().contains(location);
	}

	@Override
	public List<Pair<FileURI, ProjectDescription>> computeProjectsIncludingUnnecessary() {
		return projectProvider.computeProjectsIncludingUnnecessary();
	}

	@Override
	public Collection<FileURI> getAllProjectLocations() {
		return projectProvider.getAllProjectLocations();
	}

	@Override
	public Map<N4JSProjectName, VersionNumber> getProjectNameVersionMap() {
		Map<N4JSProjectName, VersionNumber> externalLibs = new HashMap<>();
		for (N4JSExternalProject pd : getProjects()) {
			N4JSProjectName name = pd.getIProject().getProjectName();
			VersionNumber version = pd.getIProject().getVersion();
			externalLibs.put(name, version);
		}

		return externalLibs;
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(FileURI rootLocation) {
		return projectProvider.getProjectsIn(rootLocation);
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(Collection<FileURI> rootLocations) {
		Collection<N4JSExternalProject> projects = new LinkedList<>();
		for (FileURI rootLoc : rootLocations) {
			projects.addAll(getProjectsIn(rootLoc));
		}
		return projects;
	}

	@Override
	public N4JSExternalProject getProject(N4JSProjectName projectName) {
		return projectProvider.getProject(projectName);
	}

	@Override
	public N4JSExternalProject getProject(FileURI projectLocation) {
		return projectProvider.getProject(projectLocation);
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
	public List<Pair<FileURI, ProjectDescription>> getProjectsIncludingUnnecessary() {
		return projectProvider.getProjectsIncludingUnnecessary();
	}

	@Override
	public List<N4JSExternalProject> getProjectsForName(N4JSProjectName projectName) {
		return projectProvider.getProjectsForName(projectName);
	}

	@Override
	public FileURI getRootLocationForResource(FileURI nestedLocation) {
		return getRootLocationForResource(projectProvider.getAllRootLocations(), nestedLocation);
	}

}
