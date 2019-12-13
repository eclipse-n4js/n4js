/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.semver.serializer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;

/**
 * Overridden to specialize the serialization of data type rule values. The default Xtext generator will insert
 * conservative spaces between the chars.
 */
@SuppressWarnings("restriction")
public class CustomSemverSyntacticSequencer extends SemverSyntacticSequencer {

	@Override
	protected String getFILE_TAGToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "file:";
	}

	@Override
	protected String getSEMVER_TAGToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "semvr:";
	}

}
