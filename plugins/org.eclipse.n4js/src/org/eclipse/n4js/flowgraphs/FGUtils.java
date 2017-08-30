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
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 *
 */
public class FGUtils {

	/**
	 * @returns label text that is the actual text from the source code
	 */
	public static String getTextLabel(EObject eo) {
		ICompositeNode actualNode = NodeModelUtils.findActualNodeFor(eo);
		String text = NodeModelUtils.getTokenText(actualNode);
		return text;
	}

	/**
	 * Creates a readable but still unique name for a given {@link ControlFlowElement}.
	 */
	public static String getNameID(ControlFlowElement cfe) {
		String className = getClassName(cfe);
		String nameID = className + "#" + cfe.hashCode();
		return nameID;
	}

	/**
	 */
	public static String getClassName(ControlFlowElement cfe) {
		String className = cfe.getClass().getSimpleName().toString();
		int idx = className.lastIndexOf("Impl");
		if (idx == className.length() - "Impl".length()) {
			className = className.substring(0, idx);
		}
		return className;
	}

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

	public static boolean isCFContainer(ControlFlowElement cfe) {
		EObject cfeContainer = cfe.eContainer();

		boolean isBlock = cfe instanceof Block;
		boolean containerIsFunctionDeclaration = cfeContainer instanceof FunctionDeclaration;
		boolean containerIsFunctionDefinition = cfeContainer instanceof FunctionDefinition;

		boolean isCFContainer = false;
		isCFContainer |= isBlock && (containerIsFunctionDeclaration || containerIsFunctionDefinition);
		return isCFContainer;
	}

}
