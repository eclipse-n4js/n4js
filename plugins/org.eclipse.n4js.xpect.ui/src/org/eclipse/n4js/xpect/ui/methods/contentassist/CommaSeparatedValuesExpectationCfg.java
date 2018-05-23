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
package org.eclipse.n4js.xpect.ui.methods.contentassist;

import java.lang.annotation.Annotation;

import org.eclipse.xpect.expectation.CommaSeparatedValuesExpectation;

import com.google.common.base.Function;

import org.eclipse.n4js.xpect.config.XpEnvironmentData;

@SuppressWarnings(value = "all")
public class CommaSeparatedValuesExpectationCfg implements CommaSeparatedValuesExpectation {

	private final CommaSeparatedValuesExpectation delegate;
	private boolean ordered;
	private XpEnvironmentData data;

	public XpEnvironmentData getData() {
		return data;
	}

	public void setData(XpEnvironmentData data) {
		this.data = data;
	}

	/***/
	public CommaSeparatedValuesExpectationCfg(CommaSeparatedValuesExpectation delegate) {
		this.delegate = delegate;
		this.ordered = delegate.ordered();
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return CommaSeparatedValuesExpectation.class;
	}

	@Override
	public boolean caseSensitive() {
		return delegate.caseSensitive();
	}

	@Override
	public Class<? extends Function<Object, String>> itemFormatter() {
		return delegate.itemFormatter();
	}

	@Override
	public int maxItemsPerLine() {
		return delegate.maxItemsPerLine();
	}

	@Override
	public int maxLineWidth() {
		return delegate.maxLineWidth();
	}

	@Override
	public boolean ordered() {
		return ordered;
	}

	@Override
	public boolean quoted() {
		return delegate.quoted();
	}

	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	@Override
	public boolean whitespaceSensitive() {
		return delegate.whitespaceSensitive();
	}
}
