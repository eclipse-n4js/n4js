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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;

import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.utils.validation.DelegatingValidationMessageAcceptor;
import org.eclipse.n4js.validation.validators.IDEBUGValidator;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.CancelableDiagnostician;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 */
@SuppressWarnings("javadoc")
public abstract class AbstractMessageAdjustingN4JSValidator extends AbstractN4JSValidator {

	public boolean createTypeError(Result result, EObject source) {
		if (result.isFailure()) {
			String msg = result.getCompiledFailureMessage();
			getMessageAcceptor().acceptError(msg, source, null,
					ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "org.eclipse.n4js.TypeErrorIssueCode");
			return true;
		}
		return false;
	}

	/**
	 * This class introduces for reuse the utility method {@link CancelIndicator#isCanceled}
	 */
	protected static class MethodWrapperCancelable extends MethodWrapper {

		protected MethodWrapperCancelable(AbstractDeclarativeValidator instance, Method m) {
			super(instance, m);
		}

		/**
		 * Returns the {@link CancelIndicator} tracked by the current
		 * {@link org.eclipse.xtext.validation.AbstractDeclarativeValidator.State}.
		 * <p>
		 * Before calling a validation method, this method is used to check for pending cancellation requests. To keep
		 * the UI responsive, such request may be serviced by (a) skipping validation; or (b) throwing a
		 * {@code OperationCanceledException} or {@code OperationCanceledError}.
		 */
		protected CancelIndicator getCancelIndicator(State state) {
			if (null != state.context) {
				CancelIndicator cancelIndicator = (CancelIndicator) state.context
						.get(CancelableDiagnostician.CANCEL_INDICATOR);
				if (null != cancelIndicator) {
					return cancelIndicator;
				}
			}
			return null;
		}
	}

	public AbstractMessageAdjustingN4JSValidator() {
		this.setMessageAcceptor(new DelegatingValidationMessageAcceptor(this.getMessageAcceptor()) {

			@Override
			public void acceptError(String message, EObject object, int offset, int length, String code,
					String... issueData) {
				String bugMessage = adjustedBug(message, object, Severity.ERROR);
				if (bugMessage != null) {
					delegate.acceptWarning(bugMessage, object, offset, length, code, issueData);
				} else {
					delegate.acceptError(message, object, offset, length, code, issueData);
				}
			}

			@Override
			public void acceptError(String message, EObject object, EStructuralFeature feature, int index, String code,
					String... issueData) {
				String bugMessage = adjustedBug(message, object, Severity.ERROR);
				if (bugMessage != null) {
					delegate.acceptWarning(bugMessage, object, feature, index, code, issueData);

				} else {
					delegate.acceptError(message, object, feature, index, code, issueData);
				}
			}

			private String adjustedBug(String message, EObject source, Severity severity) {
				if (severity == Severity.ERROR) {
					AnnotableElement annotableElement = EcoreUtil2.getContainerOfType(source, AnnotableElement.class);
					Iterable<Annotation> idebugs = AnnotationDefinition.IDEBUG.getAllAnnotations(annotableElement);
					Annotation annotation = head(filter(idebugs, a -> {
						if (a.getArgs().size() == 2) {
							String filterMessageText = a.getArgs().get(1).getValueAsString();
							if (filterMessageText.endsWith("…")) {
								return message
										.startsWith(filterMessageText.substring(0, filterMessageText.length() - 1));
							} else {
								return message.equals(filterMessageText);
							}
						}
						return false;
					}));
					if (null != annotation) {
						IDEBUGValidator.getUsedAnnotations(getContext()).add(annotation);

						String bugID = annotation.getArgs().get(0).getValueAsString();
						return "IDEBUG-" + bugID + ": " + message;
					}
				}
				return null;
			}

		});
	}

	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = super.getEPackages();
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/n4js/utils/Validation"));
		return result;
	}
}
