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
package org.eclipse.n4js.ui.utils;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.runtime.CoreException;

/**
 * Utilities for workspace auto-build setting
 */
public class AutobuildUtils {

	/** @return current setting of workspace auto-build */
	static public boolean get() {
		return getWorkspace().getDescription().isAutoBuilding();
	}

	/** Turns off auto-build */
	static public void turnOff() {
		set(false);
	}

	/** Turns on auto-build */
	static public void turnOn() {
		set(true);
	}

	/** Sets workspace auto-build according to the provided flag. Thrown exceptions are handled by logging. */
	static public void set(boolean enable) {
		if (isWorkbenchRunning()) {
			IWorkspace workspace = getWorkspace();
			IWorkspaceDescription workspaceDescription = workspace.getDescription();
			if (null != workspaceDescription) {
				if (workspaceDescription.isAutoBuilding() != enable) {
					workspaceDescription.setAutoBuilding(enable);
					try {
						workspace.setDescription(workspaceDescription);
					} catch (CoreException e) {
						throw new IllegalStateException("Error while trying to turn workspace autobuild "
								+ (enable ? "on" : "off") + ".", e);
					}
				}
			}
		}
	}

}
