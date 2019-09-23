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
package org.eclipse.n4js.cli;

import org.eclipse.n4js.ide.N4JSIdeSetup;
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl;

import com.google.inject.Injector;

/**
 * Factory to create the injector, language server and other singletons for CLI use cases
 */
public class N4jscFactory {

	/** Creates a new injector */
	public static Injector createInjector() {
		return new N4JSIdeSetup().createInjectorAndDoEMFRegistration();
	}

	/** @return the {@link N4JSLanguageServerImpl} instance from the given injector */
	public static N4JSLanguageServerImpl createLanguageServer(Injector injector) {
		N4JSLanguageServerImpl languageServer = injector.getInstance(N4JSLanguageServerImpl.class);
		return languageServer;
	}

}
