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

import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

/**
 * Setup {@link ProjectStatePersister}
 */
public class N4JSProjectStatePersisterConfig extends ProjectStatePersisterConfig {

	@Override
	public String getPersistedFileVersion() {
		return N4JSLanguageUtils.getLanguageVersion();
	}

}
