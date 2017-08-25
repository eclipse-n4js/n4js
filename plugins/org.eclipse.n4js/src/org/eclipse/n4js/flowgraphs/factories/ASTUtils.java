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
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.Statement;

public class ASTUtils {

	public static String getLabel(Statement stmt) {
		EObject container = stmt.eContainer();
		if (container instanceof ExpressionStatement) {
			container = container.eContainer();
		}
		if (container instanceof LabelledStatement) {
			LabelledStatement lblStmt = (LabelledStatement) container;
			return lblStmt.getName();
		}
		return null;
	}

	/** Returns a detailed string about the given node */
	public static String getNodeDetailString(Node node) {
		ControlFlowElement nCFE = node.getControlFlowElement();
		String edgeStr = FGUtils.getClassName(nCFE) + ":" + node.name + ":" + FGUtils.getTextLabel(nCFE);
		return edgeStr;
	}

	public static ControlFlowElement getCFContainer(ControlFlowElement cfe) {
		ControlFlowElement curCFE = cfe;
		while (curCFE != null) {
			if (isCFContainer(curCFE)) {
				return curCFE;
			}
			EObject eObj = curCFE.eContainer();
			if (!(eObj instanceof ControlFlowElement)) {
				return null;
			}
			curCFE = (ControlFlowElement) eObj;
		}
		return null;
	}

	public static boolean isCFContainer(ControlFlowElement cfe) {
		boolean isCFContainer = false;
		isCFContainer |= cfe instanceof Block && cfe.eContainer() instanceof FunctionDeclaration;
		return isCFContainer;
	}
}
