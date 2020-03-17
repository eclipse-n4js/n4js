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
package org.eclipse.n4js.ts.types.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.ts.types.TypesPackage
 * @generated
 */
public class TypesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesSwitch() {
		if (modelPackage == null) {
			modelPackage = TypesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TypesPackage.TYPE_DEFS: {
				TypeDefs typeDefs = (TypeDefs)theEObject;
				T result = caseTypeDefs(typeDefs);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TMODULE: {
				TModule tModule = (TModule)theEObject;
				T result = caseTModule(tModule);
				if (result == null) result = caseSyntaxRelatedTElement(tModule);
				if (result == null) result = caseTAnnotableElement(tModule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.RUNTIME_DEPENDENCY: {
				RuntimeDependency runtimeDependency = (RuntimeDependency)theEObject;
				T result = caseRuntimeDependency(runtimeDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.COMPOSED_MEMBER_CACHE: {
				ComposedMemberCache composedMemberCache = (ComposedMemberCache)theEObject;
				T result = caseComposedMemberCache(composedMemberCache);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TYPABLE_ELEMENT: {
				TypableElement typableElement = (TypableElement)theEObject;
				T result = caseTypableElement(typableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.IDENTIFIABLE_ELEMENT: {
				IdentifiableElement identifiableElement = (IdentifiableElement)theEObject;
				T result = caseIdentifiableElement(identifiableElement);
				if (result == null) result = caseTypableElement(identifiableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TEXPORTABLE_ELEMENT: {
				TExportableElement tExportableElement = (TExportableElement)theEObject;
				T result = caseTExportableElement(tExportableElement);
				if (result == null) result = caseIdentifiableElement(tExportableElement);
				if (result == null) result = caseTypableElement(tExportableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TANNOTATION: {
				TAnnotation tAnnotation = (TAnnotation)theEObject;
				T result = caseTAnnotation(tAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TANNOTATION_ARGUMENT: {
				TAnnotationArgument tAnnotationArgument = (TAnnotationArgument)theEObject;
				T result = caseTAnnotationArgument(tAnnotationArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TANNOTATION_STRING_ARGUMENT: {
				TAnnotationStringArgument tAnnotationStringArgument = (TAnnotationStringArgument)theEObject;
				T result = caseTAnnotationStringArgument(tAnnotationStringArgument);
				if (result == null) result = caseTAnnotationArgument(tAnnotationStringArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TTYPED_ELEMENT: {
				TTypedElement tTypedElement = (TTypedElement)theEObject;
				T result = caseTTypedElement(tTypedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TANNOTATION_TYPE_REF_ARGUMENT: {
				TAnnotationTypeRefArgument tAnnotationTypeRefArgument = (TAnnotationTypeRefArgument)theEObject;
				T result = caseTAnnotationTypeRefArgument(tAnnotationTypeRefArgument);
				if (result == null) result = caseTAnnotationArgument(tAnnotationTypeRefArgument);
				if (result == null) result = caseTTypedElement(tAnnotationTypeRefArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TANNOTABLE_ELEMENT: {
				TAnnotableElement tAnnotableElement = (TAnnotableElement)theEObject;
				T result = caseTAnnotableElement(tAnnotableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TYPE_VARIABLE: {
				TypeVariable typeVariable = (TypeVariable)theEObject;
				T result = caseTypeVariable(typeVariable);
				if (result == null) result = caseType(typeVariable);
				if (result == null) result = caseTExportableElement(typeVariable);
				if (result == null) result = caseTAnnotableElement(typeVariable);
				if (result == null) result = caseVersionable(typeVariable);
				if (result == null) result = caseIdentifiableElement(typeVariable);
				if (result == null) result = caseTypableElement(typeVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INFERENCE_VARIABLE: {
				InferenceVariable inferenceVariable = (InferenceVariable)theEObject;
				T result = caseInferenceVariable(inferenceVariable);
				if (result == null) result = caseTypeVariable(inferenceVariable);
				if (result == null) result = caseType(inferenceVariable);
				if (result == null) result = caseTExportableElement(inferenceVariable);
				if (result == null) result = caseTAnnotableElement(inferenceVariable);
				if (result == null) result = caseVersionable(inferenceVariable);
				if (result == null) result = caseIdentifiableElement(inferenceVariable);
				if (result == null) result = caseTypableElement(inferenceVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TFUNCTION: {
				TFunction tFunction = (TFunction)theEObject;
				T result = caseTFunction(tFunction);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tFunction);
				if (result == null) result = caseSyntaxRelatedTElement(tFunction);
				if (result == null) result = caseTVersionable(tFunction);
				if (result == null) result = caseType(tFunction);
				if (result == null) result = caseAccessibleTypeElement(tFunction);
				if (result == null) result = caseTExportableElement(tFunction);
				if (result == null) result = caseTAnnotableElement(tFunction);
				if (result == null) result = caseVersionable(tFunction);
				if (result == null) result = caseIdentifiableElement(tFunction);
				if (result == null) result = caseTypableElement(tFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TYPE: {
				Type type = (Type)theEObject;
				T result = caseType(type);
				if (result == null) result = caseTExportableElement(type);
				if (result == null) result = caseTAnnotableElement(type);
				if (result == null) result = caseVersionable(type);
				if (result == null) result = caseIdentifiableElement(type);
				if (result == null) result = caseTypableElement(type);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ACCESSIBLE_TYPE_ELEMENT: {
				AccessibleTypeElement accessibleTypeElement = (AccessibleTypeElement)theEObject;
				T result = caseAccessibleTypeElement(accessibleTypeElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DECLARED_TYPE_WITH_ACCESS_MODIFIER: {
				DeclaredTypeWithAccessModifier declaredTypeWithAccessModifier = (DeclaredTypeWithAccessModifier)theEObject;
				T result = caseDeclaredTypeWithAccessModifier(declaredTypeWithAccessModifier);
				if (result == null) result = caseType(declaredTypeWithAccessModifier);
				if (result == null) result = caseAccessibleTypeElement(declaredTypeWithAccessModifier);
				if (result == null) result = caseTExportableElement(declaredTypeWithAccessModifier);
				if (result == null) result = caseTAnnotableElement(declaredTypeWithAccessModifier);
				if (result == null) result = caseVersionable(declaredTypeWithAccessModifier);
				if (result == null) result = caseIdentifiableElement(declaredTypeWithAccessModifier);
				if (result == null) result = caseTypableElement(declaredTypeWithAccessModifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.CONTAINER_TYPE: {
				ContainerType<?> containerType = (ContainerType<?>)theEObject;
				T result = caseContainerType(containerType);
				if (result == null) result = caseType(containerType);
				if (result == null) result = caseTExportableElement(containerType);
				if (result == null) result = caseTAnnotableElement(containerType);
				if (result == null) result = caseVersionable(containerType);
				if (result == null) result = caseIdentifiableElement(containerType);
				if (result == null) result = caseTypableElement(containerType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.VIRTUAL_BASE_TYPE: {
				VirtualBaseType virtualBaseType = (VirtualBaseType)theEObject;
				T result = caseVirtualBaseType(virtualBaseType);
				if (result == null) result = caseContainerType(virtualBaseType);
				if (result == null) result = caseArrayLike(virtualBaseType);
				if (result == null) result = caseType(virtualBaseType);
				if (result == null) result = caseTExportableElement(virtualBaseType);
				if (result == null) result = caseTAnnotableElement(virtualBaseType);
				if (result == null) result = caseVersionable(virtualBaseType);
				if (result == null) result = caseIdentifiableElement(virtualBaseType);
				if (result == null) result = caseTypableElement(virtualBaseType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.MODULE_NAMESPACE_VIRTUAL_TYPE: {
				ModuleNamespaceVirtualType moduleNamespaceVirtualType = (ModuleNamespaceVirtualType)theEObject;
				T result = caseModuleNamespaceVirtualType(moduleNamespaceVirtualType);
				if (result == null) result = caseType(moduleNamespaceVirtualType);
				if (result == null) result = caseSyntaxRelatedTElement(moduleNamespaceVirtualType);
				if (result == null) result = caseTExportableElement(moduleNamespaceVirtualType);
				if (result == null) result = caseTAnnotableElement(moduleNamespaceVirtualType);
				if (result == null) result = caseVersionable(moduleNamespaceVirtualType);
				if (result == null) result = caseIdentifiableElement(moduleNamespaceVirtualType);
				if (result == null) result = caseTypableElement(moduleNamespaceVirtualType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.PRIMITIVE_TYPE: {
				PrimitiveType primitiveType = (PrimitiveType)theEObject;
				T result = casePrimitiveType(primitiveType);
				if (result == null) result = caseContainerType(primitiveType);
				if (result == null) result = caseArrayLike(primitiveType);
				if (result == null) result = caseType(primitiveType);
				if (result == null) result = caseTExportableElement(primitiveType);
				if (result == null) result = caseTAnnotableElement(primitiveType);
				if (result == null) result = caseVersionable(primitiveType);
				if (result == null) result = caseIdentifiableElement(primitiveType);
				if (result == null) result = caseTypableElement(primitiveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.BUILT_IN_TYPE: {
				BuiltInType builtInType = (BuiltInType)theEObject;
				T result = caseBuiltInType(builtInType);
				if (result == null) result = caseType(builtInType);
				if (result == null) result = caseTExportableElement(builtInType);
				if (result == null) result = caseTAnnotableElement(builtInType);
				if (result == null) result = caseVersionable(builtInType);
				if (result == null) result = caseIdentifiableElement(builtInType);
				if (result == null) result = caseTypableElement(builtInType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ANY_TYPE: {
				AnyType anyType = (AnyType)theEObject;
				T result = caseAnyType(anyType);
				if (result == null) result = caseBuiltInType(anyType);
				if (result == null) result = caseType(anyType);
				if (result == null) result = caseTExportableElement(anyType);
				if (result == null) result = caseTAnnotableElement(anyType);
				if (result == null) result = caseVersionable(anyType);
				if (result == null) result = caseIdentifiableElement(anyType);
				if (result == null) result = caseTypableElement(anyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UNDEFINED_TYPE: {
				UndefinedType undefinedType = (UndefinedType)theEObject;
				T result = caseUndefinedType(undefinedType);
				if (result == null) result = caseBuiltInType(undefinedType);
				if (result == null) result = caseType(undefinedType);
				if (result == null) result = caseTExportableElement(undefinedType);
				if (result == null) result = caseTAnnotableElement(undefinedType);
				if (result == null) result = caseVersionable(undefinedType);
				if (result == null) result = caseIdentifiableElement(undefinedType);
				if (result == null) result = caseTypableElement(undefinedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.NULL_TYPE: {
				NullType nullType = (NullType)theEObject;
				T result = caseNullType(nullType);
				if (result == null) result = caseBuiltInType(nullType);
				if (result == null) result = caseType(nullType);
				if (result == null) result = caseTExportableElement(nullType);
				if (result == null) result = caseTAnnotableElement(nullType);
				if (result == null) result = caseVersionable(nullType);
				if (result == null) result = caseIdentifiableElement(nullType);
				if (result == null) result = caseTypableElement(nullType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.VOID_TYPE: {
				VoidType voidType = (VoidType)theEObject;
				T result = caseVoidType(voidType);
				if (result == null) result = caseBuiltInType(voidType);
				if (result == null) result = caseType(voidType);
				if (result == null) result = caseTExportableElement(voidType);
				if (result == null) result = caseTAnnotableElement(voidType);
				if (result == null) result = caseVersionable(voidType);
				if (result == null) result = caseIdentifiableElement(voidType);
				if (result == null) result = caseTypableElement(voidType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TSTRUCTURAL_TYPE: {
				TStructuralType tStructuralType = (TStructuralType)theEObject;
				T result = caseTStructuralType(tStructuralType);
				if (result == null) result = caseContainerType(tStructuralType);
				if (result == null) result = caseSyntaxRelatedTElement(tStructuralType);
				if (result == null) result = caseType(tStructuralType);
				if (result == null) result = caseTExportableElement(tStructuralType);
				if (result == null) result = caseTAnnotableElement(tStructuralType);
				if (result == null) result = caseVersionable(tStructuralType);
				if (result == null) result = caseIdentifiableElement(tStructuralType);
				if (result == null) result = caseTypableElement(tStructuralType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TVERSIONABLE: {
				TVersionable tVersionable = (TVersionable)theEObject;
				T result = caseTVersionable(tVersionable);
				if (result == null) result = caseType(tVersionable);
				if (result == null) result = caseTExportableElement(tVersionable);
				if (result == null) result = caseTAnnotableElement(tVersionable);
				if (result == null) result = caseVersionable(tVersionable);
				if (result == null) result = caseIdentifiableElement(tVersionable);
				if (result == null) result = caseTypableElement(tVersionable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TMIGRATABLE: {
				TMigratable tMigratable = (TMigratable)theEObject;
				T result = caseTMigratable(tMigratable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TMIGRATION: {
				TMigration tMigration = (TMigration)theEObject;
				T result = caseTMigration(tMigration);
				if (result == null) result = caseTFunction(tMigration);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tMigration);
				if (result == null) result = caseSyntaxRelatedTElement(tMigration);
				if (result == null) result = caseTVersionable(tMigration);
				if (result == null) result = caseType(tMigration);
				if (result == null) result = caseAccessibleTypeElement(tMigration);
				if (result == null) result = caseTExportableElement(tMigration);
				if (result == null) result = caseTAnnotableElement(tMigration);
				if (result == null) result = caseVersionable(tMigration);
				if (result == null) result = caseIdentifiableElement(tMigration);
				if (result == null) result = caseTypableElement(tMigration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TCLASSIFIER: {
				TClassifier tClassifier = (TClassifier)theEObject;
				T result = caseTClassifier(tClassifier);
				if (result == null) result = caseContainerType(tClassifier);
				if (result == null) result = caseSyntaxRelatedTElement(tClassifier);
				if (result == null) result = caseTVersionable(tClassifier);
				if (result == null) result = caseType(tClassifier);
				if (result == null) result = caseTExportableElement(tClassifier);
				if (result == null) result = caseTAnnotableElement(tClassifier);
				if (result == null) result = caseVersionable(tClassifier);
				if (result == null) result = caseIdentifiableElement(tClassifier);
				if (result == null) result = caseTypableElement(tClassifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TOBJECT_PROTOTYPE: {
				TObjectPrototype tObjectPrototype = (TObjectPrototype)theEObject;
				T result = caseTObjectPrototype(tObjectPrototype);
				if (result == null) result = caseTClassifier(tObjectPrototype);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tObjectPrototype);
				if (result == null) result = caseArrayLike(tObjectPrototype);
				if (result == null) result = caseContainerType(tObjectPrototype);
				if (result == null) result = caseSyntaxRelatedTElement(tObjectPrototype);
				if (result == null) result = caseTVersionable(tObjectPrototype);
				if (result == null) result = caseAccessibleTypeElement(tObjectPrototype);
				if (result == null) result = caseType(tObjectPrototype);
				if (result == null) result = caseTExportableElement(tObjectPrototype);
				if (result == null) result = caseTAnnotableElement(tObjectPrototype);
				if (result == null) result = caseVersionable(tObjectPrototype);
				if (result == null) result = caseIdentifiableElement(tObjectPrototype);
				if (result == null) result = caseTypableElement(tObjectPrototype);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ARRAY_LIKE: {
				ArrayLike arrayLike = (ArrayLike)theEObject;
				T result = caseArrayLike(arrayLike);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TN4_CLASSIFIER: {
				TN4Classifier tn4Classifier = (TN4Classifier)theEObject;
				T result = caseTN4Classifier(tn4Classifier);
				if (result == null) result = caseTClassifier(tn4Classifier);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tn4Classifier);
				if (result == null) result = caseTMigratable(tn4Classifier);
				if (result == null) result = caseContainerType(tn4Classifier);
				if (result == null) result = caseSyntaxRelatedTElement(tn4Classifier);
				if (result == null) result = caseTVersionable(tn4Classifier);
				if (result == null) result = caseAccessibleTypeElement(tn4Classifier);
				if (result == null) result = caseType(tn4Classifier);
				if (result == null) result = caseTExportableElement(tn4Classifier);
				if (result == null) result = caseTAnnotableElement(tn4Classifier);
				if (result == null) result = caseVersionable(tn4Classifier);
				if (result == null) result = caseIdentifiableElement(tn4Classifier);
				if (result == null) result = caseTypableElement(tn4Classifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TCLASS: {
				TClass tClass = (TClass)theEObject;
				T result = caseTClass(tClass);
				if (result == null) result = caseTN4Classifier(tClass);
				if (result == null) result = caseTClassifier(tClass);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tClass);
				if (result == null) result = caseTMigratable(tClass);
				if (result == null) result = caseContainerType(tClass);
				if (result == null) result = caseSyntaxRelatedTElement(tClass);
				if (result == null) result = caseTVersionable(tClass);
				if (result == null) result = caseAccessibleTypeElement(tClass);
				if (result == null) result = caseType(tClass);
				if (result == null) result = caseTExportableElement(tClass);
				if (result == null) result = caseTAnnotableElement(tClass);
				if (result == null) result = caseVersionable(tClass);
				if (result == null) result = caseIdentifiableElement(tClass);
				if (result == null) result = caseTypableElement(tClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TINTERFACE: {
				TInterface tInterface = (TInterface)theEObject;
				T result = caseTInterface(tInterface);
				if (result == null) result = caseTN4Classifier(tInterface);
				if (result == null) result = caseTClassifier(tInterface);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tInterface);
				if (result == null) result = caseTMigratable(tInterface);
				if (result == null) result = caseContainerType(tInterface);
				if (result == null) result = caseSyntaxRelatedTElement(tInterface);
				if (result == null) result = caseTVersionable(tInterface);
				if (result == null) result = caseAccessibleTypeElement(tInterface);
				if (result == null) result = caseType(tInterface);
				if (result == null) result = caseTExportableElement(tInterface);
				if (result == null) result = caseTAnnotableElement(tInterface);
				if (result == null) result = caseVersionable(tInterface);
				if (result == null) result = caseIdentifiableElement(tInterface);
				if (result == null) result = caseTypableElement(tInterface);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TMEMBER: {
				TMember tMember = (TMember)theEObject;
				T result = caseTMember(tMember);
				if (result == null) result = caseIdentifiableElement(tMember);
				if (result == null) result = caseTAnnotableElement(tMember);
				if (result == null) result = caseSyntaxRelatedTElement(tMember);
				if (result == null) result = caseTypableElement(tMember);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER: {
				TMemberWithAccessModifier tMemberWithAccessModifier = (TMemberWithAccessModifier)theEObject;
				T result = caseTMemberWithAccessModifier(tMemberWithAccessModifier);
				if (result == null) result = caseTMember(tMemberWithAccessModifier);
				if (result == null) result = caseIdentifiableElement(tMemberWithAccessModifier);
				if (result == null) result = caseTAnnotableElement(tMemberWithAccessModifier);
				if (result == null) result = caseSyntaxRelatedTElement(tMemberWithAccessModifier);
				if (result == null) result = caseTypableElement(tMemberWithAccessModifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TSTRUCT_MEMBER: {
				TStructMember tStructMember = (TStructMember)theEObject;
				T result = caseTStructMember(tStructMember);
				if (result == null) result = caseTMember(tStructMember);
				if (result == null) result = caseIdentifiableElement(tStructMember);
				if (result == null) result = caseTAnnotableElement(tStructMember);
				if (result == null) result = caseSyntaxRelatedTElement(tStructMember);
				if (result == null) result = caseTypableElement(tStructMember);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TMETHOD: {
				TMethod tMethod = (TMethod)theEObject;
				T result = caseTMethod(tMethod);
				if (result == null) result = caseTFunction(tMethod);
				if (result == null) result = caseTMemberWithAccessModifier(tMethod);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tMethod);
				if (result == null) result = caseTVersionable(tMethod);
				if (result == null) result = caseTMember(tMethod);
				if (result == null) result = caseType(tMethod);
				if (result == null) result = caseAccessibleTypeElement(tMethod);
				if (result == null) result = caseSyntaxRelatedTElement(tMethod);
				if (result == null) result = caseTExportableElement(tMethod);
				if (result == null) result = caseTAnnotableElement(tMethod);
				if (result == null) result = caseVersionable(tMethod);
				if (result == null) result = caseIdentifiableElement(tMethod);
				if (result == null) result = caseTypableElement(tMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TSTRUCT_METHOD: {
				TStructMethod tStructMethod = (TStructMethod)theEObject;
				T result = caseTStructMethod(tStructMethod);
				if (result == null) result = caseTMethod(tStructMethod);
				if (result == null) result = caseTStructMember(tStructMethod);
				if (result == null) result = caseTFunction(tStructMethod);
				if (result == null) result = caseTMemberWithAccessModifier(tStructMethod);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tStructMethod);
				if (result == null) result = caseTVersionable(tStructMethod);
				if (result == null) result = caseTMember(tStructMethod);
				if (result == null) result = caseType(tStructMethod);
				if (result == null) result = caseAccessibleTypeElement(tStructMethod);
				if (result == null) result = caseSyntaxRelatedTElement(tStructMethod);
				if (result == null) result = caseTExportableElement(tStructMethod);
				if (result == null) result = caseTAnnotableElement(tStructMethod);
				if (result == null) result = caseVersionable(tStructMethod);
				if (result == null) result = caseIdentifiableElement(tStructMethod);
				if (result == null) result = caseTypableElement(tStructMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TFORMAL_PARAMETER: {
				TFormalParameter tFormalParameter = (TFormalParameter)theEObject;
				T result = caseTFormalParameter(tFormalParameter);
				if (result == null) result = caseIdentifiableElement(tFormalParameter);
				if (result == null) result = caseTAnnotableElement(tFormalParameter);
				if (result == null) result = caseSyntaxRelatedTElement(tFormalParameter);
				if (result == null) result = caseTTypedElement(tFormalParameter);
				if (result == null) result = caseTypableElement(tFormalParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TANONYMOUS_FORMAL_PARAMETER: {
				TAnonymousFormalParameter tAnonymousFormalParameter = (TAnonymousFormalParameter)theEObject;
				T result = caseTAnonymousFormalParameter(tAnonymousFormalParameter);
				if (result == null) result = caseTFormalParameter(tAnonymousFormalParameter);
				if (result == null) result = caseIdentifiableElement(tAnonymousFormalParameter);
				if (result == null) result = caseTAnnotableElement(tAnonymousFormalParameter);
				if (result == null) result = caseSyntaxRelatedTElement(tAnonymousFormalParameter);
				if (result == null) result = caseTTypedElement(tAnonymousFormalParameter);
				if (result == null) result = caseTypableElement(tAnonymousFormalParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TFIELD: {
				TField tField = (TField)theEObject;
				T result = caseTField(tField);
				if (result == null) result = caseTMemberWithAccessModifier(tField);
				if (result == null) result = caseTTypedElement(tField);
				if (result == null) result = caseTConstableElement(tField);
				if (result == null) result = caseTMember(tField);
				if (result == null) result = caseIdentifiableElement(tField);
				if (result == null) result = caseTAnnotableElement(tField);
				if (result == null) result = caseSyntaxRelatedTElement(tField);
				if (result == null) result = caseTypableElement(tField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TSTRUCT_FIELD: {
				TStructField tStructField = (TStructField)theEObject;
				T result = caseTStructField(tStructField);
				if (result == null) result = caseTField(tStructField);
				if (result == null) result = caseTStructMember(tStructField);
				if (result == null) result = caseTMemberWithAccessModifier(tStructField);
				if (result == null) result = caseTTypedElement(tStructField);
				if (result == null) result = caseTConstableElement(tStructField);
				if (result == null) result = caseTMember(tStructField);
				if (result == null) result = caseIdentifiableElement(tStructField);
				if (result == null) result = caseTAnnotableElement(tStructField);
				if (result == null) result = caseSyntaxRelatedTElement(tStructField);
				if (result == null) result = caseTypableElement(tStructField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FIELD_ACCESSOR: {
				FieldAccessor fieldAccessor = (FieldAccessor)theEObject;
				T result = caseFieldAccessor(fieldAccessor);
				if (result == null) result = caseTMemberWithAccessModifier(fieldAccessor);
				if (result == null) result = caseTMember(fieldAccessor);
				if (result == null) result = caseIdentifiableElement(fieldAccessor);
				if (result == null) result = caseTAnnotableElement(fieldAccessor);
				if (result == null) result = caseSyntaxRelatedTElement(fieldAccessor);
				if (result == null) result = caseTypableElement(fieldAccessor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TGETTER: {
				TGetter tGetter = (TGetter)theEObject;
				T result = caseTGetter(tGetter);
				if (result == null) result = caseFieldAccessor(tGetter);
				if (result == null) result = caseTMemberWithAccessModifier(tGetter);
				if (result == null) result = caseTMember(tGetter);
				if (result == null) result = caseIdentifiableElement(tGetter);
				if (result == null) result = caseTAnnotableElement(tGetter);
				if (result == null) result = caseSyntaxRelatedTElement(tGetter);
				if (result == null) result = caseTypableElement(tGetter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TSTRUCT_GETTER: {
				TStructGetter tStructGetter = (TStructGetter)theEObject;
				T result = caseTStructGetter(tStructGetter);
				if (result == null) result = caseTGetter(tStructGetter);
				if (result == null) result = caseTStructMember(tStructGetter);
				if (result == null) result = caseFieldAccessor(tStructGetter);
				if (result == null) result = caseTMemberWithAccessModifier(tStructGetter);
				if (result == null) result = caseTMember(tStructGetter);
				if (result == null) result = caseIdentifiableElement(tStructGetter);
				if (result == null) result = caseTAnnotableElement(tStructGetter);
				if (result == null) result = caseSyntaxRelatedTElement(tStructGetter);
				if (result == null) result = caseTypableElement(tStructGetter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TSETTER: {
				TSetter tSetter = (TSetter)theEObject;
				T result = caseTSetter(tSetter);
				if (result == null) result = caseFieldAccessor(tSetter);
				if (result == null) result = caseTMemberWithAccessModifier(tSetter);
				if (result == null) result = caseTMember(tSetter);
				if (result == null) result = caseIdentifiableElement(tSetter);
				if (result == null) result = caseTAnnotableElement(tSetter);
				if (result == null) result = caseSyntaxRelatedTElement(tSetter);
				if (result == null) result = caseTypableElement(tSetter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TSTRUCT_SETTER: {
				TStructSetter tStructSetter = (TStructSetter)theEObject;
				T result = caseTStructSetter(tStructSetter);
				if (result == null) result = caseTSetter(tStructSetter);
				if (result == null) result = caseTStructMember(tStructSetter);
				if (result == null) result = caseFieldAccessor(tStructSetter);
				if (result == null) result = caseTMemberWithAccessModifier(tStructSetter);
				if (result == null) result = caseTMember(tStructSetter);
				if (result == null) result = caseIdentifiableElement(tStructSetter);
				if (result == null) result = caseTAnnotableElement(tStructSetter);
				if (result == null) result = caseSyntaxRelatedTElement(tStructSetter);
				if (result == null) result = caseTypableElement(tStructSetter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TENUM: {
				TEnum tEnum = (TEnum)theEObject;
				T result = caseTEnum(tEnum);
				if (result == null) result = caseDeclaredTypeWithAccessModifier(tEnum);
				if (result == null) result = caseSyntaxRelatedTElement(tEnum);
				if (result == null) result = caseTVersionable(tEnum);
				if (result == null) result = caseTMigratable(tEnum);
				if (result == null) result = caseType(tEnum);
				if (result == null) result = caseAccessibleTypeElement(tEnum);
				if (result == null) result = caseTExportableElement(tEnum);
				if (result == null) result = caseTAnnotableElement(tEnum);
				if (result == null) result = caseVersionable(tEnum);
				if (result == null) result = caseIdentifiableElement(tEnum);
				if (result == null) result = caseTypableElement(tEnum);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TENUM_LITERAL: {
				TEnumLiteral tEnumLiteral = (TEnumLiteral)theEObject;
				T result = caseTEnumLiteral(tEnumLiteral);
				if (result == null) result = caseSyntaxRelatedTElement(tEnumLiteral);
				if (result == null) result = caseIdentifiableElement(tEnumLiteral);
				if (result == null) result = caseTypableElement(tEnumLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.SYNTAX_RELATED_TELEMENT: {
				SyntaxRelatedTElement syntaxRelatedTElement = (SyntaxRelatedTElement)theEObject;
				T result = caseSyntaxRelatedTElement(syntaxRelatedTElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TCONSTABLE_ELEMENT: {
				TConstableElement tConstableElement = (TConstableElement)theEObject;
				T result = caseTConstableElement(tConstableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TVARIABLE: {
				TVariable tVariable = (TVariable)theEObject;
				T result = caseTVariable(tVariable);
				if (result == null) result = caseTExportableElement(tVariable);
				if (result == null) result = caseTConstableElement(tVariable);
				if (result == null) result = caseSyntaxRelatedTElement(tVariable);
				if (result == null) result = caseTAnnotableElement(tVariable);
				if (result == null) result = caseAccessibleTypeElement(tVariable);
				if (result == null) result = caseTTypedElement(tVariable);
				if (result == null) result = caseIdentifiableElement(tVariable);
				if (result == null) result = caseTypableElement(tVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Defs</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Defs</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeDefs(TypeDefs object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TModule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TModule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTModule(TModule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Runtime Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Runtime Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuntimeDependency(RuntimeDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composed Member Cache</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composed Member Cache</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComposedMemberCache(ComposedMemberCache object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypableElement(TypableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifiable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifiable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifiableElement(IdentifiableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TExportable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TExportable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTExportableElement(TExportableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TAnnotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TAnnotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTAnnotation(TAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TAnnotation Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TAnnotation Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTAnnotationArgument(TAnnotationArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TAnnotation String Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TAnnotation String Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTAnnotationStringArgument(TAnnotationStringArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TTyped Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TTyped Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTTypedElement(TTypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TAnnotation Type Ref Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TAnnotation Type Ref Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTAnnotationTypeRefArgument(TAnnotationTypeRefArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TAnnotable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TAnnotable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTAnnotableElement(TAnnotableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeVariable(TypeVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inference Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inference Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInferenceVariable(InferenceVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TFunction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TFunction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTFunction(TFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseType(Type object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Accessible Type Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Accessible Type Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAccessibleTypeElement(AccessibleTypeElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Declared Type With Access Modifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Declared Type With Access Modifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeclaredTypeWithAccessModifier(DeclaredTypeWithAccessModifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Container Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Container Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <MT extends TMember> T caseContainerType(ContainerType<MT> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Virtual Base Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Virtual Base Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVirtualBaseType(VirtualBaseType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Namespace Virtual Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Namespace Virtual Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleNamespaceVirtualType(ModuleNamespaceVirtualType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Primitive Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Primitive Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrimitiveType(PrimitiveType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Built In Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Built In Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBuiltInType(BuiltInType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyType(AnyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Undefined Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Undefined Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUndefinedType(UndefinedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Null Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Null Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNullType(NullType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Void Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Void Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVoidType(VoidType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TStructural Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TStructural Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTStructuralType(TStructuralType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TVersionable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TVersionable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTVersionable(TVersionable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TMigratable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TMigratable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTMigratable(TMigratable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TMigration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TMigration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTMigration(TMigration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TClassifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TClassifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTClassifier(TClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TObject Prototype</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TObject Prototype</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTObjectPrototype(TObjectPrototype object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Like</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Like</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArrayLike(ArrayLike object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TN4 Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TN4 Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTN4Classifier(TN4Classifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TClass</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TClass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTClass(TClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TInterface</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TInterface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTInterface(TInterface object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TMember</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TMember</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTMember(TMember object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TMember With Access Modifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TMember With Access Modifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTMemberWithAccessModifier(TMemberWithAccessModifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TStruct Member</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TStruct Member</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTStructMember(TStructMember object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TMethod</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TMethod</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTMethod(TMethod object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TStruct Method</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TStruct Method</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTStructMethod(TStructMethod object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TFormal Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TFormal Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTFormalParameter(TFormalParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TAnonymous Formal Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TAnonymous Formal Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTAnonymousFormalParameter(TAnonymousFormalParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TField</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TField</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTField(TField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TStruct Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TStruct Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTStructField(TStructField object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field Accessor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field Accessor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFieldAccessor(FieldAccessor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGetter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGetter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGetter(TGetter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TStruct Getter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TStruct Getter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTStructGetter(TStructGetter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TSetter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TSetter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTSetter(TSetter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TStruct Setter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TStruct Setter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTStructSetter(TStructSetter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TEnum</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TEnum</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTEnum(TEnum object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TEnum Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TEnum Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTEnumLiteral(TEnumLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Syntax Related TElement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Syntax Related TElement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSyntaxRelatedTElement(SyntaxRelatedTElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TConstable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TConstable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTConstableElement(TConstableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TVariable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TVariable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTVariable(TVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Versionable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Versionable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionable(Versionable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //TypesSwitch
