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
package org.eclipse.n4js;

import static org.eclipse.n4js.N4JSGlobals.JSX_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.JS_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.N4JSD_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.N4JSX_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.N4JS_FILE_EXTENSION;
import static org.eclipse.n4js.N4JSGlobals.TS_FILE_EXTENSION;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.n4js.json.JSONStandaloneSetup;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.regex.RegularExpressionStandaloneSetup;
import org.eclipse.n4js.semver.SemverStandaloneSetup;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without equinox extension registry. The content here is completely
 * copied from N4JSStandaloneSetupGenerate.
 */
public class N4JSStandaloneSetup implements ISetup {

	/**
	 * @return injector configured with N4JS runtime module
	 */
	public Injector createInjector() {
		return Guice.createInjector(new N4JSRuntimeModule());
	}

	/**
	 * Performs the setup and populates the EMF registers in the stand-alone environment.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	public static Injector doSetup() {
		return new N4JSStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// trigger class loading
		TypeRefsPackage.eINSTANCE.getNsURI();
		TypesPackage.eINSTANCE.getNsURI();
		N4JSPackage.eINSTANCE.getNsURI();
		XMLTypePackage.eINSTANCE.getNsURI();

		/*
		 * Explicitly set the package registry. This is necessary to work around EMF's global initialization approach,
		 * which prevents the re-initialization of the package, in case this method needs to be called several times
		 * (e.g. when using JUnit tests with different subclasses of N4JSInjectorProvider in @InjectWith annotations
		 * within the same bundle). For example, see N4JSPackageImpl#isInited and its effect in N4JSPackageImpl#init().
		 * See also GHOLD-254.
		 */
		EPackage.Registry.INSTANCE.put(TypeRefsPackage.eNS_URI, TypeRefsPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(TypesPackage.eNS_URI, TypesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(N4JSPackage.eNS_URI, N4JSPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(XMLTypePackage.eNS_URI, XMLTypePackage.eINSTANCE);

		setupOtherLanguages();

		Injector injector = createInjector();
		register(injector);
		return injector;
	}

	/**
	 * Can be overridden to specialize the setup of other languages, e.g. the LSP case needs to setup the LSP variants
	 * of the other injectors.
	 */
	protected void setupOtherLanguages() {
		RegularExpressionStandaloneSetup.doSetup();
		TypeExpressionsStandaloneSetup.doSetup();
		JSONStandaloneSetup.doSetup();
		SemverStandaloneSetup.doSetup();
	}

	/**
	 * Registers all dependent packages (N4JS, Types, ProjectDescription, XML) and file extensions (n4js, js)
	 *
	 * @param injector
	 *            the injector to get the resource service provider from
	 */
	public void register(Injector injector) {
		EPackage.Registry.INSTANCE.put(N4JSPackage.eINSTANCE.getNsURI(), N4JSPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(TypeRefsPackage.eINSTANCE.getNsURI(), TypeRefsPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(TypesPackage.eINSTANCE.getNsURI(), TypesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(XMLTypePackage.eINSTANCE.getNsURI(), XMLTypePackage.eINSTANCE);

		IResourceFactory resourceFactory = injector.getInstance(org.eclipse.xtext.resource.IResourceFactory.class);
		IResourceServiceProvider serviceProvider = injector
				.getInstance(org.eclipse.xtext.resource.IResourceServiceProvider.class);

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(N4JS_FILE_EXTENSION, resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(N4JS_FILE_EXTENSION, serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(N4JSX_FILE_EXTENSION, resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(N4JSX_FILE_EXTENSION,
				serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(JS_FILE_EXTENSION, resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(JS_FILE_EXTENSION, serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(JSX_FILE_EXTENSION, resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(JSX_FILE_EXTENSION, serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(N4JSD_FILE_EXTENSION, resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(N4JSD_FILE_EXTENSION,
				serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(TS_FILE_EXTENSION, resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(TS_FILE_EXTENSION,
				serviceProvider);
	}
}
