/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.typesearch;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.n4js.ui.N4JSEditor;
import org.eclipse.n4js.ui.utils.HandlerServiceUtils;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

/**
 * Handler for opening the N4JS type selection dialog.
 */
public class OpenTypeSelectionDialogHandler extends AbstractHandler {

	private static final AtomicBoolean TYPE_SEARCH_IN_USE = new AtomicBoolean();

	private static final Logger LOGGER = Logger.getLogger(OpenTypeSelectionDialogHandler.class);

	@Inject
	private Provider<OpenTypeSelectionDialog> provider;

	@Inject
	private N4JSLanguageHelper languageHelper;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (TYPE_SEARCH_IN_USE.compareAndSet(false, true)) {
			try {
				OpenTypeSelectionDialog typeSelectionDialog = provider.get();
				typeSelectionDialog.setInitialPattern(computeInitialPattern());
				typeSelectionDialog.open();
			} finally {
				TYPE_SEARCH_IN_USE.set(false);
			}
		}

		return null;
	}

	/**
	 * Computes and returns the initial pattern for the type search dialog.
	 *
	 * If no initial pattern can be computed, an empty string is returned.
	 */
	private String computeInitialPattern() {
		IEditorPart activeEditor = HandlerServiceUtils.getActiveEditor().orNull();

		if (activeEditor instanceof N4JSEditor) {
			Point range = ((N4JSEditor) activeEditor).getSourceViewer2().getSelectedRange();
			try {
				String text = ((N4JSEditor) activeEditor).getDocument().get(range.x, range.y);

				if (N4JSLanguageUtils.isValidIdentifier(text)
						&& !startWithLowercaseLetter(text)
						&& !languageHelper.isReservedIdentifier(text)) {
					return text;
				}
			} catch (BadLocationException e) {
				LOGGER.error("Failed to infer type search pattern from editor selection.", e);
			}

		}
		return "";
	}

	private boolean startWithLowercaseLetter(String string) {
		return !string.isEmpty() && string.charAt(0) == string.toLowerCase().charAt(0);
	}
}
