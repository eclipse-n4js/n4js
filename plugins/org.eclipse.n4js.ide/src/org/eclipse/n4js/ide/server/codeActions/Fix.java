/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.codeActions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.n4js.validation.IssueCodes;

/**
 * Annotation for quick-fix methods.
 * <p>
 * <b>Important:</b><br>
 * Make sure that annotated methods:
 * <ul>
 * <li/>are public
 * <li/>have exactly two arguments, first of type {@link QuickfixContext} and second of type {@link ICodeActionAcceptor}
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Repeatable(Fixes.class)
public @interface Fix {
	/**
	 * The issue code that is about the be resolved. The annotated method will potentially produce a fix for this code.
	 */
	IssueCodes value();

	/**
	 * Only evaluated if {@link #value()} equals to {@link IssueCodes#INVALID_ISSUE_CODE}. Will be used instead of
	 * {@link #value()}.
	 */
	String valueName() default "";

	/** Returns true if the implemented fix is a multi fix. */
	boolean multiFix() default true;
}
