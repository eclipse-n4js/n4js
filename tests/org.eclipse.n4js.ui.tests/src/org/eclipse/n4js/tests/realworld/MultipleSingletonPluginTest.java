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
package org.eclipse.n4js.tests.realworld;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.n4mf.ui.internal.N4MFActivator;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.utils.N4JSInjectorSupplier;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;
import org.eclipse.xtext.ui.shared.internal.SharedStateContributionRegistryImpl;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Binding;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

/**
 * Tests parsing and validation of ListBase and underscore.
 */
public class MultipleSingletonPluginTest extends AbstractBuilderParticipantTest {

	@Inject
	ISharedStateContributionRegistry sharedRegistry;

	/**
	 * Updates the known external library locations with the {@code node_modules} folder.
	 */
	@Before
	public void setup() throws Exception {
		setupExternalLibraries(true, true);
	}

	/**
	 * Tries to make sure the external libraries are cleaned from the Xtext index, cleanup file system leftovers.
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		tearDownExternalLibraries(true);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testListBase() throws Exception {
		ProjectTestsUtils.importProject(new File("probands"), "ListBase");
		IResourcesSetupUtil.waitForBuild();

		Set<Class<?>> singletonInstances = new HashSet<>();
		Injector n4jsInj = new N4JSInjectorSupplier().get();

		Injector n4mfInj = N4MFActivator.getInstance().getInjector(N4MFActivator.ORG_ECLIPSE_N4JS_N4MF_N4MF);
		Injector sharedInj = getSharedInjector();

		checkSingletonsOfInjector(singletonInstances, n4jsInj);
		checkSingletonsOfInjector(singletonInstances, n4mfInj);
		checkSingletonsOfInjector(singletonInstances, sharedInj);

		assertTrue(true);
	}

	private void checkSingletonsOfInjector(Set<Class<?>> singletonInstances, Injector injector) {
		for (Binding<?> b : injector.getAllBindings().values()) {
			Key<?> key = b.getKey();
			TypeLiteral<?> typeLiteral = key.getTypeLiteral();
			Type type = typeLiteral.getType();
			if (type instanceof Class<?>) {
				Class<?> c = (Class<?>) type;
				Singleton annotation = c.getAnnotation(Singleton.class);
				if (annotation != null) {

					int size = injector.findBindingsByType(typeLiteral).size();
					if (size > 1) {
						System.err.println("Found more than one binding");
					}
					if (singletonInstances.contains(c)) {
						String msg = "Singleton class '" + c.getName() + "' is provided twice.";
						System.err.println(msg);
						fail(msg);
					} else {
						singletonInstances.add(c);
					}
				}
			}
		}
	}

	private Injector getSharedInjector() {
		try {
			Class<? extends SharedStateContributionRegistryImpl> clazz = SharedStateContributionRegistryImpl.class;
			Field field = clazz.getDeclaredField("injector");
			field.setAccessible(true);
			Injector injector = (Injector) field.get(this.sharedRegistry);
			return injector;

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
