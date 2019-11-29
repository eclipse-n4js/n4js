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
package org.eclipse.n4js.ui.contentassist

import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.ts.types.util.MemberList
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor
import com.google.inject.Inject
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.jface.viewers.StyledString

/**
 * 
 */
class N4JSMethodProposalProvider extends AbstractN4JSProposalProvider {
	
	@Inject
	private ContainerTypesHelper containerTypesHelper;
	
	static final String OVERRIDE_ANNOTATION = AnnotationDefinition.OVERRIDE.name;

	static final String EMPTY_METHOD_BODY = " {\n\n\t}";

	static final String AUTO_GENERATED_METHOD_BODY = " {\n\t\t// TODO Auto-generated method stub\n\t\t" +
		"return null\n\t}";
	
	override public void complete_LiteralOrComputedPropertyName(EObject model, RuleCall ruleCall, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
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

	protected def void completeMethodDeclarationFromField(N4FieldDeclaration n4FieldDeclaration, INode node,
		ContainerTypesHelper.MemberCollector memberCollector, Type tclass, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val annotations = n4FieldDeclaration.annotations;

		for (TMember methodMember : memberCollector.inheritedMembers(tclass as TClass)) {
			val implementedMembers = memberCollector.allMembers(tclass as TClass, false, false, false);
			if (!methodAlreadyImplemented(implementedMembers, methodMember)) {
				val EList<N4Modifier> declaredModifiers = getDeclaredModifiers(methodMember);
				if (!methodMember.declaredFinal) {
					buildMethodCompletionProposal(context, acceptor, node, annotations, declaredModifiers,
						methodMember);
				}
			}
		}
	}

	def private EList<N4Modifier> getDeclaredModifiers(TMember methodMember) {

		var EList<N4Modifier> declaredModifiers;
		val n4MethodDeclaration = methodMember.astElement;
		if (n4MethodDeclaration instanceof N4MethodDeclaration) {
			declaredModifiers = n4MethodDeclaration.declaredModifiers;
		} else {
			declaredModifiers = null;
		}
		return declaredModifiers;
	}

	def private String getReturnTypeAsString(TMethod methodMember) {

		val StringBuilder strb = new StringBuilder();
		if (methodMember.returnTypeRef !== null) {
			if (methodMember.declaredAsync || methodMember.declaredGenerator) {
				val returnTypeName = methodMember.returnTypeRef.typeArgs.get(0).declaredType.name;
				if (returnTypeName.equalsIgnoreCase("undefined")) {
					strb.append(": ").append("void");
				} else {
					strb.append(": ").append(returnTypeName);
				}
			} else {
				strb.append(": ").append(methodMember.returnTypeRef.typeRefAsString);
			}
		}
		return strb.toString;
	}

	def private buildMethodCompletionProposal(ContentAssistContext context, ICompletionProposalAcceptor acceptor,
		INode node, EList<Annotation> annotations, EList<N4Modifier> declaredModifiers, TMember methodMember) {
		var annotationString = addAnnotations(annotations);
		var String methodBody;
		var String methodMemberAsString;
		if (methodMember instanceof TMethod) {
			val String returnType = getReturnTypeAsString(methodMember);
			methodMemberAsString = getMethodMemberAsString(methodMember);

			if (returnType.equalsIgnoreCase(": void")) {
				methodBody = EMPTY_METHOD_BODY;
			} else {
				methodBody = AUTO_GENERATED_METHOD_BODY;
			}

			val proposalString = getProposalString(methodMember, annotationString, declaredModifiers, methodBody);
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
			val ICompletionProposal proposal = createMethodCompletionProposal(proposalString, methodMemberAsString,
				context, replacementOffset, replacementLength);
			acceptor.accept(proposal);
		}
	}

	def private getMethodMemberAsString(TMethod methodMember) {
		val StringBuilder strb = new StringBuilder();
		strb.append(methodMember.name).append("(").append(methodMember.fpars.map[formalParameterAsString].join(", ")).
			append(")");
		if (methodMember.returnTypeRef !== null) {
			if (methodMember.declaredAsync || methodMember.declaredGenerator) {
				val returnTypeName = methodMember.returnTypeRef.typeArgs.get(0).declaredType.name;
				if (returnTypeName.equalsIgnoreCase("undefined")) {
					strb.append(": ").append("void");
				} else {
					strb.append(": ").append(returnTypeName);
				}
			} else {
				strb.append(": ").append(methodMember.returnTypeRef.typeRefAsString);
			}
		}
		if (methodMember.returnValueOptional) strb.append('?');
		return strb.toString();
	}

	def private getProposalString(TMethod methodMember, String annotationString, EList<N4Modifier> declaredModifiers,
		String methodBody) {
		val StringBuilder strb = new StringBuilder();
		strb.append(annotationString);
		if (hasAccessModifier(declaredModifiers)) strb.append(methodMember.memberAccessModifier.toString + " ");
		if (methodMember.generic)
			strb.append("<").append(methodMember.typeVars.map[typeAsString].join(",")).append("> ");
		if (methodMember.declaredStatic) strb.append("static ");
		if (methodMember.declaredAsync) strb.append("async ");
		if (methodMember.declaredGenerator) strb.append("*");
		strb.append(getMethodMemberAsString(methodMember));
		strb.append(methodBody);
		return strb.toString();
	}

	def private createMethodCompletionProposal(String proposal, String methodMemberName, ContentAssistContext context,
		int offset, int length) {
		if (isValidProposal(methodMemberName, context.prefix, context)) {
			return doCreateProposal(proposal, new StyledString(methodMemberName), null, offset, length);
		}
		return null;
	}

	def private String addAnnotations(EList<Annotation> annotations) {
		var hasOverride = false;
		var proposalString = ""
		for (Annotation

annotation : annotations) {
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

	def private boolean hasAccessModifier(EList<N4Modifier> declaredModifiers) {
		if (declaredModifiers === null) {
			return false
		} else {
			for (N4Modifier modifier : declaredModifiers) {
				switch (modifier.name()) {
					case N4Modifier.PUBLIC.name(): return true
					case N4Modifier.PROJECT.name(): return true
					case N4Modifier.PROTECTED.name(): return true
					default: return false
				}
			}
		}
	}

	def private boolean methodAlreadyImplemented(MemberList<TMember> implementedMethods, TMember method) {
		var boolean methodImplemented = false
		if (implementedMethods === null) {
			return methodImplemented
		} else {
			for (TMember methodMember : implementedMethods) {
				if (methodMember.memberAsString.equals(method.memberAsString)) {
					methodImplemented = true
				}
			}
		}
		return methodImplemented
	}

}
