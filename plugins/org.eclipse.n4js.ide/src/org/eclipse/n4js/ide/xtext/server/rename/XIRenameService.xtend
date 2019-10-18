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
package org.eclipse.n4js.ide.xtext.server.rename

import org.eclipse.lsp4j.PrepareRenameResult
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.RenameParams
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.WorkspaceEdit
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.ide.server.ILanguageServerAccess
import org.eclipse.xtext.ide.server.WorkspaceManager
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager

/**
 * @author koehnlein - Initial contribution and API
 * @since 2.13
 * @deprecated use {IRenameService2} instead.
 */
@Deprecated
interface XIRenameService {

	@Deprecated
	def WorkspaceEdit rename(XWorkspaceManager workspaceManager, RenameParams renameParams, CancelIndicator cancelIndicator)
}