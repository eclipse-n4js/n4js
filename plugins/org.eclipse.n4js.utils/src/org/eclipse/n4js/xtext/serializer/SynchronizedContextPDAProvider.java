/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.serializer;

import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.serializer.analysis.ContextPDAProvider;
import org.eclipse.xtext.serializer.analysis.ISerState;
import org.eclipse.xtext.serializer.analysis.SerializationContextMap;
import org.eclipse.xtext.util.formallang.Pda;

import com.google.inject.Singleton;

@SuppressWarnings({ "restriction" })
@Singleton
class SynchronizedContextPDAProvider extends ContextPDAProvider {

	@Override
	public SerializationContextMap<Pda<ISerState, RuleCall>> getContextPDAs(Grammar grammar) {
		return SynchronizedSerializationContextMap.from(super.getContextPDAs(grammar));
	}

}
