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
package org.eclipse.n4js.ide.xtext.server;

import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class ProjectStatePersisterConfig {

	/** @return true iff the file that persists the project state will be deleted at startup */
	public boolean isDeleteState() {
		return true;
	}

	/** @return true iff the project state will be persisted to a file on disk after build */
	public boolean isWriteToDisk() {
		return false;
	}

}
