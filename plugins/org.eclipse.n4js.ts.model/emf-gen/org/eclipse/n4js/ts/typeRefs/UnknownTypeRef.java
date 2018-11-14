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
 * A representation of the model object '<em><b>Unknown Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Unknown type references are the result of an unresolved link or other form of error.
 * No follow up errors should be produced for such unknown type references.
 * <pre>
 * var x = doesNotExist // error here; type of x will be UnknownTypeRef
 * x.cannotKnowIfThisExists // no error here
 * var s: String = x // no error here, either
 * x = s // nor here
 * </pre>
 * Because {@code UnknownTypeRef}s suppress consequential errors, they must never
 * appear in an N4JSResource that does not have any original parse or validation errors!
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getUnknownTypeRef()
 * @model
 * @generated
 */
public interface UnknownTypeRef extends TypeRef {
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

} // UnknownTypeRef
