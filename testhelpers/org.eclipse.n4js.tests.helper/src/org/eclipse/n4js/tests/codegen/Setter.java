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
 * Generates code for a setter method of a {@link Classifier}.
 */
public class Setter extends Member<Setter> {
	protected String fieldType;

	/**
	 * Creates a new setter with the given parameters.
	 *
	 * @param name
	 *            the setter's name
	 */
	public Setter(String name) {
		super(name);
	}

	/**
	 * Sets the field type of this setter.
	 *
	 * @param fieldType
	 *            the field type
	 */
	public Setter setFieldType(String fieldType) {
		this.fieldType = fieldType;
		return this;
	}

	@Override
	protected String generateMember() {
		String result = generateAbstract();
		result += "set " + name + "(value";
		if (Strings.isNullOrEmpty(fieldType)) {
			result += ": " + fieldType;
		}
		result += ")";
		if (isAbstract()) {
			result += ";";
		} else {
			result += "{}";
		}
		return result;
	}
}
