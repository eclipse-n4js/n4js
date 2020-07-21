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

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IProjectConfig;

/**
 * Extension of {@link XIProjectConfig} to include project dependencies and support for snapshots.
 */
@SuppressWarnings("restriction")
public interface XIProjectConfig extends IProjectConfig {

	/** Returns the names of all other projects the receiving project depends on. */
	Set<String> getDependencies();

	/**
	 * Tells whether issues of the resource with the given URI should be hidden, i.e. not be sent to the LSP client.
	 * This is intended for things like "external libraries" which might be located inside the workspace but are not
	 * actively being developed (e.g. contents of "node_modules" folders in a Javascript/npm workspace).
	 * <p>
	 * By default, this method returns <code>true</code> for all resources that are not contained in one of
	 * {@link IProjectConfig#getSourceFolders() the project's source folders}.
	 * <p>
	 * Note that this affects the builder and therefore closed files only; once a file is being opened issues will
	 * always become visible.
	 */
	default boolean isResourceWithHiddenIssues(URI uri) {
		return findSourceFolderContaining(uri) == null;
	}

	/** Returns a snapshot of the current state of the workspace represented by this {@link XIProjectConfig}. */
	IProjectConfigSnapshot toSnapshot();
}
