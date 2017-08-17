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
package org.eclipse.n4js.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;

/**
 * This class provides helper methods for calculating hierarchical names of AST nodes.
 */
public class N4JSHierarchicalNameComputerHelper {

	/**
	 * Calculate the hierarchically qualified name of an EObject.
	 *
	 * @param eob
	 *            the EObject to calculate logical name for
	 * @param labelProvider
	 *            the label provider that knows how to display EObject instances
	 *
	 * @return the hierarchically
	 */
	public static String calculateLogicallyQualifiedDisplayName(EObject eob, LabelProvider labelProvider,
			boolean includeRoot) {
		// Calculate hierarchical logical name, e.g. C.m
		String text = labelProvider.getText(eob);
		EObject currContainer = eob.eContainer();
		while (currContainer != null) {
			if (isShowable(currContainer)) {
				text = labelProvider.getText(currContainer) + "." + text;
			}
			currContainer = currContainer.eContainer();
			if (currContainer != null && !includeRoot && currContainer instanceof Script)
				break;
		}
		return text;
	}

	/**
	 * Check if an EObject is showable in a hierarchical name.
	 *
	 * @param eobj
	 *            the EObject to check.
	 * @return true if the EObject is showable and false otherwise.
	 */
	public static boolean isShowable(EObject eobj) {
		return eobj instanceof N4MemberDeclaration || eobj instanceof N4ClassifierDefinition
				|| eobj instanceof FunctionDeclaration || eobj instanceof ExportedVariableDeclaration
				|| eobj instanceof Script || eobj instanceof TMember || eobj instanceof TClassifier
				|| eobj instanceof TModule;
	}

}
