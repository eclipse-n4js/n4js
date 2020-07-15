/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.workspace;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * An immutable, thread-safe snapshot of an {@link XIWorkspaceConfig}.
 */
@SuppressWarnings("restriction")
public interface IWorkspaceConfigSnapshot {

	/** See {@link XIWorkspaceConfig#getPath()}. */
	URI getPath();

	/** See {@link IWorkspaceConfig#getProjects()}. */
	Collection<? extends IProjectConfigSnapshot> getProjects();

	/** See {@link IWorkspaceConfig#findProjectContaining(URI)}. */
	IProjectConfigSnapshot findProjectContaining(URI member);

	/** See {@link IWorkspaceConfig#findProjectByName(String)}. */
	IProjectConfigSnapshot findProjectByName(String name);

	/** Create a copy of this snapshot with all projects removed. */
	IWorkspaceConfigSnapshot clear();

	/**
	 * Create a copy of this snapshot with the given projects being changed/removed.
	 *
	 * @param changedProjects
	 *            projects being added or changed.
	 * @param removedProjects
	 *            projects being removed.
	 */
	IWorkspaceConfigSnapshot update(Iterable<? extends IProjectConfigSnapshot> changedProjects,
			Iterable<String> removedProjects);
}
