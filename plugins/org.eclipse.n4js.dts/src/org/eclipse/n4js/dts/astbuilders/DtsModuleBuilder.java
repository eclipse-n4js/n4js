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
import org.eclipse.n4js.dts.TypeScriptParser.ModuleDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleNameContext;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4ModuleDeclaration;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link N4NamespaceDeclaration}s from {@link ModuleDeclarationContext} and all its children from
 * d.ts parse tree elements.
 */
public class DtsModuleBuilder extends AbstractDtsNamespaceBuilder<ModuleDeclarationContext, N4ModuleDeclaration> {

	/** Constructor. */
	public DtsModuleBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	public void enterModuleDeclaration(ModuleDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4ModuleDeclaration();
		String name = getModuleName(ctx.moduleName());
		result.setName(name);
		walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
	}

	// special: may be an Identifier or a StringLiteral
	private static String getModuleName(ModuleNameContext ctx) {
		if (ctx != null) {
			if (ctx.Identifier() != null) {
				return ctx.Identifier().getText();
			} else if (ctx.StringLiteral() != null) {
				return ParserContextUtil.trimStringLiteral(ctx.StringLiteral());
			}
		}
		return null;
	}
}
