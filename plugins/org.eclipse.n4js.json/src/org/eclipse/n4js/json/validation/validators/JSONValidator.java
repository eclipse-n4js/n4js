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
package org.eclipse.n4js.json.validation.validators;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.validation.AbstractResourceDependentJSONValidator;
import org.eclipse.n4js.json.validation.IssueCodes;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ComposedChecks;

/**
 * This class contains general validation with regard to JSON files. 
 */
@ComposedChecks(validators = {
	PackageJsonValidator.class
})
public class JSONValidator extends AbstractResourceDependentJSONValidator {
	
	/**
	 * Checks for duplicate keys in {@link JSONObject}s.
	 */
	@Check
	public void checkDuplicateKeys(JSONObject object) {
		final Map<String, JSONValue> values = new HashMap<>();
		
		for (NameValuePair pair : object.getNameValuePairs()) {
			final JSONValue value = values.get(pair.getName());
			if (value != null) {
				final INode duplicatedNode = NodeModelUtils.findActualNodeFor(value);
				final int duplicatedLine = NodeModelUtils.getLineAndColumn(duplicatedNode, duplicatedNode.getOffset()).getLine();
				addIssue(IssueCodes.getMessageForJSON_DUPLICATE_KEY(pair.getName(), duplicatedLine), pair, 
						JSONPackage.Literals.NAME_VALUE_PAIR__NAME, IssueCodes.JSON_DUPLICATE_KEY);
			}
			values.put(pair.getName(), pair.getValue());
		}
	}
}
