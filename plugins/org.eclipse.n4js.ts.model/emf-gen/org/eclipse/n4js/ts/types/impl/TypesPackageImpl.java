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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl;

import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.ArrayLike;
import org.eclipse.n4js.ts.types.BuiltInType;
import org.eclipse.n4js.ts.types.ComposedMemberCache;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.DeclaredTypeWithAccessModifier;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.NameAndAccess;
import org.eclipse.n4js.ts.types.NullType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.RunTimeDependency;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationArgument;
import org.eclipse.n4js.ts.types.TAnnotationStringArgument;
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument;
import org.eclipse.n4js.ts.types.TAnonymousFormalParameter;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TConstableElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TMigratable;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TObjectPrototype;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TTypedElement;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TVersionable;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypeDefs;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.ts.types.VirtualBaseType;
import org.eclipse.n4js.ts.types.VoidType;

import org.eclipse.n4js.ts.types.util.Variance;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesPackageImpl extends EPackageImpl implements TypesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeDefsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tModuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass runTimeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass composedMemberCacheEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identifiableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tExportableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tAnnotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tAnnotationArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tAnnotationStringArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tTypedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tAnnotationTypeRefArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tAnnotableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inferenceVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass accessibleTypeElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass declaredTypeWithAccessModifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass containerTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass virtualBaseTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moduleNamespaceVirtualTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primitiveTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass builtInTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass undefinedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nullTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass voidTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tStructuralTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tVersionableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tMigratableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tMigrationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tObjectPrototypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayLikeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tn4ClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tInterfaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tMemberEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tMemberWithAccessModifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tStructMemberEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tMethodEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tStructMethodEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tFormalParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tAnonymousFormalParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tStructFieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldAccessorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tGetterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tStructGetterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tSetterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tStructSetterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tEnumEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tEnumLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass syntaxRelatedTElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tConstableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum typingStrategyEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum typeAccessModifierEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum memberAccessModifierEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum memberTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType parameterizedTypeRefIterableEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iterableOfTClassifierEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType varianceEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameAndAccessEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType memberListEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType tClassifierIterableEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.n4js.ts.types.TypesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TypesPackageImpl() {
		super(eNS_URI, TypesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link TypesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TypesPackage init() {
		if (isInited) return (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTypesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TypesPackageImpl theTypesPackage = registeredTypesPackage instanceof TypesPackageImpl ? (TypesPackageImpl)registeredTypesPackage : new TypesPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TypeRefsPackage.eNS_URI);
		TypeRefsPackageImpl theTypeRefsPackage = (TypeRefsPackageImpl)(registeredPackage instanceof TypeRefsPackageImpl ? registeredPackage : TypeRefsPackage.eINSTANCE);

		// Create package meta-data objects
		theTypesPackage.createPackageContents();
		theTypeRefsPackage.createPackageContents();

		// Initialize created meta-data
		theTypesPackage.initializePackageContents();
		theTypeRefsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTypesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TypesPackage.eNS_URI, theTypesPackage);
		return theTypesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeDefs() {
		return typeDefsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeDefs_Types() {
		return (EReference)typeDefsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTModule() {
		return tModuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_SimpleName() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_QualifiedName() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_ProjectName() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_VendorID() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_N4jsdModule() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_StaticPolyfillModule() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_StaticPolyfillAware() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_MainModule() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_PreLinkingPhase() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_Reconciled() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_DependenciesRunTime() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_CyclicModulesRunTime() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_CyclicModulesLoadTimeForInheritance() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_RunTimeCyclicLoadTimeDependents() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_TopLevelTypes() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_Variables() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_InternalTypes() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_ExposedInternalTypes() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_AstMD5() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_ComposedMemberCaches() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTModule_TemporaryTypes() {
		return (EReference)tModuleEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTModule_ModuleSpecifier() {
		return (EAttribute)tModuleEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTModule__HasDirectLoadTimeDependencyTo__TModule() {
		return tModuleEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRunTimeDependency() {
		return runTimeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRunTimeDependency_Target() {
		return (EReference)runTimeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRunTimeDependency_LoadTimeForInheritance() {
		return (EAttribute)runTimeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComposedMemberCache() {
		return composedMemberCacheEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComposedMemberCache_CachedComposedMembers() {
		return (EReference)composedMemberCacheEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComposedMemberCache_ComposedTypeRef() {
		return (EReference)composedMemberCacheEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypableElement() {
		return typableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIdentifiableElement() {
		return identifiableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentifiableElement_Name() {
		return (EAttribute)identifiableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIdentifiableElement__GetContainingModule() {
		return identifiableElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTExportableElement() {
		return tExportableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTExportableElement_ExportedName() {
		return (EAttribute)tExportableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTExportableElement__IsExported() {
		return tExportableElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTAnnotation() {
		return tAnnotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTAnnotation_Name() {
		return (EAttribute)tAnnotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTAnnotation_Args() {
		return (EReference)tAnnotationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTAnnotation__HasStringArgument__String() {
		return tAnnotationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTAnnotation__GetAnnotationAsString() {
		return tAnnotationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTAnnotationArgument() {
		return tAnnotationArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTAnnotationArgument__GetArgAsString() {
		return tAnnotationArgumentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTAnnotationStringArgument() {
		return tAnnotationStringArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTAnnotationStringArgument_Value() {
		return (EAttribute)tAnnotationStringArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTAnnotationStringArgument__GetArgAsString() {
		return tAnnotationStringArgumentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTTypedElement() {
		return tTypedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTTypedElement_TypeRef() {
		return (EReference)tTypedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTAnnotationTypeRefArgument() {
		return tAnnotationTypeRefArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTAnnotationTypeRefArgument__GetArgAsString() {
		return tAnnotationTypeRefArgumentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTAnnotableElement() {
		return tAnnotableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTAnnotableElement_Annotations() {
		return (EReference)tAnnotableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeVariable() {
		return typeVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeVariable_DeclaredCovariant() {
		return (EAttribute)typeVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeVariable_DeclaredContravariant() {
		return (EAttribute)typeVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeVariable_DeclaredUpperBound() {
		return (EReference)typeVariableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeVariable_DefinedTypeVariable() {
		return (EReference)typeVariableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTypeVariable__GetVariance() {
		return typeVariableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTypeVariable__GetTypeVars() {
		return typeVariableEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTypeVariable__GetTypeAsString() {
		return typeVariableEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTypeVariable__GetTypeVariableAsString__TypeRef() {
		return typeVariableEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInferenceVariable() {
		return inferenceVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTFunction() {
		return tFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTFunction_External() {
		return (EAttribute)tFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTFunction_Fpars() {
		return (EReference)tFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTFunction_ReturnValueMarkedOptional() {
		return (EAttribute)tFunctionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTFunction_ReturnTypeRef() {
		return (EReference)tFunctionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTFunction_TypeVars() {
		return (EReference)tFunctionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTFunction_DeclaredThisType() {
		return (EReference)tFunctionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTFunction_DeclaredAsync() {
		return (EAttribute)tFunctionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTFunction_DeclaredGenerator() {
		return (EAttribute)tFunctionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTFunction_Constructor() {
		return (EAttribute)tFunctionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFunction__IsReturnValueOptional() {
		return tFunctionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFunction__IsCallableConstructor() {
		return tFunctionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFunction__GetFparForArgIdx__int() {
		return tFunctionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFunction__GetFunctionAsString() {
		return tFunctionEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFunction__IsFinal() {
		return tFunctionEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__IsProvidedByRuntime() {
		return typeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__IsPolyfill() {
		return typeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__IsStaticPolyfill() {
		return typeEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__IsFinal() {
		return typeEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__IsDynamizable() {
		return typeEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__IsArrayLike() {
		return typeEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__GetElementType() {
		return typeEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__GetTypeAccessModifier() {
		return typeEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__IsGeneric() {
		return typeEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__GetTypeVars() {
		return typeEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__GetVarianceOfTypeVar__int() {
		return typeEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__GetRawTypeAsString() {
		return typeEClass.getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getType__GetTypeAsString() {
		return typeEClass.getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAccessibleTypeElement() {
		return accessibleTypeElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAccessibleTypeElement_DeclaredTypeAccessModifier() {
		return (EAttribute)accessibleTypeElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAccessibleTypeElement_DeclaredProvidedByRuntime() {
		return (EAttribute)accessibleTypeElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAccessibleTypeElement__IsProvidedByRuntime() {
		return accessibleTypeElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAccessibleTypeElement__GetTypeAccessModifier() {
		return accessibleTypeElementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAccessibleTypeElement__IsExported() {
		return accessibleTypeElementEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDeclaredTypeWithAccessModifier() {
		return declaredTypeWithAccessModifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContainerType() {
		return containerTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContainerType_OwnedMembersByNameAndAccess() {
		return (EAttribute)containerTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContainerType_OwnedMembers() {
		return (EReference)containerTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContainerType_CallableCtor() {
		return (EReference)containerTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContainerType_TypeVars() {
		return (EReference)containerTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getContainerType__GetOwnedCtor() {
		return containerTypeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getContainerType__FindOwnedMember__String() {
		return containerTypeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getContainerType__FindOwnedMember__String_boolean_boolean() {
		return containerTypeEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getContainerType__GetOrCreateOwnedMembersByNameAndAccess() {
		return containerTypeEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVirtualBaseType() {
		return virtualBaseTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVirtualBaseType_DeclaredOwnedMembers() {
		return (EReference)virtualBaseTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModuleNamespaceVirtualType() {
		return moduleNamespaceVirtualTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModuleNamespaceVirtualType_Module() {
		return (EReference)moduleNamespaceVirtualTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModuleNamespaceVirtualType_DeclaredDynamic() {
		return (EAttribute)moduleNamespaceVirtualTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getModuleNamespaceVirtualType__IsProvidedByRuntime() {
		return moduleNamespaceVirtualTypeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPrimitiveType() {
		return primitiveTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPrimitiveType_AssignmentCompatible() {
		return (EReference)primitiveTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPrimitiveType_AutoboxedType() {
		return (EReference)primitiveTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBuiltInType() {
		return builtInTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBuiltInType__GetTypeVars() {
		return builtInTypeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyType() {
		return anyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnyType__IsFinal() {
		return anyTypeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUndefinedType() {
		return undefinedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNullType() {
		return nullTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVoidType() {
		return voidTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTStructuralType() {
		return tStructuralTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTStructuralType__IsFinal() {
		return tStructuralTypeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTVersionable() {
		return tVersionableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTVersionable_DeclaredVersion() {
		return (EAttribute)tVersionableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTVersionable__GetVersion() {
		return tVersionableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTMigratable() {
		return tMigratableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTMigratable_Migrations() {
		return (EReference)tMigratableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTMigration() {
		return tMigrationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMigration_SourceVersion() {
		return (EAttribute)tMigrationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMigration_TargetVersion() {
		return (EAttribute)tMigrationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMigration_HasDeclaredSourceAndTargetVersion() {
		return (EAttribute)tMigrationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTMigration_SourceTypeRefs() {
		return (EReference)tMigrationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTMigration_TargetTypeRefs() {
		return (EReference)tMigrationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTMigration__principalArgumentType() {
		return (EReference)tMigrationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMigration__GetPrincipalArgumentType() {
		return tMigrationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMigration__GetMigrationAsString() {
		return tMigrationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTClassifier() {
		return tClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTClassifier_DeclaredCovariantConstructor() {
		return (EAttribute)tClassifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClassifier__IsAbstract() {
		return tClassifierEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClassifier__GetSuperClassifiers() {
		return tClassifierEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClassifier__GetSuperClassifierRefs() {
		return tClassifierEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClassifier__GetImplementedOrExtendedInterfaceRefs() {
		return tClassifierEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClassifier__IsFinal() {
		return tClassifierEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTObjectPrototype() {
		return tObjectPrototypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTObjectPrototype_SuperType() {
		return (EReference)tObjectPrototypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTObjectPrototype_DeclaredFinal() {
		return (EAttribute)tObjectPrototypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTObjectPrototype__GetOwnedCtor() {
		return tObjectPrototypeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTObjectPrototype__IsFinal() {
		return tObjectPrototypeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrayLike() {
		return arrayLikeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayLike_DeclaredElementType() {
		return (EReference)arrayLikeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getArrayLike__GetElementType() {
		return arrayLikeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTN4Classifier() {
		return tn4ClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTN4Classifier_Dynamizable() {
		return (EAttribute)tn4ClassifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTN4Classifier_TypingStrategy() {
		return (EAttribute)tn4ClassifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTClass() {
		return tClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTClass_External() {
		return (EAttribute)tClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTClass_DeclaredAbstract() {
		return (EAttribute)tClassEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTClass_DeclaredN4JS() {
		return (EAttribute)tClassEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTClass_DeclaredFinal() {
		return (EAttribute)tClassEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTClass_DeclaredPolyfill() {
		return (EAttribute)tClassEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTClass_DeclaredStaticPolyfill() {
		return (EAttribute)tClassEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTClass_Observable() {
		return (EAttribute)tClassEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTClass_SuperClassRef() {
		return (EReference)tClassEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTClass_ImplementedInterfaceRefs() {
		return (EReference)tClassEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClass__IsAbstract() {
		return tClassEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClass__GetSuperClass() {
		return tClassEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClass__GetSuperClassifierRefs() {
		return tClassEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClass__GetImplementedOrExtendedInterfaceRefs() {
		return tClassEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClass__IsPolyfill() {
		return tClassEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClass__IsStaticPolyfill() {
		return tClassEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTClass__IsFinal() {
		return tClassEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTInterface() {
		return tInterfaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTInterface_External() {
		return (EAttribute)tInterfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTInterface_SuperInterfaceRefs() {
		return (EReference)tInterfaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTInterface__IsAbstract() {
		return tInterfaceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTInterface__GetSuperClassifierRefs() {
		return tInterfaceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTInterface__GetImplementedOrExtendedInterfaceRefs() {
		return tInterfaceEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTMember() {
		return tMemberEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMember_DeclaredFinal() {
		return (EAttribute)tMemberEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMember_DeclaredStatic() {
		return (EAttribute)tMemberEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMember_DeclaredOverride() {
		return (EAttribute)tMemberEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMember_HasComputedName() {
		return (EAttribute)tMemberEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTMember_ConstituentMembers() {
		return (EReference)tMemberEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMember_Composed() {
		return (EAttribute)tMemberEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__GetContainingType() {
		return tMemberEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__GetMemberAccessModifier() {
		return tMemberEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__GetMemberType() {
		return tMemberEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsField() {
		return tMemberEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsGetter() {
		return tMemberEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsSetter() {
		return tMemberEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsAccessor() {
		return tMemberEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsMethod() {
		return tMemberEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsConstructor() {
		return tMemberEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsOptional() {
		return tMemberEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsAbstract() {
		return tMemberEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsReadable() {
		return tMemberEClass.getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsWriteable() {
		return tMemberEClass.getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__GetMemberAsString() {
		return tMemberEClass.getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsFinal() {
		return tMemberEClass.getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsStatic() {
		return tMemberEClass.getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsConst() {
		return tMemberEClass.getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMember__IsPolyfilled() {
		return tMemberEClass.getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTMemberWithAccessModifier() {
		return tMemberWithAccessModifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMemberWithAccessModifier_HasNoBody() {
		return (EAttribute)tMemberWithAccessModifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMemberWithAccessModifier_DeclaredMemberAccessModifier() {
		return (EAttribute)tMemberWithAccessModifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMemberWithAccessModifier__GetMemberAccessModifier() {
		return tMemberWithAccessModifierEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTStructMember() {
		return tStructMemberEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTStructMember_DefinedMember() {
		return (EReference)tStructMemberEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTStructMember__GetDefaultMemberAccessModifier() {
		return tStructMemberEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTStructMember__IsStatic() {
		return tStructMemberEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTStructMember__GetMemberAccessModifier() {
		return tStructMemberEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTMethod() {
		return tMethodEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMethod_DeclaredAbstract() {
		return (EAttribute)tMethodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTMethod_LacksThisOrSuperUsage() {
		return (EAttribute)tMethodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMethod__IsAbstract() {
		return tMethodEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMethod__GetMemberType() {
		return tMethodEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMethod__IsConstructor() {
		return tMethodEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMethod__GetFunctionAsString() {
		return tMethodEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTMethod__GetMemberAsString() {
		return tMethodEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTStructMethod() {
		return tStructMethodEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTFormalParameter() {
		return tFormalParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTFormalParameter_Variadic() {
		return (EAttribute)tFormalParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTFormalParameter_AstInitializer() {
		return (EAttribute)tFormalParameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTFormalParameter_HasInitializerAssignment() {
		return (EAttribute)tFormalParameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFormalParameter__HasASTInitializer() {
		return tFormalParameterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFormalParameter__IsOptional() {
		return tFormalParameterEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFormalParameter__IsVariadicOrOptional() {
		return tFormalParameterEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFormalParameter__GetFormalParameterAsTypesString() {
		return tFormalParameterEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTFormalParameter__GetFormalParameterAsString() {
		return tFormalParameterEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTAnonymousFormalParameter() {
		return tAnonymousFormalParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTAnonymousFormalParameter__GetName() {
		return tAnonymousFormalParameterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTAnonymousFormalParameter__GetDefinedName() {
		return tAnonymousFormalParameterEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTField() {
		return tFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTField_HasExpression() {
		return (EAttribute)tFieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTField_Optional() {
		return (EAttribute)tFieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTField__IsStatic() {
		return tFieldEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTField__IsReadable() {
		return tFieldEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTField__IsWriteable() {
		return tFieldEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTField__GetMemberType() {
		return tFieldEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTField__GetMemberAsString() {
		return tFieldEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTStructField() {
		return tStructFieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFieldAccessor() {
		return fieldAccessorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFieldAccessor_Optional() {
		return (EAttribute)fieldAccessorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFieldAccessor_DeclaredAbstract() {
		return (EAttribute)fieldAccessorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFieldAccessor_DeclaredThisType() {
		return (EReference)fieldAccessorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFieldAccessor__GetDeclaredTypeRef() {
		return fieldAccessorEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFieldAccessor__IsAbstract() {
		return fieldAccessorEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGetter() {
		return tGetterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGetter_DeclaredTypeRef() {
		return (EReference)tGetterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTGetter__GetMemberType() {
		return tGetterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTGetter__GetMemberAsString() {
		return tGetterEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTStructGetter() {
		return tStructGetterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTSetter() {
		return tSetterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTSetter_Fpar() {
		return (EReference)tSetterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTSetter__GetDeclaredTypeRef() {
		return tSetterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTSetter__IsReadable() {
		return tSetterEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTSetter__IsWriteable() {
		return tSetterEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTSetter__GetMemberType() {
		return tSetterEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTSetter__GetMemberAsString() {
		return tSetterEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTStructSetter() {
		return tStructSetterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTStructSetter__IsWriteable() {
		return tStructSetterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTEnum() {
		return tEnumEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTEnum_External() {
		return (EAttribute)tEnumEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTEnum_Literals() {
		return (EReference)tEnumEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTEnum__GetTypeVars() {
		return tEnumEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTEnumLiteral() {
		return tEnumLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTEnumLiteral_Value() {
		return (EAttribute)tEnumLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTEnumLiteral__GetValueOrName() {
		return tEnumLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSyntaxRelatedTElement() {
		return syntaxRelatedTElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSyntaxRelatedTElement_AstElement() {
		return (EReference)syntaxRelatedTElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTConstableElement() {
		return tConstableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTConstableElement_Const() {
		return (EAttribute)tConstableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTConstableElement_CompileTimeValue() {
		return (EAttribute)tConstableElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTVariable() {
		return tVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTVariable_External() {
		return (EAttribute)tVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTVariable_ObjectLiteral() {
		return (EAttribute)tVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTVariable_NewExpression() {
		return (EAttribute)tVariableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTVariable__GetVariableAsString() {
		return tVariableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getTypingStrategy() {
		return typingStrategyEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getTypeAccessModifier() {
		return typeAccessModifierEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getMemberAccessModifier() {
		return memberAccessModifierEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getMemberType() {
		return memberTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getParameterizedTypeRefIterable() {
		return parameterizedTypeRefIterableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIterableOfTClassifier() {
		return iterableOfTClassifierEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getVariance() {
		return varianceEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getNameAndAccess() {
		return nameAndAccessEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getMemberList() {
		return memberListEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getTClassifierIterable() {
		return tClassifierIterableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypesFactory getTypesFactory() {
		return (TypesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		typeDefsEClass = createEClass(TYPE_DEFS);
		createEReference(typeDefsEClass, TYPE_DEFS__TYPES);

		tModuleEClass = createEClass(TMODULE);
		createEAttribute(tModuleEClass, TMODULE__SIMPLE_NAME);
		createEAttribute(tModuleEClass, TMODULE__QUALIFIED_NAME);
		createEAttribute(tModuleEClass, TMODULE__PROJECT_NAME);
		createEAttribute(tModuleEClass, TMODULE__VENDOR_ID);
		createEAttribute(tModuleEClass, TMODULE__N4JSD_MODULE);
		createEAttribute(tModuleEClass, TMODULE__STATIC_POLYFILL_MODULE);
		createEAttribute(tModuleEClass, TMODULE__STATIC_POLYFILL_AWARE);
		createEAttribute(tModuleEClass, TMODULE__MAIN_MODULE);
		createEAttribute(tModuleEClass, TMODULE__PRE_LINKING_PHASE);
		createEAttribute(tModuleEClass, TMODULE__RECONCILED);
		createEReference(tModuleEClass, TMODULE__DEPENDENCIES_RUN_TIME);
		createEReference(tModuleEClass, TMODULE__CYCLIC_MODULES_RUN_TIME);
		createEReference(tModuleEClass, TMODULE__CYCLIC_MODULES_LOAD_TIME_FOR_INHERITANCE);
		createEReference(tModuleEClass, TMODULE__RUN_TIME_CYCLIC_LOAD_TIME_DEPENDENTS);
		createEReference(tModuleEClass, TMODULE__TOP_LEVEL_TYPES);
		createEReference(tModuleEClass, TMODULE__VARIABLES);
		createEReference(tModuleEClass, TMODULE__INTERNAL_TYPES);
		createEReference(tModuleEClass, TMODULE__EXPOSED_INTERNAL_TYPES);
		createEAttribute(tModuleEClass, TMODULE__AST_MD5);
		createEReference(tModuleEClass, TMODULE__COMPOSED_MEMBER_CACHES);
		createEReference(tModuleEClass, TMODULE__TEMPORARY_TYPES);
		createEAttribute(tModuleEClass, TMODULE__MODULE_SPECIFIER);
		createEOperation(tModuleEClass, TMODULE___HAS_DIRECT_LOAD_TIME_DEPENDENCY_TO__TMODULE);

		runTimeDependencyEClass = createEClass(RUN_TIME_DEPENDENCY);
		createEReference(runTimeDependencyEClass, RUN_TIME_DEPENDENCY__TARGET);
		createEAttribute(runTimeDependencyEClass, RUN_TIME_DEPENDENCY__LOAD_TIME_FOR_INHERITANCE);

		composedMemberCacheEClass = createEClass(COMPOSED_MEMBER_CACHE);
		createEReference(composedMemberCacheEClass, COMPOSED_MEMBER_CACHE__CACHED_COMPOSED_MEMBERS);
		createEReference(composedMemberCacheEClass, COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF);

		typableElementEClass = createEClass(TYPABLE_ELEMENT);

		identifiableElementEClass = createEClass(IDENTIFIABLE_ELEMENT);
		createEAttribute(identifiableElementEClass, IDENTIFIABLE_ELEMENT__NAME);
		createEOperation(identifiableElementEClass, IDENTIFIABLE_ELEMENT___GET_CONTAINING_MODULE);

		tExportableElementEClass = createEClass(TEXPORTABLE_ELEMENT);
		createEAttribute(tExportableElementEClass, TEXPORTABLE_ELEMENT__EXPORTED_NAME);
		createEOperation(tExportableElementEClass, TEXPORTABLE_ELEMENT___IS_EXPORTED);

		tAnnotationEClass = createEClass(TANNOTATION);
		createEAttribute(tAnnotationEClass, TANNOTATION__NAME);
		createEReference(tAnnotationEClass, TANNOTATION__ARGS);
		createEOperation(tAnnotationEClass, TANNOTATION___HAS_STRING_ARGUMENT__STRING);
		createEOperation(tAnnotationEClass, TANNOTATION___GET_ANNOTATION_AS_STRING);

		tAnnotationArgumentEClass = createEClass(TANNOTATION_ARGUMENT);
		createEOperation(tAnnotationArgumentEClass, TANNOTATION_ARGUMENT___GET_ARG_AS_STRING);

		tAnnotationStringArgumentEClass = createEClass(TANNOTATION_STRING_ARGUMENT);
		createEAttribute(tAnnotationStringArgumentEClass, TANNOTATION_STRING_ARGUMENT__VALUE);
		createEOperation(tAnnotationStringArgumentEClass, TANNOTATION_STRING_ARGUMENT___GET_ARG_AS_STRING);

		tTypedElementEClass = createEClass(TTYPED_ELEMENT);
		createEReference(tTypedElementEClass, TTYPED_ELEMENT__TYPE_REF);

		tAnnotationTypeRefArgumentEClass = createEClass(TANNOTATION_TYPE_REF_ARGUMENT);
		createEOperation(tAnnotationTypeRefArgumentEClass, TANNOTATION_TYPE_REF_ARGUMENT___GET_ARG_AS_STRING);

		tAnnotableElementEClass = createEClass(TANNOTABLE_ELEMENT);
		createEReference(tAnnotableElementEClass, TANNOTABLE_ELEMENT__ANNOTATIONS);

		typeVariableEClass = createEClass(TYPE_VARIABLE);
		createEAttribute(typeVariableEClass, TYPE_VARIABLE__DECLARED_COVARIANT);
		createEAttribute(typeVariableEClass, TYPE_VARIABLE__DECLARED_CONTRAVARIANT);
		createEReference(typeVariableEClass, TYPE_VARIABLE__DECLARED_UPPER_BOUND);
		createEReference(typeVariableEClass, TYPE_VARIABLE__DEFINED_TYPE_VARIABLE);
		createEOperation(typeVariableEClass, TYPE_VARIABLE___GET_VARIANCE);
		createEOperation(typeVariableEClass, TYPE_VARIABLE___GET_TYPE_VARS);
		createEOperation(typeVariableEClass, TYPE_VARIABLE___GET_TYPE_AS_STRING);
		createEOperation(typeVariableEClass, TYPE_VARIABLE___GET_TYPE_VARIABLE_AS_STRING__TYPEREF);

		inferenceVariableEClass = createEClass(INFERENCE_VARIABLE);

		tFunctionEClass = createEClass(TFUNCTION);
		createEAttribute(tFunctionEClass, TFUNCTION__EXTERNAL);
		createEReference(tFunctionEClass, TFUNCTION__FPARS);
		createEAttribute(tFunctionEClass, TFUNCTION__RETURN_VALUE_MARKED_OPTIONAL);
		createEReference(tFunctionEClass, TFUNCTION__RETURN_TYPE_REF);
		createEReference(tFunctionEClass, TFUNCTION__TYPE_VARS);
		createEReference(tFunctionEClass, TFUNCTION__DECLARED_THIS_TYPE);
		createEAttribute(tFunctionEClass, TFUNCTION__DECLARED_ASYNC);
		createEAttribute(tFunctionEClass, TFUNCTION__DECLARED_GENERATOR);
		createEAttribute(tFunctionEClass, TFUNCTION__CONSTRUCTOR);
		createEOperation(tFunctionEClass, TFUNCTION___IS_RETURN_VALUE_OPTIONAL);
		createEOperation(tFunctionEClass, TFUNCTION___IS_CALLABLE_CONSTRUCTOR);
		createEOperation(tFunctionEClass, TFUNCTION___GET_FPAR_FOR_ARG_IDX__INT);
		createEOperation(tFunctionEClass, TFUNCTION___GET_FUNCTION_AS_STRING);
		createEOperation(tFunctionEClass, TFUNCTION___IS_FINAL);

		typeEClass = createEClass(TYPE);
		createEOperation(typeEClass, TYPE___IS_PROVIDED_BY_RUNTIME);
		createEOperation(typeEClass, TYPE___IS_POLYFILL);
		createEOperation(typeEClass, TYPE___IS_STATIC_POLYFILL);
		createEOperation(typeEClass, TYPE___IS_FINAL);
		createEOperation(typeEClass, TYPE___IS_DYNAMIZABLE);
		createEOperation(typeEClass, TYPE___IS_ARRAY_LIKE);
		createEOperation(typeEClass, TYPE___GET_ELEMENT_TYPE);
		createEOperation(typeEClass, TYPE___GET_TYPE_ACCESS_MODIFIER);
		createEOperation(typeEClass, TYPE___IS_GENERIC);
		createEOperation(typeEClass, TYPE___GET_TYPE_VARS);
		createEOperation(typeEClass, TYPE___GET_VARIANCE_OF_TYPE_VAR__INT);
		createEOperation(typeEClass, TYPE___GET_RAW_TYPE_AS_STRING);
		createEOperation(typeEClass, TYPE___GET_TYPE_AS_STRING);

		accessibleTypeElementEClass = createEClass(ACCESSIBLE_TYPE_ELEMENT);
		createEAttribute(accessibleTypeElementEClass, ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER);
		createEAttribute(accessibleTypeElementEClass, ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME);
		createEOperation(accessibleTypeElementEClass, ACCESSIBLE_TYPE_ELEMENT___IS_PROVIDED_BY_RUNTIME);
		createEOperation(accessibleTypeElementEClass, ACCESSIBLE_TYPE_ELEMENT___GET_TYPE_ACCESS_MODIFIER);
		createEOperation(accessibleTypeElementEClass, ACCESSIBLE_TYPE_ELEMENT___IS_EXPORTED);

		declaredTypeWithAccessModifierEClass = createEClass(DECLARED_TYPE_WITH_ACCESS_MODIFIER);

		containerTypeEClass = createEClass(CONTAINER_TYPE);
		createEAttribute(containerTypeEClass, CONTAINER_TYPE__OWNED_MEMBERS_BY_NAME_AND_ACCESS);
		createEReference(containerTypeEClass, CONTAINER_TYPE__OWNED_MEMBERS);
		createEReference(containerTypeEClass, CONTAINER_TYPE__CALLABLE_CTOR);
		createEReference(containerTypeEClass, CONTAINER_TYPE__TYPE_VARS);
		createEOperation(containerTypeEClass, CONTAINER_TYPE___GET_OWNED_CTOR);
		createEOperation(containerTypeEClass, CONTAINER_TYPE___FIND_OWNED_MEMBER__STRING);
		createEOperation(containerTypeEClass, CONTAINER_TYPE___FIND_OWNED_MEMBER__STRING_BOOLEAN_BOOLEAN);
		createEOperation(containerTypeEClass, CONTAINER_TYPE___GET_OR_CREATE_OWNED_MEMBERS_BY_NAME_AND_ACCESS);

		virtualBaseTypeEClass = createEClass(VIRTUAL_BASE_TYPE);
		createEReference(virtualBaseTypeEClass, VIRTUAL_BASE_TYPE__DECLARED_OWNED_MEMBERS);

		moduleNamespaceVirtualTypeEClass = createEClass(MODULE_NAMESPACE_VIRTUAL_TYPE);
		createEReference(moduleNamespaceVirtualTypeEClass, MODULE_NAMESPACE_VIRTUAL_TYPE__MODULE);
		createEAttribute(moduleNamespaceVirtualTypeEClass, MODULE_NAMESPACE_VIRTUAL_TYPE__DECLARED_DYNAMIC);
		createEOperation(moduleNamespaceVirtualTypeEClass, MODULE_NAMESPACE_VIRTUAL_TYPE___IS_PROVIDED_BY_RUNTIME);

		primitiveTypeEClass = createEClass(PRIMITIVE_TYPE);
		createEReference(primitiveTypeEClass, PRIMITIVE_TYPE__ASSIGNMENT_COMPATIBLE);
		createEReference(primitiveTypeEClass, PRIMITIVE_TYPE__AUTOBOXED_TYPE);

		builtInTypeEClass = createEClass(BUILT_IN_TYPE);
		createEOperation(builtInTypeEClass, BUILT_IN_TYPE___GET_TYPE_VARS);

		anyTypeEClass = createEClass(ANY_TYPE);
		createEOperation(anyTypeEClass, ANY_TYPE___IS_FINAL);

		undefinedTypeEClass = createEClass(UNDEFINED_TYPE);

		nullTypeEClass = createEClass(NULL_TYPE);

		voidTypeEClass = createEClass(VOID_TYPE);

		tStructuralTypeEClass = createEClass(TSTRUCTURAL_TYPE);
		createEOperation(tStructuralTypeEClass, TSTRUCTURAL_TYPE___IS_FINAL);

		tVersionableEClass = createEClass(TVERSIONABLE);
		createEAttribute(tVersionableEClass, TVERSIONABLE__DECLARED_VERSION);
		createEOperation(tVersionableEClass, TVERSIONABLE___GET_VERSION);

		tMigratableEClass = createEClass(TMIGRATABLE);
		createEReference(tMigratableEClass, TMIGRATABLE__MIGRATIONS);

		tMigrationEClass = createEClass(TMIGRATION);
		createEAttribute(tMigrationEClass, TMIGRATION__SOURCE_VERSION);
		createEAttribute(tMigrationEClass, TMIGRATION__TARGET_VERSION);
		createEAttribute(tMigrationEClass, TMIGRATION__HAS_DECLARED_SOURCE_AND_TARGET_VERSION);
		createEReference(tMigrationEClass, TMIGRATION__SOURCE_TYPE_REFS);
		createEReference(tMigrationEClass, TMIGRATION__TARGET_TYPE_REFS);
		createEReference(tMigrationEClass, TMIGRATION__PRINCIPAL_ARGUMENT_TYPE);
		createEOperation(tMigrationEClass, TMIGRATION___GET_PRINCIPAL_ARGUMENT_TYPE);
		createEOperation(tMigrationEClass, TMIGRATION___GET_MIGRATION_AS_STRING);

		tClassifierEClass = createEClass(TCLASSIFIER);
		createEAttribute(tClassifierEClass, TCLASSIFIER__DECLARED_COVARIANT_CONSTRUCTOR);
		createEOperation(tClassifierEClass, TCLASSIFIER___IS_ABSTRACT);
		createEOperation(tClassifierEClass, TCLASSIFIER___GET_SUPER_CLASSIFIERS);
		createEOperation(tClassifierEClass, TCLASSIFIER___GET_SUPER_CLASSIFIER_REFS);
		createEOperation(tClassifierEClass, TCLASSIFIER___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS);
		createEOperation(tClassifierEClass, TCLASSIFIER___IS_FINAL);

		tObjectPrototypeEClass = createEClass(TOBJECT_PROTOTYPE);
		createEReference(tObjectPrototypeEClass, TOBJECT_PROTOTYPE__SUPER_TYPE);
		createEAttribute(tObjectPrototypeEClass, TOBJECT_PROTOTYPE__DECLARED_FINAL);
		createEOperation(tObjectPrototypeEClass, TOBJECT_PROTOTYPE___GET_OWNED_CTOR);
		createEOperation(tObjectPrototypeEClass, TOBJECT_PROTOTYPE___IS_FINAL);

		arrayLikeEClass = createEClass(ARRAY_LIKE);
		createEReference(arrayLikeEClass, ARRAY_LIKE__DECLARED_ELEMENT_TYPE);
		createEOperation(arrayLikeEClass, ARRAY_LIKE___GET_ELEMENT_TYPE);

		tn4ClassifierEClass = createEClass(TN4_CLASSIFIER);
		createEAttribute(tn4ClassifierEClass, TN4_CLASSIFIER__DYNAMIZABLE);
		createEAttribute(tn4ClassifierEClass, TN4_CLASSIFIER__TYPING_STRATEGY);

		tClassEClass = createEClass(TCLASS);
		createEAttribute(tClassEClass, TCLASS__EXTERNAL);
		createEAttribute(tClassEClass, TCLASS__DECLARED_ABSTRACT);
		createEAttribute(tClassEClass, TCLASS__DECLARED_N4JS);
		createEAttribute(tClassEClass, TCLASS__DECLARED_FINAL);
		createEAttribute(tClassEClass, TCLASS__DECLARED_POLYFILL);
		createEAttribute(tClassEClass, TCLASS__DECLARED_STATIC_POLYFILL);
		createEAttribute(tClassEClass, TCLASS__OBSERVABLE);
		createEReference(tClassEClass, TCLASS__SUPER_CLASS_REF);
		createEReference(tClassEClass, TCLASS__IMPLEMENTED_INTERFACE_REFS);
		createEOperation(tClassEClass, TCLASS___IS_ABSTRACT);
		createEOperation(tClassEClass, TCLASS___GET_SUPER_CLASS);
		createEOperation(tClassEClass, TCLASS___GET_SUPER_CLASSIFIER_REFS);
		createEOperation(tClassEClass, TCLASS___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS);
		createEOperation(tClassEClass, TCLASS___IS_POLYFILL);
		createEOperation(tClassEClass, TCLASS___IS_STATIC_POLYFILL);
		createEOperation(tClassEClass, TCLASS___IS_FINAL);

		tInterfaceEClass = createEClass(TINTERFACE);
		createEAttribute(tInterfaceEClass, TINTERFACE__EXTERNAL);
		createEReference(tInterfaceEClass, TINTERFACE__SUPER_INTERFACE_REFS);
		createEOperation(tInterfaceEClass, TINTERFACE___IS_ABSTRACT);
		createEOperation(tInterfaceEClass, TINTERFACE___GET_SUPER_CLASSIFIER_REFS);
		createEOperation(tInterfaceEClass, TINTERFACE___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS);

		tMemberEClass = createEClass(TMEMBER);
		createEAttribute(tMemberEClass, TMEMBER__DECLARED_FINAL);
		createEAttribute(tMemberEClass, TMEMBER__DECLARED_STATIC);
		createEAttribute(tMemberEClass, TMEMBER__DECLARED_OVERRIDE);
		createEAttribute(tMemberEClass, TMEMBER__HAS_COMPUTED_NAME);
		createEReference(tMemberEClass, TMEMBER__CONSTITUENT_MEMBERS);
		createEAttribute(tMemberEClass, TMEMBER__COMPOSED);
		createEOperation(tMemberEClass, TMEMBER___GET_CONTAINING_TYPE);
		createEOperation(tMemberEClass, TMEMBER___GET_MEMBER_ACCESS_MODIFIER);
		createEOperation(tMemberEClass, TMEMBER___GET_MEMBER_TYPE);
		createEOperation(tMemberEClass, TMEMBER___IS_FIELD);
		createEOperation(tMemberEClass, TMEMBER___IS_GETTER);
		createEOperation(tMemberEClass, TMEMBER___IS_SETTER);
		createEOperation(tMemberEClass, TMEMBER___IS_ACCESSOR);
		createEOperation(tMemberEClass, TMEMBER___IS_METHOD);
		createEOperation(tMemberEClass, TMEMBER___IS_CONSTRUCTOR);
		createEOperation(tMemberEClass, TMEMBER___IS_OPTIONAL);
		createEOperation(tMemberEClass, TMEMBER___IS_ABSTRACT);
		createEOperation(tMemberEClass, TMEMBER___IS_READABLE);
		createEOperation(tMemberEClass, TMEMBER___IS_WRITEABLE);
		createEOperation(tMemberEClass, TMEMBER___GET_MEMBER_AS_STRING);
		createEOperation(tMemberEClass, TMEMBER___IS_FINAL);
		createEOperation(tMemberEClass, TMEMBER___IS_STATIC);
		createEOperation(tMemberEClass, TMEMBER___IS_CONST);
		createEOperation(tMemberEClass, TMEMBER___IS_POLYFILLED);

		tMemberWithAccessModifierEClass = createEClass(TMEMBER_WITH_ACCESS_MODIFIER);
		createEAttribute(tMemberWithAccessModifierEClass, TMEMBER_WITH_ACCESS_MODIFIER__HAS_NO_BODY);
		createEAttribute(tMemberWithAccessModifierEClass, TMEMBER_WITH_ACCESS_MODIFIER__DECLARED_MEMBER_ACCESS_MODIFIER);
		createEOperation(tMemberWithAccessModifierEClass, TMEMBER_WITH_ACCESS_MODIFIER___GET_MEMBER_ACCESS_MODIFIER);

		tStructMemberEClass = createEClass(TSTRUCT_MEMBER);
		createEReference(tStructMemberEClass, TSTRUCT_MEMBER__DEFINED_MEMBER);
		createEOperation(tStructMemberEClass, TSTRUCT_MEMBER___GET_DEFAULT_MEMBER_ACCESS_MODIFIER);
		createEOperation(tStructMemberEClass, TSTRUCT_MEMBER___IS_STATIC);
		createEOperation(tStructMemberEClass, TSTRUCT_MEMBER___GET_MEMBER_ACCESS_MODIFIER);

		tMethodEClass = createEClass(TMETHOD);
		createEAttribute(tMethodEClass, TMETHOD__DECLARED_ABSTRACT);
		createEAttribute(tMethodEClass, TMETHOD__LACKS_THIS_OR_SUPER_USAGE);
		createEOperation(tMethodEClass, TMETHOD___IS_ABSTRACT);
		createEOperation(tMethodEClass, TMETHOD___GET_MEMBER_TYPE);
		createEOperation(tMethodEClass, TMETHOD___IS_CONSTRUCTOR);
		createEOperation(tMethodEClass, TMETHOD___GET_FUNCTION_AS_STRING);
		createEOperation(tMethodEClass, TMETHOD___GET_MEMBER_AS_STRING);

		tStructMethodEClass = createEClass(TSTRUCT_METHOD);

		tFormalParameterEClass = createEClass(TFORMAL_PARAMETER);
		createEAttribute(tFormalParameterEClass, TFORMAL_PARAMETER__VARIADIC);
		createEAttribute(tFormalParameterEClass, TFORMAL_PARAMETER__AST_INITIALIZER);
		createEAttribute(tFormalParameterEClass, TFORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT);
		createEOperation(tFormalParameterEClass, TFORMAL_PARAMETER___HAS_AST_INITIALIZER);
		createEOperation(tFormalParameterEClass, TFORMAL_PARAMETER___IS_OPTIONAL);
		createEOperation(tFormalParameterEClass, TFORMAL_PARAMETER___IS_VARIADIC_OR_OPTIONAL);
		createEOperation(tFormalParameterEClass, TFORMAL_PARAMETER___GET_FORMAL_PARAMETER_AS_TYPES_STRING);
		createEOperation(tFormalParameterEClass, TFORMAL_PARAMETER___GET_FORMAL_PARAMETER_AS_STRING);

		tAnonymousFormalParameterEClass = createEClass(TANONYMOUS_FORMAL_PARAMETER);
		createEOperation(tAnonymousFormalParameterEClass, TANONYMOUS_FORMAL_PARAMETER___GET_NAME);
		createEOperation(tAnonymousFormalParameterEClass, TANONYMOUS_FORMAL_PARAMETER___GET_DEFINED_NAME);

		tFieldEClass = createEClass(TFIELD);
		createEAttribute(tFieldEClass, TFIELD__HAS_EXPRESSION);
		createEAttribute(tFieldEClass, TFIELD__OPTIONAL);
		createEOperation(tFieldEClass, TFIELD___IS_STATIC);
		createEOperation(tFieldEClass, TFIELD___IS_READABLE);
		createEOperation(tFieldEClass, TFIELD___IS_WRITEABLE);
		createEOperation(tFieldEClass, TFIELD___GET_MEMBER_TYPE);
		createEOperation(tFieldEClass, TFIELD___GET_MEMBER_AS_STRING);

		tStructFieldEClass = createEClass(TSTRUCT_FIELD);

		fieldAccessorEClass = createEClass(FIELD_ACCESSOR);
		createEAttribute(fieldAccessorEClass, FIELD_ACCESSOR__OPTIONAL);
		createEAttribute(fieldAccessorEClass, FIELD_ACCESSOR__DECLARED_ABSTRACT);
		createEReference(fieldAccessorEClass, FIELD_ACCESSOR__DECLARED_THIS_TYPE);
		createEOperation(fieldAccessorEClass, FIELD_ACCESSOR___GET_DECLARED_TYPE_REF);
		createEOperation(fieldAccessorEClass, FIELD_ACCESSOR___IS_ABSTRACT);

		tGetterEClass = createEClass(TGETTER);
		createEReference(tGetterEClass, TGETTER__DECLARED_TYPE_REF);
		createEOperation(tGetterEClass, TGETTER___GET_MEMBER_TYPE);
		createEOperation(tGetterEClass, TGETTER___GET_MEMBER_AS_STRING);

		tStructGetterEClass = createEClass(TSTRUCT_GETTER);

		tSetterEClass = createEClass(TSETTER);
		createEReference(tSetterEClass, TSETTER__FPAR);
		createEOperation(tSetterEClass, TSETTER___GET_DECLARED_TYPE_REF);
		createEOperation(tSetterEClass, TSETTER___IS_READABLE);
		createEOperation(tSetterEClass, TSETTER___IS_WRITEABLE);
		createEOperation(tSetterEClass, TSETTER___GET_MEMBER_TYPE);
		createEOperation(tSetterEClass, TSETTER___GET_MEMBER_AS_STRING);

		tStructSetterEClass = createEClass(TSTRUCT_SETTER);
		createEOperation(tStructSetterEClass, TSTRUCT_SETTER___IS_WRITEABLE);

		tEnumEClass = createEClass(TENUM);
		createEAttribute(tEnumEClass, TENUM__EXTERNAL);
		createEReference(tEnumEClass, TENUM__LITERALS);
		createEOperation(tEnumEClass, TENUM___GET_TYPE_VARS);

		tEnumLiteralEClass = createEClass(TENUM_LITERAL);
		createEAttribute(tEnumLiteralEClass, TENUM_LITERAL__VALUE);
		createEOperation(tEnumLiteralEClass, TENUM_LITERAL___GET_VALUE_OR_NAME);

		syntaxRelatedTElementEClass = createEClass(SYNTAX_RELATED_TELEMENT);
		createEReference(syntaxRelatedTElementEClass, SYNTAX_RELATED_TELEMENT__AST_ELEMENT);

		tConstableElementEClass = createEClass(TCONSTABLE_ELEMENT);
		createEAttribute(tConstableElementEClass, TCONSTABLE_ELEMENT__CONST);
		createEAttribute(tConstableElementEClass, TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE);

		tVariableEClass = createEClass(TVARIABLE);
		createEAttribute(tVariableEClass, TVARIABLE__EXTERNAL);
		createEAttribute(tVariableEClass, TVARIABLE__OBJECT_LITERAL);
		createEAttribute(tVariableEClass, TVARIABLE__NEW_EXPRESSION);
		createEOperation(tVariableEClass, TVARIABLE___GET_VARIABLE_AS_STRING);

		// Create enums
		typingStrategyEEnum = createEEnum(TYPING_STRATEGY);
		typeAccessModifierEEnum = createEEnum(TYPE_ACCESS_MODIFIER);
		memberAccessModifierEEnum = createEEnum(MEMBER_ACCESS_MODIFIER);
		memberTypeEEnum = createEEnum(MEMBER_TYPE);

		// Create data types
		parameterizedTypeRefIterableEDataType = createEDataType(PARAMETERIZED_TYPE_REF_ITERABLE);
		iterableOfTClassifierEDataType = createEDataType(ITERABLE_OF_TCLASSIFIER);
		varianceEDataType = createEDataType(VARIANCE);
		nameAndAccessEDataType = createEDataType(NAME_AND_ACCESS);
		memberListEDataType = createEDataType(MEMBER_LIST);
		tClassifierIterableEDataType = createEDataType(TCLASSIFIER_ITERABLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		TypeRefsPackage theTypeRefsPackage = (TypeRefsPackage)EPackage.Registry.INSTANCE.getEPackage(TypeRefsPackage.eNS_URI);

		// Create type parameters
		ETypeParameter containerTypeEClass_MT = addETypeParameter(containerTypeEClass, "MT");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getTMember());
		containerTypeEClass_MT.getEBounds().add(g1);

		// Add supertypes to classes
		tModuleEClass.getESuperTypes().add(this.getSyntaxRelatedTElement());
		tModuleEClass.getESuperTypes().add(this.getTAnnotableElement());
		identifiableElementEClass.getESuperTypes().add(this.getTypableElement());
		tExportableElementEClass.getESuperTypes().add(this.getIdentifiableElement());
		tAnnotationStringArgumentEClass.getESuperTypes().add(this.getTAnnotationArgument());
		tAnnotationTypeRefArgumentEClass.getESuperTypes().add(this.getTAnnotationArgument());
		tAnnotationTypeRefArgumentEClass.getESuperTypes().add(this.getTTypedElement());
		typeVariableEClass.getESuperTypes().add(this.getType());
		inferenceVariableEClass.getESuperTypes().add(this.getTypeVariable());
		tFunctionEClass.getESuperTypes().add(this.getDeclaredTypeWithAccessModifier());
		tFunctionEClass.getESuperTypes().add(this.getSyntaxRelatedTElement());
		tFunctionEClass.getESuperTypes().add(this.getTVersionable());
		typeEClass.getESuperTypes().add(this.getTExportableElement());
		typeEClass.getESuperTypes().add(this.getTAnnotableElement());
		typeEClass.getESuperTypes().add(theTypeRefsPackage.getVersionable());
		declaredTypeWithAccessModifierEClass.getESuperTypes().add(this.getType());
		declaredTypeWithAccessModifierEClass.getESuperTypes().add(this.getAccessibleTypeElement());
		containerTypeEClass.getESuperTypes().add(this.getType());
		g1 = createEGenericType(this.getContainerType());
		EGenericType g2 = createEGenericType(this.getTMember());
		g1.getETypeArguments().add(g2);
		virtualBaseTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getArrayLike());
		virtualBaseTypeEClass.getEGenericSuperTypes().add(g1);
		moduleNamespaceVirtualTypeEClass.getESuperTypes().add(this.getType());
		moduleNamespaceVirtualTypeEClass.getESuperTypes().add(this.getSyntaxRelatedTElement());
		g1 = createEGenericType(this.getContainerType());
		g2 = createEGenericType(this.getTMember());
		g1.getETypeArguments().add(g2);
		primitiveTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getArrayLike());
		primitiveTypeEClass.getEGenericSuperTypes().add(g1);
		builtInTypeEClass.getESuperTypes().add(this.getType());
		anyTypeEClass.getESuperTypes().add(this.getBuiltInType());
		undefinedTypeEClass.getESuperTypes().add(this.getBuiltInType());
		nullTypeEClass.getESuperTypes().add(this.getBuiltInType());
		voidTypeEClass.getESuperTypes().add(this.getBuiltInType());
		g1 = createEGenericType(this.getContainerType());
		g2 = createEGenericType(this.getTStructMember());
		g1.getETypeArguments().add(g2);
		tStructuralTypeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getSyntaxRelatedTElement());
		tStructuralTypeEClass.getEGenericSuperTypes().add(g1);
		tVersionableEClass.getESuperTypes().add(this.getType());
		tMigrationEClass.getESuperTypes().add(this.getTFunction());
		g1 = createEGenericType(this.getContainerType());
		g2 = createEGenericType(this.getTMember());
		g1.getETypeArguments().add(g2);
		tClassifierEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getSyntaxRelatedTElement());
		tClassifierEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getTVersionable());
		tClassifierEClass.getEGenericSuperTypes().add(g1);
		tObjectPrototypeEClass.getESuperTypes().add(this.getTClassifier());
		tObjectPrototypeEClass.getESuperTypes().add(this.getDeclaredTypeWithAccessModifier());
		tObjectPrototypeEClass.getESuperTypes().add(this.getArrayLike());
		tn4ClassifierEClass.getESuperTypes().add(this.getTClassifier());
		tn4ClassifierEClass.getESuperTypes().add(this.getDeclaredTypeWithAccessModifier());
		tn4ClassifierEClass.getESuperTypes().add(this.getTMigratable());
		tClassEClass.getESuperTypes().add(this.getTN4Classifier());
		tInterfaceEClass.getESuperTypes().add(this.getTN4Classifier());
		tMemberEClass.getESuperTypes().add(this.getIdentifiableElement());
		tMemberEClass.getESuperTypes().add(this.getTAnnotableElement());
		tMemberEClass.getESuperTypes().add(this.getSyntaxRelatedTElement());
		tMemberWithAccessModifierEClass.getESuperTypes().add(this.getTMember());
		tStructMemberEClass.getESuperTypes().add(this.getTMember());
		tMethodEClass.getESuperTypes().add(this.getTFunction());
		tMethodEClass.getESuperTypes().add(this.getTMemberWithAccessModifier());
		tStructMethodEClass.getESuperTypes().add(this.getTMethod());
		tStructMethodEClass.getESuperTypes().add(this.getTStructMember());
		tFormalParameterEClass.getESuperTypes().add(this.getIdentifiableElement());
		tFormalParameterEClass.getESuperTypes().add(this.getTAnnotableElement());
		tFormalParameterEClass.getESuperTypes().add(this.getSyntaxRelatedTElement());
		tFormalParameterEClass.getESuperTypes().add(this.getTTypedElement());
		tAnonymousFormalParameterEClass.getESuperTypes().add(this.getTFormalParameter());
		tFieldEClass.getESuperTypes().add(this.getTMemberWithAccessModifier());
		tFieldEClass.getESuperTypes().add(this.getTTypedElement());
		tFieldEClass.getESuperTypes().add(this.getTConstableElement());
		tStructFieldEClass.getESuperTypes().add(this.getTField());
		tStructFieldEClass.getESuperTypes().add(this.getTStructMember());
		fieldAccessorEClass.getESuperTypes().add(this.getTMemberWithAccessModifier());
		tGetterEClass.getESuperTypes().add(this.getFieldAccessor());
		tGetterEClass.getESuperTypes().add(this.getTMemberWithAccessModifier());
		tStructGetterEClass.getESuperTypes().add(this.getTGetter());
		tStructGetterEClass.getESuperTypes().add(this.getTStructMember());
		tSetterEClass.getESuperTypes().add(this.getFieldAccessor());
		tSetterEClass.getESuperTypes().add(this.getTMemberWithAccessModifier());
		tStructSetterEClass.getESuperTypes().add(this.getTSetter());
		tStructSetterEClass.getESuperTypes().add(this.getTStructMember());
		tEnumEClass.getESuperTypes().add(this.getDeclaredTypeWithAccessModifier());
		tEnumEClass.getESuperTypes().add(this.getSyntaxRelatedTElement());
		tEnumEClass.getESuperTypes().add(this.getTVersionable());
		tEnumEClass.getESuperTypes().add(this.getTMigratable());
		tEnumLiteralEClass.getESuperTypes().add(this.getSyntaxRelatedTElement());
		tEnumLiteralEClass.getESuperTypes().add(this.getIdentifiableElement());
		tVariableEClass.getESuperTypes().add(this.getTExportableElement());
		tVariableEClass.getESuperTypes().add(this.getTConstableElement());
		tVariableEClass.getESuperTypes().add(this.getSyntaxRelatedTElement());
		tVariableEClass.getESuperTypes().add(this.getTAnnotableElement());
		tVariableEClass.getESuperTypes().add(this.getAccessibleTypeElement());
		tVariableEClass.getESuperTypes().add(this.getTTypedElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(typeDefsEClass, TypeDefs.class, "TypeDefs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeDefs_Types(), this.getType(), null, "types", null, 0, -1, TypeDefs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tModuleEClass, TModule.class, "TModule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTModule_SimpleName(), theEcorePackage.getEString(), "simpleName", null, 0, 1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_QualifiedName(), theEcorePackage.getEString(), "qualifiedName", null, 0, 1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_ProjectName(), theEcorePackage.getEString(), "projectName", null, 0, 1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_VendorID(), theEcorePackage.getEString(), "vendorID", null, 0, 1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_N4jsdModule(), theEcorePackage.getEBoolean(), "n4jsdModule", null, 0, 1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_StaticPolyfillModule(), theEcorePackage.getEBoolean(), "staticPolyfillModule", null, 0, 1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_StaticPolyfillAware(), theEcorePackage.getEBoolean(), "staticPolyfillAware", null, 0, 1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_MainModule(), theEcorePackage.getEBoolean(), "mainModule", null, 0, 1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_PreLinkingPhase(), theEcorePackage.getEBoolean(), "preLinkingPhase", null, 0, 1, TModule.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_Reconciled(), theEcorePackage.getEBoolean(), "reconciled", null, 0, 1, TModule.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_DependenciesRunTime(), this.getRunTimeDependency(), null, "dependenciesRunTime", null, 0, -1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_CyclicModulesRunTime(), this.getTModule(), null, "cyclicModulesRunTime", null, 0, -1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_CyclicModulesLoadTimeForInheritance(), this.getTModule(), null, "cyclicModulesLoadTimeForInheritance", null, 0, -1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_RunTimeCyclicLoadTimeDependents(), this.getTModule(), null, "runTimeCyclicLoadTimeDependents", null, 0, -1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_TopLevelTypes(), this.getType(), null, "topLevelTypes", null, 0, -1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_Variables(), this.getTVariable(), null, "variables", null, 0, -1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_InternalTypes(), this.getType(), null, "internalTypes", null, 0, -1, TModule.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_ExposedInternalTypes(), this.getType(), null, "exposedInternalTypes", null, 0, -1, TModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_AstMD5(), theEcorePackage.getEString(), "astMD5", null, 0, 1, TModule.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_ComposedMemberCaches(), this.getComposedMemberCache(), null, "composedMemberCaches", null, 0, -1, TModule.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTModule_TemporaryTypes(), this.getType(), null, "temporaryTypes", null, 0, -1, TModule.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTModule_ModuleSpecifier(), theEcorePackage.getEString(), "moduleSpecifier", null, 0, 1, TModule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getTModule__HasDirectLoadTimeDependencyTo__TModule(), theEcorePackage.getEBoolean(), "hasDirectLoadTimeDependencyTo", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getTModule(), "other", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(runTimeDependencyEClass, RunTimeDependency.class, "RunTimeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRunTimeDependency_Target(), this.getTModule(), null, "target", null, 0, 1, RunTimeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRunTimeDependency_LoadTimeForInheritance(), theEcorePackage.getEBoolean(), "loadTimeForInheritance", null, 0, 1, RunTimeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(composedMemberCacheEClass, ComposedMemberCache.class, "ComposedMemberCache", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComposedMemberCache_CachedComposedMembers(), this.getTMember(), null, "cachedComposedMembers", null, 0, -1, ComposedMemberCache.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComposedMemberCache_ComposedTypeRef(), theTypeRefsPackage.getTypeRef(), null, "composedTypeRef", null, 0, 1, ComposedMemberCache.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typableElementEClass, TypableElement.class, "TypableElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(identifiableElementEClass, IdentifiableElement.class, "IdentifiableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentifiableElement_Name(), theEcorePackage.getEString(), "name", null, 0, 1, IdentifiableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getIdentifiableElement__GetContainingModule(), this.getTModule(), "getContainingModule", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tExportableElementEClass, TExportableElement.class, "TExportableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTExportableElement_ExportedName(), theEcorePackage.getEString(), "exportedName", null, 0, 1, TExportableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTExportableElement__IsExported(), theEcorePackage.getEBoolean(), "isExported", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tAnnotationEClass, TAnnotation.class, "TAnnotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTAnnotation_Name(), theEcorePackage.getEString(), "name", null, 0, 1, TAnnotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTAnnotation_Args(), this.getTAnnotationArgument(), null, "args", null, 0, -1, TAnnotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getTAnnotation__HasStringArgument__String(), theEcorePackage.getEBoolean(), "hasStringArgument", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "argumentValue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTAnnotation__GetAnnotationAsString(), theEcorePackage.getEString(), "getAnnotationAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tAnnotationArgumentEClass, TAnnotationArgument.class, "TAnnotationArgument", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getTAnnotationArgument__GetArgAsString(), theEcorePackage.getEString(), "getArgAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tAnnotationStringArgumentEClass, TAnnotationStringArgument.class, "TAnnotationStringArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTAnnotationStringArgument_Value(), theEcorePackage.getEString(), "value", null, 0, 1, TAnnotationStringArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTAnnotationStringArgument__GetArgAsString(), theEcorePackage.getEString(), "getArgAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tTypedElementEClass, TTypedElement.class, "TTypedElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTTypedElement_TypeRef(), theTypeRefsPackage.getTypeRef(), null, "typeRef", null, 0, 1, TTypedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tAnnotationTypeRefArgumentEClass, TAnnotationTypeRefArgument.class, "TAnnotationTypeRefArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getTAnnotationTypeRefArgument__GetArgAsString(), theEcorePackage.getEString(), "getArgAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tAnnotableElementEClass, TAnnotableElement.class, "TAnnotableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTAnnotableElement_Annotations(), this.getTAnnotation(), null, "annotations", null, 0, -1, TAnnotableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeVariableEClass, TypeVariable.class, "TypeVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeVariable_DeclaredCovariant(), theEcorePackage.getEBoolean(), "declaredCovariant", null, 0, 1, TypeVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTypeVariable_DeclaredContravariant(), theEcorePackage.getEBoolean(), "declaredContravariant", null, 0, 1, TypeVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeVariable_DeclaredUpperBound(), theTypeRefsPackage.getTypeRef(), null, "declaredUpperBound", null, 0, 1, TypeVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeVariable_DefinedTypeVariable(), this.getTypeVariable(), null, "definedTypeVariable", null, 0, 1, TypeVariable.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTypeVariable__GetVariance(), this.getVariance(), "getVariance", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTypeVariable__GetTypeVars(), this.getTypeVariable(), "getTypeVars", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTypeVariable__GetTypeAsString(), theEcorePackage.getEString(), "getTypeAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTypeVariable__GetTypeVariableAsString__TypeRef(), theEcorePackage.getEString(), "getTypeVariableAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theTypeRefsPackage.getTypeRef(), "upperBound", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(inferenceVariableEClass, InferenceVariable.class, "InferenceVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tFunctionEClass, TFunction.class, "TFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTFunction_External(), theEcorePackage.getEBoolean(), "external", null, 0, 1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTFunction_Fpars(), this.getTFormalParameter(), null, "fpars", null, 0, -1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTFunction_ReturnValueMarkedOptional(), theEcorePackage.getEBoolean(), "returnValueMarkedOptional", null, 0, 1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTFunction_ReturnTypeRef(), theTypeRefsPackage.getTypeRef(), null, "returnTypeRef", null, 0, 1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTFunction_TypeVars(), this.getTypeVariable(), null, "typeVars", null, 0, -1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTFunction_DeclaredThisType(), theTypeRefsPackage.getTypeRef(), null, "declaredThisType", null, 0, 1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTFunction_DeclaredAsync(), theEcorePackage.getEBoolean(), "declaredAsync", null, 0, 1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTFunction_DeclaredGenerator(), theEcorePackage.getEBoolean(), "declaredGenerator", null, 0, 1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTFunction_Constructor(), theEcorePackage.getEBoolean(), "constructor", null, 0, 1, TFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTFunction__IsReturnValueOptional(), theEcorePackage.getEBoolean(), "isReturnValueOptional", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTFunction__IsCallableConstructor(), theEcorePackage.getEBoolean(), "isCallableConstructor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTFunction__GetFparForArgIdx__int(), this.getTFormalParameter(), "getFparForArgIdx", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "argIndex", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTFunction__GetFunctionAsString(), theEcorePackage.getEString(), "getFunctionAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTFunction__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(typeEClass, Type.class, "Type", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getType__IsProvidedByRuntime(), theEcorePackage.getEBoolean(), "isProvidedByRuntime", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__IsPolyfill(), theEcorePackage.getEBoolean(), "isPolyfill", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__IsStaticPolyfill(), theEcorePackage.getEBoolean(), "isStaticPolyfill", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__IsDynamizable(), theEcorePackage.getEBoolean(), "isDynamizable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__IsArrayLike(), theEcorePackage.getEBoolean(), "isArrayLike", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__GetElementType(), theTypeRefsPackage.getTypeRef(), "getElementType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__GetTypeAccessModifier(), this.getTypeAccessModifier(), "getTypeAccessModifier", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__IsGeneric(), theEcorePackage.getEBoolean(), "isGeneric", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__GetTypeVars(), this.getTypeVariable(), "getTypeVars", 0, -1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getType__GetVarianceOfTypeVar__int(), this.getVariance(), "getVarianceOfTypeVar", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "idx", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__GetRawTypeAsString(), theEcorePackage.getEString(), "getRawTypeAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getType__GetTypeAsString(), theEcorePackage.getEString(), "getTypeAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(accessibleTypeElementEClass, AccessibleTypeElement.class, "AccessibleTypeElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAccessibleTypeElement_DeclaredTypeAccessModifier(), this.getTypeAccessModifier(), "declaredTypeAccessModifier", null, 0, 1, AccessibleTypeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAccessibleTypeElement_DeclaredProvidedByRuntime(), theEcorePackage.getEBoolean(), "declaredProvidedByRuntime", null, 0, 1, AccessibleTypeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getAccessibleTypeElement__IsProvidedByRuntime(), theEcorePackage.getEBoolean(), "isProvidedByRuntime", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getAccessibleTypeElement__GetTypeAccessModifier(), this.getTypeAccessModifier(), "getTypeAccessModifier", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getAccessibleTypeElement__IsExported(), theEcorePackage.getEBoolean(), "isExported", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(declaredTypeWithAccessModifierEClass, DeclaredTypeWithAccessModifier.class, "DeclaredTypeWithAccessModifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(containerTypeEClass, ContainerType.class, "ContainerType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theEcorePackage.getEMap());
		g2 = createEGenericType(this.getNameAndAccess());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(this.getTMember());
		g2.setEUpperBound(g3);
		initEAttribute(getContainerType_OwnedMembersByNameAndAccess(), g1, "ownedMembersByNameAndAccess", null, 0, 1, ContainerType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(containerTypeEClass_MT);
		initEReference(getContainerType_OwnedMembers(), g1, null, "ownedMembers", null, 0, -1, ContainerType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContainerType_CallableCtor(), this.getTMethod(), null, "callableCtor", null, 0, 1, ContainerType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContainerType_TypeVars(), this.getTypeVariable(), null, "typeVars", null, 0, -1, ContainerType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getContainerType__GetOwnedCtor(), this.getTMethod(), "getOwnedCtor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getContainerType__FindOwnedMember__String(), this.getTMember(), "findOwnedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "name", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getContainerType__FindOwnedMember__String_boolean_boolean(), this.getTMember(), "findOwnedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "name", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEBoolean(), "writeAccess", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEBoolean(), "staticAccess", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getContainerType__GetOrCreateOwnedMembersByNameAndAccess(), null, "getOrCreateOwnedMembersByNameAndAccess", 0, 1, !IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(theEcorePackage.getEMap());
		g2 = createEGenericType(this.getNameAndAccess());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType(this.getTMember());
		g2.setEUpperBound(g3);
		initEOperation(op, g1);

		initEClass(virtualBaseTypeEClass, VirtualBaseType.class, "VirtualBaseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVirtualBaseType_DeclaredOwnedMembers(), this.getTMember(), null, "declaredOwnedMembers", null, 0, -1, VirtualBaseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(moduleNamespaceVirtualTypeEClass, ModuleNamespaceVirtualType.class, "ModuleNamespaceVirtualType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModuleNamespaceVirtualType_Module(), this.getTModule(), null, "module", null, 0, 1, ModuleNamespaceVirtualType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModuleNamespaceVirtualType_DeclaredDynamic(), theEcorePackage.getEBoolean(), "declaredDynamic", null, 0, 1, ModuleNamespaceVirtualType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getModuleNamespaceVirtualType__IsProvidedByRuntime(), theEcorePackage.getEBoolean(), "isProvidedByRuntime", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(primitiveTypeEClass, PrimitiveType.class, "PrimitiveType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPrimitiveType_AssignmentCompatible(), this.getPrimitiveType(), null, "assignmentCompatible", null, 0, 1, PrimitiveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrimitiveType_AutoboxedType(), this.getTClassifier(), null, "autoboxedType", null, 0, 1, PrimitiveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(builtInTypeEClass, BuiltInType.class, "BuiltInType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getBuiltInType__GetTypeVars(), this.getTypeVariable(), "getTypeVars", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(anyTypeEClass, AnyType.class, "AnyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getAnyType__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(undefinedTypeEClass, UndefinedType.class, "UndefinedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nullTypeEClass, NullType.class, "NullType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(voidTypeEClass, VoidType.class, "VoidType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tStructuralTypeEClass, TStructuralType.class, "TStructuralType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getTStructuralType__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tVersionableEClass, TVersionable.class, "TVersionable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTVersionable_DeclaredVersion(), theEcorePackage.getEInt(), "declaredVersion", null, 0, 1, TVersionable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTVersionable__GetVersion(), theEcorePackage.getEInt(), "getVersion", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tMigratableEClass, TMigratable.class, "TMigratable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTMigratable_Migrations(), this.getTMigration(), null, "migrations", null, 0, -1, TMigratable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tMigrationEClass, TMigration.class, "TMigration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTMigration_SourceVersion(), theEcorePackage.getEInt(), "sourceVersion", null, 0, 1, TMigration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTMigration_TargetVersion(), theEcorePackage.getEInt(), "targetVersion", null, 0, 1, TMigration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTMigration_HasDeclaredSourceAndTargetVersion(), theEcorePackage.getEBoolean(), "hasDeclaredSourceAndTargetVersion", null, 0, 1, TMigration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTMigration_SourceTypeRefs(), theTypeRefsPackage.getTypeRef(), null, "sourceTypeRefs", null, 0, -1, TMigration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTMigration_TargetTypeRefs(), theTypeRefsPackage.getTypeRef(), null, "targetTypeRefs", null, 0, -1, TMigration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTMigration__principalArgumentType(), this.getTMigratable(), null, "_principalArgumentType", null, 0, 1, TMigration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTMigration__GetPrincipalArgumentType(), this.getTMigratable(), "getPrincipalArgumentType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMigration__GetMigrationAsString(), theEcorePackage.getEString(), "getMigrationAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tClassifierEClass, TClassifier.class, "TClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTClassifier_DeclaredCovariantConstructor(), theEcorePackage.getEBoolean(), "declaredCovariantConstructor", null, 0, 1, TClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTClassifier__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClassifier__GetSuperClassifiers(), this.getIterableOfTClassifier(), "getSuperClassifiers", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClassifier__GetSuperClassifierRefs(), this.getParameterizedTypeRefIterable(), "getSuperClassifierRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClassifier__GetImplementedOrExtendedInterfaceRefs(), this.getParameterizedTypeRefIterable(), "getImplementedOrExtendedInterfaceRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClassifier__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tObjectPrototypeEClass, TObjectPrototype.class, "TObjectPrototype", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTObjectPrototype_SuperType(), theTypeRefsPackage.getParameterizedTypeRef(), null, "superType", null, 0, 1, TObjectPrototype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTObjectPrototype_DeclaredFinal(), theEcorePackage.getEBoolean(), "declaredFinal", null, 0, 1, TObjectPrototype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTObjectPrototype__GetOwnedCtor(), this.getTMethod(), "getOwnedCtor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTObjectPrototype__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(arrayLikeEClass, ArrayLike.class, "ArrayLike", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArrayLike_DeclaredElementType(), theTypeRefsPackage.getTypeRef(), null, "declaredElementType", null, 0, 1, ArrayLike.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getArrayLike__GetElementType(), theTypeRefsPackage.getTypeRef(), "getElementType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tn4ClassifierEClass, TN4Classifier.class, "TN4Classifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTN4Classifier_Dynamizable(), theEcorePackage.getEBoolean(), "dynamizable", "true", 0, 1, TN4Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTN4Classifier_TypingStrategy(), this.getTypingStrategy(), "typingStrategy", null, 0, 1, TN4Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tClassEClass, TClass.class, "TClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTClass_External(), theEcorePackage.getEBoolean(), "external", null, 0, 1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTClass_DeclaredAbstract(), theEcorePackage.getEBoolean(), "declaredAbstract", null, 0, 1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTClass_DeclaredN4JS(), theEcorePackage.getEBoolean(), "declaredN4JS", null, 0, 1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTClass_DeclaredFinal(), theEcorePackage.getEBoolean(), "declaredFinal", null, 0, 1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTClass_DeclaredPolyfill(), theEcorePackage.getEBoolean(), "declaredPolyfill", null, 0, 1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTClass_DeclaredStaticPolyfill(), theEcorePackage.getEBoolean(), "declaredStaticPolyfill", null, 0, 1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTClass_Observable(), theEcorePackage.getEBoolean(), "observable", null, 0, 1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTClass_SuperClassRef(), theTypeRefsPackage.getParameterizedTypeRef(), null, "superClassRef", null, 0, 1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTClass_ImplementedInterfaceRefs(), theTypeRefsPackage.getParameterizedTypeRef(), null, "implementedInterfaceRefs", null, 0, -1, TClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTClass__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClass__GetSuperClass(), this.getTClass(), "getSuperClass", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClass__GetSuperClassifierRefs(), this.getParameterizedTypeRefIterable(), "getSuperClassifierRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClass__GetImplementedOrExtendedInterfaceRefs(), this.getParameterizedTypeRefIterable(), "getImplementedOrExtendedInterfaceRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClass__IsPolyfill(), theEcorePackage.getEBoolean(), "isPolyfill", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClass__IsStaticPolyfill(), theEcorePackage.getEBoolean(), "isStaticPolyfill", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTClass__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tInterfaceEClass, TInterface.class, "TInterface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTInterface_External(), theEcorePackage.getEBoolean(), "external", null, 0, 1, TInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTInterface_SuperInterfaceRefs(), theTypeRefsPackage.getParameterizedTypeRef(), null, "superInterfaceRefs", null, 0, -1, TInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTInterface__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTInterface__GetSuperClassifierRefs(), this.getParameterizedTypeRefIterable(), "getSuperClassifierRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTInterface__GetImplementedOrExtendedInterfaceRefs(), this.getParameterizedTypeRefIterable(), "getImplementedOrExtendedInterfaceRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tMemberEClass, TMember.class, "TMember", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTMember_DeclaredFinal(), theEcorePackage.getEBoolean(), "declaredFinal", null, 0, 1, TMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTMember_DeclaredStatic(), theEcorePackage.getEBoolean(), "declaredStatic", null, 0, 1, TMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTMember_DeclaredOverride(), theEcorePackage.getEBoolean(), "declaredOverride", null, 0, 1, TMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTMember_HasComputedName(), theEcorePackage.getEBoolean(), "hasComputedName", null, 0, 1, TMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTMember_ConstituentMembers(), this.getTMember(), null, "constituentMembers", null, 0, -1, TMember.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTMember_Composed(), theEcorePackage.getEBoolean(), "composed", null, 0, 1, TMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getTMember__GetContainingType(), null, "getContainingType", 0, 1, !IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(this.getContainerType());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEOperation(getTMember__GetMemberAccessModifier(), this.getMemberAccessModifier(), "getMemberAccessModifier", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__GetMemberType(), this.getMemberType(), "getMemberType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsField(), theEcorePackage.getEBoolean(), "isField", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsGetter(), theEcorePackage.getEBoolean(), "isGetter", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsSetter(), theEcorePackage.getEBoolean(), "isSetter", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsAccessor(), theEcorePackage.getEBoolean(), "isAccessor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsMethod(), theEcorePackage.getEBoolean(), "isMethod", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsConstructor(), theEcorePackage.getEBoolean(), "isConstructor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsOptional(), theEcorePackage.getEBoolean(), "isOptional", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsReadable(), theEcorePackage.getEBoolean(), "isReadable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsWriteable(), theEcorePackage.getEBoolean(), "isWriteable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__GetMemberAsString(), theEcorePackage.getEString(), "getMemberAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsStatic(), theEcorePackage.getEBoolean(), "isStatic", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsConst(), theEcorePackage.getEBoolean(), "isConst", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMember__IsPolyfilled(), theEcorePackage.getEBoolean(), "isPolyfilled", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tMemberWithAccessModifierEClass, TMemberWithAccessModifier.class, "TMemberWithAccessModifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTMemberWithAccessModifier_HasNoBody(), theEcorePackage.getEBoolean(), "hasNoBody", null, 0, 1, TMemberWithAccessModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTMemberWithAccessModifier_DeclaredMemberAccessModifier(), this.getMemberAccessModifier(), "declaredMemberAccessModifier", null, 0, 1, TMemberWithAccessModifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTMemberWithAccessModifier__GetMemberAccessModifier(), this.getMemberAccessModifier(), "getMemberAccessModifier", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tStructMemberEClass, TStructMember.class, "TStructMember", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTStructMember_DefinedMember(), this.getTStructMember(), null, "definedMember", null, 0, 1, TStructMember.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTStructMember__GetDefaultMemberAccessModifier(), this.getMemberAccessModifier(), "getDefaultMemberAccessModifier", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTStructMember__IsStatic(), theEcorePackage.getEBoolean(), "isStatic", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTStructMember__GetMemberAccessModifier(), this.getMemberAccessModifier(), "getMemberAccessModifier", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tMethodEClass, TMethod.class, "TMethod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTMethod_DeclaredAbstract(), theEcorePackage.getEBoolean(), "declaredAbstract", null, 0, 1, TMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTMethod_LacksThisOrSuperUsage(), theEcorePackage.getEBoolean(), "lacksThisOrSuperUsage", null, 0, 1, TMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTMethod__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMethod__GetMemberType(), this.getMemberType(), "getMemberType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMethod__IsConstructor(), theEcorePackage.getEBoolean(), "isConstructor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMethod__GetFunctionAsString(), theEcorePackage.getEString(), "getFunctionAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTMethod__GetMemberAsString(), theEcorePackage.getEString(), "getMemberAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tStructMethodEClass, TStructMethod.class, "TStructMethod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tFormalParameterEClass, TFormalParameter.class, "TFormalParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTFormalParameter_Variadic(), theEcorePackage.getEBoolean(), "variadic", null, 0, 1, TFormalParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTFormalParameter_AstInitializer(), theEcorePackage.getEString(), "astInitializer", null, 0, 1, TFormalParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTFormalParameter_HasInitializerAssignment(), theEcorePackage.getEBoolean(), "hasInitializerAssignment", null, 0, 1, TFormalParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTFormalParameter__HasASTInitializer(), theEcorePackage.getEBoolean(), "hasASTInitializer", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTFormalParameter__IsOptional(), theEcorePackage.getEBoolean(), "isOptional", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTFormalParameter__IsVariadicOrOptional(), theEcorePackage.getEBoolean(), "isVariadicOrOptional", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTFormalParameter__GetFormalParameterAsTypesString(), theEcorePackage.getEString(), "getFormalParameterAsTypesString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTFormalParameter__GetFormalParameterAsString(), theEcorePackage.getEString(), "getFormalParameterAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tAnonymousFormalParameterEClass, TAnonymousFormalParameter.class, "TAnonymousFormalParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getTAnonymousFormalParameter__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTAnonymousFormalParameter__GetDefinedName(), theEcorePackage.getEString(), "getDefinedName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tFieldEClass, TField.class, "TField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTField_HasExpression(), theEcorePackage.getEBoolean(), "hasExpression", null, 0, 1, TField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTField_Optional(), theEcorePackage.getEBoolean(), "optional", null, 0, 1, TField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTField__IsStatic(), theEcorePackage.getEBoolean(), "isStatic", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTField__IsReadable(), theEcorePackage.getEBoolean(), "isReadable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTField__IsWriteable(), theEcorePackage.getEBoolean(), "isWriteable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTField__GetMemberType(), this.getMemberType(), "getMemberType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTField__GetMemberAsString(), theEcorePackage.getEString(), "getMemberAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tStructFieldEClass, TStructField.class, "TStructField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(fieldAccessorEClass, FieldAccessor.class, "FieldAccessor", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFieldAccessor_Optional(), theEcorePackage.getEBoolean(), "optional", null, 0, 1, FieldAccessor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldAccessor_DeclaredAbstract(), theEcorePackage.getEBoolean(), "declaredAbstract", null, 0, 1, FieldAccessor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldAccessor_DeclaredThisType(), theTypeRefsPackage.getTypeRef(), null, "declaredThisType", null, 0, 1, FieldAccessor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFieldAccessor__GetDeclaredTypeRef(), theTypeRefsPackage.getTypeRef(), "getDeclaredTypeRef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFieldAccessor__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tGetterEClass, TGetter.class, "TGetter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGetter_DeclaredTypeRef(), theTypeRefsPackage.getTypeRef(), null, "declaredTypeRef", null, 0, 1, TGetter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTGetter__GetMemberType(), this.getMemberType(), "getMemberType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTGetter__GetMemberAsString(), theEcorePackage.getEString(), "getMemberAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tStructGetterEClass, TStructGetter.class, "TStructGetter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tSetterEClass, TSetter.class, "TSetter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTSetter_Fpar(), this.getTFormalParameter(), null, "fpar", null, 0, 1, TSetter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTSetter__GetDeclaredTypeRef(), theTypeRefsPackage.getTypeRef(), "getDeclaredTypeRef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTSetter__IsReadable(), theEcorePackage.getEBoolean(), "isReadable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTSetter__IsWriteable(), theEcorePackage.getEBoolean(), "isWriteable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTSetter__GetMemberType(), this.getMemberType(), "getMemberType", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTSetter__GetMemberAsString(), theEcorePackage.getEString(), "getMemberAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tStructSetterEClass, TStructSetter.class, "TStructSetter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getTStructSetter__IsWriteable(), theEcorePackage.getEBoolean(), "isWriteable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tEnumEClass, TEnum.class, "TEnum", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTEnum_External(), theEcorePackage.getEBoolean(), "external", null, 0, 1, TEnum.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTEnum_Literals(), this.getTEnumLiteral(), null, "literals", null, 0, -1, TEnum.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTEnum__GetTypeVars(), this.getTypeVariable(), "getTypeVars", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tEnumLiteralEClass, TEnumLiteral.class, "TEnumLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTEnumLiteral_Value(), theEcorePackage.getEString(), "value", null, 0, 1, TEnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTEnumLiteral__GetValueOrName(), theEcorePackage.getEString(), "getValueOrName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(syntaxRelatedTElementEClass, SyntaxRelatedTElement.class, "SyntaxRelatedTElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSyntaxRelatedTElement_AstElement(), theEcorePackage.getEObject(), null, "astElement", null, 0, 1, SyntaxRelatedTElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tConstableElementEClass, TConstableElement.class, "TConstableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTConstableElement_Const(), theEcorePackage.getEBoolean(), "const", null, 0, 1, TConstableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTConstableElement_CompileTimeValue(), theEcorePackage.getEString(), "compileTimeValue", null, 0, 1, TConstableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tVariableEClass, TVariable.class, "TVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTVariable_External(), theEcorePackage.getEBoolean(), "external", null, 0, 1, TVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTVariable_ObjectLiteral(), theEcorePackage.getEBoolean(), "objectLiteral", null, 0, 1, TVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTVariable_NewExpression(), theEcorePackage.getEBoolean(), "newExpression", null, 0, 1, TVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTVariable__GetVariableAsString(), theEcorePackage.getEString(), "getVariableAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(typingStrategyEEnum, TypingStrategy.class, "TypingStrategy");
		addEEnumLiteral(typingStrategyEEnum, TypingStrategy.DEFAULT);
		addEEnumLiteral(typingStrategyEEnum, TypingStrategy.NOMINAL);
		addEEnumLiteral(typingStrategyEEnum, TypingStrategy.STRUCTURAL);
		addEEnumLiteral(typingStrategyEEnum, TypingStrategy.STRUCTURAL_FIELDS);
		addEEnumLiteral(typingStrategyEEnum, TypingStrategy.STRUCTURAL_READ_ONLY_FIELDS);
		addEEnumLiteral(typingStrategyEEnum, TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS);
		addEEnumLiteral(typingStrategyEEnum, TypingStrategy.STRUCTURAL_FIELD_INITIALIZER);
		addEEnumLiteral(typingStrategyEEnum, TypingStrategy.EMPTY);

		initEEnum(typeAccessModifierEEnum, TypeAccessModifier.class, "TypeAccessModifier");
		addEEnumLiteral(typeAccessModifierEEnum, TypeAccessModifier.UNDEFINED);
		addEEnumLiteral(typeAccessModifierEEnum, TypeAccessModifier.PRIVATE);
		addEEnumLiteral(typeAccessModifierEEnum, TypeAccessModifier.PROJECT);
		addEEnumLiteral(typeAccessModifierEEnum, TypeAccessModifier.PUBLIC_INTERNAL);
		addEEnumLiteral(typeAccessModifierEEnum, TypeAccessModifier.PUBLIC);

		initEEnum(memberAccessModifierEEnum, MemberAccessModifier.class, "MemberAccessModifier");
		addEEnumLiteral(memberAccessModifierEEnum, MemberAccessModifier.UNDEFINED);
		addEEnumLiteral(memberAccessModifierEEnum, MemberAccessModifier.PRIVATE);
		addEEnumLiteral(memberAccessModifierEEnum, MemberAccessModifier.PROJECT);
		addEEnumLiteral(memberAccessModifierEEnum, MemberAccessModifier.PROTECTED_INTERNAL);
		addEEnumLiteral(memberAccessModifierEEnum, MemberAccessModifier.PROTECTED);
		addEEnumLiteral(memberAccessModifierEEnum, MemberAccessModifier.PUBLIC_INTERNAL);
		addEEnumLiteral(memberAccessModifierEEnum, MemberAccessModifier.PUBLIC);

		initEEnum(memberTypeEEnum, MemberType.class, "MemberType");
		addEEnumLiteral(memberTypeEEnum, MemberType.GETTER);
		addEEnumLiteral(memberTypeEEnum, MemberType.SETTER);
		addEEnumLiteral(memberTypeEEnum, MemberType.FIELD);
		addEEnumLiteral(memberTypeEEnum, MemberType.METHOD);

		// Initialize data types
		initEDataType(parameterizedTypeRefIterableEDataType, Iterable.class, "ParameterizedTypeRefIterable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.lang.Iterable<org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef>");
		initEDataType(iterableOfTClassifierEDataType, Iterable.class, "IterableOfTClassifier", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.lang.Iterable<? extends org.eclipse.n4js.ts.types.TClassifier>");
		initEDataType(varianceEDataType, Variance.class, "Variance", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameAndAccessEDataType, NameAndAccess.class, "NameAndAccess", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(memberListEDataType, List.class, "MemberList", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.List<? extends org.eclipse.n4js.ts.types.TMember>");
		initEDataType(tClassifierIterableEDataType, Iterable.class, "TClassifierIterable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.lang.Iterable<org.eclipse.n4js.ts.types.TClassifier>");

		// Create resource
		createResource(eNS_URI);
	}

} //TypesPackageImpl
