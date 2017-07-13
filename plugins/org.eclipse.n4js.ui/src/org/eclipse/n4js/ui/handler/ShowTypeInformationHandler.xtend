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
package org.eclipse.n4js.ui.handler

import com.google.inject.Inject
import org.eclipse.n4js.ui.dialog.TypeInformationPopup
import org.eclipse.n4js.ui.labeling.N4JSTypeInformationHoverProvider
import org.eclipse.n4js.ui.selection.AstSelectionProvider2
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.jface.text.ITextSelection
import org.eclipse.jface.text.ITextViewer
import org.eclipse.jface.text.Region
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.widgets.Control
import org.eclipse.xtext.ui.editor.hover.html.XtextBrowserInformationControlInput
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.util.TextRegion

import static org.eclipse.ui.handlers.HandlerUtil.*
import static org.eclipse.xtext.ui.editor.utils.EditorUtils.*

/**
 * Handler for revealing the type information of a particular variable or an expression based on the current selection of
 * the active N4JS editor.
 */
class ShowTypeInformationHandler extends AbstractHandler {

	@Inject
	private extension N4JSTypeInformationHoverProvider;

	@Inject
	private extension AstSelectionProvider2;

	override execute(ExecutionEvent event) throws ExecutionException {
		val editor = getActiveXtextEditor(event);
		if (null !== editor) {
			val selection = editor.selectionProvider.selection as ITextSelection;
			val node = editor.document.getNodeElementFromSelection(selection);
			if (null !== node) {
				val region = new Region(selection.offset, selection.length);
				val info = node.getHoverInfo(editor.internalSourceViewer, region).info as XtextBrowserInformationControlInput;
				if (null !== info) {
					raiseTypeInformationPopup(event, info.html);
				}
			}
		}
		return null;
	}

	private def getNodeElementFromSelection(IXtextDocument document, ITextSelection selection) {
		return document.getSelectedAstElement(new TextRegion(selection.offset, selection.length));
	}

	private def raiseTypeInformationPopup(ExecutionEvent event, String html) {
		new TypeInformationPopup(
			getActiveShell(event),
			getPopupAnchor(event),
			html
		).open;
	}

	/* Calculates the anchor for the popup dialog. It will be right bottom of the selection. */
	private def getPopupAnchor(ExecutionEvent event) {
		val textWidget = getStyledText(event);
		if (null === textWidget) {
			return null
		}

		val docRange = textWidget.selectionRange;
		val midOffset = docRange.x + (docRange.y / 2);
		var point = textWidget.getLocationAtOffset(midOffset);
		point = textWidget.toDisplay(point);

		val gc = new GC(textWidget);
		gc.setFont(textWidget.font);
		val height = gc.fontMetrics.height;
		gc.dispose();
		point.y += height;
		return point;
	}

	/* Adapts the currently active workbench part to extract the StyledText from it. */
	private def getStyledText(ExecutionEvent event) {
		val part = getActiveWorkbenchWindow(event).activePage.activePart
		val viewer = part.getAdapter(ITextViewer)
		if (viewer === null) {
			val control = part.getAdapter(Control)
			if (control instanceof StyledText) {
				return control;
			}
		} else {
			return viewer.textWidget
		}
	}

}
