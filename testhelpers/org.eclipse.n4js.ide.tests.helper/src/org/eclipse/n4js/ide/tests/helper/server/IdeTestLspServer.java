/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server;

import org.eclipse.n4js.ide.tests.helper.client.IdeTestLanguageClient;
import org.eclipse.n4js.xtext.ide.server.XLanguageServerImpl;
import org.eclipse.n4js.xtext.ide.server.build.BuilderFrontend;
import org.eclipse.n4js.xtext.ide.server.build.ConcurrentIndex;
import org.eclipse.n4js.xtext.workspace.BuildOrderFactory;
import org.eclipse.n4js.xtext.workspace.XWorkspaceConfigSnapshotProvider;
import org.eclipse.xtext.LanguageInfo;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Inject;

/**
 *
 */
public class IdeTestLspServer {

	/** */
	@Inject
	public BuilderFrontend lspBuilder;
	/** */
	@Inject
	public IResourceServiceProvider.Registry resourceServerProviderRegistry;
	/** */
	@Inject
	public UriExtensions uriExtensions;
	/** */
	@Inject
	public XLanguageServerImpl languageServer;
	/** */
	@Inject
	public ConcurrentIndex concurrentIndex;
	/** */
	@Inject
	public IdeTestLanguageClient languageClient;
	/** */
	@Inject
	public LanguageInfo languageInfo;
	/** */
	@Inject
	public BuildOrderFactory projectBuildOrderFactory;
	/** */
	@Inject
	public XWorkspaceConfigSnapshotProvider workspaceConfigProvider;

}
