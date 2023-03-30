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
 * Code completion tests for scenarios that also might add an import statement
 */
@SuppressWarnings("javadoc")
public class CompletionWithImportsTest extends AbstractCompletionTest {

	/** Some default modules that export a number of classes for all tests. */
	@Override
	public final List<Pair<String, String>> getDefaultTestProject() {
		return List.of(
				Pair.of("MA", """
						export class A1 {}
						export class A2 {}"""),
				Pair.of("MBA", """
						export class B1 {}
						export class A2 {}"""));
	}

	@Test
	public void test01() {
		testAtCursor("""
				let x = new A1<|>
				""", """
				(A1, Class, MA, , , 00000, , , , ([0:12 - 0:14], A1), [([0:0 - 0:0], import {A1} from "MA";
				)], [], , )
				""");
	}

	@Test
	public void test02() {
		testAtCursor("""
				let x = new A2<|>
				""", """
				(A2, Class, MA, , , 00000, , , , ([0:12 - 0:14], A2), [([0:0 - 0:0], import {A2} from "MA";
				)], [], , )
				(A2, Class, MBA, , , 00001, , , , ([0:12 - 0:14], A2), [([0:0 - 0:0], import {A2} from "MBA";
				)], [], , )
				(Array2, Class, Array2, , , 00002, , , , ([0:12 - 0:14], Array2), [], [], , )
				""");
	}

	@Test
	public void testAliasExists1() {
		testAtCursor("""
				import {A1 as Alias1} from "MA";
				let x = new A1<|>
				""", """
				(Alias1, Class, alias for MA.A1, , , 00000, , , , ([1:12 - 1:14], Alias1), [], [], , )
				""");
	}

	@Test
	public void testAliasExists2() {
		testAtCursor("""
				import {A1 as Alias1} from "MA";
				let x = new Alias1<|>
				""", """
				(Alias1, Class, alias for MA.A1, , , 00000, , , , ([1:12 - 1:18], Alias1), [], [], , )
				""");
	}

	@Test
	public void testAliasExists3() {
		testAtCursor("""
				import {A1 as B1} from "MA";
				B1<|>
				""",
				"""
						(B1, Class, alias for MA.A1, , , 00000, , , , ([1:0 - 1:2], B1), [], [], , )
						(B1, Class, via new alias Alias_MBA_B1 for MBA.B1

						Introduces the new alias 'Alias_MBA_B1' for element MBA.B1, , , 00001, , , , ([1:0 - 1:2], Alias_MBA_B1), [([0:28 - 0:28],\s
						import {B1 as Alias_MBA_B1} from "MBA";)], [], , )
						""");
	}

	@Test
	public void testAliasExists4() {
		testAtCursor("""
				import {A2 as AliasA} from "MA";
				AliasA<|>;
				""", """
				(AliasA, Class, alias for MA.A2, , , 00000, , , , ([1:0 - 1:6], AliasA), [], [], , )
				""");
	}

	@Test
	public void testAliasExists5() {
		testAtCursor("""
				import {A2 as AliasA} from "MA";
				Ali<|>;
				""", """
				(AliasA, Class, alias for MA.A2, , , 00000, , , , ([1:0 - 1:3], AliasA), [], [], , )
				""");
	}

	@Test
	public void testAliasExists6() {
		testAtCursor("""
				import {A2 as Alias1} from "MA";
				import {A2 as Alias2} from "MBA";
				A2<|>;
				""", """
				(Alias1, Class, alias for MA.A2, , , 00000, , , , ([2:0 - 2:2], Alias1), [], [], , )
				(Alias2, Class, alias for MBA.A2, , , 00001, , , , ([2:0 - 2:2], Alias2), [], [], , )
				(Array2, Class, Array2, , , 00002, , , , ([2:0 - 2:2], Array2), [], [], , )
				""");
	}

