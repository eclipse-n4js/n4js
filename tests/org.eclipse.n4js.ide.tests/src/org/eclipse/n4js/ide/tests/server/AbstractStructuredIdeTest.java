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

import static org.eclipse.n4js.ide.tests.server.TestWorkspaceCreator.DEFAULT_MODULE_NAME;
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceCreator.DEFAULT_PROJECT_NAME;
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceCreator.DEPENDENCIES;
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceCreator.N4JS_RUNTIME_NAME;
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceCreator.NODE_MODULES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;

import com.google.common.collect.Lists;

/**
 * Base class for {@link AbstractIdeTest IDE tests} in which every test case has a uniform structure and behavior.
 */
public abstract class AbstractStructuredIdeTest<T> extends AbstractIdeTest {
	static final String MODULE_SELECTOR = "*";
	static final String CURSOR_SYMBOL = "<|>";

	/** Data class for file content and position of a cursor symbol */
	static class ContentAndPosition {
		final String content;
		final int line;
		final int column;

		ContentAndPosition(String content, int line, int column) {
			this.content = content;
			this.line = line;
			this.column = column;
		}
	}

	/** @return the content (without cursor symbol) and position of the cursor symbol for a given string */
	protected ContentAndPosition getContentAndPosition(String codeWithCursor) {
		int cursorIdx = codeWithCursor.indexOf(CURSOR_SYMBOL);
		if (cursorIdx < 0) {
			throw new IllegalArgumentException("Cursor symbol " + CURSOR_SYMBOL + " missing");
		}

		String model = codeWithCursor.replace(CURSOR_SYMBOL, "");
		String[] lines = model.substring(0, cursorIdx).replaceAll("\r", "").split("\n");
		int lineCountBeforeCursor = lines.length - 1;
		int columnBeforeCursor = lines[lineCountBeforeCursor].length();

		return new ContentAndPosition(model, lineCountBeforeCursor, columnBeforeCursor);
	}

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
	 * NPM setup of a single project. A n4js-runtime project will be added automatically into the local node_modules
	 * folder.
	 */
	protected List<Pair<String, String>> getDefaultTestModules() {
		return Lists.newArrayList();
	}

	/**
	 * Yarn setup of a workspace. The following things have to be defined explicitly:
	 * <ul>
	 * <li>n4js-runtime project; however, a {@code null} value of module/contents suffices.
	 * <li>all dependencies from n4js projects to n4js-runtime project
	 * <li>selected project where the test specific module will be put into
	 * </ul>
	 */
	protected List<Pair<String, List<Pair<String, String>>>> getDefaultTestYarnWorkspace() {
		return Lists.newArrayList();
	}

