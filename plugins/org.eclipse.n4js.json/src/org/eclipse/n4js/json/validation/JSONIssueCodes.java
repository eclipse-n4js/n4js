package org.eclipse.n4js.json.validation;

import static org.eclipse.xtext.diagnostics.Severity.ERROR;
import static org.eclipse.xtext.diagnostics.Severity.WARNING;

import org.eclipse.xtext.diagnostics.Severity;

/**
 * Enum contains all issues
 */
@SuppressWarnings("javadoc")
public enum JSONIssueCodes {

	/** 0: Property Name, 1: Line of original definition */
	JSON_DUPLICATE_KEY(WARNING, "Property %s duplicates property %s (line %d)."),

	/** No parameters */
	JSON_COMMENT_UNSUPPORTED(WARNING, "Comments are not a valid JSON construct."),

	/** No parameters */
	JSON_TRAILING_COMMAS_UNSUPPORTED(WARNING, "Trailing commas are not allowed in JSON."),

	/** No parameters */
	JSON_INVALID_DOUBLE_VALUE(ERROR, "Invalid decimal number value."),

	/** 0: expected JSON value type, 1: actual JSON value type, 2: location clause (e.g. for property 'name') */
	JSON_EXPECTED_DIFFERENT_VALUE_TYPE(ERROR, "Expected %s instead of %s %s."),

	/** 0: mandatory key name */
	JSON_MISSING_PROPERTY(ERROR, "Missing mandatory property %s."),

	/** 0: property name */
	JSON_EMPTY_STRING(ERROR, "Value for property '%s' must not be empty.")

	;

	public final Severity severity;
	private final String msgTemplate;

	JSONIssueCodes(Severity severity, String msgTemplate) {
		this.severity = severity;
		this.msgTemplate = msgTemplate;
	}

	public String getMessage(Object... values) {
		if (values == null) {
			return msgTemplate;
		}
		return msgTemplate.formatted(values);
	}
}
