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
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IProjectConfig;

/**
 * Extension of {@link IProjectConfig} to include project dependencies and support for snapshots.
 */
@SuppressWarnings("restriction")
public interface XIProjectConfig extends IProjectConfig {

	// overridden to avoid "restriction" warnings in client code
	@Override
	String getName();

	/** Return all {@link URI} of files that describe the project. */
	default Collection<URI> getProjectDescriptionUris() {
		return Collections.emptySet();
	}

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

	/** Returns <code>true</code> iff this project generates output files for its source files. */
	default boolean isGeneratorEnabled() {
		return true;
	}

	/** Returns the names of all other projects the receiving project depends on. */
	Set<String> getDependencies();

}
