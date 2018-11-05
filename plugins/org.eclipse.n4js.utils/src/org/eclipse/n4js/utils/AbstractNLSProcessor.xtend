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

import java.io.IOException
import java.io.InputStream
import java.lang.reflect.InvocationTargetException
import java.util.Map
import java.util.MissingResourceException
import java.util.Properties
import java.util.ResourceBundle
import java.util.regex.Pattern
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.Visibility
import org.eclipse.xtext.xbase.lib.Functions.Function0
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions

/**
 * Base class for active annotations which parses NLS properties files and 
 * generates helper methods in NLS message class to create messages with correct number of parameters.
 * 
 * For each key in the properties file, the annotation generates
 * <ul>
 * <li>static final String constant field with same name and same value
 * <li>a get message method having Object parameters of  same count as of unique occurrences of wild cards (e.g. first parameter then
 *   maps to "{0}")
 * </ul>
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

/** See annotation NLS */
public abstract class AbstractNLSProcessor extends AbstractClassProcessor {
	protected extension ReflectExtensions reflExt = new ReflectExtensions

	protected static final String BUNDLE_NAME_FIELD = "BUNDLE_NAME"
	protected static final String RESOURCE_BUNDLE_FIELD = "RESOURCE_BUNDLE"
	protected static final String nlsClass = "org.eclipse.osgi.util.NLS"
	
	abstract protected def Class<?> getAnnotationType();
	
	def String getAnnotationName() {
		return annotationType.simpleName;
	}
	
	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		val nlsAnnotation = annotatedClass.getNLSAnnotation(context)
		if (nlsAnnotation===null) {
			throw new NullPointerException("Unable to retrieve the annotation")
		}
		if (findTypeGlobally(nlsClass) === null) {
			nlsAnnotation.addError(nlsClass + " isn't on the classpath.")
		}
		val propertyFileNameValue = nlsAnnotation.getNLSAnnotationPropertyValue(context)
		val propertiesFileInputStream = annotatedClass.getPropertiesFile(context, propertyFileNameValue, nlsAnnotation)
		val properties = propertiesFileInputStream.loadPropertiesFile(context, nlsAnnotation)
		
		addMembers(annotatedClass, nlsAnnotation, context, propertyFileNameValue);
		
