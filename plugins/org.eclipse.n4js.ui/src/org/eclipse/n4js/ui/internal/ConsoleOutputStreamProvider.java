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
package org.eclipse.n4js.ui.internal;

import static com.google.common.base.Suppliers.memoize;
import static org.eclipse.n4js.ui.utils.UIUtils.getDisplay;
import static org.eclipse.swt.SWT.COLOR_LIST_FOREGROUND;
import static org.eclipse.swt.SWT.COLOR_RED;

import java.io.OutputStream;

import org.eclipse.n4js.utils.io.OutputRedirection;
import org.eclipse.n4js.utils.io.OutputStreamProvider;
import org.eclipse.n4js.utils.io.OutputStreamPrinterThread.OutputStreamType;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.google.common.base.Supplier;
import com.google.inject.Singleton;

/**
 * Output stream provider to the npm console.
 */
@Singleton
public class ConsoleOutputStreamProvider implements OutputStreamProvider {

	private static final String CONSOLE_VIEW_ID = "org.eclipse.ui.console.ConsoleView";
	private static final Default DEFAULT = new OutputStreamProvider.Default();

	private final Supplier<MessageConsole> consoleSupplier = memoize(() -> new MessageConsole("npm Console", null));

	@Override
	public OutputStream getOutputStream(final OutputStreamType type, OutputRedirection redirect) {
		if (!PlatformUI.isWorkbenchRunning()) {
			return DEFAULT.getOutputStream(type, redirect);
		}
		final MessageConsole console = consoleSupplier.get();
		boolean silent = redirect == OutputRedirection.SUPPRESS;
		if (!silent) {
			console.activate();
		}
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
		final MessageConsoleStream stream = console.newMessageStream();
		getDisplay().asyncExec(() -> {
			stream.setColor(toColor(type));
			showConsoleView(silent);
		});
		return stream;
	}

	private void showConsoleView(final boolean silent) {
		if (PlatformUI.isWorkbenchRunning() && !silent) {
			final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			final IViewPart view = window.getActivePage().findView(CONSOLE_VIEW_ID);
			if (null != view) {
				view.setFocus();
			}
		}
	}

	private Color toColor(OutputStreamType type) {
		switch (type) {
		case STD_OUT:
			return getDisplay().getSystemColor(COLOR_LIST_FOREGROUND);
		case STD_ERR:
			return getDisplay().getSystemColor(COLOR_RED);
		default:
			throw new IllegalArgumentException("Unexpected output stream type.");
		}
	}

}
