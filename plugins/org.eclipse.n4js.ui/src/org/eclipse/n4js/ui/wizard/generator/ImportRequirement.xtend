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
package org.eclipse.n4js.ui.wizard.generator

import org.eclipse.emf.common.util.URI

/**
 * Represents an import requirement.
 */
public class ImportRequirement {
	public String typeName;
	public String alias;
	public String moduleSpecifier;

	public URI typeUri;

	new(String typeName, String alias, String moduleSpecifier, URI typeUri) {
		this.typeName = typeName
		this.alias = alias;
		this.moduleSpecifier = moduleSpecifier;
		this.typeUri = typeUri;
	}
}
