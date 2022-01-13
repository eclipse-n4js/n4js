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

import org.eclipse.n4js.dts.TypeScriptParser.ColonSepTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefContext;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsTypeRefBuilder extends AbstractDtsSubBuilder<TypeRefContext, TypeReferenceNode<TypeRef>> {

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
		pTypeRef.setDeclaredTypeAsText(ctx.typeName().getText());
		result = N4JSFactory.eINSTANCE.createTypeReferenceNode();
		result.setTypeRefInAST(pTypeRef);
	}

}
