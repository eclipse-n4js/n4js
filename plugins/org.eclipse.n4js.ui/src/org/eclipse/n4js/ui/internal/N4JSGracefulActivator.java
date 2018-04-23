/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.internal;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 *
 */
public class N4JSGracefulActivator extends N4JSActivator {

	@Override
	protected Injector createInjector(String language) {
		try {
			Module runtimeModule = getRuntimeModule(language);
			Module sharedStateModule = getSharedStateModule();
			Module uiModule = getUiModule(language);
			Module mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
			return Guice.createInjector(mergedModule);
		} catch (Exception e) {
			// An exception occurring here might be related to Guice:
			// https://stackoverflow.com/questions/39918622/why-is-guice-throwing-computationexception-from-uncaughtexceptionhandler-in-mai.
			e.printStackTrace();
			showError(e);
			System.exit(-1);
			return null;
		}
	}

	private void showError(Exception e) {
		int dialogW = 400;
		int dialogH = 300;

		Display display = new Display();
		Shell shell = new Shell(display);
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		shell.setLocation(bounds.width / 2 - dialogW / 2, bounds.height / 2 - dialogH / 2);
		shell.setText("Fatal Error with Dependency Injection.");
		shell.setSize(dialogW, dialogH);
		shell.setLayout(new FillLayout());

		Text text = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString();
		text.setText(sStackTrace);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
