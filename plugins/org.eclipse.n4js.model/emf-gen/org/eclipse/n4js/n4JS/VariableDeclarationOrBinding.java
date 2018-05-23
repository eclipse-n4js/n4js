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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt; _switchResult = null;\nboolean _matched = false;\nif (this instanceof &lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;)\n{\n\t_matched=true;\n\t_switchResult = &lt;%org.eclipse.emf.common.util.ECollections%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt;toEList(java.util.Collections.&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt;unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt;newArrayList(((&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;) this))));\n}\nif (!_matched)\n{\n\tif (this instanceof &lt;%org.eclipse.n4js.n4JS.VariableBinding%&gt;)\n\t{\n\t\t_matched=true;\n\t\t_switchResult = &lt;%org.eclipse.emf.common.util.ECollections%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt;toEList(&lt;%com.google.common.collect.Iterators%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt;filter(this.eAllContents(), &lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;.class));\n\t}\n}\nif (!_matched)\n{\n\t_switchResult = &lt;%org.eclipse.emf.common.util.ECollections%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt;toEList(java.util.Collections.&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt;unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.&lt;&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt;&gt;newArrayList()));\n}\nreturn _switchResult;'"
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
