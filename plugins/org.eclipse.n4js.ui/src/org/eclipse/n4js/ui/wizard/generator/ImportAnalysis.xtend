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

import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.URI

/**
 * Encapsulates import analysis results.
 *
 * These includes the import dependencies which still need to be inserted and
 * alias bindings which map demanded type uris to aliases in the modules scope.
 */
public class ImportAnalysis {
	public List<ImportRequirement> importRequirements;

	/** Alias bindings map demanded type uris to existing aliases in the module scope
	 *  May be empty
	 */
	public Map<URI, String> aliasBindings;

	new(List<ImportRequirement> importRequirements, Map<URI, String> aliasBindings) {
		this.importRequirements = importRequirements
		this.aliasBindings = aliasBindings
	}

}
