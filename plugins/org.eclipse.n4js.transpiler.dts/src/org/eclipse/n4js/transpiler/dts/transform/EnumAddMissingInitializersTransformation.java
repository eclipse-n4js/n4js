/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;

/**
 * Transformer to add initializers to string-/number-based enums
 */
public class EnumAddMissingInitializersTransformation extends Transformation {

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		makeInferredTypesExplicit();
	}

	private void makeInferredTypesExplicit() {
		for (ScriptElement rootElemRaw : getState().im.getScriptElements()) {
			ScriptElement rootElem = (rootElemRaw instanceof ExportDeclaration)
					? ((ExportDeclaration) rootElemRaw).getExportedElement()
					: rootElemRaw;

			if (rootElem instanceof N4EnumDeclaration) {
				N4EnumDeclaration enumDecl = (N4EnumDeclaration) rootElem;
				EnumKind enumKind = N4JSLanguageUtils.getEnumKind(enumDecl);
				boolean isLiteralBased = enumKind != EnumKind.Normal;
				boolean isPartiallyInitialized = false;
				for (N4EnumLiteral literal : enumDecl.getLiterals()) {
					Expression valueExpr = literal.getValueExpression();
					isPartiallyInitialized = isPartiallyInitialized
							|| (valueExpr != null && valueExpr instanceof StringLiteral);

					if (valueExpr == null) {
						if (isPartiallyInitialized) {
							literal.setValueExpression(_StringLiteral(literal.getName()));
						} else if (isLiteralBased) {
							SymbolTableEntry propSTE = getState().steCache.mapNamedElement_2_STE.get(literal);
							TEnumLiteral tLiteral = (propSTE instanceof SymbolTableEntryOriginal)
									? (TEnumLiteral) ((SymbolTableEntryOriginal) propSTE).getOriginalTarget()
									: null;
							Literal newLit = TranspilerUtils.enumLiteralToNumericOrStringLiteral(tLiteral);
							literal.setValueExpression(newLit);
						}
					}
				}
			}
		}
	}

}
