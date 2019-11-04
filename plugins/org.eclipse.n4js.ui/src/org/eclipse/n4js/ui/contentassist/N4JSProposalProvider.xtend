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
import com.google.inject.Provider
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.jface.viewers.StyledString
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.DeclaredTypeWithAccessModifier
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TExportableElement
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.n4js.ui.proposals.imports.ImportsAwareReferenceProposalCreator
import org.eclipse.n4js.ui.proposals.linkedEditing.N4JSCompletionProposal
import org.eclipse.swt.graphics.Image
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.GrammarUtil
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider.DefaultProposalCreator
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor

import static extension org.eclipse.n4js.ui.utils.ConfigurableCompletionProposalExtensions.*
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.ts.types.util.MemberList
import org.eclipse.n4js.ts.types.impl.TMethodImpl
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EDataTypeEList
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.impl.AnnotationImpl

/**
 * see http://www.eclipse.org/Xtext/documentation.html#contentAssist on how to customize content assistant
 */
class N4JSProposalProvider extends AbstractN4JSProposalProvider {

	private static final String KEY_SCANNED_SCOPES = N4JSProposalProvider.name + "_scannedScopes";

	@Inject
	private ImportsAwareReferenceProposalCreator importAwareReferenceProposalCreator

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter

	@Inject
	private N4JSGrammarAccess n4jsGrammarAccess;

	@Inject
	private N4JSLabelProvider labelProvider;
	
	@Inject
	private ContainerTypesHelper containerTypesHelper;

	override completeRuleCall(RuleCall ruleCall, ContentAssistContext contentAssistContext,
		ICompletionProposalAcceptor acceptor) {
		val calledRule = ruleCall.getRule();
		if (!"INT".equals(calledRule.getName)) {
			super.completeRuleCall(ruleCall, contentAssistContext, acceptor)
		}
	}

	override protected lookupCrossReference(CrossReference crossReference, ContentAssistContext contentAssistContext,
		ICompletionProposalAcceptor acceptor) {
		lookupCrossReference(crossReference, contentAssistContext, acceptor, new N4JSCandidateFilter());
	}

