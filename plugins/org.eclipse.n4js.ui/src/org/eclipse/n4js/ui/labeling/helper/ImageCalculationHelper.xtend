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
package org.eclipse.n4js.ui.labeling.helper

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.jface.viewers.IDecoration
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnumLiteral
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ui.labeling.EObjectWithContext
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider
import org.eclipse.n4js.ui.typesearch.TypeSearchKind
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.ui.label.AbstractLabelProvider

/**
 * This helper class serves as replacement for the polymorphic dispatch done
 * by {@link AbstractLabelProvider} in favor of
 * Xtend dispatch methods. Here the dispatch of icons to be shown e.g. in
 * the outline view is done. It is called in {@link N4JSLabelProvider#doGetImage}.
 * <br /><br />
 * General pattern is to delegate from an AST element to the types elmenent
 * as in the types model the required information to calculate the decorated
 * image is better provided.
 * <br /><br />
 * See /org.eclipse.n4js.ui/icons/icons_origin.txt for the origins of the
 * icons used to create the images.
 * <br /><br />
 * In some cases the image file provided can be used as it is but in most cases
 * some additional image decorators have to be added to visualize properties
 * of the represented element, e.g. accessibility, static/non-static, final,
 * constant, abstract, constructor. Some images like for getter/setter are
 * created using decorators as no specific image file was available. For classes
 * and interfaces there were specific images available when they are declared
 * public or private for roles this is done with decorators.
 * <br /><br />
 * For all unexpected elements a default image is shown.
 * <br /><br />
 * Decorators can be placed top left, top right, bottom right, bottom left.
 * Its also possible to add multiple decorators in one corner. This is done by
 * using {@code N4JSDecoratorRow}. Actually the creation of
 * image descriptions is completely layed out in {@link ImageDescriptionHelper}.
 * The selection of image files used as main images is done in
 * {@link ImageFileNameCalculationHelper}. {@link N4JSImageDescriptionLibrary}
 * holds the prepared image descriptions to be used as decorators.
 * <br /><br />
 * In the bottom left corner an error or warning decorator is added if the element
 * represented has corresponding validation issues.
 */
class ImageCalculationHelper {
	@Inject
	private extension ImageDescriptionHelper imageDescriptionHelper

	@Inject
	private extension ImageFileNameCalculationHelper imageFileNameCalculationHelper

	private extension N4JSImageDescriptionLibrary imageDescriptionLibrary

	def void setLabelProvider(N4JSLabelProvider provider) {
		this.imageDescriptionHelper.setLabelProvider(provider)
		this.imageDescriptionLibrary = imageDescriptionHelper.getImageDescriptionLibrary
	}

	/* dispatchDoGetImage AST -> delegates to types model, as information is easier to retrieve there */

	/**
	 * Should not happen as this will lead to consequential errors, still added here to make helper more robust.
	 */
	def dispatch ImageDescriptor dispatchDoGetImage(Void _null) {
		return null;
	}
	
	def dispatch ImageDescriptor dispatchDoGetImage(EObjectWithContext eObjectWithContext) {
		return dispatchDoGetImage(eObjectWithContext.obj)
	}

	def dispatch ImageDescriptor dispatchDoGetImage(N4ClassifierDeclaration n4ClassifierDeclaration) {
		return dispatchDoGetImage(n4ClassifierDeclaration.definedType)
	}

	def dispatch ImageDescriptor dispatchDoGetImage(N4MemberDeclaration n4MemberDeclaration) {
		return dispatchDoGetImage(n4MemberDeclaration.definedTypeElement)
	}

	def dispatch ImageDescriptor dispatchDoGetImage(N4GetterDeclaration getter) {
		return dispatchDoGetImage(getter.definedGetter)
	}

	def dispatch ImageDescriptor dispatchDoGetImage(N4SetterDeclaration setter) {
		return dispatchDoGetImage(setter.definedSetter)
	}

	def dispatch ImageDescriptor dispatchDoGetImage(FunctionDeclaration functionDeclaration) {
		return dispatchDoGetImage(functionDeclaration.definedType)
	}

	def dispatch ImageDescriptor dispatchDoGetImage(ExportedVariableDeclaration variableDeclaration) {
		return dispatchDoGetImage(variableDeclaration.definedVariable)
	}

