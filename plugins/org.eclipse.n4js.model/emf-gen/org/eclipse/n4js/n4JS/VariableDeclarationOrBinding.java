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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Declaration Or Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableDeclarationOrBinding()
 * @model abstract="true"
 * @generated
 */
public interface VariableDeclarationOrBinding extends ControlFlowElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.VariableDeclaration%>> _switchResult = null;\nboolean _matched = false;\nif (this instanceof <%org.eclipse.n4js.n4JS.VariableDeclaration%>)\n{\n\t_matched=true;\n\t_switchResult = <%org.eclipse.emf.common.util.ECollections%>.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>toEList(java.util.Collections.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>newArrayList(((<%org.eclipse.n4js.n4JS.VariableDeclaration%>) this))));\n}\nif (!_matched)\n{\n\tif (this instanceof <%org.eclipse.n4js.n4JS.VariableBinding%>)\n\t{\n\t\t_matched=true;\n\t\t_switchResult = <%org.eclipse.emf.common.util.ECollections%>.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>toEList(<%com.google.common.collect.Iterators%>.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>filter(this.eAllContents(), <%org.eclipse.n4js.n4JS.VariableDeclaration%>.class));\n\t}\n}\nif (!_matched)\n{\n\t_switchResult = <%org.eclipse.emf.common.util.ECollections%>.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>toEList(java.util.Collections.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>newArrayList()));\n}\nreturn _switchResult;'"
	 * @generated
	 */
	EList<VariableDeclaration> getVariableDeclarations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	Expression getExpression();

} // VariableDeclarationOrBinding
