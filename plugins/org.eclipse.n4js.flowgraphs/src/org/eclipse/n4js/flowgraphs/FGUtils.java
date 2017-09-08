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
package org.eclipse.n4js.flowgraphs;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * Utilities that are handy when dealing with the control flow graph and its elements.
 */
public class FGUtils {

	/** @returns label text that is the actual text from the source code */
	public static String getTextLabel(EObject eo) {
		ICompositeNode actualNode = NodeModelUtils.findActualNodeFor(eo);
		String text = NodeModelUtils.getTokenText(actualNode);
		return text;
	}

	/** Creates a readable but still unique name for a given {@link ControlFlowElement}. */
	public static String getNameID(ControlFlowElement cfe) {
		String className = getClassName(cfe);
		String nameID = className + "#" + cfe.hashCode();
		return nameID;
	}

	/** @returns the class name of the given cfe */
	public static String getClassName(ControlFlowElement cfe) {
		String className = cfe.getClass().getSimpleName().toString();
		int idx = className.lastIndexOf("Impl");
		if (idx == className.length() - "Impl".length()) {
			className = className.substring(0, idx);
		}
		return className;
	}

	/** @returns the containing {@link ControlFlowElement} such as the function's body. */
	public static ControlFlowElement getCFContainer(ControlFlowElement cfe) {
		ControlFlowElement curCFE = cfe;
		while (curCFE != null) {
			if (isCFContainer(curCFE)) {
				return curCFE;
			}

			EObject eObj = curCFE;
			do {
				eObj = eObj.eContainer();
			} while (eObj != null && !(eObj instanceof ControlFlowElement));

			curCFE = (ControlFlowElement) eObj;
		}
		throw new IllegalArgumentException("Could not find Container for: " + cfe.toString());
	}

	/** @returns true iff the given {@link ControlFlowElement} is a container such as a function's body. */
	public static boolean isCFContainer(ControlFlowElement cfe) {
		EObject cfeContainer = cfe.eContainer();

		boolean isScript = cfe instanceof Script;
		boolean isBlock = cfe instanceof Block;
		boolean isExpression = cfe instanceof Expression;
		boolean containerIsFunctionDeclaration = cfeContainer instanceof FunctionDeclaration;
		boolean containerIsFunctionDefinition = cfeContainer instanceof FunctionDefinition;
		boolean containerIsFieldAccessor = cfeContainer instanceof FieldAccessor;
		boolean containerIsFormalParameter = cfeContainer instanceof FormalParameter;

		boolean isCFContainer = false;
		isCFContainer |= isScript;
		isCFContainer |= isBlock && containerIsFunctionDeclaration;
		isCFContainer |= isBlock && containerIsFunctionDefinition;
		isCFContainer |= isBlock && containerIsFieldAccessor;
		isCFContainer |= isExpression && containerIsFormalParameter;
		return isCFContainer;
	}

	/**
	 * Returns true iff the represented {@link ControlFlowElement} is one of the following:
	 * <ul>
	 * <li>Block</li>
	 * <li>IfStatement</li>
	 * <li>ForStatement</li>
	 * <li>DoStatement</li>
	 * <li>WhileStatement</li>
	 * <li>TryStatement</li>
	 * <li>SwitchStatement</li>
	 * <li>AbstractCaseClause</li>
	 * </ul>
	 */
	public static boolean isControlElement(ControlFlowElement cfe) {
		boolean isControlElement = false;
		isControlElement |= cfe instanceof Block;
		isControlElement |= cfe instanceof IfStatement;
		isControlElement |= cfe instanceof ForStatement;
		isControlElement |= cfe instanceof DoStatement;
		isControlElement |= cfe instanceof WhileStatement;
		isControlElement |= cfe instanceof TryStatement;
		isControlElement |= cfe instanceof SwitchStatement;
		isControlElement |= cfe instanceof AbstractCaseClause;
		return isControlElement;
	}
}
