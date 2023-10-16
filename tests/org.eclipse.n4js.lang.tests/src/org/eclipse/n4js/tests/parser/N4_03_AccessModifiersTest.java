/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.parser;

import static java.util.Arrays.asList;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.junit.Test;

public class N4_03_AccessModifiersTest extends AbstractParserTest {

	@Test
	public void testTypeAccessModifiers() throws Exception {
		Script script = parseHelper.parse("""
				class								Cprv {}
				export class						Cdef {}
				export project class				Cpro {}
				export @Internal public class		Cpub {}
				export public class 				Cep {}
				interface 							Iprv {}
				export interface 					Idef {}
				export project interface 			Ipro {}
				export @Internal public interface	Ipub {}
				export public interface				Iep {}
				interface 							Rprv {}
				export interface 					Rdef {}
				export project interface 			Rpro {}
				export @Internal public interface	Rpub {}
				export public interface				Rep {}
				enum 								Eprv {L}
				export enum 						Edef {L}
				export project enum 				Epro {L}
				export @Internal public enum		Epub {L}
				export public enum					Eep {L}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		List<Class<? extends N4TypeDeclaration>> expectedMT = List.of(N4ClassDeclaration.class,
				N4InterfaceDeclaration.class, N4InterfaceDeclaration.class, N4EnumDeclaration.class);
		List<TypeAccessModifier> expectedAM = List.of(TypeAccessModifier.PRIVATE, TypeAccessModifier.PROJECT,
				TypeAccessModifier.PROJECT,
				TypeAccessModifier.PUBLIC_INTERNAL, TypeAccessModifier.PUBLIC);
		List<String> expectedAnno = asList(new String[] { null, null, null, "Internal", null });

		assertEquals(expectedMT.size() * expectedAM.size(), script.getScriptElements().size());

		int annoIndex = -1;

		Iterator<ScriptElement> iter = script.getScriptElements().iterator();
		for (Class<? extends N4TypeDeclaration> emt : expectedMT) {
			for (TypeAccessModifier eam : expectedAM) {
				annoIndex = (annoIndex + 1) % expectedAM.size();
				EObject stmt = iter.next();
				stmt = (stmt instanceof ExportDeclaration) ? ((ExportDeclaration) stmt).getExportedElement() : stmt;
				assertTrue(emt.isAssignableFrom(stmt.getClass()));
				assertTrue(stmt instanceof N4TypeDeclaration);
				N4TypeDeclaration decl = (N4TypeDeclaration) stmt;
				assertEquals("expected modifier for " + decl.getName(), eam,
						decl.getDefinedType().getTypeAccessModifier());
				if (expectedAnno.get(annoIndex) == null) {
					assertTrue(decl.getAnnotations().isEmpty());
				} else {
					assertEquals(1, decl.getAnnotations().size());
					assertEquals(expectedAnno.get(annoIndex), decl.getAnnotations().get(0).getName());
				}
			}
		}
	}

	@Test
	public void testMemberAccessModifiers() throws Exception {
		Script script = parseHelper.parse("""
				public class C {
					f0;
					private f1;
					project f2;
					@Internal protected f4;
					protected f5;
					@Internal public f6;
					public f7;
				}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		List<MemberAccessModifier> expectedAM = List.of(MemberAccessModifier.PROJECT, MemberAccessModifier.PRIVATE,
				MemberAccessModifier.PROJECT,
				MemberAccessModifier.PROTECTED_INTERNAL, MemberAccessModifier.PROTECTED,
				MemberAccessModifier.PUBLIC_INTERNAL, MemberAccessModifier.PUBLIC);
		List<String> expectedAnno = asList(new String[] { null, null, null, "Internal", null, "Internal", null });

		N4ClassDeclaration cdecl = (N4ClassDeclaration) script.getScriptElements().get(0);
		assertEquals(expectedAM.size(), cdecl.getOwnedMembers().size());

		int annoIndex = -1;
		Iterator<N4MemberDeclaration> iter = cdecl.getOwnedMembers().iterator();
		for (MemberAccessModifier eam : expectedAM) {
			annoIndex = (annoIndex + 1) % expectedAM.size();
			N4MemberDeclaration member = iter.next();
			assertTrue(member instanceof N4FieldDeclaration);

			assertEquals(eam, member.getDefinedTypeElement().getMemberAccessModifier());
			if (expectedAnno.get(annoIndex) == null) {
				assertTrue(member.getAnnotations().isEmpty());
			} else {
				assertEquals(1, member.getAnnotations().size());
				assertEquals(expectedAnno.get(annoIndex), member.getAnnotations().get(0).getName());
			}
		}

	}

	@Test
	public void testVarAccessModifiers_01() throws Exception {
		Script script = parseHelper.parse("""
				export @Internal public var x = "";
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		VariableStatement varStatement = (VariableStatement) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(varStatement.getDeclaredModifiers().contains(N4Modifier.PUBLIC));
	}

	@Test
	public void testVarAccessModifiers_02() throws Exception {
		Script script = parseHelper.parse("""
				export project var x = "";
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		VariableStatement varStatement = (VariableStatement) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(varStatement.getDeclaredModifiers().contains(N4Modifier.PROJECT));
	}

	@Test
	public void testVarAccessModifiers_03() throws Exception {
		Script script = parseESSuccessfully("""
				export @Internal public const x = ""
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		VariableStatement varStatement = (VariableStatement) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(varStatement.getDeclaredModifiers().contains(N4Modifier.PUBLIC));
	}

	@Test
	public void testVarAccessModifiers_04() throws Exception {
		Script script = parseESSuccessfully("""
				export project const x = ""
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		VariableStatement varStatement = (VariableStatement) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(varStatement.getDeclaredModifiers().contains(N4Modifier.PROJECT));
	}

	@Test
	public void testVarAccessModifiers_05() throws Exception {
		Script script = parseHelper.parse("""
				export @Internal public var x = "";
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		VariableStatement varStatement = (VariableStatement) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(varStatement.getDeclaredModifiers().contains(N4Modifier.PUBLIC));
	}

	@Test
	public void testVarAccessModifiers_06() throws Exception {
		Script script = parseHelper.parse("""
				export project var x = "";
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		VariableStatement varStatement = (VariableStatement) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(varStatement.getDeclaredModifiers().contains(N4Modifier.PROJECT));
	}

	@Test
	public void testFunctionAccessModifiers_01() throws Exception {
		Script script = parseHelper.parse("""
				export function x() {}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		FunctionDeclaration functionDecl = (FunctionDeclaration) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(functionDecl.getDeclaredModifiers().isEmpty());
		TypeAccessModifier accessModifier = functionDecl.getDefinedType().getTypeAccessModifier();
		assertEquals(TypeAccessModifier.PROJECT, accessModifier);
	}

	@Test
	public void testFunctionAccessModifiers_02() throws Exception {
		Script script = parseHelper.parse("""
				export @Internal public function x() {}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		FunctionDeclaration functionDecl = (FunctionDeclaration) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(functionDecl.getDeclaredModifiers().contains(N4Modifier.PUBLIC));
		TypeAccessModifier accessModifier = functionDecl.getDefinedType().getTypeAccessModifier();
		assertEquals(TypeAccessModifier.PUBLIC_INTERNAL, accessModifier);
	}

	@Test
	public void testFunctionAccessModifiers_03() throws Exception {
		Script script = parseHelper.parse("""
				export public function x() {}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		FunctionDeclaration functionDecl = (FunctionDeclaration) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(functionDecl.getDeclaredModifiers().contains(N4Modifier.PUBLIC));
		TypeAccessModifier accessModifier = functionDecl.getDefinedType().getTypeAccessModifier();
		assertEquals(TypeAccessModifier.PUBLIC, accessModifier);
	}

	@Test
	public void testFunctionAccessModifiers_04() throws Exception {
		Script script = parseHelper.parse("""
				export @Internal public function x() {}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		FunctionDeclaration functionDecl = (FunctionDeclaration) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(functionDecl.getDeclaredModifiers().contains(N4Modifier.PUBLIC));
		TypeAccessModifier accessModifier = functionDecl.getDefinedType().getTypeAccessModifier();
		assertEquals(TypeAccessModifier.PUBLIC_INTERNAL, accessModifier);
	}

	@Test
	public void testFunctionAccessModifiers_05() throws Exception {
		Script script = parseHelper.parse("""
				export project function x() {}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		FunctionDeclaration functionDecl = (FunctionDeclaration) ((ExportDeclaration) script.getScriptElements().get(0))
				.getExportedElement();
		assertTrue(functionDecl.getDeclaredModifiers().contains(N4Modifier.PROJECT));
		TypeAccessModifier accessModifier = functionDecl.getDefinedType().getTypeAccessModifier();
		assertEquals(TypeAccessModifier.PROJECT, accessModifier);
	}
}
