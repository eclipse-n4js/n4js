/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.codegen;

import com.google.common.base.Strings;

/**
 * Generates code for a field of a {@link Classifier}.
 */
public class Field extends Member<Field> {
	String fieldType;
	String defaultValue;

	/**
	 * Creates a new field with the given values.
	 *
	 * @param name
	 *            the name of this field
	 */
	public Field(String name) {
		super(name);
	}

	/**
	 * Sets the field type.
	 *
	 * @param fieldType
	 *            the field type
	 */
	public Field setFieldType(String fieldType) {
		this.fieldType = fieldType;
		return this;
	}

	/**
	 * Sets the default value or expression.
	 *
	 * @param defaultValue
	 *            the default value
	 */
	public Field setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	@Override
	protected String generateMember() {
		String result = "";
		if (!Strings.isNullOrEmpty(fieldType)) {
			result += fieldType;
		}
		if (!Strings.isNullOrEmpty(defaultValue)) {
			result += " = " + defaultValue + ";\n";
		}
		return result;
	}
}
