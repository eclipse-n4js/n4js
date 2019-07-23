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
package org.eclipse.n4js.validation.validators.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.util.ExtendedClassesIterable;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.common.collect.Iterables;

/**
 * A util class for member redefinition validation.
 */
public class MemberRedefinitionUtils {

	/**
	 * Filters the given overridden members to only contain members which are metatype compatible with the overriding
	 * member.
	 */
	public static Iterable<TMember> getMetatypeCompatibleOverriddenMembers(TMember overridingMember,
			Iterable<TMember> overriddenMembers) {
		return Iterables.filter(overriddenMembers, member -> isMetaTypeCompatible(overridingMember, member));
	}

	/**
	 * Returns {@code true} if the two members are metatype compatible.
	 *
	 * @param m1
	 *            The first member
	 * @param m2
	 *            The second member
	 */
	public static boolean isMetaTypeCompatible(TMember m1, TMember m2) {
		boolean result = m1.getMemberType() == m2.getMemberType();

		if (!result) {
			switch (m1.getMemberType()) {
			case METHOD:
				break; // initial test covers this case
			case FIELD:
				result = m2.getMemberType() == MemberType.GETTER || m2.getMemberType() == MemberType.SETTER;
				break;
			case SETTER:
			case GETTER:
				result = m2.getMemberType() == MemberType.FIELD;
				break;
			}
		}
		return result;
	}

	/**
	 * Returns the fitting verb for a redefinition of all of the redefined members by the current classifier.
	 *
	 * @param redefinedMembers
	 *            The redefined members
	 * @param redefiningClassifier
	 *            The classifier which redefines the given members
	 */
	public static String getRedefinitionVerb(Iterable<TMember> redefinedMembers, TClassifier redefiningClassifier) {
		Set<EClass> overriddenClassifierTypes = StreamSupport.stream(redefinedMembers.spliterator(), false)
				.map(member -> member.getContainingType().eClass())
				.collect(Collectors.toSet());

		boolean isOverridingSuperclassMember = overriddenClassifierTypes.contains(TypesPackage.eINSTANCE.getTClass());
		boolean isOverridingObjectPrototypeMembers = overriddenClassifierTypes
				.contains(TypesPackage.eINSTANCE.getTObjectPrototype());

		boolean isImplementing = overriddenClassifierTypes.contains(TypesPackage.eINSTANCE.getTInterface());
		boolean isOverriding = isOverridingSuperclassMember || isOverridingObjectPrototypeMembers;
		boolean isInterfaceExtendingInterface = redefiningClassifier instanceof TInterface;

		if (isOverriding && isImplementing) {
			return "overriding/implementing";
		} else if (isOverriding || isInterfaceExtendingInterface) {
			return "overriding";
		} else if (isImplementing) {
			return "implementing";
		} else {
			// General case, should not happen
			return "redefining";
		}
	}

	/**
	 * Returns the class which is filled by the given static polyfill class definition.
	 *
	 * Returns {@code null} if staticPolyfillDefinition isn't a valid static polyfill class definition.
	 */
	public static TClass getFilledClass(N4ClassDefinition staticPolyfillDefinition) {
		if (!(staticPolyfillDefinition.getDefinedType() instanceof TClassifier)) {
			return null;
		}

		TClassifier classifier = (TClassifier) staticPolyfillDefinition.getDefinedType();
		if (!classifier.isStaticPolyfill()) {
			return null;
		}

		ParameterizedTypeRef superClassTypeRef = Iterables
				.getFirst(classifier.getSuperClassifierRefs(), null);
		if (null == superClassTypeRef) {
			return null;
		}

		Type superClassType = superClassTypeRef.getDeclaredType();
		if (superClassType instanceof TClass
				&& ((TClass) superClassType).getContainingModule().isStaticPolyfillAware()) {
			return (TClass) superClassType;
		} else {
			return null;
		}
	}

	/**
	 * Returns the context type to be used as this binding (see
	 * {@link N4JSTypeSystem#createRuleEnvironmentForContext(TypeRef, TypeRef, Resource) here} and
	 * {@link RuleEnvironmentExtensions#addThisType(RuleEnvironment, TypeRef)} here) for checking constructor
	 * compatibility.
	 *
	 * For example, given the following code
	 *
	 * <pre>
	 * class C1 {
	 *     constructor(@Spec spec: ~i~this) {}
	 * }
	 *
	 * class C2 extends C1 {
	 *     public field2;
	 * }
	 *
	 * &#64;CovariantConstructor
	 * class C3 extends C2 {
	 *     public field3;
	 * }
	 *
	 * class C4 extends C3 {
	 *     // XPECT noerrors -->
	 *     constructor(@Spec spec: ~i~this) { super(spec); }
	 * }
	 * </pre>
	 *
	 * When checking constructor compatibility in C4, we have to use class C3 as the 'this' context on right-hand side,
	 * not class C1, because the addition of fields <code>field2</code> and <code>field3</code> does not break the
	 * contract of the &#64;CovariantConstructor annotation. Therefore, we cannot simply use the containing type of the
	 * right-hand side constructor as 'this' context. In the above example, we would call this method with C4 as
	 * <code>baseContext</code> and C1's constructor as <code>ctor</code> and it would return C3 as the 'this' context
	 * to use.
	 */
	public static final Type findThisContextForConstructor(Type baseContext, TMethod ctor) {
		final Type ctorContainer = ctor.getContainingType();
		if (!(baseContext instanceof TClass) || !(ctorContainer instanceof TClass)) {
			return ctorContainer;
		}
		final TClass ctorContainerCasted = (TClass) ctorContainer;
		final TClass baseContextCasted = (TClass) baseContext;
		if (N4JSLanguageUtils.hasCovariantConstructor(ctorContainerCasted)) {
			return ctorContainerCasted;
		}
		final List<TClass> superClassesUpToCtorContainer = new ArrayList<>();
		for (TClass superClass : new ExtendedClassesIterable(baseContextCasted)) {
			if (superClass != ctorContainerCasted) {
				superClassesUpToCtorContainer.add(superClass);
			} else {
				break;
			}
		}
		for (int i = superClassesUpToCtorContainer.size() - 1; i >= 0; i--) {
			final TClass superClass = superClassesUpToCtorContainer.get(i);
			if (N4JSLanguageUtils.hasCovariantConstructor(superClass)) {
				return superClass;
			}
		}
		return baseContext;
	}

	/* Non-instantiable util class. */
	private MemberRedefinitionUtils() {
	}
}
