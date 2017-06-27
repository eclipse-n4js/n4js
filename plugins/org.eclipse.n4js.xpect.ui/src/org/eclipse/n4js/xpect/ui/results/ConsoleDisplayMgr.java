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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;

/**
 * Manages instances of console used to display information during test runs.
 */
public class ConsoleDisplayMgr {
	private static Map<String, XpectConsole> map = new HashMap<>();

	/**
	 * Returns console associated with given name. If console with given name doesn't exists will create new one.
	 */
	public static XpectConsole getOrCreate(String name) {
		XpectConsole console = map.get(name);
		if (console != null) {
			return console;
		} else {
			MessageConsole lkpMessageConsole = findMessageConsole(name);
			if (lkpMessageConsole == null) {
				console = new XpectConsole(createMessageConsoleStream(name));
			} else {
				console = new XpectConsole(lkpMessageConsole);
			}
			map.put(name, console);
		}

		return console;
	}

	private static MessageConsole findMessageConsole(String title) {
		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (IConsole iConsole : consoles) {
			if (iConsole.getName().equals(title)) {
				return (MessageConsole) iConsole;
			}
		}
		return null;
	}

	private static MessageConsole createMessageConsoleStream(String title) {
		MessageConsole messageConsole = new MessageConsole(title, null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { messageConsole });
		return messageConsole;
	}

}
