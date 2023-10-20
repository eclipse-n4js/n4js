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
package org.eclipse.n4js.tests.scoping;

import static org.eclipse.n4js.validation.IssueCodes.UNSUPPORTED;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sort;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSRuntimeModule;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tests.scoping.ExportDefinitionInCyclicResourcesTest.N4JSInjectorProviderWithoutCycleDetection;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Tests certain corner cases of {@link ExportDefinition}s.
 */
@InjectWith(N4JSInjectorProviderWithoutCycleDetection.class)
public class ExportDefinitionInCyclicResourcesTest extends AbstractN4JSTest {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	/**
	 * Disable Xtext's default cycle detection during lazy-linking proxy resolution to ensure the tests in this class
	 * will fail if our scoping does not gracefully handle the cycles (i.e. if it runs into a StackOverflowError).
	 */
	public static class N4JSInjectorProviderWithoutCycleDetection extends N4JSInjectorProvider {
		@Override
		protected Module createRuntimeModule() {
			return new N4JSRuntimeModule() {
				@Override
				public void configure(Binder binder) {
					super.configure(binder);
					binder.bindConstant()
							.annotatedWith(Names.named(LazyLinkingResource.CYCLIC_LINKING_DECTECTION_COUNTER_LIMIT))
							.to(Integer.MAX_VALUE);
				}
			};
		}
	}

	@Test
	public void testDirectExports() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { ClsB } from "other2";

				export class ClsA { fieldA: number; }
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { ClsA } from "other1";

				export class ClsB { fieldB: number; }
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		Script scriptUsage = testHelper.parseInFile("""
				import { ClsA } from "other1";
				import { ClsB } from "other2";

				let v01: boolean = new ClsA().fieldA; // error
				let v02: number = new ClsA().fieldA;

				let v11: boolean = new ClsB().fieldB; // error
				let v12: number = new ClsB().fieldB;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"));
	}

	@Test
	public void testSeparateExports() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { ClsB } from "other2";

				public class ClsA { fieldA: number; }
				export { ClsA }
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { ClsA } from "other1";

				public class ClsB { fieldB: number; }
				export { ClsB }
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		Script scriptUsage = testHelper.parseInFile("""
				import { ClsA } from "other1";
				import { ClsB } from "other2";

				let v01: boolean = new ClsA().fieldA;
				let v02: number = new ClsA().fieldA;

				let v11: boolean = new ClsB().fieldB;
				let v12: number = new ClsB().fieldB;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"));
	}

	@Test
	public void testSeparateExports_withImportedType() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { ClsValueB } from "other2";

				export public class ClsValueA {}

				public class ClsA { fieldA: ClsValueB; }
				export { ClsA }
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { ClsValueA } from "other1";

				export public class ClsValueB {}

				public class ClsB { fieldB: ClsValueA; }
				export { ClsB }
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		Script scriptUsage = testHelper.parseInFile("""
				import { ClsA, ClsValueA } from "other1";
				import { ClsB, ClsValueB } from "other2";

				let v01: boolean = new ClsA().fieldA;
				let v02: ClsValueB = new ClsA().fieldA;

				let v11: boolean = new ClsB().fieldB;
				let v12: ClsValueA = new ClsB().fieldB;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:ClsValueB is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
				"ERROR:ClsValueA is not a subtype of boolean. (usage.n4js line : 7 column : 20)"));
	}

	@Test
	public void testReexports_separateImportExport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { ClsB } from "other2";

				export public class ClsA { fieldA: number; }

				export { ClsB as ClsBViaOther1 }
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { ClsA } from "other1";

				export public class ClsB { fieldB: number; }

				export { ClsA as ClsAViaOther2 }
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		Script scriptUsage = testHelper.parseInFile("""
				import { ClsBViaOther1 } from "other1";
				import { ClsAViaOther2 } from "other2";

				let v01: boolean = new ClsBViaOther1().fieldB;
				let v02: number = new ClsBViaOther1().fieldB;

				let v11: boolean = new ClsAViaOther2().fieldA;
				let v12: number = new ClsAViaOther2().fieldA;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"));
	}

	@Test
	public void testReexports_mergedImportExport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				export public class ClsA { fieldA: number; }

				export { ClsB as ClsBViaOther1 } from "other2"
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				export public class ClsB { fieldB: number; }

				export { ClsA as ClsAViaOther2 } from "other1"
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		Script scriptUsage = testHelper.parseInFile("""
				import { ClsBViaOther1 } from "other1";
				import { ClsAViaOther2 } from "other2";

				let v01: boolean = new ClsBViaOther1().fieldB;
				let v02: number = new ClsBViaOther1().fieldB;

				let v11: boolean = new ClsAViaOther2().fieldA;
				let v12: number = new ClsAViaOther2().fieldA;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"));
	}

	@Test
	public void testReexportChain() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { ClsUnrelated2 } from "other2";
				export public class ClsUnrelated1 {}

				export { Cls } from "other2"
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { ClsUnrelated3 } from "other3";
				export public class ClsUnrelated2 {}

				export { Cls } from "other3"
				""", "other2.n4js", rs);

