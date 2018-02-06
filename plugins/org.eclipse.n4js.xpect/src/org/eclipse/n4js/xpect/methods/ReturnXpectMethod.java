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
package org.eclipse.n4js.xpect.methods;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.validation.validators.FunctionFullReport;
import org.eclipse.n4js.validation.validators.ReturnMode;
import org.eclipse.n4js.validation.validators.ReturnOrThrowAnalysis;

/**
 */
public class ReturnXpectMethod {

	// Is it possible to Inject this instance?
	private final ReturnOrThrowAnalysis rotAnalyzer = new ReturnOrThrowAnalysis();

	/**
	 *
	 * @param expectation
	 *            Parsed expected String
	 * @param resource
	 *            effected Resource of test
	 * @param offset
	 *            actual position
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg2=OFFSET)?")
	public void returnOrThrow(@StringExpectation IStringExpectation expectation,
			@ThisResource XtextResource resource, INode offset) {

		if (offset == null) {
			return;
		}

		String actual = evaluateReturnBehaviour(offset);

		expectation.assertEquals(actual);

	}

	private String evaluateReturnBehaviour(INode offset) {

		EObject context = NodeModelUtils.findActualSemanticObjectFor(offset);

		// Function can be evaluated on their own and should be self-contained
		if (context instanceof FunctionDeclaration) {
			FunctionDeclaration funDecl = (FunctionDeclaration) context;
			ReturnMode result = rotAnalyzer.exitBehaviour(funDecl.getBody().getStatements());

			return returnModeToString(result);

		} else if (context instanceof Statement) {
			// Other snippet might encounter scope-issues in break/continue with labels.

			ReturnMode result = rotAnalyzer.evalSubstatement((Statement) context);
			return returnModeToString(result);

		} else {

			// System.out.println("*** Xpect 'returnOrThrow' called on wrong context: " + context);
		}

		return "";
	}

	/**
	 * @param result
	 *            computed result
	 * @return String representation of the Result, NPE for Null
	 */
	private String returnModeToString(ReturnMode result) {
		if (result == null)
			return "NPE";

		return result.toString();
	}

	/**
	 * Checks whether the dead code analysis produces the expected result: dead code found or no dead code found.
	 *
	 * @param expectation
	 *            "dead code" or "no dead code"
	 * @param offset
	 *            the offset where the XPECT comment is used
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void deadCode(@StringExpectation IStringExpectation expectation,
			// @ThisResource XtextResource resource,
			INode offset) {
		if (offset == null) {
			return;
		}

		String actual = evaluateDeadCode(offset);

		expectation.assertEquals(actual);

	}

	private String evaluateDeadCode(INode offset) {

		EObject context = NodeModelUtils.findActualSemanticObjectFor(offset);

		// Function can be evaluated on their own and should be self-contained
		if (context instanceof FunctionDeclaration) {
			FunctionDeclaration funDecl = (FunctionDeclaration) context;
			Block body = funDecl.getBody();
			FunctionFullReport result = rotAnalyzer.exitBehaviourWithFullReport(body == null ? null : body
					.getStatements());

			return reportDeadCode(result);

		} else if (context instanceof Statement) {

			FunctionFullReport result = rotAnalyzer.evalSubstatementF((Statement) context);
			return reportDeadCode(result);

		} else {
			// Don't know
		}

		return "";
	}

	private String reportDeadCode(FunctionFullReport result) {
		if (result == null)
			return "NPE";
		if (result.deadCode.isEmpty())
			return "no dead code";
		else
			return "dead code";
	}

}
