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
package org.eclipse.n4js.ui.preferences.external;

import static org.eclipse.jface.layout.GridDataFactory.fillDefaults;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.PUSH;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Utility for creating JFace buttons. Essentially collection of specialized factory methods to reduce clutter in the ui
 * code.
 */
public class ButtonFactoryUtil {

	/**
	 * Creates button with {@link org.eclipse.swt.SWT#PUSH} style. Provided parameters control other aspects of the
	 * button.
	 *
	 * @param parent
	 *            the parent used to create the button.
	 * @param text
	 *            the text used to set text of the button.
	 * @param listener
	 *            the listener added to the button.
	 * @param enabled
	 *            flag controls if created button is enabled.
	 * @return created button.
	 */
	public static Button createPushButton(final Composite parent, final String text, final String tooltip,
			final SelectionListener listener, boolean enabled) {

		final Button button = new Button(parent, PUSH);
		button.setLayoutData(fillDefaults().align(FILL, CENTER).create());
		button.setText(text);
		button.setToolTipText(tooltip);
		if (null != listener) {
			button.addSelectionListener(listener);
			button.addDisposeListener(e -> {
				button.removeSelectionListener(listener);
			});
		}
		button.setEnabled(enabled);
		return button;
	}

	/**
	 * Delegates to {@link #createPushButton(Composite, String, String, SelectionListener, boolean)} with
	 * {@code enabled} set to {@code false}.
	 */
	public static Button createDisabledPushButton(final Composite parent, final String text, final String tooltip,
			final SelectionListener listener) {
		return createPushButton(parent, text, tooltip, listener, false);
	}

	/**
	 * Delegates to {@link #createPushButton(Composite, String, String, SelectionListener, boolean)} with
	 * {@code enabled} set to {@code true}.
	 */
	public static Button createEnabledPushButton(final Composite parent, final String text, final String tooltip,
			final SelectionListener listener) {
		return createPushButton(parent, text, tooltip, listener, true);
	}

}
