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
package org.eclipse.n4js.cli;

import org.eclipse.n4js.xtext.ide.server.build.DefaultBuildRequestFactory;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterBuildFileListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterBuildRequestListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterDeleteListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterGenerateListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterValidateListener;

import com.google.inject.Singleton;

/**
 * Note: This is a stateful singleton on purpose: It adds listeners to every {@link XBuildRequest} instance created.
 */
@Singleton
public class N4jscBuildRequestFactory extends DefaultBuildRequestFactory {
	private AfterValidateListener afterValidateListener;
	private AfterGenerateListener afterGenerateListener;
	private AfterDeleteListener afterDeleteListener;
	private AfterBuildFileListener afterBuildFileListener;
	private AfterBuildRequestListener afterBuildRequestListener;

	/** Create the build request. */
	@Override
	public void onPostCreate(XBuildRequest result) {
		if (afterDeleteListener != null) {
			result.addAfterDeleteListener(afterDeleteListener);
		}
		if (afterValidateListener != null) {
			result.addAfterValidateListener(afterValidateListener);
		}
		if (afterGenerateListener != null) {
			result.addAfterGenerateListener(afterGenerateListener);
		}
		if (afterBuildFileListener != null) {
			result.addAfterBuildFileListener(afterBuildFileListener);
		}
		if (afterBuildRequestListener != null) {
			result.addAfterBuildRequestListener(afterBuildRequestListener);
		}
	}

	/** @return {@link AfterValidateListener} */
	public AfterValidateListener getAfterValidateListener() {
		return afterValidateListener;
	}

	/** Set {@link #afterValidateListener} */
	public void setAfterValidateListener(AfterValidateListener afterValidateListener) {
		this.afterValidateListener = afterValidateListener;
	}

	/** @return {@link AfterGenerateListener} */
	public AfterGenerateListener getAfterGenerateListener() {
		return afterGenerateListener;
	}

	/** Set {@link #afterGenerateListener} */
	public void setAfterGenerateListener(AfterGenerateListener afterGenerateListener) {
		this.afterGenerateListener = afterGenerateListener;
	}

	/** @return {@link AfterDeleteListener} */
	public AfterDeleteListener getAfterDeleteListener() {
		return afterDeleteListener;
	}

	/** Set {@link #afterDeleteListener} */
	public void setAfterDeleteListener(AfterDeleteListener afterDeleteListener) {
		this.afterDeleteListener = afterDeleteListener;
	}

	/** @return {@link AfterBuildFileListener} */
	public AfterBuildFileListener getAfterBuildFileListener() {
		return afterBuildFileListener;
	}

	/** Set {@link #afterBuildFileListener} */
	public void setAfterBuildFileListener(AfterBuildFileListener afterBuildFileListener) {
		this.afterBuildFileListener = afterBuildFileListener;
	}

	/** @return {@link AfterBuildRequestListener} */
	public AfterBuildRequestListener getAfterBuildListener() {
		return afterBuildRequestListener;
	}

	/** Set {@link #afterBuildRequestListener} */
	public void setAfterBuildListener(AfterBuildRequestListener afterBuildRequestListener) {
		this.afterBuildRequestListener = afterBuildRequestListener;
	}
}
