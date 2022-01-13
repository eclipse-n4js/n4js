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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classTail;

import java.util.List;
import java.util.Set;

import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyMemberDeclarationContext;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
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
				RULE_classTail,
				RULE_classElementList,
				RULE_classElement);
	}

	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4ClassDeclaration();
		result.setName(ctx.identifierOrKeyWord().getText());
		result.getDeclaredModifiers().add(N4Modifier.EXTERNAL);

		List<N4TypeVariable> typeVars = typeVariablesBuilder.consume(ctx.typeParameters());
		result.getTypeVars().addAll(typeVars);

		walker.enqueue(ctx.classTail());
	}

	@Override
	public void enterPropertyMemberDeclaration(PropertyMemberDeclarationContext ctx) {
		if (ctx.propertyMemberBase() != null && ctx.propertyName() != null) {
			// this is a property
			N4FieldDeclaration fd = N4JSFactory.eINSTANCE.createN4FieldDeclaration();
			LiteralOrComputedPropertyName locpn = N4JSFactory.eINSTANCE.createLiteralOrComputedPropertyName();
			locpn.setLiteralName(ctx.propertyName().getText());
			fd.setDeclaredName(locpn);
			fd.setDeclaredOptional(ctx.QuestionMark() != null);
			result.getOwnedMembersRaw().add(fd);
		}
	}
}
