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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelDirectory='/org.eclipse.n4js.semver.model/emf-gen' forceOverwrite='true' updateClasspath='false' literalsInterface='true' loadInitialization='false' complianceLevel='8.0' copyrightFields='false' copyrightText='Copyright (c) 2016 NumberFour AG.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n  NumberFour AG - Initial API and implementation' language='' basePackage='org.eclipse.n4js.semver'"
 * @generated
 */
public interface SEMVERPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "SEMVER";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/SemanticVersioning";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "SEMVER";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SEMVERPackage eINSTANCE = org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionRangeSetImpl <em>Version Range Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionRangeSetImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionRangeSet()
	 * @generated
	 */
	int VERSION_RANGE_SET = 0;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET__RANGES = 0;

	/**
	 * The number of structural features of the '<em>Version Range Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Version Range Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionRangeImpl <em>Version Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionRangeImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionRange()
	 * @generated
	 */
	int VERSION_RANGE = 1;

	/**
	 * The number of structural features of the '<em>Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.HyphenVersionRangeImpl <em>Hyphen Version Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.HyphenVersionRangeImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getHyphenVersionRange()
	 * @generated
	 */
	int HYPHEN_VERSION_RANGE = 2;

	/**
	 * The feature id for the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPHEN_VERSION_RANGE__FROM = VERSION_RANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPHEN_VERSION_RANGE__TO = VERSION_RANGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Hyphen Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPHEN_VERSION_RANGE_FEATURE_COUNT = VERSION_RANGE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Hyphen Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPHEN_VERSION_RANGE_OPERATION_COUNT = VERSION_RANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.EnumeratedVersionRangeImpl <em>Enumerated Version Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.EnumeratedVersionRangeImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getEnumeratedVersionRange()
	 * @generated
	 */
	int ENUMERATED_VERSION_RANGE = 3;

	/**
	 * The feature id for the '<em><b>Simple Versions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS = VERSION_RANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumerated Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VERSION_RANGE_FEATURE_COUNT = VERSION_RANGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Enumerated Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VERSION_RANGE_OPERATION_COUNT = VERSION_RANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl <em>Simple Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getSimpleVersion()
	 * @generated
	 */
	int SIMPLE_VERSION = 4;

	/**
	 * The feature id for the '<em><b>Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Comparator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__COMPARATOR = 1;

	/**
	 * The feature id for the '<em><b>Has Tilde</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__HAS_TILDE = 2;

	/**
	 * The feature id for the '<em><b>Has Caret</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__HAS_CARET = 3;

	/**
	 * The number of structural features of the '<em>Simple Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Simple Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl <em>Version Number</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionNumber()
	 * @generated
	 */
	int VERSION_NUMBER = 5;

	/**
	 * The feature id for the '<em><b>Qualifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__QUALIFIER = 0;

	/**
	 * The feature id for the '<em><b>Major</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__MAJOR = 1;

	/**
	 * The feature id for the '<em><b>Minor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__MINOR = 2;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__PATH = 3;

	/**
	 * The number of structural features of the '<em>Version Number</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Version Number</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.QualifierImpl <em>Qualifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.QualifierImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getQualifier()
	 * @generated
	 */
	int QUALIFIER = 6;

	/**
	 * The feature id for the '<em><b>Pre Release</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER__PRE_RELEASE = 0;

	/**
	 * The feature id for the '<em><b>Build Metadata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER__BUILD_METADATA = 1;

	/**
	 * The number of structural features of the '<em>Qualifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Qualifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.VersionComparator <em>Version Comparator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.VersionComparator
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionComparator()
	 * @generated
	 */
	int VERSION_COMPARATOR = 7;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.VersionRangeSet <em>Version Range Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Range Set</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionRangeSet
	 * @generated
	 */
	EClass getVersionRangeSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.semver.SEMVER.VersionRangeSet#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ranges</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionRangeSet#getRanges()
	 * @see #getVersionRangeSet()
	 * @generated
	 */
	EReference getVersionRangeSet_Ranges();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.VersionRange <em>Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Range</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionRange
	 * @generated
	 */
	EClass getVersionRange();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.HyphenVersionRange <em>Hyphen Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hyphen Version Range</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.HyphenVersionRange
	 * @generated
	 */
	EClass getHyphenVersionRange();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.HyphenVersionRange#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>From</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.HyphenVersionRange#getFrom()
	 * @see #getHyphenVersionRange()
	 * @generated
	 */
	EReference getHyphenVersionRange_From();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.HyphenVersionRange#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>To</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.HyphenVersionRange#getTo()
	 * @see #getHyphenVersionRange()
	 * @generated
	 */
	EReference getHyphenVersionRange_To();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.EnumeratedVersionRange <em>Enumerated Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumerated Version Range</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.EnumeratedVersionRange
	 * @generated
	 */
	EClass getEnumeratedVersionRange();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.semver.SEMVER.EnumeratedVersionRange#getSimpleVersions <em>Simple Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Simple Versions</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.EnumeratedVersionRange#getSimpleVersions()
	 * @see #getEnumeratedVersionRange()
	 * @generated
	 */
	EReference getEnumeratedVersionRange_SimpleVersions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion <em>Simple Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Version</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion
	 * @generated
	 */
	EClass getSimpleVersion();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Number</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#getNumber()
	 * @see #getSimpleVersion()
	 * @generated
	 */
	EReference getSimpleVersion_Number();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getComparator <em>Comparator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comparator</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#getComparator()
	 * @see #getSimpleVersion()
	 * @generated
	 */
	EAttribute getSimpleVersion_Comparator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isHasTilde <em>Has Tilde</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Tilde</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isHasTilde()
	 * @see #getSimpleVersion()
	 * @generated
	 */
	EAttribute getSimpleVersion_HasTilde();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isHasCaret <em>Has Caret</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Caret</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isHasCaret()
	 * @see #getSimpleVersion()
	 * @generated
	 */
	EAttribute getSimpleVersion_HasCaret();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber <em>Version Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Number</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber
	 * @generated
	 */
	EClass getVersionNumber();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getQualifier <em>Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Qualifier</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getQualifier()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Qualifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getMajor <em>Major</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Major</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getMajor()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EAttribute getVersionNumber_Major();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getMinor <em>Minor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minor</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getMinor()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EAttribute getVersionNumber_Minor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getPath()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EAttribute getVersionNumber_Path();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.Qualifier <em>Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Qualifier</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.Qualifier
	 * @generated
	 */
	EClass getQualifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getPreRelease <em>Pre Release</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Release</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.Qualifier#getPreRelease()
	 * @see #getQualifier()
	 * @generated
	 */
	EAttribute getQualifier_PreRelease();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getBuildMetadata <em>Build Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Build Metadata</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.Qualifier#getBuildMetadata()
	 * @see #getQualifier()
	 * @generated
	 */
	EAttribute getQualifier_BuildMetadata();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.semver.SEMVER.VersionComparator <em>Version Comparator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Version Comparator</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionComparator
	 * @generated
	 */
	EEnum getVersionComparator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SEMVERFactory getSEMVERFactory();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionRangeSetImpl <em>Version Range Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionRangeSetImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionRangeSet()
		 * @generated
		 */
		EClass VERSION_RANGE_SET = eINSTANCE.getVersionRangeSet();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_RANGE_SET__RANGES = eINSTANCE.getVersionRangeSet_Ranges();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionRangeImpl <em>Version Range</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionRangeImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionRange()
		 * @generated
		 */
		EClass VERSION_RANGE = eINSTANCE.getVersionRange();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.HyphenVersionRangeImpl <em>Hyphen Version Range</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.HyphenVersionRangeImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getHyphenVersionRange()
		 * @generated
		 */
		EClass HYPHEN_VERSION_RANGE = eINSTANCE.getHyphenVersionRange();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HYPHEN_VERSION_RANGE__FROM = eINSTANCE.getHyphenVersionRange_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HYPHEN_VERSION_RANGE__TO = eINSTANCE.getHyphenVersionRange_To();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.EnumeratedVersionRangeImpl <em>Enumerated Version Range</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.EnumeratedVersionRangeImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getEnumeratedVersionRange()
		 * @generated
		 */
		EClass ENUMERATED_VERSION_RANGE = eINSTANCE.getEnumeratedVersionRange();

		/**
		 * The meta object literal for the '<em><b>Simple Versions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS = eINSTANCE.getEnumeratedVersionRange_SimpleVersions();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl <em>Simple Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getSimpleVersion()
		 * @generated
		 */
		EClass SIMPLE_VERSION = eINSTANCE.getSimpleVersion();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_VERSION__NUMBER = eINSTANCE.getSimpleVersion_Number();

		/**
		 * The meta object literal for the '<em><b>Comparator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_VERSION__COMPARATOR = eINSTANCE.getSimpleVersion_Comparator();

		/**
		 * The meta object literal for the '<em><b>Has Tilde</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_VERSION__HAS_TILDE = eINSTANCE.getSimpleVersion_HasTilde();

		/**
		 * The meta object literal for the '<em><b>Has Caret</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_VERSION__HAS_CARET = eINSTANCE.getSimpleVersion_HasCaret();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl <em>Version Number</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionNumber()
		 * @generated
		 */
		EClass VERSION_NUMBER = eINSTANCE.getVersionNumber();

		/**
		 * The meta object literal for the '<em><b>Qualifier</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_NUMBER__QUALIFIER = eINSTANCE.getVersionNumber_Qualifier();

		/**
		 * The meta object literal for the '<em><b>Major</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_NUMBER__MAJOR = eINSTANCE.getVersionNumber_Major();

		/**
		 * The meta object literal for the '<em><b>Minor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_NUMBER__MINOR = eINSTANCE.getVersionNumber_Minor();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_NUMBER__PATH = eINSTANCE.getVersionNumber_Path();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.QualifierImpl <em>Qualifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.QualifierImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getQualifier()
		 * @generated
		 */
		EClass QUALIFIER = eINSTANCE.getQualifier();

		/**
		 * The meta object literal for the '<em><b>Pre Release</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUALIFIER__PRE_RELEASE = eINSTANCE.getQualifier_PreRelease();

		/**
		 * The meta object literal for the '<em><b>Build Metadata</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUALIFIER__BUILD_METADATA = eINSTANCE.getQualifier_BuildMetadata();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.VersionComparator <em>Version Comparator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.VersionComparator
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionComparator()
		 * @generated
		 */
		EEnum VERSION_COMPARATOR = eINSTANCE.getVersionComparator();

	}

} //SEMVERPackage
