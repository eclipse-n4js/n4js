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
import org.eclipse.emf.ecore.EOperation;
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
 *        annotation="http://www.eclipse.org/emf/2002/GenModel fileExtensions='semver' modelDirectory='/org.eclipse.n4js.semver.model/emf-gen' forceOverwrite='true' updateClasspath='false' literalsInterface='true' loadInitialization='false' complianceLevel='8.0' copyrightFields='false' copyrightText='Copyright (c) 2016 NumberFour AG.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n  NumberFour AG - Initial API and implementation' language='' basePackage='org.eclipse.n4js.semver'"
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
	String eNS_URI = "http://www.eclipse.org/n4js/SEMVER";

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
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.NPMVersionImpl <em>NPM Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.NPMVersionImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getNPMVersion()
	 * @generated
	 */
	int NPM_VERSION = 0;

	/**
	 * The number of structural features of the '<em>NPM Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NPM_VERSION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>NPM Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NPM_VERSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.URLVersionImpl <em>URL Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.URLVersionImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getURLVersion()
	 * @generated
	 */
	int URL_VERSION = 1;

	/**
	 * The feature id for the '<em><b>Version Specifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION__VERSION_SPECIFIER = NPM_VERSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION__PROTOCOL = NPM_VERSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION__URL = NPM_VERSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>URL Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_FEATURE_COUNT = NPM_VERSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Has Simple Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION___HAS_SIMPLE_VERSION = NPM_VERSION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Simple Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION___GET_SIMPLE_VERSION = NPM_VERSION_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>URL Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_OPERATION_COUNT = NPM_VERSION_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.URLVersionSpecifierImpl <em>URL Version Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.URLVersionSpecifierImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getURLVersionSpecifier()
	 * @generated
	 */
	int URL_VERSION_SPECIFIER = 2;

	/**
	 * The number of structural features of the '<em>URL Version Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_SPECIFIER_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>URL Version Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_SPECIFIER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.URLSemverImpl <em>URL Semver</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.URLSemverImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getURLSemver()
	 * @generated
	 */
	int URL_SEMVER = 3;

	/**
	 * The feature id for the '<em><b>Simple Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_SEMVER__SIMPLE_VERSION = URL_VERSION_SPECIFIER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>URL Semver</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_SEMVER_FEATURE_COUNT = URL_VERSION_SPECIFIER_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>URL Semver</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_SEMVER_OPERATION_COUNT = URL_VERSION_SPECIFIER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.URLCommitISHImpl <em>URL Commit ISH</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.URLCommitISHImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getURLCommitISH()
	 * @generated
	 */
	int URL_COMMIT_ISH = 4;

	/**
	 * The feature id for the '<em><b>Commit ISH</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_COMMIT_ISH__COMMIT_ISH = URL_VERSION_SPECIFIER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>URL Commit ISH</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_COMMIT_ISH_FEATURE_COUNT = URL_VERSION_SPECIFIER_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>URL Commit ISH</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_COMMIT_ISH_OPERATION_COUNT = URL_VERSION_SPECIFIER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.GitHubVersionImpl <em>Git Hub Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.GitHubVersionImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getGitHubVersion()
	 * @generated
	 */
	int GIT_HUB_VERSION = 5;

	/**
	 * The feature id for the '<em><b>Github Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION__GITHUB_URL = NPM_VERSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Commit ISH</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION__COMMIT_ISH = NPM_VERSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Git Hub Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION_FEATURE_COUNT = NPM_VERSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Git Hub Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION_OPERATION_COUNT = NPM_VERSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.LocalPathVersionImpl <em>Local Path Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.LocalPathVersionImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getLocalPathVersion()
	 * @generated
	 */
	int LOCAL_PATH_VERSION = 6;

	/**
	 * The feature id for the '<em><b>Local Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_VERSION__LOCAL_PATH = NPM_VERSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Local Path Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_VERSION_FEATURE_COUNT = NPM_VERSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Local Path Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_VERSION_OPERATION_COUNT = NPM_VERSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.TagVersionImpl <em>Tag Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.TagVersionImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getTagVersion()
	 * @generated
	 */
	int TAG_VERSION = 7;

	/**
	 * The feature id for the '<em><b>Tag Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VERSION__TAG_NAME = NPM_VERSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Tag Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VERSION_FEATURE_COUNT = NPM_VERSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Tag Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VERSION_OPERATION_COUNT = NPM_VERSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionRangeSetImpl <em>Version Range Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionRangeSetImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionRangeSet()
	 * @generated
	 */
	int VERSION_RANGE_SET = 8;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET__RANGES = NPM_VERSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Version Range Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET_FEATURE_COUNT = NPM_VERSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Version Range Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET_OPERATION_COUNT = NPM_VERSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionRangeImpl <em>Version Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionRangeImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionRange()
	 * @generated
	 */
	int VERSION_RANGE = 9;

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
	int HYPHEN_VERSION_RANGE = 10;

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
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionRangeConstraintImpl <em>Version Range Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionRangeConstraintImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionRangeConstraint()
	 * @generated
	 */
	int VERSION_RANGE_CONSTRAINT = 11;

	/**
	 * The feature id for the '<em><b>Version Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS = VERSION_RANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Version Range Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_CONSTRAINT_FEATURE_COUNT = VERSION_RANGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Version Range Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_CONSTRAINT_OPERATION_COUNT = VERSION_RANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl <em>Simple Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getSimpleVersion()
	 * @generated
	 */
	int SIMPLE_VERSION = 12;

	/**
	 * The feature id for the '<em><b>Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Comparators</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__COMPARATORS = 1;

	/**
	 * The number of structural features of the '<em>Simple Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Is Specific</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_SPECIFIC = 0;

	/**
	 * The operation id for the '<em>Is Caret</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_CARET = 1;

	/**
	 * The operation id for the '<em>Is Tilde</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_TILDE = 2;

	/**
	 * The operation id for the '<em>Is Greater</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_GREATER = 3;

	/**
	 * The operation id for the '<em>Is Greater Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_GREATER_EQUALS = 4;

	/**
	 * The operation id for the '<em>Is Smaller</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_SMALLER = 5;

	/**
	 * The operation id for the '<em>Is Smaller Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_SMALLER_EQUALS = 6;

	/**
	 * The number of operations of the '<em>Simple Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION_OPERATION_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl <em>Version Number</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionNumber()
	 * @generated
	 */
	int VERSION_NUMBER = 13;

	/**
	 * The feature id for the '<em><b>Major</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__MAJOR = 0;

	/**
	 * The feature id for the '<em><b>Minor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__MINOR = 1;

	/**
	 * The feature id for the '<em><b>Patch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__PATCH = 2;

	/**
	 * The feature id for the '<em><b>Extended</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__EXTENDED = 3;

	/**
	 * The feature id for the '<em><b>Qualifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__QUALIFIER = 4;

	/**
	 * The number of structural features of the '<em>Version Number</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>Get Pre Release Tag</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___GET_PRE_RELEASE_TAG = 0;

	/**
	 * The operation id for the '<em>Has Pre Release Tag</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___HAS_PRE_RELEASE_TAG = 1;

	/**
	 * The operation id for the '<em>Length</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___LENGTH = 2;

	/**
	 * The operation id for the '<em>Get Part</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___GET_PART__INT = 3;

	/**
	 * The operation id for the '<em>Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___EQUALS__OBJECT = 4;

	/**
	 * The number of operations of the '<em>Version Number</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER_OPERATION_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionPartImpl <em>Version Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionPartImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionPart()
	 * @generated
	 */
	int VERSION_PART = 14;

	/**
	 * The feature id for the '<em><b>Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART__WILDCARD = 0;

	/**
	 * The feature id for the '<em><b>Number Raw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART__NUMBER_RAW = 1;

	/**
	 * The number of structural features of the '<em>Version Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Get Number</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART___GET_NUMBER = 0;

	/**
	 * The operation id for the '<em>Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART___EQUALS__OBJECT = 1;

	/**
	 * The number of operations of the '<em>Version Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.QualifierImpl <em>Qualifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.QualifierImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getQualifier()
	 * @generated
	 */
	int QUALIFIER = 15;

	/**
	 * The feature id for the '<em><b>Pre Release</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER__PRE_RELEASE = 0;

	/**
	 * The feature id for the '<em><b>Build Metadata</b></em>' containment reference.
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
	 * The operation id for the '<em>Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER___EQUALS__OBJECT = 0;

	/**
	 * The number of operations of the '<em>Qualifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.impl.QualifierTagImpl <em>Qualifier Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.impl.QualifierTagImpl
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getQualifierTag()
	 * @generated
	 */
	int QUALIFIER_TAG = 16;

	/**
	 * The feature id for the '<em><b>Parts</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG__PARTS = 0;

	/**
	 * The number of structural features of the '<em>Qualifier Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG___EQUALS__OBJECT = 0;

	/**
	 * The number of operations of the '<em>Qualifier Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.SEMVER.VersionComparator <em>Version Comparator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.SEMVER.VersionComparator
	 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionComparator()
	 * @generated
	 */
	int VERSION_COMPARATOR = 17;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.NPMVersion <em>NPM Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>NPM Version</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.NPMVersion
	 * @generated
	 */
	EClass getNPMVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.URLVersion <em>URL Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Version</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersion
	 * @generated
	 */
	EClass getURLVersion();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.URLVersion#getVersionSpecifier <em>Version Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Version Specifier</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersion#getVersionSpecifier()
	 * @see #getURLVersion()
	 * @generated
	 */
	EReference getURLVersion_VersionSpecifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.URLVersion#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Protocol</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersion#getProtocol()
	 * @see #getURLVersion()
	 * @generated
	 */
	EAttribute getURLVersion_Protocol();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.URLVersion#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersion#getUrl()
	 * @see #getURLVersion()
	 * @generated
	 */
	EAttribute getURLVersion_Url();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.URLVersion#hasSimpleVersion() <em>Has Simple Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Simple Version</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersion#hasSimpleVersion()
	 * @generated
	 */
	EOperation getURLVersion__HasSimpleVersion();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.URLVersion#getSimpleVersion() <em>Get Simple Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Simple Version</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersion#getSimpleVersion()
	 * @generated
	 */
	EOperation getURLVersion__GetSimpleVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.URLVersionSpecifier <em>URL Version Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Version Specifier</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersionSpecifier
	 * @generated
	 */
	EClass getURLVersionSpecifier();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.URLSemver <em>URL Semver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Semver</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLSemver
	 * @generated
	 */
	EClass getURLSemver();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.URLSemver#getSimpleVersion <em>Simple Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Simple Version</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLSemver#getSimpleVersion()
	 * @see #getURLSemver()
	 * @generated
	 */
	EReference getURLSemver_SimpleVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.URLCommitISH <em>URL Commit ISH</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Commit ISH</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLCommitISH
	 * @generated
	 */
	EClass getURLCommitISH();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.URLCommitISH#getCommitISH <em>Commit ISH</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commit ISH</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.URLCommitISH#getCommitISH()
	 * @see #getURLCommitISH()
	 * @generated
	 */
	EAttribute getURLCommitISH_CommitISH();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.GitHubVersion <em>Git Hub Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Git Hub Version</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.GitHubVersion
	 * @generated
	 */
	EClass getGitHubVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.GitHubVersion#getGithubUrl <em>Github Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Github Url</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.GitHubVersion#getGithubUrl()
	 * @see #getGitHubVersion()
	 * @generated
	 */
	EAttribute getGitHubVersion_GithubUrl();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.GitHubVersion#getCommitISH <em>Commit ISH</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commit ISH</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.GitHubVersion#getCommitISH()
	 * @see #getGitHubVersion()
	 * @generated
	 */
	EAttribute getGitHubVersion_CommitISH();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.LocalPathVersion <em>Local Path Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Path Version</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.LocalPathVersion
	 * @generated
	 */
	EClass getLocalPathVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.LocalPathVersion#getLocalPath <em>Local Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local Path</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.LocalPathVersion#getLocalPath()
	 * @see #getLocalPathVersion()
	 * @generated
	 */
	EAttribute getLocalPathVersion_LocalPath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.TagVersion <em>Tag Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag Version</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.TagVersion
	 * @generated
	 */
	EClass getTagVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.TagVersion#getTagName <em>Tag Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tag Name</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.TagVersion#getTagName()
	 * @see #getTagVersion()
	 * @generated
	 */
	EAttribute getTagVersion_TagName();

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
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint <em>Version Range Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Range Constraint</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint
	 * @generated
	 */
	EClass getVersionRangeConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint#getVersionConstraints <em>Version Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Version Constraints</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint#getVersionConstraints()
	 * @see #getVersionRangeConstraint()
	 * @generated
	 */
	EReference getVersionRangeConstraint_VersionConstraints();

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
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getComparators <em>Comparators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Comparators</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#getComparators()
	 * @see #getSimpleVersion()
	 * @generated
	 */
	EAttribute getSimpleVersion_Comparators();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isSpecific() <em>Is Specific</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Specific</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isSpecific()
	 * @generated
	 */
	EOperation getSimpleVersion__IsSpecific();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isCaret() <em>Is Caret</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Caret</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isCaret()
	 * @generated
	 */
	EOperation getSimpleVersion__IsCaret();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isTilde() <em>Is Tilde</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Tilde</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isTilde()
	 * @generated
	 */
	EOperation getSimpleVersion__IsTilde();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isGreater() <em>Is Greater</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Greater</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isGreater()
	 * @generated
	 */
	EOperation getSimpleVersion__IsGreater();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isGreaterEquals() <em>Is Greater Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Greater Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isGreaterEquals()
	 * @generated
	 */
	EOperation getSimpleVersion__IsGreaterEquals();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isSmaller() <em>Is Smaller</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Smaller</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isSmaller()
	 * @generated
	 */
	EOperation getSimpleVersion__IsSmaller();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isSmallerEquals() <em>Is Smaller Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Smaller Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion#isSmallerEquals()
	 * @generated
	 */
	EOperation getSimpleVersion__IsSmallerEquals();

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
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getMajor <em>Major</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Major</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getMajor()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Major();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getMinor <em>Minor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Minor</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getMinor()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Minor();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getPatch <em>Patch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Patch</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getPatch()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Patch();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getExtended <em>Extended</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Extended</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getExtended()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Extended();

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
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getPreReleaseTag() <em>Get Pre Release Tag</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Pre Release Tag</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getPreReleaseTag()
	 * @generated
	 */
	EOperation getVersionNumber__GetPreReleaseTag();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#hasPreReleaseTag() <em>Has Pre Release Tag</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Pre Release Tag</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#hasPreReleaseTag()
	 * @generated
	 */
	EOperation getVersionNumber__HasPreReleaseTag();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#length() <em>Length</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Length</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#length()
	 * @generated
	 */
	EOperation getVersionNumber__Length();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getPart(int) <em>Get Part</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Part</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#getPart(int)
	 * @generated
	 */
	EOperation getVersionNumber__GetPart__int();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#equals(java.lang.Object) <em>Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber#equals(java.lang.Object)
	 * @generated
	 */
	EOperation getVersionNumber__Equals__Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.VersionPart <em>Version Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Part</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionPart
	 * @generated
	 */
	EClass getVersionPart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.VersionPart#isWildcard <em>Wildcard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wildcard</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionPart#isWildcard()
	 * @see #getVersionPart()
	 * @generated
	 */
	EAttribute getVersionPart_Wildcard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.SEMVER.VersionPart#getNumberRaw <em>Number Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Raw</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionPart#getNumberRaw()
	 * @see #getVersionPart()
	 * @generated
	 */
	EAttribute getVersionPart_NumberRaw();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.VersionPart#getNumber() <em>Get Number</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Number</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionPart#getNumber()
	 * @generated
	 */
	EOperation getVersionPart__GetNumber();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.VersionPart#equals(java.lang.Object) <em>Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionPart#equals(java.lang.Object)
	 * @generated
	 */
	EOperation getVersionPart__Equals__Object();

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
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getPreRelease <em>Pre Release</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Release</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.Qualifier#getPreRelease()
	 * @see #getQualifier()
	 * @generated
	 */
	EReference getQualifier_PreRelease();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getBuildMetadata <em>Build Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Build Metadata</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.Qualifier#getBuildMetadata()
	 * @see #getQualifier()
	 * @generated
	 */
	EReference getQualifier_BuildMetadata();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#equals(java.lang.Object) <em>Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.Qualifier#equals(java.lang.Object)
	 * @generated
	 */
	EOperation getQualifier__Equals__Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.SEMVER.QualifierTag <em>Qualifier Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Qualifier Tag</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.QualifierTag
	 * @generated
	 */
	EClass getQualifierTag();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.semver.SEMVER.QualifierTag#getParts <em>Parts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parts</em>'.
	 * @see org.eclipse.n4js.semver.SEMVER.QualifierTag#getParts()
	 * @see #getQualifierTag()
	 * @generated
	 */
	EAttribute getQualifierTag_Parts();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.SEMVER.QualifierTag#equals(java.lang.Object) <em>Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.SEMVER.QualifierTag#equals(java.lang.Object)
	 * @generated
	 */
	EOperation getQualifierTag__Equals__Object();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.NPMVersionImpl <em>NPM Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.NPMVersionImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getNPMVersion()
		 * @generated
		 */
		EClass NPM_VERSION = eINSTANCE.getNPMVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.URLVersionImpl <em>URL Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.URLVersionImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getURLVersion()
		 * @generated
		 */
		EClass URL_VERSION = eINSTANCE.getURLVersion();

		/**
		 * The meta object literal for the '<em><b>Version Specifier</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference URL_VERSION__VERSION_SPECIFIER = eINSTANCE.getURLVersion_VersionSpecifier();

		/**
		 * The meta object literal for the '<em><b>Protocol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URL_VERSION__PROTOCOL = eINSTANCE.getURLVersion_Protocol();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URL_VERSION__URL = eINSTANCE.getURLVersion_Url();

		/**
		 * The meta object literal for the '<em><b>Has Simple Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation URL_VERSION___HAS_SIMPLE_VERSION = eINSTANCE.getURLVersion__HasSimpleVersion();

		/**
		 * The meta object literal for the '<em><b>Get Simple Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation URL_VERSION___GET_SIMPLE_VERSION = eINSTANCE.getURLVersion__GetSimpleVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.URLVersionSpecifierImpl <em>URL Version Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.URLVersionSpecifierImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getURLVersionSpecifier()
		 * @generated
		 */
		EClass URL_VERSION_SPECIFIER = eINSTANCE.getURLVersionSpecifier();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.URLSemverImpl <em>URL Semver</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.URLSemverImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getURLSemver()
		 * @generated
		 */
		EClass URL_SEMVER = eINSTANCE.getURLSemver();

		/**
		 * The meta object literal for the '<em><b>Simple Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference URL_SEMVER__SIMPLE_VERSION = eINSTANCE.getURLSemver_SimpleVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.URLCommitISHImpl <em>URL Commit ISH</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.URLCommitISHImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getURLCommitISH()
		 * @generated
		 */
		EClass URL_COMMIT_ISH = eINSTANCE.getURLCommitISH();

		/**
		 * The meta object literal for the '<em><b>Commit ISH</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URL_COMMIT_ISH__COMMIT_ISH = eINSTANCE.getURLCommitISH_CommitISH();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.GitHubVersionImpl <em>Git Hub Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.GitHubVersionImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getGitHubVersion()
		 * @generated
		 */
		EClass GIT_HUB_VERSION = eINSTANCE.getGitHubVersion();

		/**
		 * The meta object literal for the '<em><b>Github Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GIT_HUB_VERSION__GITHUB_URL = eINSTANCE.getGitHubVersion_GithubUrl();

		/**
		 * The meta object literal for the '<em><b>Commit ISH</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GIT_HUB_VERSION__COMMIT_ISH = eINSTANCE.getGitHubVersion_CommitISH();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.LocalPathVersionImpl <em>Local Path Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.LocalPathVersionImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getLocalPathVersion()
		 * @generated
		 */
		EClass LOCAL_PATH_VERSION = eINSTANCE.getLocalPathVersion();

		/**
		 * The meta object literal for the '<em><b>Local Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_PATH_VERSION__LOCAL_PATH = eINSTANCE.getLocalPathVersion_LocalPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.TagVersionImpl <em>Tag Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.TagVersionImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getTagVersion()
		 * @generated
		 */
		EClass TAG_VERSION = eINSTANCE.getTagVersion();

		/**
		 * The meta object literal for the '<em><b>Tag Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG_VERSION__TAG_NAME = eINSTANCE.getTagVersion_TagName();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionRangeConstraintImpl <em>Version Range Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionRangeConstraintImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionRangeConstraint()
		 * @generated
		 */
		EClass VERSION_RANGE_CONSTRAINT = eINSTANCE.getVersionRangeConstraint();

		/**
		 * The meta object literal for the '<em><b>Version Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS = eINSTANCE.getVersionRangeConstraint_VersionConstraints();

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
		 * The meta object literal for the '<em><b>Comparators</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_VERSION__COMPARATORS = eINSTANCE.getSimpleVersion_Comparators();

		/**
		 * The meta object literal for the '<em><b>Is Specific</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_VERSION___IS_SPECIFIC = eINSTANCE.getSimpleVersion__IsSpecific();

		/**
		 * The meta object literal for the '<em><b>Is Caret</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_VERSION___IS_CARET = eINSTANCE.getSimpleVersion__IsCaret();

		/**
		 * The meta object literal for the '<em><b>Is Tilde</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_VERSION___IS_TILDE = eINSTANCE.getSimpleVersion__IsTilde();

		/**
		 * The meta object literal for the '<em><b>Is Greater</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_VERSION___IS_GREATER = eINSTANCE.getSimpleVersion__IsGreater();

		/**
		 * The meta object literal for the '<em><b>Is Greater Equals</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_VERSION___IS_GREATER_EQUALS = eINSTANCE.getSimpleVersion__IsGreaterEquals();

		/**
		 * The meta object literal for the '<em><b>Is Smaller</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_VERSION___IS_SMALLER = eINSTANCE.getSimpleVersion__IsSmaller();

		/**
		 * The meta object literal for the '<em><b>Is Smaller Equals</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_VERSION___IS_SMALLER_EQUALS = eINSTANCE.getSimpleVersion__IsSmallerEquals();

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
		 * The meta object literal for the '<em><b>Major</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_NUMBER__MAJOR = eINSTANCE.getVersionNumber_Major();

		/**
		 * The meta object literal for the '<em><b>Minor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_NUMBER__MINOR = eINSTANCE.getVersionNumber_Minor();

		/**
		 * The meta object literal for the '<em><b>Patch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_NUMBER__PATCH = eINSTANCE.getVersionNumber_Patch();

		/**
		 * The meta object literal for the '<em><b>Extended</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_NUMBER__EXTENDED = eINSTANCE.getVersionNumber_Extended();

		/**
		 * The meta object literal for the '<em><b>Qualifier</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_NUMBER__QUALIFIER = eINSTANCE.getVersionNumber_Qualifier();

		/**
		 * The meta object literal for the '<em><b>Get Pre Release Tag</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSION_NUMBER___GET_PRE_RELEASE_TAG = eINSTANCE.getVersionNumber__GetPreReleaseTag();

		/**
		 * The meta object literal for the '<em><b>Has Pre Release Tag</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSION_NUMBER___HAS_PRE_RELEASE_TAG = eINSTANCE.getVersionNumber__HasPreReleaseTag();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSION_NUMBER___LENGTH = eINSTANCE.getVersionNumber__Length();

		/**
		 * The meta object literal for the '<em><b>Get Part</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSION_NUMBER___GET_PART__INT = eINSTANCE.getVersionNumber__GetPart__int();

		/**
		 * The meta object literal for the '<em><b>Equals</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSION_NUMBER___EQUALS__OBJECT = eINSTANCE.getVersionNumber__Equals__Object();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.VersionPartImpl <em>Version Part</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.VersionPartImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getVersionPart()
		 * @generated
		 */
		EClass VERSION_PART = eINSTANCE.getVersionPart();

		/**
		 * The meta object literal for the '<em><b>Wildcard</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_PART__WILDCARD = eINSTANCE.getVersionPart_Wildcard();

		/**
		 * The meta object literal for the '<em><b>Number Raw</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_PART__NUMBER_RAW = eINSTANCE.getVersionPart_NumberRaw();

		/**
		 * The meta object literal for the '<em><b>Get Number</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSION_PART___GET_NUMBER = eINSTANCE.getVersionPart__GetNumber();

		/**
		 * The meta object literal for the '<em><b>Equals</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSION_PART___EQUALS__OBJECT = eINSTANCE.getVersionPart__Equals__Object();

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
		 * The meta object literal for the '<em><b>Pre Release</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUALIFIER__PRE_RELEASE = eINSTANCE.getQualifier_PreRelease();

		/**
		 * The meta object literal for the '<em><b>Build Metadata</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUALIFIER__BUILD_METADATA = eINSTANCE.getQualifier_BuildMetadata();

		/**
		 * The meta object literal for the '<em><b>Equals</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation QUALIFIER___EQUALS__OBJECT = eINSTANCE.getQualifier__Equals__Object();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.SEMVER.impl.QualifierTagImpl <em>Qualifier Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.SEMVER.impl.QualifierTagImpl
		 * @see org.eclipse.n4js.semver.SEMVER.impl.SEMVERPackageImpl#getQualifierTag()
		 * @generated
		 */
		EClass QUALIFIER_TAG = eINSTANCE.getQualifierTag();

		/**
		 * The meta object literal for the '<em><b>Parts</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUALIFIER_TAG__PARTS = eINSTANCE.getQualifierTag_Parts();

		/**
		 * The meta object literal for the '<em><b>Equals</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation QUALIFIER_TAG___EQUALS__OBJECT = eINSTANCE.getQualifierTag__Equals__Object();

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
