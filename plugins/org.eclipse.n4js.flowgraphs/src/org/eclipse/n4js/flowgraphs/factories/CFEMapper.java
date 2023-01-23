/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;

/**
 * Some {@link ControlFlowElement}s are not represented in the control flow graph but are replaced by another
 * {@link ControlFlowElement} as an optimization. For example, {@link ExpressionStatement}s are replaced by their
 * {@link Expression}s.
 */
public class CFEMapper {

	/**
	 * Maps the given {@link EObject} to another {@link EObject} which will be used in the control flow graph.
	 */
	static public ControlFlowElement map(ControlFlowElement cfe) {
		while (cfe instanceof LabelledStatement && ((LabelledStatement) cfe).getStatement() != null) {
			cfe = ((LabelledStatement) cfe).getStatement();
		}
		if (cfe instanceof ExpressionStatement && ((ExpressionStatement) cfe).getExpression() != null) {
			cfe = ((ExpressionStatement) cfe).getExpression();
		}
		return cfe;
	}

}
