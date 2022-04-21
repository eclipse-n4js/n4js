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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Binder
import com.google.inject.Inject
import com.google.inject.Module
import com.google.inject.Provider
import com.google.inject.name.Names
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.N4JSRuntimeModule
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.ExportDefinition
import org.eclipse.xtext.linking.lazy.LazyLinkingResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.junit.Assert
import org.junit.Test

import static org.eclipse.n4js.validation.IssueCodes.UNSUPPORTED

/**
 * Tests certain corner cases of {@link ExportDefinition}s.
 */
@InjectWith(N4JSInjectorProviderWithoutCycleDetection)
class ExportDefinitionInCyclicResourcesTest extends AbstractN4JSTest {

	@Inject private Provider<XtextResourceSet> resourceSetProvider


	/**
	 * Disable Xtext's default cycle detection during lazy-linking proxy resolution
	 * to ensure the tests in this class will fail if our scoping does not gracefully
	 * handle the cycles (i.e. if it runs into a StackOverflowError).
	 */
	public static class N4JSInjectorProviderWithoutCycleDetection extends N4JSInjectorProvider {
		override protected Module createRuntimeModule() {
			return new N4JSRuntimeModule() {
				override configure(Binder binder) {
					super.configure(binder);
					binder.bindConstant()
							.annotatedWith(Names.named(LazyLinkingResource.CYCLIC_LINKING_DECTECTION_COUNTER_LIMIT))
							.to(Integer.MAX_VALUE);
				}
			};
		}
	}


	@Test
	def void testDirectExports() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { ClsB } from "other2"

			export class ClsA { fieldA: number; }
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { ClsA } from "other1"

			export class ClsB { fieldB: number; }
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		val scriptUsage = '''
			import { ClsA } from "other1"
			import { ClsB } from "other2"

			let v01: boolean = new ClsA().fieldA; // error
			let v02: number = new ClsA().fieldA;

			let v11: boolean = new ClsB().fieldB; // error
			let v12: number = new ClsB().fieldB;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		]);
	}

	@Test
	def void testSeparateExports() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { ClsB } from "other2"

			public class ClsA { fieldA: number; }
			export { ClsA }
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { ClsA } from "other1"

			public class ClsB { fieldB: number; }
			export { ClsB }
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		val scriptUsage = '''
			import { ClsA } from "other1"
			import { ClsB } from "other2"

			let v01: boolean = new ClsA().fieldA;
			let v02: number = new ClsA().fieldA;

