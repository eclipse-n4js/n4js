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
package org.eclipse.n4js.tests.scoping;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.last;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.scoping.members.MemberScope;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for static scoping, combined with type system test.
 *
 * @see AT_185_ThisScopingTest
 * @see ThisScopingTest
 * @see MemberScope
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class StaticScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;
	@Inject
	N4JSTypeSystem ts;
	@Inject
	TypeSystemHelper tsh;
	@Inject
	IScopeProvider scopeProvider;

	public List<Issue> getIssues(Script script) {
		return toList(filter(valTestHelper.validate(script), issue -> issue.getCode() != IssueCodes.CFG_LOCAL_VAR_UNUSED
				&& issue.getCode() != IssueCodes.DFG_NULL_DEREFERENCE));
	}

	@Test
	public void testStaticGetterSetterAccess() throws Exception {
		Script script = parseHelper.parse("""
				class Callee {
				   private static myPrivateStaticField: string = "myPrivateStaticField";
				   private myPrivateNonStaticField: string = "myPrivateNonStaticField";

				   static get myPrivateStaticAccessor() {
						return this.myPrivateStaticField;
						 }

				   static set myPrivateStaticAccessor(myPrivateStaticParam: string) {
						/*this*/Callee.myPrivateStaticField = myPrivateStaticParam;
						 }

				   get myPrivateNonStaticAccessor() {
						return this.myPrivateNonStaticField;
						 }

				   set myPrivateNonStaticAccessor(myPrivateParam: string) {
						this.myPrivateNonStaticField = myPrivateParam;
						 }

				}
				class Caller {

					call() {
						Callee.myPrivateStaticAccessor = "a"
						var a = Callee.myPrivateStaticAccessor

						var callee: Callee = null;
						callee.myPrivateNonStaticAccessor = "a"
						a = callee.myPrivateNonStaticAccessor
					}
				}
				""");

		List<Issue> issues = getIssues(script);
		Assert.assertEquals(Strings.join(", ", issues), 0, issues.size());
	}

	@Test
	public void testStaticTypeInference() throws Exception {
		Script script = parseHelper.parse("""
				class A {
					static m() {
						return this; // type{A}
					}
					m() {
						return this; // A
					}
				}
				""");

		List<Issue> issues = getIssues(script);
		Assert.assertEquals(Strings.join(", ", issues), 0, issues.size());

		ThisLiteral thisInMethod1 = head(filter(script.eAllContents(), ThisLiteral.class));
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		TypeRef thisType1 = ts.upperBoundWithReopen(G, ts.type(G, thisInMethod1));
		Assert.assertTrue("expected type{A} but was " + thisType1.getClass(), thisType1 instanceof TypeTypeRef);
		TypeTypeRef classifierTypeRef1 = (TypeTypeRef) thisType1;
		String typeName1 = tsh.getStaticType(G, classifierTypeRef1).getName();
		Assert.assertEquals("A", typeName1);

		ThisLiteral thisInMethod2 = last(filter(script.eAllContents(), ThisLiteral.class));
		TypeRef thisType2 = ts.upperBoundWithReopen(G, ts.type(G, thisInMethod2));
		Assert.assertTrue("expected type{A} but was " + thisType2.getClass(),
				thisType2 instanceof ParameterizedTypeRef);
		ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef) thisType2;
		String typeName2 = parameterizedTypeRef.getDeclaredType().getName();
		Assert.assertEquals("A", typeName2);
	}

	@Test
	public void testTyping() throws Exception {
		Script script = parseHelper.parse("""
				class C {}
				var x = C
				var c: C;
				var y = c.constructor
				//var z1 = new y() // would raise error: "Cannot instantiate constructor{? extends C}."
				var z2 = new C()
				""");

		List<Issue> issues = getIssues(script);
		Assert.assertEquals(Strings.join(", ", issues), 0, issues.size());

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		VariableDeclaration varX = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "x")));
		TypeRef varXType = ts.tau(varX);
		Assert.assertTrue("type{C}", varXType instanceof TypeTypeRef);
		TypeTypeRef varXConstructorTypeRef = (TypeTypeRef) varXType;
		String varXTypeName = tsh.getStaticType(G, varXConstructorTypeRef).getName();
		Assert.assertEquals("C", varXTypeName);

		VariableDeclaration varC = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "c")));
		TypeRef varCType = ts.tau(varC);
		Assert.assertTrue(varCType instanceof ParameterizedTypeRef);
		ParameterizedTypeRef varCParameterizedTypeRef = (ParameterizedTypeRef) varCType;
		String varCTypeName = varCParameterizedTypeRef.getDeclaredType().getName();
		Assert.assertEquals("C", varCTypeName);

		NewExpression newCExpression = last(filter(script.eAllContents(), NewExpression.class));
		TypeRef identifierRefType = ts.tau(newCExpression.getCallee());
		Assert.assertTrue("ConstructorTypeRef expected but was " + identifierRefType.getClass(),
				identifierRefType instanceof TypeTypeRef);
		String identifierConstructorTypeName = tsh.getStaticType(G, (TypeTypeRef) identifierRefType).getName();
		Assert.assertEquals("C", identifierConstructorTypeName);

		VariableDeclaration varZ2 = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "z2")));
		TypeRef varZ2Type = ts.tau(varZ2);
		Assert.assertTrue("C expected but was " + varZ2Type.getClass(), varZ2Type instanceof ParameterizedTypeRef);
		ParameterizedTypeRef varZ2ParameterizedTypeRef = (ParameterizedTypeRef) varZ2Type;
		String varZ2TypeName = varZ2ParameterizedTypeRef.getDeclaredType().getName();
		Assert.assertEquals("C", varZ2TypeName);
	}

	@Test
	@Ignore("Check, if .constructor should return Function or constructor{T} and adapt test accordingly")
	public void testTypingForConstructorProperty() throws Exception {
		Script script = parseHelper.parse("""
				class C {}
				var C c;
				var y = c.constructor
				var z1 = new y()
				""");

		List<Issue> issues = valTestHelper.validate(script);
		Assert.assertEquals(Strings.join(", ", issues), 0, issues.size());

		VariableDeclaration varC = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "c")));
		TypeRef varCType = ts.tau(varC);
		Assert.assertTrue(varCType instanceof ParameterizedTypeRef);
		ParameterizedTypeRef varCParameterizedTypeRef = (ParameterizedTypeRef) varCType;
		String varCTypeName = varCParameterizedTypeRef.getDeclaredType().getName();
		Assert.assertEquals("C", varCTypeName);

		Iterator<ParameterizedPropertyAccessExpression> accesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);
		ParameterizedPropertyAccessExpression constructor = head(accesses);
		Assert.assertEquals("constructor", constructor.getProperty().getName());
		TypeRef constructorType = ts.tau(constructor.getProperty());

		// Assert.assertTrue("constructor{Y} expected but was " + constructorType.class, constructorType instanceof
		// ConstructorTypeRef);
		// ConstructorTypeRef constructorTypeRef = (ConstructorTypeRef) constructorType ;
		// val constructorTypeName = constructorTypeRef.staticType.getName();
		// Assert.assertEquals("C", constructorTypeName);
		Assert.assertTrue(constructorType instanceof ParameterizedTypeRef);
		ParameterizedTypeRef constructorTypeRef = (ParameterizedTypeRef) constructorType;
		String constructorTypeName = constructorTypeRef.getDeclaredType().getName();
		Assert.assertEquals("Function", constructorTypeName);

		VariableDeclaration varY = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "y")));
		TypeRef varYType = ts.tau(varY);

		// Assert.assertTrue("constructor{Y} expected but was " + constructorType.class, varYType instanceof
		// ConstructorTypeRef);
		// ConstructorTypeRef varYConstructorTypeRef = (ConstructorTypeRef) varCType ;
		Assert.assertTrue(varYType instanceof ParameterizedTypeRef);
		ParameterizedTypeRef varYTypeRef = (ParameterizedTypeRef) varYType;
		String varYTypeName = varYTypeRef.getDeclaredType().getName();
		Assert.assertEquals("Function", varYTypeName);

		VariableDeclaration varZ1 = head(filter(filter(script.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "z1")));
		TypeRef varZ1Type = ts.tau(varZ1);
		Assert.assertTrue("constructor{Y} expected but was " + varZ1Type.getClass(),
				varZ1Type instanceof ParameterizedTypeRef);
		ParameterizedTypeRef varZ1ParameterizedTypeRef = (ParameterizedTypeRef) varZ1Type;
		String varZ1TypeName = varZ1ParameterizedTypeRef.getDeclaredType().getName();

		// Assert.assertEquals("C", varZ1TypeName);
		Assert.assertEquals("Function", varZ1TypeName);
	}

	@Test
	@Ignore("Check, if .constructor should return Function or constructor{T} and adapt test accordingly")
	public void testStaticConstructorAccess() throws Exception {
		Script script = parseHelper.parse("""
				class A {
					one() {
						this.constructor.two()
					}
					static two() {
					}
					three() {
					}
				}
				""");

		List<Issue> issues = valTestHelper.validate(script);
		Assert.assertEquals(Strings.join(", ", issues), 0, issues.size());

		Iterator<ParameterizedPropertyAccessExpression> accesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);

		ParameterizedPropertyAccessExpression constructor = head(accesses);
		Assert.assertEquals("constructor", constructor.getProperty().getName());

		ParameterizedPropertyAccessExpression staticPropertyAccessExpression = last(accesses);
		Iterable<IEObjectDescription> staticResult = scopeProvider.getScope(staticPropertyAccessExpression,
				N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY).getAllElements();
		// println(staticResult);
		Assert.assertEquals(1, size(staticResult));
		Assert.assertEquals(1, size(
				filter(filter(map(staticResult, od -> od.getEObjectOrProxy()), TMember.class), m -> m.isStatic())));
	}
}
