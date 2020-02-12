/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.ide.contentassist;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.NameValuePair;

/**
 * Utilities when working with a model in the context of code completion.
 */
public final class CompletionUtils {
	/**
	 * Return the property names on the path towards the current model.
	 */
	public static List<String> getJsonPathNames(EObject model) {
		List<String> namePath = new LinkedList<>();
		EObject eobj = model;
		while (!(eobj instanceof JSONDocument)) {

			if (eobj instanceof NameValuePair) {
				NameValuePair nvp = (NameValuePair) eobj;
				namePath.add(0, nvp.getName());
			}

			eobj = eobj.eContainer();
		}
		return namePath;
	}

	/**
	 * Returns the property names that are already in use in the current model.
	 */
	public static Set<String> getAlreadyUsedNames(EObject model) {
		Set<String> alreadyUsedNames = new HashSet<>();
		if (model instanceof JSONObject) {
			JSONObject jsonObj = (JSONObject) model;
			for (NameValuePair nvp : jsonObj.getNameValuePairs()) {
				alreadyUsedNames.add(nvp.getName());
			}
		}
		return alreadyUsedNames;
	}
}
