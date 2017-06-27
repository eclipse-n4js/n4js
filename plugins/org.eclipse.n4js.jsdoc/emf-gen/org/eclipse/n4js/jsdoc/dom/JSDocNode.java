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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.jsdoc.dom.Marker%>> _markers = this.getMarkers();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.Marker%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.Marker%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.jsdoc.dom.Marker%> it)\n\t{\n\t\t<%java.lang.String%> _key = it.getKey();\n\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_key, theKey));\n\t}\n};\n<%org.eclipse.n4js.jsdoc.dom.Marker%> _findFirst = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.jsdoc.dom.Marker%>>findFirst(_markers, _function);\n<%java.lang.String%> _value = null;\nif (_findFirst!=null)\n{\n\t_value=_findFirst.getValue();\n}\nreturn _value;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.jsdoc.dom.Marker%>> _markers = this.getMarkers();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.Marker%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.Marker%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.jsdoc.dom.Marker%> it)\n\t{\n\t\t<%java.lang.String%> _key = it.getKey();\n\t\t<%java.lang.String%> _key_1 = it.getKey();\n\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_key, _key_1));\n\t}\n};\n<%org.eclipse.n4js.jsdoc.dom.Marker%> marker = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.jsdoc.dom.Marker%>>findFirst(_markers, _function);\nboolean _equals = <%com.google.common.base.Objects%>.equal(marker, null);\nif (_equals)\n{\n\t<%org.eclipse.n4js.jsdoc.dom.Marker%> _createMarker = <%org.eclipse.n4js.jsdoc.dom.DomFactory%>.eINSTANCE.createMarker();\n\tmarker = _createMarker;\n\tmarker.setKey(theKey);\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.jsdoc.dom.Marker%>> _markers_1 = this.getMarkers();\n\t_markers_1.add(marker);\n}\nmarker.setValue(value);'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.jsdoc.dom.Marker%>> _markers = this.getMarkers();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.Marker%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.Marker%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.jsdoc.dom.Marker%> it)\n\t{\n\t\treturn <%java.lang.Boolean%>.valueOf((<%com.google.common.base.Objects%>.equal(it.getKey(), theKey) && <%com.google.common.base.Objects%>.equal(it.getValue(), theValue)));\n\t}\n};\n<%org.eclipse.n4js.jsdoc.dom.Marker%> _findFirst = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.jsdoc.dom.Marker%>>findFirst(_markers, _function);\nreturn (_findFirst != null);'"
	 * @generated
	 */
	boolean isMarkedAs(String theKey, String theValue);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%org.eclipse.n4js.jsdoc.JSDocSerializer%>.toJSDocString(this);'"
	 * @generated
	 */
	String toString();

} // JSDocNode
