/**
 * Documentation defining default test projects and workspaces in
 * {@link org.eclipse.n4js.ide.tests.server.AbstractStructuredIdeTest AbstractStructuredIdeTest}s and for their creation
 * in {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager TestWorkspaceManager}.
 * <p>
 * <h3>Contents</h3>
 * <ul>
 * <li> Use default {@link org.eclipse.n4js.ide.tests.server.Documentation#DEFAULT_TEST_PROJECT projects}
 * <li> Use default {@link org.eclipse.n4js.ide.tests.server.Documentation#DEFAULT_TEST_WORKSPACE workspaces}
 * <li> {@link org.eclipse.n4js.ide.tests.server.Documentation#MODULE_NAMES Module names}
 * <ul>
 * <li> Special case: {@link org.eclipse.n4js.ide.tests.server.Documentation#PACKAGE_JSON package.json}
 * <li> Special case: {@link org.eclipse.n4js.ide.tests.server.Documentation#CFG_DEPENDENCIES CFG_DEPENDENCIES}
 * </ul>
 * <li> Dependency projects inside node_modules
 * <ul>
 * <li> {@link org.eclipse.n4js.ide.tests.server.Documentation#PROJECT_NODE_MODULES of npm projects}
 * <li> {@link org.eclipse.n4js.ide.tests.server.Documentation#WORKSPACE_NODE_MODULES of yarn workspace}
 * </ul>
 * </ul>
 */
package org.eclipse.n4js.ide.tests.server;

/**
 * Contents see: {@link org.eclipse.n4js.ide.tests.server}
 */
abstract class Documentation {
	/** Do not instantiate. Only for Java-Documentation */
	private Documentation() {
	}

	/**
	 * If method {@link org.eclipse.n4js.ide.tests.server.AbstractStructuredIdeTest#getDefaultTestProject()
	 * getDefaultTestProject()} is overridden, a default npm project is used for all tests of one
	 * {@link org.eclipse.n4js.ide.tests.server.AbstractStructuredIdeTest AbstractStructuredIdeTest}. However, an
	 * additional module is added to this default project. This additional module contains the actual test case that
	 * will refer to modules of its default project.
	 * <p>
	 * Implicitly, the default project will be extended by a node_modules folder that contains the project
	 * 'n4js-runtime'. Also, a dependency from the default project will be added to n4js-runtime.
	 * <p>
	 * Defining a default npm workspace can be done using the convenient xtend list, pair and string literal notation.
	 * This notation will also be used in the following examples.
	 * <p>
	 * <b>Simple default npm project</b>
	 *
	 * <pre>
	 * override final List<Pair<String, String>> getDefaultTestProject() {
	 * 	return #[
	 * 		"MA"  -> '''
	 * 				export class A1 {}
	 * 				export class A2 {}''',
	 * 		"MBA" -> '''
	 * 				export class B1 {}
	 * 				export class A2 {}'''];
	 * }
	 * </pre>
	 *
	 * The implicit test module will also be added to the test project. Moreover, it will be the selected module and be
	 * opened during execution of the test.
	 */
	static int DEFAULT_TEST_PROJECT;

	/**
	 * If method {@link org.eclipse.n4js.ide.tests.server.AbstractStructuredIdeTest#getDefaultTestWorkspace()
	 * getDefaultTestWorkspace()} is overridden, a default yarn workspace is used for all tests of one
	 * {@link org.eclipse.n4js.ide.tests.server.AbstractStructuredIdeTest AbstractStructuredIdeTest}. However, an
	 * additional module is added to this default workspace. This additional module contains the actual test case that
	 * will refer to projects and modules of its default workspace.
	 * <p>
	 * Defining a default yarn workspace can be done using the convenient xtend list, pair and string literal notation.
	 * This notation will also be used in the following examples.
	 * <p>
	 * <b>Simple default yarn workspace</b>
	 *
	 * <pre>
	 * override final List<Pair<String, String>> getDefaultTestWorkspace() {
	 * 		return #[
	 * 			"P1*" -> #[
	 * 				"M1" -> '''
	 * 					import "XY" from "LibXY";
	 * 					const xy = new XY();
	 * 				'''],
	 * 			"P2" -> #[
	 * 				"LibXY" -> '''
	 * 					export public class XY {}
	 * 				''']
	 * 		];
	 * }
	 * </pre>
	 *
	 * It is necessary to select one of the project as the selected project. This is done by appending the
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#MODULE_SELECTOR MODULE_SELECTOR} at the end of a
	 * project name. The result is, that the implicit test module is added to the selected project. Similar to the
	 * default test project, the implicit module will be the selected module and be opened during execution of the test.
	 */
	static int DEFAULT_TEST_WORKSPACE;

