/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/*** Basic tests for N4jsc,like checking command line options or simple compile. */
@RunWith(Parameterized.class)
public class N4jscArgumentOrderTest extends AbstractN4jscTest {

	static File WORKSPACE;
	static String WS_ORDER_TEST = "N4jscArgumentOrderTest";

	static String[] args;

	/**
	 * Setup workspace.
	 */
	@BeforeClass
	public static void setupWorkspace() throws IOException {
		WORKSPACE = setupWorkspace(WS_ORDER_TEST);
		String currentPath = WORKSPACE.getAbsolutePath().toString();
		System.out.println("just for reference base-path is: " + currentPath);

		// @formatter:off
		args = new String[]{
				"--testCatalogFile " + currentPath + "/build/test-catalog.json",
				"--installMissingDependencies",
				"--buildType projects",
				currentPath + "/PA",
				currentPath + "/PB",
				currentPath + "/PC",
				currentPath + "/PD",
				currentPath + "/PE",
				currentPath + "/PF",
				currentPath + "/PG",
				"--debug"
		};
		// @formatter:on

	}

	/** Cleanup. */
	@AfterClass
	public static void deleteWorkspace() throws IOException {
		FileDeleter.delete(WORKSPACE.toPath(), true);
	}

	// @formatter:off
	final static String expectedOrder = "" +
			"DEBUG: 1. Project PA (RUNTIME_LIBRARY) used by [PA (RUNTIME_LIBRARY), PB (LIBRARY), PC (RUNTIME_LIBRARY)]\n"+
			"DEBUG: 2. Project PB (LIBRARY) used by [PB (LIBRARY)]\n"+
			"DEBUG: 3. Project PC (RUNTIME_LIBRARY) used by [PC (RUNTIME_LIBRARY)]\n"+
			"DEBUG: 4. Project PD (RUNTIME_LIBRARY) used by [PD (RUNTIME_LIBRARY)]\n"+
			"DEBUG: 5. Project PE (RUNTIME_LIBRARY) used by [PE (RUNTIME_LIBRARY)]\n"+
			"DEBUG: 6. Project PF (RUNTIME_LIBRARY) used by [PF (RUNTIME_LIBRARY)]\n"+
			"DEBUG: 7. Project PG (RUNTIME_LIBRARY) used by [PG (RUNTIME_LIBRARY)]\n";

	final static int[][] shuffleOrders = {
			{0,1,2,3,4,5,6,7,8,9,10},
			{10,9,8,7,6,5,4,3,2,1,0},
			{0,1,2,3,4,5,7,8,9,10,6},
			{0,1,2,10,3,9,4,8,5,7,6},
			};
	// @formatter:on

	@SuppressWarnings("javadoc")
	@Parameter
	public int[] shuffleOrder;

	/**
	 * Returns test data.
	 */
	@Parameters
	public static Collection<int[]> shuffleOrders() {
		return Arrays.asList(shuffleOrders);
	}

	/**
	 * normal compile all test without flag "--keepCompiling"
	 */
	@Test
	public void testDifferentArgumentOrder() {

		String[] shuffledArgs = shuffleArgs(shuffleOrder);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);

		try {
			new N4jscBase().doMain(shuffledArgs);

			Reader reader = new InputStreamReader(new ByteArrayInputStream(baos.toByteArray()));
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			String actualOrder = "";
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(". Project ")) {
					old.println(line);
					actualOrder += line + "\n";
				}
			}

			assertEquals(expectedOrder, actualOrder);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			fail();
		} finally {
			System.out.flush();
			System.setOut(old);
		}
	}

	/**
	 * Shuffles the arguments based on the the given order. Also splits arguments at space characters.
	 */
	private String[] shuffleArgs(int[] shufflOrder) {
		List<String> shuffledArgs = new LinkedList<>();
		for (int i = 0; i < shufflOrder.length; i++) {
			String argLine = args[shufflOrder[i]];
			String[] splitArgLine = argLine.split(" ");
			for (String sal : splitArgLine) {
				shuffledArgs.add(sal);
			}
		}

		return shuffledArgs.toArray(new String[shuffledArgs.size()]);
	}

}
