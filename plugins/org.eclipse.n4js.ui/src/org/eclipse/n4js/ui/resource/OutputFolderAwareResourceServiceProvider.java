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
package org.eclipse.n4js.ui.resource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;

/**
 */
@Singleton
public class OutputFolderAwareResourceServiceProvider extends DefaultResourceUIServiceProvider {

	private final IN4JSEclipseCore eclipseCore;

	/**
	 * The default constructor for this instance. Should not be called directly but used with dependency injection.
	 *
	 * @param delegate
	 *            the delegate runtime services
	 */
	@Inject
	public OutputFolderAwareResourceServiceProvider(IResourceServiceProvider delegate, IN4JSEclipseCore eclipseCore) {
		super(delegate);
		this.eclipseCore = eclipseCore;
	}

	@Override
	public boolean canBuild(URI uri, IStorage storage) {
		if (storage instanceof IFile && super.canBuild(uri, storage)) {
			// mark all files that do not reside in source folders as already handled
			Optional<? extends IN4JSSourceContainer> optional = eclipseCore.create((IFile) storage);
			if (!(optional.isPresent() && optional.get().exists())) {
				return false;
			}
			return true;
		}
		return super.canBuild(uri, storage);
	}

}
