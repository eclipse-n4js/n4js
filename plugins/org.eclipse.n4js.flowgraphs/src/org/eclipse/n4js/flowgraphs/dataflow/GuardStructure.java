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
package org.eclipse.n4js.flowgraphs.dataflow;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * A {@link GuardStructure} is a complete condition of an {@link IfStatement}, {@link ConditionalExpression} or the left
 * hand side of a {@link BinaryLogicalExpression}. The top expression of a {@link GuardStructure}s can contain nested
 * conditions and therefore can contain {@link Guard}s. Moreover, for the else branch, this top expression can be
 * negated.
 */
public class GuardStructure {
	final private GuardFactory guardFactory;

	/** Top expression */
	final Expression condition;
	/** true iff the {@link GuardStructure} is used for reasoning about the else branch */
	final boolean negate;
	/**
	 * All guards within this {@link GuardStructure}. Mapping from guarded {@link Symbol} to the symbols {@link Guard}
	 */
	final Multimap<Symbol, Guard> guards;

	/** Constructor. */
	GuardStructure(GuardFactory guardFactory, Expression condition, boolean negate) {
		this.guardFactory = guardFactory;
		this.condition = condition;
		this.negate = negate;
		guards = getGuards();
	}

	private Multimap<Symbol, Guard> getGuards() {
		Multimap<Symbol, Guard> guardsMap = HashMultimap.create();
		List<Expression> allExpressions = EcoreUtil2.getAllContentsOfType(condition, Expression.class);
		allExpressions.add(condition);
		EObject conditionContainer = condition.eContainer();

		for (Expression expr : allExpressions) {
			Guard guard = guardFactory.create(conditionContainer, expr, negate);
			if (guard != null) {
				guardsMap.put(guard.symbol, guard);
			}
		}

		return guardsMap;
	}
}
