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
package org.eclipse.n4js.tests.findReferences;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.tooling.findReferences.HeadlessReferenceFinder;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class HeadlessReferenceFinderTest extends Assert {

	@Inject
	HeadlessReferenceFinder refFinder;
	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	@Test
	public void test_01() throws Exception {
		Script script = parseHelper.parse("""
				enum E {
					A , B
				}
				var e: E = E.A
				""");
		EList<ScriptElement> elems = script.getScriptElements();
		N4EnumDeclaration e = (N4EnumDeclaration) elems.get(0);
		N4EnumLiteral a = e.getLiterals().get(0);
		List<IReferenceDescription> references = refFinder.findReferencesTo(a);
		ResourceSet resourceSet = script.eResource().getResourceSet();
		assertEquals(1, references.size());
		Set<Object> set = toSet(map(references, ref -> resourceSet.getEObject(ref.getSourceEObjectUri(), false)));
		VariableStatement varStmt = (VariableStatement) elems.get(elems.size() - 1);
		VariableDeclaration varDecl = varStmt.getVarDecl().get(0);
		ParameterizedPropertyAccessExpression expr = (ParameterizedPropertyAccessExpression) varDecl.getExpression();
		assertTrue(set.contains(expr)); // E.A
	}

	@Test
	public void test_02() throws Exception {
		ResourceSet resourceSet = resourceSetProvider.get();
		Script scriptA = parseHelper.parse("""
				export class A {
				}
				""", URI.createURI("a.js"), resourceSet);
		Script scriptB = parseHelper.parse("""
				import * as N from 'a'
				var a: N.A
				""", URI.createURI("b.js"), resourceSet);
		TModule aModule = scriptA.getModule();
		List<IReferenceDescription> references = refFinder.findReferencesTo(aModule);
		assertEquals(1, references.size());
		Set<Object> set = toSet(map(references, ref -> resourceSet.getEObject(ref.getSourceEObjectUri(), false)));
		ImportDeclaration imp = (ImportDeclaration) scriptB.getScriptElements().get(0);
		assertTrue(set.contains(imp));
	}

	@Test
	public void test_03() throws Exception {
		ResourceSet resourceSet = resourceSetProvider.get();
		Script scriptA = parseHelper.parse("""
				export class A {
				}
				""", URI.createURI("a.js"), resourceSet);
		Script scriptB = parseHelper.parse("""
				import * as N from 'a'
				export var a: N.A
				""", URI.createURI("b.js"), resourceSet);
		N4ClassDeclaration a = (N4ClassDeclaration) ((ExportDeclaration) scriptA.getScriptElements().get(0))
				.getExportedElement();
		List<IReferenceDescription> references = refFinder.findReferencesTo(a);
		assertEquals(1, references.size());
		Set<Object> set = toSet(map(references, ref -> resourceSet.getEObject(ref.getSourceEObjectUri(), false)));
		EList<ScriptElement> bElems = scriptB.getScriptElements();
		VariableStatement varStmt = (VariableStatement) ((ExportDeclaration) bElems.get(bElems.size() - 1))
				.getExportedElement();
		VariableDeclaration varDecl = varStmt.getVarDecl().get(0);
		TypeRef typeRef = varDecl.getDeclaredTypeRefInAST();
		assertTrue(set.contains(typeRef));
	}

	@Test
	public void test_04() throws Exception {
		ResourceSet resourceSet = resourceSetProvider.get();
		Script scriptA = parseHelper.parse("""
				export class A {
					get p(): String {}
					set p(newP: String) {}
				}
				""", URI.createURI("a.js"), resourceSet);
		Script scriptB = parseHelper.parse("""
				import * as N from 'a'
				var a: N.A = null
				a.p = ""
				""", URI.createURI("b.js"), resourceSet);
		N4ClassDeclaration a = (N4ClassDeclaration) ((ExportDeclaration) scriptA.getScriptElements().get(0))
				.getExportedElement();
		N4SetterDeclaration setter = a.getOwnedSetters().get(0);
		List<IReferenceDescription> references = refFinder.findReferencesTo(setter);
		assertEquals(1, references.size());
		Set<Object> set = toSet(map(references, ref -> resourceSet.getEObject(ref.getSourceEObjectUri(), false)));
		EList<ScriptElement> bElems = scriptB.getScriptElements();
		AssignmentExpression assignment = (AssignmentExpression) ((ExpressionStatement) bElems.get(bElems.size() - 1))
				.getExpression();
		ParameterizedPropertyAccessExpression propertyAccess = (ParameterizedPropertyAccessExpression) assignment
				.getLhs();
		assertTrue(set.contains(propertyAccess));
	}

}
