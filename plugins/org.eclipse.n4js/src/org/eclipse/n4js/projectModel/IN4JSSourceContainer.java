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
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Optional;
import com.google.common.collect.Iterators;

/**
 * The project model representation of a configured source folder in a project.
 */
public interface IN4JSSourceContainer extends Iterable<URI> {

	/**
	 * Returns the project this source container belongs to.
	 */
	IN4JSProject getProject();

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
	default Iterator<URI> iterator() {
		return Iterators.transform(getLocation().getAllChildren(), pl -> pl.toURI());
	}

	/**
	 * Returns the typesafe location of this source container.
	 */
	SafeURI<?> getLocation();

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
	SafeURI<?> findArtifact(QualifiedName name, Optional<String> fileExtension);

	/**
	 * The relative location under the project root.
	 */
	String getRelativeLocation();

	/**
	 * Returns <code>true</code> if the source container exists.
	 */
	boolean exists();
}
