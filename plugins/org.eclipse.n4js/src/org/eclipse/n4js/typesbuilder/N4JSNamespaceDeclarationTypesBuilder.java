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
package org.eclipse.n4js.typesbuilder;

import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TypesFactory;

@SuppressWarnings("javadoc")
public class N4JSNamespaceDeclarationTypesBuilder extends N4JSClassifierDeclarationTypesBuilder {

	boolean relinkTNamespace(N4NamespaceDeclaration n4Namespace, AbstractNamespace target,
			@SuppressWarnings("unused") boolean preLinkingPhase, int idx) {

		if (n4Namespace.getName() == null) { // may be null due to syntax errors
			return false;
		}

		TNamespace namespaceType = target.getNamespaces().get(idx);
		_n4JSTypesBuilderHelper.ensureEqualName(n4Namespace, namespaceType);

		namespaceType.setAstElement(n4Namespace);
		n4Namespace.setDefinedType(namespaceType);

		return true;
	}

	protected TNamespace createTNamespace(N4NamespaceDeclaration n4Namespace, AbstractNamespace target,
			boolean preLinkingPhase) {
		if (n4Namespace.getName() == null) {
			return null;
		}

		TNamespace namespaceType = createTNamespace(n4Namespace);
		_n4JSTypesBuilderHelper.setTypeAccessModifier(namespaceType, n4Namespace);

		_n4JSTypesBuilderHelper.setProvidedByRuntime(namespaceType, n4Namespace, preLinkingPhase);

		namespaceType.setAstElement(n4Namespace);
		n4Namespace.setDefinedType(namespaceType);

		target.getNamespaces().add(namespaceType);
		return namespaceType;
	}

	private TNamespace createTNamespace(N4NamespaceDeclaration n4Namespace) {
		TNamespace namespaceType = TypesFactory.eINSTANCE.createTNamespace();
		namespaceType.setName(n4Namespace.getName());
		namespaceType.setExternal(n4Namespace.isExternal());

		return namespaceType;
	}

}
