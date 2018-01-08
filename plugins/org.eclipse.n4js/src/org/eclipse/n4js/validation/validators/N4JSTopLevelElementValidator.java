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
package org.eclipse.n4js.validation.validators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * A validator to issue error messages on top-level elements based on the {@link JavaScriptVariant} in use.
 */
public class N4JSTopLevelElementValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private JavaScriptVariantHelper javaScriptVariantHelper;

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Checks whether the current {@link JavaScriptVariant} allows for top-level statements and issues errors
	 * accordingly.
	 */
	@Check
	public void checkTopLevelElements(Script script) {
		if (!javaScriptVariantHelper.allowTopLevelStatements(script)) {
			script.getScriptElements().stream().forEach(this::handleScriptElement);
		}
	}

	/**
	 * Adds an issue for the given element if it is considered a statement {@link #isStatement}.
	 */
	private void handleScriptElement(ScriptElement element) {
		if (isStatement(element) && element.eContainer().eContainer() == null) {
			final String variantName = javaScriptVariantHelper.getVariantName(element);
			addIssue(IssueCodes.getMessageForAST_TOP_LEVEL_STATEMENTS(variantName), element,
					IssueCodes.AST_TOP_LEVEL_STATEMENTS);
		}
	}

	private boolean isStatement(EObject element) {
		return element instanceof Statement && !(element instanceof FunctionDeclaration);
	}
}
