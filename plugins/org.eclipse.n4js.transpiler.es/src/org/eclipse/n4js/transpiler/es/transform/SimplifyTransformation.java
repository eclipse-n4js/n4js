/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;

import java.util.List;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.inject.Inject;

/**
 * Performs various simplifications of the output code. This should run rather late to have those simplifications be
 * applied to the results of, i.e. code generated by, the main transformations.
 */
public class SimplifyTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		List<LiteralOrComputedPropertyName> nodes = collectNodes(getState().im, LiteralOrComputedPropertyName.class,
				false);
		for (LiteralOrComputedPropertyName locpn : nodes) {
			simplify(locpn);
		}
	}

	private void simplify(LiteralOrComputedPropertyName name) {
		// simplify { ["name"+1]: ... } to { ["name1"]: ... }
		if (name.getKind() == PropertyNameKind.COMPUTED
				&& isOfTypeStringInAST(name.getExpression())) {

			replace(name.getExpression(), _StringLiteral(name.getComputedName()));
		}

		// simplify { ["name"]: ... } to { "name": ... }
		if (name.getKind() == PropertyNameKind.COMPUTED && name.getExpression() instanceof StringLiteral) {

			name.setKind(PropertyNameKind.STRING);
			name.setLiteralName(name.getComputedName());
			name.setComputedName(null);
			remove(name.getExpression());
		}

		// simplify { "name": ... } to { name: ... } (iff 'name' is a valid identifier)
		if (name.getKind() == PropertyNameKind.STRING
				&& name.getLiteralName() != null
				&& N4JSLanguageUtils.isValidIdentifier(name.getLiteralName())) {

			name.setKind(PropertyNameKind.IDENTIFIER);
		}
	}

	/**
	 * Returns true iff the given expression has a corresponding expression in the original AST and that expression in
	 * the AST is of type 'string'. Returns false in all error cases.
	 * <p>
	 * Does not support expressions created by earlier transformations (i.e. without original AST node); returns false
	 * in those cases.
	 */
	private boolean isOfTypeStringInAST(Expression expressionInIM) {
		Expression expressionInAST = getState().tracer.getOriginalASTNodeOfSameType(expressionInIM, false);
		if (expressionInAST != null) {
			TypeRef typeRef = ts.type(getState().G, expressionInAST);
			if (!(typeRef instanceof UnknownTypeRef)) {
				return ts.subtypeSucceeded(getState().G, typeRef, stringTypeRef(getState().G));
			}
		}
		return false;
	}
}