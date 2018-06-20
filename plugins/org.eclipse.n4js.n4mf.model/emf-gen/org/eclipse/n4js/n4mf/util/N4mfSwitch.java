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
package org.eclipse.n4js.n4mf.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.n4js.n4mf.*;

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
 * @see org.eclipse.n4js.n4mf.N4mfPackage
 * @generated
 */
public class N4mfSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static N4mfPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4mfSwitch() {
		if (modelPackage == null) {
			modelPackage = N4mfPackage.eINSTANCE;
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
			case N4mfPackage.PROJECT_DESCRIPTION: {
				ProjectDescription projectDescription = (ProjectDescription)theEObject;
				T result = caseProjectDescription(projectDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4mfPackage.DECLARED_VERSION: {
				DeclaredVersion declaredVersion = (DeclaredVersion)theEObject;
				T result = caseDeclaredVersion(declaredVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION: {
				SourceContainerDescription sourceContainerDescription = (SourceContainerDescription)theEObject;
				T result = caseSourceContainerDescription(sourceContainerDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4mfPackage.MODULE_FILTER: {
				ModuleFilter moduleFilter = (ModuleFilter)theEObject;
				T result = caseModuleFilter(moduleFilter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4mfPackage.BOOTSTRAP_MODULE: {
				BootstrapModule bootstrapModule = (BootstrapModule)theEObject;
				T result = caseBootstrapModule(bootstrapModule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4mfPackage.PROJECT_REFERENCE: {
				ProjectReference projectReference = (ProjectReference)theEObject;
				T result = caseProjectReference(projectReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4mfPackage.MODULE_FILTER_SPECIFIER: {
				ModuleFilterSpecifier moduleFilterSpecifier = (ModuleFilterSpecifier)theEObject;
				T result = caseModuleFilterSpecifier(moduleFilterSpecifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4mfPackage.PROJECT_DEPENDENCY: {
				ProjectDependency projectDependency = (ProjectDependency)theEObject;
				T result = caseProjectDependency(projectDependency);
				if (result == null) result = caseProjectReference(projectDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4mfPackage.VERSION_CONSTRAINT: {
				VersionConstraint versionConstraint = (VersionConstraint)theEObject;
				T result = caseVersionConstraint(versionConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectDescription(ProjectDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Declared Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Declared Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeclaredVersion(DeclaredVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Source Container Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source Container Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSourceContainerDescription(SourceContainerDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleFilter(ModuleFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bootstrap Module</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bootstrap Module</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBootstrapModule(BootstrapModule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectReference(ProjectReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Filter Specifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Filter Specifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleFilterSpecifier(ModuleFilterSpecifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectDependency(ProjectDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionConstraint(VersionConstraint object) {
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

} //N4mfSwitch
