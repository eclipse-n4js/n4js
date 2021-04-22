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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TDynamic Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An identifiable element imported via a dynamic named or dynamic default import, with unknown characteristics
 * that may or may not exist. However, we assume it exists and that it can be used in whatever way the programmer
 * wants. In this sense, it corresponds to the dynamic type 'any+'.
 * <p>
 * A {@code TDynamicElement} will never be exported, but inside a module it plays the role of imported elements,
 * which are always subtypes of 'TExportableElement', and thus the super type 'TExportableElement' is required.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTDynamicElement()
 * @model
 * @generated
 */
public interface TDynamicElement extends TExportableElement, SyntaxRelatedTElement {
} // TDynamicElement
