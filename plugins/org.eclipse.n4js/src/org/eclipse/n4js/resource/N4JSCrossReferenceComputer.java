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

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.scoping.members.ComposedMemberScope;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.utils.TypeHelper;
import org.eclipse.xtext.util.IAcceptor;

import com.google.inject.Inject;

/**
 * Collects all Types,TVariables,TLiterals and IdentifiableElements referenced within the AST of a given fully*resolved
 * resource,when they aren'tcontained in this resource,fully resolved and no built-in type.Additional*checks can be
 * performed by the passed acceptor.*
 * <p>
 * Helper for{@link N4JSResourceDescription}.
 *
 * TODO: handle {@link Wildcard}s and other {@link TypeArgument}s in {@link ParameterizedTypeRef}s.
 */
public class N4JSCrossReferenceComputer {

	@Inject
	private N4JSExternalReferenceChecker externalReferenceChecker;
	@Inject
	private TypeHelper th;

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
	public void computeCrossRefs(Resource resource, IAcceptor<ImmutablePair<EObject, EObject>> acceptor) {
		TreeIterator<EObject> allContentsIter = resource.getAllContents();
		while (allContentsIter.hasNext()) {
			EObject eObject = allContentsIter.next();
			computeCrossRefs(eObject, acceptor);
		}
	}

	/*
	 * Browse all references type by the EClass of the given EObject ignoring References between AST element to its
	 * defined type and vice versa.
	 */
	private void computeCrossRefs(EObject from, IAcceptor<ImmutablePair<EObject, EObject>> acceptor) {
		EList<EReference> references = from.eClass().getEAllReferences();
		for (EReference eReference : references) {
			if (eReference != N4JSPackage.Literals.TYPE_DEFINING_ELEMENT__DEFINED_TYPE
					&& eReference != TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT) {
				if (from.eIsSet(eReference)) {
					Object val = from.eGet(eReference);
					// Handle both toOne and toMany cases
					if (!eReference.isMany()) {
						handleReferenceObject(from, acceptor, (EObject) val);
					} else {
						@SuppressWarnings("unchecked")
						BasicEList<EObject> list = (BasicEList<EObject>) val;
						for (int i = 0; i < list.size(); i++) {
							EObject to = list.basicGet(i);
							handleReferenceObject(from, acceptor, to);
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
	private void handleReferenceObject(EObject from, IAcceptor<ImmutablePair<EObject, EObject>> acceptor, EObject to) {
		if (to instanceof TypeRef) {
			handleTypeRef(from, acceptor, (TypeRef) to);
		} else if (to instanceof Type) {
			handleType(from, acceptor, (Type) to);
		} else if (to instanceof IdentifiableElement) {
			handleIdentifiableElement(from, acceptor, (IdentifiableElement) to);
		}
	}

	/*
	 * Extract declared type for the given type reference.
	 */
	private void handleTypeRef(EObject from, IAcceptor<ImmutablePair<EObject, EObject>> acceptor, TypeRef to) {
		Type toType = th.extractType(to);
		if (toType != null) {
			handleType(from, acceptor, toType);
		}
	}

	private void handleType(EObject from, IAcceptor<ImmutablePair<EObject, EObject>> acceptor, Type to) {
		if (to instanceof TMember && ComposedMemberScope.isComposedMember((TMember) to)) {
			// TODO IDE-1253 / IDE-1806: handling of composed members in N4JSCrossReferenceComputer
			if (to.eResource() == null) {
				return; // quick fix: ignore this member (would lead to an exception below)
			}
		}

		if (to != null && !N4Scheme.isFromResourceWithN4Scheme(to)
				&& externalReferenceChecker.isResolvedAndExternal(from, to)) {
			acceptor.accept(new ImmutablePair<EObject, EObject>(from, to));
		}
	}

	private void handleIdentifiableElement(EObject from, IAcceptor<ImmutablePair<EObject, EObject>> acceptor,
			IdentifiableElement to) {
		if (to != null) {
			Resource resource = to.eResource();
			// guard against null resource that is sometimes returned if a member was put into a
			// union type ref that is not contained in a resource and does not have an original decl
			if (resource != null && !N4Scheme.isFromResourceWithN4Scheme(to)
					&& externalReferenceChecker.isResolvedAndExternal(from, to)) {
				acceptor.accept(new ImmutablePair<EObject, EObject>(from, to));
			} else if (resource == null && !to.eIsProxy()) {
				// we want to record these imported names anyway
				acceptor.accept(new ImmutablePair<EObject, EObject>(from, to));
			}
		}
	}
}
