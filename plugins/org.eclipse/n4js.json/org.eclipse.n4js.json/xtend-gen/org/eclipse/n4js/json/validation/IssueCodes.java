package org.eclipse.n4js.json.validation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.n4js.utils.NLS;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@NLS(propertyFileName = "messages")
@SuppressWarnings("all")
public class IssueCodes {
  private final static String BUNDLE_NAME = IssueCodes.class.getPackage().getName() + ".messages";
  
  private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
  
  private final static String INITIALIZER = new Function0<String>() {
        public String apply() {
          org.eclipse.osgi.util.NLS.initializeMessages(BUNDLE_NAME, IssueCodes.class);
          return "";
        }
      }.apply();;
  
  private static String getString(final String key) {
    try {
    	String value = RESOURCE_BUNDLE.getString(key);
    	String[] parts = value.split(";;;");
    	return parts[1];
    } catch (MissingResourceException e) {
    	return '!' + key + '!';
    }
  }
  
  public static Severity getDefaultSeverity(final String key) {
    try {
    	String value = RESOURCE_BUNDLE.getString(key);
    	String[] parts = value.split(";;;");
    	String defaultSeverity = parts[0];
    	return Severity.valueOf(defaultSeverity.toUpperCase());
    } catch (MissingResourceException e) {
    	return null;
    }
  }
  
  public final static String PACKAGE_JSON_OBJECT_ERROR = "PACKAGE_JSON_OBJECT_ERROR";
  
  /**
   * package.json exclusive error.
   */
  public static String getMessageForPACKAGE_JSON_OBJECT_ERROR() {
    return org.eclipse.osgi.util.NLS.bind(getString(PACKAGE_JSON_OBJECT_ERROR), new Object [] {  });
  }
}
