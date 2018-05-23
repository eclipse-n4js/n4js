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

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Versioned Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This is a base class for all AST nodes where the user can explicitly declare a specific version.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.VersionedElement#getDeclaredVersion <em>Declared Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVersionedElement()
 * @model abstract="true"
 * @generated
 */
public interface VersionedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The version as declared by the user in the source code. Unlike the requestedVersion attribute of class
	 * VersionedReference, the value of this attribute is set by the parser and never changed by the type system.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Version</em>' attribute.
	 * @see #setDeclaredVersion(BigDecimal)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVersionedElement_DeclaredVersion()
	 * @model unique="false"
	 * @generated
	 */
	BigDecimal getDeclaredVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.VersionedElement#getDeclaredVersion <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Version</em>' attribute.
	 * @see #getDeclaredVersion()
	 * @generated
	 */
	void setDeclaredVersion(BigDecimal value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates whether this type reference has an explicitly declared version.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.math.BigDecimal%&gt; _declaredVersion = this.getDeclaredVersion();\nreturn (_declaredVersion != null);'"
	 * @generated
	 */
	boolean hasDeclaredVersion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='int _xifexpression = (int) 0;\nboolean _hasDeclaredVersion = this.hasDeclaredVersion();\nif (_hasDeclaredVersion)\n{\n\t_xifexpression = this.getDeclaredVersion().intValue();\n}\nelse\n{\n\t_xifexpression = 0;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	int getDeclaredVersionOrZero();

} // VersionedElement
