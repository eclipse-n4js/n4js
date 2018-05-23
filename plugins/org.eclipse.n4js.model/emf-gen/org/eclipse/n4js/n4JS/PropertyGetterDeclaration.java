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
package org.eclipse.n4js.n4JS;

import org.eclipse.n4js.ts.types.TStructGetter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Getter Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyGetterDeclaration()
 * @model
 * @generated
 */
public interface PropertyGetterDeclaration extends GetterDeclaration, AnnotablePropertyAssignment {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TGetter%&gt; _definedGetter = super.getDefinedGetter();\nreturn ((&lt;%org.eclipse.n4js.ts.types.TStructGetter%&gt;) _definedGetter);'"
	 * @generated
	 */
	TStructGetter getDefinedGetter();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getDefinedGetter();'"
	 * @generated
	 */
	TStructGetter getDefinedMember();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Getters in object literals may not be named 'prototype'.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _name = this.getName();\nboolean _equals = &lt;%com.google.common.base.Objects%&gt;.equal(\"prototype\", _name);\nif (_equals)\n{\n\treturn false;\n}\nreturn true;'"
	 * @generated
	 */
	boolean isValidName();

} // PropertyGetterDeclaration
