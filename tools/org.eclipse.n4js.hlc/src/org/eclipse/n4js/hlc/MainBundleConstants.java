package org.eclipse.n4js.hlc;

/**
 * Constants that help to identify main product bundle and method to invoke. This is used in reflection calls to avoid
 * pollution of the pom.xml or MANIFEST.MF of the launcher.
 */
final class MainBundleConstants {

	/** regex pattern used to identify main product bundle */
	final static String MAIN_BUNDLE_NAME_PATTERN = "app.*";
	/** name of the method to invoke */
	final static String MAIN_BUNDLE_METHOD_NAME = "main";
	/** fqn of the class with method to invoke */
	final static String MAIN_BUNDLE_CLASS_FQN = "app.Application";

}
