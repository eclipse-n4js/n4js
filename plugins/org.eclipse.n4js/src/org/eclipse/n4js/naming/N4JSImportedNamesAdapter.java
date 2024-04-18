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
package org.eclipse.n4js.naming;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * Adapts default implementation to not normalize qualified names to be all lower case.
 * <p>
 * cf IDE-369
 */
public class N4JSImportedNamesAdapter extends ImportedNamesAdapter {

	/**
	 * Adapts default implementation to not normalize qualified names to be all lower case.
	 */
	public class N4JSWrappingScope extends WrappingScope {

		private final IScope delegate;

		/**
		 * @param scope
		 *            wrapped scope
		 */
		public N4JSWrappingScope(IScope scope) {
			super(scope);
			this.delegate = scope;
		}

		@Override
		public IEObjectDescription getSingleElement(QualifiedName name) {
			addImportedName(name);
			return delegate.getSingleElement(name);
		}

		@Override
		public Iterable<IEObjectDescription> getElements(final QualifiedName name) {
			return new Iterable<>() {
				@Override
				public Iterator<IEObjectDescription> iterator() {
					addImportedName(name);
					final Iterable<IEObjectDescription> elements = delegate.getElements(name);
					return elements.iterator();
				}
			};
		}
	}

	private final Set<QualifiedName> nonNullImportedNames = new HashSet<>();

	@Override
	public IScope wrap(IScope scope) {
		return new N4JSWrappingScope(scope);
	}

	@Override
	synchronized public TreeSet<QualifiedName> getImportedNames() {
		return Sets.newTreeSet(nonNullImportedNames);
	}

	/** Adds given element to set of imported names. */
	synchronized public void addImportedName(QualifiedName name) {
		Preconditions.checkNotNull(name);
		nonNullImportedNames.add(name);
	}

	@Override
	synchronized public void clear() {
		nonNullImportedNames.clear();
	}
}
