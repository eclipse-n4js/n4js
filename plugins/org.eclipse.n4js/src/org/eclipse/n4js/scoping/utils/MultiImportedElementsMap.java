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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.n4js.scoping.imports.ImportedElementsMap;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Multimap-based implementation of {@link ImportedElementsMap}.
 *
 * This map can only hold multiple {@link IEObjectDescription}s per qualified name.
 */
public class MultiImportedElementsMap implements ImportedElementsMap {

	private final Multimap<QualifiedName, IEObjectDescription> elementsMap;

	/**
	 * Instantiates a new {@link MultiImportedElementsMap} instance.
	 */
	public MultiImportedElementsMap() {
		this.elementsMap = LinkedHashMultimap.create();
	}

	@Override
	public boolean containsElement(QualifiedName name) {
		return this.elementsMap.containsKey(name);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		return this.elementsMap.get(name);
	}

	@Override
	public void put(QualifiedName name, IEObjectDescription element) {
		this.elementsMap.put(name, element);
	}

	@Override
	public Iterable<IEObjectDescription> values() {
		return this.elementsMap.values();
	}

}
