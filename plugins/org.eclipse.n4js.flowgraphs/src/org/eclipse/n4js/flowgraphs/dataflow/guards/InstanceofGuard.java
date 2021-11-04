/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.dataflow.guards;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.utils.Strings;

import com.google.common.collect.ImmutableList;

/**
 * Subclass of {@link Guard} that hold context information, such as {@link #typeIdentifiers}.
 */
public class InstanceofGuard extends Guard {
	/** {@link IdentifierRef} to the disjunctive set of types of the type guard. */
	final public List<Expression> typeIdentifiers;

	/** Constructor */
	public InstanceofGuard(Expression condition, GuardAssertion asserts, ControlFlowElement symbolCFE,
			Expression... typeIdentifiers) {

		this(condition, asserts, symbolCFE, Arrays.asList(typeIdentifiers));
	}

	/** Constructor */
	public InstanceofGuard(Expression condition, GuardAssertion asserts, ControlFlowElement symbolCFE,
			Collection<Expression> typeIdentifiers) {

		super(condition, GuardType.InstanceOf, asserts, symbolCFE);
		this.typeIdentifiers = ImmutableList.copyOf(typeIdentifiers);
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += " <: " + Strings.join("|", FGUtils::getSourceText, typeIdentifiers);
		return str;
	}
}
