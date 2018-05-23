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
package org.eclipse.n4js.tests.codegen

import java.util.List

/**
 * Generates the code for an interface.
 */
class Interface extends Classifier<Interface> {
	List<String> extendedInterfaces;

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name the name of the interface
	 */
	public new(String name) {
		super(name)
	}

	/**
	 * Adds a super interface to this interface
	 *
	 * @param implementedInterface the interface to add
	 *
	 * @return this builder
	 */
	public def Interface addSuperInterface(Interface implementedInterface) {
		return addSuperInterface(implementedInterface.name)
	}

	/**
	 * Adds a super interface to this interface
	 *
	 * @param implementedInterface the name of the interface to add
	 */
	public def Interface addSuperInterface(String implementedInterface) {
		if (extendedInterfaces === null)
			extendedInterfaces = newLinkedList();
		extendedInterfaces.add(implementedInterface);
		return this;
	}


	override protected def generateType() '''interface '''

	override protected def CharSequence generateTypeRelations() '''
	«IF extendedInterfaces !== null»«FOR i : extendedInterfaces BEFORE ' extends ' SEPARATOR ', '»«i»«ENDFOR»«ENDIF»
	'''
}
