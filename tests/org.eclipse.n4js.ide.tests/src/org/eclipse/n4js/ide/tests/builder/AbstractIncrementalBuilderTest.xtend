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
package org.eclipse.n4js.ide.tests.builder

import com.google.inject.Injector
import java.nio.file.Files
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig
import org.eclipse.n4js.projectDescription.ProjectType
import org.junit.Assert

/**
 * Abstract base class for tests of the incremental builder in the LSP server.
 */
abstract class AbstractIncrementalBuilderTest extends AbstractIdeTest {

	// FIXME reconsider the default in AbstractIdeTest
	override ProjectType getProjectType() {
		return ProjectType.LIBRARY;
	}

	// FIXME reconsider the defaults in ProjectStatePersisterConfig
	override protected Injector createInjector() {
		val injector = super.createInjector();
		val persisterConfig = injector.getInstance(ProjectStatePersisterConfig);
		persisterConfig.setDeleteState(false);
		persisterConfig.setWriteToDisk(true);
		return injector;
	}

	def protected void assertOutputFileExists(String projectName, String moduleName) {
		val mainProjectSrcGen = getProjectRoot(projectName).toPath().resolve("src-gen");
		val ajs = mainProjectSrcGen.resolve(moduleName + ".js");
		Assert.assertTrue(Files.isRegularFile(ajs));
	}
}
