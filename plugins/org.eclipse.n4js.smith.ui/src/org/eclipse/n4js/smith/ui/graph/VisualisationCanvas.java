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
package org.eclipse.n4js.smith.ui.graph;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.window.DefaultToolTip;
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
 * SWT widget to draw a {@link Graph}.
 */
@SuppressWarnings("javadoc")
public class VisualisationCanvas extends Canvas {

	protected VisualisationGraph graph = null;
	protected boolean graphNeedsLayout = true;

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

	protected VisualisationNode hoveredNode = null;
	protected final Set<VisualisationNode> selectedNodes = new HashSet<>();

	protected DefaultToolTip toolTip;

	public VisualisationCanvas(Composite parent, int style) {
		super(parent, style);
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

	public VisualisationGraph getGraph() {
		return graph;
	}

	public void clear() {
		setGraph(null);
	}

	public void setGraph(VisualisationGraph vis) {
		if (vis != this.graph) {
			clearSelection();
			if (this.graph == null || vis == null) {
				offsetX = 0;
				offsetY = 0;
			}
			this.graph = vis;
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
		setScrollOffset(
				Math.round(graphX * scale - screenX),
				Math.round(graphY * scale - screenY));
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
			// turn on hawk-eye mode
			final int border = 5;
			final float w = getSize().x;
			final float h = getSize().y;
			final Rectangle b = graph.getBounds();
			final int newOffsetX = border;
			final int newOffsetY = border;
			final float newScale = Math.min((w - border * 2) / b.width, (h - border * 2) / b.height);
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
		} else if (!active && hawkEye_active) {
			// turn off hawk-eye mode
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

	public void setSelectedNodes(VisualisationNode... nodes) {
		final Set<VisualisationNode> newNodes = new HashSet<>();
		for (VisualisationNode n : nodes)
			if (n != null)
				newNodes.add(n);
		if (!selectedNodes.equals(newNodes)) {
			selectedNodes.clear();
			selectedNodes.addAll(newNodes);
			redraw();
		}
	}

	public void toggleSelection(VisualisationNode node) {
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

	public VisualisationNode getNodeAt(int screenX, int screenY) {
		final Point p = screenToGraph(screenX, screenY);
		return graph != null ? graph.getNodeAt(p.x, p.y) : null;
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
		final VisualisationNode mNode = getNodeAt(event.x, event.y);
		// update hovered node
		if (!mousePressed)
			setHoveredNode(mNode);
		// dragging
		if (mousePressed) {
			mouseDragged |= Math.abs(event.x - mouseDragStartX) > 3 || Math.abs(event.y - mouseDragStartY) > 3;

			if (mouseDragged)
				onMouseDrag(event);
		}
	}

	protected void onMouseDrag(MouseEvent event) {
		setScrollOffset(
				mouseDragBaseX - event.x + mouseDragStartX,
				mouseDragBaseY - event.y + mouseDragStartY);
		redraw();
	}

	protected void onMouseUp(MouseEvent event) {
		if (!mouseDragged)
			onClick(event);
		mousePressed = false;
		mouseDragged = false;
		mouseDragStartX = 0;
		mouseDragStartY = 0;
	}

	protected void onClick(MouseEvent event) {
		// update selection
		final VisualisationNode mNode = getNodeAt(event.x, event.y);
		// final Node mNode = getNodeAt(event.x, event.y);
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

	protected void setHoveredNode(VisualisationNode node) {
		if (node != hoveredNode) {
			hoveredNode = node;

			if (hoveredNode != null && hoveredNode.getDescription() != null) {
				toolTip.setText(hoveredNode.getDescription());
				toolTip.show(new org.eclipse.swt.graphics.Point(4, 4));
			} else {
				toolTip.hide();
			}

			redraw();
		}
	}

	protected void doPaint(GC gc, PaintEvent event) {
		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));

		gc.fillRectangle(event.x, event.y, event.width, event.height);

		if (graph != null) {
			final Transform tf = new Transform(gc.getDevice());
			tf.translate(-offsetX, -offsetY);
			tf.scale(scale, scale);
			gc.setTransform(tf);

			if (graphNeedsLayout) {
				graph.layout();
				graphNeedsLayout = false;
			}

			graph.getNodes().forEach(n -> n.paint(gc));

			gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));

			gc.setTransform(null);
			tf.dispose();

			if (hawkEye_active && hawkEye_target != null) {
				final int w = Math.round(getSize().x / hawkEye_oldScale * scale);
				final int h = Math.round(getSize().y / hawkEye_oldScale * scale);
				gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
				gc.setAlpha(64);
				gc.fillRectangle(
						(int) hawkEye_target.x - w / 2,
						(int) hawkEye_target.y - h / 2,
						w, h);
				gc.setAlpha(255);
			}
		}
	}
}
