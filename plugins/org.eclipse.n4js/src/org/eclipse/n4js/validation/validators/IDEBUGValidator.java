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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.validation.IssueCodes.ANN_UNUSED_IDEBUG;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.utils.validation.PostValidation;
import org.eclipse.n4js.validation.AbstractMessageAdjustingN4JSValidator;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * Creates issues for unnecessary IDEBUG annotations.
 * <p>
 * The list of of used IDEBUG annotations is managed by {@link AbstractN4JSDeclarativeValidator}.
 *
 * @see AbstractN4JSDeclarativeValidator
 */
@SuppressWarnings("javadoc")
public class IDEBUGValidator extends AbstractMessageAdjustingN4JSValidator {

	public static String DEFINED_IDEBUGS_KEY = AnnotationDefinition.IDEBUG.name + "_defined";
	public static String USED_IDEBUGS_KEY = AnnotationDefinition.IDEBUG.name + "_used";

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	public static Collection<Annotation> getDefinedAnnotations(Map<Object, Object> context) {
		@SuppressWarnings("unchecked")
		Collection<Annotation> annotations = (Collection<Annotation>) context.get(DEFINED_IDEBUGS_KEY);
		if (annotations == null) {
			annotations = new HashSet<>();
			context.put(DEFINED_IDEBUGS_KEY, annotations);
		}
		return annotations;
	}

	public static Collection<Annotation> getUsedAnnotations(Map<Object, Object> context) {
		@SuppressWarnings("unchecked")
		Collection<Annotation> annotations = (Collection<Annotation>) context.get(USED_IDEBUGS_KEY);
		if (annotations == null) {
			annotations = new HashSet<>();
			context.put(USED_IDEBUGS_KEY, annotations);
		}
		return annotations;
	}

	@Check
	public void checkDefinedIDEBUGAnnotation(Annotation annotation) {
		if (AnnotationDefinition.IDEBUG.name.equals(annotation.getName())) {
			getDefinedAnnotations(getContext()).add(annotation);
		}
	}

	@Check
	public void checkUnusedIDEBUGAnnotation(@SuppressWarnings("unused") PostValidation postValidation) {
		Collection<Annotation> definedAnnotations = getDefinedAnnotations(getContext());
		Collection<Annotation> usedAnnotations = getUsedAnnotations(getContext());
		for (Annotation a : filter(definedAnnotations, it -> !usedAnnotations.contains(it))) {
			if (!a.getArgs().isEmpty()) {
				String bugID = a.getArgs().get(0).getValueAsString();
				IssueItem issueItem = ANN_UNUSED_IDEBUG.toIssueItem(bugID);
				addIssue(issueItem.message, a, issueItem.getID());
			}
		}
	}
}
