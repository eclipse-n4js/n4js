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
package org.eclipse.n4js.ui.dialog;

import static java.lang.Math.max;
import static org.eclipse.swt.SWT.NONE;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is responsible for providing type information in a popup dialog.
 */
public class TypeInformationPopup extends PopupDialog {

	private static final Point DEFAULT_SIZE = new Point(100, 240);

	private final String html;
	private final Point anchor;

	/**
	 * Creates a new popup dialog instance to show some type information in it.<br>
	 * Note: command ID is intentionally not associated with the current instances and passed into the supertypes. Since
	 * this popup dialog does not intended to save any kind of information about itself, command ID can be ignored.
	 *
	 * @param parent
	 *            the parent shell of the receiver popup dialog.
	 * @param anchor
	 *            the position to initialize the popup dialog.
	 * @param html
	 *            the HTML representation of any type related information to reveal in the dialog area.
	 */
	public TypeInformationPopup(final Shell parent, final Point anchor, final String html) {
		super(parent, INFOPOPUPRESIZE_SHELLSTYLE, true, true, false, true, false, null, null);
		this.anchor = anchor;
		this.html = html;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Object layoutData = parent.getLayoutData();
		if (layoutData instanceof GridData) {
			((GridData) layoutData).heightHint = DEFAULT_SIZE.x;
			((GridData) layoutData).widthHint = DEFAULT_SIZE.y;
		}
		final Browser browser = new Browser(parent, NONE);
		browser.setText(html);
		return browser;
	}

	/**
	 * Returns the initial location to use for the shell based upon the current selection in the viewer. Bottom is
	 * preferred to top, and right is preferred to left, therefore if possible the popup will be located below and to
	 * the right of the selection.
	 *
	 * @param initialSize
	 *            the initial size of the shell, as returned by {@code getInitialSize}.
	 * @return the initial location of the shell
	 */
	@Override
	protected Point getInitialLocation(final Point initialSize) {
		if (null == anchor) {
			return super.getInitialLocation(initialSize);
		}

		final Point point = anchor;
		final Rectangle monitor = getShell().getMonitor().getClientArea();
		if (monitor.width < point.x + initialSize.x) {
			point.x = max(0, point.x - initialSize.x);
		}
		if (monitor.height < point.y + initialSize.y) {
			point.y = max(0, point.y - initialSize.y);
		}

		return point;
	}

}
