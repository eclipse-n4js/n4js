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
package org.eclipse.n4js.ide.editor.contentassist;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.eclipse.xtext.xbase.lib.ListExtensions.map;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalPriorities;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.inject.Inject;

/**
 * A helper class that proposes methods to be overwritten based on the prefix typed so far when pressing the combination
 * CTRL + space. If only one method matches the prefix, it will be completed automatically. Otherwise, you can choose
 * from a proposal list and confirm your choice with Enter.
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
	 * This method activates the Content Assist to propose and complete methods inherited by a superclass based on a
	 * specific prefix.
	 */
	public void complete_Method(EObject model, RuleCall ruleCall, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {

		if (model instanceof N4FieldDeclaration) {
			MemberCollector memberCollector = containerTypesHelper.fromContext(model);
			EObject n4classdeclaration = model.eContainer();
			if (n4classdeclaration instanceof N4ClassDeclaration) {
				Type tclass = ((N4ClassDeclaration) n4classdeclaration).getDefinedType();
				ICompositeNode node = NodeModelUtils.getNode(model);
				completeMethodDeclarationFromField((N4FieldDeclaration) model, node, memberCollector, tclass, context,
						acceptor);

			}
		}

		if (model instanceof LiteralOrComputedPropertyName) {
			MemberCollector memberCollector = containerTypesHelper.fromContext(model);
			EObject n4classdeclaration = model.eContainer().eContainer();
			if (n4classdeclaration instanceof N4ClassDeclaration) {
				Type tclass = ((N4ClassDeclaration) n4classdeclaration).getDefinedType();
				EObject n4FieldDeclaration = model.eContainer();
				if (n4FieldDeclaration instanceof N4FieldDeclaration) {
					ICompositeNode node = NodeModelUtils.getNode(model).getParent().getParent();
					completeMethodDeclarationFromField((N4FieldDeclaration) n4FieldDeclaration, node, memberCollector,
							tclass, context, acceptor);
				}
			}
		}
	}

	private boolean isAccessInternal(TMethod methodMember) {
		String accessModifier = methodMember.getMemberAccessModifier().toString();
		int length = accessModifier.length();
		return (length >= 8) && accessModifier.substring(length - 8, length).equals("Internal");
	}

	private String getAccessModifier(TMethod methodMember) {
		String accessModifier = methodMember.getMemberAccessModifier().toString();
		if (isAccessInternal(methodMember)) {
			// remove appended "Internal" keyword, which is appended to the access modifier
			return accessModifier.substring(0, accessModifier.length() - 8);
		} else {
			return accessModifier;
		}
	}

	private boolean showAccessModifier(TMethod methodMember) {
		return !getAccessModifier(methodMember).equalsIgnoreCase(N4Modifier.PROJECT.getName());
	}

	protected void completeMethodDeclarationFromField(N4FieldDeclaration n4FieldDeclaration, INode node,
			ContainerTypesHelper.MemberCollector memberCollector, Type tclass, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {

		MemberList<TMember> implementedMembers = memberCollector.allMembers((TClass) tclass, false, false, false);

		Set<String> implementedMembersNames = toSet(map(implementedMembers, member -> member.getMemberAsString()));
		for (TMember member : memberCollector.inheritedMembers((TClass) tclass)) {
			if (!implementedMembersNames.contains(member.getMemberAsString())) {
				if (!member.isDeclaredFinal() && !member.getName().equals("constructor")) {
					EList<TAnnotation> annotations = member.getAnnotations();
					buildMethodCompletionProposal(context, acceptor, node, annotations, member);
				}
			}
		}
	}

	private void buildMethodCompletionProposal(ContentAssistContext context, IIdeContentProposalAcceptor acceptor,
			INode node, EList<TAnnotation> annotations, TMember member) {

		if (member instanceof TMethod) {
			TMethod method = (TMethod) member;
			String methodBody;
			String annotationString = addAnnotations(annotations);
			String returnType = getReturnTypeAsString(method);
			String methodMemberAsString = getMethodMemberAsString(method);

			if ((method.isDeclaredGenerator() &&
					!(method.getReturnTypeRef().getTypeArgsWithDefaults().get(1).getDeclaredType() instanceof AnyType))
					|| returnType.equalsIgnoreCase(": void")) {
				methodBody = EMPTY_METHOD_BODY;
			} else {
				StringBuilder strb = new StringBuilder();
				strb.append(Strings.join(",", fp -> fp.getName(), method.getFpars()));
				String methodReturnBody;
				if (!method.getFpars().isEmpty() &&
						!method.getFpars().get(method.getFpars().size() - 1).isVariadic()) {
					methodReturnBody = "\treturn super." + method.getName() + "(" + strb.toString() + ");\n}";
				} else if (method.isDeclaredAsync() || (!method.getFpars().isEmpty() &&
						method.getFpars().get(method.getFpars().size() - 1).isVariadic())) {
					methodReturnBody = "\treturn null;\n}";
				} else {
					methodReturnBody = "\treturn super." + method.getName() + "();\n}";
				}
				methodBody = AUTO_GENERATED_METHOD_BODY_START + methodReturnBody;
			}

			String proposalString = getProposalString(method, annotationString, methodBody);
			int replacementOffset = node.getOffset(); // replace from beginning of node;
			if (!(context.getCurrentModel() instanceof LiteralOrComputedPropertyName)) {
				EObject semanticElement = node.getSemanticElement();
				if (semanticElement instanceof N4FieldDeclaration) {
					N4FieldDeclaration fieldDecl = (N4FieldDeclaration) semanticElement;
					if (fieldDecl.getAnnotationList() == null
							&& fieldDecl.getDeclaredModifiers().isEmpty()) {
						// calculate replacementOffset by length of prefix, because node of prefix is not accessible in
						// case a syntax error occurred
						replacementOffset = context.getOffset() - context.getPrefix().length();
					}
				}
			}
			int replacementLength = context.getOffset() - node.getOffset(); // replace from beginning of the node to the
			// current cursor position
			ContentAssistEntry proposal = createMethodCompletionProposal(proposalString, methodMemberAsString, context,
					method);
			acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal));
		}
	}

	private String addAnnotations(EList<TAnnotation> annotations) {
		boolean hasOverride = false;
		String proposalString = "";
		for (TAnnotation annotation : annotations) {
			proposalString += "@" + annotation.getName() + "\n\t";
			if (annotation.getName().equals(OVERRIDE_ANNOTATION)) {
				hasOverride = true;
			}
		}
		if (!hasOverride) {
			proposalString += "@" + OVERRIDE_ANNOTATION + "\n\t";
		}
		return proposalString;
	}

	private String getMethodMemberAsString(TMethod methodMember) {
		StringBuilder strb = new StringBuilder();
		String fparsStr = Strings.join(",", fp -> fp.getFormalParameterAsString(), methodMember.getFpars());
		strb.append(methodMember.getName());
		strb.append("(");
		strb.append(fparsStr);
		strb.append(")");
		strb.append(getReturnTypeAsString(methodMember));
		if (methodMember.isReturnValueOptional()) {
			strb.append('?');
		}
		return strb.toString();
	}

	/**
	 * Resolves the simplified return type of a given method recursively and returns it as string.
	 *
	 * @param methodMember
	 *            a method of a class
	 */
	private String getReturnTypeAsString(TMethod methodMember) {
		boolean topLevel = true;
		boolean async = methodMember.isDeclaredAsync();
		boolean generator = methodMember.isDeclaredGenerator();
		TypeRef returnTypeRef = methodMember.getReturnTypeRef();
		EList<TypeArgument> typeArgs = returnTypeRef.getTypeArgsWithDefaults();

		if (topLevel && (generator || async && (typeArgs.get(1).getDeclaredType() instanceof AnyType ||
				typeArgs.get(1) instanceof Wildcard))) {
			var firstReturnTypeArg = typeArgs.get(0);
			if (firstReturnTypeArg instanceof ParameterizedTypeRef) {
				if (firstReturnTypeArg.getDeclaredType() instanceof UndefinedType) {
					return ": void";
				}
			}
		}
		return ": " + returnTypeRef.getTypeRefAsString();
	}

	private String getProposalString(TMethod methodMember, String annotString, String methodBody) {
		StringBuilder strb = new StringBuilder();
		if (isAccessInternal(methodMember)) {
			strb.append("@" + INTERNAL_ANNOTATION + "\n");
		}
		strb.append(annotString);
		if (showAccessModifier(methodMember)) {
			strb.append(getAccessModifier(methodMember) + " ");
		}
		if (methodMember.isDeclaredStatic()) {
			strb.append("static ");
		}
		if (methodMember.isDeclaredAsync()) {
			strb.append("async ");
		}
		if (methodMember.isGeneric()) {
			String typeVarsStr = Strings.join(",", tv -> tv.getTypeAsString(), methodMember.getTypeVars());
			strb.append("<").append(typeVarsStr).append("> ");
		}
		if (methodMember.isDeclaredGenerator()) {
			strb.append("*");
		}
		strb.append(getMethodMemberAsString(methodMember));
		strb.append(methodBody);
		return strb.toString();
	}

	private ContentAssistEntry createMethodCompletionProposal(String proposal, String methodName,
			ContentAssistContext context, TMethod method) {

		ContentAssistEntry entry = new ContentAssistEntry();
		entry.setProposal(proposal);
		entry.setPrefix(context.getPrefix());
		entry.setKind(ContentAssistEntry.KIND_METHOD);
		entry.setLabel(method.getFunctionAsString());
		entry.setDescription("Override " + method.getContainingType().getName() + "#" + methodName);

		return entry;
	}
}
