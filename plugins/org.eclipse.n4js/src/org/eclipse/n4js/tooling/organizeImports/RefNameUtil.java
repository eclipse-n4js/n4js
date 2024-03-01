/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tooling.organizeImports;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * Utility to find actual name that was used for given reference.
 */
class RefNameUtil {

	/**
	 * Finds name that is used as identifier.
	 */
	public static String findIdentifierName(IdentifierRef ref) {
		return join(map(filter(NodeModelUtils.findActualNodeFor(ref).getLeafNodes(), n -> !n.isHidden()),
				n -> n.getText()));
	}

	/**
	 * Finds the name in the ParameterizedTypeRef.
	 *
	 * @return null if no connection to AST
	 */
	public static String findTypeName(ParameterizedTypeRef ref) {
		ICompositeNode astNode = NodeModelUtils.findActualNodeFor(ref);
		if (astNode != null) {
			int prefixLen = 0;
			int suffixLen = 0;
			String nodeText = join(map(filter(astNode.getLeafNodes(), n -> !n.isHidden()), n -> n.getText()));

			if (!ref.getDefinedTypingStrategy().equals(TypingStrategy.NOMINAL)) {
				String typingLiteral = ref.getDefinedTypingStrategy().getLiteral();
				if (nodeText.startsWith(typingLiteral)) {
					// handle things like
					// foo2 : ~r~ /* ~r~ */ A
					// nodeText does not contain whitespace or comments, so it is like
					// ~r~A
					// drop typing strategy literal value and return just
					// A
					prefixLen = ref.getDefinedTypingStrategy().getLiteral().length();
				}
			}

			// handle A?
			if (ref.isFollowedByQuestionMark() && nodeText.endsWith("?")) {
				suffixLen = 1;
			}

			// handle A+
			if (ref.isDynamic() && nodeText.endsWith("+")) {
				suffixLen = 1;
			}

			return nodeText.substring(prefixLen, nodeText.length() - suffixLen);
		} else {
			return null;
		}
	}
}