	@Test
	public void testAliasExists7() {
		testAtCursor("""
				import {A2 as Alias1} from "MA";
				import {A2 as Alias2} from "MBA";
				Ali<|>;
				""", """
				(Alias1, Class, alias for MA.A2, , , 00000, , , , ([2:0 - 2:3], Alias1), [], [], , )
				(Alias2, Class, alias for MBA.A2, , , 00001, , , , ([2:0 - 2:3], Alias2), [], [], , )
				""");
	}

	@Test
	public void testAliasExists8() {
		testAtCursor("""
				import {A2 as A2_Alias} from "MA";
				A2<|>;
				""", """
				(A2, Class, MBA, , , 00000, , , , ([1:0 - 1:2], A2), [([0:34 - 0:34],\s
				import {A2} from "MBA";)], [], , )
				(A2_Alias, Class, alias for MA.A2, , , 00001, , , , ([1:0 - 1:2], A2_Alias), [], [], , )
				(Array2, Class, Array2, , , 00002, , , , ([1:0 - 1:2], Array2), [], [], , )
				""");
	}

	@Test
	public void testAliasExists9() {
		testAtCursor("""
				import {A2} from "MA";
				import {A2 as A2_Alias} from "MBA";
				A2<|>;
				""", """
				(A2, Class, MA, , , 00000, , , , ([2:0 - 2:2], A2), [], [], , )
				(A2_Alias, Class, alias for MBA.A2, , , 00001, , , , ([2:0 - 2:2], A2_Alias), [], [], , )
				(Array2, Class, Array2, , , 00002, , , , ([2:0 - 2:2], Array2), [], [], , )
				""");
	}

	@Test
	public void testAliasExists10() {
		testAtCursor("""
				import {A1 as A2} from "MA";
				A2<|>;
				""",
				"""
						(A2, Class, alias for MA.A1, , , 00000, , , , ([1:0 - 1:2], A2), [], [], , )
						(A2, Class, via new alias Alias_MA_A2 for MA.A2

						Introduces the new alias 'Alias_MA_A2' for element MA.A2, , , 00001, , , , ([1:0 - 1:2], Alias_MA_A2), [([0:28 - 0:28],\s
						import {A2 as Alias_MA_A2} from "MA";)], [], , )
						(A2, Class, via new alias Alias_MBA_A2 for MBA.A2

						Introduces the new alias 'Alias_MBA_A2' for element MBA.A2, , , 00002, , , , ([1:0 - 1:2], Alias_MBA_A2), [([0:28 - 0:28],\s
						import {A2 as Alias_MBA_A2} from "MBA";)], [], , )
						(Array2, Class, Array2, , , 00003, , , , ([1:0 - 1:2], Array2), [], [], , )
						""");
	}

	@Test
	public void testAliasNecessary1() {
		testAtCursor("""
				import {A2} from "MA";
				let x = new A2<|>
				""",
				"""
						(A2, Class, MA, , , 00000, , , , ([1:12 - 1:14], A2), [], [], , )
						(A2, Class, via new alias Alias_MBA_A2 for MBA.A2

						Introduces the new alias 'Alias_MBA_A2' for element MBA.A2, , , 00001, , , , ([1:12 - 1:14], Alias_MBA_A2), [([0:22 - 0:22],\s
						import {A2 as Alias_MBA_A2} from "MBA";)], [], , )
						(Array2, Class, Array2, , , 00002, , , , ([1:12 - 1:14], Array2), [], [], , )
						""");
	}

	@Test
	public void testNamespaceExists1() {
		testAtCursor("""
				import * as NSMA from "MA";
				let x = new A1<|>
				""", """
				(NSMA.A1, Class, MA.A1, , , 00000, , , , ([1:12 - 1:14], NSMA.A1), [], [], , )
				""");
	}

	@Test
	public void testNamespaceExists2() {
		testAtCursor("""
				import * as NSMA from "MA";
				let x = new NSMA.A1<|>
				""", """
				(A1, Class, MA, , , 00000, , , , ([1:17 - 1:19], A1), [], [], , )
				""");
	}

