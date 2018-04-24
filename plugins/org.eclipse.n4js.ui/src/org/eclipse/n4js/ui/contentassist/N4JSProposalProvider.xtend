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
package org.eclipse.n4js.ui.contentassist

import com.google.common.base.Predicate
import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.jface.viewers.StyledString
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ui.proposals.imports.ImportsAwareReferenceProposalCreator
import org.eclipse.n4js.ui.proposals.linkedEditing.N4JSCompletionProposal
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.GrammarUtil
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor
import org.eclipse.n4js.n4JS.JSXElement

/**
 * see http://www.eclipse.org/Xtext/documentation.html#contentAssist on how to customize content assistant
 */
class N4JSProposalProvider extends AbstractN4JSProposalProvider {

	@Inject
	ImportsAwareReferenceProposalCreator importAwareReferenceProposalCreator

	@Inject
	IQualifiedNameProvider qualifiedNameProvider

	@Inject
	IQualifiedNameConverter qualifiedNameConverter

	@Inject
	private N4JSGrammarAccess n4jsGrammarAccess;

	override completeRuleCall(RuleCall ruleCall, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor) {
		val calledRule = ruleCall.getRule();
		if(!"INT".equals(calledRule.getName)) {
			super.completeRuleCall(ruleCall, contentAssistContext, acceptor)
		}
	}

	override protected lookupCrossReference(CrossReference crossReference, ContentAssistContext contentAssistContext, ICompletionProposalAcceptor acceptor) {
		lookupCrossReference(crossReference, contentAssistContext, acceptor, new N4JSCandidateFilter());
	}
	override protected void lookupCrossReference(CrossReference crossReference, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor, Predicate<IEObjectDescription> filter) {
		// because rule "TypeReference" in TypeExpressions.xtext (overridden in N4JS.xtext) is a wildcard fragment,
		// the standard behavior of the super method would fail in the following case:
		val containingParserRule = GrammarUtil.containingParserRule(crossReference);
		if (containingParserRule === n4jsGrammarAccess.typeReferenceRule) {
			val featureName = GrammarUtil.containingAssignment(crossReference).getFeature();
			if (featureName == TypeRefsPackage.eINSTANCE.parameterizedTypeRef_DeclaredType.name) {
				lookupCrossReference(crossReference, TypeRefsPackage.eINSTANCE.parameterizedTypeRef_DeclaredType,
					contentAssistContext, acceptor, filter);
			}
		}
		// standard behavior:
		super.lookupCrossReference(crossReference, contentAssistContext, acceptor, filter);
	}

	/**
	 * For type proposals, use a dedicated proposal creator that will query the scope, filter it and
	 * apply the proposal factory to all applicable {@link IEObjectDescription descriptions}.
	 */
	override protected lookupCrossReference(CrossReference crossReference, EReference reference, ContentAssistContext context, ICompletionProposalAcceptor acceptor, Predicate<IEObjectDescription> filter) {
		if (reference.EReferenceType.isSuperTypeOf(TypesPackage.Literals.TYPE) || TypesPackage.Literals.TYPE.isSuperTypeOf(reference.EReferenceType)) {
			// if we complete a reference to something that is aware of imports, enable automatic import insertion by using the
			//   importAwareReferenceProposalCreator
			var String ruleName = null;
			if (crossReference.terminal instanceof RuleCall) {
				ruleName = (crossReference.terminal as RuleCall).rule.name
			}
			val proposalFactory = getProposalFactory(ruleName, context)
			importAwareReferenceProposalCreator.lookupCrossReference(context.currentModel, reference, context, acceptor, filter, proposalFactory);
		} else {
			super.lookupCrossReference(crossReference, reference, context, acceptor, filter)
		}
	}

	/**
	 * <b>TEMPORARY WORK-AROUND</b>
	 * <p>
	 * Our proposal provider implementation makes heavy use of some Xtext base implementations intended for Java (esp.
	 * the abstract base class AbstractJavaBasedContentProposalProvider) which in many places has '.' hard-code as the
	 * delimiter of qualified names (e.g. FQNPrefixMatcher). This wasn't a problem as long as we used '.' as delimiter
	 * in N4JS qualified names, but this was changed to '/' as of GHOLD-162. Thus, the base implementations intended for
	 * Java now get confused.
	 * </p><p>
	 * <b>TEMPORARY PARTIAL SOLUTION</b>: to not confuse the implementations intended for Java, we do not provide our
	 * "official" N4JSQualifiedNameComputer obtained via injection, but instead provide a fake IQualifiedNameComputer
	 * that uses '.' as delimiter.<br>
	 * However, this <em>will</em> break in case of project/folder names containing '.' (which is valid in Javascript).
	 * </p><p>
	 * TODO IDE-2227 fix handling of qualified names in content assist or create follow-up task
	 * </p>
	 *
	 * @see AbstractJavaBasedContentProposalProvider
	 */
	override protected getProposalFactory(String ruleName, ContentAssistContext contentAssistContext) {
		return new DefaultProposalCreator(contentAssistContext, ruleName,
			new IQualifiedNameConverter.DefaultImpl() // provide a fake implementation using '.' as delimiter like Java
		);
	}

