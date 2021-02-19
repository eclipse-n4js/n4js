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
package org.eclipse.n4js.ide;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.n4js.N4JSRuntimeModule;
import org.eclipse.n4js.N4JSStandaloneSetup;
import org.eclipse.n4js.json.ide.JSONIdeSetup;
import org.eclipse.n4js.regex.ide.RegularExpressionIdeSetup;
import org.eclipse.n4js.semver.ide.SemverIdeSetup;
import org.eclipse.n4js.ts.ide.TypeExpressionsIdeSetup;
import org.eclipse.n4js.ts.ide.TypesIdeSetup;
import org.eclipse.xtext.ide.server.ServerModule;
import org.eclipse.xtext.util.Modules2;

import com.google.common.base.Optional;
import com.google.common.collect.ObjectArrays;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class N4JSIdeSetup extends N4JSStandaloneSetup {

	@Override
	public Injector createInjector() {
		Module[] modules = {
				new ServerModule(), new N4JSRuntimeModule(), new N4JSIdeModule()
		};

		Optional<Class<? extends Module>> overridingModuleCls = getOverridingModule();
		if (overridingModuleCls.isPresent()) {
			try {
				Module overridingModule = overridingModuleCls.get().getDeclaredConstructor().newInstance();
				modules = ObjectArrays.concat(modules, overridingModule);
			} catch (NoSuchMethodException | IllegalAccessException | InstantiationException
					| InvocationTargetException e) {
				throw new WrappedException(e);
			}
		}

		return Guice.createInjector(
				Modules2.mixin(modules));
	}

	/** If present, the returned module will override default bindings in the injector. */
	protected Optional<Class<? extends Module>> getOverridingModule() {
		return Optional.absent();
	}

	@Override
	protected void setupOtherLanguages() {
		new RegularExpressionIdeSetup().createInjectorAndDoEMFRegistration();
		new TypesIdeSetup().createInjectorAndDoEMFRegistration();
		new TypeExpressionsIdeSetup().createInjectorAndDoEMFRegistration();
		new JSONIdeSetup().createInjectorAndDoEMFRegistration();
		new SemverIdeSetup().createInjectorAndDoEMFRegistration();
	}

}
