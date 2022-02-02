/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.astbuilders;

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classTail;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_propertyMember;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_propertyMemberDeclaration;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.AbstractDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.GetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyMemberBaseContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyMemberContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyOrMethodContext;
import org.eclipse.n4js.dts.TypeScriptParser.SetAccessorContext;
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4MemberAnnotationList;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsClassBuilder extends AbstractDtsSubBuilder<ClassDeclarationContext, N4ClassDeclaration> {
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(tokenStream, resource);
	private final DtsTypeVariablesBuilder typeVariablesBuilder = new DtsTypeVariablesBuilder(tokenStream, resource);

	/** Constructor */
	public DtsClassBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_classTail,
				RULE_classElementList,
				RULE_classElement,
				RULE_propertyMemberDeclaration,
				RULE_propertyMember);
	}

	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4ClassDeclaration();
		result.setName(ctx.identifierOrKeyWord().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);

		List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(ctx.typeParameters());
		result.getTypeVars().addAll(typeVars);

		walker.enqueue(ctx.classTail());
	}

	@Override
	public void enterPropertyOrMethod(PropertyOrMethodContext ctx) {
		if (ctx.propertyName() == null) {
			return;
		}

		boolean isReadonly = false, isStatic = false;
		PropertyMemberContext pmctx = (PropertyMemberContext) ctx.parent;
		if (pmctx.propertyMemberBase() != null) {
			PropertyMemberBaseContext pmb = pmctx.propertyMemberBase();
			isStatic = pmb.Static() != null;
			isReadonly = pmb.ReadOnly() != null;
		}

		AnnotableN4MemberDeclaration memberDecl = null;
		if (ctx.callSignature() == null) {
			// this is a property
			N4FieldDeclaration fd = N4JSFactory.eINSTANCE.createN4FieldDeclaration();

			LiteralOrComputedPropertyName locpn = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
			locpn.setLiteralName(ctx.propertyName().getText());
			fd.setDeclaredName(locpn);
			fd.setDeclaredOptional(ctx.QuestionMark() != null);

			TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(ctx.colonSepTypeRef());
			fd.setDeclaredTypeRefNode(trn);

			memberDecl = fd;

		} else {
			// this is a method
			N4MethodDeclaration md = N4JSFactory.eINSTANCE.createN4MethodDeclaration();
			LiteralOrComputedPropertyName locpn = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
			locpn.setLiteralName(ctx.propertyName().getText());
			md.setDeclaredName(locpn);

			TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(ctx.callSignature().typeRef());
			md.setDeclaredReturnTypeRefNode(trn);

			memberDecl = md;
		}

		memberDecl.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		if (isStatic) {
			if (isStatic) {
				if (isReadonly) {
					memberDecl.getDeclaredModifiers().add(N4Modifier.CONST);
				} else {
					memberDecl.getDeclaredModifiers().add(N4Modifier.STATIC);
				}
			} else {
				if (isReadonly) {
					N4MemberAnnotationList annList = N4JSFactory.eINSTANCE.createN4MemberAnnotationList();
					Annotation ann = N4JSFactory.eINSTANCE.createAnnotation();
					ann.setName(AnnotationDefinition.FINAL.name);
					annList.getAnnotations().add(ann);
					memberDecl.setAnnotationList(annList);
				}
			}
		}
		addLocationInfo(memberDecl, ctx);
		result.getOwnedMembersRaw().add(memberDecl);
	}

	@Override
	public void enterGetAccessor(GetAccessorContext ctx) {
		if (ctx.getter() == null || ctx.getter().propertyName() == null) {
			return;
		}

		N4GetterDeclaration getter = N4JSFactory.eINSTANCE.createN4GetterDeclaration();

		LiteralOrComputedPropertyName locpn = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
		locpn.setLiteralName(ctx.getter().propertyName().getText());
		getter.setDeclaredName(locpn);

		TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(ctx.colonSepTypeRef());
		getter.setDeclaredTypeRefNode(trn);

		PropertyMemberContext pmctx = (PropertyMemberContext) ctx.parent;
		if (pmctx.propertyMemberBase() != null) {
			PropertyMemberBaseContext pmb = pmctx.propertyMemberBase();
			if (pmb.Static() != null) {
				getter.getDeclaredModifiers().add(N4Modifier.STATIC);
			}
		}

		getter.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		addLocationInfo(getter, ctx);
		result.getOwnedMembersRaw().add(getter);
	}

	@Override
	public void enterSetAccessor(SetAccessorContext ctx) {
		if (ctx.setter() == null || ctx.setter().propertyName() == null) {
			return;
		}

		N4SetterDeclaration setter = N4JSFactory.eINSTANCE.createN4SetterDeclaration();

		LiteralOrComputedPropertyName locpn = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
		locpn.setLiteralName(ctx.setter().propertyName().getText());
		setter.setDeclaredName(locpn);

		FormalParameter fpar = N4JSFactory.eINSTANCE.createFormalParameter();
		setter.setFpar(fpar);
		TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(ctx.colonSepTypeRef());
		fpar.setDeclaredTypeRefNode(trn);
		if (ctx.Identifier() != null) {
			fpar.setName(ctx.Identifier().getText());
		} else if (ctx.bindingPattern() != null) {
			fpar.setBindingPattern(new DtsBindingPatternBuilder(this).consume(ctx.bindingPattern()));
		}

		PropertyMemberContext pmctx = (PropertyMemberContext) ctx.parent;
		if (pmctx.propertyMemberBase() != null) {
			PropertyMemberBaseContext pmb = pmctx.propertyMemberBase();
			if (pmb.Static() != null) {
				setter.getDeclaredModifiers().add(N4Modifier.STATIC);
			}
		}

		setter.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		addLocationInfo(setter, ctx);
		result.getOwnedMembersRaw().add(setter);
	}

	@Override
	public void enterAbstractDeclaration(AbstractDeclarationContext ctx) {
		// TODO Auto-generated method stub
		super.enterAbstractDeclaration(ctx);
	}

}
