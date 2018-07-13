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
package org.eclipse.n4js.semver.SEMVER;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Part</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.VersionPart#isWildcard <em>Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.VersionPart#getNumberRaw <em>Number Raw</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionPart()
 * @model
 * @generated
 */
public interface VersionPart extends EObject {
	/**
	 * Returns the value of the '<em><b>Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wildcard</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wildcard</em>' attribute.
	 * @see #setWildcard(boolean)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionPart_Wildcard()
	 * @model unique="false"
	 * @generated
	 */
	boolean isWildcard();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.VersionPart#isWildcard <em>Wildcard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wildcard</em>' attribute.
	 * @see #isWildcard()
	 * @generated
	 */
	void setWildcard(boolean value);

	/**
	 * Returns the value of the '<em><b>Number Raw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Raw</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Raw</em>' attribute.
	 * @see #setNumberRaw(Integer)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionPart_NumberRaw()
	 * @model unique="false"
	 * @generated
	 */
	Integer getNumberRaw();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.VersionPart#getNumberRaw <em>Number Raw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Raw</em>' attribute.
	 * @see #getNumberRaw()
	 * @generated
	 */
	void setNumberRaw(Integer value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isWildcard = this.isWildcard();\nif (_isWildcard)\n{\n\treturn null;\n}\nreturn this.getNumberRaw();'"
	 * @generated
	 */
	Integer getNumber();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" objUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((!(obj instanceof &lt;%org.eclipse.n4js.semver.SEMVER.VersionPart%&gt;)))\n{\n\treturn false;\n}\nfinal &lt;%org.eclipse.n4js.semver.SEMVER.VersionPart%&gt; vp = ((&lt;%org.eclipse.n4js.semver.SEMVER.VersionPart%&gt;) obj);\nboolean equals = ((this.isWildcard() == vp.isWildcard()) &amp;&amp; &lt;%com.google.common.base.Objects%&gt;.equal(this.getNumberRaw(), vp.getNumberRaw()));\nreturn equals;'"
	 * @generated
	 */
	boolean equals(Object obj);

} // VersionPart
