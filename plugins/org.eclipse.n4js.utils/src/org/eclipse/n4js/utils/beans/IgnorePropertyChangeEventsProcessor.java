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
package org.eclipse.n4js.utils.beans;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;

import org.eclipse.xtend.lib.macro.AbstractFieldProcessor;
import org.eclipse.xtend.lib.macro.ValidationContext;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.TypeDeclaration;

/**
 * Processor for validating all fields that are supposed to NOT participate in property change support.
 */
public class IgnorePropertyChangeEventsProcessor extends AbstractFieldProcessor {

	@Override
	public void doValidate(FieldDeclaration field, ValidationContext context) {

		TypeDeclaration clazz = field.getDeclaringType();

		Iterable<? extends AnnotationReference> annotations = filter(clazz.getAnnotations(),
				ann -> ann.getAnnotationTypeDeclaration().getQualifiedName() == PropertyChangeSupport.class.getName());

		if (isNullOrEmpty(annotations)) {
			context.addError(field,
					"Declaring type is not annotated with @" + PropertyChangeSupport.class.getSimpleName() + ".");
			return;
		}

		if (field.isStatic()) {
			context.addError(field, "Cannot enable property change support on static field.");
			return;
		}

		if (field.isFinal()) {
			context.addError(field, "Cannot enable property change support on final field.");
			return;
		}

		if (null != field.getType() && field.getType().isInferred()) {
			context.addError(field, "Cannot enable property change support on fields with inferred types.");
			return;
		}

	}

}
