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
package org.eclipse.n4js.tester.tests;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.Injector;

/**
 * Annotation for creating a parent injector for test classes using the {@code @JUnitGuiceClassRunner} annotation. If a
 * single static method with {@link Injector} return value and with zero formal parameters is annotated with
 * {@code @WithParentInjector} then the return value of that method will be used as the parent injector of the given
 * modules.
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface WithParentInjector {
	//
}
