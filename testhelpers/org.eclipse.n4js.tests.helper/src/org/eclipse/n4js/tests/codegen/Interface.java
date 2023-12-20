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

/**
 * Generates the code for an interface.
 */
public class Interface extends Classifier<Interface> {
	List<String> extendedInterfaces = new LinkedList<>();

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name
	 *            the name of the interface
	 */
	public Interface(String name) {
		super(name);
	}

	/**
	 * Adds a super interface to this interface
	 *
	 * @param implementedInterface
	 *            the interface to add
	 *
	 * @return this builder
	 */
	public Interface addSuperInterface(Interface implementedInterface) {
		return addSuperInterface(implementedInterface.name);
	}

	/**
	 * Adds a super interface to this interface
	 *
	 * @param implementedInterface
	 *            the name of the interface to add
	 */
	public Interface addSuperInterface(String implementedInterface) {
		extendedInterfaces.add(implementedInterface);
		return this;
	}

	@Override
	protected String generateType() {
		return "interface ";
	}

	@Override
	protected String generateTypeRelations() {
		if (!extendedInterfaces.isEmpty()) {
			String result = " extends ";
			boolean isFirst = true;
			for (String inf : extendedInterfaces) {
				if (!isFirst) {
					result += ", ";
				}
				result += inf;
				isFirst = false;
			}
			return result;
		}
		return "";
	}
}
