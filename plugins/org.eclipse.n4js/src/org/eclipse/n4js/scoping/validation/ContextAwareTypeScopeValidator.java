/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.validation;

import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareTypeScope;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Handles types that are allowed only in certain areas of the AST. Accessibility is handled by
 * {@link VisibilityAwareTypeScope}.
 */
public class ContextAwareTypeScopeValidator implements IScopeValidator {
	private final EObject context;
	private final boolean isValidLocationForNull;
	private final boolean isValidLocationForVoid;
	private final boolean isValidLocationForFunctionType;

	/** See {@link ContextAwareTypeScopeValidator}. */
	public ContextAwareTypeScopeValidator(EObject context) {
		if (context.eContainer() instanceof TypeReferenceNode<?>) {
			context = context.eContainer();
		}
		this.context = context;
		final EObject container = context.eContainer();
		final EReference eRef = context.eContainmentFeature();

		this.isValidLocationForNull = false; // in the source code, 'null' is never a valid type

		this.isValidLocationForVoid = eRef == N4JSPackage.eINSTANCE.getFunctionDefinition_DeclaredReturnTypeRefNode()
				|| eRef == TypeRefsPackage.eINSTANCE.getFunctionTypeExpression_ReturnTypeRef()
				|| eRef == TypesPackage.eINSTANCE.getTFunction_ReturnTypeRef()
				// void is not truly allowed as the return type of a getter, but there's a separate validation for
				// that; so treat this case as legal here:
				|| container instanceof GetterDeclaration
						&& eRef == N4JSPackage.eINSTANCE.getTypedElement_DeclaredTypeRefNode();

		this.isValidLocationForFunctionType = false
				// the following is only required for content assist in JSXElements (main scoping works without this)
				|| eRef == N4JSPackage.eINSTANCE.getJSXElement_JsxElementName()
				// function types are not truly allowed within TypeTypeRefs (i.e. inside 'type{...}'), but there's a
				// separate validation for that; so treat this case as legal here:
				|| eRef == TypeRefsPackage.eINSTANCE.getTypeTypeRef_TypeArg()
				|| eRef == N4JSPackage.eINSTANCE.getTypedElement_DeclaredTypeRefNode();
	}

	@Override
	public boolean isValid(IEObjectDescription originalDescr) {
		final EClass eClass = originalDescr.getEClass();
		if (!isValidLocationForNull && eClass == TypesPackage.Literals.NULL_TYPE) {
			return false;
		}
		if (!isValidLocationForVoid && eClass == TypesPackage.Literals.VOID_TYPE) {
			return false; // Requirements 13, Void type.
		}
		if (!isValidLocationForFunctionType && TypesPackage.Literals.TFUNCTION.isSuperTypeOf(eClass)) {
			return false;
		}
		return true;
	}

	@Override
	public ScopeElementIssue getIssue(IEObjectDescription originalDescr) {
		final EClass eClass = originalDescr.getEClass();
		if (!isValidLocationForNull && eClass == TypesPackage.Literals.NULL_TYPE) {
			return null; // 'null' will filter out this element completely (i.e. not custom error message)
		}
		if (!isValidLocationForVoid && eClass == TypesPackage.Literals.VOID_TYPE) {
			return new ScopeElementIssue(originalDescr,
					IssueCodes.TYS_VOID_AT_WRONG_LOCATION.name(),
					IssueCodes.TYS_VOID_AT_WRONG_LOCATION.getMessage());
		}
		if (!isValidLocationForFunctionType && TypesPackage.Literals.TFUNCTION.isSuperTypeOf(eClass)) {
			return new ScopeElementIssue(originalDescr,
					IssueCodes.TYS_FUNCTION_DISALLOWED_AS_TYPE.name(),
					IssueCodes.TYS_FUNCTION_DISALLOWED_AS_TYPE.getMessage());
		}
		return null; // should never happen, because #isAccepted() has returned true
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), context);
	}

}
