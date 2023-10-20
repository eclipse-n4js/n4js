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
package org.eclipse.n4js.tests.typesbuilder.extensions;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.Pair;

public class TypeEqualityAssertionsExtension {

	public void assertExpectedTypes(String phase, Iterable<IEObjectDescription> eoDescs,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs) {

		List<EObject> eobjects = toList(map(eoDescs, od -> od.getEObjectOrProxy()));
		for (int index = 0; index < expectedTypeToNamePairs.size(); index++) {
			Pair<? extends Class<? extends EObject>, String> pair = expectedTypeToNamePairs.get(index);

			Class<? extends EObject> expectedTypeElement = pair.getKey();
			String expectedTypeElementName = pair.getValue();
			EObject typeElement = eobjects.get(index);
			boolean assignable = expectedTypeElement.isAssignableFrom(typeElement.getClass());
			assertTrue(phase + ": "
					+ "Expecting «expectedTypeElement» with name «expectedTypeElementName» at position «index» but was «typeElement»",
					assignable);
			assertEqualsNames(phase, typeElement, expectedTypeElementName);
		}
	}

	private void assertEqualsNames(String phase, EObject element, String expectedName) {
		String actualName = "unknown type: " + element.toString();
		if (element instanceof Type) {
			actualName = ((Type) element).getName();
		} else if (element instanceof TVariable) {
			actualName = ((TVariable) element).getName();
		} else if (element instanceof TModule) {
			actualName = ((TModule) element).getQualifiedName();
		}
		assertEquals(phase + ": Exported " + element.eClass().getName() + " has wrong name", expectedName, actualName);
	}
}
