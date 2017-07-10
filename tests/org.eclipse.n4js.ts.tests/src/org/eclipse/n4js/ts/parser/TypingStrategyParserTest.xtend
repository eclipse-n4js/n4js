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
package org.eclipse.n4js.ts.parser

import com.google.inject.Inject
import org.eclipse.n4js.ts.TypesInjectorProvider
import org.eclipse.n4js.ts.types.TypeDefs
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import org.junit.Test
import static org.junit.Assert.*
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef

@RunWith(XtextRunner)
@InjectWith(TypesInjectorProvider)
/**
 * @see IDE-680
 */
class TypingStrategyParserTest {

	@Inject
	extension ParseHelper<TypeDefs>

	@Test
	def void testDefinitionSiteStructuralTypeWithClasses() {
		val typedefs = '''
			public class C {}
			public class ~D {}
		'''.parse;
		assertNotNull(typedefs);
		assertTrue(typedefs.eResource.errors.toString, typedefs.eResource.errors.empty)

		val C = typedefs.types.get(0) as TClass
		val D = typedefs.types.get(1) as TClass

		assertEquals(TypingStrategy.DEFAULT, C.typingStrategy)
		assertEquals(TypingStrategy.STRUCTURAL, D.typingStrategy)
	}

	@Test
	def void testDefinitionSiteStructuralTypeWithClasses_NoStructuralField() {
		val typedefs = '''
			public class ~~D {}
		'''.parse;
		assertNotNull(typedefs);
		assertFalse("At definition site, structural field typing must not be possible", typedefs.eResource.errors.empty)
	}

	@Test
	def void testUseSiteStructuralTypeWithClasses() {
		val typedefs = '''
			public class C {
				public fNominal(p: C): void
				public fStructual(p: ~C): void
				public fStucturalField(p: ~~C): void
			}
		'''.parse;
		assertNotNull(typedefs);
		assertTrue(typedefs.eResource.errors.toString, typedefs.eResource.errors.empty)

		val C = typedefs.types.get(0) as TClass
		assertEquals(TypingStrategy.DEFAULT, C.typingStrategy)
		assertTypingStrategyOfFPar(TypingStrategy.NOMINAL, C.ownedMembers.get(0) as TMethod)
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL, C.ownedMembers.get(1) as TMethod)
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL_FIELDS, C.ownedMembers.get(2) as TMethod)

	}

	def void assertTypingStrategyOfFPar(TypingStrategy expectedStrategy, TMethod method) {
		val ptrOfFPar = method.fpars.head.typeRef as ParameterizedTypeRef
		assertEquals("Expected " + expectedStrategy.getName + " but was " + ptrOfFPar.typingStrategy?.getName + ": ",
			expectedStrategy, ptrOfFPar.typingStrategy);
	}

}