			let v11: boolean = new ClsB().fieldB;
			let v12: number = new ClsB().fieldB;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		]);
	}

	@Test
	def void testSeparateExports_withImportedType() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { ClsValueB } from "other2"

			export public class ClsValueA {}

			public class ClsA { fieldA: ClsValueB; }
			export { ClsA }
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { ClsValueA } from "other1"

			export public class ClsValueB {}

			public class ClsB { fieldB: ClsValueA; }
			export { ClsB }
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		val scriptUsage = '''
			import { ClsA, ClsValueA } from "other1"
			import { ClsB, ClsValueB } from "other2"

			let v01: boolean = new ClsA().fieldA;
			let v02: ClsValueB = new ClsA().fieldA;

			let v11: boolean = new ClsB().fieldB;
			let v12: ClsValueA = new ClsB().fieldB;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:ClsValueB is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
			"ERROR:ClsValueA is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		]);
	}

	@Test
	def void testReexports_separateImportExport() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { ClsB } from "other2"

			export public class ClsA { fieldA: number; }

			export { ClsB as ClsBViaOther1 }
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { ClsA } from "other1"

			export public class ClsB { fieldB: number; }

			export { ClsA as ClsAViaOther2 }
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		val scriptUsage = '''
			import { ClsBViaOther1 } from "other1"
			import { ClsAViaOther2 } from "other2"

			let v01: boolean = new ClsBViaOther1().fieldB;
			let v02: number = new ClsBViaOther1().fieldB;

			let v11: boolean = new ClsAViaOther2().fieldA;
			let v12: number = new ClsAViaOther2().fieldA;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		]);
	}

	@Test
	def void testReexports_mergedImportExport() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			export public class ClsA { fieldA: number; }

			export { ClsB as ClsBViaOther1 } from "other2"
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			export public class ClsB { fieldB: number; }

			export { ClsA as ClsAViaOther2 } from "other1"
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		val scriptUsage = '''
			import { ClsBViaOther1 } from "other1"
			import { ClsAViaOther2 } from "other2"

			let v01: boolean = new ClsBViaOther1().fieldB;
			let v02: number = new ClsBViaOther1().fieldB;

			let v11: boolean = new ClsAViaOther2().fieldA;
			let v12: number = new ClsAViaOther2().fieldA;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		]);
	}

	@Test
	def void testReexportChain() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { ClsUnrelated2 } from "other2"
			export public class ClsUnrelated1 {}

			export { Cls } from "other2"
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { ClsUnrelated3 } from "other3"
			export public class ClsUnrelated2 {}

			export { Cls } from "other3"
		'''.parseInFile("other2.n4js", rs);

		val script3 = '''
			import { ClsUnrelated1 } from "other1" // <-- closing the cycle
			export public class ClsUnrelated3 {}
			
			public class Cls { field: number; }
			export { Cls }
		'''.parseInFile("other3.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);
		assertNoErrors(script3);

		val scriptUsage = '''
			import { Cls as ClsViaOther1 } from "other1"
			import { Cls as ClsViaOther3 } from "other3"

			let v01: boolean = new ClsViaOther1().field;
			let v02: number = new ClsViaOther1().field;

			let v11: boolean = new ClsViaOther3().field;
			let v12: number = new ClsViaOther3().field;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		]);
	}

	@Test
	def void testReexportChain_reverse() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { ClsUnrelated2 } from "other2"
			export public class ClsUnrelated1 {}

			public class Cls { field: number; }
			export { Cls }
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { ClsUnrelated3 } from "other3"
			export public class ClsUnrelated2 {}

			export { Cls } from "other1"
		'''.parseInFile("other2.n4js", rs);

		val script3 = '''
			import { ClsUnrelated1 } from "other1" // <-- closing the cycle
			export public class ClsUnrelated3 {}
			
			export { Cls } from "other2"
		'''.parseInFile("other3.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);
		assertNoErrors(script3);

		val scriptUsage = '''
			import { Cls as ClsViaOther1 } from "other1"
			import { Cls as ClsViaOther3 } from "other3"

			let v01: boolean = new ClsViaOther1().field;
			let v02: number = new ClsViaOther1().field;

			let v11: boolean = new ClsViaOther3().field;
			let v12: number = new ClsViaOther3().field;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
		]);
	}

	@Test
	def void testReexportLoop_minimal_separateImportExport() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { Cls2 } from "other2"
			export public class Cls1 { field: number; }
			export { Cls2 as Cls3 }
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { Cls1 } from "other1"
			export { Cls1 as Cls2 }
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		val scriptUsage = '''
			import { Cls3 } from "other1"

			let v01: boolean = new Cls3().field; // error
			let v02: number = new Cls3().field;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 3 column : 20)"
		]);
	}

	@Test
	def void testReexportLoop_minimal_mergedImportExport() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			export public class Cls1 { field: number; }
			export { Cls2 as Cls3 } from "other2"
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			export { Cls1 as Cls2 } from "other1"
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
		assertNoErrors(script2);

		val scriptUsage = '''
			import { Cls3 } from "other1"

			let v01: boolean = new Cls3().field; // error
			let v02: number = new Cls3().field;
		'''.parseInFile("usage.n4js", rs);

		assertErrors(scriptUsage, #[
			"ERROR:number is not a subtype of boolean. (usage.n4js line : 3 column : 20)"
		]);
	}

	@Test
	def void testReexportLoop_minimalUnsupported_separateImportExport() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { Cls2 } from "other2"
			export public class Cls1 {}
			export { Cls2 as Cls3 }
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { Cls1 } from "other1"
			import { Cls3 } from "other1"
			export { Cls1 as Cls2 }
			export { Cls3 }
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
// TODO GH-2338 not supported yet
		assertParseErrors(script2, #[
			"line 4, column 10: Couldn't resolve reference to IdentifiableElement 'Cls3'."
		]);
	}

	@Test
	def void testReexportLoop_minimalUnsupported_mergedImportExport() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			export public class Cls1 {}
			export { Cls2 as Cls3 } from "other2"
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			export { Cls1 as Cls2 } from "other1"
			export { Cls3 } from "other1"
		'''.parseInFile("other2.n4js", rs);

		assertNoErrors(script1);
// TODO GH-2338 not supported yet
		assertParseErrors(script2, #[
			"line 2, column 10: Couldn't resolve reference to IdentifiableElement 'Cls3'."
		]);
	}

	@Test
	def void testReexportLoop_crazy_separateImportExport() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { ClsUnrelated2 } from "other2"
			import { Cls2 } from "other2"
			import { Cls5 } from "other2"
			import { Cls8 } from "other2"

			export public class ClsUnrelated1 {}

			export { Cls2 as Cls3 }
			export { Cls5 as Cls6 }
			export { Cls8 as Cls9 }
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { ClsUnrelated3 } from "other3"
			import { Cls1 } from "other3"
			import { Cls4 } from "other3"
			import { Cls7 } from "other3"

			export public class ClsUnrelated2 {}

			export { Cls1 as Cls2 }
			export { Cls4 as Cls5 }
			export { Cls7 as Cls8 }
		'''.parseInFile("other2.n4js", rs);

		val script3 = '''
			import { ClsUnrelated1 } from "other1" // <-- closing the cycle
			import { Cls3 } from "other1"
			import { Cls6 } from "other1"

			export public class ClsUnrelated3 {}

			public class Cls { field: number; }
			export { Cls as Cls1 }

			export { Cls3 as Cls4 }
			export { Cls6 as Cls7 }
		'''.parseInFile("other3.n4js", rs);

