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
package org.eclipse.n4js.utils

import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TStructuralType
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import java.util.Objects
import org.eclipse.xtext.xbase.lib.Functions.Function1

import static org.eclipse.n4js.ts.types.MemberAccessModifier.*
import static org.eclipse.n4js.utils.AndFunction1.conjunctionOf

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Utility class for filtering out structural members for different structural typing strategies.
 */
abstract class StructuralMembersPredicates {

	/**
	 * Accepts all members but the {@code constructor}s.
	 */
	public static val MEMBERS_PREDICATE = conjunctionOf(
		TMember it | !isConstructor
	);

	/**
	 * Predicate for accepting all field structural type members. Such as data {@link TField field}s and field {@link FieldAccessor accessor}s.
	 */
	public static val FIELDS_PREDICATE = conjunctionOf(
		TMember it | it instanceof TField || it instanceof FieldAccessor
	);

	/**
	 * Predicate for accepting readable fields such as {@link TMember#isReadable() readable} {@link TField data field}s
	 * and {@link TGetter getter}s.
	 */
	public static val READABLE_FIELDS_PREDICATE = conjunctionOf(
		TMember it | (it instanceof TField && readable) || it instanceof TGetter
	);

	/**
	 * Predicate for writable fields. Accepts {@link TMember#isWriteable() writable} data {@link TField field}s and
 	 * {@link TSetter setter}s. Also accepts {@link TField#isFinal() @Final} fields without any {@link TField#isHasExpression()
 	 * initializer expression}.
	 */
	public static val WRITABLE_FIELDS_PREDICATE = conjunctionOf(
		TMember it | if (it instanceof TField) { writeable || (final && !hasExpression) } else it instanceof TSetter
	);

	/**
	 * Predicate for write-only fields. Accepts only {@link TGetter getter}s.
	 */
	public static val GETTERS_PREDICATE = conjunctionOf(
		TMember it | it instanceof TGetter
	);

	/**
	 * Accepts only writable {@link FieldAccessor field accessor}s; {@link TSetter setter}s.
	 */
	public static val SETTERS_PREDICATE = conjunctionOf(
		TMember it | it instanceof TSetter
	);

	/**
	 * Creates and returns with a new predicate instance that can be used to filter out all members of a type that
	 * cannot be a member of any structural types. For instance each {@link TMember#isStatic() static} and/or non-public
	 * members will be filtered out.
	 */
	static def createBaseStructuralMembersPredicate(RuleEnvironment it) {
		return conjunctionOf(new BaseStructuralMembersPredicate(it) as Function1<TMember, Boolean>)
	}

	/**
	 * Base predicate {@link Function1 function} for filtering out {@link TMember member}s which for sure cannot
	 * be member of a structural part. Such as {@link TMember#isStatic() static} members or non-public ones.
	 */
	private static class BaseStructuralMembersPredicate implements Function1<TMember, Boolean> {

		private final RuleEnvironment G;
		private final Type n4ObjectType;
		private final TMember object__proto__;

		private new(RuleEnvironment G) {
			this.G = G;
			this.n4ObjectType = Objects.requireNonNull(G.n4ObjectType);
			this.object__proto__ = Objects.requireNonNull(G.objectType.ownedMembers.findFirst[
				name==N4JSLanguageConstants.PROPERTY__PROTO__NAME
			]);
		}

		override apply(TMember it) {

			if (!hasStructuralTypeContainer) {
				if (!publicVisible) {
					return false;
				}

				if (hasN4ObjectTypeContainer) {
					return false;
				}

				if (static) {
					return false;
				}

				if (it === object__proto__) {
					return false;
				}
			}

			return true;
		}

		def private hasStructuralTypeContainer(TMember it) {
			return containingType instanceof TStructuralType;
		}

		def private hasN4ObjectTypeContainer(TMember it) {
			return containingType === n4ObjectType; // cf Constraint 62.4
		}

		def private isPublicVisible(TMember it) {
			// Visibility is public internal or greater: public.
			0 >= PUBLIC_INTERNAL.compareTo(memberAccessModifier)
		}
	}
}
