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
package org.eclipse.n4js.ts.types.util;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;

/**
 * Utility methods for easily creating a types model used in a test.
 */
public class TypesTestUtils {

	public static ParameterizedTypeRef ref(Type c) {
		ParameterizedTypeRef ref = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		ref.setDeclaredType(c);
		return ref;
	}

	public static TInterface interf(String name) {
		TInterface r = TypesFactory.eINSTANCE.createTInterface();
		r.setName(name);
		return r;
	}

	public static TClass clazz(String name) {
		TClass c = TypesFactory.eINSTANCE.createTClass();
		c.setName(name);
		return c;
	}

	public static TClass impl(TClass c, TInterface... roles) {
		for (TInterface r : roles) {
			c.getImplementedInterfaceRefs().add(ref(r));
		}
		return c;
	}

	public static TClass ext(TClass c, TClass superC) {
		c.setSuperClassRef(ref(superC));
		return c;
	}

	public static TInterface ext(TInterface c, TInterface... roles) {
		for (TInterface r : roles) {
			c.getSuperInterfaceRefs().add(ref(r));
		}
		return c;
	}

}
