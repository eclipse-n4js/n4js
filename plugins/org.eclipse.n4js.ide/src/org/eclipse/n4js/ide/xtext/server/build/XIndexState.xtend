/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ide.xtext.server.build

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData

/**
 * @author Sven Efftinge - Initial contribution and API
 */
@FinalFieldsConstructor @Accessors class XIndexState {
	
	val ResourceDescriptionsData resourceDescriptions
	val XSource2GeneratedMapping fileMappings
	
	new () {
		this(new ResourceDescriptionsData(emptySet), new XSource2GeneratedMapping)
	}
	
}
