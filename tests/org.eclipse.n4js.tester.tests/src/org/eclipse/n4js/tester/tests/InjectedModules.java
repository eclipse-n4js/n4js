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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.Module;

/**
 * Annotation for injecting {@link Module module} classes into a JUnit runner.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Inherited
@Documented
public @interface InjectedModules {

	/**
	 * An array of base {@link Module module} classes to be used for the injection. Can be empty but not {@code null}.
	 * These modules will be overridden with the {@link #overrides()}.
	 *
	 * @return an array of modules as the base modules.
	 */
	Class<? extends Module>[] baseModules();

	/**
	 * An array of overriding modules. Used to override bindings in the {@link #baseModules()}. Can be empty but not
	 * {@code null}.
	 *
	 * @return an array of modules that intended to override binding in the {@link #baseModules() base module}s.
	 */
	Class<? extends Module>[] overrides();

}
