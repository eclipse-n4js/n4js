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

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.util.Variance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for all types. This abstraction provides all necessary information
 * when dealing with types, e.g. it helps with conformance checks and other constraints.
 * <p>
 * Note that this class must not be abstract.
 * </p>
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getType()
 * @model
 * @generated
 */
public interface Type extends TExportableElement, TAnnotableElement, Versionable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if type's implementation is provided by runtime. Default implementation returns true, overridden in user-defined (meta) types.
	 * Since it is about the implementation, types without implementation (structural types, primitive types) that are always handled as if they
	 * were only provided by runtime. The flag is usually set via annotations.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return true;'"
	 * @generated
	 */
	boolean isProvidedByRuntime();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if type actually is a polyfill or a static-polyfill. The flag is usually set via annotations and false for most types (only TClass can actually be
	 * declared to be a polyfill).  (c.f. {@link Type#isStaticPolyfill()})
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isPolyfill();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if type actually is a static polyfill. The flag is usually set via annotations and false for most types (only TClass can actually be
	 * declared to be a static polyfill).  (c.f. {@link Type#isPolyfill()})
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isStaticPolyfill();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if type is final, that is no sub-types may be defined by the user.
	 * Default implementation returns true, has to be overridden by subclasses.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return true;'"
	 * @generated
	 */
	boolean isFinal();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns whether references to this type may be flagged as dynamic.
	 * This is false by default, but usually true for TClassifiers.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isDynamizable();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Tells if this type has special support for index access with a numeric index.
	 * Provided only for readability; will return <code>true</code> if and only if {@link #getElementType()}
	 * returns a non-<code>null</code> value.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt; _elementType = this.getElementType();\nreturn (_elementType != null);'"
	 * @generated
	 */
	boolean isArrayLike();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the element type that would be returned if this was accessed by a numeric index or <code>null</code>
	 * if this is not an array-like type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.Object%&gt; _elementType = &lt;%org.eclipse.n4js.ts.types.ArrayLikes%&gt;.getElementType(this);\nreturn ((&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt;) _elementType);'"
	 * @generated
	 */
	TypeRef getElementType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the automatically computed type, that is public for built-in and pseudo types,
	 * project for exported types and private for non-exported declared types.
	 * This base implementation always returns public.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.ts.types.TypeAccessModifier%&gt;.PUBLIC;'"
	 * @generated
	 */
	TypeAccessModifier getTypeAccessModifier();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if at least one type variable is defined.
	 * This actually returns false for most kind of types, only ContainerTypes (such as TClassifiers) may be declared generic---
	 * this method is introduced to simplify client code and reduce the number of instance-of cascades.
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
	 * Convenience method, returns the list of defined type variables or an empty list if the kind of type cannot be generic at all.
	 * This actually returns an empty list for most kind of types, only ContainerTypes (such as TClassifiers) may be declared generic---
	 * this method is introduced to simplify client code and reduce the number of instance-of cascades.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;&gt;emptyEList();'"
	 * @generated
	 */
	EList<TypeVariable> getTypeVars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method for obtaining the variance of this type's type variable with the given index.
	 * For details see {@link TypeVariable#getVariance()}.
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.n4js.ts.types.Variance" unique="false" idxUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.util.Variance%&gt; _xifexpression = null;\nif (((idx &gt;= 0) &amp;&amp; (idx &lt; this.getTypeVars().size())))\n{\n\t_xifexpression = this.getTypeVars().get(idx).getVariance();\n}\nelse\n{\n\t_xifexpression = null;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	Variance getVarianceOfTypeVar(int idx);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the raw type as string, i.e. the type without any type variables.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getName();'"
	 * @generated
	 */
	String getRawTypeAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns string representation of type, basically for testing and debugging.
	 * As the returned string is used for comparison in tests, this method should not be changed.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _xifexpression = null;\nboolean _isGeneric = this.isGeneric();\nif (_isGeneric)\n{\n\t&lt;%java.lang.String%&gt; _name = this.getName();\n\t&lt;%java.lang.String%&gt; _plus = (_name + \"&lt;\");\n\tfinal &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;, &lt;%java.lang.String%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;, &lt;%java.lang.String%&gt;&gt;()\n\t{\n\t\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt; it)\n\t\t{\n\t\t\treturn it.getTypeAsString();\n\t\t}\n\t};\n\t&lt;%java.lang.String%&gt; _join = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getTypeVars(), _function), \",\");\n\t&lt;%java.lang.String%&gt; _plus_1 = (_plus + _join);\n\t_xifexpression = (_plus_1 + \"&gt;\");\n}\nelse\n{\n\t_xifexpression = this.getName();\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	String getTypeAsString();

} // Type
