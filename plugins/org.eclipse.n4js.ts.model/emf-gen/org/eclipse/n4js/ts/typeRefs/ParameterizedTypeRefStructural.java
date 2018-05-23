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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _definedTypingStrategy = this.getDefinedTypingStrategy();\nboolean _tripleEquals = (_definedTypingStrategy == &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.DEFAULT);\nif (_tripleEquals)\n{\n\treturn &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.STRUCTURAL;\n}\nelse\n{\n\treturn this.getDefinedTypingStrategy();\n}'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _equals = &lt;%com.google.common.base.Objects%&gt;.equal(typingStrategy, &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.NOMINAL);\nif (_equals)\n{\n\tthrow new &lt;%java.lang.IllegalArgumentException%&gt;(\"cannot set structural type reference to nominal\");\n}\nthis.setDefinedTypingStrategy(typingStrategy);'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TStructMember%&gt;&gt; _xifexpression = null;\n&lt;%org.eclipse.n4js.ts.types.TStructuralType%&gt; _structuralType = this.getStructuralType();\nboolean _tripleNotEquals = (_structuralType != null);\nif (_tripleNotEquals)\n{\n\t_xifexpression = this.getStructuralType().getOwnedMembers();\n}\nelse\n{\n\t&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TStructMember%&gt;&gt; _xifexpression_1 = null;\n\tboolean _isEmpty = this.getAstStructuralMembers().isEmpty();\n\tboolean _not = (!_isEmpty);\n\tif (_not)\n\t{\n\t\t_xifexpression_1 = this.getAstStructuralMembers();\n\t}\n\telse\n\t{\n\t\t_xifexpression_1 = this.getGenStructuralMembers();\n\t}\n\t_xifexpression = _xifexpression_1;\n}\nreturn &lt;%org.eclipse.emf.common.util.ECollections%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TStructMember%&gt;&gt;unmodifiableEList(_xifexpression);'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _typingStrategy = this.getTypingStrategy();\n&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType = this.getDeclaredType();\n&lt;%java.lang.String%&gt; _rawTypeAsString = null;\nif (_declaredType!=null)\n{\n\t_rawTypeAsString=_declaredType.getRawTypeAsString();\n}\n&lt;%java.lang.String%&gt; _plus = (_typingStrategy + _rawTypeAsString);\n&lt;%java.lang.String%&gt; _xifexpression = null;\nboolean _isEmpty = this.getTypeArgs().isEmpty();\nif (_isEmpty)\n{\n\t_xifexpression = \"\";\n}\nelse\n{\n\tfinal &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;, &lt;%java.lang.String%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;, &lt;%java.lang.String%&gt;&gt;()\n\t{\n\t\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt; it)\n\t\t{\n\t\t\treturn it.getTypeRefAsString();\n\t\t}\n\t};\n\t&lt;%java.lang.String%&gt; _join = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getTypeArgs(), _function), \",\");\n\t&lt;%java.lang.String%&gt; _plus_1 = (\"&lt;\" + _join);\n\t_xifexpression = (_plus_1 + \"&gt;\");\n}\n&lt;%java.lang.String%&gt; _plus_2 = (_plus + _xifexpression);\n&lt;%java.lang.String%&gt; _xifexpression_1 = null;\nboolean _isEmpty_1 = this.getStructuralMembers().isEmpty();\nif (_isEmpty_1)\n{\n\t_xifexpression_1 = \"\";\n}\nelse\n{\n\tfinal &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TStructMember%&gt;, &lt;%java.lang.String%&gt;&gt; _function_1 = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TStructMember%&gt;, &lt;%java.lang.String%&gt;&gt;()\n\t{\n\t\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.types.TStructMember%&gt; it)\n\t\t{\n\t\t\treturn it.getMemberAsString();\n\t\t}\n\t};\n\t&lt;%java.lang.String%&gt; _join_1 = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TStructMember%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getStructuralMembers(), _function_1), \"; \");\n\t&lt;%java.lang.String%&gt; _plus_3 = (\" with { \" + _join_1);\n\t&lt;%java.lang.String%&gt; _plus_4 = (_plus_3 + \" }\");\n\t&lt;%java.lang.String%&gt; _xifexpression_2 = null;\n\tboolean _isEmpty_2 = this.getPostponedSubstitutions().isEmpty();\n\tif (_isEmpty_2)\n\t{\n\t\t_xifexpression_2 = \"\";\n\t}\n\telse\n\t{\n\t\tfinal &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%&gt;, &lt;%java.lang.String%&gt;&gt; _function_2 = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%&gt;, &lt;%java.lang.String%&gt;&gt;()\n\t\t{\n\t\t\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%&gt; it)\n\t\t\t{\n\t\t\t\t&lt;%java.lang.String%&gt; _typeAsString = it.getTypeVar().getTypeAsString();\n\t\t\t\t&lt;%java.lang.String%&gt; _plus = (_typeAsString + \"-&gt;\");\n\t\t\t\t&lt;%java.lang.String%&gt; _typeRefAsString = it.getTypeArg().getTypeRefAsString();\n\t\t\t\treturn (_plus + _typeRefAsString);\n\t\t\t}\n\t\t};\n\t\t&lt;%java.lang.String%&gt; _join_2 = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeVariableMapping%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getPostponedSubstitutions(), _function_2), \", \");\n\t\t&lt;%java.lang.String%&gt; _plus_5 = (\" [[\" + _join_2);\n\t\t_xifexpression_2 = (_plus_5 + \"]]\");\n\t}\n\t_xifexpression_1 = (_plus_4 + _xifexpression_2);\n}\nreturn (_plus_2 + _xifexpression_1);'"
	 * @generated
	 */
	String getTypeRefAsString();

} // ParameterizedTypeRefStructural
