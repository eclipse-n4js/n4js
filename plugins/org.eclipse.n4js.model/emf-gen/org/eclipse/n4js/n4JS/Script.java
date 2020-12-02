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

import org.eclipse.n4js.ts.types.TModule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Script</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Root element
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.Script#getHashbang <em>Hashbang</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.Script#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.Script#getScriptElements <em>Script Elements</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.Script#getModule <em>Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.Script#isFlaggedUsageMarkingFinished <em>Flagged Usage Marking Finished</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getScript()
 * @model
 * @generated
 */
public interface Script extends VariableEnvironmentElement, AnnotableElement, ControlFlowElement {
	/**
	 * Returns the value of the '<em><b>Hashbang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hashbang</em>' attribute.
	 * @see #setHashbang(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getScript_Hashbang()
	 * @model unique="false"
	 * @generated
	 */
	String getHashbang();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.Script#getHashbang <em>Hashbang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hashbang</em>' attribute.
	 * @see #getHashbang()
	 * @generated
	 */
	void setHashbang(String value);

	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.Annotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getScript_Annotations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Annotation> getAnnotations();

	/**
	 * Returns the value of the '<em><b>Script Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.ScriptElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Script Elements</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getScript_ScriptElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<ScriptElement> getScriptElements();

	/**
	 * Returns the value of the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module</em>' reference.
	 * @see #setModule(TModule)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getScript_Module()
	 * @model transient="true"
	 * @generated
	 */
	TModule getModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.Script#getModule <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module</em>' reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(TModule value);

	/**
	 * Returns the value of the '<em><b>Flagged Usage Marking Finished</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Flag set after linking-phase is closed. Model is in it's final state and computation
	 * of element-usage is sealed.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Flagged Usage Marking Finished</em>' attribute.
	 * @see #setFlaggedUsageMarkingFinished(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getScript_FlaggedUsageMarkingFinished()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	boolean isFlaggedUsageMarkingFinished();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.Script#isFlaggedUsageMarkingFinished <em>Flagged Usage Marking Finished</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Flagged Usage Marking Finished</em>' attribute.
	 * @see #isFlaggedUsageMarkingFinished()
	 * @generated
	 */
	void setFlaggedUsageMarkingFinished(boolean value);

} // Script
