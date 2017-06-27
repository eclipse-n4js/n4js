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

/**
 * Generates code for a setter method of a {@link Classifier}.
 */
class Setter extends Member<Setter> {
	protected String fieldType

	/**
	 * Creates a new setter with the given parameters.
	 *
	 * @param name the setter's name
	 */
	public new(String name) {
		super(name)
	}

	/**
	 * Sets the field type of this setter.
	 *
	 * @param fieldType the field type
	 */
	public def Setter setFieldType(String fieldType) {
		this.fieldType = fieldType;
		return this;
	}

	override protected generateMember() '''
	«generateAbstract()»set «name»(value«IF !fieldType.nullOrEmpty»: «fieldType»«ENDIF»)«IF abstract»;«ELSE» {}«ENDIF»
	'''
}
