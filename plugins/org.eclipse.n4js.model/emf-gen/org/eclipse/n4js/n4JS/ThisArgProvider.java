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
 * A representation of the model object '<em><b>This Arg Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Depending on the mode the this keyword may be bound to the thisArg, implicitly
 * set in case of function calls. Thus, a "ThisArgProvider" may be a function,
 * which could also be a getter or setter (which are not defined as function definitions).
 * (cf ECMAScript 10.4.3 Entering Function Code)
 * Note that this is not bound by local scoping, as \'this\' is not a variable (or cross reference)!
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getThisArgProvider()
 * @model abstract="true"
 * @generated
 */
public interface ThisArgProvider extends EObject {
} // ThisArgProvider
