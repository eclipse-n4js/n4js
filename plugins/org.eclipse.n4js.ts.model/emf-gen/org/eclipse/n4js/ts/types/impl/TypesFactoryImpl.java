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
package org.eclipse.n4js.ts.types.impl;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.ts.types.*;

import org.eclipse.n4js.ts.types.util.Variance;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesFactoryImpl extends EFactoryImpl implements TypesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypesFactory init() {
		try {
			TypesFactory theTypesFactory = (TypesFactory)EPackage.Registry.INSTANCE.getEFactory(TypesPackage.eNS_URI);
			if (theTypesFactory != null) {
				return theTypesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TypesPackage.TYPE_DEFS: return createTypeDefs();
			case TypesPackage.TMODULE: return createTModule();
			case TypesPackage.COMPOSED_MEMBER_CACHE: return createComposedMemberCache();
			case TypesPackage.IDENTIFIABLE_ELEMENT: return createIdentifiableElement();
			case TypesPackage.TEXPORTABLE_ELEMENT: return createTExportableElement();
			case TypesPackage.TANNOTATION: return createTAnnotation();
			case TypesPackage.TANNOTATION_STRING_ARGUMENT: return createTAnnotationStringArgument();
			case TypesPackage.TANNOTATION_TYPE_REF_ARGUMENT: return createTAnnotationTypeRefArgument();
			case TypesPackage.TANNOTABLE_ELEMENT: return createTAnnotableElement();
			case TypesPackage.TYPE_VARIABLE: return createTypeVariable();
			case TypesPackage.INFERENCE_VARIABLE: return createInferenceVariable();
			case TypesPackage.TFUNCTION: return createTFunction();
			case TypesPackage.TYPE: return createType();
			case TypesPackage.DECLARED_TYPE_WITH_ACCESS_MODIFIER: return createDeclaredTypeWithAccessModifier();
			case TypesPackage.VIRTUAL_BASE_TYPE: return createVirtualBaseType();
			case TypesPackage.MODULE_NAMESPACE_VIRTUAL_TYPE: return createModuleNamespaceVirtualType();
			case TypesPackage.PRIMITIVE_TYPE: return createPrimitiveType();
			case TypesPackage.BUILT_IN_TYPE: return createBuiltInType();
			case TypesPackage.ANY_TYPE: return createAnyType();
			case TypesPackage.UNDEFINED_TYPE: return createUndefinedType();
			case TypesPackage.NULL_TYPE: return createNullType();
			case TypesPackage.VOID_TYPE: return createVoidType();
			case TypesPackage.TSTRUCTURAL_TYPE: return createTStructuralType();
			case TypesPackage.TMIGRATION: return createTMigration();
			case TypesPackage.TOBJECT_PROTOTYPE: return createTObjectPrototype();
			case TypesPackage.TN4_CLASSIFIER: return createTN4Classifier();
			case TypesPackage.TCLASS: return createTClass();
			case TypesPackage.TINTERFACE: return createTInterface();
			case TypesPackage.TMETHOD: return createTMethod();
			case TypesPackage.TSTRUCT_METHOD: return createTStructMethod();
			case TypesPackage.TFORMAL_PARAMETER: return createTFormalParameter();
			case TypesPackage.TANONYMOUS_FORMAL_PARAMETER: return createTAnonymousFormalParameter();
			case TypesPackage.TFIELD: return createTField();
			case TypesPackage.TSTRUCT_FIELD: return createTStructField();
			case TypesPackage.TGETTER: return createTGetter();
			case TypesPackage.TSTRUCT_GETTER: return createTStructGetter();
			case TypesPackage.TSETTER: return createTSetter();
			case TypesPackage.TSTRUCT_SETTER: return createTStructSetter();
			case TypesPackage.TENUM: return createTEnum();
			case TypesPackage.TENUM_LITERAL: return createTEnumLiteral();
			case TypesPackage.TVARIABLE: return createTVariable();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TypesPackage.TYPING_STRATEGY:
				return createTypingStrategyFromString(eDataType, initialValue);
			case TypesPackage.TYPE_ACCESS_MODIFIER:
				return createTypeAccessModifierFromString(eDataType, initialValue);
			case TypesPackage.MEMBER_ACCESS_MODIFIER:
				return createMemberAccessModifierFromString(eDataType, initialValue);
			case TypesPackage.MEMBER_TYPE:
				return createMemberTypeFromString(eDataType, initialValue);
			case TypesPackage.ITERABLE_OF_TCLASSIFIER:
				return createIterableOfTClassifierFromString(eDataType, initialValue);
			case TypesPackage.VARIANCE:
				return createVarianceFromString(eDataType, initialValue);
			case TypesPackage.NAME_AND_ACCESS:
				return createNameAndAccessFromString(eDataType, initialValue);
			case TypesPackage.MEMBER_LIST:
				return createMemberListFromString(eDataType, initialValue);
			case TypesPackage.TCLASSIFIER_ITERABLE:
				return createTClassifierIterableFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TypesPackage.TYPING_STRATEGY:
				return convertTypingStrategyToString(eDataType, instanceValue);
			case TypesPackage.TYPE_ACCESS_MODIFIER:
				return convertTypeAccessModifierToString(eDataType, instanceValue);
			case TypesPackage.MEMBER_ACCESS_MODIFIER:
				return convertMemberAccessModifierToString(eDataType, instanceValue);
			case TypesPackage.MEMBER_TYPE:
				return convertMemberTypeToString(eDataType, instanceValue);
			case TypesPackage.ITERABLE_OF_TCLASSIFIER:
				return convertIterableOfTClassifierToString(eDataType, instanceValue);
			case TypesPackage.VARIANCE:
				return convertVarianceToString(eDataType, instanceValue);
			case TypesPackage.NAME_AND_ACCESS:
				return convertNameAndAccessToString(eDataType, instanceValue);
			case TypesPackage.MEMBER_LIST:
				return convertMemberListToString(eDataType, instanceValue);
			case TypesPackage.TCLASSIFIER_ITERABLE:
				return convertTClassifierIterableToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeDefs createTypeDefs() {
		TypeDefsImpl typeDefs = new TypeDefsImpl();
		return typeDefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TModule createTModule() {
		TModuleImpl tModule = new TModuleImpl();
		return tModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedMemberCache createComposedMemberCache() {
		ComposedMemberCacheImpl composedMemberCache = new ComposedMemberCacheImpl();
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdentifiableElement createIdentifiableElement() {
		IdentifiableElementImpl identifiableElement = new IdentifiableElementImpl();
		return identifiableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TExportableElement createTExportableElement() {
		TExportableElementImpl tExportableElement = new TExportableElementImpl();
		return tExportableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TAnnotation createTAnnotation() {
		TAnnotationImpl tAnnotation = new TAnnotationImpl();
		return tAnnotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TAnnotationStringArgument createTAnnotationStringArgument() {
		TAnnotationStringArgumentImpl tAnnotationStringArgument = new TAnnotationStringArgumentImpl();
		return tAnnotationStringArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TAnnotationTypeRefArgument createTAnnotationTypeRefArgument() {
		TAnnotationTypeRefArgumentImpl tAnnotationTypeRefArgument = new TAnnotationTypeRefArgumentImpl();
		return tAnnotationTypeRefArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TAnnotableElement createTAnnotableElement() {
		TAnnotableElementImpl tAnnotableElement = new TAnnotableElementImpl();
		return tAnnotableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeVariable createTypeVariable() {
		TypeVariableImpl typeVariable = new TypeVariableImpl();
		return typeVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InferenceVariable createInferenceVariable() {
		InferenceVariableImpl inferenceVariable = new InferenceVariableImpl();
		return inferenceVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFunction createTFunction() {
		TFunctionImpl tFunction = new TFunctionImpl();
		return tFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type createType() {
		TypeImpl type = new TypeImpl();
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeclaredTypeWithAccessModifier createDeclaredTypeWithAccessModifier() {
		DeclaredTypeWithAccessModifierImpl declaredTypeWithAccessModifier = new DeclaredTypeWithAccessModifierImpl();
		return declaredTypeWithAccessModifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualBaseType createVirtualBaseType() {
		VirtualBaseTypeImpl virtualBaseType = new VirtualBaseTypeImpl();
		return virtualBaseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleNamespaceVirtualType createModuleNamespaceVirtualType() {
		ModuleNamespaceVirtualTypeImpl moduleNamespaceVirtualType = new ModuleNamespaceVirtualTypeImpl();
		return moduleNamespaceVirtualType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrimitiveType createPrimitiveType() {
		PrimitiveTypeImpl primitiveType = new PrimitiveTypeImpl();
		return primitiveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuiltInType createBuiltInType() {
		BuiltInTypeImpl builtInType = new BuiltInTypeImpl();
		return builtInType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyType createAnyType() {
		AnyTypeImpl anyType = new AnyTypeImpl();
		return anyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UndefinedType createUndefinedType() {
		UndefinedTypeImpl undefinedType = new UndefinedTypeImpl();
		return undefinedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullType createNullType() {
		NullTypeImpl nullType = new NullTypeImpl();
		return nullType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VoidType createVoidType() {
		VoidTypeImpl voidType = new VoidTypeImpl();
		return voidType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructuralType createTStructuralType() {
		TStructuralTypeImpl tStructuralType = new TStructuralTypeImpl();
		return tStructuralType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMigration createTMigration() {
		TMigrationImpl tMigration = new TMigrationImpl();
		return tMigration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TObjectPrototype createTObjectPrototype() {
		TObjectPrototypeImpl tObjectPrototype = new TObjectPrototypeImpl();
		return tObjectPrototype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TN4Classifier createTN4Classifier() {
		TN4ClassifierImpl tn4Classifier = new TN4ClassifierImpl();
		return tn4Classifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TClass createTClass() {
		TClassImpl tClass = new TClassImpl();
		return tClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TInterface createTInterface() {
		TInterfaceImpl tInterface = new TInterfaceImpl();
		return tInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMethod createTMethod() {
		TMethodImpl tMethod = new TMethodImpl();
		return tMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructMethod createTStructMethod() {
		TStructMethodImpl tStructMethod = new TStructMethodImpl();
		return tStructMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFormalParameter createTFormalParameter() {
		TFormalParameterImpl tFormalParameter = new TFormalParameterImpl();
		return tFormalParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TAnonymousFormalParameter createTAnonymousFormalParameter() {
		TAnonymousFormalParameterImpl tAnonymousFormalParameter = new TAnonymousFormalParameterImpl();
		return tAnonymousFormalParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TField createTField() {
		TFieldImpl tField = new TFieldImpl();
		return tField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructField createTStructField() {
		TStructFieldImpl tStructField = new TStructFieldImpl();
		return tStructField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGetter createTGetter() {
		TGetterImpl tGetter = new TGetterImpl();
		return tGetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructGetter createTStructGetter() {
		TStructGetterImpl tStructGetter = new TStructGetterImpl();
		return tStructGetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TSetter createTSetter() {
		TSetterImpl tSetter = new TSetterImpl();
		return tSetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructSetter createTStructSetter() {
		TStructSetterImpl tStructSetter = new TStructSetterImpl();
		return tStructSetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnum createTEnum() {
		TEnumImpl tEnum = new TEnumImpl();
		return tEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumLiteral createTEnumLiteral() {
		TEnumLiteralImpl tEnumLiteral = new TEnumLiteralImpl();
		return tEnumLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TVariable createTVariable() {
		TVariableImpl tVariable = new TVariableImpl();
		return tVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypingStrategy createTypingStrategyFromString(EDataType eDataType, String initialValue) {
		TypingStrategy result = TypingStrategy.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypingStrategyToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeAccessModifier createTypeAccessModifierFromString(EDataType eDataType, String initialValue) {
		TypeAccessModifier result = TypeAccessModifier.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeAccessModifierToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberAccessModifier createMemberAccessModifierFromString(EDataType eDataType, String initialValue) {
		MemberAccessModifier result = MemberAccessModifier.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMemberAccessModifierToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberType createMemberTypeFromString(EDataType eDataType, String initialValue) {
		MemberType result = MemberType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMemberTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Iterable<? extends TClassifier> createIterableOfTClassifierFromString(EDataType eDataType, String initialValue) {
		return (Iterable<? extends TClassifier>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIterableOfTClassifierToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variance createVarianceFromString(EDataType eDataType, String initialValue) {
		return (Variance)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVarianceToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameAndAccess createNameAndAccessFromString(EDataType eDataType, String initialValue) {
		return (NameAndAccess)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNameAndAccessToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<? extends TMember> createMemberListFromString(EDataType eDataType, String initialValue) {
		return (List<? extends TMember>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMemberListToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Iterable<TClassifier> createTClassifierIterableFromString(EDataType eDataType, String initialValue) {
		return (Iterable<TClassifier>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTClassifierIterableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesPackage getTypesPackage() {
		return (TypesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TypesPackage getPackage() {
		return TypesPackage.eINSTANCE;
	}

} //TypesFactoryImpl
