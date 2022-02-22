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

import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleNameContext;
import org.eclipse.n4js.n4JS.N4AbstractNamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4ModuleDeclaration;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link N4NamespaceDeclaration}s/{@link N4ModuleDeclaration}s from {@link ModuleDeclarationContext}
 * and all its children from d.ts parse tree elements.
 */
public class DtsModuleBuilder
		extends AbstractDtsNamespaceBuilder<ModuleDeclarationContext, N4AbstractNamespaceDeclaration> {

	/** Constructor. */
	public DtsModuleBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	public void enterModuleDeclaration(ModuleDeclarationContext ctx) {
		ModuleNameContext ctxName = ctx.moduleName();
		if (ctxName != null) {
			TerminalNode strLit = ctxName.StringLiteral();
			if (strLit != null) {
				// this module declaration actually declares a module
				result = doCreateModuleDeclaration(ParserContextUtil.trimStringLiteral(strLit));
				walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
			} else {
				TerminalNode identifier = ctxName.Identifier();
				if (identifier != null) {
					// this module declaration declares a "legacy module" that acts like a namespace
					boolean isExported = ParserContextUtil.isExported(ctx);
					result = doCreateN4NamespaceDeclaration(identifier.getText(), isExported);
					walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
				}
			}
		}
	}
}
