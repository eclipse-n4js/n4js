package org.eclipse.n4js.ide.server.codeActions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation Container for @{@link Fix} annotations.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Fixes {

	/**
	 * All the fixes.
	 */
	Fix[] value();

}