		Script script3 = testHelper.parseInFile("""
				import { ClsUnrelated1 } from "other1" // <-- closing the cycle;
				export public class ClsUnrelated3 {}

				public class Cls { field: number; }
				export { Cls }
				""", "other3.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);
		assertNoErrors(script3);

		Script scriptUsage = testHelper.parseInFile("""
				import { Cls as ClsViaOther1 } from "other1";
				import { Cls as ClsViaOther3 } from "other3";

				let v01: boolean = new ClsViaOther1().field;
				let v02: number = new ClsViaOther1().field;

				let v11: boolean = new ClsViaOther3().field;
				let v12: number = new ClsViaOther3().field;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"));
	}

	@Test
	public void testReexportChain_reverse() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { ClsUnrelated2 } from "other2";
				export public class ClsUnrelated1 {}

				public class Cls { field: number; }
				export { Cls }
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { ClsUnrelated3 } from "other3";
				export public class ClsUnrelated2 {}

				export { Cls } from "other1"
				""", "other2.n4js", rs);

		Script script3 = testHelper.parseInFile("""
				import { ClsUnrelated1 } from "other1" // <-- closing the cycle;
				export public class ClsUnrelated3 {}

				export { Cls } from "other2"
				""", "other3.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);
		assertNoErrors(script3);

		Script scriptUsage = testHelper.parseInFile("""
				import { Cls as ClsViaOther1 } from "other1";
				import { Cls as ClsViaOther3 } from "other3";

				let v01: boolean = new ClsViaOther1().field;
				let v02: number = new ClsViaOther1().field;

				let v11: boolean = new ClsViaOther3().field;
				let v12: number = new ClsViaOther3().field;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"));
	}

	@Test
	public void testReexportLoop_minimal_separateImportExport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { Cls2 } from "other2";
				export public class Cls1 { field: number; }
				export { Cls2 as Cls3 }
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { Cls1 } from "other1";
				export { Cls1 as Cls2 }
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		Script scriptUsage = testHelper.parseInFile("""
				import { Cls3 } from "other1";

				let v01: boolean = new Cls3().field; // error
				let v02: number = new Cls3().field;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 3 column : 20)"));
	}

	@Test
	public void testReexportLoop_minimal_mergedImportExport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				export public class Cls1 { field: number; }
				export { Cls2 as Cls3 } from "other2"
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				export { Cls1 as Cls2 } from "other1"
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		Script scriptUsage = testHelper.parseInFile("""
				import { Cls3 } from "other1";

				let v01: boolean = new Cls3().field; // error
				let v02: number = new Cls3().field;
				""", "usage.n4js", rs);

		assertErrors(scriptUsage, List.of(
				"ERROR:number is not a subtype of boolean. (usage.n4js line : 3 column : 20)"));
	}

