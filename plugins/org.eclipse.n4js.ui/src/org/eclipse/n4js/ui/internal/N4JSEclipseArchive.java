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
package org.eclipse.n4js.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;

import com.google.common.collect.ImmutableList;

import org.eclipse.n4js.internal.N4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseArchive;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSEclipseArchive extends N4JSArchive implements IN4JSEclipseArchive {

	private final IFile archive;

	protected N4JSEclipseArchive(N4JSEclipseProject project, IFile archive) {
		super(project, URI.createPlatformResourceURI(archive.getFullPath().toString(), true));
		this.archive = archive;
	}

	@Override
	public IFile getArchiveFile() {
		return archive;
	}

	@Override
	public boolean exists() {
		return getProject().exists() && archive.exists();
	}

	@Override
	public N4JSEclipseProject getProject() {
		return (N4JSEclipseProject) super.getProject();
	}

	@Override
	protected N4JSEclipseModel getModel() {
		return getProject().getModel();
	}

	@Override
	public ImmutableList<? extends IN4JSSourceContainer> getSourceContainers() {
		if (exists())
			return super.getSourceContainers();
		return ImmutableList.of();
	}

	@Override
	public ImmutableList<? extends IN4JSEclipseArchive> getReferencedLibraries() {
		return getModel().getLibraries(this);
	}

}
