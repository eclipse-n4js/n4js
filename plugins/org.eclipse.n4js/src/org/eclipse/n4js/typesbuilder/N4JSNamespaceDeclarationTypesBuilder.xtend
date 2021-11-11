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
package org.eclipse.n4js.typesbuilder

import org.eclipse.n4js.n4JS.N4NamespaceDeclaration
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.TNamespace
import org.eclipse.n4js.ts.types.TypesFactory

public class N4JSNamespaceDeclarationTypesBuilder extends N4JSClassifierDeclarationTypesBuilder {
	
	def package boolean relinkTNamespace(N4NamespaceDeclaration n4Namespace, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		if (n4Namespace.name === null) { // may be null due to syntax errors
			return false;
		}

		val TNamespace namespaceType = target.topLevelTypes.get(idx) as TNamespace
		//namespaceType.relinkClassifierAndMembers(n4Namespace, preLinkingPhase);
		return true;
	}

	def protected TNamespace createTNamespace(N4NamespaceDeclaration n4Namespace, AbstractNamespace target, boolean preLinkingPhase) {
		if (n4Namespace.name === null) {
			return null;
		}

		val namespaceType = createTNamespace(n4Namespace);
		namespaceType.setTypeAccessModifier(n4Namespace)
		

		namespaceType.setProvidedByRuntime(n4Namespace, preLinkingPhase)

		namespaceType.astElement = n4Namespace

		n4Namespace.definedType = namespaceType

		target.namespaces += namespaceType
		return namespaceType;
	}

	def private TNamespace createTNamespace(N4NamespaceDeclaration n4Namespace) {
		val namespaceType = TypesFactory::eINSTANCE.createTNamespace();
		namespaceType.name = n4Namespace.name;
		namespaceType.exportedName = n4Namespace.exportedName;
		namespaceType.external = n4Namespace.external;
		
		return namespaceType
	}

}
