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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.semver.SEMVER.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage
 * @generated
 */
public class SEMVERAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SEMVERPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SEMVERAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SEMVERPackage.eINSTANCE;
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
	protected SEMVERSwitch<Adapter> modelSwitch =
		new SEMVERSwitch<Adapter>() {
			@Override
			public Adapter caseAbstractSEMVERSerializer(AbstractSEMVERSerializer object) {
				return createAbstractSEMVERSerializerAdapter();
			}
			@Override
			public Adapter caseNPMVersion(NPMVersion object) {
				return createNPMVersionAdapter();
			}
			@Override
			public Adapter caseURLVersion(URLVersion object) {
				return createURLVersionAdapter();
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
			public Adapter caseGitHubVersion(GitHubVersion object) {
				return createGitHubVersionAdapter();
			}
			@Override
			public Adapter caseLocalPathVersion(LocalPathVersion object) {
				return createLocalPathVersionAdapter();
			}
			@Override
			public Adapter caseTagVersion(TagVersion object) {
				return createTagVersionAdapter();
			}
			@Override
			public Adapter caseVersionRangeSet(VersionRangeSet object) {
				return createVersionRangeSetAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.AbstractSEMVERSerializer <em>Abstract SEMVER Serializer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.AbstractSEMVERSerializer
	 * @generated
	 */
	public Adapter createAbstractSEMVERSerializerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.NPMVersion <em>NPM Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.NPMVersion
	 * @generated
	 */
	public Adapter createNPMVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.URLVersion <em>URL Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersion
	 * @generated
	 */
	public Adapter createURLVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.URLVersionSpecifier <em>URL Version Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.URLVersionSpecifier
	 * @generated
	 */
	public Adapter createURLVersionSpecifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.URLSemver <em>URL Semver</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.URLSemver
	 * @generated
	 */
	public Adapter createURLSemverAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.URLCommitISH <em>URL Commit ISH</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.URLCommitISH
	 * @generated
	 */
	public Adapter createURLCommitISHAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.GitHubVersion <em>Git Hub Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.GitHubVersion
	 * @generated
	 */
	public Adapter createGitHubVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.LocalPathVersion <em>Local Path Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.LocalPathVersion
	 * @generated
	 */
	public Adapter createLocalPathVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.TagVersion <em>Tag Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.TagVersion
	 * @generated
	 */
	public Adapter createTagVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.VersionRangeSet <em>Version Range Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionRangeSet
	 * @generated
	 */
	public Adapter createVersionRangeSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.VersionRange <em>Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionRange
	 * @generated
	 */
	public Adapter createVersionRangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.HyphenVersionRange <em>Hyphen Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.HyphenVersionRange
	 * @generated
	 */
	public Adapter createHyphenVersionRangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint <em>Version Range Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint
	 * @generated
	 */
	public Adapter createVersionRangeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion <em>Simple Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.SimpleVersion
	 * @generated
	 */
	public Adapter createSimpleVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber <em>Version Number</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionNumber
	 * @generated
	 */
	public Adapter createVersionNumberAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.VersionPart <em>Version Part</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionPart
	 * @generated
	 */
	public Adapter createVersionPartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.Qualifier <em>Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.Qualifier
	 * @generated
	 */
	public Adapter createQualifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.semver.SEMVER.QualifierTag <em>Qualifier Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.semver.SEMVER.QualifierTag
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

} //SEMVERAdapterFactory
