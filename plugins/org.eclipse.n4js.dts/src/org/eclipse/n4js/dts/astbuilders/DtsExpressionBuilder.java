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

import org.eclipse.n4js.dts.TypeScriptParser.SingleExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeParameterContext;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsExpressionBuilder extends AbstractDtsSubBuilder<SingleExpressionContext, Expression> {
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(resource);

	/** Constructor */
	public DtsExpressionBuilder(LazyLinkingResource resource) {
		super(resource);
	}

	@Override
	public void enterTypeParameter(TypeParameterContext ctx) {
		N4TypeVariable typeVar = N4JSFactory.eINSTANCE.createN4TypeVariable();
		typeVar.setName(ctx.identifierName().getText());
		if (ctx.constraint() != null && ctx.constraint().typeRef() != null) {
			TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(ctx.constraint().typeRef());
			typeVar.setDeclaredUpperBoundNode(trn);
		}
		if (ctx.defaultType() != null && ctx.defaultType().typeRef() != null) {
			typeVar.setDeclaredOptional(true);
			TypeReferenceNode<TypeRef> trn = typeRefBuilder.consume(ctx.defaultType().typeRef());
			typeVar.setDeclaredDefaultArgumentNode(trn);
		}
	}

}
