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

import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import java.lang.annotation.Target
import java.lang.annotation.ElementType
import org.eclipse.xtend.lib.macro.Active
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.Visibility
import org.apache.log4j.Logger


@Target(ElementType.TYPE)
@Active(LogProcessor)
annotation Log {}

/**
 * Adds logger declaration (field name "logger") to annotated class.
 * Manual definition:
 * <pre>
 * private final static Logger logger = Logger.getLogger(TYPE.class);
 * </pre>
 */
class LogProcessor extends AbstractClassProcessor {

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		addLoggerDeclaration(annotatedClass, context);
	}


	def addLoggerDeclaration(MutableClassDeclaration cdecl, TransformationContext context) {
		cdecl.addField("logger") [
			static=true;
			final=true;
			visibility=Visibility.PRIVATE;
			type= context.newTypeReference(Logger);
			initializer = '''«Logger».getLogger(«cdecl».class)'''
		]
	}
}
