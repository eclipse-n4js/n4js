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
 * Abstracts over {@link IN4JSProject projects } and {@link IN4JSArchive archives}.
 */
public interface IN4JSSourceContainerAware {

	/**
	 * The source containers of this container structure, possibly empty.
	 */
	ImmutableList<? extends IN4JSSourceContainer> getSourceContainers();

	/**
	 * All direct dependencies for this structure.
	 */
	ImmutableList<? extends IN4JSSourceContainerAware> getAllDirectDependencies();

	/**
	 * @return true if this is an {@link IN4JSProject}.
	 */
	boolean isProject();

	/**
	 * @return true if this is an {@link IN4JSArchive}.
	 */
	boolean isArchive();

	/**
	 * @return true if this container structure is external to the workspace.
	 */
	boolean isExternal();

	/**
	 * The location of this structure of the project model.
	 */
	URI getLocation();

	/**
	 * @return the receiving project's ID. Also available if the project does not exist.
	 */
	String getProjectId();
}
