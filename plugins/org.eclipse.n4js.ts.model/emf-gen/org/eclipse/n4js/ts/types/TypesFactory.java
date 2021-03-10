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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.ts.types.TypesPackage
 * @generated
 */
public interface TypesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypesFactory eINSTANCE = org.eclipse.n4js.ts.types.impl.TypesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Type Defs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Defs</em>'.
	 * @generated
	 */
	TypeDefs createTypeDefs();

	/**
	 * Returns a new object of class '<em>TModule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TModule</em>'.
	 * @generated
	 */
	TModule createTModule();

	/**
	 * Returns a new object of class '<em>Runtime Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Runtime Dependency</em>'.
	 * @generated
	 */
	RuntimeDependency createRuntimeDependency();

	/**
	 * Returns a new object of class '<em>Composed Member Cache</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composed Member Cache</em>'.
	 * @generated
	 */
	ComposedMemberCache createComposedMemberCache();

	/**
	 * Returns a new object of class '<em>Identifiable Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Identifiable Element</em>'.
	 * @generated
	 */
	IdentifiableElement createIdentifiableElement();

	/**
	 * Returns a new object of class '<em>TExportable Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TExportable Element</em>'.
	 * @generated
	 */
	TExportableElement createTExportableElement();

	/**
	 * Returns a new object of class '<em>TAnnotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TAnnotation</em>'.
	 * @generated
	 */
	TAnnotation createTAnnotation();

	/**
	 * Returns a new object of class '<em>TAnnotation String Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TAnnotation String Argument</em>'.
	 * @generated
	 */
	TAnnotationStringArgument createTAnnotationStringArgument();

	/**
	 * Returns a new object of class '<em>TAnnotation Type Ref Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TAnnotation Type Ref Argument</em>'.
	 * @generated
	 */
	TAnnotationTypeRefArgument createTAnnotationTypeRefArgument();

	/**
	 * Returns a new object of class '<em>TAnnotable Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TAnnotable Element</em>'.
	 * @generated
	 */
	TAnnotableElement createTAnnotableElement();

	/**
	 * Returns a new object of class '<em>Type Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Variable</em>'.
	 * @generated
	 */
	TypeVariable createTypeVariable();

	/**
	 * Returns a new object of class '<em>Inference Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inference Variable</em>'.
	 * @generated
	 */
	InferenceVariable createInferenceVariable();

	/**
	 * Returns a new object of class '<em>TFunction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TFunction</em>'.
	 * @generated
	 */
	TFunction createTFunction();

	/**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
	Type createType();

	/**
	 * Returns a new object of class '<em>Virtual Base Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Virtual Base Type</em>'.
	 * @generated
	 */
	VirtualBaseType createVirtualBaseType();

	/**
	 * Returns a new object of class '<em>Module Namespace Virtual Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Module Namespace Virtual Type</em>'.
	 * @generated
	 */
	ModuleNamespaceVirtualType createModuleNamespaceVirtualType();

	/**
	 * Returns a new object of class '<em>Primitive Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primitive Type</em>'.
	 * @generated
	 */
	PrimitiveType createPrimitiveType();

	/**
	 * Returns a new object of class '<em>Built In Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Built In Type</em>'.
	 * @generated
	 */
	BuiltInType createBuiltInType();

	/**
	 * Returns a new object of class '<em>Any Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Type</em>'.
	 * @generated
	 */
	AnyType createAnyType();

	/**
	 * Returns a new object of class '<em>Undefined Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Undefined Type</em>'.
	 * @generated
	 */
	UndefinedType createUndefinedType();

	/**
	 * Returns a new object of class '<em>Null Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Null Type</em>'.
	 * @generated
	 */
	NullType createNullType();

	/**
	 * Returns a new object of class '<em>Void Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Void Type</em>'.
	 * @generated
	 */
	VoidType createVoidType();

	/**
	 * Returns a new object of class '<em>TStructural Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TStructural Type</em>'.
	 * @generated
	 */
	TStructuralType createTStructuralType();

	/**
	 * Returns a new object of class '<em>TMigration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TMigration</em>'.
	 * @generated
	 */
	TMigration createTMigration();

	/**
	 * Returns a new object of class '<em>TObject Prototype</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TObject Prototype</em>'.
	 * @generated
	 */
	TObjectPrototype createTObjectPrototype();

	/**
	 * Returns a new object of class '<em>TN4 Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TN4 Classifier</em>'.
	 * @generated
	 */
	TN4Classifier createTN4Classifier();

	/**
	 * Returns a new object of class '<em>TClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TClass</em>'.
	 * @generated
	 */
	TClass createTClass();

	/**
	 * Returns a new object of class '<em>TInterface</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TInterface</em>'.
	 * @generated
	 */
	TInterface createTInterface();

	/**
	 * Returns a new object of class '<em>TMethod</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TMethod</em>'.
	 * @generated
	 */
	TMethod createTMethod();

	/**
	 * Returns a new object of class '<em>TStruct Method</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TStruct Method</em>'.
	 * @generated
	 */
	TStructMethod createTStructMethod();

	/**
	 * Returns a new object of class '<em>TFormal Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TFormal Parameter</em>'.
	 * @generated
	 */
	TFormalParameter createTFormalParameter();

	/**
	 * Returns a new object of class '<em>TAnonymous Formal Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TAnonymous Formal Parameter</em>'.
	 * @generated
	 */
	TAnonymousFormalParameter createTAnonymousFormalParameter();

	/**
	 * Returns a new object of class '<em>TField</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TField</em>'.
	 * @generated
	 */
	TField createTField();

	/**
	 * Returns a new object of class '<em>TStruct Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TStruct Field</em>'.
	 * @generated
	 */
	TStructField createTStructField();

	/**
	 * Returns a new object of class '<em>TGetter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGetter</em>'.
	 * @generated
	 */
	TGetter createTGetter();

	/**
	 * Returns a new object of class '<em>TStruct Getter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TStruct Getter</em>'.
	 * @generated
	 */
	TStructGetter createTStructGetter();

	/**
	 * Returns a new object of class '<em>TSetter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TSetter</em>'.
	 * @generated
	 */
	TSetter createTSetter();

	/**
	 * Returns a new object of class '<em>TStruct Setter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TStruct Setter</em>'.
	 * @generated
	 */
	TStructSetter createTStructSetter();

	/**
	 * Returns a new object of class '<em>TEnum</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TEnum</em>'.
	 * @generated
	 */
	TEnum createTEnum();

	/**
	 * Returns a new object of class '<em>TEnum Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TEnum Literal</em>'.
	 * @generated
	 */
	TEnumLiteral createTEnumLiteral();

	/**
	 * Returns a new object of class '<em>Type Alias</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Alias</em>'.
	 * @generated
	 */
	TypeAlias createTypeAlias();

	/**
	 * Returns a new object of class '<em>TVariable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TVariable</em>'.
	 * @generated
	 */
	TVariable createTVariable();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TypesPackage getTypesPackage();

} //TypesFactory
