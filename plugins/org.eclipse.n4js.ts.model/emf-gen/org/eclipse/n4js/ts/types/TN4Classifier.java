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
 * A representation of the model object '<em><b>TN4 Classifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TN4Classifier#isDynamizable <em>Dynamizable</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TN4Classifier#getTypingStrategy <em>Typing Strategy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTN4Classifier()
 * @model
 * @generated
 */
public interface TN4Classifier extends TClassifier, AccessibleTypeElement, TMigratable {
	/**
	 * Returns the value of the '<em><b>Dynamizable</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Flag indicating whether references to this type may be flagged as dynamic.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dynamizable</em>' attribute.
	 * @see #setDynamizable(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTN4Classifier_Dynamizable()
	 * @model default="true" unique="false"
	 * @generated
	 */
	boolean isDynamizable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TN4Classifier#isDynamizable <em>Dynamizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dynamizable</em>' attribute.
	 * @see #isDynamizable()
	 * @generated
	 */
	void setDynamizable(boolean value);

	/**
	 * Returns the value of the '<em><b>Typing Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.ts.types.TypingStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The typing strategy on definition site, nominal typing by default.
	 * Could be changed to structural but not to structural field.
	 * The latter is only available on use site.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Typing Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypingStrategy
	 * @see #setTypingStrategy(TypingStrategy)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTN4Classifier_TypingStrategy()
	 * @model unique="false"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TN4Classifier#getTypingStrategy <em>Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Typing Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypingStrategy
	 * @see #getTypingStrategy()
	 * @generated
	 */
	void setTypingStrategy(TypingStrategy value);

} // TN4Classifier
