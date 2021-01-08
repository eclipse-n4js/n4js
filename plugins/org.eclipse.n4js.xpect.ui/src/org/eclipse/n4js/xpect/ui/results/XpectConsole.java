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
package org.eclipse.n4js.xpect.ui.results;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Custom console for displaying test related data. Used mainly for displaying generated bug reports.
 */
public class XpectConsole {
	private final MessageConsole messageConsole;
	private static final int MSG_LOG = 0;
	private static final int MSG_INFORMATION = 1;
	private static final int MSG_WARNING = 2;
	private static final int MSG_SUCCESS = 3;
	private static final int MSG_ERROR = 4;

	XpectConsole(MessageConsole nonNullMessageConsole) {
		messageConsole = Optional.of(nonNullMessageConsole).get();// nullcheck
	}

	/** Conveninece method. Writes given message to the console. */
	public void log(String message) {
		write(message, MSG_LOG);
	}

	/** Conveninece method. Writes given message to the console. */
	public void info(String message) {
		write(message, MSG_INFORMATION);
	}

	/** Conveninece method. Writes given message to the console. */
	public void warn(String message) {
		write(message, MSG_WARNING);
	}

	/** Conveninece method. Writes given message to the console. */
	public void success(String message) {
		write(message, MSG_WARNING);
	}

	/** Conveninece method. Writes given message to the console. */
	public void error(String message) {
		write(message, MSG_ERROR);
	}

	private void write(String msg, int msgKind) {
		if (msg == null) {
			return;
		}
		messageConsole.activate();
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try (MessageConsoleStream mcs = getNewMessageConsoleStream(msgKind)) {
					mcs.println(msg);
				} catch (IOException e) {
					// ignore
				}
			}
		});
	}

	/**
	 * clears console view
	 */
	public void clear() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				messageConsole.clearConsole();
			}
		});
	}

	private MessageConsoleStream getNewMessageConsoleStream(int msgKind) {
		int swtColorId = SWT.COLOR_BLACK;

		switch (msgKind) {
		case MSG_LOG:
			swtColorId = SWT.COLOR_BLACK;
			break;
		case MSG_INFORMATION:
			swtColorId = SWT.COLOR_DARK_GRAY;
			break;
		case MSG_ERROR:
			swtColorId = SWT.COLOR_DARK_MAGENTA;
			break;
		case MSG_WARNING:
			swtColorId = SWT.COLOR_DARK_YELLOW;
			break;
		case MSG_SUCCESS:
			swtColorId = SWT.COLOR_DARK_GREEN;
			break;
		default:
			swtColorId = SWT.COLOR_BLACK;
			break;
		}

		MessageConsoleStream msgConsoleStream = messageConsole.newMessageStream();
		msgConsoleStream.setColor(Display.getCurrent().getSystemColor(swtColorId));

		return msgConsoleStream;
	}

}
