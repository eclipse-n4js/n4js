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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatement;

import java.util.Set;

import org.eclipse.n4js.dts.TypeScriptParser.ExportAsNamespaceContext;
import org.eclipse.n4js.dts.TypeScriptParser.ExportElementAsDefaultContext;
import org.eclipse.n4js.dts.TypeScriptParser.ExportElementDirectlyContext;
import org.eclipse.n4js.dts.TypeScriptParser.ExportElementsContext;
import org.eclipse.n4js.dts.TypeScriptParser.ExportEqualsContext;
import org.eclipse.n4js.dts.TypeScriptParser.ExportImportContext;
import org.eclipse.n4js.dts.TypeScriptParser.ExportModuleContext;
import org.eclipse.n4js.dts.TypeScriptParser.ExportStatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.IdentifierNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.ImportedElementContext;
import org.eclipse.n4js.dts.TypeScriptParser.MultipleExportElementsContext;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.dts.TypeScriptParser.StatementContext;
import org.eclipse.n4js.dts.utils.ParserContextUtils;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.NamedExportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceExportSpecifier;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.utils.UtilN4;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsExportBuilder extends AbstractDtsModuleRefBuilder<ExportStatementContext, ExportDeclaration> {

	/** Constructor */
	public DtsExportBuilder(AbstractDtsBuilder<?, ?> parent) {
		super(parent);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_exportStatement);
	}

	static String findExportEqualsIdentifier(ProgramContext ctx) {
		if (ctx.statementList() != null) {
			for (StatementContext stmtCtx : ctx.statementList().statement()) {
				if (stmtCtx.exportStatement() != null
						&& stmtCtx.exportStatement().exportStatementTail() instanceof ExportEqualsContext) {

					ExportEqualsContext eeCtx = (ExportEqualsContext) stmtCtx.exportStatement().exportStatementTail();
					return eeCtx.namespaceName().getText().toString();
				}
			}
		}
		return null;
	}

	/**
	 * A "directly exported" element, i.e. the "export" keyword is placed directly in front of the element's
	 * declaration.
	 */
	@Override
	public void enterExportElementDirectly(ExportElementDirectlyContext ctx) {
		// ignore here (handled in DtsScriptBuilder#addAndHandleExported())
	}

	/**
	 * <pre>
	 * declare class Cls {}
	 * export default Cls;
	 * </pre>
	 */
	@Override
	public void enterExportElementAsDefault(ExportElementAsDefaultContext ctx) {
		// because N4JS does not support exporting values, we convert
		// 'export default MyCls;'
		// to
		// 'export { MyCls as default };'

		IdentifierNameContext nameCtx = ctx.identifierName();
		if (nameCtx != null) {
			IdentifierRef idRef = newExpressionBuilder().consume(nameCtx);
			if (idRef != null) {
				result = N4JSFactory.eINSTANCE.createExportDeclaration();
				NamedExportSpecifier nes = N4JSFactory.eINSTANCE.createNamedExportSpecifier();
				nes.setExportedElement(idRef);
				nes.setAlias(UtilN4.EXPORT_DEFAULT_NAME);
				result.getNamedExports().add(nes);
			}
		}
	}

	/**
	 * <pre>
	 * declare class Cls {}
	 * export { Cls [as MyAlias] };
	 * </pre>
	 *
	 * OR
	 *
	 * <pre>
	 * export { Cls [as MyAlias] } from "./some/other/file.d.ts";
	 * </pre>
	 */
	@Override
	public void enterExportElements(ExportElementsContext ctx) {
		MultipleExportElementsContext mee = ctx.multipleExportElements();
		if (mee == null) {
			return;
		}

		result = N4JSFactory.eINSTANCE.createExportDeclaration();

		if (ctx.From() != null) {
			setModuleSpecifier(result, ctx.StringLiteral());
		}

		DtsExpressionBuilder expressionBuilder = newExpressionBuilder();
		for (ImportedElementContext impElemCtx : mee.importedElement()) {
			IdentifierNameContext nameCtx = impElemCtx.identifierName().size() >= 1 ? impElemCtx.identifierName(0)
					: null;
			if (nameCtx != null) {
				IdentifierRef idRef = expressionBuilder.consume(nameCtx);
				if (idRef != null) {
					NamedExportSpecifier nes = N4JSFactory.eINSTANCE.createNamedExportSpecifier();
					nes.setExportedElement(idRef);

					String alias = ParserContextUtils.getIdentifierName(
							impElemCtx.identifierName().size() >= 2 ? impElemCtx.identifierName(1) : null);
					if (alias != null && alias.length() > 0) {
						nes.setAlias(alias);
					}

					result.getNamedExports().add(nes);
				}
			}
		}
	}

	/**
	 * <pre>
	 * export * [as MyAlias] from "./some/other/file.d.ts";
	 * </pre>
	 */
	@Override
	public void enterExportModule(ExportModuleContext ctx) {
		result = N4JSFactory.eINSTANCE.createExportDeclaration();

		setModuleSpecifier(result, ctx.StringLiteral());

		NamespaceExportSpecifier nes = N4JSFactory.eINSTANCE.createNamespaceExportSpecifier();
		if (ctx.As() != null) {
			String alias = ParserContextUtils.getIdentifierName(ctx.identifierName());
			if (alias != null) {
				nes.setAlias(alias);
			}
		}
		result.setNamespaceExport(nes);
	}

	/**
	 * <pre>
	 * export class Cls {}
	 * export as namespace NS;
	 * </pre>
	 *
	 * Introduces a so-called "UMD global" called "NS", allowing use of Cls via the global NS, such as
	 *
	 * <pre>
	 * NS.Cls; // no explicit import required!
	 * </pre>
	 *
	 * but this is not allowed in modules:
	 *
	 * <pre>
	 * // main.ts
	 * import {SomethingElse} from "./some/where/else/m" // this import turns the current file into a module
	 * NS.Cls; // TypeScript error: "'NS' refers to a UMD global, but the current file is a module. Consider adding an import instead."
	 * </pre>
	 */
	@Override
	public void enterExportAsNamespace(ExportAsNamespaceContext ctx) {
		// TODO
	}

	/**
	 * <pre>
	 * // lib.d.ts
	 * declare class Cls {}
	 * export = Cls;
	 * </pre>
	 *
	 * Can be imported in a .ts files with
	 *
	 * <pre>
	 * import MyCls = require("./lib");
	 * new MyCls();
	 * </pre>
	 *
	 * or, iff the compiler flag 'esModuleInterop' is set, with a ES6 default import such as
	 *
	 * <pre>
	 * import MyCls from "./lib"
	 * new MyCls();
	 * </pre>
	 */
	@Override
	public void enterExportEquals(ExportEqualsContext ctx) {
		// done via #isExportedEquals()
	}

	@Override
	public void enterExportImport(ExportImportContext ctx) {
		// TODO
	}
}
