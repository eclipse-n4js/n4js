/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.server;

import org.eclipse.n4js.xtext.ide.server.build.ProjectStatePersister;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;

import com.google.inject.Singleton;

/**
 * Hold configuration of {@link ProjectStatePersister}
 */
@SuppressWarnings({ "unused" })
@Singleton
public class ProjectStatePersisterConfig {
	/** True iff the file that persists the project state will be deleted at startup */
	protected boolean isDeleteState = true;
	/** True iff the project state will be persisted to a file on disk after build */
	protected boolean isWriteToDisk = false;

	/** Sets whether the project state file will be deleted before at startup */
	public void setDeleteState(boolean isDeleteState) {
		this.isDeleteState = isDeleteState;
	}

	/** @return true iff the file that persists the project state will be deleted at startup */
	public boolean isDeleteState(ProjectConfigSnapshot projectConfig) {
		return isDeleteState;
	}

	/** Sets whether the project state will be persisted to disk after build */
	public void setWriteToDisk(boolean isWriteToDisk) {
		this.isWriteToDisk = isWriteToDisk;
	}

	/** @return true iff the project state will be persisted to a file on disk after build */
	public boolean isWriteToDisk(ProjectConfigSnapshot projectConfig) {
		return isWriteToDisk;
	}
}
