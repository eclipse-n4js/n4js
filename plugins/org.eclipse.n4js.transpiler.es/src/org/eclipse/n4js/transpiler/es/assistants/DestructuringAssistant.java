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
package org.eclipse.n4js.transpiler.es.assistants;

import static com.google.common.collect.Iterables.toArray;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrayElement;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrayPadding;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._AssignmentExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ObjLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyNameValuePair;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PrimaryExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.transpiler.TransformationAssistant;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;

/**
 * A {@link TransformationAssistant} providing helper functionality for dealing with ES2015 destructuring.
 */
public class DestructuringAssistant extends TransformationAssistant {

	/**
	 * Converts the given array or object binding pattern into an array or object literal that, if used on the
	 * right-hand side of an assignment expression, performs the equivalent destructuring operation.
	 * <p>
	 * Expression for default values are removed from the given binding, so the given binding is incomplete after this
	 * method returns. It is only guaranteed that (1) the given binding is not removed from its contained and (2) it
	 * will still include the same variable declarations as before, which can be retrieved via
	 * {@link VariableDeclarationOrBinding#getAllVariableDeclarations()} on the containing variable binding.
	 */
	public PrimaryExpression convertBindingPatternToArrayOrObjectLiteral(BindingPattern binding) {
		if (binding instanceof ArrayBindingPattern) {
			return convertArrayBindingPatternToArrayLiteral((ArrayBindingPattern) binding);
		}
		if (binding instanceof ObjectBindingPattern) {
			return convertObjectBindingPatternToObjectLiteral((ObjectBindingPattern) binding);
		}
		return null;
	}

	/**
	 * Same as {@link #convertBindingPatternToArrayOrObjectLiteral(BindingPattern)}, but only for array binding
	 * patterns.
	 */
	public ArrayLiteral convertArrayBindingPatternToArrayLiteral(ArrayBindingPattern binding) {
		ArrayElement[] elems = toArray(map(binding.getElements(), elem -> convertBindingElementToArrayElement(elem)),
				ArrayElement.class);

		return _ArrLit(elems);
	}

	/**
	 * Same as {@link #convertBindingPatternToArrayOrObjectLiteral(BindingPattern)}, but only for object binding
	 * patterns.
	 */
	public ObjectLiteral convertObjectBindingPatternToObjectLiteral(ObjectBindingPattern binding) {
		PropertyNameValuePair[] pairs = toArray(
				map(binding.getProperties(), prop -> convertBindingPropertyToPropertyNameValuePair(prop)),
				PropertyNameValuePair.class);
		return _ObjLit(pairs);
	}

	private ArrayElement convertBindingElementToArrayElement(BindingElement element) {
		BindingPattern nestedPattern = element.getNestedPattern();
		VariableDeclaration varDecl = element.getVarDecl();

		Expression lhs;
		Expression rhs;
		if (nestedPattern != null) {
			lhs = convertBindingPatternToArrayOrObjectLiteral(nestedPattern);
			rhs = element.getExpression(); // may be null (which is ok, see below)
		} else if (varDecl != null) {
			SymbolTableEntry ste_varDecl = findSymbolTableEntryForElement(varDecl, true);
			lhs = _IdentRef(ste_varDecl);
			rhs = varDecl.getExpression(); // may be null (which is ok, see below)
		} else {
			return _ArrayPadding();
		}

		return _ArrayElement(
				element.isRest(),
				(rhs != null) ? _AssignmentExpr(lhs, rhs) : lhs);
	}

	private PropertyNameValuePair convertBindingPropertyToPropertyNameValuePair(BindingProperty property) {
		return _PropertyNameValuePair(
				property.getName(),
				convertBindingElementToArrayElement(property.getValue()).getExpression());
	}
}
