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
package org.eclipse.n4js.ide.tests.builder

import java.util.Set
import org.eclipse.n4js.validation.IssueCodes
import org.junit.Test

/**
 * Testing incremental builds of re-export chains.
 */
class IncrementalBuilderReexportTest extends AbstractIncrementalBuilderTest {

	override protected Set<String> getIgnoredIssueCodes() {
		(super.getIgnoredIssueCodes() + #[
			// note: this test is testing internal infrastructure for a feature not available in N4JS[D] (only in .d.ts)
			IssueCodes.UNSUPPORTED
		]).toSet;
	}

	@Test
	def void testSingleProject_simple() {
		testWorkspaceManager.createTestProjectOnDisk(
			"Other1" -> '''
				export public class Cls {
					public field: string;
				}
			''',
			"Other2" -> '''
				export { Cls } from "Other1";
			''',
			"Other3" -> '''
				export { Cls } from "Other2";
			''',
			"Main" -> '''
				import {Cls} from "Other3";

				let x: string = new Cls().field;
			'''
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:31], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	def void testSingleProject_separateExports() {
		testWorkspaceManager.createTestProjectOnDisk(
			"Other1" -> '''
				export public class Cls {
					public field: string;
				}
			''',
			"Other2" -> '''
				import { Cls } from "Other1";
				export { Cls };
			''',
			"Other3" -> '''
				import { Cls } from "Other2";
				export { Cls };
			''',
			"Main" -> '''
				import {Cls} from "Other3";

				let x: string = new Cls().field;
			'''
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:31], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	def void testSingleProject_withAlias() {
		testWorkspaceManager.createTestProjectOnDisk(
			"Other1" -> '''
				export public class Cls {
					public field: string;
				}
			''',
			"Other2" -> '''
				import { Cls as MyCls } from "Other1";
				export { MyCls as Cls2 };
			''',
			"Other3" -> '''
				import { Cls2 as MyCls } from "Other2";
				export { MyCls as Cls3 };
			''',
			"Main" -> '''
				import { Cls3 as MyCls } from "Other3";

				let x: string = new MyCls().field;
			'''
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:33], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	def void testSingleProject_moduleReexport() {
		testWorkspaceManager.createTestProjectOnDisk(
			"Other1" -> '''
				export public class Cls {
					public field: string;
				}
			''',
			"Other2" -> '''
				export * from "Other1";
			''',
			"Other3" -> '''
				export * from "Other2";
			''',
			"Main" -> '''
				import {Cls} from "Other3";

				let x: string = new Cls().field;
			'''
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:31], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	def void testSingleProject_moduleReexportWithAlias() {
		testWorkspaceManager.createTestProjectOnDisk(
			"Other1" -> '''
				export public class Cls {
					public field: string;
				}
			''',
			"Other2" -> '''
				export * as N2 from "Other1";
			''',
			"Other3" -> '''
				export * as N3 from "Other2";
			''',
			"Main" -> '''
				import {N3} from "Other3";

				let x: string = new N3.N2.Cls().field;
			'''
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:37], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	def void testSingleProject_changeReexportedElement() {
		testWorkspaceManager.createTestProjectOnDisk(
			"Other1" -> '''
				export public class Cls1 {
					public field: string;
				}
				export public class Cls2 {
					public field: number;
				}
			''',
			"Other2" -> '''
				export { Cls1 as Cls } from "Other1";
			''',
			"Other3" -> '''
				export { Cls } from "Other2";
			''',
			"Main" -> '''
				import {Cls} from "Other3";

				let x: string = new Cls().field;
			'''
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other2", "Cls1 as Cls" -> "Cls2 as Cls");
		joinServerRequests();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:31], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other2", "Cls2 as Cls" -> "Cls1 as Cls");
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	def void testAcrossProjects_simple() {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject1" -> #[
				"Other1" -> '''
					export public class Cls {
						public field: string;
					}
				'''
			],
			"OtherProject2" -> #[
				"Other2" -> '''
					export { Cls } from "Other1";
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject1
				'''
			],
			"OtherProject3" -> #[
				"Other3" -> '''
					export { Cls } from "Other2";
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject2
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {Cls} from "Other3";

					let x: string = new Cls().field;
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject3
				'''
			]
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:31], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	def void testAcrossProjects_separateExports() {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject1" -> #[
				"Other1" -> '''
					export public class Cls {
						public field: string;
					}
				'''
			],
			"OtherProject2" -> #[
				"Other2" -> '''
					import { Cls } from "Other1";
					export { Cls };
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject1
				'''
			],
			"OtherProject3" -> #[
				"Other3" -> '''
					import { Cls } from "Other2";
					export { Cls };
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject2
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {Cls} from "Other3";

					let x: string = new Cls().field;
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject3
				'''
			]
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:31], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	def void testAcrossProjects_withAlias() {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject1" -> #[
				"Other1" -> '''
					export public class Cls {
						public field: string;
					}
				'''
			],
			"OtherProject2" -> #[
				"Other2" -> '''
					import { Cls as MyCls } from "Other1";
					export { MyCls as Cls2 };
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject1
				'''
			],
			"OtherProject3" -> #[
				"Other3" -> '''
					import { Cls2 as MyCls } from "Other2";
					export { MyCls as Cls3 };
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject2
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import { Cls3 as MyCls } from "Other3";

					let x: string = new MyCls().field;
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject3
				'''
			]
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:33], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	def void testAcrossProjects_moduleReexport() {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject1" -> #[
				"Other1" -> '''
					export public class Cls {
						public field: string;
					}
				'''
			],
			"OtherProject2" -> #[
				"Other2" -> '''
					export * from "Other1";
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject1
				'''
			],
			"OtherProject3" -> #[
				"Other3" -> '''
					export * from "Other2";
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject2
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {Cls} from "Other3";

					let x: string = new Cls().field;
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject3
				'''
			]
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:31], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	def void testAcrossProjects_moduleReexportWithAlias() {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject1" -> #[
				"Other1" -> '''
					export public class Cls {
						public field: string;
					}
				'''
			],
			"OtherProject2" -> #[
				"Other2" -> '''
					export * as N2 from "Other1";
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject1
				'''
			],
			"OtherProject3" -> #[
				"Other3" -> '''
					export * as N3 from "Other2";
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject2
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {N3} from "Other3";

					let x: string = new N3.N2.Cls().field;
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject3
				'''
			]
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:37], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertNoIssues();
	}

	@Test
	def void testAcrossProjects_changeReexportedElement() {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject1" -> #[
				"Other1" -> '''
					export public class Cls1 {
						public field: string;
					}
					export public class Cls2 {
						public field: number;
					}
				'''
			],
			"OtherProject2" -> #[
				"Other2" -> '''
					export { Cls1 as Cls } from "Other1";
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject1
				'''
			],
			"OtherProject3" -> #[
				"Other3" -> '''
					export { Cls } from "Other2";
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject2
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {Cls} from "Other3";

					let x: string = new Cls().field;
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject3
				'''
			]
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other2", "Cls1 as Cls" -> "Cls2 as Cls");
		joinServerRequests();

		assertIssues2(
			"Main" -> #[
				"(Error, [2:16 - 2:31], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other2", "Cls2 as Cls" -> "Cls1 as Cls");
		joinServerRequests();

		assertNoIssues();
	}
}
