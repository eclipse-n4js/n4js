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

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;

/**
 * An immutable, thread-safe snapshot of an {@link XIProjectConfig}.
 */
@SuppressWarnings("restriction")
public interface IProjectConfigSnapshot {

	/** See {@link IProjectConfig#getName()}. */
	String getName();

	/** See {@link IProjectConfig#getPath()}. */
	URI getPath();

	/** See {@link XIProjectConfig#getDependencies()}. */
	ImmutableSet<String> getDependencies();

	/** See {@link IProjectConfig#getSourceFolders()}. */
	public ImmutableCollection<? extends ISourceFolderSnapshot> getSourceFolders();
}
