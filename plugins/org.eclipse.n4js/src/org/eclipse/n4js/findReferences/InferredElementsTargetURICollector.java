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
package org.eclipse.n4js.findReferences;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.projectModel.ProjectUtils;
import org.eclipse.n4js.resource.InferredElements;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;

import com.google.inject.Inject;

/**
 * Collector of target URIs when find references is triggered. Collects the URI of the given object and of all objects
 * that have been inferred from that.
 */
@SuppressWarnings("restriction")
public class InferredElementsTargetURICollector extends TargetURICollector {

	@Inject
	private InferredElements inferredElements;

	@Inject
	private ProjectUtils projectUtils;

	@Override
	protected void doAdd(EObject primaryTarget, TargetURIs targetURIsAddHere) {
		Resource resource = primaryTarget.eResource();
		// If the target is not contained in a resource, we cannot do anything but return.
		if (resource == null)
			return;
		EcoreUtil.resolveAll(primaryTarget.eResource());

		// Special handling for composed members and TStructMember
		if (primaryTarget instanceof TMember && ((TMember) primaryTarget).isComposed()) {
			// In case of composed member, add the constituent members instead.
			List<TMember> constituentMembers = ((TMember) primaryTarget).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				super.doAdd(constituentMember, targetURIsAddHere);
			}
		} else {
			if (primaryTarget instanceof TStructMember) {
				TStructMember crossRefStructMember = ((TStructMember) primaryTarget).getDefinedMember();
				if (crossRefStructMember != null)
					// If this TStructMember is an AST, also add the defined member located in the TModule
					super.doAdd(((TStructMember) primaryTarget).getDefinedMember(), targetURIsAddHere);
			}
			super.doAdd(primaryTarget, targetURIsAddHere);
		}

		inferredElements.collectInferredElements(primaryTarget, (object) -> {
			if (object != null) {
				super.doAdd(object, targetURIsAddHere);
			}
		}, projectUtils);
	}

}
