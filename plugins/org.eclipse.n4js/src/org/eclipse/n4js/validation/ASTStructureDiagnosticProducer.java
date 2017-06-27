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

import org.eclipse.xtext.diagnostics.AbstractDiagnosticProducer;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.linking.impl.XtextLinkingDiagnostic;

/**
 * Produces diagnostics for bogusly nested AST elements, e.g. function declarations in blocks.
 *
 * We reuse {@link XtextLinkingDiagnostic linking diagnostics} since they allow to use a custom issue code anyway.
 */
public class ASTStructureDiagnosticProducer extends AbstractDiagnosticProducer {

	ASTStructureDiagnosticProducer(IDiagnosticConsumer consumer) {
		super(consumer);
	}

	@Override
	protected Diagnostic createDiagnostic(DiagnosticMessage message) {
		return new XtextLinkingDiagnostic(getNode(), message.getMessage(), message.getIssueCode(),
				message.getIssueData());
	}

}
