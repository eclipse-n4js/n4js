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
 * A representation of the model object '<em><b>Doclet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.Doclet#getLineTags <em>Line Tags</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getDoclet()
 * @model
 * @generated
 */
public interface Doclet extends Composite {
	/**
	 * Returns the value of the '<em><b>Line Tags</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.jsdoc.dom.LineTag}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.jsdoc.dom.LineTag#getDoclet <em>Doclet</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Tags</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Tags</em>' containment reference list.
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getDoclet_LineTags()
	 * @see org.eclipse.n4js.jsdoc.dom.LineTag#getDoclet
	 * @model opposite="doclet" containment="true"
	 * @generated
	 */
	EList<LineTag> getLineTags();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" titleUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.jsdoc.dom.LineTag%>> _lineTags = this.getLineTags();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.LineTag%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.LineTag%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.jsdoc.dom.LineTag%> it)\n\t{\n\t\t<%org.eclipse.n4js.jsdoc.dom.TagTitle%> _title = it.getTitle();\n\t\t<%java.lang.String%> _title_1 = _title.getTitle();\n\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_title_1, title));\n\t}\n};\n<%org.eclipse.n4js.jsdoc.dom.LineTag%> _findFirst = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.jsdoc.dom.LineTag%>>findFirst(_lineTags, _function);\nreturn (_findFirst != null);'"
	 * @generated
	 */
	boolean hasLineTag(String title);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" titleUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.jsdoc.dom.LineTag%>> _lineTags = this.getLineTags();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.LineTag%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.jsdoc.dom.LineTag%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.jsdoc.dom.LineTag%> it)\n\t{\n\t\t<%org.eclipse.n4js.jsdoc.dom.TagTitle%> _title = it.getTitle();\n\t\t<%java.lang.String%> _title_1 = _title.getTitle();\n\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_title_1, title));\n\t}\n};\n<%java.lang.Iterable%><<%org.eclipse.n4js.jsdoc.dom.LineTag%>> _filter = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.jsdoc.dom.LineTag%>>filter(_lineTags, _function);\nreturn <%org.eclipse.emf.common.util.ECollections%>.<<%org.eclipse.n4js.jsdoc.dom.LineTag%>>toEList(_filter);'"
	 * @generated
	 */
	EList<LineTag> lineTags(String title);

} // Doclet
