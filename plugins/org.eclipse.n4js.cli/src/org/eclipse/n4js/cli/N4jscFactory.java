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

import org.eclipse.n4js.cli.compiler.N4jscLanguageClient;
import org.eclipse.n4js.ide.N4JSIdeSetup;
import org.eclipse.n4js.ide.server.N4JSLanguageServerImpl;
import org.eclipse.n4js.ide.server.N4JSWorkspaceManager;

import com.google.inject.Injector;

/**
 * Factory to create the injector, language server and other singletons for CLI use cases
 */
public class N4jscFactory {

	static N4jscFactory INSTANCE = new N4jscFactory();

	/** @return the {@link N4jscBackend} instance from the given injector */
	public static N4jscBackend createBackend() throws Exception {
		return INSTANCE.internalCreateBackend();
	}

	/** Creates a new injector */
	public static Injector createInjector() {
		return INSTANCE.internalCreateInjector();
	}

	/** @return the {@link N4JSLanguageServerImpl} instance from the given injector */
	public static N4JSLanguageServerImpl getLanguageServer(Injector injector) {
		return INSTANCE.internalGetLanguageServer(injector);
	}

	/** @return the {@link N4jscLanguageClient} instance from the given injector */
	public static N4jscLanguageClient getLanguageClient(Injector injector) {
		return INSTANCE.internalGetLanguageClient(injector);
	}

	/** @return the {@link N4JSWorkspaceManager} instance from the given injector */
	public static N4JSWorkspaceManager getWorkspaceManager(Injector injector) {
		return INSTANCE.internalGetWorkspaceManager(injector);
	}

	N4jscBackend internalCreateBackend() throws Exception {
		return new N4jscBackend();
	}

	Injector internalCreateInjector() {
		return new N4JSIdeSetup().createInjectorAndDoEMFRegistration();
	}

	N4JSLanguageServerImpl internalGetLanguageServer(Injector injector) {
		N4JSLanguageServerImpl languageServer = injector.getInstance(N4JSLanguageServerImpl.class);
		return languageServer;
	}

	N4jscLanguageClient internalGetLanguageClient(Injector injector) {
		N4jscLanguageClient callback = injector.getInstance(N4jscLanguageClient.class);
		return callback;
	}

	N4JSWorkspaceManager internalGetWorkspaceManager(Injector injector) {
		N4JSWorkspaceManager workspaceManager = injector.getInstance(N4JSWorkspaceManager.class);
		return workspaceManager;
	}
}
