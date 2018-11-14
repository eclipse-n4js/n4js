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
package org.eclipse.n4js.ui.binaries;

import static org.eclipse.jface.dialogs.IDialogConstants.OK_LABEL;
import static org.eclipse.swt.SWT.COLOR_WIDGET_BACKGROUND;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.MULTI;
import static org.eclipse.swt.SWT.READ_ONLY;
import static org.eclipse.swt.SWT.TOP;
import static org.eclipse.swt.SWT.UNDERLINE_LINK;
import static org.eclipse.swt.SWT.WRAP;
import static org.eclipse.ui.dialogs.PreferencesUtil.createPreferenceDialogOn;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Customized message dialog for notifying the user about an incorrect binary configuration.
 */
public class IllegalBinaryStateDialog extends MessageDialog {

	private static final int TEXT_HEIGHT_HINT = 50;
	private static final int TEXT_WIDTH_HINT = 450;
	private static final String[] BUTTON_LABELS = new String[] { OK_LABEL };
	private static final String[] FILTER_IDS = { BinariesPreferencePage.ID };

	private final IllegalBinaryStateException cause;

	/**
	 * Creates a new dialog instance with the given illegal binary state exception.
	 *
	 * @param cause
	 *            the exception about the illegal binary state.
	 */
	public IllegalBinaryStateDialog(final IllegalBinaryStateException cause) {
		this(cause, cause.getBinary().getLabel() + " Configuration Error", cause.getMessage());
	}

	/**
	 * Creates a new dialog instance with the given title and error message.
	 *
	 * @param cause
	 *            the causing exception.
	 * @param title
	 *            the dialog title.
	 * @param message
	 *            the dialog message.
	 */
	protected IllegalBinaryStateDialog(final IllegalBinaryStateException cause, final String title,
			final String message) {

		super(UIUtils.getShell(), title, null, message, ERROR, BUTTON_LABELS, 0);
		this.cause = cause;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		return createCustomAreaWithLink(parent, this, cause.getBinary());
	}

	/**
	 * Creates a control with some message and with link to the Binaries preference page.
	 *
	 * @param parent
	 *            the parent composite.
	 * @param dialog
	 *            the container dialog that has to be closed.
	 * @param binary
	 *            the binary with the illegal state.
	 *
	 * @return a control with error message and link that can be reused in dialogs.
	 */
	public static Control createCustomAreaWithLink(final Composite parent, final Dialog dialog, final Binary binary) {
		final String binaryLabel = binary.getLabel();
		final String prefix = "The requested operation cannot be performed due to invalid '" + binaryLabel
				+ "' settings. Check your '" + binaryLabel
				+ "' configuration and preferences under the corresponding ";
		final String link = "preference page";
		final String suffix = ".";
		final String text = prefix + link + suffix;

		final Composite control = new Composite(parent, NONE);
		control.setLayout(GridLayoutFactory.fillDefaults().create());
		final GridData gridData = GridDataFactory.fillDefaults().align(LEFT, TOP).grab(true, true).create();
		control.setLayoutData(gridData);

		final StyleRange style = new StyleRange();
		style.underline = true;
		style.underlineStyle = UNDERLINE_LINK;

		final StyledText styledText = new StyledText(control, MULTI | READ_ONLY | WRAP);
		styledText.setWordWrap(true);
		styledText.setJustify(true);
		styledText.setText(text);
		final GridData textGridData = GridDataFactory.fillDefaults().align(FILL, FILL).grab(true, true).create();
		textGridData.widthHint = TEXT_WIDTH_HINT;
		textGridData.heightHint = TEXT_HEIGHT_HINT;
		styledText.setLayoutData(textGridData);

		styledText.setEditable(false);
		styledText.setBackground(UIUtils.getSystemColor(COLOR_WIDGET_BACKGROUND));
		final int[] ranges = { text.indexOf(link), link.length() };
		final StyleRange[] styles = { style };
		styledText.setStyleRanges(ranges, styles);

		styledText.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(final MouseEvent event) {
				try {
					final int offset = styledText.getOffsetAtPoint(new Point(event.x, event.y));
					final StyleRange actualStyle = offset >= 0 ? styledText.getStyleRangeAtOffset(offset) : null;
					if (null != actualStyle && actualStyle.underline
							&& UNDERLINE_LINK == actualStyle.underlineStyle) {

						dialog.close();
						final PreferenceDialog preferenceDialog = createPreferenceDialogOn(
								UIUtils.getShell(),
								BinariesPreferencePage.ID,
								FILTER_IDS,
								null);

						if (null != preferenceDialog) {
							preferenceDialog.open();
						}

					}
				} catch (final IllegalArgumentException e) {
					// We are not over the actual text.
				}
			}

		});

		return control;
	}

}
