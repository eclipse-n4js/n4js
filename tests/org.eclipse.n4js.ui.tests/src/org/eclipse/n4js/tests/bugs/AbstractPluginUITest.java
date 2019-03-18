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
package org.eclipse.n4js.tests.bugs;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.ui.IWorkbench;
import org.junit.BeforeClass;

/**
 * Base test class for plug-in UI tests. Does not contain any test cases rather convenient methods and assertions for
 * concrete test classes.
 */
public abstract class AbstractPluginUITest extends AbstractIDEBUG_Test {

	/**
	 * Asserts that the {@link IWorkbench workbench} is running.
	 */
	@BeforeClass
	public static void assertWorkbenchIsRunning() {
		assertTrue("Expected running workbench.", isWorkbenchRunning());
	}

	/**
	 * Wait until all background tasks are complete then makes sure that the UI thread is idle as well.
	 */
	protected void waitForIdleState() {
		ProjectTestsUtils.waitForAutoBuild();
		ProjectTestsUtils.waitForAllJobs(Long.MAX_VALUE);
		UIUtils.waitForUiThread();
	}

	/**
	 * Asserts that {@code expected} argument is {@link Class#isAssignableFrom(Class) assignable} from the
	 * {@code actual} argument. If {@code true}, then returns with the casted type safe instance, otherwise throws an
	 * exception.
	 *
	 * @param actual
	 *            the actual object to check. Must not be {@code null}.
	 * @param expected
	 *            the expected class. Must not {@code null}.
	 * @return the {@code actual} argument casted to the {@code expected} type.
	 */
	protected <T> T assertInstanceOf(Object actual, Class<T> expected) {
		checkNotNull(expected, "expected");
		assertNotNull(actual);
		assertTrue("Expected an instance of " + expected.getName() + " was: " + actual,
				expected.isAssignableFrom(actual.getClass()));
		return expected.cast(actual);
	}

}
