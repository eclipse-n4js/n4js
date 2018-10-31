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
package org.eclipse.n4js.ui.labeling

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.emf.ecore.EObject

import static org.eclipse.n4js.utils.UtilN4.sanitizeForHTML

import static extension com.google.common.base.Strings.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Extended hover provider which unlike {@link N4JSHoverProvider}
 * handles non-declared AST elements such as expressions.
 */
class N4JSTypeInformationHoverProvider extends N4JSHoverProvider {

	@Inject private extension N4JSTypeSystem;

	override protected hasHover(EObject obj) {
		return doHasHover(obj);
	}

	override protected getLabel(EObject obj) {
		return sanitizeForHTML(doGetLabel(obj).nullToEmpty);
	}

	override protected String getFirstLine(EObject obj) {
		val label = getLabel(obj);
		return if (label.isNullOrEmpty) null else '''Type of selected expression:<br>«IF !label.contains('<b>')»<b>«label»</b>«ELSE»«label»«ENDIF»''';
	}

	/**
	 * Copied from super class to add support interrupting the hover information calculation
	 * when type information does not exist for an expression.
	 */
	override protected String getHoverInfoAsHtml(EObject obj) {
		if (!obj.hasHover) {
			return null;
		}

		//additional point to interrupt popup raise in case of unresolved label
		val firstLine = obj.firstLine;
		if (firstLine.isNullOrEmpty) {
			return null;
		}
		val sb = new StringBuilder(firstLine);
		val documentation = obj.documentation;
		if (null !== documentation && 0 < documentation.length) {
			sb.append('<p>');
			sb.append(documentation);
			sb.append('</p>');
		}
		return sb.toString();
	}

	// Default behavior described in super class.
	def private dispatch doHasHover(EObject obj) {
		return super.hasHover(obj);
	}

	def private dispatch doHasHover(Expression expression) {
		return true;
	}

	// Default behavior described in super class.
	def private dispatch doGetLabel(EObject obj) {
		return super.getLabel(obj);
	}

	def private dispatch doGetLabel(Expression expression) {
		if (null === expression.eResource) {
			return null;
		}
		val type = expression.newRuleEnvironment.type(expression).value;
		return if (null === type) null else type.typeRefAsString;
	}

}
