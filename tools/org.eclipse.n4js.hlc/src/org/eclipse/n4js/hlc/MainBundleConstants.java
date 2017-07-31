package org.eclipse.n4js.hlc;

import org.eclipse.n4js.hlc.base.N4jscBase;

/**
 * Constants that help to identify main product bundle and method to invoke. This is used in reflection calls to avoid
 * pollution of the pom.xml or MANIFEST.MF of the launcher.
 */
final class MainBundleConstants {

	private static String calculatedBundleName;
	private static String calulatedClassName;

	/**
	 * We could hardcoded names, but dynamically calculating them makes this a bit more maintainable. Also it is easier
	 * to find references etc.
	 */
	static {
		Class<?> appClass = N4jscBase.class;
		String className = appClass.getSimpleName();
		calulatedClassName = appClass.getCanonicalName();
		calculatedBundleName = calulatedClassName.substring(0, calulatedClassName.length() - className.length() - 1);
	}

	/** regex pattern used to identify main product bundle */
	final static String MAIN_BUNDLE_NAME_PATTERN = calculatedBundleName + "*";
	/** fqn of the class with method to invoke */
	final static String MAIN_BUNDLE_CLASS_FQN = calulatedClassName;
	/** name of the method to invoke */
	final static String MAIN_BUNDLE_METHOD_NAME = "main"; // hardcoded :/

}
