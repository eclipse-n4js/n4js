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
package org.eclipse.n4js.tests.n4JS;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests for flag {@link ImportSpecifier#isRetainedAtRuntime()}. Indirectly it also tests methods
 * {@link N4JSLanguageUtils#hasRuntimeRepresentation(org.eclipse.n4js.ts.types.IdentifiableElement)
 * #hasRuntimeRepresentation()} and
 * {@link N4JSLanguageUtils#hasRuntimeRepresentation(org.eclipse.n4js.n4JS.N4TypeDeclaration)
 * #hasRuntimeRepresentation()}.
 */
public class ImportRetainedAtRuntimeTest extends AbstractN4JSTest {

	@Test
	public void testNamedImports() throws Exception {
		ResourceSet resourceSet = testHelper.parseAndValidateSuccessfullyMany(List.of(
				Pair.of("ABCD.n4js", """
							export public class A {}
							export public class B {}
							export public class C {}
							export public class D {}
						"""),
				Pair.of("Main1.n4js", """
							import {A,B,C,D} from "ABCD"
							A;
							export function foo(p: C) {}
							class Main extends D {}
						"""),
				Pair.of("Main2.n4js", """
							import {A} from "ABCD"
							import {B} from "ABCD"
							import {C} from "ABCD"
							import {D} from "ABCD"
							A;
							export function foo(p: C) {}
							class Main extends D {}
						""")));

		Script main1 = findScript(resourceSet, "Main1.n4js");
		List<ImportDeclaration> impDecls1 = toList(filter(main1.getScriptElements(), ImportDeclaration.class));
		ImportDeclaration importDecl = impDecls1.get(0);
		assertTrue(importDecl.isRetainedAtRuntime());
		assertTrue(importDecl.getImportSpecifiers().get(0).isRetainedAtRuntime());
		assertFalse(importDecl.getImportSpecifiers().get(1).isRetainedAtRuntime());
		assertFalse(importDecl.getImportSpecifiers().get(2).isRetainedAtRuntime());
		assertTrue(importDecl.getImportSpecifiers().get(3).isRetainedAtRuntime());

		Script main2 = findScript(resourceSet, "Main2.n4js");
		List<ImportDeclaration> impDecls2 = toList(filter(main2.getScriptElements(), ImportDeclaration.class));
		ImportDeclaration importDeclA = impDecls2.get(0);
		ImportDeclaration importDeclB = impDecls2.get(1);
		ImportDeclaration importDeclC = impDecls2.get(2);
		ImportDeclaration importDeclD = impDecls2.get(3);
		assertTrue(importDeclA.isRetainedAtRuntime());
		assertTrue(importDeclA.getImportSpecifiers().get(0).isRetainedAtRuntime());
		assertFalse(importDeclB.isRetainedAtRuntime());
		assertFalse(importDeclB.getImportSpecifiers().get(0).isRetainedAtRuntime());
		assertFalse(importDeclC.isRetainedAtRuntime());
		assertFalse(importDeclC.getImportSpecifiers().get(0).isRetainedAtRuntime());
		assertTrue(importDeclD.isRetainedAtRuntime());
		assertTrue(importDeclD.getImportSpecifiers().get(0).isRetainedAtRuntime());
	}

	@Test
	public void testNamespaceImports() throws Exception {
		ResourceSet resourceSet = testHelper.parseAndValidateSuccessfullyMany(List.of(
				Pair.of("A.n4js", """
							export public class A {}
						"""),
				Pair.of("B.n4js", """
							export public class B {}
						"""),
				Pair.of("C.n4js", """
							export public class C {}
						"""),
				Pair.of("D.n4js", """
							export public class D {}
						"""),
				Pair.of("Main.n4js", """
							import * as NA from "A"
							import * as NB from "B"
							import * as NC from "C"
							import * as ND from "D"
							NA.A;
							export function foo(p: NC.C) {}
							class Main extends ND.D {}
						""")));

		Script main = findScript(resourceSet, "Main.n4js");
		List<ImportDeclaration> impDecls = toList(filter(main.getScriptElements(), ImportDeclaration.class));
		ImportDeclaration importDeclA = impDecls.get(0);
		ImportDeclaration importDeclB = impDecls.get(1);
		ImportDeclaration importDeclC = impDecls.get(2);
		ImportDeclaration importDeclD = impDecls.get(3);
		assertTrue(importDeclA.isRetainedAtRuntime());
		assertFalse(importDeclB.isRetainedAtRuntime());
		assertFalse(importDeclC.isRetainedAtRuntime());
		assertTrue(importDeclD.isRetainedAtRuntime());
	}

	@Test
	public void testDefaultImports() throws Exception {
		ResourceSet resourceSet = testHelper.parseAndValidateSuccessfullyMany(List.of(
				Pair.of("A.n4js", """
							export default public class A {}
						"""),
				Pair.of("B.n4js", """
							export default public class B {}
						"""),
				Pair.of("C.n4js", """
							export default public class C {}
						"""),
				Pair.of("D.n4js", """
							export default public class D {}
						"""),
				Pair.of("Main.n4js", """
							import a from "A"
							import b from "B"
							import c from "C"
							import d from "D"
							a;
							export function foo(p: c) {}
							class Main extends d {}
						""")));

		Script main = findScript(resourceSet, "Main.n4js");
		List<ImportDeclaration> impDecls = toList(filter(main.getScriptElements(), ImportDeclaration.class));
		ImportDeclaration importDeclA = impDecls.get(0);
		ImportDeclaration importDeclB = impDecls.get(1);
		ImportDeclaration importDeclC = impDecls.get(2);
		ImportDeclaration importDeclD = impDecls.get(3);
		assertTrue(importDeclA.isRetainedAtRuntime());
		assertFalse(importDeclB.isRetainedAtRuntime());
		assertFalse(importDeclC.isRetainedAtRuntime());
		assertTrue(importDeclD.isRetainedAtRuntime());
	}

	@Test
	public void testBareImports() throws Exception {
		ResourceSet resourceSet = testHelper.parseAndValidateSuccessfullyMany(List.of(
				Pair.of("A.n4js", """
							export public class A {}
						"""),
				Pair.of("Main.n4js", """
							import "A"
						""")));

		Script main = findScript(resourceSet, "Main.n4js");
		List<ImportDeclaration> impDecls = toList(filter(main.getScriptElements(), ImportDeclaration.class));
		ImportDeclaration importDecl = impDecls.get(0);
		assertTrue(importDecl.isRetainedAtRuntime()); // bare imports are always retained at runtime
	}

	@Test
	public void testImportingEnums() throws Exception {
		ResourceSet resourceSet = testHelper.parseAndValidateSuccessfullyMany(List.of(
				Pair.of("E.n4js", """
							export public enum E1 { L1, L2, L3 }
							@StringBased
							export public enum E2 { L1, L2, L3 }
						"""),
				Pair.of("Main.n4js", """
							import {E1} from "E"
							import {E2} from "E"

							console.log(E1.L1);
							console.log(E2.L1);
						""")));

		Script main = findScript(resourceSet, "Main.n4js");
		List<ImportDeclaration> impDecls = toList(filter(main.getScriptElements(), ImportDeclaration.class));
		ImportDeclaration importDeclE1 = impDecls.get(0);
		ImportDeclaration importDeclE2 = impDecls.get(1);

		assertTrue(importDeclE1.isRetainedAtRuntime());
		assertFalse(importDeclE2.isRetainedAtRuntime());
	}

	@Test
	public void testImportingFromN4JSDFiles() throws Exception {
		ResourceSet resourceSet = testHelper.parseAndValidateSuccessfullyMany(List.of(
				Pair.of("Def.n4jsd", """
							export external public class C {}
							export external public interface I1 {}
							export external public interface ~I3 {}
						"""),
				Pair.of("Main1.n4js", """
							import {C} from "Def"
							import {I1} from "Def"
							import {I3} from "Def"

							C, I1;
						"""),
				Pair.of("Main2.n4js", """
							import {C} from "Def"
							import {I1} from "Def"
							import {I3} from "Def"

							class T0 extends C {}
							class T1 implements I1 {}
							class T3 implements I3 {}
						""")));

		Script main1 = findScript(resourceSet, "Main1.n4js");
		List<ImportDeclaration> impDecls1 = toList(filter(main1.getScriptElements(), ImportDeclaration.class));
		ImportDeclaration importDeclC_in1 = impDecls1.get(0);
		ImportDeclaration importDeclI1_in1 = impDecls1.get(1);
		ImportDeclaration importDeclI3_in1 = impDecls1.get(2);

		assertTrue(importDeclC_in1.isRetainedAtRuntime());
		assertTrue(importDeclI1_in1.isRetainedAtRuntime());
		assertFalse(importDeclI3_in1.isRetainedAtRuntime());

		Script main2 = findScript(resourceSet, "Main2.n4js");
		List<ImportDeclaration> impDecls2 = toList(filter(main2.getScriptElements(), ImportDeclaration.class));
		ImportDeclaration importDeclC_in2 = impDecls2.get(0);
		ImportDeclaration importDeclI1_in2 = impDecls2.get(1);
		ImportDeclaration importDeclI3_in2 = impDecls2.get(2);

		assertTrue(importDeclC_in2.isRetainedAtRuntime());
		assertTrue(importDeclI1_in2.isRetainedAtRuntime());
		assertFalse(importDeclI3_in2.isRetainedAtRuntime());
	}

	private Script findScript(ResourceSet resourceSet, String name) {
		N4JSResource res = findResource(resourceSet, name);
		return res == null ? null : res.getScript();
	}

	private N4JSResource findResource(ResourceSet resourceSet, String name) {
		return findFirst(filter(resourceSet.getResources(), N4JSResource.class),
				res -> Objects.equals(res.getURI().lastSegment(), name));
	}
}
