/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.server;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.Streams;

/**
 * Base class for {@link AbstractIdeTest IDE tests} in which every test case has a uniform structure and behavior.
 */
public abstract class AbstractStructuredIdeTest<T> extends AbstractIdeTest {

	/**
	 * This method gets eventually called after calling one of the {@code test()} methods. Overwrite this method to
	 * implement test logic.
	 *
	 * @param project
	 *            project that was created and is used during the test
	 * @param moduleName
	 *            name of the module passed to the {@link #test(Map, String, Object) #test())} method and opened.
	 * @param t
	 *            given argument from the {@code test()} method
	 */
	protected abstract void performTest(Project project, String moduleName, T t) throws Exception;

	/**
	 * Call this method during a test.
	 *
	 * @param contents
	 *            will be added to a default module and project.
	 */
	protected Project test(String contents) {
		return test(MODULE_NAME, contents, null);
	}

	/**
	 * Call this method during a test.
	 *
	 * @param contents
	 *            added to a default module and project.
	 * @param t
	 *            will be passed to {@link #performTest(Project, String, Object)}.
	 */
	protected Project test(String contents, T t) {
		return test(MODULE_NAME, contents, t);
	}

	/**
	 * Call this method during a test.
	 *
	 * @param moduleName
	 *            used to create a module in a default project.
	 * @param contents
	 *            contents of the {@code moduleName} module.
	 * @param t
	 *            will be passed to {@link #performTest(Project, String, Object)}.
	 */
	protected Project test(String moduleName, String contents, T t) {
		moduleName = getModuleNameOrDefault(moduleName);
		Map<String, String> srcFileNameToContents = Collections.singletonMap(moduleName, contents);
		return test(srcFileNameToContents, moduleName, t);
	}

	/** Same as {@link #test(Iterable, String, Object)}, using {@link AbstractIdeTest#MODULE_NAME} as module name. */
	protected Project test(Iterable<? extends Pair<String, String>> moduleNameToContents, T t) {
		return test(moduleNameToContents, MODULE_NAME, t);
	}

	/**
	 * Same as {@link #test(Map, String, Object)}, but name and content of the modules can be provided as {@link Pair
	 * pairs}.
	 */
	protected Project test(Iterable<? extends Pair<String, String>> moduleNameToContents, String moduleName, T t) {
		Map<String, String> moduleNameToContentsAsMap = Streams.stream(moduleNameToContents)
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
		return test(moduleNameToContentsAsMap, moduleName, t);
	}

	/**
	 * Call this method during a test.
	 *
	 * @param moduleNameToContents
	 *            map that maps module names to their contents.
	 * @param moduleName
	 *            one moduleName of the {@code moduleNameToContents}. Will be opened during the test.
	 * @param t
	 *            will be passed to {@link #performTest(Project, String, Object)}.
	 */
	protected Project test(Map<String, String> moduleNameToContents, String moduleName, T t) {
		Project project = createTestProjectOnDisk(moduleNameToContents);
		startAndWaitForLspServer();
		openFile(moduleName, moduleNameToContents.get(moduleName));
		try {
			performTest(project, moduleName, t);
		} catch (AssertionError ae) {
			throw ae;
		} catch (Throwable th) {
			throw new AssertionError("exception/error in #performTest() method", th);
		}
		return project;
	}
}
