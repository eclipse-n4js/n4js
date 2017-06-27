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
package org.eclipse.n4js.annotation

import org.eclipse.xtend.lib.macro.AbstractMethodProcessor
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration
import org.eclipse.xtext.util.ITextRegion
import org.eclipse.xtext.util.TextRegion

import static org.eclipse.xtend.lib.macro.declaration.Visibility.PRIVATE

import static extension com.google.common.base.Strings.*

/**
 * Processor for transforming each method annotated with the {@link TestWithScript @TestWithScript} annotation.
 */
class TestWithScriptProcessor extends AbstractMethodProcessor {

	@Override
	override doTransform(MutableMethodDeclaration m, extension TransformationContext context) {

		val annotations = m.annotations.filter[annotationTypeDeclaration.qualifiedName == TestWithScript.name];

		if (!annotations.nullOrEmpty) {

			if (annotations.size > 1) {
				m.addError(
					'Duplicate annotation of non-repeatable type @TestWithScript.
							Only annotation types marked @Repeatable can be used multiple times at one target.')
				return;
			}

			val annotation = annotations.get(0);
			val script = annotation.getStringValue('script');
			val selectedText = annotation.getStringValue('selectedText').nullToEmpty;
			val occurrenceIndex = annotation.getIntValue('occurrenceIndex');

			if (null === script) {
				m.addError('Script cannot be null.');
				return;
			}

			if (0 > occurrenceIndex) {
				m.addError('Occurrence index must be a non-negative integer.')
			}

			var fromIndex = 0;
			var indexOf = 0;
			var numOfSuccess = -1;
			var stop = false

			while (!stop) {
				indexOf = script.indexOf(selectedText, fromIndex);
				if (indexOf > -1) {
					numOfSuccess = numOfSuccess + 1;
					fromIndex = indexOf + 1;
					stop = fromIndex >= script.length - 1 || occurrenceIndex === numOfSuccess;
				} else {
					stop = true;
				}
			}

			if (0 > numOfSuccess) {
				m.addError('Cannot find selected text in script.');
				return;
			}

			if (occurrenceIndex !== numOfSuccess) {
				m.addError(
					'''Selected text exists only «numOfSuccess + 1» time«IF numOfSuccess !== 0»s«ENDIF» in the script instead of «occurrenceIndex +
						1».''');
				return;
			}

			val offset = indexOf;
			val length = selectedText.length

			val originalName = m.simpleName;
			val newName = '''__«originalName»''';

			m.simpleName = newName;
			m.visibility = PRIVATE;
			m.declaringType.addMethod(originalName) [
				returnType = m.returnType;
				exceptions = m.exceptions;
				docComment = m.docComment;
				for (p : parameters) {
					addParameter(p.simpleName, p.type);
				}
				for (a : m.annotations.filter[it.annotationTypeDeclaration.qualifiedName != TestWithScript.name]) {
					addAnnotation(a);
				}
				body = [
					'''
						«ITextRegion.name» selection = new «TextRegion.name»(«offset», «length»);
						«String.name» script = "«script»";
						«IF !m.returnType.void»return «ENDIF»«newName»(«FOR p : m.parameters SEPARATOR ', '»«p.simpleName»«ENDFOR»);
					'''
				]
			]
			m.addParameter('selection', context.newTypeReference(ITextRegion));
			m.addParameter('script', context.newTypeReference(String));
			for (a : m.annotations) {
				m.removeAnnotation(a);
			}
		}
	}

}
