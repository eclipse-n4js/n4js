/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.workspace.IN4JSCoreNEW;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Utilities and helper methods related to the {@link IEObjectDescription}s.
 */
@Singleton
public final class EObjectDescriptionHelper {
	@Inject
	private IN4JSCoreNEW n4jsCore;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * Helper method that checks if given {@link IEObjectDescription description} describes {@link TModule} containing
	 * given {@link EObject}.
	 *
	 * Returns <code>true</code> only if provided {@link IEObjectDescription description} has the same
	 * {@link QualifiedName} as module of the {@link EObject}. Additionally if {@link IEObjectDescription description}
	 * describes {@link TModule#isMainModule() main module} then it is checked if both are contained in the same
	 * {@link N4JSProjectConfigSnapshot}.
	 *
	 * @returns true if {@link IEObjectDescription} describes module of {@link EObject}
	 */
	public boolean isDescriptionOfModuleWith(Notifier context, IEObjectDescription eoDescription, EObject eObject) {
		// check if module names are the same
		final Script containingScript = EcoreUtil2.getContainerOfType(eObject, Script.class);
		final TModule containingModule = containingScript != null ? containingScript.getModule() : null;
		final String eObjectModuleName = containingModule != null ? containingModule.getQualifiedName() : null;
		if (eObjectModuleName == null
				|| !eObjectModuleName.equals(qualifiedNameConverter.toString(eoDescription.getQualifiedName()))) {
			return false;
		}

		// if not a main module we assume true
		if (!isMainModule(eoDescription)) {
			return true;
		}

		// for main modules we check containing project
		Resource eObjectResource = eObject != null ? eObject.eResource() : null;
		URI eObjectResourceURI = eObjectResource != null ? eObjectResource.getURI() : null;
		if (eObjectResourceURI == null) {
			return false;
		}

		final N4JSProjectConfigSnapshot targetProject = n4jsCore.findProject(context, eoDescription.getEObjectURI())
				.orNull();
		final N4JSProjectConfigSnapshot currentProject = n4jsCore.findProject(context, eObjectResourceURI).orNull();

		return targetProject == currentProject;
	}

	/**
	 * Determines whether the given object description represents a main module.
	 *
	 * Try to determine the main module status from the contained eObject if it is resolved and a module. This is
	 * necessary because the headless compiler doesn't serialize the modules for performance reasons, so the
	 * N4JSResourceDescriptionStrategy.MAIN_MODULE_KEY user data entry is not there to check.
	 */
	private boolean isMainModule(IEObjectDescription eoDescription) {
		return isMainModuleFromObject(eoDescription) || isMainModuleFromUserData(eoDescription);
	}

	/**
	 * Accesses the object referenced by the given object description to determine whether it represents a main module.
	 */
	private boolean isMainModuleFromObject(IEObjectDescription eoDescription) {
		TModule moduleOrProxy = (TModule) eoDescription.getEObjectOrProxy();
		return !moduleOrProxy.eIsProxy() && moduleOrProxy.isMainModule();
	}

	/**
	 * Accesses the serialized main module status value in the user data of the given object description to determine
	 * whether it represents a main module.
	 */
	private boolean isMainModuleFromUserData(IEObjectDescription eoDescription) {
		return N4JSResourceDescriptionStrategy.getMainModule(eoDescription);
	}

}
