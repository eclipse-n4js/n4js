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
package org.eclipse.n4js.typesystem;

import com.google.common.base.Predicate;

import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * Filter out results not available for a given typing strategy.
 */
public class TypingStrategyFilter implements Predicate<TMember> {

	final TypingStrategy typingStrategy;

	/**
	 * Creates filter for the given strategy.
	 */
	public TypingStrategyFilter(TypingStrategy typingStrategy) {
		this.typingStrategy = typingStrategy;
	}

	/**
	 * Returns the typing strategy of this filter, set in the constructor.
	 */
	public TypingStrategy getTypingStrategy() {
		return typingStrategy;
	}

	@Override
	public boolean apply(TMember member) {
		if (typingStrategy == TypingStrategy.DEFAULT) {
			return true;
		}

		if (member.isStatic() || member.getMemberAccessModifier() != MemberAccessModifier.PUBLIC) {
			return false;
		}
		if (member instanceof TMethod) {
			return typingStrategy == TypingStrategy.STRUCTURAL;
		}
		return true;
	}

}
