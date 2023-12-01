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
package org.eclipse.n4js.utils;

import org.apache.log4j.Logger;
import org.eclipse.xtend.lib.macro.AbstractClassProcessor;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableFieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Visibility;
import org.eclipse.xtend2.lib.StringConcatenationClient;

/**
 * Adds logger declaration (field name "logger") to annotated class. Manual definition:
 *
 * <pre>
 * private final static Logger logger = Logger.getLogger(TYPE.class);
 * </pre>
 */
public class LogProcessor extends AbstractClassProcessor {

	@Override
	public void doTransform(MutableClassDeclaration annotatedClass, TransformationContext context) {
		addLoggerDeclaration(annotatedClass, context);
	}

	public void addLoggerDeclaration(MutableClassDeclaration cdecl, TransformationContext context) {
		cdecl.addField("logger", (MutableFieldDeclaration field) -> {
			field.setStatic(true);
			field.setFinal(true);
			field.setVisibility(Visibility.PRIVATE);
			field.setType(context.newTypeReference(Logger.class));
			field.setInitializer(new StringConcatenationClient() {
				@Override
				protected void appendTo(StringConcatenationClient.TargetStringConcatenation tsc) {
					tsc.append(Logger.class);
					tsc.append(".getLogger(");
					tsc.append(cdecl);
					tsc.append(".class)");
				}
			});
		});
	}
}
