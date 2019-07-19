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
package org.eclipse.n4js.external;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.singleton;
import static org.eclipse.core.runtime.IStatus.ERROR;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.internal.resources.BuildConfiguration;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.resources.ProjectDescription;
import org.eclipse.core.internal.resources.ProjectInfo;
import org.eclipse.core.internal.resources.ResourceInfo;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.ICommand;
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
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.resources.DelegatingWorkspace;
import org.eclipse.n4js.utils.resources.ExternalFile;
import org.eclipse.n4js.utils.resources.ExternalFolder;
import org.eclipse.n4js.utils.resources.IExternalResource;

import com.google.common.base.Objects;

/**
 * External project resource implementation.
 */
@SuppressWarnings("restriction")
public class ExternalProject extends Project implements IExternalResource {

	private final File file;
	private final java.net.URI uri;
	private final EclipseProjectName eclipseProjectName;
	private final Collection<String> natureIds;
	private final Collection<String> builderIds;

	/**
	 * Creates a new external project with the underlying wrapped file resource and the project nature and builder ID.
	 *
	 * @param file
	 *            the underlying file to wrap.
	 * @param natureId
	 *            the project nature ID.
	 * @param builderId
	 *            the builder ID for the project.
	 */
	public ExternalProject(File file, String natureId, String builderId) {
		this(file, singleton(natureId), singleton(builderId));
	}

	/**
	 * Creates a new external project with the underlying wrapped file resource and the project nature and builder IDs.
	 *
	 * @param file
	 *            the underlying file to wrap.
	 * @param natureIds
	 *            the project nature IDs.
	 * @param builderIds
	 *            the builder IDs for the project.
	 */
	public ExternalProject(File file, String[] natureIds, String[] builderIds) {
		this(file, Arrays.asList(natureIds), Arrays.asList(builderIds));
	}

	/**
	 * Creates a new external project with the underlying wrapped file resource and the project nature and builder IDs.
	 *
	 * @param file
	 *            the underlying file to wrap.
	 * @param natureIds
	 *            the project nature IDs.
	 * @param builderIds
	 *            the builder IDs for the project.
	 */
	public ExternalProject(File file, Iterable<String> natureIds, Iterable<String> builderIds) {
		super(new Path(file.getAbsolutePath()), null);
		checkState(file.isDirectory(), "Resource '" + file + "' is not a directory but a file.");
		this.file = file;
		this.uri = file.toURI();
		this.natureIds = newHashSet(natureIds);
		this.builderIds = newHashSet(builderIds);

		this.eclipseProjectName = new FileURI(file).getProjectName().toEclipseProjectName();
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
		return eclipseProjectName.getRawName();
	}

	@Override
	public IProject getProject() {
		return this;
	}

	@Override
	public IContainer getParent() {
		return this;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public boolean exists(int flags, boolean checkType) {
		return true;
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
		return uri;
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
	// ISchedulingRule
	// --------------------------------

	@Override
	public boolean contains(ISchedulingRule rule) {
		return getWorkspace().getRoot().contains(rule);
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return getWorkspace().getRoot().isConflicting(rule);
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
					? new ExternalFile(this, this, subFile)
					: new ExternalFolder(this, this, subFile);
		}

		return members;
	}

	@Override
	public void refreshLocal(int depth, IProgressMonitor monitor) throws CoreException {
		// Nothing to refresh
	}

	@Override
	public IFolder getFolder(String name) {
		final File folderCandidate = getFullPath().append(name).toFile();
		// Shortcut to avoid visiting members with infinite depth
		if (folderCandidate.isDirectory()) {
			return new ExternalFolder(this, this, folderCandidate);
		}

		IFolder resource = getResource(name, IFolder.class);
		if (resource != null)
			return resource;

		return new ExternalFolder(this, this, folderCandidate);
	}

	@Override
	public IFile getFile(String name) {
		final File fileCandidate = getFullPath().append(name).toFile();
		// Shortcut to avoid visiting members with infinite depth
		if (fileCandidate.isFile()) {
			return new ExternalFile(this, this, fileCandidate);
		}

		IFile resource = getResource(name, IFile.class);
		if (resource != null)
			return resource;

		return new ExternalFile(this, this, fileCandidate);
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
		} catch (CoreException e) {
			throw new RuntimeException("Error while visiting resource." + this, e);
		}
	}

	// --------------------------------
	// IProject
	// --------------------------------

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public boolean isOpen(int flags) {
		return true;
	}

	@Override
	public IBuildConfiguration getActiveBuildConfig() throws CoreException {
		return new BuildConfiguration(this);
	}

	@Override
	public ResourceInfo getResourceInfo(boolean phantom, boolean mutable) {
		ProjectDescription description = new ProjectDescription();
		description.setName(getName());
		description.setNatureIds(from(natureIds).toArray(String.class));
		ICommand[] buildSpecs = new ICommand[builderIds.size()];
		int i = 0;
		for (String builderId : builderIds) {
			ICommand command = description.newCommand();
			command.setBuilderName(builderId);
			buildSpecs[i++] = command;
		}
		description.setBuildSpec(buildSpecs);

		ProjectInfo info = new ProjectInfo();
		info.setModificationStamp(file.lastModified());
		info.setDescription(description);

		return info;
	}

	@Override
	public boolean isNatureEnabled(String natureId) throws CoreException {
		return natureIds.contains(natureId);
	}

	/**
	 * Like {@link #getActiveBuildConfig()} but doesn't check accessibility. Project must be accessible.
	 *
	 * @see #getActiveBuildConfig()
	 */
	public IBuildConfiguration unsafeGetActiveBuildConfig() {
		String configName = internalGetDescription().getActiveBuildConfig();
		try {
			if (configName != null)
				return getBuildConfig(configName);
		} catch (CoreException e) {
			// Build configuration doesn't exist; treat the first as active.
		}
		return internalGetBuildConfigs(false)[0];
	}

	@Override
	public String toString() {
		return "E" + super.toString();
	}

	@Override
	public int hashCode() {
		return null == file ? 0 : file.hashCode();
	}

	@Override
	public boolean equals(Object target) {
		if (target instanceof ExternalProject) {
			return Objects.equal(file, ((ExternalProject) target).file);
		}
		return false;
	}

}
