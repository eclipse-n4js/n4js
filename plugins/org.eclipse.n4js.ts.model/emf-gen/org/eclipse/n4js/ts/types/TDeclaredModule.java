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
 * A representation of the model object '<em><b>TDeclared Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This type of module is created only from .d.ts files and represents a module created from
 * a module declaration such as
 * <pre>
 * // .d.ts
 * declare module "my/declared/module" { ... }
 * </pre>
 * Note that module declarations of the form
 * <pre>
 * // .d.ts
 * declare module myLegacyModule { ... }
 * </pre>
 * are actually an old syntax for namespaces and will thus be represented as a {@link TNamespace},
 * not as a {@code TDeclaredModule}.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTDeclaredModule()
 * @model
 * @generated
 */
public interface TDeclaredModule extends AbstractModule {
} // TDeclaredModule
