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
package org.eclipse.n4js.utils.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.eclipse.xtend.lib.macro.Active;

/**
 * Active annotation for generating property change support code.
 *
 * @see IgnorePropertyChangeEvents
 */
@Active(PropertyChangeSupportProcessor.class)
@Target(ElementType.TYPE)
public @interface PropertyChangeSupport {

	/**
	 * If this flag is set to {@code true} then each property change will be logged, otherwise no. {@code false} by
	 * default.
	 *
	 * @return {@code true} if the property change events has to be logged, otherwise {@code false}.
	 */
	boolean verbose() default false;

}
