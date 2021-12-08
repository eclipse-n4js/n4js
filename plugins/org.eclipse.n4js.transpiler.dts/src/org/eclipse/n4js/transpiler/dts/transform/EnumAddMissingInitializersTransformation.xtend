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
package org.eclipse.n4js.transpiler.dts.transform

import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.transpiler.utils.TranspilerUtils
import org.eclipse.n4js.ts.types.TEnumLiteral
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Transformer to add initializers to string-/number-based enums
 */
class EnumAddMissingInitializersTransformation extends Transformation {

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		makeInferredTypesExplicit();
	}

	def private void makeInferredTypesExplicit() {
		for (rootElemRaw : state.im.scriptElements) {
			val rootElem = if (rootElemRaw instanceof ExportDeclaration) rootElemRaw.exportedElement else rootElemRaw;
			if (rootElem instanceof N4EnumDeclaration) {

				val enumKind = N4JSLanguageUtils.getEnumKind(rootElem);
				val isLiteralBased = enumKind !== EnumKind.Normal;
				var isPartiallyInitialized = false;
				for (literal : rootElem.literals) {
					isPartiallyInitialized = isPartiallyInitialized
						|| (literal.valueExpression !== null && literal.valueExpression instanceof StringLiteral);

					if (literal.valueExpression === null) {
						if (isPartiallyInitialized) {
							literal.valueExpression = _StringLiteral(literal.name);
						} else if (isLiteralBased) {
							val propSTE = state.steCache.mapNamedElement_2_STE.get(literal);
							val tLiteral = if (propSTE instanceof SymbolTableEntryOriginal) propSTE.originalTarget as TEnumLiteral;
							val newLit = TranspilerUtils.enumLiteralToNumericOrStringLiteral(tLiteral);
							literal.valueExpression = newLit;
						}
					}
				}	
			}
		}
	}
	
}
