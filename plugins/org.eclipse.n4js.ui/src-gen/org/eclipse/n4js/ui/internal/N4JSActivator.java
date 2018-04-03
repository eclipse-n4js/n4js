/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.internal;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.n4js.N4JSRuntimeModule;
import org.eclipse.n4js.ui.N4JSUiModule;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.eclipse.xtext.util.Modules2;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * This class was generated. Customizations should only happen in a newly introduced subclass.
 */
public class N4JSActivator extends AbstractUIPlugin {

	public static final String ORG_ECLIPSE_N4JS_N4JS = "org.eclipse.n4js.N4JS";

	private static final Logger logger = Logger.getLogger(N4JSActivator.class);

	private static N4JSActivator INSTANCE;

	private final Map<String, Injector> injectors = Collections
			.synchronizedMap(Maps.<String, Injector> newHashMapWithExpectedSize(1));

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		injectors.clear();
		INSTANCE = null;
		super.stop(context);
	}

	public static N4JSActivator getInstance() {
		return INSTANCE;
	}

	public Injector getInjector(String language) {
		synchronized (injectors) {
			Injector injector = injectors.get(language);
			if (injector == null) {
				injector = createInjector(language);
				injectors.put(language, injector);
			}
			return injector;
		}
	}

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
			System.exit(-1);
			return null;
		}
	}

	protected Module getRuntimeModule(String grammar) {
		if (ORG_ECLIPSE_N4JS_N4JS.equals(grammar)) {
			return new N4JSRuntimeModule();
		}
		throw new IllegalArgumentException(grammar);
	}

	protected Module getUiModule(String grammar) {
		if (ORG_ECLIPSE_N4JS_N4JS.equals(grammar)) {
			return new N4JSUiModule(this);
		}
		throw new IllegalArgumentException(grammar);
	}

	protected Module getSharedStateModule() {
		return new SharedStateModule();
	}

}
