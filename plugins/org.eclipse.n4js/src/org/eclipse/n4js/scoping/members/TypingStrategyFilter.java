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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.NameAndAccess;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;

/**
 * Filter used in {@link TypingStrategyAwareMemberScope} to filter out results not available for a given typing
 * strategy.
 */
class TypingStrategyFilter implements Predicate<IEObjectDescription> {

	final TypingStrategy typingStrategy;
	final boolean isWriteAccess;

	TypingStrategyFilter(TypingStrategy typingStrategy, boolean isWriteAccess) {
		this.typingStrategy = typingStrategy;
		this.isWriteAccess = isWriteAccess;
	}

	/**
	 * Returns the typing strategy of this filter, set in the constructor.
	 */
	public TypingStrategy getTypingStrategy() {
		return typingStrategy;
	}

	@Override
	public boolean apply(IEObjectDescription description) {
		if (typingStrategy == TypingStrategy.DEFAULT || typingStrategy == TypingStrategy.NOMINAL) {
			return true;
		}
		EObject proxyOrInstance = description.getEObjectOrProxy();
		if (proxyOrInstance == null || proxyOrInstance.eIsProxy()) {
			return true;
		}
		if (!(proxyOrInstance instanceof TMember)) {
			return true;
		}
		TMember member = (TMember) proxyOrInstance;
		if (member.isStatic() || member.getMemberAccessModifier() != MemberAccessModifier.PUBLIC) {
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
				boolean isAccessable = !isWriteAccess && (!field.isFinal() || !field.isHasExpression());
				return isAccessable;
			}
		}

		return true;
	}

}
