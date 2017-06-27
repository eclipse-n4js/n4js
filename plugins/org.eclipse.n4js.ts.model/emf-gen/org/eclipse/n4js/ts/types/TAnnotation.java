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
package org.eclipse.n4js.ts.types;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TAnnotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TAnnotation#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TAnnotation#getArgs <em>Args</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTAnnotation()
 * @model
 * @generated
 */
public interface TAnnotation extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTAnnotation_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TAnnotation#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Args</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TAnnotationArgument}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Args</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Args</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTAnnotation_Args()
	 * @model containment="true"
	 * @generated
	 */
	EList<TAnnotationArgument> getArgs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if an argument of type TAnnotatonStringArgument exists with given value.
	 * <!-- end-model-doc -->
	 * @model unique="false" argumentValueUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TAnnotationArgument%>> _args = this.getArgs();\n<%java.lang.Iterable%><<%org.eclipse.n4js.ts.types.TAnnotationStringArgument%>> _filter = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.ts.types.TAnnotationStringArgument%>>filter(_args, <%org.eclipse.n4js.ts.types.TAnnotationStringArgument%>.class);\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TAnnotationStringArgument%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TAnnotationStringArgument%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.ts.types.TAnnotationStringArgument%> it)\n\t{\n\t\t<%java.lang.String%> _value = it.getValue();\n\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_value, argumentValue));\n\t}\n};\nreturn <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.ts.types.TAnnotationStringArgument%>>exists(_filter, _function);'"
	 * @generated
	 */
	boolean hasStringArgument(String argumentValue);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%java.lang.StringBuilder%> strb = new <%java.lang.StringBuilder%>();\nstrb.append(\"@\");\n<%java.lang.String%> _name = this.getName();\nstrb.append(_name);\n<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TAnnotationArgument%>> _args = this.getArgs();\nint _length = ((<%java.lang.Object%>[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(_args, <%java.lang.Object%>.class)).length;\nboolean _greaterThan = (_length > 0);\nif (_greaterThan)\n{\n\tstrb.append(\"(\");\n\tfor (int i = 0; (i < ((<%java.lang.Object%>[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(this.getArgs(), <%java.lang.Object%>.class)).length); i++)\n\t{\n\t\t{\n\t\t\tif ((i > 0))\n\t\t\t{\n\t\t\t\tstrb.append(\", \");\n\t\t\t}\n\t\t\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TAnnotationArgument%>> _args_1 = this.getArgs();\n\t\t\t<%org.eclipse.n4js.ts.types.TAnnotationArgument%> _get = _args_1.get(i);\n\t\t\t<%java.lang.String%> _argAsString = _get.getArgAsString();\n\t\t\tstrb.append(_argAsString);\n\t\t}\n\t}\n\tstrb.append(\")\");\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String getAnnotationAsString();

} // TAnnotation
