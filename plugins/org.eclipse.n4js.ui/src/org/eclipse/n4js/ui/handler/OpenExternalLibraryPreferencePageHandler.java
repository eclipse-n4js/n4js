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
package org.eclipse.n4js.ui.handler;

import static org.eclipse.ui.dialogs.PreferencesUtil.createPreferenceDialogOn;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import org.eclipse.n4js.ui.preferences.external.ExternalLibraryPreferencePage;

/**
 * Handler for opening the External Libraries preference page.
 */
public class OpenExternalLibraryPreferencePageHandler extends AbstractHandler {

	private static final String[] FILTER_IDS = { ExternalLibraryPreferencePage.ID };

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		PreferenceDialog dialog = createPreferenceDialogOn(shell, ExternalLibraryPreferencePage.ID, FILTER_IDS, null);
		if (null != dialog) {
			dialog.open();
		}
		return null;
	}

}
