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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TMigration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Represents an N4IDL migration declaration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#getSourceVersion <em>Source Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#getTargetVersion <em>Target Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#getSourceTypeRefs <em>Source Type Refs</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#getTargetTypeRefs <em>Target Type Refs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration()
 * @model
 * @generated
 */
public interface TMigration extends TFunction {
	/**
	 * Returns the value of the '<em><b>Source Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Version</em>' attribute.
	 * @see #setSourceVersion(int)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration_SourceVersion()
	 * @model unique="false"
	 * @generated
	 */
	int getSourceVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMigration#getSourceVersion <em>Source Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Version</em>' attribute.
	 * @see #getSourceVersion()
	 * @generated
	 */
	void setSourceVersion(int value);

	/**
	 * Returns the value of the '<em><b>Target Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Version</em>' attribute.
	 * @see #setTargetVersion(int)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration_TargetVersion()
	 * @model unique="false"
	 * @generated
	 */
	int getTargetVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMigration#getTargetVersion <em>Target Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Version</em>' attribute.
	 * @see #getTargetVersion()
	 * @generated
	 */
	void setTargetVersion(int value);

	/**
	 * Returns the value of the '<em><b>Source Type Refs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.typeRefs.TypeRef}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the source TypeRefs of this migration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Type Refs</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration_SourceTypeRefs()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='final <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TFormalParameter%>, <%org.eclipse.n4js.ts.typeRefs.TypeRef%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TFormalParameter%>, <%org.eclipse.n4js.ts.typeRefs.TypeRef%>>()\n{\n\tpublic <%org.eclipse.n4js.ts.typeRefs.TypeRef%> apply(final <%org.eclipse.n4js.ts.types.TFormalParameter%> p)\n\t{\n\t\treturn p.getTypeRef();\n\t}\n};\nreturn <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.ts.types.TFormalParameter%>, <%org.eclipse.n4js.ts.typeRefs.TypeRef%>>map(this.getFpars(), _function);'"
	 * @generated
	 */
	EList<TypeRef> getSourceTypeRefs();

	/**
	 * Returns the value of the '<em><b>Target Type Refs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.typeRefs.TypeRef}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the target TypeRefs of this migration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target Type Refs</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration_TargetTypeRefs()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='final <%org.eclipse.n4js.ts.typeRefs.TypeRef%> returnTypeRef = this.getReturnTypeRef();\nif ((returnTypeRef == null))\n{\n\treturn <%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%>.<<%org.eclipse.n4js.ts.typeRefs.TypeRef%>>emptyEList();\n}\nif ((returnTypeRef instanceof <%org.eclipse.n4js.ts.typeRefs.StructuralTypeRef%>))\n{\n\tfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TStructField%>, <%org.eclipse.n4js.ts.typeRefs.TypeRef%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TStructField%>, <%org.eclipse.n4js.ts.typeRefs.TypeRef%>>()\n\t{\n\t\tpublic <%org.eclipse.n4js.ts.typeRefs.TypeRef%> apply(final <%org.eclipse.n4js.ts.types.TStructField%> f)\n\t\t{\n\t\t\treturn f.getTypeRef();\n\t\t}\n\t};\n\treturn <%org.eclipse.emf.common.util.ECollections%>.<<%org.eclipse.n4js.ts.typeRefs.TypeRef%>>toEList(<%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.ts.types.TStructField%>, <%org.eclipse.n4js.ts.typeRefs.TypeRef%>>map(<%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.ts.types.TStructField%>>filter(this.getReturnTypeRef().getStructuralMembers(), <%org.eclipse.n4js.ts.types.TStructField%>.class), _function));\n}\nelse\n{\n\treturn <%org.eclipse.emf.common.util.ECollections%>.<<%org.eclipse.n4js.ts.typeRefs.TypeRef%>>singletonEList(this.getReturnTypeRef());\n}'"
	 * @generated
	 */
	EList<TypeRef> getTargetTypeRefs();

} // TMigration
