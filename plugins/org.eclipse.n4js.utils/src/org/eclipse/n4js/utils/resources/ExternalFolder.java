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
package org.eclipse.n4js.utils.resources;

import static org.eclipse.core.runtime.IStatus.ERROR;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import org.eclipse.n4js.utils.io.FileDeleter;

/**
 * External folder resource implementation.
 */
@SuppressWarnings("restriction")
public class ExternalFolder extends Folder implements IExternalResource {

	private final File file;
	private final IProject project;
	private final IContainer parent;

	/**
	 * Creates a new external folder with the given project, parent and the external resource that the new instance
	 * wraps.
	 *
	 * @param project
	 *            the container project.
	 * @param parent
	 *            the parent container.
	 * @param file
	 *            the file that is wrapped by the new folder instance.
	 */
	public ExternalFolder(IProject project, IContainer parent, File file) {
		super(new Path(file.getAbsolutePath()), null);
		this.file = file;
		this.project = project;
		this.parent = parent;
	}

	@Override
	public File getExternalResource() {
		return file;
	}

	// --------------------------------
	// IResource
	// --------------------------------

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public IContainer getParent() {
		return parent;
	}

	@Override
	public boolean exists() {
		return file.exists() && file.isDirectory();
	}

	@Override
	public boolean exists(int flags, boolean checkType) {
		return exists();
	}

	@Override
	public boolean isAccessible() {
		return exists();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isReadOnly() {
		return !file.canWrite();
	}

	@Override
	public void accept(IResourceVisitor visitor) throws CoreException {
		accept(visitor, DEPTH_INFINITE, NONE);
	}

	@Override
	public void accept(IResourceVisitor visitor, int depth, boolean includePhantoms) throws CoreException {
		accept(visitor, DEPTH_INFINITE, NONE);
	}

	@Override
	public void accept(IResourceVisitor visitor, int depth, int memberFlags) throws CoreException {
		if (depth == DEPTH_ZERO) {
			visitor.visit(this);
		} else {
			if (visitor.visit(this)) {
				for (IResource member : members()) {
					member.accept(visitor, DEPTH_ONE == depth ? DEPTH_ZERO : DEPTH_INFINITE, memberFlags);
				}
			}
		}
	}

	@Override
	public URI getLocationURI() {
		return file.toURI();
	}

	@Override
	public IPath getLocation() {
		return new Path(file.toPath().toString());
	}

	@Override
	public void delete(boolean force, IProgressMonitor monitor) throws CoreException {
		try {
			FileDeleter.delete(file.toPath());
		} catch (final IOException e) {
			throw new CoreException(
					new Status(ERROR, "org.eclipse.n4js.utils", "Error while deleting resource: " + file, e));
		}
	}

	@Override
	public void delete(boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		delete(force, monitor);
	}

	@Override
	public void delete(int updateFlags, IProgressMonitor monitor) throws CoreException {
		delete(true, monitor);
	}

	@Override
	public void deleteResource(boolean convertToPhantom, MultiStatus status) throws CoreException {
		delete(true, new NullProgressMonitor());
	}

	@Override
	public IMarker findMarker(long id) {
		return null;
	}

	@Override
	public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
		return new IMarker[0];
	}

	@Override
	public void deleteMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
		// Nothing to do for external resources.
	}

	@Override
	public IPath getProjectRelativePath() {
		java.nio.file.Path projectPath = ((IExternalResource) getProject()).getExternalResource().toPath();
		java.nio.file.Path currentPath = getExternalResource().toPath();
		java.nio.file.Path relativePath = projectPath.relativize(currentPath);
		return new Path(relativePath.toString());
	}

	@Override
	public IWorkspace getWorkspace() {
		return new DelegatingWorkspace();
	}

	@Override
	public void touch(IProgressMonitor monitor) throws CoreException {
		if (exists()) {
			file.setLastModified(System.currentTimeMillis());
		}
	}

	// --------------------------------
	// IContainer
	// --------------------------------

	@Override
	public IResource[] members() throws CoreException {
		return members(NONE);
	}

	@Override
	public IResource[] members(boolean phantom) throws CoreException {
		return members(NONE);
	}

	@Override
	public IResource[] members(int memberFlags) throws CoreException {
		File[] subFiles = file.listFiles();
		IResource[] members = new IResource[subFiles.length];
		for (int i = 0; i < subFiles.length; i++) {
			File subFile = subFiles[i];
			members[i] = subFile.isFile()
					? new ExternalFile(getProject(), this, subFile)
					: new ExternalFolder(getProject(), this, subFile);
		}

		return members;
	}

	@Override
	public void refreshLocal(int depth, IProgressMonitor monitor) throws CoreException {
		// Nothing to refresh
	}

	@Override
	public IFolder getFolder(String name) {
		return getResource(name, IFolder.class);
	}

	@Override
	public IFile getFile(String name) {
		return getResource(name, IFile.class);
	}

	private <T extends IResource> T getResource(String name, Class<T> resourceClass) {
		final File fileCandidate = getFullPath().append(name).toFile();
		final AtomicReference<T> actualRef = new AtomicReference<>();
		if (fileCandidate.exists()) {
			acceptUnsafe(resource -> {
				if (resource instanceof IExternalResource && resourceClass.isAssignableFrom(resource.getClass())) {
					if (fileCandidate.equals(((IExternalResource) resource).getExternalResource())) {
						actualRef.set(resourceClass.cast(resource));
						return false;
					}
				}
				return true;
			});
		}

		return actualRef.get(); // TODO return with missing instance?
	}

	private void acceptUnsafe(IResourceVisitor visitor) {
		try {
			accept(visitor);
		} catch (final CoreException e) {
			throw new RuntimeException("Error while visiting resource." + this, e);
		}
	}

	@Override
	public String toString() {
		return "E" + super.toString();
	}

}
