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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.Lists;

/**
 * Base class for {@link AbstractIdeTest IDE tests} in which every test case has a uniform structure and behavior.
 */
public abstract class AbstractStructuredIdeTest<T> extends AbstractIdeTest {
	static final String MODULE_SELECTOR = "*";

	/**
	 * This method gets eventually called after calling one of the {@code test()} methods. Overwrite this method to
	 * implement test logic.
	 *
	 * @param project
	 *            project that was created and is used during the test
	 * @param moduleName
	 *            name of the module passed to the {@link #test(LinkedHashMap, String, String, Object) #test())} method
	 *            and opened.
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
		String nameWithSelector = moduleName + MODULE_SELECTOR;
		ArrayList<Pair<String, String>> srcFileNameToContents = Lists.newArrayList(Pair.of(nameWithSelector, contents));
		return test(srcFileNameToContents, t);
	}

	/** Same as {@link #test(List, Object)}, using {@link AbstractIdeTest#MODULE_NAME} as module name. */
	protected Project test(List<Pair<String, String>> moduleNameToContents, T t) {
		Pair<String, List<Pair<String, String>>> pair = Pair.of(PROJECT_NAME, moduleNameToContents);
		ArrayList<Pair<String, List<Pair<String, String>>>> projects = Lists.newArrayList(pair);

		return testWS(projects, t);
	}

	/**
	 * Same as {@link #test(LinkedHashMap, String, String, Object)}, but name and content of the modules can be provided
	 * as {@link Pair pairs}.
	 */
	protected Project testWS(List<Pair<String, List<Pair<String, String>>>> moduleNameToContents, T t) {
		String selectedProject = null;
		String selectedModule = null;
		LinkedHashMap<String, Map<String, String>> moduleNameToContentsAsMap = new LinkedHashMap<>();
		for (Pair<String, List<Pair<String, String>>> project : moduleNameToContents) {
			String projectPath = project.getKey();
			Iterable<? extends Pair<String, String>> modules = project.getValue();
			Map<String, String> modulesMap = null;
			if (modules != null) {
				modulesMap = new HashMap<>();
				for (Pair<String, String> moduleContent : modules) {
					String moduleName = moduleContent.getKey();
					if (moduleName.endsWith(MODULE_SELECTOR)) {
						moduleName = moduleName.substring(0, moduleName.length() - 1);
						selectedProject = projectPath;
						selectedModule = moduleName;
					}
					modulesMap.put(moduleName, moduleContent.getValue());
				}
			}
			moduleNameToContentsAsMap.put(projectPath, modulesMap);
		}

		if (selectedModule == null) {
			throw new IllegalArgumentException(
					"No module selected. Fix by appending '" + MODULE_SELECTOR + "' to one of the project modules.");
		}

		return test(moduleNameToContentsAsMap, selectedProject, selectedModule, t);
	}

	/**
	 * Call this method during a test.
	 *
	 * @param projectsModulesContents
	 *            map that maps directories to module names which map to their contents. Select the project under test
	 *            by appending a {@link #MODULE_SELECTOR} to the directory.
	 * @param moduleName
	 *            one moduleName of the {@code moduleNameToContents}. Will be opened during the test.
	 * @param t
	 *            will be passed to {@link #performTest(Project, String, Object)}.
	 */
	protected Project test(LinkedHashMap<String, Map<String, String>> projectsModulesContents, String projectPath,
			String moduleName, T t) {

		Project project = createTestOnDisk(projectsModulesContents);
		startAndWaitForLspServer();
		String moduleContent = projectsModulesContents.get(projectPath).get(moduleName);
		openFile(moduleName, moduleContent);
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
