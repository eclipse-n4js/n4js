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
package org.eclipse.n4js.smith.graph.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.n4js.smith.graph.editoroverlay.EditorOverlay;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * SWT widget to draw a {@link ASTGraph}.
 */
@SuppressWarnings("javadoc")
public class GraphCanvas extends Canvas {
	final protected EditorOverlay editorOverlay;

	protected Graph<?> graph = null;
	protected boolean graphNeedsLayout = true;

	protected boolean showAllCrossLinks = false;

	protected final Set<Node> selectedNodes = new HashSet<>();

	protected int offsetX = 0;
	protected int offsetY = 0;
	protected float scale = 1.0f;

	/** Whether hawk-eye mode is active or not. */
	protected boolean hawkEye_active = false;
	/** If non-null, denotes the center of the region where we want to go when leaving hawk-eye mode. */
	protected Point hawkEye_target = null;
	/** Old scroll offset before entering hawk-eye mode. */
	protected int hawkEye_oldOffsetX = 0;
	/** Old scroll offset before entering hawk-eye mode. */
	protected int hawkEye_oldOffsetY = 0;
	/** Old scale before entering hawk-eye mode. */
	protected float hawkEye_oldScale = 1.0f;

	protected boolean mousePressed = false;
	protected boolean mouseDragged = false;
	protected int mouseDragStartX;
	protected int mouseDragStartY;
	protected int mouseDragBaseX;
	protected int mouseDragBaseY;

	protected Node hoveredNode = null;

	protected DefaultToolTip toolTip;