	@Test
	public void testNamespaceExists3() {
		testAtCursor("""
				import * as NSMA from "MA";
				A2<|>
				""", """
				(A2, Class, MBA, , , 00000, , , , ([1:0 - 1:2], A2), [([0:27 - 0:27],\s
				import {A2} from "MBA";)], [], , )
				(Array2, Class, Array2, , , 00001, , , , ([1:0 - 1:2], Array2), [], [], , )
				(NSMA.A2, Class, MA.A2, , , 00002, , , , ([1:0 - 1:2], NSMA.A2), [], [], , )
				""");
	}

	@Test
	public void testNamespaceExists4() {
		testAtCursor("""
				import * as A2 from "MA";
				A2<|>
				""",
				"""
						(A2, Color, MyModule, , , 00000, , , , ([1:0 - 1:2], A2), [], [], , )
						(A2.A2, Class, MA.A2, , , 00001, , , , ([1:0 - 1:2], A2.A2), [], [], , )
						(A2, Class, via new alias Alias_MBA_A2 for MBA.A2

						Introduces the new alias 'Alias_MBA_A2' for element MBA.A2, , , 00002, , , , ([1:0 - 1:2], Alias_MBA_A2), [([0:25 - 0:25],\s
						import {A2 as Alias_MBA_A2} from "MBA";)], [], , )
						(Array2, Class, Array2, , , 00003, , , , ([1:0 - 1:2], Array2), [], [], , )
						""");
	}

	@Test
	public void testBuiltinType1() {
		testAtCursor("""
				Obj<|>
				""",
				"""
						(N4Object, Class, N4Object, , , 00000, , , , ([0:0 - 0:3], N4Object), [], [], , )
						(Object, Class, Object, , , 00001, , , , ([0:0 - 0:3], Object), [], [], , )
						(ObjectConstructor, Text, ObjectConstructor, , , 00002, , , , ([0:0 - 0:3], ObjectConstructor), [], [], , )
						""");
	}

	@Test
	public void testBuiltinType2() {
		testAtCursor("""
				N4En<|>
				""",
				"""
						(N4Enum, Class, N4Enum, , , 00000, , , , ([0:0 - 0:4], N4Enum), [], [], , )
						(N4EnumType, Class, N4EnumType, , , 00001, , , , ([0:0 - 0:4], N4EnumType), [], [], , )
						(N4NumberBasedEnum, Class, N4NumberBasedEnum, , , 00002, , , , ([0:0 - 0:4], N4NumberBasedEnum), [], [], , )
						(N4StringBasedEnum, Class, N4StringBasedEnum, , , 00003, , , , ([0:0 - 0:4], N4StringBasedEnum), [], [], , )
						""");
	}

	@Test
	public void testGlobal1() {
		testAtCursor("""
				isNaN<|>
				""", """
				(isNaN, Method, isNaN, , , 00000, , , , ([0:0 - 0:5], isNaN), [], [], , )
				""");
	}

