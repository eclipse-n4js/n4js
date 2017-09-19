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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.util.ITextRegion;

/**
 * Draws an overlay over the editor view. The overlay draws a frame around a specific {@link EObject}, i.e. a source
 * code element. In case the editor contents changes (by user key inputs), no overlay is drawn since the position data
 * is invalidated.
 */
public class EditorOverlay implements PaintListener {
	final private ILocationInFileProvider locFileProvider;
	final private XtextEditor editor;
	final private StyledText styledText;

	private EObject currentSelection;

	/**
	 * Constructor
	 */
	public EditorOverlay() {
		locFileProvider = new DefaultLocationInFileProvider();
		editor = EditorUtils.getActiveXtextEditor();
		styledText = editor.getInternalSourceViewer().getTextWidget();
	}

	/** Sets the highlighted element in the editor view */
	public void setSelection(EObject currentSelection) {
		this.currentSelection = currentSelection;
		draw();
	}

	private void draw() {
		if (currentSelection != null) {
			drawSelection();
		} else {
			clear();
		}
	}

	private void clear() {
		if (!styledText.isDisposed()) {
			styledText.removePaintListener(this);
			styledText.redraw();
		}
	}

	private void drawSelection() {
		if (!styledText.isDisposed()) {
			styledText.addPaintListener(this);
			styledText.redraw();
		}
	}

	/** Computes an array of points that create a frame around the selected element. */
	private int[] getConturePointArray() {
		ITextRegion tr = locFileProvider.getFullTextRegion(currentSelection);
		int trOffset = tr.getOffset();
		if (trOffset == 0) {
			return null;
		}
		int lineHeight = styledText.getLineHeight(trOffset);

		// Calculate end points for each line
		List<Point> points = new LinkedList<>();
		Point sPoint = styledText.getLocationAtOffset(trOffset);
		Point lPoint = sPoint;
		int minX = sPoint.x;
		for (int i = 1; i <= tr.getLength() && trOffset + i < styledText.getCharCount(); i++) {
			Point p = styledText.getLocationAtOffset(trOffset + i);
			minX = Math.min(minX, p.x);
			if (p.y != lPoint.y) {
				points.add(lPoint);
				Point lPointNL = new Point(lPoint.x, lPoint.y + lineHeight);
				points.add(lPointNL);
			}
			lPoint = p;
		}
		// Add end points for the last line
		points.add(lPoint);
		Point lPointNL = new Point(lPoint.x, lPoint.y + lineHeight);
		points.add(lPointNL);

		// Prepend the start point
		sPoint.x = minX;
		points.add(0, sPoint);

		// Append the end point
		Point ePoint = new Point(minX, lPoint.y + lineHeight);
		points.add(ePoint);

		// convert to array
		int[] pointArray = new int[2 * points.size()];
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			pointArray[i * 2] = p.x;
			pointArray[i * 2 + 1] = p.y;
		}

		return pointArray;
	}

	/////////////////////// PaintListener ///////////////////////

	@Override
	public void paintControl(PaintEvent e) {
		if (currentSelection == null)
			return;

		int[] pointArray = getConturePointArray();
		if (pointArray != null) {
			Color green = new Color(e.display, 20, 200, 20);
			e.gc.setForeground(green);
			e.gc.setBackground(green);
			e.gc.drawPolygon(pointArray);
		}
	}
}
