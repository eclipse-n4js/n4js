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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;

/**
 * Collection of convenient SWT and JFace utility methods.
 */
public abstract class UIUtils {

	/** Default timeout when waiting for values obtained from the UI. */
	public static final long DEFAULT_UI_TIMEOUT = TimeUnit.SECONDS.toMillis(60);

	/**
	 * Like {@link Supplier}, but supports results that may not be available yet, by returning immediately with an
	 * {@link Optional#empty() empty optional}.
	 */
	public interface NonWaitingSupplier<T> {

		/**
		 * Gets a result without waiting for it. Iff the result is not available yet, an {@link Optional#empty() empty
		 * optional} is returned.
		 *
		 * @return a result
		 */
		Optional<T> get();
	}

	/**
	 * Like {@link #waitForValueFromUI(NonWaitingSupplier, Supplier, long)}, but using {@link #DEFAULT_UI_TIMEOUT} as
	 * timeout.
	 */
	public static <T> T waitForValueFromUI(NonWaitingSupplier<T> nonWaitingSupplier,
			Supplier<String> valueDescSupplier) {
		return waitForValueFromUI(nonWaitingSupplier, valueDescSupplier, DEFAULT_UI_TIMEOUT);
	}

	/**
	 * Utility method to obtain some value from the UI (e.g. TreeItem of a JFace viewer, string label of a button) and
	 * waiting for it if it isn't readily available at the time this method is invoked, up to the given timeout. If the
	 * value is still not available after timeout, this method will throw an exception.
	 *
	 * @param nonWaitingSupplier
	 *            a supplier trying to obtain the value without waiting for it.
	 * @param valueDescSupplier
	 *            a supplier providing a description of the value to be obtained. Used for messages in exceptions
	 *            thrown.
	 * @param timeout
	 *            timeout in ms. If lower or equal 0 the supplier will still be invoked once but no waiting will be
	 *            performed.
	 * @return the value obtained from the UI. Never returns <code>null</code>.
	 * @throws TimeoutRuntimeException
	 *             upon timeout.
	 * @throws InterruptedRuntimeException
	 *             when an interrupt is received while waiting for the value.
	 */
	public static <T> T waitForValueFromUI(NonWaitingSupplier<T> nonWaitingSupplier, Supplier<String> valueDescSupplier,
			long timeout) {
		final long start = System.currentTimeMillis();

		Optional<T> item;
		while (!(item = nonWaitingSupplier.get()).isPresent()
				&& System.currentTimeMillis() - start < timeout) {
			try {
				Thread.sleep(100); // wait for more UI events coming in
			} catch (InterruptedException e) {
				throw new InterruptedRuntimeException(
						"received interrupt while waiting for " + valueDescSupplier.get());
			}
			waitForUiThread(); // process all pending UI events
		}

		if (!item.isPresent()) {
			throw new TimeoutRuntimeException(
					"timed out after " + timeout + "ms while waiting for " + valueDescSupplier.get());
		}
		return item.get();
	}

	/**
	 * Obtain child item with given text from 'root', waiting for UI updates if it is not present yet (see
	 * {@link #waitForValueFromUI(NonWaitingSupplier, Supplier, long)}). If more than one text is given, the
	 * corresponding path through the tree will be followed.
	 */
	public static TreeItem waitForTreeItem(Tree root, String... texts) {
		return waitForTreeItemInternal(root, texts);
	}

	/**
	 * Same as {@link #waitForTreeItem(Tree, String...)}, but starts with a {@link TreeItem} instead of a {@link Tree}
	 * as root.
	 */
	public static TreeItem waitForTreeItem(TreeItem root, String... texts) {
		return waitForTreeItemInternal(root, texts);
	}

	// root must be a Tree or TreeItem
	private static TreeItem waitForTreeItemInternal(Object root, String... texts) {
		Objects.requireNonNull(root);
		Objects.requireNonNull(texts);
		TreeItem currChild = null;
		for (int i = 0; i < texts.length; i++) {
			Object currParent = currChild != null ? currChild : root;
			String currText = texts[i];
			currChild = waitForValueFromUI(
					() -> getChildItem(currParent, currText),
					() -> "child item \"" + currText + "\"");
		}
		return currChild;
	}

	// root must be a Tree or TreeItem
	private static Optional<TreeItem> getChildItem(Object parent, String text) {
		if (parent instanceof TreeItem && !((TreeItem) parent).getExpanded()) {
			((TreeItem) parent).setExpanded(true);
			waitForUiThread();
		}
		TreeItem[] currChildren = parent instanceof Tree
				? ((Tree) parent).getItems()
				: ((TreeItem) parent).getItems();
		return Stream.of(currChildren)
				.filter(child -> text.equals(child.getText()))
				.findFirst();
	}

	/**
	 * Processes UI input and does not return while there are things to do on the UI thread. I.e., when this method
	 * returns, there is no more work to do on the UI thread <em>at this time</em>. This method may be invoked from any
	 * thread.
	 * <p>
	 * Moved here from <code>AbstractPluginUITest#waitForUiThread()</code>.
	 */
	public static void waitForUiThread() {
		final Display display = getDisplay();
		display.syncExec(() -> {
			if (!display.isDisposed()) {
				while (display.readAndDispatch()) {
					// wait while there might be something to process.
				}
				display.update();
			}
		});
	}

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
				monitorBounds.y, Math.min(centerPoint.y - (initialSize.y * 2 / 3),
						monitorBounds.y + monitorBounds.height - initialSize.y)));
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

	/**
	 * Shows an error dialog that gives information on the given {@link Throwable}.
	 *
	 * This static method may be invoked at any point during startup as it does not rely on activators to be loaded.
	 */
	public static void showError(Throwable t) {
		int dialogW = 400;
		int dialogH = 300;

		Display display = new Display();
		Shell shell = new Shell(display);
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		shell.setLocation(bounds.width / 2 - dialogW / 2, bounds.height / 2 - dialogH / 2);
		shell.setText("Fatal Error with Dependency Injection.");
		shell.setSize(dialogW, dialogH);
		shell.setLayout(new FillLayout());

		Text text = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String sStackTrace = sw.toString();
		text.setText(sStackTrace);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/** Checks if it is called on the UI thread. */
	public static boolean runsInUIThread() {
		AtomicReference<Thread> refUIThread = new AtomicReference<>();
		UIUtils.getDisplay().syncExec(() -> {
			refUIThread.set(Thread.currentThread());
		});
		return Thread.currentThread().equals(refUIThread.get());
	}

}
