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
package org.eclipse.n4js.jsdoc2spec.ui;

import java.text.SimpleDateFormat;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Wizard page shows a text area to display messages during some computation which also uses the progress bar.
 */
public class SpecProcessPage extends SpecPage {
	private final Stack<Message> msgStack = new Stack<>();
	private StyledText errorText;
	private Color highlightColor;
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss\t ");

	private class Message {
		final String msg;
		final Color color;

		Message(String msg, Color color) {
			this.msg = msg;
			this.color = color;
		}
	}

	/**
	 * Constructor
	 */
	public SpecProcessPage(String name) {
		super(name);
		setMessage("Performing specified tasks...");
	}

	/**
	 * (non-Javadoc) Method declared on IDialogPage.
	 */
	@Override
	public void createControl(Composite parent) {
		setPageComplete(true);
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		new Label(composite, SWT.NONE).setText("Messages:");
		createErrorGroup(composite);

		Display display = getShell().getDisplay();
		highlightColor = display.getSystemColor(SWT.COLOR_RED);

		setControl(composite);
	}

	private void createErrorGroup(Composite parent) {
		Composite errorGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		errorGroup.setLayout(layout);
		errorGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		errorGroup.setFont(parent.getFont());
		errorText = new StyledText(errorGroup, SWT.READ_ONLY | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		errorText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	/**
	 * Display a message in color black.
	 */
	public String displayMessage(String msg) {
		if (msg == null || msg.isEmpty())
			return msg;

		return displayMessage(msg, null);
	}

	/**
	 * Display a message in color red.
	 */
	public String displayMessageRed(String msg) {
		if (msg == null || msg.isEmpty())
			return msg;

		displayMessage(msg, highlightColor);
		return msg;
	}

	private String displayMessage(String msg, Color color) {
		String formattedDate = dateFormatter.format(System.currentTimeMillis());
		String timeMsg = formattedDate + msg;
		Message m = null;
		synchronized (msgStack) {
			if (!msgStack.isEmpty()) { // merge messages for better performance
				Message lastM = msgStack.lastElement();
				if (lastM != null && lastM.color == color) {
					msgStack.remove(msgStack.size() - 1);
					m = new Message(lastM.msg + "\n" + timeMsg, color);
				}
			}
			if (m == null) {
				m = new Message(timeMsg, color);
			}
			msgStack.push(m);
		}

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				asyncDisplayMessages();
			}
		});
		return msg;
	}

	private void asyncDisplayMessages() {
		Message m = null;
		synchronized (msgStack) {
			if (msgStack.isEmpty())
				return;

			m = msgStack.pop();
		}
		int offsetStart = errorText.getCharCount();
		errorText.append(m.msg + "\n");
		int offsetEnd = errorText.getCharCount();
		errorText.setTopIndex(errorText.getLineCount() - 1);

		if (m.color != null) {
			StyleRange range = new StyleRange(offsetStart, offsetEnd - offsetStart, m.color, null);
			errorText.setStyleRange(range);
		}
	}
}
