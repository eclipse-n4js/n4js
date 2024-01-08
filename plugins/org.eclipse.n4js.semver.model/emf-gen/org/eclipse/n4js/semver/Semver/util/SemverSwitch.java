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
package org.eclipse.n4js.semver.Semver.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.n4js.semver.Semver.*;

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
 * @see org.eclipse.n4js.semver.Semver.SemverPackage
 * @generated
 */
public class SemverSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SemverPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemverSwitch() {
		if (modelPackage == null) {
			modelPackage = SemverPackage.eINSTANCE;
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
			case SemverPackage.SEMVER_TO_STRINGABLE: {
				SemverToStringable semverToStringable = (SemverToStringable)theEObject;
				T result = caseSemverToStringable(semverToStringable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.NPM_VERSION_REQUIREMENT: {
				NPMVersionRequirement npmVersionRequirement = (NPMVersionRequirement)theEObject;
				T result = caseNPMVersionRequirement(npmVersionRequirement);
				if (result == null) result = caseSemverToStringable(npmVersionRequirement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.URL_VERSION_REQUIREMENT: {
				URLVersionRequirement urlVersionRequirement = (URLVersionRequirement)theEObject;
				T result = caseURLVersionRequirement(urlVersionRequirement);
				if (result == null) result = caseNPMVersionRequirement(urlVersionRequirement);
				if (result == null) result = caseSemverToStringable(urlVersionRequirement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.URL_VERSION_SPECIFIER: {
				URLVersionSpecifier urlVersionSpecifier = (URLVersionSpecifier)theEObject;
				T result = caseURLVersionSpecifier(urlVersionSpecifier);
				if (result == null) result = caseSemverToStringable(urlVersionSpecifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.URL_SEMVER: {
				URLSemver urlSemver = (URLSemver)theEObject;
				T result = caseURLSemver(urlSemver);
				if (result == null) result = caseURLVersionSpecifier(urlSemver);
				if (result == null) result = caseSemverToStringable(urlSemver);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.URL_COMMIT_ISH: {
				URLCommitISH urlCommitISH = (URLCommitISH)theEObject;
				T result = caseURLCommitISH(urlCommitISH);
				if (result == null) result = caseURLVersionSpecifier(urlCommitISH);
				if (result == null) result = caseSemverToStringable(urlCommitISH);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT: {
				WorkspaceVersionRequirement workspaceVersionRequirement = (WorkspaceVersionRequirement)theEObject;
				T result = caseWorkspaceVersionRequirement(workspaceVersionRequirement);
				if (result == null) result = caseNPMVersionRequirement(workspaceVersionRequirement);
				if (result == null) result = caseSemverToStringable(workspaceVersionRequirement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT: {
				GitHubVersionRequirement gitHubVersionRequirement = (GitHubVersionRequirement)theEObject;
				T result = caseGitHubVersionRequirement(gitHubVersionRequirement);
				if (result == null) result = caseNPMVersionRequirement(gitHubVersionRequirement);
				if (result == null) result = caseSemverToStringable(gitHubVersionRequirement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.LOCAL_PATH_VERSION_REQUIREMENT: {
				LocalPathVersionRequirement localPathVersionRequirement = (LocalPathVersionRequirement)theEObject;
				T result = caseLocalPathVersionRequirement(localPathVersionRequirement);
				if (result == null) result = caseNPMVersionRequirement(localPathVersionRequirement);
				if (result == null) result = caseSemverToStringable(localPathVersionRequirement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.TAG_VERSION_REQUIREMENT: {
				TagVersionRequirement tagVersionRequirement = (TagVersionRequirement)theEObject;
				T result = caseTagVersionRequirement(tagVersionRequirement);
				if (result == null) result = caseNPMVersionRequirement(tagVersionRequirement);
				if (result == null) result = caseSemverToStringable(tagVersionRequirement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.VERSION_RANGE_SET_REQUIREMENT: {
				VersionRangeSetRequirement versionRangeSetRequirement = (VersionRangeSetRequirement)theEObject;
				T result = caseVersionRangeSetRequirement(versionRangeSetRequirement);
				if (result == null) result = caseNPMVersionRequirement(versionRangeSetRequirement);
				if (result == null) result = caseSemverToStringable(versionRangeSetRequirement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.VERSION_RANGE: {
				VersionRange versionRange = (VersionRange)theEObject;
				T result = caseVersionRange(versionRange);
				if (result == null) result = caseSemverToStringable(versionRange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.HYPHEN_VERSION_RANGE: {
				HyphenVersionRange hyphenVersionRange = (HyphenVersionRange)theEObject;
				T result = caseHyphenVersionRange(hyphenVersionRange);
				if (result == null) result = caseVersionRange(hyphenVersionRange);
				if (result == null) result = caseSemverToStringable(hyphenVersionRange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.VERSION_RANGE_CONSTRAINT: {
				VersionRangeConstraint versionRangeConstraint = (VersionRangeConstraint)theEObject;
				T result = caseVersionRangeConstraint(versionRangeConstraint);
				if (result == null) result = caseVersionRange(versionRangeConstraint);
				if (result == null) result = caseSemverToStringable(versionRangeConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.SIMPLE_VERSION: {
				SimpleVersion simpleVersion = (SimpleVersion)theEObject;
				T result = caseSimpleVersion(simpleVersion);
				if (result == null) result = caseSemverToStringable(simpleVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.VERSION_NUMBER: {
				VersionNumber versionNumber = (VersionNumber)theEObject;
				T result = caseVersionNumber(versionNumber);
				if (result == null) result = caseSemverToStringable(versionNumber);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.VERSION_PART: {
				VersionPart versionPart = (VersionPart)theEObject;
				T result = caseVersionPart(versionPart);
				if (result == null) result = caseSemverToStringable(versionPart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.QUALIFIER: {
				Qualifier qualifier = (Qualifier)theEObject;
				T result = caseQualifier(qualifier);
				if (result == null) result = caseSemverToStringable(qualifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SemverPackage.QUALIFIER_TAG: {
				QualifierTag qualifierTag = (QualifierTag)theEObject;
				T result = caseQualifierTag(qualifierTag);
				if (result == null) result = caseSemverToStringable(qualifierTag);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>To Stringable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>To Stringable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSemverToStringable(SemverToStringable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>NPM Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>NPM Version Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNPMVersionRequirement(NPMVersionRequirement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>URL Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>URL Version Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseURLVersionRequirement(URLVersionRequirement object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Workspace Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Workspace Version Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWorkspaceVersionRequirement(WorkspaceVersionRequirement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Git Hub Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Git Hub Version Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGitHubVersionRequirement(GitHubVersionRequirement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local Path Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Path Version Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalPathVersionRequirement(LocalPathVersionRequirement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tag Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tag Version Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTagVersionRequirement(TagVersionRequirement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Range Set Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Range Set Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionRangeSetRequirement(VersionRangeSetRequirement object) {
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

} //SemverSwitch
