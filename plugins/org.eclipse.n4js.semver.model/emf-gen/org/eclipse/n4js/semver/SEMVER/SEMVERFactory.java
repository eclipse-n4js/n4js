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
package org.eclipse.n4js.semver.SEMVER;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage
 * @generated
 */
public interface SEMVERFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SEMVERFactory eINSTANCE = org.eclipse.n4js.semver.SEMVER.impl.SEMVERFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Version Range Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Range Set</em>'.
	 * @generated
	 */
	VersionRangeSet createVersionRangeSet();

	/**
	 * Returns a new object of class '<em>Hyphen Version Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hyphen Version Range</em>'.
	 * @generated
	 */
	HyphenVersionRange createHyphenVersionRange();

	/**
	 * Returns a new object of class '<em>Version Range Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Range Constraint</em>'.
	 * @generated
	 */
	VersionRangeConstraint createVersionRangeConstraint();

	/**
	 * Returns a new object of class '<em>Simple Version</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Version</em>'.
	 * @generated
	 */
	SimpleVersion createSimpleVersion();

	/**
	 * Returns a new object of class '<em>Version Number</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Number</em>'.
	 * @generated
	 */
	VersionNumber createVersionNumber();

	/**
	 * Returns a new object of class '<em>Version Part</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Part</em>'.
	 * @generated
	 */
	VersionPart createVersionPart();

	/**
	 * Returns a new object of class '<em>Qualifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Qualifier</em>'.
	 * @generated
	 */
	Qualifier createQualifier();

	/**
	 * Returns a new object of class '<em>Qualifier Tag</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Qualifier Tag</em>'.
	 * @generated
	 */
	QualifierTag createQualifierTag();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SEMVERPackage getSEMVERPackage();

} //SEMVERFactory
