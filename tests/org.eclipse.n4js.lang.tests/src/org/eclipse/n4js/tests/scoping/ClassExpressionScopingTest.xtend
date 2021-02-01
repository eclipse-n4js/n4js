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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class ClassExpressionScopingTest {

	@Inject extension ParseHelper<Script>

	@Test
	@Ignore("This test requires 'this' working")
	def void testScopingOfNestedN4ClassExpressions() {
		val script = '''
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
		'''.parse
		val class1 = (script.scriptElements.head as ExportDeclaration).exportedElement as N4ClassDeclaration
		Assert.assertNotNull(class1)
		val classExpressions = script.eAllContents.filter(N4ClassExpression).toList
		Assert.assertEquals(3, classExpressions.size)
		val class2 = classExpressions.filter[name == "Class2"].head
		Assert.assertNotNull(class2)
		val class3 = classExpressions.filter[name == "Class3"].head
		Assert.assertNotNull(class3)
		val class4 = classExpressions.filter[name == "Class4"].head
		Assert.assertNotNull(class4)

		val newExpressionsTargets = script.eAllContents.filter(NewExpression).map[callee].filter(IdentifierRef).toList
		Assert.assertEquals(4, newExpressionsTargets.size)
		val class1CallTarget = newExpressionsTargets.filter[id.name == "Class1"].map[id].filter(TClass).head
		Assert.assertNotNull(class1CallTarget)
		val class2CallTarget = newExpressionsTargets.filter[id.name == "Class2"].map[id].filter(TClass).head
		Assert.assertNotNull(class2CallTarget)
		val class3CallTarget = newExpressionsTargets.filter[id.name == "Class3"].map[id].filter(TClass).head
		Assert.assertNotNull(class3CallTarget)
		val class4CallTarget = newExpressionsTargets.filter[id.name == "Class4"].map[id].filter(TClass).head
		Assert.assertNotNull(class4CallTarget)

		Assert.assertSame("class1 correctly scoped", class1CallTarget.astElement, class1)
		Assert.assertSame("class2 correctly scoped", class2CallTarget.astElement, class2)
		Assert.assertSame("class3 correctly scoped", class3CallTarget.astElement, class3)
		Assert.assertSame("class4 correctly scoped", class4CallTarget.astElement, class4)

		val variableTypes = script.eAllContents.filter(N4FieldDeclaration).map[declaredTypeRefInAST].filter(ParameterizedTypeRef).map[declaredType].toList
		Assert.assertSame("class1 correctly scoped at type reference in variable declaration", (variableTypes.head as TClass).astElement, class1)

		val assignmentPairs = script.eAllContents.filter(AssignmentExpression).filter[EcoreUtil2.getContainerOfType(it, N4MethodDeclaration) !== null].filter[lhs instanceof IdentifierRef && rhs instanceof IdentifierRef].map[(lhs as IdentifierRef).id -> (rhs as IdentifierRef).id].toList
		// TODO: fix proxies: same:<org.eclipse.n4js.ts.model.impl.IdentifiableElementImpl@5c5f1c48 (eProxyURI: __synthetic0.n4js,js#|3)> was not:<org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl@6839ea58 (name: Class1, accessModifier: project, abstract: false)>
		// TODO: to make this test work, it is required that this works
		Assert.assertSame("class1 correctly scoped at assignment inside method", assignmentPairs.head.key, class1)
		Assert.assertSame("a correctly scoped at assignment inside method", assignmentPairs.head.value, null)
	}
}
