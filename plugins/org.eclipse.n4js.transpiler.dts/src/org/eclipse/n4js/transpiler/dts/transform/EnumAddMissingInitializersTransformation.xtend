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
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.transpiler.Transformation

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*
import org.eclipse.n4js.n4JS.StringLiteral

/**
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

				var mustHaveInitializers = false;
				for (literal : rootElem.literals) {
					mustHaveInitializers = mustHaveInitializers
						|| (literal.valueExpression !== null && literal.valueExpression instanceof StringLiteral);

					if (mustHaveInitializers && literal.valueExpression === null) {
						addInitializer(literal);
					}
				}	
			}
		}
	}
	
	def addInitializer(N4EnumLiteral literal) {
		literal.valueExpression = _StringLiteral(literal.name);
	}
}
