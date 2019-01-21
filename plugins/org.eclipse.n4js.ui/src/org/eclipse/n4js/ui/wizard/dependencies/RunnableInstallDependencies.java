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
package org.eclipse.n4js.ui.wizard.dependencies;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.utils.AutobuildUtils;
import org.eclipse.n4js.utils.StatusHelper;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * Runnable collector of the workspace projects setting files. Note single instance single use, not thread safe.
 */
public class RunnableInstallDependencies implements IRunnableWithProgress {
	private MultiStatus multistatus;
	private InstallOptions options;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private ExternalLibrariesActionsHelper librariesActionsHelper;

	/** needs to be called before {@link #run(IProgressMonitor)} */
	public void setInstallOptions(InstallOptions options) {
		this.options = options;
	}

	/** @return status of runnable */
	public synchronized IStatus getResultStatus() {
		if (multistatus == null)
			return statusHelper.createError(getClass().getName() + " was not called yet!");

		MultiStatus result = statusHelper.createMultiStatus("result");
		result.merge(multistatus);
		return result;
	}

	@Override
	synchronized public void run(IProgressMonitor pmonitor) {
		final SubMonitor monitor = SubMonitor.convert(pmonitor, 1);

		final boolean wasAutoBuilding = AutobuildUtils.get();

		AutobuildUtils.turnOff();

		multistatus = statusHelper
				.createMultiStatus("Status of setting up dependencies.");

		librariesActionsHelper.cleanAndInstallAllDependencies(
				getParentPathOfNpmrc(options.npmrc),
				monitor, multistatus);

		if (!multistatus.isOK())
			return;
		if (wasAutoBuilding)
			AutobuildUtils.turnOn();
	}

	/**
	 * Checks if given string is a path pointing to an <code>.npmrc</code> file and then returns its parent(!) path.
	 *
	 * @return {@link Path} instance or {@code absent}.
	 */
	private Optional<Path> getParentPathOfNpmrc(String npmrcPath) {
		if (!Strings.isNullOrEmpty(npmrcPath)) {
			File npmrcFile = new File(npmrcPath);
			if (npmrcFile.isFile()) {
				File parent = npmrcFile.getParentFile();
				if (parent != null) {
					return Optional.of(parent.toPath());
				}
			}
		}
		return Optional.absent();
	}
}
