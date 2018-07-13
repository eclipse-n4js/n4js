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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.semver.SEMVER.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SEMVERFactoryImpl extends EFactoryImpl implements SEMVERFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SEMVERFactory init() {
		try {
			SEMVERFactory theSEMVERFactory = (SEMVERFactory)EPackage.Registry.INSTANCE.getEFactory(SEMVERPackage.eNS_URI);
			if (theSEMVERFactory != null) {
				return theSEMVERFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SEMVERFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SEMVERFactoryImpl() {
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
			case SEMVERPackage.URL_VERSION: return createURLVersion();
			case SEMVERPackage.URL_SEMVER: return createURLSemver();
			case SEMVERPackage.URL_COMMIT_ISH: return createURLCommitISH();
			case SEMVERPackage.GIT_HUB_VERSION: return createGitHubVersion();
			case SEMVERPackage.LOCAL_PATH_VERSION: return createLocalPathVersion();
			case SEMVERPackage.TAG_VERSION: return createTagVersion();
			case SEMVERPackage.VERSION_RANGE_SET: return createVersionRangeSet();
			case SEMVERPackage.HYPHEN_VERSION_RANGE: return createHyphenVersionRange();
			case SEMVERPackage.VERSION_RANGE_CONSTRAINT: return createVersionRangeConstraint();
			case SEMVERPackage.SIMPLE_VERSION: return createSimpleVersion();
			case SEMVERPackage.VERSION_NUMBER: return createVersionNumber();
			case SEMVERPackage.VERSION_PART: return createVersionPart();
			case SEMVERPackage.QUALIFIER: return createQualifier();
			case SEMVERPackage.QUALIFIER_TAG: return createQualifierTag();
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
			case SEMVERPackage.VERSION_COMPARATOR:
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
			case SEMVERPackage.VERSION_COMPARATOR:
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
	public URLVersion createURLVersion() {
		URLVersionImpl urlVersion = new URLVersionImpl();
		return urlVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URLSemver createURLSemver() {
		URLSemverImpl urlSemver = new URLSemverImpl();
		return urlSemver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URLCommitISH createURLCommitISH() {
		URLCommitISHImpl urlCommitISH = new URLCommitISHImpl();
		return urlCommitISH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GitHubVersion createGitHubVersion() {
		GitHubVersionImpl gitHubVersion = new GitHubVersionImpl();
		return gitHubVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalPathVersion createLocalPathVersion() {
		LocalPathVersionImpl localPathVersion = new LocalPathVersionImpl();
		return localPathVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TagVersion createTagVersion() {
		TagVersionImpl tagVersion = new TagVersionImpl();
		return tagVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionRangeSet createVersionRangeSet() {
		VersionRangeSetImpl versionRangeSet = new VersionRangeSetImpl();
		return versionRangeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HyphenVersionRange createHyphenVersionRange() {
		HyphenVersionRangeImpl hyphenVersionRange = new HyphenVersionRangeImpl();
		return hyphenVersionRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionRangeConstraint createVersionRangeConstraint() {
		VersionRangeConstraintImpl versionRangeConstraint = new VersionRangeConstraintImpl();
		return versionRangeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleVersion createSimpleVersion() {
		SimpleVersionImpl simpleVersion = new SimpleVersionImpl();
		return simpleVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionNumber createVersionNumber() {
		VersionNumberImpl versionNumber = new VersionNumberImpl();
		return versionNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionPart createVersionPart() {
		VersionPartImpl versionPart = new VersionPartImpl();
		return versionPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Qualifier createQualifier() {
		QualifierImpl qualifier = new QualifierImpl();
		return qualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	public SEMVERPackage getSEMVERPackage() {
		return (SEMVERPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SEMVERPackage getPackage() {
		return SEMVERPackage.eINSTANCE;
	}

} //SEMVERFactoryImpl
