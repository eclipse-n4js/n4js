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
package org.eclipse.n4js.utils.beans

import com.google.common.base.CaseFormat
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.lang.annotation.Annotation
import java.lang.annotation.Repeatable
import java.util.ArrayList
import java.util.Collection
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.AccessorsProcessor
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.AnnotationTarget
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.Visibility

/**
 * Class transformation processor for generating {@link PropertyChangeSupport} for all
 * non-final non-static fields within a class annotated with {@link org.eclipse.n4js.utils.beans.PropertyChangeSupport} annotation.
 */
class PropertyChangeSupportProcessor extends AbstractClassProcessor {

	override doTransform(MutableClassDeclaration clazz, extension TransformationContext context) {

		val annotations = clazz.annotations.filter[annotationTypeDeclaration.qualifiedName == org.eclipse.n4js.utils.beans.PropertyChangeSupport.name];

		if (annotations.nullOrEmpty) {
			return;
		}

		if (annotations.size > 1) {
			clazz.addError(
				'''Duplicate annotation of non-repeatable type @«org.eclipse.n4js.utils.beans.PropertyChangeSupport.simpleName».
						Only annotation types marked @«Repeatable.simpleName» can be used multiple times at one target.''')
			return;
		}

		val verbose = annotations.head.getBooleanValue('verbose');
		if (verbose) {
				clazz.addField('PROPERTY_CHANGE_LOGGER') [
					visibility = Visibility.PRIVATE;
					static = true;
					final = true;
					type = Logger.newTypeReference;
					it.initializer = '''«Logger».getLogger(«clazz».class)''';
				];
			}


		val fields = clazz.declaredFields.filter[
			null !== type
			&& !final
			&& !static
			&& !hasAnnotation(IgnorePropertyChangeEvents)
		];

		val inferredFields = fields.filter[type.inferred];
		if (!inferredFields.nullOrEmpty) {
			inferredFields.forEach[addError('''The type of field must be explicitly declared to enable the property change support.''')];
			return;
		}

		if (!hasSuperPropertyChangeSupportGetter(clazz, context)) {
			clazz.addField('_propertyChangeSupport') [
				visibility = Visibility.PRIVATE;
				static = false;
				final = true;
				type = PropertyChangeSupport.newTypeReference;
				initializer = '''new «PropertyChangeSupport»(this)''';
			];

			clazz.addMethod('internalGetPropertyChangeSupport') [
				visibility = Visibility.PROTECTED;
				static = false;
				final = true;
				returnType = PropertyChangeSupport.newTypeReference;
				body = '''
					return this._propertyChangeSupport;
				'''
			];
		}

		if (!hasSuperAddPropertyChangeListener(clazz, context)) {
			clazz.addMethod('addPropertyChangeListener') [
				returnType = primitiveVoid
				val typeRef = context.newTypeReference(PropertyChangeListener)
				val param = addParameter('listener', typeRef);
				visibility = Visibility.PUBLIC;
				static = false;
				final = true;
				body = '''
				internalGetPropertyChangeSupport().addPropertyChangeListener(«param.simpleName»);
				'''
			];
		}

		if (!hasSuperRemovePropertyChangeListener(clazz, context)) {
			clazz.addMethod('removePropertyChangeListener') [
				returnType = primitiveVoid
				val typeRef = context.newTypeReference(PropertyChangeListener)
				val param = addParameter('listener', typeRef);
				visibility = Visibility.PUBLIC;
				static = false;
				final = true;
				body = '''
				internalGetPropertyChangeSupport().removePropertyChangeListener(«param.simpleName»);
				'''
			];
		}

		fields.forEach[ field |

			val propertyName = '''«CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, field.simpleName)»_PROPERTY''';
			val fieldTypeRef = if (null === field.type) object else field.type;

			// Generate constant property for the field.
			clazz.addField(propertyName) [
				visibility = Visibility.PUBLIC;
				static = true;
				final = true;
				type = string;
				constantValueAsString = field.simpleName;
			];

			// Generate common getter.
			val accessorUtil = new AccessorsProcessor.Util(context);
			if (accessorUtil.shouldAddGetter(field)) {
				accessorUtil.addGetter(field, Visibility.PUBLIC);
			}

			// Generate setter that invokes the property change support.
			clazz.addMethod(accessorUtil.getSetterName(field)) [
				returnType = primitiveVoid
				val param = addParameter(field.simpleName, fieldTypeRef);
				val oldValue = if (context.newTypeReference(Collection).isAssignableFrom(fieldTypeRef)) {
					'''new «ArrayList.name»(this.«field.simpleName»)''';
				} else {
					'''this.«field.simpleName»''';
				}
				body = '''
				internalGetPropertyChangeSupport().firePropertyChange(«propertyName», «oldValue», this.«field.simpleName» = «param.simpleName»);
				''';
				it.visibility = Visibility.PUBLIC;
			];

			if (context.newTypeReference(Collection).isAssignableFrom(fieldTypeRef)) {

				clazz.addMethod('''add«field.simpleName.toFirstUpper»''') [
					returnType = primitiveVoid
					val typeRef = fieldTypeRef.actualTypeArguments.head?: object;
					val param = addParameter('''element«field.simpleName.toFirstUpper»''', typeRef);
					body = '''
						internalGetPropertyChangeSupport().firePropertyChange(
							«propertyName»,
							new «ArrayList.name»(this.«field.simpleName»),
							this.«field.simpleName».add(«param.simpleName») ? this.«field.simpleName» : this.«field.simpleName»);
					''';
					it.visibility = Visibility.PUBLIC;
				];

				clazz.addMethod('''remove«field.simpleName.toFirstUpper»''') [
					returnType = primitiveVoid
					val typeRef = fieldTypeRef.actualTypeArguments.head?: object;
					val param = addParameter('''element«field.simpleName.toFirstUpper»''', typeRef);
					body = '''
						internalGetPropertyChangeSupport().firePropertyChange(
							«propertyName»,
							new «ArrayList.name»(this.«field.simpleName»),
							this.«field.simpleName».remove(«param.simpleName») ? this.«field.simpleName» : this.«field.simpleName»);
					''';
					it.visibility = Visibility.PUBLIC;
				];

			}

		]

	}

