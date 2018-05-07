package org.eclipse.n4js.json.validation;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * A validator that is only applied if the validated resource
 * fulfills the requirements given by {@link #isApplicable(Resource)}.
 */
public interface ResourceDependentValidator {
	/**
	 * Returns {@code true} if and only if this validator
	 * can be applied to elements in the given {@link Resource}.
	 */
	boolean isApplicable(Resource resource);
}
