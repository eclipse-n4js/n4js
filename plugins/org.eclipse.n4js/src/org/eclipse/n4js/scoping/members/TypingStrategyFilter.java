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
package org.eclipse.n4js.scoping.members;

import java.util.Map;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.NameAndAccess;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypingStrategy;

import com.google.common.base.Predicate;

/**
 * Filter used in {@link TypingStrategyAwareMemberScope} to filter out results not available for a given typing
 * strategy.
 */
public class TypingStrategyFilter implements Predicate<TMember> {

	private final TypingStrategy typingStrategy;
	private final boolean isWriteAccess;
	private final boolean inSpecCtor;
	private final boolean ignoreAccessModifier;

	/**
	 * Creates new instance intended for read access and without <code>@Spec</code>-constructor semantics.
	 */
	public TypingStrategyFilter(TypingStrategy typingStrategy) {
		this(typingStrategy, false, false);
	}

	/**
	 * Creates new instance.
	 *
	 * @param inSpecCtor
	 *            For <code>@Spec</code>-constructors (as opposed to ordinary constructors), <code>@Final</code> fields
	 *            are writable even if they have an initializer expression. This semantics can be activated by setting
	 *            this flag to <code>true</code>.
	 */
	public TypingStrategyFilter(TypingStrategy typingStrategy, boolean isWriteAccess, boolean inSpecCtor) {
		this(typingStrategy, isWriteAccess, inSpecCtor, false);
	}

	/**
	 * Creates new instance.
	 *
	 * @param ignoreAccessModifier
	 *            if set to true, the filter will include non-public members.
	 */
	public TypingStrategyFilter(TypingStrategy typingStrategy, boolean isWriteAccess, boolean inSpecCtor,
			boolean ignoreAccessModifier) {

		this.typingStrategy = typingStrategy;
		this.isWriteAccess = isWriteAccess;
		this.inSpecCtor = inSpecCtor;
		this.ignoreAccessModifier = ignoreAccessModifier;
	}

	/**
	 * Returns the typing strategy of this filter, set in the constructor.
	 */
	public TypingStrategy getTypingStrategy() {
		return typingStrategy;
	}

	@Override
	public boolean apply(TMember member) {
		if (member.isStatic()) {
			return false;
		}
		if (!ignoreAccessModifier && member.getMemberAccessModifier() != MemberAccessModifier.PUBLIC) {
			return false;
		}

		if (member instanceof TMethod) {
			switch (typingStrategy) {
			case NOMINAL:
			case DEFAULT:
				return true;
			case STRUCTURAL_FIELDS:
			case STRUCTURAL_READ_ONLY_FIELDS:
			case STRUCTURAL_WRITE_ONLY_FIELDS:
			case STRUCTURAL_FIELD_INITIALIZER:
				return false;
			case STRUCTURAL:
				return true; // including constructors
			case EMPTY:
				return false;
			}
		}

		if (member instanceof TGetter) {
			switch (typingStrategy) {
			case NOMINAL:
			case DEFAULT:
			case STRUCTURAL:
			case STRUCTURAL_FIELDS:
			case STRUCTURAL_READ_ONLY_FIELDS:
				return true;
			case STRUCTURAL_WRITE_ONLY_FIELDS:
				return false;
			case STRUCTURAL_FIELD_INITIALIZER:
				ContainerType<?> type = member.getContainingType();
				NameAndAccess naa = new NameAndAccess(member.getName(), true, false);
				Map<NameAndAccess, ? extends TMember> members = type.getOwnedMembersByNameAndAccess();
				boolean hasSetter = members.containsKey(naa);
				return hasSetter;
			case EMPTY:
				return false;
			}
		}

		if (member instanceof TSetter) {
			switch (typingStrategy) {
			case NOMINAL:
			case DEFAULT:
			case STRUCTURAL:
			case STRUCTURAL_FIELDS:
			case STRUCTURAL_WRITE_ONLY_FIELDS:
				return true;
			case STRUCTURAL_READ_ONLY_FIELDS:
				return false;
			case STRUCTURAL_FIELD_INITIALIZER:
				// ~i~ turns a setter into a getter, i.e. we keep the member in case of read access and filter it out
				// otherwise
				return !isWriteAccess;
			case EMPTY:
				return false;
			}
		}

		if (member instanceof TField) {
			TField field = (TField) member;
			switch (typingStrategy) {
			case NOMINAL:
			case DEFAULT:
			case STRUCTURAL:
			case STRUCTURAL_FIELDS:
				return true;
			case STRUCTURAL_READ_ONLY_FIELDS:
				return !isWriteAccess;
			case STRUCTURAL_WRITE_ONLY_FIELDS:
				return isWriteAccess;
			case STRUCTURAL_FIELD_INITIALIZER:
				boolean isAccessable = !isWriteAccess && (!field.isFinal() || !field.isHasExpression() || inSpecCtor);
				return isAccessable;
			case EMPTY:
				return false;
			}
		}

		return true;
	}

}
