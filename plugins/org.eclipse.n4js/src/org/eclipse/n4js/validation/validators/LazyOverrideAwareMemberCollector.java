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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.AbstractCompleteHierarchyTraverser;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.DeclMergingHelper;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Collects all members, including inherited members, of a type and omits some members overridden in the type hierarchy.
 * That is, members overridden in the type hierarchy are removed and replaced by the overridden members. However, this
 * is done lazily, that is not all cases are considered, as the result is further validated anyway. That is,
 * <ul>
 * <li>data fields and corresponding accessors are not recognized to public each other
 * <li>methods (and fields) inherited from interfaces are all added and not filtered.
 * <li>private members are added to result (which is required for validation: cannot public private member etc.)
 * </ul>
 * Thus, this collector should only be used for validation purposes.
 */

public class LazyOverrideAwareMemberCollector {
	@Inject
	private DeclMergingHelper declMergingHelper;

	/**
	 * Collects all members, including owned members and members defined in implicit super types.
	 */
	public List<TMember> collectAllMembers(ContainerType<?> type) {
		return new LazyOverrideAwareMemberCollectorX(type, declMergingHelper, true, false).getResult();
	}

	/**
	 * Collects all inherited members, including members defined in implicit super types; owned members are omitted.
	 */
	public List<TMember> collectAllInheritedMembers(ContainerType<?> type) {
		return new LazyOverrideAwareMemberCollectorX(type, declMergingHelper, true, true).getResult();
	}

	/**
	 * Collects all declared members, including owned members; members defined in implicit super types are omitted.
	 */
	public List<TMember> collectAllDeclaredMembers(ContainerType<?> type) {
		return new LazyOverrideAwareMemberCollectorX(type, declMergingHelper, false, false).getResult();
	}

	/**
	 * Collects all declared inherited members; owned members and members defined in implicit super types are omitted.
	 */
	public List<TMember> collectAllDeclaredInheritedMembers(ContainerType<?> type) {
		return new LazyOverrideAwareMemberCollectorX(type, declMergingHelper, false, true).getResult();
	}

	static class LazyOverrideAwareMemberCollectorX extends AbstractCompleteHierarchyTraverser<List<TMember>> {

		boolean includeImplicitSuperTypes;

		private List<TMember> result;
		private final RuleEnvironment G;

		boolean onlyInheritedMembers;

		/**
		 * Creates a new collector with optional support for implicit super types, better use static helper methods.
		 *
		 * @param type
		 *            the base type. Must be contained in a resource set if <code>includeImplicitSuperTypes</code> is
		 *            set to <code>true</code>.
		 * @param includeImplicitSuperTypes
		 *            if true also members of implicit super types will be collected; otherwise only members of declared
		 *            super types are included.
		 * @param onlyInheritedMembers
		 *            if true, owned members of type are ignore, that is only inherited members are collected
		 * @throws IllegalArgumentException
		 *             if <code>includeImplicitSuperTypes</code> is set to <code>true</code> and <code>type</code> is
		 *             not contained in a properly initialized N4JS resource set.
		 */
		private LazyOverrideAwareMemberCollectorX(ContainerType<?> type, DeclMergingHelper declMergingHelper,
				boolean includeImplicitSuperTypes, boolean onlyInheritedMembers) {
			super(type, declMergingHelper);
			this.onlyInheritedMembers = onlyInheritedMembers;
			this.includeImplicitSuperTypes = includeImplicitSuperTypes;
			this.result = createResultInstance();
			this.G = (includeImplicitSuperTypes) ? RuleEnvironmentExtensions.newRuleEnvironment(type) : null;
		}

		@Override
		public Measurement getMeasurement() {
			return N4JSDataCollectors.dcTHT_LazyOverrideAwareMemberCollectorX
					.getMeasurementIfInactive("HierarchyTraverser");
		}

		public List<TMember> createResultInstance() {
			return Lists.newLinkedList();
		}

		@Override
		public List<TMember> doGetResult() {
			return result;
		}

		@Override
		public void doProcess(ContainerType<?> type) {
			if (type instanceof TClassifier) {
				EList<TMember> ownedMembers = ((TClassifier) type).getOwnedMembers();
				result.addAll(ownedMembers);
			}
		}

		@Override
		public void doProcess(PrimitiveType currentType) {
			// nothing to do in this case
		}

		protected void processAndReplace(TClassifier type) {
			EList<TMember> ownedMembers = type.getOwnedMembers();
			Iterator<TMember> iterInherited = result.iterator();
			while (iterInherited.hasNext()) {
				TMember inherited = iterInherited.next();
				if (exists(ownedMembers, m -> Objects.equals(m.getName(), inherited.getName())
						&& m.getMemberType() == inherited.getMemberType() && m.isStatic() == inherited.isStatic())) {
					iterInherited.remove();
				}
			}
			result.addAll(ownedMembers);
		}

		/**
		 * Does not add owned members and add inherited members overridden aware.
		 */
		@Override
		public boolean visitTClass(ParameterizedTypeRef typeRef, TClass object) {
			List<TMember> parentResult = result;
			if (object != bottomType) {
				result = createResultInstance();
			}

			// add and merge
			doSwitchTypeRefs(getSuperTypes(object));
			doSwitchTypeRefs(object.getImplementedInterfaceRefs());

			// add and replace
			if (!onlyInheritedMembers || object != bottomType) { // do not add
				processAndReplace(object); // ownedMembers
			}

			if (parentResult != result) {
				parentResult.addAll(result); // merge
				result = parentResult;
			}

			return Boolean.FALSE;
		}

		/**
		 * Does not add owned members and add inherited members overridden aware.
		 */
		@Override
		public boolean visitTInterface(ParameterizedTypeRef typeRef, TInterface object) {
			List<TMember> parentResult = result;
			if (object != bottomType) {
				result = createResultInstance();
			}

			doSwitchTypeRefs(object.getSuperInterfaceRefs());
			doSwitchTypeRefs(object.getSuperInterfaceRefs());
			if (!onlyInheritedMembers || object != bottomType) { // do not add
				processAndReplace(object);
			}

			if (parentResult != result) {
				parentResult.addAll(result); // merge
				result = parentResult;
			}

			return Boolean.FALSE;
		}

		@Override
		public List<ParameterizedTypeRef> getImplicitSuperTypes(Type t) {
			if (includeImplicitSuperTypes) {
				List<ParameterizedTypeRef> implSuperTypeRefs = new ArrayList<>();
				for (TypeRef currTypeRef : RuleEnvironmentExtensions.collectAllImplicitSuperTypes(G,
						TypeUtils.createTypeRef(t))) {
					implSuperTypeRefs.add((ParameterizedTypeRef) currTypeRef); // they should all be
																				// ParameterizedTypeRefs
				}
				return implSuperTypeRefs;
			} else {
				return Collections.emptyList();
			}
		}
	}
}
