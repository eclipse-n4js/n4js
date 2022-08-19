/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Code completion tests for camel case scenarios
 */
public class ImportSpecifierCompletionTest extends AbstractCompletionTest {

	@Override
	protected List<Pair<String, List<Pair<String, String>>>> getDefaultTestWorkspace() {
		return List.of(
				Pair.of("my-project*", List.of(
						Pair.of(CFG_DEPENDENCIES, "other-project"),
						Pair.of("CarFlyingAstronaut", ""),
						Pair.of("utils/Vision3DUtil", ""),
						Pair.of("utils/files/FileReader", ""))),

				Pair.of("other-project", List.of(
						Pair.of("UberManagerTool", ""),
						Pair.of("otherUtils/TheUnbelievableMystery", ""))),

				Pair.of("unrelated-project", List.of(
						Pair.of("ModuleInUnrelatedProject", ""))));
	}

	/***/
	@Test
	public void testAllSuggestions() {
		testAtCursor("import X from '<|>';",
				"(CarFlyingAstronaut, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:15], CarFlyingAstronaut), [], [], , )\n"
						+ "(otherUtils/TheUnbelievableMystery, Module, yarn-test-project/packages/other-project, , , 00001, , , , ([0:15 - 0:15], other-project/otherUtils/TheUnbelievableMystery), [], [], , )\n"
						+ "(UberManagerTool, Module, yarn-test-project/packages/other-project, , , 00002, , , , ([0:15 - 0:15], other-project/UberManagerTool), [], [], , )\n"
						+ "(utils/files/FileReader, Module, yarn-test-project/packages/my-project, , , 00003, , , , ([0:15 - 0:15], utils/files/FileReader), [], [], , )\n"
						+ "(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00004, , , , ([0:15 - 0:15], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesInRoot() {
		testAtCursor("import X from 'CarFlyin<|>';",
				"(CarFlyingAstronaut, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:23], CarFlyingAstronaut), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesInSubfolder() {
		testAtCursor("import X from 'Visio<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:20], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesInSubfolders() {
		testAtCursor("import X from 'FileRea<|>';",
				"(utils/files/FileReader, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:22], utils/files/FileReader), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesInSubfoldersCamelCaseSkipped() {
		testAtCursor("import X from 'FR<|>';",
				"(utils/files/FileReader, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:17], utils/files/FileReader), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesInSubfoldersCamelCaseIncluded1() {
		testAtCursor("import X from 'FFR<|>';",
				"(utils/files/FileReader, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:18], utils/files/FileReader), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesInSubfoldersCamelCaseIncluded2() {
		testAtCursor("import X from 'uFFR<|>';",
				"(utils/files/FileReader, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:19], utils/files/FileReader), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesOfOtherProject() {
		testAtCursor("import X from 'UberMana<|>';",
				"(UberManagerTool, Module, yarn-test-project/packages/other-project, , , 00000, , , , ([0:15 - 0:23], other-project/UberManagerTool), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCase() {
		testAtCursor("import X from 'CF<|>';",
				"(CarFlyingAstronaut, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:17], CarFlyingAstronaut), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseSkipped() {
		testAtCursor("import X from 'FA<|>';",
				"(CarFlyingAstronaut, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:17], CarFlyingAstronaut), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseWithSubfolderSkipped() {
		testAtCursor("import X from 'V<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:16], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseStartWithDigit() {
		testAtCursor("import X from '3D<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:17], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseWithSubfolderSkippedDigitSkipped() {
		testAtCursor("import X from 'VD<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:17], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseWithSubfolderIncluded() {
		testAtCursor("import X from 'uV<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:17], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseWithSubfolderSkippedAndDigit1() {
		testAtCursor("import X from 'V3<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:17], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseWithSubfolderSkippedAndDigit2() {
		testAtCursor("import X from 'V3D<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:18], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseWithSubfolderIncludedAndDigit1() {
		testAtCursor("import X from 'uV3<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:18], utils/Vision3DUtil), [], [], , )");
	}

	/***/
	@Test
	public void testModuleNamesCamelCaseWithSubfolderIncludedAndDigit2() {
		testAtCursor("import X from 'uV3D<|>';",
				"(utils/Vision3DUtil, Module, yarn-test-project/packages/my-project, , , 00000, , , , ([0:15 - 0:19], utils/Vision3DUtil), [], [], , )");
	}

}
