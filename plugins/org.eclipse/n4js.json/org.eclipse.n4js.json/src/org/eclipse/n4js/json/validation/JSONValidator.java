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
package org.eclipse.n4js.json.validation;

import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules with regard to JSON files. 
 */
public class JSONValidator extends AbstractJSONValidator {
	
	@Check
	public void checkDocument(JSONDocument document) {
		// validate JSON here
	}
}
