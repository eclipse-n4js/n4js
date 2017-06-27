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
package org.eclipse.n4js.jsdoc2spec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TMember;

/**
 * Spec info for top level module element.
 */
public class SpecInfo {

	/**
	 * The referenced element.
	 */
	public final SpecElementRef specElementRef;

	private final SortedSet<SpecTestInfo> relatedTypeTests;
	private final Map<TMember, SortedSet<SpecTestInfo>> relatedMemberTests;

	/**
	 * Creates a {@link SpecInfo} for a source code element.
	 */
	public SpecInfo(IdentifiableElement identifiableElement) {
		this.specElementRef = new SpecElementRef(identifiableElement);
		relatedTypeTests = new TreeSet<>();
		relatedMemberTests = new HashMap<>();
	}

	/**
	 * Creates a {@link SpecInfo} for a requirement id.
	 */
	public SpecInfo(String reqid) {
		this.specElementRef = new SpecElementRef(reqid);
		relatedTypeTests = new TreeSet<>();
		relatedMemberTests = new HashMap<>();
	}

	@SuppressWarnings("javadoc")
	public void addTypeTestInfo(SpecTestInfo testInfo) {
		relatedTypeTests.add(testInfo);
	}

	@SuppressWarnings("javadoc")
	public void addMemberTestInfo(TMember member, SpecTestInfo testInfo) {
		SortedSet<SpecTestInfo> testsForMember = relatedMemberTests.get(member);
		if (testsForMember == null) {
			testsForMember = new TreeSet<>();
			relatedMemberTests.put(member, testsForMember);
		}
		testsForMember.add(testInfo);
	}

	/**
	 * Returns set of {@link SpecTestInfo}s related to type.
	 *
	 * @return maybe empty set, never null
	 */
	public SortedSet<SpecTestInfo> getTestsForType() {
		return relatedTypeTests;
	}

	/**
	 * Returns set of {@link SpecTestInfo}s related to member.
	 *
	 * @return maybe empty set, never null
	 */
	public SortedSet<SpecTestInfo> getTestsForMember(TMember member) {
		SortedSet<SpecTestInfo> testsForMember = relatedMemberTests.get(member);
		if (testsForMember == null) {
			return Collections.emptySortedSet();
		}
		return testsForMember;
	}

	/**
	 * Returns all tests for inherited members.
	 *
	 * @return maybe empty set, never null
	 */
	public Map<TMember, SortedSet<SpecTestInfo>> getTestsForInheritedMembers() {
		HashMap<TMember, SortedSet<SpecTestInfo>> m = new HashMap<>();

		for (Entry<TMember, SortedSet<SpecTestInfo>> e : relatedMemberTests.entrySet()) {
			// do not add tests for polyfilled members, they are treated as
			// "normal" method tests, cf. IDE-2160
			TMember member = e.getKey();
			if (member.getContainingType() != specElementRef.identifiableElement && !member.isPolyfilled()) {
				m.put(member, e.getValue());
			}
		}
		return m;
	}

	@Override
	public String toString() {
		return "SpecInfo for " + specElementRef;
	}

}
