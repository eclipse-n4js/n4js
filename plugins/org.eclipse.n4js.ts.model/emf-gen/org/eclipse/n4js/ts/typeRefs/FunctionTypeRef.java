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

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TypeVariable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Function type, always references a function, that is, declaredType returns a TFunction.
 * This kind of reference is created implicitly by function calls. Clients only <em>using</em> this kind
 * of references should probably better use {@link FunctionTypeExprOrRef}.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getFunctionTypeRef()
 * @model
 * @generated
 */
public interface FunctionTypeRef extends ParameterizedTypeRef, FunctionTypeExprOrRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns the declared this type of the referenced function type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getDeclaredThisType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns the type variables of the referenced function type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TypeVariable> getTypeVars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Simply returns the given type variable's declared upper bounds (required by super class API).
	 * <!-- end-model-doc -->
	 * @model unique="false" typeVarUnique="false"
	 * @generated
	 */
	TypeRef getTypeVarUpperBound(TypeVariable typeVar);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns the fpars of the referenced function type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TFormalParameter> getFpars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns the return type of the referenced function type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getReturnTypeRef();

} // FunctionTypeRef
