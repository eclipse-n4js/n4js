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

import org.eclipse.n4js.ts.types.TStructMethod;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Method Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyMethodDeclaration()
 * @model
 * @generated
 */
public interface PropertyMethodDeclaration extends AnnotablePropertyAssignment, MethodDeclaration, TypeProvidingElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.Type%&gt; _definedType = this.getDefinedType();\nreturn ((&lt;%org.eclipse.n4js.ts.types.TStructMethod%&gt;) _definedType);'"
	 * @generated
	 */
	TStructMethod getDefinedMember();

} // PropertyMethodDeclaration
