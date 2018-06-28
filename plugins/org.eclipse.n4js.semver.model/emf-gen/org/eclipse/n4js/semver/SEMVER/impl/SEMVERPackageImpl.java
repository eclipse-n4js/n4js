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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.semver.SEMVER.EnumeratedVersionRange;
import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.SEMVERFactory;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.VersionComparator;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionRange;
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
	private EClass enumeratedVersionRangeEClass = null;

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
	private EClass qualifierEClass = null;

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
	public EClass getEnumeratedVersionRange() {
		return enumeratedVersionRangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumeratedVersionRange_SimpleVersions() {
		return (EReference)enumeratedVersionRangeEClass.getEStructuralFeatures().get(0);
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
	public EAttribute getSimpleVersion_Comparator() {
		return (EAttribute)simpleVersionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleVersion_HasTilde() {
		return (EAttribute)simpleVersionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleVersion_HasCaret() {
		return (EAttribute)simpleVersionEClass.getEStructuralFeatures().get(3);
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
	public EReference getVersionNumber_Qualifier() {
		return (EReference)versionNumberEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersionNumber_Major() {
		return (EAttribute)versionNumberEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersionNumber_Minor() {
		return (EAttribute)versionNumberEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersionNumber_Path() {
		return (EAttribute)versionNumberEClass.getEStructuralFeatures().get(3);
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
	public EAttribute getQualifier_PreRelease() {
		return (EAttribute)qualifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQualifier_BuildMetadata() {
		return (EAttribute)qualifierEClass.getEStructuralFeatures().get(1);
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

		enumeratedVersionRangeEClass = createEClass(ENUMERATED_VERSION_RANGE);
		createEReference(enumeratedVersionRangeEClass, ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS);

		simpleVersionEClass = createEClass(SIMPLE_VERSION);
		createEReference(simpleVersionEClass, SIMPLE_VERSION__NUMBER);
		createEAttribute(simpleVersionEClass, SIMPLE_VERSION__COMPARATOR);
		createEAttribute(simpleVersionEClass, SIMPLE_VERSION__HAS_TILDE);
		createEAttribute(simpleVersionEClass, SIMPLE_VERSION__HAS_CARET);

		versionNumberEClass = createEClass(VERSION_NUMBER);
		createEReference(versionNumberEClass, VERSION_NUMBER__QUALIFIER);
		createEAttribute(versionNumberEClass, VERSION_NUMBER__MAJOR);
		createEAttribute(versionNumberEClass, VERSION_NUMBER__MINOR);
		createEAttribute(versionNumberEClass, VERSION_NUMBER__PATH);

		qualifierEClass = createEClass(QUALIFIER);
		createEAttribute(qualifierEClass, QUALIFIER__PRE_RELEASE);
		createEAttribute(qualifierEClass, QUALIFIER__BUILD_METADATA);

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
		enumeratedVersionRangeEClass.getESuperTypes().add(this.getVersionRange());

		// Initialize classes, features, and operations; add parameters
		initEClass(versionRangeSetEClass, VersionRangeSet.class, "VersionRangeSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersionRangeSet_Ranges(), this.getVersionRange(), null, "ranges", null, 0, -1, VersionRangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionRangeEClass, VersionRange.class, "VersionRange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(hyphenVersionRangeEClass, HyphenVersionRange.class, "HyphenVersionRange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHyphenVersionRange_From(), this.getVersionNumber(), null, "from", null, 0, 1, HyphenVersionRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHyphenVersionRange_To(), this.getVersionNumber(), null, "to", null, 0, 1, HyphenVersionRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumeratedVersionRangeEClass, EnumeratedVersionRange.class, "EnumeratedVersionRange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumeratedVersionRange_SimpleVersions(), this.getSimpleVersion(), null, "simpleVersions", null, 0, -1, EnumeratedVersionRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleVersionEClass, SimpleVersion.class, "SimpleVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleVersion_Number(), this.getVersionNumber(), null, "number", null, 0, 1, SimpleVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleVersion_Comparator(), this.getVersionComparator(), "comparator", null, 0, 1, SimpleVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleVersion_HasTilde(), theEcorePackage.getEBoolean(), "hasTilde", null, 0, 1, SimpleVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleVersion_HasCaret(), theEcorePackage.getEBoolean(), "hasCaret", null, 0, 1, SimpleVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionNumberEClass, VersionNumber.class, "VersionNumber", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersionNumber_Qualifier(), this.getQualifier(), null, "qualifier", null, 0, 1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionNumber_Major(), theEcorePackage.getEString(), "major", null, 0, 1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionNumber_Minor(), theEcorePackage.getEString(), "minor", null, 0, 1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionNumber_Path(), theEcorePackage.getEString(), "path", null, 0, 1, VersionNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(qualifierEClass, Qualifier.class, "Qualifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQualifier_PreRelease(), theEcorePackage.getEString(), "preRelease", null, 0, 1, Qualifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQualifier_BuildMetadata(), theEcorePackage.getEString(), "buildMetadata", null, 0, 1, Qualifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(versionComparatorEEnum, VersionComparator.class, "VersionComparator");
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.EQUALS);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.SMALLER);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.SMALLER_EQUALS);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.GREATER);
		addEEnumLiteral(versionComparatorEEnum, VersionComparator.GREATER_EQUALS);

		// Create resource
		createResource(eNS_URI);
	}

} //SEMVERPackageImpl
