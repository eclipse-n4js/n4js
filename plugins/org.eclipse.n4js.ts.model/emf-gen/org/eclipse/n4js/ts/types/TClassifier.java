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
package org.eclipse.n4js.ts.types;

import java.lang.Iterable;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TClassifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for N4 specific classifiers, i.e., class, interface, or role.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TClassifier#isDeclaredCovariantConstructor <em>Declared Covariant Constructor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClassifier()
 * @model abstract="true"
 * @generated
 */
public interface TClassifier extends ContainerType<TMember>, SyntaxRelatedTElement, TVersionable {
	/**
	 * Returns the value of the '<em><b>Declared Covariant Constructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if this classifier is itself annotated with <code>@CovariantConstructor</code> or if it has an owned
	 * constructor annotated with <code>@CovariantConstructor</code>.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Covariant Constructor</em>' attribute.
	 * @see #setDeclaredCovariantConstructor(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClassifier_DeclaredCovariantConstructor()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredCovariantConstructor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClassifier#isDeclaredCovariantConstructor <em>Declared Covariant Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Covariant Constructor</em>' attribute.
	 * @see #isDeclaredCovariantConstructor()
	 * @generated
	 */
	void setDeclaredCovariantConstructor(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns all super classes and implemented or extended interfaces as classifiers.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.types.IterableOfTClassifier" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%java.util.List%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TClassifier%&gt;&gt; result = &lt;%org.eclipse.xtext.xbase.lib.CollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TClassifier%&gt;&gt;newArrayList();\nfinal &lt;%java.lang.Object%&gt; _superClassifierRefs = this.getSuperClassifierRefs();\nfor (final &lt;%java.lang.Object%&gt; superClassifierRef : ((&lt;%java.lang.Iterable%&gt;&lt;?&gt;) _superClassifierRefs))\n{\n\tif ((superClassifierRef != null))\n\t{\n\t\tfinal &lt;%org.eclipse.n4js.ts.types.Type%&gt; declType = ((&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt;) superClassifierRef).getDeclaredType();\n\t\tif ((declType instanceof &lt;%org.eclipse.n4js.ts.types.TClassifier%&gt;))\n\t\t{\n\t\t\tresult.add(((&lt;%org.eclipse.n4js.ts.types.TClassifier%&gt;)declType));\n\t\t}\n\t}\n}\nreturn result;'"
	 * @generated
	 */
	Iterable<? extends TClassifier> getSuperClassifiers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns all super classes and implemented or extended interfaces as type references.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefIterable" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.xtext.xbase.lib.CollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt;&gt;emptyList();'"
	 * @generated
	 */
	Iterable<ParameterizedTypeRef> getSuperClassifierRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns all implemented (or extended) interfaces
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefIterable" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.xtext.xbase.lib.CollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt;&gt;emptyList();'"
	 * @generated
	 */
	Iterable<ParameterizedTypeRef> getImplementedOrExtendedInterfaceRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Classifiers are usually not final, unless they have a special modifier set.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isFinal();

} // TClassifier
