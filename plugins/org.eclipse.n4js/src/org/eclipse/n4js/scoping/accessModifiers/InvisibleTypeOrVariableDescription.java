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
package org.eclipse.n4js.scoping.accessModifiers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.IssueUserDataKeys.VIS_ILLEGAL_TYPE_ACCESS;
import org.eclipse.n4js.validation.IssueUserDataKeys.VIS_ILLEGAL_VARIABLE_ACCESS;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * This description wraps an invisible type or variable.
 */
public class InvisibleTypeOrVariableDescription extends AbstractDescriptionWithError {

	private String accessModifierSuggestion = "";

	/**
	 * Creates a new instance of this wrapping description.
	 *
	 * @param delegate
	 *            the decorated description.
	 */
	public InvisibleTypeOrVariableDescription(IEObjectDescription delegate) {
		super(delegate);
	}

	/**
	 * Creates a new instance of this wrapping description with the given accessSuggestion.
	 *
	 * @param delegate
	 *            the decorated description.
	 */
	public InvisibleTypeOrVariableDescription(IEObjectDescription delegate, String accessSuggestion) {
		super(delegate);
		this.accessModifierSuggestion = accessSuggestion;
	}

	/**
	 * Sets the access modifier suggestion.
	 *
	 * @param suggestion
	 *            modifier suggestion to make element visible
	 */
	public void setAccessModifierSuggestion(String suggestion) {
		this.accessModifierSuggestion = suggestion;
	}

	@Override
	public String getMessage() {
		EObject objectOrProxy = getEObjectOrProxy();
		String name = getName().getLastSegment();
		if (objectOrProxy instanceof TFunction) {
			return IssueCodes.VIS_ILLEGAL_FUN_ACCESS.getMessage(name);
		} else if (objectOrProxy instanceof Type) {
			return IssueCodes.VIS_ILLEGAL_TYPE_ACCESS.getMessage(name);
		}
		return IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS.getMessage(name);
	}

	@Override
	public String getIssueCode() {
		EObject objectOrProxy = getEObjectOrProxy();
		if (objectOrProxy instanceof TFunction) {
			return IssueCodes.VIS_ILLEGAL_FUN_ACCESS.name();
		} else if (objectOrProxy instanceof Type) {
			return IssueCodes.VIS_ILLEGAL_TYPE_ACCESS.name();
		}
		return IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS.name();
	}

	/*
	 * NOTE: To be updated if functions,variables and types shouldn't share one description type anymore.
	 */
	@Override
	public String getUserData(String name) {

		switch (name) {

		case VIS_ILLEGAL_TYPE_ACCESS.ACCESS_SUGGESTION:
			return this.accessModifierSuggestion;
		case VIS_ILLEGAL_TYPE_ACCESS.DECLARATION_OBJECT_URI:
			return this.getEObjectURI().toString();
		default:
			return null;
		}

	}

	/*
	 * NOTE: To be updated if functions,variables and types shouldn't share one description type anymore.
	 */
	@Override
	public String[] getUserDataKeys() {
		return new String[] { VIS_ILLEGAL_VARIABLE_ACCESS.ACCESS_SUGGESTION,
				VIS_ILLEGAL_VARIABLE_ACCESS.DECLARATION_OBJECT_URI };

	}
}
