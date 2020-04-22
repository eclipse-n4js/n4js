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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.scoping.smith.MeasurableScope;
import org.eclipse.n4js.scoping.utils.RestrictedUsageDescription;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.NameAndAccess;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.VirtualBaseType;
import org.eclipse.n4js.ts.types.internal.MemberByNameAndAccessMap;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.inject.Inject;

/**
 * A scope implementation that wraps a type and allows to access its members as scope content.
 */
public class MemberScope extends AbstractMemberScope {
	/**
	 * Emulates a (injector) factory w/o FactoryModuleBuilder
	 */
	public static class MemberScopeFactory {

		@Inject
		ContainerTypesHelper containerTypesHelper;

		@Inject
		JavaScriptVariantHelper jsVariantHelper;

		/**
		 * Factory method to produce a {@link MemberScope} with the members of the given ContainerType.
		 *
		 * @param structFieldInitMode
		 *            see {@link AbstractMemberScope#structFieldInitMode}.
		 */
		public IScope create(IScope parent, ContainerType<?> type,
				EObject context, boolean staticAccess, boolean structFieldInitMode, boolean isDynamicType) {

			return new MemberScope(containerTypesHelper, parent, type, context, staticAccess, structFieldInitMode,
					isDynamicType, jsVariantHelper);
		}

		/**
		 * Factory method to produce a {@link MemberScope} with the members of the given ContainerType without a parent.
		 *
		 * @param structFieldInitMode
		 *            see {@link AbstractMemberScope#structFieldInitMode}.
		 */
		public IScope create(ContainerType<?> type,
				EObject context, boolean staticAccess, boolean structFieldInitMode, boolean isDynamicType) {

			return new MemberScope(containerTypesHelper, type, context, staticAccess, structFieldInitMode,
					isDynamicType, jsVariantHelper);
		}

		/**
		 * Factory method to produce a {@link MemberScope} with the members provided in list 'members'. Only used for
		 * structural type references with structural members.
		 *
		 * @param structFieldInitMode
		 *            see {@link AbstractMemberScope#structFieldInitMode}.
		 */
		public IScope create(IScope parent,
				List<? extends TMember> members, EObject context, boolean staticAccess, boolean structFieldInitMode,
				boolean isDynamicType) {

			return new MemberScope(containerTypesHelper, parent, members, context, staticAccess, structFieldInitMode,
					isDynamicType, jsVariantHelper);
		}
	}

	/**
	 * The type of which the members are to be found, or null if members are passed as a list in the constructor
	 * directly (usually for structural types).
	 *
	 * @see MemberScopeFactory
	 */
	final ContainerType<?> type;
	final List<TMember> members;

	MemberByNameAndAccessMap membersByNameAndAccess;

	// @Inject -- too bad that we have no @Assisted features available...
	final ContainerTypesHelper containerTypesHelper;

	/**
	 * @see MemberScopeFactory#create(IScope, List, EObject, boolean, boolean, boolean)
	 */
	MemberScope(ContainerTypesHelper containerTypesHelper, IScope parent,
			List<? extends TMember> members, EObject context,
			boolean staticAccess, boolean structFieldInitMode, boolean isDynamicType,
			JavaScriptVariantHelper jsVariantHelper) {
		this(parent, context, staticAccess, structFieldInitMode, isDynamicType, jsVariantHelper,
				containerTypesHelper, null, new ArrayList<>(members));
	}

	/**
	 * @see MemberScopeFactory#create(IScope, ContainerType, EObject, boolean, boolean, boolean)
	 */
	MemberScope(ContainerTypesHelper containerTypesHelper, IScope parent, ContainerType<?> type,
			EObject context,
			boolean staticAccess, boolean structFieldInitMode, boolean isDynamicType,
			JavaScriptVariantHelper jsVariantHelper) {
		this(parent, context, staticAccess, structFieldInitMode, isDynamicType, jsVariantHelper,
				containerTypesHelper, type, null);
	}

	/**
	 * @see MemberScopeFactory#create(ContainerType, EObject, boolean, boolean, boolean)
	 */
	MemberScope(ContainerTypesHelper containerTypesHelper, ContainerType<?> type,
			EObject context,
			boolean staticAccess, boolean structFieldInitMode, boolean isDynamicType,
			JavaScriptVariantHelper jsVariantHelper) {
		this(IScope.NULLSCOPE, context, staticAccess, structFieldInitMode, isDynamicType, jsVariantHelper,
				containerTypesHelper, type, null);
	}

	private MemberScope(IScope parent,
			EObject context,
			boolean staticAccess,
			boolean structFieldInitMode,
			boolean isDynamicType,
			JavaScriptVariantHelper jsVariantHelper,
			ContainerTypesHelper containerTypesHelper,
			ContainerType<?> type,
			List<TMember> members) {
		super(parent, context, staticAccess, structFieldInitMode, isDynamicType, jsVariantHelper);
		this.containerTypesHelper = containerTypesHelper;
		this.type = type;
		this.members = members;
	}

	@Override
	public IScope decorate(DataCollector dataCollector) {
		return new MemberScope(MeasurableScope.decorate(getParent(), dataCollector),
				context, staticAccess, structFieldInitMode, isDynamicType, jsVariantHelper,
				containerTypesHelper, type, members);
	}

	/**
	 * Returns the member for the given name and access. Subclasses may override to create members lazily, but should
	 * then also override {@link #getMembers()}.
	 */
	@Override
	protected TMember findMember(String name, boolean writeAccess, @SuppressWarnings("hiding") boolean staticAccess) {
		if (type != null) {
			return containerTypesHelper.fromContext(contextResource).findMember(type, name, writeAccess, staticAccess);
		} else {
			if (membersByNameAndAccess == null)
				membersByNameAndAccess = new MemberByNameAndAccessMap(members);
			final NameAndAccess nameAndAccess = new NameAndAccess(name, writeAccess, staticAccess);
			return membersByNameAndAccess.get(nameAndAccess);
		}
	}

	/**
	 * Returns members in this scope. Subclasses may override to create members lazily, but should then also override
	 * {@link #findMember(String, boolean, boolean)}.
	 */
	@Override
	protected Collection<? extends TMember> getMembers() {
		if (type != null) {
			return containerTypesHelper.fromContext(contextResource).members(type);
		} else {
			return members;
		}
	}

	@Override
	protected IEObjectDescription createSingleElementDescription(TMember existingMember) {
		IEObjectDescription description = super.createSingleElementDescription(existingMember);
		// #hack for IDE-662 restriction on arguments.callee in all modes except for unrestricted Javascript.
		if (type instanceof VirtualBaseType) {
			if (type.getName().equals("ArgumentsType") && existingMember.getName().equals("callee")) {
				// JavaScriptVariant jsVariant = JavaScriptVariant.getVariant(context);
				if (!jsVariantHelper.isUnrestrictedMode(context)) {
					return new RestrictedUsageDescription(description, jsVariantHelper.variantMode(context));
				}
			}
		}

		return description;
	}
}
