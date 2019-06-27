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
package org.eclipse.n4js.validation.validators.flowgraphs;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.MissingReturnOrThrowAnalyser;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.validators.N4JSFlowgraphValidator;

/**
 * This validator validates all missing return or throw statements.
 */
public class MissingReturnOrThrowValidator implements FlowValidator {
	private final MissingReturnOrThrowAnalyser mrta;

	/** Constructor */
	public MissingReturnOrThrowValidator(MissingReturnOrThrowAnalyser missingReturnOrThrowAnalyser) {
		this.mrta = missingReturnOrThrowAnalyser;
	}

	@Override
	public FlowAnalyser getFlowAnalyser() {
		return mrta;
	}

	@Override
	public void checkResults(N4JSFlowgraphValidator fVali) {
		internalCheckMissingReturnDisallowed(fVali);
	}

	private void internalCheckMissingReturnDisallowed(N4JSFlowgraphValidator fVali) {
		Collection<FunctionOrFieldAccessor> mrtFunctions = mrta.getMRTFunctions();
		for (FunctionOrFieldAccessor fofa : mrtFunctions) {
			EStructuralFeature highlightFeature = getMarkedElement(fofa);
			String msg = IssueCodes.getMessageForFUN_MISSING_RETURN_OR_THROW_STATEMENT();
			fVali.addIssue(msg, fofa, highlightFeature, IssueCodes.FUN_MISSING_RETURN_OR_THROW_STATEMENT);
		}

	}

	private EStructuralFeature getMarkedElement(FunctionOrFieldAccessor fofa) {
		if (fofa instanceof FunctionDeclaration) {
			return N4JSPackage.Literals.FUNCTION_DECLARATION__NAME;
		}
		if (fofa instanceof N4MethodDeclaration) {
			return N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME;
		}
		if (fofa instanceof FunctionExpression) {
			return N4JSPackage.Literals.FUNCTION_EXPRESSION__NAME;
		}
		if (fofa instanceof GetterDeclaration) {
			return N4JSPackage.Literals.GETTER_DECLARATION__DEFINED_GETTER;
		}
		return null;
	}

}
