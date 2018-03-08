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

import com.google.inject.Inject
import java.util.Optional
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.RelationalExpression
import org.eclipse.n4js.n4JS.RelationalOperator
import org.eclipse.n4js.n4JS.SuperLiteral
import org.eclipse.n4js.n4idl.versioning.MigrationUtils
import org.eclipse.n4js.resource.ErrorAwareLinkingService
import org.eclipse.xtext.diagnostics.DiagnosticMessage
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.nodemodel.INode

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
	N4JSScopingInstanceOfPrimitivTypeDiagnosis instanceOfPrimitiveTypeDiagnosis;
	
	@Inject
	N4IDLNoMatchingMigrationCandidateDiagnosis noMatchingMigrationCandidateDiagnosis;

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

	// Handle {@link IdentifierRef}s
	private def dispatch DiagnosticMessage diagnose(QualifiedName name, IdentifierRef context, EReference reference) {
		return handleNoMigrationCandidate(name, context)
			.orElseGet[handleInstanceOfPrimitiveType(name, context, reference)]
	}
	
	private def Optional<DiagnosticMessage> handleNoMigrationCandidate(QualifiedName name, IdentifierRef context) {
		if (MigrationUtils.isInMigration(context) && MigrationUtils.isMigrateCallIdentifier(context)) {
			return Optional.of(noMatchingMigrationCandidateDiagnosis.diagnose(name, context));
		}
		return Optional.empty();
	}
	
	private def DiagnosticMessage handleInstanceOfPrimitiveType(QualifiedName name, IdentifierRef context, EReference reference) {
		var container = context.eContainer;
		var containingFeature = context.eContainingFeature();
		// Skip all parenthesis-expression containers to allow
		// for expressions like '((int))'
		while (container instanceof ParenExpression) {
			containingFeature = container.eContainmentFeature;
			container = container.eContainer;
		}
		// Handle instanceof expressions
		if (container instanceof RelationalExpression) {
			// Check that the unresolved identifier is on the RHS of the
			// operator and the operator is INSTANCEOF
			if (container.op == RelationalOperator.INSTANCEOF &&
				containingFeature == N4JSPackage.Literals.RELATIONAL_EXPRESSION__RHS) {
				return instanceOfPrimitiveTypeDiagnosis.diagnose(name, container);
			}
		}
		
		return null;
	}


	// Default dispatch method
	private def dispatch DiagnosticMessage diagnose(QualifiedName name, EObject context, EReference reference) {
		return null;
	}

}
