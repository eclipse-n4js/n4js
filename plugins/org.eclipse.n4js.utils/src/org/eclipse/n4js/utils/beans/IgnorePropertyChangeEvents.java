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
 * Annotation to exclude fields from property change support.
 *
 * @see PropertyChangeSupport
 */
@Target(ElementType.FIELD)
@Active(IgnorePropertyChangeEventsProcessor.class)
public @interface IgnorePropertyChangeEvents {
	// just a marker annotation to enable validation
}
