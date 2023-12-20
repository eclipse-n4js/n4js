/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;

/**
 * This transformation will prepare the output code for module loading. Since dropping support for commonjs and SystemJS
 * and instead using ECMAScript 2015 imports/exports in the output code, this transformation is no longer doing much.
 */
public class ModuleWrappingTransformation extends Transformation {

	@Override
	public void assertPreConditions() {
		// true
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		// nothing
	}

	@Override
	public void transform() {
		// strip modifiers off all exported elements
		// (e.g. remove "public" from something like "export public let a = 'hello';")

		for (ModifiableElement me : filter(map(collectNodes(getState().im, ExportDeclaration.class, false),
				ed -> ed.getExportedElement()), ModifiableElement.class)) {

			me.getDeclaredModifiers().clear();
		}

		// the following is only required because earlier transformations are producing
		// invalid "export default var|let|const ..."
		// TODO instead of the next line, change the earlier transformations to not produce these invalid constructs
		for (ExportDeclaration ed : collectNodes(getState().im, ExportDeclaration.class, false)) {
			splitDefaultExportFromVarDecl(ed);
		}

		// add implicit import of "n4js-runtime"
		addEmptyImport(N4JSGlobals.N4JS_RUNTIME.getRawName());
	}

	/**
	 * Turns
	 *
	 * <pre>
	 * export default var|let|const C = ...
	 * </pre>
	 *
	 * into
	 *
	 * <pre>
	 * var|let|const C = ...
	 * export default C;
	 * </pre>
	 */
	private void splitDefaultExportFromVarDecl(ExportDeclaration exportDecl) {
		if (exportDecl.isDefaultExport()) {
			ExportableElement exportedElement = exportDecl.getExportedElement();
			if (exportedElement instanceof VariableStatement) {
				VariableStatement variableStatement = (VariableStatement) exportedElement;
				if (!isEmpty(filter(variableStatement.getVarDeclsOrBindings(), VariableBinding.class))) {
					throw new UnsupportedOperationException("unsupported: default-exported variable binding");
				}
				if (variableStatement.getVarDeclsOrBindings().size() > 1) {
					throw new UnsupportedOperationException(
							"unsupported: several default-exported variable declarations in a single export declaration");
				}
				VariableDeclaration varDecl = (VariableDeclaration) variableStatement.getVarDeclsOrBindings().get(0);
				SymbolTableEntry varDeclSTE = findSymbolTableEntryForElement(varDecl, true);
				insertBefore(exportDecl, variableStatement); // will remove exportedElement from exportDecl
				exportDecl.setDefaultExportedExpression(_IdentRef(varDeclSTE));
			}
		}
	}
}
