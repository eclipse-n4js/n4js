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
package org.eclipse.n4js.projectModel;

import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Optional;

/**
 * The project model representation of a configured source folder either in a library / archive or in a project.
 */
public interface IN4JSSourceContainer extends Iterable<URI> {

	/**
	 * Returns the project this source container belongs to. If the source container is nested in an archive, the
	 * project where the archive is used as a library is returned.
	 */
	// TODO should return IN4JSComponent
	IN4JSProject getProject();

	/**
	 * Returns the archive this source folder belongs to or <code>null</code>.
	 */
	IN4JSArchive getLibrary();

	/**
	 * Returns <code>true</code> if the source container is contained in a library.
	 */
	boolean isLibrary();

	/**
	 * Returns <code>true</code> if the source container contains tests.
	 */
	boolean isTest();

	/**
	 * Returns <code>true</code> if the source container contains production code.
	 */
	boolean isSource();

	/**
	 * Returns <code>true</code> if the source container contains external code.
	 */
	boolean isExternal();

	/**
	 * Returns all contained artifact URIs.
	 */
	@Override
	Iterator<URI> iterator();

	/**
	 * If the source container contains an file for the given fully qualified name and file extension, this method will
	 * return a URI for this file of the same format as the URIs returned by method {@link #iterator()}. Otherwise, this
	 * method returns <code>null</code>.
	 * <p>
	 * The file extension may but need not contain a leading '.'.
	 * <p>
	 * Implementations are expected to be optimized for fast look-up (in particular, they should avoid iterating over
	 * all URIs returned by method {@link #iterator()}).
	 */
	URI findArtifact(QualifiedName name, Optional<String> fileExtension);

	/**
	 * The relative location in the archive file or under the project root.
	 */
	String getRelativeLocation();

	/**
	 * The absolute location of this source container.
	 */
	URI getLocation();

	/**
	 * Returns <code>true</code> if the source container exists.
	 */
	boolean exists();
}
