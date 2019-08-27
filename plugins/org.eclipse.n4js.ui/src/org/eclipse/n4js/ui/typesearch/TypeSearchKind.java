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
package org.eclipse.n4js.ui.typesearch;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * Enumeration of available N4JS type search kinds.
 */
public enum TypeSearchKind {

	/** The class search kind. */
	CLASS {
		@Override
		public boolean matches(EClass eclass) {
			return TypesPackage.eINSTANCE.getTClass().isSuperTypeOf(eclass);
		}
	},

	/** The interface search kind. */
	INTERFACE {
		@Override
		public boolean matches(EClass eclass) {
			return TypesPackage.eINSTANCE.getTInterface().isSuperTypeOf(eclass);
		}
	},

	/** The enum search kind. */
	ENUM {
		@Override
		public boolean matches(EClass eclass) {
			return TypesPackage.eINSTANCE.getTEnum().isSuperTypeOf(eclass);
		}
	},

	/** Search kind for all N4JS types. */
	ALL_TYPES {
		@Override
		public boolean matches(EClass eclass) {
			return CLASS.matches(eclass) || INTERFACE.matches(eclass) || ENUM.matches(eclass);
		}
	},

	/** The function search kind, not including methods. */
	FUNCTION {
		@Override
		public boolean matches(EClass eclass) {
			return TypesPackage.eINSTANCE.getTFunction().isSuperTypeOf(eclass)
					&& !TypesPackage.eINSTANCE.getTMethod().isSuperTypeOf(eclass);
		}
	},

	/** The search kind for variables, including const. */
	VARIABLE {
		@Override
		public boolean matches(EClass eclass) {
			return TypesPackage.eINSTANCE.getTVariable().isSuperTypeOf(eclass);
		}
	},

	/** Search kind for all N4JS types and functions. */
	EVERYTHING {
		@Override
		public boolean matches(EClass eclass) {
			return ALL_TYPES.matches(eclass) || FUNCTION.matches(eclass) || VARIABLE.matches(eclass);
		}
	};

	/**
	 * Returns with {@code true} if the {@link EClass} argument represents the corresponding {@link TypeSearchKind
	 * search kind}. Otherwise return with {@code false}.
	 *
	 * @param eclass
	 *            the class to check whether it represents the type search kind.
	 * @return {@code true} if the search kind represents the corresponding {@link EClass}.
	 */
	public abstract boolean matches(final EClass eclass);

}
