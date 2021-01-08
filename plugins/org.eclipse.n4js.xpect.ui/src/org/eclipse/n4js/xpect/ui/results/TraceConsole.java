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

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Custom console for displaying test exceptions stack trace.
 */
public class TraceConsole {
	private final MessageConsole messageConsole;

	TraceConsole(MessageConsole nonNullMessageConsole) {
		messageConsole = Optional.of(nonNullMessageConsole).get();// nullcheck
	}

	/** */
	public void write(String msg) {
		if (msg == null) {
			return;
		}
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try (MessageConsoleStream mcs = messageConsole.newMessageStream()) {
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
}
