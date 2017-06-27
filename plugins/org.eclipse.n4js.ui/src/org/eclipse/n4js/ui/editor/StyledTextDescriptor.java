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
package org.eclipse.n4js.ui.editor;

import static org.eclipse.jface.preference.PreferenceConverter.getColor;
import static org.eclipse.jface.preference.PreferenceConverter.getDefaultColor;
import static org.eclipse.swt.SWT.COLOR_LIST_BACKGROUND;
import static org.eclipse.swt.SWT.READ_ONLY;
import static org.eclipse.swt.widgets.Display.getDefault;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND;
import static org.eclipse.ui.texteditor.AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT;

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.EditorsUI;

/**
 * Representation of a descriptor for a {@link StyledText styled text}.
 */
public interface StyledTextDescriptor {

	/**
	 * Returns with the string represented by the current text descriptor.
	 *
	 * @return the text.
	 */
	String getText();

	/**
	 * Returns with the {@link StyleRange style range} instances for the text descriptor if any. If no style ranges are
	 * defined for the text, then this method returns with {@code null}.
	 *
	 * @return the style ranges for the text.
	 */
	StyleRange[] getRanges();

	/**
	 * Returns with the font for the text descriptor.
	 * <p>
	 * Clients must NOT dispose the the returning font.
	 *
	 * @return the font for the descriptor.
	 */
	Font getFont();

	/**
	 * Creates and returns with a new {@link StyledText styled text} instance hooked up to the given parent composite.
	 *
	 * @param parent
	 *            the parent of the styled text control.
	 * @param style
	 *            style bits for the new text control.
	 * @return a new styled text control initialized from the descriptor.
	 */
	default StyledText toStyledText(final Composite parent, final int style) {

		final StyledText text = new StyledText(parent, READ_ONLY | style);
		text.setText(getText());
		text.setStyleRanges(getRanges());
		text.setFont(getFont());
		text.setEditable(false);
		text.setEnabled(false);

		final AtomicReference<Color> colorRef = new AtomicReference<>();
		final IPreferenceStore prefStore = EditorsUI.getPreferenceStore();
		if (null == prefStore
				|| prefStore.getBoolean(PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT)) {

			colorRef.set(getDefault().getSystemColor(COLOR_LIST_BACKGROUND));

		} else {

			RGB rgb = null;
			if (prefStore.contains(PREFERENCE_COLOR_BACKGROUND)) {
				if (prefStore.isDefault(PREFERENCE_COLOR_BACKGROUND)) {
					rgb = getDefaultColor(prefStore, PREFERENCE_COLOR_BACKGROUND);
				} else {
					rgb = getColor(prefStore, PREFERENCE_COLOR_BACKGROUND);
				}
				if (rgb != null) {
					colorRef.set(new Color(text.getDisplay(), rgb));
				}
			}

		}

		if (null != colorRef.get()) {
			text.setBackground(colorRef.get());
			text.addDisposeListener(e -> {
				if (!colorRef.get().isDisposed()) {
					colorRef.get().dispose();
				}
			});
		}

		text.pack();
		return text;
	}

}
