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

import com.google.common.collect.Lists
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.transpiler.Transformation

/**
 * Removes everything from the IM that is not required for .d.ts export.
 */
class TrimForDtsTransformation extends Transformation {

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		val toBeRemoved1 = Lists.newArrayList(state.im.scriptElements.filter[isPureStatement]);
		toBeRemoved1.forEach[remove(it)];

		val toBeRemoved2 = collectNodes(state.im, false, Expression, Block).filter[!isValueOfEnum(it)];
		toBeRemoved2.forEach[remove(it)];
	}

	def private boolean isPureStatement(EObject obj) {
		if (obj instanceof Statement) {
			if (obj instanceof VariableStatement
					|| obj instanceof FunctionDeclaration) {
				return false;
			}
			return true;
		}
		return false;
	}

	def private boolean isValueOfEnum(EObject obj) {
		val parent = obj?.eContainer();
		return parent instanceof N4EnumLiteral
			&& obj === (parent as N4EnumLiteral).valueExpression;
	}
}
