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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Abstract base class or all elements which can be annotated.
 * The associated annotations are obtained via {@link #getAnnotations()}.
 * Concrete annotable elements may use an {@link AbstractAnnotationList} or a containment
 * reference to hold the annotations.
 *  * Annotations are best retrieved via AnnotationDefinition.hasAnnotation(), as this takes transitivity into account as well.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getAnnotableElement()
 * @model abstract="true"
 * @generated
 */
public interface AnnotableElement extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the owned annotations of the AST element; however it is recommended to access
	 * annotations only via AnnotationDefinition and use type model annotation (or fields) if ever possible.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<Annotation> getAnnotations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the effective annotations of this annotable element. This includes the {@link #getAnnotations()}
	 * and the annotations on the containing ExportDeclaration, if any.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<Annotation> getAllAnnotations();

} // AnnotableElement
