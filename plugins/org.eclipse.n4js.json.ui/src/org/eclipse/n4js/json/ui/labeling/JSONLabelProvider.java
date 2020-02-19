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
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONNullLiteral;
import org.eclipse.n4js.json.JSON.JSONNumericLiteral;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Customized label provider for JSON documents.
 */
public class JSONLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public JSONLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	/**
	 * Display values of {@link NameValuePair}s as part of the description if they
	 * are not of container nature (e.g. 'a : "value"').
	 * 
	 * Only display the name of the value pair, if the value is an array or object.
	 */
	StyledString text(NameValuePair pair) {
		final JSONValue value = pair.getValue();
		final String nameDescription = String.format("%s", pair.getName());

		// if the name-value-pair has no children
		if (!value.isContainer()) {
			// display its value in line
			StyledString nameValueLabel = new StyledString(nameDescription + " : ");
			nameValueLabel = nameValueLabel.append(this.getText(pair.getValue()), StyledString.QUALIFIER_STYLER);
			return nameValueLabel;
		}
		return new StyledString(nameDescription);
	}

	/**
	 * Display "anonymous" objects as a grayed out {@code <object>}.
	 * 
	 * This can happen when objects are nested inside of arrays.
	 */
	StyledString text(JSONObject object) {
		return new StyledString("<object>", StyledString.QUALIFIER_STYLER);
	}

	/**
	 * Display "anonymous" arrays as a grayed out {@code <object>}.
	 * 
	 * This can happen when arrays are nested within arrays.
	 */
	StyledString text(JSONArray array) {
		return new StyledString("<array>", StyledString.QUALIFIER_STYLER);
	}

	/** Display the string representation of numeric literals. */
	String text(JSONNumericLiteral numericLiteral) {
		return numericLiteral.getValue().toString();
	}

	/** Display a double-quoted string representation of string literals. */
	String text(JSONStringLiteral stringLiteral) {
		return String.format("\"%s\"", stringLiteral.getValue());
	}

	/** Display {@code true} or {@code false} for boolean literals. */
	String text(JSONBooleanLiteral booleanLiteral) {
		return booleanLiteral.isBooleanValue() ? "true" : "false";
	}

	/** Display {@code null} for null literals. */
	String text(JSONNullLiteral nullLiteral) {
		return "null";
	}

	ImageDescriptor image(JSONArray array) {
		return JSONImageDescriptorCache.ImageRef.JSON_ARRAY.asImageDescriptor().get();
	}

	ImageDescriptor image(JSONObject object) {
		return JSONImageDescriptorCache.ImageRef.JSON_OBJECT.asImageDescriptor().get();
	}

	/** Customize fall-back icon for JSON values */
	@Override
	public Object image(Object element) {
		return JSONImageDescriptorCache.ImageRef.JSON_VALUE.asImageDescriptor().get();
	}

	ImageDescriptor image(NameValuePair pair) {
		final JSONValue value = pair.getValue();
		// display an object/array icon for name-value pairs if their value are an array
		// or object.
		if (value instanceof JSONArray) {
			return this.image((JSONArray) value);
		} else if (value instanceof JSONObject) {
			return this.image((JSONObject) value);
		}

		return JSONImageDescriptorCache.ImageRef.JSON_VALUE_PAIR.asImageDescriptor().get();
	}
}
