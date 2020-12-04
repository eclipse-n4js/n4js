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

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.scoping.members.WrongTypingStrategyDescription;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.smith.Measurement;
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
	 * Filter for invalid content assist entries
	 */
	static public class N4JSCandidateFilter implements Predicate<IEObjectDescription> {
		@Override
		public boolean apply(IEObjectDescription candidate) {
			QualifiedName qualifiedName = candidate.getQualifiedName();
			final IEObjectDescription eObjectDescription = candidate;
			// Don't propose any erroneous descriptions.
			boolean valid = true;
			valid &= !isErrorDescription(eObjectDescription);
			valid &= !N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT.equals(qualifiedName.getFirstSegment());
			valid &= !N4TSQualifiedNameProvider.isModulePolyfill(qualifiedName);
			valid &= !N4TSQualifiedNameProvider.isPolyfill(qualifiedName);
			return valid;
		}

		boolean isErrorDescription(IEObjectDescription eObjectDescription) {
			IEObjectDescriptionWithError descriptionWithError = IEObjectDescriptionWithError
					.getDescriptionWithError(eObjectDescription);
			if (descriptionWithError != null) {
				descriptionWithError.getIssueCode();

				if (descriptionWithError instanceof WrongTypingStrategyDescription) {
					return false;
				}
				return true;
			}
			return false;
		}
	}

	@Inject
	private N4JSGrammarAccess n4jsGrammarAccess;

	@Inject
	private ImportsAwareReferenceProposalCreator importsAwareReferenceProposalCreator;

	@Inject
	private ContentAssistDataCollectors dataCollectors;

	@Override
	public void createProposals(Collection<ContentAssistContext> contexts, IIdeContentProposalAcceptor acceptor) {
		try (Measurement m = dataCollectors.dcCreateProposalsInner().getMeasurement()) {
			super.createProposals(contexts, acceptor);
		}
	}

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
				lookupCrossReference(TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType(),
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
				lookupCrossReference(ref, context, acceptor, new N4JSCandidateFilter());
			}
		}
	}

	@Override
	protected void _createProposals(Keyword keyword, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {

		EObject currentModel = context.getCurrentModel();
		EObject previousModel = context.getPreviousModel();
		if (currentModel instanceof ImportSpecifier || previousModel instanceof ImportSpecifier)
			return; // filter out all keywords if we are in the context of an import specifier
		if (currentModel instanceof ParameterizedPropertyAccessExpression ||
				previousModel instanceof ParameterizedPropertyAccessExpression)
			return; // filter out all keywords if we are in the context of a property access
		if (currentModel instanceof PropertyNameValuePair || previousModel instanceof PropertyNameValuePair)
			return; // filter out all keywords if we are in the context of a property name/value pair
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
	 */
	protected void lookupCrossReference(EReference reference, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor, Predicate<IEObjectDescription> filter) {

		if (reference.getEReferenceType().isSuperTypeOf(TypesPackage.Literals.TYPE)
				|| TypesPackage.Literals.TYPE.isSuperTypeOf(reference.getEReferenceType())) {

			EObject model = context.getCurrentModel();
			importsAwareReferenceProposalCreator.lookupCrossReference(model, reference, context, acceptor, filter);
		}
	}

}
