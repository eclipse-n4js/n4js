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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_arrayLiteral;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_elementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_objectLiteral;

import java.util.Set;

import org.eclipse.n4js.dts.TypeScriptParser.ArrayElementContext;
import org.eclipse.n4js.dts.TypeScriptParser.ArrayLiteralContext;
import org.eclipse.n4js.dts.TypeScriptParser.BindingElementContext;
import org.eclipse.n4js.dts.TypeScriptParser.BindingPatternContext;
import org.eclipse.n4js.dts.TypeScriptParser.MethodPropertyContext;
import org.eclipse.n4js.dts.TypeScriptParser.ObjectLiteralContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyExpressionAssignmentContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertyGetterContext;
import org.eclipse.n4js.dts.TypeScriptParser.PropertySetterContext;
import org.eclipse.n4js.dts.TypeScriptParser.RestParameterInObjectContext;
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;
import org.eclipse.n4js.n4JS.VariableDeclaration;

class DtsBindingPatternBuilder extends AbstractDtsBuilder<BindingPatternContext, BindingPattern> {

	/** Constructor */
	public DtsBindingPatternBuilder(AbstractDtsBuilder<?, ?> dtsBuilder) {
		super(dtsBuilder.tokenStream, dtsBuilder.resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_arrayLiteral,
				RULE_elementList,
				RULE_objectLiteral);
	}

	@Override
	public void enterArrayLiteral(ArrayLiteralContext ctx) {
		result = N4JSFactory.eINSTANCE.createArrayBindingPattern();
	}

	@Override
	public void enterObjectLiteral(ObjectLiteralContext ctx) {
		result = N4JSFactory.eINSTANCE.createObjectBindingPattern();
	}

	@Override
	public void enterArrayElement(ArrayElementContext ctx) {
		BindingElement bindingElem = N4JSFactory.eINSTANCE.createBindingElement();
		bindingElem.setRest(ctx.Ellipsis() != null);
		BindingElementContext bindingElemCtx = ctx.bindingElement();

		if (bindingElemCtx.Identifier() != null) {
			VariableDeclaration varDecl = N4JSFactory.eINSTANCE.createVariableDeclaration();
			varDecl.setName(bindingElemCtx.Identifier().getText());
			bindingElem.setVarDecl(varDecl);
		}
		if (bindingElemCtx.bindingPattern() != null) {
			BindingPattern nestedBP = newBindingPatternBuilder().consume(bindingElemCtx.bindingPattern());
			bindingElem.setNestedPattern(nestedBP);
		}
		((ArrayBindingPattern) result).getElements().add(bindingElem);
	}

	@Override
	public void enterPropertyExpressionAssignment(PropertyExpressionAssignmentContext ctx) {
		BindingProperty bindingProp = N4JSFactory.eINSTANCE.createBindingProperty();
		BindingElement bindingElem = N4JSFactory.eINSTANCE.createBindingElement();
		bindingProp.setDeclaredName(newPropertyNameBuilder().consume(ctx.propertyName()));
		bindingProp.setValue(bindingElem);

		if (ctx.bindingPattern() != null) {
			BindingPattern bindingPattern = newBindingPatternBuilder().consume(ctx.bindingPattern());
			bindingElem.setNestedPattern(bindingPattern);
		} else {
			VariableDeclaration varDecl = N4JSFactory.eINSTANCE.createVariableDeclaration();
			varDecl.setName(ctx.identifierOrKeyWord().getText());
			bindingElem.setVarDecl(varDecl);
		}

		((ObjectBindingPattern) result).getProperties().add(bindingProp);
	}

	@Override
	public void enterPropertyGetter(PropertyGetterContext ctx) {
		// still unsupported
	}

	@Override
	public void enterPropertySetter(PropertySetterContext ctx) {
		// still unsupported
	}

	@Override
	public void enterMethodProperty(MethodPropertyContext ctx) {
		// still unsupported
	}

	@Override
	public void enterRestParameterInObject(RestParameterInObjectContext ctx) {
		// still unsupported
	}

}