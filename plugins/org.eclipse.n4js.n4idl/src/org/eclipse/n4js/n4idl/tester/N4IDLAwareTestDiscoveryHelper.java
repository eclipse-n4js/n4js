/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.tester;

import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.naming.QualifiedNameComputer;
import org.eclipse.n4js.tester.TestDiscoveryHelper;
import org.eclipse.n4js.ts.types.TClass;

import com.google.inject.Inject;

/**
 * A {@link TestDiscoveryHelper} that is aware of the compiled name of versioned N4IDL classes.
 */
public class N4IDLAwareTestDiscoveryHelper extends TestDiscoveryHelper {
	@Inject
	private QualifiedNameComputer qualifiedNameComputer;

	@Override
	protected String getClassName(TClass clazz) {
		if (clazz.getDeclaredVersion() > 0) {
			return qualifiedNameComputer.getFullyQualifiedTypeName(clazz) + N4IDLGlobals.COMPILED_VERSION_SEPARATOR
					+ clazz.getDeclaredVersion();
		} else {
			return super.getClassName(clazz);
		}
	}
}
