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
package org.eclipse.n4js.ui.wizard.workspace;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;

import com.google.common.primitives.Ints;

/**
 * Custom {@link org.eclipse.swt.widgets.Text} control to optionally display a grey suffix at the end of the user input.
 */
public class SuffixText extends Composite {

	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	// data binding properties
	/** Text property name */
	public static final String TEXT_PROPERTY = "text";
	/** Complete text property name */
	public static final String SUFFIX_PROPERTY = "suffix";
	/** Suffix visibility property name */
	public static final String SUFFIX_VISIBILITY_PROPERTY = "suffixVisible";

	// color constants
	private static Color INACTIVE_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND);

	// values
	private String suffix = "";
	private String text = "";

	// controls
	private final Text editableText;
	private final StyledText suffixText;

	// internal states
	private boolean mousePressed = false;
	private boolean suffixVisible = true;

	// Graphics context for text selection pixel calculation
	private final GC gc = new GC(getDisplay());

	private ControlDecoration contentProposalDecoration;

	/**
	 * Create the suffix text.
	 *
	 * @param parent
	 *            Parent composite
	 * @param style
	 *            additional style configuration
	 */
	public SuffixText(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new SuffixLayout());

		suffixText = createSuffixText();
		editableText = new Text(this, SWT.NONE);

		configureListeners();

		this.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		this.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_IBEAM));
	}

	/**
	 * Creates, configures and returns the suffix text control.
	 */
	private StyledText createSuffixText() {
		StyledText styledText = new StyledText(this, SWT.TRANSPARENT);
		styledText.setText("");
		styledText.setForeground(INACTIVE_COLOR);
		styledText.setBackground(getDisplay().getSystemColor(SWT.COLOR_TRANSPARENT));
		styledText.setEditable(false);
		styledText.setEnabled(false);
		styledText.setLeftMargin(0);

		return styledText;
	}

	/**
	 * Configures the listeners of the suffix text control.
	 */
	private void configureListeners() {
		// Redirect focus to internal editable text widget
		MouseListener focusCatcher = new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				mousePressed = false;
			}

			@Override
			public void mouseDown(MouseEvent e) {
				editableText.forceFocus();
				editableText.setSelection(editableText.getText().length());
				mousePressed = true;
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				editableText.setSelection(0, editableText.getText().length());
			}
		};

		this.addMouseListener(focusCatcher);
		suffixText.addMouseListener(focusCatcher);
		this.addMouseListener(focusCatcher);

		// Workaround theme dependent background color issues:
		// Reset the background color for every paint event
		addPaintListener(paintEvent -> setBackground(editableText.getBackground()));

		// Copy over the text from the internal editable text whenever it changes
		editableText.addModifyListener(modifyEvent -> {
			layout();
			setText(editableText.getText());
		});

		// Relayout when the suffix text changes
		this.addPropertyChangeListener(propertyChange -> {
			if (propertyChange.getPropertyName() == SUFFIX_PROPERTY) {
				layout(true);
			}
		});

		// Connect the internal suffix visibility state to the SWT label visibility
		this.addPropertyChangeListener(propertyChange -> {
			if (propertyChange.getPropertyName() == SUFFIX_VISIBILITY_PROPERTY) {
				suffixText.setVisible(suffixVisible);
			}
		});

		// Bind content proposal decoration to editable text focus state
		this.editableText.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setDecorationVisibility(false);
			}

			@Override
			public void focusGained(FocusEvent e) {
				setDecorationVisibility(true);
			}
		});

		// Make the graphic context use the font settings of the input
		gc.setFont(editableText.getFont());

		// Tracks dragging mouse movement in this widget and applies it to the text field userInput to
		// fake proper text selection behavior
		MouseMoveListener mouseMoveSelectionListener = mouseMoveEvent -> {
			if (mousePressed) {
				int userInputRightEdgeOffset = editableText.getBounds().x + editableText.getBounds().width;
				if (mouseMoveEvent.x < userInputRightEdgeOffset) {
					String inputString = editableText.getText();

					int selectedPixels = (userInputRightEdgeOffset - mouseMoveEvent.x);
					int i = 1;

					// Compute the index of the character the cursor is floating above and
					// adapt the text selection.
					while (inputString.length() - i >= 0
							&& gc.textExtent(editableText.getText().substring(inputString.length() - i,
									inputString.length() - 1)).x < selectedPixels) {
						i++;
					}

					int startIndex = Ints.max(0, inputString.length() - i + 1);

					editableText.setSelection(startIndex, inputString.length());
				}
			}

		};
		this.addMouseMoveListener(mouseMoveSelectionListener);
	}

	@Override
	public void dispose() {
		super.dispose();
		gc.dispose();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * @return The auto suffix string
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * Sets the auto suffix string
	 *
	 * @param suffix
	 *            The string
	 */
	public void setSuffix(String suffix) {
		this.firePropertyChange(SUFFIX_PROPERTY, this.suffix, this.suffix = suffix);
		this.suffixText.setText(this.suffix);
	}

	/**
	 * @return The fully merged value. User input + completed suffix.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * @param text
	 *            New text for the input
	 */
	public void setText(String text) {
		this.firePropertyChange(TEXT_PROPERTY, this.text, this.text = text);
		if (!this.editableText.getText().equals(text)) {
			this.editableText.setText(text);
		}
	}

	/**
	 * Sets the visibility of the suffix label
	 *
	 * @param state
	 *            The new state to adapt
	 */
	public void setSuffixVisible(boolean state) {
		this.firePropertyChange(SUFFIX_VISIBILITY_PROPERTY, this.suffixVisible, this.suffixVisible = state);
	}

	/**
	 * Returns the visibility state of the suffix label
	 */
	public boolean isSuffixVisible() {
		return suffixVisible;
	}

	@Override
	public boolean setFocus() {
		return this.editableText.setFocus();
	}

	/**
	 * Returns the internally used SWT text.
	 */
	public Text getInternalText() {
		return this.editableText;
	}

	/**
	 * Creates a decoration with the given image for this text.
	 *
	 * Note that the decoration is only displayed in focus.
	 */
	public void createDecoration(Image decorationImage) {
		contentProposalDecoration = new ControlDecoration(this, SWT.TOP | SWT.LEFT);
		contentProposalDecoration.setImage(decorationImage);
		contentProposalDecoration.hide();
	}

	/**
	 * Sets the decoration visibility.
	 *
	 * This method does not have any effect if the decoration wasn't created before. See
	 * {@link #createDecoration(Image)}
	 */
	private void setDecorationVisibility(boolean state) {
		if (null != contentProposalDecoration) {
			if (state) {
				contentProposalDecoration.show();
			} else {
				contentProposalDecoration.hide();
			}
		}
	}

	/**
	 * Add a property listener
	 *
	 * @param listener
	 *            listener to be called on every change of any property
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a property listener
	 *
	 * @param listener
	 *            remove listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Fire a property change event
	 *
	 * @param propertyName
	 *            bean name of the property
	 * @param newValue
	 *            new value of the property
	 * @param oldValue
	 *            old value of the property
	 */
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		this.changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * Custom layout to position the suffix label directly after the text and avoid clipping.
	 */
	private class SuffixLayout extends Layout {

		// Amount of additional pixels for label dimensions to avoid clipping
		private static final int AVOID_CLIPPING_PADDING = 16;

		private final int verticalSpacing = Platform.getOS().equals(Platform.OS_WIN32) ? 4 : 2;

		@Override
		protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
			int width = 0;
			int height = 0;
			Point textDimension = textDimensions();
			Control[] children = composite.getChildren();
			for (Control child : children) {
				if (child instanceof Text) {
					final String textContent = ((Text) child).getText();
					textDimension = gc.textExtent(textContent);
					width += textDimension.x;
					height += textDimension.y;
				}
				if (child instanceof Label) {
					Point computedSize = child.computeSize(0, 0);
					width += computedSize.x;
				}
			}
			return new Point(width, height + 4);
		}

		private Point textDimensions() {
			Point tDimensions = gc.textExtent(editableText.getText());
			return tDimensions;
		}

		private Point labelDimensions() {
			Point labelDimensions = gc.textExtent(suffix);
			labelDimensions.x += AVOID_CLIPPING_PADDING;
			return labelDimensions;
		}

		/**
		 * Return the top margin to vertically center an element of given height in an area with given total height.
		 */
		private int marginTopCenter(int height, int totalHeight) {
			return Double.valueOf(Math.floor((totalHeight - height) / 2.0)).intValue();
		}

		@Override
		protected void layout(Composite composite, boolean flushCache) {
			Rectangle clientArea = composite.getClientArea();
			Control[] children = composite.getChildren();

			Point labelDimension = labelDimensions();
			Point textDimension = textDimensions();

			for (Control child : children) {

				if (child instanceof Text) {
					int verticalCenterY = marginTopCenter(textDimension.y, clientArea.height);
					child.setBounds(0, verticalCenterY, clientArea.width, textDimension.y);
				}
				if (child instanceof StyledText) {
					int verticalCenterY = marginTopCenter(textDimension.y, clientArea.height);
					child.setBounds(textDimension.x + verticalSpacing, verticalCenterY,
							labelDimension.x, labelDimension.y);
				}

			}
		}
	}

}
