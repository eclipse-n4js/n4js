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

import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypingStrategy;

import com.google.common.base.Predicate;

/**
 * Filter out results not available for a given typing strategy, i.e. {@code apply} returns false if member is not
 * available.
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
		if (typingStrategy == TypingStrategy.DEFAULT || typingStrategy == TypingStrategy.NOMINAL) {
			return true;
		}

		if (member.isStatic() || member.getMemberAccessModifier() != MemberAccessModifier.PUBLIC) {
			return false;
		}

		if (typingStrategy == TypingStrategy.STRUCTURAL) {
			return true;
		}

		if (member instanceof TMethod) {
			return false;
		}

		// Getter, Setter, DataField
		if (typingStrategy == TypingStrategy.STRUCTURAL_FIELDS) {
			return true;
		}

		if (member instanceof TSetter) {
			return typingStrategy == TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS
					|| typingStrategy == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		}
		if (member instanceof TGetter) {
			return typingStrategy == TypingStrategy.STRUCTURAL_READ_ONLY_FIELDS;
		}

		return true;
	}

}
