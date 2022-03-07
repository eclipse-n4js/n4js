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

import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.ArrayTypeExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.ColonSepTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ConditionalTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.IntersectionTypeExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.OperatorTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.PrimaryTypeExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentListContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefWithModifiersContext;
import org.eclipse.n4js.dts.TypeScriptParser.UnionTypeExpressionContext;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsTypeRefBuilder extends AbstractDtsBuilder<TypeRefContext, TypeRef> {

	/** Constructor */
	public DtsTypeRefBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
		// RULE_typeRef,
		// RULE_conditionalTypeRef,
		// RULE_unionTypeExpression,
		// RULE_intersectionTypeExpression,
		// RULE_operatorTypeRef,
		// RULE_arrayTypeExpression,
		// RULE_primaryTypeExpression,
		// RULE_typeRefWithModifiers
		);
	}

	/** @return a {@link TypeReferenceNode} from the given context. Consumes the given context and all its children. */
	public TypeRef consume(ColonSepTypeRefContext ctx) {
		if (ctx == null) {
			return null;
		}
		return consume(ctx.typeRef());
	}

	public TypeRef consume(UnionTypeExpressionContext ctx) {
		return doConsume(ctx);
	}

	public TypeRef consume(IntersectionTypeExpressionContext ctx) {
		return doConsume(ctx);
	}

	public TypeRef consume(OperatorTypeRefContext ctx) {
		return doConsume(ctx);
	}

	/** @return a wrapped {@link ParameterizedTypeRef}, created from the given context. */
	public ParameterizedTypeRef consume(ParameterizedTypeRefContext ctx) {
		return (ParameterizedTypeRef) doConsume(ctx);
	}

	@Override
	public void enterTypeRef(TypeRefContext ctx) {
		if (ctx.conditionalTypeRef() != null) {
			enterConditionalTypeRef(ctx.conditionalTypeRef());
		}
	}

	@Override
	public void enterConditionalTypeRef(ConditionalTypeRefContext ctx) {
		if (ctx.unionTypeExpression().size() == 1) {
			enterUnionTypeExpression(ctx.unionTypeExpression(0));
		}
		// FIXME
	}

	@Override
	public void enterUnionTypeExpression(UnionTypeExpressionContext ctx) {
		int n = ctx.intersectionTypeExpression().size();
		if (n == 1) {
			enterIntersectionTypeExpression(ctx.intersectionTypeExpression(0));
			return;
		} else if (n >= 2) {
			UnionTypeExpression ute = TypeRefsFactory.eINSTANCE.createUnionTypeExpression();
			for (IntersectionTypeExpressionContext childCtx : ctx.intersectionTypeExpression()) {
				TypeRef childTypeRef = new DtsTypeRefBuilder(tokenStream, resource).consume(childCtx);
				if (childTypeRef != null) {
					ute.getTypeRefs().add(childTypeRef);
				}
			}
			result = ute;
		}
	}

	@Override
	public void enterIntersectionTypeExpression(IntersectionTypeExpressionContext ctx) {
		int n = ctx.operatorTypeRef().size();
		if (n == 1) {
			enterOperatorTypeRef(ctx.operatorTypeRef(0));
			return;
		} else if (n >= 2) {
			IntersectionTypeExpression ite = TypeRefsFactory.eINSTANCE.createIntersectionTypeExpression();
			for (OperatorTypeRefContext childCtx : ctx.operatorTypeRef()) {
				TypeRef childTypeRef = new DtsTypeRefBuilder(tokenStream, resource).consume(childCtx);
				if (childTypeRef != null) {
					ite.getTypeRefs().add(childTypeRef);
				}
			}
			result = ite;
		}
	}

	@Override
	public void enterOperatorTypeRef(OperatorTypeRefContext ctx) {
		enterArrayTypeExpression(ctx.arrayTypeExpression());
		// FIXME
	}

	@Override
	public void enterArrayTypeExpression(ArrayTypeExpressionContext ctx) {
		enterPrimaryTypeExpression(ctx.primaryTypeExpression());
		// FIXME
	}

	@Override
	public void enterPrimaryTypeExpression(PrimaryTypeExpressionContext ctx) {
		if (ctx.typeRefWithModifiers() != null) {
			enterTypeRefWithModifiers(ctx.typeRefWithModifiers());
		} else if (ctx.typeRef() != null) {
			// parentheses
			enterTypeRef(ctx.typeRef());
		}
		// FIXME
	}

	@Override
	public void enterTypeRefWithModifiers(TypeRefWithModifiersContext ctx) {
		if (ctx.parameterizedTypeRef() != null) {
			enterParameterizedTypeRef(ctx.parameterizedTypeRef());
		}
		// FIXME
	}

	@Override
	public void enterParameterizedTypeRef(ParameterizedTypeRefContext ctx) {
		ParameterizedTypeRef pTypeRef = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		String text = ctx.typeName().getText();
		String[] segs = text.split(Pattern.quote(ParserContextUtil.NAMESPACE_ACCESS_DELIMITER));
		for (int i = 0; i < segs.length - 1; i++) {
			String currSeg = segs[i];
			NamespaceLikeRef nslRef = TypeRefsFactory.eINSTANCE.createNamespaceLikeRef();
			nslRef.setDeclaredTypeAsText(currSeg);

			Type nsProxy = TypesFactory.eINSTANCE.createType();
			EReference eRef = TypeRefsPackage.eINSTANCE.getNamespaceLikeRef_DeclaredType();
			ParserContextUtil.installProxy(resource, nslRef, eRef, nsProxy, currSeg);
			nslRef.setDeclaredType(nsProxy);

			pTypeRef.getAstNamespaceLikeRefs().add(nslRef);
		}
		String lastSeg = segs[segs.length - 1];
		pTypeRef.setDeclaredTypeAsText(lastSeg);
		pTypeRef.setDeclaredType(null);

		Type typeProxy = TypesFactory.eINSTANCE.createType();
		EReference eRef = TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType();
		ParserContextUtil.installProxy(resource, pTypeRef, eRef, typeProxy, lastSeg);
		pTypeRef.setDeclaredType(typeProxy);

		if (ctx.typeArguments() != null && ctx.typeArguments().typeArgumentList() != null) {
			TypeArgumentListContext typeArgumentList = ctx.typeArguments().typeArgumentList();
			for (TypeArgumentContext targ : typeArgumentList.typeArgument()) {
				TypeRef typeArg = new DtsTypeRefBuilder(tokenStream, resource).consume(targ.typeRef());
				if (typeArg != null) {
					pTypeRef.getDeclaredTypeArgs().add(typeArg);
				}
			}
		}

		result = pTypeRef;
	}
}
