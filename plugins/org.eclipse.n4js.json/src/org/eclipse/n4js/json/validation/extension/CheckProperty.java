/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.validation.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.n4js.json.JSON.JSONDocument;

/**
 * Method annotation to be used in combination with {@link AbstractJSONValidatorExtension}
 * in order to declaratively specify validation check methods that validate only certain
 * properties of a {@link JSONDocument}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CheckProperty {
	/** The key path in the JSON document that is checked by this method. */
	public String propertyPath();

	/** Specifies whether {@code propertyPath} is considered mandatory in a document. */
	public boolean mandatory() default false;
}
