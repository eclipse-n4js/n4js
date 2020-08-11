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

import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.n4js.ide.xtext.server.build.BuilderFrontend;

/**
 * Minor adjustments for N4JS.
 */
public class N4JSBuilderFrontend extends BuilderFrontend {

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		// Overridden to suppress triggering a build upon 'didSave', because we will also receive a
		// 'didChangeWatchedFiles' event for the saved file which will trigger the build.
	}
}
