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
 * A representation of the model object '<em><b>Bound This Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Reference to this type actually bound to a concrete type,
 * this is only done by the type system and not by a user declaration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getActualThisTypeRef <em>Actual This Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getBoundThisTypeRef()
 * @model
 * @generated
 */
public interface BoundThisTypeRef extends ThisTypeRef, StructuralTypeRef {
	/**
	 * Returns the value of the '<em><b>Actual This Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actual This Type Ref</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual This Type Ref</em>' containment reference.
	 * @see #setActualThisTypeRef(ParameterizedTypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getBoundThisTypeRef_ActualThisTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	ParameterizedTypeRef getActualThisTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getActualThisTypeRef <em>Actual This Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actual This Type Ref</em>' containment reference.
	 * @see #getActualThisTypeRef()
	 * @generated
	 */
	void setActualThisTypeRef(ParameterizedTypeRef value);

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
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getBoundThisTypeRef_DefinedTypingStrategy()
	 * @model unique="false"
	 * @generated
	 */
	TypingStrategy getDefinedTypingStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}' attribute.
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _definedTypingStrategy = this.getDefinedTypingStrategy();\nboolean _tripleEquals = (_definedTypingStrategy == &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.DEFAULT);\nif (_tripleEquals)\n{\n\treturn this.getActualThisTypeRef().getTypingStrategy();\n}\nelse\n{\n\treturn this.getDefinedTypingStrategy();\n}'"
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
	 * Overrides {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _typingStrategy = this.getTypingStrategy();\n&lt;%java.lang.String%&gt; _plus = (_typingStrategy + \"this[\");\n&lt;%java.lang.String%&gt; _typeRefAsString = this.getActualThisTypeRef().getTypeRefAsString();\n&lt;%java.lang.String%&gt; _plus_1 = (_plus + _typeRefAsString);\n&lt;%java.lang.String%&gt; _plus_2 = (_plus_1 + \"]\");\n&lt;%java.lang.String%&gt; _modifiersAsString = this.getModifiersAsString();\nreturn (_plus_2 + _modifiersAsString);'"
	 * @generated
	 */
	String getTypeRefAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getActualThisTypeRef();'"
	 * @generated
	 */
	ParameterizedTypeRef getDeclaredUpperBound();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if typingStrategy of the declared type is STRUCTURAL
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt; _actualThisTypeRef = this.getActualThisTypeRef();\nif ((_actualThisTypeRef instanceof &lt;%org.eclipse.n4js.ts.types.TN4Classifier%&gt;))\n{\n\t&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt; _actualThisTypeRef_1 = this.getActualThisTypeRef();\n\t&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _typingStrategy = ((&lt;%org.eclipse.n4js.ts.types.TN4Classifier%&gt;) _actualThisTypeRef_1).getTypingStrategy();\n\treturn (_typingStrategy == &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.STRUCTURAL);\n}\n&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt; _actualThisTypeRef_2 = this.getActualThisTypeRef();\nif ((_actualThisTypeRef_2 instanceof &lt;%org.eclipse.n4js.ts.types.TStructuralType%&gt;))\n{\n\treturn true;\n}\nreturn false;'"
	 * @generated
	 */
	boolean isDefSiteStructuralTyping();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if defined typing strategy neither DEFAULT nor NOMINAL.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getDefinedTypingStrategy() != &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.DEFAULT) &amp;&amp; \n\t(this.getDefinedTypingStrategy() != &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.NOMINAL));'"
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
	 * Override VersionedElement#getVersion() to return the version of the actual this type reference.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getActualThisTypeRef().getVersion();'"
	 * @generated
	 */
	int getVersion();

} // BoundThisTypeRef