	@Test
	public void testReexportLoop_minimalUnsupported_separateImportExport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { Cls2 } from "other2";
				export public class Cls1 {}
				export { Cls2 as Cls3 }
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { Cls1 } from "other1";
				import { Cls3 } from "other1";
				export { Cls1 as Cls2 }
				export { Cls3 }
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		// TODO GH-2338 not supported yet
		assertParseErrors(script2, List.of(
				"line 4, column 10: Couldn't resolve reference to IdentifiableElement 'Cls3'."));
	}

	@Test
	public void testReexportLoop_minimalUnsupported_mergedImportExport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				export public class Cls1 {}
				export { Cls2 as Cls3 } from "other2"
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				export { Cls1 as Cls2 } from "other1"
				export { Cls3 } from "other1"
				""", "other2.n4js", rs);

		assertNoErrors(script1);
		// TODO GH-2338 not supported yet
		assertParseErrors(script2, List.of(
				"line 2, column 10: Couldn't resolve reference to IdentifiableElement 'Cls3'."));
	}

	@Test
	public void testReexportLoop_crazy_separateImportExport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { ClsUnrelated2 } from "other2";
				import { Cls2 } from "other2";
				import { Cls5 } from "other2";
				import { Cls8 } from "other2";

				export public class ClsUnrelated1 {}

				export { Cls2 as Cls3 }
				export { Cls5 as Cls6 }
				export { Cls8 as Cls9 }
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { ClsUnrelated3 } from "other3";
				import { Cls1 } from "other3";
				import { Cls4 } from "other3";
				import { Cls7 } from "other3";

				export public class ClsUnrelated2 {}

				export { Cls1 as Cls2 }
				export { Cls4 as Cls5 }
				export { Cls7 as Cls8 }
				""", "other2.n4js", rs);

		Script script3 = testHelper.parseInFile("""
				import { ClsUnrelated1 } from "other1" // <-- closing the cycle;
				import { Cls3 } from "other1";
				import { Cls6 } from "other1";

				export public class ClsUnrelated3 {}

				public class Cls { field: number; }
				export { Cls as Cls1 }

				export { Cls3 as Cls4 }
				export { Cls6 as Cls7 }
				""", "other3.n4js", rs);

		// TODO GH-2338 not supported yet
		assertParseErrors(script1, List.of(
				"line 9, column 10: Couldn't resolve reference to IdentifiableElement 'Cls5'.",
				"line 10, column 10: Couldn't resolve reference to IdentifiableElement 'Cls8'."));
		assertParseErrors(script2, List.of(
				"line 9, column 10: Couldn't resolve reference to IdentifiableElement 'Cls4'.",
				"line 10, column 10: Couldn't resolve reference to IdentifiableElement 'Cls7'."));
		assertParseErrors(script3, List.of(
				"line 10, column 10: Couldn't resolve reference to IdentifiableElement 'Cls3'.",
				"line 11, column 10: Couldn't resolve reference to IdentifiableElement 'Cls6'."));
		// instead, we would like to use this:
		// assertNoErrors(script1);
		// assertNoErrors(script2);
		// assertNoErrors(script3);
		//
		// Script scriptUsage = testHelper.parseInFile("""
		// import { Cls9 as ClsViaOther1 } from "other1";
		// import { Cls1 as ClsViaOther3 } from "other3";
		//
		// let v01: boolean = new ClsViaOther1().field;
		// let v02: number = new ClsViaOther1().field;
		//
		// let v11: boolean = new ClsViaOther3().field;
		// let v12: number = new ClsViaOther3().field;
		// """, "usage.n4js", rs);
		//
		// assertErrors(scriptUsage, List.of(
		// "ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
		// "ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		// ));
	}

	@Test
	public void testReexportLoop_crazy_mergedImportExport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script script1 = testHelper.parseInFile("""
				import { ClsUnrelated2 } from "other2";
				export public class ClsUnrelated1 {}

				export { Cls2 as Cls3 } from "other2"
				export { Cls5 as Cls6 } from "other2"
				export { Cls8 as Cls9 } from "other2"
				""", "other1.n4js", rs);

		Script script2 = testHelper.parseInFile("""
				import { ClsUnrelated3 } from "other3";
				export public class ClsUnrelated2 {}

				export { Cls1 as Cls2 } from "other3"
				export { Cls4 as Cls5 } from "other3"
				export { Cls7 as Cls8 } from "other3"
				""", "other2.n4js", rs);

		Script script3 = testHelper.parseInFile("""
				import { ClsUnrelated1 } from "other1" // <-- closing the cycle;
				export public class ClsUnrelated3 {}

				public class Cls { field: number; }
				export { Cls as Cls1 }

				export { Cls3 as Cls4 } from "other1"
				export { Cls6 as Cls7 } from "other1"
				""", "other3.n4js", rs);

		// TODO GH-2338 not supported yet
		assertParseErrors(script1, List.of(
				"line 5, column 10: Couldn't resolve reference to IdentifiableElement 'Cls5'.",
				"line 6, column 10: Couldn't resolve reference to IdentifiableElement 'Cls8'."));
		assertParseErrors(script2, List.of(
				"line 5, column 10: Couldn't resolve reference to IdentifiableElement 'Cls4'.",
				"line 6, column 10: Couldn't resolve reference to IdentifiableElement 'Cls7'."));
		assertParseErrors(script3, List.of(
				"line 7, column 10: Couldn't resolve reference to IdentifiableElement 'Cls3'.",
				"line 8, column 10: Couldn't resolve reference to IdentifiableElement 'Cls6'."));
		// instead, we would like to use this:
		// assertNoErrors(script1);
		// assertNoErrors(script2);
		// assertNoErrors(script3);
		//
		// Script scriptUsage = testHelper.parseInFile("""
		// import { Cls9 as ClsViaOther1 } from "other1";
		// import { Cls1 as ClsViaOther3 } from "other3";
		//
		// let v01: boolean = new ClsViaOther1().field;
		// let v02: number = new ClsViaOther1().field;
		//
		// let v11: boolean = new ClsViaOther3().field;
		// let v12: number = new ClsViaOther3().field;
		// """, "usage.n4js", rs);
		//
		// assertErrors(scriptUsage, List.of(
		// "ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
		// "ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		// ));
	}

	private void assertNoErrors(Script script) {
		assertErrors(script, List.of());
	}

	private void assertErrors(Script script, List<String> expectedErrors) {
		N4JSResource res = (N4JSResource) script.eResource();
		res.performPostProcessing();
		parserHelper.assertNoParseErrors(script);
		validationTestHelper.assertErrorsExcept(script, new String[] { UNSUPPORTED },
				expectedErrors.toArray(new String[0]));
	}

	private void assertParseErrors(Script script, List<String> expectedErrors) {
		N4JSResource res = (N4JSResource) script.eResource();
		res.performPostProcessing();
		List<String> actualErrors = sort(toList(map(script.eResource().getErrors(),
				err -> "line %d, column %d: %s".formatted(err.getLine(), err.getColumn(), err.getMessage()))));
		List<String> expectedErrorsSorted = sort(expectedErrors);
		assertEquals("actual parse errors did not match expected parse errors", expectedErrorsSorted, actualErrors);
	}
}
