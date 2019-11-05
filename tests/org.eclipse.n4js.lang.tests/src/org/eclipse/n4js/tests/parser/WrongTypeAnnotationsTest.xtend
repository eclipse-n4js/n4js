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
package org.eclipse.n4js.tests.parser

import org.junit.Test
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef

/**
 */
class WrongTypeAnnotationsTest extends AbstractParserTest {

	/**
	 * This test failed during smoke tests due to a problem when resolving proxies.
	 * Error message:
	 * <pre>
	 * java.lang.Error: forward reference only allowed to identifiable subtrees;
	 * but was:
	 * org.eclipse.n4js.n4JS.impl.N4InterfaceDeclarationImpl@6beef8bc (declaredModifiers: [public], name: null) (typingStrategy: ?~)
	 * in ...
	 * at org.eclipse.n4js.postprocessing.AbstractProcessor.assertTrueIfRigid(AbstractProcessor.java:177)
	 * </pre>
	 * <p>Explanation of the problem:</p>
	 * <pre>
	 * ... foo(union{A,B} p) ...
	 * </pre>
	 * is a correct declaration with the old syntax. So no problems there.
	 * Now with the colon seperated type annotations, the parser completely messes up:
	 * It interprets the code as follows:
	 * <pre>
	 * ... foo(union) { A,B } ...
	 * </pre>
	 * The parser generates errors, so this is the "recovered" state. Actually an interesting solution...
	 * Anyway. It interprets "A,B" as comma expression in a body. Since everything is messed up, I'm absolutely not sure where to really fix the problem.
	 *
	 * <p>
	 * Due to this problem, and possible other problems, exceptions are swallowed in case of corrupt ASTs. See
	 * org.eclipse.n4js.scoping.N4JSScopeProvider.getScope(EObject, EReference)
	 * and
	 * org.eclipse.n4js.resource.N4JSPostProcessor.performPostProcessing(PostProcessingAwareResource, CancelIndicator)
	 */
	@Test
	def void testTypeAnnotationMayBeInterpretedAsBody() {
		'''
			public interface <T extends A> {
				<S> foo(union{A,C} p)
				abstract bar()
				baz(p: A?)
			}
		'''.parseESWithError
	// assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}
	
	@Test
	def void testWrongArrowFunctionSignature() {
		val script = '''
			let fun = number... a => b.sort;
		'''.parseESWithError
		val elements = script.scriptElements
		assertEquals(2, elements.size)
		val varStmt = elements.head as VariableStatement
		val varDecl = varStmt.varDeclsOrBindings.head as VariableDeclaration
		assertEquals('fun', varDecl.name)
		assertNull(varDecl.expression)
		val exprStmt = elements.last as ExpressionStatement
		val identifierRef = exprStmt.expression as IdentifierRef
		// this is really bad error recovery
		assertEquals('sort', identifierRef.idAsText)
	}
	
}
