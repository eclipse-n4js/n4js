/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.util;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;

/**  */
public class SymbolKindUtil {

	/** Returns the correct {@link SymbolKind} for the given {@link EClass} */
	public static SymbolKind getSymbolKind(Object obj) {
		if (!(obj instanceof EObject)) {
			return SymbolKind.Key;
		}
		EObject eObj = (EObject) obj;
		return getSymbolKind(eObj.eClass());
	}

	/** Returns the correct {@link SymbolKind} for the given {@link EClass} */
	public static SymbolKind getSymbolKind(EClass eClass) {
		if (N4JSPackage.eINSTANCE.getN4ClassDeclaration() == eClass) {
			return SymbolKind.Class;
		}
		if (N4JSPackage.eINSTANCE.getN4InterfaceDeclaration() == eClass) {
			return SymbolKind.Interface;
		}
		if (N4JSPackage.eINSTANCE.getN4EnumDeclaration() == eClass) {
			return SymbolKind.Enum;
		}
		if (N4JSPackage.eINSTANCE.getN4TypeAliasDeclaration() == eClass) {
			return SymbolKind.Interface;
		}
		if (N4JSPackage.eINSTANCE.getMethodDeclaration() == eClass) {
			return SymbolKind.Method;
		}
		if (N4JSPackage.eINSTANCE.getFunctionDefinition() == eClass) {
			return SymbolKind.Function;
		}
		if (N4JSPackage.eINSTANCE.getScript() == eClass) {
			return SymbolKind.Module;
		}
		if (N4JSPackage.eINSTANCE.getImportDeclaration() == eClass) {
			return SymbolKind.Namespace;
		}
		if (N4JSPackage.eINSTANCE.getNamedImportSpecifier() == eClass) {
			return SymbolKind.Namespace;
		}
		if (N4JSPackage.eINSTANCE.getN4EnumLiteral() == eClass) {
			return SymbolKind.EnumMember;
		}
		if (N4JSPackage.eINSTANCE.getN4FieldDeclaration() == eClass) {
			return SymbolKind.Field;
		}
		if (N4JSPackage.eINSTANCE.getN4GetterDeclaration() == eClass) {
			return SymbolKind.Property;
		}
		if (N4JSPackage.eINSTANCE.getN4SetterDeclaration() == eClass) {
			return SymbolKind.Property;
		}
		if (N4JSPackage.eINSTANCE.getVariableDeclaration() == eClass) {
			return SymbolKind.Variable;
		}
		if (N4JSPackage.eINSTANCE.getVariableStatement() == eClass) {
			return SymbolKind.Variable;
		}
		if (N4JSPackage.eINSTANCE.getN4TypeVariable() == eClass) {
			return SymbolKind.TypeParameter;
		}
		if (N4JSPackage.eINSTANCE.getNullLiteral() == eClass) {
			return SymbolKind.Null;
		}
		if (N4JSPackage.eINSTANCE.getNumericLiteral() == eClass) {
			return SymbolKind.Number;
		}
		if (N4JSPackage.eINSTANCE.getBooleanLiteral() == eClass) {
			return SymbolKind.Boolean;
		}
		if (N4JSPackage.eINSTANCE.getStringLiteral() == eClass) {
			return SymbolKind.String;
		}
		if (N4JSPackage.eINSTANCE.getObjectLiteral() == eClass) {
			return SymbolKind.Object;
		}
		if (N4JSPackage.eINSTANCE.getAnnotation() == eClass) {
			return SymbolKind.Key;
		}

		if (TypesPackage.eINSTANCE.getTModule() == eClass) {
			return SymbolKind.Module;
		}
		if (TypesPackage.eINSTANCE.getTClass() == eClass) {
			return SymbolKind.Class;
		}
		if (TypesPackage.eINSTANCE.getTInterface() == eClass) {
			return SymbolKind.Interface;
		}
		if (TypesPackage.eINSTANCE.getTEnum() == eClass) {
			return SymbolKind.Enum;
		}
		if (TypesPackage.eINSTANCE.getTypeAlias() == eClass) {
			return SymbolKind.Interface;
		}
		if (TypesPackage.eINSTANCE.getModuleNamespaceVirtualType() == eClass) {
			return SymbolKind.Namespace;
		}
		if (TypesPackage.eINSTANCE.getTNamespace() == eClass) {
			return SymbolKind.Namespace;
		}
		if (TypesPackage.eINSTANCE.getTField() == eClass) {
			return SymbolKind.Field;
		}
		if (TypesPackage.eINSTANCE.getTMethod() == eClass) {
			return SymbolKind.Method;
		}
		if (TypesPackage.eINSTANCE.getTGetter() == eClass) {
			return SymbolKind.Property;
		}
		if (TypesPackage.eINSTANCE.getTSetter() == eClass) {
			return SymbolKind.Property;
		}
		if (TypesPackage.eINSTANCE.getTEnumLiteral() == eClass) {
			return SymbolKind.EnumMember;
		}
		if (TypesPackage.eINSTANCE.getTFunction() == eClass) {
			return SymbolKind.Function;
		}
		if (TypesPackage.eINSTANCE.getTVariable() == eClass) {
			return SymbolKind.Variable;
		}
		if (TypesPackage.eINSTANCE.getTypeVariable() == eClass) {
			return SymbolKind.TypeParameter;
		}
		if (TypesPackage.eINSTANCE.getUndefinedType() == eClass) {
			return SymbolKind.Null;
		}
		if (TypesPackage.eINSTANCE.getNullType() == eClass) {
			return SymbolKind.Null;
		}
		if (TypesPackage.eINSTANCE.getAnyType() == eClass) {
			return SymbolKind.Variable;
		}
		if (TypesPackage.eINSTANCE.getPrimitiveType() == eClass) {
			return SymbolKind.Variable;
		}
		if (TypesPackage.eINSTANCE.getBuiltInType() == eClass) {
			return SymbolKind.Object;
		}
		return SymbolKind.Key;
	}

