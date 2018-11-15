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
package org.eclipse.n4js.ts.typeRefs;

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Versioned Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This is a base class for all AST nodes that represent a reference to a versioned object and where the user can
 * explicitly request a specific version. Currently, this includes ParameterizedTypeRef (for explicitly versioned
 * parameter types and local variables in N4IDL migration scripts) and IdentifierRef (for explicitly versioned
 * references to identifiers as the callee of a NewExpression).
 *  * If an instance of this class does not have an explicitly requested version (set by the parser), then the type system
 * may compute the requested version from context information and store the result in the requestedVersion attribute.
 *  * The latter only happens in N4IDL via N4IDLVersionResolver.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.VersionedReference#getRequestedVersion <em>Requested Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getVersionedReference()
 * @model abstract="true"
 * @generated
 */
public interface VersionedReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requested Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requested Version</em>' attribute.
	 * @see #setRequestedVersion(BigDecimal)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getVersionedReference_RequestedVersion()
	 * @model unique="false"
	 * @generated
	 */
	BigDecimal getRequestedVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.VersionedReference#getRequestedVersion <em>Requested Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requested Version</em>' attribute.
	 * @see #getRequestedVersion()
	 * @generated
	 */
	void setRequestedVersion(BigDecimal value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates whether this type reference has an explicitly requested version. A requested version can either be
	 * set by the parser when the user explicitly requests a specific version in source code, or by the type system
	 * when computing the implicitly requested version, e.g. for property access expressions.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean hasRequestedVersion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	int getRequestedVersionOrZero();

} // VersionedReference
