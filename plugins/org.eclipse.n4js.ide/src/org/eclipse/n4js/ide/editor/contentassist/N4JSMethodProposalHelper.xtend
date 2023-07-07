/**
 * Copyright (c) 2019 HAW Hamburg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Philip Aguilar Bremer, Max Neuwirt; HAW Hamburg
 */
package org.eclipse.n4js.ide.editor.contentassist

import com.google.inject.Inject
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.AnyType
import org.eclipse.n4js.ts.types.TAnnotation
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.UndefinedType
import org.eclipse.n4js.ts.types.util.MemberList
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalPriorities
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils

/**
 * A helper class that proposes methods to be overwritten based on the prefix typed so far
 * when pressing the combination CTRL + space.
 * If only one method matches the prefix, it will be completed automatically.
 * Otherwise, you can choose from a proposal list and confirm your choice with Enter.
 */
class N4JSMethodProposalHelper {
	static final String OVERRIDE_ANNOTATION = AnnotationDefinition.OVERRIDE.name;
	static final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;
	static final String EMPTY_METHOD_BODY = " {\n\n}";
	static final String AUTO_GENERATED_METHOD_BODY_START = " {\n\t// TODO Auto-generated method stub\n";

	@Inject
	private ContainerTypesHelper containerTypesHelper;
	
	@Inject
	private IdeContentProposalPriorities proposalPriorities;

	/**
	 * This method activates the Content Assist to propose and complete methods inherited by a superclass
	 * based on a specific prefix. 
	 */
	public def complete_Method(EObject model, RuleCall ruleCall, ContentAssistContext context,
		IIdeContentProposalAcceptor acceptor
	) {
		if (model instanceof N4FieldDeclaration) {
			val memberCollector = containerTypesHelper.fromContext(model);
			val n4classdeclaration = model.eContainer;
			if (n4classdeclaration instanceof N4ClassDeclaration) {
				val tclass = n4classdeclaration.definedType;
				var node = NodeModelUtils.getNode(model);
				completeMethodDeclarationFromField(model, node, memberCollector, tclass, context, acceptor)

			}
		}

		if (model instanceof LiteralOrComputedPropertyName) {
			val memberCollector = containerTypesHelper.fromContext(model);
			val n4classdeclaration = model.eContainer.eContainer;
			if (n4classdeclaration instanceof N4ClassDeclaration) {
				val tclass = n4classdeclaration.definedType;
				val n4FieldDeclaration = model.eContainer;
				if (n4FieldDeclaration instanceof N4FieldDeclaration) {
					var node = NodeModelUtils.getNode(model).parent.parent;
					completeMethodDeclarationFromField(n4FieldDeclaration, node, memberCollector, tclass, context,
						acceptor)
				}
			}
		}
	}



	def private boolean isAccessInternal(TMethod methodMember) {
		var String accessModifier = methodMember.memberAccessModifier.toString;
		return (accessModifier.length >= 8) &&
			accessModifier.substring(accessModifier.length - 8, accessModifier.length).equals("Internal");
	}

	def private String getAccessModifier(TMethod methodMember) {
		var String accessModifier = methodMember.memberAccessModifier.toString;
		if (isAccessInternal(methodMember)) {
			// remove appended "Internal" keyword, which is appended to the access modifier
			return methodMember.memberAccessModifier.toString().substring(0, accessModifier.length - 8);
		} else {
			return methodMember.memberAccessModifier.toString();
		}
	}

	def private boolean showAccessModifier(TMethod methodMember) {
		return !getAccessModifier(methodMember).equalsIgnoreCase(N4Modifier.PROJECT.name());
	}

	protected def void completeMethodDeclarationFromField(N4FieldDeclaration n4FieldDeclaration, INode node,
		ContainerTypesHelper.MemberCollector memberCollector, Type tclass, ContentAssistContext context,
		IIdeContentProposalAcceptor acceptor
	) {

		val implementedMembers = memberCollector.allMembers(tclass as TClass, false, false, false);
		val implementedMembersNames = IterableExtensions.map(implementedMembers, [member | member.memberAsString]).toSet;
		for (TMember member : memberCollector.inheritedMembers(tclass as TClass)) {
			if (!implementedMembersNames.contains(member.memberAsString)) {
				if (!member.declaredFinal && !member.name.equals("constructor")) {
					val annotations = member.annotations;
					buildMethodCompletionProposal(context, acceptor, node, annotations, member);
				}
			}
		}
	}

