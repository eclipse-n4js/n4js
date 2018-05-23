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
package org.eclipse.n4js.transpiler.im;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;

import org.eclipse.n4js.ts.types.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Type Ref Structural IM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * SymbolTable-aware replacement for {@link ParameterizedTypeRefStructural}.
 * Original property {@link ParameterizedTypeRef.declaredType} is always {@code null}.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getParameterizedTypeRefStructural_IM()
 * @model
 * @generated
 */
public interface ParameterizedTypeRefStructural_IM extends ParameterizedTypeRef_IM, ParameterizedTypeRefStructural, ReferencingElement_IM {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getRewiredTarget();'"
	 * @generated
	 */
	SymbolTableEntry getDeclaredType_IM();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model targetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='this.setRewiredTarget(target);'"
	 * @generated
	 */
	void setDeclaredType_IM(SymbolTableEntry target);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  overridden attribute access to always return null
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return null;'"
	 * @generated
	 */
	Type getDeclaredType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ixUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((ix != null))\n{\n\tthrow new &lt;%java.lang.IllegalArgumentException%&gt;(\"ParameterizedTypeRef_IM cannot accept types. Use #declaredType_IM.\");\n}'"
	 * @generated
	 */
	void setDeclaredType(Type ix);

} // ParameterizedTypeRefStructural_IM
