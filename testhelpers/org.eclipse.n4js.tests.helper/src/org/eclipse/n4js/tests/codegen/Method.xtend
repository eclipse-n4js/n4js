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
import java.util.Objects

/**
 * Code generator for member methods of a {@link Classifier}.
 */
class Method extends Member<Method> {
	/**
	 * A method parameter specification.
	 */
	static class Param {
		String type;
		String name;

		/**
		 * Creates a new instance with the given type and name.
		 *
		 * @param type the parameter type
		 * @param name the parameter name
		 */
		new(String type, String name) {
			this.type = type;
			this.name = name;
		}
	}

	String returnType;
	List<Param> params;
	String body;

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param name the method name
	 */
	public new(String name) {
		super(name);
	}

	/**
	 * Sets the return type of this method.
	 *
	 * @param returnType the return type
	 */
	public def Method setReturnType(String returnType) {
		this.returnType = returnType;
		return this;
	}

	/**
	 * Adds a parameter to this method.
	 *
	 * @param param the parameter to add
	 */
	public def Method addParameter(Param param) {
		if (this.params === null)
			this.params = newLinkedList();
		this.params.add(Objects.requireNonNull(param));
		return this;
	}

	/**
	 * Sets the body of this method.
	 *
	 * @param body the body to set
	 */
	public def Method setBody(String body) {
		this.body = body;
		return this;
	}

	override protected generateMember() '''
	«generateAbstract()»«name»(«IF params !== null»«FOR p : params»«p.name»: «p.type»«ENDFOR»«ENDIF»)«IF !returnType.nullOrEmpty»: «returnType»«ENDIF»«IF abstract»;«ELSE» «IF !hasBody»{}«ELSE»{
		«IF !body.nullOrEmpty»
		«body»
		«ELSEIF !returnType.nullOrEmpty»
		return new «returnType»()
		«ENDIF»
	}«ENDIF»
	«ENDIF»
	'''

	private def boolean hasBody() {
		!body.nullOrEmpty || !returnType.nullOrEmpty
	}
}
