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
package org.eclipse.n4js.ui.utils;

import java.util.function.Function;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * Implementation of the {@link IInputValidator} that uses specialized {@link Function} for actual validation. Can be
 * created with method reference.
 */
public class InputFunctionalValidator implements IInputValidator {

	final Function<String, String> isValidFunction;

	private InputFunctionalValidator(Function<String, String> isValidFunction) {
		this.isValidFunction = isValidFunction;
	}

	@Override
	public String isValid(String newText) {
		return isValidFunction.apply(newText);
	}

	/** Factory for creating validator from lambda expression or method reference. */
	public static InputFunctionalValidator from(Function<String, String> isValidFunction) {
		return new InputFunctionalValidator(isValidFunction);
	}
}
