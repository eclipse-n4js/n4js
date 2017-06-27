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
package org.eclipse.n4js.jsdoc2spec;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;

/**
 * Accepts issues that arise during reading the N4JS code base. Beside saving the issues, also the corresponding source
 * locations are stored.
 */
public class JSDoc2SpecAcceptor implements IJSDoc2SpecIssueAcceptor {

	static class Issue {
		final String message;
		final String pos;

		Issue(String message, String pos) {
			this.message = message;
			this.pos = pos;
		}

		@Override
		public String toString() {
			if (pos == null) {
				return message;
			}
			return message + " at " + pos;
		}
	}

	List<Issue> warnings = new ArrayList<>();

	@Override
	public void reset() {
		warnings.clear();
	}

	@Override
	public void addWarning(String message, EObject astElement) {
		warnings.add(new Issue(message, toPos(astElement)));
	}

	@Override
	public String warnings() {
		return warnings.stream().map(issue -> issue.toString()).collect(Collectors.joining("\n"));
	}

	private String toPos(EObject eobj) {
		if (eobj == null)
			return "";
		StringBuilder strb = new StringBuilder();
		String res = null;
		if (eobj.eResource() != null) {
			res = eobj.eResource().getURI().toString();
			if (res.startsWith("platform:/resource/")) {
				res = res.substring("platform:/resource/".length());
			}
		}
		if (res != null)
			strb.append(res);
		EObject astNode = eobj instanceof SyntaxRelatedTElement ? ((SyntaxRelatedTElement) eobj).getAstElement() : eobj;
		ICompositeNode node = NodeModelUtils.findActualNodeFor(astNode);
		if (node != null) {
			strb.append(":").append(node.getStartLine());
		}
		return strb.toString();
	}

}
