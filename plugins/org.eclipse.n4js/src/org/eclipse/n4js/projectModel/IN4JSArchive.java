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

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.ImmutableList;

/**
 * The model for an nfar file that is used as a library.
 */
public interface IN4JSArchive extends IN4JSSourceContainerAware {

	/**
	 * The file extension for archives (without the '.' (dot)).
	 */
	String NFAR_FILE_EXTENSION = "nfar";

	/**
	 * The file extension for archives (with a leading '.' (dot)).
	 */
	String NFAR_FILE_EXTENSION_WITH_DOT = "." + NFAR_FILE_EXTENSION;

	/**
	 * Returns the project that uses the library.
	 */
	IN4JSProject getProject();

	/**
	 * The name of the project that is packed in the archive.
	 */
	String getLibraryName();

	/**
	 * The archive file name, including the suffix '.nfar'
	 */
	String getFileName();

	/**
	 * The location of the archive that can be used to inspect its content.
	 */
	@Override
	URI getLocation();

	/**
	 * The libraries that are referenced by this library.
	 */
	ImmutableList<? extends IN4JSArchive> getReferencedLibraries();

}
