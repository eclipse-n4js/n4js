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

import static java.util.Collections.emptyList;
import static org.eclipse.n4js.internal.N4JSModel.DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ArchiveURIUtil;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.N4JSSourceContainerType;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class EclipseBasedN4JSWorkspace extends InternalN4JSWorkspace {

	private final IWorkspaceRoot workspace;

	private final IResourceSetProvider resourceSetProvider;

	private final Map<URI, ProjectDescription> cache = Maps.newHashMap();

	private ProjectDescriptionLoadListener listener;

	/**
	 * Public for testing purpose.
	 */
	@Inject
	public EclipseBasedN4JSWorkspace(
			IWorkspaceRoot workspace,
			IResourceSetProvider resourceSetProvider) {
		this.workspace = workspace;
		this.resourceSetProvider = resourceSetProvider;
	}

	IWorkspaceRoot getWorkspace() {
		return workspace;
	}

	@Override
	public URI findProjectWith(URI nestedLocation) {
		if (nestedLocation.isPlatformResource()
				&& nestedLocation.segmentCount() >= DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) {
			return URI.createPlatformResourceURI(nestedLocation.segment(1), true);
		}
		return null;
	}

	@Override
	public ProjectDescription getProjectDescription(URI location) {
		if (!location.isPlatformResource()) {
			return null;
		}
		ProjectDescription existing = cache.get(location);
		if (existing == null) {
			if (location.isPlatformResource() && location.segmentCount() == DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) {
				existing = loadManifest(location.appendSegment(IN4JSProject.N4MF_MANIFEST));
			} else {
				existing = loadManifest(ArchiveURIUtil.createURI(location, IN4JSProject.N4MF_MANIFEST));
			}
			if (existing != null) {
				cache.put(location, existing);
				if (listener != null) {
					listener.onDescriptionLoaded(location);
				}
			}
		}
		return existing;
	}

	ProjectDescription loadManifest(URI manifest) {
		try {
			ProjectDescription result = null;
			ResourceSet resourceSet = resourceSetProvider.get(null /* we don't care about the project right now */);
			String platformPath = manifest.toPlatformString(true);
			if (manifest.isArchive() || platformPath != null) {
				if (manifest.isArchive() || workspace.getFile(new Path(platformPath)).exists()) {
					Resource resource = resourceSet.getResource(manifest, true);
					if (resource != null) {
						List<EObject> contents = resource.getContents();
						if (contents.isEmpty() || !(contents.get(0) instanceof ProjectDescription)) {
							return null;
						}
						result = (ProjectDescription) contents.get(0);
						contents.clear();
					}
				}
			}
			return result;
		} catch (WrappedException e) {
			throw new IllegalStateException("Unexpected manifest URI: " + manifest, e);
		}
	}

	@Override
	public URI getLocation(URI projectURI, ProjectReference projectReference,
			N4JSSourceContainerType expectedN4JSSourceContainerType) {

		if (projectURI.segmentCount() >= DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) {
			String expectedProjectName = projectReference.getProject().getProjectId();
			if (expectedProjectName != null && expectedProjectName.length() > 0) {
				IProject existingProject = workspace.getProject(expectedProjectName);
				if (existingProject.isAccessible()) {
					if (expectedN4JSSourceContainerType == N4JSSourceContainerType.ARCHIVE) {
						return null;
					} else {
						return URI.createPlatformResourceURI(expectedProjectName, true);
					}
				} else if (expectedN4JSSourceContainerType == N4JSSourceContainerType.ARCHIVE) {
					for (String libFolder : getLibraryFolders(projectURI)) {
						IFile archiveFile = workspace.getFile(new Path(projectURI.segment(1) + "/" + libFolder
								+ "/"
								+ expectedProjectName
								+ IN4JSArchive.NFAR_FILE_EXTENSION_WITH_DOT));
						if (archiveFile.exists()) {
							return URI.createPlatformResourceURI(archiveFile.getFullPath().toString(), true);
						}
					}
				}
			}
		}
		return null;
	}

	private List<String> getLibraryFolders(URI projectURI) {
		ProjectDescription pd = getProjectDescription(projectURI);
		return null == pd ? emptyList() : pd.getLibraryPaths();
	}

	@Override
	public Iterator<URI> getArchiveIterator(final URI archiveLocation, String archiveRelativeLocation) {
		ZipInputStream stream = null;
		try {
			stream = getArchiveStream(archiveLocation);
			Iterator<ZipEntry> entries = getArchiveIterator(stream, archiveRelativeLocation);
			return toArchiveURIs(archiveLocation, entries);
		} catch (CoreException | IOException e) {
			return Iterators.emptyIterator();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}

	private ZipInputStream getArchiveStream(final URI archiveLocation) throws CoreException, IOException {
		if (archiveLocation.isPlatformResource()) {
			IFile workspaceFile = workspace.getFile(new Path(archiveLocation.toPlatformString(true)));
			return new ZipInputStream(workspaceFile.getContents());
		} else {
			return new ZipInputStream(new URL(archiveLocation.toString()).openStream());
		}

	}

	@Override
	public UnmodifiableIterator<URI> getFolderIterator(URI folderLocation) {
		final IContainer container;
		if (DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT == folderLocation.segmentCount()) {
			container = workspace.getProject(folderLocation.lastSegment());
		} else {
			container = workspace.getFolder(new Path(folderLocation.toPlatformString(true)));
		}
		if (container != null && container.exists()) {
			final List<URI> result = Lists.newLinkedList();
			try {
				container.accept(new IResourceVisitor() {
					@Override
					public boolean visit(IResource resource) throws CoreException {
						if (resource.getType() == IResource.FILE) {
							result.add(URI.createPlatformResourceURI(resource.getFullPath().toString(), true));
						}
						return true;
					}
				});
				return Iterators.unmodifiableIterator(result.iterator());
			} catch (CoreException e) {
				return Iterators.unmodifiableIterator(result.iterator());
			}
		}
		return Iterators.emptyIterator();
	}

	@Override
	public URI findArtifactInFolder(URI folderLocation, String folderRelativePath) {
		final String folderLocationString = folderLocation.toPlatformString(true);
		if (null != folderLocationString) {
			final IFolder folder = workspace.getFolder(new Path(folderLocationString));
			final String subPathStr = folderRelativePath.replace(File.separator, "/");
			final IPath subPath = new Path(subPathStr);
			final IFile file = folder != null ? folder.getFile(subPath) : null;
			if (file != null && file.exists()) {
				return folderLocation.appendSegments(subPathStr.split("/"));
			}
		}
		return null;
	}

	void discardEntry(URI uri) {
		cache.remove(uri);
	}

	void discardEntries() {
		cache.clear();
	}

	void setProjectDescriptionLoadListener(ProjectDescriptionLoadListener listener) {
		this.listener = listener;
	}

}
