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
package org.eclipse.n4js.n4mf;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source Fragment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * List the in the project contained generation aware source folders categorized by their later usage.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.SourceFragment#getSourceFragmentType <em>Source Fragment Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.SourceFragment#getPaths <em>Paths</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSourceFragment()
 * @model
 * @generated
 */
public interface SourceFragment extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Fragment Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4mf.SourceFragmentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Fragment Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Fragment Type</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.SourceFragmentType
	 * @see #setSourceFragmentType(SourceFragmentType)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSourceFragment_SourceFragmentType()
	 * @model unique="false"
	 * @generated
	 */
	SourceFragmentType getSourceFragmentType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.SourceFragment#getSourceFragmentType <em>Source Fragment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Fragment Type</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.SourceFragmentType
	 * @see #getSourceFragmentType()
	 * @generated
	 */
	void setSourceFragmentType(SourceFragmentType value);

	/**
	 * Returns the value of the '<em><b>Paths</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paths</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paths</em>' attribute list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSourceFragment_Paths()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getPaths();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Compares the current source fragment instance with the {@code other} one based on the
	 * natural ordering of the wrapped {@link SourceFragmentType fragment type}. The semantic of the
	 * return value of this compare method is identical to the specification of the {@link Comparable}
	 * interface.
	 * <!-- end-model-doc -->
	 * @model unique="false" otherUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((null == other))\n{\n\treturn (-1);\n}\n&lt;%org.eclipse.n4js.n4mf.SourceFragmentType%&gt; _sourceFragmentType = this.getSourceFragmentType();\nboolean _tripleEquals = (null == _sourceFragmentType);\nif (_tripleEquals)\n{\n\tint _xifexpression = (int) 0;\n\t&lt;%org.eclipse.n4js.n4mf.SourceFragmentType%&gt; _sourceFragmentType_1 = other.getSourceFragmentType();\n\tboolean _tripleEquals_1 = (null == _sourceFragmentType_1);\n\tif (_tripleEquals_1)\n\t{\n\t\t_xifexpression = 0;\n\t}\n\telse\n\t{\n\t\t_xifexpression = 1;\n\t}\n\treturn _xifexpression;\n}\nreturn this.getSourceFragmentType().compareTo(other.getSourceFragmentType());'"
	 * @generated
	 */
	int compareByFragmentType(SourceFragment other);

} // SourceFragment
