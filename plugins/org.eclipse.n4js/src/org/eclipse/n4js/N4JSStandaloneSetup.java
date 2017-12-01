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

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4mf.N4MFStandaloneSetup;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.regex.RegularExpressionStandaloneSetup;
import org.eclipse.n4js.ts.TypeExpressionsStandaloneSetup;
import org.eclipse.n4js.ts.TypesStandaloneSetup;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.ISetup;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without equinox extension registry. The content here is completely
 * copied from N4JSStandaloneSetup and its super classes.
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
		N4mfPackage.eINSTANCE.getNsURI();
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
		EPackage.Registry.INSTANCE.put(N4mfPackage.eNS_URI, N4mfPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(XMLTypePackage.eNS_URI, XMLTypePackage.eINSTANCE);

		RegularExpressionStandaloneSetup.doSetup();
		TypesStandaloneSetup.doSetup();
		N4MFStandaloneSetup.doSetup();
		TypeExpressionsStandaloneSetup.doSetup();

		Injector injector = createInjector();
		register(injector);
		return injector;
	}

	/**
	 * Registers all dependent packages (N4JS, Types, N4MF, XML) and file extensions (n4js, js)
	 *
	 * @param injector
	 *            the injector to get the resource service provider from
	 */
	public void register(Injector injector) {
		EPackage.Registry.INSTANCE.put(N4JSPackage.eINSTANCE.getNsURI(), N4JSPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(TypeRefsPackage.eINSTANCE.getNsURI(), TypeRefsPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(TypesPackage.eINSTANCE.getNsURI(), TypesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(N4mfPackage.eINSTANCE.getNsURI(), N4mfPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(XMLTypePackage.eINSTANCE.getNsURI(), XMLTypePackage.eINSTANCE);

		org.eclipse.xtext.resource.IResourceFactory resourceFactory = injector
				.getInstance(org.eclipse.xtext.resource.IResourceFactory.class);
		org.eclipse.xtext.resource.IResourceServiceProvider serviceProvider = injector
				.getInstance(org.eclipse.xtext.resource.IResourceServiceProvider.class);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("n4js", resourceFactory);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("n4js",
				serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("n4jsx", resourceFactory);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("n4jsx",
				serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("js", resourceFactory);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("js",
				serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("jsx", resourceFactory);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("jsx",
				serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("n4jsd", resourceFactory);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("n4jsd",
				serviceProvider);
	}
}