	@Test
	public void testInsideImportStatement1() {
		testAtCursor("""
				import {A<|>
				""",
				"""
						(A1, Class, MA, , , 00000, , , , ([0:8 - 0:9], A1), [([0:0 - 0:0], import {A1} from "MA";
						)], [], , )
						(A2, Class, MA, , , 00001, , , , ([0:8 - 0:9], A2), [([0:0 - 0:0], import {A2} from "MA";
						)], [], , )
						(A2, Class, MBA, , , 00002, , , , ([0:8 - 0:9], A2), [([0:0 - 0:0], import {A2} from "MBA";
						)], [], , )
						(Array, Class, Array, , , 00003, , , , ([0:8 - 0:9], Array), [], [], , )
						(Array2, Class, Array2, , , 00004, , , , ([0:8 - 0:9], Array2), [], [], , )
						(Array3, Class, Array3, , , 00005, , , , ([0:8 - 0:9], Array3), [], [], , )
						(Array4, Class, Array4, , , 00006, , , , ([0:8 - 0:9], Array4), [], [], , )
						(Array5, Class, Array5, , , 00007, , , , ([0:8 - 0:9], Array5), [], [], , )
						(Array6, Class, Array6, , , 00008, , , , ([0:8 - 0:9], Array6), [], [], , )
						(Array7, Class, Array7, , , 00009, , , , ([0:8 - 0:9], Array7), [], [], , )
						(Array8, Class, Array8, , , 00010, , , , ([0:8 - 0:9], Array8), [], [], , )
						(Array9, Class, Array9, , , 00011, , , , ([0:8 - 0:9], Array9), [], [], , )
						(ArrayBuffer, Class, ArrayBuffer, , , 00012, , , , ([0:8 - 0:9], ArrayBuffer), [], [], , )
						(ArrayBufferConstructor, Text, ArrayBufferConstructor, , , 00013, , , , ([0:8 - 0:9], ArrayBufferConstructor), [], [], , )
						(ArrayBufferLike, Text, ArrayBufferLike, , , 00014, , , , ([0:8 - 0:9], ArrayBufferLike), [], [], , )
						(ArrayBufferTypes, Interface, ArrayBufferTypes, , , 00015, , , , ([0:8 - 0:9], ArrayBufferTypes), [], [], , )
						(ArrayBufferView, Interface, ArrayBufferView, , , 00016, , , , ([0:8 - 0:9], ArrayBufferView), [], [], , )
						(ArrayConstructor, Text, ArrayConstructor, , , 00017, , , , ([0:8 - 0:9], ArrayConstructor), [], [], , )
						(ArrayLike, Interface, ArrayLike, , , 00018, , , , ([0:8 - 0:9], ArrayLike), [], [], , )
						(AsyncGenerator, Interface, AsyncGenerator, , , 00019, , , , ([0:8 - 0:9], AsyncGenerator), [], [], , )
						(AsyncIterable, Interface, AsyncIterable, , , 00020, , , , ([0:8 - 0:9], AsyncIterable), [], [], , )
						(AsyncIterator, Interface, AsyncIterator, , , 00021, , , , ([0:8 - 0:9], AsyncIterator), [], [], , )
						(ConcatArray, Interface, ConcatArray, , , 00022, , , , ([0:8 - 0:9], ConcatArray), [], [], , )
						(Float32Array, Class, Float32Array, , , 00023, , , , ([0:8 - 0:9], Float32Array), [], [], , )
						(Float32ArrayConstructor, Text, Float32ArrayConstructor, , , 00024, , , , ([0:8 - 0:9], Float32ArrayConstructor), [], [], , )
						(Float64Array, Class, Float64Array, , , 00025, , , , ([0:8 - 0:9], Float64Array), [], [], , )
						(Float64ArrayConstructor, Text, Float64ArrayConstructor, , , 00026, , , , ([0:8 - 0:9], Float64ArrayConstructor), [], [], , )
						(IArguments, Interface, IArguments, , , 00027, , , , ([0:8 - 0:9], IArguments), [], [], , )
						(ImportAssertions, Interface, ImportAssertions, , , 00028, , , , ([0:8 - 0:9], ImportAssertions), [], [], , )
						(Int16Array, Class, Int16Array, , , 00029, , , , ([0:8 - 0:9], Int16Array), [], [], , )
						(Int16ArrayConstructor, Text, Int16ArrayConstructor, , , 00030, , , , ([0:8 - 0:9], Int16ArrayConstructor), [], [], , )
						(Int32Array, Class, Int32Array, , , 00031, , , , ([0:8 - 0:9], Int32Array), [], [], , )
						(Int32ArrayConstructor, Text, Int32ArrayConstructor, , , 00032, , , , ([0:8 - 0:9], Int32ArrayConstructor), [], [], , )
						(Int8Array, Class, Int8Array, , , 00033, , , , ([0:8 - 0:9], Int8Array), [], [], , )
						(Int8ArrayConstructor, Text, Int8ArrayConstructor, , , 00034, , , , ([0:8 - 0:9], Int8ArrayConstructor), [], [], , )
						(N4Accessor, Class, N4Accessor, , , 00035, , , , ([0:8 - 0:9], N4Accessor), [], [], , )
						(N4Annotation, Class, N4Annotation, , , 00036, , , , ([0:8 - 0:9], N4Annotation), [], [], , )
						(N4ApiNotImplementedError, Class, N4ApiNotImplementedError, , , 00037, , , , ([0:8 - 0:9], N4ApiNotImplementedError), [], [], , )
						(ReadonlyArray, Text, ReadonlyArray, , , 00038, , , , ([0:8 - 0:9], ReadonlyArray), [], [], , )
						(RegExpExecArray, Class, RegExpExecArray, , , 00039, , , , ([0:8 - 0:9], RegExpExecArray), [], [], , )
						(RegExpMatchArray, Class, RegExpMatchArray, , , 00040, , , , ([0:8 - 0:9], RegExpMatchArray), [], [], , )
						(Uint16Array, Class, Uint16Array, , , 00041, , , , ([0:8 - 0:9], Uint16Array), [], [], , )
						(Uint16ArrayConstructor, Text, Uint16ArrayConstructor, , , 00042, , , , ([0:8 - 0:9], Uint16ArrayConstructor), [], [], , )
						(Uint32Array, Class, Uint32Array, , , 00043, , , , ([0:8 - 0:9], Uint32Array), [], [], , )
						(Uint32ArrayConstructor, Text, Uint32ArrayConstructor, , , 00044, , , , ([0:8 - 0:9], Uint32ArrayConstructor), [], [], , )
						(Uint8Array, Class, Uint8Array, , , 00045, , , , ([0:8 - 0:9], Uint8Array), [], [], , )
						(Uint8ArrayConstructor, Text, Uint8ArrayConstructor, , , 00046, , , , ([0:8 - 0:9], Uint8ArrayConstructor), [], [], , )
						(Uint8ClampedArray, Class, Uint8ClampedArray, , , 00047, , , , ([0:8 - 0:9], Uint8ClampedArray), [], [], , )
						(Uint8ClampedArrayConstructor, Text, Uint8ClampedArrayConstructor, , , 00048, , , , ([0:8 - 0:9], Uint8ClampedArrayConstructor), [], [], , )
						""");
	}

