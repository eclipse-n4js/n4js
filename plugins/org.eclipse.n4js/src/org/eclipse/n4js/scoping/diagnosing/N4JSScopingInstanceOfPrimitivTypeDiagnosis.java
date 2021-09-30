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
package org.eclipse.n4js.scoping.diagnosing;

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * A scoping diagnosis for the case that the user uses the instanceof operator with a primitive type.
 */
public class N4JSScopingInstanceOfPrimitivTypeDiagnosis extends ScopingDiagnosis<RelationalExpression> {

	/**
	 * Creates a {@link DiagnosticMessage} for instanceof expressions where the right-hand-side is a primitive type
	 * identifier.
	 *
	 * It is assumed that the given qualified name was extracted from the {@link IdentifierRef} on the rhs of the given
	 * relational expression.
	 *
	 * Returns null if not applicable.
	 *
	 * @param name
	 *            The unresolved name of an IdentifierRef on the RHS of the expression.
	 *
	 * @param expression
	 *            A pair of the instanceof-expression and the unresolved IdentifierRef.
	 */
	@Override
	DiagnosticMessage diagnose(QualifiedName name, RelationalExpression expression) {
		// only applicable for the instanceof operator
		if (expression.getOp() != RelationalOperator.INSTANCEOF) {
			return null;
		}

		// query built-in type scope for the unresolved name
		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(expression.eResource().getResourceSet());
		IEObjectDescription singleElement = builtInTypeScope.getSingleElement(name);

		// if there is no primitive type with this name, this diagnosis is not applicable
		if (null == singleElement) {
			return null;
		}

		// if we can find a primitive type for the qualified name
		if (singleElement.getEClass().getClassifierID() == TypesPackage.Literals.PRIMITIVE_TYPE.getClassifierID()) {
			// create special error message
			return createMessage(IssueCodes.TYS_INSTANCEOF_NOT_SUPPORTED_FOR_PRIMITIVE_TYPES,
					IssueCodes.getMessageForTYS_INSTANCEOF_NOT_SUPPORTED_FOR_PRIMITIVE_TYPES());
		} else {
			// the found element is not a primitive type, diagnosis not applicable
			return null;
		}

	}

}
