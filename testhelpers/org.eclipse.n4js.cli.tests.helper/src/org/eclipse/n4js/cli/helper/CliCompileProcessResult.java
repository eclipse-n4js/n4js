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
package org.eclipse.n4js.cli.helper;

import java.util.Collection;
import java.util.Map;

import org.eclipse.n4js.cli.helper.AbstractCliCompileTest.N4jscVariant;

/**
 * Data class used when n4jsc.jar is executed in a separate process
 */
public class CliCompileProcessResult extends CliCompileResult {

	CliCompileProcessResult() {
		super(N4jscVariant.exprocess);
	}

	@Override
	public int getErrs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getWrns() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> getProjects() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getErrFiles() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getErrMsgs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getWrnFiles() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getWrnMsgs() {
		throw new UnsupportedOperationException();
	}

}
