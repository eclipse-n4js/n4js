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

import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TEnumLiteral
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ui.labeling.EObjectWithContext
import org.eclipse.xtext.resource.IEObjectDescription

/**
 * This helper class dispatches the selection of the main image file to use for
 * a given AST (resp. types) element. For classes, interfaces, enumerations, fields
 * and methods there are already different images available for different visibility
 * states so for their selection there is a switch here.
 * <br /><br />
 * As fallback for an unknown element null is returned, this is expected by the API
 * of the label provider.
 */
class ImageFileNameCalculationHelper {

	/* calculation of image file name for AST -> delegates to types model, as information is easier to retrieve there */

	def dispatch String getImageFileName(EObjectWithContext eObjectWithContext) {
		return getImageFileName(eObjectWithContext.obj)
	}

	def dispatch String getImageFileName(Script script) {
		return getImageFileName(script.module)
	}

	def dispatch String getImageFileName(N4ClassDeclaration n4ClassDeclaration) {
		 getImageFileName(n4ClassDeclaration.definedType)
	}

	def dispatch String getImageFileName(N4InterfaceDeclaration n4InterfaceDeclaration) {
		 getImageFileName(n4InterfaceDeclaration.definedType)
	}

	def dispatch String getImageFileName(FunctionDeclaration functionDeclaration) {
		 getImageFileName(functionDeclaration.definedType)
	}

	def dispatch String getImageFileName(N4EnumDeclaration n4EnumDeclaration) {
		 getImageFileName(n4EnumDeclaration.definedTypeAsEnum)
	}

	def dispatch String getImageFileName(N4MethodDeclaration n4MethodDeclaration) {
		 getImageFileName(n4MethodDeclaration.definedTypeElement)
	}

	def dispatch String getImageFileName(N4FieldDeclaration n4FieldDeclaration) {
		 getImageFileName(n4FieldDeclaration.definedField)
	}

	def dispatch String getImageFileName(ExportedVariableDeclaration vd) {
		 getImageFileName(vd.definedVariable)
	}

	def dispatch String getImageFileName(ExportedVariableStatement vs) {
		 return "var_simple_aggr.gif"
	}

	def dispatch String getImageFileName(ImportDeclaration importDelaration) {
		return "import_obj.gif"
	}

	def dispatch String getImageFileName(NamedImportSpecifier namedImportSpecifier) {
		return "import.gif"
	}

	/* calculation of image file name for types model */

	def dispatch String getImageFileName(TModule tClass) {
		"n4js_module_corr.png"
	}

	def dispatch String getImageFileName(TClass tClass) {
		return getClassFileName(tClass.typeAccessModifier);
	}

	def private String getClassFileName(TypeAccessModifier modifier) {
		switch modifier {
			case TypeAccessModifier.PUBLIC: "innerclass_public_obj.png"
			case TypeAccessModifier.PUBLIC_INTERNAL: "innerclass_protected_obj.png"
			case TypeAccessModifier.PROJECT: "innerclass_default_obj.png"
			case TypeAccessModifier.PRIVATE: "innerclass_private_obj.png"
			default: "innerclass_default_obj.png"
		}
	}

	def dispatch String getImageFileName(TInterface tInterface) {
		return getInterfaceFileName(tInterface.typeAccessModifier);
	}

	def private String getInterfaceFileName(TypeAccessModifier modifier) {
		switch modifier {
			case TypeAccessModifier.PUBLIC: "innerinterface_public_obj.png"
			case TypeAccessModifier.PUBLIC_INTERNAL: "innerinterface_protected_obj.png"
			case TypeAccessModifier.PROJECT: "innerinterface_default_obj.png"
			case TypeAccessModifier.PRIVATE: "innerinterface_private_obj.png"
			default: "innerinterface_default_obj.png"
		}
	}

	def dispatch String getImageFileName(TEnum tEnum) {
		return getEnumFileName(tEnum.typeAccessModifier)
	}

