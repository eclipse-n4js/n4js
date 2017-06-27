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
package org.eclipse.n4js.utils

import java.lang.annotation.ElementType
import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.AbstractFieldProcessor
import org.eclipse.xtend.lib.macro.Active
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableFieldDeclaration
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.InterfaceDeclaration

/**
 */
@Target(ElementType::FIELD)
@Active(typeof(CompilerProcessor))
annotation Compiler {
}

class CompilerProcessor extends AbstractFieldProcessor {

	override doTransform(MutableFieldDeclaration annotatedField, extension TransformationContext context) {
		if(annotatedField.type === string) {
			annotatedField.addError("Annotated field must be of type String.")
		} else {
			val value = getInitializerAsString(annotatedField, context)
			if (value !== null) {
				val resolvedType = findTypeGlobally(value)
				if (resolvedType === null) {
					annotatedField.initializer.addError(value + " isn't on the classpath.")
				} else {
					if(!(resolvedType instanceof ClassDeclaration)) {
						annotatedField.initializer.addError(value + " have to resolve to a class on the classpath.")
					} else {
						val clazz = resolvedType as ClassDeclaration
						val subGeneratorInterface = "org.eclipse.n4js.generator.common.ISubGenerator"
						if(!clazz.implementsInterface(subGeneratorInterface)) {
							annotatedField.initializer.addError("The class " + value + " have to implement the interface " + subGeneratorInterface + ", but only implements " + clazz.implementedInterfaces.map[name])
						}
					}
				}
			}
		}
	}

	def private boolean implementsInterface(ClassDeclaration clazz, String expectedInterface) {
		if(clazz.implementedInterfaces.exists[name.equals(expectedInterface)]) {
			return true;
		}
		if(clazz.implementedInterfaces.exists[(type as InterfaceDeclaration).extendsInterface(expectedInterface)]) {
			return true;
		}
		if(clazz.extendedClass !== null) {
			return implementsInterface(clazz.extendedClass.type as ClassDeclaration, expectedInterface)
		}
		return false;
	}

	def private boolean extendsInterface(InterfaceDeclaration interf, String expectedInterface) {
		if(interf.extendedInterfaces.exists[name.equals(expectedInterface)]) {
			return true;
		}
		return false;
	}

	def getInitializerAsString(FieldDeclaration f, extension TransformationContext context) {
		val string = f.initializer?.toString
		if(string === null) {
			f.addError("A value have to be assigned to this annotated field.")
		} else if(!(string.startsWith("\"") && string.endsWith("\""))) {
			f.addError("A quoted string value have to be assigned to this annotated field.")
		} else {
			return string.substring(1, string.length - 1)
		}
	}
}
