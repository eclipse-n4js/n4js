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
 * A representation of the model object '<em><b>Function Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Function declarations should only be contained in the script or functions, that is, they should not be nested in blocks.
 * This is ensured by the ASTStructureValidator.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionDeclaration#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionDeclaration#get_migrationContext <em>migration Context</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDeclaration()
 * @model
 * @generated
 */
public interface FunctionDeclaration extends AnnotableScriptElement, ModifiableElement, Statement, FunctionDefinition, GenericDeclaration, ExportableElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Function declarations are not treated as identifiable elements, as the binding should
	 * refer to the inferred TFunction rather than this declaration. The name is mandatory.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDeclaration_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionDeclaration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>migration Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Transient {@code MigrationContext} arguments variable. Access through #getMigrationContextVariable()
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>migration Context</em>' containment reference.
	 * @see #set_migrationContext(MigrationContextVariable)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDeclaration__migrationContext()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	MigrationContextVariable get_migrationContext();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionDeclaration#get_migrationContext <em>migration Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>migration Context</em>' containment reference.
	 * @see #get_migrationContext()
	 * @generated
	 */
	void set_migrationContext(MigrationContextVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if type is declared as external.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isExternal();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Lazy initialized reference to transient localArgurmentsVariable
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	MigrationContextVariable getMigrationContextVariable();

} // FunctionDeclaration
