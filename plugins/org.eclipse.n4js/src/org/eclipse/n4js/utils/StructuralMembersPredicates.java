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
package org.eclipse.n4js.utils;

import static org.eclipse.n4js.ts.types.MemberAccessModifier.PUBLIC_INTERNAL;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4ObjectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.eclipse.n4js.utils.AndFunction1.conjunctionOf;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.Objects;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * Utility class for filtering out structural members for different structural typing strategies.
 */
@SuppressWarnings("unchecked")
abstract public class StructuralMembersPredicates {

	/**
	 * Accepts all members but the {@code constructor}s.
	 */
	public static AndFunction1<TMember> MEMBERS_PREDICATE = conjunctionOf(
			(TMember tm) -> !tm.isConstructor());

	/**
	 * Predicate for accepting all field structural type members. Such as data {@link TField field}s and field
	 * {@link FieldAccessor accessor}s.
	 */
	public static AndFunction1<TMember> FIELDS_PREDICATE = conjunctionOf(
			(TMember tm) -> tm instanceof TField || tm instanceof FieldAccessor);

	/**
	 * Predicate for accepting readable fields such as {@link TMember#isReadable() readable} {@link TField data field}s
	 * and {@link TGetter getter}s.
	 */
	public static AndFunction1<TMember> READABLE_FIELDS_PREDICATE = conjunctionOf(
			(TMember tm) -> (tm instanceof TField && tm.isReadable()) || tm instanceof TGetter);

	/**
	 * Predicate for writable fields. Accepts {@link TMember#isWriteable() writable} data {@link TField field}s and
	 * {@link TSetter setter}s. Also accepts {@link TField#isFinal() @Final} fields without any
	 * {@link TField#isHasExpression() initializer expression}.
	 */
	public static AndFunction1<TMember> WRITABLE_FIELDS_PREDICATE = conjunctionOf(
			(TMember tm) -> {
				if (tm instanceof TField) {
					TField tf = (TField) tm;
					return tf.isWriteable() || (tf.isFinal() && !tf.isHasExpression());
				}
				return tm instanceof TSetter;
			});

	/**
	 * Predicate for write-only fields. Accepts only {@link TGetter getter}s.
	 */
	public static AndFunction1<TMember> GETTERS_PREDICATE = conjunctionOf(
			(TMember tm) -> tm instanceof TGetter);

	/**
	 * Accepts only writable {@link FieldAccessor field accessor}s; {@link TSetter setter}s.
	 */
	public static AndFunction1<TMember> SETTERS_PREDICATE = conjunctionOf(
			(TMember tm) -> tm instanceof TSetter);

	/**
	 * Creates and returns with a new predicate instance that can be used to filter out all members of a type that
	 * cannot be a member of any structural types. For instance each {@link TMember#isStatic() static} and/or non-public
	 * members will be filtered out.
	 */
	public static AndFunction1<TMember> createBaseStructuralMembersPredicate(RuleEnvironment it) {
		return conjunctionOf(new BaseStructuralMembersPredicate(it)); // Function1<TMember, Boolean>
	}

	/**
	 * Base predicate {@link Function1 function} for filtering out {@link TMember member}s which for sure cannot be
	 * member of a structural part. Such as {@link TMember#isStatic() static} members or non-public ones.
	 */
	private static class BaseStructuralMembersPredicate implements Function1<TMember, Boolean> {

		private final Type n4ObjectType;
		private final TMember object__proto__;

		private BaseStructuralMembersPredicate(RuleEnvironment G) {
			this.n4ObjectType = Objects.requireNonNull(n4ObjectType(G));
			this.object__proto__ = Objects.requireNonNull(
					findFirst(objectType(G).getOwnedMembers(),
							m -> N4JSLanguageConstants.PROPERTY__PROTO__NAME.equals(m.getName())));
		}

		@Override
		public Boolean apply(TMember it) {

			if (!hasStructuralTypeContainer(it)) {
				if (!isPublicVisible(it)) {
					return false;
				}

				if (hasN4ObjectTypeContainer(it)) {
					return false;
				}

				if (it.isStatic()) {
					return false;
				}

				if (it == object__proto__) {
					return false;
				}
			}

			return true;
		}

		private boolean hasStructuralTypeContainer(TMember it) {
			return it.getContainingType() instanceof TStructuralType;
		}

		private boolean hasN4ObjectTypeContainer(TMember it) {
			return it.getContainingType() == n4ObjectType; // cf Constraint 62.4
		}

		private boolean isPublicVisible(TMember it) {
			// Visibility is public internal or greater: public.
			return 0 >= PUBLIC_INTERNAL.compareTo(it.getMemberAccessModifier());
		}
	}
}
