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

import com.google.common.base.Strings;

/**
 * Generates the code for a class.
 */
public class Class extends Classifier<Class> {
	String superClass;
	List<String> implementedInterfaces = new LinkedList<>();

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the name of the class
	 */
	public Class(String name) {
		super(name);
	}

	/**
	 * Sets the super class.
	 *
	 * @param superClass
	 *            the super class or interface.
	 */
	public Class setSuperClass(Class superClass) {
		return setSuperClass(superClass.getName());
	}

	/**
	 * Sets the super class.
	 *
	 * @param superClass
	 *            the name of the super class or interface.
	 */
	public Class setSuperClass(String superClass) {
		this.superClass = superClass;
		return this;
	}

	/**
	 * Adds an interface implemented by the class to be built.
	 *
	 * @param implementedInterface
	 *            the name of the interface to implement
	 *
	 * @return this builder
	 */
	public Class addInterface(Interface implementedInterface) {
		return addInterface(implementedInterface.getName());
	}

	/**
	 * Adds an interface implemented by the class to be built.
	 *
	 * @param implementedInterface
	 *            the interface to implement
	 */
	public Class addInterface(String implementedInterface) {
		implementedInterfaces.add(implementedInterface);
		return this;
	}

	@Override
	protected String generateType() {
		return "class ";
	}

	@Override
	protected String generateTypeRelations() {
		return generateSuperClass() + generateImplementedInterfaces();
	}

	private String generateSuperClass() {
		if (!Strings.isNullOrEmpty(superClass)) {
			return " extends " + superClass;
		}
		return "";
	}

	private String generateImplementedInterfaces() {
		String result = "";
		if (!implementedInterfaces.isEmpty()) {
			result += " implements ";
			boolean isFirst = true;
			for (String inf : implementedInterfaces) {
				if (!isFirst) {
					result += ", ";
				}
				result += inf;
				isFirst = false;
			}
		}
		return result;
	}
}
