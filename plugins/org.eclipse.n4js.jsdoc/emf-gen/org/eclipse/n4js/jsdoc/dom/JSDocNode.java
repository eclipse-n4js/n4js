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
package org.eclipse.n4js.jsdoc.dom;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JS Doc Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.JSDocNode#getMarkers <em>Markers</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getJSDocNode()
 * @model abstract="true"
 * @generated
 */
public interface JSDocNode extends DocletElement {
	/**
	 * Returns the value of the '<em><b>Markers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.jsdoc.dom.Marker}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Markers, should not be accessed directly, instead, getMarkerValue and setMarker should be used.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Markers</em>' containment reference list.
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getJSDocNode_Markers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Marker> getMarkers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the value of the first marker with the given key, or null, if no such marker is found.
	 * <!-- end-model-doc -->
	 * @model unique="false" theKeyUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;, &lt;%java.lang.Boolean%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt; it)\n\t{\n\t\t&lt;%java.lang.String%&gt; _key = it.getKey();\n\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf(&lt;%com.google.common.base.Objects%&gt;.equal(_key, theKey));\n\t}\n};\n&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt; _findFirst = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;&gt;findFirst(this.getMarkers(), _function);\n&lt;%java.lang.String%&gt; _value = null;\nif (_findFirst!=null)\n{\n\t_value=_findFirst.getValue();\n}\nreturn _value;'"
	 * @generated
	 */
	String getMarkerValue(String theKey);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Sets the value of the marker with given key, if no such marker has been defined, it is added.
	 * <!-- end-model-doc -->
	 * @model theKeyUnique="false" valueUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;, &lt;%java.lang.Boolean%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt; it)\n\t{\n\t\t&lt;%java.lang.String%&gt; _key = it.getKey();\n\t\t&lt;%java.lang.String%&gt; _key_1 = it.getKey();\n\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf(&lt;%com.google.common.base.Objects%&gt;.equal(_key, _key_1));\n\t}\n};\n&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt; marker = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;&gt;findFirst(this.getMarkers(), _function);\nif ((marker == null))\n{\n\tmarker = &lt;%org.eclipse.n4js.jsdoc.dom.DomFactory%&gt;.eINSTANCE.createMarker();\n\tmarker.setKey(theKey);\n\tthis.getMarkers().add(marker);\n}\nmarker.setValue(value);'"
	 * @generated
	 */
	void setMarker(String theKey, String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if node contains given marker with the given value
	 * <!-- end-model-doc -->
	 * @model unique="false" theKeyUnique="false" theValueUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;, &lt;%java.lang.Boolean%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt; it)\n\t{\n\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf((&lt;%com.google.common.base.Objects%&gt;.equal(it.getKey(), theKey) &amp;&amp; &lt;%com.google.common.base.Objects%&gt;.equal(it.getValue(), theValue)));\n\t}\n};\n&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt; _findFirst = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.jsdoc.dom.Marker%&gt;&gt;findFirst(this.getMarkers(), _function);\nreturn (_findFirst != null);'"
	 * @generated
	 */
	boolean isMarkedAs(String theKey, String theValue);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.jsdoc.JSDocSerializer%&gt;.toJSDocString(this);'"
	 * @generated
	 */
	String toString();

} // JSDocNode
