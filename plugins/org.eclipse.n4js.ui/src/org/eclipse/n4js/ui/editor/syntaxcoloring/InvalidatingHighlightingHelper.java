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
package org.eclipse.n4js.ui.editor.syntaxcoloring;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.syntaxcoloring.HighlightingHelper;

import org.eclipse.n4js.ui.N4JSEditor;

/**
 * Fixup for Xtext problem https://bugs.eclipse.org/bugs/show_bug.cgi?id=464591
 */
public class InvalidatingHighlightingHelper extends HighlightingHelper {

	private XtextEditor myEditor;
	private XtextSourceViewer mySourceViewer;

	@Override
	public void install(XtextEditor editor, XtextSourceViewer sourceViewer) {
		this.myEditor = editor;
		this.mySourceViewer = sourceViewer;
		super.install(editor, sourceViewer);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		XtextEditor editor = myEditor;
		XtextSourceViewer sourceViewer = mySourceViewer;
		if (editor instanceof N4JSEditor && sourceViewer != null
				&& event.getProperty().contains(".syntaxColorer.tokenStyles")) {
			((N4JSEditor) editor).initializeViewerColors(sourceViewer);
			sourceViewer.invalidateTextPresentation();
		}
	}

}
