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
 * A representation of the model object '<em><b>This Type Ref Structural</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getThisTypeRefStructural()
 * @model
 * @generated
 */
public interface ThisTypeRefStructural extends ThisTypeRef, StructuralTypeRef {
	/**
	 * Returns the value of the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.ts.types.TypingStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The defined (declared or inferred) typing strategy on use site, nominal typing by default. Could be changed to structural or structural
	 * field (via tilde or tilde-tilde operators).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Defined Typing Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypingStrategy
	 * @see #setDefinedTypingStrategy(TypingStrategy)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getThisTypeRefStructural_DefinedTypingStrategy()
	 * @model unique="false"
	 * @generated
	 */
	TypingStrategy getDefinedTypingStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Typing Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypingStrategy
	 * @see #getDefinedTypingStrategy()
	 * @generated
	 */
	void setDefinedTypingStrategy(TypingStrategy value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the actual typing strategy, that is either the defined typing strategy of the reference, or the typing strategy of the
	 * actual this type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _definedTypingStrategy = this.getDefinedTypingStrategy();\nboolean _tripleEquals = (_definedTypingStrategy == &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.DEFAULT);\nif (_tripleEquals)\n{\n\treturn &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.NOMINAL;\n}\nreturn this.getDefinedTypingStrategy();'"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model typingStrategyUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='this.setDefinedTypingStrategy(typingStrategy);'"
	 * @generated
	 */
	void setTypingStrategy(TypingStrategy typingStrategy);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if typingStrategy is neither NOMINAL nor DEFAULT
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getTypingStrategy() != &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.NOMINAL) &amp;&amp; (this.getTypingStrategy() != &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.DEFAULT));'"
	 * @generated
	 */
	boolean isUseSiteStructuralTyping();

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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _typingStrategy = this.getTypingStrategy();\n&lt;%java.lang.String%&gt; _plus = (_typingStrategy + \"this\");\n&lt;%java.lang.String%&gt; _modifiersAsString = this.getModifiersAsString();\nreturn (_plus + _modifiersAsString);'"
	 * @generated
	 */
	String getTypeRefAsString();

} // ThisTypeRefStructural
