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

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * Implementation of the {@link IInputValidator} composed of several validators. All validators are called in order
 * (depth first). Returns eagerly, that is will return first error message encountered, and will not proceed to call
 * pending validators.
 */
public class InputComposedValidator implements IInputValidator {

	final Collection<IInputValidator> validators;

	private InputComposedValidator(Collection<IInputValidator> validators) {
		this.validators = validators;
	}

	@Override
	public String isValid(String newText) {
		String result = null;
		for (IInputValidator validator : validators) {
			result = validator.isValid(newText);
			if (result != null)
				break;
		}

		return result;
	}

	/** Factory for composing multiple validators */
	public static InputComposedValidator compose(IInputValidator... validators) {
		return new InputComposedValidator(Arrays.asList(validators));
	}
}
