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

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TClass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#isDeclaredAbstract <em>Declared Abstract</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#isDeclaredN4JS <em>Declared N4JS</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#isDeclaredFinal <em>Declared Final</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#isDeclaredPolyfill <em>Declared Polyfill</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#isDeclaredStaticPolyfill <em>Declared Static Polyfill</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#isObservable <em>Observable</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#getSuperClassRef <em>Super Class Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TClass#getImplementedInterfaceRefs <em>Implemented Interface Refs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass()
 * @model
 * @generated
 */
public interface TClass extends TN4Classifier {
	/**
	 * Returns the value of the '<em><b>External</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External</em>' attribute.
	 * @see #setExternal(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_External()
	 * @model unique="false"
	 * @generated
	 */
	boolean isExternal();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClass#isExternal <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External</em>' attribute.
	 * @see #isExternal()
	 * @generated
	 */
	void setExternal(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Abstract</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Abstract</em>' attribute.
	 * @see #setDeclaredAbstract(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_DeclaredAbstract()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredAbstract();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClass#isDeclaredAbstract <em>Declared Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Abstract</em>' attribute.
	 * @see #isDeclaredAbstract()
	 * @generated
	 */
	void setDeclaredAbstract(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared N4JS</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared N4JS</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared N4JS</em>' attribute.
	 * @see #setDeclaredN4JS(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_DeclaredN4JS()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredN4JS();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClass#isDeclaredN4JS <em>Declared N4JS</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared N4JS</em>' attribute.
	 * @see #isDeclaredN4JS()
	 * @generated
	 */
	void setDeclaredN4JS(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Final</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Final</em>' attribute.
	 * @see #setDeclaredFinal(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_DeclaredFinal()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredFinal();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClass#isDeclaredFinal <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Final</em>' attribute.
	 * @see #isDeclaredFinal()
	 * @generated
	 */
	void setDeclaredFinal(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Polyfill</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Polyfill</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Polyfill</em>' attribute.
	 * @see #setDeclaredPolyfill(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_DeclaredPolyfill()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredPolyfill();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClass#isDeclaredPolyfill <em>Declared Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Polyfill</em>' attribute.
	 * @see #isDeclaredPolyfill()
	 * @generated
	 */
	void setDeclaredPolyfill(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Static Polyfill</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Static Polyfill</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Static Polyfill</em>' attribute.
	 * @see #setDeclaredStaticPolyfill(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_DeclaredStaticPolyfill()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredStaticPolyfill();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClass#isDeclaredStaticPolyfill <em>Declared Static Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Static Polyfill</em>' attribute.
	 * @see #isDeclaredStaticPolyfill()
	 * @generated
	 */
	void setDeclaredStaticPolyfill(boolean value);

	/**
	 * Returns the value of the '<em><b>Observable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Observable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Observable</em>' attribute.
	 * @see #setObservable(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_Observable()
	 * @model unique="false"
	 * @generated
	 */
	boolean isObservable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClass#isObservable <em>Observable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Observable</em>' attribute.
	 * @see #isObservable()
	 * @generated
	 */
	void setObservable(boolean value);

	/**
	 * Returns the value of the '<em><b>Super Class Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Class Ref</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Class Ref</em>' containment reference.
	 * @see #setSuperClassRef(ParameterizedTypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_SuperClassRef()
	 * @model containment="true"
	 * @generated
	 */
	ParameterizedTypeRef getSuperClassRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TClass#getSuperClassRef <em>Super Class Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Class Ref</em>' containment reference.
	 * @see #getSuperClassRef()
	 * @generated
	 */
	void setSuperClassRef(ParameterizedTypeRef value);

	/**
	 * Returns the value of the '<em><b>Implemented Interface Refs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implemented Interface Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implemented Interface Refs</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTClass_ImplementedInterfaceRefs()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterizedTypeRef> getImplementedInterfaceRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.isDeclaredAbstract();'"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, return the explicitly declared super class casted to a {@link TClass} or <code>null</code> if
	 * not possible, not available. Ignores implicit super types!
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt; _superClassRef = this.getSuperClassRef();\n&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType = null;\nif (_superClassRef!=null)\n{\n\t_declaredType=_superClassRef.getDeclaredType();\n}\nfinal &lt;%org.eclipse.n4js.ts.types.Type%&gt; superType = _declaredType;\n&lt;%org.eclipse.n4js.ts.types.TClass%&gt; _xifexpression = null;\nif ((superType instanceof &lt;%org.eclipse.n4js.ts.types.TClass%&gt;))\n{\n\t_xifexpression = ((&lt;%org.eclipse.n4js.ts.types.TClass%&gt;)superType);\n}\nelse\n{\n\t_xifexpression = null;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	TClass getSuperClass();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns all super classes, consumed roles and implemented or extend interfaces
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefIterable" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt; _superClassRef = this.getSuperClassRef();\nboolean _tripleNotEquals = (_superClassRef != null);\nif (_tripleNotEquals)\n{\n\treturn &lt;%com.google.common.collect.Iterables%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt;&gt;concat(&lt;%java.util.Collections%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt;&gt;singleton(this.getSuperClassRef()), this.getImplementedInterfaceRefs());\n}\nreturn &lt;%com.google.common.collect.Iterables%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%&gt;&gt;concat(this.getImplementedInterfaceRefs());'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getImplementedInterfaceRefs();'"
	 * @generated
	 */
	Iterable<ParameterizedTypeRef> getImplementedOrExtendedInterfaceRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the class actually is a polyfill or a static-polyfill. (c.f. {@link TClass#isStaticPolyfill()})
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.isDeclaredPolyfill();'"
	 * @generated
	 */
	boolean isPolyfill();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the class actually is a static polyfill (c.f. {@link TClass#isPolyfill()})
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.isDeclaredStaticPolyfill();'"
	 * @generated
	 */
	boolean isStaticPolyfill();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns value of declaredFinal attribute.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.isDeclaredFinal();'"
	 * @generated
	 */
	boolean isFinal();

} // TClass
