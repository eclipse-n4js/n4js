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
package org.eclipse.n4js.tests.typesbuilder.extensions

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.resource.IEObjectDescription

import static org.junit.Assert.*

/**
 */
class TypeEqualityAssertionsExtension {

	def assertExpectedTypes(String phase, Iterable<IEObjectDescription> eoDescs, List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs) {
		val eobjects = eoDescs.map[EObjectOrProxy]
		val instanceCheck = getInstanceCheck(phase, eobjects)
		for(i : 0..expectedTypeToNamePairs.size - 1) {
			val expectedTypeToNamePair = expectedTypeToNamePairs.get(i)
			instanceCheck.apply(i, expectedTypeToNamePair)
		}
	}

	def private getInstanceCheck(String phase, Iterable<EObject> exported) {[
		int index, Pair<? extends Class<? extends EObject>, String> pair |
			val expectedTypeElement = pair.key
			val expectedTypeElementName = pair.value
			val typeElement = exported.get(index)
			val assignable = expectedTypeElement.isAssignableFrom(typeElement.class);
			assertTrue(phase + ": " + '''Expecting «expectedTypeElement» with name «expectedTypeElementName» at position «index» but was «typeElement»'''.toString, assignable)
			assertEqualsNames(phase, typeElement, expectedTypeElementName
			)
	]}

	def private void assertEqualsNames(String phase, EObject element, String expectedName) {
		val actualName = switch it : element {
					Type: name
					TFunction: name
					TVariable: name
					TModule: qualifiedName
					default: "unknown type: " + element.toString()
				};
		assertEquals(phase + ": Exported " + element.eClass.name + " has wrong name", expectedName, actualName)
	}
}
