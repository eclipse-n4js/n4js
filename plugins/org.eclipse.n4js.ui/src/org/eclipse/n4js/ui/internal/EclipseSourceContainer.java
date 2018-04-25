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

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSProjectSourceContainer;
import org.eclipse.n4js.n4mf.SourceFragmentType;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseSourceContainer;

/**
 */
public class EclipseSourceContainer extends N4JSProjectSourceContainer implements IN4JSEclipseSourceContainer {

	private final IContainer container;

	/**
	 * Creates a new EclipseSourceContainer that is backed by the given folder.
	 */
	protected EclipseSourceContainer(N4JSEclipseProject project, SourceFragmentType type, IContainer container) {
		super(project, type, container.getProjectRelativePath().toString());
		this.container = container;
	}

	@Override
	public IContainer getContainer() {
		return container;
	}

	@Override
	public boolean isTest() {
		return super.isTest() && exists();
	}

	@Override
	public boolean isSource() {
		return super.isSource() && exists();
	}

	@Override
	public boolean exists() {
		return getProject().exists() && container != null && container.exists();
	}

	@Override
	public Iterator<URI> iterator() {
		if (exists()) {
			return super.iterator();
		} else {
			return Collections.emptyIterator();
		}
	}

	@Override
	public IN4JSEclipseProject getProject() {
		return (IN4JSEclipseProject) super.getProject();
	}
}
