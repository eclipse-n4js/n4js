/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.assistants

import org.eclipse.n4js.n4JS.ArrayBindingPattern
import org.eclipse.n4js.n4JS.ArrayElement
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.BindingElement
import org.eclipse.n4js.n4JS.BindingPattern
import org.eclipse.n4js.n4JS.BindingProperty
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ObjectBindingPattern
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PrimaryExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.transpiler.TransformationAssistant

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * FIXME API doc
 */
class DestructuringAssistant extends TransformationAssistant {


	public def PrimaryExpression convertBindingPatternToArrayOrObjectLiteral(BindingPattern binding) {
		return switch(binding) {
			ArrayBindingPattern: convertArrayBindingPatternToArrayLiteral(binding)
			ObjectBindingPattern: convertObjectBindingPatternToObjectLiteral(binding)
		};
	}

	public def ArrayLiteral convertArrayBindingPatternToArrayLiteral(ArrayBindingPattern binding) {
		return _ArrLit(
			binding.elements.map[convertBindingElementToArrayElement]
		);
	}

	public def ObjectLiteral convertObjectBindingPatternToObjectLiteral(ObjectBindingPattern binding) {
		return _ObjLit(
			binding.properties.map[convertBindingPropertyToPropertyNameValuePair]
		);
	}

	private def ArrayElement convertBindingElementToArrayElement(BindingElement element) {
		val nestedPattern = element.nestedPattern;
		val varDecl = element.varDecl;

		var Expression lhs;
		var Expression rhs;
		if(nestedPattern!==null) {
			lhs = convertBindingPatternToArrayOrObjectLiteral(nestedPattern);
			rhs = element.expression; // may be null (which is ok, see below)
		} else if(varDecl!==null) {
			val ste_varDecl = findSymbolTableEntryForElement(varDecl, true);
			lhs = _IdentRef(ste_varDecl);
			rhs = varDecl.expression; // may be null (which is ok, see below)
		} else {
			return _ArrayPadding();
		}

		return _ArrayElement(
			element.rest,
			if(rhs!==null) {
				_AssignmentExpr(lhs, rhs)
			} else {
				lhs
			}
		);
	}

	private def PropertyNameValuePair convertBindingPropertyToPropertyNameValuePair(BindingProperty property) {
		return _PropertyNameValuePair(
			property.name,
			convertBindingElementToArrayElement(property.value).expression
		);
	}
}
