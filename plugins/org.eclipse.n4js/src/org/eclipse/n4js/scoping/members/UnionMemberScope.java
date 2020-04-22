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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.scoping.smith.MeasurableScope;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * Implements methods for handling union members.
 */
public class UnionMemberScope extends ComposedMemberScope {

	/**
	 * Constructor
	 */
	public UnionMemberScope(ComposedTypeRef composedTypeRef, MemberScopeRequest request, List<IScope> subScopes,
			N4JSTypeSystem ts) {
		super(composedTypeRef, request, subScopes, ts);
	}

	@Override
	public IScope decorate(DataCollector dataCollector) {
		List<IScope> sub = new ArrayList<>();
		for (IScope s : subScopes) {
			sub.add(MeasurableScope.decorate(s, dataCollector));
		}
		return new UnionMemberScope(composedTypeRef, request, sub, ts);
	}

	@Override
	protected IEObjectDescription getCheckedDescription(String name, TMember member) {
		IEObjectDescription description = EObjectDescription.create(member.getName(), member);

		QualifiedName qn = QualifiedName.create(name);
		for (IScope currSubScope : subScopes) {
			IEObjectDescription subDescription = currSubScope.getSingleElement(qn);

			boolean descrWithError = subDescription == null
					|| IEObjectDescriptionWithError.isErrorDescription(subDescription);
			if (descrWithError) {
				return createComposedMemberDescriptionWithErrors(description);
			}
		}

		return description;
	}

	@Override
	protected IEObjectDescription createComposedMemberDescriptionWithErrors(IEObjectDescription result) {
		return new UnionMemberDescriptionWithError(result, composedTypeRef, subScopes, writeAccess);
	}

	@Override
	protected ComposedMemberFactory getComposedMemberFactory(ComposedMemberInfo cma) {
		return new UnionMemberFactory(cma);
	}
}
