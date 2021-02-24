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
import org.eclipse.n4js.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.xtext.server.build.XWorkspaceManager;
import org.eclipse.xtext.testing.GlobalRegistries;

import com.google.common.base.Optional;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Factory to create the injector, language server and other singletons for CLI use cases
 */
public class N4jscFactory {

	static N4jscFactory INSTANCE = new N4jscFactory();

	/** Resets the injector setup */
	public static void resetInjector() {
		INSTANCE.injector = null;
		GlobalRegistries.clearGlobalRegistries(); // clears singletons from EMF global registries
	}

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
			injector = new N4JSIdeSetup() {
				@Override
				protected Optional<Class<? extends Module>> getOverridingModule() {
					return internalGetOverridingModule();
				}
			}.createInjectorAndDoEMFRegistration();
		}
		return injector;
	}

	Optional<Class<? extends Module>> internalGetOverridingModule() {
		return Optional.absent();
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
