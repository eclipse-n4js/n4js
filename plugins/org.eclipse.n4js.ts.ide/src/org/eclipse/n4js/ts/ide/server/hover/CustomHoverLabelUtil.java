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
package org.eclipse.n4js.ts.ide.server.hover;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.UtilN4;

/**
 * Defines custom labels for some AST elements
 */
public class CustomHoverLabelUtil {

	/**
	 * Returns custom labels for some elements. E.g. for functions we show the entire signature instead of just the
	 * name.
	 * <p>
	 * Note: raised visibility to public to make this reusable from N4JSHoverProvider.
	 *
	 * @return a custom label or null
	 */
	public static String getLabel(EObject obj) {
		if (obj instanceof TFunction) {
			// TODO use #getFunctionAsHTML() instead of UtilN4#sanitizeForHTML()
			return UtilN4.sanitizeForHTML(((TFunction) obj).getFunctionAsString());
		} else if (obj instanceof TMember) {
			// TODO use #getMemberAsHTML() instead of UtilN4#sanitizeForHTML()
			return UtilN4.sanitizeForHTML(((TMember) obj).getMemberAsString());
		} else if (obj instanceof TFormalParameter) {
			// TODO use #getFormalParameterAsHTML() instead of UtilN4#sanitizeForHTML()
			return UtilN4.sanitizeForHTML(((TFormalParameter) obj).getFormalParameterAsString());
		} else if (obj instanceof Type) {
			// TODO use #getTypeAsHTML() instead of UtilN4#sanitizeForHTML()
			return UtilN4.sanitizeForHTML(((Type) obj).getTypeAsString());
		} else if (obj instanceof TypeRef) {
			// TODO use #getTypeRefAsHTML() instead of UtilN4#sanitizeForHTML()
			return UtilN4.sanitizeForHTML(((TypeRef) obj).getTypeRefAsString());
		} else if (obj instanceof TVariable) {
			// TODO use #getTypeRefAsHTML() instead of UtilN4#sanitizeForHTML()
			return UtilN4.sanitizeForHTML(((TVariable) obj).getVariableAsString());
		}

		return null;
	}

}