	@Test
	public void testInsideImportStatement2() {
		testAtCursor("""
				import {A<|> from "MA";
				""",
				"""
						(A1, Class, MA, , , 00000, , , , ([0:8 - 0:9], A1), [([0:0 - 0:0], import {A1} from "MA";
						)], [], , )
						(A2, Class, MA, , , 00001, , , , ([0:8 - 0:9], A2), [([0:0 - 0:0], import {A2} from "MA";
						)], [], , )
						(A2, Class, MBA, , , 00002, , , , ([0:8 - 0:9], A2), [([0:0 - 0:0], import {A2} from "MBA";
						)], [], , )
						(Array, Class, Array, , , 00003, , , , ([0:8 - 0:9], Array), [], [], , )
						(Array2, Class, Array2, , , 00004, , , , ([0:8 - 0:9], Array2), [], [], , )
						(Array3, Class, Array3, , , 00005, , , , ([0:8 - 0:9], Array3), [], [], , )
						(Array4, Class, Array4, , , 00006, , , , ([0:8 - 0:9], Array4), [], [], , )
						(Array5, Class, Array5, , , 00007, , , , ([0:8 - 0:9], Array5), [], [], , )
						(Array6, Class, Array6, , , 00008, , , , ([0:8 - 0:9], Array6), [], [], , )
						(Array7, Class, Array7, , , 00009, , , , ([0:8 - 0:9], Array7), [], [], , )
						(Array8, Class, Array8, , , 00010, , , , ([0:8 - 0:9], Array8), [], [], , )
						(Array9, Class, Array9, , , 00011, , , , ([0:8 - 0:9], Array9), [], [], , )
						(ArrayBuffer, Class, ArrayBuffer, , , 00012, , , , ([0:8 - 0:9], ArrayBuffer), [], [], , )
						(ArrayBufferConstructor, Text, ArrayBufferConstructor, , , 00013, , , , ([0:8 - 0:9], ArrayBufferConstructor), [], [], , )
						(ArrayBufferLike, Text, ArrayBufferLike, , , 00014, , , , ([0:8 - 0:9], ArrayBufferLike), [], [], , )
						(ArrayBufferTypes, Interface, ArrayBufferTypes, , , 00015, , , , ([0:8 - 0:9], ArrayBufferTypes), [], [], , )
						(ArrayBufferView, Interface, ArrayBufferView, , , 00016, , , , ([0:8 - 0:9], ArrayBufferView), [], [], , )
						(ArrayConstructor, Text, ArrayConstructor, , , 00017, , , , ([0:8 - 0:9], ArrayConstructor), [], [], , )
						(ArrayLike, Interface, ArrayLike, , , 00018, , , , ([0:8 - 0:9], ArrayLike), [], [], , )
						(AsyncGenerator, Interface, AsyncGenerator, , , 00019, , , , ([0:8 - 0:9], AsyncGenerator), [], [], , )
						(AsyncIterable, Interface, AsyncIterable, , , 00020, , , , ([0:8 - 0:9], AsyncIterable), [], [], , )
						(AsyncIterator, Interface, AsyncIterator, , , 00021, , , , ([0:8 - 0:9], AsyncIterator), [], [], , )
						(ConcatArray, Interface, ConcatArray, , , 00022, , , , ([0:8 - 0:9], ConcatArray), [], [], , )
						(Float32Array, Class, Float32Array, , , 00023, , , , ([0:8 - 0:9], Float32Array), [], [], , )
						(Float32ArrayConstructor, Text, Float32ArrayConstructor, , , 00024, , , , ([0:8 - 0:9], Float32ArrayConstructor), [], [], , )
						(Float64Array, Class, Float64Array, , , 00025, , , , ([0:8 - 0:9], Float64Array), [], [], , )
						(Float64ArrayConstructor, Text, Float64ArrayConstructor, , , 00026, , , , ([0:8 - 0:9], Float64ArrayConstructor), [], [], , )
						(IArguments, Interface, IArguments, , , 00027, , , , ([0:8 - 0:9], IArguments), [], [], , )
						(ImportAssertions, Interface, ImportAssertions, , , 00028, , , , ([0:8 - 0:9], ImportAssertions), [], [], , )
						(Int16Array, Class, Int16Array, , , 00029, , , , ([0:8 - 0:9], Int16Array), [], [], , )
						(Int16ArrayConstructor, Text, Int16ArrayConstructor, , , 00030, , , , ([0:8 - 0:9], Int16ArrayConstructor), [], [], , )
						(Int32Array, Class, Int32Array, , , 00031, , , , ([0:8 - 0:9], Int32Array), [], [], , )
						(Int32ArrayConstructor, Text, Int32ArrayConstructor, , , 00032, , , , ([0:8 - 0:9], Int32ArrayConstructor), [], [], , )
						(Int8Array, Class, Int8Array, , , 00033, , , , ([0:8 - 0:9], Int8Array), [], [], , )
						(Int8ArrayConstructor, Text, Int8ArrayConstructor, , , 00034, , , , ([0:8 - 0:9], Int8ArrayConstructor), [], [], , )
						(N4Accessor, Class, N4Accessor, , , 00035, , , , ([0:8 - 0:9], N4Accessor), [], [], , )
						(N4Annotation, Class, N4Annotation, , , 00036, , , , ([0:8 - 0:9], N4Annotation), [], [], , )
						(N4ApiNotImplementedError, Class, N4ApiNotImplementedError, , , 00037, , , , ([0:8 - 0:9], N4ApiNotImplementedError), [], [], , )
						(ReadonlyArray, Text, ReadonlyArray, , , 00038, , , , ([0:8 - 0:9], ReadonlyArray), [], [], , )
						(RegExpExecArray, Class, RegExpExecArray, , , 00039, , , , ([0:8 - 0:9], RegExpExecArray), [], [], , )
						(RegExpMatchArray, Class, RegExpMatchArray, , , 00040, , , , ([0:8 - 0:9], RegExpMatchArray), [], [], , )
						(Uint16Array, Class, Uint16Array, , , 00041, , , , ([0:8 - 0:9], Uint16Array), [], [], , )
						(Uint16ArrayConstructor, Text, Uint16ArrayConstructor, , , 00042, , , , ([0:8 - 0:9], Uint16ArrayConstructor), [], [], , )
						(Uint32Array, Class, Uint32Array, , , 00043, , , , ([0:8 - 0:9], Uint32Array), [], [], , )
						(Uint32ArrayConstructor, Text, Uint32ArrayConstructor, , , 00044, , , , ([0:8 - 0:9], Uint32ArrayConstructor), [], [], , )
						(Uint8Array, Class, Uint8Array, , , 00045, , , , ([0:8 - 0:9], Uint8Array), [], [], , )
						(Uint8ArrayConstructor, Text, Uint8ArrayConstructor, , , 00046, , , , ([0:8 - 0:9], Uint8ArrayConstructor), [], [], , )
						(Uint8ClampedArray, Class, Uint8ClampedArray, , , 00047, , , , ([0:8 - 0:9], Uint8ClampedArray), [], [], , )
						(Uint8ClampedArrayConstructor, Text, Uint8ClampedArrayConstructor, , , 00048, , , , ([0:8 - 0:9], Uint8ClampedArrayConstructor), [], [], , )
						""");
	}