	override protected void lookupCrossReference(CrossReference crossReference,
		ContentAssistContext contentAssistContext, ICompletionProposalAcceptor acceptor,
		Predicate<IEObjectDescription> filter) {

		// because rule "TypeReference" in TypeExpressions.xtext (overridden in N4JS.xtext) is a wildcard fragment,
		// the standard behavior of the super method would fail in the following case:
		var ParserRule containingParserRule = GrammarUtil.containingParserRule(crossReference);
		if (containingParserRule === n4jsGrammarAccess.typeReferenceRule) {
			val String featureName = GrammarUtil.containingAssignment(crossReference).getFeature();

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
	override protected lookupCrossReference(CrossReference crossReference, EReference reference,
		ContentAssistContext context, ICompletionProposalAcceptor acceptor, Predicate<IEObjectDescription> filter) {

		if (haveScannedScopeFor(context.currentModel, reference)) {
			return; // avoid scanning the same scope twice
		}

		if (reference.EReferenceType.isSuperTypeOf(TypesPackage.Literals.TYPE) ||
			TypesPackage.Literals.TYPE.isSuperTypeOf(reference.EReferenceType)) {

			// if we complete a reference to something that is aware of imports, enable automatic import insertion by using the
			// importAwareReferenceProposalCreator
			var String ruleName = null;
			if (crossReference.terminal instanceof RuleCall) {
				ruleName = (crossReference.terminal as RuleCall).rule.name
			}

			val proposalFactory = getProposalFactory(ruleName, context);
			importAwareReferenceProposalCreator.lookupCrossReference(context.currentModel, reference, context, acceptor,
				filter, proposalFactory);

		} else {
			super.lookupCrossReference(crossReference, reference, context, acceptor, filter)
		}
	}

	/**
	 * Tells whether the scope for the given context and reference has already been scanned for completion proposals.
	 * In addition, the scope for the given context and reference is marked as having been scanned (if not marked as
	 * such already).
	 */
	def private boolean haveScannedScopeFor(EObject context, EReference reference) {
		// Maybe this logic could be improved further by not just returning true in case of identical context/reference
		// but also for "similar" contexts/references of which we know they will lead to an equal scope.
		return !announceProcessing(#[KEY_SCANNED_SCOPES, context, reference]);
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

		// prepare URIs of all members of built-in types Object and N4Object to avoid
		// having to invoke #getEObjectOrProxy() on every IEObjectDescription
		val builtInTypeScope = BuiltInTypeScope.get(contentAssistContext.resource.resourceSet);
		val secondaryTypes = #[ builtInTypeScope.objectType, builtInTypeScope.n4ObjectType ];
		val urisOfSecondaryMembers = secondaryTypes.flatMap[ownedMembers].map[EcoreUtil.getURI(it)].toSet;

		val myConverter = new IQualifiedNameConverter.DefaultImpl() // provide a fake implementation using '.' as delimiter like Java
		return new DefaultProposalCreator(contentAssistContext, ruleName, myConverter) {

			override apply(IEObjectDescription candidate) {
				if (candidate === null)
					return null;

				var ICompletionProposal result = null;
				var String proposal = myConverter.toString(candidate.getName());
				if (valueConverter !== null) {
					try {
						proposal = valueConverter.toString(proposal);
					} catch (ValueConverterException e) {
						return null;
					}
				} else if (ruleName !== null) {
					try {
						proposal = getValueConverter().toString(proposal, ruleName);
					} catch (ValueConverterException e) {
						return null;
					}
				}
				val StyledString displayString = getStyledDisplayString(candidate);
				val Image image = getImage(candidate);
				result = createCompletionProposal(proposal, displayString, image, contentAssistContext);
				if (result instanceof ConfigurableCompletionProposal) {
					val Provider<EObject> provider = [candidate.getEObjectOrProxy()];
					result.setProposalContextResource(contentAssistContext.getResource());
					result.setAdditionalProposalInfo(provider);
					result.setHover(hover);

					result.secondaryMember = urisOfSecondaryMembers.contains(candidate.EObjectURI);

					val bracketInfo = computeProposalBracketInfo(candidate, contentAssistContext);
					if (bracketInfo!==null) {
						result.replacementSuffix = bracketInfo.brackets;
						result.cursorOffset = bracketInfo.cursorOffset;
					}
				}
				getPriorityHelper().adjustCrossReferencePriority(result, contentAssistContext.getPrefix());
				return result;
			}
		}
	}

	override StyledString getStyledDisplayString(IEObjectDescription description) {
		val version = N4JSResourceDescriptionStrategy.getVersion(description);
		var QualifiedName qName = description.getQualifiedName();
		var QualifiedName name = description.getName();

		if (qName == name) {
			val eObj = description.getEObjectOrProxy(); // performance issue! TODO: remove it
			val qnOfEObject = qualifiedNameProvider.getFullyQualifiedName(eObj);
			qName = qnOfEObject ?: qName;
		}
		var StyledString sString = getStyledDisplayString(qName, name, version);
		return sString;
	}

	override protected StyledString getStyledDisplayString(EObject element, String qualifiedNameString,
		String shortNameString) {

		val version = getTypeVersion(element);
		val qualifiedName = qualifiedNameConverter.toQualifiedName(qualifiedNameString);
		val shortName = qualifiedNameConverter.toQualifiedName(shortNameString);
		return getStyledDisplayString(qualifiedName, shortName, version);
	}

	def protected StyledString getStyledDisplayString(QualifiedName qualifiedName, QualifiedName shortName,
		int version) {

		val result = new StyledString();
		val shortNameString = shortName.toString();
		if (qualifiedName.segmentCount > 1) {
			val dashName = ' - ' + qualifiedNameConverter.toString(qualifiedName.skipLast(1));
			val lastSegment = qualifiedName.lastSegment;
			val typeVersion = if (version === 0) "" else N4IDLGlobals.VERSION_SEPARATOR + String.valueOf(version);

			var String caption;
			var String dashInfo;
			if (shortNameString.endsWith(lastSegment)) {
				caption = lastSegment + typeVersion;
				dashInfo = dashName;
			} else {
				caption = shortNameString;
				dashInfo = dashName + " alias for " + lastSegment + typeVersion;
			}
			result.append(caption);
			result.append(dashInfo, StyledString.QUALIFIER_STYLER);
		} else {
			result.append(shortNameString);
		}
		return result;
	}

	override protected getImage(IEObjectDescription description) {
		val clazz = description.EClass;
		val type = EcoreUtil.create(clazz);
		if (type instanceof TClass) {
			type.declaredFinal = N4JSResourceDescriptionStrategy.getFinal(description);
			type.declaredAbstract = N4JSResourceDescriptionStrategy.getAbstract(description);
		}
		if (type instanceof TExportableElement) {
			type.exportedName = if (N4JSResourceDescriptionStrategy.getExported(description)) " " else null;
		}
		if (type instanceof DeclaredTypeWithAccessModifier) {
			type.declaredTypeAccessModifier = N4JSResourceDescriptionStrategy.getTypeAccessModifier(description);
		}
		if (type instanceof TFunction) {
			type.declaredTypeAccessModifier = N4JSResourceDescriptionStrategy.getTypeAccessModifier(description);
		}
		if (type instanceof TMember) {
			// The EObject of members was loaded already by the scope.
			// Hence, getting it again has only little impact on the performance.
			val member = description.getEObjectOrProxy() as TMember;
			type.declaredFinal = member.isDeclaredFinal;

			if (type instanceof TMemberWithAccessModifier) {
				type.declaredMemberAccessModifier = member.memberAccessModifier;
			}
		}
		val image = labelProvider.getImage(type);
		return image;
	}

	/**
	 * If the element is an instance of {@link TClassifier} this method
	 * returns a user-faced string description of the version information.
	 *
	 * Otherwise, this method returns an empty string.
	 */
	private def int getTypeVersion(EObject element) {
		if (!element.eIsProxy && element instanceof TClassifier && (element as TClassifier).declaredVersion != 0) {
			return (element as TClassifier).declaredVersion;
		}
		return 0;
	}

	/**
	 * Is also used to filter out certain keywords, e.g., operators or (too) short keywords.
	 */
	override completeKeyword(Keyword keyword, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (context.currentModel instanceof ParameterizedPropertyAccessExpression ||
			context.previousModel instanceof ParameterizedPropertyAccessExpression)
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
	override protected doCreateProposal(String proposal, StyledString displayString, Image image, int replacementOffset,
		int replacementLength) {
		return new N4JSCompletionProposal(proposal, replacementOffset, replacementLength, proposal.length(), image,
			displayString, null, null);
	}

	@Data
	private static final class ProposalBracketInfo {
		String brackets;
		int cursorOffset;
	}

	def private ProposalBracketInfo computeProposalBracketInfo(IEObjectDescription proposedDescription, ContentAssistContext contentAssistContext) {

		val eClass = proposedDescription.EClass;

		if (TypesPackage.eINSTANCE.TFunction.isSuperTypeOf(eClass)) {
			return new ProposalBracketInfo("()", -1);
		}

		return null;
	}
	
	override public void complete_N4FieldDeclaration(EObject model, RuleCall ruleCall, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
//		super.complete_N4FieldDeclaration(model, ruleCall, context, acceptor);
	}	
	
	//				val proposalString = mc.inheritedMembers(tclass as TClass).map[].join(" ") + " "
	override public void complete_LiteralOrComputedPropertyName(EObject model, RuleCall ruleCall, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		super.complete_LiteralOrComputedPropertyName(model, ruleCall, context, acceptor);
		if(model instanceof LiteralOrComputedPropertyName) {
			val mc = containerTypesHelper.fromContext(model);
			val n4classdeclaration = model.eContainer.eContainer;
			if(n4classdeclaration instanceof N4ClassDeclaration) {
				val tclass = n4classdeclaration.definedType;
				System.out.println(mc.inheritedMembers(tclass as TClass));
				var proposalString =""; 
				for(TMember methodMember: mc.inheritedMembers(tclass as TClass))
				{
 					if(containsAnnotation((model.eContainer as N4FieldDeclaration).annotations, "Override") &&
 					containsDeclarationModifier((model.eContainer as N4FieldDeclaration).declaredModifiers, methodMember.memberAccessModifier.toString))
 					{
						proposalString = methodMember.memberAsString	
 					}
					else if(!containsAnnotation((model.eContainer as N4FieldDeclaration).annotations, "Override") &&
 					!containsDeclarationModifier((model.eContainer as N4FieldDeclaration).declaredModifiers, methodMember.memberAccessModifier.toString)) {
						proposalString = "@Override \n" + methodMember.memberAccessModifier.toString + " " + methodMember.memberAsString	
					}
					else if(!containsAnnotation((model.eContainer as N4FieldDeclaration).annotations, "Override")) {
						proposalString = methodMember.memberAsString
					}
					else if(!containsDeclarationModifier((model.eContainer as N4FieldDeclaration).declaredModifiers, methodMember.memberAccessModifier.toString)) {
						proposalString = methodMember.memberAccessModifier.toString + " " + methodMember.memberAsString
					}
					val ICompletionProposal proposal = 
						createMethodCompletionProposal(proposalString + " {}", methodMember.memberAsString, context);
					acceptor.accept(proposal);					
				}
			}		
		}
	}
	
	def createMethodCompletionProposal(String proposal, String validProposal, ContentAssistContext context) {
		if (isValidProposal(validProposal, context.prefix, context)) {
			return doCreateProposal(proposal, new StyledString(validProposal), null, getPriorityHelper().getDefaultPriority(), context);
		}
		return null;
		
	}
	
	def private boolean containsAnnotation(EList<Annotation> annotations, String annotation) {
		for(Annotation a: annotations) {
			if(a.name.toLowerCase === annotation.toLowerCase) {
				return true;
			}
		}
		return false;	
	}
	
	def private boolean containsDeclarationModifier(EList<N4Modifier> modifiers, String modifier) {
		for(N4Modifier m: modifiers) {
			if(m.getName().toLowerCase === modifier.toLowerCase) {
				return true;
			}
		}
		return false;	
	}
	
//	def private String createMethodProposalString(EObject model) {
//		if(model instanceof LiteralOrComputedPropertyName) {
//			val mc = containerTypesHelper.fromContext(model);
//			}
//		return "";
//	}
}
