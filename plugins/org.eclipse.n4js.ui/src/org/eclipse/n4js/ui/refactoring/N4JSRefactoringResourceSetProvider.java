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

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider;

import com.google.inject.Inject;

/**
 * HACK! We need to use the ResourceSet that contains already built resources
 */
@SuppressWarnings("restriction")
public class N4JSRefactoringResourceSetProvider extends RefactoringResourceSetProvider {
	@Inject
	IN4JSCore core;

	@Override
	public ResourceSet get(IProject project) {
		// LIVE_SCOPE does not. PERSISTED_DESCRIPTIONS however works
		ResourceSet rs = super.get(project);
		rs.getLoadOptions().remove(ResourceDescriptionsProvider.LIVE_SCOPE);
		rs.getLoadOptions().put(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS, Boolean.TRUE);
		return rs;
	}
}
