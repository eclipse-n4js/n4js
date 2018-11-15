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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Environment Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  Element to which a lexical environment (containing a dictionary of variables, the variable environment) may be
 * associated with
 * (cf. ECMAScript Language Specification. International Standard ECMA-262, 5.1 Edition, paragraph 10.2).
 * This is true for Script, FunctionDefintion, WithStatement, and CatchBlock.
 * Note that this is not true for classes (or object literals in general),
 * as members are only accessed via "this" (which is not modeled as a variable, but as an expression
 * allowing access to properties of the current context).
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableEnvironmentElement()
 * @model abstract="true"
 * @generated
 */
public interface VariableEnvironmentElement extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Tells if this variable environment element applies only to block scoped elements, i.e. let & const.
	 * Returns <code>true</code> for {@link Block} and {@link ForStatement}, <code>false</code> otherwise.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean appliesOnlyToBlockScopedElements();

} // VariableEnvironmentElement
