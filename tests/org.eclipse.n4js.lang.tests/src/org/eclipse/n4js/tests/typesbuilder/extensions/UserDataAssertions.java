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

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.tests.typesbuilder.utils.OrderedEmfFormatter;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

public class UserDataAssertions {

	public void assertSerializedUserData(Iterable<IEObjectDescription> eoDescs, CharSequence expectedTypesSerialization,
			boolean enableUserDataCompare, N4JSResource resource) {

		IEObjectDescription syntaxEoDesc = IterableExtensions.head(eoDescs);

		TModule fromUserData = UserDataMapper.getDeserializedModuleFromDescription(syntaxEoDesc, resource.getURI());

		if (enableUserDataCompare) {
			compareUserData(syntaxEoDesc, fromUserData, expectedTypesSerialization.toString());
		}
	}

	public void compareUserData(IEObjectDescription syntaxEoDesc, TModule fromUserData,
			String expectedTypesSerialization) {
		String fromUserDataStr = OrderedEmfFormatter.objToStr(fromUserData);
		String uriPrefixToTrim = new FileURI(new File("").getAbsoluteFile()).toString() + "/";
		String fromUserDataStrTrimmed = fromUserDataStr.replace(uriPrefixToTrim, "");
		assertEquals("Stored user data " + syntaxEoDesc, expectedTypesSerialization, fromUserDataStrTrimmed);
	}
}
