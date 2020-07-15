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
import org.eclipse.xtext.workspace.ISourceFolder;

/**
 * An immutable, thread-safe snapshot of an {@link ISourceFolder}.
 */
@SuppressWarnings("restriction")
public interface ISourceFolderSnapshot {

	/** See {@link ISourceFolder#getName()}. */
	String getName();

	/** See {@link ISourceFolder#getPath()}. */
	URI getPath();
}
