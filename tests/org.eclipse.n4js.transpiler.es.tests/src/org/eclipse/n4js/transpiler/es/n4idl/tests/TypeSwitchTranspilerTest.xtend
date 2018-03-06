/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.n4idl.tests

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithMockProject
import org.eclipse.n4js.n4JS.N4JSFactory
import org.eclipse.n4js.transpiler.es.n4idl.assistants.TypeSwitchTranspiler
import org.eclipse.n4js.transpiler.es.tests.AbstractTranspilerTest
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.n4idl.tests.helper.AbstractN4IDLTypeSwitchTest

/**
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithMockProject)
class TypeSwitchTranspilerTest extends AbstractTranspilerTest {
	
	@Inject private extension AbstractN4IDLTypeSwitchTest
	@Inject private TypeSwitchTranspiler transpiler;
	
	@Test
	public def void testBasicTypeSwitchTranspilation() {
		val preamble = '''
		class A#1 {}
		class B#1 {}
		class C#1 {}'''
		
		val typeSwitch = computeSwitch("union{A#1, B#1, C#1}", preamble);
		
		val ident = N4JSFactory.eINSTANCE.createIdentifierRef
		ident.idAsText = "v";
		
		// TODO make this test work (do not use TranspilerBuilderBlocks)
	}
}