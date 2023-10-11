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
package org.eclipse.n4js.tests.n4JS.destructuring;

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for utility methods related to finding the root of a destructuring pattern.
 */
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
@RunWith(XtextRunner.class)
public class RootOfDestructuringPatternTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testArrayBindingPattern() {
		Script script = parseAndValidate("""

				var [a,{prop1:b,prop2:[c,d]}] = null;

				""");

		BindingPattern pattern = head(filter(script.eAllContents(), VariableBinding.class)).getPattern();
		VariableDeclaration d = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "d")));
		assertNotNull(pattern);
		assertNotNull(d);

		assertSame(pattern, DestructureUtils.getRoot(d));
	}

	@Test
	public void testArrayBindingPattern_withDefaults() {
		Script script = parseAndValidate("""

				var [a=0,{prop1:b,prop2:[c=0,d=0]}={prop1:0,prop2:[0,0]}] = null;

				""");

		BindingPattern pattern = head(filter(script.eAllContents(), VariableBinding.class)).getPattern();
		VariableDeclaration d = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "d")));
		assertNotNull(pattern);
		assertNotNull(d);

		assertSame(pattern, DestructureUtils.getRoot(d));
	}

	@Test
	public void testArrayBindingPattern_expressionNotPartOfPattern() {
		Script script = parseAndValidate("""

				var [a,b="hello",c] = null;

				""");

		StringLiteral strLit = head(filter(script.eAllContents(), StringLiteral.class));
		assertNotNull(strLit);

		assertNull(DestructureUtils.getRoot(strLit));
	}

	@Test
	public void testArrayBindingPattern_innerIndependentPattern() {
		Script script = parseAndValidate("""

				var x,y,z;
				var [a,b=([x,y,z]=null),c] = null;

				""");

		BindingPattern outerPattern = head(filter(script.eAllContents(), VariableBinding.class)).getPattern();
		ArrayLiteral innerPattern = (ArrayLiteral) head(filter(script.eAllContents(), AssignmentExpression.class))
				.getLhs();
		IdentifierRef y = toList(filter(script.eAllContents(), IdentifierRef.class)).get(1);
		assertNotNull(outerPattern);
		assertNotNull(innerPattern);
		assertEquals("y", y.getId().getName());

		EObject rootOfY = DestructureUtils.getRoot(y);
		// important: inner pattern is not part of the outer pattern, so getRoot() should not proceed from y to outer
		// pattern
		assertNotSame(outerPattern, rootOfY);
		assertSame(innerPattern, rootOfY);
	}

	@Test
	public void testObjectBindingPattern() {
		Script script = parseAndValidate(
				"""

						var {prop1:a,prop2:[b,{prop1:c,prop2:d}]} = {prop1:0,prop2:[0,{prop1:0,prop2:0}]}; // need RHS here only to avoid validation errors

						""");

		BindingPattern pattern = head(filter(script.eAllContents(), VariableBinding.class)).getPattern();
		VariableDeclaration d = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "d")));
		assertNotNull(pattern);
		assertNotNull(d);

		assertSame(pattern, DestructureUtils.getRoot(d));
	}

	@Test
	public void testObjectBindingPattern_withDefaults() {
		Script script = parseAndValidate(
				"""

						var {prop1:a=0,prop2:[b=0,{prop1:c=0,prop2:d=0}={prop1:0,prop2:0}]=[0,{prop1:0,prop2:0}] as Iterable2<number,~Object with{prop1;prop2;}>} = {prop1:0,prop2:[0,{prop1:0,prop2:0}]}; // need RHS here only to avoid validation errors

						""");

		BindingPattern pattern = head(filter(script.eAllContents(), VariableBinding.class)).getPattern();
		VariableDeclaration d = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "d")));
		assertNotNull(pattern);
		assertNotNull(d);

		assertSame(pattern, DestructureUtils.getRoot(d));
	}

	@Test
	public void testObjectBindingPattern_expressionNotPartOfPattern() {
		Script script = parseAndValidate("""

				var {prop1:a,prop2:b="hello",prop3:c} = {prop1:0,prop2:"",prop3:0};

				""");

		StringLiteral strLit = head(filter(script.eAllContents(), StringLiteral.class));
		assertNotNull(strLit);

		assertNull(DestructureUtils.getRoot(strLit));
	}

	@Test
	public void testObjectBindingPattern_innerIndependentPattern() {
		Script script = parseAndValidate("""

				var x,y,z;
				var {prop1:a,prop2:b=({x,y,z}={x:0,y:0,z:0}),prop3:c} = {prop1:0,prop2:{x:0,y:0,z:0},prop3:0};

				""");

		BindingPattern outerPattern = head(filter(script.eAllContents(), VariableBinding.class)).getPattern();
		ObjectLiteral innerPattern = (ObjectLiteral) head(filter(script.eAllContents(), AssignmentExpression.class))
				.getLhs();
		IdentifierRef y = toList(filter(script.eAllContents(), IdentifierRef.class)).get(1);
		assertNotNull(outerPattern);
		assertNotNull(innerPattern);
		assertEquals("y", y.getId().getName());

		EObject rootOfY = DestructureUtils.getRoot(y);
		// important: inner pattern is not part of the outer pattern, so getRoot() should not proceed from y to outer
		// pattern
		assertNotSame(outerPattern, rootOfY);
		assertSame(innerPattern, rootOfY);
	}

	private Script parseAndValidate(CharSequence scriptSrc) {
		Script script;
		try {
			script = parseHelper.parse(scriptSrc);
			valTestHelper.validate(script);
			valTestHelper.assertNoIssues(script);
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}
}
