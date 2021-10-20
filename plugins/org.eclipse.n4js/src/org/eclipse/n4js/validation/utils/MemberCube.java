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
package org.eclipse.n4js.validation.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.util.NameStaticPair;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.validation.validators.N4JSMemberRedefinitionValidator;

/**
 * Helper class for the {@link N4JSMemberRedefinitionValidator} storing a {@link MemberMatrix} for each member of the
 * classifier.
 */
public class MemberCube {

	private final Map<NameStaticPair, MemberMatrix> memberMatrixesByName;

	/**
	 * Creates the cube for the given classifier, using the passed member collector. The latter is passed as argument as
	 * it needed DI.
	 */
	public MemberCube(TClassifier tClassifier, MemberCollector memberCollector) {
		this.memberMatrixesByName = new HashMap<>();
		addMembers(MemberMatrix.OWNED, tClassifier.getOwnedMembers());
		if (tClassifier instanceof TClass) {
			addMembers(MemberMatrix.INHERITED, memberCollector
					.inheritedMembers((TClass) tClassifier));
		}
		// interfaces must not contain constructors anyway
		addMembers(MemberMatrix.IMPLEMENTED, memberCollector.membersOfImplementedInterfacesForConsumption(tClassifier));
	}

	private void addMembers(int source, List<TMember> members) {
		for (TMember member : members) {
			ensureResolution(member);
			NameStaticPair nsp = NameStaticPair.of(member);
			MemberMatrix memberMatrix = memberMatrixesByName.get(nsp);
			if (memberMatrix == null) {
				memberMatrix = new MemberMatrix();
				memberMatrixesByName.put(nsp, memberMatrix);
			}
			memberMatrix.add(source, member);
		}
	}

	/**
	 * TEMPORARY WORK-AROUND
	 *
	 * This is required to avoid exceptions when compiling n4js-libs.
	 *
	 * TODO GH-2235 find a better place to ensure resolution of polyfilled members
	 */
	private void ensureResolution(EObject obj) {
		// ensure we do not use types from resources that are only partially resolved
		Resource polyfillResource = obj.eResource();
		if (polyfillResource instanceof N4JSResource) {
			// Note: the following call will be a no-op if
			// 1) the resource was already post-processed, or
			// 2) the resource was loaded from the index.
			((N4JSResource) polyfillResource).performPostProcessing();
		}
	}

	/**
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<NameStaticPair, MemberMatrix>> entrySet() {
		return memberMatrixesByName.entrySet();
	}

	/** Only for debugging */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		StringBuilder row = new StringBuilder();
		for (Entry<NameStaticPair, MemberMatrix> entry : entrySet()) {
			row.setLength(0);
			row.append(entry.getKey().toString()).append(": ");
			UtilN4.fill(row, 36);
			strb.append(row);
			strb.append(entry.getValue().toShortString()).append('\n');
		}
		return strb.toString();
	}
}
