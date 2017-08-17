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
package org.eclipse.n4js.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.util.IAcceptor;

import com.google.inject.Inject;

/**
 * Collects all Types,TVariables,TLiterals and IdentifiableElements referenced within the AST of a given fully*resolved
 * resource,when they aren'tcontained in this resource,fully resolved and no built-in type.Additional*checks can be
 * performed by the passed acceptor.*
 * <p>
 * Helper for{@link N4JSResourceDescription}.
 *
 */
public class N4JSCrossReferenceComputer {

	@Inject
	private N4JSExternalReferenceChecker externalReferenceChecker;

	/**
	 * Collects all Types, TVariables, TLiterals and IdentifiableElements that are directly referenced somewhere in the
	 * given resource and aren't contained in this resource. References between AST element to its defined type and vice
	 * versa as well as references to built-in and primitive types are ignored.
	 *
	 * @param resource
	 *            the given fully resolved resource
	 * @param acceptor
	 *            the logic that collects the passed EObject found in a cross reference
	 */
	public void computeCrossRefs(Resource resource, IAcceptor<ImmutablePair<EObject, List<EObject>>> acceptor) {
		TreeIterator<EObject> allASTContentsIter;
		if (resource instanceof N4JSResource) {
			Script script = (Script) ((N4JSResource) resource).getContents().get(0);
			// We traverse the AST but not the TModule tree
			allASTContentsIter = N4JSLanguageUtils.getAllContents(script);
		} else {
			allASTContentsIter = resource.getAllContents();
		}
		while (allASTContentsIter.hasNext()) {
			EObject eObject = allASTContentsIter.next();
			computeCrossRefs(resource, eObject, acceptor);
		}
	}

	/*
	 * Browse all references type by the EClass of the given EObject ignoring References between AST element to its
	 * defined type and vice versa.
	 */
	private void computeCrossRefs(Resource resource, EObject from,
			IAcceptor<ImmutablePair<EObject, List<EObject>>> acceptor) {
		EList<EReference> references = from.eClass().getEAllReferences();
		for (EReference eReference : references) {
			// We only follow cross references
			if (!eReference.isContainment() && !eReference.isContainer()) {
				// Ignore references between AST element and its defined type and vice versa
				if (eReference != N4JSPackage.Literals.TYPE_DEFINING_ELEMENT__DEFINED_TYPE
						&& eReference != TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT) {
					if (from.eIsSet(eReference)) {
						Object val = from.eGet(eReference);
						// Handle both toOne and toMany cases
						if (!eReference.isMany()) {
							EObject to = (EObject) val;
							handleReferenceObject(resource, from, acceptor, to);
						} else {
							@SuppressWarnings("unchecked")
							BasicEList<EObject> list = (BasicEList<EObject>) val;
							// Since the cross type computer is called very frequently, we want to optimize *many*
							// cases
							if (TypesPackage.Literals.TYPE.isSuperTypeOf(eReference.getEReferenceType())) {
								for (EObject to : list) {
									handleType(resource, from, acceptor, (Type) to);
								}
							} else if (TypesPackage.Literals.IDENTIFIABLE_ELEMENT
									.isSuperTypeOf(eReference.getEReferenceType())) {
								for (EObject to : list) {
									handleIdentifiableElement(resource, from, acceptor,
											Collections.singletonList((IdentifiableElement) to));
								}
							} else {
								// Handle all other cases
								for (EObject to : list) {
									handleReferenceObject(resource, from, acceptor, to);
								}
							}
						}
					}
				}
			}
		}
	}

	/*
	 * Collect references to type references, types and identifiable element (direct or as part of and property access
	 * expression):
	 */
	private void handleReferenceObject(Resource resource, EObject from,
			IAcceptor<ImmutablePair<EObject, List<EObject>>> acceptor, EObject to) {
		if (to instanceof Type) {
			handleType(resource, from, acceptor, (Type) to);
		} else if (to instanceof TMember) {
			// Special handling of TMember because it can be a composed member
			handleTMember(resource, from, acceptor, (TMember) to);
		} else if (to instanceof IdentifiableElement) {
			handleIdentifiableElement(resource, from, acceptor, Collections.singletonList((IdentifiableElement) to));
		}
	}

	private void handleTMember(Resource resource, EObject from,
			IAcceptor<ImmutablePair<EObject, List<EObject>>> acceptor, TMember to) {
		if (to.isComposed()) {
			// If the member is a composed member, handle the constituent members instead
			List<IdentifiableElement> constituentMembers = new ArrayList<>();
			for (TMember constituentMember : to.getConstituentMembers())
				constituentMembers.add(constituentMember);
			handleIdentifiableElement(resource, from, acceptor, constituentMembers);
		} else {
			// Standard case
			handleIdentifiableElement(resource, from, acceptor, Collections.singletonList(to));
		}
	}

	private void handleType(Resource resource, EObject from, IAcceptor<ImmutablePair<EObject, List<EObject>>> acceptor,
			Type to) {
		if (to != null) {
			if (!N4Scheme.isFromResourceWithN4Scheme(to) &&
					externalReferenceChecker.isResolvedAndExternal(resource, to)) {
				acceptor.accept(new ImmutablePair<EObject, List<EObject>>(from, Collections.singletonList(to)));
			}
		}
	}

	private void handleIdentifiableElement(Resource resource, EObject from,
			IAcceptor<ImmutablePair<EObject, List<EObject>>> acceptor,
			List<IdentifiableElement> tos) {
		if (tos != null) {
			// Filter those in 'tos' that are located in other resources
			acceptor.accept(new ImmutablePair<EObject, List<EObject>>(from,
					tos.stream().filter(to -> isLocatedInOtherResources(resource, to))
							.collect(Collectors.toList())));
		}
	}

	private boolean isLocatedInOtherResources(Resource resource, EObject eobj) {
		if (eobj == null || eobj.eResource() == null)
			return false;

		boolean ret = !N4Scheme.isFromResourceWithN4Scheme(eobj)
				&& externalReferenceChecker.isResolvedAndExternal(resource, eobj);
		return ret;
	}
}
