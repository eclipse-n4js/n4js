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
package org.eclipse.n4js.ts.types.util;

import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;

/**
 * Utility class providing relations for access modifiers.
 * <p>
 * This class deals exclusively with modifiers in the Types model, not with modifiers in the AST as represented by
 * org.eclipse.n4js.n4JS.N4Modifier.
 */
public class AccessModifiers {

	/**
	 * Convenience method, translates the type's type access modifier to a member access modifier via
	 * {@link #toMemberModifier(TypeAccessModifier)}.
	 */
	public static MemberAccessModifier toMemberModifier(Type type) {
		return toMemberModifier(type == null ? TypeAccessModifier.UNDEFINED : type.getTypeAccessModifier());
	}

	/**
	 * Translates given type access modifier to corresponding member access modifier. Also see N4JSSpec: Definition 6
	 * (Type and Member Accessibility Relation).
	 */
	public static MemberAccessModifier toMemberModifier(TypeAccessModifier modifier) {
		switch (modifier) {
		case PRIVATE:
			return MemberAccessModifier.PRIVATE;
		case PROJECT:
			return MemberAccessModifier.PROJECT;
		case PUBLIC_INTERNAL:
			return MemberAccessModifier.PUBLIC_INTERNAL;
		case PUBLIC:
			return MemberAccessModifier.PUBLIC;
		default:
			return MemberAccessModifier.PROJECT;
		}
	}

	/**
	 * Convenience method, returns true if left member access modifier is less then corresponding member access modifier
	 * of the given type access modifier, converted via {@link #toMemberModifier(TypeAccessModifier)}
	 */
	public static boolean less(MemberAccessModifier memberAccessModifier, TypeAccessModifier typeAccessModifier) {
		return less(memberAccessModifier, toMemberModifier(typeAccessModifier));
	}

	/**
	 * Convenience method, returns true if left member access modifier is less then right one.
	 */
	public static boolean less(TMember left, TMember right) {
		return less(left.getMemberAccessModifier(), right.getMemberAccessModifier());
	}

	/**
	 * Returns true if left modifier is less than right modifier. The modifiers must not be
	 * {@link MemberAccessModifier#UNDEFINED}.
	 *
	 * Note that {@link MemberAccessModifier} do not define a totatlly ordered set. That is,
	 * <code>less({@link MemberAccessModifier#PROTECTED}, {@link MemberAccessModifier#PUBLIC_INTERNAL}</code> and
	 * <code>less({@link MemberAccessModifier#PUBLIC_INTERNAL}, {@link MemberAccessModifier#PROTECTED}</code> both
	 * return false.
	 *
	 */
	public static boolean less(MemberAccessModifier left, MemberAccessModifier right) {
		int iLeft = left.getValue();
		if (iLeft == 0) {
			throw new IllegalArgumentException("Cannot compare with undefined on left");
		}
		int iRight = right.getValue();
		if (iRight == 0) {
			throw new IllegalArgumentException("Cannot compare with undefined on right");
		}
		if (iLeft < iRight) {
			if (left == MemberAccessModifier.PROTECTED) {
				return right != MemberAccessModifier.PUBLIC_INTERNAL;
			}
			if (right == MemberAccessModifier.PROTECTED) {
				return left != MemberAccessModifier.PUBLIC_INTERNAL;
			}
			return true;
		}
		return false;
	}

	/**
	 * Similar to {@link #less(TMember, TMember)}, but the access modifiers of both members are "checked" (and
	 * {@link #fixed(TMember)} if necessary) before comparison. This is done to avoid strange error messages (or
	 * strangely omitted error messages) due to wrong accessors.
	 */
	public static boolean checkedLess(TMember left, TMember right) {
		MemberAccessModifier fixedLeft = fixed(left);
		MemberAccessModifier fixedRight = fixed(right);
		return less(fixedLeft, fixedRight);
	}

	/**
	 * Returns either the declared access modifier, or a correct fixed version. E.g., members of interfaces must never
	 * be declared private, thus such a modifier is corrected to project. Note that
	 * {@link TMemberWithAccessModifier#getMemberAccessModifier()} may calculate the modifier if it is undefined, but
	 * does not "fix" a wrongly declared modifier.
	 */
	public static MemberAccessModifier fixed(TMember m) {
		MemberAccessModifier modifier = m.getMemberAccessModifier();
		Type containingType = m.getContainingType();
		if (containingType instanceof TInterface) {
			// correspongingMAM will be one of {PRIVATE PROJECT PUBLIC_INTERNAL PUBLIC} - with PROJECT as default.
			MemberAccessModifier correspondingTypesMAM = toMemberModifier(containingType);
			if (correspondingTypesMAM == MemberAccessModifier.PRIVATE && containingType instanceof TInterface) {
				correspondingTypesMAM = MemberAccessModifier.PROJECT;
			}
		}
		return modifier;
	}
}
