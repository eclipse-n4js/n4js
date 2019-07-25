/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.projectModel.locations;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;

import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * Wrapper around platform resource URIs.
 */
public final class PlatformResourceURI extends SafeURI<PlatformResourceURI> {

	private IResource cachedResource;

	/**
	 * Create a new typesafe wrapper for the given platform resource location.
	 *
	 * @param location
	 *            the platform resource uri.
	 */
	public PlatformResourceURI(URI location) {
		super(location);
	}

	/**
	 * Creates a new typesafe location representation of the given resource.
	 *
	 * @param resource
	 *            the resource.
	 */
	public PlatformResourceURI(IResource resource) {
		super(URI.createPlatformResourceURI(resource.getFullPath().toString(), true));
		this.cachedResource = resource;
	}

	@Override
	protected URI validate(URI given) throws IllegalArgumentException, NullPointerException {
		super.validate(given);
		Preconditions.checkArgument(given.isPlatformResource(), "%s", given);
		Preconditions.checkArgument(given.segmentCount() >= 2, "%s", given);
		return given;
	}

	private IResource getCachedResource() {
		if (cachedResource != null) {
			return cachedResource;
		}
		return cachedResource = ResourcesPlugin.getWorkspace().getRoot().findMember(toURI().toPlatformString(true));
	}

	@Override
	public boolean isFile() {
		IResource r = getCachedResource();
		if (r == null) {
			return false;
		}
		return r.getType() == IResource.FILE;
	}

	@Override
	public String getName() {
		return toURI().lastSegment();
	}

	@Override
	public boolean exists() {
		IResource r = getCachedResource();
		return r != null && r.exists();
	}

	@Override
	public boolean isDirectory() {
		IResource r = getCachedResource();
		if (r == null) {
			return false;
		}
		int type = r.getType();
		return type == IResource.FOLDER || type == IResource.PROJECT;
	}

	@Override
	public Iterable<? extends PlatformResourceURI> getChildren() {
		IResource resource = getCachedResource();
		if (resource instanceof IContainer) {
			try {
				IResource[] children = ((IContainer) resource).members();
				return FluentIterable.from(children).transform(PlatformResourceURI::new);
			} catch (CoreException e) {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	@Override
	public InputStream getContents() throws IOException {
		IResource resource = getCachedResource();
		if (resource instanceof IFile) {
			try {
				return ((IFile) resource).getContents();
			} catch (CoreException e) {
				throw new IOException(e);
			}
		}
		return null;
	}

	@Override
	public String getAbsolutePath() {
		return toURI().toPlatformString(true);
	}

	@Override
	protected PlatformResourceURI createFrom(URI uri) {
		return new PlatformResourceURI(uri);
	}

	@Override
	public PlatformResourceURI getParent() {
		URI uri = toURI();
		if (uri.segmentCount() > 2) {
			return new PlatformResourceURI(uri.trimSegments(1));
		}
		return null;
	}

	@Override
	public FileURI resolveSymLinks() {
		File file = toJavaIoFile();
		try {
			return new FileURI(file.getCanonicalFile());
		} catch (IOException e) {
			return new FileURI(file);
		}
	}

	@Override
	public Iterator<PlatformResourceURI> getAllChildren() {
		IResource container = getCachedResource();
		if (container instanceof IContainer) {
			final List<PlatformResourceURI> result = Lists.newArrayList();
			try {
				container.accept(new IResourceVisitor() {
					@Override
					public boolean visit(IResource resource) throws CoreException {
						if (resource.getType() == IResource.FILE)
							result.add(new PlatformResourceURI(resource));
						// do not iterate over contents of nested node_modules folders
						if (resource.getType() == IResource.FOLDER
								&& resource.getName().equals(N4JSGlobals.NODE_MODULES)) {
							return false;
						}
						return true;
					}
				});
				return Iterators.unmodifiableIterator(result.iterator());
			} catch (CoreException e) {
				return Iterators.unmodifiableIterator(result.iterator());
			}
		}
		return Iterators.unmodifiableIterator(Collections.emptyIterator());
	}

	@Override
	public void delete(Consumer<? super IOException> errorHandler) {
		try {
			IResource r = getCachedResource();
			if (r == null) {
				return;
			}
			r.delete(false, null);
		} catch (CoreException e) {
			errorHandler.accept(new IOException(e));
		}
	}

	/**
	 * Obtain the equivalent file URI.
	 */
	@Override
	public FileURI toFileURI() {
		return new FileURI(toJavaIoFile());
	}

	@Override
	public File toJavaIoFile() {
		IResource r = getCachedResource();
		if (r == null) {
			IPath workspaceLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			IPath doesNotExist = workspaceLocation.append(toURI().toPlatformString(true));
			return doesNotExist.toFile();
		}
		return r.getLocation().toFile();
	}

}
