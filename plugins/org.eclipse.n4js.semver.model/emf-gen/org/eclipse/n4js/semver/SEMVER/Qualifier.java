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
 * A representation of the model object '<em><b>Qualifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getPreRelease <em>Pre Release</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getBuildMetadata <em>Build Metadata</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getQualifier()
 * @model
 * @generated
 */
public interface Qualifier extends EObject {
	/**
	 * Returns the value of the '<em><b>Pre Release</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Release</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Release</em>' containment reference.
	 * @see #setPreRelease(QualifierTag)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getQualifier_PreRelease()
	 * @model containment="true"
	 * @generated
	 */
	QualifierTag getPreRelease();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getPreRelease <em>Pre Release</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Release</em>' containment reference.
	 * @see #getPreRelease()
	 * @generated
	 */
	void setPreRelease(QualifierTag value);

	/**
	 * Returns the value of the '<em><b>Build Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Metadata</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Metadata</em>' containment reference.
	 * @see #setBuildMetadata(QualifierTag)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getQualifier_BuildMetadata()
	 * @model containment="true"
	 * @generated
	 */
	QualifierTag getBuildMetadata();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getBuildMetadata <em>Build Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Metadata</em>' containment reference.
	 * @see #getBuildMetadata()
	 * @generated
	 */
	void setBuildMetadata(QualifierTag value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" objUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((!(obj instanceof &lt;%org.eclipse.n4js.semver.SEMVER.Qualifier%&gt;)))\n{\n\treturn false;\n}\nfinal &lt;%org.eclipse.n4js.semver.SEMVER.Qualifier%&gt; q = ((&lt;%org.eclipse.n4js.semver.SEMVER.Qualifier%&gt;) obj);\nboolean equals = (this.getPreRelease().equals(q.getPreRelease()) &amp;&amp; this.getBuildMetadata().equals(q.getBuildMetadata()));\nreturn equals;'"
	 * @generated
	 */
	boolean equals(Object obj);

} // Qualifier
