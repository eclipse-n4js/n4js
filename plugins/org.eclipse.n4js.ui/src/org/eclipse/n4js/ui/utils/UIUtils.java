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
package org.eclipse.n4js.ui.utils;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;

/**
 * Collection of convenient SWT and JFace utility methods.
 */
public abstract class UIUtils {

	/**
	 * Disposes the resource argument. Has no effect if the resource argument is either {@code null} or already
	 * {@link Resource#isDisposed() disposed}.
	 *
	 * @param resource
	 *            the resource to dispose. Optional, can be {@code null}.
	 */
	public static void dispose(final Resource resource) {
		if (null != resource && !resource.isDisposed()) {
			resource.dispose();
		}
	}

	/**
	 * Disposes the widget argument. Has no effect if the widget argument is either {@code null} or already
	 * {@link Widget#isDisposed() disposed}.
	 *
	 * @param widget
	 *            the widget to dispose. Optional, can be {@code null}.
	 */
	public static void dispose(final Widget widget) {
		if (null != widget && !widget.isDisposed()) {
			widget.dispose();
		}
	}

	/**
	 * Returns with the display. If the current display is not available, returns with the default one.
	 *
	 * @return the display between SWT and the OS.
	 */
	public static Display getDisplay() {
		return null == Display.getCurrent()
				? Display.getDefault()
				: Display.getCurrent();
	}

	/**
	 * Returns with the active {@link Shell shell} from the {@link #getDisplay() display}. May return with {@code null}
	 * if called from non-UI thread.
	 *
	 * @return the active shell, or {@code null} if invoked from non-UI thread.
	 */
	public static Shell getShell() {
		final Display display = getDisplay();
		return null == display ? null : display.getActiveShell();
	}

	/**
	 * Returns the matching standard color for the given constant, which should be one of the color constants specified
	 * in class SWT. Any value other than one of the SWT color constants which is passed in will result in the color
	 * black. This color must not be disposed/released because it was allocated by the system, not the application.
	 *
	 * @param swtColorConstantId
	 *            the SWT color constant.
	 * @return the system color for the given color constant ID or black.
	 */
	public static Color getSystemColor(final int swtColorConstantId) {
		return getDisplay().getSystemColor(swtColorConstantId);
	}

	/**
	 * Given the desired position of the shell, this method returns an adjusted position such that the window is no
	 * larger than its monitor, and does not extend beyond the edge of the monitor. This is used for computing the
	 * initial window position via the parent shell, clients can use this as a utility method if they want to limit the
	 * region in which the window may be moved.
	 *
	 * @param shell
	 *            the shell which shell bounds is being calculated.
	 * @param preferredSize
	 *            the preferred position of the window.
	 * @return a rectangle as close as possible to preferredSize that does not extend outside the monitor.
	 */
	public static Rectangle getConstrainedShellBounds(final Shell shell, final Point preferredSize) {

		final Point location = getInitialLocation(shell, preferredSize);
		final Rectangle result = new Rectangle(location.x, location.y, preferredSize.x, preferredSize.y);

		final Monitor monitor = getClosestMonitor(shell.getDisplay(), Geometry.centerPoint(result));

		final Rectangle bounds = monitor.getClientArea();

		if (result.height > bounds.height) {
			result.height = bounds.height;
		}

		if (result.width > bounds.width) {
			result.width = bounds.width;
		}

		result.x = Math.max(bounds.x, Math.min(result.x, bounds.x
				+ bounds.width - result.width));
		result.y = Math.max(bounds.y, Math.min(result.y, bounds.y
				+ bounds.height - result.height));

		return result;
	}

	/**
	 * Returns the initial location to use for the shell. The default implementation centers the shell horizontally (1/2
	 * of the difference to the left and 1/2 to the right) and vertically (1/3 above and 2/3 below) relative to the
	 * active workbench windows shell, or display bounds if there is no parent shell.
	 *
	 * @param shell
	 *            the shell which initial location has to be calculated.
	 * @param initialSize
	 *            the initial size of the shell, as returned by <code>getInitialSize</code>.
	 * @return the initial location of the shell
	 */
	public static Point getInitialLocation(final Shell shell, final Point initialSize) {
		final Composite parent = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		Monitor monitor = shell.getDisplay().getPrimaryMonitor();
		if (parent != null) {
			monitor = parent.getMonitor();
		}

		final Rectangle monitorBounds = monitor.getClientArea();
		Point centerPoint;
		if (parent != null) {
			centerPoint = Geometry.centerPoint(parent.getBounds());
		} else {
			centerPoint = Geometry.centerPoint(monitorBounds);
		}

		return new Point(centerPoint.x - (initialSize.x / 2), Math.max(
				monitorBounds.y, Math.min(centerPoint.y
						- (initialSize.y * 2 / 3), monitorBounds.y
								+ monitorBounds.height - initialSize.y)));
	}

	/**
	 * Returns the monitor whose client area contains the given point. If no monitor contains the point, returns the
	 * monitor that is closest to the point.
	 *
	 * @param toSearch
	 *            point to find (display coordinates).
	 * @param toFind
	 *            point to find (display coordinates).
	 * @return the monitor closest to the given point.
	 */
	private static Monitor getClosestMonitor(final Display toSearch, final Point toFind) {
		int closest = Integer.MAX_VALUE;

		final Monitor[] monitors = toSearch.getMonitors();
		Monitor result = monitors[0];

		for (int index = 0; index < monitors.length; index++) {
			final Monitor current = monitors[index];

			final Rectangle clientArea = current.getClientArea();

			if (clientArea.contains(toFind)) {
				return current;
			}

			final int distance = Geometry.distanceSquared(Geometry.centerPoint(clientArea), toFind);
			if (distance < closest) {
				closest = distance;
				result = current;
			}
		}

		return result;
	}

	private UIUtils() {
		//
	}

}
