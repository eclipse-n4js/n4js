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

import org.eclipse.xtext.workspace.IProjectConfig;

/**
 * Extension of {@link XIProjectConfig} to include project dependencies and support for snapshots.
 */
@SuppressWarnings("restriction")
public interface XIProjectConfig extends IProjectConfig {

	/**
	 * Returns true iff this project will be indexed only, i.e. neither validated nor generated.
	 * <p>
	 * Regarding generation the semantics is that the generated files will always remain untouched, i.e. they will
	 * neither be added nor removed.
	 * <p>
	 * Regarding validation the semantics is that issues will not be created. However, issues might be cleaned/removed.
	 */
	default boolean indexOnly() {
		return false;
	}

	/** Returns the names of all other projects the receiving project depends on. */
	Set<String> getDependencies();

	/** Returns a snapshot of the current state of the workspace represented by this {@link XIProjectConfig}. */
	ProjectConfigSnapshot toSnapshot();
}
