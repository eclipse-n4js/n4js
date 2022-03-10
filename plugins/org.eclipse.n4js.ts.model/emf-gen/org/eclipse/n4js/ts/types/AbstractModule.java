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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractModule#getSimpleName <em>Simple Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractModule#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractModule#getModuleSpecifier <em>Module Specifier</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractModule()
 * @model abstract="true"
 * @generated
 */
public interface AbstractModule extends AbstractNamespace, SyntaxRelatedTElement {
	/**
	 * Returns the value of the '<em><b>Simple Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The module's simple name, i.e. the last segment of its qualified name.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Simple Name</em>' attribute.
	 * @see #setSimpleName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractModule_SimpleName()
	 * @model unique="false"
	 * @generated
	 */
	String getSimpleName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.AbstractModule#getSimpleName <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Simple Name</em>' attribute.
	 * @see #getSimpleName()
	 * @generated
	 */
	void setSimpleName(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The qualified name of the module, which is derived from the file path. Includes the module's
	 * file name (without extension) and the names of all ancestor folders up to, excluding the
	 * containing project's source folder. The containing project's name is also not included,
	 * but can be retrieved via {@link #getPackageName()}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractModule_QualifiedName()
	 * @model unique="false"
	 * @generated
	 */
	String getQualifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.AbstractModule#getQualifiedName <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
	void setQualifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Module Specifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns this module's module specifier as it would appear, for example, in the string literal at the end of an
	 * import statement.
	 * <p>
	 * Since we changed the delimiter for our internal qualified names from '.' to '/', this simply returns the same
	 * value as {@link #getQualifiedName()}. However, this getter is retained for the time being to let client code
	 * differentiate between internal use (qualified name, e.g. in the Xtext index) and Javascript context (module
	 * specifier, e.g. in import statements).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Module Specifier</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractModule_ModuleSpecifier()
	 * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getModuleSpecifier();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isStaticPolyfillModule();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isStaticPolyfillAware();

} // AbstractModule