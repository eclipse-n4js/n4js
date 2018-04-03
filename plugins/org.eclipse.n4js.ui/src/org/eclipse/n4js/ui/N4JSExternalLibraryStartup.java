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
package org.eclipse.n4js.ui;

import org.eclipse.n4js.external.GitCloneSupplier;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.ui.IStartup;

import com.google.inject.Inject;

/**
 * N4JS IDE startup hook to makes sure that the local Git repository exists for the type definition files.
 */
public class N4JSExternalLibraryStartup implements IStartup {

	@Inject
	private GitCloneSupplier gitCloneSupplier;

	@Override
	public void earlyStartup() {
		// Client code can still clone the repository on demand. (Mind plug-in UI tests.)
		if (ExternalLibrariesActivator.requiresInfrastructureForLibraryManager()) {
			new Thread(() -> {
				gitCloneSupplier.synchronizeTypeDefinitions();
			}).start();
		}
	}

}
