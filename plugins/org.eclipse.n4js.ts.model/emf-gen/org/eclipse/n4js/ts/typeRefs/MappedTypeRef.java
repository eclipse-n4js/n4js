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
package org.eclipse.n4js.ts.typeRefs;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapped Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  Only allowed in DTS.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#isIncludeReadonly <em>Include Readonly</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#isExcludeReadonly <em>Exclude Readonly</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#isIncludeOptional <em>Include Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#isExcludeOptional <em>Exclude Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#getPropName <em>Prop Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#getPropNameTypeRef <em>Prop Name Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#getTemplateTypeRef <em>Template Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getMappedTypeRef()
 * @model
 * @generated
 */
public interface MappedTypeRef extends TypeRef {
	/**
	 * Returns the value of the '<em><b>Include Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include Readonly</em>' attribute.
	 * @see #setIncludeReadonly(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getMappedTypeRef_IncludeReadonly()
	 * @model unique="false"
	 * @generated
	 */
	boolean isIncludeReadonly();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#isIncludeReadonly <em>Include Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Include Readonly</em>' attribute.
	 * @see #isIncludeReadonly()
	 * @generated
	 */
	void setIncludeReadonly(boolean value);

	/**
	 * Returns the value of the '<em><b>Exclude Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Readonly</em>' attribute.
	 * @see #setExcludeReadonly(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getMappedTypeRef_ExcludeReadonly()
	 * @model unique="false"
	 * @generated
	 */
	boolean isExcludeReadonly();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#isExcludeReadonly <em>Exclude Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Readonly</em>' attribute.
	 * @see #isExcludeReadonly()
	 * @generated
	 */
	void setExcludeReadonly(boolean value);

	/**
	 * Returns the value of the '<em><b>Include Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include Optional</em>' attribute.
	 * @see #setIncludeOptional(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getMappedTypeRef_IncludeOptional()
	 * @model unique="false"
	 * @generated
	 */
	boolean isIncludeOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#isIncludeOptional <em>Include Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Include Optional</em>' attribute.
	 * @see #isIncludeOptional()
	 * @generated
	 */
	void setIncludeOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Exclude Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Optional</em>' attribute.
	 * @see #setExcludeOptional(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getMappedTypeRef_ExcludeOptional()
	 * @model unique="false"
	 * @generated
	 */
	boolean isExcludeOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#isExcludeOptional <em>Exclude Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Optional</em>' attribute.
	 * @see #isExcludeOptional()
	 * @generated
	 */
	void setExcludeOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Prop Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prop Name</em>' attribute.
	 * @see #setPropName(String)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getMappedTypeRef_PropName()
	 * @model unique="false"
	 * @generated
	 */
	String getPropName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#getPropName <em>Prop Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prop Name</em>' attribute.
	 * @see #getPropName()
	 * @generated
	 */
	void setPropName(String value);

	/**
	 * Returns the value of the '<em><b>Prop Name Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prop Name Type Ref</em>' containment reference.
	 * @see #setPropNameTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getMappedTypeRef_PropNameTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getPropNameTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#getPropNameTypeRef <em>Prop Name Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prop Name Type Ref</em>' containment reference.
	 * @see #getPropNameTypeRef()
	 * @generated
	 */
	void setPropNameTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Template Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template Type Ref</em>' containment reference.
	 * @see #setTemplateTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getMappedTypeRef_TemplateTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getTemplateTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.MappedTypeRef#getTemplateTypeRef <em>Template Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Type Ref</em>' containment reference.
	 * @see #getTemplateTypeRef()
	 * @generated
	 */
	void setTemplateTypeRef(TypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

} // MappedTypeRef
