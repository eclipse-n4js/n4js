/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import org.eclipse.n4js.ide.xtext.resource.XResourceDescriptionsData;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * @author Sven Efftinge - Initial contribution and API
 */
public class XIndexState {
	private final ResourceDescriptionsData resourceDescriptions;

	private final XSource2GeneratedMapping fileMappings;

	/**
	 * Constructor
	 */
	public XIndexState() {
		this(new XResourceDescriptionsData(CollectionLiterals.<IResourceDescription> emptySet()),
				new XSource2GeneratedMapping());
	}

	/**
	 * Constructor
	 */
	public XIndexState(ResourceDescriptionsData resourceDescriptions,
			XSource2GeneratedMapping fileMappings) {
		super();
		this.resourceDescriptions = resourceDescriptions;
		this.fileMappings = fileMappings;
	}

	/**
	 * Getter
	 */
	public ResourceDescriptionsData getResourceDescriptions() {
		return this.resourceDescriptions;
	}

	/**
	 * Getter
	 */
	public XSource2GeneratedMapping getFileMappings() {
		return this.fileMappings;
	}
}
