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
import org.eclipse.xtext.xbase.lib.Pair;

import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import it.xsemantics.runtime.RuleEnvironment;

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
	private List<Pair<TMember, RuleEnvironment>> siblings;

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
	 */
	public void addMember(TMember member, RuleEnvironment G) {
		Objects.nonNull(siblings);
		Pair<TMember, RuleEnvironment> pair = null;
		if (member != null) {
			pair = new Pair<>(member, G);
		}
		siblings.add(pair); // adds null to indicate missing members
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
