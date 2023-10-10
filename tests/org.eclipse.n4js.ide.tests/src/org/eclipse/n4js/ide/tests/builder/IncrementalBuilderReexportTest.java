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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Testing incremental builds of re-export chains.
 */

public class IncrementalBuilderReexportTest extends AbstractIncrementalBuilderTest {

	@Override
	protected Set<String> getIgnoredIssueCodes() {
		// note: this test is testing internal infrastructure for a feature not available in N4JS[D] (only in .d.ts)
		HashSet<String> iic = new HashSet<>(super.getIgnoredIssueCodes());
		iic.add(IssueCodes.UNSUPPORTED);
		return iic;
	}

	@Test
	public void testSingleProject_simple() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Other1", """
							export public class Cls {
								public field: string;
							}
						""",
				"Other2", """
							export { Cls } from "Other1";
						""",
				"Other3", """
							export { Cls } from "Other2";
						""",
				"Main", """
							import {Cls} from "Other3";

							let x: string = new Cls().field;
						"""));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:32], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	public void testSingleProject_separateExports() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Other1", """
							export public class Cls {
								public field: string;
							}
						""",
				"Other2", """
							import { Cls } from "Other1";
							export { Cls };
						""",
				"Other3", """
							import { Cls } from "Other2";
							export { Cls };
						""",
				"Main", """
							import {Cls} from "Other3";

							let x: string = new Cls().field;
						"""));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:32], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	public void testSingleProject_withAlias() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Other1", """
							export public class Cls {
								public field: string;
							}
						""",
				"Other2", """
							import { Cls as MyCls } from "Other1";
							export { MyCls as Cls2 };
						""",
				"Other3", """
							import { Cls2 as MyCls } from "Other2";
							export { MyCls as Cls3 };
						""",
				"Main", """
							import { Cls3 as MyCls } from "Other3";

							let x: string = new MyCls().field;
						"""));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:34], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	public void testSingleProject_moduleReexport() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Other1", """
							export public class Cls {
								public field: string;
							}
						""",
				"Other2", """
							export * from "Other1";
						""",
				"Other3", """
							export * from "Other2";
						""",
				"Main", """
							import {Cls} from "Other3";

							let x: string = new Cls().field;
						"""));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:32], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	public void testSingleProject_moduleReexportWithAlias() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Other1", """
							export public class Cls {
								public field: string;
							}
						""",
				"Other2", """
							export * as N2 from "Other1";
						""",
				"Other3", """
							export * as N3 from "Other2";
						""",
				"Main", """
							import {N3} from "Other3";

							let x: string = new N3.N2.Cls().field;
						"""));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:38], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	public void testSingleProject_changeReexportedElement() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Other1", """
							export public class Cls1 {
								public field: string;
							}
							export public class Cls2 {
								public field: number;
							}
						""",
				"Other2", """
							export { Cls1 as Cls } from "Other1";
						""",
				"Other3", """
							export { Cls } from "Other2";
						""",
				"Main", """
							import {Cls} from "Other3";

							let x: string = new Cls().field;
						"""));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other2", Pair.of("Cls1 as Cls", "Cls2 as Cls"));
		joinServerRequests();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:32], number is not a subtype of string.)")));

		changeNonOpenedFile("Other2", Pair.of("Cls2 as Cls", "Cls1 as Cls"));
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	public void testAcrossProjects_simple() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject1", Map.of(
						"Other1", """
									export public class Cls {
										public field: string;
									}
								"""),
				"OtherProject2", Map.of(
						"Other2", """
									export { Cls } from "Other1";
								""",
						CFG_DEPENDENCIES, """
									OtherProject1
								"""),
				"OtherProject3", Map.of(
						"Other3", """
									export { Cls } from "Other2";
								""",
						CFG_DEPENDENCIES, """
									OtherProject2
								"""),
				"MainProject", Map.of(
						"Main", """
									import {Cls} from "Other3";

