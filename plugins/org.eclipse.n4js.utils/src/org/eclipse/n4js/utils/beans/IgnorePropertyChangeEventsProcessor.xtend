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
package org.eclipse.n4js.utils.beans

import org.eclipse.xtend.lib.macro.AbstractFieldProcessor
import org.eclipse.xtend.lib.macro.ValidationContext
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration

/**
 * Processor for validating all fields that are supposed to NOT participate in property change support.
 */
class IgnorePropertyChangeEventsProcessor extends AbstractFieldProcessor {

	@Override
	override doValidate(FieldDeclaration field, extension ValidationContext context) {

		val clazz = field.declaringType;

		val annotations = clazz.annotations.filter[annotationTypeDeclaration.qualifiedName == PropertyChangeSupport.name];
		if (annotations.nullOrEmpty) {
			field.addError('''Declaring type is not annotated with @«PropertyChangeSupport.simpleName».''');
			return;
		}

		if (field.static) {
			field.addError('''Cannot enable property change support on static field.''');
			return;
		}

		if (field.final) {
			field.addError('''Cannot enable property change support on final field.''');
			return;
		}

		if (null !== field.type && field.type.inferred) {
			field.addError('''Cannot enable property change support on fields with inferred types.''');
			return;
		}

	}

}
