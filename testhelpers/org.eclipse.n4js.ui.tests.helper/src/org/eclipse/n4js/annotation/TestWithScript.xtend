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
package org.eclipse.n4js.annotation

import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.Active
import org.eclipse.xtext.util.ITextRegion

/**
 * Active annotation for injecting the {@code script} given as a string
 * and a {@link ITextRegion} instance with {@code selection} name to the method
 * using with the current annotation. The text region instance is calculated from
 * the optional {@link #selectedText} value. If the script does not contain
 * the selected text then the annotated method will report a compile error.
 * If the script contains the selected text multiple times one can define the proper
 * selection with the 0 based {@link #occurrenceIndex} value. By default is 0 which
 * means that the text region will be calculated by the first occurrence of the
 * selected text in the script.
 */
@Target(METHOD)
@Active(typeof(TestWithScriptProcessor))
annotation TestWithScript {
	/** The script to be injected. */
	String script;
	/** The selected text to calculate the {@code selection} from. */
	String selectedText = '';
	/** The 0 based occurrence index of the selected text. Must be a non negative integer.*/
	int occurrenceIndex = 0;
}
