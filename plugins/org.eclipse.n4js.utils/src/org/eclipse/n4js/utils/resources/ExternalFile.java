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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

/**
 * External file resource implementation.
 */
@SuppressWarnings("restriction")
public class ExternalFile extends org.eclipse.core.internal.resources.File implements IExternalResource {

	private final File file;
	private final java.net.URI uri;
	private final IProject project;
	private final IContainer parent;

	/**
	 * Creates a new external file with the given container project, the direct parent container and the external file
	 * that is wrapped by the new instance.
	 *
	 * @param project
	 *            the container project.
	 * @param parent
	 *            the direct parent container.
	 * @param file
	 *            the external file that is wrapped by the new instance.
	 */
	public ExternalFile(IProject project, IContainer parent, File file) {
		super(new Path(file.getAbsolutePath()), null);
		this.file = file;
		this.uri = file.toURI();
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
		return file.exists() && file.isFile();
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
		visitor.visit(this);
	}

	@Override
	public URI getLocationURI() {
		return uri;
	}

	@Override
	public IPath getLocation() {
		return new Path(file.toPath().toString());
	}

	@Override
	public void delete(boolean force, IProgressMonitor monitor) throws CoreException {
		file.delete();
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
	// IFile
	// --------------------------------

	@Override
	public InputStream getContents() throws CoreException {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File '" + file + "' does not exist.", e);
		}
	}

	@Override
	public IPath getFullPath() {
		return new Path(file.getAbsolutePath());
	}

	@Override
	public String toString() {
		return "E" + super.toString();
	}

}
