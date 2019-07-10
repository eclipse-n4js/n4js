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
package org.eclipse.n4js.ui.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;

import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 *
 */
public class PlatformResourceURI extends SafeURI<PlatformResourceURI> {

	private IResource cachedResource;

	public PlatformResourceURI(URI location) {
		super(location);
	}

	public PlatformResourceURI(IResource resource) {
		super(URI.createPlatformResourceURI(resource.getFullPath().toString(), true));
		this.cachedResource = resource;
	}

	@Override
	protected PlatformResourceURI self() {
		return this;
	}

	@Override
	protected URI validate(URI given) throws IllegalArgumentException, NullPointerException {
		super.validate(given);
		Preconditions.checkArgument(given.isPlatformResource(), "%s", given);
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
		return getCachedResource().getType() == IResource.FILE;
	}

	@Override
	public String getName() {
		return toURI().lastSegment();
	}

	@Override
	public boolean exists() {
		return getCachedResource().exists();
	}

	@Override
	public boolean isDirectory() {
		int type = getCachedResource().getType();
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
	public PlatformResourceURI resolve(String relativePath) {
		URI base = toURI();
		if (!base.hasTrailingPathSeparator()) {
			base = base.appendSegment("");
		}
		URI result = URI.createURI(relativePath).resolve(base);
		return new PlatformResourceURI(result);
	}

	@Override
	public PlatformResourceURI appendPath(String path) {
		URI relativeURI = URI.createURI(path);
		if (!URI.validSegments(relativeURI.segments())) {
			return null;
		}
		URI base = toURI();
		if (!base.hasTrailingPathSeparator()) {
			base = base.appendSegment("");
		}
		URI result = relativeURI.resolve(base);
		return new PlatformResourceURI(result);
	}

	@Override
	public PlatformResourceURI appendSegment(String segment) {
		return new PlatformResourceURI(toURI().appendSegment(segment));
	}

	@Override
	public PlatformResourceURI appendSegments(String[] segments) {
		return new PlatformResourceURI(toURI().appendSegments(segments));
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
		Path path = toFileSystemPath();
		try {
			return new FileURI(path.toFile().getCanonicalFile());
		} catch (IOException e) {
			return new FileURI(path.toFile());
		}
	}

	@Override
	public Iterator<? extends PlatformResourceURI> getAllChildren() {
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
			getCachedResource().delete(false, null);
		} catch (CoreException e) {
			errorHandler.accept(new IOException(e));
		}
	}

	@Override
	public Path toFileSystemPath() {
		return getCachedResource().getLocation().toFile().toPath();
	}

}
