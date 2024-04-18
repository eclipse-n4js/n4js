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
package org.eclipse.n4js.resource;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.typesbuilder.N4JSTypesBuilder;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IDerivedStateComputer;

import com.google.inject.Inject;

/**
 * Derives the types model from the AST and stores it at the second index of the resource. See {@link N4JSTypesBuilder}.
 */
public class N4JSDerivedStateComputer implements IDerivedStateComputer {

	@Inject
	private N4JSUnloader n4jsUnloader;
	@Inject
	private N4JSTypesBuilder typesBuilder;

	/**
	 * Creates an {@link TModule} on the second slot of the resource. when the resource contents is not empty.
	 */
	@Override
	public void installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		List<EObject> contents = resource.getContents();
		if (contents.isEmpty()) {
			String msg = "cannot install derived state in resource '" + resource.getURI().toString() + "' without AST";
			throw new IllegalStateException(msg);
		} else if (contents.size() == 1) {
			typesBuilder.createTModuleFromSource(resource, preLinkingPhase);
		} else if (contents.size() == 2) {
			typesBuilder.relinkTModuleToSource(resource, preLinkingPhase);
		} else {
			throw new IllegalStateException("resource '" + resource.getURI().toString() + "' with more than two roots");
		}
	}

	/**
	 * Calls {@link N4JSUnloader#unloadRoot(EObject)} for the second slot root. Then all contents of the resource are
	 * cleared.
	 */
	@Override
	public void discardDerivedState(DerivedStateAwareResource resource) {
		List<EObject> contents = resource.getContents();
		// resource.getContents().get(1-n)clear
		if (contents.isEmpty()) {
			return;
		}

		// other resources may hold references to the derived state thus we
		// have to unload (proxify) it explicitly before it is removed from the resource
		List<EObject> tail = contents.subList(1, contents.size());
		for (EObject eo : tail) {
			n4jsUnloader.unloadRoot(eo);
		}
		tail.clear();
	}
}