		properties.entrySet.sortBy[String.valueOf(key)].forEach [
			addField(annotatedClass, nlsAnnotation, context)
			addMethod(annotatedClass, nlsAnnotation, context)
		]
	}
	
	/**
	 * May be overridden by sub classes to add or remove members which are to be created.
	 */
	def protected void addMembers(
		MutableClassDeclaration annotatedClass, 
		AnnotationReference nlsAnnotation, 
		TransformationContext context,
		String propertyFileNameValue
	) {
		addBundleNameField(annotatedClass, nlsAnnotation, context, propertyFileNameValue)
		addResourceBundleField(annotatedClass, nlsAnnotation, context)
		addStaticBlock(annotatedClass, nlsAnnotation, context)
		addGetStringMethod(annotatedClass, nlsAnnotation, context)
	}
	
	

	def protected void addStaticBlock(MutableClassDeclaration annotatedClass, AnnotationReference nlsAnnotation,
		extension TransformationContext context) {
		val fieldName = "INITIALIZER";
		checkForExistentField(annotatedClass, fieldName, context, nlsAnnotation)
		annotatedClass.addField(fieldName) [
			visibility = Visibility.PRIVATE
			static = true
			final = true
			type = string
			initializer = '''
				new «Function0»<«String»>() {
				    public «string» apply() {
				      «nlsClass».initializeMessages(«annotatedClass.findDeclaredField(
					BUNDLE_NAME_FIELD).simpleName», «annotatedClass.newTypeReference».class);
				      return "";
				    }
				  }.apply();
			'''
		]
	}

	def protected void addBundleNameField(MutableClassDeclaration annotatedClass, AnnotationReference nlsAnnotation,
		extension TransformationContext context, String propertyFileName) {
		checkForExistentField(annotatedClass, BUNDLE_NAME_FIELD, context, nlsAnnotation)
		annotatedClass.addField(BUNDLE_NAME_FIELD) [
			visibility = Visibility.PRIVATE
			static = true
			final = true
			type = string
			initializer = '''«annotatedClass.newTypeReference».class.getPackage().getName() + ".«propertyFileName.replace(".properties", "")»"'''
		]
	}

	def protected void addResourceBundleField(MutableClassDeclaration annotatedClass,
		AnnotationReference nlsAnnotation, extension TransformationContext context) {
		checkForExistentField(annotatedClass, RESOURCE_BUNDLE_FIELD, context, nlsAnnotation)
		annotatedClass.addField(RESOURCE_BUNDLE_FIELD) [
			visibility = Visibility.PRIVATE
			static = true
			final = true
			type = ResourceBundle.newTypeReference
			initializer = '''«ResourceBundle».getBundle(«annotatedClass.findDeclaredField(BUNDLE_NAME_FIELD).simpleName»)'''
		]
	}

	def protected void addGetStringMethod(MutableClassDeclaration annotatedClass, AnnotationReference nlsAnnotation,
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
					return value;
				} catch («MissingResourceException» e) {
					return '!' + key + '!';
				}
			'''
		]
	}

	
	def protected void addField(Map.Entry<Object, Object> entry, MutableClassDeclaration annotatedClass,
		AnnotationReference nlsAnnotation, extension TransformationContext context) {
		val fieldName = entry.key as String
		checkForExistentField(annotatedClass, fieldName, context, nlsAnnotation)
		annotatedClass.addField(fieldName) [
			visibility = Visibility.PUBLIC
			static = true
			final = true
			type = string
			initializer = '''"«entry.key»"'''
			// remove when https://bugs.eclipse.org/bugs/show_bug.cgi?id=444333 is fixed
			try {
				val delegate = it.invoke("getDelegate")
				delegate.invoke("setConstant", true)
			} catch (NoSuchFieldException exc) {
				throw new RuntimeException(exc)
			} catch (IllegalAccessException exc) {
				throw new RuntimeException(exc)
			} catch (NoSuchMethodException exc) {
				throw new RuntimeException(exc)
			} catch (InvocationTargetException exc) {
				throw new RuntimeException(exc)
			}
		]
	}

	def protected void checkForExistentField(MutableClassDeclaration annotatedClass, String fieldName,
		extension TransformationContext context, AnnotationReference nlsAnnotation) {
		if (annotatedClass.findDeclaredField(fieldName) !== null) {
			nlsAnnotation.addError("Field " + fieldName + " already present in class.")
		}
	}

	def protected void addMethod(Map.Entry<Object, Object> entry, MutableClassDeclaration annotatedClass,
		AnnotationReference nlsAnnotation, extension TransformationContext context) {
		val message = entry.value as String
		val wildcardCount = message.getWildcardCount
		val params = if (wildcardCount > 0) (0 .. wildcardCount - 1).map["param" + it] else newArrayList
		val msgMethodName = "msg" + entry.key
		checkForExistentMethod(annotatedClass, msgMethodName, context, nlsAnnotation, params.size)
		annotatedClass.addMethod(msgMethodName) [
			visibility = Visibility.PUBLIC
			static = true
			returnType = context.string
			params.forEach(param|addParameter(param, object))
			docComment = message
			body = '''return «nlsClass».bind(getString(«entry.key»), new Object [] { «params.join(", ")» });'''
		]
	}
	
	def protected void checkForExistentMethod(MutableClassDeclaration annotatedClass, String methodName,
		extension TransformationContext context, AnnotationReference nlsAnnotation, int parameterListSize) {
		val existentMethod = annotatedClass.findDeclaredMethod(methodName)
		if (existentMethod !== null) {
			if (existentMethod.parameters.size == parameterListSize) {
				nlsAnnotation.addError("Method " + methodName + "/" + parameterListSize + " already present in class.")
			}
		}
	}

	def protected getWildcardCount(String unboundMessage) {
		val pattern = Pattern.compile("\\{\\d*\\}")
		val matcher = pattern.matcher(unboundMessage);
		val matches = <String>newHashSet
		while (matcher.find) {
			val matchResult = matcher.toMatchResult
			matches.add(matchResult.group())
		}
		matches.size
	}

	def private getNLSAnnotation(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		annotatedClass.findAnnotation(annotationType.newTypeReference.type)
	}
	
	

	def private getNLSAnnotationPropertyValue(AnnotationReference nlsAnnotation,
		extension TransformationContext context) {
		val value = nlsAnnotation.getValue('propertyFileName') as String
		if (value.nullOrEmpty) {
			nlsAnnotation.addError(getAnnotationName() + " requires non empty propertyFileName property value.")
		}
		value
	}
	
	def private getPropertiesFile(MutableClassDeclaration annotatedClass, extension TransformationContext context,
		String propertyFileName, AnnotationReference nlsAnnotation) {
		val folder = annotatedClass.compilationUnit.filePath?.parent
		if (folder === null || !folder.exists) {
			nlsAnnotation.addError("Cannot find folder for class " + annotatedClass.qualifiedName + ": " + folder)
			throw new IllegalArgumentException(
				"Cannot find folder for class " + annotatedClass.qualifiedName + ": " + folder)
		}
		val propertiesFilePath = folder.append(
			propertyFileName + (if (!propertyFileName.endsWith(".properties")) ".properties" else ""))
		if (!propertiesFilePath.exists) {
			nlsAnnotation.addError(propertiesFilePath.lastSegment + " doesn't exist.")
		}
		propertiesFilePath.getContentsAsStream
	}

	def private loadPropertiesFile(InputStream propertiesFile, extension TransformationContext transformationContext,
		AnnotationReference nlsAnnotation) {
		val properties = new Properties
		try {
			properties.load(propertiesFile)
		} catch (IOException ioe) {
			nlsAnnotation.addError("Cannot load properties file: " + ioe.message)
		}
		properties
	}
}
