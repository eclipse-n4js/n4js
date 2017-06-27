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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TAnnotable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Although most annotations are expected to be normal fields in the type model, it may be possible that
 * some annotations are copied from the AST (see types builders).
 *  * Annotations are best retrieved via AnnotationDefinition.hasAnnotation(), as this takes transitivity into account as well.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TAnnotableElement#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTAnnotableElement()
 * @model
 * @generated
 */
public interface TAnnotableElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TAnnotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the owned annotations of the AST element; however it is recommended to access
	 * annotations only via AnnotationDefinition.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Annotations</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTAnnotableElement_Annotations()
	 * @model containment="true"
	 * @generated
	 */
	EList<TAnnotation> getAnnotations();

} // TAnnotableElement
