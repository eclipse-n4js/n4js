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
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;

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
	public static Injector getOrCreateInjector() {
		return INSTANCE.internalGetOrCreateInjector();
	}

	/** @return the {@link XLanguageServerImpl} instance from the given injector */
	public static XLanguageServerImpl getLanguageServer() {
		return INSTANCE.internalGetLanguageServer();
	}

	/** @return the {@link N4jscLanguageClient} instance from the given injector */
	public static N4jscLanguageClient getLanguageClient() {
		return INSTANCE.internalGetLanguageClient();
	}

	/** @return the {@link XWorkspaceManager} instance from the given injector */
	public static XWorkspaceManager getWorkspaceManager() {
		return INSTANCE.internalGetWorkspaceManager();
	}

	Injector injector;

	N4jscBackend internalCreateBackend() throws Exception {
		return new N4jscBackend();
	}

	Injector internalGetOrCreateInjector() {
		if (injector == null) {
			injector = new N4JSIdeSetup().createInjectorAndDoEMFRegistration();
		}
		return injector;
	}

	XLanguageServerImpl internalGetLanguageServer() {
		XLanguageServerImpl languageServer = internalGetOrCreateInjector().getInstance(XLanguageServerImpl.class);
		return languageServer;
	}

	N4jscLanguageClient internalGetLanguageClient() {
		N4jscLanguageClient callback = internalGetOrCreateInjector().getInstance(N4jscLanguageClient.class);
		return callback;
	}

	XWorkspaceManager internalGetWorkspaceManager() {
		XWorkspaceManager workspaceManager = internalGetOrCreateInjector().getInstance(XWorkspaceManager.class);
		return workspaceManager;
	}
}
