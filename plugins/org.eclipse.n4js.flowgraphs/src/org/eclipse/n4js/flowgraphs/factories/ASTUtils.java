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
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.Statement;

/** Utilities for factory classes. */
public class ASTUtils {

	/** Returns a detailed string about the given node */
	public static String getNodeDetailString(Node node) {
		ControlFlowElement nCFE = node.getControlFlowElement();
		String edgeStr = FGUtils.getClassName(nCFE) + ":" + node.name + ":" + FGUtils.getSourceText(nCFE);
		return edgeStr;
	}

	/**
	 * @return the {@link LabelledStatement} that the given stmt refers to or represents.
	 */
	public static LabelledStatement getLabelledStatement(Statement stmt) {
		if (stmt instanceof LabelledStatement) {
			return (LabelledStatement) stmt;
		}
		if (stmt instanceof BreakStatement) {
			return ((BreakStatement) stmt).getLabel();
		}
		if (stmt instanceof ContinueStatement) {
			return ((ContinueStatement) stmt).getLabel();
		}
		EObject container = stmt.eContainer();
		if (container instanceof LabelledStatement) {
			LabelledStatement lblStmt = (LabelledStatement) container;
			return lblStmt;
		}
		return null;
	}

}
