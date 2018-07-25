package org.eclipse.n4js.ui.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleListener;
import org.eclipse.ui.console.IConsoleManager;

public class N4JSStackTraceConsoleFactory implements IConsoleFactory {
	private IConsoleManager fConsoleManager = null;
	private N4JSStackTraceConsole fConsole = null;

	public N4JSStackTraceConsoleFactory() {
		fConsoleManager = ConsolePlugin.getDefault().getConsoleManager();
		fConsoleManager.addConsoleListener(new IConsoleListener() {
			@Override
			public void consolesAdded(IConsole[] consoles) {
			}

			@Override
			public void consolesRemoved(IConsole[] consoles) {
				for (int i = 0; i < consoles.length; i++) {
					if (consoles[i] == fConsole) {
						fConsole.saveDocument();
						fConsole = null;
					}
				}
			}

		});
	}

	/**
	 * Opens the console (creating a new one if not previously initialized).
	 */
	@Override
	public void openConsole() {
		openConsole(null);
	}

	/**
	 * Opens the console (creating a new one if not previously initialized). If the passed string is not
	 * <code>null</code>, the text of the console is set to the string.
	 *
	 * @param initialText
	 *            text to put in the console or <code>null</code>.
	 */
	public void openConsole(String initialText) {
		if (fConsole == null) {
			fConsole = new N4JSStackTraceConsole();
			fConsole.initializeDocument();
			fConsoleManager.addConsoles(new IConsole[] { fConsole });
		}
		if (initialText != null) {
			fConsole.getDocument().set(initialText);
		}
		fConsoleManager.showConsoleView(fConsole);
	}

}
