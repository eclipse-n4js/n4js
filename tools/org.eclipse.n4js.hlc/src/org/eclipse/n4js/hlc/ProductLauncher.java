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
package org.eclipse.n4js.hlc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * Product launcher that is responsible for starting Equinox/Eclipse and then loading and running concrete bundles.
 * <p>
 * While the actual compiler implementation is done in {@code org.eclipse.hlc.base} and its transitive dependencies, the
 * headless tool contains two small code parts related only to packaging and startup.
 * <p>
 * First is the custom class loader and main class. Since the headless tool is packaged as JAR-with-JARs, it requires a
 * custom class loader to be able to load dependencies from within itself. Currently we use an implementation copied
 * from the JDT tools see <a href=
 * "https://github.com/eclipse/eclipse.jdt.ui/tree/master/org.eclipse.jdt.ui/jar%20in%20jar%20loader/org/eclipse/jdt/internal/jarinjarloader">jarinjarloader</a>
 * <p>
 * As for the main class we can highlight three classes that delegate to each other at startup of the application:
 * <ul>
 * <li/><b>org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader</b> is the main class from a plain JAR point of view.
 * It is tightly integrated with the custom class loader. In general it is infrastructure level, and in principle it
 * should not require any modifications (unless the packaging of the jar is changed).
 * <li/><b>org.eclipse.n4js.hlc.ProductLauncher</b> is a middleware. It bootstraps Eclipse/Equinox platform and loads
 * all the bundles.
 * <li/><b>org.eclipse.n4js.hlc.base.N4jscBase</b> is the main entry point. This is the place were actual compiler code
 * starts.
 * </ul>
 */
@SuppressWarnings("restriction")
public class ProductLauncher {
	private static boolean DEBUG = false;

	/** see org.eclipse.core.runtime.adaptor.EclipseStarter.REFERENCE_SCHEME */
	private final static String BUNDLE_INSTALL_SCHEME = "reference:file:/";

	/** Token used to identify equinox threads. */
	private final static String EQUINOX_THREAD_DESCRIPTION_TOKEN = "equinox";

	/**
	 * @param args
	 *            mix of arguments from the OSGI and for the application
	 * @throws Exception
	 *             Exceptions propagated from the Equinox platform
	 */
	public static void main(String[] args) throws Exception {

		if (args == null || args.length == 0) {
			args = new String[] {};
		}
		// is user passed args are mixture of plain platform arguments and arguments for
		// the (later) invoked bundle, then now is the time to process them.

		String[] platformArgs = new String[] { /* defaults are in ProductLauncher.startPlatform */ };

		/** parameters for the product call, e.g. parameters for the N4JSC.class */
		String[] appCallArgs = args;

		List<String> bundlesToInstall = getInstallableBundlesNames(PluginsToLoadConstants.BUNDLES_LIST_RESOURCE,
				PluginsToLoadConstants.PLUGINS_SEPARATOR);
		if (bundlesToInstall.isEmpty())
			throw new RuntimeException("No bundles to load discovered");

		final Path osgiConfigurationArea = Files.createTempDirectory(".n4jsc");
		if (!osgiConfigurationArea.toFile().exists())
			throw new RuntimeException("Cannot obtain working directory");

		try {
			BundleContext context = startPlatform(platformArgs, osgiConfigurationArea);

			Set<String> installedBundleDescriptions = preLaodedBundlesNamePtterns(context);
			log("start install bundles");
			for (Iterator<String> iterator = bundlesToInstall.iterator(); iterator.hasNext();) {
				String bundle = iterator.next();
				if (isBundleLoaded(bundle, installedBundleDescriptions)) {
					log("SKIP INSTALL  " + bundle);
					continue;
				}
				InputStream bndInputStream = getResourceAsStream(bundle);
				Objects.requireNonNull(bndInputStream, "Cannot obtain resource for bundle " + bundle);
				String loc = BUNDLE_INSTALL_SCHEME + bundle;
				log("INSTALL " + loc);
				context.installBundle(loc, bndInputStream);
				bndInputStream.close();
			}

			log("load bundle and invoke its method");
			invoke(context, findBundle(context, MainBundleConstants.MAIN_BUNDLE_NAME_PATTERN),
					MainBundleConstants.MAIN_BUNDLE_CLASS_FQN, MainBundleConstants.MAIN_BUNDLE_METHOD_NAME,
					appCallArgs);
			log("invocation finished");

			shutdownPlatform();

		} finally {
			log("finally");

			String errors = "";

			cleanupWorkArea(osgiConfigurationArea);
			if (osgiConfigurationArea.toFile().exists())
				errors += ("ERROR: Cannot cleanup working area : " + osgiConfigurationArea.toString() + "\n");

			cleanupThreads();
			if (ThreadsUtil.getIdentifiedThredsCount(EQUINOX_THREAD_DESCRIPTION_TOKEN) > 0)
				errors += ("ERROR: There are still platform threads running:\n"
						+ ThreadsUtil.getThreadsInfo(EQUINOX_THREAD_DESCRIPTION_TOKEN));

			if (!errors.isEmpty())
				System.err.println(errors);
		}
	}

