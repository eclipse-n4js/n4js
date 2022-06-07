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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_importStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.TypeScriptParser.ImportAliasDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ImportFromBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.ImportStatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.ImportedElementContext;
import org.eclipse.n4js.dts.utils.ParserContextUtils;
import org.eclipse.n4js.dts.utils.TripleSlashDirective;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TypesFactory;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsImportBuilder extends AbstractDtsModuleRefBuilder<ImportStatementContext, ImportDeclaration> {

	/** Constructor */
	public DtsImportBuilder(AbstractDtsBuilder<?, ?> parent) {
		super(parent);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_importStatement);
	}

	/**
	 * Like {@link #consumeTripleSlashDirective(TripleSlashDirective)}, but for several directives.
	 */
	public List<ImportDeclaration> consumeTripleSlashDirectives(Iterable<TripleSlashDirective> tsds) {
		@SuppressWarnings("hiding")
		List<ImportDeclaration> result = new ArrayList<>();
		for (TripleSlashDirective tsd : tsds) {
			ImportDeclaration importDecl = consumeTripleSlashDirective(tsd);
			if (importDecl != null) {
				result.add(importDecl);
			}
		}
		return result;
	}

	/**
	 * Converts a single {@link TripleSlashDirective triple-slash directive} to an {@link ImportDeclaration}.
	 */
	public ImportDeclaration consumeTripleSlashDirective(TripleSlashDirective tsd) {
		if ("reference".equals(tsd.name)) {
			if (tsd.attr.isPresent()) {
				String attrName = tsd.attr.get().getKey();
				String attrValue = tsd.attr.get().getValue();
				if ("path".equals(attrName)) {
					ImportDeclaration importDecl = N4JSFactory.eINSTANCE.createImportDeclaration();
					setModuleSpecifier(importDecl, attrValue);
					return importDecl;
				} else if ("types".equals(attrName)) {
					ImportDeclaration importDecl = N4JSFactory.eINSTANCE.createImportDeclaration();
					attrValue = attrValue.trim();
					if (!attrValue.startsWith("@types/")
							&& !attrValue.startsWith("./")
							&& !attrValue.startsWith("../")) {
						attrValue = "@types/" + attrValue;
					}
					setModuleSpecifier(importDecl, attrValue);
					return importDecl;
				} else if ("lib".equals(attrName)) {
					// TODO library references in .d.ts files
				}
			}
		}
		return null;
	}

	@Override
	public void enterImportFromBlock(ImportFromBlockContext ctx) {
		result = N4JSFactory.eINSTANCE.createImportDeclaration();

		result.setImportFrom(ctx.From() != null); // will be null for bare imports
		setModuleSpecifier(result, ctx.StringLiteral()); // must also do this in case ctx.From() == null

		if (ctx.Multiply() != null) {
			// default import
			NamespaceImportSpecifier nsis = N4JSFactory.eINSTANCE.createNamespaceImportSpecifier();
			result.getImportSpecifiers().add(nsis);
			nsis.setAlias(ctx.identifierName().getText());
			nsis.setDeclaredDynamic(true);

		} else if (ctx.multipleImportElements() != null && ctx.multipleImportElements().importedElement() != null) {
			// named import
			for (ImportedElementContext ieCtx : ctx.multipleImportElements().importedElement()) {
				if (ieCtx.identifierName() != null && !ieCtx.identifierName().isEmpty()) {
					NamedImportSpecifier nis = N4JSFactory.eINSTANCE.createNamedImportSpecifier();
					String ieName = ieCtx.identifierName().get(0).getText();
					nis.setImportedElementAsText(ieName);
					if (ieCtx.identifierName().size() > 1) {
						String isAlias = ieCtx.identifierName().get(1).getText();
						nis.setAlias(isAlias);
					}

					TExportableElement tExpElemProxy = TypesFactory.eINSTANCE.createTExportableElement();
					EReference eRef = N4JSPackage.eINSTANCE.getNamedImportSpecifier_ImportedElement();
					ParserContextUtils.installProxy(resource, nis, eRef, tExpElemProxy, ieName);
					nis.setImportedElement(tExpElemProxy);

					result.getImportSpecifiers().add(nis);
				}
			}

		} else if (ctx.identifierName() != null) {
			// not supported
		}
	}

	@Override
	public void enterImportAliasDeclaration(ImportAliasDeclarationContext ctx) {
		if (ctx.Require() != null) {
			// import id = require('./someModule.js');
			String identifier = ParserContextUtils.getIdentifierName(ctx.Identifier());
			if (identifier == null) {
				return;
			}
			DtsScriptBuilder scriptBuilder = getScriptBuilder();
			if (scriptBuilder.isExportedEquals()
					&& identifier.equals(scriptBuilder.getExportEqualsIdentifier())) {
				// we have this situation:
				//
				// import id = require('./someModule.js');
				// export = id;
				//
				// --> ignore here (DtsExportBuilder will create a single re-export on N4JS side for both the import and
				// the export of DTS)
			} else {
				// TODO
			}
		}
	}
}
