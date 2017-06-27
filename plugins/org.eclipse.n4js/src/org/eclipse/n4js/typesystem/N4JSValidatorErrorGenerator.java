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
package org.eclipse.n4js.typesystem;

import it.xsemantics.runtime.ErrorInformation;
import it.xsemantics.runtime.RuleFailedException;
import it.xsemantics.runtime.validation.XsemanticsValidatorErrorGenerator;
import it.xsemantics.runtime.validation.XsemanticsValidatorFilter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.inject.Inject;

/**
 * Creates error messages from {@link RuleFailedException}s. Delegates to {@link TypeSystemErrorExtensions} mostly; main
 * logic should be added / modified there.
 */
public class N4JSValidatorErrorGenerator extends XsemanticsValidatorErrorGenerator {

	@Inject
	private XsemanticsValidatorFilter filter;

	/**
	 * Copied from super class to create error messages from RuleFailedExceptions via method
	 * {@link TypeSystemErrorExtensions#compileMessage(RuleFailedException)}.
	 */
	@Override
	protected void generateErrors(
			ValidationMessageAcceptor validationMessageAcceptor,
			RuleFailedException ruleFailedException, EObject originalSource) {
		if (ruleFailedException == null) {
			return;
		}
		Iterable<RuleFailedException> allFailures = filter
				.filterRuleFailedExceptions(ruleFailedException);
		// the last information about a model element with error
		ErrorInformation lastErrorInformationWithSource = null;
		// we will use it to print error messages which do not have
		// an associated model element
		for (RuleFailedException ruleFailedException2 : allFailures) {
			lastErrorInformationWithSource = generateErrors(
					validationMessageAcceptor,
					// ================================================================
					// only change w.r.t. super-class method:
					TypeSystemErrorExtensions.compileMessage(ruleFailedException2),
					// ruleFailedException2.getMessage(),
					// ================================================================
					ruleFailedException2.getIssue(),
					filter.filterErrorInformation(ruleFailedException2),
					lastErrorInformationWithSource, originalSource);
		}
	}
}
