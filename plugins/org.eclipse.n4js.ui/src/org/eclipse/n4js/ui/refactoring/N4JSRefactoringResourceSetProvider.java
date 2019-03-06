/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.refactoring;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider;

/**
 * HACK! We need to use the ResourceSet that contains already built resources
 */
public class N4JSRefactoringResourceSetProvider extends RefactoringResourceSetProvider {
	// public static ResourceSet myGlobalResourceSet;
	//
	// @Override
	// public ResourceSet get(IProject project) {
	// // final ResourceSet resourceSet = resourceSetProvider.get();
	// // resourceSet.getLoadOptions().put(PERSISTED_DESCRIPTIONS, TRUE);
	// // createAllResourcesWorkspace(resourceSet);
	// // attachResourceDescriptionsData(resourceSet);
	// // return resourceSet;
	//
	// // return N4JSRefactoringResourceSetProvider.myGlobalResourceSet;
	// return super.get(project);
	// }

	@Override
	protected void configure(ResourceSet resourceSet) {
		// Install a lightweight index.
		// OrderedResourceDescriptionsData index = new OrderedResourceDescriptionsData(Collections.emptyList());
		// ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(resourceSet, index);
	}

}
