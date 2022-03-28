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
package org.eclipse.n4js.transpiler.im.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.MethodDeclaration;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4FieldAccessor;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PrimaryExpression;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.StrictModeRelevant;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.ThisArgProvider;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.TypeProvidingElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.transpiler.im.*;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;

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
 * @see org.eclipse.n4js.transpiler.im.ImPackage
 * @generated
 */
public class ImSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ImPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImSwitch() {
		if (modelPackage == null) {
			modelPackage = ImPackage.eINSTANCE;
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
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ImPackage.SCRIPT_IM: {
				Script_IM script_IM = (Script_IM)theEObject;
				T1 result = caseScript_IM(script_IM);
				if (result == null) result = caseScript(script_IM);
				if (result == null) result = caseVariableEnvironmentElement(script_IM);
				if (result == null) result = caseAnnotableElement(script_IM);
				if (result == null) result = caseControlFlowElement(script_IM);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.SYMBOL_TABLE: {
				SymbolTable symbolTable = (SymbolTable)theEObject;
				T1 result = caseSymbolTable(symbolTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.SYMBOL_TABLE_ENTRY: {
				SymbolTableEntry symbolTableEntry = (SymbolTableEntry)theEObject;
				T1 result = caseSymbolTableEntry(symbolTableEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL: {
				SymbolTableEntryOriginal symbolTableEntryOriginal = (SymbolTableEntryOriginal)theEObject;
				T1 result = caseSymbolTableEntryOriginal(symbolTableEntryOriginal);
				if (result == null) result = caseSymbolTableEntry(symbolTableEntryOriginal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.SYMBOL_TABLE_ENTRY_IM_ONLY: {
				SymbolTableEntryIMOnly symbolTableEntryIMOnly = (SymbolTableEntryIMOnly)theEObject;
				T1 result = caseSymbolTableEntryIMOnly(symbolTableEntryIMOnly);
				if (result == null) result = caseSymbolTableEntry(symbolTableEntryIMOnly);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.SYMBOL_TABLE_ENTRY_INTERNAL: {
				SymbolTableEntryInternal symbolTableEntryInternal = (SymbolTableEntryInternal)theEObject;
				T1 result = caseSymbolTableEntryInternal(symbolTableEntryInternal);
				if (result == null) result = caseSymbolTableEntry(symbolTableEntryInternal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.REFERENCING_ELEMENT_IM: {
				ReferencingElement_IM referencingElement_IM = (ReferencingElement_IM)theEObject;
				T1 result = caseReferencingElement_IM(referencingElement_IM);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.MANY_REFERENCING_ELEMENT_IM: {
				ManyReferencingElement_IM manyReferencingElement_IM = (ManyReferencingElement_IM)theEObject;
				T1 result = caseManyReferencingElement_IM(manyReferencingElement_IM);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.PLAIN_REFERENCE: {
				PlainReference plainReference = (PlainReference)theEObject;
				T1 result = casePlainReference(plainReference);
				if (result == null) result = caseReferencingElement_IM(plainReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.REFERENCING_ELEMENT_EXPRESSION_IM: {
				ReferencingElementExpression_IM referencingElementExpression_IM = (ReferencingElementExpression_IM)theEObject;
				T1 result = caseReferencingElementExpression_IM(referencingElementExpression_IM);
				if (result == null) result = caseReferencingElement_IM(referencingElementExpression_IM);
				if (result == null) result = caseExpression(referencingElementExpression_IM);
				if (result == null) result = caseTypableElement(referencingElementExpression_IM);
				if (result == null) result = caseControlFlowElement(referencingElementExpression_IM);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.IDENTIFIER_REF_IM: {
				IdentifierRef_IM identifierRef_IM = (IdentifierRef_IM)theEObject;
				T1 result = caseIdentifierRef_IM(identifierRef_IM);
				if (result == null) result = caseIdentifierRef(identifierRef_IM);
				if (result == null) result = caseReferencingElementExpression_IM(identifierRef_IM);
				if (result == null) result = casePrimaryExpression(identifierRef_IM);
				if (result == null) result = caseStrictModeRelevant(identifierRef_IM);
				if (result == null) result = caseReferencingElement_IM(identifierRef_IM);
				if (result == null) result = caseExpression(identifierRef_IM);
				if (result == null) result = caseTypableElement(identifierRef_IM);
				if (result == null) result = caseControlFlowElement(identifierRef_IM);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM: {
				ParameterizedPropertyAccessExpression_IM parameterizedPropertyAccessExpression_IM = (ParameterizedPropertyAccessExpression_IM)theEObject;
				T1 result = caseParameterizedPropertyAccessExpression_IM(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseParameterizedPropertyAccessExpression(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseReferencingElementExpression_IM(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseExpressionWithTarget(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseMemberAccess(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseParameterizedAccess(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseReferencingElement_IM(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseExpression(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseTypableElement(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = caseControlFlowElement(parameterizedPropertyAccessExpression_IM);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.TYPE_REFERENCE_NODE_IM: {
				TypeReferenceNode_IM<?> typeReferenceNode_IM = (TypeReferenceNode_IM<?>)theEObject;
				T1 result = caseTypeReferenceNode_IM(typeReferenceNode_IM);
				if (result == null) result = caseTypeReferenceNode(typeReferenceNode_IM);
				if (result == null) result = caseManyReferencingElement_IM(typeReferenceNode_IM);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.SNIPPET: {
				Snippet snippet = (Snippet)theEObject;
				T1 result = caseSnippet(snippet);
				if (result == null) result = caseExpression(snippet);
				if (result == null) result = caseTypableElement(snippet);
				if (result == null) result = caseControlFlowElement(snippet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.DELEGATING_MEMBER: {
				DelegatingMember delegatingMember = (DelegatingMember)theEObject;
				T1 result = caseDelegatingMember(delegatingMember);
				if (result == null) result = caseN4MemberDeclaration(delegatingMember);
				if (result == null) result = caseAnnotableElement(delegatingMember);
				if (result == null) result = caseModifiableElement(delegatingMember);
				if (result == null) result = caseTypableElement(delegatingMember);
				if (result == null) result = caseNamedElement(delegatingMember);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.DELEGATING_GETTER_DECLARATION: {
				DelegatingGetterDeclaration delegatingGetterDeclaration = (DelegatingGetterDeclaration)theEObject;
				T1 result = caseDelegatingGetterDeclaration(delegatingGetterDeclaration);
				if (result == null) result = caseN4GetterDeclaration(delegatingGetterDeclaration);
				if (result == null) result = caseDelegatingMember(delegatingGetterDeclaration);
				if (result == null) result = caseGetterDeclaration(delegatingGetterDeclaration);
				if (result == null) result = caseN4FieldAccessor(delegatingGetterDeclaration);
				if (result == null) result = caseFieldAccessor(delegatingGetterDeclaration);
				if (result == null) result = caseTypedElement(delegatingGetterDeclaration);
				if (result == null) result = caseAnnotableN4MemberDeclaration(delegatingGetterDeclaration);
				if (result == null) result = caseFunctionOrFieldAccessor(delegatingGetterDeclaration);
				if (result == null) result = caseTypeProvidingElement(delegatingGetterDeclaration);
				if (result == null) result = casePropertyNameOwner(delegatingGetterDeclaration);
				if (result == null) result = caseN4MemberDeclaration(delegatingGetterDeclaration);
				if (result == null) result = caseAnnotableElement(delegatingGetterDeclaration);
				if (result == null) result = caseVariableEnvironmentElement(delegatingGetterDeclaration);
				if (result == null) result = caseThisArgProvider(delegatingGetterDeclaration);
				if (result == null) result = caseTypableElement(delegatingGetterDeclaration);
				if (result == null) result = caseNamedElement(delegatingGetterDeclaration);
				if (result == null) result = caseModifiableElement(delegatingGetterDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.DELEGATING_SETTER_DECLARATION: {
				DelegatingSetterDeclaration delegatingSetterDeclaration = (DelegatingSetterDeclaration)theEObject;
				T1 result = caseDelegatingSetterDeclaration(delegatingSetterDeclaration);
				if (result == null) result = caseN4SetterDeclaration(delegatingSetterDeclaration);
				if (result == null) result = caseDelegatingMember(delegatingSetterDeclaration);
				if (result == null) result = caseSetterDeclaration(delegatingSetterDeclaration);
				if (result == null) result = caseN4FieldAccessor(delegatingSetterDeclaration);
				if (result == null) result = caseFieldAccessor(delegatingSetterDeclaration);
				if (result == null) result = caseAnnotableN4MemberDeclaration(delegatingSetterDeclaration);
				if (result == null) result = caseFunctionOrFieldAccessor(delegatingSetterDeclaration);
				if (result == null) result = caseTypeProvidingElement(delegatingSetterDeclaration);
				if (result == null) result = casePropertyNameOwner(delegatingSetterDeclaration);
				if (result == null) result = caseN4MemberDeclaration(delegatingSetterDeclaration);
				if (result == null) result = caseAnnotableElement(delegatingSetterDeclaration);
				if (result == null) result = caseVariableEnvironmentElement(delegatingSetterDeclaration);
				if (result == null) result = caseThisArgProvider(delegatingSetterDeclaration);
				if (result == null) result = caseTypableElement(delegatingSetterDeclaration);
				if (result == null) result = caseNamedElement(delegatingSetterDeclaration);
				if (result == null) result = caseModifiableElement(delegatingSetterDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.DELEGATING_METHOD_DECLARATION: {
				DelegatingMethodDeclaration delegatingMethodDeclaration = (DelegatingMethodDeclaration)theEObject;
				T1 result = caseDelegatingMethodDeclaration(delegatingMethodDeclaration);
				if (result == null) result = caseN4MethodDeclaration(delegatingMethodDeclaration);
				if (result == null) result = caseDelegatingMember(delegatingMethodDeclaration);
				if (result == null) result = caseAnnotableN4MemberDeclaration(delegatingMethodDeclaration);
				if (result == null) result = caseMethodDeclaration(delegatingMethodDeclaration);
				if (result == null) result = caseN4MemberDeclaration(delegatingMethodDeclaration);
				if (result == null) result = caseFunctionDefinition(delegatingMethodDeclaration);
				if (result == null) result = caseGenericDeclaration(delegatingMethodDeclaration);
				if (result == null) result = casePropertyNameOwner(delegatingMethodDeclaration);
				if (result == null) result = caseModifiableElement(delegatingMethodDeclaration);
				if (result == null) result = caseNamedElement(delegatingMethodDeclaration);
				if (result == null) result = caseFunctionOrFieldAccessor(delegatingMethodDeclaration);
				if (result == null) result = caseTypeDefiningElement(delegatingMethodDeclaration);
				if (result == null) result = caseAnnotableElement(delegatingMethodDeclaration);
				if (result == null) result = caseTypableElement(delegatingMethodDeclaration);
				if (result == null) result = caseVariableEnvironmentElement(delegatingMethodDeclaration);
				if (result == null) result = caseThisArgProvider(delegatingMethodDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ImPackage.STRING_LITERAL_FOR_STE: {
				StringLiteralForSTE stringLiteralForSTE = (StringLiteralForSTE)theEObject;
				T1 result = caseStringLiteralForSTE(stringLiteralForSTE);
				if (result == null) result = caseStringLiteral(stringLiteralForSTE);
				if (result == null) result = caseLiteral(stringLiteralForSTE);
				if (result == null) result = casePrimaryExpression(stringLiteralForSTE);
				if (result == null) result = caseExpression(stringLiteralForSTE);
				if (result == null) result = caseTypableElement(stringLiteralForSTE);
				if (result == null) result = caseControlFlowElement(stringLiteralForSTE);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Script IM</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Script IM</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseScript_IM(Script_IM object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbol Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbol Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSymbolTable(SymbolTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbol Table Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbol Table Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSymbolTableEntry(SymbolTableEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbol Table Entry Original</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbol Table Entry Original</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSymbolTableEntryOriginal(SymbolTableEntryOriginal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbol Table Entry IM Only</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbol Table Entry IM Only</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSymbolTableEntryIMOnly(SymbolTableEntryIMOnly object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbol Table Entry Internal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbol Table Entry Internal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSymbolTableEntryInternal(SymbolTableEntryInternal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Referencing Element IM</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Referencing Element IM</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseReferencingElement_IM(ReferencingElement_IM object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many Referencing Element IM</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many Referencing Element IM</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseManyReferencingElement_IM(ManyReferencingElement_IM object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plain Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plain Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 casePlainReference(PlainReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Referencing Element Expression IM</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Referencing Element Expression IM</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseReferencingElementExpression_IM(ReferencingElementExpression_IM object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifier Ref IM</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifier Ref IM</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIdentifierRef_IM(IdentifierRef_IM object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameterized Property Access Expression IM</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameterized Property Access Expression IM</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseParameterizedPropertyAccessExpression_IM(ParameterizedPropertyAccessExpression_IM object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Reference Node IM</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Reference Node IM</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends TypeRef> T1 caseTypeReferenceNode_IM(TypeReferenceNode_IM<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Snippet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Snippet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSnippet(Snippet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Delegating Member</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Delegating Member</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDelegatingMember(DelegatingMember object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Delegating Getter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Delegating Getter Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDelegatingGetterDeclaration(DelegatingGetterDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Delegating Setter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Delegating Setter Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDelegatingSetterDeclaration(DelegatingSetterDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Delegating Method Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Delegating Method Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDelegatingMethodDeclaration(DelegatingMethodDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Literal For STE</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Literal For STE</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStringLiteralForSTE(StringLiteralForSTE object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Environment Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Environment Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseVariableEnvironmentElement(VariableEnvironmentElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAnnotableElement(AnnotableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Control Flow Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Control Flow Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseControlFlowElement(ControlFlowElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Script</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Script</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseScript(Script object) {
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
	public T1 caseTypableElement(TypableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Primary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Primary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 casePrimaryExpression(PrimaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Strict Mode Relevant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Strict Mode Relevant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStrictModeRelevant(StrictModeRelevant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifier Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifier Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIdentifierRef(IdentifierRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression With Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression With Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseExpressionWithTarget(ExpressionWithTarget object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Member Access</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Member Access</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMemberAccess(MemberAccess object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameterized Access</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameterized Access</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseParameterizedAccess(ParameterizedAccess object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameterized Property Access Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameterized Property Access Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Reference Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Reference Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends TypeRef> T1 caseTypeReferenceNode(TypeReferenceNode<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Modifiable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Modifiable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseModifiableElement(ModifiableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>N4 Member Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>N4 Member Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseN4MemberDeclaration(N4MemberDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>This Arg Provider</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>This Arg Provider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseThisArgProvider(ThisArgProvider object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Or Field Accessor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Or Field Accessor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFunctionOrFieldAccessor(FunctionOrFieldAccessor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Providing Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Providing Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTypeProvidingElement(TypeProvidingElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Name Owner</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Name Owner</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 casePropertyNameOwner(PropertyNameOwner object) {
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
	public T1 caseFieldAccessor(FieldAccessor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTypedElement(TypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Getter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Getter Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseGetterDeclaration(GetterDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotable N4 Member Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotable N4 Member Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAnnotableN4MemberDeclaration(AnnotableN4MemberDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>N4 Field Accessor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>N4 Field Accessor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseN4FieldAccessor(N4FieldAccessor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>N4 Getter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>N4 Getter Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseN4GetterDeclaration(N4GetterDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Setter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Setter Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSetterDeclaration(SetterDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>N4 Setter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>N4 Setter Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseN4SetterDeclaration(N4SetterDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Defining Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Defining Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTypeDefiningElement(TypeDefiningElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFunctionDefinition(FunctionDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseGenericDeclaration(GenericDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Method Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Method Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMethodDeclaration(MethodDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>N4 Method Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>N4 Method Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseN4MethodDeclaration(N4MethodDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseLiteral(Literal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStringLiteral(StringLiteral object) {
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
	public T1 defaultCase(EObject object) {
		return null;
	}

} //ImSwitch
