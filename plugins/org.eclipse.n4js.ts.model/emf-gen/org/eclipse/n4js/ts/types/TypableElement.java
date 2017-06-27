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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Typable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An element that has an (actual) type that can be inferred using the type system.
 * <p>
 * Notes:
 * <ul>
 * <li>not all typable elements have an expected type; only {@code Expression}s have an expected type.
 * <li>subclasses of AbstractAnnotationList are <b>not</b> typable, even though they inherit from
 *     {@code TypableElement} (this special case is handled in {@code N4JSLanguageUtils#isTypableNode()}.
 * </ul>
 * <p>
 * Don't confuse with {@link org.eclipse.n4js.n4JS.TypedElement TypedElement}: a {@code TypedElement} can be
 * given a type by the programmer via a type declaration (usually optional). {@code TypedElement}s are usually
 * {@code TypableNode}s, but most {@code TypableNode}s aren't {@code TypedElement}s (e.g. expressions).
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypableElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface TypableElement extends EObject {
} // TypableElement
