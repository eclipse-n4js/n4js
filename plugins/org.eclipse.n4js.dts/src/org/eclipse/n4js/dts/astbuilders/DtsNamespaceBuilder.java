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

import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link N4NamespaceDeclaration}s from {@link NamespaceDeclarationContext} and all its children from
 * d.ts parse tree elements.
 */
public class DtsNamespaceBuilder
		extends AbstractDtsNamespaceBuilder<NamespaceDeclarationContext, N4NamespaceDeclaration> {

	/** Constructor. */
	public DtsNamespaceBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	public void enterNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4NamespaceDeclaration();
		result.setName(ctx.namespaceName().getText());
		boolean isExported = ParserContextUtil.isExported(ctx);
		if (isExported) {
			result.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		}
		walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
	}
}
