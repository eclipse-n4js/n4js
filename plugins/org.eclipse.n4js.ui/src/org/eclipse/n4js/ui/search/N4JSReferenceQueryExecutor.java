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
import org.eclipse.n4js.n4JS.JSXElementName;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.util.NameStaticPair;
import org.eclipse.n4js.ts.ui.search.LabelledReferenceDescription;
import org.eclipse.n4js.ts.ui.search.LabellingReferenceQueryExecutor;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.validation.validators.utils.MemberCube;
import org.eclipse.n4js.validation.validators.utils.MemberMatrix;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IReferenceDescription;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Adds more filter criteria to the UI find references presentation.
 */
public class N4JSReferenceQueryExecutor extends LabellingReferenceQueryExecutor {
	@Inject
	ContainerTypesHelper containerTypesHelper;
	/** Flag indicating whether overridden members should be considered or not while finding references. */
	public static boolean considerOverridenMethods = false;
	/** Flag indicating whether named import specifier should be ignored. */
	public static boolean ignoreNamedImportSpecifier = true;

	/**
	 * Here, we add overridden/overriding members or super/subclasses if needed depending on user preferences when
	 * finding references.
	 */
	@Override
	protected Iterable<URI> getTargetURIs(EObject primaryTarget) {
		@SuppressWarnings("restriction")
		Iterable<URI> originalResult = super.getTargetURIs(primaryTarget);
		List<URI> newResult = Lists.newArrayList(originalResult);

		if (primaryTarget instanceof LiteralOrComputedPropertyName) {
			primaryTarget = primaryTarget.eContainer();
		}

		// Handling for various configurations e.g. consider overriden members
		List<EObject> realTargets = new ArrayList<>();
		if (primaryTarget instanceof TMember && ((TMember) primaryTarget).isComposed()) {
			// In case of composed member, add the constituent members instead.
			List<TMember> constituentMembers = ((TMember) primaryTarget).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				realTargets.add(constituentMember);
			}
		} else {
			// Standard case
			realTargets.add(primaryTarget);
		}

		for (EObject realTarget : realTargets) {
			if (N4JSReferenceQueryExecutor.considerOverridenMethods) {
				// Add overriden members
				if (realTarget instanceof N4MemberDeclaration
						// Only consider members within a classifier.Ignore TStructMember.
						|| (realTarget instanceof TMember && realTarget.eContainer() instanceof TClassifier)) {
					TMember tmember;
					if (primaryTarget instanceof N4MemberDeclaration) {
						tmember = ((N4MemberDeclaration) primaryTarget).getDefinedTypeElement();
					} else {
						tmember = (TMember) primaryTarget;
					}
					for (TMember inheritedOrImplementedMember : getInheritedAndImplementedMembers(tmember)) {
						URI uri = EcoreUtil2.getPlatformResourceOrNormalizedURI(inheritedOrImplementedMember);
						newResult.add(uri);
					}
				}
			}
		}

		return newResult;
	}

	/**
	 * Returns <code>true</code> if the reference should be presented to the user.
	 */
	@Override
	protected boolean isRelevantToUser(IReferenceDescription referenceDescription) {
		if (referenceDescription instanceof LabelledReferenceDescription) {
			EObject source = ((LabelledReferenceDescription) referenceDescription).getSource();
			if (source.eContainer() instanceof JSXElementName && source.eContainer()
					.eContainingFeature() == N4JSPackage.eINSTANCE.getJSXElement_JsxClosingName()) {
				// Do not show JSX element's closing tag
				return false;
			} else {
				return true;
			}
		} else {
			return super.isRelevantToUser(referenceDescription);
		}
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
}
