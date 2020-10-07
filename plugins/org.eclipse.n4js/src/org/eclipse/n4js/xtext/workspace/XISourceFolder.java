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

import org.eclipse.xtext.workspace.ISourceFolder;

/**
 * Extension of {@link ISourceFolder} to provide support for snapshots.
 */
@SuppressWarnings("restriction")
public interface XISourceFolder extends ISourceFolder {

	/** Returns a snapshot of the current state of this {@link XISourceFolder}. */
	default SourceFolderSnapshot toSnapshot() {
		return new SourceFolderSnapshot(getName(), getPath());
	}
}