	private static void cleanupThreads() {
		if (ThreadsUtil.getIdentifiedThredsCount(EQUINOX_THREAD_DESCRIPTION_TOKEN) > 0) {
			log("There are still platform threads running:\n"
					+ ThreadsUtil.getThreadsInfo(EQUINOX_THREAD_DESCRIPTION_TOKEN));
			try {
				shutdownPlatform();
			} catch (Exception e) {
				// log error
				e.printStackTrace();
			}
		}
	}

	private static void cleanupWorkArea(Path path) {
		log("working area exists : " + path.toFile().exists());
		try {
			FileDeleter.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@link EclipseStarter#getSystemBundleContext}
	 *
	 * @throws Exception
	 *             propagates exception from Equinox
	 */
	private static BundleContext startPlatform(String[] platformArgs, Path osgiConfigurationArea) throws Exception {
		Map<String, String> ip = new HashMap<>();
		ip.put("eclipse.ignoreApp", "true");
		ip.put("eclipse.consoleLog", "true");
		ip.put("eclipse.log.level", "ALL");

		/*
		 * http://help.eclipse.org/oxygen/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fmisc%2Fruntime-
		 * options.html
		 */
		// ip.put("osgi.debug", "");
		// ip.put("osgi.debug.verbose", "true");

		ip.put("osgi.clean", "true");
		ip.put("osgi.framework.shape", "jar");
		ip.put("osgi.configuration.area", osgiConfigurationArea.toAbsolutePath().toString());
		ip.put("osgi.noShutdown", "false");
		EclipseStarter.setInitialProperties(ip);

		BundleContext context = EclipseStarter.startup(platformArgs, null);
		return context;
	}

	private static void shutdownPlatform() throws Exception {
		EclipseStarter.shutdown();
	}

	private static long findBundle(BundleContext context, String namePattern) {
		Bundle[] installedBundles = context.getBundles();
		for (int i = 0; i < installedBundles.length; i++) {
			Bundle bundle = installedBundles[i];
			if (bundle.getSymbolicName().matches(namePattern)) {
				return bundle.getBundleId();
			}
		}
		throw new RuntimeException("Cannot locate bundle with name pattern " + namePattern);
	}

	private static Object invoke(BundleContext context, long bundleID, String classFqn, String methodToInvoke,
			String[] args)
			throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		Bundle appBundle = context.getBundle(bundleID);
		Class<?> appClass = appBundle.loadClass(classFqn);
		final Method method = appClass.getMethod(methodToInvoke, args.getClass());
		return method.invoke(null, new Object[] { args });
	}

	private static Set<String> preLaodedBundlesNamePtterns(BundleContext context) {
		Set<String> installedBundleDescriptions = new HashSet<>();
		Bundle[] installed0 = context.getBundles();
		for (int i = 0; i < installed0.length; i++) {
			String descriptor = installed0[i].getSymbolicName() + ".*";
			installedBundleDescriptions.add(descriptor);
		}
		return installedBundleDescriptions;
	}

	private static boolean isBundleLoaded(String bundleName, Set<String> preloadeBundlePatternNames) {
		for (Iterator<String> iterator = preloadeBundlePatternNames.iterator(); iterator.hasNext();) {
			String pattern = iterator.next();
			if (bundleName.matches(pattern)) {
				return true;
			}
		}

		return false;
	}

	private static void log(String msg) {
		if (DEBUG)
			System.out.println(new java.text.SimpleDateFormat("HH:mm:ss:SSS").format(new java.util.Date()) + " " + msg);
	}

	private static List<String> getInstallableBundlesNames(String path, String separator) {
		List<String> filenames = new ArrayList<>();
		String resource;
		try (InputStream in = getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			while ((resource = br.readLine()) != null) {
				filenames.addAll(Arrays.asList(resource.split(separator)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filenames;
	}

	private static InputStream getResourceAsStream(String resource) {
		final InputStream in = getContextClassLoader().getResourceAsStream(resource);
		return in == null ? ProductLauncher.class.getResourceAsStream(resource) : in;
	}

	private static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
