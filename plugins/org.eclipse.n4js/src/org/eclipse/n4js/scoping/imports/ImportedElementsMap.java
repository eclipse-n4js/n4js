/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.imports;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.scoping.utils.MultiImportedElementsMap;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * A map that can be used to collect imported elements and keep track of them by their {@link QualifiedName}.
 */
@ImplementedBy(SingleImportedElementsMap.class)
public interface ImportedElementsMap {
	/**
	 * Returns true if the map contains an {@link IEObjectDescription} for the given {@link QualifiedName}.
	 */
	boolean containsElement(QualifiedName name);

	/**
	 * Returns all elements that this map contains for the given {@link QualifiedName}.
	 */
	Iterable<IEObjectDescription> getElements(QualifiedName name);

	/**
	 * Puts the given {@link IEObjectDescription} into the map under the given {@link QualifiedName}.
	 */
	void put(QualifiedName name, IEObjectDescription element);

	/**
	 * Returns all {@link IEObjectDescription} that were put into this maa.
	 */
	Iterable<IEObjectDescription> values();

	/**
	 * A provider for {@link ImportedElementsMap} instances.
	 */
	public static class Provider {

		@Inject
		private JavaScriptVariantHelper javaScriptVariantHelper;

		/**
		 * Returns a new instanceof of {@link ImportedElementsMap} based on the {@link JavaScriptVariant} of the given
		 * context.
		 */
		public ImportedElementsMap get(EObject context) {
			if (javaScriptVariantHelper.isMultiQNScope(context)) {
				return new MultiImportedElementsMap();
			} else {
				return new SingleImportedElementsMap();
			}
		}
	}
}
