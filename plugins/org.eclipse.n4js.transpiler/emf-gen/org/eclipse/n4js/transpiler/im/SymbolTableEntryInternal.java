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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbol Table Entry Internal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Internal symbol table entries represent internal low-level identifiable elements that are not visible from N4JS and
 * do not have a representation in the intermediate model (there are only references to this element in the intermediate
 * model!). Therefore, they don't have an originalTarget and no elementsOfThisName. For a comparison, see
 * {@link SymbolTableEntry here}.
 * <p>
 * Examples include <code>$makeClass</code>, <code>$n4export</code> but also SystemJS' <code>System</code> and
 * <code>System.register</code>.
 * <p>
 * Background: sometimes the transpiler has to emit low-level code that uses some global objects that are not
 * visible from N4JS. Therefore, we do not have any representation for these elements, neither in a TModule (or in
 * the original AST, e.g. in case of non-exported variable declarations) nor in the intermediate model. For these
 * cases, the special case of SymbolTableEntryInternal. Internal symbol table entries are the only STE's that are
 * primarily identified by name. So when creating one or searching for them you only have to provide a name.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntryInternal()
 * @model
 * @generated
 */
public interface SymbolTableEntryInternal extends SymbolTableEntry {
} // SymbolTableEntryInternal