// TODO GH-2338 not supported yet
		assertParseErrors(script1, #[
			"line 9, column 10: Couldn't resolve reference to IdentifiableElement 'Cls5'.",
			"line 10, column 10: Couldn't resolve reference to IdentifiableElement 'Cls8'."
		]);
		assertParseErrors(script2, #[
			"line 9, column 10: Couldn't resolve reference to IdentifiableElement 'Cls4'.",
			"line 10, column 10: Couldn't resolve reference to IdentifiableElement 'Cls7'."
		]);
		assertParseErrors(script3, #[
			"line 10, column 10: Couldn't resolve reference to IdentifiableElement 'Cls3'.",
			"line 11, column 10: Couldn't resolve reference to IdentifiableElement 'Cls6'."
		]);
// instead, we would like to use this:
//		assertNoErrors(script1);
//		assertNoErrors(script2);
//		assertNoErrors(script3);
//
//		val scriptUsage = '''
//			import { Cls9 as ClsViaOther1 } from "other1"
//			import { Cls1 as ClsViaOther3 } from "other3"
//
//			let v01: boolean = new ClsViaOther1().field;
//			let v02: number = new ClsViaOther1().field;
//
//			let v11: boolean = new ClsViaOther3().field;
//			let v12: number = new ClsViaOther3().field;
//		'''.parseInFile("usage.n4js", rs);
//
//		assertErrors(scriptUsage, #[
//			"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
//			"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
//		]);
	}

	@Test
	def void testReexportLoop_crazy_mergedImportExport() {
		val rs = resourceSetProvider.get();

		val script1 = '''
			import { ClsUnrelated2 } from "other2"
			export public class ClsUnrelated1 {}

			export { Cls2 as Cls3 } from "other2"
			export { Cls5 as Cls6 } from "other2"
			export { Cls8 as Cls9 } from "other2"
		'''.parseInFile("other1.n4js", rs);

		val script2 = '''
			import { ClsUnrelated3 } from "other3"
			export public class ClsUnrelated2 {}

			export { Cls1 as Cls2 } from "other3"
			export { Cls4 as Cls5 } from "other3"
			export { Cls7 as Cls8 } from "other3"
		'''.parseInFile("other2.n4js", rs);

		val script3 = '''
			import { ClsUnrelated1 } from "other1" // <-- closing the cycle
			export public class ClsUnrelated3 {}

			public class Cls { field: number; }
			export { Cls as Cls1 }

			export { Cls3 as Cls4 } from "other1"
			export { Cls6 as Cls7 } from "other1"
		'''.parseInFile("other3.n4js", rs);

// TODO GH-2338 not supported yet
		assertParseErrors(script1, #[
			"line 5, column 10: Couldn't resolve reference to IdentifiableElement 'Cls5'.",
			"line 6, column 10: Couldn't resolve reference to IdentifiableElement 'Cls8'."
		]);
		assertParseErrors(script2, #[
			"line 5, column 10: Couldn't resolve reference to IdentifiableElement 'Cls4'.",
			"line 6, column 10: Couldn't resolve reference to IdentifiableElement 'Cls7'."
		]);
		assertParseErrors(script3, #[
			"line 7, column 10: Couldn't resolve reference to IdentifiableElement 'Cls3'.",
			"line 8, column 10: Couldn't resolve reference to IdentifiableElement 'Cls6'."
		]);
// instead, we would like to use this:
//		assertNoErrors(script1);
//		assertNoErrors(script2);
//		assertNoErrors(script3);
//
//		val scriptUsage = '''
//			import { Cls9 as ClsViaOther1 } from "other1"
//			import { Cls1 as ClsViaOther3 } from "other3"
//
//			let v01: boolean = new ClsViaOther1().field;
//			let v02: number = new ClsViaOther1().field;
//
//			let v11: boolean = new ClsViaOther3().field;
//			let v12: number = new ClsViaOther3().field;
//		'''.parseInFile("usage.n4js", rs);
//
//		assertErrors(scriptUsage, #[
//			"ERROR:number is not a subtype of boolean. (usage.n4js line : 4 column : 20)",
//			"ERROR:number is not a subtype of boolean. (usage.n4js line : 7 column : 20)"
//		]);
	}

	def private void assertNoErrors(Script script) {
		assertErrors(script, #[]);
	}

	def private void assertErrors(Script script, String[] expectedErrors) {
		val res = script.eResource as N4JSResource;
		res.performPostProcessing;
		script.assertNoParseErrors();
		script.assertErrorsExcept(#[ UNSUPPORTED ], expectedErrors);
	}

	def private void assertParseErrors(Script script, String[] expectedErrors) {
		val res = script.eResource as N4JSResource;
		res.performPostProcessing;
		val actualErrors = script.eResource.errors.map['''line «line», column «column»: «message»'''].sort;
		val expectedErrorsSorted = expectedErrors.sort;
		Assert.assertEquals("actual parse errors did not match expected parse errors", expectedErrorsSorted, actualErrors);
	}
}
