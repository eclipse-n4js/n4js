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
package org.eclipse.n4js.tester.ui.resultsview;

import static org.eclipse.swt.SWT.COLOR_WHITE;
import static org.eclipse.swt.widgets.Display.getCurrent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import org.eclipse.n4js.tester.domain.TestStatus;

/**
 * Shows a progress bar with information on number of passed, failed, etc. tests. The expected total must be set with
 * method {@link #setExpectedTotal(int)}; if the expected total is not available, then this widget won't will just show
 * the aggregated status.
 */
public class TestProgressBar extends Canvas {

	private TestStatusCounter counter;
	private int expectedTotal = 0;

	private int preferredHeight = 18;

	private final Color colorSkipped;
	private final Color colorPassed;
	private final Color colorFailed;
	private final Color colorError;
	private final Color colorFixme;

	/**
	 * Create instance.
	 */
	public TestProgressBar(Composite parent, int style) {
		super(parent, style);

		// damn you, SWT color management
		// Color sample is form: http://www.colorpicker.com/c6f2b1
		colorSkipped = new Color(Display.getCurrent(), 230, 232, 235);
		colorPassed = new Color(Display.getCurrent(), 198, 242, 177);
		colorFailed = new Color(Display.getCurrent(), 242, 188, 177);
		colorError = new Color(Display.getCurrent(), 242, 188, 177);
		colorFixme = new Color(Display.getCurrent(), 177, 231, 242);

		addPaintListener((ev) -> {
			onPaint(ev.gc);
		});
		addDisposeListener((ev) -> {
			onDispose();
		});
	}

	/**
	 * Sets the preferred height.
	 */
	protected void setPreferredHeight(int preferredHeight) {
		this.preferredHeight = preferredHeight;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		if (hHint == SWT.DEFAULT) {
			return super.computeSize(wHint, preferredHeight, changed);
		}
		return super.computeSize(wHint, hHint, changed);
	}

	/**
	 * Sets the expected total. Without this being set, not actual progress "bar" will be shown, but the aggregated
	 * status will be visible.
	 */
	public void setExpectedTotal(int expectedTotal) {
		this.expectedTotal = expectedTotal;
		redraw();
	}

	/**
	 * Sets the counter.
	 */
	public void setCounter(TestStatusCounter counter) {
		this.counter = counter;
		redraw();
	}

	/**
	 * Returns the color this widget will use for the given status. This allows {@link TestResultsView} to reuse these
	 * colors.
	 */
	/* package */final Color getColorForStatus(TestStatus status) {
		if (status != null) {
			switch (status) {
			case SKIPPED_IGNORE: //$FALL-THROUGH$
			case SKIPPED_PRECONDITION: //$FALL-THROUGH$
			case SKIPPED_NOT_IMPLEMENTED: //$FALL-THROUGH$
			case SKIPPED:
				return colorSkipped;
			case PASSED:
				return colorPassed;
			case SKIPPED_FIXME:
				return colorFixme;
			case FAILED:
				return colorFailed;
			case ERROR:
				return colorError;
			default:
				break;
			}
		}
		return getCurrent().getSystemColor(COLOR_WHITE);
	}

	/**
	 * Paint.
	 */
	protected void onPaint(GC gc) {
		final Rectangle b = getBounds();

		final TestStatus status = counter != null ? counter.getAggregatedStatus() : null;
		if (status != null) {

			final int total = Math.max(expectedTotal, counter.getTotal()); // this is our 100% value
			final int value = counter.getTotal(); // current value
			final int totalPx = b.width;
			final int valuePx = Math.round(totalPx * (((float) value) / ((float) total)));

			gc.setBackground(getColorForStatus(status));
			gc.fillRectangle(0, 0, valuePx, b.height);

			gc.setBackground(getBackground());
			gc.fillRectangle(0 + valuePx, 0, b.width - valuePx, b.height);
		} else {
			// clear
			gc.setBackground(getBackground());
			gc.fillRectangle(b);
		}

		if (counter != null) {
			final FontMetrics fm = gc.getFontMetrics();
			gc.setForeground(getForeground());
			final int pending = expectedTotal > 0 ? expectedTotal - counter.getTotal() : -1;
			gc.drawString(counter.toString(true, pending, SWT.RIGHT),
					4, b.height / 2 - fm.getHeight() / 2 - fm.getDescent(), true);
		}
	}

	/**
	 * Dispose.
	 */
	protected void onDispose() {
		dispose(colorSkipped, colorPassed, colorFailed, colorError, colorFixme);
		super.dispose();
	}

	private void dispose(final Resource... rs) {
		for (final Resource r : rs) {
			if (null != r && !r.isDisposed()) {
				r.dispose();
			}
		}
	}
}
