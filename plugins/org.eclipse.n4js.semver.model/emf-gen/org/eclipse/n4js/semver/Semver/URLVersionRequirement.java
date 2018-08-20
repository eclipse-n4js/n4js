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
package org.eclipse.n4js.semver.Semver;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>URL Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getVersionSpecifier <em>Version Specifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getUrl <em>Url</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getURLVersionRequirement()
 * @model
 * @generated
 */
public interface URLVersionRequirement extends NPMVersionRequirement {
	/**
	 * Returns the value of the '<em><b>Version Specifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Specifier</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Specifier</em>' containment reference.
	 * @see #setVersionSpecifier(URLVersionSpecifier)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getURLVersionRequirement_VersionSpecifier()
	 * @model containment="true"
	 * @generated
	 */
	URLVersionSpecifier getVersionSpecifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getVersionSpecifier <em>Version Specifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Specifier</em>' containment reference.
	 * @see #getVersionSpecifier()
	 * @generated
	 */
	void setVersionSpecifier(URLVersionSpecifier value);

	/**
	 * Returns the value of the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol</em>' attribute.
	 * @see #setProtocol(String)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getURLVersionRequirement_Protocol()
	 * @model unique="false"
	 * @generated
	 */
	String getProtocol();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getProtocol <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol</em>' attribute.
	 * @see #getProtocol()
	 * @generated
	 */
	void setProtocol(String value);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getURLVersionRequirement_Url()
	 * @model unique="false"
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.semver.Semver.SimpleVersion%&gt; _simpleVersion = this.getSimpleVersion();\nreturn (_simpleVersion != null);'"
	 * @generated
	 */
	boolean hasSimpleVersion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.semver.Semver.URLVersionSpecifier%&gt; _versionSpecifier = this.getVersionSpecifier();\nif ((_versionSpecifier instanceof &lt;%org.eclipse.n4js.semver.Semver.URLSemver%&gt;))\n{\n\t&lt;%org.eclipse.n4js.semver.Semver.URLVersionSpecifier%&gt; _versionSpecifier_1 = this.getVersionSpecifier();\n\tfinal &lt;%org.eclipse.n4js.semver.Semver.URLSemver%&gt; urlSemver = ((&lt;%org.eclipse.n4js.semver.Semver.URLSemver%&gt;) _versionSpecifier_1);\n\treturn urlSemver.getSimpleVersion();\n}\nreturn null;'"
	 * @generated
	 */
	SimpleVersion getSimpleVersion();

} // URLVersionRequirement
