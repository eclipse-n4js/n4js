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
package org.eclipse.n4js.json.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONNullLiteral;
import org.eclipse.n4js.json.JSON.JSONNumericLiteral;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.ui.JSONUIModelUtils;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
public class JSONLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public JSONLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	String text(NameValuePair pair) {
		JSONValue value = pair.getValue();
		// if the name-value-pair has no children
		if (!JSONUIModelUtils.isContainer(value)) {
			// display its value inline
			return String.format("%s = %s", pair.getName(), getText(value));
		}
		return pair.getName();
	}
	
	String text(JSONObject object) {
		return "<object>";
	}
	
	String text(JSONArray object) {
		return "<array>";
	}
	
	String text(JSONNumericLiteral numericLiteral) {
		return numericLiteral.getValue().toString();
	}
	
	String text(JSONStringLiteral stringLiteral) {
		return String.format("\"%s\"", stringLiteral.getValue());
	}
	
	String text(JSONBooleanLiteral booleanLiteral) {
		return booleanLiteral.isBooleanValue() ? "true" : "false";
	}
	
	String text(JSONNullLiteral nullLiteral) {
		return "null";
	}
	
	ImageDescriptor image(NameValuePair pair) {
		return JSONImageDescriptorCache.ImageRef.EXPRESSION_OBJ.asImageDescriptor().get();
	}
	
	// Labels and icons can be computed like this:
	
//	String text(Greeting ele) {
//		return "A greeting to " + ele.getName();
//	}
//
//	String image(Greeting ele) {
//		return "Greeting.gif";
//	}
}
