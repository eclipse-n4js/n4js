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
package org.eclipse.n4js.ui.workingsets;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.osgi.service.prefs.Preferences;

/**
 * Representation of a memento aware concept. The internal state can be saved to and restored from {@link Preferences
 * OSGi based preferences}.
 */
public interface MementoAware {

	/**
	 * Saves the state of the current instance by updating the {@link #getPreferences() preferences}. It is the
	 * implementors responsibility to call the {@link Preferences#flush()} to actually make the state persistent.
	 *
	 * @param monitor
	 *            the monitor for the process.
	 * @return a status instance representing the whether the save operation was successful or not.
	 */
	IStatus saveState(final IProgressMonitor monitor);

	/**
	 * Restores the state of the current instance by reading the saved values from the {@link #getPreferences()
	 * preferences}.
	 *
	 * @param monitor
	 *            the monitor for the process.
	 * @return a status instance representing the whether the state restore operation was successful or not.
	 */
	IStatus restoreState(final IProgressMonitor monitor);

	/**
	 * Returns with the OSGi based preferences that can be used to save to and to restore from its internal state.
	 *
	 * @return the preferences for persistence purposes.
	 */
	Preferences getPreferences();

}
