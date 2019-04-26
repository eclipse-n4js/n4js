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
package org.eclipse.n4js.ide.server.symbol;

import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.xtext.resource.IEObjectDescription;

/** */
public class SymbolKindCalculationHelper {

	/** */
	public SymbolKind getSymbolKind(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof ParameterizedTypeRef) {
			return null;
		}
		if (obj instanceof Script) {
			return SymbolKind.Module;
		}
		if (obj instanceof ImportDeclaration) {
			return SymbolKind.Namespace;
		}
		if (obj instanceof NamedImportSpecifier) {
			return SymbolKind.Namespace;
		}
		if (obj instanceof N4ClassifierDeclaration) {
			return SymbolKind.Class;
		}
		if (obj instanceof N4GetterDeclaration) {
			return SymbolKind.Property;
		}
		if (obj instanceof N4SetterDeclaration) {
			return SymbolKind.Property;
		}
		if (obj instanceof ExportedVariableStatement) {
			return SymbolKind.Variable;
		}
		if (obj instanceof NamedElement) {
			return SymbolKind.Property;
		}
		if (obj instanceof TModule) {
			return SymbolKind.Module;
		}
		if (obj instanceof TClass) {
			return SymbolKind.Class;
		}
		if (obj instanceof TInterface) {
			return SymbolKind.Interface;
		}
		if (obj instanceof TEnum) {
			return SymbolKind.Enum;
		}
		if (obj instanceof TEnumLiteral) {
			return SymbolKind.EnumMember;
		}
		if (obj instanceof NullLiteral) {
			return SymbolKind.Null;
		}
		if (obj instanceof NumericLiteral) {
			return SymbolKind.Number;
		}
		if (obj instanceof BooleanLiteral) {
			return SymbolKind.Boolean;
		}
		if (obj instanceof StringLiteral) {
			return SymbolKind.String;
		}
		if (obj instanceof ObjectLiteral) {
			return SymbolKind.Object;
		}
		if (obj instanceof TGetter) {
			return SymbolKind.Property;
		}
		if (obj instanceof TSetter) {
			return SymbolKind.Property;
		}
		if (obj instanceof FunctionTypeExpression) {
			return SymbolKind.Operator;
		}
		if (obj instanceof TMethod) {
			return SymbolKind.Method;
		}
		if (obj instanceof TFunction) {
			return SymbolKind.Function;
		}
		if (obj instanceof TFormalParameter) {
			return null;
		}
		if (obj instanceof TField) {
			return SymbolKind.Field;
		}
		if (obj instanceof TypeVariable) {
			return SymbolKind.TypeParameter;
		}
		if (obj instanceof IdentifiableElement) {
			return SymbolKind.Property;
		}
		if (obj instanceof IEObjectDescription) {
			return SymbolKind.Property;
		}
		return null;
	}

}
