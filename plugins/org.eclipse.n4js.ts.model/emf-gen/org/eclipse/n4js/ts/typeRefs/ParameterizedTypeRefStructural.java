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

import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Type Ref Structural</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A {@link ParameterizedTypeRef} with use site structural typing. Note
 * that use site structural typing is not allowed everywhere. E.g., it
 * is not allowed for specifying super types of a class, interface or role.
 * It is mainly used for the type of formal parameters.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRefStructural()
 * @model
 * @generated
 */
public interface ParameterizedTypeRefStructural extends ParameterizedTypeRef, StructuralTypeRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the actual typing strategy.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.ts.types.TypingStrategy%> _definedTypingStrategy = this.getDefinedTypingStrategy();\nboolean _tripleEquals = (_definedTypingStrategy == <%org.eclipse.n4js.ts.types.TypingStrategy%>.DEFAULT);\nif (_tripleEquals)\n{\n\treturn <%org.eclipse.n4js.ts.types.TypingStrategy%>.STRUCTURAL;\n}\nelse\n{\n\treturn this.getDefinedTypingStrategy();\n}'"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Sets the actual typing strategy, required for copy operation.
	 * <!-- end-model-doc -->
	 * @model typingStrategyUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _equals = <%com.google.common.base.Objects%>.equal(typingStrategy, <%org.eclipse.n4js.ts.types.TypingStrategy%>.NOMINAL);\nif (_equals)\n{\n\tthrow new <%java.lang.IllegalArgumentException%>(\"cannot set structural type reference to nominal\");\n}\nthis.setDefinedTypingStrategy(typingStrategy);'"
	 * @generated
	 */
	void setTypingStrategy(TypingStrategy typingStrategy);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns either the members of the structuralType (if non-null) or the astStructuralMembers
	 * (if non-empty) or the genStructuralMembers.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TStructMember%>> _xifexpression = null;\n<%org.eclipse.n4js.ts.types.TStructuralType%> _structuralType = this.getStructuralType();\nboolean _tripleNotEquals = (_structuralType != null);\nif (_tripleNotEquals)\n{\n\t<%org.eclipse.n4js.ts.types.TStructuralType%> _structuralType_1 = this.getStructuralType();\n\t_xifexpression = _structuralType_1.getOwnedMembers();\n}\nelse\n{\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TStructMember%>> _xifexpression_1 = null;\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TStructMember%>> _astStructuralMembers = this.getAstStructuralMembers();\n\tboolean _isEmpty = _astStructuralMembers.isEmpty();\n\tboolean _not = (!_isEmpty);\n\tif (_not)\n\t{\n\t\t_xifexpression_1 = this.getAstStructuralMembers();\n\t}\n\telse\n\t{\n\t\t_xifexpression_1 = this.getGenStructuralMembers();\n\t}\n\t_xifexpression = _xifexpression_1;\n}\nreturn <%org.eclipse.emf.common.util.ECollections%>.<<%org.eclipse.n4js.ts.types.TStructMember%>>unmodifiableEList(_xifexpression);'"
	 * @generated
	 */
	EList<TStructMember> getStructuralMembers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.ts.types.TypingStrategy%> _typingStrategy = this.getTypingStrategy();\n<%org.eclipse.n4js.ts.types.Type%> _declaredType = this.getDeclaredType();\n<%java.lang.String%> _rawTypeAsString = null;\nif (_declaredType!=null)\n{\n\t_rawTypeAsString=_declaredType.getRawTypeAsString();\n}\n<%java.lang.String%> _plus = (_typingStrategy + _rawTypeAsString);\n<%java.lang.String%> _xifexpression = null;\n<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>> _typeArgs = this.getTypeArgs();\nboolean _isEmpty = _typeArgs.isEmpty();\nif (_isEmpty)\n{\n\t_xifexpression = \"\";\n}\nelse\n{\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>> _typeArgs_1 = this.getTypeArgs();\n\tfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>, <%java.lang.String%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>, <%java.lang.String%>>()\n\t{\n\t\tpublic <%java.lang.String%> apply(final <%org.eclipse.n4js.ts.typeRefs.TypeArgument%> it)\n\t\t{\n\t\t\treturn it.getTypeRefAsString();\n\t\t}\n\t};\n\t<%org.eclipse.emf.common.util.EList%><<%java.lang.String%>> _map = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>, <%java.lang.String%>>map(_typeArgs_1, _function);\n\t<%java.lang.String%> _join = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.join(_map, \",\");\n\t<%java.lang.String%> _plus_1 = (\"<\" + _join);\n\t_xifexpression = (_plus_1 + \">\");\n}\n<%java.lang.String%> _plus_2 = (_plus + _xifexpression);\n<%java.lang.String%> _xifexpression_1 = null;\n<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TStructMember%>> _structuralMembers = this.getStructuralMembers();\nboolean _isEmpty_1 = _structuralMembers.isEmpty();\nif (_isEmpty_1)\n{\n\t_xifexpression_1 = \"\";\n}\nelse\n{\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TStructMember%>> _structuralMembers_1 = this.getStructuralMembers();\n\tfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TStructMember%>, <%java.lang.String%>> _function_1 = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.types.TStructMember%>, <%java.lang.String%>>()\n\t{\n\t\tpublic <%java.lang.String%> apply(final <%org.eclipse.n4js.ts.types.TStructMember%> it)\n\t\t{\n\t\t\treturn it.getMemberAsString();\n\t\t}\n\t};\n\t<%org.eclipse.emf.common.util.EList%><<%java.lang.String%>> _map_1 = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.ts.types.TStructMember%>, <%java.lang.String%>>map(_structuralMembers_1, _function_1);\n\t<%java.lang.String%> _join_1 = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.join(_map_1, \"; \");\n\t<%java.lang.String%> _plus_3 = (\" with { \" + _join_1);\n\t<%java.lang.String%> _plus_4 = (_plus_3 + \" }\");\n\t<%java.lang.String%> _xifexpression_2 = null;\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%>> _postponedSubstitutions = this.getPostponedSubstitutions();\n\tboolean _isEmpty_2 = _postponedSubstitutions.isEmpty();\n\tif (_isEmpty_2)\n\t{\n\t\t_xifexpression_2 = \"\";\n\t}\n\telse\n\t{\n\t\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%>> _postponedSubstitutions_1 = this.getPostponedSubstitutions();\n\t\tfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%>, <%java.lang.String%>> _function_2 = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%>, <%java.lang.String%>>()\n\t\t{\n\t\t\tpublic <%java.lang.String%> apply(final <%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%> it)\n\t\t\t{\n\t\t\t\t<%org.eclipse.n4js.ts.types.TypeVariable%> _typeVar = it.getTypeVar();\n\t\t\t\t<%java.lang.String%> _typeAsString = _typeVar.getTypeAsString();\n\t\t\t\t<%java.lang.String%> _plus = (_typeAsString + \"->\");\n\t\t\t\t<%org.eclipse.n4js.ts.typeRefs.TypeArgument%> _typeArg = it.getTypeArg();\n\t\t\t\t<%java.lang.String%> _typeRefAsString = _typeArg.getTypeRefAsString();\n\t\t\t\treturn (_plus + _typeRefAsString);\n\t\t\t}\n\t\t};\n\t\t<%org.eclipse.emf.common.util.EList%><<%java.lang.String%>> _map_2 = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%>, <%java.lang.String%>>map(_postponedSubstitutions_1, _function_2);\n\t\t<%java.lang.String%> _join_2 = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.join(_map_2, \", \");\n\t\t<%java.lang.String%> _plus_5 = (\" [[\" + _join_2);\n\t\t_xifexpression_2 = (_plus_5 + \"]]\");\n\t}\n\t_xifexpression_1 = (_plus_4 + _xifexpression_2);\n}\nreturn (_plus_2 + _xifexpression_1);'"
	 * @generated
	 */
	String getTypeRefAsString();

} // ParameterizedTypeRefStructural
