/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import java.io.File;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 *
 */
public class XtFileRunner extends Runner {
	final XtIdeTest ideTest;
	final String folderName;
	final File file;

	XtFileData xtFileData;
	Description description;

	/**
	 */
	public XtFileRunner(XtIdeTest ideTest, String folderName, File file) {
		this.ideTest = ideTest;
		this.folderName = folderName;
		this.file = file;
	}

	public String getName() {
		return file.getName() + ": " + folderName;
	}

	@Override
	public Description getDescription() {
		return getOrCreateDescription();
	}

	@Override
	public void run(RunNotifier notifier) {
		try {
			notifier.fireTestRunStarted(getDescription());

			ideTest.initializeXtFile(xtFileData);
			for (MethodData testMethodData : xtFileData.testMethodData) {
				Description testDescription = testMethodData.getDescription(xtFileData);
				try {
					notifier.fireTestStarted(testDescription);
					ideTest.invokeTestMethod(testMethodData);
					notifier.fireTestFinished(testDescription);
				} catch (Throwable t) {
					notifier.fireTestFailure(new Failure(testDescription, t));
				}
			}
		} catch (Throwable t) {
			notifier.fireTestFailure(new Failure(getDescription(), t));
		} finally {
			try {
				ideTest.teardown();

				notifier.fireTestRunFinished(new Result());
			} catch (Throwable t) {
				notifier.fireTestFailure(new Failure(getDescription(), t));
			}
		}
	}

	/** Also initializes {@link #xtFileData} */
	private Description getOrCreateDescription() {
		if (description != null) {
			return description;
		}

		if (xtFileData == null) {
			try {
				xtFileData = XtFileDataParser.parse(file);
			} catch (Throwable t) {
				return Description.createSuiteDescription(t.getMessage());
			}
		}

		if (xtFileData.testMethodData.isEmpty()) {
			String msg = "No tests found in " + getName();
			description = Description.createSuiteDescription(msg);
			return description;
		}

		description = Description.createSuiteDescription(getName(), file);
		for (MethodData testMethodData : xtFileData.testMethodData) {
			description.addChild(testMethodData.getDescription(xtFileData));
		}
		return description;
	}
}
