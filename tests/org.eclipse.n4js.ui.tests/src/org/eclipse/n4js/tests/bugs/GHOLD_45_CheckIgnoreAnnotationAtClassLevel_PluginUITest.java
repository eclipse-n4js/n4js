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

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.views.console.ProcessConsole;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.tester.nodejs.ui.NodejsTesterLaunchShortcut;
import org.eclipse.n4js.tester.ui.TesterUiActivator;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.internal.console.ConsoleView;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Injector;

/**
 * Test for testing the {@code @Ignore} annotation while running tests via Mangelhaft.
 */
@SuppressWarnings("restriction")
public class GHOLD_45_CheckIgnoreAnnotationAtClassLevel_PluginUITest extends AbstractPluginUITest {

	private static final EclipseProjectName PROJECT_NAME = new EclipseProjectName("GHOLD-45");

	private static final String CONSOLE_VIEW_ID = "org.eclipse.ui.console.ConsoleView";

	private static final String[] EMPTY_ARRAY = new String[0];

	@Override
	protected ProjectImporter getProjectImporter() {
		return new ProjectImporter(
				new File(new File("probands/" + PROJECT_NAME + "/").getAbsolutePath()),
				Lists.newArrayList(
						N4JSGlobals.N4JS_RUNTIME,
						N4JSGlobals.MANGELHAFT,
						N4JSGlobals.MANGELHAFT_ASSERT,
						N4JSGlobals.MANGELHAFT_CLI));
	}

	/**
	 * Runs a test module with one single class that has method with {@code Ignore} annotation.
	 */
	@Test
	public void testModuleWithIgnoredMethod() {
		final IProject project = ProjectTestsUtils.getProjectByName(PROJECT_NAME);
		assertTrue("Project is not accessible.", project.isAccessible());
		final IFile module = project.getFile("test/X1.n4js");
		assertTrue("Module is not accessible.", module.isAccessible());

		runTestWaitResult(module);

		final String[] expected = { "X1#x12Test" };
		final String[] actual = getConsoleContentLines();

		assertArrayEquals(expected, actual);
	}

	/**
	 * Runs a test module with one single class annotated with {@code Ignore} at class level.
	 */
	@Test
	public void testModuleWithIgnoredClass() {
		final IProject project = ProjectTestsUtils.getProjectByName(PROJECT_NAME);
		assertTrue("Project is not accessible.", project.isAccessible());
		final IFile module = project.getFile("test/X2.n4js");
		assertTrue("Module is not accessible.", module.isAccessible());

		runTestWaitResult(module);

		final String[] expected = EMPTY_ARRAY;
		final String[] actual = getConsoleContentLines();

		assertArrayEquals(expected, actual);
	}

	/**
	 * Runs a test module with one single class that has neither super class nor {@code @Ignore} annotation.
	 */
	@Test
	public void testModuleWithoutSuperClass() {
		final IProject project = ProjectTestsUtils.getProjectByName(PROJECT_NAME);
		assertTrue("Project is not accessible.", project.isAccessible());
		final IFile module = project.getFile("test/A.n4js");
		assertTrue("Module is not accessible.", module.isAccessible());

		runTestWaitResult(module);

		final String[] expected = { "A#aTest" };
		final String[] actual = getConsoleContentLines();

		assertArrayEquals(expected, actual);
	}

	/**
	 * Runs a test module with one single class that is annotated with {@code @Ignore} and has a super class.
	 */
	@Test
	public void testIgnoredModuleWithSuperClass() {
		final IProject project = ProjectTestsUtils.getProjectByName(PROJECT_NAME);
		assertTrue("Project is not accessible.", project.isAccessible());
		final IFile module = project.getFile("test/B.n4js");
		assertTrue("Module is not accessible.", module.isAccessible());

		runTestWaitResult(module);

		final String[] expected = EMPTY_ARRAY;
		final String[] actual = getConsoleContentLines();

		assertArrayEquals(expected, actual);
	}

	/**
	 * Runs a test module with one single class that is not annotated with {@code @Ignore} but its direct super class
	 * is.
	 */
	@Test
	public void testModuleWithIgnoredInSuperClassChain() {
		final IProject project = ProjectTestsUtils.getProjectByName(PROJECT_NAME);
		assertTrue("Project is not accessible.", project.isAccessible());
		final IFile module = project.getFile("test/C.n4js");
		assertTrue("Module is not accessible.", module.isAccessible());

		runTestWaitResult(module);

		final String[] expected = { "A#aTest", "B#b1Test", "C#cTest" };
		final String[] actual = getConsoleContentLines();

		assertArrayEquals(expected, actual);
	}

	/**
	 * Same as {@link Assert#assertArrayEquals(Object[], Object[])}, but with a better failure message.
	 */
	private static void assertArrayEquals(Object[] expected, Object[] actual) {
		Assert.assertArrayEquals("arrays are not equal"
				+ "; expected: " + Arrays.toString(expected)
				+ "; actual: " + Arrays.toString(actual),
				expected, actual);
	}

	private void runTestWaitResult(final IFile moduleToTest) {
		final ILaunchShortcut launchShortcut = getLaunchShortcut();
		launchShortcut.launch(new StructuredSelection(moduleToTest), ILaunchManager.RUN_MODE);
		ProjectTestsUtils.waitForAllJobs();
	}

	private String[] getConsoleContentLines() {
		final List<String> lines = newArrayList(getConsoleContent().split("\r\n?|\n"));
		// Instead of returning a single element array with an empty string, return with an empty array.
		if (lines.size() == 1 && "".equals(lines.get(0))) {
			return new String[0];
		}
		Collections.sort(lines);
		return Iterables.toArray(lines, String.class);
	}

	private String getConsoleContent() {
		waitForIdleState();
		final IViewPart viewPart = EclipseUIUtils.showView(CONSOLE_VIEW_ID);
		final ConsoleView consoleView = assertInstanceOf(viewPart, ConsoleView.class);
		final IConsole console = consoleView.getConsole();
		// Can be null, if nothing was logged to the console yet. Such cases return with empty string instead.
		if (console == null) {
			return "";
		}
		final ProcessConsole processConsole = assertInstanceOf(console, ProcessConsole.class);
		final String content = processConsole.getDocument().get();
		return content;
	}

	private ILaunchShortcut getLaunchShortcut() {
		return getTesterInjector().getInstance(NodejsTesterLaunchShortcut.class);
	}

	private Injector getTesterInjector() {
		return TesterUiActivator.getInjector();
	}

}
