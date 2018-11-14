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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;

/**
 * The purpose of the {@link ComposedMemberInfoBuilder} is to provide methods to aggregate all existing members into one
 * {@link ComposedMemberInfo} object on which a new composed member is based upon. The existing members are also called
 * siblings.
 *
 * The method {@link #get()} returns an aggregation object. Their life cycle is as follows:
 *
 * <pre>
 * Start -> init() -> addMember()* -> get() -> End.
 *                       ^             |
 *                       |_____________|
 * </pre>
 */
public class ComposedMemberInfoBuilder {
	private boolean writeAccess;
	private Resource resource;
	private N4JSTypeSystem ts;
	private List<ToBeComposedMemberInfo> siblings;

	/**
	 * Required information on each member that is to be composed into a {@link ComposedMemberInfo}. In other words, two
	 * or more {@link ToBeComposedMemberInfo}s are composed into a single {@link ComposedMemberInfo} by using
	 * {@link ComposedMemberInfoBuilder}.
	 */
	public static final class ToBeComposedMemberInfo {
		final TMember member;
		final RuleEnvironment G;
		final boolean structFieldInitMode;

		/** See {@link ToBeComposedMemberInfo}. */
		public ToBeComposedMemberInfo(TMember member, RuleEnvironment G, boolean structFieldInitMode) {
			this.member = member;
			this.G = G;
			this.structFieldInitMode = structFieldInitMode;
		}
	}

	/**
	 * Initializes the static methods. (Also refer to the life cycle mentioned above.)
	 */
	@SuppressWarnings("hiding")
	public void init(boolean writeAccess, Resource resource, N4JSTypeSystem ts) {
		Objects.isNull(siblings);
		this.writeAccess = writeAccess;
		this.resource = resource;
		this.ts = ts;
		this.siblings = new LinkedList<>();
	}

	/**
	 * Adds a sibling member on which a new composed member is based upon. (Also refer to the life cycle mentioned
	 * above.)
	 *
	 * @param member
	 *            the member to be added or <code>null</code> to denote a missing member.
	 */
	public void addMember(TMember member, RuleEnvironment G, boolean structFieldInitMode) {
		Objects.nonNull(siblings);
		ToBeComposedMemberInfo info = null;
		if (member != null) {
			info = new ToBeComposedMemberInfo(member, G, structFieldInitMode);
		}
		siblings.add(info); // adds null to indicate missing members
	}

	/**
	 * Returns a data object that provides all aggregated information to create a new composed {@link TMember}. This
	 * method also performs an initialization for adding members to the next data object. (Also refer to the life cycle
	 * mentioned above.)
	 */
	public ComposedMemberInfo get() {
		ComposedMemberInfo cmi = new ComposedMemberInfo(writeAccess, resource, ts, siblings);
		return cmi;
	}

}