	/** Returns the correct kind for the given {@link EClass} */
	public static String getKind(EClass eClass) {
		if (TypesPackage.eINSTANCE.getTClass() == eClass) {
			return ContentAssistEntry.KIND_CLASS;
		}
		if (TypesPackage.eINSTANCE.getBuiltInType() == eClass) {
			return ContentAssistEntry.KIND_CLASS;
		}
		if (TypesPackage.eINSTANCE.getPrimitiveType() == eClass) {
			return ContentAssistEntry.KIND_KEYWORD;
		}
		if (TypesPackage.eINSTANCE.getUndefinedType() == eClass) {
			return ContentAssistEntry.KIND_KEYWORD;
		}
		if (TypesPackage.eINSTANCE.getAnyType() == eClass) {
			return ContentAssistEntry.KIND_KEYWORD;
		}
		if (TypesPackage.eINSTANCE.getTInterface() == eClass) {
			return ContentAssistEntry.KIND_INTERFACE;
		}
		if (TypesPackage.eINSTANCE.getTField() == eClass) {
			return ContentAssistEntry.KIND_FIELD;
		}
		if (TypesPackage.eINSTANCE.getTMethod() == eClass) {
			return ContentAssistEntry.KIND_METHOD;
		}
		if (TypesPackage.eINSTANCE.getTGetter() == eClass) {
			return ContentAssistEntry.KIND_PROPERTY;
		}
		if (TypesPackage.eINSTANCE.getTSetter() == eClass) {
			return ContentAssistEntry.KIND_PROPERTY;
		}
		if (TypesPackage.eINSTANCE.getTEnum() == eClass) {
			return ContentAssistEntry.KIND_ENUM;
		}
		if (TypesPackage.eINSTANCE.getTEnumLiteral() == eClass) {
			return ContentAssistEntry.KIND_VALUE;
		}
		if (TypesPackage.eINSTANCE.getTFunction() == eClass) {
			return ContentAssistEntry.KIND_FUNCTION;
		}
		if (TypesPackage.eINSTANCE.getTVariable() == eClass) {
			return ContentAssistEntry.KIND_VARIABLE;
		}
		if (N4JSPackage.eINSTANCE.getVariableDeclaration() == eClass) {
			return ContentAssistEntry.KIND_VARIABLE;
		}
		if (TypesPackage.eINSTANCE.getModuleNamespaceVirtualType() == eClass) {
			return ContentAssistEntry.KIND_COLOR;
		}
		if (TypesPackage.eINSTANCE.getTNamespace() == eClass) {
			return ContentAssistEntry.KIND_COLOR;
		}
		if (N4JSPackage.eINSTANCE.getAnnotation() == eClass) {
			return ContentAssistEntry.KIND_COLOR;
		}
		if (TypesPackage.eINSTANCE.getTAnnotation() == eClass) {
			return ContentAssistEntry.KIND_COLOR;
		}
		return ContentAssistEntry.KIND_TEXT;
	}
}