									let x: string = new Cls().field;
								""",
						CFG_DEPENDENCIES, """
									OtherProject3
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:32], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	public void testAcrossProjects_separateExports() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject1", Map.of(
						"Other1", """
									export public class Cls {
										public field: string;
									}
								"""),
				"OtherProject2", Map.of(
						"Other2", """
									import { Cls } from "Other1";
									export { Cls };
								""",
						CFG_DEPENDENCIES, """
									OtherProject1
								"""),
				"OtherProject3", Map.of(
						"Other3", """
									import { Cls } from "Other2";
									export { Cls };
								""",
						CFG_DEPENDENCIES, """
									OtherProject2
								"""),
				"MainProject", Map.of(
						"Main", """
									import {Cls} from "Other3";

									let x: string = new Cls().field;
								""",
						CFG_DEPENDENCIES, """
									OtherProject3
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:32], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	public void testAcrossProjects_withAlias() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject1", Map.of(
						"Other1", """
									export public class Cls {
										public field: string;
									}
								"""),
				"OtherProject2", Map.of(
						"Other2", """
									import { Cls as MyCls } from "Other1";
									export { MyCls as Cls2 };
								""",
						CFG_DEPENDENCIES, """
									OtherProject1
								"""),
				"OtherProject3", Map.of(
						"Other3", """
									import { Cls2 as MyCls } from "Other2";
									export { MyCls as Cls3 };
								""",
						CFG_DEPENDENCIES, """
									OtherProject2
								"""),
				"MainProject", Map.of(
						"Main", """
									import { Cls3 as MyCls } from "Other3";

									let x: string = new MyCls().field;
								""",
						CFG_DEPENDENCIES, """
									OtherProject3
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:34], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	public void testAcrossProjects_moduleReexport() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject1", Map.of(
						"Other1", """
									export public class Cls {
										public field: string;
									}
								"""),
				"OtherProject2", Map.of(
						"Other2", """
									export * from "Other1";
								""",
						CFG_DEPENDENCIES, """
									OtherProject1
								"""),
				"OtherProject3", Map.of(
						"Other3", """
									export * from "Other2";
								""",
						CFG_DEPENDENCIES, """
									OtherProject2
								"""),
				"MainProject", Map.of(
						"Main", """
									import {Cls} from "Other3";

									let x: string = new Cls().field;
								""",
						CFG_DEPENDENCIES, """
									OtherProject3
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:32], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	public void testAcrossProjects_moduleReexportWithAlias() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject1", Map.of(
						"Other1", """
									export public class Cls {
										public field: string;
									}
								"""),
				"OtherProject2", Map.of(
						"Other2", """
									export * as N2 from "Other1";
								""",
						CFG_DEPENDENCIES, """
									OtherProject1
								"""),
				"OtherProject3", Map.of(
						"Other3", """
									export * as N3 from "Other2";
								""",
						CFG_DEPENDENCIES, """
									OtherProject2
								"""),
				"MainProject", Map.of(
						"Main", """
									import {N3} from "Other3";

									let x: string = new N3.N2.Cls().field;
								""",
						CFG_DEPENDENCIES, """
									OtherProject3
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:38], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	public void testAcrossProjects_changeReexportedElement() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject1", Map.of(
						"Other1", """
									export public class Cls1 {
										public field: string;
									}
									export public class Cls2 {
										public field: number;
									}
								"""),
				"OtherProject2", Map.of(
						"Other2", """
									export { Cls1 as Cls } from "Other1";
								""",
						CFG_DEPENDENCIES, """
									OtherProject1
								"""),
				"OtherProject3", Map.of(
						"Other3", """
									export { Cls } from "Other2";
								""",
						CFG_DEPENDENCIES, """
									OtherProject2
								"""),
				"MainProject", Map.of(
						"Main", """
									import {Cls} from "Other3";

									let x: string = new Cls().field;
								""",
						CFG_DEPENDENCIES, """
									OtherProject3
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other2", Pair.of("Cls1 as Cls", "Cls2 as Cls"));
		joinServerRequests();

		assertIssues2(
				Pair.of("Main", List.of(
						"(Error, [2:17 - 2:32], number is not a subtype of string.)")));

		changeNonOpenedFile("Other2", Pair.of("Cls2 as Cls", "Cls1 as Cls"));
		joinServerRequests();

		assertNoIssues();
	}
}
