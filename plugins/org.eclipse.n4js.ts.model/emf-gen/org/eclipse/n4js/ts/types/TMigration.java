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
 * Represents an N4IDL migration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#getSourceVersion <em>Source Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#getTargetVersion <em>Target Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#isHasDeclaredSourceAndTargetVersion <em>Has Declared Source And Target Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#getSourceTypeRefs <em>Source Type Refs</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#getTargetTypeRefs <em>Target Type Refs</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigration#get_principalArgumentType <em>principal Argument Type</em>}</li>
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
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The source model version of this migration.
	 * <!-- end-model-doc -->
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
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The target model version of this migration.
	 * <!-- end-model-doc -->
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
	 * Returns the value of the '<em><b>Has Declared Source And Target Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns {@code true} iff migration has an explicitly declared
	 * source and target version.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Has Declared Source And Target Version</em>' attribute.
	 * @see #setHasDeclaredSourceAndTargetVersion(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration_HasDeclaredSourceAndTargetVersion()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasDeclaredSourceAndTargetVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMigration#isHasDeclaredSourceAndTargetVersion <em>Has Declared Source And Target Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Declared Source And Target Version</em>' attribute.
	 * @see #isHasDeclaredSourceAndTargetVersion()
	 * @generated
	 */
	void setHasDeclaredSourceAndTargetVersion(boolean value);

	/**
	 * Returns the value of the '<em><b>Source Type Refs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.typeRefs.TypeRef}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The source TypeRefs of this migration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Type Refs</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration_SourceTypeRefs()
	 * @model
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
	 * The target TypeRefs of this migration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target Type Refs</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration_TargetTypeRefs()
	 * @model
	 * @generated
	 */
	EList<TypeRef> getTargetTypeRefs();

	/**
	 * Returns the value of the '<em><b>principal Argument Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Transient principal argument type. Access through #getPrincipalArgumentType()
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>principal Argument Type</em>' reference.
	 * @see #set_principalArgumentType(TMigratable)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigration__principalArgumentType()
	 * @model transient="true"
	 * @generated
	 */
	TMigratable get_principalArgumentType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMigration#get_principalArgumentType <em>principal Argument Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>principal Argument Type</em>' reference.
	 * @see #get_principalArgumentType()
	 * @generated
	 */
	void set_principalArgumentType(TMigratable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The principal argument of this migration.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TMigratable%&gt; __principalArgumentType = this.get_principalArgumentType();\nboolean _tripleEquals = (__principalArgumentType == null);\nif (_tripleEquals)\n{\n\tfinal &lt;%org.eclipse.xtext.xbase.lib.Procedures.Procedure0%&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Procedures.Procedure0%&gt;()\n\t{\n\t\tpublic void apply()\n\t\t{\n\t\t\t&lt;%this%&gt;.set_principalArgumentType(&lt;%org.eclipse.n4js.ts.versions.MigratableUtils%&gt;.findPrincipalMigrationArgument(&lt;%this%&gt;.getSourceTypeRefs()).orElse(null));\n\t\t}\n\t};\n\t&lt;%org.eclipse.n4js.utils.EcoreUtilN4%&gt;.doWithDeliver(false, _function, this);\n}\nreturn this.get_principalArgumentType();'"
	 * @generated
	 */
	TMigratable getPrincipalArgumentType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns string representation of this migration.
	 * 
	 * This includes source types and target types.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt;, &lt;%java.lang.String%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt;, &lt;%java.lang.String%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt; t)\n\t{\n\t\treturn t.getTypeRefAsString();\n\t}\n};\nfinal &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt;, &lt;%java.lang.String%&gt;&gt; _function_1 = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt;, &lt;%java.lang.String%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt; t)\n\t{\n\t\treturn t.getTypeRefAsString();\n\t}\n};\nreturn &lt;%java.lang.String%&gt;.format(\"%s (%s) =&gt; (%s)\", \n\tthis.getName(), \n\t&lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getSourceTypeRefs(), _function), \", \"), \n\t&lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getTargetTypeRefs(), _function_1), \", \"));'"
	 * @generated
	 */
	String getMigrationAsString();

} // TMigration