	@Test
	public void testInsideImportStatement3() {
		testAtCursor("""
				import {A<|>} from "MA";
				""", """
				(A1, Class, MA, , , 00000, , , , ([0:8 - 0:9], A1), [], [], , )
				(A2, Class, MA, , , 00001, , , , ([0:8 - 0:9], A2), [], [], , )
				""");
	}

	@Test
	public void testUnresolvedReferenceDoesNotCauseAliasImport_inN4JS() {
		testAtCursor("""
				A1<|>;
				// the fact we have the following unresolved reference to the proposed element "A1" in the source code
				// should not cause an alias import to be created:
				A1;
				""",
				"""
						(A1, Class, MA, , , 00000, , , , ([0:0 - 0:2], A1), [([0:0 - 0:0], import {A1} from "MA";
						)], [], , )
						""");
	}

	@Test
	public void testUnresolvedReferenceDoesNotCauseAliasImport_inN4JSX() {
		testAtCursorInN4JSX(
				"""
						<A1<|>
						/>;
						// the fact we have the following unresolved reference to the proposed element "A1" in the source code
						// should not cause an alias import to be created:
						<A1/>;
						""",
				"""
						(A1, Class, MA, , , 00000, , , , ([0:1 - 0:3], A1), [([0:0 - 0:0], import {A1} from "MA";
						)], [], , )
						""");
	}

	/**
	 * Similar to {@link #testUnresolvedReferenceDoesNotCauseAliasImport_inN4JSX}, but the unresolved reference is not
	 * located elsewhere in the source code but right where the content assist is triggered.
	 */
	@Test
	public void testExistingJSXTagDoesNotCauseAliasImport() {
		// the important point in this test code is that one of the proposals (i.e. "A1") is already completely given
		// in the source code, which causes the JSX support in scoping to create a UnresolvableObjectDescription which
		// must not trigger creation of an alias import:
		testAtCursorInN4JSX("""
				<A1<|>
				/>;
				""", """
				(A1, Class, MA, , , 00000, , , , ([0:1 - 0:3], A1), [([0:0 - 0:0], import {A1} from "MA";
				)], [], , )
				""");
	}
}
