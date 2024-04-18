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
package org.eclipse.n4js.validation.validators

import java.util.Collection
import java.util.HashSet
import java.util.Map
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.n4js.utils.validation.PostValidation
import org.eclipse.n4js.validation.AbstractMessageAdjustingN4JSValidator
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueItem
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

/**
 * Creates issues for unnecessary IDEBUG annotations.
 * <p>
 * The list of of used IDEBUG annotations
 * is managed by {@link AbstractN4JSDeclarativeValidator}.
 *
 * @see AbstractN4JSDeclarativeValidator
 */
class IDEBUGValidator extends AbstractMessageAdjustingN4JSValidator {

	public val static DEFINED_IDEBUGS_KEY =  AnnotationDefinition.IDEBUG.name + "_defined";
	public val static USED_IDEBUGS_KEY =  AnnotationDefinition.IDEBUG.name + "_used";

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	public static def Collection<Annotation> getDefinedAnnotations(Map<Object,Object> context) {
		var Collection<Annotation> annotations = context.get(DEFINED_IDEBUGS_KEY) as Collection<Annotation>;
		if (annotations===null) {
			annotations = new HashSet<Annotation>();
			context.put(DEFINED_IDEBUGS_KEY, annotations);
		}
		return annotations
	}

	public static def Collection<Annotation> getUsedAnnotations(Map<Object,Object> context) {
		var Collection<Annotation> annotations = context.get(USED_IDEBUGS_KEY) as Collection<Annotation>;
		if (annotations===null) {
			annotations = new HashSet<Annotation>();
			context.put(USED_IDEBUGS_KEY, annotations);
		}
		return annotations
	}

	@Check
	def void checkDefinedIDEBUGAnnotation(Annotation annotation) {
		if (AnnotationDefinition.IDEBUG.name.equals(annotation.name)) {
			getDefinedAnnotations(context).add(annotation);
		}
	}

	@Check
	def void checkUnusedIDEBUGAnnotation(PostValidation postValidation) {
		val definedAnnotations = getDefinedAnnotations(context);
		val usedAnnotations = getUsedAnnotations(context);
		for (Annotation a: definedAnnotations.filter[!usedAnnotations.contains(it)]) {
			if (!a.args.isEmpty) {
				val bugID = a.args.get(0).valueAsString;
				val IssueItem issueItem = ANN_UNUSED_IDEBUG.toIssueItem(bugID);
				addIssue(issueItem.message, a, issueItem.getID());
			}
		}
	}
}
