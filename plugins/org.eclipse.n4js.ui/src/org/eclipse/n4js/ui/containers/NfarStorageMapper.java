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
package org.eclipse.n4js.ui.containers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorage;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseArchive;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.xtext.ui.resource.IStorage2UriMapperContribution;
import org.eclipse.xtext.ui.resource.UriValidator;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// FIXME delete entire class
/**
 * Put the URIs that are found in NFARs into the URI cache such that they become available as {@link IStorage storages}
 * within Eclipse. That is, resource from NFARs become navigable and the builder can resolve their contents on load.
 *
 * @see IStorage2UriMapperContribution
 */
@Singleton
@SuppressWarnings({ "javadoc" })
public class NfarStorageMapper implements IStorage2UriMapperContribution {

	/**
	 * This constant is only kept to avoid breaking old .nfar tests. All nfar support will soon be removed, anyway.
	 */
	public final static String N4MF_MANIFEST = "manifest.n4mf";

	private class Listener implements IResourceChangeListener {
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			final Set<IProject> affectedProjects = Sets.newHashSetWithExpectedSize(1);
			try {
				event.getDelta().accept(new IResourceDeltaVisitor() {
					@Override
					public boolean visit(IResourceDelta delta) throws CoreException {
						IResource resource = delta.getResource();
						if (resource.getType() == IResource.ROOT) {
							return true;
						} else if (resource.getType() == IResource.PROJECT) {
							IProject project = (IProject) resource;
							if ((delta.getFlags() & IResourceDelta.OPEN) != 0 && project.isOpen()) {
								IProject[] referencingProjects = project.getReferencingProjects();
								affectedProjects.addAll(Arrays.asList(referencingProjects));
							}
							if ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0 && project.isOpen()) {
								affectedProjects.add(project);
							}
							// traverse if the delta contains a manifest change
							// FIXME delete entire class
							if (delta.findMember(new Path(N4MF_MANIFEST)) != null)
								return true;
							// or the project has a manifest
							if (project.getFile(N4MF_MANIFEST).exists()) {
								return true;
							}
						} else if (resource.getType() == IResource.FOLDER) {
							// We may not access the library folder from the project description here thus
							// we traverse the complete delta if there was a manifest in the project
							return true;
						} else if (IN4JSArchive.NFAR_FILE_EXTENSION.equals(resource.getFileExtension())) {
							affectedProjects.add(resource.getProject());
						} else if (N4MF_MANIFEST.equals(delta.getFullPath().lastSegment())) {
							affectedProjects.add(resource.getProject());
						}
						return false;
					}
				});
			} catch (CoreException e) {
				// ignore
			}
			if (!affectedProjects.isEmpty()) {
				Optional<? extends IN4JSEclipseProject> projectOpt = null;
				for (IProject project : affectedProjects) {
					projectOpt = eclipseCore.create(project);
					if (projectOpt.isPresent()) {
						updateCache(projectOpt.get());
					}
				}
			}
		}
	}

	@Inject
	private IWorkspace workspace;

	@Inject
	private UriValidator uriValidator;

	@Inject
	private IN4JSEclipseCore eclipseCore;

	/**
	 * A cache from archive URI to content's uri.
	 */
	private final Map<URI, Set<URI>> knownEntries = Maps.newConcurrentMap();
	/**
	 * A cache of archive URIs per project URI.
	 */
	private final Map<URI, Set<URI>> knownLibArchives = Maps.newConcurrentMap();

	@Override
	public void initializeCache() {
		Optional<? extends IN4JSEclipseProject> projectOpt = null;
		IN4JSEclipseProject n4jsProject = null;
		for (IProject project : workspace.getRoot().getProjects()) {
			projectOpt = eclipseCore.create(project);
			if (projectOpt.isPresent()) {
				n4jsProject = projectOpt.get();
				if (n4jsProject.exists()) {
					updateCache(n4jsProject);
				}
			}
		}
		Listener listener = new Listener();
		workspace.addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
	}

	private void updateCache(IN4JSEclipseProject project) {
		Set<URI> libArchives = knownLibArchives.get(project.getLocation());
		if (libArchives != null) {
			Map<URI, Set<URI>> filteredMap = Maps.filterKeys(knownLibArchives,
					Predicates.not(Predicates.equalTo(project.getLocation())));
			Set<URI> remainingLibs = Sets.newHashSet(Iterables.concat(filteredMap.values()));
			for (URI archive : libArchives) {
				if (!remainingLibs.contains(archive)) {
					knownEntries.remove(archive);
				}
			}
		}
		if (project.exists()) {
			libArchives = Sets.newHashSetWithExpectedSize(3);
			List<? extends IN4JSArchive> libraries = project.getLibraries();
			for (IN4JSArchive archive : libraries) {
				URI location = archive.getLocation();
				libArchives.add(location);
				if (!knownEntries.containsKey(location)) {
					Set<URI> entryURIs = Sets.newHashSet();
					traverseArchive(archive, entryURIs);
					knownEntries.put(location, Collections.unmodifiableSet(entryURIs));
				}
			}
			knownLibArchives.put(project.getLocation(), libArchives);
		} else {
			knownLibArchives.remove(project.getLocation());
		}
	}

	private void traverseArchive(IN4JSArchive archive, Set<URI> entryURIs) {
		List<? extends IN4JSSourceContainer> sourceContainers = archive.getSourceContainers();
		for (IN4JSSourceContainer sourceContainer : sourceContainers) {
			Iterator<URI> contents = sourceContainer.iterator();
			while (contents.hasNext()) {
				URI entryURI = contents.next();
				if (uriValidator.isValid(entryURI, new URIBasedStorage(entryURI))) {
					entryURIs.add(entryURI);
				}
			}
		}
	}

	public void collectNfarURIs(IProject project, Set<URI> target) {
		Optional<? extends IN4JSEclipseProject> projectOpt = eclipseCore.create(project);
		if (projectOpt.isPresent()) {
			IN4JSEclipseProject n4jsProject = projectOpt.get();
			Set<URI> archives = knownLibArchives.get(n4jsProject.getLocation());
			if (archives != null) {
				for (URI archiveURI : archives) {
					Set<URI> entries = knownEntries.get(archiveURI);
					if (entries != null) {
						target.addAll(entries);
					}
				}
			}
		}
	}

	public void collectNfarURIs(URI archiveURI, Set<URI> target) {
		Set<URI> entries = knownEntries.get(archiveURI);
		if (entries != null) {
			target.addAll(entries);
		}
	}

	public void collectNfarURIs(IFile nfarArchive, Set<URI> target) {
		URI archiveURI = URI.createPlatformResourceURI(nfarArchive.getFullPath().toString(), true);
		Set<URI> entries = knownEntries.get(archiveURI);
		if (entries != null) {
			target.addAll(entries);
		} else if (nfarArchive.exists()) {
			Optional<? extends IN4JSEclipseProject> projectOpt = eclipseCore.create(nfarArchive.getProject());
			if (projectOpt.isPresent()) {
				IN4JSEclipseProject n4jsProject = projectOpt.get();
				if (n4jsProject.exists()) {
					updateCache(n4jsProject);
				}
				entries = knownEntries.get(archiveURI);
				if (entries != null) {
					target.addAll(entries);
				} else { // project exists but archive is not on the project path - we have to scan it nevertheless
					Optional<? extends IN4JSEclipseArchive> archive = eclipseCore.findArchive(archiveURI);
					if (archive.isPresent()) {
						traverseArchive(archive.get(), target);
					}
				}
			}
		}
	}

	@Override
	public boolean isRejected(IFolder folder) {
		if (isLibraryFolder(folder)) {
			return false;
		}
		Optional<? extends IN4JSSourceContainer> sourceContainerOpt = eclipseCore.create(folder);
		return !sourceContainerOpt.isPresent();
	}

	private boolean isLibraryFolder(IResource folder) {
		Optional<? extends IN4JSEclipseProject> projectOpt = eclipseCore.create(folder.getProject());
		if (projectOpt.isPresent()) {
			IN4JSEclipseProject project = projectOpt.get();
			for (String libraryFolder : project.getLibraryFolders()) {
				if (libraryFolder.equals(folder.getProjectRelativePath().toString())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Iterable<Pair<IStorage, IProject>> getStorages(URI uri) {
		if (uri.isArchive()) {
			URIBasedStorage storage = new URIBasedStorage(uri);
			String authority = uri.authority();
			URI archiveFileURI = URI.createURI(authority.substring(0, authority.length() - 1));
			Optional<? extends IN4JSEclipseProject> optionalProject = eclipseCore.findProject(archiveFileURI);
			if (optionalProject.isPresent()) {
				return Collections.singletonList(Tuples.<IStorage, IProject> create(storage, optionalProject.get()
						.getProject()));
			} else {
				return Collections.singletonList(Tuples.create(storage, null));
			}
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public URI getUri(IStorage storage) {
		if (storage instanceof URIBasedStorage) {
			return ((URIBasedStorage) storage).getURI();
		}
		return null;
	}

}
