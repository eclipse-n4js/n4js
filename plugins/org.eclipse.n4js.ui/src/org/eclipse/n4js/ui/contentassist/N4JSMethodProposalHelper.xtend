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
package org.eclipse.n4js.ui.contentassist

import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
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
import org.eclipse.n4js.ts.types.TAnnotation
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.xtext.ui.editor.contentassist.AbstractCompletionProposalFactory
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.Wildcard

/**
 * A helper class that proposes methods to be overwritten based on the prefix typed so far
 * when pressing the combination CTRL + space.
 * If only one method matches the prefix, it will be completed automatically.
 * Otherwise, you can choose from a proposal list and confirm your choice with Enter.
 */
class N4JSMethodProposalHelper extends AbstractCompletionProposalFactory {
	//TODO: Add some more tests for more complicated method return types:
	// "org.eclipse.n4js.xpect.ui.tests/testdata_nonvalidating/proposal/contentassist/ContentAssist_overriding_methods_apply.n4js.xt"

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	static final String OVERRIDE_ANNOTATION = AnnotationDefinition.OVERRIDE.name;

	static final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;

	static final String EMPTY_METHOD_BODY = " {\n\n\t}";

	static final String AUTO_GENERATED_METHOD_BODY = " {\n\t\t// TODO Auto-generated method stub\n\t\t";

	/**
	 * This method activates the Content Assist to propose and complete methods inherited by a superclass
	 * based on a specific prefix. 
	 */
	public def complete_Method(EObject model, RuleCall ruleCall, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
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

		for (TMember methodMember : memberCollector.inheritedMembers(tclass as TClass)) {
			val implementedMembers = memberCollector.allMembers(tclass as TClass, false, false, false);
			if (!methodAlreadyImplemented(implementedMembers, methodMember)) {
				if (!methodMember.declaredFinal && !methodMember.name.equals("constructor")) {
					val annotations = methodMember.annotations;
					buildMethodCompletionProposal(context, acceptor, node, annotations, methodMember);
				}
			}
		}
	}

	def private String getReturnTypeAsString(TMethod methodMember) {

		var topLevel = true;
		var boolean asyncOrGenerator = methodMember.declaredAsync || methodMember.declaredGenerator;
		var returnTypeRef = methodMember.returnTypeRef;

		resolveReturnTypeTopLevel(returnTypeRef, topLevel, asyncOrGenerator);
	}

	def private String resolveReturnTypeTopLevel(TypeArgument returnTypeRef, boolean topLevel,
		boolean asyncOrGenerator) {
		if (topLevel && asyncOrGenerator) {
			if (returnTypeRef instanceof TypeRef) {
				val returnTypeName = returnTypeRef.typeArgs.get(0).declaredType.name;
				if (returnTypeName.equalsIgnoreCase("undefined")) {
					return ": void";
				}
			}
		}
		return ": " + resolveReturnType(returnTypeRef, topLevel);
	}