	/**
	 * Executes the test with the given default setup. Depending on whether {@link #getDefaultTestYarnWorkspace()}
	 * returns a non-empty result, {@link #testWS(List, Object)} will be triggered. Otherwise
	 * {@link #test(List, Object)} will be used and uses the default setup of {@link #getDefaultTestModules()}.
	 * <p>
	 * For further details mind the documentation of {@link #getDefaultTestYarnWorkspace()} or
	 * {@link #getDefaultTestModules()}respectively.
	 */
	protected void testInDefaultWorkspace(String content, T t) {
		String nameWithSelector = DEFAULT_MODULE_NAME + MODULE_SELECTOR;
		Pair<String, String> moduleContents = Pair.of(nameWithSelector, content);

		boolean moduleAdded = false;
		if (!getDefaultTestYarnWorkspace().isEmpty()) {
			List<Pair<String, List<Pair<String, String>>>> workspace = getDefaultTestYarnWorkspace();
			for (Pair<String, List<Pair<String, String>>> project : workspace) {
				String projectName = project.getKey();
				if (projectName.endsWith(MODULE_SELECTOR)) {
					List<Pair<String, String>> modulesPlusMyModule = new ArrayList<>(project.getValue());
					modulesPlusMyModule.add(moduleContents);
					try {
						ReflectExtensions reflectExtensions = new ReflectExtensions();
						reflectExtensions.set(project, "k", projectName.substring(0, projectName.length() - 1));
						reflectExtensions.set(project, "v", modulesPlusMyModule);
						moduleAdded = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (!moduleAdded) {
				throw new IllegalStateException("No project selected. Use " + MODULE_SELECTOR);
			}

			testWS(workspace, t);
			return;

		} else {
			ArrayList<Pair<String, String>> allModules = Lists.newArrayList(moduleContents);
			allModules.addAll(getDefaultTestModules());
			test(allModules, t);
		}
	}

	/**
	 * Same as {@link #test(String, String, Object)}, but creates a default project with a single module with default
	 * name and given contents.
	 *
	 * @param contents
	 *            will be added to a default module and project.
	 */
	protected Project test(String contents) {
		return test(DEFAULT_MODULE_NAME, contents, null);
	}

	/**
	 * Same as {@link #test(String, String, Object)}, but creates a default project with a single module with default
	 * name and given contents.
	 *
	 * @param contents
	 *            added to a default module and project.
	 * @param t
	 *            will be passed to {@link #performTest(Project, String, Object)}.
	 */
	protected Project test(String contents, T t) {
		return test(DEFAULT_MODULE_NAME, contents, t);
	}

	/**
	 * Same as {@link #test(List, Object)}, but creates a default project with a single module of given name and
	 * contents.
	 *
	 * @param moduleName
	 *            used to create a module in a default project.
	 * @param contents
	 *            contents of the {@code moduleName} module.
	 * @param t
	 *            will be passed to {@link #performTest(Project, String, Object)}.
	 */
	protected Project test(String moduleName, String contents, T t) {
		moduleName = workspaceCreator.getModuleNameOrDefault(moduleName);
		String nameWithSelector = moduleName + MODULE_SELECTOR;
		ArrayList<Pair<String, String>> srcFileNameToContents = Lists.newArrayList(Pair.of(nameWithSelector, contents));
		return test(srcFileNameToContents, t);
	}

	/**
	 * Same as {@link #testWS(List, Object)}, but creates a default project with name {@link #DEFAULT_PROJECT_NAME}.
	 * Also creates project {@link #N4JS_RUNTIME_NAME} and a dependency from default project to n4js-runtime.
	 * <p>
	 * One module has to be selected using {@link #MODULE_SELECTOR}
	 *
	 * @param modulesContents
	 *            pairs that map module names to their contents
	 */
	protected Project test(List<Pair<String, String>> modulesContents, T t) {
		ArrayList<Pair<String, String>> modulesContentsCpy = new ArrayList<>(modulesContents);
		Pair<String, String> pairN4jsRuntime = Pair.of(NODE_MODULES + N4JS_RUNTIME_NAME, null);
		modulesContentsCpy.add(Pair.of(DEPENDENCIES, N4JS_RUNTIME_NAME));
		modulesContentsCpy.add(pairN4jsRuntime);

		ArrayList<Pair<String, List<Pair<String, String>>>> projects = new ArrayList<>();
		Pair<String, List<Pair<String, String>>> pairDefaultPrj = Pair.of(DEFAULT_PROJECT_NAME, modulesContentsCpy);
		projects.add(pairDefaultPrj);

		return testWS(projects, t);
	}

	/**
	 * Same as {@link #test(LinkedHashMap, String, String, Object)}, but name and content of the modules can be provided
	 * as {@link Pair pairs}.
	 * <p>
	 * Finds the selected project and module using the {@link #MODULE_SELECTOR} and removes the selector.
	 */
	protected Project testWS(List<Pair<String, List<Pair<String, String>>>> projectsModulesContents, T t) {
		String selectedProject = null;
		String selectedModule = null;
		LinkedHashMap<String, Map<String, String>> projectsModulesContentsAsMap = new LinkedHashMap<>();
		for (Pair<String, List<Pair<String, String>>> project : projectsModulesContents) {
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
			projectsModulesContentsAsMap.put(projectPath, modulesMap);
		}

		if (selectedModule == null) {
			throw new IllegalArgumentException(
					"No module selected. Fix by appending '" + MODULE_SELECTOR + "' to one of the project modules.");
		}

		return test(projectsModulesContentsAsMap, selectedProject, selectedModule, t);
	}

	/**
	 * Single entry method to perform tests.
	 * <p>
	 * Note: At this point, neither projects nor modules have a '*' appended.
	 *
	 * @param projectsModulesContents
	 *            map that maps directories to module names which map to their contents.
	 * @param projectPath
	 *            project that contains the module with the name 'moduleName'
	 * @param moduleName
	 *            one moduleName of the {@code moduleNameToContents}. Will be opened during the test.
	 * @param t
	 *            will be passed to {@link #performTest(Project, String, Object)}.
	 */
	protected Project test(LinkedHashMap<String, Map<String, String>> projectsModulesContents, String projectPath,
			String moduleName, T t) {

		Project project = workspaceCreator.createTestOnDisk(projectsModulesContents);
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