	// uses icon for a collection of variables and adds visibility decorators (at bottom right) and a
	// constant decorator (top right) if they are consts
	def dispatch ImageDescriptor dispatchDoGetImage(ExportedVariableStatement vs) {
		val main = createValidationAwareImageDescriptor(vs, getImageFileName(vs))
		val firstDecl = vs.varDecl?.head
		if(firstDecl !== null && (firstDecl instanceof ExportedVariableDeclaration) && (firstDecl as ExportedVariableDeclaration).definedVariable !== null) {
			val firstDefinedVariable = (firstDecl as ExportedVariableDeclaration).definedVariable
			val newMain = if(firstDefinedVariable.const) {
				val decorator = createConstImageDecorator
				createDecorationOverlayIcon(main, decorator, IDecoration.TOP_RIGHT)
			} else {
				main
			}
			return addAccessibiltyImageDecorator(newMain, firstDefinedVariable.typeAccessModifier)
		}
		return main
	}

	def dispatch ImageDescriptor dispatchDoGetImage(N4EnumLiteral n4EnumLiteral) {
		return dispatchDoGetImage(n4EnumLiteral.definedLiteral)
	}

	/* dispatchDoGetImage types model */

	// uses icon for a class and adds abstract and final decorator if necessary at top right
	// and validation issues at bottom left
	def dispatch ImageDescriptor dispatchDoGetImage(TClass tClass) {
		val main = createValidationAwareImageDescriptor(tClass, getImageFileName(tClass))
		// an abstract class cannot be set to final (with annotation @Final)
		if(tClass.abstract) {
			return createDecorationOverlayIcon(main, createAbstractImageDecorator, IDecoration.TOP_RIGHT)
		}
		if(tClass.final) {
			return createDecorationOverlayIcon(main, createFinalImageDecorator, IDecoration.TOP_RIGHT)
		}
		return main
	}

	/**
	 * returns either the original image (provided no decorators were given) or an ImageDescriptor to display the decorators on top-right.
	 */
	private def ImageDescriptor decorated(ImageDescriptor main, List<ImageDescriptor> decorators) {
		if (decorators.isEmpty) {
			return main
		}
		if(decorators.size == 1) {
			return createDecorationOverlayIcon(main, decorators.head, IDecoration.TOP_RIGHT)
		}
		return createDecorationComposite(main, decorators)
	}

	// uses icon for an interface and adds visibility decorators at bottom right and validation
	// issues at bottom left
	def dispatch ImageDescriptor dispatchDoGetImage(TInterface tInterface) {
		val main = createValidationAwareImageDescriptor(tInterface, getImageFileName(tInterface))
		addAccessibiltyImageDecorator(main, tInterface.typeAccessModifier)
	}

	// handling interfaces: interface icons and only validation issues at bottom left
	def dispatch ImageDescriptor dispatchDoGetImage(TClassifier tClassifier) {
		createValidationAwareImageDescriptor(tClassifier, getImageFileName(tClassifier))
	}

	// uses icon for a function and adds visibility decorators at bottom right and validation
	// issues at bottom left
	def dispatch ImageDescriptor dispatchDoGetImage(TFunction tFunction) {
		val base = createValidationAwareImageDescriptor(tFunction, getImageFileName(tFunction))
		return decorateImageForTFunction(base, tFunction.typeAccessModifier);
	}

	def private ImageDescriptor decorateImageForTFunction(ImageDescriptor baseDesc, TypeAccessModifier accessModifier) {
		return addAccessibiltyImageDecorator(baseDesc, accessModifier);
	}

	// uses icon for a variable and adds visibility decorators at bottom right, validation
	// issues at bottom left and a decorator at top right when a constant
	def dispatch ImageDescriptor dispatchDoGetImage(TVariable tVariable) {
		val base = createValidationAwareImageDescriptor(tVariable, getImageFileName(tVariable))
		return decorateImageForTVariable(base, tVariable.const, tVariable.typeAccessModifier);
	}

	def private ImageDescriptor decorateImageForTVariable(ImageDescriptor baseDesc, boolean isConst, TypeAccessModifier accessModifier) {
		var result = baseDesc;
		if(isConst) {
			result = createDecorationOverlayIcon(result, createConstImageDecorator, IDecoration.TOP_RIGHT)
		}
		result = addAccessibiltyImageDecorator(result, accessModifier);
		return result;
	}

	// uses icon for field and adds static, final as decorators if necessary at top right,
	// validation issues at bottom left
	def dispatch ImageDescriptor dispatchDoGetImage(TField tField) {
		val decorators = newArrayList
		if(tField.static) {
			decorators += createStaticImageDecorator
		}
		if(tField.final) {
			decorators += createFinalImageDecorator
		}
		val main = createValidationAwareImageDescriptor(tField, getImageFileName(tField))
		return decorated(main, decorators)
	}

