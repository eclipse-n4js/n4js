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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Strings;

/**
 * Code generator for member methods of a {@link Classifier}.
 */
public class Method extends Member<Method> {
	/**
	 * A method parameter specification.
	 */
	static class Param {
		String type;
		String name;

		/**
		 * Creates a new instance with the given type and name.
		 *
		 * @param type
		 *            the parameter type
		 * @param name
		 *            the parameter name
		 */
		Param(String type, String name) {
			this.type = type;
			this.name = name;
		}
	}

	String returnType;
	List<Param> params = new LinkedList<>();
	String body;

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the method name
	 */
	public Method(String name) {
		super(name);
	}

	/**
	 * Sets the return type of this method.
	 *
	 * @param returnType
	 *            the return type
	 */
	public Method setReturnType(String returnType) {
		this.returnType = returnType;
		return this;
	}

	/**
	 * Adds a parameter to this method.
	 *
	 * @param param
	 *            the parameter to add
	 */
	public Method addParameter(Param param) {
		this.params.add(Objects.requireNonNull(param));
		return this;
	}

	/**
	 * Sets the body of this method.
	 *
	 * @param body
	 *            the body to set
	 */
	public Method setBody(String body) {
		this.body = body;
		return this;
	}

	@Override
	protected String generateMember() {
		String result = generateAbstract();
		result += name + "(";
		boolean isFirst = true;
		for (Param p : params) {
			if (!isFirst) {
				result += ", ";
			}
			result += p.name + ": " + p.type;
			isFirst = false;
		}
		result += ")";
		if (!Strings.isNullOrEmpty(returnType)) {
			result += ": " + returnType;
		}
		if (isAbstract()) {
			result += ";";
		} else {
			if (hasBody()) {
				result += "{";
				if (!Strings.isNullOrEmpty(body)) {
					result += body;
				} else if (!Strings.isNullOrEmpty(returnType)) {
					result += "return new " + returnType + "()";
				}
				result += "}";
			} else {
				result += "{}";
			}
		}

		return result;
	}

	private boolean hasBody() {
		return !Strings.isNullOrEmpty(body) || !Strings.isNullOrEmpty(returnType);
	}
}
