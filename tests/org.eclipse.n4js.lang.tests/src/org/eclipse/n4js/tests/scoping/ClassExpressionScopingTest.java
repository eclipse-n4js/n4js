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
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class ClassExpressionScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	@Ignore("This test requires 'this' working")
	public void testScopingOfNestedN4ClassExpressions() throws Exception {
		Script script = parseHelper.parse("""
				export class Class1 {
				    Class1 class1 = new Class1();
					a = Class1;
					reset() {  class1 = a;  }
				    b = class Class2 {
				    	Class2 class2 = new Class2();
				    	reset() {  class2 = b;  }
				    	c = class Class3 {
				    		Class3 class3 = new Class3();
				    		reset() {  class3 = c;  }
				    		d = class Class4 {
				    			Class4 class4 = new Class4();
				    			reset() {  class4 = d;  }
				    		};
				    	};
				    };
				};
				""");
		N4ClassDeclaration class1 = (N4ClassDeclaration) ((ExportDeclaration) head(script.getScriptElements()))
				.getExportedElement();
		Assert.assertNotNull(class1);
		List<N4ClassExpression> classExpressions = IteratorExtensions
				.toList(IteratorExtensions.filter(script.eAllContents(), N4ClassExpression.class));
		Assert.assertEquals(3, classExpressions.size());
		N4ClassExpression class2 = head(filter(classExpressions, ce -> Objects.equals(ce.getName(), "Class2")));
		Assert.assertNotNull(class2);
		N4ClassExpression class3 = head(filter(classExpressions, ce -> Objects.equals(ce.getName(), "Class3")));
		Assert.assertNotNull(class3);
		N4ClassExpression class4 = head(filter(classExpressions, ce -> Objects.equals(ce.getName(), "Class4")));
		Assert.assertNotNull(class4);

		List<IdentifierRef> newExpressionsTargets = IteratorExtensions.toList(IteratorExtensions
				.filter(IteratorExtensions.map(IteratorExtensions.filter(script.eAllContents(), NewExpression.class),
						ne -> ne.getCallee()), IdentifierRef.class));

		Assert.assertEquals(4, newExpressionsTargets.size());
		TClass class1CallTarget = head(filter(map(filter(newExpressionsTargets,
				et -> Objects.equals(et.getId().getName(), "Class1")),
				et -> et.getId()), TClass.class));
		Assert.assertNotNull(class1CallTarget);
		TClass class2CallTarget = head(filter(map(filter(newExpressionsTargets,
				et -> Objects.equals(et.getId().getName(), "Class2")),
				et -> et.getId()), TClass.class));
		Assert.assertNotNull(class2CallTarget);
		TClass class3CallTarget = head(filter(map(filter(newExpressionsTargets,
				et -> Objects.equals(et.getId().getName(), "Class3")),
				et -> et.getId()), TClass.class));
		Assert.assertNotNull(class3CallTarget);
		TClass class4CallTarget = head(filter(map(filter(newExpressionsTargets,
				et -> Objects.equals(et.getId().getName(), "Class4")),
				et -> et.getId()), TClass.class));
		Assert.assertNotNull(class4CallTarget);

		Assert.assertSame("class1 correctly scoped", class1CallTarget.getAstElement(), class1);
		Assert.assertSame("class2 correctly scoped", class2CallTarget.getAstElement(), class2);
		Assert.assertSame("class3 correctly scoped", class3CallTarget.getAstElement(), class3);
		Assert.assertSame("class4 correctly scoped", class4CallTarget.getAstElement(), class4);

		List<Type> variableTypes = IteratorExtensions.toList(IteratorExtensions.map(
				IteratorExtensions.filter(IteratorExtensions.map(
						IteratorExtensions.filter(script.eAllContents(), N4FieldDeclaration.class),
						fd -> fd.getDeclaredTypeRefInAST()), ParameterizedTypeRef.class),
				ptr -> ptr.getDeclaredType()));

		Assert.assertSame("class1 correctly scoped at type reference in variable declaration",
				((TClass) head(variableTypes)).getAstElement(), class1);

		List<Pair<IdentifiableElement, IdentifiableElement>> assignmentPairs = IteratorExtensions.toList(
				IteratorExtensions.map(
						IteratorExtensions.filter(
								IteratorExtensions.filter(script.eAllContents(), AssignmentExpression.class),
								ae -> EcoreUtil2.getContainerOfType(ae, N4MethodDeclaration.class) != null
										&& ae.getLhs() instanceof IdentifierRef
										&& ae.getRhs() instanceof IdentifierRef),
						ae -> Pair.of(((IdentifierRef) ae.getLhs()).getId(), ((IdentifierRef) ae.getRhs()).getId())));

		// TODO: fix proxies: same:<org.eclipse.n4js.ts.model.impl.IdentifiableElementImpl@5c5f1c48 (eProxyURI:
		// __synthetic0.n4js,js#|3)> was not:<org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl@6839ea58 (name: Class1,
		// accessModifier: project, abstract: false)>
		// TODO: to make this test work, it is required that this works
		Assert.assertSame("class1 correctly scoped at assignment inside method", head(assignmentPairs).getKey(),
				class1);
		Assert.assertSame("a correctly scoped at assignment inside method", head(assignmentPairs).getValue(), null);
	}
}
