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
package org.eclipse.n4js.ui.navigator.internal;

import static com.google.common.collect.FluentIterable.from;
import static org.eclipse.ui.ISharedImages.IMG_OBJ_FILE;
import static org.eclipse.ui.ISharedImages.IMG_OBJ_FOLDER;
import static org.eclipse.ui.PlatformUI.getWorkbench;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.swt.graphics.Image;

import com.google.common.collect.FluentIterable;

/**
 * Represents a {@link File file} resource in the file system as a node in the Project Explorer.
 */
/* default */ class ResourceNode extends NodeAdapter implements IStorage {

	private static final Image SRC_FOLDER_IMG = ImageRef.SRC_FOLDER.asImage().orNull();
	private static final Image FOLDER_IMG = getWorkbench().getSharedImages().getImage(IMG_OBJ_FOLDER);
	private static final Image FILE_IMG = getWorkbench().getSharedImages().getImage(IMG_OBJ_FILE);

	private final File file;
	private final String label;

	static public ResourceNode create(final Node parent, final File file) {
		return create(parent, file, file.getName());
	}

	static public ResourceNode create(final Node parent, final File file, final String label) {
		if (file == null || !file.exists()) {
			return null;
		}
		ResourceNode resourceNode = new ResourceNode(parent, file, label);
		return resourceNode;
	}

	/**
	 * Creates a new node instance representing a file resource.
	 *
	 * @param parent
	 *            the parent of the current node.
	 * @param file
	 *            the wrapped file that the new node instance represents.
	 */
	private ResourceNode(final Node parent, final File file, final String label) {
		super(parent);
		this.file = file;
		this.label = label;
	}

	@Override
	public String getText() {
		return label;
	}

	@Override
	public Object[] getChildren() {
		if (file.isDirectory()) {
			final FluentIterable<File> subFiles = from(Arrays.asList(file.listFiles()));
			return subFiles.transform(f -> create(this, f)).toArray(ResourceNode.class);
		}
		return EMPTY_ARRAY;
	}

	@Override
	public Image getImage() {
		return file.isDirectory() ? (getParent() instanceof ResourceNode) ? FOLDER_IMG : SRC_FOLDER_IMG : FILE_IMG;
	}

	@Override
	public InputStream getContents() throws CoreException {
		try {
			return new FileInputStream(file);
		} catch (final FileNotFoundException e) {
			throw new RuntimeException("File '" + file + "' does not exist.", e);
		}
	}

	@Override
	public IPath getFullPath() {
		return new Path(file.getAbsolutePath());
	}

	@Override
	public String getName() {
		return label;
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		return null;
	}

	/* default */ File getResource() {
		return file;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ResourceNode)) {
			return false;
		}
		final ResourceNode other = (ResourceNode) obj;
		if (file == null) {
			if (other.file != null) {
				return false;
			}
		} else if (!file.equals(other.file)) {
			return false;
		}
		return true;
	}

}
