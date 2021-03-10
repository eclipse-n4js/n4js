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
package org.eclipse.n4js.ide.server;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.xtext.server.build.ProjectStatePersister;

/**
 * Setup {@link ProjectStatePersister}
 */
public class N4JSProjectStatePersister extends ProjectStatePersister {

	@Override
	public String getPersistedFileName() {
		return N4JSGlobals.N4JS_PROJECT_STATE;
	}

	@Override
	public String getLanguageVersion() {
		return N4JSLanguageUtils.getLanguageVersion();
	}

}
