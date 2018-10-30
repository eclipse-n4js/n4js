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
package org.eclipse.n4js.validation;

import java.lang.reflect.Method
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.typesystem.utils.Result
import org.eclipse.n4js.utils.validation.DelegatingValidationMessageAcceptor
import org.eclipse.n4js.validation.validators.IDEBUGValidator
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.AbstractDeclarativeValidator
import org.eclipse.xtext.validation.CancelableDiagnostician
import org.eclipse.xtext.validation.ValidationMessageAcceptor

/**
 */
public class AbstractMessageAdjustingN4JSValidator extends AbstractN4JSValidator {

	def boolean createTypeError(Result<?> result, EObject source) {
		if (result.failure) {
			val msg = result.combinedFailureMessage;
			getMessageAcceptor().acceptError(msg, source, null,
				ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "org.eclipse.n4js.TypeErrorIssueCode");
			return true;
		}
		false;
	}

	/**
	 * This class introduces for reuse the utility method {@link #isCanceled}
	 */
	protected static class MethodWrapperCancelable extends MethodWrapper {

		protected new(AbstractDeclarativeValidator instance, Method m) {
			super(instance, m)
		}

		/**
		 * Returns the {@link CancelIndicator} tracked by the current {@link State}.
		 * <p>
		 * Before calling a validation method, this method is used to check for pending cancellation requests.
		 * To keep the UI responsive, such request may be serviced by
		 * (a) skipping validation; or (b) throwing a {@code OperationCanceledException} or {@code OperationCanceledError}.
		 */
		protected def CancelIndicator getCancelIndicator(State state) {
			if (null !== state.context) {
				val cancelIndicator = state.context.get(CancelableDiagnostician.CANCEL_INDICATOR) as CancelIndicator;
				if (null !== cancelIndicator) {
					return cancelIndicator;
				}
			}
			return null;
		}
	}


	new() {
		this.messageAcceptor = new DelegatingValidationMessageAcceptor(this.messageAcceptor) {

			override acceptError(String message, EObject object, int offset, int length, String code,
				String... issueData) {
				val bugMessage = adjustedBug(message, object, Severity.ERROR);
				if (bugMessage !== null) {
					delegate.acceptWarning(bugMessage, object, offset, length, code, issueData)
				} else {
					delegate.acceptError(message, object, offset, length, code, issueData)
				}
			}

			override acceptError(String message, EObject object, EStructuralFeature feature, int index, String code,
				String... issueData) {
				val bugMessage = adjustedBug(message, object, Severity.ERROR);
				if (bugMessage !== null) {
					delegate.acceptWarning(bugMessage, object, feature, index, code, issueData)

				} else {
					delegate.acceptError(message, object, feature, index, code, issueData)
				}
			}

			private def String adjustedBug(String message, EObject source, Severity severity) {
				if (severity == Severity.ERROR) {
					val AnnotableElement annotableElement = EcoreUtil2.getContainerOfType(source, AnnotableElement);
					val idebugs = AnnotationDefinition.IDEBUG.getAllAnnotations(annotableElement);
					val annotation = idebugs.filter [ a |
						if (a.getArgs().size() == 2) {
							val String filterMessageText = a.getArgs().get(1).valueAsString
							if (filterMessageText.endsWith("…")) {
								return message.startsWith(filterMessageText.substring(0, filterMessageText.length() - 1));
							} else {
								return message.equals(filterMessageText);
							}
						}
						return false;
					].head;
					if (null !== annotation) {
						IDEBUGValidator.getUsedAnnotations(context).add(annotation);

						val String bugID = annotation.getArgs().get(0).valueAsString;
						return "IDEBUG-" + bugID + ": " + message;
					}
				}
				return null;
			}

		}
	}

	override protected List<EPackage> getEPackages() {
		val List<EPackage> result = super.EPackages;
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/n4js/utils/Validation"));
		return result;
	}
}
