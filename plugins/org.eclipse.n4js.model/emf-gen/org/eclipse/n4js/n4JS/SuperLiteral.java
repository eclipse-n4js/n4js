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
 * A representation of the model object '<em><b>Super Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getSuperLiteral()
 * @model
 * @generated
 */
public interface SuperLiteral extends PrimaryExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if super literal is directly contained in a call expression.
	 * This is a call to the super constructor of a class, which is only allowed in a constructor of a subclass.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.ecore.EObject%&gt; _eContainer = this.eContainer();\nreturn (_eContainer instanceof &lt;%org.eclipse.n4js.n4JS.ParameterizedCallExpression%&gt;);'"
	 * @generated
	 */
	boolean isSuperConstructorAccess();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if super literal is directly contained in a property or index access expression.
	 * This is a call to a super's member, e.g., via "super.foo()".
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.eContainer() instanceof &lt;%org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression%&gt;) || (this.eContainer() instanceof &lt;%org.eclipse.n4js.n4JS.IndexedAccessExpression%&gt;));'"
	 * @generated
	 */
	boolean isSuperMemberAccess();

} // SuperLiteral
