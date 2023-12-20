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
package org.eclipse.n4js.validation.helper;

import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_IMPLICIT_DEFAULT_PARAM;
import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_INITIALIZER_ILLEGAL_AWAIT_CALL;
import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_INITIALIZER_ILLEGAL_FORWARD_REFERENCE;
import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_VARIADIC_ONLY_LAST;
import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_VARIADIC_WITH_INITIALIZER;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.validation.ASTStructureValidator;
import org.eclipse.n4js.validation.validators.N4JSFunctionValidator;
import org.eclipse.xtext.EcoreUtil2;

/**
 * This class contains validations that are called from both {@link ASTStructureValidator} and the
 * {@link N4JSFunctionValidator}.
 */
public class FunctionValidationHelper {

	/**
	 * Helper interface used to pass a function for consuming issues which are created during the validation.
	 */
	static public interface TripleConsumer<U, V, W> {
		/**
		 * Accepts an issue created during the validation of functions.
		 *
		 * @param msg
		 *            The issue message.
		 * @param id
		 *            The issue id.
		 * @param eObj
		 *            The {@link EObject} related to the issue.
		 */
		void accept(U msg, V id, W eObj);
	}

	/**
	 * IDEBUG-211, IDE-145</br>
	 * Check for variadic, default, and missing initializer forward references in formal parameters. </br>
	 * Note: This method is called for the {@link N4JSFunctionValidator}.
	 */
	static public <T extends EObject> void internalCheckFormalParameters(
			T[] fpars,
			Predicate<T> variadic,
			Predicate<T> hasInitAssgn,
			Function<T, String> name,
			TripleConsumer<String, String, EObject> issueConsumer) {

		List<T> fparsL = Arrays.asList(fpars);

		for (T fPar : fparsL) {
			// check 1. and 2. only if the we are not in a FunctionDefinition to avoid double checking!
			// Parameters of FunctionDefintions are also checked by the ASTStructureValidator,
			// which invokes the following method already for parameter.
			boolean isInFunctionDefinition = fPar.eContainer() instanceof FunctionDefinition;
			if (!isInFunctionDefinition)
				internalCheckFormalParameter(fparsL, fPar, variadic, hasInitAssgn, issueConsumer);

			// 3. Reference to succeeding parameter
			if (hasInitAssgn.test(fPar)) {
				int fpPos = fparsL.indexOf(fPar);
				List<IdentifierRef> irs = EcoreUtil2.getAllContentsOfType(fPar, IdentifierRef.class);
				for (IdentifierRef ir : irs) {
					Object id = ir.getId();
					if (id instanceof SyntaxRelatedTElement) {
						id = ((SyntaxRelatedTElement) id).eGet(
								TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT, false);
					}
					if (fparsL.indexOf(id) >= fpPos) {
						String msg = FUN_PARAM_INITIALIZER_ILLEGAL_FORWARD_REFERENCE.getMessage();
						issueConsumer.accept(msg, FUN_PARAM_INITIALIZER_ILLEGAL_FORWARD_REFERENCE.name(), ir);
					}
				}

				List<AwaitExpression> awaits = EcoreUtil2.getAllContentsOfType(fPar, AwaitExpression.class);
				if (!awaits.isEmpty()) {
					String paramName = "";
					if (fPar instanceof TFormalParameter) {
						paramName = ((TFormalParameter) fPar).getName();
					}
					if (fPar instanceof FormalParameter) {
						paramName = ((FormalParameter) fPar).getName();
					}
					for (AwaitExpression await : awaits) {
						String msg = FUN_PARAM_INITIALIZER_ILLEGAL_AWAIT_CALL.getMessage(paramName);
						issueConsumer.accept(msg, FUN_PARAM_INITIALIZER_ILLEGAL_AWAIT_CALL.name(), await);
					}
				}
			}
		}

		// 4. Implicit default formal parameters
		boolean initAssgnVisited = false;
		Iterator<T> iter = fparsL.iterator();
		while (iter.hasNext() && !initAssgnVisited) {
			initAssgnVisited = hasInitAssgn.test(iter.next());
		}
		while (initAssgnVisited && iter.hasNext()) {
			T fpar = iter.next();
			if (!hasInitAssgn.test(fpar) && !variadic.test(fpar)) {
				String msg = FUN_PARAM_IMPLICIT_DEFAULT_PARAM.getMessage(name.apply(fpar));
				issueConsumer.accept(msg, FUN_PARAM_IMPLICIT_DEFAULT_PARAM.name(), fpar);
			}
		}
	}

	/**
	 * Checks only whether a formal parameter is not last or also a default parameter.</br>
	 * Note: This method is called from the {@link ASTStructureValidator}, that means, that the AST building is not
	 * finished yet.
	 */
	static public <T extends EObject> void internalCheckFormalParameter(
			List<T> fPars,
			T fPar,
			Predicate<T> variadic,
			Predicate<T> hasInitAssgn,
			TripleConsumer<String, String, EObject> issueConsumer) {

		if (variadic.test(fPar)) {
			int fpPos = fPars.indexOf(fPar);

			// 1. Variadic is last
			if (fpPos != fPars.size() - 1) { // is not last?
				String msg = FUN_PARAM_VARIADIC_ONLY_LAST.getMessage();
				issueConsumer.accept(msg, FUN_PARAM_VARIADIC_ONLY_LAST.name(), fPar);
			}

			// 2. Both variadic and initializerAssignment
			if (hasInitAssgn.test(fPar)) {
				String msg = FUN_PARAM_VARIADIC_WITH_INITIALIZER.getMessage();
				issueConsumer.accept(msg, FUN_PARAM_VARIADIC_WITH_INITIALIZER.name(), fPar);
			}
		}
	}

}
