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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classBody;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classMember;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classMemberList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_propertyMember;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_propertyMemberDeclaration;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.AbstractDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ClassExtendsClauseContext;
import org.eclipse.n4js.dts.TypeScriptParser.ClassHeritageContext;
import org.eclipse.n4js.dts.TypeScriptParser.ClassImplementsClauseContext;
import org.eclipse.n4js.dts.TypeScriptParser.GetAccessorContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyMemberBaseContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyMemberContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyOrMethodContext;
import org.eclipse.n4js.dts.TypeScriptParser.SetAccessorContext;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsFormalParametersBuilder.DtsFormalParametersBuilder;
import org.eclipse.n4js.dts.astbuilders.AbstractDtsTypeVariablesBuilder.DtsN4TypeVariablesBuilder;
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.FormalParameter;
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
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsClassBuilder
		extends AbstractDtsBuilderWithHelpers<ClassDeclarationContext, N4ClassDeclaration> {

	private final DtsN4TypeVariablesBuilder typeVariablesBuilder = new DtsN4TypeVariablesBuilder(tokenStream, resource);
	private final DtsFormalParametersBuilder formalParametersBuilder = new DtsFormalParametersBuilder(tokenStream,
			resource);
	private final DtsPropertyNameBuilder propertyNameBuilder = new DtsPropertyNameBuilder(tokenStream, resource);
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(tokenStream, resource);

	/** Constructor */
	public DtsClassBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_classBody,
				RULE_classMemberList,
				RULE_classMember,
				RULE_propertyMemberDeclaration,
				RULE_propertyMember);
	}

	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4ClassDeclaration();
		result.setName(ctx.identifierOrKeyWord().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);

		if (ctx.Abstract() != null) {
			result.getDeclaredModifiers().add(N4Modifier.ABSTRACT);
		}

		List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(ctx.typeParameters());
		result.getTypeVars().addAll(typeVars);

		ClassHeritageContext heritage = ctx.classHeritage();
		if (heritage != null) {
			ClassExtendsClauseContext extendsClause = heritage.classExtendsClause();
			if (extendsClause != null && extendsClause.parameterizedTypeRef() != null) {
				ParameterizedTypeRef typeRef = typeRefBuilder.consume(extendsClause.parameterizedTypeRef());
				result.setSuperClassRef(ParserContextUtil.wrapInTypeRefNode(typeRef));
			}
			ClassImplementsClauseContext implementsClause = heritage.classImplementsClause();
			if (implementsClause != null && implementsClause.classOrInterfaceTypeList() != null
					&& implementsClause.classOrInterfaceTypeList().parameterizedTypeRef() != null) {
				// TODO classes implementing classes not supported in N4JS!
				for (ParameterizedTypeRefContext extendsTypeRefCtx : implementsClause.classOrInterfaceTypeList()
						.parameterizedTypeRef()) {
					ParameterizedTypeRef typeRef = typeRefBuilder.consume(extendsTypeRefCtx);
					result.getImplementedInterfaceRefs().add(ParserContextUtil.wrapInTypeRefNode(typeRef));
				}
			}
		}

		walker.enqueue(ctx.classBody());
	}

	@Override
	public void enterPropertyOrMethod(PropertyOrMethodContext ctx) {
		if (ctx.propertyName() == null) {
			return;
		}

		boolean isReadonly = ctx.ReadOnly() != null;
		boolean isStatic = false;
		PropertyMemberContext pmctx = (PropertyMemberContext) ctx.parent;
		if (pmctx.propertyMemberBase() != null) {
			PropertyMemberBaseContext pmb = pmctx.propertyMemberBase();
			isStatic = pmb.Static() != null;
		}

		AnnotableN4MemberDeclaration memberDecl = null;
		if (ctx.callSignature() == null) {
			// this is a property
			N4FieldDeclaration fd = N4JSFactory.eINSTANCE.createN4FieldDeclaration();

			fd.setDeclaredName(propertyNameBuilder.consume(ctx.propertyName()));
			fd.setDeclaredOptional(ctx.QuestionMark() != null);

			TypeRef typeRef = typeRefBuilder.consume(ctx.colonSepTypeRef());
			fd.setDeclaredTypeRefNode(ParserContextUtil.wrapInTypeRefNode(orAnyPlus(typeRef)));

			memberDecl = fd;

		} else {
			// this is a method
			N4MethodDeclaration md = N4JSFactory.eINSTANCE.createN4MethodDeclaration();
			md.setDeclaredName(propertyNameBuilder.consume(ctx.propertyName()));

			List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(ctx.callSignature().typeParameters());
			md.getTypeVars().addAll(typeVars);
			List<FormalParameter> fPars = formalParametersBuilder.consume(ctx.callSignature().parameterBlock());
			md.getFpars().addAll(fPars);
			TypeRef typeRef = typeRefBuilder.consume(ctx.callSignature().typeRef());
			md.setDeclaredReturnTypeRefNode(ParserContextUtil.wrapInTypeRefNode(orAnyPlus(typeRef)));

			memberDecl = md;
		}

		memberDecl.getDeclaredModifiers().add(N4Modifier.PUBLIC);
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
		addLocationInfo(memberDecl, ctx);
		result.getOwnedMembersRaw().add(memberDecl);
	}

	@Override
	public void enterGetAccessor(GetAccessorContext ctx) {
		N4GetterDeclaration getter = createGetAccessor(ctx);
		if (getter != null) {
			addLocationInfo(getter, ctx);
			result.getOwnedMembersRaw().add(getter);
		}
	}

	@Override
	public void enterSetAccessor(SetAccessorContext ctx) {
		N4SetterDeclaration setter = createSetAccessor(ctx);
		if (setter != null) {
			addLocationInfo(setter, ctx);
			result.getOwnedMembersRaw().add(setter);
		}
	}

	@Override
	public void enterAbstractDeclaration(AbstractDeclarationContext ctx) {
		// TODO Auto-generated method stub
		super.enterAbstractDeclaration(ctx);
	}

}
