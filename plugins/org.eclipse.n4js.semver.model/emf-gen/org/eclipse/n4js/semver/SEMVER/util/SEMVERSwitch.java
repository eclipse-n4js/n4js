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
package org.eclipse.n4js.semver.SEMVER.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.n4js.semver.SEMVER.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage
 * @generated
 */
public class SEMVERSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SEMVERPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SEMVERSwitch() {
		if (modelPackage == null) {
			modelPackage = SEMVERPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case SEMVERPackage.ABSTRACT_SEMVER_SERIALIZER: {
				AbstractSEMVERSerializer abstractSEMVERSerializer = (AbstractSEMVERSerializer)theEObject;
				T result = caseAbstractSEMVERSerializer(abstractSEMVERSerializer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.NPM_VERSION: {
				NPMVersion npmVersion = (NPMVersion)theEObject;
				T result = caseNPMVersion(npmVersion);
				if (result == null) result = caseAbstractSEMVERSerializer(npmVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.URL_VERSION: {
				URLVersion urlVersion = (URLVersion)theEObject;
				T result = caseURLVersion(urlVersion);
				if (result == null) result = caseNPMVersion(urlVersion);
				if (result == null) result = caseAbstractSEMVERSerializer(urlVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.URL_VERSION_SPECIFIER: {
				URLVersionSpecifier urlVersionSpecifier = (URLVersionSpecifier)theEObject;
				T result = caseURLVersionSpecifier(urlVersionSpecifier);
				if (result == null) result = caseAbstractSEMVERSerializer(urlVersionSpecifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.URL_SEMVER: {
				URLSemver urlSemver = (URLSemver)theEObject;
				T result = caseURLSemver(urlSemver);
				if (result == null) result = caseURLVersionSpecifier(urlSemver);
				if (result == null) result = caseAbstractSEMVERSerializer(urlSemver);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.URL_COMMIT_ISH: {
				URLCommitISH urlCommitISH = (URLCommitISH)theEObject;
				T result = caseURLCommitISH(urlCommitISH);
				if (result == null) result = caseURLVersionSpecifier(urlCommitISH);
				if (result == null) result = caseAbstractSEMVERSerializer(urlCommitISH);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.GIT_HUB_VERSION: {
				GitHubVersion gitHubVersion = (GitHubVersion)theEObject;
				T result = caseGitHubVersion(gitHubVersion);
				if (result == null) result = caseNPMVersion(gitHubVersion);
				if (result == null) result = caseAbstractSEMVERSerializer(gitHubVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.LOCAL_PATH_VERSION: {
				LocalPathVersion localPathVersion = (LocalPathVersion)theEObject;
				T result = caseLocalPathVersion(localPathVersion);
				if (result == null) result = caseNPMVersion(localPathVersion);
				if (result == null) result = caseAbstractSEMVERSerializer(localPathVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.TAG_VERSION: {
				TagVersion tagVersion = (TagVersion)theEObject;
				T result = caseTagVersion(tagVersion);
				if (result == null) result = caseNPMVersion(tagVersion);
				if (result == null) result = caseAbstractSEMVERSerializer(tagVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.VERSION_RANGE_SET: {
				VersionRangeSet versionRangeSet = (VersionRangeSet)theEObject;
				T result = caseVersionRangeSet(versionRangeSet);
				if (result == null) result = caseNPMVersion(versionRangeSet);
				if (result == null) result = caseAbstractSEMVERSerializer(versionRangeSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.VERSION_RANGE: {
				VersionRange versionRange = (VersionRange)theEObject;
				T result = caseVersionRange(versionRange);
				if (result == null) result = caseAbstractSEMVERSerializer(versionRange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.HYPHEN_VERSION_RANGE: {
				HyphenVersionRange hyphenVersionRange = (HyphenVersionRange)theEObject;
				T result = caseHyphenVersionRange(hyphenVersionRange);
				if (result == null) result = caseVersionRange(hyphenVersionRange);
				if (result == null) result = caseAbstractSEMVERSerializer(hyphenVersionRange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.VERSION_RANGE_CONSTRAINT: {
				VersionRangeConstraint versionRangeConstraint = (VersionRangeConstraint)theEObject;
				T result = caseVersionRangeConstraint(versionRangeConstraint);
				if (result == null) result = caseVersionRange(versionRangeConstraint);
				if (result == null) result = caseAbstractSEMVERSerializer(versionRangeConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.SIMPLE_VERSION: {
				SimpleVersion simpleVersion = (SimpleVersion)theEObject;
				T result = caseSimpleVersion(simpleVersion);
				if (result == null) result = caseAbstractSEMVERSerializer(simpleVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.VERSION_NUMBER: {
				VersionNumber versionNumber = (VersionNumber)theEObject;
				T result = caseVersionNumber(versionNumber);
				if (result == null) result = caseAbstractSEMVERSerializer(versionNumber);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.VERSION_PART: {
				VersionPart versionPart = (VersionPart)theEObject;
				T result = caseVersionPart(versionPart);
				if (result == null) result = caseAbstractSEMVERSerializer(versionPart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.QUALIFIER: {
				Qualifier qualifier = (Qualifier)theEObject;
				T result = caseQualifier(qualifier);
				if (result == null) result = caseAbstractSEMVERSerializer(qualifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SEMVERPackage.QUALIFIER_TAG: {
				QualifierTag qualifierTag = (QualifierTag)theEObject;
				T result = caseQualifierTag(qualifierTag);
				if (result == null) result = caseAbstractSEMVERSerializer(qualifierTag);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract SEMVER Serializer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract SEMVER Serializer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractSEMVERSerializer(AbstractSEMVERSerializer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>NPM Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>NPM Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNPMVersion(NPMVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>URL Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>URL Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseURLVersion(URLVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>URL Version Specifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>URL Version Specifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseURLVersionSpecifier(URLVersionSpecifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>URL Semver</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>URL Semver</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseURLSemver(URLSemver object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>URL Commit ISH</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>URL Commit ISH</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseURLCommitISH(URLCommitISH object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Git Hub Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Git Hub Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGitHubVersion(GitHubVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local Path Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Path Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalPathVersion(LocalPathVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tag Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tag Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTagVersion(TagVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Range Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Range Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionRangeSet(VersionRangeSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Range</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Range</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionRange(VersionRange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Hyphen Version Range</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Hyphen Version Range</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHyphenVersionRange(HyphenVersionRange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Range Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Range Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionRangeConstraint(VersionRangeConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleVersion(SimpleVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Number</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Number</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionNumber(VersionNumber object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Part</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Part</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionPart(VersionPart object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Qualifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Qualifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQualifier(Qualifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Qualifier Tag</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Qualifier Tag</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQualifierTag(QualifierTag object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //SEMVERSwitch
