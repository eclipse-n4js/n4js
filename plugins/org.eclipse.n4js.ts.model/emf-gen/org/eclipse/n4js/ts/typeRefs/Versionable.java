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
package org.eclipse.n4js.ts.typeRefs;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Versionable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for all things that can provide a version. The returned value will be 0 in any language that does not
 * support versions. In those languages that do support versions, the return value depends on the semantics of the
 * actual subclass and how it implements the getVersion method.
 *  * All type references and all types extend this class. In addition, some AST nodes such as IdentifierRef and
 * N4IDLClassDeclaration also extend this class.
 *  * Type references generally delegate a call to getVersion to the type that they reference (via declaredType).
 * Exceptions are BoundThisTypeRef, which delegates to actualThisTypeRef, and ParameterizedTypeRef, which will return
 * the requested version if the user provided one.
 *  * Types usually return 0 unless they are explicitly versionable, in which case they return the declared version.
 * In N4IDL, this only applies to TClassifier, which returns the version that was declared by the user (as set by the
 * types builder).
 *  * For AST nodes, the return value depends highly on the type of the node itself. For example, the IdentifierRef returns
 * the explicitly requested version if the user provided one, otherwise it returns 0. For an N4IDLClassDeclaration,
 * getVersion returns the version that was declared by the user for that class.
 *  * It was decided that TypeRef and Type should extend Versionable to avoid switching on the type of a the Type or
 * TypeRef instance under consideration. This is possible since 0 is a sensible default for unversioned objects.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getVersionable()
 * @model abstract="true"
 * @generated
 */
public interface Versionable extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	int getVersion();

} // Versionable
