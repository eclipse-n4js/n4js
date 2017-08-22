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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.util.IAcceptor;

import com.google.inject.Inject;

/**
 * Collects all Types, TVariables, TLiterals and IdentifiableElements referenced within the AST of a given fully
 * resolved resource, when they aren't contained in this resource, fully resolved and no built-in type. Additional
 * checks can be performed by the passed acceptor.
 * <p>
 * Helper for {@link N4JSResourceDescription}.
 *
 * TODO: handle {@link Wildcard}s and other {@link TypeArgument}s in {@link ParameterizedTypeRef}s.
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
	void computeCrossRefs(Resource resource, IAcceptor<EObject> acceptor) {
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
	private void computeCrossRefs(EObject from, IAcceptor<EObject> acceptor) {
		EList<EReference> references = from.eClass().getEAllReferences();
		for (EReference eReference : references) {
			if (eReference != N4JSPackage.Literals.TYPE_DEFINING_ELEMENT__DEFINED_TYPE
					&& eReference != TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT) {
				if (from.eIsSet(eReference)) {
					handleReference(from, acceptor, eReference);
				}
			}
		}
	}

	/*
	 * Collect references to type references, types and identifiable element (direct or as part of and property access
	 * expression):
	 */
	private void handleReference(EObject from, IAcceptor<EObject> acceptor, EReference eReference) {
		Object val = from.eGet(eReference, true);
		if (eReference != N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY) {
			if (eReference.getEReferenceType() == TypeRefsPackage.Literals.TYPE_REF
					|| eReference.getEReferenceType() == TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF) {
				handleParameterizedTypeRef(from, acceptor, eReference, val);
			} else if (eReference.getEReferenceType() == TypesPackage.Literals.TYPE) {
				handleType(from, acceptor, eReference, val);
			} else if (eReference.getEReferenceType() == TypesPackage.Literals.IDENTIFIABLE_ELEMENT) {
				handleIdentifiableElement(from, acceptor, eReference, val);
			}
		} else {
			handlePropertyAccess(from, acceptor, eReference, val);
		}
	}

	/*
	 * handle toOne and toMany references for reference of type property access expression
	 */
	private void handlePropertyAccess(EObject from, IAcceptor<EObject> acceptor, EReference eReference, Object val) {
		if (!eReference.isMany()) {
			handlePropertyAccess((ParameterizedPropertyAccessExpression) from, acceptor, val);
		} else {
			@SuppressWarnings("unchecked")
			BasicEList<EObject> list = (BasicEList<EObject>) val;
			for (int i = 0; i < list.size(); i++) {
				EObject to = list.basicGet(i);
				handlePropertyAccess((ParameterizedPropertyAccessExpression) from, acceptor, to);
			}
		}
	}

	/*
	 * handle toOne and toMany references for reference of type identifiable element
	 */
	private void handleIdentifiableElement(EObject from, IAcceptor<EObject> acceptor, EReference eReference,
			Object val) {
		if (!eReference.isMany()) {
			handleIdentifiableElement(from, acceptor, (IdentifiableElement) val);
		} else {
			@SuppressWarnings("unchecked")
			InternalEList<EObject> list = (InternalEList<EObject>) val;
			for (int i = 0; i < list.size(); i++) {
				EObject to = list.basicGet(i);
				handleIdentifiableElement(from, acceptor, (IdentifiableElement) to);
			}
		}
	}

	/*
	 * handle toOne and toMany references for reference of type Type
	 */
	private void handleType(EObject from, IAcceptor<EObject> acceptor, EReference eReference, Object val) {
		if (!eReference.isMany()) {
			handleType(from, acceptor, (Type) val);
		} else {
			@SuppressWarnings("unchecked")
			InternalEList<EObject> list = (InternalEList<EObject>) val;
			for (int i = 0; i < list.size(); i++) {
				EObject to = list.basicGet(i);
				handleType(from, acceptor, (Type) to);
			}
		}
	}

	/*
	 * handle toOne and toMany references for reference of type ParameterizedTypeRef
	 */
	private void handleParameterizedTypeRef(EObject from, IAcceptor<EObject> acceptor, EReference eReference,
			Object val) {
		if (!eReference.isMany()) {
			handleTypeRef(from, acceptor, val);
		} else {
			@SuppressWarnings("unchecked")
			InternalEList<EObject> list = (InternalEList<EObject>) val;
			for (int i = 0; i < list.size(); i++) {
				EObject to = list.basicGet(i);
				handleTypeRef(from, acceptor, to);
			}
		}
	}

	/*
	 * dispatches Types, TVariables, TLiterals and IdentifiableElements for resolved reference for property in property
	 * access expression.
	 */
	private void handlePropertyAccess(ParameterizedPropertyAccessExpression from, IAcceptor<EObject> acceptor,
			Object val) {
		if (val instanceof TypeRef
				|| val instanceof ParameterizedTypeRef) {
			handleTypeRef(from, acceptor, val);
		} else if (val instanceof Type) {
			handleType(from, acceptor, (Type) val);
		} else if (val instanceof IdentifiableElement) {
			handleIdentifiableElement(from, acceptor, (IdentifiableElement) val);
		}
	}

	/*
	 * Extract declared type for the given type reference.
	 */
	private void handleTypeRef(EObject from, IAcceptor<EObject> acceptor, Object val) {
		if (val instanceof ParameterizedTypeRef) {
			ParameterizedTypeRef ref = (ParameterizedTypeRef) val;
			Type to = ref.getDeclaredType();
			handleType(from, acceptor, to);
		} else {
			// TODO handle other type refs
			// TypeRef ref = (TypeRef) val;
		}
	}

	private void handleType(EObject from, IAcceptor<EObject> acceptor, Type to) {
		if (to != null && !N4Scheme.isFromResourceWithN4Scheme(to)
				&& externalReferenceChecker.isResolvedAndExternal(from, to)) {
			acceptor.accept(to);
		}
	}

	private void handleIdentifiableElement(EObject from, IAcceptor<EObject> acceptor, IdentifiableElement to) {
		if (to != null) {
			Resource resource = to.eResource();
			// guard against null resource that is sometimes returned if a member was put into a
			// union type ref that is not contained in a resource and does not have an original decl
			if (resource != null && N4Scheme.isResourceWithN4Scheme(resource)
					&& externalReferenceChecker.isResolvedAndExternal(from, to)) {
				acceptor.accept(to);
			} else if (resource == null && !to.eIsProxy()) {
				// we want to record these imported names anyway
				acceptor.accept(to);
			}
		}
	}

}
