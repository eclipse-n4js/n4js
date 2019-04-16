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
package org.eclipse.n4js.n4JS;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tagged Template String</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * An invocation of the form
 * {@code method `template`}
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.TaggedTemplateString#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.TaggedTemplateString#getTemplate <em>Template</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTaggedTemplateString()
 * @model
 * @generated
 */
public interface TaggedTemplateString extends Expression {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTaggedTemplateString_Target()
	 * @model containment="true"
	 * @generated
	 */
	Expression getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TaggedTemplateString#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Expression value);

	/**
	 * Returns the value of the '<em><b>Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template</em>' containment reference.
	 * @see #setTemplate(TemplateLiteral)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTaggedTemplateString_Template()
	 * @model containment="true"
	 * @generated
	 */
	TemplateLiteral getTemplate();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TaggedTemplateString#getTemplate <em>Template</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template</em>' containment reference.
	 * @see #getTemplate()
	 * @generated
	 */
	void setTemplate(TemplateLiteral value);

} // TaggedTemplateString
