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
package org.eclipse.n4js.scoping.members;

import static org.eclipse.n4js.ts.types.MemberType.FIELD;
import static org.eclipse.n4js.ts.types.MemberType.GETTER;
import static org.eclipse.n4js.ts.types.MemberType.METHOD;
import static org.eclipse.n4js.ts.types.MemberType.SETTER;

import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TMember;

/**
 * Implements the method {@link #getNewMemberType()} which selects the correct {@link MemberType} for a new composed
 * {@link TMember}. Refer to N4JS-Spec for an overview table.
 * <p>
 * Implements the method {@link #getMemberFactory()} which returns a new {@link MemberFactory} for a given
 * {@link MemberType}.
 */
public class UnionMemberFactory extends ComposedMemberFactory {

	UnionMemberFactory(ComposedMemberInfo cma) {
		super(cma);
	}

	@Override
	protected MemberFactory getMemberFactory() {
		switch (memberType) {
		case METHOD:
			return new MethodFactory.UnionMethodFactory(cmi);
		case FIELD:
			return new FieldFactory.UnionFieldFactory(cmi);
		case GETTER:
			return new GetterFactory.UnionGetterFactory(cmi);
		case SETTER:
			return new SetterFactory.UnionSetterFactory(cmi);
		}
		return null;
	}

	/**
	 * Retrieve the member type, i.e. method, field, setter or getter, of the composed member of the union.
	 * <p>
	 * See Chapter 4.10. Union and Intersection Type (Composed Types) for merge rules.
	 */
	@Override
	protected MemberType getNewMemberType() {
		// mix of all memberTypes
		if (cmi.hasMethodMemberType() && cmi.hasNonMethodMemberType()) {
			return null; // inValid
		}
		if (cmi.onlyMethodMemberTypes()) {
			return METHOD;
		}
		// mix of all non-method memberTypes
		if (cmi.onlyGetterMemberTypes()) {
			return GETTER;
		}
		if (cmi.onlySetterMemberTypes()) {
			return SETTER;
		}
		if (cmi.onlyFieldMemberTypes()) {
			if (allTypeRefAreEqual()) {
				return FIELD;
			} else {
				if (cmi.isWriteAccess()) {
					return SETTER;
				} else {
					return GETTER;
				}
			}
		}
		// mix of all non-method memberTypes
		if (!cmi.hasGetterMemberType()) {
			return SETTER;
		}
		if (!cmi.hasSetterMemberType()) {
			return GETTER;
		}
		return null; // inValid
	}

	@Override
	public boolean isEmpty() {
		return cmi.isEmpty();
	}

	@Override
	public boolean isValid() {
		if (cmi.isSiblingMissing())
			return false;
		if (specialMemberFactory == null)
			return false;
		return specialMemberFactory.isValid();
	}

}
