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

/**
 * Annotation for quick-fix methods.
 * <p>
 * <b>Important:</b><br>
 * Make sure that annotated methods:
 * <ul>
 * <li/>are public
 * <li/>have exactly two arguments, first of type {@link QuickfixContext} and second of type {@link CodeActionAcceptor}
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Repeatable(Fixes.class)
public @interface Fix {
	/** Issue code of issue that is fixed by the annotated method */
	String value();

	/** Returns true if the implemented fix is a multi fix. */
	boolean multiFix() default true;
}