	// uses icon for method and adds static, final, abstract, constructor as decorators
	// if necessary at top right and validation issues at bottom left
	def dispatch ImageDescriptor dispatchDoGetImage(TMethod tMethod) {
		val decorators = newArrayList
		if(tMethod.abstract) {
			decorators += createAbstractImageDecorator
		}
		if(tMethod.static) {
			decorators += createStaticImageDecorator
		}
		if(tMethod.final) {
			decorators += createFinalImageDecorator
		}
		if(tMethod.constructor) {
			decorators += createConstructorImageDecorator
		}
		val main = createValidationAwareImageDescriptor(tMethod, getImageFileName(tMethod))
		return decorated(main, decorators)
	}

	// uses icon for method and adds static, final, abstract as decorators
	// if necessary at top right, validation issues at bottom left and finally
	// a decorator at bottom left, to indicate if it is a getter or a setter
	def dispatch ImageDescriptor dispatchDoGetImage(FieldAccessor tFieldAccessor) {
		val decorators = newArrayList
		if(tFieldAccessor.abstract) {
			decorators += createAbstractImageDecorator
		}
		if(tFieldAccessor.static) {
			decorators += createStaticImageDecorator
		}
		if(tFieldAccessor.final) {
			decorators += createFinalImageDecorator
		}
		val newMain = if(decorators.isEmpty) {
			createValidationAwareImageDescriptor(tFieldAccessor, getImageFileName(tFieldAccessor))
		} else {
			val main = createValidationAwareImageDescriptor(tFieldAccessor, getImageFileName(tFieldAccessor))
			decorated(main, decorators)
		}
		val fieldAccessorDecorator = if(tFieldAccessor instanceof TSetter) {
			createSetterImageDecorator
		} else if(tFieldAccessor instanceof TGetter) {
			createGetterImageDecorator
		}
		return createDecorationOverlayIcon(newMain, fieldAccessorDecorator, IDecoration.BOTTOM_LEFT)
	}

	// uses icon for field and adds static and final decorators
	// at top right (analogous to JDT)
	def dispatch ImageDescriptor dispatchDoGetImage(TEnumLiteral tEnumLiteral) {
		val decorators = newArrayList
		decorators += createStaticImageDecorator
		decorators += createFinalImageDecorator
		val main = createValidationAwareImageDescriptor(tEnumLiteral, getImageFileName(tEnumLiteral))
		return createDecorationComposite(main, decorators)
	}

	// fallback
	def dispatch ImageDescriptor dispatchDoGetImage(Type type) {
		return createValidationAwareImageDescriptor(type, getImageFileName(type))
	}

	// no validation for Xtext keywords
	def dispatch ImageDescriptor dispatchDoGetImage(Keyword keyword) {
		return createSimpleImageDescriptor(getImageFileName(keyword))
	}

	// fallback
	def dispatch ImageDescriptor dispatchDoGetImage(EObject object) {
		if (object instanceof IEObjectDescription) {
			if (TypeSearchKind.EVERYTHING.matches(object.EClass)) {
				// why not createValidationAwareImageDescriptor ?
				val imageDesc = createSimpleImageDescriptor(object.imageFileName)
				val eClass = object.EClass;
				if (TypesPackage.eINSTANCE.TVariable === eClass) {
					val isConst = N4JSResourceDescriptionStrategy.getConst(object);
					val accessModifier = N4JSResourceDescriptionStrategy.getTypeAccessModifier(object);
					return decorateImageForTVariable(imageDesc, isConst, accessModifier);
				} else if (TypesPackage.eINSTANCE.TFunction.isSuperTypeOf(eClass)) {
					val accessModifier = N4JSResourceDescriptionStrategy.getTypeAccessModifier(object);
					return decorateImageForTFunction(imageDesc, accessModifier);
				} else if (TypesPackage.eINSTANCE.TClass === eClass) {
					// an abstract class cannot be set to final (with annotation @Final)
					if (N4JSResourceDescriptionStrategy.getAbstract(object)) {
						return createDecorationOverlayIcon(imageDesc, createAbstractImageDecorator, IDecoration.TOP_RIGHT)
					}
					if (N4JSResourceDescriptionStrategy.getFinal(object)) {
						return createDecorationOverlayIcon(imageDesc, createFinalImageDecorator, IDecoration.TOP_RIGHT)
					}
				}
				return imageDesc
			}
		}
		return createValidationAwareImageDescriptor(object, getImageFileName(object))
	}

	// fallback
	def dispatch ImageDescriptor dispatchDoGetImage(Object object) {
		return createSimpleImageDescriptor(getImageFileName(object))
	}
}
