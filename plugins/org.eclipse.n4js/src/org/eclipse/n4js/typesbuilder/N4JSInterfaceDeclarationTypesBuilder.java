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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

/***/
public class N4JSInterfaceDeclarationTypesBuilder extends N4JSClassifierDeclarationTypesBuilder {

	boolean relinkTInterface(N4InterfaceDeclaration n4Interface, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		if (n4Interface.getName() == null) { // may be null due to syntax errors
			return false;
		}

		TInterface interfaceType = (TInterface) target.getTypes().get(idx);
		relinkClassifierAndMembers(interfaceType, n4Interface, preLinkingPhase);
		return true;
	}

	/***/
	protected TInterface createTInterface(N4InterfaceDeclaration n4Interface, AbstractNamespace target,
			boolean preLinkingPhase) {
		if (n4Interface.getName() == null) {
			return null;
		}

		TInterface interfaceType = createTInterface(n4Interface);
		_n4JSTypesBuilderHelper.setTypeAccessModifier(interfaceType, n4Interface);

		interfaceType.setTypingStrategy(
				(n4Interface.getTypingStrategy() == TypingStrategy.DEFAULT) ? TypingStrategy.DEFAULT
						: // STRUCTURAL_FIELD is not allowed on def site, but maybe we got a wrong input
						TypingStrategy.STRUCTURAL);

		_n4JSTypesBuilderHelper.setProvidedByRuntime(interfaceType, n4Interface, preLinkingPhase);
		interfaceType.setDeclaredNonStaticPolyfill(N4JSLanguageUtils.isNonStaticPolyfill(n4Interface));
		interfaceType
				.setDeclaredCovariantConstructor(_n4JSTypesBuilderHelper.isDeclaredCovariantConstructor(n4Interface));
		_n4JSTypeVariableTypesBuilder.addTypeParameters(interfaceType, n4Interface, preLinkingPhase);
		addExtendedInterfaces(interfaceType, n4Interface, preLinkingPhase);

		addFields(interfaceType, n4Interface, preLinkingPhase);
		addMethods(interfaceType, n4Interface, target, preLinkingPhase);

		addGetters(interfaceType, n4Interface, target, preLinkingPhase);
		addSetters(interfaceType, n4Interface, target, preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(interfaceType, n4Interface, preLinkingPhase);

		interfaceType.setAstElement(n4Interface);
		n4Interface.setDefinedType(interfaceType);

		target.getTypes().add(interfaceType);
		return interfaceType;
	}

	private TInterface createTInterface(N4InterfaceDeclaration n4Interface) {
		TInterface interfaceType = TypesFactory.eINSTANCE.createTInterface();
		interfaceType.setName(n4Interface.getName());
		interfaceType.setExternal(n4Interface.isExternal());

		return interfaceType;
	}

	private void addExtendedInterfaces(TInterface interfaceType, N4InterfaceDeclaration c, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			_n4JSTypesBuilderHelper.addCopyOfReferences(interfaceType.getSuperInterfaceRefs(),
					toList(map(c.getSuperInterfaceRefs(), sir -> sir.getTypeRefInAST())));
		}
	}
}
