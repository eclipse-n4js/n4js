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
package org.eclipse.n4js.n4idl.validation.validators

import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.MigrationDeclaration
import org.eclipse.n4js.validation.validators.N4JSFunctionValidator
import org.eclipse.xtext.validation.Check

/**
 * Adaptation of N4JSFunctionValidator to avoid checking of function declaration name for N4IDL migrations. This
 * is necessary because migrations don't have a name.
 */
class N4IDLFunctionValidator extends N4JSFunctionValidator {

	/** additional check on top of {@link #checkFunctionName()} */
	@Check
	override checkFunctionDeclarationName(FunctionDeclaration functionDeclaration) {
		if (!(functionDeclaration instanceof MigrationDeclaration)) {
			super.checkFunctionDeclarationName(functionDeclaration);
		}
	}
}
