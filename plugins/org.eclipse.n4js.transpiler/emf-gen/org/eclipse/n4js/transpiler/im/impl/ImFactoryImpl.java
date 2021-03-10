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
package org.eclipse.n4js.transpiler.im.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.transpiler.im.*;
import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ImFactoryImpl extends EFactoryImpl implements ImFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ImFactory init() {
		try {
			ImFactory theImFactory = (ImFactory)EPackage.Registry.INSTANCE.getEFactory(ImPackage.eNS_URI);
			if (theImFactory != null) {
				return theImFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ImFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImFactoryImpl() {
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
			case ImPackage.SCRIPT_IM: return createScript_IM();
			case ImPackage.SYMBOL_TABLE: return createSymbolTable();
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL: return createSymbolTableEntryOriginal();
			case ImPackage.SYMBOL_TABLE_ENTRY_IM_ONLY: return createSymbolTableEntryIMOnly();
			case ImPackage.SYMBOL_TABLE_ENTRY_INTERNAL: return createSymbolTableEntryInternal();
			case ImPackage.TYPE_REFERENCE_NODE_IM: return createTypeReferenceNode_IM();
			case ImPackage.IDENTIFIER_REF_IM: return createIdentifierRef_IM();
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM: return createParameterizedPropertyAccessExpression_IM();
			case ImPackage.PARAMETERIZED_TYPE_REF_IM: return createParameterizedTypeRef_IM();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM: return createParameterizedTypeRefStructural_IM();
			case ImPackage.SNIPPET: return createSnippet();
			case ImPackage.DELEGATING_GETTER_DECLARATION: return createDelegatingGetterDeclaration();
			case ImPackage.DELEGATING_SETTER_DECLARATION: return createDelegatingSetterDeclaration();
			case ImPackage.DELEGATING_METHOD_DECLARATION: return createDelegatingMethodDeclaration();
			case ImPackage.STRING_LITERAL_FOR_STE: return createStringLiteralForSTE();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_IM: return createVersionedParameterizedTypeRef_IM();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM: return createVersionedParameterizedTypeRefStructural_IM();
			case ImPackage.VERSIONED_IDENTIFIER_REF_IM: return createVersionedIdentifierRef_IM();
			case ImPackage.VERSIONED_NAMED_IMPORT_SPECIFIER_IM: return createVersionedNamedImportSpecifier_IM();
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
	public Script_IM createScript_IM() {
		Script_IMImpl script_IM = new Script_IMImpl();
		return script_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTable createSymbolTable() {
		SymbolTableImpl symbolTable = new SymbolTableImpl();
		return symbolTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntryOriginal createSymbolTableEntryOriginal() {
		SymbolTableEntryOriginalImpl symbolTableEntryOriginal = new SymbolTableEntryOriginalImpl();
		return symbolTableEntryOriginal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntryIMOnly createSymbolTableEntryIMOnly() {
		SymbolTableEntryIMOnlyImpl symbolTableEntryIMOnly = new SymbolTableEntryIMOnlyImpl();
		return symbolTableEntryIMOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntryInternal createSymbolTableEntryInternal() {
		SymbolTableEntryInternalImpl symbolTableEntryInternal = new SymbolTableEntryInternalImpl();
		return symbolTableEntryInternal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public <T extends TypeRef> TypeReferenceNode_IM<T> createTypeReferenceNode_IM() {
		TypeReferenceNode_IMImpl<T> typeReferenceNode_IM = new TypeReferenceNode_IMImpl<T>();
		return typeReferenceNode_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifierRef_IM createIdentifierRef_IM() {
		IdentifierRef_IMImpl identifierRef_IM = new IdentifierRef_IMImpl();
		return identifierRef_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedPropertyAccessExpression_IM createParameterizedPropertyAccessExpression_IM() {
		ParameterizedPropertyAccessExpression_IMImpl parameterizedPropertyAccessExpression_IM = new ParameterizedPropertyAccessExpression_IMImpl();
		return parameterizedPropertyAccessExpression_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef_IM createParameterizedTypeRef_IM() {
		ParameterizedTypeRef_IMImpl parameterizedTypeRef_IM = new ParameterizedTypeRef_IMImpl();
		return parameterizedTypeRef_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRefStructural_IM createParameterizedTypeRefStructural_IM() {
		ParameterizedTypeRefStructural_IMImpl parameterizedTypeRefStructural_IM = new ParameterizedTypeRefStructural_IMImpl();
		return parameterizedTypeRefStructural_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Snippet createSnippet() {
		SnippetImpl snippet = new SnippetImpl();
		return snippet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DelegatingGetterDeclaration createDelegatingGetterDeclaration() {
		DelegatingGetterDeclarationImpl delegatingGetterDeclaration = new DelegatingGetterDeclarationImpl();
		return delegatingGetterDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DelegatingSetterDeclaration createDelegatingSetterDeclaration() {
		DelegatingSetterDeclarationImpl delegatingSetterDeclaration = new DelegatingSetterDeclarationImpl();
		return delegatingSetterDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DelegatingMethodDeclaration createDelegatingMethodDeclaration() {
		DelegatingMethodDeclarationImpl delegatingMethodDeclaration = new DelegatingMethodDeclarationImpl();
		return delegatingMethodDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringLiteralForSTE createStringLiteralForSTE() {
		StringLiteralForSTEImpl stringLiteralForSTE = new StringLiteralForSTEImpl();
		return stringLiteralForSTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionedParameterizedTypeRef_IM createVersionedParameterizedTypeRef_IM() {
		VersionedParameterizedTypeRef_IMImpl versionedParameterizedTypeRef_IM = new VersionedParameterizedTypeRef_IMImpl();
		return versionedParameterizedTypeRef_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionedParameterizedTypeRefStructural_IM createVersionedParameterizedTypeRefStructural_IM() {
		VersionedParameterizedTypeRefStructural_IMImpl versionedParameterizedTypeRefStructural_IM = new VersionedParameterizedTypeRefStructural_IMImpl();
		return versionedParameterizedTypeRefStructural_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionedIdentifierRef_IM createVersionedIdentifierRef_IM() {
		VersionedIdentifierRef_IMImpl versionedIdentifierRef_IM = new VersionedIdentifierRef_IMImpl();
		return versionedIdentifierRef_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionedNamedImportSpecifier_IM createVersionedNamedImportSpecifier_IM() {
		VersionedNamedImportSpecifier_IMImpl versionedNamedImportSpecifier_IM = new VersionedNamedImportSpecifier_IMImpl();
		return versionedNamedImportSpecifier_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ImPackage getImPackage() {
		return (ImPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ImPackage getPackage() {
		return ImPackage.eINSTANCE;
	}

} //ImFactoryImpl
