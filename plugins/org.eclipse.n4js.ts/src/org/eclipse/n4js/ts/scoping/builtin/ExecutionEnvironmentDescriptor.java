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
package org.eclipse.n4js.ts.scoping.builtin;

import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Allows to obtain resources by name from a well defined location.
 */
public class ExecutionEnvironmentDescriptor {

	private final ResourceSet resourceSet;

	/**
	 * Creates a new descriptor that loads its well defined content.
	 */
	public ExecutionEnvironmentDescriptor(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	/**
	 * Processes the contents of the given files.
	 */
	protected void processResources(String[] fileNames, Consumer<? super Resource> consumer) {
		for (String fileName : fileNames) {
			URI uri = N4Scheme.N4URI.create(fileName);
			Resource resource = resourceSet.getResource(uri, true);
			consumer.accept(resource);
		}
	}

}
