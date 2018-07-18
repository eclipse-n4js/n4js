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
package org.eclipse.n4js.semver.SEMVER.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.semver.SEMVER.GitHubVersionRequirement;
import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange;
import org.eclipse.n4js.semver.SEMVER.LocalPathVersionRequirement;
import org.eclipse.n4js.semver.SEMVER.NPMVersionRequirement;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.QualifierTag;
import org.eclipse.n4js.semver.SEMVER.SEMVERFactory;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.SEMVERtoStringable;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.TagVersionRequirement;
import org.eclipse.n4js.semver.SEMVER.URLCommitISH;
import org.eclipse.n4js.semver.SEMVER.URLSemver;
import org.eclipse.n4js.semver.SEMVER.URLVersionRequirement;
import org.eclipse.n4js.semver.SEMVER.URLVersionSpecifier;
import org.eclipse.n4js.semver.SEMVER.VersionComparator;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionPart;
import org.eclipse.n4js.semver.SEMVER.VersionRange;
import org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSetRequirement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SEMVERPackageImpl extends EPackageImpl implements SEMVERPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass semveRtoStringableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass npmVersionRequirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass urlVersionRequirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass urlVersionSpecifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass urlSemverEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass urlCommitISHEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gitHubVersionRequirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localPathVersionRequirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tagVersionRequirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionRangeSetRequirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionRangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hyphenVersionRangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionRangeConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionNumberEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionPartEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass qualifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass qualifierTagEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum versionComparatorEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SEMVERPackageImpl() {
		super(eNS_URI, SEMVERFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link SEMVERPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SEMVERPackage init() {
		if (isInited) return (SEMVERPackage)EPackage.Registry.INSTANCE.getEPackage(SEMVERPackage.eNS_URI);

		// Obtain or create and register package
		SEMVERPackageImpl theSEMVERPackage = (SEMVERPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SEMVERPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SEMVERPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSEMVERPackage.createPackageContents();

		// Initialize created meta-data
		theSEMVERPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSEMVERPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SEMVERPackage.eNS_URI, theSEMVERPackage);
		return theSEMVERPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSEMVERtoStringable() {
		return semveRtoStringableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSEMVERtoStringable__ToString() {
		return semveRtoStringableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNPMVersionRequirement() {
		return npmVersionRequirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getURLVersionRequirement() {
		return urlVersionRequirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getURLVersionRequirement_VersionSpecifier() {
		return (EReference)urlVersionRequirementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getURLVersionRequirement_Protocol() {
		return (EAttribute)urlVersionRequirementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getURLVersionRequirement_Url() {
		return (EAttribute)urlVersionRequirementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getURLVersionRequirement__HasSimpleVersion() {
		return urlVersionRequirementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getURLVersionRequirement__GetSimpleVersion() {
		return urlVersionRequirementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getURLVersionSpecifier() {
		return urlVersionSpecifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getURLSemver() {
		return urlSemverEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getURLSemver_SimpleVersion() {
		return (EReference)urlSemverEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getURLCommitISH() {
		return urlCommitISHEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getURLCommitISH_CommitISH() {
		return (EAttribute)urlCommitISHEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGitHubVersionRequirement() {
		return gitHubVersionRequirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGitHubVersionRequirement_GithubUrl() {
		return (EAttribute)gitHubVersionRequirementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGitHubVersionRequirement_CommitISH() {
		return (EAttribute)gitHubVersionRequirementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalPathVersionRequirement() {
		return localPathVersionRequirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalPathVersionRequirement_LocalPath() {
		return (EAttribute)localPathVersionRequirementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTagVersionRequirement() {
		return tagVersionRequirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTagVersionRequirement_TagName() {
		return (EAttribute)tagVersionRequirementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersionRangeSetRequirement() {
		return versionRangeSetRequirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionRangeSetRequirement_Ranges() {
		return (EReference)versionRangeSetRequirementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersionRange() {
		return versionRangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHyphenVersionRange() {
		return hyphenVersionRangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHyphenVersionRange_From() {
		return (EReference)hyphenVersionRangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHyphenVersionRange_To() {
		return (EReference)hyphenVersionRangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersionRangeConstraint() {
		return versionRangeConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionRangeConstraint_VersionConstraints() {
		return (EReference)versionRangeConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleVersion() {
		return simpleVersionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimpleVersion_Number() {
		return (EReference)simpleVersionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleVersion_Comparators() {
		return (EAttribute)simpleVersionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleVersion__IsSpecific() {
		return simpleVersionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleVersion__IsCaret() {
		return simpleVersionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleVersion__IsTilde() {
		return simpleVersionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleVersion__IsGreater() {
		return simpleVersionEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleVersion__IsGreaterEquals() {
		return simpleVersionEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleVersion__IsSmaller() {
		return simpleVersionEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleVersion__IsSmallerEquals() {
		return simpleVersionEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersionNumber() {
		return versionNumberEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionNumber_Major() {
		return (EReference)versionNumberEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionNumber_Minor() {
		return (EReference)versionNumberEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionNumber_Patch() {
		return (EReference)versionNumberEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionNumber_Extended() {
		return (EReference)versionNumberEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionNumber_Qualifier() {
		return (EReference)versionNumberEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVersionNumber__GetPreReleaseTag() {
		return versionNumberEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVersionNumber__HasPreReleaseTag() {
		return versionNumberEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVersionNumber__Length() {
		return versionNumberEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVersionNumber__GetPart__int() {
		return versionNumberEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVersionNumber__Equals__Object() {
		return versionNumberEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersionPart() {
		return versionPartEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersionPart_Wildcard() {
		return (EAttribute)versionPartEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersionPart_NumberRaw() {
		return (EAttribute)versionPartEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVersionPart__GetNumber() {
		return versionPartEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVersionPart__Equals__Object() {
		return versionPartEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQualifier() {
		return qualifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQualifier_PreRelease() {
		return (EReference)qualifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQualifier_BuildMetadata() {
		return (EReference)qualifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getQualifier__Equals__Object() {
		return qualifierEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQualifierTag() {
		return qualifierTagEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQualifierTag_Parts() {
		return (EAttribute)qualifierTagEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getQualifierTag__Equals__Object() {
		return qualifierTagEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getVersionComparator() {
		return versionComparatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SEMVERFactory getSEMVERFactory() {
		return (SEMVERFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		semveRtoStringableEClass = createEClass(SEMVE_RTO_STRINGABLE);
		createEOperation(semveRtoStringableEClass, SEMVE_RTO_STRINGABLE___TO_STRING);

		npmVersionRequirementEClass = createEClass(NPM_VERSION_REQUIREMENT);

		urlVersionRequirementEClass = createEClass(URL_VERSION_REQUIREMENT);
		createEReference(urlVersionRequirementEClass, URL_VERSION_REQUIREMENT__VERSION_SPECIFIER);
		createEAttribute(urlVersionRequirementEClass, URL_VERSION_REQUIREMENT__PROTOCOL);
		createEAttribute(urlVersionRequirementEClass, URL_VERSION_REQUIREMENT__URL);
		createEOperation(urlVersionRequirementEClass, URL_VERSION_REQUIREMENT___HAS_SIMPLE_VERSION);
		createEOperation(urlVersionRequirementEClass, URL_VERSION_REQUIREMENT___GET_SIMPLE_VERSION);

		urlVersionSpecifierEClass = createEClass(URL_VERSION_SPECIFIER);

		urlSemverEClass = createEClass(URL_SEMVER);
		createEReference(urlSemverEClass, URL_SEMVER__SIMPLE_VERSION);

		urlCommitISHEClass = createEClass(URL_COMMIT_ISH);
		createEAttribute(urlCommitISHEClass, URL_COMMIT_ISH__COMMIT_ISH);

		gitHubVersionRequirementEClass = createEClass(GIT_HUB_VERSION_REQUIREMENT);
		createEAttribute(gitHubVersionRequirementEClass, GIT_HUB_VERSION_REQUIREMENT__GITHUB_URL);
		createEAttribute(gitHubVersionRequirementEClass, GIT_HUB_VERSION_REQUIREMENT__COMMIT_ISH);

		localPathVersionRequirementEClass = createEClass(LOCAL_PATH_VERSION_REQUIREMENT);
		createEAttribute(localPathVersionRequirementEClass, LOCAL_PATH_VERSION_REQUIREMENT__LOCAL_PATH);

		tagVersionRequirementEClass = createEClass(TAG_VERSION_REQUIREMENT);
		createEAttribute(tagVersionRequirementEClass, TAG_VERSION_REQUIREMENT__TAG_NAME);

		versionRangeSetRequirementEClass = createEClass(VERSION_RANGE_SET_REQUIREMENT);
		createEReference(versionRangeSetRequirementEClass, VERSION_RANGE_SET_REQUIREMENT__RANGES);

		versionRangeEClass = createEClass(VERSION_RANGE);

		hyphenVersionRangeEClass = createEClass(HYPHEN_VERSION_RANGE);
		createEReference(hyphenVersionRangeEClass, HYPHEN_VERSION_RANGE__FROM);
		createEReference(hyphenVersionRangeEClass, HYPHEN_VERSION_RANGE__TO);

		versionRangeConstraintEClass = createEClass(VERSION_RANGE_CONSTRAINT);
		createEReference(versionRangeConstraintEClass, VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS);

		simpleVersionEClass = createEClass(SIMPLE_VERSION);
		createEReference(simpleVersionEClass, SIMPLE_VERSION__NUMBER);
		createEAttribute(simpleVersionEClass, SIMPLE_VERSION__COMPARATORS);
		createEOperation(simpleVersionEClass, SIMPLE_VERSION___IS_SPECIFIC);
		createEOperation(simpleVersionEClass, SIMPLE_VERSION___IS_CARET);
		createEOperation(simpleVersionEClass, SIMPLE_VERSION___IS_TILDE);
		createEOperation(simpleVersionEClass, SIMPLE_VERSION___IS_GREATER);
		createEOperation(simpleVersionEClass, SIMPLE_VERSION___IS_GREATER_EQUALS);
		createEOperation(simpleVersionEClass, SIMPLE_VERSION___IS_SMALLER);
		createEOperation(simpleVersionEClass, SIMPLE_VERSION___IS_SMALLER_EQUALS);

		versionNumberEClass = createEClass(VERSION_NUMBER);
		createEReference(versionNumberEClass, VERSION_NUMBER__MAJOR);
		createEReference(versionNumberEClass, VERSION_NUMBER__MINOR);
		createEReference(versionNumberEClass, VERSION_NUMBER__PATCH);
		createEReference(versionNumberEClass, VERSION_NUMBER__EXTENDED);
		createEReference(versionNumberEClass, VERSION_NUMBER__QUALIFIER);
		createEOperation(versionNumberEClass, VERSION_NUMBER___GET_PRE_RELEASE_TAG);
		createEOperation(versionNumberEClass, VERSION_NUMBER___HAS_PRE_RELEASE_TAG);
		createEOperation(versionNumberEClass, VERSION_NUMBER___LENGTH);
		createEOperation(versionNumberEClass, VERSION_NUMBER___GET_PART__INT);
		createEOperation(versionNumberEClass, VERSION_NUMBER___EQUALS__OBJECT);

		versionPartEClass = createEClass(VERSION_PART);
		createEAttribute(versionPartEClass, VERSION_PART__WILDCARD);
		createEAttribute(versionPartEClass, VERSION_PART__NUMBER_RAW);
		createEOperation(versionPartEClass, VERSION_PART___GET_NUMBER);
		createEOperation(versionPartEClass, VERSION_PART___EQUALS__OBJECT);

		qualifierEClass = createEClass(QUALIFIER);
		createEReference(qualifierEClass, QUALIFIER__PRE_RELEASE);
		createEReference(qualifierEClass, QUALIFIER__BUILD_METADATA);
		createEOperation(qualifierEClass, QUALIFIER___EQUALS__OBJECT);

		qualifierTagEClass = createEClass(QUALIFIER_TAG);
		createEAttribute(qualifierTagEClass, QUALIFIER_TAG__PARTS);
		createEOperation(qualifierTagEClass, QUALIFIER_TAG___EQUALS__OBJECT);

		// Create enums
		versionComparatorEEnum = createEEnum(VERSION_COMPARATOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		npmVersionRequirementEClass.getESuperTypes().add(this.getSEMVERtoStringable());
		urlVersionRequirementEClass.getESuperTypes().add(this.getNPMVersionRequirement());
		urlVersionSpecifierEClass.getESuperTypes().add(this.getSEMVERtoStringable());
		urlSemverEClass.getESuperTypes().add(this.getURLVersionSpecifier());
		urlCommitISHEClass.getESuperTypes().add(this.getURLVersionSpecifier());
		gitHubVersionRequirementEClass.getESuperTypes().add(this.getNPMVersionRequirement());
		localPathVersionRequirementEClass.getESuperTypes().add(this.getNPMVersionRequirement());
		tagVersionRequirementEClass.getESuperTypes().add(this.getNPMVersionRequirement());
		versionRangeSetRequirementEClass.getESuperTypes().add(this.getNPMVersionRequirement());
		versionRangeEClass.getESuperTypes().add(this.getSEMVERtoStringable());
		hyphenVersionRangeEClass.getESuperTypes().add(this.getVersionRange());
		versionRangeConstraintEClass.getESuperTypes().add(this.getVersionRange());
		simpleVersionEClass.getESuperTypes().add(this.getSEMVERtoStringable());
		versionNumberEClass.getESuperTypes().add(this.getSEMVERtoStringable());
		versionPartEClass.getESuperTypes().add(this.getSEMVERtoStringable());
		qualifierEClass.getESuperTypes().add(this.getSEMVERtoStringable());
		qualifierTagEClass.getESuperTypes().add(this.getSEMVERtoStringable());

		// Initialize classes, features, and operations; add parameters
		initEClass(semveRtoStringableEClass, SEMVERtoStringable.class, "SEMVERtoStringable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getSEMVERtoStringable__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(npmVersionRequirementEClass, NPMVersionRequirement.class, "NPMVersionRequirement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(urlVersionRequirementEClass, URLVersionRequirement.class, "URLVersionRequirement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getURLVersionRequirement_VersionSpecifier(), this.getURLVersionSpecifier(), null, "versionSpecifier", null, 0, 1, URLVersionRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getURLVersionRequirement_Protocol(), theEcorePackage.getEString(), "protocol", null, 0, 1, URLVersionRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getURLVersionRequirement_Url(), theEcorePackage.getEString(), "url", null, 0, 1, URLVersionRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getURLVersionRequirement__HasSimpleVersion(), theEcorePackage.getEBoolean(), "hasSimpleVersion", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getURLVersionRequirement__GetSimpleVersion(), this.getSimpleVersion(), "getSimpleVersion", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(urlVersionSpecifierEClass, URLVersionSpecifier.class, "URLVersionSpecifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(urlSemverEClass, URLSemver.class, "URLSemver", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getURLSemver_SimpleVersion(), this.getSimpleVersion(), null, "simpleVersion", null, 0, 1, URLSemver.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(urlCommitISHEClass, URLCommitISH.class, "URLCommitISH", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getURLCommitISH_CommitISH(), theEcorePackage.getEString(), "commitISH", null, 0, 1, URLCommitISH.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gitHubVersionRequirementEClass, GitHubVersionRequirement.class, "GitHubVersionRequirement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGitHubVersionRequirement_GithubUrl(), theEcorePackage.getEString(), "githubUrl", null, 0, 1, GitHubVersionRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGitHubVersionRequirement_CommitISH(), theEcorePackage.getEString(), "commitISH", null, 0, 1, GitHubVersionRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(localPathVersionRequirementEClass, LocalPathVersionRequirement.class, "LocalPathVersionRequirement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocalPathVersionRequirement_LocalPath(), theEcorePackage.getEString(), "localPath", null, 0, 1, LocalPathVersionRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tagVersionRequirementEClass, TagVersionRequirement.class, "TagVersionRequirement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTagVersionRequirement_TagName(), theEcorePackage.getEString(), "tagName", null, 0, 1, TagVersionRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionRangeSetRequirementEClass, VersionRangeSetRequirement.class, "VersionRangeSetRequirement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersionRangeSetRequirement_Ranges(), this.getVersionRange(), null, "ranges", null, 0, -1, VersionRangeSetRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionRangeEClass, VersionRange.class, "VersionRange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(hyphenVersionRangeEClass, HyphenVersionRange.class, "HyphenVersionRange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHyphenVersionRange_From(), this.getVersionNumber(), null, "from", null, 0, 1, HyphenVersionRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHyphenVersionRange_To(), this.getVersionNumber(), null, "to", null, 0, 1, HyphenVersionRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionRangeConstraintEClass, VersionRangeConstraint.class, "VersionRangeConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersionRangeConstraint_VersionConstraints(), this.getSimpleVersion(), null, "versionConstraints", null, 0, -1, VersionRangeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleVersionEClass, SimpleVersion.class, "SimpleVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleVersion_Number(), this.getVersionNumber(), null, "number", null, 0, 1, SimpleVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleVersion_Comparators(), this.getVersionComparator(), "comparators", null, 0, -1, SimpleVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getSimpleVersion__IsSpecific(), theEcorePackage.getEBoolean(), "isSpecific", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSimpleVersion__IsCaret(), theEcorePackage.getEBoolean(), "isCaret", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSimpleVersion__IsTilde(), theEcorePackage.getEBoolean(), "isTilde", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSimpleVersion__IsGreater(), theEcorePackage.getEBoolean(), "isGreater", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSimpleVersion__IsGreaterEquals(), theEcorePackage.getEBoolean(), "isGreaterEquals", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSimpleVersion__IsSmaller(), theEcorePackage.getEBoolean(), "isSmaller", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSimpleVersion__IsSmallerEquals(), theEcorePackage.getEBoolean(), "isSmallerEquals", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(versionNumberEClass, VersionNumber.class, "VersionNumber", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersionNumber_Major(), this.getVersionPart(), null, "major", null, 0, 1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersionNumber_Minor(), this.getVersionPart(), null, "minor", null, 0, 1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersionNumber_Patch(), this.getVersionPart(), null, "patch", null, 0, 1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersionNumber_Extended(), this.getVersionPart(), null, "extended", null, 0, -1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersionNumber_Qualifier(), this.getQualifier(), null, "qualifier", null, 0, 1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getVersionNumber__GetPreReleaseTag(), theEcorePackage.getEString(), "getPreReleaseTag", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getVersionNumber__HasPreReleaseTag(), theEcorePackage.getEBoolean(), "hasPreReleaseTag", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getVersionNumber__Length(), theEcorePackage.getEInt(), "length", 0, 1, !IS_UNIQUE, IS_ORDERED);

		EOperation op = initEOperation(getVersionNumber__GetPart__int(), this.getVersionPart(), "getPart", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "idx", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getVersionNumber__Equals__Object(), theEcorePackage.getEBoolean(), "equals", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEJavaObject(), "obj", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(versionPartEClass, VersionPart.class, "VersionPart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersionPart_Wildcard(), theEcorePackage.getEBoolean(), "wildcard", null, 0, 1, VersionPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionPart_NumberRaw(), theEcorePackage.getEIntegerObject(), "numberRaw", null, 0, 1, VersionPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getVersionPart__GetNumber(), theEcorePackage.getEIntegerObject(), "getNumber", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getVersionPart__Equals__Object(), theEcorePackage.getEBoolean(), "equals", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEJavaObject(), "obj", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(qualifierEClass, Qualifier.class, "Qualifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQualifier_PreRelease(), this.getQualifierTag(), null, "preRelease", null, 0, 1, Qualifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQualifier_BuildMetadata(), this.getQualifierTag(), null, "buildMetadata", null, 0, 1, Qualifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getQualifier__Equals__Object(), theEcorePackage.getEBoolean(), "equals", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEJavaObject(), "obj", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(qualifierTagEClass, QualifierTag.class, "QualifierTag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQualifierTag_Parts(), theEcorePackage.getEString(), "parts", null, 0, -1, QualifierTag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getQualifierTag__Equals__Object(), theEcorePackage.getEBoolean(), "equals", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEJavaObject(), "obj", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(versionComparatorEEnum, VersionComparator.class, "VersionComparator");
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.VERSION);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.EQUALS);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.TILDE);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.CARET);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.SMALLER);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.SMALLER_EQUALS);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.GREATER);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.GREATER_EQUALS);

		// Create resource
		createResource(eNS_URI);
	}

} //SEMVERPackageImpl
