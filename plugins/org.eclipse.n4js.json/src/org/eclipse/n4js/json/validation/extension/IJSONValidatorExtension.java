package org.eclipse.n4js.json.validation.extension;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.n4js.json.JSON.JSONDocument;

/**
 * An extension interface that allows to provide custom validation for {@link JSONDocument}s.
 * 
 * A registered {@link IJSONValidatorExtension} will be executed for all JSON resource in the workspace. In case a
 * validator extension only applies to JSON files that meet certain criteria, the validator must check those conditions
 * first.
 */
public interface IJSONValidatorExtension {
	/**
	 * Validate the given JSON document element and report issues to {@code diagnosticChain}.
	 * 
	 * @param document
	 *            The JSON document to validate.
	 * @param diagnosticChain
	 *            The diagnostic chain to report issues to.
	 */
	public void validateJSON(JSONDocument document, DiagnosticChain diagnosticChain);
}
