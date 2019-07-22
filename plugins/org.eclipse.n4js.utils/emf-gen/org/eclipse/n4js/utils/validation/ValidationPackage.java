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
package org.eclipse.n4js.utils.validation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * *
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  * Contributors:
 *   NumberFour AG - Initial API and implementation
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.utils.validation.ValidationFactory
 * @model kind="package"
 * @generated
 */
public interface ValidationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "validation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/utils/Validation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "validation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ValidationPackage eINSTANCE = org.eclipse.n4js.utils.validation.impl.ValidationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.utils.validation.impl.ValidationMarkerImpl <em>Marker</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.utils.validation.impl.ValidationMarkerImpl
	 * @see org.eclipse.n4js.utils.validation.impl.ValidationPackageImpl#getValidationMarker()
	 * @generated
	 */
	int VALIDATION_MARKER = 0;

	/**
	 * The feature id for the '<em><b>Delegate Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MARKER__DELEGATE_RESOURCE = 0;

	/**
	 * The number of structural features of the '<em>Marker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MARKER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>EResource</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MARKER___ERESOURCE = 0;

	/**
	 * The number of operations of the '<em>Marker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MARKER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.utils.validation.impl.PreValidationImpl <em>Pre Validation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.utils.validation.impl.PreValidationImpl
	 * @see org.eclipse.n4js.utils.validation.impl.ValidationPackageImpl#getPreValidation()
	 * @generated
	 */
	int PRE_VALIDATION = 1;

	/**
	 * The feature id for the '<em><b>Delegate Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_VALIDATION__DELEGATE_RESOURCE = VALIDATION_MARKER__DELEGATE_RESOURCE;

	/**
	 * The number of structural features of the '<em>Pre Validation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_VALIDATION_FEATURE_COUNT = VALIDATION_MARKER_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>EResource</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_VALIDATION___ERESOURCE = VALIDATION_MARKER___ERESOURCE;

	/**
	 * The number of operations of the '<em>Pre Validation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_VALIDATION_OPERATION_COUNT = VALIDATION_MARKER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.utils.validation.impl.PostValidationImpl <em>Post Validation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.utils.validation.impl.PostValidationImpl
	 * @see org.eclipse.n4js.utils.validation.impl.ValidationPackageImpl#getPostValidation()
	 * @generated
	 */
	int POST_VALIDATION = 2;

	/**
	 * The feature id for the '<em><b>Delegate Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_VALIDATION__DELEGATE_RESOURCE = VALIDATION_MARKER__DELEGATE_RESOURCE;

	/**
	 * The number of structural features of the '<em>Post Validation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_VALIDATION_FEATURE_COUNT = VALIDATION_MARKER_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>EResource</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_VALIDATION___ERESOURCE = VALIDATION_MARKER___ERESOURCE;

	/**
	 * The number of operations of the '<em>Post Validation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_VALIDATION_OPERATION_COUNT = VALIDATION_MARKER_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.utils.validation.ValidationMarker <em>Marker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Marker</em>'.
	 * @see org.eclipse.n4js.utils.validation.ValidationMarker
	 * @generated
	 */
	EClass getValidationMarker();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.utils.validation.ValidationMarker#getDelegateResource <em>Delegate Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delegate Resource</em>'.
	 * @see org.eclipse.n4js.utils.validation.ValidationMarker#getDelegateResource()
	 * @see #getValidationMarker()
	 * @generated
	 */
	EAttribute getValidationMarker_DelegateResource();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.utils.validation.ValidationMarker#eResource() <em>EResource</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>EResource</em>' operation.
	 * @see org.eclipse.n4js.utils.validation.ValidationMarker#eResource()
	 * @generated
	 */
	EOperation getValidationMarker__EResource();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.utils.validation.PreValidation <em>Pre Validation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Validation</em>'.
	 * @see org.eclipse.n4js.utils.validation.PreValidation
	 * @generated
	 */
	EClass getPreValidation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.utils.validation.PostValidation <em>Post Validation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Validation</em>'.
	 * @see org.eclipse.n4js.utils.validation.PostValidation
	 * @generated
	 */
	EClass getPostValidation();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ValidationFactory getValidationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.utils.validation.impl.ValidationMarkerImpl <em>Marker</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.utils.validation.impl.ValidationMarkerImpl
		 * @see org.eclipse.n4js.utils.validation.impl.ValidationPackageImpl#getValidationMarker()
		 * @generated
		 */
		EClass VALIDATION_MARKER = eINSTANCE.getValidationMarker();

		/**
		 * The meta object literal for the '<em><b>Delegate Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATION_MARKER__DELEGATE_RESOURCE = eINSTANCE.getValidationMarker_DelegateResource();

		/**
		 * The meta object literal for the '<em><b>EResource</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VALIDATION_MARKER___ERESOURCE = eINSTANCE.getValidationMarker__EResource();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.utils.validation.impl.PreValidationImpl <em>Pre Validation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.utils.validation.impl.PreValidationImpl
		 * @see org.eclipse.n4js.utils.validation.impl.ValidationPackageImpl#getPreValidation()
		 * @generated
		 */
		EClass PRE_VALIDATION = eINSTANCE.getPreValidation();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.utils.validation.impl.PostValidationImpl <em>Post Validation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.utils.validation.impl.PostValidationImpl
		 * @see org.eclipse.n4js.utils.validation.impl.ValidationPackageImpl#getPostValidation()
		 * @generated
		 */
		EClass POST_VALIDATION = eINSTANCE.getPostValidation();

	}

} //ValidationPackage
