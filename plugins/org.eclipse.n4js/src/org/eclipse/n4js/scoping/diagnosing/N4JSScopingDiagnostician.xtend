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
package org.eclipse.n4js.scoping.diagnosing

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.xtext.diagnostics.DiagnosticMessage
import com.google.inject.Inject
import org.eclipse.n4js.n4JS.SuperLiteral
import org.eclipse.n4js.resource.ErrorAwareLinkingService
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

/**
 * This class provides enhanced error reporting in the case that
 * a reference can't be resolved by the N4JS scoping mechanisms.
 *
 * This class can be extended by more special cases
 * to provide the user with more informative error messages.
 */
class N4JSScopingDiagnostician {

	@Inject
	N4JSScopingConsumableMethodsDiagnosis consumableMethodsDiagnosis;

	@Inject
	ErrorAwareLinkingService linkingService;

	@Inject
	IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * Returns a custom {@link DiagnosticMessage} for the given unresolvable reference.
	 * May return {@code null} if no supported special case is applicable.
	 *
	 * Note that this methods already assumes, that the given reference actually isn't resolvable.
	 */
	public def DiagnosticMessage getMessageFor(EObject context, EReference reference, INode node) {
		// use linking service here to work with the same qualified name as we do in scoping
		val crossRefAsString = linkingService.getCrossRefNodeAsString(context, reference, node);

		if (null !== crossRefAsString && !crossRefAsString.equals("")) {
			val qualifiedName = qualifiedNameConverter.toQualifiedName(crossRefAsString);
			return diagnose(qualifiedName, context, reference);
		}
		return null;
	}

	// Handle {@link ParameterizedPropertyAccessExpressions}
	private def dispatch DiagnosticMessage diagnose(QualifiedName name, ParameterizedPropertyAccessExpression context, EReference reference) {
		if (context.target instanceof SuperLiteral) {
			// custom error message for referring to consumable methods via super
			return consumableMethodsDiagnosis.diagnose(name, context);
		}
	}

	// Default dispatch method
	private def dispatch DiagnosticMessage diagnose(QualifiedName name, EObject context, EReference reference) {
		return null;
	}

}
