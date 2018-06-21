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
import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.utils.UIUtils.NonWaitingSupplier;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.junit.BeforeClass;

import com.google.common.collect.Lists;

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
	 * Returns with the {@link Display#getCurrent() current} display.
	 *
	 * @return the current display.
	 */
	protected Display getDisplay() {
		return checkNotNull(Display.getCurrent(), "Not on UI thread.");
	}

	/**
	 * Returns with the active page.
	 *
	 * @return the active page.
	 */
	protected IWorkbenchPage getPage() {
		return checkNotNull(getWindow().getActivePage(), "Active page was null.");
	}

	/**
	 * Returns with the active workbench window.
	 *
	 * @return the active window.
	 */
	protected IWorkbenchWindow getWindow() {
		return checkNotNull(
				getWorkbench().getActiveWorkbenchWindow(),
				"Active workbench window was null. Is this method called from the UI thread?");
	}

	/**
	 * Returns with the view part with the given ID. May return with {@code null} if the view part cannot be found with
	 * the given identifier.
	 *
	 * @param id
	 *            the unique ID of the view part.
	 * @return the view part, or {@code null} if cannot be found.
	 */
	protected IViewPart findView(final String id) {
		return getPage().findView(checkNotNull(id, "id"));
	}

	/**
	 * Shows then returns with the view with the given unique view part identifier. May return with {@code null} if the
	 * view cannot be found.
	 *
	 * @param id
	 *            the unique ID of the view part to show.
	 * @return the view part or {@code null} if the view part cannot be shown.
	 */
	protected IViewPart showView(final String id) {
		try {
			return getPage().showView(checkNotNull(id, "id"));
		} catch (final PartInitException e) {
			final String message = "Error occurred while initializing view with ID: '" + id + "'.";
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		}
	}

	/**
	 * Hides the view part argument.
	 *
	 * @param viewPart
	 *            the view part to hide. Must not be {@code null}.
	 */
	protected void hideView(final IViewPart viewPart) {
		getActivePage().hideView(checkNotNull(viewPart, "viewPart"));
	}

	/**
	 * See {@link UIUtils#waitForUiThread()}.
	 */
	protected void waitForUiThread() {
		UIUtils.waitForUiThread();
	}

	/**
	 * Wait until all background tasks are complete then makes sure that the UI thread is idle as well.
	 */
	protected void waitForIdleState() {
		final IJobManager manager = Job.getJobManager();
		while (manager.currentJob() != null) {
			waitForUiThread();
		}
		waitForUiThread();
	}

	/** See {@link UIUtils#waitForValueFromUI(NonWaitingSupplier, Supplier)}. */
	protected <T> T waitForValueFromUISW(NonWaitingSupplier<T> nonWaitingSupplier, Supplier<String> valueDescSupplier) {
		return UIUtils.waitForValueFromUI(nonWaitingSupplier, valueDescSupplier);
	}

	/** See {@link UIUtils#waitForValueFromUI(NonWaitingSupplier, Supplier, long)}. */
	protected <T> T waitForValueFromUIS(NonWaitingSupplier<T> nonWaitingSupplier, Supplier<String> valueDescSupplier,
			long timeout) {
		return UIUtils.waitForValueFromUI(nonWaitingSupplier, valueDescSupplier, timeout);
	}

	/**
	 * Returns with the {@link IProject project} from the {@link IWorkspace workspace} with the given project name.
	 * Makes no assertions whether the project can be accessed or not.
	 *
	 * @param projectName
	 *            the name of the desired project.
	 * @return the project we are looking for. Could be non-{@link IProject#isAccessible() accessible} project.
	 */
	protected IProject getProjectByName(final String projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}

	/**
	 * Returns with an array or project form the workspace. for the given subset of unique project names. Sugar for
	 *
	 * @param projectName
	 *            the name of the project.
	 * @param otherName
	 *            the name of another project.
	 * @param rest
	 *            additional names of desired projects.
	 * @return an array of projects, could contain non-accessible project.
	 */
	protected IProject[] getProjectsByName(final String projectName, final String otherName, final String... rest) {
		final List<String> projectNames = Lists.asList(projectName, otherName, rest);
		final IProject[] projects = new IProject[projectNames.size()];
		for (int i = 0; i < projects.length; i++) {
			projects[i] = getProjectByName(projectNames.get(i));
		}
		return projects;
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
