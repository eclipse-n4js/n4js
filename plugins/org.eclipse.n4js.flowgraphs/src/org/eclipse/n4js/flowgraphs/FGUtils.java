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

import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.AnnotationArgument;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.NamedExportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.n4js.n4JS.WithStatement;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * Utilities that are handy when dealing with the control flow graph and its elements.
 */
public class FGUtils {

	/**
	 * <b>Attention:</b> This call is expensive due to the nested call to
	 * {@link NodeModelUtils#getTokenText(org.eclipse.xtext.nodemodel.INode)}
	 *
	 * @return label text that is the actual text from the source code.
	 */
	public static String getSourceText(EObject eo) {
		ICompositeNode actualNode = NodeModelUtils.findActualNodeFor(eo);
		String text = NodeModelUtils.getTokenText(actualNode);
		return text;
	}

	/** Creates a readable but still unique name for a given {@link ControlFlowElement}. */
	public static String getNameID(EObject cfe) {
		String className = getClassName(cfe);
		String nameID = className + "#" + cfe.hashCode();
		return nameID;
	}

	/** @return the class name of the given cfe */
	public static String getClassName(EObject cfe) {
		String className = cfe.getClass().getSimpleName().toString();
		int idx = className.lastIndexOf("Impl");
		if (idx == className.length() - "Impl".length()) {
			className = className.substring(0, idx);
		}
		return className;
	}

	/** @return the containing {@link ControlFlowElement} such as the function's body. */
	public static ControlFlowElement getCFContainer(ControlFlowElement cfe) {
		ControlFlowElement curCFE = cfe;
		while (curCFE != null) {
			if (isCFContainer(curCFE)) {
				return curCFE;
			}

			curCFE = getContainer(curCFE);
		}
		throw new IllegalArgumentException("Could not find Container for: " + cfe.toString());
	}

	/**
	 * @return the container of the given {@link ControlFlowElement}. Omits AST elements that are not part of the CFG.
	 */
	private static ControlFlowElement getContainer(ControlFlowElement curCFE) {
		EObject eObj = curCFE;
		do {
			eObj = eObj.eContainer();
		} while (eObj != null && !(eObj instanceof ControlFlowElement));

		curCFE = (ControlFlowElement) eObj;
		return curCFE;
	}

	/** @return true iff the given {@link ControlFlowElement} is a container such as a function's body. */
	public static boolean isCFContainer(EObject cfe) {
		boolean isScript = cfe instanceof Script;
		boolean isBlock = cfe instanceof Block;
		boolean isExpression = cfe instanceof Expression;
		boolean isBindingPattern = cfe instanceof BindingPattern;
		if (!isScript && !isBlock && !isExpression && !isBindingPattern) {
			return false;
		}

		EObject cfeContainer = cfe.eContainer();
		EObject cfeContainer2 = (cfeContainer == null) ? null : cfeContainer.eContainer();

		boolean containerIsFunctionDeclaration = cfeContainer instanceof FunctionDeclaration;
		boolean containerIsFunctionDefinition = cfeContainer instanceof FunctionDefinition;
		boolean containerIsFieldAccessor = cfeContainer instanceof FieldAccessor;
		boolean containerIsFormalParameter = cfeContainer instanceof FormalParameter;
		boolean containerIsFieldDeclaration = cfeContainer instanceof N4FieldDeclaration;
		boolean containerIsAnnotationArgument = cfeContainer instanceof AnnotationArgument;
		boolean containerIsLiteralOrComputedPropertyName = cfeContainer instanceof LiteralOrComputedPropertyName;
		boolean containerIsNamedExportSpecifier = cfeContainer instanceof NamedExportSpecifier;
		boolean containerIsExportDeclaration = cfeContainer instanceof ExportDeclaration;
		boolean containerIsN4ClassDefinition = cfeContainer instanceof N4ClassDefinition;

		boolean container2IsN4FieldDeclaration = cfeContainer2 instanceof N4FieldDeclaration;

		boolean isCFContainer = false;
		isCFContainer |= isScript;
		isCFContainer |= isBlock && containerIsFunctionDeclaration;
		isCFContainer |= isBlock && containerIsFunctionDefinition;
		isCFContainer |= isBlock && containerIsFieldAccessor;
		isCFContainer |= isBindingPattern && containerIsFormalParameter;
		isCFContainer |= isExpression && containerIsFormalParameter;
		isCFContainer |= isExpression && containerIsFieldDeclaration;
		isCFContainer |= isExpression && containerIsAnnotationArgument;
		isCFContainer |= isExpression && containerIsLiteralOrComputedPropertyName && container2IsN4FieldDeclaration;
		isCFContainer |= isExpression && containerIsN4ClassDefinition;
		isCFContainer |= isExpression && containerIsNamedExportSpecifier;
		isCFContainer |= isExpression && containerIsExportDeclaration;
		return isCFContainer;
	}

	/**
	 * Returns true iff the represented {@link ControlFlowElement} is one of the following:
	 * <ul>
	 * <li>AbstractCaseClause</li>
	 * <li>Block</li>
	 * <li>DoStatement</li>
	 * <li>ForStatement</li>
	 * <li>IfStatement</li>
	 * <li>TryStatement</li>
	 * <li>SwitchStatement</li>
	 * <li>WhileStatement</li>
	 * <li>WithStatement</li>
	 * </ul>
	 */
	public static boolean isControlStatement(ControlFlowElement cfe) {
		boolean isControlStatement = false;
		isControlStatement |= cfe instanceof AbstractCaseClause;
		isControlStatement |= cfe instanceof Block;
		isControlStatement |= cfe instanceof DoStatement;
		isControlStatement |= cfe instanceof ForStatement;
		isControlStatement |= cfe instanceof IfStatement;
		isControlStatement |= cfe instanceof SwitchStatement;
		isControlStatement |= cfe instanceof TryStatement;
		isControlStatement |= cfe instanceof WhileStatement;
		isControlStatement |= cfe instanceof WithStatement;
		return isControlStatement;
	}

	/**
	 * Returns all containers of the given {@link ControlFlowElement} that are assignable from the given class. The
	 * results are all in the same container like the given cfe is.
	 */
	@SuppressWarnings("unchecked")
	public static <T> LinkedList<T> getAllContainersOfTypeUptoCFContainer(ControlFlowElement cfe, Class<T> clazz) {
		LinkedList<T> results = new LinkedList<>();
		ControlFlowElement curCFE = cfe;
		while (curCFE != null && !isCFContainer(curCFE)) {
			if (clazz.isAssignableFrom(curCFE.getClass())) {
				results.add((T) curCFE);
			}
			curCFE = getContainer(curCFE);
		}
		return results;
	}

	/** @return the first {@link Block} that is the direct child of a {@link CatchBlock} element, or null. */
	public static Block getCatchBlock(ControlFlowElement cfe) {
		LinkedList<Block> mightBeInCatchBlock = getAllContainersOfTypeUptoCFContainer(cfe, Block.class);
		for (Block block : mightBeInCatchBlock) {
			if (block.eContainer() instanceof CatchBlock) {
				return block;
			}
		}
		return null;
	}

	/** @return the AST depth of the given {@link EObject}. */
	public static int getASTDepth(EObject eObj) {
		int i;
		for (i = 0; eObj != null; i++) {
			eObj = eObj.eContainer();
		}
		return i;
	}
}
