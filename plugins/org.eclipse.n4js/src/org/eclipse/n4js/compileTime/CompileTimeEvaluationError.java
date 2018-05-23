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
package org.eclipse.n4js.compileTime;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import org.eclipse.n4js.compileTime.CompileTimeValue.ValueInvalid;

/**
 * An error that occurred during evaluation of compile-time expressions. Always wrapped in a {@link ValueInvalid}.
 */
public class CompileTimeEvaluationError {
	/** Message for this error; never <code>null</code>. */
	public final String message;
	/** Optional AST node where this error occurred; may be <code>null</code>. */
	public final EObject astNode;
	/** Optional {@link EStructuralFeature feature} where this error occurred; may be <code>null</code>. */
	public final EStructuralFeature feature;

	/**
	 * @param message
	 *            message for this error; must not be <code>null</code>.
	 * @param astNode
	 *            optional AST node where this error occurred; may be <code>null</code>.
	 * @param feature
	 *            optional {@link EStructuralFeature feature} where this error occurred; may be <code>null</code>.
	 */
	public CompileTimeEvaluationError(String message, EObject astNode, EStructuralFeature feature) {
		Objects.requireNonNull(message);
		this.message = message;
		this.astNode = astNode;
		this.feature = feature;
	}

	/**
	 * Returns this error's message with a suffix explaining where the error occurred if {@link #astNode} is given. This
	 * method ignores field {@link #feature}.
	 */
	public String getMessageWithLocation() {
		if (astNode == null) {
			return message;
		}
		final INode node = NodeModelUtils.findActualNodeFor(astNode);
		final String tokenText = node != null ? NodeModelUtils.getTokenText(node) : null;
		if (tokenText == null) {
			return message;
		}
		return message + " at \"" + tokenText + "\"";
	}
}
