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
package org.eclipse.n4js.ui;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;

/**
 */
public class N4JSEditor extends XtextEditor {

	private static final Logger LOG = Logger.getLogger(N4JSEditor.class);

	/**
	 * Returns the {@link ISourceViewer}, which will most likely be an {@link XtextSourceViewer}. Same as
	 * {@link AbstractTextEditor#getSourceViewer() getSourceViewer()} in super class, but provided here to increase
	 * visibility.
	 */
	public final ISourceViewer getSourceViewer2() {
		return getSourceViewer();
	}

	/**
	 * Make publicly available.
	 */
	@Override
	public void initializeViewerColors(ISourceViewer viewer) {
		super.initializeViewerColors(viewer);
	}

	@Override
	protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
		// this event is not supposed to occur here anymore due to some internal changes in Xtext
		// Unfortunately the colors won't be invalidated due to that in some rare occasions.
		// That was fixed by introducing the InvalidatingHighlightingHelper
		boolean tokenStyleChanged = event.getProperty().contains(".syntaxColorer.tokenStyles");
		if (tokenStyleChanged) {
			LOG.error("Unexpected event", new Exception());
			return;
		}
		super.handlePreferenceStoreChanged(event);
	}

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

}
