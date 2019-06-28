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
package org.eclipse.n4js.semver.Semver;

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
 * @see org.eclipse.n4js.semver.Semver.SemverFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel fileExtensions='semver' modelDirectory='/org.eclipse.n4js.semver.model/emf-gen' forceOverwrite='true' updateClasspath='false' literalsInterface='true' loadInitialization='false' complianceLevel='11.0' copyrightFields='false' copyrightText='Copyright (c) 2016 NumberFour AG.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n  NumberFour AG - Initial API and implementation' language='' basePackage='org.eclipse.n4js.semver'"
 * @generated
 */
public interface SemverPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Semver";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/Semver";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Semver";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SemverPackage eINSTANCE = org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.SemverToStringableImpl <em>To Stringable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverToStringableImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getSemverToStringable()
	 * @generated
	 */
	int SEMVER_TO_STRINGABLE = 0;

	/**
	 * The number of structural features of the '<em>To Stringable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMVER_TO_STRINGABLE_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMVER_TO_STRINGABLE___TO_STRING = 0;

	/**
	 * The number of operations of the '<em>To Stringable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMVER_TO_STRINGABLE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.NPMVersionRequirementImpl <em>NPM Version Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.NPMVersionRequirementImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getNPMVersionRequirement()
	 * @generated
	 */
	int NPM_VERSION_REQUIREMENT = 1;

	/**
	 * The number of structural features of the '<em>NPM Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NPM_VERSION_REQUIREMENT_FEATURE_COUNT = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NPM_VERSION_REQUIREMENT___TO_STRING = SEMVER_TO_STRINGABLE___TO_STRING;

	/**
	 * The number of operations of the '<em>NPM Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NPM_VERSION_REQUIREMENT_OPERATION_COUNT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.URLVersionRequirementImpl <em>URL Version Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.URLVersionRequirementImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getURLVersionRequirement()
	 * @generated
	 */
	int URL_VERSION_REQUIREMENT = 2;

	/**
	 * The feature id for the '<em><b>Version Specifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_REQUIREMENT__VERSION_SPECIFIER = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_REQUIREMENT__PROTOCOL = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_REQUIREMENT__URL = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>URL Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_REQUIREMENT_FEATURE_COUNT = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_REQUIREMENT___TO_STRING = NPM_VERSION_REQUIREMENT___TO_STRING;

	/**
	 * The operation id for the '<em>Has Simple Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_REQUIREMENT___HAS_SIMPLE_VERSION = NPM_VERSION_REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Simple Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_REQUIREMENT___GET_SIMPLE_VERSION = NPM_VERSION_REQUIREMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>URL Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_REQUIREMENT_OPERATION_COUNT = NPM_VERSION_REQUIREMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.URLVersionSpecifierImpl <em>URL Version Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.URLVersionSpecifierImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getURLVersionSpecifier()
	 * @generated
	 */
	int URL_VERSION_SPECIFIER = 3;

	/**
	 * The number of structural features of the '<em>URL Version Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_SPECIFIER_FEATURE_COUNT = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_SPECIFIER___TO_STRING = SEMVER_TO_STRINGABLE___TO_STRING;

	/**
	 * The number of operations of the '<em>URL Version Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_VERSION_SPECIFIER_OPERATION_COUNT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.URLSemverImpl <em>URL Semver</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.URLSemverImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getURLSemver()
	 * @generated
	 */
	int URL_SEMVER = 4;

	/**
	 * The feature id for the '<em><b>Simple Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_SEMVER__SIMPLE_VERSION = URL_VERSION_SPECIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>With Semver Tag</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_SEMVER__WITH_SEMVER_TAG = URL_VERSION_SPECIFIER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>URL Semver</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_SEMVER_FEATURE_COUNT = URL_VERSION_SPECIFIER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_SEMVER___TO_STRING = URL_VERSION_SPECIFIER___TO_STRING;

	/**
	 * The number of operations of the '<em>URL Semver</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_SEMVER_OPERATION_COUNT = URL_VERSION_SPECIFIER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.URLCommitISHImpl <em>URL Commit ISH</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.URLCommitISHImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getURLCommitISH()
	 * @generated
	 */
	int URL_COMMIT_ISH = 5;

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
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_COMMIT_ISH___TO_STRING = URL_VERSION_SPECIFIER___TO_STRING;

	/**
	 * The number of operations of the '<em>URL Commit ISH</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_COMMIT_ISH_OPERATION_COUNT = URL_VERSION_SPECIFIER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.GitHubVersionRequirementImpl <em>Git Hub Version Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.GitHubVersionRequirementImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getGitHubVersionRequirement()
	 * @generated
	 */
	int GIT_HUB_VERSION_REQUIREMENT = 6;

	/**
	 * The feature id for the '<em><b>Github Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION_REQUIREMENT__GITHUB_URL = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Commit ISH</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION_REQUIREMENT__COMMIT_ISH = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Git Hub Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION_REQUIREMENT_FEATURE_COUNT = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION_REQUIREMENT___TO_STRING = NPM_VERSION_REQUIREMENT___TO_STRING;

	/**
	 * The number of operations of the '<em>Git Hub Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_HUB_VERSION_REQUIREMENT_OPERATION_COUNT = NPM_VERSION_REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.LocalPathVersionRequirementImpl <em>Local Path Version Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.LocalPathVersionRequirementImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getLocalPathVersionRequirement()
	 * @generated
	 */
	int LOCAL_PATH_VERSION_REQUIREMENT = 7;

	/**
	 * The feature id for the '<em><b>Local Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_VERSION_REQUIREMENT__LOCAL_PATH = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Local Path Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_VERSION_REQUIREMENT_FEATURE_COUNT = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_VERSION_REQUIREMENT___TO_STRING = NPM_VERSION_REQUIREMENT___TO_STRING;

	/**
	 * The number of operations of the '<em>Local Path Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_VERSION_REQUIREMENT_OPERATION_COUNT = NPM_VERSION_REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.TagVersionRequirementImpl <em>Tag Version Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.TagVersionRequirementImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getTagVersionRequirement()
	 * @generated
	 */
	int TAG_VERSION_REQUIREMENT = 8;

	/**
	 * The feature id for the '<em><b>Tag Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VERSION_REQUIREMENT__TAG_NAME = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Tag Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VERSION_REQUIREMENT_FEATURE_COUNT = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VERSION_REQUIREMENT___TO_STRING = NPM_VERSION_REQUIREMENT___TO_STRING;

	/**
	 * The number of operations of the '<em>Tag Version Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VERSION_REQUIREMENT_OPERATION_COUNT = NPM_VERSION_REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionRangeSetRequirementImpl <em>Version Range Set Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.VersionRangeSetRequirementImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionRangeSetRequirement()
	 * @generated
	 */
	int VERSION_RANGE_SET_REQUIREMENT = 9;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET_REQUIREMENT__RANGES = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Version Range Set Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET_REQUIREMENT_FEATURE_COUNT = NPM_VERSION_REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET_REQUIREMENT___TO_STRING = NPM_VERSION_REQUIREMENT___TO_STRING;

	/**
	 * The number of operations of the '<em>Version Range Set Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_SET_REQUIREMENT_OPERATION_COUNT = NPM_VERSION_REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionRangeImpl <em>Version Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.VersionRangeImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionRange()
	 * @generated
	 */
	int VERSION_RANGE = 10;

	/**
	 * The number of structural features of the '<em>Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_FEATURE_COUNT = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE___TO_STRING = SEMVER_TO_STRINGABLE___TO_STRING;

	/**
	 * The number of operations of the '<em>Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_OPERATION_COUNT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.HyphenVersionRangeImpl <em>Hyphen Version Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.HyphenVersionRangeImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getHyphenVersionRange()
	 * @generated
	 */
	int HYPHEN_VERSION_RANGE = 11;

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
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPHEN_VERSION_RANGE___TO_STRING = VERSION_RANGE___TO_STRING;

	/**
	 * The number of operations of the '<em>Hyphen Version Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPHEN_VERSION_RANGE_OPERATION_COUNT = VERSION_RANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionRangeConstraintImpl <em>Version Range Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.VersionRangeConstraintImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionRangeConstraint()
	 * @generated
	 */
	int VERSION_RANGE_CONSTRAINT = 12;

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
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_CONSTRAINT___TO_STRING = VERSION_RANGE___TO_STRING;

	/**
	 * The number of operations of the '<em>Version Range Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_RANGE_CONSTRAINT_OPERATION_COUNT = VERSION_RANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.SimpleVersionImpl <em>Simple Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.SimpleVersionImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getSimpleVersion()
	 * @generated
	 */
	int SIMPLE_VERSION = 13;

	/**
	 * The feature id for the '<em><b>Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__NUMBER = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>With Letter V</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__WITH_LETTER_V = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Comparators</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION__COMPARATORS = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Simple Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION_FEATURE_COUNT = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___TO_STRING = SEMVER_TO_STRINGABLE___TO_STRING;

	/**
	 * The operation id for the '<em>Is Wildcard</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_WILDCARD = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Specific</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_SPECIFIC = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Caret</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_CARET = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Tilde</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_TILDE = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Greater</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_GREATER = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Greater Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_GREATER_EQUALS = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Smaller</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_SMALLER = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Smaller Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION___IS_SMALLER_EQUALS = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 7;

	/**
	 * The number of operations of the '<em>Simple Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_VERSION_OPERATION_COUNT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl <em>Version Number</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionNumber()
	 * @generated
	 */
	int VERSION_NUMBER = 14;

	/**
	 * The feature id for the '<em><b>Major</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__MAJOR = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Minor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__MINOR = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Patch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__PATCH = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Extended</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__EXTENDED = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Qualifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER__QUALIFIER = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Version Number</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER_FEATURE_COUNT = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___TO_STRING = SEMVER_TO_STRINGABLE___TO_STRING;

	/**
	 * The operation id for the '<em>Is Wildcard</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___IS_WILDCARD = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Pre Release Tag</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___GET_PRE_RELEASE_TAG = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Has Pre Release Tag</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___HAS_PRE_RELEASE_TAG = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Length</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___LENGTH = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Part</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___GET_PART__INT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER___EQUALS__OBJECT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>Version Number</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_NUMBER_OPERATION_COUNT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionPartImpl <em>Version Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.VersionPartImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionPart()
	 * @generated
	 */
	int VERSION_PART = 15;

	/**
	 * The feature id for the '<em><b>Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART__WILDCARD = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Number Raw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART__NUMBER_RAW = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Version Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART_FEATURE_COUNT = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART___TO_STRING = SEMVER_TO_STRINGABLE___TO_STRING;

	/**
	 * The operation id for the '<em>Get Number</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART___GET_NUMBER = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART___EQUALS__OBJECT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Version Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PART_OPERATION_COUNT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.QualifierImpl <em>Qualifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.QualifierImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getQualifier()
	 * @generated
	 */
	int QUALIFIER = 16;

	/**
	 * The feature id for the '<em><b>Pre Release</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER__PRE_RELEASE = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Build Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER__BUILD_METADATA = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Qualifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_FEATURE_COUNT = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER___TO_STRING = SEMVER_TO_STRINGABLE___TO_STRING;

	/**
	 * The operation id for the '<em>Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER___EQUALS__OBJECT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Qualifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_OPERATION_COUNT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.impl.QualifierTagImpl <em>Qualifier Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.impl.QualifierTagImpl
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getQualifierTag()
	 * @generated
	 */
	int QUALIFIER_TAG = 17;

	/**
	 * The feature id for the '<em><b>Parts</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG__PARTS = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Qualifier Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG_FEATURE_COUNT = SEMVER_TO_STRINGABLE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG___TO_STRING = SEMVER_TO_STRINGABLE___TO_STRING;

	/**
	 * The operation id for the '<em>Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG___EQUALS__OBJECT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Qualifier Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUALIFIER_TAG_OPERATION_COUNT = SEMVER_TO_STRINGABLE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.semver.Semver.VersionComparator <em>Version Comparator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.semver.Semver.VersionComparator
	 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionComparator()
	 * @generated
	 */
	int VERSION_COMPARATOR = 18;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.SemverToStringable <em>To Stringable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>To Stringable</em>'.
	 * @see org.eclipse.n4js.semver.Semver.SemverToStringable
	 * @generated
	 */
	EClass getSemverToStringable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SemverToStringable#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SemverToStringable#toString()
	 * @generated
	 */
	EOperation getSemverToStringable__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.NPMVersionRequirement <em>NPM Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>NPM Version Requirement</em>'.
	 * @see org.eclipse.n4js.semver.Semver.NPMVersionRequirement
	 * @generated
	 */
	EClass getNPMVersionRequirement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement <em>URL Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Version Requirement</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionRequirement
	 * @generated
	 */
	EClass getURLVersionRequirement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getVersionSpecifier <em>Version Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Version Specifier</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionRequirement#getVersionSpecifier()
	 * @see #getURLVersionRequirement()
	 * @generated
	 */
	EReference getURLVersionRequirement_VersionSpecifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Protocol</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionRequirement#getProtocol()
	 * @see #getURLVersionRequirement()
	 * @generated
	 */
	EAttribute getURLVersionRequirement_Protocol();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionRequirement#getUrl()
	 * @see #getURLVersionRequirement()
	 * @generated
	 */
	EAttribute getURLVersionRequirement_Url();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#hasSimpleVersion() <em>Has Simple Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Simple Version</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionRequirement#hasSimpleVersion()
	 * @generated
	 */
	EOperation getURLVersionRequirement__HasSimpleVersion();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement#getSimpleVersion() <em>Get Simple Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Simple Version</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionRequirement#getSimpleVersion()
	 * @generated
	 */
	EOperation getURLVersionRequirement__GetSimpleVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.URLVersionSpecifier <em>URL Version Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Version Specifier</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionSpecifier
	 * @generated
	 */
	EClass getURLVersionSpecifier();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.URLSemver <em>URL Semver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Semver</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLSemver
	 * @generated
	 */
	EClass getURLSemver();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.URLSemver#getSimpleVersion <em>Simple Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Simple Version</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLSemver#getSimpleVersion()
	 * @see #getURLSemver()
	 * @generated
	 */
	EReference getURLSemver_SimpleVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.URLSemver#isWithSemverTag <em>With Semver Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>With Semver Tag</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLSemver#isWithSemverTag()
	 * @see #getURLSemver()
	 * @generated
	 */
	EAttribute getURLSemver_WithSemverTag();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.URLCommitISH <em>URL Commit ISH</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Commit ISH</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLCommitISH
	 * @generated
	 */
	EClass getURLCommitISH();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.URLCommitISH#getCommitISH <em>Commit ISH</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commit ISH</em>'.
	 * @see org.eclipse.n4js.semver.Semver.URLCommitISH#getCommitISH()
	 * @see #getURLCommitISH()
	 * @generated
	 */
	EAttribute getURLCommitISH_CommitISH();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.GitHubVersionRequirement <em>Git Hub Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Git Hub Version Requirement</em>'.
	 * @see org.eclipse.n4js.semver.Semver.GitHubVersionRequirement
	 * @generated
	 */
	EClass getGitHubVersionRequirement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.GitHubVersionRequirement#getGithubUrl <em>Github Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Github Url</em>'.
	 * @see org.eclipse.n4js.semver.Semver.GitHubVersionRequirement#getGithubUrl()
	 * @see #getGitHubVersionRequirement()
	 * @generated
	 */
	EAttribute getGitHubVersionRequirement_GithubUrl();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.GitHubVersionRequirement#getCommitISH <em>Commit ISH</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commit ISH</em>'.
	 * @see org.eclipse.n4js.semver.Semver.GitHubVersionRequirement#getCommitISH()
	 * @see #getGitHubVersionRequirement()
	 * @generated
	 */
	EAttribute getGitHubVersionRequirement_CommitISH();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement <em>Local Path Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Path Version Requirement</em>'.
	 * @see org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement
	 * @generated
	 */
	EClass getLocalPathVersionRequirement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement#getLocalPath <em>Local Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local Path</em>'.
	 * @see org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement#getLocalPath()
	 * @see #getLocalPathVersionRequirement()
	 * @generated
	 */
	EAttribute getLocalPathVersionRequirement_LocalPath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.TagVersionRequirement <em>Tag Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag Version Requirement</em>'.
	 * @see org.eclipse.n4js.semver.Semver.TagVersionRequirement
	 * @generated
	 */
	EClass getTagVersionRequirement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.TagVersionRequirement#getTagName <em>Tag Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tag Name</em>'.
	 * @see org.eclipse.n4js.semver.Semver.TagVersionRequirement#getTagName()
	 * @see #getTagVersionRequirement()
	 * @generated
	 */
	EAttribute getTagVersionRequirement_TagName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement <em>Version Range Set Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Range Set Requirement</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement
	 * @generated
	 */
	EClass getVersionRangeSetRequirement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ranges</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement#getRanges()
	 * @see #getVersionRangeSetRequirement()
	 * @generated
	 */
	EReference getVersionRangeSetRequirement_Ranges();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.VersionRange <em>Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Range</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionRange
	 * @generated
	 */
	EClass getVersionRange();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.HyphenVersionRange <em>Hyphen Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hyphen Version Range</em>'.
	 * @see org.eclipse.n4js.semver.Semver.HyphenVersionRange
	 * @generated
	 */
	EClass getHyphenVersionRange();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.HyphenVersionRange#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>From</em>'.
	 * @see org.eclipse.n4js.semver.Semver.HyphenVersionRange#getFrom()
	 * @see #getHyphenVersionRange()
	 * @generated
	 */
	EReference getHyphenVersionRange_From();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.HyphenVersionRange#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>To</em>'.
	 * @see org.eclipse.n4js.semver.Semver.HyphenVersionRange#getTo()
	 * @see #getHyphenVersionRange()
	 * @generated
	 */
	EReference getHyphenVersionRange_To();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.VersionRangeConstraint <em>Version Range Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Range Constraint</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionRangeConstraint
	 * @generated
	 */
	EClass getVersionRangeConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.semver.Semver.VersionRangeConstraint#getVersionConstraints <em>Version Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Version Constraints</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionRangeConstraint#getVersionConstraints()
	 * @see #getVersionRangeConstraint()
	 * @generated
	 */
	EReference getVersionRangeConstraint_VersionConstraints();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.SimpleVersion <em>Simple Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Version</em>'.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion
	 * @generated
	 */
	EClass getSimpleVersion();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Number</em>'.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#getNumber()
	 * @see #getSimpleVersion()
	 * @generated
	 */
	EReference getSimpleVersion_Number();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isWithLetterV <em>With Letter V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>With Letter V</em>'.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isWithLetterV()
	 * @see #getSimpleVersion()
	 * @generated
	 */
	EAttribute getSimpleVersion_WithLetterV();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#getComparators <em>Comparators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Comparators</em>'.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#getComparators()
	 * @see #getSimpleVersion()
	 * @generated
	 */
	EAttribute getSimpleVersion_Comparators();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isWildcard() <em>Is Wildcard</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Wildcard</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isWildcard()
	 * @generated
	 */
	EOperation getSimpleVersion__IsWildcard();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isSpecific() <em>Is Specific</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Specific</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isSpecific()
	 * @generated
	 */
	EOperation getSimpleVersion__IsSpecific();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isCaret() <em>Is Caret</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Caret</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isCaret()
	 * @generated
	 */
	EOperation getSimpleVersion__IsCaret();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isTilde() <em>Is Tilde</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Tilde</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isTilde()
	 * @generated
	 */
	EOperation getSimpleVersion__IsTilde();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isGreater() <em>Is Greater</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Greater</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isGreater()
	 * @generated
	 */
	EOperation getSimpleVersion__IsGreater();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isGreaterEquals() <em>Is Greater Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Greater Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isGreaterEquals()
	 * @generated
	 */
	EOperation getSimpleVersion__IsGreaterEquals();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isSmaller() <em>Is Smaller</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Smaller</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isSmaller()
	 * @generated
	 */
	EOperation getSimpleVersion__IsSmaller();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isSmallerEquals() <em>Is Smaller Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Smaller Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion#isSmallerEquals()
	 * @generated
	 */
	EOperation getSimpleVersion__IsSmallerEquals();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.VersionNumber <em>Version Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Number</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber
	 * @generated
	 */
	EClass getVersionNumber();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMajor <em>Major</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Major</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#getMajor()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Major();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMinor <em>Minor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Minor</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#getMinor()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Minor();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getPatch <em>Patch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Patch</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#getPatch()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Patch();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getExtended <em>Extended</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Extended</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#getExtended()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Extended();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getQualifier <em>Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Qualifier</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#getQualifier()
	 * @see #getVersionNumber()
	 * @generated
	 */
	EReference getVersionNumber_Qualifier();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#isWildcard() <em>Is Wildcard</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Wildcard</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#isWildcard()
	 * @generated
	 */
	EOperation getVersionNumber__IsWildcard();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getPreReleaseTag() <em>Get Pre Release Tag</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Pre Release Tag</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#getPreReleaseTag()
	 * @generated
	 */
	EOperation getVersionNumber__GetPreReleaseTag();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#hasPreReleaseTag() <em>Has Pre Release Tag</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Pre Release Tag</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#hasPreReleaseTag()
	 * @generated
	 */
	EOperation getVersionNumber__HasPreReleaseTag();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#length() <em>Length</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Length</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#length()
	 * @generated
	 */
	EOperation getVersionNumber__Length();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getPart(int) <em>Get Part</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Part</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#getPart(int)
	 * @generated
	 */
	EOperation getVersionNumber__GetPart__int();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#equals(java.lang.Object) <em>Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber#equals(java.lang.Object)
	 * @generated
	 */
	EOperation getVersionNumber__Equals__Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.VersionPart <em>Version Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Part</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionPart
	 * @generated
	 */
	EClass getVersionPart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.VersionPart#isWildcard <em>Wildcard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wildcard</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionPart#isWildcard()
	 * @see #getVersionPart()
	 * @generated
	 */
	EAttribute getVersionPart_Wildcard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.semver.Semver.VersionPart#getNumberRaw <em>Number Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Raw</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionPart#getNumberRaw()
	 * @see #getVersionPart()
	 * @generated
	 */
	EAttribute getVersionPart_NumberRaw();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.VersionPart#getNumber() <em>Get Number</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Number</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.VersionPart#getNumber()
	 * @generated
	 */
	EOperation getVersionPart__GetNumber();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.VersionPart#equals(java.lang.Object) <em>Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.VersionPart#equals(java.lang.Object)
	 * @generated
	 */
	EOperation getVersionPart__Equals__Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.Qualifier <em>Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Qualifier</em>'.
	 * @see org.eclipse.n4js.semver.Semver.Qualifier
	 * @generated
	 */
	EClass getQualifier();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.Qualifier#getPreRelease <em>Pre Release</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Release</em>'.
	 * @see org.eclipse.n4js.semver.Semver.Qualifier#getPreRelease()
	 * @see #getQualifier()
	 * @generated
	 */
	EReference getQualifier_PreRelease();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.semver.Semver.Qualifier#getBuildMetadata <em>Build Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Build Metadata</em>'.
	 * @see org.eclipse.n4js.semver.Semver.Qualifier#getBuildMetadata()
	 * @see #getQualifier()
	 * @generated
	 */
	EReference getQualifier_BuildMetadata();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.Qualifier#equals(java.lang.Object) <em>Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.Qualifier#equals(java.lang.Object)
	 * @generated
	 */
	EOperation getQualifier__Equals__Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.semver.Semver.QualifierTag <em>Qualifier Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Qualifier Tag</em>'.
	 * @see org.eclipse.n4js.semver.Semver.QualifierTag
	 * @generated
	 */
	EClass getQualifierTag();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.semver.Semver.QualifierTag#getParts <em>Parts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parts</em>'.
	 * @see org.eclipse.n4js.semver.Semver.QualifierTag#getParts()
	 * @see #getQualifierTag()
	 * @generated
	 */
	EAttribute getQualifierTag_Parts();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.semver.Semver.QualifierTag#equals(java.lang.Object) <em>Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equals</em>' operation.
	 * @see org.eclipse.n4js.semver.Semver.QualifierTag#equals(java.lang.Object)
	 * @generated
	 */
	EOperation getQualifierTag__Equals__Object();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.semver.Semver.VersionComparator <em>Version Comparator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Version Comparator</em>'.
	 * @see org.eclipse.n4js.semver.Semver.VersionComparator
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
	SemverFactory getSemverFactory();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.SemverToStringableImpl <em>To Stringable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverToStringableImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getSemverToStringable()
		 * @generated
		 */
		EClass SEMVER_TO_STRINGABLE = eINSTANCE.getSemverToStringable();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SEMVER_TO_STRINGABLE___TO_STRING = eINSTANCE.getSemverToStringable__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.NPMVersionRequirementImpl <em>NPM Version Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.NPMVersionRequirementImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getNPMVersionRequirement()
		 * @generated
		 */
		EClass NPM_VERSION_REQUIREMENT = eINSTANCE.getNPMVersionRequirement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.URLVersionRequirementImpl <em>URL Version Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.URLVersionRequirementImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getURLVersionRequirement()
		 * @generated
		 */
		EClass URL_VERSION_REQUIREMENT = eINSTANCE.getURLVersionRequirement();

		/**
		 * The meta object literal for the '<em><b>Version Specifier</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference URL_VERSION_REQUIREMENT__VERSION_SPECIFIER = eINSTANCE.getURLVersionRequirement_VersionSpecifier();

		/**
		 * The meta object literal for the '<em><b>Protocol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URL_VERSION_REQUIREMENT__PROTOCOL = eINSTANCE.getURLVersionRequirement_Protocol();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URL_VERSION_REQUIREMENT__URL = eINSTANCE.getURLVersionRequirement_Url();

		/**
		 * The meta object literal for the '<em><b>Has Simple Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation URL_VERSION_REQUIREMENT___HAS_SIMPLE_VERSION = eINSTANCE.getURLVersionRequirement__HasSimpleVersion();

		/**
		 * The meta object literal for the '<em><b>Get Simple Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation URL_VERSION_REQUIREMENT___GET_SIMPLE_VERSION = eINSTANCE.getURLVersionRequirement__GetSimpleVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.URLVersionSpecifierImpl <em>URL Version Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.URLVersionSpecifierImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getURLVersionSpecifier()
		 * @generated
		 */
		EClass URL_VERSION_SPECIFIER = eINSTANCE.getURLVersionSpecifier();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.URLSemverImpl <em>URL Semver</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.URLSemverImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getURLSemver()
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
		 * The meta object literal for the '<em><b>With Semver Tag</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URL_SEMVER__WITH_SEMVER_TAG = eINSTANCE.getURLSemver_WithSemverTag();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.URLCommitISHImpl <em>URL Commit ISH</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.URLCommitISHImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getURLCommitISH()
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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.GitHubVersionRequirementImpl <em>Git Hub Version Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.GitHubVersionRequirementImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getGitHubVersionRequirement()
		 * @generated
		 */
		EClass GIT_HUB_VERSION_REQUIREMENT = eINSTANCE.getGitHubVersionRequirement();

		/**
		 * The meta object literal for the '<em><b>Github Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GIT_HUB_VERSION_REQUIREMENT__GITHUB_URL = eINSTANCE.getGitHubVersionRequirement_GithubUrl();

		/**
		 * The meta object literal for the '<em><b>Commit ISH</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GIT_HUB_VERSION_REQUIREMENT__COMMIT_ISH = eINSTANCE.getGitHubVersionRequirement_CommitISH();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.LocalPathVersionRequirementImpl <em>Local Path Version Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.LocalPathVersionRequirementImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getLocalPathVersionRequirement()
		 * @generated
		 */
		EClass LOCAL_PATH_VERSION_REQUIREMENT = eINSTANCE.getLocalPathVersionRequirement();

		/**
		 * The meta object literal for the '<em><b>Local Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_PATH_VERSION_REQUIREMENT__LOCAL_PATH = eINSTANCE.getLocalPathVersionRequirement_LocalPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.TagVersionRequirementImpl <em>Tag Version Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.TagVersionRequirementImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getTagVersionRequirement()
		 * @generated
		 */
		EClass TAG_VERSION_REQUIREMENT = eINSTANCE.getTagVersionRequirement();

		/**
		 * The meta object literal for the '<em><b>Tag Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG_VERSION_REQUIREMENT__TAG_NAME = eINSTANCE.getTagVersionRequirement_TagName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionRangeSetRequirementImpl <em>Version Range Set Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.VersionRangeSetRequirementImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionRangeSetRequirement()
		 * @generated
		 */
		EClass VERSION_RANGE_SET_REQUIREMENT = eINSTANCE.getVersionRangeSetRequirement();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_RANGE_SET_REQUIREMENT__RANGES = eINSTANCE.getVersionRangeSetRequirement_Ranges();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionRangeImpl <em>Version Range</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.VersionRangeImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionRange()
		 * @generated
		 */
		EClass VERSION_RANGE = eINSTANCE.getVersionRange();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.HyphenVersionRangeImpl <em>Hyphen Version Range</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.HyphenVersionRangeImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getHyphenVersionRange()
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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionRangeConstraintImpl <em>Version Range Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.VersionRangeConstraintImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionRangeConstraint()
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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.SimpleVersionImpl <em>Simple Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.SimpleVersionImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getSimpleVersion()
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
		 * The meta object literal for the '<em><b>With Letter V</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_VERSION__WITH_LETTER_V = eINSTANCE.getSimpleVersion_WithLetterV();

		/**
		 * The meta object literal for the '<em><b>Comparators</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_VERSION__COMPARATORS = eINSTANCE.getSimpleVersion_Comparators();

		/**
		 * The meta object literal for the '<em><b>Is Wildcard</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_VERSION___IS_WILDCARD = eINSTANCE.getSimpleVersion__IsWildcard();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl <em>Version Number</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionNumber()
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
		 * The meta object literal for the '<em><b>Is Wildcard</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSION_NUMBER___IS_WILDCARD = eINSTANCE.getVersionNumber__IsWildcard();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.VersionPartImpl <em>Version Part</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.VersionPartImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionPart()
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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.QualifierImpl <em>Qualifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.QualifierImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getQualifier()
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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.impl.QualifierTagImpl <em>Qualifier Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.impl.QualifierTagImpl
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getQualifierTag()
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
		 * The meta object literal for the '{@link org.eclipse.n4js.semver.Semver.VersionComparator <em>Version Comparator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.semver.Semver.VersionComparator
		 * @see org.eclipse.n4js.semver.Semver.impl.SemverPackageImpl#getVersionComparator()
		 * @generated
		 */
		EEnum VERSION_COMPARATOR = eINSTANCE.getVersionComparator();

	}

} //SemverPackage
