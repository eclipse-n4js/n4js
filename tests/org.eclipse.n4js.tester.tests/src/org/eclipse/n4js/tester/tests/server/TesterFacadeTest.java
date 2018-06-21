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
package org.eclipse.n4js.tester.tests.server;

import static com.google.common.collect.Lists.newArrayList;

import org.eclipse.n4js.tester.TesterFacade;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.server.HttpServerManager;
import org.eclipse.n4js.tester.tests.AbstractTestTreeTest;
import org.eclipse.n4js.tester.tests.InjectedModules;
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner;
import org.eclipse.n4js.tester.tests.MockTesterModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Class for testing the {@link TesterFacade} runtime behavior.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = { MockTesterModule.class })
public class TesterFacadeTest extends AbstractTestTreeTest {

	@Inject
	private HttpServerManager serverManager;

	@Inject
	private TesterFacade testerFacade;

	private int actualPort = -1;

	/***/
	@Before
	public void before() {
		if (actualPort != -1) {
			serverManager.stopServer(actualPort);
		}
	}

	/***/
	@After
	public void after() {
		serverManager.stopServer(actualPort);
	}

	/***/
	@Test
	public void testEnsureServerIsRunningPrepareSession() {
		actualPort = testerFacade.prepareTestSession(new TestTree(new ID("sessionId"), newArrayList()));
	}

	/***/
	@Test
	public void testPrepareSessionWithoutRunningServer() {
		actualPort = testerFacade.prepareTestSession(new TestTree(new ID("sessionId"), newArrayList()));
	}
}