	/**
	 * Module names used in default test projects or workspace can be given either with extension or without. The
	 * default extension is {@code n4js} if no extension (which must be one of
	 * {@link org.eclipse.n4js.N4JSGlobals#ALL_N4_FILE_EXTENSIONS}) was given.
	 * <p>
	 * In case the module name is {@code package.json}, see {@link #PACKAGE_JSON}.
	 * <p>
	 * In case the string starts with the reserved string
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#CFG_NODE_MODULES CFG_NODE_MODULES}, see
	 * {@link #PROJECT_NODE_MODULES} or {@link #WORKSPACE_NODE_MODULES}.
	 */
	static int MODULE_NAMES;

	/**
	 * {@code package.json} files are created automatically. In case this should be overridden by a custom file, use
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#PACKAGE_JSON PACKAGE_JSON} as a module name and
	 * define its contents.
	 *
	 * <p>
	 * <b>Simple example</b>
	 *
	 * <pre>
	 * override final List<Pair<String, String>> getDefaultTestModules() {
	 * 		return #[
	 * 			NODE_MODULES + "@n4jsd/SomeNPM" + SRC + "index.n4jsd" -> '''
	 * 				export public external class A1 {}
	 * 				''',
	 * 			NODE_MODULES + "@n4jsd/SomeNPM" + SRC + PACKAGE_JSON  -> '''
	 * 				{
	 * 					"name": "@n4jsd/SomeNPM",
	 * 					"version": "0.0.1",
	 * 					"n4js": {
	 * 						"projectType": "definition",
	 * 						"definesPackage": "someNPM",
	 * 						"sources": {
	 * 							"source": [
	 * 								"src"
	 * 							]
	 * 						}
	 * 					}
	 * 				}'''
	 * 		];
	 * }
	 * </pre>
	 *
	 * The example above shows how a default project that only defines one project inside the node_modules folder. Since
	 * this project is of project type {@code definition}, its package.json file is defined explicitly and overrides the
	 * one that would have been generated. Note that the reserved string
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#CFG_SRC CFG_SRC} is misleading here: the
	 * package.json file will be located in the base directory of the project.
	 */
	static int PACKAGE_JSON;

	/**
	 * Instead of a module name, also the reserved string
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#CFG_DEPENDENCIES CFG_DEPENDENCIES} can be given. As
	 * the contents of this entry, a comma separated list of project names is expected. These dependencies will be added
	 * to the current project.
	 * <p>
	 * <b>Simple example</b>
	 *
	 * <pre>
	 * override final List<Pair<String, String>> getDefaultTestModules() {
	 * 	return #[
	 * 		"MA"  -> '''
	 * 				export public class A1 {}''',
	 * 		DEPENDENCIES -> '''
	 * 				n4js-runtime,
	 * 				react'''];
	 * }
	 * </pre>
	 *
	 * The example above shows that the default project defines dependencies to two projects: n4js-runtime and react.
	 * Mind that the definition of the dependency to n4js-runtime might be obsolete, since this dependency is added
	 * automatically for default projects (but not for default workspaces, though).
	 */
	static int CFG_DEPENDENCIES;

