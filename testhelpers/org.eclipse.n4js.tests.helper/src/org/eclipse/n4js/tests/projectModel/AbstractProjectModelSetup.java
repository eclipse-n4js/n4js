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
package org.eclipse.n4js.tests.projectModel;

import org.eclipse.n4js.N4JSGlobals;

/**
 */
public abstract class AbstractProjectModelSetup {
	/***/
	public static final String LIB_FOLDER_NAME = "lib";

	/** The name of the file that provides a project's description. */
	public static final String PROJECT_DESCRIPTION_FILENAME = N4JSGlobals.PACKAGE_JSON;

	/***/
	protected final AbstractProjectModelTest host;

	/***/
	protected AbstractProjectModelSetup(AbstractProjectModelTest host) {
		this.host = host;
	}

	/***/
	protected abstract void createTempProjects();

	/***/
	protected abstract void deleteTempProjects();

}
