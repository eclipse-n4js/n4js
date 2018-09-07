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
import java.util.List
import java.util.Map
import java.util.MissingResourceException
import org.eclipse.xtend.lib.macro.Active
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.Visibility
import org.eclipse.xtext.diagnostics.Severity

/**
 * Active annotation which parses NLS properties files and generates helper methods in NLS message class to
 * create messages with correct number of parameters.
 *
 * The value of properties have to follow the following pattern:
 * <ul>
 * <li>comment in the first line, stating the description of all required wildcards
 * <li> issue ID: all in upper case, no whitespaces allowed - use underscore
 * 		instead, if sensible, use the encoded short cuts from above to indicate
 * 		place and domain of the issue followed by a few additional meaningful words
 * <li> default severity: the severity to use, if there is no other severity
 * 		provided by the issue code provider (see explanation above)
 * <li>separator: ";;;" is used to separate default severity and issue message
 * <li>the issue message with wildcards
 * </ul>
 *
 * For each key in the properties file, the annotation generates
 * <ul>
 * <li>static final String constant field with same name and same value
 * <li>method {@code getMessageFor[ISSUE_ID]} having Object parameters of  same count as of unique occurrences of wild cards (e.g. first parameter then
 *   maps to "{0}")
 * </ul>
 *
 * Additionally, a general helper method is generated:
 * <ul>
 * <li>{@code getDefaultSeverity(String)} returns the default severity for the given issue ID (= key in properties file)
 * </ul>
 *
 * Example:
 * <pre>
 * # 0: type or variable, 1: type name, 2: comma separated list of types - the sources of the imports
 * IMP_AMBIGUOUS=error;;;The {0} {1} is ambiguously imported from {2}.
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
 */
@Target(ElementType::TYPE)
@Active(typeof(NLSProcessor))
public annotation NLS {
	String propertyFileName
}

/** See annotation NLS */
public class NLSProcessor extends AbstractNLSProcessor {

	private static List<String> SEVERITIES = #["error", "warning", "info", "ignore"]

	override Class<?> getAnnotationType() {
		return NLS;
	}


	override void addMembers(
		MutableClassDeclaration annotatedClass, 
		AnnotationReference nlsAnnotation, 
		TransformationContext context,
		String propertyFileNameValue
	) {
		super.addMembers(annotatedClass, nlsAnnotation, context, propertyFileNameValue);
		addGetDefaultSeverityMethod(annotatedClass, nlsAnnotation, context)
	}
	
	override void addGetStringMethod(MutableClassDeclaration annotatedClass, AnnotationReference nlsAnnotation,
		extension TransformationContext context) {
		val methodName = "getString"
		checkForExistentMethod(annotatedClass, methodName, context, nlsAnnotation, 1)
		annotatedClass.addMethod(methodName) [
			visibility = Visibility.PRIVATE
			static = true
			returnType = string
			addParameter("key", string)
			body = '''
				try {
					«string» value = «annotatedClass.findDeclaredField(RESOURCE_BUNDLE_FIELD).simpleName».getString(key);
					«string»[] parts = value.split(";;;");
					return parts[1];
				} catch («MissingResourceException» e) {
					return '!' + key + '!';
				}
			'''
		]
	}


	def private void addGetDefaultSeverityMethod(MutableClassDeclaration annotatedClass,
		AnnotationReference nlsAnnotation, extension TransformationContext context) {
		val methodName = "getDefaultSeverity"
		checkForExistentMethod(annotatedClass, methodName, context, nlsAnnotation, 1)
		annotatedClass.addMethod(methodName) [
			visibility = Visibility.PUBLIC
			static = true
			returnType = Severity.newTypeReference
			addParameter("key", string)
			body = '''
				try {
					«string» value = «annotatedClass.findDeclaredField(RESOURCE_BUNDLE_FIELD).simpleName».getString(key);
					«string»[] parts = value.split(";;;");
					«string» defaultSeverity = parts[0];
					return «Severity».valueOf(defaultSeverity.toUpperCase());
				} catch («MissingResourceException» e) {
					return null;
				}
			'''
		]
	}

	override void addMethod(Map.Entry<Object, Object> entry, MutableClassDeclaration annotatedClass,
		AnnotationReference nlsAnnotation, extension TransformationContext context) {
		val value = entry.value as String
		val parts = value.split(";;;")
		if (parts.length != 2) {
			nlsAnnotation.addError(
				"Value for " + entry.key + " in properties file doesn't follow pattern 'defaultSeverity;;;message'")
			return
		}
		val defaultSeverity = parts.get(0)
		if (!SEVERITIES.contains(defaultSeverity)) {
			nlsAnnotation.addError(
				defaultSeverity + " is not a valid severity (which would be " + SEVERITIES.join(", ") + ").")
			return
		}
		val message = parts.get(1)
		val wildcardCount = message.getWildcardCount
		val params = if (wildcardCount > 0) (0 .. wildcardCount - 1).map["param" + it] else newArrayList
		val getMessageForMethodName = "getMessageFor" + entry.key
		checkForExistentMethod(annotatedClass, getMessageForMethodName, context, nlsAnnotation, params.size)
		annotatedClass.addMethod(getMessageForMethodName) [
			visibility = Visibility.PUBLIC
			static = true
			returnType = context.string
			params.forEach(param|addParameter(param, object))
			docComment = message
			body = '''return «nlsClass».bind(getString(«entry.key»), new Object [] { «params.join(", ")» });'''
		]
	}
	

	
}
