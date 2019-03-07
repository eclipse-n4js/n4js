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
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider;

/**
 * Xtext's RefactoringResourceSetProvider uses LIVE_SCOPE resource loading which does not work for some reason.
 */
@SuppressWarnings("restriction")
public class N4JSRefactoringResourceSetProvider extends RefactoringResourceSetProvider {

	@Override
	public ResourceSet get(IProject project) {
		// LIVE_SCOPE does not. PERSISTED_DESCRIPTIONS however works
		ResourceSet rs = super.get(project);
		rs.getLoadOptions().remove(ResourceDescriptionsProvider.LIVE_SCOPE);
		rs.getLoadOptions().put(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS, Boolean.TRUE);
		return rs;
	}
}
