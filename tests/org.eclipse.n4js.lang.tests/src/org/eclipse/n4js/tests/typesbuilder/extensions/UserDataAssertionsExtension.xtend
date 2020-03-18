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

import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.resource.UserDataMapper
import org.eclipse.n4js.tests.typesbuilder.utils.OrderedEmfFormatter
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.xtext.resource.IEObjectDescription

import static org.junit.Assert.*

/**
 */
class UserDataAssertionsExtension {

	def assertSerializedUserData(Iterable<IEObjectDescription> eoDescs, CharSequence expectedTypesSerialization, boolean enableUserDataCompare, N4JSResource resource) {
		val syntaxEoDesc = eoDescs.head;

		val fromUserData = UserDataMapper.getDeserializedModuleFromDescription(syntaxEoDesc,resource.URI)

		if(enableUserDataCompare) {
			compareUserData(syntaxEoDesc, fromUserData, expectedTypesSerialization.toString)
		}
	}

	def compareUserData(IEObjectDescription syntaxEoDesc, TModule fromUserData, String expectedTypesSerialization) {
		assertEquals("Stored user data " + syntaxEoDesc, expectedTypesSerialization, OrderedEmfFormatter.objToStr(fromUserData))
	}
}
