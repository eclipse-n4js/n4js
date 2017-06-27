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
package org.eclipse.n4js.utils.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * Proxy-like base class for message acceptors, concrete subclasses can modify the message (text or severity etc.). A
 * concrete subclass of this delegate is usually set in the constructor of a {@link AbstractDeclarativeValidator} (which
 * is a {@link ValidationMessageAcceptor} itself, like (Xtend syntax) that:
 *
 * <pre>
 * new() {
 * 	this.messageAcceptor = new DelegatingValidationMessageAcceptor(this.messageAcceptor) {
 * 	... // override accept methods which modify the message
 * 	}
 * }
 * </pre>
 */
public abstract class DelegatingValidationMessageAcceptor implements ValidationMessageAcceptor {

	/**
	 * The original message acceptor.
	 */
	final protected ValidationMessageAcceptor delegate;

	/**
	 * Creates this proxy and sets delegate (original) message acceptor.
	 */
	public DelegatingValidationMessageAcceptor(ValidationMessageAcceptor delegate) {
		this.delegate = delegate;
	}

	@Override
	public void acceptError(String message, EObject object, EStructuralFeature feature, int index, String code,
			String... issueData) {
		delegate.acceptError(message, object, feature, index, code, issueData);
	}

	@Override
	public void acceptError(String message, EObject object, int offset, int length, String code, String... issueData) {
		delegate.acceptError(message, object, offset, length, code, issueData);
	}

	@Override
	public void acceptWarning(String message, EObject object, EStructuralFeature feature, int index, String code,
			String... issueData) {
		delegate.acceptWarning(message, object, feature, index, code, issueData);
	}

	@Override
	public void acceptWarning(String message, EObject object, int offset, int length, String code, String... issueData) {
		delegate.acceptWarning(message, object, offset, length, code, issueData);
	}

	@Override
	public void acceptInfo(String message, EObject object, EStructuralFeature feature, int index, String code,
			String... issueData) {
		delegate.acceptInfo(message, object, feature, index, code, issueData);
	}

	@Override
	public void acceptInfo(String message, EObject object, int offset, int length, String code, String... issueData) {
		delegate.acceptInfo(message, object, offset, length, code, issueData);
	}

}
