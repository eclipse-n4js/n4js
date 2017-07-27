package org.eclipse.n4js.hlc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * Product launcher that is responsible for starting Equinox / Eclipse and then loading and running concrete bundles.
 */
@SuppressWarnings("restriction")
public class ProductLauncher {

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

		String[] platformArgs = new String[] { "-consoleLog", "-debug" };

		/** parameters for the product call, e.g. parameters for the N4JSC.class */
		String[] appCallArgs = args;

		List<String> bundlesToInstall = getInstallableBundlesNames(PluginsToLoadConstants.BUNDLES_LIST_RESOURCE,
				PluginsToLoadConstants.PLUGINS_SEPARATOR);
		if (bundlesToInstall.isEmpty())
			throw new RuntimeException("No bundles to load discovered");

		try {
			BundleContext context = startPlatform(platformArgs);

			Set<String> installedBundleDescriptions = preLaodedBundlesNamePtterns(context);
			log("start install bundles");
			for (Iterator<String> iterator = bundlesToInstall.iterator(); iterator.hasNext();) {
				String bundle = iterator.next();
				if (isBundleLoaded(bundle, installedBundleDescriptions)) {
					log("Skip install of already running bundle " + bundle);
					continue;
				}
				InputStream bndInputStream = getResourceAsStream(bundle);
				Objects.requireNonNull(bndInputStream, "Cannot obtain resource for bundle " + bundle);
				context.installBundle(BUNDLE_INSTALL_SCHEME + bundle, bndInputStream);
				bndInputStream.close();
			}
			log("finish install bundles");

			log("load bundle and invoke its method");
			invoke(context, findBundle(context, MainBundleConstants.MAIN_BUNDLE_NAME_PATTERN),
					MainBundleConstants.MAIN_BUNDLE_CLASS_FQN, MainBundleConstants.MAIN_BUNDLE_METHOD_NAME,
					appCallArgs);
			log("invocation finised");

			shutdownPlatform();

		} finally {
			log("finally");
			if (ThreadsUtil.getIdentifiedThredsCount(EQUINOX_THREAD_DESCRIPTION_TOKEN) > 0) {
				log("There are still platform threads running:\n"
						+ ThreadsUtil.getThreadsInfo(EQUINOX_THREAD_DESCRIPTION_TOKEN));
				shutdownPlatform();
				if (ThreadsUtil.getIdentifiedThredsCount(EQUINOX_THREAD_DESCRIPTION_TOKEN) > 0) {
					log("Failed to cleanup platform threads.");
					log(ThreadsUtil.getThreadsInfo(EQUINOX_THREAD_DESCRIPTION_TOKEN));
				}
			}
		}

	}

	/**
	 * {@link EclipseStarter#getSystemBundleContext}
	 *
	 * @throws Exception
	 *             propagates exception from Equinox
	 */
	private static BundleContext startPlatform(String[] platformArgs) throws Exception {
		Map<String, String> ip = new HashMap<>();
		ip.put("eclipse.ignoreApp", "true");
		ip.put("osgi.clean", "true");
		ip.put(EclipseStarter.PROP_NOSHUTDOWN, "false");
		ip.put(EclipseStarter.PROP_CONSOLE_LOG, "true");
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
