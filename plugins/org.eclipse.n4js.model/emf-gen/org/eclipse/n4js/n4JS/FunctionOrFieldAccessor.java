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
package org.eclipse.n4js.n4JS;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Or Field Accessor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for functions or getter/setter.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#get_lok <em>lok</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionOrFieldAccessor()
 * @model abstract="true"
 * @generated
 */
public interface FunctionOrFieldAccessor extends AnnotableElement, VariableEnvironmentElement, ThisArgProvider, TypableElement {
	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Block)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionOrFieldAccessor_Body()
	 * @model containment="true"
	 * @generated
	 */
	Block getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Block value);

	/**
	 * Returns the value of the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Transient local arguments variable. Access through #getLocalArgumentsVariable()
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>lok</em>' containment reference.
	 * @see #set_lok(LocalArgumentsVariable)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionOrFieldAccessor__lok()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	LocalArgumentsVariable get_lok();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#get_lok <em>lok</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>lok</em>' containment reference.
	 * @see #get_lok()
	 * @generated
	 */
	void set_lok(LocalArgumentsVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Lazy initialized reference to transient localArgurmentsVariable
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.n4JS.LocalArgumentsVariable%> __lok = this.get_lok();\nboolean _tripleEquals = (__lok == null);\nif (_tripleEquals)\n{\n\tfinal <%org.eclipse.n4js.n4JS.LocalArgumentsVariable%> newLok = <%org.eclipse.n4js.n4JS.N4JSFactory%>.eINSTANCE.createLocalArgumentsVariable();\n\tnewLok.setName(\"arguments\");\n\tfinal <%org.eclipse.xtext.xbase.lib.Procedures.Procedure0%> _function = new <%org.eclipse.xtext.xbase.lib.Procedures.Procedure0%>()\n\t{\n\t\tpublic void apply()\n\t\t{\n\t\t\t<%this%>.set_lok(newLok);\n\t\t}\n\t};\n\t<%org.eclipse.n4js.utils.EcoreUtilN4%>.doWithDeliver(false, _function, this);\n}\nreturn this.get_lok();'"
	 * @generated
	 */
	LocalArgumentsVariable getLocalArgumentsVariable();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isReturnValueOptional();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Default implementation, always returns false (since accessors cannot be async), overridden in FunctionDefinition
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isAsync();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.n4js.n4JS.FunctionOrFieldAccessor%> _this = this;\n<%org.eclipse.emf.ecore.EObject%> _switchResult = null;\nboolean _matched = false;\nif (_this instanceof <%org.eclipse.n4js.n4JS.FunctionDefinition%>)\n{\n\t_matched=true;\n\t_switchResult = ((<%org.eclipse.n4js.n4JS.FunctionDefinition%>)_this).getDefinedType();\n}\nif (!_matched)\n{\n\tif (_this instanceof <%org.eclipse.n4js.n4JS.FieldAccessor%>)\n\t{\n\t\t_matched=true;\n\t\t_switchResult = ((<%org.eclipse.n4js.n4JS.FieldAccessor%>)_this).getDefinedAccessor();\n\t}\n}\nreturn ((<%org.eclipse.n4js.ts.types.IdentifiableElement%>)_switchResult);'"
	 * @generated
	 */
	IdentifiableElement getDefinedFunctionOrAccessor();

} // FunctionOrFieldAccessor
