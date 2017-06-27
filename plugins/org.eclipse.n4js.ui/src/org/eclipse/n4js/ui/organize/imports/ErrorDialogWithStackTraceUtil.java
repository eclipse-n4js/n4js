/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.organize.imports;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

/**
 * Utility to show (in case of errors) dialog with stack trace.
 */
public class ErrorDialogWithStackTraceUtil {

	/**
	 * Shows JFace ErrorDialog but improved by constructing full stack trace in detail area.
	 *
	 * @return true if OK was pressed
	 */
	public static boolean showErrorDialogWithStackTrace(String msg, Throwable throwable) {

		// Temporary holder of child statuses
		List<Status> childStatuses = new ArrayList<>();

		for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
			childStatuses.add(new Status(IStatus.ERROR, "N4js-plugin-id", stackTraceElement.toString()));
		}

		MultiStatus ms = new MultiStatus("N4js-plugin-id", IStatus.ERROR,
				childStatuses.toArray(new Status[] {}), // convert to array of statuses
				throwable.getLocalizedMessage(), throwable);

		final AtomicBoolean result = new AtomicBoolean(true);
		Display.getDefault()
				.syncExec(
						() -> result.set(
								ErrorDialog.openError(null, "Error occurred while organizing ", msg, ms) == Window.OK));

		return result.get();
	}

}
