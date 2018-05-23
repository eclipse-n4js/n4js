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
 * A representation of the model object '<em><b>Source Container Description</b></em>'.
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
 *   <li>{@link org.eclipse.n4js.n4mf.SourceContainerDescription#getSourceContainerType <em>Source Container Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.SourceContainerDescription#getPathsRaw <em>Paths Raw</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSourceContainerDescription()
 * @model
 * @generated
 */
public interface SourceContainerDescription extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Container Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4mf.SourceContainerType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Container Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Container Type</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.SourceContainerType
	 * @see #setSourceContainerType(SourceContainerType)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSourceContainerDescription_SourceContainerType()
	 * @model unique="false"
	 * @generated
	 */
	SourceContainerType getSourceContainerType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.SourceContainerDescription#getSourceContainerType <em>Source Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Container Type</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.SourceContainerType
	 * @see #getSourceContainerType()
	 * @generated
	 */
	void setSourceContainerType(SourceContainerType value);

	/**
	 * Returns the value of the '<em><b>Paths Raw</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paths Raw</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paths Raw</em>' attribute list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSourceContainerDescription_PathsRaw()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getPathsRaw();

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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((null == other))\n{\n\treturn (-1);\n}\n&lt;%org.eclipse.n4js.n4mf.SourceContainerType%&gt; _sourceContainerType = this.getSourceContainerType();\nboolean _tripleEquals = (null == _sourceContainerType);\nif (_tripleEquals)\n{\n\tint _xifexpression = (int) 0;\n\t&lt;%org.eclipse.n4js.n4mf.SourceContainerType%&gt; _sourceContainerType_1 = other.getSourceContainerType();\n\tboolean _tripleEquals_1 = (null == _sourceContainerType_1);\n\tif (_tripleEquals_1)\n\t{\n\t\t_xifexpression = 0;\n\t}\n\telse\n\t{\n\t\t_xifexpression = 1;\n\t}\n\treturn _xifexpression;\n}\nreturn this.getSourceContainerType().compareTo(other.getSourceContainerType());'"
	 * @generated
	 */
	int compareByFragmentType(SourceContainerDescription other);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='int _length = ((&lt;%java.lang.Object%&gt;[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(this.getPathsRaw(), &lt;%java.lang.Object%&gt;.class)).length;\nfinal &lt;%org.eclipse.emf.common.util.BasicEList%&gt;&lt;&lt;%java.lang.String%&gt;&gt; paths = new &lt;%org.eclipse.emf.common.util.BasicEList%&gt;&lt;&lt;%java.lang.String%&gt;&gt;(_length);\n&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%java.lang.String%&gt;&gt; _pathsRaw = this.getPathsRaw();\nfor (final &lt;%java.lang.String%&gt; pathRaw : _pathsRaw)\n{\n\t{\n\t\tfinal &lt;%java.lang.String%&gt; normalizedPath = &lt;%org.eclipse.n4js.utils.io.FileUtils%&gt;.normalizeDotWhenEmpty(pathRaw);\n\t\tpaths.add(normalizedPath);\n\t}\n}\nreturn paths;'"
	 * @generated
	 */
	EList<String> getPaths();

} // SourceContainerDescription
