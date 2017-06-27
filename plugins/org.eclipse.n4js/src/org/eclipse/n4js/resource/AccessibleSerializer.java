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
package org.eclipse.n4js.resource;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parsetree.reconstr.ITokenStream;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.impl.Serializer;

/**
 * Make {@link #serialize(EObject, ITokenStream, SaveOptions)} public. The method is otherwise protected in the
 * framework since the {@link ITokenStream} is internal to the serializer. However, we want to pass an observable token
 * stream into that method thus we need to call it.
 *
 * This is useful for content assist where we want to record the cursor position of a given grammar element.
 */
@SuppressWarnings("restriction")
public class AccessibleSerializer extends Serializer {

	@Override
	public void serialize(EObject obj, ITokenStream tokenStream, SaveOptions options) throws IOException {
		super.serialize(obj, tokenStream, options);
	}

}
