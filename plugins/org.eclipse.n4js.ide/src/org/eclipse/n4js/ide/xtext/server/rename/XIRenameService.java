package org.eclipse.n4js.ide.xtext.server.rename;

import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * @author koehnlein - Initial contribution and API
 * @since 2.13
 * @deprecated use {IRenameService2} instead.
 */
@Deprecated
@SuppressWarnings("all")
public interface XIRenameService {
	@Deprecated
	WorkspaceEdit rename(XWorkspaceManager workspaceManager, RenameParams renameParams,
			CancelIndicator cancelIndicator);
}