	private def hasPropertyChangeSupportGetter(ClassDeclaration it, TransformationContext context) {
		declaredMethods.exists [
			simpleName == 'internalGetPropertyChangeSupport'
				&& returnType == context.newTypeReference(PropertyChangeSupport)
				&& parameters.size == 0
				&& (visibility === Visibility.PROTECTED || visibility === Visibility.PUBLIC)
		];
	}

	private def hasSuperPropertyChangeSupportGetter(ClassDeclaration cls, TransformationContext context) {
		var superClass = (cls.extendedClass.type as ClassDeclaration)
		while (null !== superClass) {
			if (superClass.hasPropertyChangeSupportGetter(context)) {
				return true;
			}
			superClass = superClass.extendedClass?.type as ClassDeclaration;
		}
		return false;
	}

	private def hasAnnotation(AnnotationTarget it, Class<? extends Annotation> annotation) {
		annotations.exists[annotationTypeDeclaration.qualifiedName == annotation.name];
	}

	private def hasAddPropertyChangeListener(ClassDeclaration it, TransformationContext context) {
		declaredMethods.exists [
			simpleName == 'addPropertyChangeListener'
				&& returnType == context.primitiveVoid
				&& parameters.size == 1
				&& parameters.head.type == context.newTypeReference(PropertyChangeListener)
		];
	}

	private def boolean hasSuperAddPropertyChangeListener(ClassDeclaration cls, TransformationContext context) {
		var superClass = (cls.extendedClass.type as ClassDeclaration)
		while (null !== superClass) {
			if (superClass.hasAddPropertyChangeListener(context)) {
				return true;
			}
			superClass = superClass.extendedClass?.type as ClassDeclaration;
		}
		return false;
	}

	private def hasRemovePropertyChangeListener(ClassDeclaration it, TransformationContext context) {
		declaredMethods.exists [
			simpleName == 'removePropertyChangeListener'
				&& returnType == context.primitiveVoid
				&& parameters.size == 1
				&& parameters.head.type == context.newTypeReference(PropertyChangeListener)
		];
	}

	private def boolean hasSuperRemovePropertyChangeListener(ClassDeclaration cls, TransformationContext context) {
		var superClass = (cls.extendedClass.type as ClassDeclaration)
		while (null !== superClass) {
			if (superClass.hasRemovePropertyChangeListener(context)) {
				return true;
			}
			superClass = superClass.extendedClass?.type as ClassDeclaration;
		}
		return false;
	}


}