	def private void buildMethodCompletionProposal(ContentAssistContext context, IIdeContentProposalAcceptor acceptor,
		INode node, EList<TAnnotation> annotations, TMember method
	) {
		if (method instanceof TMethod) {
			var String methodBody;
			val annotationString = addAnnotations(annotations);
			val String returnType = getReturnTypeAsString(method);
			val String methodMemberAsString = getMethodMemberAsString(method);

			if ((method.declaredGenerator &&
				!(method.returnTypeRef.typeArgsWithDefaults.get(1).declaredType instanceof AnyType)) ||
				returnType.equalsIgnoreCase(": void")) {
				methodBody = EMPTY_METHOD_BODY;
			} else {
				val StringBuilder strb = new StringBuilder();
				strb.append(method.fpars.map[name].join(", "))
				var String methodReturnBody;
				if (!method.fpars.isNullOrEmpty &&
					!method.fpars.get(method.fpars.length - 1).variadic) {
					methodReturnBody = "\treturn super." + method.name + "(" + strb.toString() + ");\n}";
				} else if (method.declaredAsync || (!method.fpars.isNullOrEmpty &&
					method.fpars.get(method.fpars.length - 1).variadic)) {
					methodReturnBody = "\treturn null;\n}";
				} else {
					methodReturnBody = "\treturn super." + method.name + "();\n}";
				}
				methodBody = AUTO_GENERATED_METHOD_BODY_START + methodReturnBody;
			}

			val proposalString = getProposalString(method, annotationString, methodBody);
			var replacementOffset = node.offset; // replace from beginning of node;
			if (!(context.currentModel instanceof LiteralOrComputedPropertyName)) {
				val semanticElement = node.semanticElement;
				if (semanticElement instanceof N4FieldDeclaration)
					if (semanticElement.annotationList === null && semanticElement.declaredModifiers.isNullOrEmpty) {
						// calculate replacementOffset by length of prefix, because node of prefix is not accessible in case a syntax error occurred 
						replacementOffset = context.offset - context.prefix.length;
					}
			}
			val replacementLength = context.offset - node.offset; // replace from beginning of the node to the current cursor position
			val ContentAssistEntry proposal = createMethodCompletionProposal(proposalString, methodMemberAsString, context, method);
			acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal));
		}
	}

	def private String addAnnotations(EList<TAnnotation> annotations) {
		var hasOverride = false;
		var proposalString = "";
		for (TAnnotation annotation : annotations) {
			proposalString += "@" + annotation.name + "\n\t"
			if (annotation.name.equals(OVERRIDE_ANNOTATION)) {
				hasOverride = true;
			}
		}
		if (!hasOverride) {
			proposalString += "@" + OVERRIDE_ANNOTATION + "\n\t"
		}
		return proposalString;
	}

	def private String getMethodMemberAsString(TMethod methodMember) {
		val StringBuilder strb = new StringBuilder();
		strb.append(methodMember.name).append("(").append(methodMember.fpars.map[formalParameterAsString].join(", ")).
			append(")");
		strb.append(getReturnTypeAsString(methodMember));
		if (methodMember.returnValueOptional) {
			strb.append('?');
		}
		return strb.toString();
	}

	/**
	 * Resolves the simplified return type of a given method recursively and returns it as string.
	 * @param methodMember a method of a class
	 */
	def private String getReturnTypeAsString(TMethod methodMember) {
		var topLevel = true;
		val boolean async = methodMember.declaredAsync;
		val boolean generator = methodMember.declaredGenerator;
		val returnTypeRef = methodMember.returnTypeRef;
		val typeArgs = returnTypeRef.typeArgsWithDefaults;

		if (topLevel && (generator || async && (typeArgs.get(1).declaredType instanceof AnyType ||
			typeArgs.get(1) instanceof Wildcard))) {
			var firstReturnTypeArg = typeArgs.get(0);
			if (firstReturnTypeArg instanceof ParameterizedTypeRef) {
				if (firstReturnTypeArg.declaredType instanceof UndefinedType) {
					return ": void";
				}
			}
		}
		returnTypeRef.typeRefAsStringWithAliasExpansion
		return ": " + returnTypeRef.typeRefAsString;
	}

	def private String getProposalString(TMethod methodMember, String annotString, String methodBody) {
		val StringBuilder strb = new StringBuilder();
		if (isAccessInternal(methodMember)) {
			strb.append("@" + INTERNAL_ANNOTATION + "\n");
		}
		strb.append(annotString);
		if (showAccessModifier(methodMember)) {
			strb.append(getAccessModifier(methodMember) + " ");
		}
		if (methodMember.declaredStatic) {
			strb.append("static ");
		}
		if (methodMember.declaredAsync) {
			strb.append("async ");
		}
		if (methodMember.generic) {
			strb.append("<").append(methodMember.typeVars.map[typeAsString].join(",")).append("> ");
		}
		if (methodMember.declaredGenerator) {
			strb.append("*");
		}
		strb.append(getMethodMemberAsString(methodMember));
		strb.append(methodBody);
		return strb.toString();
	}

	def private ContentAssistEntry createMethodCompletionProposal(String proposal, String methodName, ContentAssistContext context, TMethod method) {
		val ContentAssistEntry entry = new ContentAssistEntry();
		entry.setProposal(proposal);
		entry.setPrefix(context.getPrefix());
		entry.setKind(ContentAssistEntry.KIND_METHOD);
		entry.setLabel(method.functionAsString);
		entry.setDescription("Override " + method.containingType.name + "#" + methodName);
			
		return entry;
	}
}
