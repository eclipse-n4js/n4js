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
package org.eclipse.n4js.tests.n4JS.destructuring

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.DestructureUtils
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 * Tests for utility methods related to finding the root of a destructuring pattern.
 */
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
@RunWith(XtextRunner)
class RootOfDestructuringPatternTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper


	@Test
	def void testArrayBindingPattern() {
		val script = '''

			var [a,{prop1:b,prop2:[c,d]}] = null;

		'''.parseAndValidate;

		val pattern = script.eAllContents.filter(VariableBinding).head.pattern;
		val d = script.eAllContents.filter(VariableDeclaration).filter[name=='d'].head;
		assertNotNull(pattern);
		assertNotNull(d);

		assertSame(pattern, DestructureUtils.getRoot(d));
	}

	@Test
	def void testArrayBindingPattern_withDefaults() {
		val script = '''

			var [a=0,{prop1:b,prop2:[c=0,d=0]}={prop1:0,prop2:[0,0]}] = null;

		'''.parseAndValidate;

		val pattern = script.eAllContents.filter(VariableBinding).head.pattern;
		val d = script.eAllContents.filter(VariableDeclaration).filter[name=='d'].head;
		assertNotNull(pattern);
		assertNotNull(d);

		assertSame(pattern, DestructureUtils.getRoot(d));
	}

	@Test
	def void testArrayBindingPattern_expressionNotPartOfPattern() {
		val script = '''

			var [a,b="hello",c] = null;

		'''.parseAndValidate;

		val strLit = script.eAllContents.filter(StringLiteral).head;
		assertNotNull(strLit);

		assertNull(DestructureUtils.getRoot(strLit));
	}

	@Test
	def void testArrayBindingPattern_innerIndependentPattern() {
		val script = '''

			var x,y,z;
			var [a,b=([x,y,z]=null),c] = null;

		'''.parseAndValidate;

		val outerPattern = script.eAllContents.filter(VariableBinding).head.pattern;
		val innerPattern = script.eAllContents.filter(AssignmentExpression).head.lhs as ArrayLiteral;
		val y = script.eAllContents.filter(IdentifierRef).toList.get(1);
		assertNotNull(outerPattern);
		assertNotNull(innerPattern);
		assertEquals("y", y.id.name);

		val rootOfY = DestructureUtils.getRoot(y);
		assertNotSame(outerPattern, rootOfY); // important: inner pattern is not part of the outer pattern, so getRoot() should not proceed from y to outer pattern
		assertSame(innerPattern, rootOfY);
	}

	@Test
	def void testObjectBindingPattern() {
		val script = '''

			var {prop1:a,prop2:[b,{prop1:c,prop2:d}]} = {prop1:0,prop2:[0,{prop1:0,prop2:0}]}; // need RHS here only to avoid validation errors

		'''.parseAndValidate;

		val pattern = script.eAllContents.filter(VariableBinding).head.pattern;
		val d = script.eAllContents.filter(VariableDeclaration).filter[name=='d'].head;
		assertNotNull(pattern);
		assertNotNull(d);

		assertSame(pattern, DestructureUtils.getRoot(d));
	}

	@Test
	def void testObjectBindingPattern_withDefaults() {
		val script = '''

			var {prop1:a=0,prop2:[b=0,{prop1:c=0,prop2:d=0}={prop1:0,prop2:0}]=[0,{prop1:0,prop2:0}] as Iterable2<number,~Object with{prop1;prop2;}>} = {prop1:0,prop2:[0,{prop1:0,prop2:0}]}; // need RHS here only to avoid validation errors

		'''.parseAndValidate;

		val pattern = script.eAllContents.filter(VariableBinding).head.pattern;
		val d = script.eAllContents.filter(VariableDeclaration).filter[name=='d'].head;
		assertNotNull(pattern);
		assertNotNull(d);

		assertSame(pattern, DestructureUtils.getRoot(d));
	}

	@Test
	def void testObjectBindingPattern_expressionNotPartOfPattern() {
		val script = '''

			var {prop1:a,prop2:b="hello",prop3:c} = {prop1:0,prop2:"",prop3:0};

		'''.parseAndValidate;

		val strLit = script.eAllContents.filter(StringLiteral).head;
		assertNotNull(strLit);

		assertNull(DestructureUtils.getRoot(strLit));
	}

	@Test
	def void testObjectBindingPattern_innerIndependentPattern() {
		val script = '''

			var x,y,z;
			var {prop1:a,prop2:b=({x,y,z}={x:0,y:0,z:0}),prop3:c} = {prop1:0,prop2:{x:0,y:0,z:0},prop3:0};

		'''.parseAndValidate;

		val outerPattern = script.eAllContents.filter(VariableBinding).head.pattern;
		val innerPattern = script.eAllContents.filter(AssignmentExpression).head.lhs as ObjectLiteral;
		val y = script.eAllContents.filter(IdentifierRef).toList.get(1);
		assertNotNull(outerPattern);
		assertNotNull(innerPattern);
		assertEquals("y", y.id.name);

		val rootOfY = DestructureUtils.getRoot(y);
		assertNotSame(outerPattern, rootOfY); // important: inner pattern is not part of the outer pattern, so getRoot() should not proceed from y to outer pattern
		assertSame(innerPattern, rootOfY);
	}


	def private Script parseAndValidate(CharSequence scriptSrc) {
		val script = scriptSrc.parse
		script.validate;
		script.assertNoIssues;
		return script;
	}
}
