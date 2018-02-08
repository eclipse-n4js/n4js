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
package org.eclipse.n4js.ts.typeRefs;

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TypeVariable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Type Expr Or Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for {@link FunctionTypeRef} and {@link FunctionTypeExpression}.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getFunctionTypeExprOrRef()
 * @model abstract="true"
 * @generated
 */
public interface FunctionTypeExprOrRef extends StaticBaseTypeRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * optional thisType declaration e.g. for a given class A it's member-type can be specified as "{A.function():void}"
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getDeclaredThisType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns the declared type casted to a TFunction
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.ts.types.Type%> _declaredType = this.getDeclaredType();\nreturn ((<%org.eclipse.n4js.ts.types.TFunction%>) _declaredType);'"
	 * @generated
	 */
	TFunction getFunctionType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type variables as declared in the expression or of the referenced function type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TypeVariable> getTypeVars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the upper bounds of the given type variable (which should be one of those returned by
	 * {@link FunctionTypeExprOrRef#getTypeVars()}). Never read the upper bounds directly from the type variable in
	 * case of type variables coming from a FunctionTypeExprOrRef; for more info why this is required, see
	 * {@link FunctionTypeExpression#getTypeVarUpperBound(TypeVariable)}.
	 * <!-- end-model-doc -->
	 * @model unique="false" typeVarUnique="false"
	 * @generated
	 */
	TypeRef getTypeVarUpperBound(TypeVariable typeVar);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The formal parameters as declared in the expression or of the referenced function type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TFormalParameter> getFpars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getReturnTypeRef() != null) && this.getReturnTypeRef().isFollowedByQuestionMark());'"
	 * @generated
	 */
	boolean isReturnValueOptional();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The return type as declared in the expression or of the referenced function type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getReturnTypeRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#isGeneric()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isEmpty = this.getTypeVars().isEmpty();\nreturn (!_isEmpty);'"
	 * @generated
	 */
	boolean isGeneric();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#isRaw()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (this.isGeneric() && (this.getTypeArgs().size() < this.getTypeVars().size()));'"
	 * @generated
	 */
	boolean isRaw();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the formal parameter corresponding to the argument at index 'argIndex' in a function call
	 * or 'null' if 'argIndex' is invalid. This method takes into account optional and variadic parameters.
	 * <!-- end-model-doc -->
	 * @model unique="false" argIndexUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final int fparsSize = this.getFpars().size();\nif (((argIndex >= 0) && (argIndex < fparsSize)))\n{\n\treturn this.getFpars().get(argIndex);\n}\nelse\n{\n\tif ((((argIndex >= fparsSize) && (fparsSize > 0)) && this.getFpars().get((fparsSize - 1)).isVariadic()))\n\t{\n\t\treturn this.getFpars().get((fparsSize - 1));\n\t}\n}\nreturn null;'"
	 * @generated
	 */
	TFormalParameter getFparForArgIdx(int argIndex);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.String%> _xifexpression = null;\n<%org.eclipse.n4js.ts.typeRefs.TypeRef%> _declaredThisType = this.getDeclaredThisType();\nboolean _tripleNotEquals = (_declaredThisType != null);\nif (_tripleNotEquals)\n{\n\t<%java.lang.String%> _typeRefAsString = this.getDeclaredThisType().getTypeRefAsString();\n\t<%java.lang.String%> _plus = (\"@This(\" + _typeRefAsString);\n\t_xifexpression = (_plus + \") \");\n}\nelse\n{\n\t_xifexpression = \"\";\n}\n<%java.lang.String%> _plus_1 = (\"{\" + _xifexpression);\n<%java.lang.String%> _plus_2 = (_plus_1 + \"function\");\n<%java.lang.String%> _xifexpression_1 = null;\nboolean _isGeneric = this.isGeneric();\nif (_isGeneric)\n{\n\tfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TypeVariable%>, <%java.lang.String%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TypeVariable%>, <%java.lang.String%>>()\n\t{\n\t\tpublic <%java.lang.String%> apply(final <%org.eclipse.n4js.ts.types.TypeVariable%> it)\n\t\t{\n\t\t\treturn it.getTypeVariableAsString(<%this%>.getTypeVarUpperBound(it));\n\t\t}\n\t};\n\t<%java.lang.String%> _join = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.join(<%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.ts.types.TypeVariable%>, <%java.lang.String%>>map(this.getTypeVars(), _function), \",\");\n\t<%java.lang.String%> _plus_3 = (\"<\" + _join);\n\t_xifexpression_1 = (_plus_3 + \">\");\n}\nelse\n{\n\t_xifexpression_1 = \"\";\n}\n<%java.lang.String%> _plus_4 = (_plus_2 + _xifexpression_1);\n<%java.lang.String%> _plus_5 = (_plus_4 + \"(\");\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TFormalParameter%>, <%java.lang.String%>> _function_1 = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TFormalParameter%>, <%java.lang.String%>>()\n{\n\tpublic <%java.lang.String%> apply(final <%org.eclipse.n4js.ts.types.TFormalParameter%> it)\n\t{\n\t\treturn it.getFormalParameterAsTypesString();\n\t}\n};\n<%java.lang.String%> _join_1 = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.join(<%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.ts.types.TFormalParameter%>, <%java.lang.String%>>map(this.getFpars(), _function_1), \",\");\n<%java.lang.String%> _plus_6 = (_plus_5 + _join_1);\n<%java.lang.String%> _plus_7 = (_plus_6 + \")\");\n<%java.lang.String%> _xifexpression_2 = null;\n<%org.eclipse.n4js.ts.typeRefs.TypeRef%> _returnTypeRef = this.getReturnTypeRef();\nboolean _tripleNotEquals_1 = (_returnTypeRef != null);\nif (_tripleNotEquals_1)\n{\n\t<%java.lang.String%> _typeRefAsString_1 = this.getReturnTypeRef().getTypeRefAsString();\n\t_xifexpression_2 = (\":\" + _typeRefAsString_1);\n}\nelse\n{\n\t_xifexpression_2 = \"\";\n}\n<%java.lang.String%> _plus_8 = (_plus_7 + _xifexpression_2);\n<%java.lang.String%> _xifexpression_3 = null;\nboolean _isReturnValueOptional = this.isReturnValueOptional();\nif (_isReturnValueOptional)\n{\n\t_xifexpression_3 = \"?\";\n}\nelse\n{\n\t_xifexpression_3 = \"\";\n}\n<%java.lang.String%> _plus_9 = (_plus_8 + _xifexpression_3);\n<%java.lang.String%> _plus_10 = (_plus_9 + \"}\");\n<%java.lang.String%> _modifiersAsString = this.getModifiersAsString();\nreturn (_plus_10 + _modifiersAsString);'"
	 * @generated
	 */
	String getTypeRefAsString();

} // FunctionTypeExprOrRef
