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

import static org.eclipse.n4js.types.utils.TypeUtils.isAny;
import static org.eclipse.n4js.types.utils.TypeUtils.isVoid;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.eclipse.xtext.xbase.lib.ListExtensions.map;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.ide.server.util.SymbolKindUtil;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4;
import org.eclipse.n4js.xtext.ide.editor.contentassist.N4JSContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalPriorities;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.inject.Inject;

/**
 * A helper class that proposes methods to be overwritten based on the prefix typed so far when pressing the combination
 * CTRL + space. If only one method matches the prefix, it will be completed automatically. Otherwise, you can choose
 * from a proposal list and confirm your choice with Enter.
 */
class N4JSProposalHelper {
	static final String OVERRIDE_ANNOTATION = AnnotationDefinition.OVERRIDE.name;
	static final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;
	static final String AUTO_GENERATED_STUB = "\t// TODO Auto-generated stub\n";
	static final String EMPTY_METHOD_BODY = " {\n" + AUTO_GENERATED_STUB + "\n}";
	static final String AUTO_GENERATED_METHOD_BODY_START = " {\n" + AUTO_GENERATED_STUB;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Inject
	private IdeContentProposalPriorities proposalPriorities;

	/**
	 * This method activates the Content Assist to propose and complete methods inherited by a superclass based on a
	 * specific prefix.
	 */
	public void complete(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		EObject model = context.getCurrentModel();

		// annotations
		if ("@@".equals(context.getPrefix())) {
			for (AnnotationDefinition annDef : AnnotationDefinition.getAll()) {
				if (annDef.targets[0] == N4JSPackage.eINSTANCE.getScript()) {
					ContentAssistEntry proposal = createProposal("", annDef);
					acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal));
				}
			}
		} else if ("@".equals(context.getPrefix())) {
			for (AnnotationDefinition annDef : AnnotationDefinition.getAll()) {
				if (annDef.targets.length > 1 || annDef.targets[0] != N4JSPackage.eINSTANCE.getScript()) {
					ContentAssistEntry proposal = createProposal("", annDef);
					acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal));
				}
			}
		} else if (context.getPreviousModel() instanceof Annotation) {
			Annotation anno = (Annotation) context.getPreviousModel();
			if (AnnotationDefinition.find(anno.getName()) == null) {
				ICompositeNode node = NodeModelUtils.getNode(anno);

				String prefix = NodeModelUtilsN4.getTokenTextWithHiddenTokens(node).trim();
				for (AnnotationDefinition annDef : AnnotationDefinition.getAll()) {
					ContentAssistEntry proposal = createProposal(prefix, annDef);
					acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal));
				}
			}
		}

		// overrides for properties from super types
		if (model instanceof N4FieldDeclaration) {
			MemberCollector memberCollector = containerTypesHelper.fromContext(model);
			EObject n4classdeclaration = model.eContainer();
			if (n4classdeclaration instanceof N4ClassDeclaration) {
				Type tclass = ((N4ClassDeclaration) n4classdeclaration).getDefinedType();
				buildProposals((N4FieldDeclaration) model, memberCollector, tclass, acceptor);
			}
		} else if (model instanceof LiteralOrComputedPropertyName) {
			MemberCollector memberCollector = containerTypesHelper.fromContext(model);
			EObject n4classdeclaration = model.eContainer().eContainer();
			if (n4classdeclaration instanceof N4ClassDeclaration) {
				Type tclass = ((N4ClassDeclaration) n4classdeclaration).getDefinedType();
				EObject n4FieldDeclaration = model.eContainer();
				if (n4FieldDeclaration instanceof N4FieldDeclaration) {
					buildProposals((N4FieldDeclaration) n4FieldDeclaration, memberCollector, tclass,
							acceptor);
				}
			}
		}
	}

	private void buildProposals(N4FieldDeclaration n4FieldDecl, ContainerTypesHelper.MemberCollector memberCollector,
			Type tclass, IIdeContentProposalAcceptor acceptor) {

		MemberList<TMember> implementedMembers = memberCollector.allMembers((TClass) tclass, false, false, false);

		Set<String> implementedMembersNames = toSet(map(implementedMembers, member -> member.getMemberAsString()));
		for (TMember member : memberCollector.inheritedMembers((TClass) tclass)) {
			if (!implementedMembersNames.contains(member.getMemberAsString())) {
				if (!member.isDeclaredFinal() && !member.getName().equals("constructor")) {
					delegateProposal(acceptor, n4FieldDecl, member);
				}
			}
		}
	}

	private void delegateProposal(IIdeContentProposalAcceptor acceptor, N4FieldDeclaration n4FieldDecl,
			TMember member) {

		if (member instanceof TMethod) {
			buildMethodProposal(acceptor, n4FieldDecl, (TMethod) member);
		}
		if (member instanceof TField) {
			buildFieldProposal(acceptor, n4FieldDecl, (TField) member);
		}
		if (member instanceof FieldAccessor) {
			buildAccessorProposal(acceptor, n4FieldDecl, (FieldAccessor) member);
		}
	}

	private void buildMethodProposal(IIdeContentProposalAcceptor acceptor, N4FieldDeclaration location,
			TMethod method) {

		TypeRef retTRef = method.getReturnTypeRef();
		String methodBody = EMPTY_METHOD_BODY;
		if (!isVoid(retTRef) && !isReturningVoid(method) &&
				!(method.isDeclaredGenerator() && isAny(retTRef.getTypeArgsWithDefaults().get(1)))) {

			String params = Strings.join(", ", fp -> fp.getName(), method.getFpars());
			String methodReturnBody;
			if (!method.getFpars().isEmpty() &&
					!method.getFpars().get(method.getFpars().size() - 1).isVariadic()) {

				methodReturnBody = "\treturn super." + method.getName() + "(" + params + ");\n}";
			} else if (method.isDeclaredAsync() || (!method.getFpars().isEmpty() &&
					method.getFpars().get(method.getFpars().size() - 1).isVariadic())) {

				methodReturnBody = "\treturn null;\n}";
			} else {
				methodReturnBody = "\treturn super." + method.getName() + "();\n}";
			}
			methodBody = AUTO_GENERATED_METHOD_BODY_START + methodReturnBody;
		}

		StringBuilder strb = new StringBuilder();
		if (isAccessInternal(method)) {
			strb.append("@" + INTERNAL_ANNOTATION + "\n");
		}
		strb.append(getAnnotations(method.getAnnotations()));
		if (showAccessModifier(method)) {
			strb.append(getAccessModifier(method).getLiteral() + " ");
		}
		if (method.isDeclaredStatic()) {
			strb.append("static ");
		}
		if (method.isDeclaredAsync()) {
			strb.append("async ");
		}
		if (method.isGeneric()) {
			String typeVarsStr = Strings.join(", ", tv -> tv.getTypeAsString(), method.getTypeVars());
			strb.append("<").append(typeVarsStr).append("> ");
		}
		if (method.isDeclaredGenerator()) {
			strb.append("*");
		}
		String methodMemberAsString = getSignatureAsString(method);
		strb.append(methodMemberAsString);
		strb.append(methodBody);
		String proposalString = strb.toString();

		ContentAssistEntry proposal = createProposal(location, proposalString, methodMemberAsString, method);
		acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal));
	}

	private String getSignatureAsString(TMethod member) {
		StringBuilder strb = new StringBuilder();
		String fparsStr = Strings.join(", ", fp -> fp.getFormalParameterAsString(), member.getFpars());
		strb.append(member.getName());
		strb.append("(");
		strb.append(fparsStr);
		strb.append(")");
		if (isReturningVoid(member)) {
			strb.append(": void");
		} else {
			strb.append(getAsReturnType(member.getReturnTypeRef()));
		}
		if (member.isReturnValueOptional()) {
			strb.append('?');
		}
		return strb.toString();
	}

	private void buildFieldProposal(IIdeContentProposalAcceptor acceptor, N4FieldDeclaration location, TField field) {
		StringBuilder strb = new StringBuilder();
		if (isAccessInternal(field)) {
			strb.append("@" + INTERNAL_ANNOTATION + "\n");
		}
		strb.append(getAnnotations(field.getAnnotations()));
		if (showAccessModifier(field)) {
			strb.append(getAccessModifier(field).getLiteral() + " ");
		}
		if (field.isDeclaredStatic()) {
			strb.append("static ");
		}
		strb.append(field.getName());
		if (field.isOptional()) {
			strb.append('?');
		}
		if (field.getTypeRef() != null) {
			strb.append(" : ");
			strb.append(field.getTypeRef().getTypeRefAsString());
		}
		if (field.getAstElement() instanceof N4FieldDeclaration) {
			N4FieldDeclaration superDecl = (N4FieldDeclaration) field.getAstElement();
			if (superDecl.getExpression() != null) {
				strb.append(" = undefined; // TODO Auto-generated initializer");
			}
		}
		String proposalString = strb.toString();

		ContentAssistEntry proposal = createProposal(location, proposalString, field.getName(), field);
		acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal));
	}

	private void buildAccessorProposal(IIdeContentProposalAcceptor acceptor, N4FieldDeclaration location,
			FieldAccessor accessor) {

		String signatureAsString = getSignatureAsString(accessor);

		String type = null;
		String body = null;
		if (accessor instanceof TGetter) {
			type = "get ";
			String methodReturnBody = "\treturn super." + accessor.getName() + ";\n}";
			body = AUTO_GENERATED_METHOD_BODY_START + methodReturnBody;
		}
		if (accessor instanceof TSetter) {
			type = "set ";
			TSetter setter = (TSetter) accessor;
			body = " {\n" + AUTO_GENERATED_STUB
					+ "\tsuper." + accessor.getName() + " = " + setter.getFpar().getName() + ";\n}";
		}

		StringBuilder strb = new StringBuilder();
		if (isAccessInternal(accessor)) {
			strb.append("@" + INTERNAL_ANNOTATION + "\n");
		}
		strb.append(getAnnotations(accessor.getAnnotations()));
		if (showAccessModifier(accessor)) {
			strb.append(getAccessModifier(accessor).getLiteral() + " ");
		}
		if (accessor.isDeclaredStatic()) {
			strb.append("static ");
		}

		strb.append(type);
		strb.append(accessor.getName());
		strb.append(signatureAsString);
		strb.append(body);
		String proposalString = strb.toString();

		ContentAssistEntry proposal = createProposal(location, proposalString, signatureAsString, accessor);
		acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal));
	}

	private String getSignatureAsString(FieldAccessor accessor) {
		StringBuilder strb = new StringBuilder();
		if (accessor.isOptional()) {
			strb.append('?');
		}
		strb.append("(");
		if (accessor instanceof TSetter) {
			TSetter setter = (TSetter) accessor;
			strb.append(setter.getFpar().getFormalParameterAsString());
		}
		strb.append(")");
		if (accessor instanceof TGetter) {
			TGetter getter = (TGetter) accessor;
			strb.append(getAsReturnType(getter.getTypeRef()));
		}
		return strb.toString();
	}

	private String getAnnotations(EList<TAnnotation> annotations) {
		boolean hasOverride = false;
		String proposalString = "";
		for (TAnnotation annotation : annotations) {
			proposalString += "@" + annotation.getName() + "\n";
			if (annotation.getName().equals(OVERRIDE_ANNOTATION)) {
				hasOverride = true;
			}
		}
		if (!hasOverride) {
			proposalString += "@" + OVERRIDE_ANNOTATION + "\n";
		}
		return proposalString;
	}

	private boolean isReturningVoid(TMethod method) {
		boolean async = method.isDeclaredAsync();
		boolean generator = method.isDeclaredGenerator();
		TypeRef returnTypeRef = method.getReturnTypeRef();
		EList<TypeArgument> typeArgs = returnTypeRef.getTypeArgsWithDefaults();

		if (generator || async && (isAny(typeArgs.get(1)) || typeArgs.get(1) instanceof Wildcard)) {
			var firstReturnTypeArg = typeArgs.get(0);
			if (firstReturnTypeArg instanceof ParameterizedTypeRef) {
				if (firstReturnTypeArg.getDeclaredType() instanceof UndefinedType) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Resolves the simplified return type of a given method recursively and returns it as string.
	 */
	private String getAsReturnType(TypeRef typeRef) {
		return ": " + typeRef.getTypeRefAsString();
	}

	private boolean showAccessModifier(TMemberWithAccessModifier member) {
		MemberAccessModifier accessModifier = member.getMemberAccessModifier();
		return accessModifier != MemberAccessModifier.PROJECT;
	}

	private boolean isAccessInternal(TMemberWithAccessModifier member) {
		MemberAccessModifier accessModifier = member.getMemberAccessModifier();
		return accessModifier == MemberAccessModifier.PROTECTED_INTERNAL
				|| accessModifier == MemberAccessModifier.PUBLIC_INTERNAL;
	}

	private MemberAccessModifier getAccessModifier(TMemberWithAccessModifier member) {
		MemberAccessModifier accessModifier = member.getMemberAccessModifier();
		switch (accessModifier) {
		case PROTECTED_INTERNAL:
			return MemberAccessModifier.PROTECTED;
		case PUBLIC_INTERNAL:
			return MemberAccessModifier.PUBLIC;
		default:
			return accessModifier;
		}
	}

	private ContentAssistEntry createProposal(EObject location, String proposal, String signature, TMember member) {
		ICompositeNode node = NodeModelUtils.getNode(location);
		String prefix = NodeModelUtilsN4.getTokenTextWithHiddenTokens(node).trim();

		N4JSContentAssistEntry entry = new N4JSContentAssistEntry();
		entry.setProposal(proposal);
		entry.setPrefix(prefix);
		entry.setKind(SymbolKindUtil.getKind(member.eClass()));
		entry.setLabel(member.getMemberAsString());
		entry.setDescription("Override " + member.getContainingType().getName() + "#" + signature);
		entry.setFilterText(member.getName());

		return entry;
	}

	private ContentAssistEntry createProposal(String prefix, AnnotationDefinition annotation) {
		N4JSContentAssistEntry entry = new N4JSContentAssistEntry();
		entry.setProposal(annotation.name);
		entry.setPrefix(prefix);
		entry.setKind(SymbolKindUtil.getKind(N4JSPackage.eINSTANCE.getAnnotation()));
		entry.setLabel(annotation.name);
		entry.setDescription("add @" + annotation.name + " annotation");
		entry.setFilterText(annotation.name);

		return entry;
	}
}
