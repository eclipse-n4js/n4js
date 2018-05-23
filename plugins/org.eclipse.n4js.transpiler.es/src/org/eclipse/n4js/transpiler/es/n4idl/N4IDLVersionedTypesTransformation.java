/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.n4idl;

import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4idl.transpiler.utils.N4IDLTranspilerUtils;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Simple transformation which renames versioned types to prevent name conflicts between versioned types at runtime.
 */
public class N4IDLVersionedTypesTransformation extends Transformation {

	@Override
	public void analyze() {
		// nothing to analyze
	}

	@Override
	public void transform() {
		EcoreUtil2.getAllContentsOfType(getState().im, N4TypeDeclaration.class)
				.forEach(this::transformTypeDeclaration);
		EcoreUtil2.getAllContentsOfType(getState().im, FunctionDeclaration.class)
				.forEach(this::transformFunctionDeclaration);
	}

	/**
	 * Updates the name of versioned type declarations so that there are no name conflicts between different type
	 * versions.
	 */
	private void transformTypeDeclaration(N4TypeDeclaration typeDeclaration) {
		// for non-versioned types, this will default to the plain name
		typeDeclaration.setName(N4IDLTranspilerUtils.getVersionedInternalName(typeDeclaration));
	}

	/**
	 * Updates the name of versioned function declarations so that there are no name conflicts between different type
	 * versions.
	 */
	private void transformFunctionDeclaration(FunctionDeclaration functionDeclaration) {
		// for non-versioned types, this will default to the plain name
		functionDeclaration.setName(N4IDLTranspilerUtils.getVersionedInternalName(functionDeclaration));
	}

	@Override
	public void assertPreConditions() {
		// nothing to assert

	}

	@Override
	public void assertPostConditions() {
		// nothing to assert
	}

}
