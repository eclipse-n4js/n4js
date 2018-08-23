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

import org.eclipse.xtext.serializer.analysis.SemanticSequencerNfaProvider;

import com.google.inject.Singleton;

@SuppressWarnings({ "restriction", "javadoc" })
@Singleton
class SynchronizedSemanticSequencerNfaProvider extends SemanticSequencerNfaProvider {

	public SynchronizedSemanticSequencerNfaProvider() {
		this.cache = new ValueWrappingMap<>(SynchronizedSerializationContextMap::from);
	}

}
