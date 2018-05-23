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
package org.eclipse.n4js.n4mf.ui;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextReadonlyEditorInput;

/**
 * Customized N4JS manifest editor implementation.
 */
public class N4MFEditor extends XtextEditor implements IShowInSource, IShowInTargetList {

	@Override
	protected void editorContextMenuAboutToShow(final IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);

		final IContributionItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof IMenuManager) {
				final IMenuManager subMenu = (IMenuManager) items[i];
				final IContributionItem testShowIn = subMenu.find(ContributionItemFactory.VIEWS_SHOW_IN.getId());
				if (null != testShowIn) {
					menu.remove(subMenu);
				}
			}
		}
	}

	/**
	 * Provides input so that the Project Explorer can locate the editor's input in its tree.
	 */
	@Override
	public ShowInContext getShowInContext() {
		IEditorInput editorInput = getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			return new ShowInContext(fei.getFile(), null);
		} else if (editorInput instanceof XtextReadonlyEditorInput) {
			XtextReadonlyEditorInput readOnlyEditorInput = (XtextReadonlyEditorInput) editorInput;
			IStorage storage;
			try {
				storage = readOnlyEditorInput.getStorage();
				return new ShowInContext(storage.getFullPath(), null);
			} catch (CoreException e) {
				// Do nothing
			}
		}
		return new ShowInContext(null, null);
	}

	/**
	 * List Project Explorer as target in Navigator -> Show In.
	 */
	@Override
	public String[] getShowInTargetIds() {
		return new String[] { IPageLayout.ID_PROJECT_EXPLORER };
	}
}
