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
package org.eclipse.n4js.transpiler.im;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Many Referencing Element IM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Like {@link ReferencingElement_IM}, but may refer to <em>several</em> other elements.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.ManyReferencingElement_IM#getRewiredReferences <em>Rewired References</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getManyReferencingElement_IM()
 * @model abstract="true"
 * @generated
 */
public interface ManyReferencingElement_IM extends EObject {

	/**
	 * Returns the value of the '<em><b>Rewired References</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.transpiler.im.PlainReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rewired References</em>' containment reference list.
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getManyReferencingElement_IM_RewiredReferences()
	 * @model containment="true"
	 * @generated
	 */
	EList<PlainReference> getRewiredReferences();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<SymbolTableEntry> getRewiredTargets();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model steUnique="false"
	 * @generated
	 */
	void addRewiredTarget(SymbolTableEntry ste);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model steUnique="false"
	 * @generated
	 */
	void removeRewiredTarget(SymbolTableEntry ste);
} // ManyReferencingElement_IM
