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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Annotation List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An {@link ExpressionAnnotationList} holds annotations and can be a placeholder
 * where an {@link Expression} is expected.
 * This allows to handle syntax errors in the input file gracefully while
 * being able to left factor the grammar to make it parseable.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getExpressionAnnotationList()
 * @model
 * @generated
 */
public interface ExpressionAnnotationList extends AbstractAnnotationList, Expression {
} // ExpressionAnnotationList
