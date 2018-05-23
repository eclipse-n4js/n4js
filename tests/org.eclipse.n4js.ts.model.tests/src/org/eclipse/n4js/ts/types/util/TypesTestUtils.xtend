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
package org.eclipse.n4js.ts.types.util

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypesFactory

/**
 * Utility methods for easily creating a types model used in a test.
 */
class TypesTestUtils {
	def static ParameterizedTypeRef ref(Type c) {
		val ref = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		ref.declaredType = c;
		return ref;
	}

	def static TInterface interf(String name) {
		val r = TypesFactory.eINSTANCE.createTInterface();
		r.name = name;
		return r;
	}

	def static TClass clazz(String name) {
		val c = TypesFactory.eINSTANCE.createTClass();
		c.name = name;
		return c;
	}


	def static TClass impl(TClass c, TInterface... roles) {
		for (r : roles) {
			c.implementedInterfaceRefs.add(ref(r));
		}
		return c;
	}

	def static TClass ext(TClass c, TClass superC) {
		c.superClassRef = ref(superC);
		return c;
	}

	def static TInterface ext(TInterface c, TInterface... roles) {
		for (r : roles) {
			c.superInterfaceRefs.add(ref(r));
		}
		return c;
	}

}
