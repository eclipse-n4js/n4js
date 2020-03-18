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
package org.eclipse.n4js.tests.n4JS

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.junit.Test

/**
 * Tests for flag {@link ImportSpecifier#isRetainedAtRuntime()}. Indirectly it also tests methods
 * {@link N4JSLanguageUtils#hasRuntimeRepresentation(org.eclipse.n4js.ts.types.IdentifiableElement, org.eclipse.n4js.validation.JavaScriptVariantHelper) #hasRuntimeRepresentation()} and
 * {@link N4JSLanguageUtils#hasRuntimeRepresentation(org.eclipse.n4js.n4JS.N4TypeDeclaration, org.eclipse.n4js.validation.JavaScriptVariantHelper) #hasRuntimeRepresentation()}.
 */
class ImportRetainedAtRuntimeTest extends AbstractN4JSTest {

	@Test
	def void testSimple() {
		val resourceSet = #[
			"ABCD.n4js" -> '''
				export public class A {}
				export public class B {}
				export public class C {}
				export public class D {}
			''',
			"Main1.n4js" -> '''
				import {A,B,C,D} from "ABCD"
				A;
				export function foo(p: C) {}
				class Main extends D {}
			''',
			"Main2.n4js" -> '''
				import {A} from "ABCD"
				import {B} from "ABCD"
				import {C} from "ABCD"
				import {D} from "ABCD"
				A;
				export function foo(p: C) {}
				class Main extends D {}
			'''
		].parseAndValidateSuccessfullyMany;

		val main1 = resourceSet.findScript("Main1.n4js");
		val importDecl = main1.scriptElements.filter(ImportDeclaration).head;
		assertTrue(importDecl.retainedAtRuntime);
		assertTrue(importDecl.importSpecifiers.get(0).retainedAtRuntime);
		assertFalse(importDecl.importSpecifiers.get(1).retainedAtRuntime);
		assertFalse(importDecl.importSpecifiers.get(2).retainedAtRuntime);
		assertTrue(importDecl.importSpecifiers.get(3).retainedAtRuntime);

		val main2 = resourceSet.findScript("Main2.n4js");
		val importDeclA = main2.scriptElements.filter(ImportDeclaration).get(0);
		val importDeclB = main2.scriptElements.filter(ImportDeclaration).get(1);
		val importDeclC = main2.scriptElements.filter(ImportDeclaration).get(2);
		val importDeclD = main2.scriptElements.filter(ImportDeclaration).get(3);
		assertTrue(importDeclA.retainedAtRuntime);
		assertTrue(importDeclA.importSpecifiers.head.retainedAtRuntime);
		assertFalse(importDeclB.retainedAtRuntime);
		assertFalse(importDeclB.importSpecifiers.head.retainedAtRuntime);
		assertFalse(importDeclC.retainedAtRuntime);
		assertFalse(importDeclC.importSpecifiers.head.retainedAtRuntime);
		assertTrue(importDeclD.retainedAtRuntime);
		assertTrue(importDeclD.importSpecifiers.head.retainedAtRuntime);
	}

	@Test
	def void testEnums() {
		val resourceSet = #[
			"E.n4js" -> '''
				export public enum E1 { L1, L2, L3 }
				@StringBased
				export public enum E2 { L1, L2, L3 }
			''',
			"Main.n4js" -> '''
				import {E1} from "E"
				import {E2} from "E"
				
				console.log(E1.L1);
				console.log(E2.L1);
			'''
		].parseAndValidateSuccessfullyMany;

		val main = resourceSet.findScript("Main.n4js");
		val importDeclE1 = main.scriptElements.filter(ImportDeclaration).get(0);
		val importDeclE2 = main.scriptElements.filter(ImportDeclaration).get(1);

		assertTrue(importDeclE1.retainedAtRuntime);
		assertFalse(importDeclE2.retainedAtRuntime);
	}

	@Test
	def void testN4JSDFiles() {
		val resourceSet = #[
			"Def.n4jsd" -> '''
				export external public class C {}
				@N4JS export external public interface I1 {}
				export external public interface I2 {}
				export external public interface ~I3 {}
			''',
			"Main1.n4js" -> '''
				import {C} from "Def"
				import {I1} from "Def"
				import {I2} from "Def"
				import {I3} from "Def"
				
				C, I1, I2, I3;
			''',
			"Main2.n4js" -> '''
				import {C} from "Def"
				import {I1} from "Def"
				import {I2} from "Def"
				import {I3} from "Def"
				
				class T0 extends C {}
				class T1 implements I1 {}
				class T2 implements I2 {}
				class T3 implements I3 {}
			'''
		].parseAndValidateSuccessfullyMany;

		val main1 = resourceSet.findScript("Main1.n4js");
		val importDeclC_in1 = main1.scriptElements.filter(ImportDeclaration).get(0);
		val importDeclI1_in1 = main1.scriptElements.filter(ImportDeclaration).get(1);
		val importDeclI2_in1 = main1.scriptElements.filter(ImportDeclaration).get(2);
		val importDeclI3_in1 = main1.scriptElements.filter(ImportDeclaration).get(3);

		assertTrue(importDeclC_in1.retainedAtRuntime);
		assertTrue(importDeclI1_in1.retainedAtRuntime);
		assertFalse(importDeclI2_in1.retainedAtRuntime);
		assertFalse(importDeclI3_in1.retainedAtRuntime);

		val main2 = resourceSet.findScript("Main2.n4js");
		val importDeclC_in2 = main2.scriptElements.filter(ImportDeclaration).get(0);
		val importDeclI1_in2 = main2.scriptElements.filter(ImportDeclaration).get(1);
		val importDeclI2_in2 = main2.scriptElements.filter(ImportDeclaration).get(2);
		val importDeclI3_in2 = main2.scriptElements.filter(ImportDeclaration).get(3);

		assertTrue(importDeclC_in2.retainedAtRuntime);
		assertTrue(importDeclI1_in2.retainedAtRuntime);
		assertFalse(importDeclI2_in2.retainedAtRuntime);
		assertFalse(importDeclI3_in2.retainedAtRuntime);
	}

	def private Script findScript(ResourceSet resourceSet, String name) {
		return findResource(resourceSet, name)?.script;
	}

	def private N4JSResource findResource(ResourceSet resourceSet, String name) {
		return resourceSet.resources.filter(N4JSResource).findFirst[getURI.lastSegment == name];
	}
}
