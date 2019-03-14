/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ui.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.ui.refactoring.impl.DefaultDependentElementsCalculator;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Delivers all contained elements of an element to be renamed in order to updated references to them.
 *
 * @author Jan Koehnlein - Initial contribution and API
 */
@SuppressWarnings("restriction")
public class N4JSDependentElementsCalculator extends DefaultDependentElementsCalculator {

	@Inject
	private TargetURICollector uriCollector;

	@Inject
	private Provider<TargetURIs> targetURIProvider;

	private static final int MONITOR_CHUNK_SIZE = 1000;

	@Override
	public List<URI> getDependentElementURIs(EObject baseElement, IProgressMonitor monitor) {
		// // Special handling for composed members
		// List<EObject> realTargets = new ArrayList<>();
		// if ((baseElement instanceof TMember) && ((TMember) baseElement).isComposed()) {
		// // In case of composed member, add the constituent members instead.
		// List<TMember> constituentMembers = ((TMember) baseElement).getConstituentMembers();
		// for (TMember constituentMember : constituentMembers) {
		// realTargets.add(constituentMember);
		// }
		// } else {
		// // Standard case
		// realTargets.add(declaration);
		// }
		//
		// TargetURIs targets = targetURISetProvider.get();
		//
		// for (EObject realTarget : realTargets) {
		// collector.add(realTarget, targets);
		// }
		// return targets;

		List<EObject> realTargets = new ArrayList<>();
		if ((baseElement instanceof TMember) && ((TMember) baseElement).isComposed()) {
			// In case of composed member, add the constituent members instead.
			List<TMember> constituentMembers = ((TMember) baseElement).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				realTargets.add(constituentMember);
			}
		} else {
			// Standard case
			realTargets.add(baseElement);
		}

		List<URI> elementURIs = super.getDependentElementURIs(baseElement, monitor);

		for (EObject realTarget : realTargets) {
			elementURIs.add(EcoreUtil.getURI(realTarget));
		}

		return elementURIs;
	}

}
