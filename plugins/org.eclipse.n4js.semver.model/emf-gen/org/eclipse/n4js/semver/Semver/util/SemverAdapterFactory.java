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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.semver.Semver.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.semver.Semver.SemverPackage
 * @generated
 */
public class SemverAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SemverPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemverAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SemverPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SemverSwitch<Adapter> modelSwitch =
		new SemverSwitch<Adapter>() {
			@Override
			public Adapter caseSemverToStringable(SemverToStringable object) {
				return createSemverToStringableAdapter();
			}
			@Override
			public Adapter caseNPMVersionRequirement(NPMVersionRequirement object) {
				return createNPMVersionRequirementAdapter();
			}
			@Override
			public Adapter caseURLVersionRequirement(URLVersionRequirement object) {
				return createURLVersionRequirementAdapter();
			}
			@Override
			public Adapter caseURLVersionSpecifier(URLVersionSpecifier object) {
				return createURLVersionSpecifierAdapter();
			}
			@Override
			public Adapter caseURLSemver(URLSemver object) {
				return createURLSemverAdapter();
			}
			@Override
			public Adapter caseURLCommitISH(URLCommitISH object) {
				return createURLCommitISHAdapter();
			}
			@Override
			public Adapter caseWorkspaceVersionRequirement(WorkspaceVersionRequirement object) {
				return createWorkspaceVersionRequirementAdapter();
			}
			@Override
			public Adapter caseGitHubVersionRequirement(GitHubVersionRequirement object) {
				return createGitHubVersionRequirementAdapter();
			}
			@Override
			public Adapter caseLocalPathVersionRequirement(LocalPathVersionRequirement object) {
				return createLocalPathVersionRequirementAdapter();
			}
			@Override
			public Adapter caseTagVersionRequirement(TagVersionRequirement object) {
				return createTagVersionRequirementAdapter();
			}
			@Override
			public Adapter caseVersionRangeSetRequirement(VersionRangeSetRequirement object) {
				return createVersionRangeSetRequirementAdapter();
			}
			@Override
			public Adapter caseVersionRange(VersionRange object) {
				return createVersionRangeAdapter();
			}
			@Override
			public Adapter caseHyphenVersionRange(HyphenVersionRange object) {
				return createHyphenVersionRangeAdapter();
			}
			@Override
			public Adapter caseVersionRangeConstraint(VersionRangeConstraint object) {
				return createVersionRangeConstraintAdapter();
			}
			@Override
			public Adapter caseSimpleVersion(SimpleVersion object) {
				return createSimpleVersionAdapter();
			}
			@Override
			public Adapter caseVersionNumber(VersionNumber object) {
				return createVersionNumberAdapter();
			}
			@Override
			public Adapter caseVersionPart(VersionPart object) {
				return createVersionPartAdapter();
			}
			@Override
			public Adapter caseQualifier(Qualifier object) {
				return createQualifierAdapter();
			}
			@Override
			public Adapter caseQualifierTag(QualifierTag object) {
				return createQualifierTagAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.SemverToStringable <em>To Stringable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.SemverToStringable
	 * @generated
	 */
	public Adapter createSemverToStringableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.NPMVersionRequirement <em>NPM Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.NPMVersionRequirement
	 * @generated
	 */
	public Adapter createNPMVersionRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.URLVersionRequirement <em>URL Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionRequirement
	 * @generated
	 */
	public Adapter createURLVersionRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.URLVersionSpecifier <em>URL Version Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.URLVersionSpecifier
	 * @generated
	 */
	public Adapter createURLVersionSpecifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.URLSemver <em>URL Semver</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.URLSemver
	 * @generated
	 */
	public Adapter createURLSemverAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.URLCommitISH <em>URL Commit ISH</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.URLCommitISH
	 * @generated
	 */
	public Adapter createURLCommitISHAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.WorkspaceVersionRequirement <em>Workspace Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.WorkspaceVersionRequirement
	 * @generated
	 */
	public Adapter createWorkspaceVersionRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.GitHubVersionRequirement <em>Git Hub Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.GitHubVersionRequirement
	 * @generated
	 */
	public Adapter createGitHubVersionRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement <em>Local Path Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement
	 * @generated
	 */
	public Adapter createLocalPathVersionRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.TagVersionRequirement <em>Tag Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.TagVersionRequirement
	 * @generated
	 */
	public Adapter createTagVersionRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement <em>Version Range Set Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement
	 * @generated
	 */
	public Adapter createVersionRangeSetRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.VersionRange <em>Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.VersionRange
	 * @generated
	 */
	public Adapter createVersionRangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.HyphenVersionRange <em>Hyphen Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.HyphenVersionRange
	 * @generated
	 */
	public Adapter createHyphenVersionRangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.VersionRangeConstraint <em>Version Range Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.VersionRangeConstraint
	 * @generated
	 */
	public Adapter createVersionRangeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.SimpleVersion <em>Simple Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.SimpleVersion
	 * @generated
	 */
	public Adapter createSimpleVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.VersionNumber <em>Version Number</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.VersionNumber
	 * @generated
	 */
	public Adapter createVersionNumberAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.VersionPart <em>Version Part</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.VersionPart
	 * @generated
	 */
	public Adapter createVersionPartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.Qualifier <em>Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.Qualifier
	 * @generated
	 */
	public Adapter createQualifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.Semver.QualifierTag <em>Qualifier Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.Semver.QualifierTag
	 * @generated
	 */
	public Adapter createQualifierTagAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //SemverAdapterFactory
