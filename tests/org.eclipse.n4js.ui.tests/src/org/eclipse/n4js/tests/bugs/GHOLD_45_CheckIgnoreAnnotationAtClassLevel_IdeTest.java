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

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Test for testing the {@code @Ignore} annotation while running tests via Mangelhaft.
 */
// converted from GHOLD_45_CheckIgnoreAnnotationAtClassLevel_PluginUITest
public class GHOLD_45_CheckIgnoreAnnotationAtClassLevel_IdeTest extends ConvertedIdeTest {

	private static final String PROJECT_NAME = "GHOLD-45";

	private static final String[] EMPTY_ARRAY = new String[0];

	/** Import the proband. */
	@Before
	public void prepareWorkspace() {
		importProband(new File("probands", PROJECT_NAME), Lists.newArrayList(
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
		final String[] expected = { "X1#x12Test" };
		final String[] actual = runTestWaitResult("X1");
		assertArrayEquals(expected, actual);
	}

	/**
	 * Runs a test module with one single class annotated with {@code Ignore} at class level.
	 */
	@Test
	public void testModuleWithIgnoredClass() {
		final String[] expected = EMPTY_ARRAY;
		final String[] actual = runTestWaitResult("X2");
		assertArrayEquals(expected, actual);
	}

	/**
	 * Runs a test module with one single class that has neither super class nor {@code @Ignore} annotation.
	 */
	@Test
	public void testModuleWithoutSuperClass() {
		final String[] expected = { "A#aTest" };
		final String[] actual = runTestWaitResult("A");
		assertArrayEquals(expected, actual);
	}

	/**
	 * Runs a test module with one single class that is annotated with {@code @Ignore} and has a super class.
	 */
	@Test
	public void testIgnoredModuleWithSuperClass() {
		final String[] expected = EMPTY_ARRAY;
		final String[] actual = runTestWaitResult("B");

		assertArrayEquals(expected, actual);
	}

	/**
	 * Runs a test module with one single class that is not annotated with {@code @Ignore} but its direct super class
	 * is.
	 */
	@Test
	public void testModuleWithIgnoredInSuperClassChain() {
		final String[] expected = { "A#aTest", "B#b1Test", "C#cTest" };
		final String[] actual = runTestWaitResult("C");

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

	private String[] runTestWaitResult(String moduleToTest) {
		String output = runMangelhaft(PROJECT_NAME, Optional.of(moduleToTest), true);
		final List<String> lines = newArrayList(output.split("\r\n?|\n"));
		// Instead of returning a single element array with an empty string, return with an empty array.
		if (lines.size() == 1 && "".equals(lines.get(0))) {
			return new String[0];
		}
		Collections.sort(lines);
		return Iterables.toArray(lines, String.class);
	}
}
