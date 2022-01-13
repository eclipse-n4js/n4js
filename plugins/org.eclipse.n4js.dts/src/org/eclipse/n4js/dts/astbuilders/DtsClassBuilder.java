/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.astbuilders;

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElementList;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.TypeScriptParser;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsClassBuilder extends AbstractDtsSubBuilder<ClassDeclarationContext, N4ClassDeclaration> {
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder();
	private final DtsTypeVariablesBuilder typeVariablesBuilder = new DtsTypeVariablesBuilder();

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_classElementList);
	}

	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4ClassDeclaration();
		result.setName(ctx.identifierOrKeyWord().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);

		List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(ctx.typeParameters());
		result.getTypeVars().addAll(typeVars);
	}

}
