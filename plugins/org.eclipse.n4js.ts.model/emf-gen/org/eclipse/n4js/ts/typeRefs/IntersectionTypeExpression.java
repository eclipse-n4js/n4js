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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Intersection Type Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Intersection type can be specified by the user directly in terms of an intersection type expression
 * or in the context of a type variable. It may also be inferred by the type system.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getIntersectionTypeExpression()
 * @model
 * @generated
 */
public interface IntersectionTypeExpression extends ComposedTypeRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getTypeRefAsString();

} // IntersectionTypeExpression
