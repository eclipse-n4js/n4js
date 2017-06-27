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
package org.eclipse.n4js.transpiler.im;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.n4JS.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbol Table Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Each <code>SymbolTableEntry</code> represents a named element in the intermediate model that may be the target of
 * a reference from somewhere else in the intermediate model (represented by {@link ReferencingElement_IM}).
 *  * <h2>Comparison of Use Cases</h2>
 * The three subclasses represent three distinct use cases of symbol table entries, summarized in the following table:
 * <p>
 * <center>
 * <table border="1">
 * <tr>
 *     <th>{@link SymbolTableEntryOriginal}</th>
 *     <th>{@link SymbolTableEntryIMOnly}</th>
 *     <th>{@link SymbolTableEntryInternal}</th>
 * </tr>
 * <tr>
 *     <td>visible from N4JS</td>
 *     <td>*not* visible from N4JS</td>
 *     <td>*not* visible from N4JS</td>
 * </tr>
 * <tr>
 *     <td>
 *         defined in ...
 *         <ul>
 *         <li>original AST or
 *         <li>another N4JS resource (maybe .n4jsd) or
 *         <li>as part of built-in types.
 *         </ul>
 *     </td>
 *     <td>defined in IM</td>
 *     <td>defined elsewhere<br>(bootstrap code, runtime, etc.)</td>
 * </tr>
 * <tr>
 *     <td>maybe* represented in IM<br>(see <code>elementsOfThisName</code>)</td>
 *     <td>always represented in IM<br>(see <code>elementsOfThisName</code>)</td>
 *     <td>never represented in IM<br>(<code>elementsOfThisName</code> is empty)</td>
 * </tr>
 * </table>
 * </center>
 * </p>
 * <p>
 * &#42; a SymbolTableEntryOriginal will be represented in the intermediate model (i.e. will have one or more elements in
 * property <code>elementsOfThisName</code>) if and only if the element was defined in the original AST, because then
 * a copy of that definition (e.g. a class declaration) will have been copied to the intermediate model during the
 * preparation step.
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.SymbolTableEntry#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.SymbolTableEntry#getElementsOfThisName <em>Elements Of This Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.SymbolTableEntry#getReferencingElements <em>Referencing Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntry()
 * @model abstract="true"
 * @generated
 */
public interface SymbolTableEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Name of the entry in the symbol table.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntry_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntry#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Elements Of This Name</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.NamedElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Lists all named things within the intermediate model that use this symbol. There can be more than one user, e.g.
	 * after splitting up a class declaration into a variable-declaration accompanied by a function expression, both
	 * will use the same name.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Elements Of This Name</em>' reference list.
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntry_ElementsOfThisName()
	 * @model
	 * @generated
	 */
	EList<NamedElement> getElementsOfThisName();

	/**
	 * Returns the value of the '<em><b>Referencing Elements</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.transpiler.im.ReferencingElement_IM}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.transpiler.im.ReferencingElement_IM#getRewiredTarget <em>Rewired Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * All the elements of the intermediate model that refer to this symbol table entry.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Referencing Elements</em>' reference list.
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntry_ReferencingElements()
	 * @see org.eclipse.n4js.transpiler.im.ReferencingElement_IM#getRewiredTarget
	 * @model opposite="rewiredTarget"
	 * @generated
	 */
	EList<ReferencingElement_IM> getReferencingElements();

} // SymbolTableEntry
