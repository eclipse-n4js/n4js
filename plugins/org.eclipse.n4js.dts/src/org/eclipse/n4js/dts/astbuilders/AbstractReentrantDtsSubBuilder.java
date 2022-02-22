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

import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Like {@link AbstractDtsSubBuilder}, but for elements that may contain child elements of the same type. For example,
 * this is intended for namespaces, which may contain child namespaces.
 */
public class AbstractReentrantDtsSubBuilder<T extends ParserRuleContext, R> extends AbstractDtsSubBuilder<T, R> {

	/** Stack of results. */
	protected final Stack<R> resultStack = new Stack<>();

	/** Constructor */
	public AbstractReentrantDtsSubBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	/**
	 * Pushes the given new result to the stack and sets {@link AbstractDtsSubBuilder#result} to this value.
	 */
	protected void pushResult(R newResult) {
		resultStack.push(newResult);
		result = newResult;
	}

	/**
	 * Returns the latest result on the top of the stack without removing it from the stack.
	 *
	 * @throw EmptyStackException iff the result stack is empty.
	 */
	protected R peekResult() {
		return resultStack.peek();
	}

	/**
	 * Pops the latest result off the stack and resets {@link AbstractDtsSubBuilder#result} to the previous result. If
	 * no previous result exists, i.e. if this call popped the last result off the stack, field
	 * {@link AbstractDtsSubBuilder#result result} will be left unchanged.
	 *
	 * @throw EmptyStackException iff the result stack is empty.
	 */
	protected R popResult() {
		R oldResult = resultStack.pop();
		if (!resultStack.isEmpty()) {
			result = resultStack.peek();
		}
		return oldResult;
	}

	@Override
	protected void resetResult() {
		super.resetResult();
		resultStack.clear();
	}
}
