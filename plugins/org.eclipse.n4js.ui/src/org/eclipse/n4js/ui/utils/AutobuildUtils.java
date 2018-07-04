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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.runtime.CoreException;

/**
 * Utilities for workspace auto-build setting
 */
public class AutobuildUtils {

	private static final Logger LOG = LogManager.getLogger(AutobuildUtils.class);

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
						LOG.info("Turning auto-build " + (enable ? "on" : "off") + "...");
						workspace.setDescription(workspaceDescription);
						LOG.info("Auto-build was successfully turned " + (enable ? "on" : "off") + ".");
					} catch (CoreException e) {
						throw new IllegalStateException("Error while trying to turn workspace autobuild "
								+ (enable ? "on" : "off") + ".", e);
					}
				}
			}
		} else {
			LOG.info("Workbench is not running, cannot change autobuild settings.");
		}
	}

	/** Remembers the original auto-build setting and sets it back when closed. */
	static public class ClosableAutobuild implements AutoCloseable {
		private final boolean originalSetting;

		ClosableAutobuild(boolean originalSetting) {
			this.originalSetting = originalSetting;
		}

		@Override
		public void close() {
			set(originalSetting);
		}
	}

	/** Turns off the auto-build and returns an auto-closable handle that reverts the original setting */
	static public ClosableAutobuild suppressAutobuild() {
		ClosableAutobuild closableAutobuild = new ClosableAutobuild(get());
		turnOff();
		return closableAutobuild;
	}

}
