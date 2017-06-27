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
package org.eclipse.n4js.runner.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchDelegate;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.n4js.runner.RunConfiguration;

/**
 * Static utility methods for runners on UI level.
 */
public class RunnerUiUtils {

	/**
	 * Read the N4JS runner ID from the given Eclipse launch configuration. Will throw exceptions if 'failFast' is
	 * <code>true</code>, otherwise will return <code>null</code> in case of error.
	 */
	public static String getRunnerId(ILaunchConfiguration launchConfig, boolean failFast) {
		try {
			// 1) simple case: runnerId already defined in launchConfig
			final String id = launchConfig.getAttribute(RunConfiguration.RUNNER_ID, (String) null);
			if (id != null)
				return id;
			// 2) tricky case: not set yet, so have to go via the ILaunchConfigurationType or the launchConfig
			final ILaunchConfigurationType launchConfigType = launchConfig.getType();
			return getRunnerId(launchConfigType, failFast);
		} catch (CoreException e) {
			if (failFast)
				throw new WrappedException(e);
			return null;
		}
	}

	/**
	 * Read the N4JS runner ID from the given Eclipse launch configuration type. Will throw exceptions if 'failFast' is
	 * <code>true</code>, otherwise will return <code>null</code> in case of error.
	 */
	public static String getRunnerId(ILaunchConfigurationType launchConfigType, boolean failFast) {
		final Set<Set<String>> modeCombis = launchConfigType.getSupportedModeCombinations();
		final Set<String> runnerIds = new HashSet<>();
		for (Set<String> modeCombi : modeCombis) {
			final ILaunchDelegate[] delegates;
			try {
				delegates = launchConfigType.getDelegates(modeCombi);
			} catch (CoreException e) {
				continue;
			}
			for (ILaunchDelegate launchDelegate : delegates) {
				final ILaunchConfigurationDelegate launchConfigDelegate;
				try {
					launchConfigDelegate = launchDelegate.getDelegate();
				} catch (CoreException e) {
					throw new WrappedException(
							"error while getting launch configuration delegate from launch delegate", e);
				}
				if (launchConfigDelegate instanceof IDERunnerDelegate) {
					final String runnerId = ((IDERunnerDelegate) launchConfigDelegate).getRunnerId();
					if (runnerId != null)
						runnerIds.add(runnerId);
				}
			}
		}
		if (runnerIds.size() == 1)
			return runnerIds.iterator().next();
		if (failFast) {
			if (runnerIds.isEmpty()) {
				throw new IllegalStateException(
						"cannot find an N4JS runner ID for the launch configuration type with ID: "
								+ launchConfigType.getIdentifier());
			}
			else { // runnerIds.size() > 1
				throw new IllegalStateException(
						"several N4JS runner IDs used for a single ILaunchConfigurationType: "
								+ launchConfigType.getIdentifier());
			}
		}
		return null;
	}
}
