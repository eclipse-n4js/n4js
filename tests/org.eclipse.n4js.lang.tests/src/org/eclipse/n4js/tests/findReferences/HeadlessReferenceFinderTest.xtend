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
package org.eclipse.n4js.tests.findReferences

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.findReferences.HeadlessReferenceFinder
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class HeadlessReferenceFinderTest extends Assert {

	@Inject extension HeadlessReferenceFinder
	@Inject extension ParseHelper<Script>
	@Inject Provider<XtextResourceSet> resourceSetProvider;

	@Test def void test_01() {
		val script = '''
			enum E {
				A , B
			}
			var e: E = E.A
		'''.parse
		val e = script.scriptElements.head as N4EnumDeclaration
		val a = e.literals.head
		val references = a.findReferencesTo
		val resourceSet = script.eResource.resourceSet
		assertEquals(1, references.size)
		val set = references.map[ resourceSet.getEObject(sourceEObjectUri, false) ].toSet
		val varStmt = script.scriptElements.last as VariableStatement
		var varDecl = varStmt.varDecl.head
		var expr = varDecl.expression as ParameterizedPropertyAccessExpression
		assertTrue(set.contains(expr)) // E.A
	}

	@Test def void test_02() {
		val resourceSet = resourceSetProvider.get
		val scriptA = '''
			export class A {
			}
		'''.parse(URI.createURI('a.js'), resourceSet)
		val scriptB = '''
			import * as N from 'a'
			var a: N.A
		'''.parse(URI.createURI('b.js'), resourceSet)
		val aModule = scriptA.module
		val references = aModule.findReferencesTo
		assertEquals(1, references.size)
		val set = references.map[ resourceSet.getEObject(sourceEObjectUri, false) ].toSet
		val imp = scriptB.scriptElements.head as ImportDeclaration
		assertTrue(set.contains(imp))
	}

	@Test def void test_03() {
		val resourceSet = resourceSetProvider.get
		val scriptA = '''
			export class A {
			}
		'''.parse(URI.createURI('a.js'), resourceSet)
		val scriptB = '''
			import * as N from 'a'
			export var a: N.A
		'''.parse(URI.createURI('b.js'), resourceSet)
		val a = (scriptA.scriptElements.head as ExportDeclaration).exportedElement as N4ClassDeclaration
		val references = a.findReferencesTo
		assertEquals(1, references.size)
		val set = references.map[ resourceSet.getEObject(sourceEObjectUri, false) ].toSet
		val varStmt = (scriptB.scriptElements.last as ExportDeclaration).exportedElement as ExportedVariableStatement
		val varDecl = varStmt.varDecl.head as ExportedVariableDeclaration
		val typeRef = varDecl.declaredTypeRefInAST
		assertTrue(set.contains(typeRef))
	}

	@Test def void test_04() {
		val resourceSet = resourceSetProvider.get
		val scriptA = '''
			export class A {
				get p(): String {}
				set p(newP: String) {}
			}
		'''.parse(URI.createURI('a.js'), resourceSet)
		val scriptB = '''
			import * as N from 'a'
			var a: N.A = null
			a.p = ""
		'''.parse(URI.createURI('b.js'), resourceSet)
		val a = (scriptA.scriptElements.head as ExportDeclaration).exportedElement as N4ClassDeclaration
		val setter = a.ownedSetters.head
		val references = setter.findReferencesTo
		assertEquals(1, references.size)
		val set = references.map[ resourceSet.getEObject(sourceEObjectUri, false) ].toSet
		val assignment = (scriptB.scriptElements.last as ExpressionStatement).expression as AssignmentExpression
		val propertyAccess = assignment.lhs as ParameterizedPropertyAccessExpression
		assertTrue(set.contains(propertyAccess))
	}

}
