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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Version Number</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMajor <em>Major</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMinor <em>Minor</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getPatch <em>Patch</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getExtended <em>Extended</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber()
 * @model
 * @generated
 */
public interface VersionNumber extends SemverToStringable {
	/**
	 * Returns the value of the '<em><b>Major</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Major</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Major</em>' containment reference.
	 * @see #setMajor(VersionPart)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Major()
	 * @model containment="true"
	 * @generated
	 */
	VersionPart getMajor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMajor <em>Major</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Major</em>' containment reference.
	 * @see #getMajor()
	 * @generated
	 */
	void setMajor(VersionPart value);

	/**
	 * Returns the value of the '<em><b>Minor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minor</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minor</em>' containment reference.
	 * @see #setMinor(VersionPart)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Minor()
	 * @model containment="true"
	 * @generated
	 */
	VersionPart getMinor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMinor <em>Minor</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minor</em>' containment reference.
	 * @see #getMinor()
	 * @generated
	 */
	void setMinor(VersionPart value);

	/**
	 * Returns the value of the '<em><b>Patch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Patch</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patch</em>' containment reference.
	 * @see #setPatch(VersionPart)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Patch()
	 * @model containment="true"
	 * @generated
	 */
	VersionPart getPatch();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getPatch <em>Patch</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Patch</em>' containment reference.
	 * @see #getPatch()
	 * @generated
	 */
	void setPatch(VersionPart value);

	/**
	 * Returns the value of the '<em><b>Extended</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.semver.Semver.VersionPart}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended</em>' containment reference list.
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Extended()
	 * @model containment="true"
	 * @generated
	 */
	EList<VersionPart> getExtended();

	/**
	 * Returns the value of the '<em><b>Qualifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualifier</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualifier</em>' containment reference.
	 * @see #setQualifier(Qualifier)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Qualifier()
	 * @model containment="true"
	 * @generated
	 */
	Qualifier getQualifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getQualifier <em>Qualifier</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualifier</em>' containment reference.
	 * @see #getQualifier()
	 * @generated
	 */
	void setQualifier(Qualifier value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> * @return true if the major part is a
	 * wildcard <!-- end-model-doc -->
	 * 
	 * @model kind="operation" unique="false" annotation="http://www.eclipse.org/emf/2002/GenModel body='return
	 *        this.getMajor().isWildcard();'"
	 * @generated
	 */
	boolean isWildcard();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%java.lang.String%&gt;&gt; _xifexpression = null;\nif (((this.getQualifier() != null) &amp;&amp; (this.getQualifier().getPreRelease() != null)))\n{\n\t_xifexpression = this.getQualifier().getPreRelease().getParts();\n}\nelse\n{\n\t_xifexpression = null;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	EList<String> getPreReleaseTag();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getPreReleaseTag() != null) &amp;&amp; (!this.getPreReleaseTag().isEmpty()));'"
	 * @generated
	 */
	boolean hasPreReleaseTag();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='int length = 0;\n&lt;%org.eclipse.n4js.semver.Semver.VersionPart%&gt; _major = this.getMajor();\nboolean _tripleNotEquals = (_major != null);\nif (_tripleNotEquals)\n{\n\tlength++;\n}\n&lt;%org.eclipse.n4js.semver.Semver.VersionPart%&gt; _minor = this.getMinor();\nboolean _tripleNotEquals_1 = (_minor != null);\nif (_tripleNotEquals_1)\n{\n\tlength++;\n}\n&lt;%org.eclipse.n4js.semver.Semver.VersionPart%&gt; _patch = this.getPatch();\nboolean _tripleNotEquals_2 = (_patch != null);\nif (_tripleNotEquals_2)\n{\n\tlength++;\n\tint _length = length;\n\tint _length_1 = ((&lt;%java.lang.Object%&gt;[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(this.getExtended(), &lt;%java.lang.Object%&gt;.class)).length;\n\tlength = (_length + _length_1);\n}\nreturn length;'"
	 * @generated
	 */
	int length();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model unique="false" idxUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='switch (idx)\n{\n\tcase 0:\n\t\treturn this.getMajor();\n\tcase 1:\n\t\treturn this.getMinor();\n\tcase 2:\n\t\treturn this.getPatch();\n\tdefault:\n\t\treturn this.getExtended().get((idx - 3));\n}'"
	 * @generated
	 */
	VersionPart getPart(int idx);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model unique="false" objUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((!(obj instanceof &lt;%org.eclipse.n4js.semver.Semver.VersionNumber%&gt;)))\n{\n\treturn false;\n}\nfinal &lt;%org.eclipse.n4js.semver.Semver.VersionNumber%&gt; vn = ((&lt;%org.eclipse.n4js.semver.Semver.VersionNumber%&gt;) obj);\nboolean equals = true;\nint _length = this.length();\nint _length_1 = vn.length();\nboolean _equals = (_length == _length_1);\nequals = _equals;\nfor (int i = 0; (i &lt; this.length()); i++)\n{\n\tequals = (equals &amp;&amp; this.getPart(i).equals(vn.getPart(i)));\n}\nequals = (equals &amp;&amp; (&lt;%java.lang.Boolean%&gt;.valueOf((this.getQualifier() == null)) == &lt;%java.lang.Boolean%&gt;.valueOf((vn.getQualifier() == null))));\nequals = (equals &amp;&amp; ((this.getQualifier() == null) || this.getQualifier().equals(vn.getQualifier())));\nreturn equals;'"
	 * @generated
	 */
	@Override
	boolean equals(Object obj);

} // VersionNumber
