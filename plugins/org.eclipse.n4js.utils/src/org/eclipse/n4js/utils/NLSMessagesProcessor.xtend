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
package org.eclipse.n4js.utils

import java.lang.annotation.ElementType
import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.Active

/**
 * Active annotation which parses NLS message properties files and generates helper methods in NLS message class to
 * create messages with correct number of parameters.
 *
 * The value of properties have to follow the following pattern:
 * <ul>
 * <li>comment in the first line, stating the description of all required wildcards
 * <li> issue ID: all in upper case, no whitespaces allowed - use underscore
 * 		instead, if sensible, use the encoded short cuts from above to indicate
 * 		place and domain of the issue followed by a few additional meaningful words
 * <li>the issue message with wildcards
 * </ul>
 *
 * For each key in the properties file, the annotation generates
 * <ul>
 * <li>static final String constant field with same name and same value
 * <li>method {@code msg[ISSUE_ID]} having Object parameters of  same count as of unique occurrences of wild cards (e.g. first parameter then
 *   maps to "{0}")
 * </ul>
 *
 * Example:
 * <pre>
 * # 0: type or variable, 1: type name, 2: comma separated list of types - the sources of the imports
 * IMP_AMBIGUOUS=The {0} {1} is ambiguously imported from {2}.
 * </pre>
 *
 * From that, the following members are created in the NLS class:
 * <pre>
 * public final static String IMP_AMBIGUOUS = "IMP_AMBIGUOUS";
 *
 * public static String getMessageIMP_AMBIGUOUS(final Object param0, final Object param1, final Object param2) {
 *   return org.eclipse.osgi.util.NLS.bind(getString(IMP_AMBIGUOUS), new Object [] { param0, param1, param2 });
 * }
 * </pre>
 *
 * Additional usage notes:
 * <ul>
 * <li>To apply changes from the properties file to the xtend NLS class you have to
 * make the xtend class dirty (resp. make a fake change). If there are mistakes
 * in the properties file (e.g. missing severity in value), the NLS annotation
 * will get an error marker pointing you to the cause of the problem
 * <li>With CTRL + left mouse click you can navigate from a message.properties entry to one of its current usages.
 * <li>The message properties are sorted alphabetically, in order to simplify finding of entries, and to
 * avoid problems of differently sorted sets due to different Java versions.</p>
 * </ul>
 * 
 * @see NLSProcessor
 */
@Target(ElementType::TYPE)
@Active(typeof(NLSMessagesProcessor))
public annotation NLSMessages {
	String propertyFileName
}

/** See annotation NLSMessages */
public class NLSMessagesProcessor extends AbstractNLSProcessor {
	
	override Class<?> getAnnotationType() {
		return NLSMessages;
	}
}
