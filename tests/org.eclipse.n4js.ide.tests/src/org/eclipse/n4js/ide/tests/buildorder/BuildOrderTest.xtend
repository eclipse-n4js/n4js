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
package org.eclipse.n4js.ide.tests.buildorder

import org.junit.Test
import org.eclipse.xtext.resource.impl.ProjectDescription
import org.eclipse.n4js.ide.xtext.server.OrderInfo
import com.google.inject.Injector
import org.eclipse.n4js.utils.Strings
import java.util.List
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.ide.xtext.server.IOrderInfo
import static org.junit.Assert.assertEquals

/**
 *
 */
class BuildOrderTest extends AbstractIdeTest {

	private OrderInfo.Provider projectBuildOrderProvider;

	override Injector createInjector() {
		val Injector injector = super.createInjector();
		projectBuildOrderProvider = injector.getInstance(OrderInfo.Provider);
		return injector;
	}

	/**
	 * @param expectation
	 *            string of project names ordered in build order
	 * @param projectsModulesContents
	 *            test workspace
	 */
	@SafeVarargs
	 def final void test(String expectation, Pair<String, List<Pair<String, String>>>... projectsModulesContents) {
		testWorkspaceManager.createTestOnDisk(projectsModulesContents);
		startAndWaitForLspServer();

		val IOrderInfo<ProjectDescription> orderInfo = projectBuildOrderProvider.get();
		orderInfo.visitAll();
		try {
			val String names = Strings.toString([getName()], orderInfo)
			assertEquals(expectation, names);
		} catch (Exception exc) {
			throw new RuntimeException("Never happens since toString never throws an exception. Bogus xtext warning", exc)
		};
	}
	
	
	@Test
	def void testSingleDependency1() {
		test("[yarn-test-project, n4js-runtime, P1]", 
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			]
		);
	}
	
	@Test
	def void testTwoDependencies1() {
		test("[yarn-test-project, n4js-runtime, P1, P2]", 
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			]
		);
	}
	
	@Test
	def void testTwoDependencies2() {
		test("[yarn-test-project, n4js-runtime, P1, P2]", 
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P1
				'''
			]
		);
	}
	
	@Test
	def void testTwoDependencies3() {
		test("[yarn-test-project, n4js-runtime, P2, P1]", 
			"#NODE_MODULES:n4js-runtime" -> null,
			"P1" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime,
					P2
				'''
			],
			"P2" -> #[
				"#DEPENDENCY" -> '''
					n4js-runtime
				'''
			]
		);
	}
	 
}