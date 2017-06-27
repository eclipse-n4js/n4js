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
 * Generates code for a getter method of a {@link Classifier}.
 */
class Getter extends Member<Getter> {
	String fieldType
	String defaultValue;

	/**
	 * Creates a new getter with the given parameters.
	 *
	 * @param name the getter's name
	 */
	public new(String name) {
		super(name)
	}

	/**
	 * Sets the field type.
	 *
	 * @param fieldType the field type
	 */
	public def Getter setFieldType(String fieldType) {
		this.fieldType = fieldType;
		return this;
	}

	/**
	 * Sets the default value or expression.
	 *
	 * @param defaultValue the default value
	 */
	public def Getter setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue
		return this;
	}

	override protected generateMember() '''
	«generateAbstract()»get «name»()«IF !fieldType.nullOrEmpty»: «fieldType»«ENDIF»«IF abstract»;«ELSE» { return «IF defaultValue.nullOrEmpty»null«ELSE»defaultValue«ENDIF»; }«ENDIF»
	'''
}
