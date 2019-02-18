/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ui.refactoring;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
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
		List<URI> elementURIs = super.getDependentElementURIs(baseElement, monitor);

		// Collect target URIs using URICollector so that TClass are also added to the set of target URIs.
		TargetURIs targetURIs = targetURIProvider.get();
		uriCollector.add(baseElement, targetURIs);

		for (URI uri : targetURIs) {
			elementURIs.add(uri);
		}

		return elementURIs;
	}

}
