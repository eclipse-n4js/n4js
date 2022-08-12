/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.server.N4JSWorkspaceManager;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.xtext.ide.server.build.XWorkspaceManager;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 *
 */
public class CyclicDependenciesBuilderNoRebuildTest extends AbstractIncrementalBuilderTest {

	/**
	 * Test that a single refresh without any other changes will have an empty set of cyclic project names since the
	 * existing cycle did not change.
	 */
	@Test
	public void testStableCycleNoRebuild() {
		testWorkspaceManager.createTestOnDisk(
				Pair.of("P1", List.of(
						Pair.of("M1", "export public class C1 {}"),
						Pair.of(CFG_DEPENDENCIES, "P2"))),
				Pair.of("P2", List.of(
						Pair.of("M2",
								"import {C1} from 'M1'; \n" +
										"const x: C1 = null;\n" +
										"x;"),
						Pair.of(CFG_DEPENDENCIES, "P1"))));

		startAndWaitForLspServer();
		joinServerRequests();

		assertIssues(
				Pair.of("P1/package.json", List.of(
						"(Error, [16:3 - 16:7], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")),
				Pair.of("P2/package.json", List.of(
						"(Error, [16:3 - 16:7], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")));

		executeCommand(N4JSCommandService.N4JS_REFRESH);
		joinServerRequests();

		assertIssues(
				Pair.of("P1/package.json", List.of(
						"(Error, [16:3 - 16:7], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")),
				Pair.of("P2/package.json", List.of(
						"(Error, [16:3 - 16:7], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")));
	}

	@Override
	protected Optional<Class<? extends Module>> getOverridingModule() {
		return Optional.of(Test_Module.class);
	}

	/***/
	public static final class Test_Module extends AbstractGenericModule {
		/***/
		public Class<? extends XWorkspaceManager> bindXWorkspaceManager() {
			return Test_N4JSWorkspaceManager.class;
		}
	}

	/***/
	@Singleton
	public static class Test_N4JSWorkspaceManager extends N4JSWorkspaceManager {
		@Override
		public UpdateResult update(Set<URI> dirtyFiles, Set<URI> deletedFiles, boolean refresh) {
			UpdateResult update = super.update(dirtyFiles, deletedFiles, refresh);
			assertEquals("Expect no changes in cycles", 0, update.cyclicProjectsRemoved.size());
			assertEquals("Expect no changes in cycles", 0, update.cyclicProjectsAdded.size());
			return update;
		}
	}
}
