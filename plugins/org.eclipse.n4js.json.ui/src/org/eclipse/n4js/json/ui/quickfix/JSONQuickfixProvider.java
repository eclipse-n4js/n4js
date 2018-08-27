/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.ui.quickfix;

import org.eclipse.n4js.json.ui.extension.JSONUiExtensionRegistry;
import org.eclipse.n4js.utils.ui.quickfix.DelegatingQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;

import com.google.inject.Inject;

/**
 * Custom quickfixes.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#quick-fixes
 */
public class JSONQuickfixProvider extends DelegatingQuickfixProvider {

	@Inject
	private JSONUiExtensionRegistry registry;

	@Override
	protected Iterable<? extends IssueResolutionProvider> getDelegates() {
		return registry.getQuickfixProviderExtensions();
	}

	// @Fix(JSONValidator.INVALID_NAME)
	// public void capitalizeName(final Issue issue, IssueResolutionAcceptor
	// acceptor) {
	// acceptor.accept(issue, "Capitalize name", "Capitalize the name.",
	// "upcase.png", new IModification() {
	// public void apply(IModificationContext context) throws BadLocationException {
	// IXtextDocument xtextDocument = context.getXtextDocument();
	// String firstLetter = xtextDocument.get(issue.getOffset(), 1);
	// xtextDocument.replace(issue.getOffset(), 1, firstLetter.toUpperCase());
	// }
	// });
	// }

}
