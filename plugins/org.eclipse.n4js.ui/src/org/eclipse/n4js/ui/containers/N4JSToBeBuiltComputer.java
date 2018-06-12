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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorage;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseSourceContainer;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.impl.IToBeBuiltComputerContribution;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.ui.resource.UriValidator;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Add the contents of NFARs to the list of to-be-built resources in to make sure they are indexed properly.
 *
 * @see IToBeBuiltComputerContribution
 */
@Singleton
@SuppressWarnings("restriction")
public class N4JSToBeBuiltComputer implements IToBeBuiltComputerContribution {

	@Inject
	private NfarStorageMapper storageMapper;

	@Inject
	private IBuilderState builderState;

	@Inject
	private IN4JSEclipseCore eclipseCore;

	@Inject
	private UriValidator uriValidator;

	/**
	 * Caches the URIs that are contained in an archive such that the archive is not opened each and every time the
	 * contents has to be iterated.
	 *
	 * This one is used in addition to the cache in the {@link NfarStorageMapper} because it's used in a different
	 * life-cycle, e.g. the builder is triggered asynchronously and has to provide information about files after they
	 * have been removed physically from disk. The {@link NfarStorageMapper} updates its cache synchronously to the
	 * delete event.
	 */
	private final Map<String, Set<URI>> knownEntries = Maps.newConcurrentMap();

	@Override
	public void removeProject(ToBeBuilt toBeBuilt, IProject project, IProgressMonitor monitor) {
		storageMapper.collectNfarURIs(project, toBeBuilt.getToBeDeleted());
		Iterator<Map.Entry<String, Set<URI>>> iterator = knownEntries.entrySet().iterator();
		String keyPrefix = project.getName() + "|";
		while (iterator.hasNext()) {
			String key = iterator.next().getKey();
			if (key.startsWith(keyPrefix)) {
				iterator.remove();
			}
		}
	}

	@Override
	public void updateProject(ToBeBuilt toBeBuilt, IProject project, IProgressMonitor monitor) throws CoreException {
		// nothing to do per project, storages are processed individually
	}

	@Override
	public boolean updateStorage(ToBeBuilt toBeBuilt, IStorage storage, IProgressMonitor monitor) {

		if (storage instanceof IFile) {
			IFile file = (IFile) storage;
			String extension = file.getFileExtension();
			// changed archive - schedule contents
			if (IN4JSArchive.NFAR_FILE_EXTENSION.equals(extension)) {
				IProject project = file.getProject();
				String key = project.getName() + "|" + storage.getFullPath().toPortableString();
				Set<URI> cachedURIs = knownEntries.remove(key);
				if (cachedURIs != null) {
					toBeBuilt.getToBeDeleted().addAll(cachedURIs);
				}
				cachedURIs = Sets.newHashSet();
				storageMapper.collectNfarURIs((IFile) storage, cachedURIs);
				knownEntries.put(key, cachedURIs);
				toBeBuilt.getToBeUpdated().addAll(cachedURIs);
				return true;
			} else if (IN4JSProject.N4MF_MANIFEST.equals(file.getName()) ||
					IN4JSProject.PACKAGE_JSON.equals(file.getName())) {
				// changed project description resource - schedule all resources from source folders
				final IN4JSEclipseProject project = eclipseCore.create(file.getProject()).orNull();
				if (null != project && project.exists()) {

					List<? extends IN4JSEclipseSourceContainer> sourceContainers = project.getSourceContainers();
					Set<URI> toBeUpdated = toBeBuilt.getToBeUpdated();
					for (IN4JSEclipseSourceContainer sourceContainer : sourceContainers) {
						for (URI uri : sourceContainer) {
							if (uriValidator.canBuild(uri, new URIBasedStorage(uri))) {
								toBeUpdated.add(uri);
							}
						}
					}
				}

				IProject resourceProject = file.getProject();
				String projectName = resourceProject.getName();
				Set<URI> toBeDeleted = toBeBuilt.getToBeDeleted();
				for (final IResourceDescription description : builderState.getAllResourceDescriptions()) {
					URI uri = description.getURI();
					if (uri.isPlatformResource()) {
						if (projectName.equals(uri.segment(1))) {
							toBeDeleted.add(uri);
						}
					}
				}

				// still return false because we want to do the normal processing for the manifest file, too
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean removeStorage(ToBeBuilt toBeBuilt, IStorage storage, IProgressMonitor monitor) {
		if (IN4JSArchive.NFAR_FILE_EXTENSION.equals(storage.getFullPath().getFileExtension())
				&& storage instanceof IFile) {
			IProject project = ((IFile) storage).getProject();
			String key = project.getName() + "|" + storage.getFullPath().toPortableString();
			Set<URI> cachedURIs = knownEntries.remove(key);
			if (cachedURIs != null) {
				toBeBuilt.getToBeDeleted().addAll(cachedURIs);
			} else {
				// cache not populated, use the index to find the URIs that shall be removed, e.g.
				// can happen after a restart of Eclipse
				Iterable<IResourceDescription> descriptions = builderState.getAllResourceDescriptions();
				String expectedAuthority = "platform:/resource" + storage.getFullPath() + "!";
				Set<URI> toBeDeleted = toBeBuilt.getToBeDeleted();
				for (IResourceDescription description : descriptions) {
					URI knownURI = description.getURI();
					if (knownURI.isArchive()) {
						String authority = knownURI.authority();
						if (expectedAuthority.equals(authority)) {
							toBeDeleted.add(knownURI);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isPossiblyHandled(IStorage storage) {
		// no additional storage types are processed
		return false;
	}

	@Override
	public boolean isRejected(IFolder folder) {
		boolean result = storageMapper.isRejected(folder);
		return result;
	}

}