	/**
	 * Provide reasonable labels for qualified proposals. The produced labels look like this:
	 * <pre>
	 * MyTypeName - com.acme.my_module
	 * </pre>
	 * and
	 * <pre>
	 * MyAliasedName - com.acme.my_module alias for MyTypeName
	 * </pre>
	 * respectively.
	 *
	 */
	override protected getStyledDisplayString(EObject element, String qualifiedName, String shortName) {
		val (QualifiedName, String)=>StyledString stringifier = [ it, name |
			val result = new StyledString(name)
			if (it.segmentCount > 1) {
				if (it.lastSegment.endsWith(name)) {
					result.append(getTypeVersionString(element) +' - ' + qualifiedNameConverter.toString(it.skipLast(1)), StyledString.QUALIFIER_STYLER)
				} else {
					// aliased - print the alias and the original name
					result.append(' - ' + qualifiedNameConverter.toString(it.skipLast(1)) + ' alias for ' + it.lastSegment + getTypeVersionString(element), StyledString.QUALIFIER_STYLER)
				}
			}
			return result
		]
		if (qualifiedName == shortName) {
			val parsedQualifiedName = qualifiedNameConverter.toQualifiedName(qualifiedName)
			if (parsedQualifiedName.segmentCount == 1) {
				return tryGetDisplayString(element, stringifier, shortName) ?: stringifier.apply(parsedQualifiedName, shortName)
			}
			return stringifier.apply(parsedQualifiedName, parsedQualifiedName.lastSegment)
		}
		return tryGetDisplayString(element, stringifier, shortName) ?: stringifier.apply(qualifiedNameConverter.toQualifiedName(qualifiedName), shortName)
	}

	/**
	 * If the element is an instance of {@link TClassifier} this method
	 * returns a user-faced string description of the version information.
	 *
	 * Otherwise, this method returns an empty string.
	 */
	private def String getTypeVersionString(EObject element) {
		if (element instanceof TClassifier &&
			(element as TClassifier).declaredVersion != 0) {
			return N4IDLGlobals.VERSION_SEPARATOR + Integer.toString((element as TClassifier).declaredVersion)
		}
		return "";
	}

	/**
	 * Returns the display string for a non-proxy element, otherwise null.
	 */
	private def tryGetDisplayString(EObject element, (QualifiedName, String)=>StyledString stringifier, String shortName) {
		if (!element.eIsProxy && element instanceof Type) {
			val qualifiedTypeName = qualifiedNameProvider.getFullyQualifiedName(element)
			if (qualifiedTypeName !== null) {
				return stringifier.apply(qualifiedTypeName, shortName)
			}
		}
		return null
	}

	/**
	 * Is also used to filter out certain keywords, e.g., operators or (too) short keywords.
	 */
	override completeKeyword(Keyword keyword, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (context.currentModel instanceof ParameterizedPropertyAccessExpression || context.previousModel instanceof ParameterizedPropertyAccessExpression)
			return; // filter out all keywords if we are in the context of a property access
		if (context.currentModel instanceof JSXElement || context.previousModel instanceof JSXElement)
			return; // filter out all keywords if we are in the context of a JSX element
		if (!Character.isAlphabetic(keyword.value.charAt(0)))
			return; // filter out operators
		if (keyword.value.length < 5)
			return; // filter out short keywords
		super.completeKeyword(keyword, context, acceptor)
	}

	/**
	 * Produce linked mode aware completion proposals by default.
	 * @see N4JSCompletionProposal#setLinkedModeBuilder
	 */
	override protected doCreateProposal(String proposal, StyledString displayString, Image image, int replacementOffset, int replacementLength) {
		return new N4JSCompletionProposal(
			proposal,
			replacementOffset,
			replacementLength,
			proposal.length(),
			image,
			displayString,
			null,
			null);
	}
}
