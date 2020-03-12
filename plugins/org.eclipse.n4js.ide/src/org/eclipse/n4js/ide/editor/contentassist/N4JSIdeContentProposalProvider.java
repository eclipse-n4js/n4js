/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.editor.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportsAwareReferenceProposalCreator;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 *
 */
public class N4JSIdeContentProposalProvider extends IdeContentProposalProvider {

	/**
	 * TODO ADD JAVADOC
	 */
	static public class N4JSCandidateFilter implements Predicate<IEObjectDescription> {
		@Override
		public boolean apply(IEObjectDescription candidate) {
			QualifiedName qualifiedName = candidate.getQualifiedName();
			final IEObjectDescription eObjectDescription = candidate;
			// Don't propose any erroneous descriptions.
			return !IEObjectDescriptionWithError.isErrorDescription(eObjectDescription)
					&& !N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT.equals(qualifiedName.getFirstSegment())
					&& !N4TSQualifiedNameProvider.isModulePolyfill(qualifiedName)
					&& !N4TSQualifiedNameProvider.isPolyfill(qualifiedName);
		}
	}

	@Inject
	private N4JSGrammarAccess n4jsGrammarAccess;

	@Inject
	private ImportsAwareReferenceProposalCreator importsAwareReferenceProposalCreator;

	@Override
	protected void _createProposals(CrossReference crossReference, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {

		if (context.getCurrentModel() == null || context.getCurrentModel().eIsProxy()) {
			return;
		}

		// because rule "TypeReference" in TypeExpressions.xtext (overridden in N4JS.xtext) is a wildcard fragment,
		// the standard behavior of the super method would fail in the following case:
		ParserRule containingParserRule = GrammarUtil.containingParserRule(crossReference);
		if (containingParserRule != n4jsGrammarAccess.getTypeReferenceRule()) {
			String featureName = GrammarUtil.containingAssignment(crossReference).getFeature();
			if (featureName.equals(TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType().getName())) {
				lookupCrossReference(crossReference,
						TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType(),
						context, acceptor, new N4JSCandidateFilter());
			}
		}
		if (!GrammarUtil.isDatatypeRule(containingParserRule)) {
			EReference ref;
			if (containingParserRule.isWildcard()) {
				// TODO we need better ctrl flow analysis here
				// The cross reference may come from another parser rule then the current model
				ref = GrammarUtil.getReference(crossReference, context.getCurrentModel().eClass());
			} else {
				ref = GrammarUtil.getReference(crossReference);
			}
			if (ref == null && containingParserRule == n4jsGrammarAccess.getTypeReferenceRule()) {
				String featureName = GrammarUtil.containingAssignment(crossReference).getFeature();
				if (featureName.equals(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE.getName())) {
					ref = TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE;
				} else if (featureName
						.equals(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__AST_NAMESPACE.getName())) {
					ref = TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_AstNamespace();
				}
			}
			if (ref != null) {
				lookupCrossReference(crossReference, ref, context, acceptor, new N4JSCandidateFilter());
			}
		}

		// super._createProposals(crossReference, context, acceptor);
	}

	@Override
	protected void _createProposals(Keyword keyword, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {
		EObject currentModel = context.getCurrentModel();
		EObject previousModel = context.getPreviousModel();
		if (currentModel instanceof ParameterizedPropertyAccessExpression ||
				previousModel instanceof ParameterizedPropertyAccessExpression)
			return; // filter out all keywords if we are in the context of a property access
		if (currentModel instanceof JSXElement || previousModel instanceof JSXElement)
			return; // filter out all keywords if we are in the context of a JSX element
		if (!Character.isAlphabetic(keyword.getValue().charAt(0)))
			return; // filter out operators
		if (keyword.getValue().length() < 5)
			return; // filter out short keywords
		super._createProposals(keyword, context, acceptor);
	}

	@Override
	protected void _createProposals(Assignment assignment, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {
		AbstractElement terminal = assignment.getTerminal();
		if (terminal instanceof CrossReference) {
			createProposals(terminal, context, acceptor);
		}
	}

	/**
	 * For type proposals, use a dedicated proposal creator that will query the scope, filter it and apply the proposal
	 * factory to all applicable {@link IEObjectDescription descriptions}.
	 *
	 * @param crossReference
	 *            unused for now
	 */
	protected void lookupCrossReference(CrossReference crossReference, EReference reference,
			ContentAssistContext context, IIdeContentProposalAcceptor acceptor, Predicate<IEObjectDescription> filter) {

		// if (haveScannedScopeFor(context.getCurrentModel(), reference)) {
		// return; // avoid scanning the same scope twice
		// }

		if (reference.getEReferenceType().isSuperTypeOf(TypesPackage.Literals.TYPE) ||
				TypesPackage.Literals.TYPE.isSuperTypeOf(reference.getEReferenceType())) {

			// if we complete a reference to something that is aware of imports, enable automatic import insertion by
			// using the
			// importAwareReferenceProposalCreator
			// String ruleName = null;
			// if (crossReference.getTerminal() instanceof RuleCall) {
			// ruleName = ((RuleCall) crossReference.getTerminal()).getRule().getName();
			// }

			importsAwareReferenceProposalCreator.lookupCrossReference(
					context.getCurrentModel(),
					reference,
					context,
					acceptor,
					filter,
					super.getProposalCreator());
		}
	}

}
