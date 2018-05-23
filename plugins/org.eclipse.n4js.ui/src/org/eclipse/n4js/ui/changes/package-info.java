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
/**
 * This package contains a small framework for managing textual changes in one or more
 * {@link org.eclipse.jface.text.IDocument}s. Changes can be composed of other changes and may span multiple files.
 * <p>
 * Note that this is not intended to be (or become) a refactoring framework. Refactoring entails semantic / structural
 * changes whereas the classes in this package handle purely textual changes. However, the semantic changes produced by
 * a future refactoring framework will probably be converted to textual changes represented by the classes in this
 * package before actually being applied to the source code.
 * <p>
 * Usually, instances of {@link org.eclipse.n4js.ui.changes.IChange IChange} should be created using the methods in
 * class {@link org.eclipse.n4js.ui.changes.ChangeProvider ChangeProvider} then be applied to one or more documents
 * all in a single step by invoking helper method
 * {@link org.eclipse.n4js.ui.changes.ChangeManager#applyAll(java.util.Collection) apply()} in
 * {@link org.eclipse.n4js.ui.changes.ChangeManager}.
 * <p>
 * Implementation notes:
 * <ul>
 * <li>currently this is just a base implementation intended to be extended over time (for example when used for
 * refactoring later). However, if there are no subclasses of IAtomicChange other than class Replacement in the future,
 * the implementation could be simplified by removing IChange and the ICompositeChange vs. IAtomicChange distinction and
 * just using standard collections of Replacement to represent composite changes.
 * </ul>
 */
package org.eclipse.n4js.ui.changes;
