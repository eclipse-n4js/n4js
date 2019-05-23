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
package org.eclipse.n4js.semver.Semver.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.semver.Semver.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SemverFactoryImpl extends EFactoryImpl implements SemverFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SemverFactory init() {
		try {
			SemverFactory theSemverFactory = (SemverFactory)EPackage.Registry.INSTANCE.getEFactory(SemverPackage.eNS_URI);
			if (theSemverFactory != null) {
				return theSemverFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SemverFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemverFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SemverPackage.URL_VERSION_REQUIREMENT: return createURLVersionRequirement();
			case SemverPackage.URL_SEMVER: return createURLSemver();
			case SemverPackage.URL_COMMIT_ISH: return createURLCommitISH();
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT: return createGitHubVersionRequirement();
			case SemverPackage.LOCAL_PATH_VERSION_REQUIREMENT: return createLocalPathVersionRequirement();
			case SemverPackage.TAG_VERSION_REQUIREMENT: return createTagVersionRequirement();
			case SemverPackage.VERSION_RANGE_SET_REQUIREMENT: return createVersionRangeSetRequirement();
			case SemverPackage.HYPHEN_VERSION_RANGE: return createHyphenVersionRange();
			case SemverPackage.VERSION_RANGE_CONSTRAINT: return createVersionRangeConstraint();
			case SemverPackage.SIMPLE_VERSION: return createSimpleVersion();
			case SemverPackage.VERSION_NUMBER: return createVersionNumber();
			case SemverPackage.VERSION_PART: return createVersionPart();
			case SemverPackage.QUALIFIER: return createQualifier();
			case SemverPackage.QUALIFIER_TAG: return createQualifierTag();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SemverPackage.VERSION_COMPARATOR:
				return createVersionComparatorFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SemverPackage.VERSION_COMPARATOR:
				return convertVersionComparatorToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public URLVersionRequirement createURLVersionRequirement() {
		URLVersionRequirementImpl urlVersionRequirement = new URLVersionRequirementImpl();
		return urlVersionRequirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public URLSemver createURLSemver() {
		URLSemverImpl urlSemver = new URLSemverImpl();
		return urlSemver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public URLCommitISH createURLCommitISH() {
		URLCommitISHImpl urlCommitISH = new URLCommitISHImpl();
		return urlCommitISH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GitHubVersionRequirement createGitHubVersionRequirement() {
		GitHubVersionRequirementImpl gitHubVersionRequirement = new GitHubVersionRequirementImpl();
		return gitHubVersionRequirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LocalPathVersionRequirement createLocalPathVersionRequirement() {
		LocalPathVersionRequirementImpl localPathVersionRequirement = new LocalPathVersionRequirementImpl();
		return localPathVersionRequirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TagVersionRequirement createTagVersionRequirement() {
		TagVersionRequirementImpl tagVersionRequirement = new TagVersionRequirementImpl();
		return tagVersionRequirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionRangeSetRequirement createVersionRangeSetRequirement() {
		VersionRangeSetRequirementImpl versionRangeSetRequirement = new VersionRangeSetRequirementImpl();
		return versionRangeSetRequirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HyphenVersionRange createHyphenVersionRange() {
		HyphenVersionRangeImpl hyphenVersionRange = new HyphenVersionRangeImpl();
		return hyphenVersionRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionRangeConstraint createVersionRangeConstraint() {
		VersionRangeConstraintImpl versionRangeConstraint = new VersionRangeConstraintImpl();
		return versionRangeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleVersion createSimpleVersion() {
		SimpleVersionImpl simpleVersion = new SimpleVersionImpl();
		return simpleVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionNumber createVersionNumber() {
		VersionNumberImpl versionNumber = new VersionNumberImpl();
		return versionNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionPart createVersionPart() {
		VersionPartImpl versionPart = new VersionPartImpl();
		return versionPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Qualifier createQualifier() {
		QualifierImpl qualifier = new QualifierImpl();
		return qualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public QualifierTag createQualifierTag() {
		QualifierTagImpl qualifierTag = new QualifierTagImpl();
		return qualifierTag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionComparator createVersionComparatorFromString(EDataType eDataType, String initialValue) {
		VersionComparator result = VersionComparator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVersionComparatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SemverPackage getSemverPackage() {
		return (SemverPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SemverPackage getPackage() {
		return SemverPackage.eINSTANCE;
	}

} //SemverFactoryImpl
