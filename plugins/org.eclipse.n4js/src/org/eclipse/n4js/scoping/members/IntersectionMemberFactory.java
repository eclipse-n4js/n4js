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
public class IntersectionMemberFactory extends ComposedMemberFactory {

	IntersectionMemberFactory(ComposedMemberInfo cma) {
		super(cma);
	}

	@Override
	protected MemberFactory getMemberFactory() {
		switch (memberType) {
		case METHOD:
			return new MethodFactory.IntersectionMethodFactory(cmi);
		case FIELD:
			return new FieldFactory.IntersectionFieldFactory(cmi);
		case GETTER:
			return new GetterFactory.IntersectionGetterFactory(cmi);
		case SETTER:
			return new SetterFactory.IntersectionSetterFactory(cmi);
		}
		return null;
	}

	/**
	 * Retrieve the member type, i.e. method, field, setter or getter of the composed member of the intersection.
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
		if (cmi.onlyGetterMemberTypes() && !cmi.isWriteAccess()) {
			return GETTER;
		}
		if (cmi.onlySetterMemberTypes() && cmi.isWriteAccess()) {
			return SETTER;
		}
		if (allTypeRefAreEqual()) {
			return FIELD;
		}
		// mix of all non-method memberTypes AND different return types
		if (cmi.isWriteAccess()) {
			if (!cmi.hasGetterMemberType()) {
				// return MemberType.FIELD;
			}
			return SETTER;
		}
		if (!cmi.isWriteAccess()) {
			if (!cmi.hasSetterMemberType()) {
				// return MemberType.FIELD;
			}
			return GETTER;
		}
		return null; // inValid
	}

	@Override
	public boolean isValid() {
		if (specialMemberFactory == null)
			return false;
		return specialMemberFactory.isValid();
	}

}
