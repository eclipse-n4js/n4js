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

import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.QualifierTag;
import org.eclipse.n4js.semver.SEMVER.SEMVERFactory;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.VersionComparator;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionPart;
import org.eclipse.n4js.semver.SEMVER.VersionRange;
import org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;

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
	private EClass versionRangeSetEClass = null;

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
	public EClass getVersionRangeSet() {
		return versionRangeSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionRangeSet_Ranges() {
		return (EReference)versionRangeSetEClass.getEStructuralFeatures().get(0);
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
	public EAttribute getVersionPart_Number() {
		return (EAttribute)versionPartEClass.getEStructuralFeatures().get(1);
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
		versionRangeSetEClass = createEClass(VERSION_RANGE_SET);
		createEReference(versionRangeSetEClass, VERSION_RANGE_SET__RANGES);

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

		versionPartEClass = createEClass(VERSION_PART);
		createEAttribute(versionPartEClass, VERSION_PART__WILDCARD);
		createEAttribute(versionPartEClass, VERSION_PART__NUMBER);

		qualifierEClass = createEClass(QUALIFIER);
		createEReference(qualifierEClass, QUALIFIER__PRE_RELEASE);
		createEReference(qualifierEClass, QUALIFIER__BUILD_METADATA);

		qualifierTagEClass = createEClass(QUALIFIER_TAG);
		createEAttribute(qualifierTagEClass, QUALIFIER_TAG__PARTS);

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
		hyphenVersionRangeEClass.getESuperTypes().add(this.getVersionRange());
		versionRangeConstraintEClass.getESuperTypes().add(this.getVersionRange());

		// Initialize classes, features, and operations; add parameters
		initEClass(versionRangeSetEClass, VersionRangeSet.class, "VersionRangeSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersionRangeSet_Ranges(), this.getVersionRange(), null, "ranges", null, 0, -1, VersionRangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

		initEClass(versionPartEClass, VersionPart.class, "VersionPart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersionPart_Wildcard(), theEcorePackage.getEBoolean(), "wildcard", null, 0, 1, VersionPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionPart_Number(), theEcorePackage.getEInt(), "number", null, 0, 1, VersionPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(qualifierEClass, Qualifier.class, "Qualifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQualifier_PreRelease(), this.getQualifierTag(), null, "preRelease", null, 0, 1, Qualifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQualifier_BuildMetadata(), this.getQualifierTag(), null, "buildMetadata", null, 0, 1, Qualifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(qualifierTagEClass, QualifierTag.class, "QualifierTag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQualifierTag_Parts(), theEcorePackage.getEString(), "parts", null, 0, -1, QualifierTag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
