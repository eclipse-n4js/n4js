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
package org.eclipse.n4js.internal.locations;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;

@SuppressWarnings("javadoc")
public class FileURI extends SafeURI {

	private File cachedFile;

	public FileURI(URI location) {
		super(location);
	}

	public FileURI(File file) {
		super(toFileURI(file));
	}

	private static URI toFileURI(File file) {
		URI fileURI = URI.createFileURI(file.getAbsolutePath());
		if (fileURI.authority() == null) {
			fileURI = URI.createHierarchicalURI(fileURI.scheme(), "", fileURI.device(), fileURI.segments(),
					fileURI.query(), fileURI.fragment());
		}
		return fileURI;
	}

	@Override
	protected URI validate(URI given) throws IllegalArgumentException, NullPointerException {
		super.validate(given);
		Preconditions.checkNotNull(given.authority());
		Preconditions.checkArgument(given.isFile());
		return given;
	}

	public FileURI trimFragment() {
		return new FileURI(toURI().trimFragment());
	}

	@Override
	public FileURI getParent() {
		URI uri = toURI();
		if (uri.segmentCount() > 0) {
			return new FileURI(uri.trimSegments(1));
		}
		return null;
	}

	public String toFileString() {
		return toURI().toFileString();
	}

	@Override
	public boolean exists() {
		return new File(toURI().toFileString()).exists();
	}

	@Override
	public FileURI appendPath(String path) {
		URI relativeURI = URI.createURI(path);
		if (!URI.validSegments(relativeURI.segments())) {
			return null;
		}
		URI result = relativeURI.resolve(toURI());
		return new FileURI(result);
		// String relativePathWithForwardSlashes = path.replace(File.separatorChar, '/');
		// String[] segments = relativePathWithForwardSlashes.split("/");
		// FileBasedLocation result = this;
		// for (String segment : segments) {
		// switch (segment) {
		// case "..":
		// result = result.getParent();
		// if (result == null) {
		// return null;
		// }
		// break;
		// case ".":
		// break;
		// default:
		// result = result.appendSegment(segment);
		// break;
		// }
		// }
		// return result;
	}

	@Override
	public FileURI appendSegment(String segment) {
		return new FileURI(toURI().appendSegment(segment));
	}

	@Override
	public SafeURI appendSegments(String[] segments) {
		return new FileURI(toURI().appendSegments(segments));
	}

	@Override
	public boolean isFile() {
		return getCachedFile().isFile();
	}

	private File getCachedFile() {
		if (cachedFile != null) {
			return cachedFile;
		}
		return cachedFile = new File(toURI().toFileString());
	}

	@Override
	public String getName() {
		return toURI().lastSegment();
	}

	@Override
	public boolean isDirectory() {
		return getCachedFile().isDirectory();
	}

	@Override
	public Iterable<? extends FileURI> getChildren() {
		File[] children = getCachedFile().listFiles();
		if (children == null) {
			return Collections.emptyList();
		}
		return FluentIterable.from(children).transform(FileURI::new);
	}

	@Override
	public InputStream getContents() throws IOException {
		return new BufferedInputStream(new FileInputStream(getCachedFile()));
	}

	@Override
	public String getAbsolutePath() {
		return toURI().path();
	}

	@Override
	public SafeURI resolve(String relativePath) {
		URI result = URI.createURI(relativePath).resolve(toURI());
		return new FileURI(result);
	}

	@Override
	public SafeURI resolveSymLinks() {
		try {
			return new FileURI(getCachedFile().getCanonicalFile());
		} catch (IOException e) {
			return this;
		}
	}

	@Override
	public Iterator<? extends FileURI> getAllChildren() {
		File container = getCachedFile();
		if (container.isDirectory()) {
			AbstractTreeIterator<File> treeIterator = new AbstractTreeIterator<>(container, false) {
				@Override
				protected Iterator<? extends File> getChildren(Object root) {
					if (root instanceof File) {
						final File file = (File) root;
						if (file.isDirectory()) {
							// do not iterate over contents of nested node_modules folders
							if (file.getName().equals(N4JSGlobals.NODE_MODULES)) {
								return Collections.emptyIterator();
							}
							return Arrays.asList(((File) root).listFiles()).iterator();
						}
					}
					return Collections.emptyIterator();
				}
			};
			return FluentIterable.from(() -> treeIterator).transform(FileURI::new).iterator();
		}
		return Collections.emptyIterator();
	}

	@Override
	public void delete(Consumer<? super IOException> errorHandler) {
		FileDeleter.delete(getCachedFile(), errorHandler);
	}

	@Override
	public Path toFileSystemPath() {
		return getCachedFile().toPath();
	}

}
