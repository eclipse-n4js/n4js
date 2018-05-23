/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.organize.imports;

import java.util.function.Predicate;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;

import com.google.inject.Inject;

import org.eclipse.n4js.projectModel.IN4JSCore;

/**
 * Tests if provided {@link IFile} is located in N4JS source container (according to {@link IN4JSCore}).
 */
public class FileContainerFilter implements Predicate<IFile> {
	private final IN4JSCore core;

	/**
	 * This class is expected to be injected, but caller can call constructor by providing all required parameters.
	 *
	 * @param core
	 *            {@link IN4JSCore} used to look for the source container
	 */
	@Inject
	FileContainerFilter(IN4JSCore core) {
		this.core = core;
	}

	/**
	 * Check if a file is inside N4JS Source Container.
	 */
	@Override
	public boolean test(IFile iFile) {
		URI fileURI = URI.createPlatformResourceURI(iFile.getFullPath().toString(), true);
		return core.findN4JSSourceContainer(fileURI).isPresent();
	}

}
