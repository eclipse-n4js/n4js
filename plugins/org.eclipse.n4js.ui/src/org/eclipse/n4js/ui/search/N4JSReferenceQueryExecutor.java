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
package org.eclipse.n4js.ui.search;

import org.eclipse.emf.ecore.EReference;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.ui.search.LabellingReferenceQueryExecutor;

/**
 * Adds more filter criteria to the UI find references represenation.
 */
public class N4JSReferenceQueryExecutor extends LabellingReferenceQueryExecutor {

	@Override
	protected boolean isRelevantToUser(EReference reference) {
		return super.isRelevantToUser(reference)
				&& N4JSPackage.Literals.TYPE_DEFINING_ELEMENT__DEFINED_TYPE != reference &&
				N4JSPackage.Literals.N4_ENUM_LITERAL__DEFINED_LITERAL != reference &&
				N4JSPackage.Literals.N4_FIELD_DECLARATION__DEFINED_FIELD != reference &&
				N4JSPackage.Literals.GETTER_DECLARATION__DEFINED_GETTER != reference &&
				N4JSPackage.Literals.SETTER_DECLARATION__DEFINED_SETTER != reference &&
				N4JSPackage.Literals.EXPORTED_VARIABLE_DECLARATION__DEFINED_VARIABLE != reference &&
				N4JSPackage.Literals.SCRIPT__MODULE != reference;
	}
}
