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
package org.eclipse.n4js.validation.validators;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.types.utils.LambdaUtils;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 */
public class N4JSLambdaValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	ContainerTypesHelper containerTypesHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * A top-level arrow function can't include uses of <code>arguments</code> and <code>this</code> as they lack an
	 * outer lexical context that would provide bindings for them.
	 */
	@Check
	public void checkTopLevelLambda(ArrowFunction arrowFun) {
		if (LambdaUtils.isTopLevelLambda(arrowFun)) {
			rejectUsagesOfThisInTopLevelLambda(arrowFun);
		}
	}

	/**
	 * Rejects uses of 'this' in top-level functions, which due to their top-level nature can't capture any 'this' from
	 * the enclosing context (same goes for 'arguments', by the way).
	 * <p>
	 * Precondition: the argument is a top-level lambda.
	 */
	private void rejectUsagesOfThisInTopLevelLambda(ArrowFunction topLevelLambda) {
		assert LambdaUtils.isLambda(topLevelLambda);
		Iterator<ThisLiteral> thisUsages = LambdaUtils.thisLiterals(topLevelLambda.getBody());
		while (thisUsages.hasNext()) {
			EObject thisUsage = thisUsages.next();
			addIssue(thisUsage, IssueCodes.KEY_THIS_REJECTED_IN_TOP_LEVEL_LAMBDA);
		}
	}
}
