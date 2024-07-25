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
package org.eclipse.n4js.validation;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.PropertySpread;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterType;
import org.eclipse.n4js.ts.types.BuiltInType;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TDynamicElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.validation.utils.TypesKeywordProvider;

import com.google.inject.Singleton;

/**
 * Helper returning the keyword of a given AST or type element, e.g., "class" for a class declaration.
 */
@Singleton
@SuppressWarnings("unused")
public class N4JSElementKeywordProvider extends TypesKeywordProvider {

	@Override
	public String keyword(Object n4RoleDeclaration) {
		if (n4RoleDeclaration instanceof TClass) {
			return _keyword((TClass) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TInterface) {
			return _keyword((TInterface) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TStructGetter) {
			return _keyword((TStructGetter) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TStructSetter) {
			return _keyword((TStructSetter) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TClassifier) {
			return _keyword((TClassifier) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TGetter) {
			return _keyword((TGetter) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TMethod) {
			return _keyword((TMethod) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TSetter) {
			return _keyword((TSetter) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TStructField) {
			return _keyword((TStructField) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4InterfaceDeclaration) {
			return _keyword((N4InterfaceDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof PrimitiveType) {
			return _keyword((PrimitiveType) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TField) {
			return _keyword((TField) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TFunction) {
			return _keyword((TFunction) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TypeAlias) {
			return _keyword((TypeAlias) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4ClassDefinition) {
			return _keyword((N4ClassDefinition) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4EnumDeclaration) {
			return _keyword((N4EnumDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4GetterDeclaration) {
			return _keyword((N4GetterDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4MethodDeclaration) {
			return _keyword((N4MethodDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4SetterDeclaration) {
			return _keyword((N4SetterDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4TypeAliasDeclaration) {
			return _keyword((N4TypeAliasDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof PropertyGetterDeclaration) {
			return _keyword((PropertyGetterDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof PropertyNameValuePair) {
			return _keyword((PropertyNameValuePair) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof PropertySetterDeclaration) {
			return _keyword((PropertySetterDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof PropertySpread) {
			return _keyword((PropertySpread) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof BuiltInType) {
			return _keyword((BuiltInType) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TEnum) {
			return _keyword((TEnum) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TNamespace) {
			return _keyword((TNamespace) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TypeVariable) {
			return _keyword((TypeVariable) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof CatchVariable) {
			return _keyword((CatchVariable) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof FormalParameter) {
			return _keyword((FormalParameter) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof FunctionDeclaration) {
			return _keyword((FunctionDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof FunctionExpression) {
			return _keyword((FunctionExpression) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4ClassifierDefinition) {
			return _keyword((N4ClassifierDefinition) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4FieldDeclaration) {
			return _keyword((N4FieldDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4NamespaceDeclaration) {
			return _keyword((N4NamespaceDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof ParameterizedCallExpression) {
			return _keyword((ParameterizedCallExpression) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof ThisLiteral) {
			return _keyword((ThisLiteral) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof VariableDeclaration) {
			return _keyword((VariableDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof VariableStatement) {
			return _keyword((VariableStatement) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TDynamicElement) {
			return _keyword((TDynamicElement) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TFormalParameter) {
			return _keyword((TFormalParameter) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TMember) {
			return _keyword((TMember) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TVariable) {
			return _keyword((TVariable) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof Type) {
			return _keyword((Type) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof BreakStatement) {
			return _keyword((BreakStatement) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof ContinueStatement) {
			return _keyword((ContinueStatement) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof PropertyAssignment) {
			return _keyword((PropertyAssignment) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof ReturnStatement) {
			return _keyword((ReturnStatement) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof ThrowStatement) {
			return _keyword((ThrowStatement) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4MemberDeclaration) {
			return _keyword((N4MemberDeclaration) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof N4TypeVariable) {
			return _keyword((N4TypeVariable) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof NamedImportSpecifier) {
			return _keyword((NamedImportSpecifier) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof ModuleFilterType) {
			return _keyword((ModuleFilterType) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof MemberAccessModifier) {
			return _keyword((MemberAccessModifier) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TypeAccessModifier) {
			return _keyword((TypeAccessModifier) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof TypingStrategy) {
			return _keyword((TypingStrategy) n4RoleDeclaration);
		} else if (n4RoleDeclaration instanceof EObject) {
			return _keyword((EObject) n4RoleDeclaration);
		} else if (n4RoleDeclaration == null) {
			return _keyword((Void) null);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.<Object> asList(n4RoleDeclaration).toString());
		}
	}

	private String _keyword(N4NamespaceDeclaration n4NamespaceDeclaration) {
		return "namespace";
	}

	private String _keyword(TNamespace tNamespace) {
		return "namespace";
	}

	private String _keyword(N4ClassDefinition n4ClassDefinition) {
		return "class";
	}

	private String _keyword(N4InterfaceDeclaration n4RoleDeclaration) {
		return "interface";
	}

	private String _keyword(N4EnumDeclaration n4EnumDeclaration) {
		return "enum";
	}

	private String _keyword(N4TypeAliasDeclaration n4TypeAliasDeclaration) {
		return "type alias";
	}

	private String _keyword(FunctionDeclaration functionDeclaration) {
		return "function";
	}

	private String _keyword(N4ClassifierDefinition n4ClassifierDefinition) {
		return "classifier";
	}

	private String _keyword(N4MemberDeclaration n4MemberDeclaration) {
		return "member";
	}

	private String _keyword(N4FieldDeclaration n4FieldDeclaration) {
		return "field";
	}

	private String _keyword(N4MethodDeclaration n4MethodDeclaration) {
		if (n4MethodDeclaration.isConstructor()) {
			return "constructor";
		} else if (n4MethodDeclaration.isCallSignature()) {
			return "call signature";
		} else if (n4MethodDeclaration.isConstructSignature()) {
			return "construct signature";
		} else {
			return "method";
		}
	}

	private String _keyword(N4GetterDeclaration n4GetterDeclaration) {
		return "getter";
	}

	private String _keyword(N4SetterDeclaration n4SetterDeclaration) {
		return "setter";
	}

	private String _keyword(VariableStatement VariableStmt) {
		return "variable";
	}

	private String _keyword(VariableDeclaration VariableDecl) {
		return "variable";
	}

	private String _keyword(N4TypeVariable typeVar) {
		return "type variable";
	}

	private String _keyword(CatchVariable catchVar) {
		return "catch variable";
	}

	private String _keyword(FunctionExpression fnExp) {
		return "function expression";
	}

	private String _keyword(ParameterizedCallExpression callExp) {
		return "call expression";
	}

	private String _keyword(FormalParameter fparam) {
		return "parameter";
	}

	private String _keyword(NamedImportSpecifier namedImportSpecifier) {
		return "named import";
	}

	private String _keyword(PropertyAssignment propertyAssignment) {
		return "property";
	}

	private String _keyword(PropertyGetterDeclaration propertyGetterDeclaration) {
		return "property getter";
	}

	private String _keyword(PropertySetterDeclaration propertySetterDeclaration) {
		return "property setter";
	}

	private String _keyword(PropertyNameValuePair propertyNameValuePair) {
		return "property";
	}

	private String _keyword(PropertySpread propertySpread) {
		return "property spread";
	}

	private String _keyword(ThisLiteral thisLiteral) {
		return "this keyword";
	}

	private String _keyword(BreakStatement brk) {
		return "break";
	}

	private String _keyword(ContinueStatement cntn) {
		return "continue";
	}

	private String _keyword(ThrowStatement thrw) {
		return "throw";
	}

	private String _keyword(ReturnStatement rtrn) {
		return "return";
	}

	private String _keyword(Void nullValue) {
		return "";
	}

	private String _keyword(ModuleFilterType moduleFilterType) {
		switch (moduleFilterType) {
		case NO_VALIDATE:
			return "noValidate";
		default: {
			return "unknown filter type";
		}
		}
	}

}
