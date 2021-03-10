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
package org.eclipse.n4js.ide.server.build;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.xtext.server.build.BuilderFrontend;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;

/**
 * Minor adjustments for N4JS.
 */
public class N4JSBuilderFrontend extends BuilderFrontend {

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		// Overridden to suppress triggering a build upon 'didSave', because we will also receive a
		// 'didChangeWatchedFiles' event for the saved file which will trigger the build.
	}

	@Override
	protected boolean isSourceFile(WorkspaceConfigSnapshot workspaceConfig, URI uri) {
		if (uri != null && N4JSGlobals.PACKAGE_JSON.equals(uri.lastSegment())) {
			// the default logic fails for package.json files in case a change in a package.json file would lead to a
			// new project showing up in the workspace (because that project does not yet exist in 'workspaceConfig',
			// the default logic would return false for 'uri')
			return true;
		}
		return super.isSourceFile(workspaceConfig, uri);
	}
}