	public GraphCanvas(Composite parent, int style, EditorOverlay editorOverlay) {
		super(parent, style);
		this.editorOverlay = editorOverlay;

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent event) {
				doPaint(event.gc, event);
			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseDown(MouseEvent e) {
				onMouseDown(e);
			}

			@Override
			public void mouseUp(MouseEvent e) {
				onMouseUp(e);
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// do nothing
			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent e) {
				onMouseWheel(e);
			}
		});
		addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				onMouseMove(e);
			}
		});
		addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseEnter(MouseEvent e) {
				onMouseEnter(e);
			}

			@Override
			public void mouseExit(MouseEvent e) {
				onMouseExit(e);
			}

			@Override
			public void mouseHover(MouseEvent e) {
				// ignore
			}
		});
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (isCtrlAlt(e)) {
					setHawkEyeMode(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (isCtrlAlt(e)) {
					setHawkEyeMode(false);
				}
			}

			private final boolean isCtrlAlt(KeyEvent e) {
				return (e.keyCode == SWT.CTRL && 0 != (e.stateMask & SWT.ALT)) ||
						(e.keyCode == SWT.ALT && 0 != (e.stateMask & SWT.CTRL));
			}
		});

		toolTip = new DefaultToolTip(this, SWT.NONE, true);
		toolTip.setText("Hello tool tip!");
	}

	public Graph<?> getGraph() {
		return graph;
	}

	public void clear() {
		setGraph(null);
	}

	public void setGraph(Graph<?> graph) {
		if (graph != this.graph) {
			clearSelection();
			if (this.graph == null || graph == null) {
				offsetX = 0;
				offsetY = 0;
			}
			this.graph = graph;
			graphNeedsLayout = true;
			redraw();
		}
	}

	public Point screenToGraph(int screenX, int screenY) {
		return new Point((offsetX + screenX) / scale, (offsetY + screenY) / scale);
	}

	public void setScrollOffset(int offsetX, int offsetY) {
		if (offsetX != this.offsetX || offsetY != this.offsetY) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			redraw();
		}
	}

	/**
	 * Sets the scroll offset such that the graph's point at (graphX,graphY) will appear at screen position
	 * (screenX,screenY).
	 */
	public void setScrollOffset(float graphX, float graphY, int screenX, int screenY) {
		int offsX = Math.round(graphX * scale - screenX);
		int offsY = Math.round(graphY * scale - screenY);
		setScrollOffset(offsX, offsY);
	}

	public void zoom(int screenX, int screenY, float deltaScale) {
		if (deltaScale != 0.0f) {
			final Point pFix = screenToGraph(screenX, screenY); // this is the center of the zoom
			scale += deltaScale;
			setScrollOffset(pFix.x, pFix.y, screenX, screenY);
			redraw();
		}
	}

	public void setHawkEyeMode(boolean active) {
		if (active && !hawkEye_active && graph != null) {
			turnOnHawkeyeMode();
		} else if (!active && hawkEye_active) {
			turnOffHawkeyeMode();
		}
	}

	/**
	 * turn on hawk-eye mode
	 */
	private void turnOnHawkeyeMode() {
		final int border = 5;
		final float w = getSize().x;
		final float h = getSize().y;
		final Rectangle b = graph.getBounds();
		final int newOffsetX = border;
		final int newOffsetY = border;
		final float scaleA = (w - border * 2) / b.width;
		final float scaleB = (h - border * 2) / b.height;
		final float newScale = Math.min(scaleA, scaleB);
		if (newScale < 1.0f) {
			hawkEye_oldOffsetX = offsetX;
			hawkEye_oldOffsetY = offsetY;
			hawkEye_oldScale = scale;
			offsetX = newOffsetX;
			offsetY = newOffsetY;
			scale = newScale;
			hawkEye_target = null;
			hawkEye_active = true;
			redraw();
		}
	}

	/**
	 * turn off hawk-eye mode
	 */
	private void turnOffHawkeyeMode() {
		if (hawkEye_target != null) {
			// we do have a target -> go there
			// find the point in the graph we want to have at the center of the screen (after leaving hawk eye mode)
			final Point pCenter = screenToGraph(Math.round(hawkEye_target.x), Math.round(hawkEye_target.y));
			// switch back to old scale
			scale = hawkEye_oldScale;
			// set scroll offset such that pCenter is at the center of the screen
			setScrollOffset(pCenter.x, pCenter.y, getSize().x / 2, getSize().y / 2);
			// clear old target info
			hawkEye_target = null;
		} else {
			// we have no target, so simply go back to the old offset/scale
			offsetX = hawkEye_oldOffsetX;
			offsetY = hawkEye_oldOffsetY;
			scale = hawkEye_oldScale;
		}
		hawkEye_active = false;
		redraw();
	}

	protected void setHawkEyeTarget(Point screenPos) {
		if (hawkEye_active) {
			if (screenPos != null) {
				if (hawkEye_target == null || hawkEye_target.x != screenPos.x || hawkEye_target.y != screenPos.y) {
					hawkEye_target = screenPos;
					redraw();
				}
			} else {
				if (hawkEye_target != null) {
					hawkEye_target = null;
					redraw();
				}
			}
		} else {
			hawkEye_target = null; // not really required, just intended for clean-up
		}
	}

	public void setShowAllCrossLinks(boolean value) {
		if (value != showAllCrossLinks) {
			showAllCrossLinks = value;
			redraw();
		}
	}

	public Set<Node> getSelectedNodes() {
		return Collections.unmodifiableSet(selectedNodes);
	}

	public void setSelectedNodes(Node... nodes) {
		final Set<Node> newNodes = new HashSet<>();
		for (Node n : nodes) {
			if (n != null) {
				newNodes.add(n);
			}
		}
		if (!selectedNodes.equals(newNodes)) {
			selectedNodes.clear();
			selectedNodes.addAll(newNodes);
			redraw();
		}
	}

	public void toggleSelection(Node node) {
		if (selectedNodes.contains(node))
			selectedNodes.remove(node);
		else
			selectedNodes.add(node);

		redraw();
	}

	public void clearSelection() {
		if (!selectedNodes.isEmpty()) {
			selectedNodes.clear();
			redraw();
		}
	}

	public Node getNodeAt(int screenX, int screenY) {
		final Point p = screenToGraph(screenX, screenY);
		final Node result = graph == null ? null : graph.getNodeAt(p.x, p.y);
		return result;
	}

	protected void onMouseEnter(MouseEvent event) {
		if (hawkEye_active) {
			setHawkEyeTarget(new Point(event.x, event.y));
		}
	}

	protected void onMouseExit(@SuppressWarnings("unused") MouseEvent event) {
		if (hawkEye_active) {
			setHawkEyeTarget(null);
		}
	}

	protected void onMouseDown(MouseEvent event) {
		mousePressed = true;
		mouseDragged = false;
		mouseDragStartX = event.x;
		mouseDragStartY = event.y;
		mouseDragBaseX = offsetX;
		mouseDragBaseY = offsetY;
	}

	protected void onMouseMove(MouseEvent event) {
		if (hawkEye_active) {
			setHawkEyeTarget(new Point(event.x, event.y));
		}
		final Node mNode = getNodeAt(event.x, event.y);
		// update hovered node
		if (!mousePressed) {
			setHoveredNode(mNode);
			setEditorOverlay(mNode);
		}

		// dragging
		if (mousePressed) {
			mouseDragged |= Math.abs(event.x - mouseDragStartX) > 3;
			mouseDragged |= Math.abs(event.y - mouseDragStartY) > 3;

			if (mouseDragged) {
				onMouseDrag(event);
			}
		}
	}

	protected void onMouseDrag(MouseEvent event) {
		int scrollOffsetX = mouseDragBaseX - event.x + mouseDragStartX;
		int scrollOffsetY = mouseDragBaseY - event.y + mouseDragStartY;
		setScrollOffset(scrollOffsetX, scrollOffsetY);
		redraw();
	}

	protected void onMouseUp(MouseEvent event) {
		if (!mouseDragged) {
			onClick(event);
		}
		mousePressed = false;
		mouseDragged = false;
		mouseDragStartX = 0;
		mouseDragStartY = 0;
	}

	protected void onClick(MouseEvent event) {
		// update selection
		final Node mNode = getNodeAt(event.x, event.y);
		final boolean shiftPressed = (event.stateMask & SWT.SHIFT) != 0;
		if (!shiftPressed) {
			if (mNode == null) {
				clearSelection();
			} else {
				setSelectedNodes(mNode);
			}
		} else {
			if (mNode == null) {
				// do nothing
			} else {
				toggleSelection(mNode);
			}
		}
	}

	protected void onMouseWheel(MouseEvent event) {
		zoom(event.x, event.y, 0.01f * event.count);
	}

	protected void setHoveredNode(Node node) {
		if (node != hoveredNode) {
			hoveredNode = node;

			if (hoveredNode != null && hoveredNode.getDescription() != null) {
				toolTip.setText(hoveredNode.getDescription());
				org.eclipse.swt.graphics.Point point = new org.eclipse.swt.graphics.Point(4, 4);
				toolTip.show(point);
			} else {
				toolTip.hide();
			}

			redraw();
		}
	}

	protected void setEditorOverlay(Node node) {
		EObject selection = null;
		if (node != null && node.getElement() instanceof EObject) {
			selection = (EObject) node.getElement();
		}
		editorOverlay.setSelection(selection);
	}

	protected void doPaint(GC gc, PaintEvent event) {
		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		gc.fillRectangle(event.x, event.y, event.width, event.height);

		if (graph == null)
			return;

		final Transform tf = new Transform(gc.getDevice());
		tf.translate(-offsetX, -offsetY);
		tf.scale(scale, scale);
		gc.setTransform(tf);

		if (graphNeedsLayout) {
			graph.layout(gc);
			graphNeedsLayout = false;
		}

		for (Edge e : graph.getEdges()) {
			Stream<Node> eNodes = e.getNodes().stream();
			boolean paintIt = false;
			paintIt |= !e.isCrossLink();
			paintIt |= e.isCrossLink() && (showAllCrossLinks
					|| eNodes.anyMatch(n -> n == hoveredNode || selectedNodes.contains(n)));

			if (paintIt) {
				e.paint(gc);
			}
		}
		for (Node n : graph.getNodes()) {
			n.paint(gc);
		}

		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		for (Node sn : selectedNodes) {
			float x = sn.getX() - 2;
			float y = sn.getY() - 2;
			float width = sn.getWidth() + 4;
			float height = sn.getHeight() + 4;
			GraphUtils.drawRectangle(gc, x, y, width, height);
		}

		gc.setTransform(null);
		tf.dispose();

		if (hawkEye_active && hawkEye_target != null) {
			final int w = Math.round(getSize().x / hawkEye_oldScale * scale);
			final int h = Math.round(getSize().y / hawkEye_oldScale * scale);
			final int x = (int) hawkEye_target.x - w / 2;
			final int y = (int) hawkEye_target.y - h / 2;
			gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
			gc.setAlpha(64);
			gc.fillRectangle(x, y, w, h);
			gc.setAlpha(255);
		}
	}
}
