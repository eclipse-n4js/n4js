package org.eclipse.n4js.ui.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleListener;
import org.eclipse.ui.console.IConsoleManager;

/**
 * Creates a new N4JS stack trace console.
 */
public class N4JSStackTraceConsoleFactory implements IConsoleFactory {
	private IConsoleManager fConsoleManager = null;
	private N4JSStackTraceConsole console = null;

	/**
	 * Creates the console.
	 */
	public N4JSStackTraceConsoleFactory() {
		fConsoleManager = ConsolePlugin.getDefault().getConsoleManager();
		fConsoleManager.addConsoleListener(new IConsoleListener() {
			@Override
			public void consolesAdded(IConsole[] consoles) {
				// nothing to do here
			}

			@Override
			public void consolesRemoved(IConsole[] consoles) {
				for (int i = 0; i < consoles.length; i++) {
					if (consoles[i] == console) {
						console.saveDocument();
						console = null;
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
		if (console == null) {
			console = new N4JSStackTraceConsole();
			console.initializeDocument();
			fConsoleManager.addConsoles(new IConsole[] { console });
		}
		fConsoleManager.showConsoleView(console);
	}

}
