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
package org.eclipse.n4js.smith.graph.editoroverlay;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.util.ITextRegion;

/**
 *
 */
public class EditorOverlay {

	ILocationInFileProvider locFileProvider = new DefaultLocationInFileProvider();

	EObject currentSelection;

	/**
	 * Sets the highlighted element in the editor view
	 */
	public void setSelection(EObject currentSelection) {
		this.currentSelection = currentSelection;
		draw();
	}

	private void draw() {
		if (currentSelection == null) {
			clear();
		} else {
			drawSelection();
		}
	}

	private void clear() {
		XtextEditor editor = EditorUtils.getActiveXtextEditor();
		StyledText styledText = editor.getInternalSourceViewer().getTextWidget();
		styledText.setBackgroundImage(null);
	}

	private void drawSelection() {
		ITextRegion tr = locFileProvider.getSignificantTextRegion(currentSelection);
		XtextEditor editor = EditorUtils.getActiveXtextEditor();
		Point size = editor.getShell().getSize();

		StyledText styledText = editor.getInternalSourceViewer().getTextWidget();
		org.eclipse.swt.graphics.Point p1 = styledText.getLocationAtOffset(tr.getOffset());
		org.eclipse.swt.graphics.Point p2 = styledText.getLocationAtOffset(tr.getOffset() + tr.getLength());

		int lineHeight = styledText.getLineHeight(tr.getOffset()) - 1;

		PaletteData palette = new PaletteData(0xFF, 0xFF00, 0xFF0000);
		ImageData imageData = new ImageData(size.x, size.y, 24, palette);
		int pixel = imageData.getPixel(1, 1);
		imageData.transparentPixel = pixel;

		Display display = styledText.getDisplay();
		Image image = new Image(display, imageData);
		// Image image = ImageDescriptor.createFromImageData(imageData).createImage();
		GC gc = new GC(image);
		imageData.alpha = 150;

		org.eclipse.swt.graphics.Color color = new org.eclipse.swt.graphics.Color(display, 20, 200, 20);
		gc.setBackground(color);
		gc.setForeground(color);
		gc.drawRectangle(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y + lineHeight);
		gc.dispose();
		color.dispose();

		styledText.setBackgroundImage(image);
	}
}
