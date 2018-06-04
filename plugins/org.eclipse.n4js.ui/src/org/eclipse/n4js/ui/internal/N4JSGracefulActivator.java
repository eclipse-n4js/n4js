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
package org.eclipse.n4js.ui.internal;

import java.util.concurrent.Semaphore;

import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 *
 */
public class N4JSGracefulActivator extends N4JSActivator {
	private final Semaphore semaphore = new Semaphore(1);

	@Override
	public Injector getInjector(String language) {
		synchronized (semaphore) {
			if (semaphore.availablePermits() < 1) {
				throw new InjectorNotYetAvailableException();
			}
			try {
				semaphore.acquire();
				return super.getInjector(language);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				semaphore.release();
			}
		}
	}

	@Override
	protected Injector createInjector(String language) {
		try {
			Module runtimeModule = getRuntimeModule(language);
			Module sharedStateModule = getSharedStateModule();
			Module uiModule = getUiModule(language);
			Module mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
			return Guice.createInjector(mergedModule);
		} catch (Exception e) {
			// An exception occurring here might be related to Guice:
			// https://stackoverflow.com/questions/39918622/why-is-guice-throwing-computationexception-from-uncaughtexceptionhandler-in-mai.
			e.printStackTrace();
			UIUtils.showError(e);
			System.exit(-1);
			return null;
		}
	}
}
