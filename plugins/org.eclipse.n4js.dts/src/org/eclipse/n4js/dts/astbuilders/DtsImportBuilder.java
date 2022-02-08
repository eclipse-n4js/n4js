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

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.ImportFromBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.ImportStatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.ImportedElementContext;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsImportBuilder extends AbstractDtsSubBuilder<ImportStatementContext, ImportDeclaration> {

	/** Constructor */
	public DtsImportBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_importStatement);
	}

	@Override
	public void enterImportFromBlock(ImportFromBlockContext ctx) {
		result = N4JSFactory.eINSTANCE.createImportDeclaration();
		String fromModule = ParserContextUtil.trimStringLiteral(ctx.StringLiteral());
		result.setImportFrom(fromModule != null);
		if (fromModule != null) {
			result.setModuleSpecifierAsText(fromModule);

			// trim extension
			String moduleName = URIUtils.trimFileExtension(URI.createFileURI(fromModule)).toFileString();

			TModule tModuleProxy = TypesFactory.eINSTANCE.createTModule();
			EReference eRef = N4JSPackage.eINSTANCE.getImportDeclaration_Module();
			ParserContextUtil.installProxy(resource, result, eRef, tModuleProxy, moduleName);
			result.setModule(tModuleProxy);
		}

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
					ParserContextUtil.installProxy(resource, nis, eRef, tExpElemProxy, ieName);
					nis.setImportedElement(tExpElemProxy);

					result.getImportSpecifiers().add(nis);
				}
			}

		} else if (ctx.identifierName() != null) {
			// not supported
		}
	}

}
