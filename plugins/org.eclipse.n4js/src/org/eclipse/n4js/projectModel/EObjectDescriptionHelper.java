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
package org.eclipse.n4js.projectModel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.ts.types.TModule;
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
	private IN4JSCore n4jsCore;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * Helper method that checks if given {@link IEObjectDescription description} describes {@link TModule} containing
	 * given {@link EObject}.
	 *
	 * Returns <code>true</code> only if provided {@link IEObjectDescription description} has the same
	 * {@link QualifiedName} as module of the {@link EObject}. Additionally if {@link IEObjectDescription description}
	 * describes {@link TModule#isMainModule() main module} then it is checked if both are contained in the same
	 * {@link IN4JSProject}.
	 *
	 * @returns true if {@link IEObjectDescription} describes module of {@link EObject}
	 */
	public boolean isDescriptionOfModuleWith(IEObjectDescription eoDescription, EObject eObject) {
		// check if module names are the same
		final String eobjectModuleName = EcoreUtil2.getContainerOfType(eObject, Script.class).getModule()
				.getQualifiedName();
		if (!eobjectModuleName.equals(qualifiedNameConverter.toString(eoDescription.getQualifiedName()))) {
			return false;
		}

		// check if not a main module, assume true
		final String mainModuelUserData = eoDescription.getUserData(N4JSResourceDescriptionStrategy.MAIN_MODULE_KEY);
		if (mainModuelUserData == null || !Boolean.getBoolean(mainModuelUserData)) {
			return true;
		}

		// for main modules we check containing project
		final IN4JSProject targetProject = n4jsCore.findProject(eoDescription.getEObjectURI()).orNull();
		final IN4JSProject currentProject = n4jsCore.findProject(eObject.eResource().getURI()).orNull();

		return targetProject == currentProject;
	}

}
