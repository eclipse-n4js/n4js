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
package org.eclipse.n4js.ui.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.util.NameStaticPair;
import org.eclipse.n4js.ts.ui.search.LabellingReferenceQueryExecutor;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.validation.validators.utils.MemberCube;
import org.eclipse.n4js.validation.validators.utils.MemberMatrix;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Adds more filter criteria to the UI find references represenation.
 */
public class N4JSReferenceQueryExecutor extends LabellingReferenceQueryExecutor {
	@Inject
	ContainerTypesHelper containerTypesHelper;

	/***
	 * Here, we add overriden/overrding members or super/subclasses if needed depending on user preferences when finding
	 * references.
	 */
	@Override
	protected Iterable<URI> getTargetURIs(EObject primaryTarget) {
		@SuppressWarnings("restriction")
		Iterable<URI> originalResult = super.getTargetURIs(primaryTarget);
		List<URI> newResult = Lists.newArrayList(originalResult);

		if (primaryTarget instanceof LiteralOrComputedPropertyName) {
			primaryTarget = primaryTarget.eContainer();
		}
		// Add overriden members
		if (primaryTarget instanceof N4MemberDeclaration) {
			TMember tmember = ((N4MemberDeclaration) primaryTarget).getDefinedTypeElement();
			for (TMember inheritedOrImplementedMember : getInheritedAndImplementedMembers(tmember)) {
				URI uri = EcoreUtil2.getPlatformResourceOrNormalizedURI(inheritedOrImplementedMember);
				newResult.add(uri);
			}
		}

		// GH-73: TODO add overriding members

		// GH-73: TODO add superclasses

		// GH-73: TODO add subclasses

		return newResult;
	}

	private MemberMatrix getMemberMatrix(TMember member) {
		TClassifier tclassifier = (TClassifier) member.eContainer();
		MemberCollector memberCollector = containerTypesHelper.fromContext(tclassifier);
		MemberCube memberCube = new MemberCube(tclassifier, memberCollector);
		NameStaticPair nsp = NameStaticPair.of(member);
		Optional<Entry<NameStaticPair, MemberMatrix>> ret = memberCube.entrySet().stream()
				.filter(entry -> entry.getKey().equals(nsp)).findFirst();
		if (ret.isPresent()) {
			Entry<NameStaticPair, MemberMatrix> mmEntry = ret.get();
			return mmEntry.getValue();
		}
		return null;
	}

	private List<TMember> getInheritedAndImplementedMembers(TMember tmember) {
		List<TMember> ret = new ArrayList<>();
		MemberMatrix mm = getMemberMatrix(tmember);
		if (mm != null) {
			for (TMember member : mm.inherited()) {
				ret.add(member);
			}
			for (TMember member : mm.implemented()) {
				ret.add(member);
			}
		}
		return ret;
	}

	@Override
	protected boolean isRelevantToUser(EReference reference) {
		return super.isRelevantToUser(reference)
				&& N4JSPackage.Literals.TYPE_DEFINING_ELEMENT__DEFINED_TYPE != reference &&
				N4JSPackage.Literals.N4_ENUM_LITERAL__DEFINED_LITERAL != reference &&
				N4JSPackage.Literals.N4_FIELD_DECLARATION__DEFINED_FIELD != reference &&
				N4JSPackage.Literals.GETTER_DECLARATION__DEFINED_GETTER != reference &&
				N4JSPackage.Literals.SETTER_DECLARATION__DEFINED_SETTER != reference &&
				N4JSPackage.Literals.EXPORTED_VARIABLE_DECLARATION__DEFINED_VARIABLE != reference &&
				N4JSPackage.Literals.SCRIPT__MODULE != reference;
	}
}