	/**
	 * Instead of a module name, also the reserved string
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#CFG_MAIN_MODULE CFG_MAIN_MODULE} can be given. This
	 * will set the package.json property {@link org.eclipse.n4js.packagejson.PackageJsonProperties#MAIN_MODULE
	 * "mainModule"} to the value given as argument.
	 * <p>
	 * <b>Simple example</b>
	 *
	 * <pre>
	 * override final List<Pair<String, String>> getDefaultTestModules() {
	 * 	return #[
	 * 		"MA"  -> '''
	 * 			export public class A1 {}
	 * 		''',
	 * 		MAIN_MODULE -> "MA"
	 * 	];
	 * }
	 * </pre>
	 */
	static int CFG_MAIN_MODULE;

	/**
	 * Each project (both default projects and projects in default workspaces) can have a nested node_modules folder
	 * that contains other projects. This structure is defined using the reserved string
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#CFG_NODE_MODULES CFG_NODE_MODULES}.
	 * <p>
	 * <b>Simple example for default project</b>
	 *
	 * <pre>
	 * override final List<Pair<String, String>> getDefaultTestModules() {
	 * 	return #[
	 * 		"MA"  -> '''
	 * 				export class A1 {}''',
	 * 		NODE_MODULES + "P2" + SRC + "index" -> '''
	 * 				export public class A2 {}''',
	 * 		DEPENDENCIES -> "P2"],
	 * }
	 * </pre>
	 *
	 * The example above shows that the default project has a node_modules folder that contains the project P2. Note
	 * that the dependency from the default project to P2 is also added (see {@link #CFG_DEPENDENCIES}). Also note that
	 * the project n4js-runtime and its dependency is added implicitly (see {@link #DEFAULT_TEST_PROJECT}).
	 * <p>
	 * The modules inside node_module projects are defined using two registered strings.
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#CFG_NODE_MODULES CFG_NODE_MODULES} and
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#CFG_SRC CFG_SRC} separated the name of the project
	 * and one of its modules. It is possible to define several modules of a single node_modules projects.
	 */
	static int PROJECT_NODE_MODULES;

	/**
	 * {@code node_modules} folders of a workspace are located in the base directory of the yarn workspace project. To
	 * define projects at this location, use the registered string
	 * {@link org.eclipse.n4js.ide.tests.server.TestWorkspaceManager#CFG_NODE_MODULES CFG_NODE_MODULES} followed by a
	 * project name.
	 * <p>
	 * <b>Simple example for default project</b>
	 *
	 * <pre>
	 * override final List<Pair<String, String>> getDefaultTestModules() {
	 * 	return #[
	 * 		"P1"  -> #[
	 * 			"MA"  -> '''
	 * 				export class A1 {}
	 * 				''',
	 * 			DEPENDENCIES -> '''
	 * 				&#64;n4jsd/SomeNPM,
	 * 				n4js-runtime
	 * 				'''
	 * 		],
	 *
	 * 		NODE_MODULES + N4JS_RUNTIME -> null,
	 * 		NODE_MODULES + "@n4jsd/SomeNPM" -> #[
	 * 			"index.n4jsd" -> '''
	 * 				export public external class A1 {}
	 * 				''',
	 * 			PACKAGE_JSON  -> '''
	 * 				{
	 * 					"name": "@n4jsd/SomeNPM",
	 * 					"version": "0.0.1",
	 * 					"n4js": {
	 * 						"projectType": "definition",
	 * 						"definesPackage": "someNPM",
	 * 						"sources": {
	 * 							"source": [
	 * 								"src"
	 * 							]
	 * 						}
	 * 					}
	 * 				}'''
	 * 			]
	 * 		];
	 * }
	 * </pre>
	 *
	 * The example above shows a workspace of three projects: {@code P1}, {@code n4js-runtime} and
	 * {@code @n4jsd/SomeNPM}, where {@code P1} is inside the packages folder and the other two are inside the
	 * node_modules folder of the workspace. Note that the project {@code n4js-runtime} has to be defined and used
	 * explicitly in default yarn workspace setups. Also, the dependencies are defined explicitly, which are here from
	 * project {@code P1} to {@code n4js-runtime} and {@code @n4jsd/SomeNPM}.
	 * <p>
	 * Note that it is also possible to put projects into node_modules folders of a specific project's (e.g. {@code P1})
	 * node_modules folder instead of the workspace's node_modules folder.
	 */
	static int WORKSPACE_NODE_MODULES;
}
