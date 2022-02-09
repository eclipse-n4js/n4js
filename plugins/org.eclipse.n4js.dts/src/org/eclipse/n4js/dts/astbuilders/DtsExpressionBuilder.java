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

import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierExpressionContext;
import org.eclipse.n4js.dts.TypeScriptParser.SingleExpressionContext;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsExpressionBuilder extends AbstractDtsSubBuilder<SingleExpressionContext, Expression> {

	/** Constructor */
	public DtsExpressionBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	public void enterIdentifierExpression(IdentifierExpressionContext ctx) {
		if (ctx.identifierName() == null || ctx.identifierName().getText().isBlank()) {
			return;
		}

		String idName = ctx.identifierName().getText();
		IdentifierRef iRef = N4JSFactory.eINSTANCE.createIdentifierRef();
		iRef.setIdAsText(idName);

		IdentifiableElement ieProxy = TypesFactory.eINSTANCE.createIdentifiableElement();
		EReference eRef = N4JSPackage.eINSTANCE.getIdentifierRef_Id();
		ParserContextUtil.installProxy(resource, iRef, eRef, ieProxy, idName);

		iRef.setId(ieProxy);
		result = iRef;
	}
}
