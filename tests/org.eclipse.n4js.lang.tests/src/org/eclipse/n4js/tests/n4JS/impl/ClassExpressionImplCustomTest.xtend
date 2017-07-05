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
package org.eclipse.n4js.tests.n4JS.impl

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4ClassDefinition
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.ts.types.TClass
import java.util.Iterator
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.resource.XtextResource
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator.*
import static org.junit.Assert.*

@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class ClassExpressionImplCustomTest {
	@Inject
	extension ParseHelper<Script>

	@Inject
	extension ValidationTestHelper

	@Inject ContainerTypesHelper containerTypesHelper;

	@Test
	def void testGenericParserSimpleWithBinding() {
		val program =
		'''
			var C = class {
				a: any;
				b: any;
				c: any;
			};
		'''.parse // only parsed, not linked

		// syntax ok?
		assertTrue(program.eResource.errors.empty)
		// bindings ok?
allowClassExpressions[
		program.assertNoErrors;
]

		val classExpression = program.eAllContents.filter(N4ClassExpression).head.definedType as TClass
		val allMembers = containerTypesHelper.fromContext(program).members(classExpression)
		assertTrue(classExpression.ownedMembers.forall[m|allMembers.contains(m)])
	}

	@Test
	def void testAllMembersInSync() {
		val String programAsString = '''
			class X {}
			class Y {
				abc: any = null;
				foox(): any {
					this.abc;
				}
			}
			class Z extends Y {
			}
		'''
		val program = programAsString.parse
		// TODO adapt after Xcore is running
		val Iterator<N4ClassDefinition> classDefs = program.eAllContents.filter(N4ClassDefinition);
		val classX = classDefs.next;
		val classY = classDefs.next;
		var classZ = classDefs.next;
		val typeX = classX.definedType as TClass
		val typeY = classY.definedType as TClass
		val typeZ = classZ.definedType as TClass

		val allMembersX = containerTypesHelper.fromContext(program).members(typeX)
		var allMembersY = containerTypesHelper.fromContext(program).members(typeY)
		var allMembersZ = containerTypesHelper.fromContext(program).members(typeZ)

		assertEquals(2, allMembersY.size-allMembersX.size)
		assertEquals(2, allMembersZ.size-allMembersX.size)

		var nonBaseMembers = allMembersZ.filter[! allMembersX.contains(it)];
		var firstMember = nonBaseMembers.head
		assertEquals('abc', firstMember.name)

		// rename abc to axc
		val resource = program.eResource as XtextResource
		val uriZ = resource.getURIFragment(classZ)
		resource.update(programAsString.indexOf('abc') + 1, 1, 'x')

		// no partial parsing, classZ is no longer contained
		assertNull(classZ.eResource)
		classZ = resource.getEObject(uriZ) as N4ClassDefinition
		// trigger derived state computation
		resource.contents

		val reparsedType = classZ.definedType as TClass
		allMembersZ = containerTypesHelper.fromContext(classZ).members(reparsedType)
		assertEquals(2, allMembersZ.size-allMembersX.size)

		nonBaseMembers = allMembersZ.filter[! allMembersX.contains(it)];
		firstMember = nonBaseMembers.head
		assertEquals('axc', firstMember.name)
	}
}