	def private String getEnumFileName(TypeAccessModifier modifier) {
		switch modifier {
			case TypeAccessModifier.PUBLIC: "enum_obj.png"
			case TypeAccessModifier.PUBLIC_INTERNAL: "enum_protected_obj.png"
			case TypeAccessModifier.PROJECT: "enum_default_obj.png"
			case TypeAccessModifier.PRIVATE: "enum_private_obj.png"
			default: "enum_obj.png"
		}
	}

	def dispatch String getImageFileName(TFunction tFunction) {
		return getFunctionFileName(tFunction.typeAccessModifier);
	}

	def private String getFunctionFileName(TypeAccessModifier accessModifier) {
		switch accessModifier {
			case TypeAccessModifier.PUBLIC: "n4js_function.png"
			case TypeAccessModifier.PUBLIC_INTERNAL: "n4js_function.png"
			case TypeAccessModifier.PROJECT: "n4js_function.png"
			case TypeAccessModifier.PRIVATE: "n4js_function.png"
			default: "n4js_function.png"
		}
	}

	def dispatch String getImageFileName(TMethod tMethod) {
		switch tMethod.memberAccessModifier {
			case MemberAccessModifier.PUBLIC: "method_public_obj.gif"
			case MemberAccessModifier.PUBLIC_INTERNAL: "method_public_obj.gif"
			case MemberAccessModifier.PROTECTED: "method_protected_obj.gif"
			case MemberAccessModifier.PROJECT: "method.gif"
			case MemberAccessModifier.PRIVATE: "method_private_obj.gif"
			default: "method.gif"
		}
	}

	def dispatch String getImageFileName(FieldAccessor fieldAccessor) {
		switch fieldAccessor.memberAccessModifier {
			case MemberAccessModifier.PUBLIC: "method_public_obj.gif"
			case MemberAccessModifier.PUBLIC_INTERNAL: "method_public_obj.gif"
			case MemberAccessModifier.PROTECTED: "method_protected_obj.gif"
			case MemberAccessModifier.PROJECT: "method.gif"
			case MemberAccessModifier.PRIVATE: "method_private_obj.gif"
			default: "method.gif"
		}
	}

	def dispatch String getImageFileName(TField tField) {
		switch tField.memberAccessModifier {
			case MemberAccessModifier.PUBLIC: "field_public_obj.gif"
			case MemberAccessModifier.PUBLIC_INTERNAL: "field_public_obj.gif"
			case MemberAccessModifier.PROTECTED: "method_protected_obj.gif"
			case MemberAccessModifier.PROJECT: "field_default_obj.gif"
			case MemberAccessModifier.PRIVATE: "field_private_obj.gif"
			default: "field_default_obj.gif"
		}
	}

	def dispatch String getImageFileName(TEnumLiteral tEnumLiteral) {
		"field_public_obj.gif"
	}

	def dispatch String getImageFileName(TVariable tVariable) {
		return getVariableFileName();
	}

	def private String getVariableFileName() {
		return "var_simple.gif";
	}

	def dispatch String getImageFileName(IEObjectDescription description) {
		val modifier = tryGetAccessModifier(description);
		val eClass = description.EClass;
		if (TypesPackage.eINSTANCE.TFunction.isSuperTypeOf(eClass)) {
			return getFunctionFileName(modifier);
		}
		if (TypesPackage.eINSTANCE.TVariable.isSuperTypeOf(eClass)) {
			return getVariableFileName();
		}
		switch (eClass) {
			case TypesPackage.eINSTANCE.TClass: getClassFileName(modifier)
			case TypesPackage.eINSTANCE.TInterface: getInterfaceFileName(modifier)
			case TypesPackage.eINSTANCE.TEnum: getEnumFileName(modifier)
			default: null
		}
	}

	def private TypeAccessModifier tryGetAccessModifier(IEObjectDescription description) {
		return N4JSResourceDescriptionStrategy.getTypeAccessModifier(description);
	}

	// fallback
	def dispatch String getImageFileName(Object object) {
		return null
	}
}
