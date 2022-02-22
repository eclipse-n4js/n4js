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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_arrayTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_conditionalTypeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_intersectionTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_operatorTypeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_primaryTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeRefWithModifiers;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_unionTypeExpression;

import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.ColonSepTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeArgumentListContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefContext;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsTypeRefBuilder extends AbstractDtsSubBuilder<TypeRefContext, TypeReferenceNode<TypeRef>> {

	/** Constructor */
	public DtsTypeRefBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_typeRef,
				RULE_conditionalTypeRef,
				RULE_unionTypeExpression,
				RULE_intersectionTypeExpression,
				RULE_operatorTypeRef,
				RULE_arrayTypeExpression,
				RULE_primaryTypeExpression,
				RULE_typeRefWithModifiers);
	}

	/** @return a {@link TypeReferenceNode} from the given context. Consumes the given context and all its children. */
	public TypeReferenceNode<TypeRef> consume(ColonSepTypeRefContext ctx) {
		if (ctx == null) {
			return null;
		}
		return consume(ctx.typeRef());
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
		if (!isPrimitive(pTypeRef)) {
			pTypeRef.setDefinedTypingStrategy(TypingStrategy.STRUCTURAL);
		}

		Type typeProxy = TypesFactory.eINSTANCE.createType();
		EReference eRef = TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType();
		ParserContextUtil.installProxy(resource, pTypeRef, eRef, typeProxy, lastSeg);
		pTypeRef.setDeclaredType(typeProxy);

		if (ctx.typeArguments() != null && ctx.typeArguments().typeArgumentList() != null) {
			TypeArgumentListContext typeArgumentList = ctx.typeArguments().typeArgumentList();
			for (TypeArgumentContext targ : typeArgumentList.typeArgument()) {
				TypeReferenceNode<TypeRef> trn = new DtsTypeRefBuilder(tokenStream, resource).consume(targ.typeRef());
				if (trn != null && trn.getTypeRefInAST() != null) {
					TypeRef typeArg = trn.getTypeRefInAST();
					pTypeRef.getDeclaredTypeArgs().add(typeArg);
				}
			}
		}

		result = N4JSFactory.eINSTANCE.createTypeReferenceNode();
		result.setTypeRefInAST(pTypeRef);
	}

	private boolean isPrimitive(ParameterizedTypeRef pTypeRef) {
		switch (pTypeRef.getDeclaredTypeAsText()) {
		case "number":
		case "string":
			return true;
		default:
			return false;
		}
	}
}
