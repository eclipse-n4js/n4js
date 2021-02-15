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
import java.io.IOException;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * Runs all tests defined by {@value XtFileDataParser#XT_X_PECT} of a single .xt file
 */
public class XtFileRunner extends Runner {
	final public XtIdeTest ideTest;
	final public String testClassName;
	final public String folderName;
	final public File file;
	final public XtFileData xtFileData;

	Description description;

	/** Constructor */
	public XtFileRunner(XtIdeTest ideTest, String testClassName, String folderName, File file) throws IOException {
		this.ideTest = ideTest;
		this.testClassName = testClassName;
		this.folderName = folderName;
		this.file = file;
		this.xtFileData = XtFileDataParser.parse(file);
	}

	/** @return {@link XtFileData#setupRunnerName} */
	public String getSetupRunnerName() {
		return xtFileData.setupRunnerName;
	}

	@Override
	public Description getDescription() {
		return getOrCreateDescription();
	}

	@Override
	public void run(RunNotifier notifier) {
		if (!testClassName.equals(getSetupRunnerName())) {
			notifier.fireTestIgnored(getDescription());
			return;
		}

		try {
			notifier.fireTestRunStarted(getDescription());

			ideTest.initializeXtFile(xtFileData);
			for (MethodData testMethodData : xtFileData.getTestMethodData()) {
				Description testDescription = testMethodData.getDescription(xtFileData);
				try {
					notifier.fireTestStarted(testDescription);
					ideTest.invokeTestMethod(testMethodData);
					notifier.fireTestFinished(testDescription);
				} catch (Throwable t) {
					notifier.fireTestFailure(new Failure(testDescription, t));
				}
			}
		} catch (AssertionError testFailure) {
			notifier.fireTestFailure(new Failure(getDescription(), testFailure));
		} catch (Throwable t) {
			notifier.fireTestAssumptionFailed(new Failure(getDescription(), t));
		} finally {
			try {
				ideTest.teardown();

				notifier.fireTestRunFinished(new Result());
			} catch (Throwable t) {
				notifier.fireTestFailure(new Failure(getDescription(), t));
			}
		}
	}

	private Description getOrCreateDescription() {
		if (description != null) {
			return description;
		}

		if (!testClassName.equals(getSetupRunnerName())) {
			String msg = getName() + ": Specified runner does not match current runner";
			description = Description.createSuiteDescription(msg, file);
			return description;
		}
		if (xtFileData.noTests()) {
			String msg = getName() + ": No tests found.";
			description = Description.createSuiteDescription(msg, file);
			return description;
		}

		description = Description.createSuiteDescription(getName(), file);
		for (MethodData testMethodData : xtFileData.getTestMethodData()) {
			description.addChild(testMethodData.getDescription(xtFileData));
		}
		return description;
	}

	public String getName() {
		return file.getName() + ": " + folderName;
	}
}
