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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.n4mf.ui.internal.N4MFActivator;
import org.eclipse.n4js.regex.ui.internal.RegularExpressionActivator;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ts.ui.internal.TypesActivator;
import org.eclipse.n4js.ui.utils.N4JSInjectorSupplier;
import org.eclipse.n4js.utils.InjectorCollector;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Binding;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

/**
 * This test detects multiple instances of injected classes marked with @Singleton.
 * <p>
 * Note that instances are created lazily. Consequently, this test cannot guarantee completeness. In case a case of
 * multiple singleton instances is missing, this test should be adjusted to provoke the lazy creation of this missing
 * case.
 * <p>
 * Note also that singleton classes that are never bound explicitly (meaning in none of the modules), are not checked in
 * this test.
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

	/**
	 * The tests first collects all injectors. The shared injector is identified using the class
	 * {@link InjectorCollector}. Afterwards, it detects all singleton classes that are bound in N4JS related injectors.
	 * Finally, for every such singleton class all injectors are checked whether they created an instance of the
	 * singleton class and whether these instances are the same.
	 */
	@Test
	public void identifyMultipleSingletons() throws Exception {
		ProjectTestsUtils.importProject(new File("probands"), "ListBase");
		IResourcesSetupUtil.waitForBuild();

		Multimap<Class<?>, Injector> singletonInstances = HashMultimap.create();

		Map<Injector, String> injectors = getAllInjectors();
		for (Injector injector : injectors.keySet()) {
			getN4JSSingletonsOfInjector(injector, singletonInstances);
		}

		String status = getMultipleSingletonStatusString(singletonInstances, injectors);

		assertEquals(MultiSingletonExpectation.get(), status);
	}

	Map<Injector, String> getAllInjectors() {
		Map<Injector, String> injectors = new HashMap<>();

		injectors.putAll(InjectorCollector.getSharedInjectors());
		injectors.put(new N4JSInjectorSupplier().get(),
				"N4JS-Injector");
		injectors.put(N4MFActivator.getInstance().getInjector(N4MFActivator.ORG_ECLIPSE_N4JS_N4MF_N4MF),
				"N4MF-Injector");
		injectors.put(RegularExpressionActivator.getInstance()
				.getInjector(RegularExpressionActivator.ORG_ECLIPSE_N4JS_REGEX_REGULAREXPRESSION),
				"Regex-Injector");
		injectors.put(TypesActivator.getInstance().getInjector(TypesActivator.ORG_ECLIPSE_N4JS_TS_TYPES),
				"Types-Injector");
		injectors.put(TesterModule.getInjector(TesterModule.N4_TESTER_MODULE_ID),
				"Tester-Injector");

		return injectors;
	}

	private void getN4JSSingletonsOfInjector(Injector injector, Multimap<Class<?>, Injector> singletonInstances) {
		for (Binding<?> b : injector.getAllBindings().values()) {
			Key<?> key = b.getKey();
			TypeLiteral<?> typeLiteral = key.getTypeLiteral();
			Type type = typeLiteral.getType();
			if (type instanceof Class<?>) {
				Class<?> singletonClass = (Class<?>) type;
				String singletonName = singletonClass.getName();
				Singleton annotation = singletonClass.getAnnotation(Singleton.class);

				if (annotation != null && singletonName.toLowerCase().contains("n4js")) {
					singletonInstances.put(singletonClass, injector);
				}
			}
		}
	}

	private String getMultipleSingletonStatusString(Multimap<Class<?>, Injector> singletonInstances,
			Map<Injector, String> injectors) {

		// sort to preserve output order
		List<Class<?>> sortedByClassName = new ArrayList<>(singletonInstances.keySet());
		Comparator<Class<?>> comparatorByClassName = new Comparator<Class<?>>() {
			@Override
			public int compare(Class<?> c1, Class<?> c2) {
				return c1.getName().compareTo(c2.getName());
			}
		};
		Collections.sort(sortedByClassName, comparatorByClassName);

		String status = "";
		int multipleInstancesCount = 0;
		for (Class<?> singleton : sortedByClassName) {
			String outputForInstance = printInjectorsForInstances(singleton, injectors);
			if (outputForInstance.length() > 0) {
				status += outputForInstance;
				multipleInstancesCount++;
			}
		}
		status = "Found multiple instances for " + multipleInstancesCount + " singleton classes:\n" + status;
		return status;
	}

	private String printInjectorsForInstances(Class<?> singleton, Map<Injector, String> injectors) {
		Multimap<Object, String> instances = HashMultimap.create();
		for (Map.Entry<Injector, String> entry : injectors.entrySet()) {
			String name = entry.getValue();
			Injector inj = entry.getKey();
			try {
				Binding<?> existingBinding = inj.getExistingBinding(Key.get(singleton));
				if (existingBinding != null) {
					Object instance = inj.getInstance(singleton);
					instances.put(instance, name);
				}
			} catch (Exception e) {
				// ignore
			}
		}

		int instanceCount = instances.keySet().size();
		String status = "";
		if (instanceCount > 1) {
			String singletonName = singleton.getName();
			status += "Singleton '" + singletonName + "' has " + instanceCount;
			status += " instances that have the following injectors:\n";
			List<String> statusLines = new ArrayList<>();
			for (Object instance : instances.keySet()) {
				List<String> injNames = new ArrayList<>(instances.get(instance));
				Collections.sort(injNames); // sort to preserve output order
				statusLines.add("\t- " + String.join(", ", injNames));
			}

			Collections.sort(statusLines); // sort to preserve output order
			status += String.join("\n", statusLines);
			status += "\n";
		}

		return status;
	}

}
