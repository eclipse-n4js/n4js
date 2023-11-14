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
import org.eclipse.xtext.serializer.analysis.ContextTypePDAProvider;
import org.eclipse.xtext.serializer.analysis.ISerState;
import org.eclipse.xtext.serializer.analysis.SerializationContextMap;
import org.eclipse.xtext.util.formallang.Pda;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;

import com.google.inject.Singleton;

@SuppressWarnings({ "restriction" })
@Singleton
class SynchronizedContextTypePDAProvider extends ContextTypePDAProvider {

	public SynchronizedContextTypePDAProvider() {
		try {
			ValueWrappingMap<Grammar, SerializationContextMap<Pda<ISerState, RuleCall>>> vwm = new ValueWrappingMap<>(
					SynchronizedSerializationContextMap::from);
			new ReflectExtensions().set(this, "cache", vwm);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