	def private String resolveReturnType(TypeArgument returnTypeRef, boolean topLevel) {
		var StringBuilder strb = new StringBuilder();
		val notTopLevel = false;
		var declaredType = returnTypeRef.declaredType;
		if (returnTypeRef instanceof ParameterizedTypeRef) {
			if (returnTypeRef.isIterableTypeExpression) {
				var typeArgs = returnTypeRef.typeArgs;
				strb.append("[").append(typeArgs.map[resolveReturnType(it, notTopLevel)].join(", ")).append("]");
			} else if (returnTypeRef.isArrayLike && returnTypeRef.typeArgs.size > 0) {
				strb.append("Array<").append(resolveReturnType(returnTypeRef.typeArgs.get(0), notTopLevel)).append(">");
			} else if (declaredType.name.equalsIgnoreCase("Generator")) {
				//TODO: Simplified notation, if only the generator's first parameter is defined
				var typeArgs = returnTypeRef.typeArgs;
				strb.append("Generator<").append(typeArgs.map[resolveReturnType(it, notTopLevel)].join(", ")).
					append(">");
			} else if (declaredType.name.equalsIgnoreCase("Promise")) {
				//TODO: Simplified notation, if only the promise's first parameter is defined
				var typeArgs = returnTypeRef.typeArgs;
				strb.append("Promise<").append(typeArgs.map[resolveReturnType(it, notTopLevel)].join(", ")).append(">");
			} else {
				strb.append(declaredType.name);
			}
		} else if (returnTypeRef instanceof IntersectionTypeExpression) {
			var typeRefs = returnTypeRef.typeRefs;
			if (!topLevel) strb.append("(");
			strb.append(typeRefs.map[resolveReturnType(it, notTopLevel)].join(" & "));
			if (!topLevel) strb.append(")");
		} else if (returnTypeRef instanceof UnionTypeExpression) {
			var typeRefs = returnTypeRef.typeRefs;
			if (!topLevel) strb.append("(");
			strb.append(typeRefs.map[resolveReturnType(it, notTopLevel)].join(" | "));
			if (!topLevel) strb.append(")");
		} else if (returnTypeRef instanceof Wildcard) {
			strb.append("?");
		} else {
			strb.append(declaredType.name);
		}
		return strb.toString();
	}

	def private buildMethodCompletionProposal(ContentAssistContext context, ICompletionProposalAcceptor acceptor,
		INode node, EList<TAnnotation> annotations, TMember methodMember) {
		var annotationString = addAnnotations(annotations);
		var String methodBody;
		var String methodMemberAsString;
		if (methodMember instanceof TMethod) {
			val String returnType = getReturnTypeAsString(methodMember);
			methodMemberAsString = getMethodMemberAsString(methodMember);

			if (returnType.equalsIgnoreCase(": void")) {
				methodBody = EMPTY_METHOD_BODY;
			} else {
				val StringBuilder strb = new StringBuilder();
				strb.append(methodMember.fpars.map[name].join(", "))
				var String methodReturnBody;
				if (!methodMember.fpars.isNullOrEmpty &&
					!methodMember.fpars.get(methodMember.fpars.length - 1).variadic) {
					methodReturnBody = "return super." + methodMember.name + "(" + strb.toString() + ");\n\t}";
				} else if ((methodMember.declaredAsync || methodMember.declaredGenerator) ||
					(!methodMember.fpars.isNullOrEmpty &&
						methodMember.fpars.get(methodMember.fpars.length - 1).variadic)) {
					methodReturnBody = "return null;\n\t}";
				} else {
					methodReturnBody = "return super." + methodMember.name + "();\n\t}";
				}
				methodBody = AUTO_GENERATED_METHOD_BODY + methodReturnBody;
			}

			val proposalString = getProposalString(methodMember, annotationString, methodBody);
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
		strb.append(getReturnTypeAsString(methodMember));
		if (methodMember.returnValueOptional) strb.append('?');
		return strb.toString();
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

	def private getProposalString(TMethod methodMember, String annotString, String methodBody) {
		val StringBuilder strb = new StringBuilder();
		var annotationString = annotString;
		if (isAccessInternal(methodMember)) annotationString = "@" + INTERNAL_ANNOTATION + "\n\t" + annotationString;
		strb.append(annotationString);
		if (showAccessModifier(methodMember)) strb.append(getAccessModifier(methodMember) + " ");
		if (methodMember.declaredStatic) strb.append("static ");
		if (methodMember.declaredAsync) strb.append("async ");
		if (methodMember.generic)
			strb.append("<").append(methodMember.typeVars.map[typeAsString].join(",")).append("> ");
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

	def private String addAnnotations(EList<TAnnotation> annotations) {
		var hasOverride = false;
		var proposalString = ""
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

	def private boolean showAccessModifier(TMethod methodMember) {
		return !getAccessModifier(methodMember).equalsIgnoreCase(N4Modifier.PROJECT.name());
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
